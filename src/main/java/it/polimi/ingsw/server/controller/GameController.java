package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.protocol.*;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.characters.cards.HerbsGranny;
import it.polimi.ingsw.server.model.characters.parameters.ArchipelagoParameter;
import it.polimi.ingsw.server.model.characters.parameters.IntegerParameter;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import it.polimi.ingsw.server.model.characters.parameters.PlayerParameter;
import it.polimi.ingsw.server.serverexceptions.EndGameException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This is the controller of the game
 */
public class GameController implements Observer {
    private final Game game;
    private final ArrayList<View> virtualViews;
    private final ArrayList<View> pendingViews;
    private final ArrayList<JoinGameMessage> pendingJGM;
    private Tower currentSelectedTower;
    private Wizard currentSelectedWizard;
    private boolean lastRound;
    private boolean deleteGame;
    private boolean gameStarted;

    private int numCurrPlayers = 0;
    private int numGamePlayers = -1;

    //This ArrayList contains all the players with their Virtual View sorted according to the turn of the game
    private List<TurnController> orderedPlayers;
    private int indexCurrentPlayer;
    private int turnPhase;

    private final Object lockDeleteGame;
    private final Object lockGameStarted;

    private final Object lockNumCurrPlayers;
    private final Object lockNumGamePlayers;

    public GameController(Game game) {
        virtualViews = new ArrayList<>();
        this.game = game;
        pendingViews = new ArrayList<>();
        pendingJGM = new ArrayList<>();
        orderedPlayers = new ArrayList<>();
        lastRound = false;
        deleteGame = false;
        gameStarted = false;
        lockDeleteGame = new Object();
        lockGameStarted = new Object();
        lockNumCurrPlayers = new Object();
        lockNumGamePlayers = new Object();
    }

    @Override
    public synchronized void update(Message message) {
        manageMessage(message);
    }

    /**
     * It's used when all the joinGame parameters are valid and the player joins the game officially
     *
     * @param view is the Virtual View of the player
     */
    private void addVirtualView(View view) {
        virtualViews.add(view);
    }

    /**
     * This method is called when GameController is updated by a VirtualView
     */
    private void manageMessage(Message message) {
        switch (message.getMessageType()) {
            case JOIN_GAME:
                manageJoinGame(message);
                break;
            case SELECTED_GAME_SETTINGS:
                manageGameSettings(message);
                break;
            case SELECTED_TOWER_COLOR:
                manageSelectedTowerColor(message);
                break;
            case SELECTED_WIZARD:
                manageSelectedWizard(message);
                break;
            case SELECTED_CARD:
                manageSelectedCard(message);
                break;
            case MOVE_STUDENTS:
                manageMoveStudents(message);
                break;
            case STEPS_MOTHER_NATURE:
                manageStepsMotherNature(message);
                break;
            case SELECTED_CLOUD:
                manageSelectedCloud(message);
                break;
            case USE_CHARACTER:
                manageUseCharacter(message);
                break;
            case MOVE_ON:
                turnPhaseSuspended(orderedPlayers.get(indexCurrentPlayer).getPlayer(), orderedPlayers.get(indexCurrentPlayer).getPlayerView());
                break;
        }
    }

    /**
     * It's used to add a player to the game
     *
     * @param joinGameMessage is the loginMessage received by the client
     * @param view            specifies the virtualView of the client
     */
    public synchronized void addPlayer(JoinGameMessage joinGameMessage, VirtualView view) {
        setNumCurrPlayers();
        if (!isDeleteGame()) {
            if (pendingViews.size() == 0) {
                pendingViews.add(view);
                pendingJGM.add(joinGameMessage);
                manageMessage(joinGameMessage);
            } else {
                pendingViews.add(view);
                pendingJGM.add(joinGameMessage);
                view.joinGameError("WAIT"); //wait for other players to join
            }
        } else {
            view.endGame("Error during join game, try again");
        }
    }


    /**
     * It's called when the player wants to use a character (obviously there will be this message just in the case
     * expertMode = true)
     * All the parameters are checked client side
     *
     * @param message is the message received
     */
    private void manageUseCharacter(Message message) {
        UseCharacterMessage useCharacterMessage = (UseCharacterMessage) message;
        int indexCharacter = useCharacterMessage.getCharacterIndex();
        ArrayList<Parameter> parameters = useCharacterMessage.getParameters();
        Player player = orderedPlayers.get(indexCurrentPlayer).getPlayer();
        VirtualView playerView = orderedPlayers.get(indexCurrentPlayer).getPlayerView();

        parameters = correctCharactersParameters(indexCharacter, parameters);

        if (parameters != null) {
            // Check if the player has enough coins
            if (player.getCoins() >= game.getCharacters()[indexCharacter].getCost()) {
                // Player can use the character
                try {
                    // update now coins:
                    player.setCoins(player.getCoins() - game.getCharacters()[indexCharacter].getCost());
                    game.setCoins(game.getCoins() + game.getCharacters()[indexCharacter].getCost());

                    game.useCharacter(indexCharacter, parameters);
                    turnPhaseSuspended(player, playerView);

                } catch (EndGameException e) {
                    for (TurnController p : orderedPlayers) {
                        p.getPlayerView().endGame(player.getNickname() + "wins for: " + e.getMessage());
                        p.getPlayerView().closeConnection();
                    }
                }
            } else {
                // error coins
                playerView.errorCharacter("Not enough coins!");
            }
        } else {
            playerView.errorCharacter("You can't use this character!");
        }
    }


    /**
     * This method is used in manageUseCharacter() when a player has just used a character (successfully or not)
     * to tell him which is the operation that was suspended during the decision to use a character
     *
     * @param player     specifies the current player
     * @param playerView specifies the current player's Virtual View
     */
    private void turnPhaseSuspended(Player player, VirtualView playerView) {
        switch (turnPhase) {
            case 1:
                //Move students
                playerView.isYourTurn();
                break;
            case 2:
                //moveMotherNature
                playerView.moveMotherNature(player.getDeck().getCardPlayed().getMotherMovements());
                break;
            case 3:
                //ChooseCloud
                ArrayList<Integer> cloudsIndexes = new ArrayList<>();
                ArrayList<Cloud> clouds = game.getClouds();
                for (int i = 0; i < clouds.size(); i++)
                    if (clouds.get(i).getStudents().size() != 0)
                        cloudsIndexes.add(i + 1);
                playerView.chooseCloud(cloudsIndexes);
                break;
        }
    }

    /**
     * This method is used to create the correct parameters to give to the character chosen by the player
     *
     * @param indexCharacter specifies the character chosen by the player
     * @param parameters     is an ArrayList<Parameter> which contains the data received by the client
     * @return an ArrayList<Parameter> which contains the right parameters for the character chosen by the player
     */
    private ArrayList<Parameter> correctCharactersParameters(int indexCharacter, ArrayList<Parameter> parameters) {
        ArrayList<Parameter> correctParameters = null;

        switch (game.getCharacters()[indexCharacter].getId()) {
            case 1:
                if (((IntegerParameter) parameters.get(0)).getValue() != -1) {
                    ArchipelagoParameter archipelagoParameterForMonk = new ArchipelagoParameter("ARCHIPELAGO", game.getArchipelagos().get(((IntegerParameter) parameters.get(0)).getValue()));
                    correctParameters = new ArrayList<>();
                    correctParameters.add(archipelagoParameterForMonk);
                    correctParameters.add(parameters.get(1));
                }
                break;
            case 2:
            case 8:
                correctParameters = new ArrayList<>();
                correctParameters.add(new PlayerParameter("PLAYER", orderedPlayers.get(indexCurrentPlayer).getPlayer()));
                break;
            case 3:
            case 4:
            case 6:
            case 9:
            case 12:
                correctParameters = parameters;
                break;
            case 5:

                if (((HerbsGranny) game.getCharacters()[indexCharacter]).getNoEntry() > 0) {
                    ArchipelagoParameter archipelagoParameterHB = new ArchipelagoParameter("ARCHIPELAGO", game.getArchipelagos().get(((IntegerParameter) parameters.get(0)).getValue()));
                    correctParameters = new ArrayList<>();
                    correctParameters.add(archipelagoParameterHB);
                }
                break;
            case 7:
            case 10:
                if (((IntegerParameter) parameters.get(0)).getValue() != -1) {
                    PlayerParameter playerParameter = new PlayerParameter("PLAYER", orderedPlayers.get(indexCurrentPlayer).getPlayer());
                    correctParameters = new ArrayList<>();
                    correctParameters.add(playerParameter);
                    correctParameters.add(parameters.get(0));
                    int index = ((IntegerParameter) parameters.get(0)).getValue();
                    for (int i = 0; i < 2 * index; i++)
                        correctParameters.add(parameters.get(i + 1));
                }
                break;
            case 11:
                if (((IntegerParameter) parameters.get(0)).getValue() != -1) {
                    PlayerParameter playerParameterForSP = new PlayerParameter("PLAYER", orderedPlayers.get(indexCurrentPlayer).getPlayer());
                    correctParameters = new ArrayList<>();
                    correctParameters.add(playerParameterForSP);
                    correctParameters.add(parameters.get(0));
                }
                break;
        }

        return correctParameters;
    }

    /**
     * It's called when the player chooses the cloud at the end of the turn
     *
     * @param message is the message received
     */
    private void manageSelectedCloud(Message message) {
        SelectedCloudMessage selectedCloudMessage = (SelectedCloudMessage) message;
        int cloudChosen = selectedCloudMessage.getCloudIndex();
        ArrayList<Integer> cloudsIndexes = new ArrayList<>();
        ArrayList<Cloud> clouds = game.getClouds();

        if (cloudChosen <= 0 || cloudChosen > clouds.size() || clouds.get(cloudChosen - 1).getStudents().size() == 0) {
            for (int i = 0; i < clouds.size(); i++)
                if (clouds.get(i).getStudents().size() != 0)
                    cloudsIndexes.add(i + 1);

            orderedPlayers.get(indexCurrentPlayer).getPlayerView().errorCloud("Error index cloud!", cloudsIndexes);
        } else {
            //Move Students from cloud to entrance
            cloudChosen--;
            game.chooseCloud(orderedPlayers.get(indexCurrentPlayer).getPlayer().getNickname(), cloudChosen);

            //Set activeCharacter to null because the player's turn is ended
            game.setActiveCharacter(null);

            //Check End Round or End Game:
            checkEndRoundOrEndGame();
        }
    }


    /**
     * This method is called in manageSelectedCloud() method to check if the player who has just chosen a cloud
     * was the last one for this round.
     * if the player was the last for this round we check if this was the last round and we notify all the players
     * with an EndGameMessage which contains the nickname of the winner
     */
    private void checkEndRoundOrEndGame() {

        //Check if there are other players for this round:
        if (indexCurrentPlayer < orderedPlayers.size() - 1)
            keepOnThisRound();
        else {
            //There are no more players for this round:
            //Check if Bag is empty or there are no more assistant cards playable, in this case the game has to finish
            if (!game.isBagEmpty() && orderedPlayers.get(0).getPlayer().getDeck().getCards().size() != 0) {
                game.fillClouds();
            } else
                lastRound = true;

            //Check if this was the last round
            if (lastRound) {
                setUpEndGame();
                endGameSystem();

                //Close Connection now
                for (TurnController t : orderedPlayers)
                    t.getPlayerView().closeConnection();

                setDeleteGame(true);
            } else
                startANewRound();
        }
    }


    /**
     * This method is the first one called during the endGameSystem, it orders the players and notifies all of them
     * with the current status of the table
     */
    private void setUpEndGame() {
        //We order the list of players:
        Function<TurnController, Integer> f = (TurnController p) -> p.getPlayer().getBoard().getNumberTowers();
        orderedPlayers = orderedPlayers.stream()
                .sorted((Comparator.comparing(f)))
                .collect(Collectors.toList());

        //Notify all the players with the current table's status
        for (TurnController t : orderedPlayers) {
            t.getPlayerView().playersUpdate(game.getPlayers());
            t.getPlayerView().islandsUpdate(game.getArchipelagos());
        }
    }

    /**
     * This method is called when there are other players who have to play in this round
     */
    private void keepOnThisRound() {
        //There are other players for this round
        indexCurrentPlayer++;
        for (int i = 0; i < orderedPlayers.size(); i++)
            if (i != indexCurrentPlayer)
                orderedPlayers.get(i).getPlayerView().waitTurn(orderedPlayers.get(indexCurrentPlayer).getPlayer().getNickname());

        game.setCurrentPlayer(orderedPlayers.get(indexCurrentPlayer).getPlayer());
        orderedPlayers.get(indexCurrentPlayer).getPlayerView().isYourTurn();
        turnPhase = 1;
    }

    /**
     * This method is called when the game ends and we have to check who is the winner
     * We will perform the right task according to the number of player of the game
     */
    private void endGameSystem() {
        //We choose what to do according to the number of players:
        switch (game.getNumberPlayers()) {
            case 2:
                endGameWithTwoPlayers();
                break;
            case 3:
                endGameWithThreePlayers();
                break;
            case 4:
                endGameWithFourPlayers();
                break;
        }
    }

    /**
     * This method is called to start a new round:
     * we order now the players according to the assistant card they played
     * The first player to play is the one who has chosen (in the last round) an assistant card with lower value
     */
    private void startANewRound() {
        int indexFirstPLayer = 0;
        Player firstPlayerOfTurn = orderedPlayers.get(0).getPlayer();
        for (int i = 1; i < orderedPlayers.size(); i++) {
            if (orderedPlayers.get(i).getPlayer().getDeck().getCardPlayed().getCardValue() < firstPlayerOfTurn.getDeck().getCardPlayed().getCardValue()) {
                indexFirstPLayer = i;
                firstPlayerOfTurn = orderedPlayers.get(i).getPlayer();
            }
        }

        ArrayList<TurnController> help = new ArrayList<>();
        for (int i = 0; i < indexFirstPLayer; i++) {
            help.add(orderedPlayers.get(0));
            orderedPlayers.remove(0);
        }

        orderedPlayers.addAll(help);
        help.clear();

        //Start a new round
        indexCurrentPlayer = 0;
        game.setCurrentPlayer(orderedPlayers.get(0).getPlayer());
        orderedPlayers.get(0).getPlayerView().chooseCard(orderedPlayers.get(0).getPlayer().getDeck().getCards());
        for (int i = 1; i < orderedPlayers.size(); i++)
            orderedPlayers.get(i).getPlayerView().waitTurn(orderedPlayers.get(0).getPlayer().getNickname());
    }

    /**
     * This method is called when the game ends and the number of players of the game is 4
     */
    public void endGameWithFourPlayers() {
        Player p1, p2, playerLeaderWinner;
        int winner;

        p1 = game.getTeams()[0].getPlayerLeader(); //player leader 1
        p2 = game.getTeams()[1].getPlayerLeader(); //player leader 2

        if (p1.getBoard().getNumberTowers() == 0 || p2.getBoard().getNumberTowers() == 0) {
            //I can directly say which team has won
            if (p1.getBoard().getNumberTowers() == 0)
                winner = 0; //Team 1
            else
                winner = 1; //Team 2
        } else {
            if (p1.getBoard().getNumberTowers() <= p2.getBoard().getNumberTowers())
                winner = compareTwoPlayers(p1, p2); //Team 1 or Team 2
            else
                winner = 1; //Team 2

        }

        if (winner != -1) {
            Player partner;
            if (winner == 0) {
                playerLeaderWinner = p1;
                partner = game.getTeams()[0].getPlayers()[1];
            } else {
                playerLeaderWinner = p2;
                partner = game.getTeams()[1].getPlayers()[1];
            }

            for (TurnController p : orderedPlayers) {
                p.getPlayerView().endGame("Winners: " + playerLeaderWinner.getNickname() + " and " + partner.getNickname());
            }

        } else {
            for (TurnController p : orderedPlayers) {
                p.getPlayerView().endGame("Draw Between Teams!");
            }
        }
    }

    /**
     * This method is called when the game ends and the number of players of the game is 3
     */
    private void endGameWithThreePlayers() {
        Player p1, p2, p3;
        int winner;

        p1 = orderedPlayers.get(0).getPlayer();
        p2 = orderedPlayers.get(1).getPlayer();
        p3 = orderedPlayers.get(2).getPlayer();

        if (p1.getBoard().getNumberTowers() < p2.getBoard().getNumberTowers()) {
            //p1 wins
            winner = 0;
            for (TurnController p : orderedPlayers) {
                p.getPlayerView().endGame(orderedPlayers.get(winner).getPlayer().getNickname() + " is the winner!");
            }

        } else {
            if (p2.getBoard().getNumberTowers() == p3.getBoard().getNumberTowers()) {
                //All the players have the same number of towers on their board
                Function<TurnController, Integer> f2 = (TurnController p) -> p.getPlayer().getBoard().numberProfessorOwned();
                orderedPlayers = orderedPlayers.stream().sorted(Comparator.comparing(f2).reversed()).collect(Collectors.toList());

                if (orderedPlayers.get(0).getPlayer().getBoard().numberProfessorOwned() == orderedPlayers.get(1).getPlayer().getBoard().numberProfessorOwned()) {
                    if (orderedPlayers.get(0).getPlayer().getBoard().numberProfessorOwned() == orderedPlayers.get(2).getPlayer().getBoard().numberProfessorOwned()) {
                        for (TurnController p : orderedPlayers) {
                            p.getPlayerView().endGame("Draw between: " + orderedPlayers.get(0).getPlayer().getNickname() + ", " + orderedPlayers.get(1).getPlayer().getNickname() + " and " + orderedPlayers.get(2).getPlayer().getNickname());
                        }
                    } else {
                        for (TurnController p : orderedPlayers) {
                            p.getPlayerView().endGame("Draw between: " + orderedPlayers.get(0).getPlayer().getNickname() + " and " + orderedPlayers.get(1).getPlayer().getNickname());
                        }
                    }
                } else {
                    for (TurnController p : orderedPlayers) {
                        p.getPlayerView().endGame(orderedPlayers.get(0).getPlayer().getNickname() + " is the winner!");
                    }
                }

            } else {
                //Compare just p1 and p2
                winner = compareTwoPlayers(p1, p2);

                if (winner != -1) {
                    for (TurnController p : orderedPlayers) {
                        p.getPlayerView().endGame(orderedPlayers.get(winner).getPlayer().getNickname() + " is the winner!");
                    }
                } else {
                    //Draw between p1 and p2
                    for (TurnController p : orderedPlayers) {
                        p.getPlayerView().endGame("Draw between: " + orderedPlayers.get(0).getPlayer().getNickname() + " and " + orderedPlayers.get(1).getPlayer().getNickname());
                    }
                }
            }
        }
    }

    /**
     * This method is called when the game ends and the number of players of the game is 2
     */
    private void endGameWithTwoPlayers() {
        Player p1, p2;
        int winner;

        p1 = orderedPlayers.get(0).getPlayer();
        p2 = orderedPlayers.get(1).getPlayer();

        winner = compareTwoPlayers(p1, p2);

        if (winner != -1) {
            //One of the 2 players wins
            for (TurnController p : orderedPlayers) {
                p.getPlayerView().endGame(orderedPlayers.get(winner).getPlayer().getNickname() + " is the winner!");
            }
        } else {
            //DRAW
            for (TurnController p : orderedPlayers) {
                p.getPlayerView().endGame("DRAW between: " + orderedPlayers.get(0).getPlayer().getNickname() + " and " + orderedPlayers.get(1).getPlayer().getNickname());
            }
        }
    }

    /**
     * This method is used with a game with 2 or 4 players
     * It used to compare 2 generics players to check which one (or which team) has won
     *
     * @param p1 could be a player leader (with a 4 player game) or the first player of the orderedPlayers list
     * @param p2 could be a player leader (with a 4 player game) or the second player of the orderedPlayers list
     * @return an integer which describes which player (or which team) has won
     */
    private int compareTwoPlayers(Player p1, Player p2) {
        int winner;

        if (p1.getBoard().getNumberTowers() < p2.getBoard().getNumberTowers())
            winner = 0;
        else {
            int p1Professors;
            int p2Professors;

            p1Professors = p1.getBoard().numberProfessorOwned();
            p2Professors = p2.getBoard().numberProfessorOwned();

            if (game.getNumberPlayers() == 4) {
                //Take partners and sum their number of professors
                Player partner1, partner2;
                Team[] teams = game.getTeams();
                if (teams[0].getTower().equals(p1.getBoard().getTowerColor())) {
                    partner1 = teams[0].getPlayers()[1];
                    partner2 = teams[1].getPlayers()[1];
                } else {
                    partner1 = teams[1].getPlayers()[1];
                    partner2 = teams[0].getPlayers()[1];
                }

                p1Professors += partner1.getBoard().numberProfessorOwned();
                p1Professors += partner2.getBoard().numberProfessorOwned();
            }

            if (p1Professors > p2Professors)
                winner = 0;
            else {
                if (p1Professors == p2Professors)
                    winner = -1;
                else
                    winner = 1;
            }
        }

        return winner;
    }

    /**
     * It's called when the player chooses the number of steps mother nature has to perform
     *
     * @param message is the message received
     */
    private void manageStepsMotherNature(Message message) {
        StepsMotherNatureMessage steps = (StepsMotherNatureMessage) message;
        ArrayList<Integer> cloudsIndexes = new ArrayList<>();
        ArrayList<Cloud> clouds = game.getClouds();
        int stepsChosen = steps.getSteps();
        // if the player's choice is valid
        if (stepsChosen > 0 && stepsChosen <= orderedPlayers.get(indexCurrentPlayer).getPlayer().getDeck().getCardPlayed().getMotherMovements()) {
            try {
                game.moveMotherNature(stepsChosen);
                for (int i = 0; i < clouds.size(); i++)
                    if (clouds.get(i).getStudents().size() != 0)
                        cloudsIndexes.add(i + 1);

                if (cloudsIndexes.size() > 0) {
                    orderedPlayers.get(indexCurrentPlayer).getPlayerView().chooseCloud(cloudsIndexes);
                    turnPhase = 3;
                } else {
                    //All the clouds are empty, we skip this phase
                    //Set activeCharacter to null because the player's turn is ended
                    game.setActiveCharacter(null);

                    //Check End Round or End Game:
                    checkEndRoundOrEndGame();
                }

            } catch (EndGameException e) {
                //End of the game, notify all the players
                setUpEndGame();
                endGameSystem();

                //Close Connection now
                for (TurnController t : orderedPlayers)
                    t.getPlayerView().closeConnection();
            }
        } else
            orderedPlayers.get(indexCurrentPlayer).getPlayerView().errorMotherNature("Error steps value", orderedPlayers.get(indexCurrentPlayer).getPlayer().getDeck().getCardPlayed().getMotherMovements());
    }

    /**
     * It's called when the player chooses where and which students to move
     *
     * @param message is the message received
     */
    private void manageMoveStudents(Message message) {
        MoveStudentsMessage moveStudentsMessage = (MoveStudentsMessage) message;
        ArrayList<Integer> entranceIndexes = moveStudentsMessage.getEntranceIndexes();
        ArrayList<Integer> destinationIndexes = moveStudentsMessage.getDestinationIndexes();
        Board b = orderedPlayers.get(indexCurrentPlayer).getPlayer().getBoard();
        Player p = orderedPlayers.get(indexCurrentPlayer).getPlayer();
        boolean validIndexes = true;
        EnumMap<Student, Integer> tempSum = new EnumMap<>(Student.class);

        //Check entrance indexes:
        for (int i = 0; i < entranceIndexes.size() && validIndexes; i++) {
            if (entranceIndexes.get(i) <= 0 || entranceIndexes.get(i) >= 10 ||
                    orderedPlayers.get(indexCurrentPlayer).getPlayer().getBoard().getEntrance()[entranceIndexes.get(i) - 1] == null) {
                validIndexes = false;
            }
        }

        //Check destination indexes:
        for (int i = 0; i < destinationIndexes.size() && validIndexes; i++) {
            if (destinationIndexes.get(i) <= 0 || destinationIndexes.get(i) >= 14)
                validIndexes = false;
        }

        if (validIndexes) {
            //Fix indexes:
            for (int i = 0; i < entranceIndexes.size(); i++)
                entranceIndexes.set(i, entranceIndexes.get(i) - 1);

            //Check here if the hall is full for the students chosen:
            tempSum.put(Student.GREEN, b.getNumberOfStudents(Student.GREEN));
            tempSum.put(Student.RED, b.getNumberOfStudents(Student.RED));
            tempSum.put(Student.YELLOW, b.getNumberOfStudents(Student.YELLOW));
            tempSum.put(Student.PINK, b.getNumberOfStudents(Student.PINK));
            tempSum.put(Student.BLUE, b.getNumberOfStudents(Student.BLUE));
            boolean hallFull = false;
            for (int z = 0; z < destinationIndexes.size() && !hallFull; z++) {
                Student colorCheck = Student.valueOf(b.getEntrance()[entranceIndexes.get(z)].name());
                if (destinationIndexes.get(z) == 13) {
                    if (tempSum.get(colorCheck) == 10)
                        hallFull = true;
                    else
                        tempSum.put(colorCheck, tempSum.get(colorCheck) + 1);
                }
            }

            if (!hallFull) {
                //Move students now:
                for (int i = 0; i < entranceIndexes.size(); i++) {
                    if (destinationIndexes.get(i) != 13) {
                        //Move to island
                        boolean found = false;
                        int j = 0;
                        int k = 0;
                        for (; j < game.getArchipelagos().size() && !found; j++) {
                            k = 0;
                            for (; k < game.getArchipelagos().get(j).getIslands().size() && !found; k++) {
                                destinationIndexes.set(i, destinationIndexes.get(i) - 1);
                                if (destinationIndexes.get(i) == 0) {
                                    found = true;
                                }
                            }
                        }

                        Island destinationIsland = game.getArchipelagos().get(j - 1).getIslands().get(k - 1);
                        b.moveStudentToIsland(destinationIsland, entranceIndexes.get(i));
                    } else {
                        //Move to hall
                        Student color = Student.valueOf(b.getEntrance()[entranceIndexes.get(i)].name());
                        b.moveStudentToHall(entranceIndexes.get(i));
                        if (game.isExpertMode()) {
                            int numberStudents = b.getNumberOfStudents(color);
                            int remainder = numberStudents % 3;
                            numberStudents = numberStudents / 3;
                            if (remainder == 0) {
                                if (numberStudents > p.getBoard().getTakenCoins().get(color)) {
                                    if (game.getCoins() > 0) {
                                        p.getBoard().getTakenCoins().put(color, p.getBoard().getTakenCoins().get(color) + 1);
                                        p.addCoin();
                                        game.setCoins(game.getCoins() - 1);
                                    }
                                }
                            }
                        }
                    }
                }

                game.checkTeachers();

                orderedPlayers.get(indexCurrentPlayer).getPlayerView().moveMotherNature(p.getDeck().getCardPlayed().getMotherMovements());
                turnPhase = 2;
            } else {
                orderedPlayers.get(indexCurrentPlayer).getPlayerView().errorStudents("One of the tables is full!");
            }
        } else
            orderedPlayers.get(indexCurrentPlayer).getPlayerView().errorStudents("Parameters not valid!");
    }

    /**
     * It's called when the player played the assistant card
     *
     * @param message is the message received
     */
    private void manageSelectedCard(Message message) {
        SelectedCardMessage selectedCardMessage = (SelectedCardMessage) message;
        int indexCard = selectedCardMessage.getCard();
        indexCard = indexCard - 1;
        Player player = orderedPlayers.get(indexCurrentPlayer).getPlayer();
        VirtualView playerView = orderedPlayers.get(indexCurrentPlayer).getPlayerView();

        if (indexCard < 0 || indexCard > player.getDeck().getCards().size() - 1)
            playerView.errorCard("invalid index", player.getDeck().getCards());
        else {
            //Check for available card
            ArrayList<Assistant> cardsAlreadyPlayed = new ArrayList<>();
            for (int i = indexCurrentPlayer - 1; i >= 0; i--)
                cardsAlreadyPlayed.add(orderedPlayers.get(i).getPlayer().getDeck().getCardPlayed());

            //check if the Assistant has already been played during this turn
            if (cardsAlreadyPlayed.contains(player.getDeck().getCards().get(indexCard))) {
                //check if no other cards can be played
                boolean isPlayable = true;
                for (int i = 0; i < player.getDeck().getCards().size() && isPlayable; i++)
                    if (!player.getDeck().getCards().get(i).equals(player.getDeck().getCards().get(indexCard)))
                        if (!cardsAlreadyPlayed.contains(player.getDeck().getCards().get(i)))
                            isPlayable = false;
                if (!isPlayable)
                    playerView.errorCard("invalid card, choose a card which has not been already chosen", player.getDeck().getCards());
                else
                    playAnAssistantCard(player, indexCard);
            } else
                playAnAssistantCard(player, indexCard);
        }
    }


    /**
     * This method is called when the current player has chosen a playable assistant card
     *
     * @param player    is the current player
     * @param indexCard is the index of the list which contains all the assistant card of the player, it specifies
     *                  which card has to be played
     */
    private void playAnAssistantCard(Player player, int indexCard) {
        player.getDeck().playACard(indexCard);
        //There are other players who have to choose a card
        if (indexCurrentPlayer < orderedPlayers.size() - 1)
        {
            indexCurrentPlayer++;
            Player nextPlayer = orderedPlayers.get(indexCurrentPlayer).getPlayer();
            game.setCurrentPlayer(orderedPlayers.get(indexCurrentPlayer).getPlayer());
            for (TurnController p : orderedPlayers) {
                if (!p.getPlayer().getNickname().equals(nextPlayer.getNickname()))
                    p.getPlayerView().waitTurn(nextPlayer.getNickname());
            }
            orderedPlayers.get(indexCurrentPlayer).getPlayerView().chooseCard(nextPlayer.getDeck().getCards());
        } else {
            //We sort orderedPlayers according to the value of the card chosen by each player
            Function<TurnController, Integer> f = (TurnController el) -> el.getPlayer().getDeck().getCardPlayed().getCardValue();
            orderedPlayers = orderedPlayers.stream().sorted(Comparator.comparing(f)).collect(Collectors.toList());
            indexCurrentPlayer = 0;
            for (int i = 1; i < orderedPlayers.size(); i++)
                orderedPlayers.get(i).getPlayerView().waitTurn(orderedPlayers.get(0).getPlayer().getNickname());
            game.setCurrentPlayer(orderedPlayers.get(0).getPlayer());
            orderedPlayers.get(0).getPlayerView().isYourTurn();
            turnPhase = 1;
        }
    }

    /**
     * It's used when a message with SELECTED_WIZARD label is received
     *
     * @param message is the message received
     */
    private void manageSelectedWizard(Message message) {
        if (checkAvailableWizards().contains(((SelectedWizardMessage) message).getWizard())) {
            currentSelectedWizard = ((SelectedWizardMessage) message).getWizard();
            addToGame();
            pendingViews.remove(0);
            pendingJGM.remove(0);
            if (pendingJGM.size() > 0)
                manageMessage(pendingJGM.get(0));
        } else
            pendingViews.get(0).errorWizard("invalid wizard", checkAvailableWizards());
    }

    /**
     * It's used when a message with SELECTED_TOWER_COLOR label is received
     *
     * @param message is the message received
     */
    private void manageSelectedTowerColor(Message message) {
        if (checkAvailableTowers().contains(((SelectedTowerColorMessage) message).getColor())) {
            currentSelectedTower = ((SelectedTowerColorMessage) message).getColor();
            pendingViews.get(0).chooseWizard(checkAvailableWizards());
        } else
            pendingViews.get(0).errorTowerColor("invalid tower", checkAvailableTowers());
    }

    /**
     * It's used when a message with JOIN_GAME label is received
     *
     * @param message is the message received
     */
    private void manageJoinGame(Message message) {
        if (game.getPlayers().size() == game.getNumberPlayers()) {
            for (View v : pendingViews)
                v.joinGameError("FULL");
            pendingViews.clear();
            pendingJGM.clear();
        } else {
            //Now this View can cause the end of the game due to player's disconnection
            ((VirtualView) pendingViews.get(0)).setJoinedGame(true);
            if (((VirtualView) pendingViews.get(0)).getConnection()) {
                if (checkNickname(((JoinGameMessage) message).getNickname())) {
                    if (game.getPlayers().size() == 0)
                        pendingViews.get(0).chooseGameSettings();
                    else
                        pendingViews.get(0).chooseTowerColor(checkAvailableTowers());

                    //Reset the correct nickName Chosen
                    pendingJGM.set(0, (JoinGameMessage) message);
                }
            } else
                ((VirtualView) pendingViews.get(0)).closeConnection();
        }
    }

    /**
     * It's used to check if the Nickname chosen by the player has already been used
     *
     * @param nickname value
     * @return true if the Nickname is available
     */
    private boolean checkNickname(String nickname) {
        for (Player p : game.getPlayers()) {
            if (nickname.equals(p.getNickname())) {
                pendingViews.get(0).joinGameError("invalid nickname");
                return false;
            }
        }
        return true;
    }

    /**
     * It's used to get all the Towers available that the player can choose
     *
     * @return the list of towers available
     */
    private ArrayList<Tower> checkAvailableTowers() {
        ArrayList<Tower> temp = new ArrayList<>();
        ArrayList<Tower> available = new ArrayList<>();
        if (game.getNumberPlayers() == 2) {
            for (Player p : game.getPlayers())
                temp.add(p.getBoard().getTowerColor());
            if (!temp.contains(Tower.BLACK))
                available.add(Tower.BLACK);
            if (!temp.contains(Tower.WHITE))
                available.add(Tower.WHITE);
            return available;
        } else if (game.getNumberPlayers() == 3) {
            for (Player p : game.getPlayers())
                temp.add(p.getBoard().getTowerColor());
            if (!temp.contains(Tower.BLACK))
                available.add(Tower.BLACK);
            if (!temp.contains(Tower.WHITE))
                available.add(Tower.WHITE);
            if (!temp.contains(Tower.GREY))
                available.add(Tower.GREY);
            return available;
        } else if (game.getNumberPlayers() == 4 && (game.getPlayers().size() == 0 || game.getPlayers().size() == 1)) {
            for (Player p : game.getPlayers())
                temp.add(p.getBoard().getTowerColor());
            if (!temp.contains(Tower.BLACK))
                available.add(Tower.BLACK);
            if (!temp.contains(Tower.WHITE))
                available.add(Tower.WHITE);
            return available;
        } else if (game.getNumberPlayers() == 4 && game.getPlayers().size() == 2) {
            available.add(Tower.BLACK);
            available.add(Tower.WHITE);
            return available;
        } else {
            available.add(game.getPlayers().get(0).getBoard().getTowerColor());
            available.add(game.getPlayers().get(1).getBoard().getTowerColor());
            available.remove(game.getPlayers().get(2).getBoard().getTowerColor());
            return available;
        }
    }

    /**
     * It's used to get all the wizards available that the player can choose
     *
     * @return the list of wizards available
     */
    private ArrayList<Wizard> checkAvailableWizards() {
        ArrayList<Wizard> available = new ArrayList<>();
        available.add(Wizard.WIZARD1);
        available.add(Wizard.WIZARD2);
        available.add(Wizard.WIZARD3);
        available.add(Wizard.WIZARD4);
        for (Player p : game.getPlayers())
            available.remove(p.getWizard());
        return available;
    }

    /**
     * When all the parameters are valid we add the player to the game
     */
    private void addToGame() {
        game.joinGame(pendingJGM.get(0).getNickname(), currentSelectedTower, currentSelectedWizard);
        addVirtualView(pendingViews.get(0));
        if (game.getPlayers().size() != game.getNumberPlayers()) {
            for (View v : virtualViews)
                v.waitingPlayers(game.getPlayers().size());
        } else {
            for (int i = 0; i < virtualViews.size(); i++)
                orderedPlayers.add(new TurnController((VirtualView) virtualViews.get(i), game.getPlayers().get(i)));
            game.startGame(virtualViews);
            setGameStarted(true);
            //Set first Player
            game.setCurrentPlayer(orderedPlayers.get(0).getPlayer());
            //Notify now the first player who has to choose an assistant card
            indexCurrentPlayer = 0;
            ArrayList<Assistant> availableCards = orderedPlayers.get(0).getPlayer().getDeck().getCards();
            orderedPlayers.get(0).getPlayerView().chooseCard(availableCards);
            for (int i = 1; i < orderedPlayers.size(); i++) {
                orderedPlayers.get(i).getPlayerView().waitTurn(orderedPlayers.get(0).getPlayer().getNickname());
            }
        }
    }

    /**
     * This method is called when the first player has just chosen the game's settings
     *
     * @param message specifies the settings of the game
     */
    private void manageGameSettings(Message message) {
        game.setExpertMode(((SelectedGameSettingsMessage) message).isExpertMode());
        game.setNumberPlayers(((SelectedGameSettingsMessage) message).getNumPlayers());
        setNumGamePlayers(((SelectedGameSettingsMessage) message).getNumPlayers());
        pendingViews.get(0).chooseTowerColor(checkAvailableTowers());
    }

    /**
     * It's called by a player after disconnection or is called when the game ends and we want to delete the current
     * game
     * Either way, the attribute "gameController" in ManageClient.class will be set to 'null'
     */
    public synchronized void playerDisconnectedOrEndGame() {
        //Notify now the players who are still connected that other players disconnected
        if (!isDeleteGame()) {
            setDeleteGame(true);
            for (View v : virtualViews) {
                if (((VirtualView) v).getConnection()) {
                    v.endGame("Some Players disconnected");
                    ((VirtualView) v).closeConnection();
                }
            }

            if (pendingViews.size() != 0) {
                for (View v : pendingViews) {
                    v.endGame("Some Players disconnected");
                    ((VirtualView) v).closeConnection();
                }
            }
        }
    }

    /**
     * @return true if the game has already ended
     */
    public boolean isDeleteGame() {
        synchronized (lockDeleteGame) {
            return deleteGame;
        }
    }

    /**
     * @param deleteGame is true when game ends for disconnection of a player or when someone wins and the game ends
     */
    public void setDeleteGame(boolean deleteGame) {
        synchronized (lockDeleteGame) {
            this.deleteGame = deleteGame;
        }
    }

    /**
     * @return true if game already started
     */
    public boolean isGameStarted() {
        synchronized (lockGameStarted) {
            return gameStarted;
        }
    }

    /**
     * Called when game starts
     *
     * @param gameStarted is true when game is starting
     */
    public void setGameStarted(boolean gameStarted) {
        synchronized (lockGameStarted) {
            this.gameStarted = gameStarted;
        }
    }

    /**
     * @return number of players that have already been added to the GameController
     */
    public int getNumCurrPlayers() {
        synchronized (lockNumCurrPlayers) {
            return numCurrPlayers;
        }
    }

    /**
     * increases the number of players that have been added to the GameController
     */
    public void setNumCurrPlayers() {
        synchronized (lockNumCurrPlayers) {
            numCurrPlayers++;
        }
    }

    /**
     * @return the chosen number of players for the game; returns -1 if it has not been chosen yet
     */
    public int getNumGamePlayers() {
        synchronized (lockNumGamePlayers) {
            return numGamePlayers;
        }
    }

    /**
     * sets the number of players of the game
     */
    public void setNumGamePlayers(int numGamePlayers) {
        synchronized (lockNumGamePlayers) {
            this.numGamePlayers = numGamePlayers;
        }
    }
}

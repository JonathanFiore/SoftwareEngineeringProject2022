package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.View;
import it.polimi.ingsw.server.model.characters.cards.*;
import it.polimi.ingsw.server.model.characters.cards.Character;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import it.polimi.ingsw.server.serverexceptions.EndGameException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class represents a single game
 */
public class Game {

    //GENERICS COMPONENTS
    private final ArrayList<Player> players;
    private Player currentPlayer;
    private int numberPlayers;
    private int numStudentsForEachCloud;
    private int motherNaturePosition;
    private final Bag bag;
    private int coins;

    //OBSERVERS
    private ArrayList<View> virtualViews;

    //ALL ABOUT EXPERT MODE
    private boolean expertMode;
    private Character[] characters;
    private Character activeCharacter;

    //ALL ABOUT ISLANDS AND CLOUDS
    private final ArrayList<Archipelago> archipelagos;
    private final ArrayList<Cloud> clouds;

    //USED FOR TEAMS (ONLY WITH 4 PLAYERS)
    private Team[] teams;


    public Game() {
        players = new ArrayList<>();
        bag = new Bag();

        archipelagos = new ArrayList<>();
        clouds = new ArrayList<>();

        numberPlayers = -1;
    }


    /**
     * This method is called when the game start
     */
    public void startGame(ArrayList<View> virtualViews) {
        this.virtualViews = virtualViews;

        if (numberPlayers == 3)
            numStudentsForEachCloud = 4;
        else
            numStudentsForEachCloud = 3;

        if (expertMode)
            coins = 20 - numberPlayers;
        else
            coins = 0;

        initialSetUp();
        setUpTeams();
        setUpBoards();

        if (getNumberPlayers() != 4) {
            if (isExpertMode()) {
                for (View v : virtualViews) {
                    v.startGame(getPlayers(), null, getClouds(), getArchipelagos(), getCharacters(), expertMode);
                }
            } else {
                for (View v : virtualViews) {
                    v.startGame(getPlayers(), null, getClouds(), getArchipelagos(), null, expertMode);
                }
            }
        } else {
            if (isExpertMode()) {
                for (View v : virtualViews) {
                    v.startGame(getPlayers(), getTeams(), getClouds(), getArchipelagos(), getCharacters(), expertMode);
                }
            } else {
                for (View v : virtualViews) {
                    v.startGame(getPlayers(), getTeams(), getClouds(), getArchipelagos(), null, expertMode);
                }
            }
        }
    }

    /**
     * This method updates the archipelagos' structure according to the towers positioned on them
     *
     * @throws EndGameException if the total number of archipelagos is equal to 3
     */
    public void checkArchipelagosStructure() throws EndGameException {
        for (int i = 0; i < archipelagos.size(); i++) {
            if (archipelagos.get(i % archipelagos.size()).getTower() == archipelagos.get((i + 1) % archipelagos.size()).getTower() && archipelagos.get(i % archipelagos.size()).getTower() != null) {
                //Move islands
                archipelagos.get(i % archipelagos.size()).addIslands(archipelagos.get((i + 1) % archipelagos.size()).getIslands());

                //Check noEntry
                if (archipelagos.get((i + 1) % archipelagos.size()).checkNoEntry() > 0)
                    archipelagos.get(i % archipelagos.size()).addNoEntry(archipelagos.get((i + 1) % archipelagos.size()).checkNoEntry());

                //Check MotherNature
                if (archipelagos.get((i + 1) % archipelagos.size()).checkMotherNature()) {
                    archipelagos.get(i % archipelagos.size()).updateMotherNature();
                    if (i == archipelagos.size() - 1) {
                        motherNaturePosition = i - 1;
                    } else {
                        motherNaturePosition = i;
                    }
                }

                //remove Archipelago linked
                archipelagos.remove((i + 1) % archipelagos.size());
                i--;
            }
        }

        if (this.numberArchipelagos() == 3) {
            throw new EndGameException("archipelago");
        }
    }


    /**
     * @return array characters
     */
    public Character[] getCharacters() {
        return characters;
    }

    /**
     * @return number of players
     */
    public int getNumberPlayers() {
        return numberPlayers;
    }

    /**
     * @return the list "players"
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Every time a Player adds some students to the hall of his board this method has to be called
     */
    public void checkTeachers() {
        ArrayList<Student> colors = new ArrayList<>();
        List<Player> playersOrdered;
        List<Player> loser;
        Function<Player, Integer> f;
        colors.add(Student.BLUE);
        colors.add(Student.RED);
        colors.add(Student.GREEN);
        colors.add(Student.PINK);
        colors.add(Student.YELLOW);

        //for each color I need to know which player has more students on his hall
        for (Student s : colors) {
            f = p -> p.getBoard().getNumberOfStudents(s);
            playersOrdered = players.stream()
                    .sorted(Comparator.comparing(f).reversed())
                    .collect(Collectors.toList());
            //I ordered all the players according to the number of students of the color
            // specified they have on their hall
            //----------------------------------------------------------------------------------------------------------
            //check if there are 2 players with the same number of students,
            // in this case we don't have to modify anything
            if (playersOrdered.get(0).getBoard().getNumberOfStudents(s) >
                    playersOrdered.get(1).getBoard().getNumberOfStudents(s)) {
                if (!playersOrdered.get(0).getBoard().isProfessorPresent(s)) {
                    //I have to find out which player had the professor;
                    loser = players.stream()
                            .filter(p -> p.getBoard().isProfessorPresent(s))
                            .collect(Collectors.toList());

                    if (loser.size() > 0)
                        loser.get(0).getBoard().updatePresenceOfProfessor(s);

                    playersOrdered.get(0).getBoard().updatePresenceOfProfessor(s);
                }
            }
        }

        for (View v : virtualViews) {
            v.playersUpdate(getPlayers());
            v.islandsUpdate(getArchipelagos());
        }
    }

    /**
     * this method is used when a player wants to activate a character's functionality
     *
     * @param index      specifies which character the player wants to activate
     * @param parameters contains all the parameters needed for the specific character
     * @throws EndGameException if "Herald" character is used and the currentPlayer wins the game
     */
    public void useCharacter(int index, ArrayList<Parameter> parameters) throws EndGameException {
        activeCharacter = characters[index];
        activeCharacter.task(parameters);

        if (activeCharacter.getId() == 3) //We manage the Herald character's function here
            influenceOnArchipelago((archipelagos.get(((Herald) activeCharacter).getArchipelago())));

        checkTeachers();
        checkArchipelagosStructure();

        for (View view : virtualViews)
            view.characterUpdate(activeCharacter, index);
    }

    /**
     * It's used to move all the students on a specific cloud in the entrance of a specific player's board
     *
     * @param nickName is the nickname of the player who chosen the cloud
     * @param index    is used to specify the cloud chosen
     */
    public void chooseCloud(String nickName, int index) {
        ArrayList<Student> s = clouds.get(index).getStudents();
        Player p = getPlayer(nickName);
        for (Student student : s)
            p.getBoard().moveToEntrance(student);
        s.clear();
        clouds.get(index).clearCloud();

        for (View v : virtualViews) {
            v.cloudUpdate(getClouds());
            v.playersUpdate(getPlayers());
        }
    }

    /**
     * Preconditions: A PLayer with this nickName must be present in the List
     *
     * @param nickName specifies the nickName of the player
     * @return the player
     */
    public Player getPlayer(String nickName) {
        return players.stream()
                .filter(p -> p.getNickname().equals(nickName))
                .collect(Collectors.toList())
                .get(0);
    }

    /**
     * @return the pointer of teams collection
     */
    public Team[] getTeams() {
        return teams;
    }

    /**
     * @return the player is playing right now in this round
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @return coins
     */
    public int getCoins() {
        return coins;
    }

    /**
     * adds a coin to the supply
     */
    public void addCoin() {
        coins++;
    }

    /**
     * @param steps specifies the number of steps we have to take to move mother nature across the archipelagos
     * @throws EndGameException if a player's last tower is placed or the total number of archipelagos is equal to 3
     */
    public void moveMotherNature(int steps) throws EndGameException {

        //This is for MagicPostman Character
        if (activeCharacter != null && activeCharacter.getId() == 4)
            steps += ((MagicPostman) activeCharacter).getAdditionalSteps();

        int index = (motherNaturePosition + steps) % archipelagos.size();
        archipelagos.get(motherNaturePosition).updateMotherNature();
        archipelagos.get(index).updateMotherNature();
        motherNaturePosition = index;

        influenceOnArchipelago(archipelagos.get(index));
    }


    /**
     * This method is used to calculate the influence on a specific archipelago, it also implements tasks
     * of some characters which modify the influence and implements the "HerbsGranny" character
     * which use the noEntry cards
     *
     * @param a specifies the specific archipelago where we want to calculate the influence
     * @throws EndGameException if a player's last tower is placed
     */
    private void influenceOnArchipelago(Archipelago a) throws EndGameException {
        List<Player> playersSorted;
        Player winner;
        if (!(a.checkNoEntry() > 0)) {
            Function<Player, Integer> f = (Player p1) -> a.totalPlayerInfluence(p1);
            if (activeCharacter != null) {
                switch (activeCharacter.getId()) {
                    case 2:
                        //Farmer
                        f = (Player p1) -> ((Farmer) activeCharacter).actualTask(p1, a);
                        break;
                    case 6:
                        //Centaur
                        f = (Player p1) -> ((Centaur) activeCharacter).actualTask(p1, a);
                        break;
                    case 8:
                        //Knight
                        f = (Player p1) -> a.totalPlayerInfluence(p1) + ((Knight) activeCharacter).actualTask(p1);
                        break;
                    case 9:
                        //MushroomHunter
                        f = (Player p1) -> ((MushroomHunter) activeCharacter).actualTask(p1, a);
                        break;
                }
            }

            if (numberPlayers != 4) {
                playersSorted = players.stream().sorted((Comparator.comparing(f))
                        .reversed()).collect(Collectors.toList());
                //Now all the players are sorted according to their influence on this archipelago

                //We have to calculate the value of the influence of the first 2 players
                String nickFirstPlayer = playersSorted.get(0).getNickname();
                int influenceFirstPlayer = playersSorted.stream()
                        .filter(p -> p.getNickname().equals(nickFirstPlayer))
                        .map(f).collect(Collectors.toList())
                        .get(0);

                String nickSecondPlayer = playersSorted.get(1).getNickname();
                int influenceSecondPlayer = playersSorted.stream()
                        .filter(p -> p.getNickname().equals(nickSecondPlayer))
                        .map(f).collect(Collectors.toList())
                        .get(0);

                //Check if there are 2 players with the same influence
                if (influenceFirstPlayer != influenceSecondPlayer) {
                    winner = playersSorted.get(0);
                    changesForInfluence(winner, a);
                }
            } else {
                //Actions for 4 players, calculate influence for team mode:

                //Influence first team:
                int influenceFirstTeam;
                influenceFirstTeam = players.stream()
                        .filter(p -> p.getNickname().equals(teams[0].getPlayers()[0].getNickname()))
                        .map(f)
                        .collect(Collectors.toList())
                        .get(0);

                influenceFirstTeam += players.stream()
                        .filter(p -> p.getNickname().equals(teams[0].getPlayers()[1].getNickname()))
                        .map(f)
                        .collect(Collectors.toList())
                        .get(0);

                int influenceSecondTeam;
                influenceSecondTeam = players.stream()
                        .filter(p -> p.getNickname().equals(teams[1].getPlayers()[0].getNickname()))
                        .map(f)
                        .collect(Collectors.toList())
                        .get(0);

                influenceSecondTeam += players.stream()
                        .filter(p -> p.getNickname().equals(teams[1].getPlayers()[1].getNickname()))
                        .map(f)
                        .collect(Collectors.toList())
                        .get(0);


                if (activeCharacter == null || activeCharacter.getId() != 6) {
                    if (a.getTower() != null) {
                        //For one of the teams we counted the number of towers twice
                        if (a.getTower().equals(teams[0].getTower())) {
                            influenceFirstTeam -= a.getIslands().size();
                        } else {
                            influenceSecondTeam -= a.getIslands().size();
                        }
                    }
                }

                //Compare now the values:
                if (influenceFirstTeam != influenceSecondTeam) {
                    if (influenceFirstTeam > influenceSecondTeam) {
                        //First team has more influence
                        winner = teams[0].getPlayerLeader();
                    } else {
                        //Second team has more influence
                        winner = teams[1].getPlayerLeader();
                    }

                    changesForInfluence(winner, a);
                }
            }

        } else //there was a noEntry card on this archipelago, this 'if' implements "HerbsGranny" character
        {
            a.removeNoEntry();
            for (int i = 0; i < characters.length; i++) {
                if (characters[i].getId() == 5) {
                    ((HerbsGranny) characters[i]).addNoEntry();
                    for (View v : virtualViews)
                        v.characterUpdate(characters[i], i);
                }
            }
        }

        checkArchipelagosStructure();

        for (View v : virtualViews) {
            v.islandsUpdate(archipelagos);
            v.playersUpdate(players);
        }
    }

    /**
     * @param winner specifies the player with more influence (in 4 players mode is the game Leader)
     * @param a      specifies the specific archipelago where we calculated the influence
     * @throws EndGameException if a player's last tower is placed
     */
    private void changesForInfluence(Player winner, Archipelago a) throws EndGameException {
        Player loser;
        //NEED TO TAKE PLAYER_LEADER IF 4-PLAYER MODE IS ON
        if (numberPlayers == 4)
            winner = getPlayerLeader(winner);

        //move towers of the player with more influence on the archipelago
        if (a.getTower() != winner.getBoard().getTowerColor() && a.getTower() != null) {
            loser = players
                    .stream()
                    .filter(p -> p.getBoard().getTowerColor().equals(a.getTower()))
                    .collect(Collectors.toList()).get(0);

            //NEED TO TAKE PLAYER_LEADER IF 4-PLAYER MODE IS ON
            if (numberPlayers == 4)
                loser = getPlayerLeader(loser);

            a.setTower(winner.getBoard().getTowerColor());
            for (int i = 0; i < a.getIslands().size(); i++) {
                winner.getBoard().removeTower();
                loser.getBoard().addTower();
            }
        } else if (a.getTower() == null) {
            a.setTower(winner.getBoard().getTowerColor());
            winner.getBoard().removeTower();
        }
    }

    /**
     * @return number of archipelagos
     */
    public int numberArchipelagos() {
        return archipelagos.size();
    }

    /**
     * This method is used to add a player to this game before the match starts
     * PRECONDITIONS: nickName, towerColor and wizard must be available
     * and there must be a place for tha player in this game
     * <p>
     * We force the first 2 players to be playerLeaders of a team!
     *
     * @param nickname   specifies the nickname chosen by the player
     * @param towerColor specifies the tower's color chosen by the player
     */
    public void joinGame(String nickname, Tower towerColor, Wizard wizard) {
        Player p;
        Board b;
        int numberTowers;

        if (numberPlayers == 3)
            numberTowers = 6;
        else
            numberTowers = 8;

        if (numberPlayers != 4) {
            b = new Board(numberTowers, towerColor);
            p = new Player(nickname, isExpertMode(), b, wizard);
            players.add(p);
        } else {
            if (players.size() < 2)
                b = new Board(numberTowers, towerColor);
            else
                b = new Board(0, towerColor);

            p = new Player(nickname, isExpertMode(), b, wizard);
            players.add(p);
        }
    }

    /**
     * fill clouds each round and update each player
     */
    public void fillClouds() {
        realFillClouds();
        for (View v : virtualViews)
            v.cloudUpdate(getClouds());
    }

    /**
     * This is the method which fills the clouds
     */
    private void realFillClouds() {
        for (Cloud c : clouds) {
            for (int j = 0; j < numStudentsForEachCloud; j++) {
                Student s;
                s = bag.drawStudent();
                if (s != null)
                    c.addStudent(s);
            }
        }
    }

    /**
     * This method is used to pick up hte playerLeader given a random player when team mode is on
     *
     * @param p is the player in the team whose I want to know the leader
     * @return the team's playerLeader which contains p
     */
    private Player getPlayerLeader(Player p) {
        Team[] team = Arrays.stream(teams)
                .filter(t -> Arrays.asList(t.getPlayers()).contains(p))
                .toArray(Team[]::new);
        return team[0].getPlayerLeader();
    }


    //----------------------------------------- SETUP METHODS: ---------------------------------------

    /**
     * Set up the game before to start
     */
    private void initialSetUp() {
        //Creation islands, set motherNature and put the students on the islands:
        setUpIslands();
        //--------------------------------------------------------------------------------
        //setUp Clouds:
        setUpClouds();
        //--------------------------------------------------------------------------------
        //setUp Characters if expertMode is true:
        setUpCharacters();
    }

    /**
     * setUp Characters if expertMode is true
     */
    private void setUpCharacters() {
        if (expertMode) {
            characters = new Character[3];
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < 12; i++)
                list.add(i);
            Collections.shuffle(list);
            for (int i = 0; i < 3; i++) {
                switch (list.get(i)) {
                    case 0:
                        characters[i] = new Monk(bag);
                        break;
                    case 1:
                        characters[i] = new Farmer(players);
                        break;
                    case 2:
                        characters[i] = new Herald();
                        break;
                    case 3:
                        characters[i] = new MagicPostman();
                        break;
                    case 4:
                        characters[i] = new HerbsGranny();
                        break;
                    case 5:
                        characters[i] = new Centaur();
                        break;
                    case 6:
                        characters[i] = new Jester(bag);
                        break;
                    case 7:
                        characters[i] = new Knight();
                        break;

                    case 8:
                        characters[i] = new MushroomHunter();
                        break;

                    case 9:
                        characters[i] = new Minstrel();
                        break;

                    case 10:
                        characters[i] = new SpoiledPrincess(bag);
                        break;

                    case 11:
                        characters[i] = new Thief(players, bag);
                        break;
                }
            }
        }
    }

    /**
     * setUp Teams if number of players is equal to 4
     */
    private void setUpTeams() {
        if (numberPlayers == 4) {
            teams = new Team[2];
            Player teamPlayer;

            teamPlayer = players
                    .stream().
                    filter(p -> p.getBoard().getTowerColor().equals(players.get(0).getBoard().getTowerColor()))
                    .collect(Collectors.toList()).get(1);
            teams[0] = new Team(players.get(0), teamPlayer, players.get(0).getBoard().getTowerColor());

            teamPlayer = players
                    .stream().
                    filter(p -> p.getBoard().getTowerColor().equals(players.get(1).getBoard().getTowerColor()))
                    .collect(Collectors.toList()).get(1);
            teams[1] = new Team(players.get(1), teamPlayer, players.get(1).getBoard().getTowerColor());
        }
    }

    /**
     * setUp Clouds
     */
    private void setUpClouds() {
        for (int i = 0; i < numberPlayers; i++)
            clouds.add(new Cloud());
        realFillClouds();
    }

    /**
     * Creation islands, set motherNature and put the students on the islands
     */
    private void setUpIslands() {
        int setMotherNature;
        ArrayList<Student> s = new ArrayList<>();
        int islandEmpty;

        for (int i = 0; i < 12; i++)
            archipelagos.add(new Archipelago(false));

        setMotherNature = (int) Math.floor((Math.random() * 12)); //max = 11, min = 0
        archipelagos.get(setMotherNature).updateMotherNature();
        motherNaturePosition = setMotherNature;
        islandEmpty = (setMotherNature + 6) % 12;

        for (int i = 0; i < 2; i++) {
            s.add(Student.BLUE);
            s.add(Student.PINK);
            s.add(Student.RED);
            s.add(Student.GREEN);
            s.add(Student.YELLOW);
        }

        for (int j = 0, index; j < archipelagos.size(); j++) {
            if (j != islandEmpty && j != motherNaturePosition) {
                index = (int) Math.floor(Math.random() * s.size());
                archipelagos.get(j).getIslands().get(0).addStudent(s.get(index));
                s.remove(index);
            }
        }
    }


    /**
     * It adds the right number of students on the entrance of all the boards
     */
    private void setUpBoards() {
        int numStudents;
        if (numberPlayers != 3)
            numStudents = 7;
        else
            numStudents = 9;

        for (Player p : players)
            for (int i = 0; i < numStudents; i++) {
                Student s = bag.drawStudent();
                if (s != null)
                    p.getBoard().moveToEntrance(s);
            }
    }

    /**
     * It's used to check if this game expertMode is active
     *
     * @return true if expertMode has been activated
     */
    public boolean isExpertMode() {
        return expertMode;
    }

    /**
     * It's used to get clouds' list of the game
     *
     * @return the list of clouds
     */
    public ArrayList<Cloud> getClouds() {
        return clouds;
    }

    /**
     * It's used to get archipelagos' list of the game
     *
     * @return the list of archipelagos
     */
    public ArrayList<Archipelago> getArchipelagos() {
        return archipelagos;
    }

    /**
     * It's used to check if bag is empty in GameController
     *
     * @return true if bag is empty
     */
    public boolean isBagEmpty() {
        return bag.isEmpty();
    }

    /**
     * It's used to set the number of players for the game
     *
     * @param numberPlayers is the number of players selected
     */
    public void setNumberPlayers(int numberPlayers) {
        this.numberPlayers = numberPlayers;
    }

    /**
     * It's used to set the mode of the game
     *
     * @param expertMode true if the mode selected is expertMode, false if it isn't
     */
    public void setExpertMode(boolean expertMode) {
        this.expertMode = expertMode;
    }

    /**
     * It's used to set the players that are currently in the game
     *
     * @param currentPlayer represents a current player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * It's used to set the number of the coins still on the game table
     *
     * @param coins it's the number of coins
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }

    /**
     * It's used to set the ActiveCharacter of the turn
     *
     * @param activeCharacter represents the character that affects the turn
     */
    public void setActiveCharacter(Character activeCharacter) {
        this.activeCharacter = activeCharacter;
    }
}

package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.controllers.InputChecker;
import it.polimi.ingsw.protocol.*;
import it.polimi.ingsw.server.controller.Observer;
import it.polimi.ingsw.server.controller.View;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.characters.cards.Character;
import it.polimi.ingsw.server.model.characters.cards.Jester;
import it.polimi.ingsw.server.model.characters.cards.Monk;
import it.polimi.ingsw.server.model.characters.cards.SpoiledPrincess;
import it.polimi.ingsw.server.model.characters.parameters.IntegerParameter;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import it.polimi.ingsw.server.model.characters.parameters.StudentParameter;
import java.io.IOException;
import java.util.*;

/**
 * This class manage the interaction with the user by a CLI
 * it observes the NetworkHandler, and it's observable by the NetworkHandler
 */
public class ViewCLI implements View {

    Observer networkHandler;

    private String nickname;

    private ArrayList<Player> players;
    private Team[] teams;
    private ArrayList<Cloud> clouds;
    private ArrayList<Archipelago> archipelagos;
    private Character[] characters;
    private boolean expertMode;
    private boolean usedCharacter;

    private boolean waiting = false;
    private String currentP = null;
    private boolean skipFirstWait = true;

    @Override
    public void chooseCard(ArrayList<Assistant> availableCards) {
        System.out.println("Choose one of the following assistants:\n");
        for (int i = 1; i <= availableCards.size(); i++)
            System.out.println("[" + i + "] Card value = " + availableCards.get(i - 1).getCardValue() + "   Mother Nature movements = " + availableCards.get(i - 1).getMotherMovements());
        System.out.print("\nInsert index: ");
        Scanner input = new Scanner(System.in);
        boolean validInt = false;
        String s;
        while (!validInt) {
            s = input.nextLine();
            try {
                int card = Integer.parseInt(s);
                SelectedCardMessage sc = new SelectedCardMessage(card);
                networkHandler.update(sc);
                validInt = true;
            } catch (NumberFormatException e) {
                System.out.print("Wrong input. Insert index: ");
            }
        }
    }

    @Override
    public void errorCard(String error, ArrayList<Assistant> availableCards) {
        System.out.println("ERROR: " + error);
        chooseCard(availableCards);
    }

    @Override
    public void isYourTurn() {
        waiting = false;

        printScreen();

        int reps;
        boolean validPos, validDest, validIsl;
        String p;
        Scanner input = new Scanner(System.in);
        ArrayList<Integer> entranceIndexes = new ArrayList<>();
        ArrayList<Integer> destinationIndexes = new ArrayList<>();

        if (players.size() == 3)
            reps = 4;
        else
            reps = 3;

        validPos = false;
        validDest = false;
        validIsl = false;

        for (int i = 0; i < reps; i++) {
            System.out.print("Choose an entrance position where to remove a student from ");
            if (players.size() == 3)
                System.out.print("[between 1 and 9]");
            else
                System.out.print("[between 1 and 7]");
            if (expertMode && !usedCharacter) {
                if (i == 0)
                    System.out.print(" or use a Character card [c]: ");
                else
                    System.out.print(": ");
            } else
                System.out.print(": ");

            while (!validPos) {
                p = input.nextLine();

                if (i == 0 && expertMode && !usedCharacter && p.equals("c")) {
                    useCharacter();
                    return;
                }

                try {
                    int index = Integer.parseInt(p);
                    if (!entranceIndexes.contains(index)) {
                        entranceIndexes.add(index);
                        validPos = true;
                    } else {
                        if (i == 0 && expertMode && !usedCharacter)
                            System.out.print("Wrong input. Insert position or use a Character card: ");
                        else
                            System.out.print("Wrong input. Insert position: ");
                    }
                } catch (NumberFormatException e) {
                    if (i == 0 && expertMode && !usedCharacter)
                        System.out.print("Wrong input. Insert position or use a Character card: ");
                    else
                        System.out.print("Wrong input. Insert position: ");
                }
            }

            System.out.print("Choose the student destination between Island or Dining Room [i/d]: ");
            while (!validDest) {
                p = input.nextLine();
                if (p.equals("d")) {
                    destinationIndexes.add(13);
                    validDest = true;
                } else if (p.equals("i")) {
                    System.out.print("Choose the target island [between 1 and 12]: ");
                    while (!validIsl) {
                        p = input.nextLine();
                        try {
                            int index = Integer.parseInt(p);
                            destinationIndexes.add(index);
                            validIsl = true;
                            validDest = true;
                        } catch (NumberFormatException e) {
                            System.out.print("Wrong input. Insert target island: ");
                        }
                    }
                } else
                    System.out.print("Wrong input. Insert destination: ");
            }

            validPos = false;
            validDest = false;
            validIsl = false;
        }

        MoveStudentsMessage msm = new MoveStudentsMessage(entranceIndexes, destinationIndexes);
        networkHandler.update(msm);
    }

    @Override
    public void waitTurn(String currentPlayer) {
        usedCharacter = false;
        if (!skipFirstWait)
            printScreen();
        else
            skipFirstWait = false;

        System.out.println("Wait for your turn... " + currentPlayer + "'s turn");

        waiting = true;
        currentP = currentPlayer;
    }

    @Override
    public void errorStudents(String error) {
        System.out.println("ERROR: " + error);
        isYourTurn();
    }

    @Override
    public void moveMotherNature(int maxSteps) {

        printScreen();

        Scanner input = new Scanner(System.in);
        String s;
        boolean validSteps;

        System.out.print("Choose the number of Mother Nature steps [between 1 and " + maxSteps + "]");
        if (expertMode && !usedCharacter)
            System.out.print(" or use a Character card [c]: ");
        else
            System.out.print(": ");

        validSteps = false;
        while (!validSteps) {
            s = input.nextLine();

            if (expertMode && !usedCharacter && s.equals("c")) {
                useCharacter();
                return;
            }

            try {
                int num = Integer.parseInt(s);
                StepsMotherNatureMessage steps = new StepsMotherNatureMessage(num);
                networkHandler.update(steps);
                validSteps = true;
            } catch (NumberFormatException e) {
                if (expertMode && !usedCharacter)
                    System.out.print("Wrong input. Insert the number of Mother Nature steps or use a Character card: ");
                else
                    System.out.print("Wrong input. Insert the number of Mother Nature steps: ");
            }
        }
    }

    @Override
    public void errorMotherNature(String error, int maxSteps) {
        System.out.println("ERROR: " + error);
        moveMotherNature(maxSteps);
    }

    @Override
    public void chooseCloud(ArrayList<Integer> availableClouds) {

        printScreen();

        Scanner input = new Scanner(System.in);
        String s;
        boolean validCloud;

        System.out.print("Choose an available cloud [");
        if (availableClouds.size() == 1) {
            System.out.print(availableClouds.get(0));
        } else {
            System.out.print(availableClouds.get(0));
            for (int i = 1; i < (availableClouds.size() - 1); i++) {
                System.out.print(" , " + availableClouds.get(i));
            }
            System.out.print(" or " + availableClouds.get(availableClouds.size() - 1));
        }
        System.out.print("]");
        if (expertMode && !usedCharacter)
            System.out.print(" or use a Character card [c]: ");
        else
            System.out.print(": ");


        validCloud = false;
        while (!validCloud) {
            s = input.nextLine();

            if (expertMode && !usedCharacter && s.equals("c")) {
                useCharacter();
                return;
            }

            try {
                int ind = Integer.parseInt(s);
                SelectedCloudMessage cloud = new SelectedCloudMessage(ind);
                networkHandler.update(cloud);
                validCloud = true;
            } catch (NumberFormatException e) {
                if (expertMode && !usedCharacter)
                    System.out.print("Wrong input. Insert the index of an available cloud or use a Character card: ");
                else
                    System.out.print("Wrong input. Insert the index of an available cloud: ");
            }
        }
    }

    @Override
    public void errorCloud(String error, ArrayList<Integer> availableClouds) {
        System.out.println("ERROR: " + error);
        chooseCloud(availableClouds);
    }

    @Override
    public void errorCharacter(String error) {
        Scanner input = new Scanner(System.in);

        System.out.println("ERROR: " + error);
        System.out.println("Choose between using a Character card [c] or resuming the previous turn phase [r]: ");

        boolean wrongIn = true;
        while (wrongIn) {
            String s = input.nextLine();

            if (s.equals("c")) {
                useCharacter();
                wrongIn = false;
            } else if (s.equals("r")) {
                MoveOnMessage m = new MoveOnMessage();
                networkHandler.update(m);
                usedCharacter = false;
                wrongIn = false;
            } else {
                System.out.print("Wrong input. Insert a valid choice: ");
            }
        }

    }

    @Override
    public void chooseTowerColor(ArrayList<Tower> availableColors) {
        char color;
        System.out.print("Available towers: ");
        for (int i = 0; i < (availableColors.size() - 1); i++) {
            color = availableColors.get(i).name().toLowerCase(Locale.ROOT).charAt(0);
            System.out.print(availableColors.get(i).name() + " [" + color + "]" + " , ");
        }
        color = availableColors.get(availableColors.size() - 1).name().toLowerCase(Locale.ROOT).charAt(0);
        System.out.print(availableColors.get(availableColors.size() - 1).name() + " [" + color + "]" + "\n");
        Scanner input = new Scanner(System.in);
        boolean wrongChoice = true;
        while (wrongChoice) {
            String s = input.nextLine();

            if (s.equals("b")) {
                SelectedTowerColorMessage st = new SelectedTowerColorMessage(Tower.BLACK);
                networkHandler.update(st);
                wrongChoice = false;
            } else if (s.equals("w")) {
                SelectedTowerColorMessage st = new SelectedTowerColorMessage(Tower.WHITE);
                networkHandler.update(st);
                wrongChoice = false;
            } else if (s.equals("g")) {
                SelectedTowerColorMessage st = new SelectedTowerColorMessage(Tower.GREY);
                networkHandler.update(st);
                wrongChoice = false;
            } else {
                System.out.print("Invalid choice. Choose a valid color: ");
            }
        }
    }

    @Override
    public void errorTowerColor(String error, ArrayList<Tower> availableColors) {
        System.out.println("ERROR: " + error + ". Choose one of the following colors:");
        chooseTowerColor(availableColors);
    }

    @Override
    public void chooseWizard(ArrayList<Wizard> availableWizards) {
        char num;
        System.out.print("Available wizards: ");
        for (int i = 0; i < (availableWizards.size() - 1); i++) {
            num = availableWizards.get(i).name().charAt(6);
            System.out.print(availableWizards.get(i).name() + " [" + num + "]" + " , ");
        }
        num = availableWizards.get(availableWizards.size() - 1).name().charAt(6);
        System.out.print(availableWizards.get(availableWizards.size() - 1).name() + " [" + num + "]" + "\n");
        Scanner input = new Scanner(System.in);
        boolean wrongChoice = true;
        while (wrongChoice) {
            String s = input.nextLine();

            if (s.equals("1")) {
                SelectedWizardMessage sw = new SelectedWizardMessage(Wizard.WIZARD1);
                networkHandler.update(sw);
                wrongChoice = false;
            } else if (s.equals("2")) {
                SelectedWizardMessage sw = new SelectedWizardMessage(Wizard.WIZARD2);
                networkHandler.update(sw);
                wrongChoice = false;
            } else if (s.equals("3")) {
                SelectedWizardMessage sw = new SelectedWizardMessage(Wizard.WIZARD3);
                networkHandler.update(sw);
                wrongChoice = false;
            } else if (s.equals("4")) {
                SelectedWizardMessage sw = new SelectedWizardMessage(Wizard.WIZARD4);
                networkHandler.update(sw);
                wrongChoice = false;
            } else {
                System.out.print("Invalid choice. Choose a valid wizard: ");
            }
        }
    }

    @Override
    public void errorWizard(String error, ArrayList<Wizard> availableWizards) {
        System.out.println("ERROR: " + error + ". Choose one of the following wizards:");
        chooseWizard(availableWizards);
    }

    @Override
    public void waitingPlayers(int numCurrentPlayers) {
        // modificare gamecontroller per mandare a tutte le cli il messaggio di wait
        System.out.println("Waiting Players...\nPlayers in game: " + numCurrentPlayers);
    }

    @Override
    public void startGame(ArrayList<Player> players, Team[] teams, ArrayList<Cloud> clouds, ArrayList<Archipelago> archipelagos, Character[] characters, boolean expertMode) {
        System.out.println("Starting game...");
        this.players = players;
        this.teams = teams;
        this.clouds = clouds;
        this.archipelagos = archipelagos;
        this.characters = characters;
        this.expertMode = expertMode;
        usedCharacter = false;

        printScreen();
    }

    @Override
    public void joinGameError(String s) {
        if (s.equals("WAIT"))
            System.out.println("Wait for other players to join...");
        else if (s.equals("FULL")) {
            System.out.println("Ongoing game is already full");
            ((NetworkHandler) networkHandler).closeConnection();
        } else {
            System.out.println("ERROR: " + s);
            JoinGameMessage j = fillJoinGame();
            networkHandler.update(j);
        }
    }

    @Override
    public void chooseGameSettings() {
        Scanner sg = new Scanner(System.in);
        String em, np;
        boolean expertMode = false;
        int numPlayers = 0;

        System.out.print("Enable Expert Mode [y/n]: ");
        boolean validEM = false;
        while (!validEM) {
            em = sg.nextLine();

            if (em.equals("y")) {
                expertMode = true;
                validEM = true;
            } else if (em.equals("n")) {
                expertMode = false;
                validEM = true;
            } else {
                System.out.print("Invalid choice. Enable Expert Mode [y/n]: ");
            }
        }

        System.out.print("Insert number of players [2 , 3 , 4]: ");
        boolean validNP = false;
        while (!validNP) {
            np = sg.nextLine();
            try {
                numPlayers = Integer.parseInt(np);
                if (numPlayers >= 2 && numPlayers <= 4) {
                    validNP = true;
                } else {
                    System.out.print("Invalid choice. Insert number of players [2 , 3 , 4]: ");
                }
            } catch (NumberFormatException en) {
                System.out.print("Invalid choice. Insert number of players [2 , 3 , 4]: ");
            }
        }

        SelectedGameSettingsMessage message = new SelectedGameSettingsMessage(expertMode, numPlayers);
        networkHandler.update(message);

    }

    @Override
    public void endGame(String winner) {
        if (players == null)
            System.out.println("Game's ended. " + winner);
        else
            System.out.println(winner);

        Scanner input = new Scanner(System.in);
        String s;
        System.out.print("Choose between playing another game [p] or quitting [q]: ");

        boolean validChoice = false;
        while (!validChoice) {
            s = input.nextLine();
            if (s.equals("p")) {
                validChoice = true;
                startCLI();
            } else if (s.equals("q")) {
                validChoice = true;
                System.out.print("Quitting game...");
            } else {
                System.out.print("Invalid choice. Choose between playing another game [p] or quitting [q]: ");
            }
        }
    }

    @Override
    public void playersUpdate(ArrayList<Player> updatedPlayers) {

        if (players.size() == 4) {
            Team t1, t2;
            if (updatedPlayers.get(0).getBoard().getTowerColor() == teams[0].getTower()) {
                if (updatedPlayers.get(2).getBoard().getTowerColor() == teams[0].getTower()) {
                    t1 = new Team(updatedPlayers.get(0), updatedPlayers.get(2), teams[0].getTower());
                    t2 = new Team(updatedPlayers.get(1), updatedPlayers.get(3), teams[1].getTower());
                } else {
                    t1 = new Team(updatedPlayers.get(0), updatedPlayers.get(3), teams[0].getTower());
                    t2 = new Team(updatedPlayers.get(1), updatedPlayers.get(2), teams[1].getTower());
                }
            } else {
                if (updatedPlayers.get(2).getBoard().getTowerColor() == teams[0].getTower()) {
                    t1 = new Team(updatedPlayers.get(1), updatedPlayers.get(2), teams[0].getTower());
                    t2 = new Team(updatedPlayers.get(0), updatedPlayers.get(3), teams[1].getTower());
                } else {
                    t1 = new Team(updatedPlayers.get(1), updatedPlayers.get(3), teams[0].getTower());
                    t2 = new Team(updatedPlayers.get(0), updatedPlayers.get(2), teams[1].getTower());
                }
            }

            teams[0] = t1;
            teams[1] = t2;
        }


        players = updatedPlayers;
        if (waiting) {
            printScreen();
            System.out.println("Wait for your turn... " + currentP + "'s turn");
        }
    }

    @Override
    public void islandsUpdate(ArrayList<Archipelago> updatedArchipelagos) {
        archipelagos = updatedArchipelagos;
        if (waiting) {
            printScreen();
            System.out.println("Wait for your turn... " + currentP + "'s turn");
        }
    }

    @Override
    public void cloudUpdate(ArrayList<Cloud> updatedClouds) {
        clouds = updatedClouds;
        if (waiting) {
            printScreen();
            System.out.println("Wait for your turn... " + currentP + "'s turn");
        }
    }

    @Override
    public void characterUpdate(Character updatedCharacter, int index) {
        characters[index] = updatedCharacter;
        if (waiting) {
            printScreen();
            System.out.println("Wait for your turn... " + currentP + "'s turn");
        }
    }

    @Override
    public void addObserver(Observer observer) {
        this.networkHandler = observer;
    }

    /**
     * This method is used when the CLI is launched
     */
    public void startCLI() {
        NetworkHandler networkHandler;
        JoinGameMessage joinGameMessage;
        Scanner sg;
        String ip = null;
        String port = null;
        sg = new Scanner(System.in);

        printTitle();

        boolean okPar = false;
        while (!okPar) {
            System.out.print("Insert ip: ");
            ip = sg.nextLine();
            System.out.print("Insert port: ");
            port = sg.nextLine();
            if (InputChecker.checkIp(ip) && InputChecker.checkPort(port)) {
                okPar = true;
            } else
                System.out.println("Chosen ip or chosen port is not valid");
        }

        joinGameMessage = fillJoinGame();
        //When we create NetworkHandler we open connection
        try {
            networkHandler = new NetworkHandler(this, ip, Integer.parseInt(port), joinGameMessage);
            this.addObserver(networkHandler);
        } catch (IOException e) {
            System.out.println("Error Connection... close Application");
        }
    }

    /**
     * Asks the nickname and checks if it's valid, then creates a joinGameMessage
     *
     * @return the message that a new player wants to join if the check went well
     */
    private JoinGameMessage fillJoinGame() {
        Scanner sg;
        String nick = null;
        sg = new Scanner(System.in);

        System.out.print("Insert nickName: ");
        boolean okNick = false;
        while (!okNick) {
            nick = sg.nextLine();
            if (nick.length() > 0 && nick.length() <= 20) { // nickname must be 20 letters long at most
                nickname = nick;
                okNick = true;
            } else
                System.out.print("The nickname has to be twenty letters long at most. Insert nickName: ");
        }

        JoinGameMessage joinGameMessage = new JoinGameMessage(nick);
        return joinGameMessage;
    }

    /**
     * Asks to choose a character and then calls the method corresponding to the chosen character's index
     */
    private void useCharacter() {
        String s;
        int ind = 0;
        boolean validChara;
        Scanner input = new Scanner(System.in);

        System.out.print("Choose a Character card [1 , 2 or 3]: ");

        validChara = false;
        while (!validChara) {
            s = input.nextLine();

            try {
                ind = Integer.parseInt(s);
                if (ind == 1 || ind == 2 || ind == 3)
                    validChara = true;
                else
                    System.out.print("Wrong input. Insert a valid Character card index: ");
            } catch (NumberFormatException e) {
                System.out.print("Wrong input. Insert a valid Character card index: ");
            }
        }

        switch (characters[ind - 1].getId()) {
            case 1:
                useCharacter1(ind);
                break;
            case 2:
            case 6:
            case 8:
                useCharacter2_6_8(ind);
                break;
            case 3:
            case 5:
                useCharacter3_5(ind);
                break;
            case 4:
                useCharacter4(ind);
                break;
            case 7:
                useCharacter7(ind);
                break;
            case 9:
            case 12:
                useCharacter9_12(ind);
                break;
            case 10:
                useCharacter10(ind);
                break;
            case 11:
                useCharacter11(ind);
                break;
        }
    }

    /**
     * This method is called when the player wants to use Monk character
     * @param ind specifies the character's index in array 'characters' the player wants to use
     */
    private void useCharacter1(int ind) {
        String s;
        Scanner input = new Scanner(System.in);

        if (((Monk) characters[ind - 1]).getStudents().size() == 0) {
            ArrayList<Parameter> p1 = new ArrayList<>();
            Parameter err = new IntegerParameter("error", -1);
            p1.add(err);
            UseCharacterMessage ucm1 = new UseCharacterMessage(ind - 1, p1);
            networkHandler.update(ucm1);
            usedCharacter = true;
        } else {

            System.out.print("Choose one of following students: ");

            if (((Monk) characters[ind - 1]).getStudents().size() == 1) {
                System.out.print(((Monk) characters[ind - 1]).getStudents().get(0).name() + "[1]\n");
            } else {
                System.out.print(((Monk) characters[ind - 1]).getStudents().get(0).name() + "[1]");
                for (int i = 1; i < (((Monk) characters[ind - 1]).getStudents().size() - 1); i++) {
                    System.out.print(" , " + ((Monk) characters[ind - 1]).getStudents().get(i).name() + "[" + (i + 1) + "]");
                }
                System.out.print(" or " + ((Monk) characters[ind - 1]).getStudents().get((((Monk) characters[ind - 1]).getStudents().size() - 1)).name() + "[" + ((Monk) characters[ind - 1]).getStudents().size() + "]\n");
            }

            int st = 0;
            boolean validStudent = false;
            while (!validStudent) {
                s = input.nextLine();

                try {
                    st = Integer.parseInt(s);
                    if (st >= 1 && st <= ((Monk) characters[ind - 1]).getStudents().size())
                        validStudent = true;
                    else
                        System.out.print("Wrong input. Insert a valid student index: ");
                } catch (NumberFormatException e) {
                    System.out.print("Wrong input. Insert a valid student index: ");
                }
            }

            System.out.print("Choose a target archipelago [between 1 and " + archipelagos.size() + "]: ");

            int ar = 0;
            boolean validA = false;
            while (!validA) {
                s = input.nextLine();

                try {
                    ar = Integer.parseInt(s);
                    if (ar >= 1 && ar <= archipelagos.size())
                        validA = true;
                    else
                        System.out.print("Wrong input. Insert a valid archipelago index: ");
                } catch (NumberFormatException e) {
                    System.out.print("Wrong input. Insert a valid archipelago index: ");
                }
            }

            ArrayList<Parameter> p1 = new ArrayList<>();
            Parameter arp = new IntegerParameter("archipelago index", ar - 1);
            Parameter stp = new IntegerParameter("student index", st - 1);
            p1.add(arp);
            p1.add(stp);
            UseCharacterMessage ucm1 = new UseCharacterMessage(ind - 1, p1);
            networkHandler.update(ucm1);
            usedCharacter = true;
        }
    }

    /**
     * This method is called when the player wants to use one of the following character: Farmer, Centaur, Knight
     * @param ind specifies the character's index in array 'characters' the player wants to use
     */
    private void useCharacter2_6_8(int ind) {
        ArrayList<Parameter> p2 = new ArrayList<>();
        UseCharacterMessage ucm2 = new UseCharacterMessage(ind - 1, p2);
        networkHandler.update(ucm2);
        usedCharacter = true;
    }

    /**
     * This method is called when the player wants to use one of the following character: Herald, HerbsGranny
     * @param ind specifies the character's index in array 'characters' the player wants to use
     */
    private void useCharacter3_5(int ind) {
        String s;
        Scanner input = new Scanner(System.in);

        System.out.print("Choose a target archipelago [between 1 and " + archipelagos.size() + "]: ");

        int ar3 = 0;
        boolean validA3 = false;
        while (!validA3) {
            s = input.nextLine();

            try {
                ar3 = Integer.parseInt(s);
                if (ar3 >= 1 && ar3 <= archipelagos.size())
                    validA3 = true;
                else
                    System.out.print("Wrong input. Insert a valid archipelago index: ");
            } catch (NumberFormatException e) {
                System.out.print("Wrong input. Insert a valid archipelago index: ");
            }
        }

        ArrayList<Parameter> p3 = new ArrayList<>();
        Parameter arp3 = new IntegerParameter("archipelago index", ar3 - 1);
        p3.add(arp3);
        UseCharacterMessage ucm3 = new UseCharacterMessage(ind - 1, p3);
        networkHandler.update(ucm3);
        usedCharacter = true;
    }

    /**
     * This method is called when the player wants to use MagicPostman character
     * @param ind specifies the character's index in array 'characters' the player wants to use
     */
    private void useCharacter4(int ind) {
        String s;
        Scanner input = new Scanner(System.in);

        System.out.print("Choose the number of additional Mother Nature steps [between 1 and 2]: ");

        int st4 = 0;
        boolean validSt4 = false;
        while (!validSt4) {
            s = input.nextLine();

            try {
                st4 = Integer.parseInt(s);
                if (st4 == 1 || st4 == 2)
                    validSt4 = true;
                else
                    System.out.print("Wrong input. Insert a valid number: ");
            } catch (NumberFormatException e) {
                System.out.print("Wrong input. Insert a valid number: ");
            }
        }

        ArrayList<Parameter> p4 = new ArrayList<>();
        Parameter stp4 = new IntegerParameter("steps", st4);
        p4.add(stp4);
        UseCharacterMessage ucm4 = new UseCharacterMessage(ind - 1, p4);
        networkHandler.update(ucm4);
        usedCharacter = true;
    }

    /**
     * This method is called when the player wants to use Jester character
     * @param ind specifies the character's index in array 'characters' the player wants to use
     */
    private void useCharacter7(int ind) {
        String s;
        Scanner input = new Scanner(System.in);

        ArrayList<Parameter> p7 = new ArrayList<>();

        int numStudEnt = 0;
        int eDim;

        Board b = new Board(0, null);
        for (Player pl : players) {
            if (pl.getNickname().equals(nickname)) {
                b = pl.getBoard();
            }
        }

        if (players.size() == 3)
            eDim = 9;
        else
            eDim = 7;

        for (int i = 0; i < eDim; i++) {
            if (b.getEntrance()[i] != null) {
                numStudEnt++;
            }
        }

        // Empty character or empty entrance case: the int value in the IntegerParameter is set to -1
        if (((Jester) characters[ind - 1]).getStudents().size() == 0 || numStudEnt == 0) {
            Parameter np = new IntegerParameter("number of students", -1);
            p7.add(np);
            UseCharacterMessage ucm7 = new UseCharacterMessage(ind - 1, p7);
            networkHandler.update(ucm7);
        } else {

            System.out.print("How many students do you want to swap [1 , 2 or 3] ? ");

            int st7 = 0;
            boolean validSt7 = false;
            while (!validSt7) {
                s = input.nextLine();

                try {
                    st7 = Integer.parseInt(s);
                    if ((st7 == 1 || st7 == 2 || st7 == 3) && st7 <= ((Jester) characters[ind - 1]).getStudents().size() && st7 <= numStudEnt)
                        validSt7 = true;
                    else
                        System.out.print("Wrong input. Insert a valid number: ");
                } catch (NumberFormatException e) {
                    System.out.print("Wrong input. Insert a valid number: ");
                }
            }

            Parameter np = new IntegerParameter("number of students", st7);
            p7.add(np);

            ArrayList<Integer> cardStudents = new ArrayList<>();
            for (int l = 0; l < ((Jester) characters[ind - 1]).getStudents().size(); l++)
                cardStudents.add(0);

            for (int i = 0; i < st7; i++) {
                System.out.print("Choose one of following card students: ");

                if (((Jester) characters[ind - 1]).getStudents().size() == 1) {
                    System.out.print(((Jester) characters[ind - 1]).getStudents().get(0).name() + "[1]\n");
                } else {
                    boolean first = false;
                    if (cardStudents.get(0) != -1) {
                        System.out.print(((Jester) characters[ind - 1]).getStudents().get(0).name() + "[1]");
                        first = true;
                    }
                    for (int j = 1; j < (((Jester) characters[ind - 1]).getStudents().size() - 1); j++) {
                        if (cardStudents.get(j) != -1) {
                            if (!first) {
                                System.out.print(((Jester) characters[ind - 1]).getStudents().get(j).name() + "[" + (j + 1) + "]");
                                first = true;
                            } else {
                                System.out.print(" , " + ((Jester) characters[ind - 1]).getStudents().get(j).name() + "[" + (j + 1) + "]");
                            }
                        }
                    }
                    if (cardStudents.get(((Jester) characters[ind - 1]).getStudents().size() - 1) != -1) {
                        if (!first) {
                            System.out.print(((Jester) characters[ind - 1]).getStudents().get((((Jester) characters[ind - 1]).getStudents().size() - 1)).name() + "[" + ((Jester) characters[ind - 1]).getStudents().size() + "]\n");
                        } else
                            System.out.print(" , " + ((Jester) characters[ind - 1]).getStudents().get((((Jester) characters[ind - 1]).getStudents().size() - 1)).name() + "[" + ((Jester) characters[ind - 1]).getStudents().size() + "]\n");
                    } else
                        System.out.print("\n");
                }

                int stud7 = 0;
                boolean validStud7 = false;
                while (!validStud7) {
                    s = input.nextLine();

                    try {
                        stud7 = Integer.parseInt(s);
                        if (stud7 >= 1 && stud7 <= ((Jester) characters[ind - 1]).getStudents().size()) {
                            if (cardStudents.get(stud7 - 1) != -1) {
                                cardStudents.set(stud7 - 1, -1);
                                validStud7 = true;
                            }
                        } else
                            System.out.print("Wrong input. Insert a valid student index: ");
                    } catch (NumberFormatException e) {
                        System.out.print("Wrong input. Insert a valid student index: ");
                    }
                }

                Parameter scp = new IntegerParameter("card student", stud7 - 1);
                p7.add(scp);
            }

            ArrayList<Integer> entranceStudents = new ArrayList<>();

            for (int l = 0; l < eDim; l++) {
                if (b.getEntrance()[l] == null)
                    entranceStudents.add(-1);
                else
                    entranceStudents.add(0);
            }

            for (int i = 0; i < st7; i++) {
                System.out.print("Choose one of following entrance students: ");

                boolean firstE = false;
                if (entranceStudents.get(0) != -1) {
                    System.out.print(b.getEntrance()[0].name() + "[1]\n");
                    firstE = true;
                }
                for (int k = 1; k < (eDim - 1); k++) {
                    if (entranceStudents.get(k) != -1) {
                        if (!firstE) {
                            System.out.print(b.getEntrance()[k].name() + "[" + (k + 1) + "]");
                            firstE = true;
                        } else {
                            System.out.print(" , " + b.getEntrance()[k].name() + "[" + (k + 1) + "]");
                        }
                    }
                }
                if (entranceStudents.get(eDim - 1) != -1) {
                    if (!firstE)
                        System.out.print(b.getEntrance()[eDim - 1].name() + "[" + eDim + "]\n");
                    else
                        System.out.print(" , " + b.getEntrance()[eDim - 1].name() + "[" + eDim + "]");
                } else
                    System.out.print("\n");

                int es7 = 0;
                boolean validES7 = false;
                while (!validES7) {
                    s = input.nextLine();

                    try {
                        es7 = Integer.parseInt(s);
                        if (es7 >= 1 && es7 <= eDim) {
                            if (entranceStudents.get(es7 - 1) != -1) {
                                entranceStudents.set(es7 - 1, -1);
                                validES7 = true;
                            }
                        } else
                            System.out.print("Wrong input. Insert a valid entrance index: ");
                    } catch (NumberFormatException e) {
                        System.out.print("Wrong input. Insert a valid entrance index: ");
                    }
                }

                Parameter sep = new IntegerParameter("card student", es7 - 1);
                p7.add(sep);
            }

            UseCharacterMessage ucm7 = new UseCharacterMessage(ind - 1, p7);
            networkHandler.update(ucm7);
            usedCharacter = true;
        }
    }

    /**
     * This method is called when the player wants to use one of the following character: MushroomHunter, Thief
     * @param ind specifies the character's index in array 'characters' the player wants to use
     */
    private void useCharacter9_12(int ind) {
        String s;
        Scanner input = new Scanner(System.in);

        ArrayList<Parameter> p9 = new ArrayList<>();

        System.out.println("Choose one of the following Student color: RED[r] , BLUE[b] , GREEN[g] , PINK[p] , YELLOW[y]");

        boolean valid9 = false;
        while (!valid9) {
            s = input.nextLine();
            if (s.equals("r")) {
                Parameter sp9 = new StudentParameter("color", Student.RED);
                p9.add(sp9);
                valid9 = true;
            } else if (s.equals("b")) {
                Parameter sp9 = new StudentParameter("color", Student.BLUE);
                p9.add(sp9);
                valid9 = true;
            } else if (s.equals("g")) {
                Parameter sp9 = new StudentParameter("color", Student.GREEN);
                p9.add(sp9);
                valid9 = true;
            } else if (s.equals("p")) {
                Parameter sp9 = new StudentParameter("color", Student.PINK);
                p9.add(sp9);
                valid9 = true;
            } else if (s.equals("y")) {
                Parameter sp9 = new StudentParameter("color", Student.YELLOW);
                p9.add(sp9);
                valid9 = true;
            } else
                System.out.print("Wrong input. Insert a valid color: ");
        }

        UseCharacterMessage ucm9 = new UseCharacterMessage(ind - 1, p9);
        networkHandler.update(ucm9);
        usedCharacter = true;
    }

    /**
     * This method is called when the player wants to use Minstrel character
     * @param ind specifies the character's index in array 'characters' the player wants to use
     */
    private void useCharacter10(int ind) {
        String s;
        Scanner input = new Scanner(System.in);

        Board b10 = new Board(0, null);
        for (Player pl : players) {
            if (pl.getNickname().equals(nickname))
                b10 = pl.getBoard();
        }

        int totSt = b10.getNumberOfStudents(Student.RED) + b10.getNumberOfStudents(Student.BLUE) + b10.getNumberOfStudents(Student.GREEN) + b10.getNumberOfStudents(Student.PINK) + b10.getNumberOfStudents(Student.YELLOW);

        boolean okEntrance1 = false;
        int entDim;
        int entStud = 0;
        if (players.size() == 3)
            entDim = 9;
        else
            entDim = 7;

        for (int i = 0; i < entDim; i++) {
            if (b10.getEntrance()[i] != null) {
                entStud++;
                if (b10.getNumberOfStudents(b10.getEntrance()[i]) < 10 && (totSt - b10.getNumberOfStudents(b10.getEntrance()[i])) > 0) {
                    okEntrance1 = true;
                }
            }
        }

        // Dining Room empty case or none of the student in the entrance can be moved: the int value in the IntegerParameter is set to -1
        if (totSt == 0 || !okEntrance1) {
            ArrayList<Parameter> p10 = new ArrayList<>();
            Parameter ip10 = new IntegerParameter("student index", -1);
            p10.add(ip10);
            UseCharacterMessage ucm10 = new UseCharacterMessage(ind - 1, p10);
            networkHandler.update(ucm10);
            usedCharacter = true;
        } else {
            if (totSt == 1 || entStud == 1)
                System.out.print("How many students do you want to swap [1] ? ");
            else
                System.out.print("How many students do you want to swap [1 or 2] ? ");

            int numSt10 = 0;
            boolean validSt10 = false;
            while (!validSt10) {
                s = input.nextLine();

                try {
                    numSt10 = Integer.parseInt(s);
                    if (numSt10 == 1) {
                        validSt10 = true;
                    } else if (numSt10 == 2 && totSt > 1 && entStud > 1) {
                        validSt10 = true;
                    } else if (numSt10 == 2 && totSt == 1) {
                        System.out.print("You haven't got enough students in your Dining Room. Insert a valid number: ");
                    } else if (numSt10 == 2 && entStud == 1) {
                        System.out.print("You haven't got enough students in your Entrance. Insert a valid number: ");
                    } else
                        System.out.print("Wrong input. Insert a valid number: ");
                } catch (NumberFormatException e) {
                    System.out.print("Wrong input. Insert a valid number: ");
                }
            }

            Student[] entrance = b10.getEntrance();


            System.out.print("Choose one of following entrance students: ");

            boolean firstE = false;
            if (entrance[0] != null) {
                System.out.print(entrance[0].name() + "[1]");
                firstE = true;
            }
            for (int k = 1; k < (entDim - 1); k++) {
                if (entrance[k] != null) {
                    if (!firstE) {
                        System.out.print(entrance[k].name() + "[" + (k + 1) + "]");
                        firstE = true;
                    } else {
                        System.out.print(" , " + entrance[k].name() + "[" + (k + 1) + "]");
                    }
                }
            }
            if (entrance[entDim - 1] != null) {
                if (!firstE)
                    System.out.print(entrance[entDim - 1].name() + "[" + entDim + "]\n");
                else
                    System.out.print(" , " + entrance[entDim - 1].name() + "[" + entDim + "]");
            } else
                System.out.print("\n");

            int fEs10 = 0;
            boolean validFES10 = false;
            while (!validFES10) {
                s = input.nextLine();

                try {
                    fEs10 = Integer.parseInt(s);
                    if (fEs10 >= 1 && fEs10 <= entDim) {
                        if (entrance[fEs10 - 1] != null && b10.getNumberOfStudents(entrance[fEs10 - 1]) < 10 && (totSt - b10.getNumberOfStudents(entrance[fEs10 - 1])) > 0) {
                            validFES10 = true;
                        } else
                            System.out.print("Wrong input. Insert a valid entrance index: ");
                    } else
                        System.out.print("Wrong input. Insert a valid entrance index: ");
                } catch (NumberFormatException e) {
                    System.out.print("Wrong input. Insert a valid entrance index: ");
                }
            }

            System.out.print("Choose one of following Dining Room students:");

            boolean firstH = false;
            if (b10.getNumberOfStudents(Student.RED) > 0 && !entrance[fEs10 - 1].name().equals("RED")) {
                System.out.print(" RED[r]");
                firstH = true;
            }
            if (b10.getNumberOfStudents(Student.BLUE) > 0 && !entrance[fEs10 - 1].name().equals("BLUE")) {
                if (firstH)
                    System.out.print(" , BLUE[b]");
                else {
                    System.out.print(" BLUE[b]");
                    firstH = true;
                }
            }
            if (b10.getNumberOfStudents(Student.GREEN) > 0 && !entrance[fEs10 - 1].name().equals("GREEN")) {
                if (firstH)
                    System.out.print(" , GREEN[g]");
                else {
                    System.out.print(" GREEN[g]");
                    firstH = true;
                }
            }
            if (b10.getNumberOfStudents(Student.PINK) > 0 && !entrance[fEs10 - 1].name().equals("PINK")) {
                if (firstH)
                    System.out.print(" , PINK[p]");
                else {
                    System.out.print(" PINK[p]");
                    firstH = true;
                }
            }
            if (b10.getNumberOfStudents(Student.YELLOW) > 0 && !entrance[fEs10 - 1].name().equals("YELLOW")) {
                if (firstH)
                    System.out.print(" , YELLOW[y]");
                else {
                    System.out.print(" YELLOW[y]");
                }
            }
            System.out.print("\n");

            String fDs10 = "";
            Student fDs = null;
            boolean validFDS10 = false;
            while (!validFDS10) {
                fDs10 = input.nextLine();

                if (fDs10.equals("r") && b10.getNumberOfStudents(Student.RED) > 0 && !entrance[fEs10 - 1].name().equals("RED")) {
                    fDs = Student.RED;
                    validFDS10 = true;
                } else if (fDs10.equals("b") && b10.getNumberOfStudents(Student.BLUE) > 0 && !entrance[fEs10 - 1].name().equals("BLUE")) {
                    fDs = Student.BLUE;
                    validFDS10 = true;
                } else if (fDs10.equals("g") && b10.getNumberOfStudents(Student.GREEN) > 0 && !entrance[fEs10 - 1].name().equals("GREEN")) {
                    fDs = Student.GREEN;
                    validFDS10 = true;
                } else if (fDs10.equals("p") && b10.getNumberOfStudents(Student.PINK) > 0 && !entrance[fEs10 - 1].name().equals("PINK")) {
                    fDs = Student.PINK;
                    validFDS10 = true;
                } else if (fDs10.equals("y") && b10.getNumberOfStudents(Student.YELLOW) > 0 && !entrance[fEs10 - 1].name().equals("YELLOW")) {
                    fDs = Student.YELLOW;
                    validFDS10 = true;
                } else
                    System.out.print("Wrong input. Insert a valid color: ");
            }

            boolean okEntrance2 = false;
            int sEs10 = 0;
            Student sDs = null;
            if (numSt10 == 2) {

                ArrayList<Student> tempEnt = new ArrayList<>();
                EnumMap<Student, Integer> tempHall = new EnumMap<>(Student.class);

                for (int i = 0; i < entDim; i++) {
                    if (entrance[i] != null && i != (fEs10 - 1))
                        tempEnt.add(entrance[i]);
                    else
                        tempEnt.add(null);
                }

                tempHall.put(Student.RED, b10.getNumberOfStudents(Student.RED));
                tempHall.put(Student.BLUE, b10.getNumberOfStudents(Student.BLUE));
                tempHall.put(Student.GREEN, b10.getNumberOfStudents(Student.GREEN));
                tempHall.put(Student.PINK, b10.getNumberOfStudents(Student.PINK));
                tempHall.put(Student.YELLOW, b10.getNumberOfStudents(Student.YELLOW));

                if (fDs10.equals("r"))
                    tempHall.put(Student.RED, b10.getNumberOfStudents(Student.RED) - 1);
                else if (fDs10.equals("b"))
                    tempHall.put(Student.BLUE, b10.getNumberOfStudents(Student.BLUE) - 1);
                else if (fDs10.equals("g"))
                    tempHall.put(Student.GREEN, b10.getNumberOfStudents(Student.GREEN) - 1);
                else if (fDs10.equals("p"))
                    tempHall.put(Student.PINK, b10.getNumberOfStudents(Student.PINK) - 1);
                else
                    tempHall.put(Student.YELLOW, b10.getNumberOfStudents(Student.YELLOW) - 1);

                tempHall.put(entrance[fEs10 - 1], tempHall.get(entrance[fEs10 - 1]) + 1);

                int totSt2 = tempHall.get(Student.RED) + tempHall.get(Student.BLUE) + tempHall.get(Student.GREEN) + tempHall.get(Student.PINK) + tempHall.get(Student.YELLOW);

                for (int i = 0; i < entDim; i++) {
                    if (tempEnt.get(i) != null) {
                        if (tempHall.get(tempEnt.get(i)) < 10 && (totSt2 - tempHall.get(tempEnt.get(i))) > 0)
                            okEntrance2 = true;
                    }
                }

                if (okEntrance2) {

                    System.out.print("Choose one of following entrance students: ");

                    boolean firstE2 = false;
                    if (tempEnt.get(0) != null) {
                        System.out.print(tempEnt.get(0).name() + "[1]\n");
                        firstE2 = true;
                    }
                    for (int k = 1; k < (entDim - 1); k++) {
                        if (tempEnt.get(k) != null) {
                            if (!firstE2) {
                                System.out.print(tempEnt.get(k).name() + "[" + (k + 1) + "]");
                                firstE2 = true;
                            } else {
                                System.out.print(" , " + tempEnt.get(k).name() + "[" + (k + 1) + "]");
                            }
                        }
                    }
                    if (tempEnt.get(entDim - 1) != null) {
                        if (!firstE2)
                            System.out.print(tempEnt.get(entDim - 1).name() + "[" + entDim + "]\n");
                        else
                            System.out.print(" , " + tempEnt.get(entDim - 1).name() + "[" + entDim + "]");
                    } else
                        System.out.print("\n");

                    boolean validSES10 = false;
                    while (!validSES10) {
                        s = input.nextLine();

                        try {
                            sEs10 = Integer.parseInt(s);
                            if (sEs10 >= 1 && sEs10 <= entDim) {
                                if (tempEnt.get(sEs10 - 1) != null && tempHall.get(tempEnt.get(sEs10 - 1)) < 10 && (totSt2 - tempHall.get(tempEnt.get(sEs10 - 1))) > 0) {
                                    validSES10 = true;
                                } else
                                    System.out.print("Wrong input. Insert a valid entrance index: ");
                            } else
                                System.out.print("Wrong input. Insert a valid entrance index: ");
                        } catch (NumberFormatException e) {
                            System.out.print("Wrong input. Insert a valid entrance index: ");
                        }
                    }

                    System.out.print("Choose one of following Dining Room students:");

                    boolean firstH2 = false;
                    if (tempHall.get(Student.RED) > 0 && !tempEnt.get(sEs10 - 1).name().equals("RED")) {
                        System.out.print(" RED[r]");
                        firstH2 = true;
                    }
                    if (tempHall.get(Student.BLUE) > 0 && !tempEnt.get(sEs10 - 1).name().equals("BLUE")) {
                        if (firstH2)
                            System.out.print(" , BLUE[b]");
                        else {
                            System.out.print(" BLUE[b]");
                            firstH2 = true;
                        }
                    }
                    if (tempHall.get(Student.GREEN) > 0 && !tempEnt.get(sEs10 - 1).name().equals("GREEN")) {
                        if (firstH2)
                            System.out.print(" , GREEN[g]");
                        else {
                            System.out.print(" GREEN[g]");
                            firstH2 = true;
                        }
                    }
                    if (tempHall.get(Student.PINK) > 0 && !tempEnt.get(sEs10 - 1).name().equals("PINK")) {
                        if (firstH2)
                            System.out.print(" , PINK[p]");
                        else {
                            System.out.print(" PINK[p]");
                            firstH2 = true;
                        }
                    }
                    if (tempHall.get(Student.YELLOW) > 0 && !tempEnt.get(sEs10 - 1).name().equals("YELLOW")) {
                        if (firstH2)
                            System.out.print(" , YELLOW[y]");
                        else {
                            System.out.print(" YELLOW[y]");
                        }
                    }
                    System.out.print("\n");

                    String sDs10;
                    boolean validSDS10 = false;
                    while (!validSDS10) {
                        sDs10 = input.nextLine();

                        if (sDs10.equals("r") && tempHall.get(Student.RED) > 0 && !tempEnt.get(sEs10 - 1).name().equals("RED")) {
                            sDs = Student.RED;
                            validSDS10 = true;
                        } else if (sDs10.equals("b") && tempHall.get(Student.BLUE) > 0 && !tempEnt.get(sEs10 - 1).name().equals("BLUE")) {
                            sDs = Student.BLUE;
                            validSDS10 = true;
                        } else if (sDs10.equals("g") && tempHall.get(Student.GREEN) > 0 && !tempEnt.get(sEs10 - 1).name().equals("GREEN")) {
                            sDs = Student.GREEN;
                            validSDS10 = true;
                        } else if (sDs10.equals("p") && tempHall.get(Student.PINK) > 0 && !tempEnt.get(sEs10 - 1).name().equals("PINK")) {
                            sDs = Student.PINK;
                            validSDS10 = true;
                        } else if (sDs10.equals("y") && tempHall.get(Student.YELLOW) > 0 && !tempEnt.get(sEs10 - 1).name().equals("YELLOW")) {
                            sDs = Student.YELLOW;
                            validSDS10 = true;
                        } else
                            System.out.print("Wrong input. Insert a valid color: ");
                    }
                } else {
                    System.out.println("You wanted to swap 2 couples of students but you can only swap one");
                }
            }

            ArrayList<Parameter> p10 = new ArrayList<>();

            Parameter numStP;
            if (numSt10 == 2 && okEntrance2)
                numStP = new IntegerParameter("number of couples", 2);
            else
                numStP = new IntegerParameter("number of couples", 1);
            p10.add(numStP);

            Parameter es1p = new IntegerParameter("first entrance student", fEs10 - 1);
            Parameter ds1p = new StudentParameter("fist hall student", fDs);

            Parameter es2p = null;
            Parameter ds2p = null;
            if (numSt10 == 2 && okEntrance2) {
                es2p = new IntegerParameter("second entrance student", sEs10 - 1);
                ds2p = new StudentParameter("second hall student", sDs);
            }

            if (numSt10 == 2 && okEntrance2) {
                p10.add(es1p);
                p10.add(es2p);
                p10.add(ds1p);
                p10.add(ds2p);
            } else {
                p10.add(es1p);
                p10.add(ds1p);
            }

            UseCharacterMessage ucm10 = new UseCharacterMessage(ind - 1, p10);
            networkHandler.update(ucm10);
            usedCharacter = true;
        }
    }

    /**
     * This method is called when the player wants to use SpoiledPrincess character
     * @param ind specifies the character's index in array 'characters' the player wants to use
     */
    private void useCharacter11(int ind) {
        String s;
        Scanner input = new Scanner(System.in);

        Board b11 = new Board(0, null);
        for (Player pl : players) {
            if (pl.getNickname().equals(nickname))
                b11 = pl.getBoard();
        }

        boolean full = true;
        for (Student stud : ((SpoiledPrincess) characters[ind - 1]).getStudents()) {
            if (b11.getNumberOfStudents(stud) < 10)
                full = false;
        }

        // Dining Room full case: the int value in the IntegerParameter is set to -1
        if (full) {
            ArrayList<Parameter> p11 = new ArrayList<>();
            Parameter ip11 = new IntegerParameter("student index", -1);
            p11.add(ip11);
            UseCharacterMessage ucm11 = new UseCharacterMessage(ind - 1, p11);
            networkHandler.update(ucm11);
            usedCharacter = true;
        } else {
            System.out.print("Choose one of following students: ");

            if (((SpoiledPrincess) characters[ind - 1]).getStudents().size() == 1) {
                System.out.print(((SpoiledPrincess) characters[ind - 1]).getStudents().get(0).name() + "[1]\n");
            } else {
                System.out.print(((SpoiledPrincess) characters[ind - 1]).getStudents().get(0).name() + "[1]");
                for (int i = 1; i < (((SpoiledPrincess) characters[ind - 1]).getStudents().size() - 1); i++) {
                    System.out.print(" , " + ((SpoiledPrincess) characters[ind - 1]).getStudents().get(i).name() + "[" + (i + 1) + "]");
                }
                System.out.print(" or " + ((SpoiledPrincess) characters[ind - 1]).getStudents().get((((SpoiledPrincess) characters[ind - 1]).getStudents().size() - 1)).name() + "[" + ((SpoiledPrincess) characters[ind - 1]).getStudents().size() + "]\n");
            }

            int st11 = 0;
            boolean validSt11 = false;
            while (!validSt11) {
                s = input.nextLine();

                try {
                    st11 = Integer.parseInt(s);
                    if (st11 >= 1 && st11 <= ((SpoiledPrincess) characters[ind - 1]).getStudents().size()) {
                        if (b11.getNumberOfStudents(((SpoiledPrincess) characters[ind - 1]).getStudents().get(st11 - 1)) < 10)
                            validSt11 = true;
                        else
                            System.out.print("Your Dining Room table is full. Insert a valid student index: ");
                    } else
                        System.out.print("Wrong input. Insert a valid student index: ");
                } catch (NumberFormatException e) {
                    System.out.print("Wrong input. Insert a valid student index: ");
                }
            }

            ArrayList<Parameter> p11 = new ArrayList<>();
            Parameter ip11 = new IntegerParameter("student index", st11 - 1);
            p11.add(ip11);
            UseCharacterMessage ucm11 = new UseCharacterMessage(ind - 1, p11);
            networkHandler.update(ucm11);
            usedCharacter = true;
        }
    }

    /**
     * This method prints the game title
     */
    private void printTitle() {

        System.out.println("\n" +
                "                                             \u001B[34m███████╗██████╗ ██╗ █████╗ ███╗   ██╗████████╗██╗   ██╗███████╗\n" +
                "                                             ██╔════╝██╔══██╗██║██╔══██╗████╗  ██║╚══██╔══╝╚██╗ ██╔╝██╔════╝\n" +
                "                                             █████╗  ██████╔╝██║███████║██╔██╗ ██║   ██║    ╚████╔╝ ███████╗\n" +
                "                                             ██╔══╝  ██╔══██╗██║██╔══██║██║╚██╗██║   ██║     ╚██╔╝  ╚════██║\n" +
                "                                             ███████╗██║  ██║██║██║  ██║██║ ╚████║   ██║      ██║   ███████║\n" +
                "                                             ╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝      ╚═╝   ╚══════╝\u001B[0m");

    }

    /**
     * This method prints the islands on the game table
     */
    private void printIslands() {
        int num = 0;
        int n;
        String[] red = new String[12];
        String[] blue = new String[12];
        String[] green = new String[12];
        String[] pink = new String[12];
        String[] yellow = new String[12];
        String[] mn = new String[12];
        String[] t = new String[12];
        String[][] link = new String[12][2];
        String[] nE = new String[12];

        for (Archipelago a : archipelagos) {
            for (Island i : a.getIslands()) {
                n = (int) i.getStudents().stream().filter(x -> x == Student.RED).count();
                if (n < 10)
                    red[num] = " " + n;
                else
                    red[num] = Integer.toString(n);

                n = (int) i.getStudents().stream().filter(x -> x == Student.BLUE).count();
                if (n < 10)
                    blue[num] = " " + n;
                else
                    blue[num] = Integer.toString(n);

                n = (int) i.getStudents().stream().filter(x -> x == Student.GREEN).count();
                if (n < 10)
                    green[num] = " " + n;
                else
                    green[num] = Integer.toString(n);

                n = (int) i.getStudents().stream().filter(x -> x == Student.PINK).count();
                if (n < 10)
                    pink[num] = " " + n;
                else
                    pink[num] = Integer.toString(n);

                n = (int) i.getStudents().stream().filter(x -> x == Student.YELLOW).count();
                if (n < 10)
                    yellow[num] = " " + n;
                else
                    yellow[num] = Integer.toString(n);

                if (a.checkMotherNature())
                    mn[num] = "M";
                else
                    mn[num] = " ";

                if (a.getTower() == Tower.BLACK)
                    t[num] = "BLACK";
                else if (a.getTower() == Tower.WHITE)
                    t[num] = "WHITE";
                else if (a.getTower() == Tower.GREY)
                    t[num] = "GREY ";
                else
                    t[num] = "NONE ";

                if (a.getIslands().get(0).equals(i))
                    link[num][0] = "\u001B[31m";
                else
                    link[num][0] = "\u001B[32m";

                if (a.getIslands().get(a.getIslands().size() - 1).equals(i))
                    link[num][1] = "\u001B[31m";
                else
                    link[num][1] = "\u001B[32m";

                if (a.checkNoEntry() > 0)
                    nE[num] = a.checkNoEntry() + "NE";
                else
                    nE[num] = "▄▄▄";

                num++;
            }
        }


        System.out.println("\n" +
                "     1 ▄▄▄▄▄▄▄▄▄▄▄" + nE[0] + "▄        2 ▄▄▄▄▄▄▄▄▄▄▄" + nE[1] + "▄        3 ▄▄▄▄▄▄▄▄▄▄▄" + nE[2] + "▄         4 ▄▄▄▄▄▄▄▄▄▄▄" + nE[3] + "▄        5 ▄▄▄▄▄▄▄▄▄▄▄" + nE[4] + "▄        6 ▄▄▄▄▄▄▄▄▄▄▄" + nE[5] + "▄\n" +
                "   " + link[0][0] + "\u2588\u001B[0m    \u001B[32m⬤" + green[0] + "   \u001B[31m⬤" + red[0] + "\u001B[0m     " + link[0][1] + "\u2588\u001B[0m     " + link[1][0] + "\u2588\u001B[0m    \u001B[32m⬤" + green[1] + "   \u001B[31m⬤" + red[1] + "\u001B[0m     " + link[1][1] + "\u2588\u001B[0m     " + link[2][0] + "\u2588\u001B[0m    \u001B[32m⬤" + green[2] + "   \u001B[31m⬤" + red[2] + "\u001B[0m     " + link[2][1] + "\u2588\u001B[0m      " + link[3][0] + "\u2588\u001B[0m    \u001B[32m⬤" + green[3] + "   \u001B[31m⬤" + red[3] + "\u001B[0m     " + link[3][1] + "\u2588\u001B[0m     " + link[4][0] + "\u2588\u001B[0m    \u001B[32m⬤" + green[4] + "   \u001B[31m⬤" + red[4] + "\u001B[0m     " + link[4][1] + "\u2588\u001B[0m     " + link[5][0] + "\u2588\u001B[0m    \u001B[32m⬤" + green[5] + "   \u001B[31m⬤" + red[5] + "\u001B[0m     " + link[5][1] + "\u2588\u001B[0m\n" +
                "   " + link[0][0] + "\u2588\u001B[0m \u001B[33m⬤" + yellow[0] + "\u001B[0m    " + mn[0] + "    \u001B[95m⬤" + pink[0] + "\u001B[0m  " + link[0][1] + "\u2588\u001B[0m     " + link[1][0] + "\u2588\u001B[0m \u001B[33m⬤" + yellow[1] + "\u001B[0m    " + mn[1] + "    \u001B[95m⬤" + pink[1] + "\u001B[0m  " + link[1][1] + "\u2588\u001B[0m     " + link[2][0] + "\u2588\u001B[0m \u001B[33m⬤" + yellow[2] + "\u001B[0m    " + mn[2] + "    \u001B[95m⬤" + pink[2] + "\u001B[0m  " + link[2][1] + "\u2588\u001B[0m      " + link[3][0] + "\u2588\u001B[0m \u001B[33m⬤" + yellow[3] + "\u001B[0m    " + mn[3] + "    \u001B[95m⬤" + pink[3] + "\u001B[0m  " + link[3][1] + "\u2588\u001B[0m     " + link[4][0] + "\u2588\u001B[0m \u001B[33m⬤" + yellow[4] + "\u001B[0m    " + mn[4] + "    \u001B[95m⬤" + pink[4] + "\u001B[0m  " + link[4][1] + "\u2588\u001B[0m     " + link[5][0] + "\u2588\u001B[0m \u001B[33m⬤" + yellow[5] + "\u001B[0m    " + mn[5] + "    \u001B[95m⬤" + pink[5] + "\u001B[0m  " + link[5][1] + "\u2588\u001B[0m\n" +
                "   " + link[0][0] + "\u2588\u001B[0m    \u001B[96m⬤" + blue[0] + "\u001B[0m   ♖" + t[0] + "⠀ " + link[0][1] + "\u2588\u001B[0m     " + link[1][0] + "\u2588\u001B[0m    \u001B[96m⬤" + blue[1] + "\u001B[0m   ♖" + t[1] + "⠀ " + link[1][1] + "\u2588\u001B[0m     " + link[2][0] + "\u2588\u001B[0m    \u001B[96m⬤" + blue[2] + "\u001B[0m   ♖" + t[2] + "⠀ " + link[2][1] + "\u2588\u001B[0m      " + link[3][0] + "\u2588\u001B[0m    \u001B[96m⬤" + blue[3] + "\u001B[0m   ♖" + t[3] + "⠀ " + link[3][1] + "\u2588\u001B[0m     " + link[4][0] + "\u2588\u001B[0m    \u001B[96m⬤" + blue[4] + "\u001B[0m   ♖" + t[4] + " ⠀" + link[4][1] + "\u2588\u001B[0m     " + link[5][0] + "\u2588\u001B[0m    \u001B[96m⬤" + blue[5] + "\u001B[0m   ♖" + t[5] + "  " + link[5][1] + "\u2588\u001B[0m\n" +
                "    ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀       ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀       ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀        ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀       ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀       ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\n" +
                "\n" +
                "     7 ▄▄▄▄▄▄▄▄▄▄▄" + nE[6] + "▄        8 ▄▄▄▄▄▄▄▄▄▄▄" + nE[7] + "▄        9 ▄▄▄▄▄▄▄▄▄▄▄" + nE[8] + "▄         10 ▄▄▄▄▄▄▄▄▄▄" + nE[9] + "▄        11 ▄▄▄▄▄▄▄▄▄▄" + nE[10] + "▄        12 ▄▄▄▄▄▄▄▄▄▄" + nE[11] + "▄\n" +
                "   " + link[6][0] + "\u2588\u001B[0m    \u001B[32m⬤" + green[6] + "   \u001B[31m⬤" + red[6] + "\u001B[0m     " + link[6][1] + "\u2588\u001B[0m     " + link[7][0] + "\u2588\u001B[0m    \u001B[32m⬤" + green[7] + "   \u001B[31m⬤" + red[7] + "\u001B[0m     " + link[7][1] + "\u2588\u001B[0m     " + link[8][0] + "\u2588\u001B[0m    \u001B[32m⬤" + green[8] + "   \u001B[31m⬤" + red[8] + "\u001B[0m     " + link[8][1] + "\u2588\u001B[0m      " + link[9][0] + "\u2588\u001B[0m    \u001B[32m⬤" + green[9] + "   \u001B[31m⬤" + red[9] + "\u001B[0m     " + link[9][1] + "\u2588\u001B[0m     " + link[10][0] + "\u2588\u001B[0m    \u001B[32m⬤" + green[10] + "   \u001B[31m⬤" + red[10] + "\u001B[0m     " + link[10][1] + "\u2588\u001B[0m     " + link[11][0] + "\u2588\u001B[0m    \u001B[32m⬤" + green[11] + "   \u001B[31m⬤" + red[11] + "\u001B[0m     " + link[11][1] + "\u2588\u001B[0m\n" +
                "   " + link[6][0] + "\u2588\u001B[0m \u001B[33m⬤" + yellow[6] + "\u001B[0m    " + mn[6] + "    \u001B[95m⬤" + pink[6] + "\u001B[0m  " + link[6][1] + "\u2588\u001B[0m     " + link[7][0] + "\u2588\u001B[0m \u001B[33m⬤" + yellow[7] + "\u001B[0m    " + mn[7] + "    \u001B[95m⬤" + pink[7] + "\u001B[0m  " + link[7][1] + "\u2588\u001B[0m     " + link[8][0] + "\u2588\u001B[0m \u001B[33m⬤" + yellow[8] + "\u001B[0m    " + mn[8] + "    \u001B[95m⬤" + pink[8] + "\u001B[0m  " + link[8][1] + "\u2588\u001B[0m      " + link[9][0] + "\u2588\u001B[0m \u001B[33m⬤" + yellow[9] + "\u001B[0m    " + mn[9] + "    \u001B[95m⬤" + pink[9] + "\u001B[0m  " + link[9][1] + "\u2588\u001B[0m     " + link[10][0] + "\u2588\u001B[0m \u001B[33m⬤" + yellow[10] + "\u001B[0m    " + mn[10] + "    \u001B[95m⬤" + pink[10] + "\u001B[0m  " + link[10][1] + "\u2588\u001B[0m     " + link[11][0] + "\u2588\u001B[0m \u001B[33m⬤" + yellow[11] + "\u001B[0m    " + mn[11] + "    \u001B[95m⬤" + pink[11] + "\u001B[0m  " + link[11][1] + "\u2588\u001B[0m\n" +
                "   " + link[6][0] + "\u2588\u001B[0m    \u001B[96m⬤" + blue[6] + "\u001B[0m   ♖" + t[6] + "⠀ " + link[6][1] + "\u2588\u001B[0m     " + link[7][0] + "\u2588\u001B[0m    \u001B[96m⬤" + blue[7] + "\u001B[0m   ♖" + t[7] + "⠀ " + link[7][1] + "\u2588\u001B[0m     " + link[8][0] + "\u2588\u001B[0m    \u001B[96m⬤" + blue[8] + "\u001B[0m   ♖" + t[8] + " ⠀" + link[8][1] + "\u2588\u001B[0m      " + link[9][0] + "\u2588\u001B[0m    \u001B[96m⬤" + blue[9] + "\u001B[0m   ♖" + t[9] + "⠀ " + link[9][1] + "\u2588\u001B[0m     " + link[10][0] + "\u2588\u001B[0m    \u001B[96m⬤" + blue[10] + "\u001B[0m   ♖" + t[10] + " ⠀" + link[10][1] + "\u2588\u001B[0m     " + link[11][0] + "\u2588\u001B[0m    \u001B[96m⬤" + blue[11] + "\u001B[0m   ♖" + t[11] + "⠀ " + link[11][1] + "\u2588\u001B[0m\n" +
                "    ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀       ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀       ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀        ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀       ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀       ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\n");


    }

    /**
     * This method prints the clouds and the characters (only if the expert mode is enabled) on the game table
     */
    private void printCloudsAndCharacters() {
        if (players.size() == 2 && !expertMode) {

            int[] red = new int[2];
            int[] blue = new int[2];
            int[] green = new int[2];
            int[] pink = new int[2];
            int[] yellow = new int[2];

            for (int i = 0; i < 2; i++) {
                red[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.RED).count();
                blue[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.BLUE).count();
                green[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.GREEN).count();
                pink[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.PINK).count();
                yellow[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.YELLOW).count();
            }

            System.out.println("\n" +
                    "        ▄▄▄▄▄▄▄                            ▄▄▄▄▄▄▄\n" +
                    "      ▓▀   1   ▀▓                        ▓▀   2   ▀▓\n" +
                    "  ▓▀▀▀▀  \u001B[32m" + green[0] + "  \u001B[31m " + red[0] + "\u001B[0m   ▀▀▀▀▓               ▓▀▀▀▀  \u001B[32m" + green[1] + "  \u001B[31m " + red[1] + "\u001B[0m   ▀▀▀▀▓\n" +
                    " ▓▀   \u001B[33m" + yellow[0] + "\u001B[0m     \u001B[95m" + pink[0] + "\u001B[0m     \u001B[96m" + blue[0] + "\u001B[0m   ▀▓           ▓▀   \u001B[33m" + yellow[1] + "\u001B[0m     \u001B[95m" + pink[1] + "\u001B[0m     \u001B[96m" + blue[1] + "\u001B[0m    ▀▓\n" +
                    "  ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀             ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀ ");

        } else if (players.size() == 2 && expertMode) {

            int[] red = new int[2];
            int[] blue = new int[2];
            int[] green = new int[2];
            int[] pink = new int[2];
            int[] yellow = new int[2];
            String[] name = new String[3];
            int[] cost = new int[3];

            for (int i = 0; i < 2; i++) {
                red[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.RED).count();
                blue[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.BLUE).count();
                green[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.GREEN).count();
                pink[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.PINK).count();
                yellow[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.YELLOW).count();
            }

            for (int i = 0; i < 3; i++) {
                cost[i] = characters[i].getCost();

                name[i] = getCharacterName(i);
            }

            System.out.println("\n" +
                    "\n" +
                    "        ▄▄▄▄▄▄▄                            ▄▄▄▄▄▄▄                                                                             ▓            CHARACTERS:           ▓\n" +
                    "      ▓▀   1   ▀▓                        ▓▀   2   ▀▓\n" +
                    "  ▓▀▀▀▀  \u001B[32m" + green[0] + "  \u001B[31m " + red[0] + "\u001B[0m   ▀▀▀▀▓               ▓▀▀▀▀  \u001B[32m" + green[1] + "  \u001B[31m " + red[1] + "\u001B[0m  ▀▀▀▀▓                                                                       ▓  1 - " + name[0] + " - COST: " + cost[0] + "  ▓\n" +
                    " ▓▀   \u001B[33m" + yellow[0] + "\u001B[0m     \u001B[95m" + pink[0] + "\u001B[0m     \u001B[96m" + blue[0] + "\u001B[0m   ▀▓           ▓▀   \u001B[33m" + yellow[1] + "\u001B[0m     \u001B[95m" + pink[1] + "\u001B[0m     \u001B[96m" + blue[1] + "\u001B[0m   ▀▓\n" +
                    "  ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀             ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀                                                                     ▓  2 - " + name[1] + " - COST: " + cost[1] + "  ▓\n" +
                    "\n" +
                    "                                                                                                                               ▓  3 - " + name[2] + " - COST: " + cost[2] + "  ▓");

        } else if (players.size() == 3 && !expertMode) {

            int[] red = new int[3];
            int[] blue = new int[3];
            int[] green = new int[3];
            int[] pink = new int[3];
            int[] yellow = new int[3];

            for (int i = 0; i < 3; i++) {
                red[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.RED).count();
                blue[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.BLUE).count();
                green[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.GREEN).count();
                pink[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.PINK).count();
                yellow[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.YELLOW).count();
            }

            System.out.println("" +
                    "        ▄▄▄▄▄▄▄                            ▄▄▄▄▄▄▄                             ▄▄▄▄▄▄▄\n" +
                    "      ▓▀   1   ▀▓                        ▓▀   2   ▀▓                         ▓▀   3   ▀▓\n" +
                    "  ▓▀▀▀▀  \u001B[32m" + green[0] + "  \u001B[31m " + red[0] + "\u001B[0m   ▀▀▀▀▓               ▓▀▀▀▀  \u001B[32m" + green[1] + "  \u001B[31m " + red[1] + "\u001B[0m   ▀▀▀▀▓               ▓▀▀▀▀   \u001B[32m" + green[2] + "   \u001B[31m" + red[2] + " \u001B[0m  ▀▀▀▀▓\n" +
                    " ▓▀   \u001B[33m" + yellow[0] + "\u001B[0m     \u001B[95m" + pink[0] + "\u001B[0m     \u001B[96m" + blue[0] + "\u001B[0m   ▀▓           ▓▀   \u001B[33m" + yellow[1] + "\u001B[0m     \u001B[95m" + pink[1] + "\u001B[0m     \u001B[96m" + blue[1] + "\u001B[0m    ▀▓           ▓▀   \u001B[33m" + yellow[2] + " \u001B[0m     \u001B[95m" + pink[2] + " \u001B[0m    \u001B[96m" + blue[2] + " \u001B[0m   ▀▓\n" +
                    "  ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀             ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀             ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");

        } else if (players.size() == 3 && expertMode) {

            int[] red = new int[3];
            int[] blue = new int[3];
            int[] green = new int[3];
            int[] pink = new int[3];
            int[] yellow = new int[3];
            String[] name = new String[3];
            int[] cost = new int[3];

            for (int i = 0; i < 3; i++) {
                red[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.RED).count();
                blue[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.BLUE).count();
                green[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.GREEN).count();
                pink[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.PINK).count();
                yellow[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.YELLOW).count();
            }

            for (int i = 0; i < 3; i++) {
                cost[i] = characters[i].getCost();

                name[i] = getCharacterName(i);
            }

            System.out.println("\n" +
                    "\n" +
                    "        ▄▄▄▄▄▄▄                            ▄▄▄▄▄▄▄                             ▄▄▄▄▄▄▄                                ▓            CHARACTERS:           ▓\n" +
                    "      ▓▀   1   ▀▓                        ▓▀   2   ▀▓                         ▓▀   3   ▀▓\n" +
                    "  ▓▀▀▀▀  \u001B[32m" + green[0] + "  \u001B[31m " + red[0] + "\u001B[0m   ▀▀▀▀▓               ▓▀▀▀▀  \u001B[32m" + green[1] + "  \u001B[31m " + red[1] + "\u001B[0m   ▀▀▀▀▓               ▓▀▀▀▀   \u001B[32m" + green[2] + "   \u001B[31m" + red[2] + " \u001B[0m  ▀▀▀▀▓                         ▓  1 - " + name[0] + " - COST: " + cost[0] + "  ▓\n" +
                    " ▓▀   \u001B[33m" + yellow[0] + "\u001B[0m     \u001B[95m" + pink[0] + "\u001B[0m     \u001B[96m" + blue[0] + "\u001B[0m   ▀▓           ▓▀   \u001B[33m" + yellow[1] + "\u001B[0m     \u001B[95m" + pink[1] + "\u001B[0m     \u001B[96m" + blue[1] + "\u001B[0m    ▀▓           ▓▀   \u001B[33m" + yellow[2] + " \u001B[0m     \u001B[95m" + pink[2] + " \u001B[0m    \u001B[96m" + blue[2] + " \u001B[0m   ▀▓\n" +
                    "  ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀             ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀             ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀                        ▓  2 - " + name[1] + " - COST: " + cost[1] + "  ▓\n" +
                    "\n" +
                    "                                                                                                                      ▓  3 - " + name[2] + " - COST: " + cost[2] + "  ▓");

        } else if (players.size() == 4 && !expertMode) {

            int[] red = new int[4];
            int[] blue = new int[4];
            int[] green = new int[4];
            int[] pink = new int[4];
            int[] yellow = new int[4];

            for (int i = 0; i < 4; i++) {
                red[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.RED).count();
                blue[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.BLUE).count();
                green[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.GREEN).count();
                pink[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.PINK).count();
                yellow[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.YELLOW).count();
            }


            System.out.println("\n" +
                    "        ▄▄▄▄▄▄▄                            ▄▄▄▄▄▄▄                            ▄▄▄▄▄▄▄                            ▄▄▄▄▄▄▄                           \n" +
                    "      ▓▀   1   ▀▓                        ▓▀   2   ▀▓                        ▓▀   3   ▀▓                        ▓▀   4   ▀▓\n" +
                    "  ▓▀▀▀▀  \u001B[32m" + green[0] + "  \u001B[31m " + red[0] + "\u001B[0m   ▀▀▀▀▓               ▓▀▀▀▀  \u001B[32m" + green[1] + "  \u001B[31m " + red[1] + "\u001B[0m   ▀▀▀▀▓               ▓▀▀▀▀   \u001B[32m" + green[2] + "   \u001B[31m" + red[2] + " \u001B[0m ▀▀▀▀▓               ▓▀▀▀▀  \u001B[32m" + green[3] + "   \u001B[31m" + red[3] + " \u001B[0m  ▀▀▀▀▓                     \n" +
                    " ▓▀   \u001B[33m" + yellow[0] + "\u001B[0m     \u001B[95m" + pink[0] + "\u001B[0m     \u001B[96m" + blue[0] + "\u001B[0m   ▀▓           ▓▀   \u001B[33m" + yellow[1] + "\u001B[0m     \u001B[95m" + pink[1] + "\u001B[0m     \u001B[96m" + blue[1] + "\u001B[0m    ▀▓           ▓▀   \u001B[33m" + yellow[2] + " \u001B[0m     \u001B[95m" + pink[2] + " \u001B[0m    \u001B[96m" + blue[2] + " \u001B[0m  ▀▓           ▓▀   \u001B[33m" + yellow[3] + " \u001B[0m    \u001B[95m" + pink[3] + " \u001B[0m    \u001B[96m" + blue[3] + " \u001B[0m   ▀▓\n" +
                    "  ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀             ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀             ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀             ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\n" +
                    "\n" +
                    "                                                                                                                  ");

        } else {

            int[] red = new int[4];
            int[] blue = new int[4];
            int[] green = new int[4];
            int[] pink = new int[4];
            int[] yellow = new int[4];
            String[] name = new String[3];
            int[] cost = new int[3];

            for (int i = 0; i < 4; i++) {
                red[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.RED).count();
                blue[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.BLUE).count();
                green[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.GREEN).count();
                pink[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.PINK).count();
                yellow[i] = (int) clouds.get(i).getStudents().stream().filter(x -> x == Student.YELLOW).count();
            }

            for (int i = 0; i < 3; i++) {
                cost[i] = characters[i].getCost();

                name[i] = getCharacterName(i);
            }


            System.out.println("\n" +
                    "        ▄▄▄▄▄▄▄                            ▄▄▄▄▄▄▄                            ▄▄▄▄▄▄▄                            ▄▄▄▄▄▄▄                                ▓            CHARACTERS:           ▓\n" +
                    "      ▓▀   1   ▀▓                        ▓▀   2   ▀▓                        ▓▀   3   ▀▓                        ▓▀   4   ▀▓\n" +
                    "  ▓▀▀▀▀  \u001B[32m" + green[0] + "  \u001B[31m " + red[0] + "\u001B[0m   ▀▀▀▀▓               ▓▀▀▀▀  \u001B[32m" + green[1] + "  \u001B[31m " + red[1] + "\u001B[0m   ▀▀▀▀▓               ▓▀▀▀▀   \u001B[32m" + green[2] + "   \u001B[31m" + red[2] + " \u001B[0m ▀▀▀▀▓               ▓▀▀▀▀  \u001B[32m" + green[3] + "   \u001B[31m" + red[3] + " \u001B[0m  ▀▀▀▀▓                         ▓  1 - " + name[0] + " - COST: " + cost[0] + "  ▓\n" +
                    " ▓▀   \u001B[33m" + yellow[0] + "\u001B[0m     \u001B[95m" + pink[0] + "\u001B[0m     \u001B[96m" + blue[0] + "\u001B[0m   ▀▓           ▓▀   \u001B[33m" + yellow[1] + "\u001B[0m     \u001B[95m" + pink[1] + "\u001B[0m     \u001B[96m" + blue[1] + "\u001B[0m    ▀▓           ▓▀   \u001B[33m" + yellow[2] + " \u001B[0m     \u001B[95m" + pink[2] + " \u001B[0m    \u001B[96m" + blue[2] + " \u001B[0m  ▀▓           ▓▀   \u001B[33m" + yellow[3] + " \u001B[0m    \u001B[95m" + pink[3] + " \u001B[0m    \u001B[96m" + blue[3] + " \u001B[0m   ▀▓\n" +
                    "  ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀             ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀             ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀             ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀                        ▓  2 - " + name[1] + " - COST: " + cost[1] + "  ▓\n" +
                    "\n" +
                    "                                                                                                                                                        ▓  3 - " + name[2] + " - COST: " + cost[2] + "  ▓                  ");


        }
    }

    /**
     * @param i is the index of the character whose name is returned
     * @return the name of a character
     */
    private String getCharacterName(int i) {

        String name = null;

        switch (characters[i].getId()) {
            case 1:
                name = "      MONK      ";
                break;
            case 2:
                name = "     FARMER     ";
                break;
            case 3:
                name = "     HERALD     ";
                break;
            case 4:
                name = " MAGIC POSTMAN  ";
                break;
            case 5:
                name = "  HERBS GRANNY  ";
                break;
            case 6:
                name = "    CENTAUR     ";
                break;
            case 7:
                name = "     JESTER     ";
                break;
            case 8:
                name = "     KNIGHT     ";
                break;
            case 9:
                name = "MUSHROOM HUNTER ";
                break;
            case 10:
                name = "    MINSTREL    ";
                break;
            case 11:
                name = "SPOILED PRINCESS";
                break;
            case 12:
                name = "     THIEF      ";
                break;
        }

        return name;
    }

    /**
     * This method prints all the players' boards
     */
    private void printBoards() {

        if (players.size() == 2) {

            String coins1 = null;
            String coins2 = null;
            String upperText1;
            String upperText2;
            String[] e = new String[7];
            String[] red1 = new String[10];
            String[] blue1 = new String[10];
            String[] green1 = new String[10];
            String[] pink1 = new String[10];
            String[] yellow1 = new String[10];
            String[] prof1 = new String[5]; // green, red, yellow, pink, blue
            String[] prof2 = new String[5];
            int[] count = new int[5];
            String[] e2 = new String[5];
            String[] dr2 = new String[5];
            Board b1;
            Board b2;
            String t1 = null;
            String t2 = null;
            String colort1;
            String colort2;

            if (expertMode) {
                for (Player p : players) {
                    if (p.getNickname().equals(nickname)) {
                        if (p.getCoins() < 10)
                            coins1 = p.getCoins() + " ";
                        else
                            coins1 = Integer.toString(p.getCoins());
                    } else {
                        if (p.getCoins() < 10)
                            coins2 = p.getCoins() + " ";
                        else
                            coins2 = Integer.toString(p.getCoins());
                    }
                }
            }

            if (expertMode) {
                upperText1 = " YOUR BOARD                                                                        COINS: " + coins1;
                upperText2 = "OPPONENT'S BOARD                      COINS: " + coins2;
            } else {
                upperText1 = "YOUR BOARD ";
                upperText2 = "OPPONENT'S BOARD ";
            }

            if (players.get(0).getNickname().equals(nickname)) {
                b1 = players.get(0).getBoard();
                b2 = players.get(1).getBoard();
            } else {
                b1 = players.get(1).getBoard();
                b2 = players.get(0).getBoard();
            }

            for (int i = 0; i < 7; i++) {
                if (b1.getEntrance()[i] != null)
                    e[i] = getCircleColor(b1.getEntrance()[i].name());
                else
                    e[i] = "\u2b24";
            }

            for (int i = 0; i < 10; i++) {
                if (b1.getNumberOfStudents(Student.GREEN) >= (i + 1))
                    green1[i] = getCircleColor("GREEN");
                else
                    green1[i] = "\u2b24";
                if (b1.getNumberOfStudents(Student.RED) >= (i + 1))
                    red1[i] = getCircleColor("RED");
                else
                    red1[i] = "\u2b24";
                if (b1.getNumberOfStudents(Student.YELLOW) >= (i + 1))
                    yellow1[i] = getCircleColor("YELLOW");
                else
                    yellow1[i] = "\u2b24";
                if (b1.getNumberOfStudents(Student.PINK) >= (i + 1))
                    pink1[i] = getCircleColor("PINK");
                else
                    pink1[i] = "\u2b24";
                if (b1.getNumberOfStudents(Student.BLUE) >= (i + 1))
                    blue1[i] = getCircleColor("BLUE");
                else
                    blue1[i] = "\u2b24";
            }

            if (b1.isProfessorPresent(Student.GREEN))
                prof1[0] = getCircleColor("GREEN");
            else
                prof1[0] = "\u2b24";
            if (b1.isProfessorPresent(Student.RED))
                prof1[1] = getCircleColor("RED");
            else
                prof1[1] = "\u2b24";
            if (b1.isProfessorPresent(Student.YELLOW))
                prof1[2] = getCircleColor("YELLOW");
            else
                prof1[2] = "\u2b24";
            if (b1.isProfessorPresent(Student.PINK))
                prof1[3] = getCircleColor("PINK");
            else
                prof1[3] = "\u2b24";
            if (b1.isProfessorPresent(Student.BLUE))
                prof1[4] = getCircleColor("BLUE");
            else
                prof1[4] = "\u2b24";

            if (b2.isProfessorPresent(Student.GREEN))
                prof2[0] = getCircleColor("GREEN");
            else
                prof2[0] = "\u2b24";
            if (b2.isProfessorPresent(Student.RED))
                prof2[1] = getCircleColor("RED");
            else
                prof2[1] = "\u2b24";
            if (b2.isProfessorPresent(Student.YELLOW))
                prof2[2] = getCircleColor("YELLOW");
            else
                prof2[2] = "\u2b24";
            if (b2.isProfessorPresent(Student.PINK))
                prof2[3] = getCircleColor("PINK");
            else
                prof2[3] = "\u2b24";
            if (b2.isProfessorPresent(Student.BLUE))
                prof2[4] = getCircleColor("BLUE");
            else
                prof2[4] = "\u2b24";

            for (Player p : players) {
                if (p.getNickname().equals(nickname))
                    t1 = Integer.toString(p.getBoard().getNumberTowers());
                else
                    t2 = Integer.toString(p.getBoard().getNumberTowers());
            }

            for (int i = 0; i < 5; i++)
                count[i] = 0;

            for (int i = 0; i < 7; i++) {
                if (b2.getEntrance()[i] != null) {
                    switch (b2.getEntrance()[i].name()) {
                        case "GREEN":
                            count[0]++;
                            break;
                        case "RED":
                            count[1]++;
                            break;
                        case "YELLOW":
                            count[2]++;
                            break;
                        case "PINK":
                            count[3]++;
                            break;
                        case "BLUE":
                            count[4]++;
                            break;
                    }
                }
            }

            for (int i = 0; i < 5; i++)
                e2[i] = Integer.toString(count[i]);


            if (b2.getNumberOfStudents(Student.GREEN) < 10)
                dr2[0] = " " + b2.getNumberOfStudents(Student.GREEN);
            else
                dr2[0] = Integer.toString(b2.getNumberOfStudents(Student.GREEN));
            if (b2.getNumberOfStudents(Student.RED) < 10)
                dr2[1] = " " + b2.getNumberOfStudents(Student.RED);
            else
                dr2[1] = Integer.toString(b2.getNumberOfStudents(Student.RED));
            if (b2.getNumberOfStudents(Student.YELLOW) < 10)
                dr2[2] = " " + b2.getNumberOfStudents(Student.YELLOW);
            else
                dr2[2] = Integer.toString(b2.getNumberOfStudents(Student.YELLOW));
            if (b2.getNumberOfStudents(Student.PINK) < 10)
                dr2[3] = " " + b2.getNumberOfStudents(Student.PINK);
            else
                dr2[3] = Integer.toString(b2.getNumberOfStudents(Student.PINK));
            if (b2.getNumberOfStudents(Student.BLUE) < 10)
                dr2[4] = " " + b2.getNumberOfStudents(Student.BLUE);
            else
                dr2[4] = Integer.toString(b2.getNumberOfStudents(Student.BLUE));

            colort1 = b1.getTowerColor().name();
            if (colort1.equals("GREY"))
                colort1 = "      " + colort1 + "       ";
            else
                colort1 = "      " + colort1 + "      ";

            colort2 = b2.getTowerColor().name();
            if (colort2.equals("GREY"))
                colort2 = " " + colort2;


            System.out.println("\n" +
                    upperText1 + "\n" +
                    "▓▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▓▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▓▀▀▀▀▀▀▀▓▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▓                       " + upperText2 + " \n" +
                    "▓               ▓     " + green1[0] + "   " + green1[1] + "   " + green1[2] + "   " + green1[3] + "   " + green1[4] + "   " + green1[5] + "   " + green1[6] + "   " + green1[7] + "   " + green1[8] + "   " + green1[9] + "     ▓   " + prof1[0] + "   ▓                 ▓                      ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n" +
                    "▓    " + e[0] + "     " + e[1] + "    ▓                                               ▓       ▓    AVAILABLE    ▓                                   ENTRANCE:  \u001B[32m" + e2[0] + "  \u001B[31m" + e2[1] + "\u001B[0m  \u001B[33m" + e2[2] + "\u001B[0m  \u001B[95m" + e2[3] + "\u001B[0m  \u001B[96m" + e2[4] + "\u001B[0m     \n" +
                    "▓               ▓     " + red1[0] + "   " + red1[1] + "   " + red1[2] + "   " + red1[3] + "   " + red1[4] + "   " + red1[5] + "   " + red1[6] + "   " + red1[7] + "   " + red1[8] + "   " + red1[9] + "     ▓   " + prof1[1] + "   ▓     TOWERS:     ▓                      ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n" +
                    "▓    " + e[2] + "     " + e[3] + "    ▓                                               ▓       ▓                 ▓                       DINING ROOM:  \u001B[32m" + dr2[0] + "/10\u001B[0m  \u001B[31m" + dr2[1] + "/10\u001B[0m  \u001B[33m" + dr2[2] + "/10\u001B[0m  \u001B[95m" + dr2[3] + "/10\u001B[0m  \u001B[96m" + dr2[4] + "/10\u001B[0m     \n" +
                    "▓               ▓     " + yellow1[0] + "   " + yellow1[1] + "   " + yellow1[2] + "   " + yellow1[3] + "   " + yellow1[4] + "   " + yellow1[5] + "   " + yellow1[6] + "   " + yellow1[7] + "   " + yellow1[8] + "   " + yellow1[9] + "     ▓   " + prof1[2] + "   ▓        " + t1 + "        ▓                      ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n" +
                    "▓    " + e[4] + "     " + e[5] + "    ▓                                               ▓       ▓                 ▓                               PROFESSORS:   " + prof2[0] + "   " + prof2[1] + "   " + prof2[2] + "   " + prof2[3] + "   " + prof2[4] + "     \n" +
                    "▓               ▓     " + pink1[0] + "   " + pink1[1] + "   " + pink1[2] + "   " + pink1[3] + "   " + pink1[4] + "   " + pink1[5] + "   " + pink1[6] + "   " + pink1[7] + "   " + pink1[8] + "   " + pink1[9] + "     ▓   " + prof1[3] + "   ▓                 ▓                      ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n" +
                    "▓    " + e[6] + "          ▓                                               ▓       ▓" + colort1 + "▓                                       " + colort2 + " TOWERS: " + t2 + "  \n" +
                    "▓               ▓     " + blue1[0] + "   " + blue1[1] + "   " + blue1[2] + "   " + blue1[3] + "   " + blue1[4] + "   " + blue1[5] + "   " + blue1[6] + "   " + blue1[7] + "   " + blue1[8] + "   " + blue1[9] + "     ▓   " + prof1[4] + "   ▓                 ▓                      ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n" +
                    "▓▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▓▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▓▄▄▄▄▄▄▄▓▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▓\n" +
                    "");

        } else if (players.size() == 3) {

            String coins1 = null;
            String coins2 = null;
            String coins3 = null;
            String upperText1;
            String upperText2;
            String upperText3;
            String[] e = new String[9];
            String[] red1 = new String[10];
            String[] blue1 = new String[10];
            String[] green1 = new String[10];
            String[] pink1 = new String[10];
            String[] yellow1 = new String[10];
            String[] prof1 = new String[5]; // green, red, yellow, pink, blue
            String[] prof2 = new String[5];
            String[] prof3 = new String[5];
            int[] count2 = new int[5];
            int[] count3 = new int[5];
            String[] e2 = new String[5];
            String[] e3 = new String[5];
            String[] dr2 = new String[5];
            String[] dr3 = new String[5];
            Board b1;
            Board b2;
            Board b3;
            String t1 = null;
            String t2 = null;
            String t3 = null;
            String colort1;
            char colort2;
            char colort3;

            if (expertMode) {
                for (Player p : players) {
                    if (p.getNickname().equals(nickname)) {
                        if (p.getCoins() < 10)
                            coins1 = p.getCoins() + " ";
                        else
                            coins1 = Integer.toString(p.getCoins());
                    } else if (coins2 == null) {
                        if (p.getCoins() < 10)
                            coins2 = p.getCoins() + " ";
                        else
                            coins2 = Integer.toString(p.getCoins());
                    } else {
                        if (p.getCoins() < 10)
                            coins3 = p.getCoins() + " ";
                        else
                            coins3 = Integer.toString(p.getCoins());
                    }
                }
            }

            if (expertMode) {
                upperText1 = " YOUR BOARD                                                                        COINS: " + coins1;
                upperText2 = "PLAYER 2                COINS: " + coins2;
                upperText3 = "PLAYER 3                COINS: " + coins3;
            } else {
                upperText1 = "YOUR BOARD ";
                upperText2 = "PLAYER 2                         ";
                upperText3 = "PLAYER 3";
            }

            if (players.get(0).getNickname().equals(nickname)) {
                b1 = players.get(0).getBoard();
                b2 = players.get(1).getBoard();
                b3 = players.get(2).getBoard();
            } else if (players.get(1).getNickname().equals(nickname)) {
                b1 = players.get(1).getBoard();
                b2 = players.get(0).getBoard();
                b3 = players.get(2).getBoard();
            } else {
                b1 = players.get(2).getBoard();
                b2 = players.get(0).getBoard();
                b3 = players.get(1).getBoard();
            }

            for (int i = 0; i < 9; i++) {
                if (b1.getEntrance()[i] != null)
                    e[i] = getCircleColor(b1.getEntrance()[i].name());
                else
                    e[i] = "\u2b24";
            }

            for (int i = 0; i < 10; i++) {
                if (b1.getNumberOfStudents(Student.GREEN) >= (i + 1))
                    green1[i] = getCircleColor("GREEN");
                else
                    green1[i] = "\u2b24";
                if (b1.getNumberOfStudents(Student.RED) >= (i + 1))
                    red1[i] = getCircleColor("RED");
                else
                    red1[i] = "\u2b24";
                if (b1.getNumberOfStudents(Student.YELLOW) >= (i + 1))
                    yellow1[i] = getCircleColor("YELLOW");
                else
                    yellow1[i] = "\u2b24";
                if (b1.getNumberOfStudents(Student.PINK) >= (i + 1))
                    pink1[i] = getCircleColor("PINK");
                else
                    pink1[i] = "\u2b24";
                if (b1.getNumberOfStudents(Student.BLUE) >= (i + 1))
                    blue1[i] = getCircleColor("BLUE");
                else
                    blue1[i] = "\u2b24";
            }

            if (b1.isProfessorPresent(Student.GREEN))
                prof1[0] = getCircleColor("GREEN");
            else
                prof1[0] = "\u2b24";
            if (b1.isProfessorPresent(Student.RED))
                prof1[1] = getCircleColor("RED");
            else
                prof1[1] = "\u2b24";
            if (b1.isProfessorPresent(Student.YELLOW))
                prof1[2] = getCircleColor("YELLOW");
            else
                prof1[2] = "\u2b24";
            if (b1.isProfessorPresent(Student.PINK))
                prof1[3] = getCircleColor("PINK");
            else
                prof1[3] = "\u2b24";
            if (b1.isProfessorPresent(Student.BLUE))
                prof1[4] = getCircleColor("BLUE");
            else
                prof1[4] = "\u2b24";

            if (b2.isProfessorPresent(Student.GREEN))
                prof2[0] = "1";
            else
                prof2[0] = "0";
            if (b2.isProfessorPresent(Student.RED))
                prof2[1] = "1";
            else
                prof2[1] = "0";
            if (b2.isProfessorPresent(Student.YELLOW))
                prof2[2] = "1";
            else
                prof2[2] = "0";
            if (b2.isProfessorPresent(Student.PINK))
                prof2[3] = "1";
            else
                prof2[3] = "0";
            if (b2.isProfessorPresent(Student.BLUE))
                prof2[4] = "1";
            else
                prof2[4] = "0";

            if (b3.isProfessorPresent(Student.GREEN))
                prof3[0] = "1";
            else
                prof3[0] = "0";
            if (b3.isProfessorPresent(Student.RED))
                prof3[1] = "1";
            else
                prof3[1] = "0";
            if (b3.isProfessorPresent(Student.YELLOW))
                prof3[2] = "1";
            else
                prof3[2] = "0";
            if (b3.isProfessorPresent(Student.PINK))
                prof3[3] = "1";
            else
                prof3[3] = "0";
            if (b3.isProfessorPresent(Student.BLUE))
                prof3[4] = "1";
            else
                prof3[4] = "0";

            for (Player p : players) {
                if (p.getNickname().equals(nickname))
                    t1 = Integer.toString(p.getBoard().getNumberTowers());
                else if (t2 == null)
                    t2 = Integer.toString(p.getBoard().getNumberTowers());
                else
                    t3 = Integer.toString(p.getBoard().getNumberTowers());
            }

            for (int i = 0; i < 5; i++) {
                count2[i] = 0;
                count3[i] = 0;
            }

            for (int i = 0; i < 9; i++) {
                if (b2.getEntrance()[i] != null) {
                    switch (b2.getEntrance()[i].name()) {
                        case "GREEN":
                            count2[0]++;
                            break;
                        case "RED":
                            count2[1]++;
                            break;
                        case "YELLOW":
                            count2[2]++;
                            break;
                        case "PINK":
                            count2[3]++;
                            break;
                        case "BLUE":
                            count2[4]++;
                            break;
                    }
                }
            }

            for (int i = 0; i < 9; i++) {
                if (b3.getEntrance()[i] != null) {
                    switch (b3.getEntrance()[i].name()) {
                        case "GREEN":
                            count3[0]++;
                            break;
                        case "RED":
                            count3[1]++;
                            break;
                        case "YELLOW":
                            count3[2]++;
                            break;
                        case "PINK":
                            count3[3]++;
                            break;
                        case "BLUE":
                            count3[4]++;
                            break;
                    }
                }
            }

            for (int i = 0; i < 5; i++) {
                e2[i] = Integer.toString(count2[i]);
                e3[i] = Integer.toString(count3[i]);
            }

            if (b2.getNumberOfStudents(Student.GREEN) < 10)
                dr2[0] = " " + b2.getNumberOfStudents(Student.GREEN);
            else
                dr2[0] = Integer.toString(b2.getNumberOfStudents(Student.GREEN));
            if (b2.getNumberOfStudents(Student.RED) < 10)
                dr2[1] = " " + b2.getNumberOfStudents(Student.RED);
            else
                dr2[1] = Integer.toString(b2.getNumberOfStudents(Student.RED));
            if (b2.getNumberOfStudents(Student.YELLOW) < 10)
                dr2[2] = " " + b2.getNumberOfStudents(Student.YELLOW);
            else
                dr2[2] = Integer.toString(b2.getNumberOfStudents(Student.YELLOW));
            if (b2.getNumberOfStudents(Student.PINK) < 10)
                dr2[3] = " " + b2.getNumberOfStudents(Student.PINK);
            else
                dr2[3] = Integer.toString(b2.getNumberOfStudents(Student.PINK));
            if (b2.getNumberOfStudents(Student.BLUE) < 10)
                dr2[4] = " " + b2.getNumberOfStudents(Student.BLUE);
            else
                dr2[4] = Integer.toString(b2.getNumberOfStudents(Student.BLUE));

            if (b3.getNumberOfStudents(Student.GREEN) < 10)
                dr3[0] = " " + b3.getNumberOfStudents(Student.GREEN);
            else
                dr3[0] = Integer.toString(b3.getNumberOfStudents(Student.GREEN));
            if (b3.getNumberOfStudents(Student.RED) < 10)
                dr3[1] = " " + b3.getNumberOfStudents(Student.RED);
            else
                dr3[1] = Integer.toString(b3.getNumberOfStudents(Student.RED));
            if (b3.getNumberOfStudents(Student.YELLOW) < 10)
                dr3[2] = " " + b3.getNumberOfStudents(Student.YELLOW);
            else
                dr3[2] = Integer.toString(b3.getNumberOfStudents(Student.YELLOW));
            if (b3.getNumberOfStudents(Student.PINK) < 10)
                dr3[3] = " " + b3.getNumberOfStudents(Student.PINK);
            else
                dr3[3] = Integer.toString(b3.getNumberOfStudents(Student.PINK));
            if (b3.getNumberOfStudents(Student.BLUE) < 10)
                dr3[4] = " " + b3.getNumberOfStudents(Student.BLUE);
            else
                dr3[4] = Integer.toString(b3.getNumberOfStudents(Student.BLUE));

            colort1 = b1.getTowerColor().name();
            if (colort1.equals("GREY"))
                colort1 = "      " + colort1 + "       ";
            else
                colort1 = "      " + colort1 + "      ";

            colort2 = b2.getTowerColor().name().charAt(0);
            colort3 = b3.getTowerColor().name().charAt(0);

            System.out.println("\n" +
                    upperText1 + "\n" +
                    "▓▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▓▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▓▀▀▀▀▀▀▀▓▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▓ \n" +
                    "▓    " + e[0] + "     " + e[1] + "    ▓     " + green1[0] + "   " + green1[1] + "   " + green1[2] + "   " + green1[3] + "   " + green1[4] + "   " + green1[5] + "   " + green1[6] + "   " + green1[7] + "   " + green1[8] + "   " + green1[9] + "     ▓   " + prof1[0] + "   ▓                 ▓ \n" +
                    "▓               ▓                                               ▓       ▓    AVAILABLE    ▓ \n" +
                    "▓    " + e[2] + "     " + e[3] + "    ▓     " + red1[0] + "   " + red1[1] + "   " + red1[2] + "   " + red1[3] + "   " + red1[4] + "   " + red1[5] + "   " + red1[6] + "   " + red1[7] + "   " + red1[8] + "   " + red1[9] + "     ▓   " + prof1[1] + "   ▓     TOWERS:     ▓ \n" +
                    "▓               ▓                                               ▓       ▓                 ▓ \n" +
                    "▓    " + e[4] + "     " + e[5] + "    ▓     " + yellow1[0] + "   " + yellow1[1] + "   " + yellow1[2] + "   " + yellow1[3] + "   " + yellow1[4] + "   " + yellow1[5] + "   " + yellow1[6] + "   " + yellow1[7] + "   " + yellow1[8] + "   " + yellow1[9] + "     ▓   " + prof1[2] + "   ▓        " + t1 + "        ▓ \n" +
                    "▓               ▓                                               ▓       ▓                 ▓ \n" +
                    "▓    " + e[6] + "     " + e[7] + "    ▓     " + pink1[0] + "   " + pink1[1] + "   " + pink1[2] + "   " + pink1[3] + "   " + pink1[4] + "   " + pink1[5] + "   " + pink1[6] + "   " + pink1[7] + "   " + pink1[8] + "   " + pink1[9] + "     ▓   " + prof1[3] + "   ▓                 ▓ \n" +
                    "▓               ▓                                               ▓       ▓" + colort1 + "▓ \n" +
                    "▓    " + e[8] + "          ▓     " + blue1[0] + "   " + blue1[1] + "   " + blue1[2] + "   " + blue1[3] + "   " + blue1[4] + "   " + blue1[5] + "   " + blue1[6] + "   " + blue1[7] + "   " + blue1[8] + "   " + blue1[9] + "     ▓   " + prof1[4] + "   ▓                 ▓ \n" +
                    "▓▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▓▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▓▄▄▄▄▄▄▄▓▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▓\n\n" +
                    "\n" +
                    upperText2 + "          " + upperText3 + "\n" +
                    "▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄          ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄          \n" +
                    "▓    ENTR:    ▓ DINING: ▓ PROF: ▓          ▓    ENTR:    ▓ DINING: ▓ PROF: ▓           \n" +
                    "▓   \u001B[32m" + e2[0] + "     \u001B[31m" + e2[1] + "\u001B[0m   ▓  \u001B[32m" + dr2[0] + "/10\u001B[0m  ▓   \u001B[32m" + prof2[0] + "\u001B[0m   ▓          ▓   \u001B[32m" + e3[0] + "     \u001B[31m" + e3[1] + "\u001B[0m   ▓  \u001B[32m" + dr3[0] + "/10\u001B[0m  ▓   \u001B[32m" + prof3[0] + "\u001B[0m   ▓        \n" +
                    "▓      \u001B[33m" + e2[2] + "\u001B[0m      ▓  \u001B[31m" + dr2[1] + "/10\u001B[0m  ▓   \u001B[31m" + prof2[1] + "\u001B[0m   ▓          ▓      \u001B[33m" + e3[2] + "\u001B[0m      ▓  \u001B[31m" + dr3[1] + "/10\u001B[0m  ▓   \u001B[31m" + prof3[1] + "\u001B[0m   ▓                         \n" +
                    "▓   \u001B[95m" + e2[3] + "\u001B[0m     \u001B[96m" + e2[4] + "\u001B[0m   ▓  \u001B[33m" + dr2[2] + "/10\u001B[0m  ▓   \u001B[33m" + prof2[2] + "\u001B[0m   ▓          ▓   \u001B[95m" + e3[3] + "\u001B[0m     \u001B[96m" + e3[4] + "\u001B[0m   ▓  \u001B[33m" + dr3[2] + "/10\u001B[0m  ▓   \u001B[33m" + prof3[2] + "\u001B[0m   ▓        \n" +
                    "▓  TOWERS(" + colort2 + "): ▓  \u001B[95m" + dr2[3] + "/10\u001B[0m  ▓   \u001B[95m" + prof2[3] + "\u001B[0m   ▓          ▓  TOWERS(" + colort3 + "): ▓  \u001B[95m" + dr3[3] + "/10\u001B[0m  ▓   \u001B[95m" + prof3[3] + "\u001B[0m   ▓              \n" +
                    "▓      " + t2 + "      ▓  \u001B[96m" + dr2[4] + "/10\u001B[0m  ▓   \u001B[96m" + prof2[4] + "\u001B[0m   ▓          ▓      " + t3 + "      ▓  \u001B[96m" + dr3[4] + "/10\u001B[0m  ▓   \u001B[96m" + prof3[4] + "\u001B[0m   ▓           \n" +
                    "▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀          ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀                                                                        " + "\n");
        } else {

            String coins1 = null;
            String coins2 = null;
            String coins3 = null;
            String coins4 = null;
            String upperText1;
            String upperText2;
            String upperText3;
            String upperText4;
            String[] e = new String[7];
            String[] e2 = new String[5];
            String[] e3 = new String[5];
            String[] e4 = new String[5];
            String[] red1 = new String[10];
            String[] blue1 = new String[10];
            String[] green1 = new String[10];
            String[] pink1 = new String[10];
            String[] yellow1 = new String[10];
            String[] prof1 = new String[5]; // green, red, yellow, pink, blue
            String[] prof2 = new String[5];
            String[] prof3 = new String[5];
            String[] prof4 = new String[5];
            int[] count = new int[5];
            int[] count3 = new int[5];
            int[] count4 = new int[5];
            String[] dr2 = new String[5];
            String[] dr3 = new String[5];
            String[] dr4 = new String[5];
            Board b1 = null;
            Board b2 = null;
            Board b3 = null;
            Board b4 = null;
            String t1 = null;
            String t2 = null;
            String t3 = null;
            String t4 = null;
            String colort1;
            String colort2;
            char colort3;
            char colort4;

            for (Team t : teams) {

                if (t.getPlayers()[0].getNickname().equals(nickname) || t.getPlayers()[1].getNickname().equals(nickname)) {

                    if (t.getPlayers()[0].getNickname().equals(nickname)) {

                        b1 = t.getPlayers()[0].getBoard();
                        t1 = Integer.toString(t.getPlayers()[0].getBoard().getNumberTowers());

                        b2 = t.getPlayers()[1].getBoard();
                        t2 = Integer.toString(t.getPlayers()[1].getBoard().getNumberTowers());

                        if (expertMode) {
                            if (t.getPlayers()[0].getCoins() < 10)
                                coins1 = t.getPlayers()[0].getCoins() + " ";
                            else
                                coins1 = Integer.toString(t.getPlayers()[0].getCoins());

                            if (t.getPlayers()[1].getCoins() < 10)
                                coins2 = t.getPlayers()[1].getCoins() + " ";
                            else
                                coins2 = Integer.toString(t.getPlayers()[1].getCoins());
                        }
                    } else {
                        b1 = t.getPlayers()[1].getBoard();
                        t1 = Integer.toString(t.getPlayers()[1].getBoard().getNumberTowers());

                        b2 = t.getPlayers()[0].getBoard();
                        t2 = Integer.toString(t.getPlayers()[0].getBoard().getNumberTowers());

                        if (expertMode) {
                            if (t.getPlayers()[1].getCoins() < 10)
                                coins1 = t.getPlayers()[1].getCoins() + " ";
                            else
                                coins1 = Integer.toString(t.getPlayers()[1].getCoins());

                            if (t.getPlayers()[0].getCoins() < 10)
                                coins2 = t.getPlayers()[0].getCoins() + " ";
                            else
                                coins2 = Integer.toString(t.getPlayers()[0].getCoins());
                        }
                    }

                } else {
                    b3 = t.getPlayers()[0].getBoard();
                    t3 = Integer.toString(t.getPlayers()[0].getBoard().getNumberTowers());

                    b4 = t.getPlayers()[1].getBoard();
                    t4 = Integer.toString(t.getPlayers()[1].getBoard().getNumberTowers());

                    if (expertMode) {
                        if (t.getPlayers()[0].getCoins() < 10)
                            coins3 = t.getPlayers()[0].getCoins() + " ";
                        else
                            coins3 = Integer.toString(t.getPlayers()[0].getCoins());

                        if (t.getPlayers()[1].getCoins() < 10)
                            coins4 = t.getPlayers()[1].getCoins() + " ";
                        else
                            coins4 = Integer.toString(t.getPlayers()[1].getCoins());
                    }
                }
            }


            if (expertMode) {
                upperText1 = " YOUR BOARD                                                                        COINS: " + coins1;
                upperText2 = "TEAMMATE'S BOARD                      COINS: " + coins2;
                upperText3 = "PLAYER 3                COINS: " + coins3;
                upperText4 = "PLAYER 4                COINS: " + coins4;
            } else {
                upperText1 = "YOUR BOARD ";
                upperText2 = "TEAMMATE'S BOARD                 ";
                upperText3 = "PLAYER 3";
                upperText4 = "PLAYER 4";
            }

            for (int i = 0; i < 7; i++) {
                if (b1.getEntrance()[i] != null)
                    e[i] = getCircleColor(b1.getEntrance()[i].name());
                else
                    e[i] = "\u2b24";
            }

            for (int i = 0; i < 10; i++) {
                if (b1.getNumberOfStudents(Student.GREEN) >= (i + 1))
                    green1[i] = getCircleColor("GREEN");
                else
                    green1[i] = "\u2b24";
                if (b1.getNumberOfStudents(Student.RED) >= (i + 1))
                    red1[i] = getCircleColor("RED");
                else
                    red1[i] = "\u2b24";
                if (b1.getNumberOfStudents(Student.YELLOW) >= (i + 1))
                    yellow1[i] = getCircleColor("YELLOW");
                else
                    yellow1[i] = "\u2b24";
                if (b1.getNumberOfStudents(Student.PINK) >= (i + 1))
                    pink1[i] = getCircleColor("PINK");
                else
                    pink1[i] = "\u2b24";
                if (b1.getNumberOfStudents(Student.BLUE) >= (i + 1))
                    blue1[i] = getCircleColor("BLUE");
                else
                    blue1[i] = "\u2b24";
            }


            if (b1.isProfessorPresent(Student.GREEN))
                prof1[0] = getCircleColor("GREEN");
            else
                prof1[0] = "\u2b24";
            if (b1.isProfessorPresent(Student.RED))
                prof1[1] = getCircleColor("RED");
            else
                prof1[1] = "\u2b24";
            if (b1.isProfessorPresent(Student.YELLOW))
                prof1[2] = getCircleColor("YELLOW");
            else
                prof1[2] = "\u2b24";
            if (b1.isProfessorPresent(Student.PINK))
                prof1[3] = getCircleColor("PINK");
            else
                prof1[3] = "\u2b24";
            if (b1.isProfessorPresent(Student.BLUE))
                prof1[4] = getCircleColor("BLUE");
            else
                prof1[4] = "\u2b24";


            for (int i = 0; i < 5; i++)
                count[i] = 0;

            for (int i = 0; i < 7; i++) {
                if (b2.getEntrance()[i] != null) {
                    switch (b2.getEntrance()[i].name()) {
                        case "GREEN":
                            count[0]++;
                            break;
                        case "RED":
                            count[1]++;
                            break;
                        case "YELLOW":
                            count[2]++;
                            break;
                        case "PINK":
                            count[3]++;
                            break;
                        case "BLUE":
                            count[4]++;
                            break;
                    }
                }
            }

            for (int i = 0; i < 5; i++)
                e2[i] = Integer.toString(count[i]);


            if (b2.getNumberOfStudents(Student.GREEN) < 10)
                dr2[0] = " " + b2.getNumberOfStudents(Student.GREEN);
            else
                dr2[0] = Integer.toString(b2.getNumberOfStudents(Student.GREEN));
            if (b2.getNumberOfStudents(Student.RED) < 10)
                dr2[1] = " " + b2.getNumberOfStudents(Student.RED);
            else
                dr2[1] = Integer.toString(b2.getNumberOfStudents(Student.RED));
            if (b2.getNumberOfStudents(Student.YELLOW) < 10)
                dr2[2] = " " + b2.getNumberOfStudents(Student.YELLOW);
            else
                dr2[2] = Integer.toString(b2.getNumberOfStudents(Student.YELLOW));
            if (b2.getNumberOfStudents(Student.PINK) < 10)
                dr2[3] = " " + b2.getNumberOfStudents(Student.PINK);
            else
                dr2[3] = Integer.toString(b2.getNumberOfStudents(Student.PINK));
            if (b2.getNumberOfStudents(Student.BLUE) < 10)
                dr2[4] = " " + b2.getNumberOfStudents(Student.BLUE);
            else
                dr2[4] = Integer.toString(b2.getNumberOfStudents(Student.BLUE));


            if (b2.isProfessorPresent(Student.GREEN))
                prof2[0] = getCircleColor("GREEN");
            else
                prof2[0] = "\u2b24";
            if (b2.isProfessorPresent(Student.RED))
                prof2[1] = getCircleColor("RED");
            else
                prof2[1] = "\u2b24";
            if (b2.isProfessorPresent(Student.YELLOW))
                prof2[2] = getCircleColor("YELLOW");
            else
                prof2[2] = "\u2b24";
            if (b2.isProfessorPresent(Student.PINK))
                prof2[3] = getCircleColor("PINK");
            else
                prof2[3] = "\u2b24";
            if (b2.isProfessorPresent(Student.BLUE))
                prof2[4] = getCircleColor("BLUE");
            else
                prof2[4] = "\u2b24";


            for (int i = 0; i < 5; i++) {
                count3[i] = 0;
                count4[i] = 0;
            }

            for (int i = 0; i < 7; i++) {
                if (b3.getEntrance()[i] != null) {
                    switch (b3.getEntrance()[i].name()) {
                        case "GREEN":
                            count3[0]++;
                            break;
                        case "RED":
                            count3[1]++;
                            break;
                        case "YELLOW":
                            count3[2]++;
                            break;
                        case "PINK":
                            count3[3]++;
                            break;
                        case "BLUE":
                            count3[4]++;
                            break;
                    }
                }
            }

            for (int i = 0; i < 7; i++) {
                if (b4.getEntrance()[i] != null) {
                    switch (b4.getEntrance()[i].name()) {
                        case "GREEN":
                            count4[0]++;
                            break;
                        case "RED":
                            count4[1]++;
                            break;
                        case "YELLOW":
                            count4[2]++;
                            break;
                        case "PINK":
                            count4[3]++;
                            break;
                        case "BLUE":
                            count4[4]++;
                            break;
                    }
                }
            }

            for (int i = 0; i < 5; i++) {
                e3[i] = Integer.toString(count3[i]);
                e4[i] = Integer.toString(count4[i]);
            }

            if (b3.getNumberOfStudents(Student.GREEN) < 10)
                dr3[0] = " " + b3.getNumberOfStudents(Student.GREEN);
            else
                dr3[0] = Integer.toString(b3.getNumberOfStudents(Student.GREEN));
            if (b3.getNumberOfStudents(Student.RED) < 10)
                dr3[1] = " " + b3.getNumberOfStudents(Student.RED);
            else
                dr3[1] = Integer.toString(b3.getNumberOfStudents(Student.RED));
            if (b3.getNumberOfStudents(Student.YELLOW) < 10)
                dr3[2] = " " + b3.getNumberOfStudents(Student.YELLOW);
            else
                dr3[2] = Integer.toString(b3.getNumberOfStudents(Student.YELLOW));
            if (b3.getNumberOfStudents(Student.PINK) < 10)
                dr3[3] = " " + b3.getNumberOfStudents(Student.PINK);
            else
                dr3[3] = Integer.toString(b3.getNumberOfStudents(Student.PINK));
            if (b3.getNumberOfStudents(Student.BLUE) < 10)
                dr3[4] = " " + b3.getNumberOfStudents(Student.BLUE);
            else
                dr3[4] = Integer.toString(b3.getNumberOfStudents(Student.BLUE));

            if (b4.getNumberOfStudents(Student.GREEN) < 10)
                dr4[0] = " " + b4.getNumberOfStudents(Student.GREEN);
            else
                dr4[0] = Integer.toString(b4.getNumberOfStudents(Student.GREEN));
            if (b4.getNumberOfStudents(Student.RED) < 10)
                dr4[1] = " " + b4.getNumberOfStudents(Student.RED);
            else
                dr4[1] = Integer.toString(b4.getNumberOfStudents(Student.RED));
            if (b4.getNumberOfStudents(Student.YELLOW) < 10)
                dr4[2] = " " + b4.getNumberOfStudents(Student.YELLOW);
            else
                dr4[2] = Integer.toString(b4.getNumberOfStudents(Student.YELLOW));
            if (b4.getNumberOfStudents(Student.PINK) < 10)
                dr4[3] = " " + b4.getNumberOfStudents(Student.PINK);
            else
                dr4[3] = Integer.toString(b4.getNumberOfStudents(Student.PINK));
            if (b4.getNumberOfStudents(Student.BLUE) < 10)
                dr4[4] = " " + b4.getNumberOfStudents(Student.BLUE);
            else
                dr4[4] = Integer.toString(b4.getNumberOfStudents(Student.BLUE));


            if (b3.isProfessorPresent(Student.GREEN))
                prof3[0] = "1";
            else
                prof3[0] = "0";
            if (b3.isProfessorPresent(Student.RED))
                prof3[1] = "1";
            else
                prof3[1] = "0";
            if (b3.isProfessorPresent(Student.YELLOW))
                prof3[2] = "1";
            else
                prof3[2] = "0";
            if (b3.isProfessorPresent(Student.PINK))
                prof3[3] = "1";
            else
                prof3[3] = "0";
            if (b3.isProfessorPresent(Student.BLUE))
                prof3[4] = "1";
            else
                prof3[4] = "0";

            if (b4.isProfessorPresent(Student.GREEN))
                prof4[0] = "1";
            else
                prof4[0] = "0";
            if (b4.isProfessorPresent(Student.RED))
                prof4[1] = "1";
            else
                prof4[1] = "0";
            if (b4.isProfessorPresent(Student.YELLOW))
                prof4[2] = "1";
            else
                prof4[2] = "0";
            if (b4.isProfessorPresent(Student.PINK))
                prof4[3] = "1";
            else
                prof4[3] = "0";
            if (b4.isProfessorPresent(Student.BLUE))
                prof4[4] = "1";
            else
                prof4[4] = "0";


            colort1 = b1.getTowerColor().name();
            if (colort1.equals("GREY"))
                colort1 = "      " + colort1 + "       ";
            else
                colort1 = "      " + colort1 + "      ";

            colort2 = b2.getTowerColor().name();
            if (colort2.equals("GREY"))
                colort2 = " " + colort2;

            colort3 = b3.getTowerColor().name().charAt(0);
            colort4 = b4.getTowerColor().name().charAt(0);


            System.out.println("\n" +
                    upperText1 + "\n" +
                    "▓▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▓▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▓▀▀▀▀▀▀▀▓▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▓                       " + upperText2 + " \n" +
                    "▓               ▓     " + green1[0] + "   " + green1[1] + "   " + green1[2] + "   " + green1[3] + "   " + green1[4] + "   " + green1[5] + "   " + green1[6] + "   " + green1[7] + "   " + green1[8] + "   " + green1[9] + "     ▓   " + prof1[0] + "   ▓                 ▓                      ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n" +
                    "▓    " + e[0] + "     " + e[1] + "    ▓                                               ▓       ▓    AVAILABLE    ▓                                   ENTRANCE:  \u001B[32m" + e2[0] + "  \u001B[31m" + e2[1] + "\u001B[0m  \u001B[33m" + e2[2] + "\u001B[0m  \u001B[95m" + e2[3] + "\u001B[0m  \u001B[96m" + e2[4] + "\u001B[0m     \n" +
                    "▓               ▓     " + red1[0] + "   " + red1[1] + "   " + red1[2] + "   " + red1[3] + "   " + red1[4] + "   " + red1[5] + "   " + red1[6] + "   " + red1[7] + "   " + red1[8] + "   " + red1[9] + "     ▓   " + prof1[1] + "   ▓     TOWERS:     ▓                      ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n" +
                    "▓    " + e[2] + "     " + e[3] + "    ▓                                               ▓       ▓                 ▓                       DINING ROOM:  \u001B[32m" + dr2[0] + "/10\u001B[0m  \u001B[31m" + dr2[1] + "/10\u001B[0m  \u001B[33m" + dr2[2] + "/10\u001B[0m  \u001B[95m" + dr2[3] + "/10\u001B[0m  \u001B[96m" + dr2[4] + "/10\u001B[0m     \n" +
                    "▓               ▓     " + yellow1[0] + "   " + yellow1[1] + "   " + yellow1[2] + "   " + yellow1[3] + "   " + yellow1[4] + "   " + yellow1[5] + "   " + yellow1[6] + "   " + yellow1[7] + "   " + yellow1[8] + "   " + yellow1[9] + "     ▓   " + prof1[2] + "   ▓        " + t1 + "        ▓                      ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n" +
                    "▓    " + e[4] + "     " + e[5] + "    ▓                                               ▓       ▓                 ▓                               PROFESSORS:   " + prof2[0] + "   " + prof2[1] + "   " + prof2[2] + "   " + prof2[3] + "   " + prof2[4] + "     \n" +
                    "▓               ▓     " + pink1[0] + "   " + pink1[1] + "   " + pink1[2] + "   " + pink1[3] + "   " + pink1[4] + "   " + pink1[5] + "   " + pink1[6] + "   " + pink1[7] + "   " + pink1[8] + "   " + pink1[9] + "     ▓   " + prof1[3] + "   ▓                 ▓                      ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n" +
                    "▓    " + e[6] + "          ▓                                               ▓       ▓" + colort1 + "▓                                       " + colort2 + " TOWERS: " + t2 + "  \n" +
                    "▓               ▓     " + blue1[0] + "   " + blue1[1] + "   " + blue1[2] + "   " + blue1[3] + "   " + blue1[4] + "   " + blue1[5] + "   " + blue1[6] + "   " + blue1[7] + "   " + blue1[8] + "   " + blue1[9] + "     ▓   " + prof1[4] + "   ▓                 ▓                      ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n" +
                    "▓▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▓▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▓▄▄▄▄▄▄▄▓▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▓\n\n" +
                    "\n" +
                    upperText3 + "          " + upperText4 + "\n" +
                    "▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄          ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄          \n" +
                    "▓    ENTR:    ▓ DINING: ▓ PROF: ▓          ▓    ENTR:    ▓ DINING: ▓ PROF: ▓           \n" +
                    "▓   \u001B[32m" + e3[0] + "     \u001B[31m" + e3[1] + "\u001B[0m   ▓  \u001B[32m" + dr3[0] + "/10\u001B[0m  ▓   \u001B[32m" + prof3[0] + "\u001B[0m   ▓          ▓   \u001B[32m" + e4[0] + "     \u001B[31m" + e4[1] + "\u001B[0m   ▓  \u001B[32m" + dr4[0] + "/10\u001B[0m  ▓   \u001B[32m" + prof4[0] + "\u001B[0m   ▓        \n" +
                    "▓      \u001B[33m" + e3[2] + "\u001B[0m      ▓  \u001B[31m" + dr3[1] + "/10\u001B[0m  ▓   \u001B[31m" + prof3[1] + "\u001B[0m   ▓          ▓      \u001B[33m" + e4[2] + "\u001B[0m      ▓  \u001B[31m" + dr4[1] + "/10\u001B[0m  ▓   \u001B[31m" + prof4[1] + "\u001B[0m   ▓                         \n" +
                    "▓   \u001B[95m" + e3[3] + "\u001B[0m     \u001B[96m" + e3[4] + "\u001B[0m   ▓  \u001B[33m" + dr3[2] + "/10\u001B[0m  ▓   \u001B[33m" + prof3[2] + "\u001B[0m   ▓          ▓   \u001B[95m" + e4[3] + "\u001B[0m     \u001B[96m" + e4[4] + "\u001B[0m   ▓  \u001B[33m" + dr4[2] + "/10\u001B[0m  ▓   \u001B[33m" + prof4[2] + "\u001B[0m   ▓        \n" +
                    "▓  TOWERS(" + colort3 + "): ▓  \u001B[95m" + dr3[3] + "/10\u001B[0m  ▓   \u001B[95m" + prof3[3] + "\u001B[0m   ▓          ▓  TOWERS(" + colort4 + "): ▓  \u001B[95m" + dr4[3] + "/10\u001B[0m  ▓   \u001B[95m" + prof4[3] + "\u001B[0m   ▓              \n" +
                    "▓      " + t3 + "      ▓  \u001B[96m" + dr3[4] + "/10\u001B[0m  ▓   \u001B[96m" + prof3[4] + "\u001B[0m   ▓          ▓      " + t4 + "      ▓  \u001B[96m" + dr4[4] + "/10\u001B[0m  ▓   \u001B[96m" + prof4[4] + "\u001B[0m   ▓           \n" +
                    "▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀          ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀                                                                        " + "\n");

        }
    }

    /**
     * Used to color a circle representing a specific pawn
     *
     * @param color represents the color of a pawn
     * @return the colored circle
     */
    private String getCircleColor(String color) {

        String c = null;

        switch (color) {
            case "RED":
                c = "\u001B[31m" + "\u2b24" + "\u001B[0m";
                break;
            case "BLUE":
                c = "\u001B[96m" + "\u2b24" + "\u001B[0m";
                break;
            case "GREEN":
                c = "\u001B[32m" + "\u2b24" + "\u001B[0m";
                break;
            case "PINK":
                c = "\u001B[35m" + "\u2b24" + "\u001B[0m";
                break;
            case "YELLOW":
                c = "\u001B[33m" + "\u2b24" + "\u001B[0m";
                break;
        }

        return c;
    }

    /**
     * Prints all the elements of the game
     */
    private void printScreen() {
        clearScreen();
        printIslands();
        printCloudsAndCharacters();
        printBoards();
    }

    /**
     * Clears the user's console
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

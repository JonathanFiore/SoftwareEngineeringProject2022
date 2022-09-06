package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.protocol.*;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.characters.cards.Character;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class represents a client on the server, it contains the socket to send messages to the client,
 * It has a thread to send ping messages periodically
 * It has a thread to wait messages from client
 * It starts a thread which calls the controller to handle the message received
 * It's a Game's Observer
 * When the controller wants the Player to do something it calls one of the methods in View
 */
public class VirtualView implements View {
    private Observer gameController;
    private final Socket socketClient;
    private final ScheduledExecutorService pingService;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private boolean connection;
    private boolean joinedGame;
    private Thread readMessages;
    private final Object lockConnection;
    private final Object lockNetworkDown;
    private final Object lockJoinedGame;
    private boolean networkDownByClient;

    public VirtualView(Socket socketClient, ObjectInputStream in, ObjectOutputStream out) {
        this.socketClient = socketClient;
        this.in = in;
        this.out = out;
        pingService = Executors.newSingleThreadScheduledExecutor();
        connection = true;
        lockConnection = new Object();
        lockNetworkDown = new Object();
        lockJoinedGame = new Object();
        networkDownByClient = false;
        joinedGame = false;
        enablePing();
        enableReadMessages();
    }

    /**
     * This method enables Ping messages, periodically we send Ping Messages to check the connection: TimeOut/2 = 10s
     */
    void enablePing() {
        pingService.scheduleAtFixedRate(() -> sendMessage(new PingMessage()), 0, 10000, TimeUnit.MILLISECONDS);
    }

    /**
     * This method is called to start a thread which reads messages from socket
     */
    public void enableReadMessages() {
        readMessages = new Thread(() -> {
            while (getConnection()) {
                try {
                    Message m = (Message) in.readObject();
                    if (m.getMessageType() != MessageLabel.PING) {
                        //Notify GameController with a thread
                        notifyGameController(m);
                    }
                } catch (IOException e) {
                    setNetworkDownByClient();
                    closeConnection();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            //Connection Closed
        });
        readMessages.start();
    }

    @Override
    public void chooseCard(ArrayList<Assistant> availableCards) {
        ChooseCardMessage message = new ChooseCardMessage(availableCards);
        sendMessage(message);
    }

    @Override
    public void errorCard(String error, ArrayList<Assistant> availableCards) {
        ErrorCardMessage message = new ErrorCardMessage(error, availableCards);
        sendMessage(message);
    }

    @Override
    public void isYourTurn() {
        IsYourTurnMessage message = new IsYourTurnMessage();
        sendMessage(message);
    }

    @Override
    public void waitTurn(String currentPlayer) {
        WaitYourTurnMessage message = new WaitYourTurnMessage(currentPlayer);
        sendMessage(message);
    }

    @Override
    public void errorStudents(String error) {
        GenericErrorMessage message = new GenericErrorMessage(MessageLabel.ERROR_STUDENTS, error);
        sendMessage(message);
    }

    @Override
    public void moveMotherNature(int maxSteps) {
        MoveMotherNatureMessage message = new MoveMotherNatureMessage(maxSteps);
        sendMessage(message);
    }

    @Override
    public void errorMotherNature(String error, int maxSteps) {
        ErrorMotherNatureMessage message = new ErrorMotherNatureMessage(error, maxSteps);
        sendMessage(message);
    }

    @Override
    public void chooseCloud(ArrayList<Integer> availableClouds) {
        ChooseCloudMessage message = new ChooseCloudMessage(availableClouds);
        sendMessage(message);
    }

    @Override
    public void errorCloud(String error, ArrayList<Integer> availableClouds) {
        ErrorCloudMessage message = new ErrorCloudMessage(error, availableClouds);
        sendMessage(message);
    }

    @Override
    public void errorCharacter(String error) {
        GenericErrorMessage message = new GenericErrorMessage(MessageLabel.ERROR_CHARACTER, error);
        sendMessage(message);
    }

    @Override
    public void chooseTowerColor(ArrayList<Tower> availableColors) {
        ChooseTowerColorMessage message = new ChooseTowerColorMessage(availableColors);
        sendMessage(message);
    }

    @Override
    public void errorTowerColor(String error, ArrayList<Tower> availableColors) {
        ErrorTowerColorMessage message = new ErrorTowerColorMessage(error, availableColors);
        sendMessage(message);
    }

    @Override
    public void chooseWizard(ArrayList<Wizard> availableWizards) {
        ChooseWizardMessage message = new ChooseWizardMessage(availableWizards);
        sendMessage(message);
    }

    @Override
    public void errorWizard(String error, ArrayList<Wizard> availableWizards) {
        ErrorWizardMessage message = new ErrorWizardMessage(error, availableWizards);
        sendMessage(message);
    }

    @Override
    public void waitingPlayers(int numCurrentPlayers) {
        WaitingOtherPlayersMessage message = new WaitingOtherPlayersMessage(numCurrentPlayers);
        sendMessage(message);
    }

    @Override
    public void chooseGameSettings() {
        ChooseGameSettingsMessage message = new ChooseGameSettingsMessage();
        sendMessage(message);
    }

    @Override
    public void startGame(ArrayList<Player> players, Team[] teams, ArrayList<Cloud> clouds, ArrayList<Archipelago> archipelagos, Character[] characters, boolean expertMode) {
        StartGameMessage message = new StartGameMessage(players, teams, clouds, archipelagos, characters, expertMode);
        sendMessage(message);
    }

    @Override
    public void joinGameError(String s) {
        GenericErrorMessage message = new GenericErrorMessage(MessageLabel.ERROR_JOIN_GAME, s);
        sendMessage(message);
    }

    @Override
    public void endGame(String winner) {
        EndGameMessage message = new EndGameMessage(winner);
        sendMessage(message);
    }

    @Override
    public void playersUpdate(ArrayList<Player> updatedPlayers) {
        PlayersUpdateMessage message = new PlayersUpdateMessage(updatedPlayers);
        sendMessage(message);
    }

    @Override
    public void islandsUpdate(ArrayList<Archipelago> updatedArchipelagos) {
        IslandsUpdateMessage message = new IslandsUpdateMessage(updatedArchipelagos);
        sendMessage(message);
    }

    @Override
    public void cloudUpdate(ArrayList<Cloud> clouds) {
        CloudUpdateMessage message = new CloudUpdateMessage(clouds);
        sendMessage(message);
    }

    @Override
    public void characterUpdate(Character updatedCharacter, int index) {
        CharacterUpdateMessage message = new CharacterUpdateMessage(updatedCharacter, index);
        sendMessage(message);
    }


    /**
     * Send a message to the client, it has to be synchronized because there is another thread which periodically
     * sends ping messages
     *
     * @param message is the specific message we want to send
     */
    public synchronized void sendMessage(Message message) {
        if (getConnection()) {
            try {
                out.reset();
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                setNetworkDownByClient();
                closeConnection();
            }
        }
    }

    /**
     * It's used to get the status of connection
     */
    public boolean getConnection() {
        synchronized (lockConnection) {
            return connection;
        }
    }

    /**
     * It's used to close the connection
     */
    public void closeConnection() {
        synchronized (lockConnection) {
            connection = false;
            pingService.shutdown();
            if (isNetworkDownByClient() && isJoinedGame()) {
                //Need to notify GameController
                Thread t = new Thread(() -> ((GameController) gameController).playerDisconnectedOrEndGame());
                t.start();
            }
        }
    }


    /**
     * @return true if this player has already joined the game
     */
    public boolean isJoinedGame() {
        synchronized (lockJoinedGame) {
            return joinedGame;
        }
    }

    /**
     * @param joinedGame is true when the player can make the decisions for the game
     */
    public void setJoinedGame(boolean joinedGame) {
        synchronized (lockJoinedGame) {
            this.joinedGame = joinedGame;
        }
    }

    /**
     * This method is called when client disconnected
     */
    public void setNetworkDownByClient() {
        synchronized (lockNetworkDown) {
            this.networkDownByClient = true;
        }
    }

    /**
     * This method is used by GameController to check if player has already disconnected
     *
     * @return true if player disconnected
     */
    public boolean isNetworkDownByClient() {
        synchronized (lockNetworkDown) {
            return networkDownByClient;
        }
    }

    /**
     * This method is used to create a thread which notifies GameController and it's called by the thread which read
     * messages from client
     *
     * @param message is sent to GameController
     */
    private void notifyGameController(Message message) {
        Thread t = new Thread(() -> gameController.update(message));
        t.start();
    }

    //--------------------------ADD_OBSERVER--------------------------

    @Override
    public void addObserver(Observer gameController) {
        this.gameController = gameController;
    }
}

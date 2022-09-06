package it.polimi.ingsw.client;

import it.polimi.ingsw.protocol.*;
import it.polimi.ingsw.server.controller.Observer;
import it.polimi.ingsw.server.controller.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * This class manage the connection with the server
 * it observes the View, and it's observable by the View
 */
public class NetworkHandler implements Observer {

    private final View view;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final ScheduledExecutorService pingService;
    private final ExecutorService operations;
    private boolean connection;
    private boolean networkDownByClient;
    private boolean endGameReceived;
    private final Object lockConnection;
    private final Object lockManageMessage;
    private final Object lockNetworkDown;
    private final Object lockEndGameMessage;
    private EndGameMessage endGameMessage;
    private Thread readMessages;
    Socket socket;

    public NetworkHandler(View view, String ip, int port, JoinGameMessage message) throws IOException {
        this.view = view;
        socket = new Socket(ip, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        socket.setSoTimeout(20000);
        connection = true;
        networkDownByClient = false;
        endGameReceived = false;
        endGameMessage = null;
        lockConnection = new Object();
        lockManageMessage = new Object();
        lockNetworkDown = new Object();
        lockEndGameMessage = new Object();
        pingService = Executors.newSingleThreadScheduledExecutor();
        operations = Executors.newSingleThreadExecutor();
        sendMessage(message);
        enablePing();
        enableReadMessages();
    }

    @Override
    public void update(Message message) {
        sendMessage(message);
    }

    /**
     * This method enables Ping messages, periodically we send Ping Messages to check the connection: TimeOut/2 = 10s
     */
    private void enablePing() {
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
                        //Notify View
                        if (m.getMessageType() == MessageLabel.END_GAME) {
                            setEndGameMessage((EndGameMessage) m);
                        }
                        try {
                            operations.submit(() -> manageMessage(m));
                        } catch (RejectedExecutionException e) {
                            //We don't need to send this message
                        }
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

    /**
     * Send a message to the server, it has to be synchronized because there is another thread which periodically
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
     * It's called by a thread which is created when we receive a message
     *
     * @param message is the message we received from server
     */
    private void manageMessage(Message message) {
        synchronized (lockManageMessage) {
            switch (message.getMessageType()) {

                case CHOOSE_GAME_SETTINGS:
                    view.chooseGameSettings();
                    break;
                case ERROR_JOIN_GAME:
                    view.joinGameError(((GenericErrorMessage) message).getError());
                    break;
                case CHOOSE_TOWER_COLOR:
                    view.chooseTowerColor(((ChooseTowerColorMessage) message).getAvailableColors());
                    break;
                case ERROR_TOWER_COLOR:
                    view.errorTowerColor(((ErrorTowerColorMessage) message).getError(), ((ErrorTowerColorMessage) message).getAvailableColors());
                    break;
                case CHOOSE_WIZARD:
                    view.chooseWizard(((ChooseWizardMessage) message).getAvailableWizards());
                    break;
                case ERROR_WIZARD:
                    view.errorWizard(((ErrorWizardMessage) message).getError(), ((ErrorWizardMessage) message).getAvailableWizards());
                    break;
                case WAITING_OTHER_PLAYERS:
                    view.waitingPlayers(((WaitingOtherPlayersMessage) message).getNumCurrentPlayers());
                    break;
                case START_GAME:
                    view.startGame(((StartGameMessage) message).getPlayers(), ((StartGameMessage) message).getTeams(), ((StartGameMessage) message).getClouds(), ((StartGameMessage) message).getArchipelagos(), ((StartGameMessage) message).getCharacters(), ((StartGameMessage) message).isExpertMode());
                    break;
                case CHOOSE_CARD:
                    view.chooseCard(((ChooseCardMessage) message).getAvailableCards());
                    break;
                case ERROR_CARD:
                    view.errorCard(((ErrorCardMessage) message).getError(), ((ErrorCardMessage) message).getAvailableCards());
                    break;
                case IS_YOUR_TURN:
                    view.isYourTurn();
                    break;
                case ERROR_STUDENTS:
                    view.errorStudents(((GenericErrorMessage) message).getError());
                    break;
                case MOVE_MOTHER_NATURE:
                    view.moveMotherNature(((MoveMotherNatureMessage) message).getMaxSteps());
                    break;
                case ERROR_MOTHER_NATURE:
                    view.errorMotherNature(((ErrorMotherNatureMessage) message).getError(), ((ErrorMotherNatureMessage) message).getMaxSteps());
                    break;
                case CHOOSE_CLOUD:
                    view.chooseCloud(((ChooseCloudMessage) message).getAvailableClouds());
                    break;
                case ERROR_CLOUD:
                    view.errorCloud(((ErrorCloudMessage) message).getError(), ((ErrorCloudMessage) message).getAvailableClouds());
                    break;
                case ERROR_CHARACTER:
                    view.errorCharacter(((GenericErrorMessage) message).getError());
                    break;
                case ISLANDS_UPDATE:
                    view.islandsUpdate(((IslandsUpdateMessage) message).getUpdatedArchipelagos());
                    break;
                case PLAYERS_UPDATE:
                    view.playersUpdate(((PlayersUpdateMessage) message).getUpdatedPlayers());
                    break;
                case CLOUD_UPDATE:
                    view.cloudUpdate(((CloudUpdateMessage) message).getClouds());
                    break;
                case CHARACTER_UPDATE:
                    view.characterUpdate(((CharacterUpdateMessage) message).getUpdatedCharacter(), ((CharacterUpdateMessage) message).getIndex());
                    break;
                case WAIT_YOUR_TURN:
                    view.waitTurn(((WaitYourTurnMessage) message).getCurrentPlayer());
                    break;
                case END_GAME:
                    // if connection has not been shut down by client, then connection is closed
                    if (!isNetworkDownByClient())
                        closeConnection();
                    // if client has not received an EndGameMessage yet, then the EndGameMessage is sent
                    if (!endGameReceived) {
                        endGameReceived = true;
                        // checks if an EndGameMessage sent by the server has been received by the client
                        if (getEndGameMessage() != null)
                            view.endGame(endGameMessage.getWinner());
                        else
                            view.endGame(((EndGameMessage) message).getWinner());
                    }
                    break;
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
            if (connection) {
                connection = false;
                pingService.shutdown();

                if (isNetworkDownByClient()) {
                    try {
                        operations.submit(() -> manageMessage(new EndGameMessage("You Disconnected!")));
                    } catch (RejectedExecutionException e) {
                        //We don't need to create this message
                    }
                }

                operations.shutdown();
            }
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
     * it's used to check if the server sent an endGameMessage
     *
     * @return the endGameMessage received from server, null if the server didn't send any endGameMessage
     */
    public EndGameMessage getEndGameMessage() {
        synchronized (lockEndGameMessage) {
            return endGameMessage;
        }
    }

    /**
     * It's called when an endGameMessage is received from server
     *
     * @param endGameMessage is the specific message
     */
    public void setEndGameMessage(EndGameMessage endGameMessage) {
        synchronized (lockEndGameMessage) {
            this.endGameMessage = endGameMessage;
        }
    }

    /**
     * @return the ViewCLI/ViewGUI associated to this NetworkHandler
     */
    public View getView() {
        return view;
    }
}
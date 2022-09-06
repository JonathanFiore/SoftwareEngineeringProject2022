package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.protocol.JoinGameMessage;
import it.polimi.ingsw.server.model.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class is used to manage a single client in the 'run' method
 */
public class ManageClient implements Runnable {

    private static ArrayList<GameController> gameControllers = new ArrayList<>();
    private final Socket socket;
    private VirtualView virtualView;

    public ManageClient(Socket socket) {
        this.socket = socket;
    }

    /**
     * This method is called when a ManageClient is instantiated and calls the method addPlayerToGame for the management of the loginMessage
     */
    public void run() {
        try {
            socket.setSoTimeout(20000);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            JoinGameMessage loginMessage = (JoinGameMessage) in.readObject();
            virtualView = new VirtualView(socket, in, out);
            addPlayerToGame(loginMessage, virtualView);
        } catch (IOException | ClassNotFoundException e) {
            virtualView.closeConnection();
        }
    }

    /**
     * This method is called by each ManageClient object to add a player to the game
     * It's static and synchronized, in this way we use the class locker end we guarantee that is called by one
     * instance of ManageClient at the time
     *
     * @param loginMessage contains the nickName chosen by the player
     * @param view         is the view associated to the player
     */
    private static synchronized void addPlayerToGame(JoinGameMessage loginMessage, VirtualView view) {
        // We have to check if the GameController is not created yet
        // The first player of each game will cause the creation of Game, GameController
        // We check if a game has already started or has been already deleted as well

        // if there are no games
        if (gameControllers.size() == 0) {
            Game game = new Game();
            GameController gameController = new GameController(game);
            view.addObserver(gameController);
            gameControllers.add(gameController);
            gameControllers.get(0).addPlayer(loginMessage, view);
        } else {
            // check if there's any ended game so that it can be removed from the ArrayList
            for (int i = 0; i < gameControllers.size(); i++) {
                if (gameControllers.get(i).isDeleteGame()) {
                    gameControllers.remove(i);
                    i--;
                }
            }

            boolean added = false;
            // checks if there is an available game
            for (int i = 0; i < gameControllers.size() && !added; i++) {
                // checks if the i-th game has not started yet
                if (!gameControllers.get(i).isGameStarted()) {
                    // if the number of players for the game has not been decided yet
                    if (gameControllers.get(i).getNumGamePlayers() == -1) {
                        // if the number of player that have already been added to the game queue is less than 2
                        if (gameControllers.get(i).getNumCurrPlayers() < 2) {
                            view.addObserver(gameControllers.get(i));
                            gameControllers.get(i).addPlayer(loginMessage, view);
                            added = true;
                        }
                    } else {
                        // if the number of players for the game has already been decided, and it is greater than the number of player that have already been added to the game queue
                        if (gameControllers.get(i).getNumGamePlayers() > gameControllers.get(i).getNumCurrPlayers()) {
                            view.addObserver(gameControllers.get(i));
                            gameControllers.get(i).addPlayer(loginMessage, view);
                            added = true;
                        }
                    }
                }
            }

            // if the player has not been added to any existing game
            if (!added) {
                Game game = new Game();
                GameController gameController = new GameController(game);
                view.addObserver(gameController);
                gameControllers.add(gameController);
                gameControllers.get(gameControllers.size() - 1).addPlayer(loginMessage, view);
            }
        }
    }
}

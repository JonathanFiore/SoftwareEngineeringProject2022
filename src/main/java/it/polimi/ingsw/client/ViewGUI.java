package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.JavaFXGUI;
import it.polimi.ingsw.client.gui.controllers.*;
import it.polimi.ingsw.server.controller.Observer;
import it.polimi.ingsw.server.controller.View;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.characters.cards.Character;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class manage the interaction with the user by a GUI
 * it observes the NetworkHandler, and it's observable by the NetworkHandler
 */
public class ViewGUI implements View {

    private Stage stage;
    private Observer networkHandler;
    private Controller currentController;
    private String nickname;

    public ViewGUI(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void chooseCard(ArrayList<Assistant> availableCards) {
        try {
            Thread.sleep(500); // Wait for 0.5 sec before updating the scene
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> {
            ((GameSceneController) currentController).setTurnPhase("ASSISTANT");
            ((GameSceneController) currentController).setAssistantCards(availableCards);

            stage.show();
        });
    }

    @Override
    public void errorCard(String error, ArrayList<Assistant> availableCards) {
        chooseCard(availableCards);
    }

    @Override
    public void isYourTurn() {
        try {
            Thread.sleep(500); // Wait for 0.5 sec before updating the scene
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            try {
                ((GameSceneController) currentController).setTurnPhase("MOVE_STUDENTS");

                stage.show();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void waitTurn(String currentPlayer) {
        try {
            Thread.sleep(500); // Wait for 0.5 sec before updating the scene
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            try {
                ((GameSceneController) currentController).setTurnPhase("WAIT");
                ((GameSceneController) currentController).setCurrentPlayer(currentPlayer);

                stage.show();
            } catch (ClassCastException e) {
                waitTurn(currentPlayer);
            }
        });
    }

    @Override
    public void errorStudents(String error) {

    }

    @Override
    public void chooseGameSettings() {
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(JavaFXGUI.class.getClassLoader().getResource("fxml/game_settings.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(JavaFXGUI.class.getClassLoader().getResource("css/login_nickname_settings.css").toExternalForm());

                ((Controller) fxmlLoader.getController()).setNetworkHandler(networkHandler);

                ImageEffects.setCursor(scene);
                stage.setScene(scene);
                stage.setResizable(true);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void moveMotherNature(int maxSteps) {
        try {
            Thread.sleep(500); // Wait for 0.5 sec before updating the scene
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            try {
                ((GameSceneController) currentController).availableMNSteps(maxSteps);
                ((GameSceneController) currentController).setTurnPhase("MOTHER_NATURE");

                stage.show();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void errorMotherNature(String error, int maxSteps) {

    }

    @Override
    public void chooseCloud(ArrayList<Integer> availableClouds) {
        try {
            Thread.sleep(500); // Wait for 0.5 sec before updating the scene
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            try {
                ((GameSceneController) currentController).setAvailableClouds(availableClouds);
                ((GameSceneController) currentController).setTurnPhase("CLOUD");

                stage.show();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void errorCloud(String error, ArrayList<Integer> availableClouds) {

    }

    @Override
    public void errorCharacter(String error) {

    }

    @Override
    public void chooseTowerColor(ArrayList<Tower> availableColors) {
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(JavaFXGUI.class.getClassLoader().getResource("fxml/choose_tower.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(JavaFXGUI.class.getClassLoader().getResource("css/choose_tower.css").toExternalForm());

                ((Controller) fxmlLoader.getController()).setNetworkHandler(networkHandler);
                ((ChooseTowerController) fxmlLoader.getController()).setAvailableTowers(availableColors);

                ImageEffects.setCursor(scene);
                stage.setScene(scene);
                stage.setResizable(true);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void errorTowerColor(String error, ArrayList<Tower> availableColors) {

    }

    @Override
    public void chooseWizard(ArrayList<Wizard> availableWizards) {
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(JavaFXGUI.class.getClassLoader().getResource("fxml/choose_wizard.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(JavaFXGUI.class.getClassLoader().getResource("css/choose_wizard.css").toExternalForm());

                ((Controller) fxmlLoader.getController()).setNetworkHandler(networkHandler);
                ((ChooseWizardController) fxmlLoader.getController()).setAvailableWizards(availableWizards);

                ImageEffects.setCursor(scene);
                stage.setScene(scene);
                stage.setResizable(true);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void errorWizard(String error, ArrayList<Wizard> availableWizards) {

    }

    @Override
    public void waitingPlayers(int numCurrentPlayers) {
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(JavaFXGUI.class.getClassLoader().getResource("fxml/waiting_players.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(JavaFXGUI.class.getClassLoader().getResource("css/waiting_players.css").toExternalForm());

                ((Controller) fxmlLoader.getController()).setNetworkHandler(networkHandler);
                ((WaitingPlayersController) fxmlLoader.getController()).setNumPlayers(numCurrentPlayers);

                ImageEffects.setCursor(scene);
                stage.setScene(scene);
                stage.setResizable(true);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void startGame(ArrayList<Player> players, Team[] teams, ArrayList<Cloud> clouds, ArrayList<Archipelago> archipelagos, Character[] characters, boolean expertMode) {
        Platform.runLater(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(JavaFXGUI.class.getClassLoader().getResource("fxml/game.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(JavaFXGUI.class.getClassLoader().getResource("css/game.css").toExternalForm());

                ((Controller) fxmlLoader.getController()).setNetworkHandler(networkHandler);
                setCurrentController(fxmlLoader.getController());
                ((GameSceneController) currentController).setGame(players, teams, clouds, archipelagos, characters, expertMode, nickname);

                ImageEffects.setCursor(scene);
                stage.setScene(scene);
                stage.setResizable(true);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void joinGameError(String s) {
        if (s.equals("WAIT")) {
            Platform.runLater(() -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(JavaFXGUI.class.getClassLoader().getResource("fxml/waiting_players_to_join.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    scene.getStylesheets().add(JavaFXGUI.class.getClassLoader().getResource("css/waiting_players.css").toExternalForm());

                    ((Controller) fxmlLoader.getController()).setNetworkHandler(networkHandler);

                    ImageEffects.setCursor(scene);
                    stage.setScene(scene);
                    stage.setResizable(true);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else if (s.equals("FULL")) {
            Platform.runLater(() -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(JavaFXGUI.class.getClassLoader().getResource("fxml/disconnection.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    scene.getStylesheets().add(JavaFXGUI.class.getClassLoader().getResource("css/disconnection.css").toExternalForm());

                    ((Controller) fxmlLoader.getController()).setNetworkHandler(networkHandler);
                    ((DisconnectionController) fxmlLoader.getController()).setTitle("F");

                    ImageEffects.setCursor(scene);
                    stage.setScene(scene);
                    stage.setResizable(true);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            Platform.runLater(() -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(JavaFXGUI.class.getClassLoader().getResource("fxml/nickname.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    scene.getStylesheets().add(JavaFXGUI.class.getClassLoader().getResource("css/login_nickname_settings.css").toExternalForm());

                    ((Controller) fxmlLoader.getController()).setNetworkHandler(networkHandler);

                    ImageEffects.setCursor(scene);
                    stage.setScene(scene);
                    stage.setResizable(true);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void endGame(String winner) {
        if (winner.equals("Some Players disconnected") || winner.equals("You Disconnected!")) {
            Platform.runLater(() -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(JavaFXGUI.class.getClassLoader().getResource("fxml/disconnection.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    scene.getStylesheets().add(JavaFXGUI.class.getClassLoader().getResource("css/disconnection.css").toExternalForm());

                    ((Controller) fxmlLoader.getController()).setNetworkHandler(networkHandler);
                    ((DisconnectionController) fxmlLoader.getController()).setTitle("D");

                    ImageEffects.setCursor(scene);
                    stage.setScene(scene);
                    stage.setResizable(true);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else if(winner.equals("Game Already Started!")){
            Platform.runLater(() -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(JavaFXGUI.class.getClassLoader().getResource("fxml/disconnection.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    scene.getStylesheets().add(JavaFXGUI.class.getClassLoader().getResource("css/disconnection.css").toExternalForm());

                    ((Controller) fxmlLoader.getController()).setNetworkHandler(networkHandler);
                    ((DisconnectionController) fxmlLoader.getController()).setTitle("F");

                    ImageEffects.setCursor(scene);
                    stage.setScene(scene);
                    stage.setResizable(true);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }else{
            Platform.runLater(() -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(JavaFXGUI.class.getClassLoader().getResource("fxml/end_game.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    scene.getStylesheets().add(JavaFXGUI.class.getClassLoader().getResource("css/end_game.css").toExternalForm());

                    ((Controller) fxmlLoader.getController()).setNetworkHandler(networkHandler);
                    ((EndGameController) fxmlLoader.getController()).setWinnerText(winner);

                    ImageEffects.setCursor(scene);
                    stage.setScene(scene);
                    stage.setResizable(true);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void playersUpdate(ArrayList<Player> updatedPlayers) {
        try {
            Thread.sleep(500); // Wait for 0.5 sec before updating the scene
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            try {
                ((GameSceneController) currentController).updatePlayers(updatedPlayers);

                stage.show();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void islandsUpdate(ArrayList<Archipelago> updatedArchipelagos) {
        try {
            Thread.sleep(500); // Wait for 0.5 sec before updating the scene
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            try {
                ((GameSceneController) currentController).updateIslands(updatedArchipelagos);

                stage.show();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void cloudUpdate(ArrayList<Cloud> clouds) {
        try {
            Thread.sleep(500); // Wait for 0.5 sec before updating the scene
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            try {
                ((GameSceneController) currentController).updateClouds(clouds);
                stage.show();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void characterUpdate(Character updatedCharacter, int index) {
        try {
            Thread.sleep(500); // Wait for 0.5 sec before updating the scene
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            try {
                ((GameSceneController) currentController).updateCharacters(updatedCharacter, index);
                stage.show();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * This method receives the controller of the current scene and updates the value of the currentController
     * @param currentController is the controller of the current scene
     */
    public void setCurrentController(Controller currentController) {
        this.currentController = currentController;
    }

    @Override
    public void addObserver(Observer observer) {
        this.networkHandler = observer;
    }

    /**
     * This method sets the nickname to the View associate to the player with that nickname
     * @param nickname it's the nickname of the player
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

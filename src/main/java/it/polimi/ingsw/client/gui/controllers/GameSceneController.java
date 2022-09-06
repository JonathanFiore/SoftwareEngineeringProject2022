package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.protocol.*;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.characters.cards.*;
import it.polimi.ingsw.server.model.characters.cards.Character;
import it.polimi.ingsw.server.model.characters.parameters.IntegerParameter;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import it.polimi.ingsw.server.model.characters.parameters.StudentParameter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * This class extends Controller and it's the GameScene's phase Controller, that manages the other controllers
 */
public class GameSceneController extends Controller {

    private String nickname;
    private ArrayList<Player> players;
    private Team[] teams;
    private ArrayList<Cloud> clouds;
    private ArrayList<Archipelago> archipelagos;
    private Character[] characters;
    private boolean expertMode;
    private boolean usedCharacter;

    private Status phase;

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tableTab, boardsTab, assistantsTab, motherNatureTab, cloudsTab, charactersTab, character1EffectTab1, character3_5EffectTab, character4EffectTab, character9_12EffectTab, character11EffectTab, character7EffectTab, character10EffectTab;

    // Table tab
    @FXML
    private AnchorPane boardPlayerAnchor;
    @FXML
    private Label boardPlayerNick;
    @FXML
    private ImageView boardPlayerEntrance1, boardPlayerEntrance2, boardPlayerEntrance3, boardPlayerEntrance4, boardPlayerEntrance5, boardPlayerEntrance6, boardPlayerEntrance7, boardPlayerEntrance8, boardPlayerEntrance9;
    @FXML
    private ImageView boardPlayerGreenStud1, boardPlayerGreenStud2, boardPlayerGreenStud3, boardPlayerGreenStud4, boardPlayerGreenStud5, boardPlayerGreenStud6, boardPlayerGreenStud7, boardPlayerGreenStud8, boardPlayerGreenStud9, boardPlayerGreenStud10;
    @FXML
    private ImageView boardPlayerRedStud1, boardPlayerRedStud2, boardPlayerRedStud3, boardPlayerRedStud4, boardPlayerRedStud5, boardPlayerRedStud6, boardPlayerRedStud7, boardPlayerRedStud8, boardPlayerRedStud9, boardPlayerRedStud10;
    @FXML
    private ImageView boardPlayerYellowStud1, boardPlayerYellowStud2, boardPlayerYellowStud3, boardPlayerYellowStud4, boardPlayerYellowStud5, boardPlayerYellowStud6, boardPlayerYellowStud7, boardPlayerYellowStud8, boardPlayerYellowStud9, boardPlayerYellowStud10;
    @FXML
    private ImageView boardPlayerPinkStud1, boardPlayerPinkStud2, boardPlayerPinkStud3, boardPlayerPinkStud4, boardPlayerPinkStud5, boardPlayerPinkStud6, boardPlayerPinkStud7, boardPlayerPinkStud8, boardPlayerPinkStud9, boardPlayerPinkStud10;
    @FXML
    private ImageView boardPlayerBlueStud1, boardPlayerBlueStud2, boardPlayerBlueStud3, boardPlayerBlueStud4, boardPlayerBlueStud5, boardPlayerBlueStud6, boardPlayerBlueStud7, boardPlayerBlueStud8, boardPlayerBlueStud9, boardPlayerBlueStud10;
    @FXML
    private ImageView boardPlayerGreenProf, boardPlayerRedProf, boardPlayerYellowProf, boardPlayerPinkProf, boardPlayerBlueProf;
    @FXML
    private ImageView boardPlayerTower1, boardPlayerTower2, boardPlayerTower3, boardPlayerTower4, boardPlayerTower5, boardPlayerTower6, boardPlayerTower7, boardPlayerTower8;
    @FXML
    private ImageView island1, island2, island3, island4, island5, island6, island7, island8, island9, island10, island11, island12;
    @FXML
    private ImageView island1GreenStud, island1RedStud, island1YellowStud, island1PinkStud, island1BlueStud, island1Tower, island1MN;
    @FXML
    private ImageView island2GreenStud, island2RedStud, island2YellowStud, island2PinkStud, island2BlueStud, island2Tower, island2MN;
    @FXML
    private ImageView island3GreenStud, island3RedStud, island3YellowStud, island3PinkStud, island3BlueStud, island3Tower, island3MN;
    @FXML
    private ImageView island4GreenStud, island4RedStud, island4YellowStud, island4PinkStud, island4BlueStud, island4Tower, island4MN;
    @FXML
    private ImageView island5GreenStud, island5RedStud, island5YellowStud, island5PinkStud, island5BlueStud, island5Tower, island5MN;
    @FXML
    private ImageView island6GreenStud, island6RedStud, island6YellowStud, island6PinkStud, island6BlueStud, island6Tower, island6MN;
    @FXML
    private ImageView island7GreenStud, island7RedStud, island7YellowStud, island7PinkStud, island7BlueStud, island7Tower, island7MN;
    @FXML
    private ImageView island8GreenStud, island8RedStud, island8YellowStud, island8PinkStud, island8BlueStud, island8Tower, island8MN;
    @FXML
    private ImageView island9GreenStud, island9RedStud, island9YellowStud, island9PinkStud, island9BlueStud, island9Tower, island9MN;
    @FXML
    private ImageView island10GreenStud, island10RedStud, island10YellowStud, island10PinkStud, island10BlueStud, island10Tower, island10MN;
    @FXML
    private ImageView island11GreenStud, island11RedStud, island11YellowStud, island11PinkStud, island11BlueStud, island11Tower, island11MN;
    @FXML
    private ImageView island12GreenStud, island12RedStud, island12YellowStud, island12PinkStud, island12BlueStud, island12Tower, island12MN;
    @FXML
    private Label island1GreenTot, island1RedTot, island1YellowTot, island1PinkTot, island1BlueTot;
    @FXML
    private Label island2GreenTot, island2RedTot, island2YellowTot, island2PinkTot, island2BlueTot;
    @FXML
    private Label island3GreenTot, island3RedTot, island3YellowTot, island3PinkTot, island3BlueTot;
    @FXML
    private Label island4GreenTot, island4RedTot, island4YellowTot, island4PinkTot, island4BlueTot;
    @FXML
    private Label island5GreenTot, island5RedTot, island5YellowTot, island5PinkTot, island5BlueTot;
    @FXML
    private Label island6GreenTot, island6RedTot, island6YellowTot, island6PinkTot, island6BlueTot;
    @FXML
    private Label island7GreenTot, island7RedTot, island7YellowTot, island7PinkTot, island7BlueTot;
    @FXML
    private Label island8GreenTot, island8RedTot, island8YellowTot, island8PinkTot, island8BlueTot;
    @FXML
    private Label island9GreenTot, island9RedTot, island9YellowTot, island9PinkTot, island9BlueTot;
    @FXML
    private Label island10GreenTot, island10RedTot, island10YellowTot, island10PinkTot, island10BlueTot;
    @FXML
    private Label island11GreenTot, island11RedTot, island11YellowTot, island11PinkTot, island11BlueTot;
    @FXML
    private Label island12GreenTot, island12RedTot, island12YellowTot, island12PinkTot, island12BlueTot;
    @FXML
    private ImageView island1NoEntry, island2NoEntry, island3NoEntry, island4NoEntry, island5NoEntry, island6NoEntry, island7NoEntry, island8NoEntry, island9NoEntry, island10NoEntry, island11NoEntry, island12NoEntry;
    @FXML
    private Label island1NETot, island2NETot, island3NETot, island4NETot, island5NETot, island6NETot, island7NETot, island8NETot, island9NETot, island10NETot, island11NETot, island12NETot;

    @FXML
    private ImageView boardPlayerCoinImage;
    @FXML
    private Label boardPlayerCoinLabel;

    @FXML
    private ImageView turn_phase_text;

    private ArrayList<ImageView> islands, islandsGreenStud, islandsRedStud, islandsYellowStud, islandsPinkStud, islandsBlueStud, islandsTower, islandsMN;
    private ArrayList<Label> islandsGreenTot, islandsRedTot, islandsYellowTot, islandsPinkTot, islandsBlueTot;
    private ArrayList<ImageView> board1Entrance, board1GreenStud, board1RedStud, board1YellowStud, board1PinkStud, board1BlueStud, board1Towers;
    private double startXPos, startYPos;
    private int startColumn, startRow;
    private int studentsMoved = 0;
    private ArrayList<Integer> entranceIndexes = new ArrayList<>(), destinationIndexes = new ArrayList<>();
    private ArrayList<Boolean> availableEntranceMoving = new ArrayList<>();
    private ArrayList<Timeline> entranceBlink = new ArrayList<>(), hallBlink = new ArrayList<>(), islandsBlink = new ArrayList<>();
    private int[] addedToHall = new int[5];
    private ArrayList<ImageView> noEntry;
    private ArrayList<Label> noEntryTot;

    // Boards tab
    @FXML
    private AnchorPane boardTopAnchor, boardMidAnchor, boardBotAnchor;
    @FXML
    private GridPane boardTopGrid, boardMidGrid, boardBotGrid, boardsGrid;
    @FXML
    private ImageView boardTopEntrance1, boardTopEntrance2, boardTopEntrance3, boardTopEntrance4, boardTopEntrance5, boardTopEntrance6, boardTopEntrance7, boardTopEntrance8, boardTopEntrance9;
    @FXML
    private ImageView boardMidEntrance1, boardMidEntrance2, boardMidEntrance3, boardMidEntrance4, boardMidEntrance5, boardMidEntrance6, boardMidEntrance7, boardMidEntrance8, boardMidEntrance9;
    @FXML
    private ImageView boardBotEntrance1, boardBotEntrance2, boardBotEntrance3, boardBotEntrance4, boardBotEntrance5, boardBotEntrance6, boardBotEntrance7, boardBotEntrance8, boardBotEntrance9;
    @FXML
    private ImageView boardTopGreenStud1, boardTopGreenStud2, boardTopGreenStud3, boardTopGreenStud4, boardTopGreenStud5, boardTopGreenStud6, boardTopGreenStud7, boardTopGreenStud8, boardTopGreenStud9, boardTopGreenStud10;
    @FXML
    private ImageView boardMidGreenStud1, boardMidGreenStud2, boardMidGreenStud3, boardMidGreenStud4, boardMidGreenStud5, boardMidGreenStud6, boardMidGreenStud7, boardMidGreenStud8, boardMidGreenStud9, boardMidGreenStud10;
    @FXML
    private ImageView boardBotGreenStud1, boardBotGreenStud2, boardBotGreenStud3, boardBotGreenStud4, boardBotGreenStud5, boardBotGreenStud6, boardBotGreenStud7, boardBotGreenStud8, boardBotGreenStud9, boardBotGreenStud10;
    @FXML
    private ImageView boardTopRedStud1, boardTopRedStud2, boardTopRedStud3, boardTopRedStud4, boardTopRedStud5, boardTopRedStud6, boardTopRedStud7, boardTopRedStud8, boardTopRedStud9, boardTopRedStud10;
    @FXML
    private ImageView boardMidRedStud1, boardMidRedStud2, boardMidRedStud3, boardMidRedStud4, boardMidRedStud5, boardMidRedStud6, boardMidRedStud7, boardMidRedStud8, boardMidRedStud9, boardMidRedStud10;
    @FXML
    private ImageView boardBotRedStud1, boardBotRedStud2, boardBotRedStud3, boardBotRedStud4, boardBotRedStud5, boardBotRedStud6, boardBotRedStud7, boardBotRedStud8, boardBotRedStud9, boardBotRedStud10;
    @FXML
    private ImageView boardTopYellowStud1, boardTopYellowStud2, boardTopYellowStud3, boardTopYellowStud4, boardTopYellowStud5, boardTopYellowStud6, boardTopYellowStud7, boardTopYellowStud8, boardTopYellowStud9, boardTopYellowStud10;
    @FXML
    private ImageView boardMidYellowStud1, boardMidYellowStud2, boardMidYellowStud3, boardMidYellowStud4, boardMidYellowStud5, boardMidYellowStud6, boardMidYellowStud7, boardMidYellowStud8, boardMidYellowStud9, boardMidYellowStud10;
    @FXML
    private ImageView boardBotYellowStud1, boardBotYellowStud2, boardBotYellowStud3, boardBotYellowStud4, boardBotYellowStud5, boardBotYellowStud6, boardBotYellowStud7, boardBotYellowStud8, boardBotYellowStud9, boardBotYellowStud10;
    @FXML
    private ImageView boardTopPinkStud1, boardTopPinkStud2, boardTopPinkStud3, boardTopPinkStud4, boardTopPinkStud5, boardTopPinkStud6, boardTopPinkStud7, boardTopPinkStud8, boardTopPinkStud9, boardTopPinkStud10;
    @FXML
    private ImageView boardMidPinkStud1, boardMidPinkStud2, boardMidPinkStud3, boardMidPinkStud4, boardMidPinkStud5, boardMidPinkStud6, boardMidPinkStud7, boardMidPinkStud8, boardMidPinkStud9, boardMidPinkStud10;
    @FXML
    private ImageView boardBotPinkStud1, boardBotPinkStud2, boardBotPinkStud3, boardBotPinkStud4, boardBotPinkStud5, boardBotPinkStud6, boardBotPinkStud7, boardBotPinkStud8, boardBotPinkStud9, boardBotPinkStud10;
    @FXML
    private ImageView boardTopBlueStud1, boardTopBlueStud2, boardTopBlueStud3, boardTopBlueStud4, boardTopBlueStud5, boardTopBlueStud6, boardTopBlueStud7, boardTopBlueStud8, boardTopBlueStud9, boardTopBlueStud10;
    @FXML
    private ImageView boardMidBlueStud1, boardMidBlueStud2, boardMidBlueStud3, boardMidBlueStud4, boardMidBlueStud5, boardMidBlueStud6, boardMidBlueStud7, boardMidBlueStud8, boardMidBlueStud9, boardMidBlueStud10;
    @FXML
    private ImageView boardBotBlueStud1, boardBotBlueStud2, boardBotBlueStud3, boardBotBlueStud4, boardBotBlueStud5, boardBotBlueStud6, boardBotBlueStud7, boardBotBlueStud8, boardBotBlueStud9, boardBotBlueStud10;
    @FXML
    private ImageView boardTopGreenProf, boardTopRedProf, boardTopYellowProf, boardTopPinkProf, boardTopBlueProf;
    @FXML
    private ImageView boardMidGreenProf, boardMidRedProf, boardMidYellowProf, boardMidPinkProf, boardMidBlueProf;
    @FXML
    private ImageView boardBotGreenProf, boardBotRedProf, boardBotYellowProf, boardBotPinkProf, boardBotBlueProf;
    @FXML
    private ImageView boardTopTower1, boardTopTower2, boardTopTower3, boardTopTower4, boardTopTower5, boardTopTower6, boardTopTower7, boardTopTower8;
    @FXML
    private ImageView boardMidTower1, boardMidTower2, boardMidTower3, boardMidTower4, boardMidTower5, boardMidTower6, boardMidTower7, boardMidTower8;
    @FXML
    private ImageView boardBotTower1, boardBotTower2, boardBotTower3, boardBotTower4, boardBotTower5, boardBotTower6, boardBotTower7, boardBotTower8;
    @FXML
    private ImageView boardTopDesc, boardMidDesc, boardBotDesc;
    @FXML
    private AnchorPane boardTopNickBox, boardMidNickBox, boardBotNickBox;
    @FXML
    private Label boardTopNick, boardMidNick, boardBotNick;

    @FXML
    private ImageView boardTopCoinImage, boardMidCoinImage, boardBotCoinImage;
    @FXML
    private Label boardTopCoinLabel, boardMidCoinLabel, boardBotCoinLabel;

    private ArrayList<ImageView> board2Entrance, board2GreenStud, board2RedStud, board2YellowStud, board2PinkStud, board2BlueStud, board2Towers;
    private ArrayList<ImageView> board3Entrance, board3GreenStud, board3RedStud, board3YellowStud, board3PinkStud, board3BlueStud, board3Towers;
    private ArrayList<ImageView> board4Entrance, board4GreenStud, board4RedStud, board4YellowStud, board4PinkStud, board4BlueStud, board4Towers;
    private ArrayList<AnchorPane> boardsNickBox;
    private ArrayList<Label> boardsNick;

    private Timeline t;

    // Assistants tab
    @FXML
    private GridPane assistants_grid;
    @FXML
    private ImageView assistant1, assistant2, assistant3, assistant4, assistant5, assistant6, assistant7, assistant8, assistant9, assistant10;
    @FXML
    private ImageView confirmAssistant, assistantTitle;

    private int chosenAssistant = 0;
    private ArrayList<Boolean> assistantsAvailable, assistantsUp;
    private ArrayList<ImageView> assistants;

    private ArrayList<Integer> tempAssistant = new ArrayList<>();

    // Mother Nature tab
    @FXML
    private ImageView mn_mov_1, mn_mov_2, mn_mov_3, mn_mov_4, mn_mov_5;
    @FXML
    private ImageView confirmMotherNature;

    private int chosenMNMov = 0;
    private ArrayList<Boolean> mnAvailable = new ArrayList<>(), mnUp = new ArrayList<>();
    private ArrayList<ImageView> mnSteps;

    // Clouds tab
    @FXML
    private GridPane cloudsGrid, cloud1Grid, cloud2Grid, cloud3Grid, cloud4Grid;
    @FXML
    private ImageView cloud1, cloud2, cloud3, cloud4;
    @FXML
    private ImageView cloud1_1, cloud1_2, cloud1_3, cloud1_4;
    @FXML
    private ImageView cloud2_1, cloud2_2, cloud2_3, cloud2_4;
    @FXML
    private ImageView cloud3_1, cloud3_2, cloud3_3, cloud3_4;
    @FXML
    private ImageView cloud4_1, cloud4_2, cloud4_3;
    @FXML
    private ImageView cloudTitle, confirmCloud;
    private boolean cloud1up = false, cloud2up = false, cloud3up = false, cloud4up = false;
    private int chosenCloud = 0;
    private ArrayList<ArrayList<ImageView>> cloudPawns;
    private ArrayList<Boolean> availableCloud = new ArrayList<>();
    private ArrayList<ImageView> cloudImages;

    // Characters tab
    @FXML
    private ImageView character1, character2, character3, characterBox, useCharacterButton;
    @FXML
    private ImageView character1Elem1, character1Elem2, character1Elem3, character1Elem4, character1Elem5, character1Elem6;
    @FXML
    private ImageView character2Elem1, character2Elem2, character2Elem3, character2Elem4, character2Elem5, character2Elem6;
    @FXML
    private ImageView character3Elem1, character3Elem2, character3Elem3, character3Elem4, character3Elem5, character3Elem6;
    @FXML
    private Text character_effect_text;
    @FXML
    private ImageView character1coin, character2coin, character3coin;
    private boolean char1available = true, char2available = true, char3available = true;
    private boolean char1up = false, char2up = false, char3up = false;
    private int char1Id, char2Id, char3Id;
    private int chosenCharacter = 0;
    private ArrayList<ImageView> character1Elems, character2Elems, character3Elems;
    private boolean activeCharacter = false;

    // Character 1

    @FXML
    private ImageView character1EffectConfirmButton;
    @FXML
    private ImageView character1EffectStudent1, character1EffectStudent2, character1EffectStudent3, character1EffectStudent4;
    @FXML
    private ImageView character1EffectIsland1, character1EffectIsland2, character1EffectIsland3, character1EffectIsland4, character1EffectIsland5, character1EffectIsland6, character1EffectIsland7, character1EffectIsland8, character1EffectIsland9, character1EffectIsland10, character1EffectIsland11, character1EffectIsland12;
    private ArrayList<ImageView> character1EffectStudents, character1EffectIslands;
    private ArrayList<Boolean> character1EffectStudentsUp, character1EffectStudentsAvailable, character1EffectIslandsUp, character1EffectIslandsAvailable;
    private int chosenCharacter1EffectStudent = 0, chosenCharacter1EffectIsland = 0;
    private boolean confirmCharacter1On = false;

    // Character 3 - 5

    @FXML
    private ImageView character3_5EffectConfirmButton;
    @FXML
    private ImageView character3_5EffectIsland1, character3_5EffectIsland2, character3_5EffectIsland3, character3_5EffectIsland4, character3_5EffectIsland5, character3_5EffectIsland6, character3_5EffectIsland7, character3_5EffectIsland8, character3_5EffectIsland9, character3_5EffectIsland10, character3_5EffectIsland11, character3_5EffectIsland12;
    private ArrayList<ImageView> character3_5EffectIslands;
    private ArrayList<Boolean> character3_5EffectIslandsUp, character3_5EffectIslandsAvailable;
    private int chosenCharacter3_5EffectIsland = 0;
    private boolean confirmCharacter3_5On = false;

    // Character 4

    @FXML
    private ImageView character4EffectConfirmButton;
    @FXML
    private ImageView character4EffectStep1, character4EffectStep2;
    private boolean character4EffectStep1Up = false, character4EffectStep2Up = false;
    private int chosenCharacter4EffectStep = 0;
    private boolean confirmCharacter4On = false;

    // Character 7

    @FXML
    private ImageView character7EffectCardNext, character7EffectEntranceNext, character7EffectConfirmButton;
    @FXML
    private ImageView character7EffectCardStudent1, character7EffectCardStudent2, character7EffectCardStudent3, character7EffectCardStudent4, character7EffectCardStudent5, character7EffectCardStudent6;
    @FXML
    private ImageView character7EffectEntranceStudent1, character7EffectEntranceStudent2, character7EffectEntranceStudent3, character7EffectEntranceStudent4, character7EffectEntranceStudent5, character7EffectEntranceStudent6, character7EffectEntranceStudent7, character7EffectEntranceStudent8, character7EffectEntranceStudent9;
    private ArrayList<ImageView> character7EffectCardStudents, character7EffectEntranceStudents;
    private ArrayList<Boolean> character7EffectCardStudentsUp, character7EffectCardStudentsAvailable, character7EffectEntranceStudentsUp, character7EffectEntranceStudentsAvailable;
    private int currCharacter7EffectCardStudent = 0, currCharacter7EffectEntranceStudent = 0;
    private ArrayList<Integer> chosenCharacter7EffectCardStudents, chosenCharacter7EffectEntranceStudents;
    private boolean confirmCharacter7On = false;

    // Character 9 - 12

    @FXML
    private ImageView character9_12EffectConfirmButton;
    @FXML
    private ImageView character9_12EffectGreen, character9_12EffectRed, character9_12EffectYellow, character9_12EffectPink, character9_12EffectBlue;
    private ArrayList<ImageView> character9_12EffectColors;
    private ArrayList<Boolean> character9_12EffectColorUp;
    private int chosenCharacter9_12EffectColor = 0;
    private boolean confirmCharacter9_12On = false;

    // Character 10

    @FXML
    private ImageView character10EffectEntranceNext, character10EffectHallNext, character10EffectConfirmButton;
    @FXML
    private ImageView character10EffectEntranceStudent1, character10EffectEntranceStudent2, character10EffectEntranceStudent3, character10EffectEntranceStudent4, character10EffectEntranceStudent5, character10EffectEntranceStudent6, character10EffectEntranceStudent7, character10EffectEntranceStudent8, character10EffectEntranceStudent9;
    @FXML
    private ImageView character10EffectHallGreen, character10EffectHallRed, character10EffectHallYellow, character10EffectHallPink, character10EffectHallBlue;
    private ArrayList<ImageView> character10EffectEntranceStudents, character10EffectHallStudents;
    private ArrayList<Boolean> character10EffectEntranceStudentsUp, character10EffectEntranceStudentsAvailable, character10EffectHallStudentsUp, character10EffectHallStudentsAvailable;
    private int currCharacter10EffectEntranceStudent = 0, currCharacter10EffectHallStudent = 0;
    private ArrayList<Integer> chosenCharacter10EffectEntranceStudents, chosenCharacter10EffectHallStudents;
    private boolean confirmCharacter10On = false;

    // Character 11

    @FXML
    private ImageView character11EffectConfirmButton;
    @FXML
    private ImageView character11EffectStudent1, character11EffectStudent2, character11EffectStudent3, character11EffectStudent4;
    private ArrayList<ImageView> character11EffectStudents;
    private ArrayList<Boolean> character11EffectStudentsUp, character11EffectStudentsAvailable;
    private int chosenCharacter11EffectStudent = 0;
    private boolean confirmCharacter11On = false;

    // --------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This method is used to set the phase of a player's turn, depending on what they have to do to keep the game going
     * @param s it's the string that contains the label of the phase
     */
    public void setTurnPhase(String s) {
        switch (s) {
            case "WAIT":
                phase = Status.WAIT;
                turn_phase_text.setImage(new Image(getClass().getClassLoader().getResource("images/texts/wait_your_turn_phase.png").toString()));
                if (t != null)
                    t.pause();
                resetCurrentPlayer();
                tabPane.getSelectionModel().select(boardsTab);

                for (int i = 0; i < tempAssistant.size() - 1; i++) {
                    ImageEffects.turnOn(assistants.get(tempAssistant.get(i) - 1));
                    ImageEffects.addShadow(assistants.get(tempAssistant.get(i) - 1));
                    assistants.get(tempAssistant.get(i) - 1).setImage(new Image(getClass().getClassLoader().getResource(getAssistantImage(tempAssistant.get(i) - 1)).toString()));
                }
                tempAssistant.clear();

                usedCharacter = false;
                activeCharacter = false;
                useCharacterButton.setOpacity(0.0);
                resizeCharacter();

                break;
            case "ASSISTANT":
                phase = Status.CHOOSE_ASSISTANT;
                if (t != null)
                    t.pause();
                resetCurrentPlayer();
                chosenAssistant = 0;
                turn_phase_text.setImage(new Image(getClass().getClassLoader().getResource("images/texts/choose_assistant_phase.png").toString()));
                setAssistantsTab();
                usedCharacter = false;

                useCharacterButton.setOpacity(0.0);
                resizeCharacter();

                break;
            case "MOVE_STUDENTS":
                phase = Status.MOVE_STUDENTS;
                turn_phase_text.setImage(new Image(getClass().getClassLoader().getResource("images/texts/move_students_phase.png").toString()));
                if (t != null)
                    t.pause();
                resetCurrentPlayer();
                tabPane.getSelectionModel().select(tableTab);
                entranceBlink.clear();
                hallBlink.clear();
                islandsBlink.clear();
                studentsMoved = 0;
                for (int i = 0; i < 5; i++)
                    addedToHall[i] = 0;
                setMovingPawns();

                if (usedCharacter)
                    useCharacterButton.setOpacity(0.0);
                else
                    useCharacterButton.setOpacity(1.0);
                resizeCharacter();

                break;
            case "MOTHER_NATURE":
                phase = Status.MOVE_MOTHER_NATURE;
                turn_phase_text.setImage(new Image(getClass().getClassLoader().getResource("images/texts/mother_nature_phase.png").toString()));
                chosenMNMov = 0;
                tabPane.getTabs().add(3, motherNatureTab);
                tabPane.getSelectionModel().select(motherNatureTab);
                for (ImageView i : board1Entrance)
                    i.setEffect(null);

                if (usedCharacter)
                    useCharacterButton.setOpacity(0.0);
                else
                    useCharacterButton.setOpacity(1.0);
                resizeCharacter();

                break;
            case "CLOUD":
                phase = Status.CHOOSE_CLOUD;
                turn_phase_text.setImage(new Image(getClass().getClassLoader().getResource("images/texts/choose_cloud_phase.png").toString()));
                chosenCloud = 0;
                setCloudTab();
                tabPane.getSelectionModel().select(cloudsTab);

                if (usedCharacter)
                    useCharacterButton.setOpacity(0.0);
                else
                    useCharacterButton.setOpacity(1.0);
                resizeCharacter();

                break;
        }
    }

    /**
     * This method is used to set the player that is currently in the action phase of the game (the one that has to play, not to wait)
     * @param nick it's the nickname of the corresponding player
     */
    public void setCurrentPlayer(String nick) {
        for (int i = 0; i < 3; i++) {
            if (boardsNick.get(i).getText().equals(nick))
                setCurrentPlayerGlow(i);
        }
    }

    /**
     * This method is used to update the attribute ArrayList<Player> updatedPlayers
     * and, when the game is a 4-players game, it updates the two teams
     * @param updatedPlayers are the players that need to be updated
     */
    public void updatePlayers(ArrayList<Player> updatedPlayers) {
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
        setBoards(players.size());
        showTab();
    }

    /**
     * This method is used to update the islands/archipelagos
     * @param updatedArchipelagos it's the update of the archipelagos
     */
    public void updateIslands(ArrayList<Archipelago> updatedArchipelagos) {
        archipelagos = updatedArchipelagos;
        setArchipelagos();
        showTab();
    }

    /**
     * This method is used to update the clouds
     * @param updatedClouds it's the update of the clouds
     */
    public void updateClouds(ArrayList<Cloud> updatedClouds) {
        clouds = updatedClouds;
        setClouds();
        showTab();
    }

    /**
     * This method is used to update a character
     * @param updatedCharacter it's the updated character
     * @param index it's the index of the character to update
     */
    public void updateCharacters(Character updatedCharacter, int index) {
        characters[index] = updatedCharacter;
        switch (index) {
            case 0:
                setCharacterPawns(char1Id, index);
                break;
            case 1:
                setCharacterPawns(char2Id, index);
                break;
            case 2:
                setCharacterPawns(char3Id, index);
                break;
        }
        showTab();
    }

    // ASSISTANTS -----------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This method is used in the CHOOSE_ASSISTANT phase and it selects the 1st Assistant
     */
    public void selectAssistant1() {
        if (phase == Status.CHOOSE_ASSISTANT)
            assistantsUp.set(0, selectAssistant(assistantsAvailable.get(0), assistantsUp.get(0), assistants.get(0), 1));
    }


    /**
     * This method is used in the CHOOSE_ASSISTANT phase and it selects the 2nd Assistant
     */
    public void selectAssistant2() {
        if (phase == Status.CHOOSE_ASSISTANT)
            assistantsUp.set(1, selectAssistant(assistantsAvailable.get(1), assistantsUp.get(1), assistants.get(1), 2));
    }

    /**
     * This method is used in the CHOOSE_ASSISTANT phase and it selects the 3rd Assistant
     */
    public void selectAssistant3() {
        if (phase == Status.CHOOSE_ASSISTANT)
            assistantsUp.set(2, selectAssistant(assistantsAvailable.get(2), assistantsUp.get(2), assistants.get(2), 3));
    }

    /**
     * This method is used in the CHOOSE_ASSISTANT phase and it selects the 4th Assistant
     */
    public void selectAssistant4() {
        if (phase == Status.CHOOSE_ASSISTANT)
            assistantsUp.set(3, selectAssistant(assistantsAvailable.get(3), assistantsUp.get(3), assistants.get(3), 4));
    }

    /**
     * This method is used in the CHOOSE_ASSISTANT phase and it selects the 5th Assistant
     */
    public void selectAssistant5() {
        if (phase == Status.CHOOSE_ASSISTANT)
            assistantsUp.set(4, selectAssistant(assistantsAvailable.get(4), assistantsUp.get(4), assistants.get(4), 5));
    }

    /**
     * This method is used in the CHOOSE_ASSISTANT phase and it selects the 6th Assistant
     */
    public void selectAssistant6() {
        if (phase == Status.CHOOSE_ASSISTANT)
            assistantsUp.set(5, selectAssistant(assistantsAvailable.get(5), assistantsUp.get(5), assistants.get(5), 6));
    }

    /**
     * This method is used in the CHOOSE_ASSISTANT phase and it selects the 7th Assistant
     */
    public void selectAssistant7() {
        if (phase == Status.CHOOSE_ASSISTANT)
            assistantsUp.set(6, selectAssistant(assistantsAvailable.get(6), assistantsUp.get(6), assistants.get(6), 7));
    }

    /**
     * This method is used in the CHOOSE_ASSISTANT phase and it selects the 8th Assistant
     */
    public void selectAssistant8() {
        if (phase == Status.CHOOSE_ASSISTANT)
            assistantsUp.set(7, selectAssistant(assistantsAvailable.get(7), assistantsUp.get(7), assistants.get(7), 8));
    }

    /**
     * This method is used in the CHOOSE_ASSISTANT phase and it selects the 9th Assistant
     */
    public void selectAssistant9() {
        if (phase == Status.CHOOSE_ASSISTANT)
            assistantsUp.set(8, selectAssistant(assistantsAvailable.get(8), assistantsUp.get(8), assistants.get(8), 9));
    }

    /**
     * This method is used in the CHOOSE_ASSISTANT phase and it selects the 10th Assistant
     */
    public void selectAssistant10() {
        if (phase == Status.CHOOSE_ASSISTANT)
            assistantsUp.set(9, selectAssistant(assistantsAvailable.get(9), assistantsUp.get(9), assistants.get(9), 10));
    }

    /**
     * This method is used in the CHOOSE_ASSISTANT phase and it sets an effect when you press the mouse button on the confirm image
     */
    public void confirmAssistantOn() {
        if (phase == Status.CHOOSE_ASSISTANT) {
            if (chosenAssistant != 0) {
                confirmAssistant.setScaleX(0.8);
                confirmAssistant.setScaleY(0.8);
            }
        }
    }

    /**
     * This method is used in the CHOOSE_ASSISTANT phase and it sets an effect when you release the mouse button on the confirm image
     * then it calls the method selectConfirmAssistant()
     */
    public void confirmAssistantOff() {
        if (phase == Status.CHOOSE_ASSISTANT) {
            if (chosenAssistant != 0) {
                confirmAssistant.setScaleX(1.0);
                confirmAssistant.setScaleY(1.0);
                selectConfirmAssistant();
            }
        }
    }

    /**
     * This method is used in the CHOOSE_ASSISTANT phase, it applies effects to the chosenAssistant
     * and it sends a message with the chosen Assistant's id to the Server via NetworkHandler
     */
    private void selectConfirmAssistant() {

        int chosen = 0;
        boolean found = false;

        for (int i = 0; i < 10 && !found; i++) {
            if (assistantsAvailable.get(i) || tempAssistant.contains(i + 1)) {
                if (chosenAssistant == i + 1)
                    found = true;
                else
                    chosen++;
            }
        }

        assistantsAvailable.set(chosenAssistant - 1, false);
        assistantsUp.set(chosenAssistant - 1, false);
        ImageEffects.turnOff(assistants.get(chosenAssistant - 1));
        ImageEffects.turnOff(confirmAssistant);
        assistants.get(chosenAssistant - 1).setImage(new Image(getClass().getClassLoader().getResource(getWizardImage()).toString()));

        resetAssistantsTab();

        SelectedCardMessage m = new SelectedCardMessage(chosen + 1);
        getNetworkHandler().update(m);

        tempAssistant.add(chosenAssistant);
        chosenAssistant = 0;

        phase = Status.STANDBY;
    }

    /**
     * This method is used in the CHOOSE_ASSISTANT phase and it applies effects on the image of the currently selected Assistant
     * @param available indicates if an Assistant can be selected
     * @param up indicates if it is currently selected
     * @param a it's the image of the currently selected Assistant
     * @param chosen it's the id of the selected Assistant
     * @return true if the just selected Assistant wasn't already selected before the call of this method, false in every other case
     */
    private boolean selectAssistant(boolean available, boolean up, ImageView a, int chosen) {
        if (available) {
            if (!up) {
                resizeAssistant();
                a.setScaleX(1.1);
                a.setScaleY(1.1);
                ImageEffects.addWhiteGlow(a);
                chosenAssistant = chosen;
                ImageEffects.addLightBluShadow(confirmAssistant);
                return true;
            } else {
                resizeAssistant();
                chosenAssistant = 0;
                ImageEffects.turnOff(confirmAssistant);
                return false;
            }
        }
        return false;
    }

    /**
     * This method is used in the CHOOSE_ASSISTANT phase and it resizes the Assistant currently selected
     */
    private void resizeAssistant() {
        if (assistantsUp.get(0)) {
            assistantsUp.set(0, ImageEffects.zoomOut(assistants.get(0)));
            ImageEffects.addShadow(assistants.get(0));
        } else if (assistantsUp.get(1)) {
            assistantsUp.set(1, ImageEffects.zoomOut(assistants.get(1)));
            ImageEffects.addShadow(assistants.get(1));
        } else if (assistantsUp.get(2)) {
            assistantsUp.set(2, ImageEffects.zoomOut(assistants.get(2)));
            ImageEffects.addShadow(assistants.get(2));
        } else if (assistantsUp.get(3)) {
            assistantsUp.set(3, ImageEffects.zoomOut(assistants.get(3)));
            ImageEffects.addShadow(assistants.get(3));
        } else if (assistantsUp.get(4)) {
            assistantsUp.set(4, ImageEffects.zoomOut(assistants.get(4)));
            ImageEffects.addShadow(assistants.get(4));
        } else if (assistantsUp.get(5)) {
            assistantsUp.set(5, ImageEffects.zoomOut(assistants.get(5)));
            ImageEffects.addShadow(assistants.get(5));
        } else if (assistantsUp.get(6)) {
            assistantsUp.set(6, ImageEffects.zoomOut(assistants.get(6)));
            ImageEffects.addShadow(assistants.get(6));
        } else if (assistantsUp.get(7)) {
            assistantsUp.set(7, ImageEffects.zoomOut(assistants.get(7)));
            ImageEffects.addShadow(assistants.get(7));
        } else if (assistantsUp.get(8)) {
            assistantsUp.set(8, ImageEffects.zoomOut(assistants.get(8)));
            ImageEffects.addShadow(assistants.get(8));
        } else if (assistantsUp.get(9)) {
            assistantsUp.set(9, ImageEffects.zoomOut(assistants.get(9)));
            ImageEffects.addShadow(assistants.get(9));
        }
    }

    /**
     * This method is used when the player doesn't have to select any Assistant because they're not in the CHOOSE_ASSISTANT phase,
     * so this method applies effects on the title and on the confirm image on the AssistantsTab to make them disappear
     * and it rearranges the Assistants' images on the scene
     */
    private void resetAssistantsTab() {
        confirmAssistant.setOpacity(0.0);
        assistantTitle.setOpacity(0.0);

        RowConstraints r0 = new RowConstraints();
        RowConstraints r50 = new RowConstraints();
        r0.setPercentHeight(0);
        r50.setPercentHeight(50);

        assistants_grid.getRowConstraints().set(0, r0);
        assistants_grid.getRowConstraints().set(1, r50);
        assistants_grid.getRowConstraints().set(2, r50);
        assistants_grid.getRowConstraints().set(3, r0);
    }

    /**
     * This method is used when the player has to select an Assistant because they're in the CHOOSE_ASSISTANT phase,
     * so this method applies effects on the title and on the confirm image on the AssistantsTab to make them reappear
     * and it rearranges the Assistants' images on the scene
     */
    private void setAssistantsTab() {
        confirmAssistant.setOpacity(1.0);
        assistantTitle.setOpacity(1.0);

        RowConstraints r10 = new RowConstraints();
        RowConstraints r40 = new RowConstraints();
        r10.setPercentHeight(10);
        r40.setPercentHeight(40);

        assistants_grid.getRowConstraints().set(0, r10);
        assistants_grid.getRowConstraints().set(1, r40);
        assistants_grid.getRowConstraints().set(2, r40);
        assistants_grid.getRowConstraints().set(3, r10);
    }

    /**
     * This method is used in the CHOOSE_ASSISTANT phase and it shows the player the Assistant card that can be chosen
     */
    public void setAssistantCards(ArrayList<Assistant> availableAssistants) {
        for (ImageView a : assistants) {
            ImageEffects.turnOff(a);
            a.setImage(new Image(getClass().getClassLoader().getResource(getWizardImage()).toString()));
        }

        for (int i = 0; i < 10; i++) {
            assistantsAvailable.set(i, false);
            assistantsUp.set(i, false);
        }

        for (Assistant a : availableAssistants) {
            assistantsAvailable.set(a.getCardValue() - 1, true);
        }

        for (Integer i : tempAssistant) {
            assistantsAvailable.set(i - 1, false);
        }

        for (int i = 0; i < 10; i++) {
            if ((assistantsAvailable.get(i) && tempAssistant.size() == 0) || (assistantsAvailable.get(i) && tempAssistant.size() > 0 && !tempAssistant.contains(i + 1))) {
                ImageEffects.turnOn(assistants.get(i));
                ImageEffects.addShadow(assistants.get(i));
                assistants.get(i).setImage(new Image(getClass().getClassLoader().getResource(getAssistantImage(i)).toString()));
            }
        }

        tabPane.getSelectionModel().select(assistantsTab);
    }

    // MOTHER NATURE -----------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This method is used in the MOVE_MOTHER_NATURE phase and it applies different effects if the number of steps are available or not
     * @param steps it's the number of maximum steps that mother nature can do
     */
    public void availableMNSteps(int steps) {
        for (int i = 0; i < 5; i++) {
            if (i < steps) {
                ImageEffects.turnOn(mnSteps.get(i));
                ImageEffects.addShadow(mnSteps.get(i));
                mnAvailable.add(true);
            } else {
                ImageEffects.turnOff(mnSteps.get(i));
                mnAvailable.add(false);
            }
            mnUp.add(false);
        }
    }

    /**
     * This method is used in the MOVE_MOTHER_NATURE phase and it chooses a number of steps equals to 1
     */
    public void selectMNMov1() {
        if (phase == Status.MOVE_MOTHER_NATURE) {
            mnUp.set(0, selectMNMov(mnAvailable.get(0), mnUp.get(0), mn_mov_1, 1));
            if (expertMode)
                resizeCharacter();
        }
    }

    /**
     * This method is used in the MOVE_MOTHER_NATURE phase and it chooses a number of steps equals to 2
     */
    public void selectMNMov2() {
        if (phase == Status.MOVE_MOTHER_NATURE) {
            mnUp.set(1, selectMNMov(mnAvailable.get(1), mnUp.get(1), mn_mov_2, 2));
            if (expertMode)
                resizeCharacter();
        }
    }

    /**
     * This method is used in the MOVE_MOTHER_NATURE phase and it chooses a number of steps equals to 3
     */
    public void selectMNMov3() {
        if (phase == Status.MOVE_MOTHER_NATURE) {
            mnUp.set(2, selectMNMov(mnAvailable.get(2), mnUp.get(2), mn_mov_3, 3));
            if (expertMode)
                resizeCharacter();
        }
    }

    /**
     * This method is used in the MOVE_MOTHER_NATURE phase and it chooses a number of steps equals to 4
     */
    public void selectMNMov4() {
        if (phase == Status.MOVE_MOTHER_NATURE) {
            mnUp.set(3, selectMNMov(mnAvailable.get(3), mnUp.get(3), mn_mov_4, 4));
            if (expertMode)
                resizeCharacter();
        }
    }

    /**
     * This method is used in the MOVE_MOTHER_NATURE phase and it chooses a number of steps equals to 5
     */
    public void selectMNMov5() {
        if (phase == Status.MOVE_MOTHER_NATURE) {
            mnUp.set(4, selectMNMov(mnAvailable.get(4), mnUp.get(4), mn_mov_5, 5));
            if (expertMode)
                resizeCharacter();
        }
    }

    /**
     * This method is used in the MOVE_MOTHER_NATURE phase and it sets an effect when you press the mouse button on the confirm image
     */
    public void confirmMotherNatureOn() {
        if (phase == Status.MOVE_MOTHER_NATURE) {
            if (chosenMNMov != 0) {
                confirmMotherNature.setScaleX(0.8);
                confirmMotherNature.setScaleY(0.8);
            }
        }
    }

    /**
     * This method is used in the MOVE_MOTHER_NATURE phase and it sets an effect when you release the mouse button on the confirm image
     * then it calls the method selectConfirmMotherNature()
     */
    public void confirmMotherNatureOff() {
        if (phase == Status.MOVE_MOTHER_NATURE) {
            if (chosenMNMov != 0) {
                confirmMotherNature.setScaleX(1.0);
                confirmMotherNature.setScaleY(1.0);
                selectConfirmMotherNature();
            }
        }
    }

    /**
     * This method is used in the MOVE_MOTHER_NATURE phase
     * and it sends a message with the chosen mother nature's movement to the Server via NetworkHandler
     */
    private void selectConfirmMotherNature() {

        StepsMotherNatureMessage m = new StepsMotherNatureMessage(chosenMNMov);
        getNetworkHandler().update(m);

        phase = Status.STANDBY;

        ImageEffects.turnOff(confirmMotherNature);

        chosenMNMov = 0;
        mnAvailable.clear();
        mnUp.clear();

        tabPane.getSelectionModel().select(tableTab);
        tabPane.getTabs().remove(motherNatureTab);
    }

    /**
     * This method is used in the MOVE_MOTHER_NATURE phase and it applies effects on the image of the currently selected number of steps
     * @param available indicates if a number of steps can be selected
     * @param up indicates if it is currently selected
     * @param m it's the image of the currently selected number of steps
     * @param chosen it's the id of the selected number of steps
     * @return true if the just selected number of steps wasn't already selected before the call of this method, false in every other case
     */
    private boolean selectMNMov(boolean available, boolean up, ImageView m, int chosen) {
        if (available) {
            if (!up) {
                resizeMNMov();
                m.setScaleX(1.1);
                m.setScaleY(1.1);
                ImageEffects.addWhiteGlow(m);
                chosenMNMov = chosen;
                ImageEffects.addLightBluShadow(confirmMotherNature);
                return true;
            } else {
                resizeMNMov();
                chosenMNMov = 0;
                ImageEffects.turnOff(confirmMotherNature);
                return false;
            }
        }
        return false;
    }

    /**
     * This method is used in the MOVE_MOTHER_NATURE phase and it resizes the number of steps currently selected
     */
    private void resizeMNMov() {
        if (mnUp.get(0)) {
            mnUp.set(0, ImageEffects.zoomOut(mn_mov_1));
            ImageEffects.addShadow(mn_mov_1);
        } else if (mnUp.get(1)) {
            mnUp.set(1, ImageEffects.zoomOut(mn_mov_2));
            ImageEffects.addShadow(mn_mov_2);
        } else if (mnUp.get(2)) {
            mnUp.set(2, ImageEffects.zoomOut(mn_mov_3));
            ImageEffects.addShadow(mn_mov_3);
        } else if (mnUp.get(3)) {
            mnUp.set(3, ImageEffects.zoomOut(mn_mov_4));
            ImageEffects.addShadow(mn_mov_4);
        } else if (mnUp.get(4)) {
            mnUp.set(4, ImageEffects.zoomOut(mn_mov_5));
            ImageEffects.addShadow(mn_mov_5);
        }
    }

    // CLOUDS -----------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This method is used in the CHOOSE_CLOUD phase and it chooses the cloud number 1
     */
    public void selectCloud1() {
        if (phase == Status.CHOOSE_CLOUD) {
            if (availableCloud.get(0)) {
                if (!cloud1up) {
                    resizeClouds();
                    scaleCloud(1);
                    chosenCloud = 1;
                    ImageEffects.addLightBluShadow(confirmCloud);
                    cloud1up = true;

                    if (expertMode)
                        resizeCharacter();
                } else {
                    resizeClouds();
                    chosenCloud = 0;
                    ImageEffects.turnOff(confirmCloud);
                    cloud1up = false;

                    if (expertMode)
                        resizeCharacter();
                }
            }
        }
    }

    /**
     * This method is used in the CHOOSE_CLOUD phase and it chooses the cloud number 2
     */
    public void selectCloud2() {
        if (phase == Status.CHOOSE_CLOUD) {
            if (availableCloud.get(1)) {
                if (!cloud2up) {
                    resizeClouds();
                    scaleCloud(2);
                    chosenCloud = 2;
                    ImageEffects.addLightBluShadow(confirmCloud);
                    cloud2up = true;

                    if (expertMode)
                        resizeCharacter();
                } else {
                    resizeClouds();
                    chosenCloud = 0;
                    ImageEffects.turnOff(confirmCloud);
                    cloud2up = false;

                    if (expertMode)
                        resizeCharacter();
                }
            }
        }
    }

    /**
     * This method is used in the CHOOSE_CLOUD phase and it chooses the cloud number 3
     */
    public void selectCloud3() {
        if (phase == Status.CHOOSE_CLOUD) {
            if (availableCloud.get(2)) {
                if (!cloud3up) {
                    resizeClouds();
                    scaleCloud(3);
                    chosenCloud = 3;
                    ImageEffects.addLightBluShadow(confirmCloud);
                    cloud3up = true;

                    if (expertMode)
                        resizeCharacter();
                } else {
                    resizeClouds();
                    chosenCloud = 0;
                    ImageEffects.turnOff(confirmCloud);
                    cloud3up = false;

                    if (expertMode)
                        resizeCharacter();
                }
            }
        }
    }

    /**
     * This method is used in the CHOOSE_CLOUD phase and it chooses the cloud number 4
     */
    public void selectCloud4() {
        if (phase == Status.CHOOSE_CLOUD) {
            if (availableCloud.get(3)) {
                if (!cloud4up) {
                    resizeClouds();
                    scaleCloud(4);
                    chosenCloud = 4;
                    ImageEffects.addLightBluShadow(confirmCloud);
                    cloud4up = true;

                    if (expertMode)
                        resizeCharacter();
                } else {
                    resizeClouds();
                    chosenCloud = 0;
                    ImageEffects.turnOff(confirmCloud);
                    cloud4up = false;

                    if (expertMode)
                        resizeCharacter();
                }
            }
        }
    }

    /**
     * This method is used in the CHOOSE_CLOUD phase and it sets an effect when you press the mouse button on the confirm image
     */
    public void confirmCloudOn() {
        if (phase == Status.CHOOSE_CLOUD) {
            if (chosenCloud != 0) {
                confirmCloud.setScaleX(0.8);
                confirmCloud.setScaleY(0.8);
            }
        }
    }

    /**
     * This method is used in the CHOOSE_CLOUD phase and it sets an effect when you release the mouse button on the confirm image
     * then it calls the method selectConfirmCloud()
     */
    public void confirmCloudOff() {
        if (phase == Status.CHOOSE_CLOUD) {
            if (chosenCloud != 0) {
                confirmCloud.setScaleX(1.0);
                confirmCloud.setScaleY(1.0);
                selectConfirmCloud();
            }
        }
    }

    /**
     * This method is used in the CHOOSE_CLOUD phase
     * and it sends a message with the chosen cloud to the Server via NetworkHandler
     */
    private void selectConfirmCloud() {

        SelectedCloudMessage m = new SelectedCloudMessage(chosenCloud);
        getNetworkHandler().update(m);

        phase = Status.STANDBY;

        cloudImages.get(chosenCloud - 1).setEffect(null);
        ImageEffects.addShadow(cloudImages.get(chosenCloud - 1));
        ImageEffects.zoomOut(cloudImages.get(chosenCloud - 1));

        for (ImageView i : cloudPawns.get(chosenCloud - 1))
            ImageEffects.zoomOut(i);

        chosenCloud = 0;

        tabPane.getSelectionModel().select(tableTab);

        resetCloudTab();
    }

    /**
     * This method is used to readapt the number of clouds to the scene depending on the number of players
     * @param numClouds it's the number of the clouds
     */
    private void setCloudsGrid(int numClouds) {
        switch (numClouds) {
            case 2:
                removeCloud3();
                removeCloud4();

                ColumnConstraints c1 = new ColumnConstraints();
                ColumnConstraints c2 = new ColumnConstraints();
                ColumnConstraints c3 = new ColumnConstraints();
                ColumnConstraints c4 = new ColumnConstraints();
                c1.setPercentWidth(50.0);
                c2.setPercentWidth(50.0);
                c3.setPercentWidth(0.0);
                c4.setPercentWidth(0.0);
                cloudsGrid.getColumnConstraints().set(0, c1);
                cloudsGrid.getColumnConstraints().set(1, c2);
                cloudsGrid.getColumnConstraints().set(2, c3);
                cloudsGrid.getColumnConstraints().set(3, c4);

                ColumnConstraints c5 = new ColumnConstraints();
                c5.setPercentWidth(30);

                cloud1Grid.getColumnConstraints().set(0, c5);
                cloud1Grid.getColumnConstraints().set(6, c5);
                GridPane.setValignment(cloud1_2, VPos.BOTTOM);
                GridPane.setHalignment(cloud1_2, HPos.RIGHT);
                GridPane.setValignment(cloud1_3, VPos.BOTTOM);
                GridPane.setHalignment(cloud1_3, HPos.LEFT);
                cloud1Grid.getChildren().remove(cloud1_4);

                cloud2Grid.getColumnConstraints().set(0, c5);
                cloud2Grid.getColumnConstraints().set(6, c5);
                GridPane.setValignment(cloud2_2, VPos.BOTTOM);
                GridPane.setHalignment(cloud2_2, HPos.RIGHT);
                GridPane.setValignment(cloud2_3, VPos.BOTTOM);
                GridPane.setHalignment(cloud2_3, HPos.LEFT);
                cloud2Grid.getChildren().remove(cloud2_4);
                break;
            case 3:
                removeCloud4();
                ColumnConstraints d1 = new ColumnConstraints();
                ColumnConstraints d2 = new ColumnConstraints();
                ColumnConstraints d3 = new ColumnConstraints();
                ColumnConstraints d4 = new ColumnConstraints();
                d1.setPercentWidth(33.0);
                d2.setPercentWidth(34.0);
                d3.setPercentWidth(33.0);
                d4.setPercentWidth(0.0);
                cloudsGrid.getColumnConstraints().set(0, d1);
                cloudsGrid.getColumnConstraints().set(1, d2);
                cloudsGrid.getColumnConstraints().set(2, d3);
                cloudsGrid.getColumnConstraints().set(3, d4);
                ColumnConstraints d5 = new ColumnConstraints();
                d5.setPercentWidth(20);
                cloud1Grid.getColumnConstraints().set(0, d5);
                cloud1Grid.getColumnConstraints().set(6, d5);
                cloud2Grid.getColumnConstraints().set(0, d5);
                cloud2Grid.getColumnConstraints().set(6, d5);
                cloud3Grid.getColumnConstraints().set(0, d5);
                cloud3Grid.getColumnConstraints().set(6, d5);
                break;
            case 4:
                GridPane.setValignment(cloud1_2, VPos.BOTTOM);
                GridPane.setHalignment(cloud1_2, HPos.RIGHT);
                GridPane.setValignment(cloud1_3, VPos.BOTTOM);
                GridPane.setHalignment(cloud1_3, HPos.LEFT);
                cloud1Grid.getChildren().remove(cloud1_4);

                GridPane.setValignment(cloud2_2, VPos.BOTTOM);
                GridPane.setHalignment(cloud2_2, HPos.RIGHT);
                GridPane.setValignment(cloud2_3, VPos.BOTTOM);
                GridPane.setHalignment(cloud2_3, HPos.LEFT);
                cloud2Grid.getChildren().remove(cloud2_4);

                GridPane.setValignment(cloud3_2, VPos.BOTTOM);
                GridPane.setHalignment(cloud3_2, HPos.RIGHT);
                GridPane.setValignment(cloud3_3, VPos.BOTTOM);
                GridPane.setHalignment(cloud3_3, HPos.LEFT);
                cloud3Grid.getChildren().remove(cloud3_4);
                break;
        }

        tabPane.getSelectionModel().select(cloudsTab);
    }

    /**
     * This method is used to remove the cloud 3 and the pawns on that cloud
     */
    private void removeCloud3() {
        cloud3Grid.getChildren().remove(cloud3);
        cloud3Grid.getChildren().remove(cloud3_1);
        cloud3Grid.getChildren().remove(cloud3_2);
        cloud3Grid.getChildren().remove(cloud3_3);
        cloud3Grid.getChildren().remove(cloud3_4);
    }

    /**
     * This method is used to remove the cloud 4 and the pawns on that cloud
     */
    private void removeCloud4() {
        cloud4Grid.getChildren().remove(cloud4);
        cloud4Grid.getChildren().remove(cloud4_1);
        cloud4Grid.getChildren().remove(cloud4_2);
        cloud4Grid.getChildren().remove(cloud4_3);
    }

    /**
     * This method is used in the CHOOSE_CLOUD phase and it scales the selected cloud and the associated pawns
     * @param c it's the number of the currently selected cloud
     */
    private void scaleCloud(int c) {
        switch (c) {
            case 1:
                cloud1.setScaleX(1.15);
                cloud1_1.setScaleX(1.15);
                cloud1_2.setScaleX(1.15);
                cloud1_3.setScaleX(1.15);
                cloud1_4.setScaleX(1.15);
                cloud1.setScaleY(1.15);
                cloud1_1.setScaleY(1.15);
                cloud1_2.setScaleY(1.15);
                cloud1_3.setScaleY(1.15);
                cloud1_4.setScaleY(1.15);
                ImageEffects.addWhiteGlow(cloud1);
                break;
            case 2:
                cloud2.setScaleX(1.15);
                cloud2_1.setScaleX(1.15);
                cloud2_2.setScaleX(1.15);
                cloud2_3.setScaleX(1.15);
                cloud2_4.setScaleX(1.15);
                cloud2.setScaleY(1.15);
                cloud2_1.setScaleY(1.15);
                cloud2_2.setScaleY(1.15);
                cloud2_3.setScaleY(1.15);
                cloud2_4.setScaleY(1.15);
                ImageEffects.addWhiteGlow(cloud2);
                break;
            case 3:
                cloud3.setScaleX(1.15);
                cloud3_1.setScaleX(1.15);
                cloud3_2.setScaleX(1.15);
                cloud3_3.setScaleX(1.15);
                cloud3_4.setScaleX(1.15);
                cloud3.setScaleY(1.15);
                cloud3_1.setScaleY(1.15);
                cloud3_2.setScaleY(1.15);
                cloud3_3.setScaleY(1.15);
                cloud3_4.setScaleY(1.15);
                ImageEffects.addWhiteGlow(cloud3);
                break;
            case 4:
                cloud4.setScaleX(1.15);
                cloud4_1.setScaleX(1.15);
                cloud4_2.setScaleX(1.15);
                cloud4_3.setScaleX(1.15);
                cloud4.setScaleY(1.15);
                cloud4_1.setScaleY(1.15);
                cloud4_2.setScaleY(1.15);
                cloud4_3.setScaleY(1.15);
                ImageEffects.addWhiteGlow(cloud4);
                break;
        }
    }

    /**
     * This method is used in the CHOOSE_CLOUD phase and it resizes the cloud currently selected
     */
    private void resizeClouds() {
        if (cloud1up) {
            ImageEffects.zoomOut(cloud1);
            ImageEffects.zoomOut(cloud1_1);
            ImageEffects.zoomOut(cloud1_2);
            ImageEffects.zoomOut(cloud1_3);
            ImageEffects.zoomOut(cloud1_4);
            ImageEffects.addShadow(cloud1);
            cloud1up = false;
        } else if (cloud2up) {
            ImageEffects.zoomOut(cloud2);
            ImageEffects.zoomOut(cloud2_1);
            ImageEffects.zoomOut(cloud2_2);
            ImageEffects.zoomOut(cloud2_3);
            ImageEffects.zoomOut(cloud2_4);
            ImageEffects.addShadow(cloud2);
            cloud2up = false;
        } else if (cloud3up) {
            ImageEffects.zoomOut(cloud3);
            ImageEffects.zoomOut(cloud3_1);
            ImageEffects.zoomOut(cloud3_2);
            ImageEffects.zoomOut(cloud3_3);
            ImageEffects.zoomOut(cloud3_4);
            ImageEffects.addShadow(cloud3);
            cloud3up = false;
        } else if (cloud4up) {
            ImageEffects.zoomOut(cloud4);
            ImageEffects.zoomOut(cloud4_1);
            ImageEffects.zoomOut(cloud4_2);
            ImageEffects.zoomOut(cloud4_3);
            ImageEffects.addShadow(cloud4);
            cloud4up = false;
        }
    }

    /**
     * This method is used when the player has to select a Cloud because they're in the CHOOSE_CLOUD phase,
     * so this method applies effects on the title and on the confirm image on the CloudsTab to make them reappear
     */
    private void setCloudTab() {
        cloudTitle.setOpacity(1.0);
        ImageEffects.turnOff(confirmCloud);
        confirmCloud.setOpacity(1.0);
    }

    /**
     * This method is used when the player doesn't have to select any Clouds because they're not in the CHOOSE_CLOUD phase,
     * so this method applies effects on the title and on the confirm image on the CloudsTab to make them disappear
     */
    private void resetCloudTab() {
        cloudTitle.setOpacity(0.0);
        confirmCloud.setOpacity(0.0);
    }

    /**
     * This method is used in the CHOOSE_CLOUD phase and it shows to the player the available clouds
     * @param avClouds it's the list of the available clouds
     */
    public void setAvailableClouds(ArrayList<Integer> avClouds) {
        availableCloud.clear();

        cloud1up = false;
        cloud2up = false;
        cloud3up = false;
        cloud4up = false;

        for (int i = 0; i < clouds.size(); i++) {
            if (avClouds.contains(i + 1))
                availableCloud.add(true);
            else
                availableCloud.add(false);
        }

        ArrayList<ImageView> c = new ArrayList<>();
        c.add(cloud1);
        c.add(cloud2);
        c.add(cloud3);
        c.add(cloud4);

        for (int i = 0; i < clouds.size(); i++) {
            if (availableCloud.get(i)) {
                ImageEffects.turnOn(c.get(i));
                ImageEffects.addShadow(c.get(i));
            } else {
                c.get(i).setEffect(null);
                ImageEffects.turnOff(c.get(i));
            }
        }
    }

    // CHARACTERS -----------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This method can be used in all of the turn's phases and it chooses the charaacter number 1
     */
    public void selectCharacter1() {
        if (checkUsableCharacter())
            char1up = selectCharacter(char1available, char1up, character1, 1);
    }

    /**
     * This method can be used in all of the turn's phases and it chooses the charaacter number 2
     */
    public void selectCharacter2() {
        if (checkUsableCharacter())
            char2up = selectCharacter(char2available, char2up, character2, 2);
    }

    /**
     * This method can be used in all of the turn's phases and it chooses the charaacter number 3
     */
    public void selectCharacter3() {
        if (checkUsableCharacter())
            char3up = selectCharacter(char3available, char3up, character3, 3);
    }

    /**
     * This method is used when the player selects the 1st character, scaling it
     */
    public void scaleCharacter1() {
        character1.setScaleX(1.1);
        character1.setScaleY(1.1);
    }

    /**
     * This method is used when the player selects the 2nd character, scaling it
     */
    public void scaleCharacter2() {
        character2.setScaleX(1.1);
        character2.setScaleY(1.1);
    }

    /**
     * This method is used when the player selects the 3rd character, scaling it
     */
    public void scaleCharacter3() {
        character3.setScaleX(1.1);
        character3.setScaleY(1.1);
    }

    /**
     * This method is used to set an effect when you press the mouse button on the corresponding character image
     */
    public void confirmCharacterOn() {
        if (checkUsableCharacter() && !activeCharacter) {
            if (chosenCharacter != 0) {
                useCharacterButton.setScaleX(0.8);
                useCharacterButton.setScaleY(0.8);
            }
        }
    }

    /**
     * This method is used to set an effect when you release the mouse button on the corresponding character image
     * then it calls the method selectConfirmCharacter()
     */
    public void confirmCharacterOff() {
        if (checkUsableCharacter() && !activeCharacter) {
            if (chosenCharacter != 0) {
                useCharacterButton.setScaleX(1.0);
                useCharacterButton.setScaleY(1.0);
                selectConfirmCharacter();
            }
        }
    }

    /**
     * This method is used to activate the effect of the selected character
     */
    private void selectConfirmCharacter() {
        Player p = null;
        for (Player pl : players) {
            if (pl.getNickname().equals(nickname))
                p = pl;
        }

        if (characters[chosenCharacter - 1].getCost() <= p.getCoins() && checkValidCharacter(chosenCharacter)) {
            activeCharacter = true;
            phase = Status.USE_CHARACTER;
            setCharacterEffectTab(chosenCharacter);
            for (Timeline t : entranceBlink) {
                if (t != null)
                    t.stop();
            }
            for (ImageView i : board1Entrance) {
                i.setEffect(null);
            }
        } else if (characters[chosenCharacter - 1].getCost() > p.getCoins()) {
            character_effect_text.setText("You do not have enough coins");
        } else if (!checkValidCharacter(chosenCharacter)) {
            character_effect_text.setText("You can not use this character");
        }
    }

    /**
     * This method is used to set the id of every character
     * @param id1 it's the id of the 1st character
     * @param id2 it's the id of the 2nd character
     * @param id3 it's the id of the 3rd character
     */
    private void setCharactersId(int id1, int id2, int id3) {
        this.char1Id = id1;
        this.char2Id = id2;
        this.char3Id = id3;
    }

    /**
     * This method is used to set the corresponding images to the characters
     */
    private void setCharactersImage() {
        character1.setImage(new Image(getClass().getClassLoader().getResource(getCharacterImage(char1Id)).toString()));
        character2.setImage(new Image(getClass().getClassLoader().getResource(getCharacterImage(char2Id)).toString()));
        character3.setImage(new Image(getClass().getClassLoader().getResource(getCharacterImage(char3Id)).toString()));
    }

    /**
     * This method is used to apply effects on the image of the currently selected character
     * @param available indicates if a character can be selected
     * @param up indicates if it is currently selected
     * @param c it's the image of the currently selected character
     * @param chosen it's the id of the selected character
     * @return true if the just selected character wasn't already selected before the call of this method, false in every other case
     */
    private boolean selectCharacter(boolean available, boolean up, ImageView c, int chosen) {
        if (available && !activeCharacter) {
            if (!up) {
                resizeCharacter();
                c.setScaleX(1.1);
                c.setScaleY(1.1);
                ImageEffects.addWhiteGlow(c);
                chosenCharacter = chosen;
                updateCharacterBox();
                ImageEffects.addLightBluShadow(useCharacterButton);
                return true;
            } else {
                resizeCharacter();
                chosenCharacter = 0;
                ImageEffects.turnOff(useCharacterButton);
                updateCharacterBox();
                return false;
            }
        }
        return false;
    }

    /**
     * This method is used to resize the image of the currently selected character
     */
    private void resizeCharacter() {
        if (char1up) {
            char1up = ImageEffects.zoomOut(character1);
            ImageEffects.addShadow(character1);
        } else if (char2up) {
            char2up = ImageEffects.zoomOut(character2);
            ImageEffects.addShadow(character2);
        } else if (char3up) {
            char3up = ImageEffects.zoomOut(character3);
            ImageEffects.addShadow(character3);
        }

        if (!checkUsableCharacter()) {
            ImageEffects.turnOff(character1);
            ImageEffects.turnOff(character2);
            ImageEffects.turnOff(character3);
            for (ImageView i : character1Elems)
                ImageEffects.turnOff(i);
            for (ImageView i : character2Elems)
                ImageEffects.turnOff(i);
            for (ImageView i : character3Elems)
                ImageEffects.turnOff(i);
            ImageEffects.turnOff(character1coin);
            ImageEffects.turnOff(character2coin);
            ImageEffects.turnOff(character3coin);

            chosenCharacter = 0;
            updateCharacterBox();
            ImageEffects.turnOff(useCharacterButton);
            useCharacterButton.setOpacity(0.0);

        } else {
            ImageEffects.turnOn(character1);
            ImageEffects.turnOn(character2);
            ImageEffects.turnOn(character3);
            ImageEffects.addShadow(character1);
            ImageEffects.addShadow(character2);
            ImageEffects.addShadow(character3);
            for (ImageView i : character1Elems) {
                ImageEffects.turnOn(i);
                ImageEffects.addShadow(i);
                i.setOpacity(1);
            }
            for (ImageView i : character2Elems) {
                ImageEffects.turnOn(i);
                ImageEffects.addShadow(i);
                i.setOpacity(1);
            }
            for (ImageView i : character3Elems) {
                ImageEffects.turnOn(i);
                ImageEffects.addShadow(i);
                i.setOpacity(1);
            }

            ImageEffects.turnOn(character1coin);
            ImageEffects.turnOn(character2coin);
            ImageEffects.turnOn(character3coin);
            ImageEffects.addShadow(character1coin);
            ImageEffects.addShadow(character2coin);
            ImageEffects.addShadow(character3coin);

            chosenCharacter = 0;
            updateCharacterBox();
            ImageEffects.turnOff(useCharacterButton);
            useCharacterButton.setOpacity(1.0);
        }
    }

    /**
     * This method is used to update the text representing the effect of the currently selected character
     */
    private void updateCharacterBox() {
        switch (chosenCharacter) {
            case 0:
                characterBox.setOpacity(0.0);
                character_effect_text.setOpacity(0.0);
                break;
            case 1:
                characterBox.setOpacity(1.0);
                character_effect_text.setOpacity(1.0);
                character_effect_text.setText(getCharacterEffect(char1Id));
                break;
            case 2:
                characterBox.setOpacity(1.0);
                character_effect_text.setOpacity(1.0);
                character_effect_text.setText(getCharacterEffect(char2Id));
                break;
            case 3:
                characterBox.setOpacity(1.0);
                character_effect_text.setOpacity(1.0);
                character_effect_text.setText(getCharacterEffect(char3Id));
                break;
        }
    }

    /**
     * @param id it's the id of the selected character
     * @return the effect of the character
     */
    public String getCharacterEffect(int id) {
        String effect = "";
        switch (id) {
            case 1:
                effect = "Take 1 Student from this card and place it on an Island of your choice.\n\nThen, draw a new Student from the Bag and place it on this card.";
                break;
            case 2:
                effect = "During this turn, you take control of any number of Professors even if you have the same number of\nStudents as the player who currently controls them.";
                break;
            case 3:
                effect = "Choose an Island and resolve the Island as if Mother Nature had ended her movement there.\n\nMother Nature will still move and the Island where she ends her movement will also be resolved.";
                break;
            case 4:
                effect = "You may move Mother Nature up to 2 additional Islands than is indicated by the Assistant\ncard you’ve played.";
                break;
            case 5:
                effect = "Place a No Entry tile on an Island of your choice.\n\nThe first time Mother Nature ends her movement there, put the No Entry tile back onto this card and do not calculate influence on that Island, or place any Towers.";
                break;
            case 6:
                effect = "When resolving a Conquering on an Island, Towers do not count towards influence.";
                break;
            case 7:
                effect = "You may take up to 3 Students from this card and replace them with the same number of Students\nfrom your Entrance.";
                break;
            case 8:
                effect = "During the influence calculations this turn, you count as having 2 more influence.";
                break;
            case 9:
                effect = "Choose a color of Student: during the influence calculation this turn, that color adds no influence.";
                break;
            case 10:
                effect = "You may exchange up to 2 Students between your Entrance and your Dining Room.";
                break;
            case 11:
                effect = "Take 1 Student from this card and place it in your Dining Room.\n\nThen, draw a new Student from the Bag and place it on this card.";
                break;
            case 12:
                effect = "Choose a type of Student: every player (including yourself) must return 3 Students of that type from their Dining Room to the Bag.\n\nIf any player has fewer then 3 Students of that type, return as many Students as they have. ";
                break;
        }
        return effect;
    }

    /**
     * @param id it's the id of the selected character
     * @return the image of the character
     */
    private String getCharacterImage(int id) {
        String image = null;
        switch (id) {
            case 1:
                image = "images/characters/character_1.jpg";
                break;
            case 2:
                image = "images/characters/character_2.jpg";
                break;
            case 3:
                image = "images/characters/character_3.jpg";
                break;
            case 4:
                image = "images/characters/character_4.jpg";
                break;
            case 5:
                image = "images/characters/character_5.jpg";
                break;
            case 6:
                image = "images/characters/character_6.jpg";
                break;
            case 7:
                image = "images/characters/character_7.jpg";
                break;
            case 8:
                image = "images/characters/character_8.jpg";
                break;
            case 9:
                image = "images/characters/character_9.jpg";
                break;
            case 10:
                image = "images/characters/character_10.jpg";
                break;
            case 11:
                image = "images/characters/character_11.jpg";
                break;
            case 12:
                image = "images/characters/character_12.jpg";
                break;
        }
        return image;
    }

    /**
     * This method checks if a character can be used or not
     * @return true if it can be used
     */
    private boolean checkUsableCharacter() {
        boolean ok = true;
        if (!expertMode)
            ok = false;
        if (usedCharacter)
            ok = false;
        if (phase == Status.STANDBY || phase == Status.WAIT || phase == Status.CHOOSE_ASSISTANT)
            ok = false;
        if (phase == Status.MOVE_STUDENTS && studentsMoved != 0)
            ok = false;
        if (phase == Status.MOVE_MOTHER_NATURE) {
            for (Boolean b : mnUp) {
                if (b)
                    ok = false;
            }
        }
        if (phase == Status.CHOOSE_CLOUD) {
            if (cloud1up || cloud2up || cloud3up || cloud4up)
                ok = false;
        }
        return ok;
    }

    /**
     * This method is used to put student pawns or noEntry cards on a character
     * @param id it's the id of the character
     * @param chara it's the index of the chosen character
     */
    private void setCharacterPawns(int id, int chara) {
        ArrayList<ArrayList<ImageView>> elems = new ArrayList<>();
        elems.add(character1Elems);
        elems.add(character2Elems);
        elems.add(character3Elems);

        switch (id) {
            case 1:
                for (int i = 0; i < ((Monk) characters[chara]).getStudents().size(); i++) {
                    elems.get(chara).get(i).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(((Monk) characters[chara]).getStudents().get(i))).toString()));
                    ImageEffects.addShadow(elems.get(chara).get(i));
                }
                break;
            case 2:
            case 3:
            case 4:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
                break;
            case 5:
                for (int i = 0; i < ((HerbsGranny) characters[chara]).getNoEntry(); i++) {
                    elems.get(chara).get(i).setImage(new Image(getClass().getClassLoader().getResource("images/game/no_entry.png").toString()));
                    ImageEffects.addShadow(elems.get(chara).get(i));
                }
                break;
            case 7:
                for (int i = 0; i < ((Jester) characters[chara]).getStudents().size(); i++) {
                    elems.get(chara).get(i).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(((Jester) characters[chara]).getStudents().get(i))).toString()));
                    ImageEffects.addShadow(elems.get(chara).get(i));
                }
                break;
            case 11:
                for (int i = 0; i < ((SpoiledPrincess) characters[chara]).getStudents().size(); i++) {
                    elems.get(chara).get(i).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(((SpoiledPrincess) characters[chara]).getStudents().get(i))).toString()));
                    ImageEffects.addShadow(elems.get(chara).get(i));
                }
                break;
        }
        if (characters[chara].isCoin()) {
            switch (chara) {
                case 0:
                    character1coin.setOpacity(1.0);
                    ImageEffects.addShadow(character1coin);
                    break;
                case 1:
                    character2coin.setOpacity(1.0);
                    ImageEffects.addShadow(character2coin);
                    break;
                case 2:
                    character3coin.setOpacity(1.0);
                    ImageEffects.addShadow(character3coin);
                    break;
            }
        } else {
            switch (chara) {
                case 0:
                    character1coin.setOpacity(0.0);
                    break;
                case 1:
                    character2coin.setOpacity(0.0);
                    break;
                case 2:
                    character3coin.setOpacity(0.0);
                    break;
            }
        }
        resizeCharacter();
    }

    /**
     * This method is used to check if a character is valid
     * @param index it's the index of a character
     * @return true if it's valid
     */
    private boolean checkValidCharacter(int index) {
        boolean valid = true;
        switch (characters[index - 1].getId()) {
            case 1:
                if (((Monk) characters[index - 1]).getStudents().size() == 0)
                    valid = false;
                break;
            case 2:
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 12:
                break;
            case 4:
                if (phase == Status.CHOOSE_CLOUD)
                    valid = false;
                break;
            case 7:
                int numStudEnt = 0;
                int eDim;
                Board b = getPlayerBoard();
                if (players.size() == 3)
                    eDim = 9;
                else
                    eDim = 7;
                for (int i = 0; i < eDim; i++) {
                    if (b.getEntrance()[i] != null) {
                        numStudEnt++;
                    }
                }
                if (((Jester) characters[index - 1]).getStudents().size() == 0 || numStudEnt == 0) {
                    valid = false;
                } else {
                    boolean ok = false;
                    for (Student sc : ((Jester) characters[index - 1]).getStudents()) {
                        for (Student se : b.getEntrance()) {
                            if (se != null) {
                                if (sc != se)
                                    ok = true;
                            }
                        }
                    }
                    valid = ok;
                }
                break;
            case 10:
                Board b10 = null;
                for (Player pl : players) {
                    if (pl.getNickname().equals(nickname))
                        b10 = pl.getBoard();
                }
                int totSt = b10.getNumberOfStudents(Student.RED) + b10.getNumberOfStudents(Student.BLUE) + b10.getNumberOfStudents(Student.GREEN) + b10.getNumberOfStudents(Student.PINK) + b10.getNumberOfStudents(Student.YELLOW);
                boolean tempOk = false;
                int entDim;
                if (players.size() == 3)
                    entDim = 9;
                else
                    entDim = 7;
                for (int i = 0; i < entDim; i++) {
                    if (b10.getEntrance()[i] != null) {
                        if (b10.getNumberOfStudents(b10.getEntrance()[i]) < 10 && (totSt - b10.getNumberOfStudents(b10.getEntrance()[i])) > 0) {
                            tempOk = true;
                        }
                    }
                }
                if (totSt == 0 || !tempOk)
                    valid = false;
                break;
            case 11:
                Board b11 = null;
                for (Player pl : players) {
                    if (pl.getNickname().equals(nickname))
                        b11 = pl.getBoard();
                }

                boolean full = true;
                for (Student stud : ((SpoiledPrincess) characters[index - 1]).getStudents()) {
                    if (b11.getNumberOfStudents(stud) < 10)
                        full = false;
                }
                valid = !full;
                break;
        }
        return valid;
    }

    /**
     * This method is used to manage the effect of a character:
     * if it requires choices from the player, it opens a corresponding tab
     * otherwise, it sends the index of the selected character to the Server via NetworkHandler
     * @param index it's the index of the chosen character
     */
    private void setCharacterEffectTab(int index) {
        switch (characters[index - 1].getId()) {
            case 1:
                setCharacter1Tab(index);
                break;
            case 2:
            case 6:
            case 8:
                ArrayList<Parameter> p2 = new ArrayList<>();
                UseCharacterMessage ucm2 = new UseCharacterMessage(index - 1, p2);
                getNetworkHandler().update(ucm2);
                usedCharacter = true;
                activeCharacter = false;
                chosenCharacter = 0;
                tabPane.getSelectionModel().select(tableTab);
                break;
            case 3:
            case 5:
                setCharacter3_5Tab();
                break;
            case 4:
                setCharacter4Tab();
                break;
            case 7:
                setCharacter7Tab(index);
                break;
            case 9:
            case 12:
                setCharacter9_12Tab();
                break;
            case 10:
                setCharacter10Tab();
                break;
            case 11:
                setCharacter11Tab(index);
                break;
        }
    }

    // CHARACTER 1 -----------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This method is used when the effect of the character 1 is active and the player selects the 1st student on the card
     */
    public void selectCharacter1EffectStudent1() {
        if (character1EffectStudentsAvailable.get(0))
            character1EffectStudentsUp.set(0, selectCharacter1EffectStudent(character1EffectStudentsAvailable.get(0), character1EffectStudentsUp.get(0), character1EffectStudent1, 1));
    }

    /**
     * This method is used when the effect of the character 1 is active and the player selects the 2nd student on the card
     */
    public void selectCharacter1EffectStudent2() {
        if (character1EffectStudentsAvailable.get(1))
            character1EffectStudentsUp.set(1, selectCharacter1EffectStudent(character1EffectStudentsAvailable.get(1), character1EffectStudentsUp.get(1), character1EffectStudent2, 2));
    }

    /**
     * This method is used when the effect of the character 1 is active and the player selects the 3rd student on the card
     */
    public void selectCharacter1EffectStudent3() {
        if (character1EffectStudentsAvailable.get(2))
            character1EffectStudentsUp.set(2, selectCharacter1EffectStudent(character1EffectStudentsAvailable.get(2), character1EffectStudentsUp.get(2), character1EffectStudent3, 3));
    }

    /**
     * This method is used when the effect of the character 1 is active and the player selects the 4th student on the card
     */
    public void selectCharacter1EffectStudent4() {
        if (character1EffectStudentsAvailable.get(3))
            character1EffectStudentsUp.set(3, selectCharacter1EffectStudent(character1EffectStudentsAvailable.get(3), character1EffectStudentsUp.get(3), character1EffectStudent4, 4));
    }

    /**
     * This method is used when the effect of the character 1 is active and the player selects the 1st island
     */
    public void selectCharacter1EffectIsland1() {
        character1EffectIslandsUp.set(0, selectCharacter1EffectIsland(character1EffectIslandsAvailable.get(0), character1EffectIslandsUp.get(0), character1EffectIsland1, 1));
    }

    /**
     * This method is used when the effect of the character 1 is active and the player selects the 2nd island
     */
    public void selectCharacter1EffectIsland2() {
        character1EffectIslandsUp.set(1, selectCharacter1EffectIsland(character1EffectIslandsAvailable.get(1), character1EffectIslandsUp.get(1), character1EffectIsland2, 2));
    }

    /**
     * This method is used when the effect of the character 1 is active and the player selects the 3rd island
     */
    public void selectCharacter1EffectIsland3() {
        character1EffectIslandsUp.set(2, selectCharacter1EffectIsland(character1EffectIslandsAvailable.get(2), character1EffectIslandsUp.get(2), character1EffectIsland3, 3));
    }

    /**
     * This method is used when the effect of the character 1 is active and the player selects the 4th island
     */
    public void selectCharacter1EffectIsland4() {
        character1EffectIslandsUp.set(3, selectCharacter1EffectIsland(character1EffectIslandsAvailable.get(3), character1EffectIslandsUp.get(3), character1EffectIsland4, 4));
    }

    /**
     * This method is used when the effect of the character 1 is active and the player selects the 5th island
     */
    public void selectCharacter1EffectIsland5() {
        character1EffectIslandsUp.set(4, selectCharacter1EffectIsland(character1EffectIslandsAvailable.get(4), character1EffectIslandsUp.get(4), character1EffectIsland5, 5));
    }

    /**
     * This method is used when the effect of the character 1 is active and the player selects the 6th island
     */
    public void selectCharacter1EffectIsland6() {
        character1EffectIslandsUp.set(5, selectCharacter1EffectIsland(character1EffectIslandsAvailable.get(5), character1EffectIslandsUp.get(5), character1EffectIsland6, 6));
    }

    /**
     * This method is used when the effect of the character 1 is active and the player selects the 7th island
     */
    public void selectCharacter1EffectIsland7() {
        character1EffectIslandsUp.set(6, selectCharacter1EffectIsland(character1EffectIslandsAvailable.get(6), character1EffectIslandsUp.get(6), character1EffectIsland7, 7));
    }

    /**
     * This method is used when the effect of the character 1 is active and the player selects the 8th island
     */
    public void selectCharacter1EffectIsland8() {
        character1EffectIslandsUp.set(7, selectCharacter1EffectIsland(character1EffectIslandsAvailable.get(7), character1EffectIslandsUp.get(7), character1EffectIsland8, 8));
    }

    /**
     * This method is used when the effect of the character 1 is active and the player selects the 9th island
     */
    public void selectCharacter1EffectIsland9() {
        character1EffectIslandsUp.set(8, selectCharacter1EffectIsland(character1EffectIslandsAvailable.get(8), character1EffectIslandsUp.get(8), character1EffectIsland9, 9));
    }

    /**
     * This method is used when the effect of the character 1 is active and the player selects the 10th island
     */
    public void selectCharacter1EffectIsland10() {
        character1EffectIslandsUp.set(9, selectCharacter1EffectIsland(character1EffectIslandsAvailable.get(9), character1EffectIslandsUp.get(9), character1EffectIsland10, 10));
    }

    /**
     * This method is used when the effect of the character 1 is active and the player selects the 11th island
     */
    public void selectCharacter1EffectIsland11() {
        character1EffectIslandsUp.set(10, selectCharacter1EffectIsland(character1EffectIslandsAvailable.get(10), character1EffectIslandsUp.get(10), character1EffectIsland11, 11));
    }

    /**
     * This method is used when the effect of the character 1 is active and the player selects the 12th island
     */
    public void selectCharacter1EffectIsland12() {
        character1EffectIslandsUp.set(11, selectCharacter1EffectIsland(character1EffectIslandsAvailable.get(11), character1EffectIslandsUp.get(11), character1EffectIsland12, 12));
    }

    /**
     * This method is used to set an effect when you press the mouse button on the confirm image
     */
    public void confirmCharacter1On() {
        if (chosenCharacter1EffectStudent != 0 && chosenCharacter1EffectIsland != 0 && !confirmCharacter1On) {
            character1EffectConfirmButton.setScaleX(0.8);
            character1EffectConfirmButton.setScaleY(0.8);
        }
    }

    /**
     * This method is used to set an effect when you release the mouse button on the confirm image
     * then it calls the method confirmCharacter1()
     */
    public void confirmCharacter1Off() {
        if (chosenCharacter1EffectStudent != 0 && chosenCharacter1EffectIsland != 0 && !confirmCharacter1On) {
            character1EffectConfirmButton.setScaleX(1.0);
            character1EffectConfirmButton.setScaleY(1.0);
            confirmCharacter1();
        }
    }

    /**
     * This method sends a message to confirm the choices made on the 1st Character to the Server via NetworkHandler
     */
    private void confirmCharacter1() {
        confirmCharacter1On = true;
        ArrayList<Parameter> p1 = new ArrayList<>();
        Parameter arp = new IntegerParameter("archipelago index", chosenCharacter1EffectIsland - 1);
        Parameter stp = new IntegerParameter("student index", chosenCharacter1EffectStudent - 1);
        p1.add(arp);
        p1.add(stp);
        UseCharacterMessage ucm1 = new UseCharacterMessage(chosenCharacter - 1, p1);
        getNetworkHandler().update(ucm1);
        usedCharacter = true;
        activeCharacter = false;
        chosenCharacter = 0;
        resizeCharacter1EffectStudents();
        resizeCharacter1EffectIslands();
        tabPane.getSelectionModel().select(tableTab);
        tabPane.getTabs().remove(character1EffectTab1);
    }

    /**
     * This method applies effects on the image of the selected Character1's student and verifies if the chosen student is selectable or not
     * @param available indicates if a character's student can be selected
     * @param up indicates if it is currently selected
     * @param s it's the image of the character's student that the player wants to select
     * @param chosen it's the id of the selected character
     * @return true if the just selected Character1's student wasn't already selected before the call of this method, false in every other case
     */
    private boolean selectCharacter1EffectStudent(boolean available, boolean up, ImageView s, int chosen) {
        if (available && !confirmCharacter1On) {
            if (!up) {
                resizeCharacter1EffectStudents();
                s.setScaleX(1.1);
                s.setScaleY(1.1);
                ImageEffects.addWhiteGlow(s);
                chosenCharacter1EffectStudent = chosen;
                if (chosenCharacter1EffectIsland != 0)
                    ImageEffects.addLightBluShadow(character1EffectConfirmButton);
                return true;
            } else {
                resizeCharacter1EffectStudents();
                chosenCharacter1EffectStudent = 0;
                ImageEffects.turnOff(character1EffectConfirmButton);
                return false;
            }
        }
        return false;
    }

    /**
     * This method is used to resize the image of the previously selected Character1's student
     */
    private void resizeCharacter1EffectStudents() {
        for (int i = 0; i < 4; i++) {
            if (character1EffectStudentsUp.get(i)) {
                character1EffectStudentsUp.set(i, ImageEffects.zoomOut(character1EffectStudents.get(i)));
                ImageEffects.addShadow(character1EffectStudents.get(i));
            }
        }
    }

    /**
     * This method applies effects on the image of the selected Character1's island and verifies if the chosen island is selectable or not
     * @param available indicates if a character's island can be selected
     * @param up indicates if it is currently selected
     * @param i it's the image of the character's island that the player wants to select
     * @param chosen it's the id of the selected island
     * @return true if the just selected Character1's island wasn't already selected before the call of this method, false in every other case
     */
    private boolean selectCharacter1EffectIsland(boolean available, boolean up, ImageView i, int chosen) {
        if (available && !confirmCharacter1On) {
            if (!up) {
                resizeCharacter1EffectIslands();
                i.setScaleX(1.1);
                i.setScaleY(1.1);
                ImageEffects.addWhiteGlow(i);
                chosenCharacter1EffectIsland = chosen;
                if (chosenCharacter1EffectStudent != 0)
                    ImageEffects.addLightBluShadow(character1EffectConfirmButton);
                return true;
            } else {
                resizeCharacter1EffectIslands();
                chosenCharacter1EffectIsland = 0;
                ImageEffects.turnOff(character1EffectConfirmButton);
                return false;
            }
        }
        return false;
    }

    /**
     * This method is used to resize the image of the previously selected Character1's island
     */
    private void resizeCharacter1EffectIslands() {
        for (int i = 0; i < 12; i++) {
            if (character1EffectIslandsUp.get(i)) {
                character1EffectIslandsUp.set(i, ImageEffects.zoomOut(character1EffectIslands.get(i)));
                ImageEffects.addShadow(character1EffectIslands.get(i));
            }
        }
    }

    /**
     * This method is used to set the tab associated to the 1st Character
     * @param index it's the index of the Character
     */
    private void setCharacter1Tab(int index) {
        character1EffectStudents = new ArrayList<>();
        character1EffectStudents.add(character1EffectStudent1);
        character1EffectStudents.add(character1EffectStudent2);
        character1EffectStudents.add(character1EffectStudent3);
        character1EffectStudents.add(character1EffectStudent4);

        character1EffectIslands = new ArrayList<>();
        character1EffectIslands.add(character1EffectIsland1);
        character1EffectIslands.add(character1EffectIsland2);
        character1EffectIslands.add(character1EffectIsland3);
        character1EffectIslands.add(character1EffectIsland4);
        character1EffectIslands.add(character1EffectIsland5);
        character1EffectIslands.add(character1EffectIsland6);
        character1EffectIslands.add(character1EffectIsland7);
        character1EffectIslands.add(character1EffectIsland8);
        character1EffectIslands.add(character1EffectIsland9);
        character1EffectIslands.add(character1EffectIsland10);
        character1EffectIslands.add(character1EffectIsland11);
        character1EffectIslands.add(character1EffectIsland12);

        character1EffectStudentsUp = new ArrayList<>();
        character1EffectStudentsUp.add(false);
        character1EffectStudentsUp.add(false);
        character1EffectStudentsUp.add(false);
        character1EffectStudentsUp.add(false);

        character1EffectStudentsAvailable = new ArrayList<>();
        character1EffectStudentsAvailable.add(true);
        character1EffectStudentsAvailable.add(true);
        character1EffectStudentsAvailable.add(true);
        character1EffectStudentsAvailable.add(true);

        character1EffectIslandsUp = new ArrayList<>();
        for (int i = 0; i < 12; i++)
            character1EffectIslandsUp.add(false);

        character1EffectIslandsAvailable = new ArrayList<>();
        for (int i = 0; i < archipelagos.size(); i++)
            character1EffectIslandsAvailable.add(true);

        for (int i = archipelagos.size(); i < 12; i++) {
            character1EffectIslandsAvailable.add(false);
            ImageEffects.turnOff(character1EffectIslands.get(i));
        }

        for (int i = 0; i < ((Monk) characters[index - 1]).getStudents().size(); i++) {
            character1EffectStudents.get(i).setImage(new Image(getClass().getClassLoader().getResource(getStudentFlatImage(((Monk) characters[index - 1]).getStudents().get(i))).toString()));
        }

        for (int i = ((Monk) characters[index - 1]).getStudents().size(); i < 4; i++) {
            character1EffectStudents.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/black_disc_flat.png").toString()));
            character1EffectStudentsAvailable.set(i, false);
        }

        confirmCharacter1On = false;
        chosenCharacter1EffectStudent = 0;
        chosenCharacter1EffectIsland = 0;

        resetTabs();
        tabPane.getTabs().add(character1EffectTab1);
        tabPane.getSelectionModel().select(character1EffectTab1);
    }

    // CHARACTER 3 - 5 -----------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This method is used when the effect of the characters 3 and 5 is active and the player selects the 1st island
     */
    public void selectCharacter3_5EffectIsland1() {
        character3_5EffectIslandsUp.set(0, selectCharacter3_5EffectIsland(character3_5EffectIslandsAvailable.get(0), character3_5EffectIslandsUp.get(0), character3_5EffectIsland1, 1));
    }

    /**
     * This method is used when the effect of the characters 3 and 5 is active and the player selects the 2nd island
     */
    public void selectCharacter3_5EffectIsland2() {
        character3_5EffectIslandsUp.set(1, selectCharacter3_5EffectIsland(character3_5EffectIslandsAvailable.get(1), character3_5EffectIslandsUp.get(1), character3_5EffectIsland2, 2));
    }

    /**
     * This method is used when the effect of the characters 3 and 5 is active and the player selects the 3rd island
     */
    public void selectCharacter3_5EffectIsland3() {
        character3_5EffectIslandsUp.set(2, selectCharacter3_5EffectIsland(character3_5EffectIslandsAvailable.get(2), character3_5EffectIslandsUp.get(2), character3_5EffectIsland3, 3));
    }

    /**
     * This method is used when the effect of the characters 3 and 5 is active and the player selects the 4th island
     */
    public void selectCharacter3_5EffectIsland4() {
        character3_5EffectIslandsUp.set(3, selectCharacter3_5EffectIsland(character3_5EffectIslandsAvailable.get(3), character3_5EffectIslandsUp.get(3), character3_5EffectIsland4, 4));
    }

    /**
     * This method is used when the effect of the characters 3 and 5 is active and the player selects the 5th island
     */
    public void selectCharacter3_5EffectIsland5() {
        character3_5EffectIslandsUp.set(4, selectCharacter3_5EffectIsland(character3_5EffectIslandsAvailable.get(4), character3_5EffectIslandsUp.get(4), character3_5EffectIsland5, 5));
    }

    /**
     * This method is used when the effect of the characters 3 and 5 is active and the player selects the 6th island
     */
    public void selectCharacter3_5EffectIsland6() {
        character3_5EffectIslandsUp.set(5, selectCharacter3_5EffectIsland(character3_5EffectIslandsAvailable.get(5), character3_5EffectIslandsUp.get(5), character3_5EffectIsland6, 6));
    }

    /**
     * This method is used when the effect of the characters 3 and 5 is active and the player selects the 7th island
     */
    public void selectCharacter3_5EffectIsland7() {
        character3_5EffectIslandsUp.set(6, selectCharacter3_5EffectIsland(character3_5EffectIslandsAvailable.get(6), character3_5EffectIslandsUp.get(6), character3_5EffectIsland7, 7));
    }

    /**
     * This method is used when the effect of the characters 3 and 5 is active and the player selects the 8th island
     */
    public void selectCharacter3_5EffectIsland8() {
        character3_5EffectIslandsUp.set(7, selectCharacter3_5EffectIsland(character3_5EffectIslandsAvailable.get(7), character3_5EffectIslandsUp.get(7), character3_5EffectIsland8, 8));
    }

    /**
     * This method is used when the effect of the characters 3 and 5 is active and the player selects the 9th island
     */
    public void selectCharacter3_5EffectIsland9() {
        character3_5EffectIslandsUp.set(8, selectCharacter3_5EffectIsland(character3_5EffectIslandsAvailable.get(8), character3_5EffectIslandsUp.get(8), character3_5EffectIsland9, 9));
    }

    /**
     * This method is used when the effect of the characters 3 and 5 is active and the player selects the 10th island
     */
    public void selectCharacter3_5EffectIsland10() {
        character3_5EffectIslandsUp.set(9, selectCharacter3_5EffectIsland(character3_5EffectIslandsAvailable.get(9), character3_5EffectIslandsUp.get(9), character3_5EffectIsland10, 10));
    }

    /**
     * This method is used when the effect of the characters 3 and 5 is active and the player selects the 11th island
     */
    public void selectCharacter3_5EffectIsland11() {
        character3_5EffectIslandsUp.set(10, selectCharacter3_5EffectIsland(character3_5EffectIslandsAvailable.get(10), character3_5EffectIslandsUp.get(10), character3_5EffectIsland11, 11));
    }

    /**
     * This method is used when the effect of the characters 3 and 5 is active and the player selects the 12th island
     */
    public void selectCharacter3_5EffectIsland12() {
        character3_5EffectIslandsUp.set(11, selectCharacter3_5EffectIsland(character3_5EffectIslandsAvailable.get(11), character3_5EffectIslandsUp.get(11), character3_5EffectIsland12, 12));
    }

    /**
     * This method is used to set an effect when you press the mouse button on the confirm image
     */
    public void confirmCharacter3_5On() {
        if (chosenCharacter3_5EffectIsland != 0 && !confirmCharacter3_5On) {
            character3_5EffectConfirmButton.setScaleX(0.8);
            character3_5EffectConfirmButton.setScaleY(0.8);
        }
    }

    /**
     * This method is used to set an effect when you release the mouse button on the confirm image
     * then it calls the method confirmCharacter3_5()
     */
    public void confirmCharacter3_5Off() {
        if (chosenCharacter3_5EffectIsland != 0 && !confirmCharacter3_5On) {
            character3_5EffectConfirmButton.setScaleX(1.0);
            character3_5EffectConfirmButton.setScaleY(1.0);
            confirmCharacter3_5();
        }
    }

    /**
     * This method sends a message to confirm the choices made on the 3rd or the 5th Character to the Server via NetworkHandler
     */
    private void confirmCharacter3_5() {
        confirmCharacter1On = true;
        ArrayList<Parameter> p3 = new ArrayList<>();
        Parameter arp3 = new IntegerParameter("archipelago index", chosenCharacter3_5EffectIsland - 1);
        p3.add(arp3);
        UseCharacterMessage ucm3 = new UseCharacterMessage(chosenCharacter - 1, p3);
        getNetworkHandler().update(ucm3);
        usedCharacter = true;
        activeCharacter = false;
        chosenCharacter = 0;
        resizeCharacter3_5EffectIslands();
        tabPane.getSelectionModel().select(tableTab);
        tabPane.getTabs().remove(character3_5EffectTab);
    }

    /**
     * This method applies effects on the image of the selected Character3 or Character5's island and verifies if the chosen island is selectable or not
     * @param available indicates if a character's island can be selected
     * @param up indicates if it is currently selected
     * @param i it's the image of the character's island that the player wants to select
     * @param chosen it's the id of the selected island
     * @return true if the just selected Character3 or Character5's island wasn't already selected before the call of this method, false in every other case
     */
    private boolean selectCharacter3_5EffectIsland(boolean available, boolean up, ImageView i, int chosen) {
        if (available && !confirmCharacter3_5On) {
            if (!up) {
                resizeCharacter3_5EffectIslands();
                i.setScaleX(1.1);
                i.setScaleY(1.1);
                ImageEffects.addWhiteGlow(i);
                chosenCharacter3_5EffectIsland = chosen;
                ImageEffects.addLightBluShadow(character3_5EffectConfirmButton);
                return true;
            } else {
                resizeCharacter3_5EffectIslands();
                chosenCharacter3_5EffectIsland = 0;
                ImageEffects.turnOff(character3_5EffectConfirmButton);
                return false;
            }
        }
        return false;
    }

    /**
     * This method is used to resize the image of the previously selected Character1 or Character5's island
     */
    private void resizeCharacter3_5EffectIslands() {
        for (int i = 0; i < 12; i++) {
            if (character3_5EffectIslandsUp.get(i)) {
                character3_5EffectIslandsUp.set(i, ImageEffects.zoomOut(character3_5EffectIslands.get(i)));
                ImageEffects.addShadow(character3_5EffectIslands.get(i));
            }
        }
    }

    /**
     * This method is used to set the tab associated to the 3rd Character and 5th Character
     */
    private void setCharacter3_5Tab() {
        character3_5EffectIslands = new ArrayList<>();
        character3_5EffectIslands.add(character3_5EffectIsland1);
        character3_5EffectIslands.add(character3_5EffectIsland2);
        character3_5EffectIslands.add(character3_5EffectIsland3);
        character3_5EffectIslands.add(character3_5EffectIsland4);
        character3_5EffectIslands.add(character3_5EffectIsland5);
        character3_5EffectIslands.add(character3_5EffectIsland6);
        character3_5EffectIslands.add(character3_5EffectIsland7);
        character3_5EffectIslands.add(character3_5EffectIsland8);
        character3_5EffectIslands.add(character3_5EffectIsland9);
        character3_5EffectIslands.add(character3_5EffectIsland10);
        character3_5EffectIslands.add(character3_5EffectIsland11);
        character3_5EffectIslands.add(character3_5EffectIsland12);

        character3_5EffectIslandsUp = new ArrayList<>();
        for (int i = 0; i < 12; i++)
            character3_5EffectIslandsUp.add(false);

        character3_5EffectIslandsAvailable = new ArrayList<>();
        for (int i = 0; i < archipelagos.size(); i++)
            character3_5EffectIslandsAvailable.add(true);

        for (int i = archipelagos.size(); i < 12; i++) {
            character3_5EffectIslandsAvailable.add(false);
            ImageEffects.turnOff(character3_5EffectIslands.get(i));
        }

        confirmCharacter3_5On = false;
        chosenCharacter3_5EffectIsland = 0;

        resetTabs();
        tabPane.getTabs().add(character3_5EffectTab);
        tabPane.getSelectionModel().select(character3_5EffectTab);
    }

    // CHARACTER 4 -----------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This method is used to make mother nature do an additional step because of the effect of the 4th Character
     */
    public void selectCharacter4EffectStep1() {
        if (!character4EffectStep1Up && !confirmCharacter4On) {
            resizeCharacter4EffectSteps();
            character4EffectStep1.setScaleX(1.1);
            character4EffectStep1.setScaleY(1.1);
            ImageEffects.addWhiteGlow(character4EffectStep1);
            chosenCharacter4EffectStep = 1;
            ImageEffects.addLightBluShadow(character4EffectConfirmButton);
            character4EffectStep1Up = true;
        } else {
            resizeCharacter4EffectSteps();
            chosenCharacter4EffectStep = 0;
            ImageEffects.turnOff(character4EffectConfirmButton);
            character4EffectStep1Up = false;
        }
    }

    /**
     * This method is used to make mother nature do two additional steps because of the effect of the 4th Character
     */
    public void selectCharacter4EffectStep2() {
        if (!character4EffectStep2Up && !confirmCharacter4On) {
            resizeCharacter4EffectSteps();
            character4EffectStep2.setScaleX(1.1);
            character4EffectStep2.setScaleY(1.1);
            ImageEffects.addWhiteGlow(character4EffectStep2);
            chosenCharacter4EffectStep = 2;
            ImageEffects.addLightBluShadow(character4EffectConfirmButton);
            character4EffectStep2Up = true;
        } else {
            resizeCharacter4EffectSteps();
            chosenCharacter4EffectStep = 0;
            ImageEffects.turnOff(character4EffectConfirmButton);
            character4EffectStep2Up = false;
        }
    }

    /**
     * This method is used to set an effect when you press the mouse button on the confirm image
     */
    public void confirmCharacter4On() {
        if (chosenCharacter4EffectStep != 0 && !confirmCharacter4On) {
            character4EffectConfirmButton.setScaleX(0.8);
            character4EffectConfirmButton.setScaleY(0.8);
        }
    }

    /**
     * This method is used to set an effect when you release the mouse button on the confirm image
     * then it calls the method confirmCharacter4()
     */
    public void confirmCharacter4Off() {
        if (chosenCharacter4EffectStep != 0 && !confirmCharacter4On) {
            character4EffectConfirmButton.setScaleX(1.0);
            character4EffectConfirmButton.setScaleY(1.0);
            confirmCharacter4();
        }
    }

    /**
     * This method sends a message to confirm the choices made on the 4th Character to the Server via NetworkHandler
     */
    private void confirmCharacter4() {
        confirmCharacter4On = true;
        ArrayList<Parameter> p4 = new ArrayList<>();
        Parameter stp4 = new IntegerParameter("steps", chosenCharacter4EffectStep);
        p4.add(stp4);
        UseCharacterMessage ucm4 = new UseCharacterMessage(chosenCharacter - 1, p4);
        getNetworkHandler().update(ucm4);
        usedCharacter = true;
        activeCharacter = false;
        chosenCharacter = 0;
        resizeCharacter4EffectSteps();
        tabPane.getSelectionModel().select(tableTab);
        tabPane.getTabs().remove(character4EffectTab);
    }

    /**
     * This method is used to resize the chosen number of steps to its normal size
     */
    private void resizeCharacter4EffectSteps() {
        if (character4EffectStep1Up) {
            ImageEffects.zoomOut(character4EffectStep1);
            ImageEffects.addShadow(character4EffectStep1);
            character4EffectStep1Up = false;
        } else if (character4EffectStep2Up) {
            ImageEffects.zoomOut(character4EffectStep2);
            ImageEffects.addShadow(character4EffectStep2);
            character4EffectStep2Up = false;
        }
    }

    /**
     * This method is used to set the tab associated to the 4th Character
     */
    private void setCharacter4Tab() {
        confirmCharacter4On = false;
        chosenCharacter4EffectStep = 0;

        resetTabs();
        tabPane.getTabs().add(character4EffectTab);
        tabPane.getSelectionModel().select(character4EffectTab);
    }

    // CHARACTER 7 -----------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This method is used when the effect of the character 7 is active and the player selects the 1st student on the card
     */
    public void selectCharacter7EffectCardStudent1() {
        if (chosenCharacter7EffectCardStudents.size() == chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectCardStudents.size() < 3) {
            character7EffectCardStudentsUp.set(0, selectCharacter7EffectCardStudent(character7EffectCardStudentsAvailable.get(0), character7EffectCardStudentsUp.get(0), character7EffectCardStudents.get(0), 1));
        }
    }

    /**
     * This method is used when the effect of the character 7 is active and the player selects the 2nd student on the card
     */
    public void selectCharacter7EffectCardStudent2() {
        if (chosenCharacter7EffectCardStudents.size() == chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectCardStudents.size() < 3) {
            character7EffectCardStudentsUp.set(1, selectCharacter7EffectCardStudent(character7EffectCardStudentsAvailable.get(1), character7EffectCardStudentsUp.get(1), character7EffectCardStudents.get(1), 2));
        }
    }

    /**
     * This method is used when the effect of the character 7 is active and the player selects the 3rd student on the card
     */
    public void selectCharacter7EffectCardStudent3() {
        if (chosenCharacter7EffectCardStudents.size() == chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectCardStudents.size() < 3) {
            character7EffectCardStudentsUp.set(2, selectCharacter7EffectCardStudent(character7EffectCardStudentsAvailable.get(2), character7EffectCardStudentsUp.get(2), character7EffectCardStudents.get(2), 3));
        }
    }

    /**
     * This method is used when the effect of the character 7 is active and the player selects the 4th student on the card
     */
    public void selectCharacter7EffectCardStudent4() {
        if (chosenCharacter7EffectCardStudents.size() == chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectCardStudents.size() < 3) {
            character7EffectCardStudentsUp.set(3, selectCharacter7EffectCardStudent(character7EffectCardStudentsAvailable.get(3), character7EffectCardStudentsUp.get(3), character7EffectCardStudents.get(3), 4));
        }
    }

    /**
     * This method is used when the effect of the character 7 is active and the player selects the 5th student on the card
     */
    public void selectCharacter7EffectCardStudent5() {
        if (chosenCharacter7EffectCardStudents.size() == chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectCardStudents.size() < 3) {
            character7EffectCardStudentsUp.set(4, selectCharacter7EffectCardStudent(character7EffectCardStudentsAvailable.get(4), character7EffectCardStudentsUp.get(4), character7EffectCardStudents.get(4), 5));
        }
    }

    /**
     * This method is used when the effect of the character 7 is active and the player selects the 6th student on the card
     */
    public void selectCharacter7EffectCardStudent6() {
        if (chosenCharacter7EffectCardStudents.size() == chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectCardStudents.size() < 3) {
            character7EffectCardStudentsUp.set(5, selectCharacter7EffectCardStudent(character7EffectCardStudentsAvailable.get(5), character7EffectCardStudentsUp.get(5), character7EffectCardStudents.get(5), 6));
        }
    }

    /**
     * This method applies effects on the image of the selected Character7's student and verifies if the chosen student is selectable or not
     * @param available indicates if a character's student can be selected
     * @param up indicates if it is currently selected
     * @param s it's the image of the character's student that the player wants to select
     * @param chosen it's the id of the selected character's student
     * @return true if the just selected Character7's student wasn't already selected before the call of this method, false in every other case
     */
    private boolean selectCharacter7EffectCardStudent(boolean available, boolean up, ImageView s, int chosen) {
        if (available && !confirmCharacter7On) {
            if (!up) {
                if (validCardStudent7(chosen)) {
                    resizeCharacter7EffectCardStudents();
                    s.setScaleX(1.1);
                    s.setScaleY(1.1);
                    ImageEffects.addWhiteGlow(s);
                    currCharacter7EffectCardStudent = chosen;
                    ImageEffects.addLightBluShadow(character7EffectCardNext);
                    return true;
                } else
                    return false;
            } else {
                resizeCharacter7EffectCardStudents();
                currCharacter7EffectCardStudent = 0;
                ImageEffects.turnOff(character7EffectCardNext);
                return false;
            }
        }
        return false;
    }

    /**
     * This method is used to resize the Character7's student previously selected
     */
    private void resizeCharacter7EffectCardStudents() {
        for (int i = 0; i < 6; i++) {
            if (character7EffectCardStudentsUp.get(i)) {
                character7EffectCardStudentsUp.set(i, ImageEffects.zoomOut(character7EffectCardStudents.get(i)));
                ImageEffects.addShadow(character7EffectCardStudents.get(i));
            }
        }
    }

    /**
     * This method is used to set an effect when you press the mouse button on the "next" image
     */
    public void nextCharacter7CardOn() {
        if (currCharacter7EffectCardStudent != 0 && chosenCharacter7EffectCardStudents.size() == chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectCardStudents.size() < 3 && !confirmCharacter7On) {
            character7EffectCardNext.setScaleX(0.8);
            character7EffectCardNext.setScaleY(0.8);
        }
    }

    /**
     * This method is used to set an effect when you release the mouse button on the "next" image
     * then it calls the method selectNextCharacter7Card()
     */
    public void nextCharacter7CardOff() {
        if (currCharacter7EffectCardStudent != 0 && chosenCharacter7EffectCardStudents.size() == chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectCardStudents.size() < 3 && !confirmCharacter7On) {
            character7EffectCardNext.setScaleX(1.0);
            character7EffectCardNext.setScaleY(1.0);
            selectNextCharacter7Card();
        }
    }

    /**
     * This method is used to switch to the next choice that the player has to make
     */
    private void selectNextCharacter7Card() {
        chosenCharacter7EffectCardStudents.add(currCharacter7EffectCardStudent);
        character7EffectCardStudentsAvailable.set(currCharacter7EffectCardStudent - 1, false);
        currCharacter7EffectCardStudent = 0;
        resizeCharacter7EffectCardStudents();
        ImageEffects.turnOff(character7EffectCardNext);
        for (int i = 0; i < 6; i++)
            ImageEffects.turnOff(character7EffectCardStudents.get(i));
        for (int i = 0; i < 9; i++) {
            if (character7EffectEntranceStudentsAvailable.get(i)) {
                ImageEffects.turnOn(character7EffectEntranceStudents.get(i));
                ImageEffects.addShadow(character7EffectEntranceStudents.get(i));
            }
        }
    }

    /**
     * This method is used when the effect of the character 7 is active and the player selects the 1st student in the entrance
     */
    public void selectCharacter7EffectEntranceStudent1() {
        if (chosenCharacter7EffectCardStudents.size() > chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectEntranceStudents.size() < 3) {
            character7EffectEntranceStudentsUp.set(0, selectCharacter7EffectEntranceStudent(character7EffectEntranceStudentsAvailable.get(0), character7EffectEntranceStudentsUp.get(0), character7EffectEntranceStudents.get(0), 1));
        }
    }

    /**
     * This method is used when the effect of the character 7 is active and the player selects the 2nd student in the entrance
     */
    public void selectCharacter7EffectEntranceStudent2() {
        if (chosenCharacter7EffectCardStudents.size() > chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectEntranceStudents.size() < 3) {
            character7EffectEntranceStudentsUp.set(1, selectCharacter7EffectEntranceStudent(character7EffectEntranceStudentsAvailable.get(1), character7EffectEntranceStudentsUp.get(1), character7EffectEntranceStudents.get(1), 2));
        }
    }

    /**
     * This method is used when the effect of the character 7 is active and the player selects the 3rd student in the entrance
     */
    public void selectCharacter7EffectEntranceStudent3() {
        if (chosenCharacter7EffectCardStudents.size() > chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectEntranceStudents.size() < 3) {
            character7EffectEntranceStudentsUp.set(2, selectCharacter7EffectEntranceStudent(character7EffectEntranceStudentsAvailable.get(2), character7EffectEntranceStudentsUp.get(2), character7EffectEntranceStudents.get(2), 3));
        }
    }

    /**
     * This method is used when the effect of the character 7 is active and the player selects the 4th student in the entrance
     */
    public void selectCharacter7EffectEntranceStudent4() {
        if (chosenCharacter7EffectCardStudents.size() > chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectEntranceStudents.size() < 3) {
            character7EffectEntranceStudentsUp.set(3, selectCharacter7EffectEntranceStudent(character7EffectEntranceStudentsAvailable.get(3), character7EffectEntranceStudentsUp.get(3), character7EffectEntranceStudents.get(3), 4));
        }
    }

    /**
     * This method is used when the effect of the character 7 is active and the player selects the 5th student in the entrance
     */
    public void selectCharacter7EffectEntranceStudent5() {
        if (chosenCharacter7EffectCardStudents.size() > chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectEntranceStudents.size() < 3) {
            character7EffectEntranceStudentsUp.set(4, selectCharacter7EffectEntranceStudent(character7EffectEntranceStudentsAvailable.get(4), character7EffectEntranceStudentsUp.get(4), character7EffectEntranceStudents.get(4), 5));
        }
    }

    /**
     * This method is used when the effect of the character 7 is active and the player selects the 6th student in the entrance
     */
    public void selectCharacter7EffectEntranceStudent6() {
        if (chosenCharacter7EffectCardStudents.size() > chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectEntranceStudents.size() < 3) {
            character7EffectEntranceStudentsUp.set(5, selectCharacter7EffectEntranceStudent(character7EffectEntranceStudentsAvailable.get(5), character7EffectEntranceStudentsUp.get(5), character7EffectEntranceStudents.get(5), 6));
        }
    }

    /**
     * This method is used when the effect of the character 7 is active and the player selects the 7th student in the entrance
     */
    public void selectCharacter7EffectEntranceStudent7() {
        if (chosenCharacter7EffectCardStudents.size() > chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectEntranceStudents.size() < 3) {
            character7EffectEntranceStudentsUp.set(6, selectCharacter7EffectEntranceStudent(character7EffectEntranceStudentsAvailable.get(6), character7EffectEntranceStudentsUp.get(6), character7EffectEntranceStudents.get(6), 7));
        }
    }

    /**
     * This method is used when the effect of the character 7 is active and the player selects the 8th student in the entrance
     */
    public void selectCharacter7EffectEntranceStudent8() {
        if (chosenCharacter7EffectCardStudents.size() > chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectEntranceStudents.size() < 3) {
            character7EffectEntranceStudentsUp.set(7, selectCharacter7EffectEntranceStudent(character7EffectEntranceStudentsAvailable.get(7), character7EffectEntranceStudentsUp.get(7), character7EffectEntranceStudents.get(7), 8));
        }
    }

    /**
     * This method is used when the effect of the character 7 is active and the player selects the 9th student in the entrance
     */
    public void selectCharacter7EffectEntranceStudent9() {
        if (chosenCharacter7EffectCardStudents.size() > chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectEntranceStudents.size() < 3) {
            character7EffectEntranceStudentsUp.set(8, selectCharacter7EffectEntranceStudent(character7EffectEntranceStudentsAvailable.get(8), character7EffectEntranceStudentsUp.get(8), character7EffectEntranceStudents.get(8), 9));
        }
    }

    /**
     * This method applies effects on the image of the selected entrance's student and verifies if the chosen student is selectable or not
     * @param available indicates if a entrance's student can be selected
     * @param up indicates if it is currently selected
     * @param s it's the image of the entrance's student that the player wants to select
     * @param chosen it's the id of the selected entrance's student
     * @return true if the just selected Character7's student wasn't already selected before the call of this method, false in every other case
     */
    private boolean selectCharacter7EffectEntranceStudent(boolean available, boolean up, ImageView s, int chosen) {
        if (available && !confirmCharacter7On) {
            if (!up) {
                if (validEntranceStudent7(chosen)) {
                    resizeCharacter7EffectEntranceStudents();
                    s.setScaleX(1.1);
                    s.setScaleY(1.1);
                    ImageEffects.addWhiteGlow(s);
                    currCharacter7EffectEntranceStudent = chosen;
                    if (chosenCharacter7EffectEntranceStudents.size() < 2 && validNext7())
                        ImageEffects.addLightBluShadow(character7EffectEntranceNext);
                    ImageEffects.addLightBluShadow(character7EffectConfirmButton);
                    return true;
                } else
                    return false;
            } else {
                resizeCharacter7EffectEntranceStudents();
                currCharacter7EffectEntranceStudent = 0;
                ImageEffects.turnOff(character7EffectEntranceNext);
                ImageEffects.turnOff(character7EffectConfirmButton);
                return false;
            }
        }
        return false;
    }

    /**
     * This method is used to resize the entrance's student previously selected
     */
    private void resizeCharacter7EffectEntranceStudents() {
        for (int i = 0; i < 9; i++) {
            if (character7EffectEntranceStudentsUp.get(i)) {
                character7EffectEntranceStudentsUp.set(i, ImageEffects.zoomOut(character7EffectEntranceStudents.get(i)));
                ImageEffects.addShadow(character7EffectEntranceStudents.get(i));
            }
        }
    }

    /**
     * This method is used to set an effect when you press the mouse button on the "next" image
     */
    public void nextCharacter7EntranceOn() {
        if (currCharacter7EffectEntranceStudent != 0 && chosenCharacter7EffectCardStudents.size() > chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectEntranceStudents.size() < 2 && !confirmCharacter7On && validNext7()) {
            character7EffectEntranceNext.setScaleX(0.8);
            character7EffectEntranceNext.setScaleY(0.8);
        }
    }

    /**
     * This method is used to set an effect when you release the mouse button on the "next" image
     * then it calls the method selectNextCharacter7Entrance()
     */
    public void nextCharacter7EntranceOff() {
        if (currCharacter7EffectEntranceStudent != 0 && chosenCharacter7EffectCardStudents.size() > chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectEntranceStudents.size() < 2 && !confirmCharacter7On && validNext7()) {
            character7EffectEntranceNext.setScaleX(1.0);
            character7EffectEntranceNext.setScaleY(1.0);
            selectNextCharacter7Entrance();
        }
    }

    /**
     * This method is used to switch to the next choice that the player has to make
     */
    private void selectNextCharacter7Entrance() {
        chosenCharacter7EffectEntranceStudents.add(currCharacter7EffectEntranceStudent);
        character7EffectEntranceStudentsAvailable.set(currCharacter7EffectEntranceStudent - 1, false);
        currCharacter7EffectEntranceStudent = 0;
        resizeCharacter7EffectEntranceStudents();
        ImageEffects.turnOff(character7EffectEntranceNext);
        ImageEffects.turnOff(character7EffectConfirmButton);
        for (int i = 0; i < 9; i++)
            ImageEffects.turnOff(character7EffectEntranceStudents.get(i));
        for (int i = 0; i < 6; i++) {
            if (character7EffectCardStudentsAvailable.get(i)) {
                ImageEffects.turnOn(character7EffectCardStudents.get(i));
                ImageEffects.addShadow(character7EffectCardStudents.get(i));
            }
        }
    }

    /**
     * This method is used to set an effect when you press the mouse button on the confirm image
     */
    public void confirmCharacter7On() {
        if (currCharacter7EffectEntranceStudent != 0 && chosenCharacter7EffectCardStudents.size() > chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectEntranceStudents.size() < 3) {
            character7EffectConfirmButton.setScaleX(0.8);
            character7EffectConfirmButton.setScaleY(0.8);
        }
    }

    /**
     * This method is used to set an effect when you release the mouse button on the confirm image
     * then it calls the method confirmCharacter7()
     */
    public void confirmCharacter7Off() {
        if (currCharacter7EffectEntranceStudent != 0 && chosenCharacter7EffectCardStudents.size() > chosenCharacter7EffectEntranceStudents.size() && chosenCharacter7EffectEntranceStudents.size() < 3) {
            character7EffectConfirmButton.setScaleX(1.0);
            character7EffectConfirmButton.setScaleY(1.0);
            confirmCharacter7();
        }
    }

    /**
     * This method sends a message to confirm the choices made on the 7th Character to the Server via NetworkHandler
     */
    private void confirmCharacter7() {
        confirmCharacter7On = true;
        chosenCharacter7EffectEntranceStudents.add(currCharacter7EffectEntranceStudent);
        character7EffectEntranceStudentsAvailable.set(currCharacter7EffectEntranceStudent - 1, false);
        resizeCharacter7EffectEntranceStudents();
        ImageEffects.turnOff(character7EffectEntranceNext);
        for (int i = 0; i < 9; i++)
            ImageEffects.turnOff(character7EffectEntranceStudents.get(i));

        ArrayList<Parameter> p7 = new ArrayList<>();
        p7.add(new IntegerParameter("number of students", chosenCharacter7EffectCardStudents.size()));
        for (int i = 0; i < chosenCharacter7EffectCardStudents.size(); i++)
            p7.add(new IntegerParameter("card student", chosenCharacter7EffectCardStudents.get(i) - 1));
        for (int i = 0; i < chosenCharacter7EffectEntranceStudents.size(); i++)
            p7.add(new IntegerParameter("entrance student", chosenCharacter7EffectEntranceStudents.get(i) - 1));
        UseCharacterMessage ucm7 = new UseCharacterMessage(chosenCharacter - 1, p7);
        getNetworkHandler().update(ucm7);

        usedCharacter = true;
        activeCharacter = false;
        chosenCharacter = 0;
        tabPane.getSelectionModel().select(tableTab);
        tabPane.getTabs().remove(character7EffectTab);
    }

    /**
     * This method is used to decide if the player can select the Character7's student
     * @param chosen it's the id of the chosen Character7's student
     * @return true if the player can select it, false if they can't
     */
    private boolean validCardStudent7(int chosen) {
        Board b = getPlayerBoard();
        boolean valid = false;
        for (int i = 0; i < b.getEntrance().length; i++) {
            if (character7EffectEntranceStudentsAvailable.get(i)) {
                if (b.getEntrance()[i] != ((Jester) characters[chosenCharacter - 1]).getStudents().get(chosen - 1))
                    valid = true;
            }
        }
        return valid;
    }

    /**
     * This method is used to decide if the player can select the entrance's student
     * @param chosen it's the id of the chosen entrance's student
     * @return true if the player can select it, false if they can't
     */
    private boolean validEntranceStudent7(int chosen) {
        Board b = getPlayerBoard();
        boolean valid = false;
        if (b.getEntrance()[chosen - 1] != ((Jester) characters[chosenCharacter - 1]).getStudents().get(chosenCharacter7EffectCardStudents.get(chosenCharacter7EffectCardStudents.size() - 1) - 1))
            valid = true;
        return valid;
    }

    /**
     * This method is used to decide if the "next" button can be clicked and so the player can move on to the next choice
     * @return true if the player can click it, false if they can't
     */
    private boolean validNext7() {
        Board b = getPlayerBoard();

        boolean valid = false;
        for (int i = 0; i < ((Jester) characters[chosenCharacter - 1]).getStudents().size(); i++) {
            for (int j = 0; j < b.getEntrance().length; j++) {
                if (character7EffectCardStudentsAvailable.get(i) && j != (currCharacter7EffectEntranceStudent - 1) && character7EffectEntranceStudentsAvailable.get(j)) {
                    if (((Jester) characters[chosenCharacter - 1]).getStudents().get(i) != b.getEntrance()[j])
                        valid = true;
                }
            }
        }
        return valid;
    }

    /**
     * This method is used to set the tab associated to the 7th Character
     */
    private void setCharacter7Tab(int index) {
        character7EffectCardStudents = new ArrayList<>();
        character7EffectCardStudents.add(character7EffectCardStudent1);
        character7EffectCardStudents.add(character7EffectCardStudent2);
        character7EffectCardStudents.add(character7EffectCardStudent3);
        character7EffectCardStudents.add(character7EffectCardStudent4);
        character7EffectCardStudents.add(character7EffectCardStudent5);
        character7EffectCardStudents.add(character7EffectCardStudent6);

        character7EffectEntranceStudents = new ArrayList<>();
        character7EffectEntranceStudents.add(character7EffectEntranceStudent1);
        character7EffectEntranceStudents.add(character7EffectEntranceStudent2);
        character7EffectEntranceStudents.add(character7EffectEntranceStudent3);
        character7EffectEntranceStudents.add(character7EffectEntranceStudent4);
        character7EffectEntranceStudents.add(character7EffectEntranceStudent5);
        character7EffectEntranceStudents.add(character7EffectEntranceStudent6);
        character7EffectEntranceStudents.add(character7EffectEntranceStudent7);
        character7EffectEntranceStudents.add(character7EffectEntranceStudent8);
        character7EffectEntranceStudents.add(character7EffectEntranceStudent9);

        character7EffectCardStudentsUp = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            character7EffectCardStudentsUp.add(false);

        character7EffectCardStudentsAvailable = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            character7EffectCardStudentsAvailable.add(true);

        character7EffectEntranceStudentsUp = new ArrayList<>();
        for (int i = 0; i < 9; i++)
            character7EffectEntranceStudentsUp.add(false);

        character7EffectEntranceStudentsAvailable = new ArrayList<>();
        for (int i = 0; i < 9; i++)
            character7EffectEntranceStudentsAvailable.add(true);

        chosenCharacter7EffectCardStudents = new ArrayList<>();

        chosenCharacter7EffectEntranceStudents = new ArrayList<>();

        for (int i = 0; i < ((Jester) characters[index - 1]).getStudents().size(); i++) {
            character7EffectCardStudents.get(i).setImage(new Image(getClass().getClassLoader().getResource(getStudentFlatImage(((Jester) characters[index - 1]).getStudents().get(i))).toString()));
        }

        for (int i = ((Jester) characters[index - 1]).getStudents().size(); i < 6; i++) {
            character7EffectCardStudents.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/black_disc_flat.png").toString()));
            character7EffectCardStudentsAvailable.set(i, false);
        }

        Board b = getPlayerBoard();

        for (int i = 0; i < b.getEntrance().length; i++) {
            if (b.getEntrance()[i] != null)
                character7EffectEntranceStudents.get(i).setImage(new Image(getClass().getClassLoader().getResource(getStudentFlatImage(b.getEntrance()[i])).toString()));
            else {
                character7EffectEntranceStudents.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/black_disc_flat.png").toString()));
                character7EffectEntranceStudentsAvailable.set(i, false);
            }
        }

        for (int i = b.getEntrance().length; i < 9; i++) {
            character7EffectEntranceStudents.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/black_disc_flat.png").toString()));
            character7EffectEntranceStudentsAvailable.set(i, false);
        }

        for (int i = 0; i < character7EffectCardStudents.size(); i++) {
            if (character7EffectCardStudentsAvailable.get(i)) {
                ImageEffects.turnOn(character7EffectCardStudents.get(i));
                ImageEffects.addShadow(character7EffectCardStudents.get(i));
            }
        }

        ImageEffects.turnOff(character7EffectConfirmButton);

        confirmCharacter7On = false;
        currCharacter7EffectCardStudent = 0;
        currCharacter7EffectEntranceStudent = 0;

        resetTabs();
        tabPane.getTabs().add(character7EffectTab);
        tabPane.getSelectionModel().select(character7EffectTab);
    }

    // CHARACTER 9 - 12 -----------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This method is used when the effect of the Character9 or Character12 is active and the player selects the color "green"
     */
    public void selectCharacter9_12EffectGreen() {
        character9_12EffectColorUp.set(0, selectCharacter9_12EffectColor(character9_12EffectColorUp.get(0), character9_12EffectColors.get(0), 1));
    }

    /**
     * This method is used when the effect of the Character9 or Character12 is active and the player selects the color "red"
     */
    public void selectCharacter9_12EffectRed() {
        character9_12EffectColorUp.set(1, selectCharacter9_12EffectColor(character9_12EffectColorUp.get(1), character9_12EffectColors.get(1), 2));
    }

    /**
     * This method is used when the effect of the Character9 or Character12 is active and the player selects the color "yellow"
     */
    public void selectCharacter9_12EffectYellow() {
        character9_12EffectColorUp.set(2, selectCharacter9_12EffectColor(character9_12EffectColorUp.get(2), character9_12EffectColors.get(2), 3));
    }

    /**
     * This method is used when the effect of the Character9 or Character12 is active and the player selects the color "pink"
     */
    public void selectCharacter9_12EffectPink() {
        character9_12EffectColorUp.set(3, selectCharacter9_12EffectColor(character9_12EffectColorUp.get(3), character9_12EffectColors.get(3), 4));
    }

    /**
     * This method is used when the effect of the Character9 or Character12 is active and the player selects the color "blue"
     */
    public void selectCharacter9_12EffectBlue() {
        character9_12EffectColorUp.set(4, selectCharacter9_12EffectColor(character9_12EffectColorUp.get(4), character9_12EffectColors.get(4), 5));
    }

    /**
     * This method is used to set an effect when you press the mouse button on the confirm image
     */
    public void confirmCharacter9_12On() {
        if (chosenCharacter9_12EffectColor != 0 && !confirmCharacter9_12On) {
            character9_12EffectConfirmButton.setScaleX(0.8);
            character9_12EffectConfirmButton.setScaleY(0.8);
        }
    }

    /**
     * This method is used to set an effect when you release the mouse button on the confirm image
     * then it calls the method confirmCharacter9_12()
     */
    public void confirmCharacter9_12Off() {
        if (chosenCharacter9_12EffectColor != 0 && !confirmCharacter9_12On) {
            character9_12EffectConfirmButton.setScaleX(1.0);
            character9_12EffectConfirmButton.setScaleY(1.0);
            confirmCharacter9_12();
        }
    }

    /**
     * This method sends a message to confirm the choices made on the 9th Character or the 12th Character to the Server via NetworkHandler
     */
    private void confirmCharacter9_12() {
        confirmCharacter4On = true;
        ArrayList<Parameter> p9 = new ArrayList<>();

        switch (chosenCharacter9_12EffectColor) {
            case 1:
                Parameter sp9g = new StudentParameter("color", Student.GREEN);
                p9.add(sp9g);
                break;
            case 2:
                Parameter sp9r = new StudentParameter("color", Student.RED);
                p9.add(sp9r);
                break;
            case 3:
                Parameter sp9y = new StudentParameter("color", Student.YELLOW);
                p9.add(sp9y);
                break;
            case 4:
                Parameter sp9p = new StudentParameter("color", Student.PINK);
                p9.add(sp9p);
                break;
            case 5:
                Parameter sp9b = new StudentParameter("color", Student.BLUE);
                p9.add(sp9b);
                break;
        }

        UseCharacterMessage ucm9 = new UseCharacterMessage(chosenCharacter - 1, p9);
        getNetworkHandler().update(ucm9);
        usedCharacter = true;
        activeCharacter = false;
        chosenCharacter = 0;
        resizeCharacter9_12EffectColor();
        tabPane.getSelectionModel().select(tableTab);
        tabPane.getTabs().remove(character9_12EffectTab);
    }

    /**
     * This method applies effects on the image of the selected color and verifies if the chosen color is selectable or not
     * @param up indicates if it is currently selected
     * @param c it's the image of the color that the player wants to select
     * @param chosen it's the id of the selected color
     * @return true if the just selected color wasn't already selected before the call of this method, false in every other case
     */
    private boolean selectCharacter9_12EffectColor(boolean up, ImageView c, int chosen) {
        if (!up && !confirmCharacter9_12On) {
            resizeCharacter9_12EffectColor();
            c.setScaleX(1.1);
            c.setScaleY(1.1);
            ImageEffects.addWhiteGlow(c);
            chosenCharacter9_12EffectColor = chosen;
            ImageEffects.addLightBluShadow(character9_12EffectConfirmButton);
            return true;
        } else {
            resizeCharacter9_12EffectColor();
            chosenCharacter9_12EffectColor = 0;
            ImageEffects.turnOff(character9_12EffectConfirmButton);
            return false;
        }
    }

    /**
     * This method is used to resize the color previously selected
     */
    private void resizeCharacter9_12EffectColor() {
        for (int i = 0; i < 5; i++) {
            if (character9_12EffectColorUp.get(i)) {
                character9_12EffectColorUp.set(i, ImageEffects.zoomOut(character9_12EffectColors.get(i)));
                ImageEffects.addShadow(character9_12EffectColors.get(i));
            }
        }
    }

    /**
     * This method is used to set the tab associated to the 9th Character and the 12th Character
     */
    private void setCharacter9_12Tab() {
        character9_12EffectColors = new ArrayList<>();
        character9_12EffectColors.add(character9_12EffectGreen);
        character9_12EffectColors.add(character9_12EffectRed);
        character9_12EffectColors.add(character9_12EffectYellow);
        character9_12EffectColors.add(character9_12EffectPink);
        character9_12EffectColors.add(character9_12EffectBlue);

        character9_12EffectColorUp = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            character9_12EffectColorUp.add(false);

        confirmCharacter9_12On = false;
        chosenCharacter9_12EffectColor = 0;

        ImageEffects.turnOff(character9_12EffectConfirmButton);

        resetTabs();
        tabPane.getTabs().add(character9_12EffectTab);
        tabPane.getSelectionModel().select(character9_12EffectTab);
    }

    // CHARACTER 10 -----------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This method is used when the effect of the character 10 is active and the player selects the 1st student in the entrance
     */
    public void selectCharacter10EffectEntranceStudent1() {
        if (chosenCharacter10EffectEntranceStudents.size() == chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectEntranceStudents.size() < 2) {
            character10EffectEntranceStudentsUp.set(0, selectCharacter10EffectEntranceStudent(character10EffectEntranceStudentsAvailable.get(0), character10EffectEntranceStudentsUp.get(0), character10EffectEntranceStudents.get(0), 1));
        }
    }

    /**
     * This method is used when the effect of the character 10 is active and the player selects the 2nd student in the entrance
     */
    public void selectCharacter10EffectEntranceStudent2() {
        if (chosenCharacter10EffectEntranceStudents.size() == chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectEntranceStudents.size() < 2) {
            character10EffectEntranceStudentsUp.set(1, selectCharacter10EffectEntranceStudent(character10EffectEntranceStudentsAvailable.get(1), character10EffectEntranceStudentsUp.get(1), character10EffectEntranceStudents.get(1), 2));
        }
    }

    /**
     * This method is used when the effect of the character 10 is active and the player selects the 3rd student in the entrance
     */
    public void selectCharacter10EffectEntranceStudent3() {
        if (chosenCharacter10EffectEntranceStudents.size() == chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectEntranceStudents.size() < 2) {
            character10EffectEntranceStudentsUp.set(2, selectCharacter10EffectEntranceStudent(character10EffectEntranceStudentsAvailable.get(2), character10EffectEntranceStudentsUp.get(2), character10EffectEntranceStudents.get(2), 3));
        }
    }

    /**
     * This method is used when the effect of the character 10 is active and the player selects the 4th student in the entrance
     */
    public void selectCharacter10EffectEntranceStudent4() {
        if (chosenCharacter10EffectEntranceStudents.size() == chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectEntranceStudents.size() < 2) {
            character10EffectEntranceStudentsUp.set(3, selectCharacter10EffectEntranceStudent(character10EffectEntranceStudentsAvailable.get(3), character10EffectEntranceStudentsUp.get(3), character10EffectEntranceStudents.get(3), 4));
        }
    }

    /**
     * This method is used when the effect of the character 10 is active and the player selects the 5th student in the entrance
     */
    public void selectCharacter10EffectEntranceStudent5() {
        if (chosenCharacter10EffectEntranceStudents.size() == chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectEntranceStudents.size() < 2) {
            character10EffectEntranceStudentsUp.set(4, selectCharacter10EffectEntranceStudent(character10EffectEntranceStudentsAvailable.get(4), character10EffectEntranceStudentsUp.get(4), character10EffectEntranceStudents.get(4), 5));
        }
    }

    /**
     * This method is used when the effect of the character 10 is active and the player selects the 6th student in the entrance
     */
    public void selectCharacter10EffectEntranceStudent6() {
        if (chosenCharacter10EffectEntranceStudents.size() == chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectEntranceStudents.size() < 2) {
            character10EffectEntranceStudentsUp.set(5, selectCharacter10EffectEntranceStudent(character10EffectEntranceStudentsAvailable.get(5), character10EffectEntranceStudentsUp.get(5), character10EffectEntranceStudents.get(5), 6));
        }
    }

    /**
     * This method is used when the effect of the character 10 is active and the player selects the 7th student in the entrance
     */
    public void selectCharacter10EffectEntranceStudent7() {
        if (chosenCharacter10EffectEntranceStudents.size() == chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectEntranceStudents.size() < 2) {
            character10EffectEntranceStudentsUp.set(6, selectCharacter10EffectEntranceStudent(character10EffectEntranceStudentsAvailable.get(6), character10EffectEntranceStudentsUp.get(6), character10EffectEntranceStudents.get(6), 7));
        }
    }

    /**
     * This method is used when the effect of the character 10 is active and the player selects the 8th student in the entrance
     */
    public void selectCharacter10EffectEntranceStudent8() {
        if (chosenCharacter10EffectEntranceStudents.size() == chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectEntranceStudents.size() < 2) {
            character10EffectEntranceStudentsUp.set(7, selectCharacter10EffectEntranceStudent(character10EffectEntranceStudentsAvailable.get(7), character10EffectEntranceStudentsUp.get(7), character10EffectEntranceStudents.get(7), 8));
        }
    }

    /**
     * This method is used when the effect of the character 10 is active and the player selects the 9th student in the entrance
     */
    public void selectCharacter10EffectEntranceStudent9() {
        if (chosenCharacter10EffectEntranceStudents.size() == chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectEntranceStudents.size() < 2) {
            character10EffectEntranceStudentsUp.set(8, selectCharacter10EffectEntranceStudent(character10EffectEntranceStudentsAvailable.get(8), character10EffectEntranceStudentsUp.get(8), character10EffectEntranceStudents.get(8), 9));
        }
    }

    /**
     * This method is used to apply effects on the image of the currently selected entrance's student
     * @param available indicates if an entrance's student can be selected
     * @param up indicates if it is currently selected
     * @param s it's the image of the currently selected entrance's student
     * @param chosen it's the id of the selected entrance's student
     * @return true if the just selected entrance's student wasn't already selected before the call of this method, false in every other case
     */
    private boolean selectCharacter10EffectEntranceStudent(boolean available, boolean up, ImageView s, int chosen) {
        if (available && !confirmCharacter10On) {
            if (!up) {
                if (validEntranceStudent10(chosen)) {
                    resizeCharacter10EffectEntranceStudents();
                    s.setScaleX(1.1);
                    s.setScaleY(1.1);
                    ImageEffects.addWhiteGlow(s);
                    currCharacter10EffectEntranceStudent = chosen;
                    ImageEffects.addLightBluShadow(character10EffectEntranceNext);
                    return true;
                } else
                    return false;
            } else {
                resizeCharacter10EffectEntranceStudents();
                currCharacter10EffectEntranceStudent = 0;
                ImageEffects.turnOff(character10EffectEntranceNext);
                return false;
            }
        }
        return false;
    }

    /**
     * This method is used to resize the entrance's student when deselected
     */
    private void resizeCharacter10EffectEntranceStudents() {
        for (int i = 0; i < 9; i++) {
            if (character10EffectEntranceStudentsUp.get(i)) {
                character10EffectEntranceStudentsUp.set(i, ImageEffects.zoomOut(character10EffectEntranceStudents.get(i)));
                ImageEffects.addShadow(character10EffectEntranceStudents.get(i));
            }
        }
    }

    /**
     * This method is used to set an effect when you press the mouse button on the "next" image
     */
    public void nextCharacter10EntranceOn() {
        if (currCharacter10EffectEntranceStudent != 0 && chosenCharacter10EffectEntranceStudents.size() == chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectEntranceStudents.size() < 2 && !confirmCharacter10On) {
            character10EffectEntranceNext.setScaleX(0.8);
            character10EffectEntranceNext.setScaleY(0.8);
        }
    }

    /**
     * This method is used to set an effect when you release the mouse button on the "next" image
     * then it calls the method selectNextCharacter10Entrance()
     */
    public void nextCharacter10EntranceOff() {
        if (currCharacter10EffectEntranceStudent != 0 && chosenCharacter10EffectEntranceStudents.size() == chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectEntranceStudents.size() < 2 && !confirmCharacter10On) {
            character10EffectEntranceNext.setScaleX(1.0);
            character10EffectEntranceNext.setScaleY(1.0);
            selectNextCharacter10Entrance();
        }
    }

    /**
     * This method is used to switch to the next choice that the player has to make
     */
    private void selectNextCharacter10Entrance() {
        chosenCharacter10EffectEntranceStudents.add(currCharacter10EffectEntranceStudent);
        character10EffectEntranceStudentsAvailable.set(currCharacter10EffectEntranceStudent - 1, false);
        currCharacter10EffectEntranceStudent = 0;
        resizeCharacter10EffectEntranceStudents();
        ImageEffects.turnOff(character10EffectEntranceNext);
        for (int i = 0; i < 9; i++)
            ImageEffects.turnOff(character10EffectEntranceStudents.get(i));
        for (int i = 0; i < 5; i++) {
            if (character10EffectHallStudentsAvailable.get(i)) {
                ImageEffects.turnOn(character10EffectHallStudents.get(i));
                ImageEffects.addShadow(character10EffectHallStudents.get(i));
            }
        }
    }

    /**
     * This method is used when the effect of the Character10 is active and the player selects the color "green"
     */
    public void selectCharacter10EffectHallGreen() {
        if (chosenCharacter10EffectEntranceStudents.size() > chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectHallStudents.size() < 2) {
            character10EffectHallStudentsUp.set(0, selectCharacter10EffectHallStudent(character10EffectHallStudentsAvailable.get(0), character10EffectHallStudentsUp.get(0), character10EffectHallStudents.get(0), 1));
        }
    }

    /**
     * This method is used when the effect of the Character10 is active and the player selects the color "red"
     */
    public void selectCharacter10EffectHallRed() {
        if (chosenCharacter10EffectEntranceStudents.size() > chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectHallStudents.size() < 2) {
            character10EffectHallStudentsUp.set(1, selectCharacter10EffectHallStudent(character10EffectHallStudentsAvailable.get(1), character10EffectHallStudentsUp.get(1), character10EffectHallStudents.get(1), 2));
        }
    }

    /**
     * This method is used when the effect of the Character10 is active and the player selects the color "yellow"
     */
    public void selectCharacter10EffectHallYellow() {
        if (chosenCharacter10EffectEntranceStudents.size() > chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectHallStudents.size() < 2) {
            character10EffectHallStudentsUp.set(2, selectCharacter10EffectHallStudent(character10EffectHallStudentsAvailable.get(2), character10EffectHallStudentsUp.get(2), character10EffectHallStudents.get(2), 3));
        }
    }

    /**
     * This method is used when the effect of the Character10 is active and the player selects the color "pink"
     */
    public void selectCharacter10EffectHallPink() {
        if (chosenCharacter10EffectEntranceStudents.size() > chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectHallStudents.size() < 2) {
            character10EffectHallStudentsUp.set(3, selectCharacter10EffectHallStudent(character10EffectHallStudentsAvailable.get(3), character10EffectHallStudentsUp.get(3), character10EffectHallStudents.get(3), 4));
        }
    }

    /**
     * This method is used when the effect of the Character10 is active and the player selects the color "blue"
     */
    public void selectCharacter10EffectHallBlue() {
        if (chosenCharacter10EffectEntranceStudents.size() > chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectHallStudents.size() < 2) {
            character10EffectHallStudentsUp.set(4, selectCharacter10EffectHallStudent(character10EffectHallStudentsAvailable.get(4), character10EffectHallStudentsUp.get(4), character10EffectHallStudents.get(4), 5));
        }
    }

    /**
     * This method is used to apply effects on the image of the currently selected hall's student
     * @param available indicates if an hall's student can be selected
     * @param up indicates if it is currently selected
     * @param s it's the image of the currently selected hall's student
     * @param chosen it's the id of the selected hall's student
     * @return true if the just selected hall's student wasn't already selected before the call of this method, false in every other case
     */
    private boolean selectCharacter10EffectHallStudent(boolean available, boolean up, ImageView s, int chosen) {
        if (available && !confirmCharacter10On) {
            if (!up) {
                if (validHallStudent10(chosen)) {
                    resizeCharacter10EffectHallStudents();
                    s.setScaleX(1.1);
                    s.setScaleY(1.1);
                    ImageEffects.addWhiteGlow(s);
                    currCharacter10EffectHallStudent = chosen;
                    if (chosenCharacter10EffectHallStudents.size() < 1 && validNext10())
                        ImageEffects.addLightBluShadow(character10EffectHallNext);
                    ImageEffects.addLightBluShadow(character10EffectConfirmButton);
                    return true;
                } else
                    return false;
            } else {
                resizeCharacter10EffectHallStudents();
                currCharacter10EffectHallStudent = 0;
                ImageEffects.turnOff(character10EffectHallNext);
                ImageEffects.turnOff(character10EffectConfirmButton);
                return false;
            }
        }
        return false;
    }

    /**
     * This method is used to resize the hall's student to its normal size
     */
    private void resizeCharacter10EffectHallStudents() {
        for (int i = 0; i < 5; i++) {
            if (character10EffectHallStudentsUp.get(i)) {
                character10EffectHallStudentsUp.set(i, ImageEffects.zoomOut(character10EffectHallStudents.get(i)));
                ImageEffects.addShadow(character10EffectHallStudents.get(i));
            }
        }
    }

    /**
     * This method is used to set an effect when you press the mouse button on the "next" image
     */
    public void nextCharacter10HallOn() {
        if (currCharacter10EffectHallStudent != 0 && chosenCharacter10EffectEntranceStudents.size() > chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectHallStudents.size() < 1 && !confirmCharacter10On && validNext10()) {
            character10EffectHallNext.setScaleX(0.8);
            character10EffectHallNext.setScaleY(0.8);
        }
    }

    /**
     * This method is used to set an effect when you release the mouse button on the "next" image
     * then it calls the method selectNextCharacter10Hall()
     */
    public void nextCharacter10HallOff() {
        if (currCharacter10EffectHallStudent != 0 && chosenCharacter10EffectEntranceStudents.size() > chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectHallStudents.size() < 1 && !confirmCharacter10On && validNext10()) {
            character10EffectHallNext.setScaleX(1.0);
            character10EffectHallNext.setScaleY(1.0);
            selectNextCharacter10Hall();
        }
    }

    /**
     * This method is used to switch to the next choice that the player has to make
     */
    private void selectNextCharacter10Hall() {
        chosenCharacter10EffectHallStudents.add(currCharacter10EffectHallStudent);
        if ((getPlayerBoard().getNumberOfStudents(character10getHallStudentFromIndex(currCharacter10EffectHallStudent)) - 1) == 0)
            character10EffectHallStudentsAvailable.set(currCharacter10EffectHallStudent - 1, false);
        currCharacter10EffectHallStudent = 0;
        resizeCharacter10EffectHallStudents();
        ImageEffects.turnOff(character10EffectHallNext);
        ImageEffects.turnOff(character10EffectConfirmButton);
        for (int i = 0; i < 5; i++)
            ImageEffects.turnOff(character10EffectHallStudents.get(i));
        for (int i = 0; i < 9; i++) {
            if (character10EffectEntranceStudentsAvailable.get(i)) {
                ImageEffects.turnOn(character10EffectEntranceStudents.get(i));
                ImageEffects.addShadow(character10EffectEntranceStudents.get(i));
            }
        }
    }

    /**
     * This method is used to set an effect when you press the mouse button on the confirm image
     */
    public void confirmCharacter10On() {
        if (currCharacter10EffectHallStudent != 0 && chosenCharacter10EffectEntranceStudents.size() > chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectHallStudents.size() < 2 && !confirmCharacter10On) {
            character10EffectConfirmButton.setScaleX(0.8);
            character10EffectConfirmButton.setScaleY(0.8);
        }
    }

    /**
     * This method is used to set an effect when you release the mouse button on the confirm image
     * then it calls the method confirmCharacter10()
     */
    public void confirmCharacter10Off() {
        if (currCharacter10EffectHallStudent != 0 && chosenCharacter10EffectEntranceStudents.size() > chosenCharacter10EffectHallStudents.size() && chosenCharacter10EffectHallStudents.size() < 2 && !confirmCharacter10On) {
            character10EffectConfirmButton.setScaleX(1.0);
            character10EffectConfirmButton.setScaleY(1.0);
            confirmCharacter10();
        }
    }

    /**
     * This method sends a message to confirm the choices made on the 10th Character to the Server via NetworkHandler
     */
    private void confirmCharacter10() {
        confirmCharacter10On = true;
        chosenCharacter10EffectHallStudents.add(currCharacter10EffectHallStudent);
        if ((getPlayerBoard().getNumberOfStudents(character10getHallStudentFromIndex(currCharacter10EffectHallStudent)) - 1) == 0)
            character10EffectHallStudentsAvailable.set(currCharacter10EffectHallStudent - 1, false);
        resizeCharacter10EffectHallStudents();
        ImageEffects.turnOff(character10EffectHallNext);
        for (int i = 0; i < 5; i++)
            ImageEffects.turnOff(character10EffectHallStudents.get(i));

        ArrayList<Parameter> p10 = new ArrayList<>();
        p10.add(new IntegerParameter("number of couples", chosenCharacter10EffectEntranceStudents.size()));
        for (Integer i : chosenCharacter10EffectEntranceStudents)
            p10.add(new IntegerParameter("entrance student", i - 1));
        for (Integer i : chosenCharacter10EffectHallStudents)
            p10.add(new StudentParameter("hall student", character10getHallStudentFromIndex(i)));
        UseCharacterMessage ucm10 = new UseCharacterMessage(chosenCharacter - 1, p10);
        getNetworkHandler().update(ucm10);

        usedCharacter = true;
        activeCharacter = false;
        chosenCharacter = 0;
        tabPane.getSelectionModel().select(tableTab);
        tabPane.getTabs().remove(character10EffectTab);
    }

    /**
     * This method is used to decide if the player can select the entrance's student
     * @param chosen it's the id of the chosen entrance's student
     * @return true if the player can select it, false if they can't
     */
    private boolean validEntranceStudent10(int chosen) {
        boolean valid;
        Board b = getPlayerBoard();
        int totSt = b.getNumberOfStudents(Student.RED) + b.getNumberOfStudents(Student.BLUE) + b.getNumberOfStudents(Student.GREEN) + b.getNumberOfStudents(Student.PINK) + b.getNumberOfStudents(Student.YELLOW);
        if (b.getNumberOfStudents(b.getEntrance()[chosen - 1]) < 10 && (totSt - b.getNumberOfStudents(b.getEntrance()[chosen - 1])) > 0)
            valid = true;
        else
            valid = false;
        return valid;
    }

    /**
     * This method is used to decide if the player can select the hall's student
     * @param chosen it's the id of the chosen hall's student
     * @return true if the player can select it, false if they can't
     */
    private boolean validHallStudent10(int chosen) {
        boolean valid;
        Board b = getPlayerBoard();
        if (b.getEntrance()[chosenCharacter10EffectEntranceStudents.get(chosenCharacter10EffectEntranceStudents.size() - 1) - 1] != character10getHallStudentFromIndex(chosen))
            valid = true;
        else
            valid = false;
        return valid;
    }

    /**
     * @param index it's the index of the selected hall's student
     * @return the color of the selected student
     */
    private Student character10getHallStudentFromIndex(int index) {
        Student s = null;
        switch (index) {
            case 1:
                s = Student.GREEN;
                break;
            case 2:
                s = Student.RED;
                break;
            case 3:
                s = Student.YELLOW;
                break;
            case 4:
                s = Student.PINK;
                break;
            case 5:
                s = Student.BLUE;
                break;
        }
        return s;
    }

    /**
     * @param s it's the color of the selected student
     * @return the index of the selected student
     */
    private int character10getIndexFromStudent(Student s) {
        int i = -1;
        switch (s) {
            case GREEN:
                i = 0;
                break;
            case RED:
                i = 1;
                break;
            case YELLOW:
                i = 2;
                break;
            case PINK:
                i = 3;
                break;
            case BLUE:
                i = 4;
                break;
        }
        return i;
    }

    /**
     * This method is used to decide if the "next" button can be clicked and so the player can move on to the next choice
     * @return true if the player can click it, false if they can't
     */
    private boolean validNext10() {
        boolean valid = false;
        Board b = getPlayerBoard();
        int eDim;
        if (players.size() == 3)
            eDim = 9;
        else
            eDim = 7;
        ArrayList<Student> entrance = new ArrayList<>();
        for (int i = 0; i < eDim; i++) {
            entrance.add(b.getEntrance()[i]);
        }
        entrance.set(chosenCharacter10EffectEntranceStudents.get(0) - 1, null);
        int eTot = 0;
        for (Student s : entrance) {
            if ((s != null))
                eTot++;
        }
        ArrayList<Integer> hall = new ArrayList<>();
        hall.add(b.getNumberOfStudents(Student.GREEN));
        hall.add(b.getNumberOfStudents(Student.RED));
        hall.add(b.getNumberOfStudents(Student.YELLOW));
        hall.add(b.getNumberOfStudents(Student.PINK));
        hall.add(b.getNumberOfStudents(Student.BLUE));
        hall.set(currCharacter10EffectHallStudent - 1, hall.get(currCharacter10EffectHallStudent - 1) - 1);

        int hTot = 0;
        for (Integer i : hall)
            hTot += i;

        if (hTot == 0 || eTot == 0)
            valid = false;
        else {
            for (Student s : entrance) {
                if (s != null) {
                    if (hall.get(character10getIndexFromStudent(s)) < 10 && (hTot - hall.get(character10getIndexFromStudent(s))) > 0)
                        valid = true;
                }
            }
        }
        return valid;
    }

    /**
     * This method is used to set the tab associated to the 10th Character
     */
    private void setCharacter10Tab() {
        character10EffectEntranceStudents = new ArrayList<>();
        character10EffectEntranceStudents.add(character10EffectEntranceStudent1);
        character10EffectEntranceStudents.add(character10EffectEntranceStudent2);
        character10EffectEntranceStudents.add(character10EffectEntranceStudent3);
        character10EffectEntranceStudents.add(character10EffectEntranceStudent4);
        character10EffectEntranceStudents.add(character10EffectEntranceStudent5);
        character10EffectEntranceStudents.add(character10EffectEntranceStudent6);
        character10EffectEntranceStudents.add(character10EffectEntranceStudent7);
        character10EffectEntranceStudents.add(character10EffectEntranceStudent8);
        character10EffectEntranceStudents.add(character10EffectEntranceStudent9);

        character10EffectHallStudents = new ArrayList<>();
        character10EffectHallStudents.add(character10EffectHallGreen);
        character10EffectHallStudents.add(character10EffectHallRed);
        character10EffectHallStudents.add(character10EffectHallYellow);
        character10EffectHallStudents.add(character10EffectHallPink);
        character10EffectHallStudents.add(character10EffectHallBlue);

        character10EffectEntranceStudentsUp = new ArrayList<>();
        for (int i = 0; i < 9; i++)
            character10EffectEntranceStudentsUp.add(false);

        character10EffectEntranceStudentsAvailable = new ArrayList<>();
        for (int i = 0; i < 9; i++)
            character10EffectEntranceStudentsAvailable.add(true);

        character10EffectHallStudentsUp = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            character10EffectHallStudentsUp.add(false);

        character10EffectHallStudentsAvailable = new ArrayList<>();
        for (int i = 0; i < 9; i++)
            character10EffectHallStudentsAvailable.add(true);

        chosenCharacter10EffectEntranceStudents = new ArrayList<>();

        chosenCharacter10EffectHallStudents = new ArrayList<>();

        Board b = getPlayerBoard();

        for (int i = 0; i < b.getEntrance().length; i++) {
            if (b.getEntrance()[i] != null) {
                character10EffectEntranceStudents.get(i).setImage(new Image(getClass().getClassLoader().getResource(getStudentFlatImage(b.getEntrance()[i])).toString()));
                ImageEffects.turnOn(character10EffectEntranceStudents.get(i));
                ImageEffects.addShadow(character10EffectEntranceStudents.get(i));
            } else {
                character10EffectEntranceStudents.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/black_disc_flat.png").toString()));
                character10EffectEntranceStudentsAvailable.set(i, false);
            }
        }

        for (int i = b.getEntrance().length; i < 9; i++) {
            character10EffectEntranceStudents.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/black_disc_flat.png").toString()));
            character10EffectEntranceStudentsAvailable.set(i, false);
        }

        if (b.getNumberOfStudents(Student.GREEN) == 0) {
            character10EffectHallStudentsAvailable.set(0, false);
        }
        if (b.getNumberOfStudents(Student.RED) == 0) {
            character10EffectHallStudentsAvailable.set(1, false);
        }
        if (b.getNumberOfStudents(Student.YELLOW) == 0) {
            character10EffectHallStudentsAvailable.set(2, false);
        }
        if (b.getNumberOfStudents(Student.PINK) == 0) {
            character10EffectHallStudentsAvailable.set(3, false);
        }
        if (b.getNumberOfStudents(Student.BLUE) == 0) {
            character10EffectHallStudentsAvailable.set(4, false);
        }

        ImageEffects.turnOff(character10EffectConfirmButton);

        confirmCharacter10On = false;
        currCharacter10EffectEntranceStudent = 0;
        currCharacter10EffectHallStudent = 0;

        resetTabs();
        tabPane.getTabs().add(character10EffectTab);
        tabPane.getSelectionModel().select(character10EffectTab);
    }

    // CHARACTER 11 -----------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This method is used when the effect of the character 11 is active and the player selects the 1st student on the card
     */
    public void selectCharacter11EffectStudent1() {
        if (character11EffectStudentsAvailable.get(0))
            character11EffectStudentsUp.set(0, selectCharacter11EffectStudent(character11EffectStudentsAvailable.get(0), character11EffectStudentsUp.get(0), character11EffectStudent1, 1));
    }

    /**
     * This method is used when the effect of the character 11 is active and the player selects the 2nd student on the card
     */
    public void selectCharacter11EffectStudent2() {
        if (character11EffectStudentsAvailable.get(1))
            character11EffectStudentsUp.set(1, selectCharacter11EffectStudent(character11EffectStudentsAvailable.get(1), character11EffectStudentsUp.get(1), character11EffectStudent2, 2));
    }

    /**
     * This method is used when the effect of the character 1 is active and the player selects the 3rd student on the card
     */
    public void selectCharacter11EffectStudent3() {
        if (character11EffectStudentsAvailable.get(2))
            character11EffectStudentsUp.set(2, selectCharacter11EffectStudent(character11EffectStudentsAvailable.get(2), character11EffectStudentsUp.get(2), character11EffectStudent3, 3));
    }

    /**
     * This method is used when the effect of the character 1 is active and the player selects the 4th student on the card
     */
    public void selectCharacter11EffectStudent4() {
        if (character11EffectStudentsAvailable.get(3))
            character11EffectStudentsUp.set(3, selectCharacter11EffectStudent(character11EffectStudentsAvailable.get(3), character11EffectStudentsUp.get(3), character11EffectStudent4, 4));
    }

    /**
     * This method is used to set an effect when you press the mouse button on the confirm image
     */
    public void confirmCharacter11On() {
        if (chosenCharacter11EffectStudent != 0 && !confirmCharacter11On) {
            character11EffectConfirmButton.setScaleX(0.8);
            character11EffectConfirmButton.setScaleY(0.8);
        }
    }

    /**
     * This method is used to set an effect when you release the mouse button on the confirm image
     * then it calls the method confirmCharacter11()
     */
    public void confirmCharacter11Off() {
        if (chosenCharacter11EffectStudent != 0 && !confirmCharacter11On) {
            character11EffectConfirmButton.setScaleX(1.0);
            character11EffectConfirmButton.setScaleY(1.0);
            confirmCharacter11();
        }
    }

    /**
     * This method sends a message to confirm the choices made on the 11th Character to the Server via NetworkHandler
     */
    private void confirmCharacter11() {
        confirmCharacter11On = true;
        ArrayList<Parameter> p11 = new ArrayList<>();
        Parameter ip11 = new IntegerParameter("student index", chosenCharacter11EffectStudent - 1);
        p11.add(ip11);
        UseCharacterMessage ucm11 = new UseCharacterMessage(chosenCharacter - 1, p11);
        getNetworkHandler().update(ucm11);
        usedCharacter = true;
        activeCharacter = false;
        chosenCharacter = 0;
        resizeCharacter11EffectStudents();
        tabPane.getSelectionModel().select(tableTab);
        tabPane.getTabs().remove(character11EffectTab);
    }

    /**
     * This method is used to apply effects on the image of the currently selected student
     * @param available indicates if a student can be selected
     * @param up indicates if it is currently selected
     * @param s it's the image of the currently selected student
     * @param chosen it's the id of the selected student
     * @return true if the just selected student wasn't already selected before the call of this method, false in every other case
     */
    private boolean selectCharacter11EffectStudent(boolean available, boolean up, ImageView s, int chosen) {
        if (available && !confirmCharacter11On) {
            if (!up) {
                resizeCharacter11EffectStudents();
                s.setScaleX(1.1);
                s.setScaleY(1.1);
                ImageEffects.addWhiteGlow(s);
                chosenCharacter11EffectStudent = chosen;
                ImageEffects.addLightBluShadow(character11EffectConfirmButton);
                return true;
            } else {
                resizeCharacter11EffectStudents();
                chosenCharacter11EffectStudent = 0;
                ImageEffects.turnOff(character11EffectConfirmButton);
                return false;
            }
        }
        return false;
    }

    /**
     * This method sends a message to confirm the choices made on the 11th Character to the Server via NetworkHandler
     */
    private void resizeCharacter11EffectStudents() {
        for (int i = 0; i < 4; i++) {
            if (character11EffectStudentsUp.get(i)) {
                character11EffectStudentsUp.set(i, ImageEffects.zoomOut(character11EffectStudents.get(i)));
                ImageEffects.addShadow(character11EffectStudents.get(i));
            }
        }
    }

    /**
     * This method is used to set the tab associated to the 11th Character
     */
    private void setCharacter11Tab(int index) {
        character11EffectStudents = new ArrayList<>();
        character11EffectStudents.add(character11EffectStudent1);
        character11EffectStudents.add(character11EffectStudent2);
        character11EffectStudents.add(character11EffectStudent3);
        character11EffectStudents.add(character11EffectStudent4);

        character11EffectStudentsUp = new ArrayList<>();
        character11EffectStudentsUp.add(false);
        character11EffectStudentsUp.add(false);
        character11EffectStudentsUp.add(false);
        character11EffectStudentsUp.add(false);

        character11EffectStudentsAvailable = new ArrayList<>();
        character11EffectStudentsAvailable.add(true);
        character11EffectStudentsAvailable.add(true);
        character11EffectStudentsAvailable.add(true);
        character11EffectStudentsAvailable.add(true);

        for (int i = 0; i < ((SpoiledPrincess) characters[index - 1]).getStudents().size(); i++) {
            character11EffectStudents.get(i).setImage(new Image(getClass().getClassLoader().getResource(getStudentFlatImage(((SpoiledPrincess) characters[index - 1]).getStudents().get(i))).toString()));
        }

        for (int i = ((SpoiledPrincess) characters[index - 1]).getStudents().size(); i < 4; i++) {
            character11EffectStudents.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/black_disc_flat.png").toString()));
            character11EffectStudentsAvailable.set(i, false);
        }


        Board b = getPlayerBoard();

        for (int i = 0; i < ((SpoiledPrincess) characters[index - 1]).getStudents().size(); i++) {
            if (b.getNumberOfStudents(((SpoiledPrincess) characters[index - 1]).getStudents().get(i)) == 10) {
                character11EffectStudents.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/black_disc_flat.png").toString()));
                character11EffectStudentsAvailable.set(i, false);
            }
        }

        ImageEffects.turnOff(character11EffectConfirmButton);

        confirmCharacter11On = false;
        chosenCharacter11EffectStudent = 0;

        resetTabs();
        tabPane.getTabs().add(character11EffectTab);
        tabPane.getSelectionModel().select(character11EffectTab);
    }

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This method is used to manage the filling and the placement of the islands
     */
    private void setArchipelagos() {
        for (ImageView i : noEntry)
            i.setOpacity(0.0);
        int totCount = 0, count = 0;
        for (Archipelago a : archipelagos) {
            resetIslandCell(islands.get(totCount), totCount);
            resetCentering(islands.get(totCount));
            moveIslandPawns(totCount);
            setIslandPawns(count, 0, totCount);
            if (a.checkMotherNature())
                setMotherNature(totCount);
            else
                resetMotherNature(totCount);
            if (a.checkNoEntry() > 0) {
                noEntry.get(totCount).setOpacity(1.0);
                if (a.checkNoEntry() > 1)
                    noEntryTot.get(totCount).setText(Integer.toString(a.checkNoEntry()));
                else
                    noEntryTot.get(totCount).setText("");
            } else {
                noEntry.get(totCount).setOpacity(0.0);
                noEntryTot.get(totCount).setText("");
            }
            if (a.getTower() != null)
                islandsTower.get(totCount).setImage(new Image(getClass().getClassLoader().getResource(getTowerImage(a.getTower())).toString()));
            else
                islandsTower.get(totCount).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            for (int i = 1; i < a.getIslands().size(); i++) {
                setIslandCell(islands.get(totCount + i), islands.get(totCount));
                resetCentering(islands.get(totCount + i));
                moveIslands(i, totCount);
                moveIslandPawns(totCount + i);
                setIslandPawns(count, i, totCount + i);
                resetMotherNature(totCount + i);
                noEntry.get(totCount + i).setOpacity(0.0);
                noEntryTot.get(totCount + i).setText("");
                if (a.getTower() != null)
                    islandsTower.get(totCount + i).setImage(new Image(getClass().getClassLoader().getResource(getTowerImage(a.getTower())).toString()));
                else
                    islandsTower.get(totCount + i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }
            totCount += a.getIslands().size();
            count++;
        }
    }

    /**
     * This method is used to manage the disposition of the other players' board (depending on the number of players) and fills them
     * @param numPlayers it's the number of players
     */
    private void setBoards(int numPlayers) {
        setPlayerBoard(numPlayers);

        int entrDim;

        if (numPlayers == 3)
            entrDim = 9;
        else
            entrDim = 7;

        if (players.size() == 2) { // CASE 2 PLAYERS
            Board b2 = null;
            Player p2 = null;
            int b2greentot, b2redtot, b2yellowtot, b2pinktot, b2bluetot, b2towers;

            for (Player p : players) {
                if (!p.getNickname().equals(nickname)) {
                    b2 = p.getBoard();
                    p2 = p;
                }
            }

            for (int i = 0; i < entrDim; i++) {
                if (b2.getEntrance()[i] != null)
                    board3Entrance.get(i).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(b2.getEntrance()[i])).toString()));
            }

            b2greentot = b2.getNumberOfStudents(Student.GREEN);
            for (int i = 0; i < 10; i++) {
                if (i < b2greentot)
                    board3GreenStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/green_disc.png").toString()));
                else
                    board3GreenStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b2redtot = b2.getNumberOfStudents(Student.RED);
            for (int i = 0; i < 10; i++) {
                if (i < b2redtot)
                    board3RedStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/red_disc.png").toString()));
                else
                    board3RedStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b2yellowtot = b2.getNumberOfStudents(Student.YELLOW);
            for (int i = 0; i < 10; i++) {
                if (i < b2yellowtot)
                    board3YellowStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/yellow_disc.png").toString()));
                else
                    board3YellowStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b2pinktot = b2.getNumberOfStudents(Student.PINK);
            for (int i = 0; i < 10; i++) {
                if (i < b2pinktot)
                    board3PinkStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/pink_disc.png").toString()));
                else
                    board3PinkStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b2bluetot = b2.getNumberOfStudents(Student.BLUE);
            for (int i = 0; i < 10; i++) {
                if (i < b2bluetot)
                    board3BlueStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/blue_disc.png").toString()));
                else
                    board3BlueStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            if (b2.isProfessorPresent(Student.GREEN))
                boardMidGreenProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/green_prof.png").toString()));
            else
                boardMidGreenProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b2.isProfessorPresent(Student.RED))
                boardMidRedProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/red_prof.png").toString()));
            else
                boardMidRedProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b2.isProfessorPresent(Student.YELLOW))
                boardMidYellowProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/yellow_prof.png").toString()));
            else
                boardMidYellowProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b2.isProfessorPresent(Student.PINK))
                boardMidPinkProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/pink_prof.png").toString()));
            else
                boardMidPinkProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b2.isProfessorPresent(Student.BLUE))
                boardMidBlueProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/blue_prof.png").toString()));
            else
                boardMidBlueProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));

            b2towers = b2.getNumberTowers();
            for (int i = 0; i < 8; i++) {
                if (i < b2towers)
                    board3Towers.get(i).setImage(new Image(getClass().getClassLoader().getResource(getTowerImage(b2.getTowerColor())).toString()));
                else
                    board3Towers.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            if (expertMode) {
                boardMidCoinImage.setOpacity(1.0);
                ImageEffects.addShadow(boardMidCoinImage);
                boardMidCoinLabel.setOpacity(1.0);
                boardMidCoinLabel.setText(Integer.toString(p2.getCoins()));
                boardTopCoinImage.setOpacity(0.0);
                boardTopCoinLabel.setOpacity(0.0);
                boardBotCoinImage.setOpacity(0.0);
                boardBotCoinLabel.setOpacity(0.0);
            } else {
                boardMidCoinImage.setOpacity(0.0);
                boardMidCoinLabel.setOpacity(0.0);
                boardTopCoinImage.setOpacity(0.0);
                boardTopCoinLabel.setOpacity(0.0);
                boardBotCoinImage.setOpacity(0.0);
                boardBotCoinLabel.setOpacity(0.0);
            }
        } else if (players.size() == 3) { // CASE 3 PLAYERS
            Board b2 = null, b3 = null;
            Player p2 = null, p3 = null;
            int b2greentot, b2redtot, b2yellowtot, b2pinktot, b2bluetot, b2towers, b3greentot, b3redtot, b3yellowtot, b3pinktot, b3bluetot, b3towers;
            boolean foundSecond = false;

            for (Player p : players) {
                if (!p.getNickname().equals(nickname)) {
                    if (!foundSecond) {
                        b2 = p.getBoard();
                        p2 = p;
                        foundSecond = true;
                    } else {
                        b3 = p.getBoard();
                        p3 = p;
                    }
                }
            }

            // PLAYER 2 --------------------------------------------------------------

            for (int i = 0; i < entrDim; i++) {
                if (b2.getEntrance()[i] != null)
                    board2Entrance.get(i).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(b2.getEntrance()[i])).toString()));
            }

            b2greentot = b2.getNumberOfStudents(Student.GREEN);
            for (int i = 0; i < 10; i++) {
                if (i < b2greentot)
                    board2GreenStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/green_disc.png").toString()));
                else
                    board2GreenStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b2redtot = b2.getNumberOfStudents(Student.RED);
            for (int i = 0; i < 10; i++) {
                if (i < b2redtot)
                    board2RedStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/red_disc.png").toString()));
                else
                    board2RedStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b2yellowtot = b2.getNumberOfStudents(Student.YELLOW);
            for (int i = 0; i < 10; i++) {
                if (i < b2yellowtot)
                    board2YellowStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/yellow_disc.png").toString()));
                else
                    board2YellowStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b2pinktot = b2.getNumberOfStudents(Student.PINK);
            for (int i = 0; i < 10; i++) {
                if (i < b2pinktot)
                    board2PinkStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/pink_disc.png").toString()));
                else
                    board2PinkStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b2bluetot = b2.getNumberOfStudents(Student.BLUE);
            for (int i = 0; i < 10; i++) {
                if (i < b2bluetot)
                    board2BlueStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/blue_disc.png").toString()));
                else
                    board2BlueStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            if (b2.isProfessorPresent(Student.GREEN))
                boardTopGreenProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/green_prof.png").toString()));
            else
                boardTopGreenProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b2.isProfessorPresent(Student.RED))
                boardTopRedProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/red_prof.png").toString()));
            else
                boardTopRedProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b2.isProfessorPresent(Student.YELLOW))
                boardTopYellowProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/yellow_prof.png").toString()));
            else
                boardTopYellowProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b2.isProfessorPresent(Student.PINK))
                boardTopPinkProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/pink_prof.png").toString()));
            else
                boardTopPinkProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b2.isProfessorPresent(Student.BLUE))
                boardTopBlueProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/blue_prof.png").toString()));
            else
                boardTopBlueProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));

            b2towers = b2.getNumberTowers();
            for (int i = 0; i < 8; i++) {
                if (i < b2towers)
                    board2Towers.get(i).setImage(new Image(getClass().getClassLoader().getResource(getTowerImage(b2.getTowerColor())).toString()));
                else
                    board2Towers.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            // PLAYER 3 --------------------------------------------------------------

            for (int i = 0; i < entrDim; i++) {
                if (b3.getEntrance()[i] != null)
                    board4Entrance.get(i).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(b3.getEntrance()[i])).toString()));
            }

            b3greentot = b3.getNumberOfStudents(Student.GREEN);
            for (int i = 0; i < 10; i++) {
                if (i < b3greentot)
                    board4GreenStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/green_disc.png").toString()));
                else
                    board4GreenStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b3redtot = b3.getNumberOfStudents(Student.RED);
            for (int i = 0; i < 10; i++) {
                if (i < b3redtot)
                    board4RedStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/red_disc.png").toString()));
                else
                    board4RedStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b3yellowtot = b3.getNumberOfStudents(Student.YELLOW);
            for (int i = 0; i < 10; i++) {
                if (i < b3yellowtot)
                    board4YellowStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/yellow_disc.png").toString()));
                else
                    board4YellowStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b3pinktot = b3.getNumberOfStudents(Student.PINK);
            for (int i = 0; i < 10; i++) {
                if (i < b3pinktot)
                    board4PinkStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/pink_disc.png").toString()));
                else
                    board4PinkStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b3bluetot = b3.getNumberOfStudents(Student.BLUE);
            for (int i = 0; i < 10; i++) {
                if (i < b3bluetot)
                    board4BlueStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/blue_disc.png").toString()));
                else
                    board4BlueStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            if (b3.isProfessorPresent(Student.GREEN))
                boardBotGreenProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/green_prof.png").toString()));
            else
                boardBotGreenProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b3.isProfessorPresent(Student.RED))
                boardBotRedProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/red_prof.png").toString()));
            else
                boardBotRedProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b3.isProfessorPresent(Student.YELLOW))
                boardBotYellowProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/yellow_prof.png").toString()));
            else
                boardBotYellowProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b3.isProfessorPresent(Student.PINK))
                boardBotPinkProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/pink_prof.png").toString()));
            else
                boardBotPinkProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b3.isProfessorPresent(Student.BLUE))
                boardBotBlueProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/blue_prof.png").toString()));
            else
                boardBotBlueProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));


            b3towers = b3.getNumberTowers();
            for (int i = 0; i < 8; i++) {
                if (i < b3towers)
                    board4Towers.get(i).setImage(new Image(getClass().getClassLoader().getResource(getTowerImage(b3.getTowerColor())).toString()));
                else
                    board4Towers.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            if (expertMode) {
                boardTopCoinImage.setOpacity(1.0);
                ImageEffects.addShadow(boardTopCoinImage);
                boardTopCoinLabel.setOpacity(1.0);
                boardTopCoinLabel.setText(Integer.toString(p2.getCoins()));
                boardBotCoinImage.setOpacity(1.0);
                ImageEffects.addShadow(boardBotCoinImage);
                boardBotCoinLabel.setOpacity(1.0);
                boardBotCoinLabel.setText(Integer.toString(p3.getCoins()));
                boardMidCoinImage.setOpacity(0.0);
                boardMidCoinLabel.setOpacity(0.0);
            } else {
                boardMidCoinImage.setOpacity(0.0);
                boardMidCoinLabel.setOpacity(0.0);
                boardTopCoinImage.setOpacity(0.0);
                boardTopCoinLabel.setOpacity(0.0);
                boardBotCoinImage.setOpacity(0.0);
                boardBotCoinLabel.setOpacity(0.0);
            }
        } else { // CASE 4 PLAYERS
            Board b2 = null, b3 = null, b4 = null;
            Player p2 = null, p3 = null, p4 = null;
            int b2greentot, b2redtot, b2yellowtot, b2pinktot, b2bluetot, b2towers, b3greentot, b3redtot, b3yellowtot, b3pinktot, b3bluetot, b3towers, b4greentot, b4redtot, b4yellowtot, b4pinktot, b4bluetot, b4towers;

            for (Team t : teams) {
                if (t.getPlayers()[0].getNickname().equals(nickname) || t.getPlayers()[1].getNickname().equals(nickname)) {
                    if (t.getPlayers()[0].getNickname().equals(nickname)) {
                        b2 = t.getPlayers()[1].getBoard();
                        p2 = t.getPlayers()[1];
                    } else {
                        b2 = t.getPlayers()[0].getBoard();
                        p2 = t.getPlayers()[0];
                    }
                } else {
                    b3 = t.getPlayers()[0].getBoard();
                    b4 = t.getPlayers()[1].getBoard();
                    p3 = t.getPlayers()[0];
                    p4 = t.getPlayers()[1];
                }
            }

            // PLAYER 2 --------------------------------------------------------------

            for (int i = 0; i < entrDim; i++) {
                if (b2.getEntrance()[i] != null)
                    board2Entrance.get(i).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(b2.getEntrance()[i])).toString()));
            }

            b2greentot = b2.getNumberOfStudents(Student.GREEN);
            for (int i = 0; i < 10; i++) {
                if (i < b2greentot)
                    board2GreenStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/green_disc.png").toString()));
                else
                    board2GreenStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b2redtot = b2.getNumberOfStudents(Student.RED);
            for (int i = 0; i < 10; i++) {
                if (i < b2redtot)
                    board2RedStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/red_disc.png").toString()));
                else
                    board2RedStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b2yellowtot = b2.getNumberOfStudents(Student.YELLOW);
            for (int i = 0; i < 10; i++) {
                if (i < b2yellowtot)
                    board2YellowStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/yellow_disc.png").toString()));
                else
                    board2YellowStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b2pinktot = b2.getNumberOfStudents(Student.PINK);
            for (int i = 0; i < 10; i++) {
                if (i < b2pinktot)
                    board2PinkStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/pink_disc.png").toString()));
                else
                    board2PinkStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b2bluetot = b2.getNumberOfStudents(Student.BLUE);
            for (int i = 0; i < 10; i++) {
                if (i < b2bluetot)
                    board2BlueStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/blue_disc.png").toString()));
                else
                    board2BlueStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            if (b2.isProfessorPresent(Student.GREEN))
                boardTopGreenProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/green_prof.png").toString()));
            else
                boardTopGreenProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b2.isProfessorPresent(Student.RED))
                boardTopRedProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/red_prof.png").toString()));
            else
                boardTopRedProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b2.isProfessorPresent(Student.YELLOW))
                boardTopYellowProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/yellow_prof.png").toString()));
            else
                boardTopYellowProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b2.isProfessorPresent(Student.PINK))
                boardTopPinkProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/pink_prof.png").toString()));
            else
                boardTopPinkProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b2.isProfessorPresent(Student.BLUE))
                boardTopBlueProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/blue_prof.png").toString()));
            else
                boardTopBlueProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));

            b2towers = b2.getNumberTowers();
            for (int i = 0; i < 8; i++) {
                if (i < b2towers)
                    board2Towers.get(i).setImage(new Image(getClass().getClassLoader().getResource(getTowerImage(b2.getTowerColor())).toString()));
                else
                    board2Towers.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            // PLAYER 3 --------------------------------------------------------------

            for (int i = 0; i < entrDim; i++) {
                if (b3.getEntrance()[i] != null)
                    board3Entrance.get(i).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(b3.getEntrance()[i])).toString()));
            }

            b3greentot = b3.getNumberOfStudents(Student.GREEN);
            for (int i = 0; i < 10; i++) {
                if (i < b3greentot)
                    board3GreenStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/green_disc.png").toString()));
                else
                    board3GreenStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b3redtot = b3.getNumberOfStudents(Student.RED);
            for (int i = 0; i < 10; i++) {
                if (i < b3redtot)
                    board3RedStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/red_disc.png").toString()));
                else
                    board3RedStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b3yellowtot = b3.getNumberOfStudents(Student.YELLOW);
            for (int i = 0; i < 10; i++) {
                if (i < b3yellowtot)
                    board3YellowStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/yellow_disc.png").toString()));
                else
                    board3YellowStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b3pinktot = b3.getNumberOfStudents(Student.PINK);
            for (int i = 0; i < 10; i++) {
                if (i < b3pinktot)
                    board3PinkStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/pink_disc.png").toString()));
                else
                    board3PinkStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b3bluetot = b3.getNumberOfStudents(Student.BLUE);
            for (int i = 0; i < 10; i++) {
                if (i < b3bluetot)
                    board3BlueStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/blue_disc.png").toString()));
                else
                    board3BlueStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            if (b3.isProfessorPresent(Student.GREEN))
                boardMidGreenProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/green_prof.png").toString()));
            else
                boardMidGreenProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b3.isProfessorPresent(Student.RED))
                boardMidRedProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/red_prof.png").toString()));
            else
                boardMidRedProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b3.isProfessorPresent(Student.YELLOW))
                boardMidYellowProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/yellow_prof.png").toString()));
            else
                boardMidYellowProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b3.isProfessorPresent(Student.PINK))
                boardMidPinkProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/pink_prof.png").toString()));
            else
                boardMidPinkProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b3.isProfessorPresent(Student.BLUE))
                boardMidBlueProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/blue_prof.png").toString()));
            else
                boardMidBlueProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));

            b3towers = b3.getNumberTowers();
            for (int i = 0; i < 8; i++) {
                if (i < b3towers)
                    board3Towers.get(i).setImage(new Image(getClass().getClassLoader().getResource(getTowerImage(b3.getTowerColor())).toString()));
                else
                    board3Towers.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            // PLAYER 4 --------------------------------------------------------------

            for (int i = 0; i < entrDim; i++) {
                if (b4.getEntrance()[i] != null)
                    board4Entrance.get(i).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(b4.getEntrance()[i])).toString()));
            }

            b4greentot = b4.getNumberOfStudents(Student.GREEN);
            for (int i = 0; i < 10; i++) {
                if (i < b4greentot)
                    board4GreenStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/green_disc.png").toString()));
                else
                    board4GreenStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b4redtot = b4.getNumberOfStudents(Student.RED);
            for (int i = 0; i < 10; i++) {
                if (i < b4redtot)
                    board4RedStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/red_disc.png").toString()));
                else
                    board4RedStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b4yellowtot = b4.getNumberOfStudents(Student.YELLOW);
            for (int i = 0; i < 10; i++) {
                if (i < b4yellowtot)
                    board4YellowStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/yellow_disc.png").toString()));
                else
                    board4YellowStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b4pinktot = b4.getNumberOfStudents(Student.PINK);
            for (int i = 0; i < 10; i++) {
                if (i < b4pinktot)
                    board4PinkStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/pink_disc.png").toString()));
                else
                    board4PinkStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            b4bluetot = b4.getNumberOfStudents(Student.BLUE);
            for (int i = 0; i < 10; i++) {
                if (i < b4bluetot)
                    board4BlueStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/blue_disc.png").toString()));
                else
                    board4BlueStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            if (b4.isProfessorPresent(Student.GREEN))
                boardBotGreenProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/green_prof.png").toString()));
            else
                boardBotGreenProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b4.isProfessorPresent(Student.RED))
                boardBotRedProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/red_prof.png").toString()));
            else
                boardBotRedProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b4.isProfessorPresent(Student.YELLOW))
                boardBotYellowProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/yellow_prof.png").toString()));
            else
                boardBotYellowProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b4.isProfessorPresent(Student.PINK))
                boardBotPinkProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/pink_prof.png").toString()));
            else
                boardBotPinkProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            if (b4.isProfessorPresent(Student.BLUE))
                boardBotBlueProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/blue_prof.png").toString()));
            else
                boardBotBlueProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));

            b4towers = b4.getNumberTowers();
            for (int i = 0; i < 8; i++) {
                if (i < b4towers)
                    board4Towers.get(i).setImage(new Image(getClass().getClassLoader().getResource(getTowerImage(b4.getTowerColor())).toString()));
                else
                    board4Towers.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }

            if (expertMode) {
                boardTopCoinImage.setOpacity(1.0);
                ImageEffects.addShadow(boardTopCoinImage);
                boardTopCoinLabel.setOpacity(1.0);
                boardTopCoinLabel.setText(Integer.toString(p2.getCoins()));
                boardMidCoinImage.setOpacity(1.0);
                ImageEffects.addShadow(boardMidCoinImage);
                boardMidCoinLabel.setOpacity(1.0);
                boardMidCoinLabel.setText(Integer.toString(p3.getCoins()));
                boardBotCoinImage.setOpacity(1.0);
                ImageEffects.addShadow(boardBotCoinImage);
                boardBotCoinLabel.setOpacity(1.0);
                boardBotCoinLabel.setText(Integer.toString(p4.getCoins()));
            } else {
                boardMidCoinImage.setOpacity(0.0);
                boardMidCoinLabel.setOpacity(0.0);
                boardTopCoinImage.setOpacity(0.0);
                boardTopCoinLabel.setOpacity(0.0);
                boardBotCoinImage.setOpacity(0.0);
                boardBotCoinLabel.setOpacity(0.0);
            }
        }
    }

    /**
     * This method is used to fill the board of the player of this Client
     * @param numPlayers it's the number of players
     */
    private void setPlayerBoard(int numPlayers) {
        int entrDim, b1greentot, b1redtot, b1yellowtot, b1pinktot, b1bluetot, b1towers;
        Board b1 = null;
        Player p1 = null;

        setBoardsBackground(numPlayers);

        for (Player p : players) {
            if (p.getNickname().equals(nickname)) {
                b1 = p.getBoard();
                p1 = p;
            }
        }

        if (numPlayers == 3)
            entrDim = 9;
        else
            entrDim = 7;

        for (int i = 0; i < entrDim; i++) {
            if (b1.getEntrance()[i] != null)
                board1Entrance.get(i).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(b1.getEntrance()[i])).toString()));
        }

        b1greentot = b1.getNumberOfStudents(Student.GREEN);
        for (int i = 0; i < 10; i++) {
            if (i < b1greentot) {
                board1GreenStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/green_disc.png").toString()));
                board1GreenStud.get(i).setEffect(null);
            } else {
                board1GreenStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
                board1GreenStud.get(i).setEffect(null);
            }
        }

        b1redtot = b1.getNumberOfStudents(Student.RED);
        for (int i = 0; i < 10; i++) {
            if (i < b1redtot) {
                board1RedStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/red_disc.png").toString()));
                board1RedStud.get(i).setEffect(null);
            } else {
                board1RedStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
                board1RedStud.get(i).setEffect(null);
            }
        }

        b1yellowtot = b1.getNumberOfStudents(Student.YELLOW);
        for (int i = 0; i < 10; i++) {
            if (i < b1yellowtot) {
                board1YellowStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/yellow_disc.png").toString()));
                board1YellowStud.get(i).setEffect(null);
            } else {
                board1YellowStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
                board1YellowStud.get(i).setEffect(null);
            }
        }

        b1pinktot = b1.getNumberOfStudents(Student.PINK);
        for (int i = 0; i < 10; i++) {
            if (i < b1pinktot) {
                board1PinkStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/pink_disc.png").toString()));
                board1PinkStud.get(i).setEffect(null);
            } else {
                board1PinkStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
                board1PinkStud.get(i).setEffect(null);
            }
        }

        b1bluetot = b1.getNumberOfStudents(Student.BLUE);
        for (int i = 0; i < 10; i++) {
            if (i < b1bluetot) {
                board1BlueStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/blue_disc.png").toString()));
                board1BlueStud.get(i).setEffect(null);
            } else {
                board1BlueStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
                board1BlueStud.get(i).setEffect(null);
            }
        }

        if (b1.isProfessorPresent(Student.GREEN))
            boardPlayerGreenProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/green_prof.png").toString()));
        else
            boardPlayerGreenProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
        if (b1.isProfessorPresent(Student.RED))
            boardPlayerRedProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/red_prof.png").toString()));
        else
            boardPlayerRedProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
        if (b1.isProfessorPresent(Student.YELLOW))
            boardPlayerYellowProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/yellow_prof.png").toString()));
        else
            boardPlayerYellowProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
        if (b1.isProfessorPresent(Student.PINK))
            boardPlayerPinkProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/pink_prof.png").toString()));
        else
            boardPlayerPinkProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
        if (b1.isProfessorPresent(Student.BLUE))
            boardPlayerBlueProf.setImage(new Image(getClass().getClassLoader().getResource("images/professors/blue_prof.png").toString()));
        else
            boardPlayerBlueProf.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));

        b1towers = b1.getNumberTowers();
        for (int i = 0; i < 8; i++) {
            if (i < b1towers)
                board1Towers.get(i).setImage(new Image(getClass().getClassLoader().getResource(getTowerImage(b1.getTowerColor())).toString()));
            else
                board1Towers.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
        }

        if (expertMode) {
            boardPlayerCoinImage.setOpacity(1.0);
            ImageEffects.addShadow(boardPlayerCoinImage);
            boardPlayerCoinLabel.setOpacity(1.0);
            boardPlayerCoinLabel.setText(Integer.toString(p1.getCoins()));
        } else {
            boardPlayerCoinImage.setOpacity(0.0);
            boardPlayerCoinLabel.setOpacity(0.0);
        }
    }

    /**
     * This method is used to remove mother nature from an island
     * @param id it's the number of the island
     */
    private void resetMotherNature(int id) {
        islandsMN.get(id).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
    }

    /**
     * This method is used to set mother nature on an island
     * @param id it's the number of the island
     */
    private void setMotherNature(int id) {
        islandsMN.get(id).setImage(new Image(getClass().getClassLoader().getResource("images/game/mother_nature.png").toString()));
    }

    /**
     * This method is used to put the island in a specific cell of the GridPane
     * @param i it's the image of the island
     * @param root it's the index of the island which corresponds to a specific cell
     */
    private void resetIslandCell(ImageView i, int root) {
        switch (root) {
            case 0:
                GridPane.setColumnIndex(i, 2);
                GridPane.setRowIndex(i, 1);
                break;
            case 1:
                GridPane.setColumnIndex(i, 4);
                GridPane.setRowIndex(i, 1);
                break;
            case 2:
                GridPane.setColumnIndex(i, 6);
                GridPane.setRowIndex(i, 1);
                break;
            case 3:
                GridPane.setColumnIndex(i, 8);
                GridPane.setRowIndex(i, 1);
                break;
            case 4:
                GridPane.setColumnIndex(i, 10);
                GridPane.setRowIndex(i, 1);
                break;
            case 5:
                GridPane.setColumnIndex(i, 11);
                GridPane.setRowIndex(i, 2);
                break;
            case 6:
                GridPane.setColumnIndex(i, 10);
                GridPane.setRowIndex(i, 3);
                break;
            case 7:
                GridPane.setColumnIndex(i, 8);
                GridPane.setRowIndex(i, 3);
                break;
            case 8:
                GridPane.setColumnIndex(i, 6);
                GridPane.setRowIndex(i, 3);
                break;
            case 9:
                GridPane.setColumnIndex(i, 4);
                GridPane.setRowIndex(i, 3);
                break;
            case 10:
                GridPane.setColumnIndex(i, 2);
                GridPane.setRowIndex(i, 3);
                break;
            case 11:
                GridPane.setColumnIndex(i, 1);
                GridPane.setRowIndex(i, 2);
                break;

        }
    }

    /**
     * This method is used to move an island on a different GridPane's cell
     * @param i it's the image of the island
     * @param root it's the index of the island which corresponds to a specific cell
     */
    private void setIslandCell(ImageView i, ImageView root) {
        GridPane.setColumnIndex(i, GridPane.getColumnIndex(root));
        GridPane.setRowIndex(i, GridPane.getRowIndex(root));
    }

    /**
     * This method is used to center an image in a cell
     * @param i it's the image that has to be centered
     */
    private void resetCentering(ImageView i) {
        i.setTranslateX(0);
        i.setTranslateY(0);
    }

    /**
     * This method is used to center a label in a cell
     * @param l it's the label that has to be centered
     */
    private void resetLabelCentering(Label l) {
        l.setTranslateX(0);
        l.setTranslateY(0);
    }

    /**
     * This method is used to move ad island when it is connected to another island
     * @param curr it's the index of the island to move
     * @param root it's the first island of the archipelago that contains "curr"
     */
    private void moveIslands(int curr, int root) {
        switch (root) {
            case 0:
                if (curr < 8) {
                    islands.get(root + curr).setTranslateX(99 * curr);
                    if (curr % 2 == 1)
                        islands.get(root + curr).setTranslateY(57);
                } else if (curr == 8) {
                    islands.get(root + curr).setTranslateX(99 * curr);
                    islands.get(root + curr).setTranslateY(57 * 2);
                } else if (curr == 9) {
                    islands.get(root + curr).setTranslateX(99 * (curr - 1));
                    islands.get(root + curr).setTranslateY(57 * 4);
                }
                break;
            case 1:
                if (curr < 6) {
                    islands.get(root + curr).setTranslateX(99 * curr);
                    if (curr % 2 == 1)
                        islands.get(root + curr).setTranslateY(57);
                } else if (curr == 6) {
                    islands.get(root + curr).setTranslateX(99 * curr);
                    islands.get(root + curr).setTranslateY(57 * 2);
                } else if (curr == 7) {
                    islands.get(root + curr).setTranslateX(99 * (curr - 1));
                    islands.get(root + curr).setTranslateY(57 * 4);
                } else if (curr == 8) {
                    islands.get(root + curr).setTranslateX(99 * (curr - 3));
                    islands.get(root + curr).setTranslateY(57 * 5);
                } else if (curr == 9) {
                    islands.get(root + curr).setTranslateX(99 * (curr - 5));
                    islands.get(root + curr).setTranslateY(57 * 4);
                }
                break;
            case 2:
                if (curr < 6) {
                    islands.get(root + curr).setTranslateX(99 * curr);
                    if (curr % 2 == 1)
                        islands.get(root + curr).setTranslateY(57);
                } else if (curr == 6) {
                    islands.get(root + curr).setTranslateX(99 * (curr - 1));
                    islands.get(root + curr).setTranslateY(57 * 3);
                } else if (curr == 7) {
                    islands.get(root + curr).setTranslateX(99 * (curr - 3));
                    islands.get(root + curr).setTranslateY(57 * 4);
                } else if (curr == 8) {
                    islands.get(root + curr).setTranslateX(99 * (curr - 5));
                    islands.get(root + curr).setTranslateY(57 * 5);
                } else if (curr == 9) {
                    islands.get(root + curr).setTranslateX(99 * (curr - 7));
                    islands.get(root + curr).setTranslateY(57 * 4);
                }
                break;
            case 3:
                if (curr < 4) {
                    islands.get(root + curr).setTranslateX(99 * curr);
                    if (curr % 2 == 1)
                        islands.get(root + curr).setTranslateY(57);
                } else if (curr == 4) {
                    islands.get(root + curr).setTranslateX(99 * (curr - 1));
                    islands.get(root + curr).setTranslateY(57 * 3);
                } else if (curr == 5) {
                    islands.get(root + curr).setTranslateX(99 * (curr - 3));
                    islands.get(root + curr).setTranslateY(57 * 4);
                } else if (curr == 6) {
                    islands.get(root + curr).setTranslateX(99);
                    islands.get(root + curr).setTranslateY(57 * 5);
                } else if (curr == 7) {
                    islands.get(root + curr).setTranslateY(57 * 4);
                } else if (curr == 8) {
                    island12.setTranslateX(-99);
                    island12.setTranslateY(57 * 5);
                }
                break;
            case 4:
                if (curr == 1) {
                    islands.get(root + curr).setTranslateX(99);
                    islands.get(root + curr).setTranslateY(57);
                } else if (curr == 2) {
                    islands.get(root + curr).setTranslateX(99);
                    islands.get(root + curr).setTranslateY(57 * 3);
                } else if (curr > 2) {
                    islands.get(root + curr).setTranslateX(-99 * (curr - 3));
                    if (curr % 2 == 0)
                        islands.get(root + curr).setTranslateY(57 * 5);
                    else
                        islands.get(root + curr).setTranslateY(57 * 4);
                }
                break;
            case 5:
                islands.get(root + curr).setTranslateX(-99 * curr);
                if (curr % 2 == 1)
                    islands.get(root + curr).setTranslateY(57);
                else
                    islands.get(root + curr).setTranslateY(57 * 2);
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                islands.get(root + curr).setTranslateX(-99 * curr);
                if (curr % 2 == 1)
                    islands.get(root + curr).setTranslateY(-57);
                break;
            case 10:
                islands.get(root + curr).setTranslateX(-99);
                islands.get(root + curr).setTranslateY(-57);
                break;
        }
    }

    /**
     * This method is used to move all the pawns on an island when this island is moved
     * @param id it's the index of the moved island
     */
    private void moveIslandPawns(int id) {
        resetCentering(islandsGreenStud.get(id));
        resetCentering(islandsRedStud.get(id));
        resetCentering(islandsYellowStud.get(id));
        resetCentering(islandsPinkStud.get(id));
        resetCentering(islandsBlueStud.get(id));
        resetCentering(islandsTower.get(id));
        resetCentering(islandsMN.get(id));

        resetLabelCentering(islandsGreenTot.get(id));
        resetLabelCentering(islandsRedTot.get(id));
        resetLabelCentering(islandsYellowTot.get(id));
        resetLabelCentering(islandsPinkTot.get(id));
        resetLabelCentering(islandsBlueTot.get(id));

        GridPane.setColumnIndex(islandsGreenStud.get(id), GridPane.getColumnIndex(islands.get(id)));
        GridPane.setRowIndex(islandsGreenStud.get(id), GridPane.getRowIndex(islands.get(id)));
        GridPane.setColumnIndex(islandsRedStud.get(id), GridPane.getColumnIndex(islands.get(id)));
        GridPane.setRowIndex(islandsRedStud.get(id), GridPane.getRowIndex(islands.get(id)));
        GridPane.setColumnIndex(islandsYellowStud.get(id), GridPane.getColumnIndex(islands.get(id)));
        GridPane.setRowIndex(islandsYellowStud.get(id), GridPane.getRowIndex(islands.get(id)));
        GridPane.setColumnIndex(islandsPinkStud.get(id), GridPane.getColumnIndex(islands.get(id)));
        GridPane.setRowIndex(islandsPinkStud.get(id), GridPane.getRowIndex(islands.get(id)));
        GridPane.setColumnIndex(islandsBlueStud.get(id), GridPane.getColumnIndex(islands.get(id)));
        GridPane.setRowIndex(islandsBlueStud.get(id), GridPane.getRowIndex(islands.get(id)));
        GridPane.setColumnIndex(islandsTower.get(id), GridPane.getColumnIndex(islands.get(id)));
        GridPane.setRowIndex(islandsTower.get(id), GridPane.getRowIndex(islands.get(id)));
        GridPane.setColumnIndex(islandsMN.get(id), GridPane.getColumnIndex(islands.get(id)));
        GridPane.setRowIndex(islandsMN.get(id), GridPane.getRowIndex(islands.get(id)));

        GridPane.setColumnIndex(islandsGreenTot.get(id), GridPane.getColumnIndex(islands.get(id)));
        GridPane.setRowIndex(islandsGreenTot.get(id), GridPane.getRowIndex(islands.get(id)));
        GridPane.setColumnIndex(islandsRedTot.get(id), GridPane.getColumnIndex(islands.get(id)));
        GridPane.setRowIndex(islandsRedTot.get(id), GridPane.getRowIndex(islands.get(id)));
        GridPane.setColumnIndex(islandsYellowTot.get(id), GridPane.getColumnIndex(islands.get(id)));
        GridPane.setRowIndex(islandsYellowTot.get(id), GridPane.getRowIndex(islands.get(id)));
        GridPane.setColumnIndex(islandsPinkTot.get(id), GridPane.getColumnIndex(islands.get(id)));
        GridPane.setRowIndex(islandsPinkTot.get(id), GridPane.getRowIndex(islands.get(id)));
        GridPane.setColumnIndex(islandsBlueTot.get(id), GridPane.getColumnIndex(islands.get(id)));
        GridPane.setRowIndex(islandsBlueTot.get(id), GridPane.getRowIndex(islands.get(id)));

        islandsGreenStud.get(id).setTranslateX(islands.get(id).getTranslateX() + 20.0);
        islandsGreenStud.get(id).setTranslateY(islands.get(id).getTranslateY() - 40.0);
        islandsRedStud.get(id).setTranslateX(islands.get(id).getTranslateX() + 40.0);
        islandsRedStud.get(id).setTranslateY(islands.get(id).getTranslateY());
        islandsYellowStud.get(id).setTranslateX(islands.get(id).getTranslateX() + 20.0);
        islandsYellowStud.get(id).setTranslateY(islands.get(id).getTranslateY() + 40.0);
        islandsPinkStud.get(id).setTranslateX(islands.get(id).getTranslateX() - 20.0);
        islandsPinkStud.get(id).setTranslateY(islands.get(id).getTranslateY() + 40.0);
        islandsBlueStud.get(id).setTranslateX(islands.get(id).getTranslateX() - 40.0);
        islandsBlueStud.get(id).setTranslateY(islands.get(id).getTranslateY());
        islandsTower.get(id).setTranslateX(islands.get(id).getTranslateX() - 20.0);
        islandsTower.get(id).setTranslateY(islands.get(id).getTranslateY() - 55.0);
        islandsMN.get(id).setTranslateX(islands.get(id).getTranslateX());
        islandsMN.get(id).setTranslateY(islands.get(id).getTranslateY() - 5.0);

        islandsGreenTot.get(id).setTranslateX(islands.get(id).getTranslateX() + 12.0);
        islandsGreenTot.get(id).setTranslateY(islands.get(id).getTranslateY() - 53.0);
        islandsRedTot.get(id).setTranslateX(islands.get(id).getTranslateX() + 32.0);
        islandsRedTot.get(id).setTranslateY(islands.get(id).getTranslateY() - 13.0);
        islandsYellowTot.get(id).setTranslateX(islands.get(id).getTranslateX() + 12.0);
        islandsYellowTot.get(id).setTranslateY(islands.get(id).getTranslateY() + 27.0);
        islandsPinkTot.get(id).setTranslateX(islands.get(id).getTranslateX() - 28.0);
        islandsPinkTot.get(id).setTranslateY(islands.get(id).getTranslateY() + 27.0);
        islandsBlueTot.get(id).setTranslateX(islands.get(id).getTranslateX() - 48.0);
        islandsBlueTot.get(id).setTranslateY(islands.get(id).getTranslateY() - 13);
    }

    /**
     * This method is used to set and update all the pawns on a specific island
     * @param root it's the index of the first island of the archipelago
     * @param id it's the index of the i-th island of the archipelago
     * @param totCount it's the index of the island
     */
    private void setIslandPawns(int root, int id, int totCount) {
        int green = 0, red = 0, yellow = 0, pink = 0, blue = 0;

        for (Student s : archipelagos.get(root).getIslands().get(id).getStudents()) {
            switch (s) {
                case GREEN:
                    green++;
                    break;
                case RED:
                    red++;
                    break;
                case YELLOW:
                    yellow++;
                    break;
                case PINK:
                    pink++;
                    break;
                case BLUE:
                    blue++;
                    break;
            }
        }

        if (green > 0) {
            islandsGreenStud.get(totCount).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(Student.GREEN)).toString()));
            islandsGreenTot.get(totCount).setText(Integer.toString(green));
        } else {
            islandsGreenStud.get(totCount).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            islandsGreenTot.get(totCount).setText("");
        }
        if (red > 0) {
            islandsRedStud.get(totCount).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(Student.RED)).toString()));
            islandsRedTot.get(totCount).setText(Integer.toString(red));
        } else {
            islandsRedStud.get(totCount).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            islandsRedTot.get(totCount).setText("");
        }
        if (yellow > 0) {
            islandsYellowStud.get(totCount).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(Student.YELLOW)).toString()));
            islandsYellowTot.get(totCount).setText(Integer.toString(yellow));
        } else {
            islandsYellowStud.get(totCount).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            islandsYellowTot.get(totCount).setText("");
        }
        if (pink > 0) {
            islandsPinkStud.get(totCount).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(Student.PINK)).toString()));
            islandsPinkTot.get(totCount).setText(Integer.toString(pink));
        } else {
            islandsPinkStud.get(totCount).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            islandsPinkTot.get(totCount).setText("");
        }
        if (blue > 0) {
            islandsBlueStud.get(totCount).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(Student.BLUE)).toString()));
            islandsBlueTot.get(totCount).setText(Integer.toString(blue));
        } else {
            islandsBlueStud.get(totCount).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            islandsBlueTot.get(totCount).setText("");
        }
    }

    /**
     * This method is used to set the board's image depending on the number of players and the mode
     * @param numPlayers it's the number of players
     */
    private void setBoardsBackground(int numPlayers) {
        Image image;

        if (numPlayers != 3) {
            if (!expertMode)
                image = new Image(getClass().getClassLoader().getResource("images/boards/board_no_exp.png").toString());
            else
                image = new Image(getClass().getClassLoader().getResource("images/boards/board_exp.png").toString());
        } else {
            if (!expertMode)
                image = new Image(getClass().getClassLoader().getResource("images/boards/board_3pl_no_exp.png").toString());
            else
                image = new Image(getClass().getClassLoader().getResource("images/boards/board_3pl_exp.png").toString());
        }

        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(image), CornerRadii.EMPTY, Insets.EMPTY);

        DropShadow borderShadow = new DropShadow();
        borderShadow.setOffsetY(0f);
        borderShadow.setOffsetX(0f);
        borderShadow.setColor(Color.BLACK);
        borderShadow.setWidth(21);
        borderShadow.setHeight(21);
        borderShadow.setRadius(10);
        borderShadow.setSpread(0.25);

        switch (numPlayers) {
            case 2:
                boardPlayerAnchor.setBackground(new Background(backgroundFill));
                boardPlayerAnchor.setEffect(borderShadow);
                boardMidAnchor.setBackground(new Background(backgroundFill));
                boardMidAnchor.setEffect(borderShadow);
                break;
            case 3:
                boardPlayerAnchor.setBackground(new Background(backgroundFill));
                boardPlayerAnchor.setEffect(borderShadow);
                boardTopAnchor.setBackground(new Background(backgroundFill));
                boardTopAnchor.setEffect(borderShadow);
                boardBotAnchor.setBackground(new Background(backgroundFill));
                boardBotAnchor.setEffect(borderShadow);
                break;
            case 4:
                boardPlayerAnchor.setBackground(new Background(backgroundFill));
                boardPlayerAnchor.setEffect(borderShadow);
                boardTopAnchor.setBackground(new Background(backgroundFill));
                boardTopAnchor.setEffect(borderShadow);
                boardMidAnchor.setBackground(new Background(backgroundFill));
                boardMidAnchor.setEffect(borderShadow);
                boardBotAnchor.setBackground(new Background(backgroundFill));
                boardBotAnchor.setEffect(borderShadow);
                break;
        }
    }

    /**
     * @param s it's a student
     * @return the image associated to that student
     */
    private String getStudentImage(Student s) {
        String stud;
        switch (s) {
            case GREEN:
                stud = "images/students/green_disc.png";
                break;
            case RED:
                stud = "images/students/red_disc.png";
                break;
            case YELLOW:
                stud = "images/students/yellow_disc.png";
                break;
            case PINK:
                stud = "images/students/pink_disc.png";
                break;
            case BLUE:
                stud = "images/students/blue_disc.png";
                break;
            default:
                stud = "images/texts/blank.png";
                break;
        }
        return stud;
    }

    /**
     * @param s it's a student
     * @return the flat version image associated to that student
     */
    private String getStudentFlatImage(Student s) {
        String stud;
        switch (s) {
            case GREEN:
                stud = "images/students/green_disc_flat.png";
                break;
            case RED:
                stud = "images/students/red_disc_flat.png";
                break;
            case YELLOW:
                stud = "images/students/yellow_disc_flat.png";
                break;
            case PINK:
                stud = "images/students/pink_disc_flat.png";
                break;
            case BLUE:
                stud = "images/students/blue_disc_flat.png";
                break;
            default:
                stud = "images/texts/blank.png";
                break;
        }
        return stud;
    }

    /**
     * @param t it's a tower
     * @return the image associated to that tower
     */
    private String getTowerImage(Tower t) {
        String tower = null;
        switch (t) {
            case WHITE:
                tower = "images/towers/white_tower.png";
                break;
            case GREY:
                tower = "images/towers/grey_tower.png";
                break;
            case BLACK:
                tower = "images/towers/black_tower.png";
                break;
        }
        return tower;
    }

    /**
     * @return the image associated to a specific wizard
     */
    private String getWizardImage() {
        Wizard w = null;
        String wizard = null;

        for (Player p : players) {
            if (p.getNickname().equals(nickname))
                w = p.getWizard();
        }

        switch (w) {
            case WIZARD1:
                wizard = "images/wizards/wizard_1.png";
                break;
            case WIZARD2:
                wizard = "images/wizards/wizard_2.png";
                break;
            case WIZARD3:
                wizard = "images/wizards/wizard_3.png";
                break;
            case WIZARD4:
                wizard = "images/wizards/wizard_4.png";
                break;
        }

        return wizard;
    }

    /**
     * @param id it's the id number of an Assistant
     * @return the image associated to that Assistant
     */
    private String getAssistantImage(int id) {
        String assistant = null;

        switch (id + 1) {
            case 1:
                assistant = "images/assistants/assistant_1.png";
                break;
            case 2:
                assistant = "images/assistants/assistant_2.png";
                break;
            case 3:
                assistant = "images/assistants/assistant_3.png";
                break;
            case 4:
                assistant = "images/assistants/assistant_4.png";
                break;
            case 5:
                assistant = "images/assistants/assistant_5.png";
                break;
            case 6:
                assistant = "images/assistants/assistant_6.png";
                break;
            case 7:
                assistant = "images/assistants/assistant_7.png";
                break;
            case 8:
                assistant = "images/assistants/assistant_8.png";
                break;
            case 9:
                assistant = "images/assistants/assistant_9.png";
                break;
            case 10:
                assistant = "images/assistants/assistant_10.png";
                break;
        }

        return assistant;
    }

    /**
     * This method set the right nickname for all the players
     */
    private void setNicks() {

        setBoardsDescription();

        if (players.size() == 3)
            setBoardsGrid();

        boardPlayerNick.setText(nickname);

        switch (players.size()) {
            case 2:
                if (players.get(0).getNickname().equals(nickname))
                    boardMidNick.setText(players.get(1).getNickname());
                else
                    boardMidNick.setText(players.get(0).getNickname());
                boardTopNick.setText("");
                boardBotNick.setText("");
                boardTopNickBox.setOpacity(0.0);
                boardBotNickBox.setOpacity(0.0);
                break;
            case 3:
                boolean foundSecond = false;
                for (Player p : players) {
                    if (!p.getNickname().equals(nickname)) {
                        if (!foundSecond) {
                            boardTopNick.setText(p.getNickname());
                            foundSecond = true;
                        } else
                            boardBotNick.setText(p.getNickname());
                    }
                }
                boardMidNick.setText("");
                boardMidNickBox.setOpacity(0.0);
                break;
            case 4:
                for (Team t : teams) {
                    if (t.getPlayers()[0].getNickname().equals(nickname) || t.getPlayers()[1].getNickname().equals(nickname)) {
                        if (t.getPlayers()[0].getNickname().equals(nickname))
                            boardTopNick.setText(t.getPlayers()[1].getNickname());
                        else
                            boardTopNick.setText(t.getPlayers()[0].getNickname());
                    } else {
                        boardMidNick.setText(t.getPlayers()[0].getNickname());
                        boardBotNick.setText(t.getPlayers()[1].getNickname());
                    }
                }
                break;
        }
    }

    /**
     * This method is used to establish if the boards that the player sees on the scene are owned by a teammate or an opponent
     */
    private void setBoardsDescription() {
        switch (players.size()) {
            case 2:
                boardTopDesc.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
                boardMidDesc.setImage(new Image(getClass().getClassLoader().getResource("images/texts/opponent.png").toString()));
                boardBotDesc.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
                break;
            case 3:
                boardTopDesc.setImage(new Image(getClass().getClassLoader().getResource("images/texts/opponent.png").toString()));
                boardMidDesc.setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
                boardBotDesc.setImage(new Image(getClass().getClassLoader().getResource("images/texts/opponent.png").toString()));
                break;
            case 4:
                boardTopDesc.setImage(new Image(getClass().getClassLoader().getResource("images/texts/teammate.png").toString()));
                boardMidDesc.setImage(new Image(getClass().getClassLoader().getResource("images/texts/opponent.png").toString()));
                boardBotDesc.setImage(new Image(getClass().getClassLoader().getResource("images/texts/opponent.png").toString()));
                break;
        }
    }

    /**
     * This method is used to set the GridPane for the other players' boards
     */
    private void setBoardsGrid() {
        RowConstraints r0 = new RowConstraints();
        r0.setPercentHeight(0.0);
        RowConstraints r60 = new RowConstraints();
        r60.setPercentHeight(60.0);
        RowConstraints r20 = new RowConstraints();
        r20.setPercentHeight(20.0);
        RowConstraints r50 = new RowConstraints();
        r50.setPercentHeight(50.0);

        boardsGrid.getRowConstraints().set(0, r50);
        boardsGrid.getRowConstraints().set(1, r0);
        boardsGrid.getRowConstraints().set(2, r50);

        boardMidGrid.getRowConstraints().set(0, r0);
        boardMidGrid.getRowConstraints().set(1, r0);
        boardMidGrid.getRowConstraints().set(2, r0);

        boardTopGrid.getRowConstraints().set(0, r20);
        boardTopGrid.getRowConstraints().set(1, r60);
        boardTopGrid.getRowConstraints().set(2, r20);

        boardBotGrid.getRowConstraints().set(0, r20);
        boardBotGrid.getRowConstraints().set(1, r60);
        boardBotGrid.getRowConstraints().set(2, r20);
    }

    /**
     * This method is used to add an effect that highlights the current player
     * @param index it's the index of the current player
     */
    private void setCurrentPlayerGlow(int index) {
        t = new Timeline();
        t.setCycleCount(Timeline.INDEFINITE);

        t.getKeyFrames().clear();

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(150),
                (ActionEvent event) -> {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.valueOf(String.valueOf(Color.rgb(92, 236, 255))));
                    borderGlow.setWidth(5);
                    borderGlow.setHeight(5);

                    boardsNickBox.get(index).setEffect(borderGlow);
                }));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(300),
                (ActionEvent event) -> {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.valueOf(String.valueOf(Color.rgb(92, 236, 255))));
                    borderGlow.setWidth(10);
                    borderGlow.setHeight(10);

                    boardsNickBox.get(index).setEffect(borderGlow);
                }));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(450),
                (ActionEvent event) -> {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.valueOf(String.valueOf(Color.rgb(92, 236, 255))));
                    borderGlow.setWidth(15);
                    borderGlow.setHeight(15);

                    boardsNickBox.get(index).setEffect(borderGlow);
                }));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(600),
                (ActionEvent event) -> {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.valueOf(String.valueOf(Color.rgb(92, 236, 255))));
                    borderGlow.setWidth(20);
                    borderGlow.setHeight(20);

                    boardsNickBox.get(index).setEffect(borderGlow);
                }));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(750),
                (ActionEvent event) -> {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.valueOf(String.valueOf(Color.rgb(92, 236, 255))));
                    borderGlow.setWidth(25);
                    borderGlow.setHeight(25);

                    boardsNickBox.get(index).setEffect(borderGlow);
                }));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(900),
                (ActionEvent event) -> {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.valueOf(String.valueOf(Color.rgb(92, 236, 255))));
                    borderGlow.setWidth(30);
                    borderGlow.setHeight(30);

                    boardsNickBox.get(index).setEffect(borderGlow);
                }));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1050),
                (ActionEvent event) -> {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.valueOf(String.valueOf(Color.rgb(92, 236, 255))));
                    borderGlow.setWidth(35);
                    borderGlow.setHeight(35);

                    boardsNickBox.get(index).setEffect(borderGlow);
                }));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1200),
                (ActionEvent event) -> {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.valueOf(String.valueOf(Color.rgb(92, 236, 255))));
                    borderGlow.setWidth(40);
                    borderGlow.setHeight(40);

                    boardsNickBox.get(index).setEffect(borderGlow);
                }));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1350),
                (ActionEvent event) -> {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.valueOf(String.valueOf(Color.rgb(92, 236, 255))));
                    borderGlow.setWidth(45);
                    borderGlow.setHeight(45);

                    boardsNickBox.get(index).setEffect(borderGlow);
                }));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1500),
                (ActionEvent event) -> {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.valueOf(String.valueOf(Color.rgb(92, 236, 255))));
                    borderGlow.setWidth(50);
                    borderGlow.setHeight(50);

                    boardsNickBox.get(index).setEffect(borderGlow);
                }));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1650),
                (ActionEvent event) -> {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.valueOf(String.valueOf(Color.rgb(92, 236, 255))));
                    borderGlow.setWidth(45);
                    borderGlow.setHeight(45);

                    boardsNickBox.get(index).setEffect(borderGlow);
                }));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1800),
                (ActionEvent event) -> {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.valueOf(String.valueOf(Color.rgb(92, 236, 255))));
                    borderGlow.setWidth(40);
                    borderGlow.setHeight(40);

                    boardsNickBox.get(index).setEffect(borderGlow);
                }));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1950),
                (ActionEvent event) -> {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.valueOf(String.valueOf(Color.rgb(92, 236, 255))));
                    borderGlow.setWidth(35);
                    borderGlow.setHeight(35);

                    boardsNickBox.get(index).setEffect(borderGlow);
                }));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(2100),
                (ActionEvent event) -> {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.valueOf(String.valueOf(Color.rgb(92, 236, 255))));
                    borderGlow.setWidth(30);
                    borderGlow.setHeight(30);

                    boardsNickBox.get(index).setEffect(borderGlow);
                }));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(2250),
                (ActionEvent event) -> {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.valueOf(String.valueOf(Color.rgb(92, 236, 255))));
                    borderGlow.setWidth(25);
                    borderGlow.setHeight(25);

                    boardsNickBox.get(index).setEffect(borderGlow);
                }));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(2400),
                (ActionEvent event) -> {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.valueOf(String.valueOf(Color.rgb(92, 236, 255))));
                    borderGlow.setWidth(20);
                    borderGlow.setHeight(20);

                    boardsNickBox.get(index).setEffect(borderGlow);
                }));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(2550),
                (ActionEvent event) -> {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.valueOf(String.valueOf(Color.rgb(92, 236, 255))));
                    borderGlow.setWidth(15);
                    borderGlow.setHeight(15);

                    boardsNickBox.get(index).setEffect(borderGlow);
                }));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(2700),
                (ActionEvent event) -> {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.valueOf(String.valueOf(Color.rgb(92, 236, 255))));
                    borderGlow.setWidth(10);
                    borderGlow.setHeight(10);

                    boardsNickBox.get(index).setEffect(borderGlow);
                }));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(2850),
                (ActionEvent event) -> {
                    DropShadow borderGlow = new DropShadow();
                    borderGlow.setOffsetY(0f);
                    borderGlow.setOffsetX(0f);
                    borderGlow.setColor(Color.valueOf(String.valueOf(Color.rgb(92, 236, 255))));
                    borderGlow.setWidth(5);
                    borderGlow.setHeight(5);

                    boardsNickBox.get(index).setEffect(borderGlow);
                }));

        t.play();
    }

    /**
     * This method reset the highlight effect when a certain player isn't the current one anymore
     */
    private void resetCurrentPlayer() {
        DropShadow borderShadow = new DropShadow();
        borderShadow.setOffsetY(0f);
        borderShadow.setOffsetX(0f);
        borderShadow.setColor(Color.BLACK);
        borderShadow.setRadius(10);
        borderShadow.setSpread(0.15);

        for (int i = 0; i < 3; i++)
            boardsNickBox.get(i).setEffect(borderShadow);
    }

    /**
     * This method is used to drag and drop the entrance's students during the MOVE_STUDENTS phase
     * @param node it's the students that the player wants to move
     */
    private void setDraggableNode(Node node) {
        node.setOnMousePressed(e -> {
            if (phase == Status.MOVE_STUDENTS && ((players.size() != 3 && studentsMoved < 3) || (players.size() == 3 && studentsMoved < 4))) {
                if (availableEntranceMoving.get(imageToEntranceIndex((ImageView) node))) {
                    startColumn = GridPane.getColumnIndex(node);
                    startRow = GridPane.getRowIndex(node);

                    startXPos = e.getSceneX() - node.getTranslateX();
                    startYPos = e.getSceneY() - node.getTranslateY();

                    for (Timeline t : entranceBlink) {
                        if (t != null)
                            t.stop();
                    }

                    for (int i = 0; i < 9; i++) {
                        if (availableEntranceMoving.get(i))
                            board1Entrance.get(i).setEffect(null);
                    }

                    setIslandsBlink();

                    for (Timeline t : islandsBlink) {
                        if (t != null)
                            t.play();
                    }

                    setHallBlink();

                    for (Timeline t : hallBlink) {
                        if (t != null)
                            t.play();
                    }
                }
            }
        });

        node.setOnMouseDragged(e -> {
            if (phase == Status.MOVE_STUDENTS && ((players.size() != 3 && studentsMoved < 3) || (players.size() == 3 && studentsMoved < 4))) {
                if (availableEntranceMoving.get(imageToEntranceIndex((ImageView) node))) {
                    node.setTranslateX(e.getSceneX() - startXPos);
                    node.setTranslateY(e.getSceneY() - startYPos);
                }
            }
        });

        node.setOnMouseReleased(e -> {
            if (phase == Status.MOVE_STUDENTS && availableEntranceMoving.get(imageToEntranceIndex((ImageView) node)) && ((players.size() != 3 && studentsMoved < 3) || (players.size() == 3 && studentsMoved < 4))) {
                if (movedOnIsland(e) != -1) {
                    int dest = movedOnIsland(e);
                    boolean found = false;
                    for (int index = 0, counter = 0; index < archipelagos.size() && !found; index++) {
                        if (archipelagos.get(index).getIslands().size() - 1 + counter >= dest) {
                            found = true;

                            zoomInOutIsland(islands.get(dest));
                            archipelagos.get(index).getIslands().get(dest - counter).addStudent(imageToStudent((ImageView) node));

                            destinationIndexes.add(dest + 1);
                            entranceIndexes.add(imageToEntranceIndex((ImageView) node) + 1);
                            studentsMoved++;

                            GridPane.setColumnIndex(node, startColumn);
                            GridPane.setRowIndex(node, startRow);
                            node.setTranslateX(0);
                            node.setTranslateY(0);
                            availableEntranceMoving.set(imageToEntranceIndex((ImageView) node), false);


                            for (Timeline t : entranceBlink) {
                                if (t != null)
                                    t.play();
                            }

                            for (Timeline t : islandsBlink) {
                                if (t != null)
                                    t.stop();
                            }

                            for (ImageView i : islands)
                                ImageEffects.addShadow(i);

                            resetHallBlink();

                            //setPlayerBoard(players.size());
                            setArchipelagos();

                            //for (int i = 0; i < availableEntranceMoving.size(); i++) {
                            for (int i = 0; i < 9; i++) {
                                if (!availableEntranceMoving.get(i))
                                    board1Entrance.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
                            }

                            if (studentsMoved == 1 && expertMode)
                                resizeCharacter();

                            Platform.runLater(() -> ((Stage) tabPane.getScene().getWindow()).show());

                            if ((players.size() != 3 && studentsMoved == 3) || (players.size() == 3 && studentsMoved == 4)) {
                                for (Timeline t : entranceBlink) {
                                    if (t != null)
                                        t.stop();
                                }

                                for (int i = 0; i < 9; i++) {
                                    if (availableEntranceMoving.get(i))
                                        board1Entrance.get(i).setEffect(null);
                                }

                                MoveStudentsMessage msm = new MoveStudentsMessage(entranceIndexes, destinationIndexes);
                                getNetworkHandler().update(msm);
                                phase = Status.STANDBY;
                            }

                        } else {
                            counter += archipelagos.get(index).getIslands().size();
                        }
                    }
                } else if (movedOnHall(e, (ImageView) node) != 0) {
                    Board b = getPlayerBoard();
                    switch (movedOnHall(e, (ImageView) node)) {
                        case 1:
                            board1GreenStud.get(b.getNumberOfStudents(Student.GREEN) + addedToHall[0]).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(Student.GREEN)).toString()));
                            addedToHall[0]++;
                            break;
                        case 2:
                            board1RedStud.get(b.getNumberOfStudents(Student.RED) + addedToHall[1]).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(Student.RED)).toString()));
                            addedToHall[1]++;
                            break;
                        case 3:
                            board1YellowStud.get(b.getNumberOfStudents(Student.YELLOW) + addedToHall[2]).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(Student.YELLOW)).toString()));
                            addedToHall[2]++;
                            break;
                        case 4:
                            board1PinkStud.get(b.getNumberOfStudents(Student.PINK) + addedToHall[3]).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(Student.PINK)).toString()));
                            addedToHall[3]++;
                            break;
                        case 5:
                            board1BlueStud.get(b.getNumberOfStudents(Student.BLUE) + addedToHall[4]).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(Student.BLUE)).toString()));
                            addedToHall[4]++;
                            break;
                    }

                    destinationIndexes.add(13);
                    entranceIndexes.add(imageToEntranceIndex((ImageView) node) + 1);
                    studentsMoved++;

                    GridPane.setColumnIndex(node, startColumn);
                    GridPane.setRowIndex(node, startRow);
                    node.setTranslateX(0);
                    node.setTranslateY(0);
                    availableEntranceMoving.set(imageToEntranceIndex((ImageView) node), false);


                    for (Timeline t : entranceBlink) {
                        if (t != null)
                            t.play();
                    }

                    for (Timeline t : islandsBlink) {
                        if (t != null)
                            t.stop();
                    }

                    for (ImageView i : islands)
                        ImageEffects.addShadow(i);

                    resetHallBlink();

                    // for (int i = 0; i < availableEntranceMoving.size(); i++) {
                    for (int i = 0; i < 9; i++) {
                        if (!availableEntranceMoving.get(i))
                            board1Entrance.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
                    }

                    if (studentsMoved == 1 && expertMode)
                        resizeCharacter();

                    Platform.runLater(() -> ((Stage) tabPane.getScene().getWindow()).show());

                    if ((players.size() != 3 && studentsMoved == 3) || (players.size() == 3 && studentsMoved == 4)) {
                        for (Timeline t : entranceBlink) {
                            if (t != null)
                                t.stop();
                        }

                        for (int i = 0; i < 9; i++) {
                            if (availableEntranceMoving.get(i))
                                board1Entrance.get(i).setEffect(null);
                        }

                        MoveStudentsMessage msm = new MoveStudentsMessage(entranceIndexes, destinationIndexes);
                        getNetworkHandler().update(msm);
                        phase = Status.STANDBY;
                    }
                } else {
                    GridPane.setColumnIndex(node, startColumn);
                    GridPane.setRowIndex(node, startRow);
                    node.setTranslateX(0);
                    node.setTranslateY(0);

                    for (Timeline t : entranceBlink) {
                        if (t != null)
                            t.play();
                    }

                    for (Timeline t : islandsBlink) {
                        if (t != null)
                            t.stop();
                    }

                    for (ImageView i : islands)
                        ImageEffects.addShadow(i);

                    resetHallBlink();
                }
            }
        });
    }

    /**
     * This method checks if a student was moved on an island
     * @param e it represents the mouse's action of dropping a student
     * @return -1 if the student wasn't moved on an island, otherwise it returns the index of the island on which it was moved
     */
    private int movedOnIsland(MouseEvent e) {
        int count = 0, island = -1;
        for (ImageView i : islands) {
            Bounds islPos = i.localToScene(i.getBoundsInLocal());
            if (e.getSceneX() >= islPos.getMinX() && e.getSceneX() <= islPos.getMaxX() && e.getSceneY() >= islPos.getMinY() && e.getSceneY() <= islPos.getMaxY()) {
                island = count;
                return island;
            }
            count++;
        }
        return island;
    }

    /**
     * This method checks if a student was moved on the hall
     * @param e it represents the mouse's action of dropping a student
     * @param image it's the image of the moved students
     * @return -1 if the student wasn't moved on the hall, otherwise it returns the index of the hall's students table on which it was moved
     */
    private int movedOnHall(MouseEvent e, ImageView image) {
        int dest = 0;

        Board b = getPlayerBoard();

        Bounds gMin;
        if (b.getNumberOfStudents(Student.GREEN) + addedToHall[0] < 10)
            gMin = board1GreenStud.get(b.getNumberOfStudents(Student.GREEN) + addedToHall[0]).localToScene(board1GreenStud.get(b.getNumberOfStudents(Student.GREEN) + addedToHall[0]).getBoundsInLocal());
        else
            gMin = board1GreenStud.get(9).localToScene(board1GreenStud.get(9).getBoundsInLocal());
        Bounds gMax = board1GreenStud.get(9).localToScene(board1GreenStud.get(9).getBoundsInLocal());

        Bounds rMin;
        if (b.getNumberOfStudents(Student.RED) + addedToHall[1] < 10)
            rMin = board1RedStud.get(b.getNumberOfStudents(Student.RED) + addedToHall[1]).localToScene(board1RedStud.get(b.getNumberOfStudents(Student.RED) + addedToHall[1]).getBoundsInLocal());
        else
            rMin = board1RedStud.get(9).localToScene(board1RedStud.get(9).getBoundsInLocal());
        Bounds rMax = board1RedStud.get(9).localToScene(board1RedStud.get(9).getBoundsInLocal());

        Bounds yMin;
        if (b.getNumberOfStudents(Student.YELLOW) + addedToHall[2] < 10)
            yMin = board1YellowStud.get(b.getNumberOfStudents(Student.YELLOW) + addedToHall[2]).localToScene(board1YellowStud.get(b.getNumberOfStudents(Student.YELLOW) + addedToHall[2]).getBoundsInLocal());
        else
            yMin = board1YellowStud.get(9).localToScene(board1YellowStud.get(9).getBoundsInLocal());
        Bounds yMax = board1YellowStud.get(9).localToScene(board1YellowStud.get(9).getBoundsInLocal());

        Bounds pMin;
        if (b.getNumberOfStudents(Student.PINK) + addedToHall[3] < 10)
            pMin = board1PinkStud.get(b.getNumberOfStudents(Student.PINK) + addedToHall[3]).localToScene(board1PinkStud.get(b.getNumberOfStudents(Student.PINK) + addedToHall[3]).getBoundsInLocal());
        else
            pMin = board1PinkStud.get(9).localToScene(board1PinkStud.get(9).getBoundsInLocal());
        Bounds pMax = board1PinkStud.get(9).localToScene(board1PinkStud.get(9).getBoundsInLocal());

        Bounds bMin;
        if (b.getNumberOfStudents(Student.BLUE) + addedToHall[4] < 10)
            bMin = board1BlueStud.get(b.getNumberOfStudents(Student.BLUE) + addedToHall[4]).localToScene(board1BlueStud.get(b.getNumberOfStudents(Student.BLUE) + addedToHall[4]).getBoundsInLocal());
        else
            bMin = board1BlueStud.get(9).localToScene(board1BlueStud.get(9).getBoundsInLocal());
        Bounds bMax = board1BlueStud.get(9).localToScene(board1BlueStud.get(9).getBoundsInLocal());

        if (b.getNumberOfStudents(Student.GREEN) + addedToHall[0] < 10 && imageToStudent(image) == Student.GREEN && e.getSceneX() >= gMin.getMinX() && e.getSceneX() <= gMax.getMaxX() && e.getSceneY() >= gMin.getMinY() && e.getSceneY() <= gMax.getMaxY())
            dest = 1;
        else if (b.getNumberOfStudents(Student.RED) + addedToHall[1] < 10 && imageToStudent(image) == Student.RED && e.getSceneX() >= rMin.getMinX() && e.getSceneX() <= rMax.getMaxX() && e.getSceneY() >= rMin.getMinY() && e.getSceneY() <= rMax.getMaxY())
            dest = 2;
        else if (b.getNumberOfStudents(Student.YELLOW) + addedToHall[2] < 10 && imageToStudent(image) == Student.YELLOW && e.getSceneX() >= yMin.getMinX() && e.getSceneX() <= yMax.getMaxX() && e.getSceneY() >= yMin.getMinY() && e.getSceneY() <= yMax.getMaxY())
            dest = 3;
        else if (b.getNumberOfStudents(Student.PINK) + addedToHall[3] < 10 && imageToStudent(image) == Student.PINK && e.getSceneX() >= pMin.getMinX() && e.getSceneX() <= pMax.getMaxX() && e.getSceneY() >= pMin.getMinY() && e.getSceneY() <= pMax.getMaxY())
            dest = 4;
        else if (b.getNumberOfStudents(Student.BLUE) + addedToHall[4] < 10 && imageToStudent(image) == Student.BLUE && e.getSceneX() >= bMin.getMinX() && e.getSceneX() <= bMax.getMaxX() && e.getSceneY() >= bMin.getMinY() && e.getSceneY() <= bMax.getMaxY())
            dest = 5;

        return dest;
    }

    /**
     * This methods is used to convert a student's image to a corresponding student
     * @param image it's the image of the student
     * @return the corresponding student
     */
    private Student imageToStudent(ImageView image) {
        Student student = null;
        switch (image.getImage().getUrl().toString().charAt(image.getImage().getUrl().toString().length() - 10)) {
            case 'n':
                student = Student.GREEN;
                break;
            case 'd':
                student = Student.RED;
                break;
            case 'w':
                student = Student.YELLOW;
                break;
            case 'k':
                student = Student.PINK;
                break;
            case 'e':
                student = Student.BLUE;
                break;
        }
        return student;
    }

    /**
     * This method is used to convert an entrance student's image to the corresponding entrance index
     * @param image it's the image of the student
     * @return the corresponding index of the entrance
     */
    private int imageToEntranceIndex(ImageView image) {
        boolean found = false;
        int index = -1, eDim;
        if (players.size() == 3)
            eDim = 9;
        else
            eDim = 7;
        for (int i = 0; i < eDim && !found; i++) {
            if (image == board1Entrance.get(i)) {
                index = i;
                found = true;
            }
        }
        return index;
    }

    /**
     * This method is used to move students from the entrance during the MOVE_STUDENTS phase
     */
    private void setMovingPawns() {
        entranceIndexes.clear();
        destinationIndexes.clear();

        Board b = getPlayerBoard();

        availableEntranceMoving.clear();
        for (Student s : b.getEntrance()) {
            if (s != null)
                availableEntranceMoving.add(true);
            else
                availableEntranceMoving.add(false);
        }
        if (players.size() == 3) {
            availableEntranceMoving.add(false);
            availableEntranceMoving.add(false);
        }


        setDraggableNode(board1Entrance.get(0));
        setDraggableNode(board1Entrance.get(1));
        setDraggableNode(board1Entrance.get(2));
        setDraggableNode(board1Entrance.get(3));
        setDraggableNode(board1Entrance.get(4));
        setDraggableNode(board1Entrance.get(5));
        setDraggableNode(board1Entrance.get(6));
        if (players.size() == 3) {
            setDraggableNode(board1Entrance.get(7));
            setDraggableNode(board1Entrance.get(8));
        }

        setEntranceBlink();

        for (Timeline t : entranceBlink) {
            if (t != null) {
                t.play();
            }
        }
    }

    /**
     * This method is used to add a blink effect to an image
     * @param s it's a string that specifies if the image is on an island, in the entrance and in the hall
     * @param image it's the image
     */
    private void setDynamicBlink(String s, ImageView image) {
        Timeline g = new Timeline();
        g.setCycleCount(Timeline.INDEFINITE);

        EventHandler<ActionEvent> e0 = actionEvent -> {
            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.WHITE);
            borderGlow.setWidth(0);
            borderGlow.setHeight(0);
            borderGlow.setSpread(0.7);

            image.setEffect(borderGlow);
        };

        EventHandler<ActionEvent> e1 = actionEvent -> {
            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.WHITE);
            borderGlow.setWidth(2.5);
            borderGlow.setHeight(2.5);
            borderGlow.setSpread(0.7);

            image.setEffect(borderGlow);
        };

        EventHandler<ActionEvent> e2 = actionEvent -> {
            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.WHITE);
            borderGlow.setWidth(5);
            borderGlow.setHeight(5);
            borderGlow.setSpread(0.7);

            image.setEffect(borderGlow);
        };

        EventHandler<ActionEvent> e3 = actionEvent -> {
            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.WHITE);
            borderGlow.setWidth(7.5);
            borderGlow.setHeight(7.5);
            borderGlow.setSpread(0.7);

            image.setEffect(borderGlow);
        };

        EventHandler<ActionEvent> e4 = actionEvent -> {
            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.WHITE);
            borderGlow.setWidth(10);
            borderGlow.setHeight(10);
            borderGlow.setSpread(0.7);

            image.setEffect(borderGlow);
        };

        EventHandler<ActionEvent> e5 = actionEvent -> {
            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.WHITE);
            borderGlow.setWidth(12.5);
            borderGlow.setHeight(12.5);
            borderGlow.setSpread(0.7);

            image.setEffect(borderGlow);
        };

        EventHandler<ActionEvent> e6 = actionEvent -> {
            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.WHITE);
            borderGlow.setWidth(15);
            borderGlow.setHeight(15);
            borderGlow.setSpread(0.7);

            image.setEffect(borderGlow);
        };

        EventHandler<ActionEvent> e7 = actionEvent -> {
            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.WHITE);
            borderGlow.setWidth(17.5);
            borderGlow.setHeight(17.5);
            borderGlow.setSpread(0.7);

            image.setEffect(borderGlow);
        };

        EventHandler<ActionEvent> e8 = actionEvent -> {
            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.WHITE);
            borderGlow.setWidth(20);
            borderGlow.setHeight(20);
            borderGlow.setSpread(0.7);

            image.setEffect(borderGlow);
        };

        EventHandler<ActionEvent> e9 = actionEvent -> {
            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.WHITE);
            borderGlow.setWidth(22.5);
            borderGlow.setHeight(22.5);
            borderGlow.setSpread(0.7);

            image.setEffect(borderGlow);
        };

        EventHandler<ActionEvent> e10 = actionEvent -> {
            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.WHITE);
            borderGlow.setWidth(25);
            borderGlow.setHeight(25);
            borderGlow.setSpread(0.7);

            image.setEffect(borderGlow);
        };

        EventHandler<ActionEvent> e11 = actionEvent -> {
            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.WHITE);
            borderGlow.setWidth(27.5);
            borderGlow.setHeight(27.5);
            borderGlow.setSpread(0.7);

            image.setEffect(borderGlow);
        };

        EventHandler<ActionEvent> e12 = actionEvent -> {
            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.WHITE);
            borderGlow.setWidth(30);
            borderGlow.setHeight(30);
            borderGlow.setSpread(0.7);

            image.setEffect(borderGlow);
        };

        EventHandler<ActionEvent> e13 = actionEvent -> {
            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.WHITE);
            borderGlow.setWidth(32.5);
            borderGlow.setHeight(32.5);
            borderGlow.setSpread(0.7);

            image.setEffect(borderGlow);
        };

        EventHandler<ActionEvent> e14 = actionEvent -> {
            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.WHITE);
            borderGlow.setWidth(35);
            borderGlow.setHeight(35);
            borderGlow.setSpread(0.7);

            image.setEffect(borderGlow);
        };

        EventHandler<ActionEvent> e15 = actionEvent -> {
            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.WHITE);
            borderGlow.setWidth(37.5);
            borderGlow.setHeight(37.5);
            borderGlow.setSpread(0.7);

            image.setEffect(borderGlow);
        };

        EventHandler<ActionEvent> e16 = actionEvent -> {
            DropShadow borderGlow = new DropShadow();
            borderGlow.setOffsetY(0f);
            borderGlow.setOffsetX(0f);
            borderGlow.setColor(Color.WHITE);
            borderGlow.setWidth(40);
            borderGlow.setHeight(40);
            borderGlow.setSpread(0.7);

            image.setEffect(borderGlow);
        };

        g.getKeyFrames().add(new KeyFrame(Duration.millis(50), e1));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(100), e2));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(150), e3));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(200), e4));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(250), e5));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(300), e6));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(350), e7));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(400), e8));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(450), e9));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(500), e10));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(550), e11));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(600), e12));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(650), e13));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(700), e14));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(750), e15));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(800), e16));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(850), e15));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(900), e14));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(950), e13));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(1000), e12));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(1050), e11));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(1100), e10));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(1150), e9));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(1200), e8));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(1250), e7));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(1300), e6));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(1350), e5));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(1400), e4));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(1450), e3));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(1500), e2));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(1550), e1));
        g.getKeyFrames().add(new KeyFrame(Duration.millis(1600), e0));

        switch (s) {
            case "e":
                entranceBlink.add(g);
                break;
            case "h":
                hallBlink.add(g);
                break;
            case "i":
                islandsBlink.add(g);
                break;
        }
    }

    /**
     * This method is used to add a blink effect to all the students in the entrance
     */
    private void setEntranceBlink() {
        for (int i = 0; i < 9; i++) {
            if (availableEntranceMoving.get(i)) {
                setDynamicBlink("e", board1Entrance.get(i));
            } else {
                entranceBlink.add(null);
            }
        }
    }

    /**
     * This method is used to add a blink effect to all the students on the island
     */
    private void setIslandsBlink() {
        for (int i = 0; i < 12; i++)
            setDynamicBlink("i", islands.get(i));
    }

    /**
     * This method is used to add a blink effect to all the students in the hall
     */
    private void setHallBlink() {
        Board b = getPlayerBoard();

        for (int i = b.getNumberOfStudents(Student.GREEN) + addedToHall[0]; i < 10; i++) {
            board1GreenStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/circle.png").toString()));
            setDynamicBlink("h", board1GreenStud.get(i));
        }
        for (int i = b.getNumberOfStudents(Student.RED) + addedToHall[1]; i < 10; i++) {
            board1RedStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/circle.png").toString()));
            setDynamicBlink("h", board1RedStud.get(i));
        }
        for (int i = b.getNumberOfStudents(Student.YELLOW) + addedToHall[2]; i < 10; i++) {
            board1YellowStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/circle.png").toString()));
            setDynamicBlink("h", board1YellowStud.get(i));
        }
        for (int i = b.getNumberOfStudents(Student.PINK) + addedToHall[3]; i < 10; i++) {
            board1PinkStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/circle.png").toString()));
            setDynamicBlink("h", board1PinkStud.get(i));
        }
        for (int i = b.getNumberOfStudents(Student.BLUE) + addedToHall[4]; i < 10; i++) {
            board1BlueStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/students/circle.png").toString()));
            setDynamicBlink("h", board1BlueStud.get(i));
        }
    }

    /**
     * This method is used to reset the blink effect to all the students in the hall
     */
    private void resetHallBlink() {
        Board b = getPlayerBoard();

        for (Timeline t : hallBlink) {
            if (t != null)
                t.stop();
        }

        hallBlink.clear();

        for (int i = b.getNumberOfStudents(Student.GREEN) + addedToHall[0]; i < 10; i++) {
            board1GreenStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            board1GreenStud.get(i).setEffect(null);
        }
        for (int i = b.getNumberOfStudents(Student.RED) + addedToHall[1]; i < 10; i++) {
            board1RedStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            board1RedStud.get(i).setEffect(null);
        }
        for (int i = b.getNumberOfStudents(Student.YELLOW) + addedToHall[2]; i < 10; i++) {
            board1YellowStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            board1YellowStud.get(i).setEffect(null);
        }
        for (int i = b.getNumberOfStudents(Student.PINK) + addedToHall[3]; i < 10; i++) {
            board1PinkStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            board1PinkStud.get(i).setEffect(null);
        }
        for (int i = b.getNumberOfStudents(Student.BLUE) + addedToHall[4]; i < 10; i++) {
            board1BlueStud.get(i).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            board1BlueStud.get(i).setEffect(null);
        }
    }

    /**
     * This method is used to add a zoom-in/zoom-out effect on an island
     * @param i it's the image of the island
     */
    private void zoomInOutIsland(ImageView i) {
        Timeline z = new Timeline();
        z.setCycleCount(1);

        z.getKeyFrames().add(new KeyFrame(
                Duration.millis(0),
                (ActionEvent event) -> {
                    i.setScaleX(1.3);
                    i.setScaleY(1.3);
                }));

        z.getKeyFrames().add(new KeyFrame(
                Duration.millis(600),
                (ActionEvent event) -> {
                    i.setScaleX(1.0);
                    i.setScaleY(1.0);
                }));

        z.play();
    }

    /**
     * This method is used to place the students on the clouds
     */
    private void setClouds() {
        int end;
        if (clouds.size() == 3)
            end = 4;
        else
            end = 3;
        for (int i = 0; i < clouds.size(); i++) {
            ImageEffects.turnOn(cloudImages.get(i));
            ImageEffects.addShadow(cloudImages.get(i));
            for (int j = 0; j < end; j++) {
                if (j < clouds.get(i).getStudents().size())
                    cloudPawns.get(i).get(j).setImage(new Image(getClass().getClassLoader().getResource(getStudentImage(clouds.get(i).getStudents().get(j))).toString()));
                else
                    cloudPawns.get(i).get(j).setImage(new Image(getClass().getClassLoader().getResource("images/texts/blank.png").toString()));
            }
        }

        setCloudsGrid(clouds.size());
    }

    /**
     * This method is used to show a specific tab depending on the phase
     */
    private void showTab() {
        switch (phase) {
            case CHOOSE_ASSISTANT:
                tabPane.getSelectionModel().select(assistantsTab);
                break;
            case MOVE_STUDENTS:
                tabPane.getSelectionModel().select(tableTab);
                break;
            case MOVE_MOTHER_NATURE:
                tabPane.getSelectionModel().select(motherNatureTab);
                break;
            case CHOOSE_CLOUD:
                tabPane.getSelectionModel().select(cloudsTab);
                break;
            default:
                tabPane.getSelectionModel().select(boardsTab);
                break;
        }
    }

    /**
     * This method is used to close and reopen all the tabs
     */
    private void resetTabs() {
        int numTabs = tabPane.getTabs().size();
        tabPane.getTabs().clear();
        tabPane.getTabs().add(tableTab);
        tabPane.getTabs().add(boardsTab);
        tabPane.getTabs().add(assistantsTab);
        if (numTabs == 6)
            tabPane.getTabs().add(motherNatureTab);
        tabPane.getTabs().add(cloudsTab);
        if (expertMode)
            tabPane.getTabs().add(charactersTab);
    }

    /**
     * @return the board of the player of this Client
     */
    private Board getPlayerBoard(){
        Board board = null;
        for (Player p : players) {
            if (p.getNickname().equals(nickname)) {
                board = p.getBoard();
            }
        }
        return board;
    }

    /**
     * This method sets up all the ArrayLists used in this scene
     */
    private void createArrayLists() {
        // BOARD 1 ----------------------------------------------------------------------
        board1Entrance = new ArrayList<>();
        board1Entrance.add(boardPlayerEntrance1);
        board1Entrance.add(boardPlayerEntrance2);
        board1Entrance.add(boardPlayerEntrance3);
        board1Entrance.add(boardPlayerEntrance4);
        board1Entrance.add(boardPlayerEntrance5);
        board1Entrance.add(boardPlayerEntrance6);
        board1Entrance.add(boardPlayerEntrance7);
        board1Entrance.add(boardPlayerEntrance8);
        board1Entrance.add(boardPlayerEntrance9);

        board1GreenStud = new ArrayList<>();
        board1GreenStud.add(boardPlayerGreenStud1);
        board1GreenStud.add(boardPlayerGreenStud2);
        board1GreenStud.add(boardPlayerGreenStud3);
        board1GreenStud.add(boardPlayerGreenStud4);
        board1GreenStud.add(boardPlayerGreenStud5);
        board1GreenStud.add(boardPlayerGreenStud6);
        board1GreenStud.add(boardPlayerGreenStud7);
        board1GreenStud.add(boardPlayerGreenStud8);
        board1GreenStud.add(boardPlayerGreenStud9);
        board1GreenStud.add(boardPlayerGreenStud10);

        board1RedStud = new ArrayList<>();
        board1RedStud.add(boardPlayerRedStud1);
        board1RedStud.add(boardPlayerRedStud2);
        board1RedStud.add(boardPlayerRedStud3);
        board1RedStud.add(boardPlayerRedStud4);
        board1RedStud.add(boardPlayerRedStud5);
        board1RedStud.add(boardPlayerRedStud6);
        board1RedStud.add(boardPlayerRedStud7);
        board1RedStud.add(boardPlayerRedStud8);
        board1RedStud.add(boardPlayerRedStud9);
        board1RedStud.add(boardPlayerRedStud10);

        board1YellowStud = new ArrayList<>();
        board1YellowStud.add(boardPlayerYellowStud1);
        board1YellowStud.add(boardPlayerYellowStud2);
        board1YellowStud.add(boardPlayerYellowStud3);
        board1YellowStud.add(boardPlayerYellowStud4);
        board1YellowStud.add(boardPlayerYellowStud5);
        board1YellowStud.add(boardPlayerYellowStud6);
        board1YellowStud.add(boardPlayerYellowStud7);
        board1YellowStud.add(boardPlayerYellowStud8);
        board1YellowStud.add(boardPlayerYellowStud9);
        board1YellowStud.add(boardPlayerYellowStud10);

        board1PinkStud = new ArrayList<>();
        board1PinkStud.add(boardPlayerPinkStud1);
        board1PinkStud.add(boardPlayerPinkStud2);
        board1PinkStud.add(boardPlayerPinkStud3);
        board1PinkStud.add(boardPlayerPinkStud4);
        board1PinkStud.add(boardPlayerPinkStud5);
        board1PinkStud.add(boardPlayerPinkStud6);
        board1PinkStud.add(boardPlayerPinkStud7);
        board1PinkStud.add(boardPlayerPinkStud8);
        board1PinkStud.add(boardPlayerPinkStud9);
        board1PinkStud.add(boardPlayerPinkStud10);

        board1BlueStud = new ArrayList<>();
        board1BlueStud.add(boardPlayerBlueStud1);
        board1BlueStud.add(boardPlayerBlueStud2);
        board1BlueStud.add(boardPlayerBlueStud3);
        board1BlueStud.add(boardPlayerBlueStud4);
        board1BlueStud.add(boardPlayerBlueStud5);
        board1BlueStud.add(boardPlayerBlueStud6);
        board1BlueStud.add(boardPlayerBlueStud7);
        board1BlueStud.add(boardPlayerBlueStud8);
        board1BlueStud.add(boardPlayerBlueStud9);
        board1BlueStud.add(boardPlayerBlueStud10);

        board1Towers = new ArrayList<>();
        board1Towers.add(boardPlayerTower1);
        board1Towers.add(boardPlayerTower2);
        board1Towers.add(boardPlayerTower3);
        board1Towers.add(boardPlayerTower4);
        board1Towers.add(boardPlayerTower5);
        board1Towers.add(boardPlayerTower6);
        board1Towers.add(boardPlayerTower7);
        board1Towers.add(boardPlayerTower8);

        // BOARD 2 ----------------------------------------------------------------------
        board2Entrance = new ArrayList<>();
        board2Entrance.add(boardTopEntrance1);
        board2Entrance.add(boardTopEntrance2);
        board2Entrance.add(boardTopEntrance3);
        board2Entrance.add(boardTopEntrance4);
        board2Entrance.add(boardTopEntrance5);
        board2Entrance.add(boardTopEntrance6);
        board2Entrance.add(boardTopEntrance7);
        board2Entrance.add(boardTopEntrance8);
        board2Entrance.add(boardTopEntrance9);

        board2GreenStud = new ArrayList<>();
        board2GreenStud.add(boardTopGreenStud1);
        board2GreenStud.add(boardTopGreenStud2);
        board2GreenStud.add(boardTopGreenStud3);
        board2GreenStud.add(boardTopGreenStud4);
        board2GreenStud.add(boardTopGreenStud5);
        board2GreenStud.add(boardTopGreenStud6);
        board2GreenStud.add(boardTopGreenStud7);
        board2GreenStud.add(boardTopGreenStud8);
        board2GreenStud.add(boardTopGreenStud9);
        board2GreenStud.add(boardTopGreenStud10);

        board2RedStud = new ArrayList<>();
        board2RedStud.add(boardTopRedStud1);
        board2RedStud.add(boardTopRedStud2);
        board2RedStud.add(boardTopRedStud3);
        board2RedStud.add(boardTopRedStud4);
        board2RedStud.add(boardTopRedStud5);
        board2RedStud.add(boardTopRedStud6);
        board2RedStud.add(boardTopRedStud7);
        board2RedStud.add(boardTopRedStud8);
        board2RedStud.add(boardTopRedStud9);
        board2RedStud.add(boardTopRedStud10);

        board2YellowStud = new ArrayList<>();
        board2YellowStud.add(boardTopYellowStud1);
        board2YellowStud.add(boardTopYellowStud2);
        board2YellowStud.add(boardTopYellowStud3);
        board2YellowStud.add(boardTopYellowStud4);
        board2YellowStud.add(boardTopYellowStud5);
        board2YellowStud.add(boardTopYellowStud6);
        board2YellowStud.add(boardTopYellowStud7);
        board2YellowStud.add(boardTopYellowStud8);
        board2YellowStud.add(boardTopYellowStud9);
        board2YellowStud.add(boardTopYellowStud10);

        board2PinkStud = new ArrayList<>();
        board2PinkStud.add(boardTopPinkStud1);
        board2PinkStud.add(boardTopPinkStud2);
        board2PinkStud.add(boardTopPinkStud3);
        board2PinkStud.add(boardTopPinkStud4);
        board2PinkStud.add(boardTopPinkStud5);
        board2PinkStud.add(boardTopPinkStud6);
        board2PinkStud.add(boardTopPinkStud7);
        board2PinkStud.add(boardTopPinkStud8);
        board2PinkStud.add(boardTopPinkStud9);
        board2PinkStud.add(boardTopPinkStud10);

        board2BlueStud = new ArrayList<>();
        board2BlueStud.add(boardTopBlueStud1);
        board2BlueStud.add(boardTopBlueStud2);
        board2BlueStud.add(boardTopBlueStud3);
        board2BlueStud.add(boardTopBlueStud4);
        board2BlueStud.add(boardTopBlueStud5);
        board2BlueStud.add(boardTopBlueStud6);
        board2BlueStud.add(boardTopBlueStud7);
        board2BlueStud.add(boardTopBlueStud8);
        board2BlueStud.add(boardTopBlueStud9);
        board2BlueStud.add(boardTopBlueStud10);

        board2Towers = new ArrayList<>();
        board2Towers.add(boardTopTower1);
        board2Towers.add(boardTopTower2);
        board2Towers.add(boardTopTower3);
        board2Towers.add(boardTopTower4);
        board2Towers.add(boardTopTower5);
        board2Towers.add(boardTopTower6);
        board2Towers.add(boardTopTower7);
        board2Towers.add(boardTopTower8);

        // BOARD 4 ----------------------------------------------------------------------
        board3Entrance = new ArrayList<>();
        board3Entrance.add(boardMidEntrance1);
        board3Entrance.add(boardMidEntrance2);
        board3Entrance.add(boardMidEntrance3);
        board3Entrance.add(boardMidEntrance4);
        board3Entrance.add(boardMidEntrance5);
        board3Entrance.add(boardMidEntrance6);
        board3Entrance.add(boardMidEntrance7);
        board3Entrance.add(boardMidEntrance8);
        board3Entrance.add(boardMidEntrance9);

        board3GreenStud = new ArrayList<>();
        board3GreenStud.add(boardMidGreenStud1);
        board3GreenStud.add(boardMidGreenStud2);
        board3GreenStud.add(boardMidGreenStud3);
        board3GreenStud.add(boardMidGreenStud4);
        board3GreenStud.add(boardMidGreenStud5);
        board3GreenStud.add(boardMidGreenStud6);
        board3GreenStud.add(boardMidGreenStud7);
        board3GreenStud.add(boardMidGreenStud8);
        board3GreenStud.add(boardMidGreenStud9);
        board3GreenStud.add(boardMidGreenStud10);

        board3RedStud = new ArrayList<>();
        board3RedStud.add(boardMidRedStud1);
        board3RedStud.add(boardMidRedStud2);
        board3RedStud.add(boardMidRedStud3);
        board3RedStud.add(boardMidRedStud4);
        board3RedStud.add(boardMidRedStud5);
        board3RedStud.add(boardMidRedStud6);
        board3RedStud.add(boardMidRedStud7);
        board3RedStud.add(boardMidRedStud8);
        board3RedStud.add(boardMidRedStud9);
        board3RedStud.add(boardMidRedStud10);

        board3YellowStud = new ArrayList<>();
        board3YellowStud.add(boardMidYellowStud1);
        board3YellowStud.add(boardMidYellowStud2);
        board3YellowStud.add(boardMidYellowStud3);
        board3YellowStud.add(boardMidYellowStud4);
        board3YellowStud.add(boardMidYellowStud5);
        board3YellowStud.add(boardMidYellowStud6);
        board3YellowStud.add(boardMidYellowStud7);
        board3YellowStud.add(boardMidYellowStud8);
        board3YellowStud.add(boardMidYellowStud9);
        board3YellowStud.add(boardMidYellowStud10);

        board3PinkStud = new ArrayList<>();
        board3PinkStud.add(boardMidPinkStud1);
        board3PinkStud.add(boardMidPinkStud2);
        board3PinkStud.add(boardMidPinkStud3);
        board3PinkStud.add(boardMidPinkStud4);
        board3PinkStud.add(boardMidPinkStud5);
        board3PinkStud.add(boardMidPinkStud6);
        board3PinkStud.add(boardMidPinkStud7);
        board3PinkStud.add(boardMidPinkStud8);
        board3PinkStud.add(boardMidPinkStud9);
        board3PinkStud.add(boardMidPinkStud10);

        board3BlueStud = new ArrayList<>();
        board3BlueStud.add(boardMidBlueStud1);
        board3BlueStud.add(boardMidBlueStud2);
        board3BlueStud.add(boardMidBlueStud3);
        board3BlueStud.add(boardMidBlueStud4);
        board3BlueStud.add(boardMidBlueStud5);
        board3BlueStud.add(boardMidBlueStud6);
        board3BlueStud.add(boardMidBlueStud7);
        board3BlueStud.add(boardMidBlueStud8);
        board3BlueStud.add(boardMidBlueStud9);
        board3BlueStud.add(boardMidBlueStud10);

        board3Towers = new ArrayList<>();
        board3Towers.add(boardMidTower1);
        board3Towers.add(boardMidTower2);
        board3Towers.add(boardMidTower3);
        board3Towers.add(boardMidTower4);
        board3Towers.add(boardMidTower5);
        board3Towers.add(boardMidTower6);
        board3Towers.add(boardMidTower7);
        board3Towers.add(boardMidTower8);

        // BOARD 3 ----------------------------------------------------------------------
        board4Entrance = new ArrayList<>();
        board4Entrance.add(boardBotEntrance1);
        board4Entrance.add(boardBotEntrance2);
        board4Entrance.add(boardBotEntrance3);
        board4Entrance.add(boardBotEntrance4);
        board4Entrance.add(boardBotEntrance5);
        board4Entrance.add(boardBotEntrance6);
        board4Entrance.add(boardBotEntrance7);
        board4Entrance.add(boardBotEntrance8);
        board4Entrance.add(boardBotEntrance9);

        board4GreenStud = new ArrayList<>();
        board4GreenStud.add(boardBotGreenStud1);
        board4GreenStud.add(boardBotGreenStud2);
        board4GreenStud.add(boardBotGreenStud3);
        board4GreenStud.add(boardBotGreenStud4);
        board4GreenStud.add(boardBotGreenStud5);
        board4GreenStud.add(boardBotGreenStud6);
        board4GreenStud.add(boardBotGreenStud7);
        board4GreenStud.add(boardBotGreenStud8);
        board4GreenStud.add(boardBotGreenStud9);
        board4GreenStud.add(boardBotGreenStud10);

        board4RedStud = new ArrayList<>();
        board4RedStud.add(boardBotRedStud1);
        board4RedStud.add(boardBotRedStud2);
        board4RedStud.add(boardBotRedStud3);
        board4RedStud.add(boardBotRedStud4);
        board4RedStud.add(boardBotRedStud5);
        board4RedStud.add(boardBotRedStud6);
        board4RedStud.add(boardBotRedStud7);
        board4RedStud.add(boardBotRedStud8);
        board4RedStud.add(boardBotRedStud9);
        board4RedStud.add(boardBotRedStud10);

        board4YellowStud = new ArrayList<>();
        board4YellowStud.add(boardBotYellowStud1);
        board4YellowStud.add(boardBotYellowStud2);
        board4YellowStud.add(boardBotYellowStud3);
        board4YellowStud.add(boardBotYellowStud4);
        board4YellowStud.add(boardBotYellowStud5);
        board4YellowStud.add(boardBotYellowStud6);
        board4YellowStud.add(boardBotYellowStud7);
        board4YellowStud.add(boardBotYellowStud8);
        board4YellowStud.add(boardBotYellowStud9);
        board4YellowStud.add(boardBotYellowStud10);

        board4PinkStud = new ArrayList<>();
        board4PinkStud.add(boardBotPinkStud1);
        board4PinkStud.add(boardBotPinkStud2);
        board4PinkStud.add(boardBotPinkStud3);
        board4PinkStud.add(boardBotPinkStud4);
        board4PinkStud.add(boardBotPinkStud5);
        board4PinkStud.add(boardBotPinkStud6);
        board4PinkStud.add(boardBotPinkStud7);
        board4PinkStud.add(boardBotPinkStud8);
        board4PinkStud.add(boardBotPinkStud9);
        board4PinkStud.add(boardBotPinkStud10);

        board4BlueStud = new ArrayList<>();
        board4BlueStud.add(boardBotBlueStud1);
        board4BlueStud.add(boardBotBlueStud2);
        board4BlueStud.add(boardBotBlueStud3);
        board4BlueStud.add(boardBotBlueStud4);
        board4BlueStud.add(boardBotBlueStud5);
        board4BlueStud.add(boardBotBlueStud6);
        board4BlueStud.add(boardBotBlueStud7);
        board4BlueStud.add(boardBotBlueStud8);
        board4BlueStud.add(boardBotBlueStud9);
        board4BlueStud.add(boardBotBlueStud10);

        board4Towers = new ArrayList<>();
        board4Towers.add(boardBotTower1);
        board4Towers.add(boardBotTower2);
        board4Towers.add(boardBotTower3);
        board4Towers.add(boardBotTower4);
        board4Towers.add(boardBotTower5);
        board4Towers.add(boardBotTower6);
        board4Towers.add(boardBotTower7);
        board4Towers.add(boardBotTower8);

        // ISLANDS ----------------------------------------------------------------------

        islands = new ArrayList<>();
        islands.add(island1);
        islands.add(island2);
        islands.add(island3);
        islands.add(island4);
        islands.add(island5);
        islands.add(island6);
        islands.add(island7);
        islands.add(island8);
        islands.add(island9);
        islands.add(island10);
        islands.add(island11);
        islands.add(island12);

        islandsGreenStud = new ArrayList<>();
        islandsGreenStud.add(island1GreenStud);
        islandsGreenStud.add(island2GreenStud);
        islandsGreenStud.add(island3GreenStud);
        islandsGreenStud.add(island4GreenStud);
        islandsGreenStud.add(island5GreenStud);
        islandsGreenStud.add(island6GreenStud);
        islandsGreenStud.add(island7GreenStud);
        islandsGreenStud.add(island8GreenStud);
        islandsGreenStud.add(island9GreenStud);
        islandsGreenStud.add(island10GreenStud);
        islandsGreenStud.add(island11GreenStud);
        islandsGreenStud.add(island12GreenStud);

        islandsRedStud = new ArrayList<>();
        islandsRedStud.add(island1RedStud);
        islandsRedStud.add(island2RedStud);
        islandsRedStud.add(island3RedStud);
        islandsRedStud.add(island4RedStud);
        islandsRedStud.add(island5RedStud);
        islandsRedStud.add(island6RedStud);
        islandsRedStud.add(island7RedStud);
        islandsRedStud.add(island8RedStud);
        islandsRedStud.add(island9RedStud);
        islandsRedStud.add(island10RedStud);
        islandsRedStud.add(island11RedStud);
        islandsRedStud.add(island12RedStud);

        islandsYellowStud = new ArrayList<>();
        islandsYellowStud.add(island1YellowStud);
        islandsYellowStud.add(island2YellowStud);
        islandsYellowStud.add(island3YellowStud);
        islandsYellowStud.add(island4YellowStud);
        islandsYellowStud.add(island5YellowStud);
        islandsYellowStud.add(island6YellowStud);
        islandsYellowStud.add(island7YellowStud);
        islandsYellowStud.add(island8YellowStud);
        islandsYellowStud.add(island9YellowStud);
        islandsYellowStud.add(island10YellowStud);
        islandsYellowStud.add(island11YellowStud);
        islandsYellowStud.add(island12YellowStud);

        islandsPinkStud = new ArrayList<>();
        islandsPinkStud.add(island1PinkStud);
        islandsPinkStud.add(island2PinkStud);
        islandsPinkStud.add(island3PinkStud);
        islandsPinkStud.add(island4PinkStud);
        islandsPinkStud.add(island5PinkStud);
        islandsPinkStud.add(island6PinkStud);
        islandsPinkStud.add(island7PinkStud);
        islandsPinkStud.add(island8PinkStud);
        islandsPinkStud.add(island9PinkStud);
        islandsPinkStud.add(island10PinkStud);
        islandsPinkStud.add(island11PinkStud);
        islandsPinkStud.add(island12PinkStud);

        islandsBlueStud = new ArrayList<>();
        islandsBlueStud.add(island1BlueStud);
        islandsBlueStud.add(island2BlueStud);
        islandsBlueStud.add(island3BlueStud);
        islandsBlueStud.add(island4BlueStud);
        islandsBlueStud.add(island5BlueStud);
        islandsBlueStud.add(island6BlueStud);
        islandsBlueStud.add(island7BlueStud);
        islandsBlueStud.add(island8BlueStud);
        islandsBlueStud.add(island9BlueStud);
        islandsBlueStud.add(island10BlueStud);
        islandsBlueStud.add(island11BlueStud);
        islandsBlueStud.add(island12BlueStud);

        islandsTower = new ArrayList<>();
        islandsTower.add(island1Tower);
        islandsTower.add(island2Tower);
        islandsTower.add(island3Tower);
        islandsTower.add(island4Tower);
        islandsTower.add(island5Tower);
        islandsTower.add(island6Tower);
        islandsTower.add(island7Tower);
        islandsTower.add(island8Tower);
        islandsTower.add(island9Tower);
        islandsTower.add(island10Tower);
        islandsTower.add(island11Tower);
        islandsTower.add(island12Tower);

        islandsMN = new ArrayList<>();
        islandsMN.add(island1MN);
        islandsMN.add(island2MN);
        islandsMN.add(island3MN);
        islandsMN.add(island4MN);
        islandsMN.add(island5MN);
        islandsMN.add(island6MN);
        islandsMN.add(island7MN);
        islandsMN.add(island8MN);
        islandsMN.add(island9MN);
        islandsMN.add(island10MN);
        islandsMN.add(island11MN);
        islandsMN.add(island12MN);

        islandsGreenTot = new ArrayList<>();
        islandsGreenTot.add(island1GreenTot);
        islandsGreenTot.add(island2GreenTot);
        islandsGreenTot.add(island3GreenTot);
        islandsGreenTot.add(island4GreenTot);
        islandsGreenTot.add(island5GreenTot);
        islandsGreenTot.add(island6GreenTot);
        islandsGreenTot.add(island7GreenTot);
        islandsGreenTot.add(island8GreenTot);
        islandsGreenTot.add(island9GreenTot);
        islandsGreenTot.add(island10GreenTot);
        islandsGreenTot.add(island11GreenTot);
        islandsGreenTot.add(island12GreenTot);

        islandsRedTot = new ArrayList<>();
        islandsRedTot.add(island1RedTot);
        islandsRedTot.add(island2RedTot);
        islandsRedTot.add(island3RedTot);
        islandsRedTot.add(island4RedTot);
        islandsRedTot.add(island5RedTot);
        islandsRedTot.add(island6RedTot);
        islandsRedTot.add(island7RedTot);
        islandsRedTot.add(island8RedTot);
        islandsRedTot.add(island9RedTot);
        islandsRedTot.add(island10RedTot);
        islandsRedTot.add(island11RedTot);
        islandsRedTot.add(island12RedTot);

        islandsYellowTot = new ArrayList<>();
        islandsYellowTot.add(island1YellowTot);
        islandsYellowTot.add(island2YellowTot);
        islandsYellowTot.add(island3YellowTot);
        islandsYellowTot.add(island4YellowTot);
        islandsYellowTot.add(island5YellowTot);
        islandsYellowTot.add(island6YellowTot);
        islandsYellowTot.add(island7YellowTot);
        islandsYellowTot.add(island8YellowTot);
        islandsYellowTot.add(island9YellowTot);
        islandsYellowTot.add(island10YellowTot);
        islandsYellowTot.add(island11YellowTot);
        islandsYellowTot.add(island12YellowTot);

        islandsPinkTot = new ArrayList<>();
        islandsPinkTot.add(island1PinkTot);
        islandsPinkTot.add(island2PinkTot);
        islandsPinkTot.add(island3PinkTot);
        islandsPinkTot.add(island4PinkTot);
        islandsPinkTot.add(island5PinkTot);
        islandsPinkTot.add(island6PinkTot);
        islandsPinkTot.add(island7PinkTot);
        islandsPinkTot.add(island8PinkTot);
        islandsPinkTot.add(island9PinkTot);
        islandsPinkTot.add(island10PinkTot);
        islandsPinkTot.add(island11PinkTot);
        islandsPinkTot.add(island12PinkTot);

        islandsBlueTot = new ArrayList<>();
        islandsBlueTot.add(island1BlueTot);
        islandsBlueTot.add(island2BlueTot);
        islandsBlueTot.add(island3BlueTot);
        islandsBlueTot.add(island4BlueTot);
        islandsBlueTot.add(island5BlueTot);
        islandsBlueTot.add(island6BlueTot);
        islandsBlueTot.add(island7BlueTot);
        islandsBlueTot.add(island8BlueTot);
        islandsBlueTot.add(island9BlueTot);
        islandsBlueTot.add(island10BlueTot);
        islandsBlueTot.add(island11BlueTot);
        islandsBlueTot.add(island12BlueTot);

        boardsNickBox = new ArrayList<>();
        boardsNickBox.add(boardTopNickBox);
        boardsNickBox.add(boardMidNickBox);
        boardsNickBox.add(boardBotNickBox);

        boardsNick = new ArrayList<>();
        boardsNick.add(boardTopNick);
        boardsNick.add(boardMidNick);
        boardsNick.add(boardBotNick);

        noEntry = new ArrayList<>();
        noEntry.add(island1NoEntry);
        noEntry.add(island2NoEntry);
        noEntry.add(island3NoEntry);
        noEntry.add(island4NoEntry);
        noEntry.add(island5NoEntry);
        noEntry.add(island6NoEntry);
        noEntry.add(island7NoEntry);
        noEntry.add(island8NoEntry);
        noEntry.add(island9NoEntry);
        noEntry.add(island10NoEntry);
        noEntry.add(island11NoEntry);
        noEntry.add(island12NoEntry);

        noEntryTot = new ArrayList<>();
        noEntryTot.add(island1NETot);
        noEntryTot.add(island2NETot);
        noEntryTot.add(island3NETot);
        noEntryTot.add(island4NETot);
        noEntryTot.add(island5NETot);
        noEntryTot.add(island6NETot);
        noEntryTot.add(island7NETot);
        noEntryTot.add(island8NETot);
        noEntryTot.add(island9NETot);
        noEntryTot.add(island10NETot);
        noEntryTot.add(island11NETot);
        noEntryTot.add(island12NETot);

        // ASSISTANTS ----------------------------------------------------------------------

        assistantsAvailable = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            assistantsAvailable.add(true);

        assistantsUp = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            assistantsUp.add(false);

        assistants = new ArrayList<>();
        assistants.add(assistant1);
        assistants.add(assistant2);
        assistants.add(assistant3);
        assistants.add(assistant4);
        assistants.add(assistant5);
        assistants.add(assistant6);
        assistants.add(assistant7);
        assistants.add(assistant8);
        assistants.add(assistant9);
        assistants.add(assistant10);

        // MOTHER NATURE ----------------------------------------------------------------------

        mnSteps = new ArrayList<>();
        mnSteps.add(mn_mov_1);
        mnSteps.add(mn_mov_2);
        mnSteps.add(mn_mov_3);
        mnSteps.add(mn_mov_4);
        mnSteps.add(mn_mov_5);

        // CLOUDS ----------------------------------------------------------------------

        ArrayList<ImageView> c1 = new ArrayList();
        c1.add(cloud1_1);
        c1.add(cloud1_2);
        c1.add(cloud1_3);
        c1.add(cloud1_4);
        ArrayList<ImageView> c2 = new ArrayList();
        c2.add(cloud2_1);
        c2.add(cloud2_2);
        c2.add(cloud2_3);
        c2.add(cloud2_4);
        ArrayList<ImageView> c3 = new ArrayList();
        c3.add(cloud3_1);
        c3.add(cloud3_2);
        c3.add(cloud3_3);
        c3.add(cloud3_4);
        ArrayList<ImageView> c4 = new ArrayList();
        c4.add(cloud4_1);
        c4.add(cloud4_2);
        c4.add(cloud4_3);
        cloudPawns = new ArrayList<>();
        cloudPawns.add(c1);
        cloudPawns.add(c2);
        cloudPawns.add(c3);
        cloudPawns.add(c4);

        cloudImages = new ArrayList<>();
        cloudImages.add(cloud1);
        cloudImages.add(cloud2);
        cloudImages.add(cloud3);
        cloudImages.add(cloud4);

        // CHARACTERS ----------------------------------------------------------------------

        character1Elems = new ArrayList<>();
        character1Elems.add(character1Elem1);
        character1Elems.add(character1Elem2);
        character1Elems.add(character1Elem3);
        character1Elems.add(character1Elem4);
        character1Elems.add(character1Elem5);
        character1Elems.add(character1Elem6);

        character2Elems = new ArrayList<>();
        character2Elems.add(character2Elem1);
        character2Elems.add(character2Elem2);
        character2Elems.add(character2Elem3);
        character2Elems.add(character2Elem4);
        character2Elems.add(character2Elem5);
        character2Elems.add(character2Elem6);

        character3Elems = new ArrayList<>();
        character3Elems.add(character3Elem1);
        character3Elems.add(character3Elem2);
        character3Elems.add(character3Elem3);
        character3Elems.add(character3Elem4);
        character3Elems.add(character3Elem5);
        character3Elems.add(character3Elem6);
    }

    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This method is used to manage the initial set up of the scene
     * @param players it's an ArrayList that contains all the players
     * @param teams it's an array that contains the teams (in the 4 player case)
     * @param clouds it's an ArrayList that contains the clouds
     * @param archipelagos it's an ArrayList that contains the archipelagos
     * @param characters it's an ArrayList that contains the characters (in the expert mode)
     * @param expertMode it's true if the expert mode is selected, false if it's not
     * @param nickname it's the nickname of the player of this Client
     */
    public void setGame(ArrayList<Player> players, Team[] teams, ArrayList<Cloud> clouds, ArrayList<Archipelago> archipelagos, Character[] characters, boolean expertMode, String nickname) {
        this.players = players;
        this.teams = teams;
        this.clouds = clouds;
        this.archipelagos = archipelagos;
        this.characters = characters;
        this.expertMode = expertMode;
        this.nickname = nickname;
        usedCharacter = false;

        this.phase = Status.STANDBY;

        createArrayLists();

        setBoards(players.size());

        setNicks();

        setArchipelagos();

        resetAssistantsTab();

        tabPane.getTabs().remove(motherNatureTab);

        if (!expertMode) {
            tabPane.getTabs().remove(charactersTab);
        } else {
            setCharactersId(characters[0].getId(), characters[1].getId(), characters[2].getId());
            setCharactersImage();
            setCharacterPawns(char1Id, 0);
            setCharacterPawns(char2Id, 1);
            setCharacterPawns(char3Id, 2);
        }

        tabPane.getTabs().remove(character1EffectTab1);
        tabPane.getTabs().remove(character3_5EffectTab);
        tabPane.getTabs().remove(character4EffectTab);
        tabPane.getTabs().remove(character9_12EffectTab);
        tabPane.getTabs().remove(character11EffectTab);
        tabPane.getTabs().remove(character7EffectTab);
        tabPane.getTabs().remove(character10EffectTab);

        resetCloudTab();
        setClouds();
    }
}

package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.Server;
import it.polimi.ingsw.server.controller.View;
import it.polimi.ingsw.server.controller.VirtualView;
import it.polimi.ingsw.server.model.characters.cards.*;
import it.polimi.ingsw.server.model.characters.cards.Character;
import it.polimi.ingsw.server.model.characters.parameters.IntegerParameter;
import it.polimi.ingsw.server.model.characters.parameters.Parameter;
import it.polimi.ingsw.server.model.characters.parameters.PlayerParameter;
import it.polimi.ingsw.server.model.characters.parameters.StudentParameter;
import it.polimi.ingsw.server.serverexceptions.EndGameException;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void startGame() {
        // An ArrayList of View (containing a single view) is created in order to check if View methods are correctly called by Game methods
        new Server(12345);
        Executors.newCachedThreadPool();
        ServerSocket serverSocket = null;
        Socket socketC;
        Socket socketS = null;
        ObjectInputStream inS = null;
        ObjectOutputStream outS = null;
        ArrayList<View> v = new ArrayList<>();
        boolean quit = false;
        while (!quit) {
            try {
                serverSocket = new ServerSocket(12345);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socketC = new Socket("127.0.0.1", 12345);
                new ObjectOutputStream(socketC.getOutputStream());
                socketS = serverSocket.accept();
                inS = new ObjectInputStream(socketS.getInputStream());
                outS = new ObjectOutputStream(socketS.getOutputStream());
                new ObjectInputStream(socketC.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            v.add(new VirtualView(socketS, inS, outS));
            quit = true;
        }

        Game g = new Game();
        // checks if the attributes in Game are correctly initialized
        assertEquals(g.getPlayers().size(), 0);
        assertEquals(g.getArchipelagos().size(), 0);
        assertEquals(g.getClouds().size(), 0);
        assertEquals(g.getNumberPlayers(), -1);

        g.setNumberPlayers(4);
        g.setExpertMode(true);
        g.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g.joinGame("p3", Tower.BLACK, Wizard.WIZARD3);
        g.joinGame("p4", Tower.WHITE, Wizard.WIZARD4);
        g.startGame(v);

        // Setup islands
        assertEquals(g.numberArchipelagos(), 12); // checks if the setup correctly instantiates 12 Archipelagos
        int mn = 0, emptyA = 0;
        for (Archipelago a : g.getArchipelagos()) {
            assertEquals(a.getIslands().size(), 1); // checks if each archipelago contains just one island after the setup
            if (a.checkMotherNature())
                mn++;
            if (a.getIslands().get(0).getStudents().size() == 0)
                emptyA++;
        }
        assertEquals(mn, 1); // checks if Mother Nature is positioned on just one Archipelago
        assertEquals(emptyA, 2); // checks if the number of empty Archipelagos after the setup is equal to 2

        // Setup clouds
        assertEquals(g.getClouds().size(), 4); // checks if the number of clouds corresponds to the number of players
        for (Cloud c : g.getClouds())
            assertEquals(c.getStudents().size(), 3); // checks if the number of players of the game correctly influences the amount of students on each cloud

        // Setup Characters
        for (int i = 0; i < 3; i++) {
            assertNotEquals(g.getCharacters()[i], null); // checks if the choice of the expert mode influences the setup of the Characters
        }
        for (int i = 0; i < 3; i++) {
            assertNotEquals(g.getCharacters()[i % 3].getId(), g.getCharacters()[(i + 1) % 3].getId()); // checks if each Character is different from the others
        }

        // Setup Teams
        assertNotEquals(g.getTeams()[0].getTower(), g.getTeams()[1].getTower()); // checks if the two teams are different
        // checks if the Teams have different players
        assertNotEquals(g.getTeams()[0].getPlayers()[0].getNickname(), g.getTeams()[0].getPlayers()[1].getNickname());
        assertNotEquals(g.getTeams()[1].getPlayers()[0].getNickname(), g.getTeams()[1].getPlayers()[1].getNickname());
        // checks if the two players of the team have the same Tower color
        assertEquals(g.getTeams()[0].getPlayers()[0].getBoard().getTowerColor(), g.getTeams()[0].getPlayers()[1].getBoard().getTowerColor());
        assertEquals(g.getTeams()[1].getPlayers()[0].getBoard().getTowerColor(), g.getTeams()[1].getPlayers()[1].getBoard().getTowerColor());

        // Setup Boards
        for (Player p : g.getPlayers()) {
            for (int i = 0; i < 7; i++)
                assertNotEquals(p.getBoard().getEntrance()[i], null); // checks if the number of players of the game correctly influences the amount of students on each entrance
        }

        Game g2 = new Game();
        g2.setNumberPlayers(4);
        g2.setExpertMode(false);
        g2.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g2.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g2.joinGame("p3", Tower.BLACK, Wizard.WIZARD3);
        g2.joinGame("p4", Tower.WHITE, Wizard.WIZARD4);
        g2.startGame(v);
        assertNull(g2.getCharacters()); // checks if the choice of the 'no expert mode' influences the setup of the Characters

        Game g3 = new Game();
        g3.setNumberPlayers(3);
        g3.setExpertMode(true);
        g3.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g3.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g3.joinGame("p3", Tower.BLACK, Wizard.WIZARD3);
        g3.startGame(v);
        for (Cloud c : g3.getClouds())
            assertEquals(c.getStudents().size(), 4); // checks if the number of players of the game correctly influences the amount of students on each cloud

        Game g4 = new Game();
        g4.setNumberPlayers(3);
        g4.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g4.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g4.joinGame("p3", Tower.BLACK, Wizard.WIZARD3);
        g4.setExpertMode(false);
        g4.startGame(v);

        for (Player p : g4.getPlayers()) {
            for (int i = 0; i < 9; i++)
                assertNotEquals(p.getBoard().getEntrance()[i], null); // checks if the number of players of the game correctly influences the amount of students on each entrance
        }
        assertEquals(g4.getPlayer("p1").getNickname(), "p1"); // checks if the returned nickname is equal to the joinGame() String parameter

        // Checks if every character can be played

        Game g5;
        int okChars = 0;
        boolean okChar1 = false;
        while (!okChar1) {
            g5 = new Game();
            g5.setNumberPlayers(2);
            g5.setExpertMode(true);
            g5.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
            g5.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
            g5.joinGame("p3", Tower.GREY, Wizard.WIZARD3);
            g5.startGame(v);
            for (Character c : g5.getCharacters()) {
                if (c.getId() == 1) {
                    okChar1 = true;
                    break;
                }
            }
            if (okChar1)
                okChars++;
        }

        boolean okChar2 = false;
        while (!okChar2) {
            g5 = new Game();
            g5.setNumberPlayers(2);
            g5.setExpertMode(true);
            g5.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
            g5.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
            g5.joinGame("p3", Tower.GREY, Wizard.WIZARD3);
            g5.startGame(v);
            for (Character c : g5.getCharacters()) {
                if (c.getId() == 2) {
                    okChar2 = true;
                    break;
                }
            }
            if (okChar2)
                okChars++;
        }

        boolean okChar3 = false;
        while (!okChar3) {
            g5 = new Game();
            g5.setNumberPlayers(2);
            g5.setExpertMode(true);
            g5.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
            g5.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
            g5.joinGame("p3", Tower.GREY, Wizard.WIZARD3);
            g5.startGame(v);
            for (Character c : g5.getCharacters()) {
                if (c.getId() == 3) {
                    okChar3 = true;
                    break;
                }
            }
            if (okChar3)
                okChars++;
        }

        boolean okChar4 = false;
        while (!okChar4) {
            g5 = new Game();
            g5.setNumberPlayers(2);
            g5.setExpertMode(true);
            g5.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
            g5.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
            g5.joinGame("p3", Tower.GREY, Wizard.WIZARD3);
            g5.startGame(v);
            for (Character c : g5.getCharacters()) {
                if (c.getId() == 4) {
                    okChar4 = true;
                    break;
                }
            }
            if (okChar4)
                okChars++;
        }

        boolean okChar5 = false;
        while (!okChar5) {
            g5 = new Game();
            g5.setNumberPlayers(2);
            g5.setExpertMode(true);
            g5.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
            g5.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
            g5.joinGame("p3", Tower.GREY, Wizard.WIZARD3);
            g5.startGame(v);
            for (Character c : g5.getCharacters()) {
                if (c.getId() == 5) {
                    okChar5 = true;
                    break;
                }
            }
            if (okChar5)
                okChars++;
        }

        boolean okChar6 = false;
        while (!okChar6) {
            g5 = new Game();
            g5.setNumberPlayers(2);
            g5.setExpertMode(true);
            g5.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
            g5.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
            g5.joinGame("p3", Tower.GREY, Wizard.WIZARD3);
            g5.startGame(v);
            for (Character c : g5.getCharacters()) {
                if (c.getId() == 6) {
                    okChar6 = true;
                    break;
                }
            }
            if (okChar6)
                okChars++;
        }

        boolean okChar7 = false;
        while (!okChar7) {
            g5 = new Game();
            g5.setNumberPlayers(2);
            g5.setExpertMode(true);
            g5.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
            g5.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
            g5.joinGame("p3", Tower.GREY, Wizard.WIZARD3);
            g5.startGame(v);
            for (Character c : g5.getCharacters()) {
                if (c.getId() == 7) {
                    okChar7 = true;
                    break;
                }
            }
            if (okChar7)
                okChars++;
        }

        boolean okChar8 = false;
        while (!okChar8) {
            g5 = new Game();
            g5.setNumberPlayers(2);
            g5.setExpertMode(true);
            g5.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
            g5.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
            g5.joinGame("p3", Tower.GREY, Wizard.WIZARD3);
            g5.startGame(v);
            for (Character c : g5.getCharacters()) {
                if (c.getId() == 8) {
                    okChar8 = true;
                    break;
                }
            }
            if (okChar8)
                okChars++;
        }

        boolean okChar9 = false;
        while (!okChar9) {
            g5 = new Game();
            g5.setNumberPlayers(2);
            g5.setExpertMode(true);
            g5.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
            g5.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
            g5.joinGame("p3", Tower.GREY, Wizard.WIZARD3);
            g5.startGame(v);
            for (Character c : g5.getCharacters()) {
                if (c.getId() == 9) {
                    okChar9 = true;
                    break;
                }
            }
            if (okChar9)
                okChars++;
        }

        boolean okChar10 = false;
        while (!okChar10) {
            g5 = new Game();
            g5.setNumberPlayers(2);
            g5.setExpertMode(true);
            g5.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
            g5.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
            g5.joinGame("p3", Tower.GREY, Wizard.WIZARD3);
            g5.startGame(v);
            for (Character c : g5.getCharacters()) {
                if (c.getId() == 10) {
                    okChar10 = true;
                    break;
                }
            }
            if (okChar10)
                okChars++;
        }

        boolean okChar11 = false;
        while (!okChar11) {
            g5 = new Game();
            g5.setNumberPlayers(2);
            g5.setExpertMode(true);
            g5.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
            g5.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
            g5.joinGame("p3", Tower.GREY, Wizard.WIZARD3);
            g5.startGame(v);
            for (Character c : g5.getCharacters()) {
                if (c.getId() == 11) {
                    okChar11 = true;
                    break;
                }
            }
            if (okChar11)
                okChars++;
        }

        boolean okChar12 = false;
        while (!okChar12) {
            g5 = new Game();
            g5.setNumberPlayers(2);
            g5.setExpertMode(true);
            g5.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
            g5.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
            g5.joinGame("p3", Tower.GREY, Wizard.WIZARD3);
            g5.startGame(v);
            for (Character c : g5.getCharacters()) {
                if (c.getId() == 12) {
                    okChar12 = true;
                    break;
                }
            }
            if (okChar12)
                okChars++;
        }
        assertEquals(okChars, 12); // checks if the number of possible different characters is correct
    }

    @Test
    void checkArchipelagosStructure() {
        ArrayList<View> v = new ArrayList<>();
        Game g = new Game();
        g.setNumberPlayers(4);
        g.setExpertMode(true);
        g.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g.joinGame("p3", Tower.BLACK, Wizard.WIZARD3);
        g.joinGame("p4", Tower.WHITE, Wizard.WIZARD4);
        g.startGame(v);

        assertEquals(g.getArchipelagos().size(), 12); // checks if the total number of archipelagos is correct when the game starts
        assertDoesNotThrow(() -> g.checkArchipelagosStructure()); // checks if the EndGame Exception is not thrown when the total number of archipelagos is > 3
        for (int i = 0; i < g.getArchipelagos().size(); i++) { // Mother Nature won't be on any Archipelago at the end of the for loop
            if (g.getArchipelagos().get(i).checkMotherNature())
                g.getArchipelagos().get(i).updateMotherNature();
        }
        g.getArchipelagos().get(0).setTower(Tower.BLACK);
        g.getArchipelagos().get(1).setTower(Tower.BLACK);
        g.getArchipelagos().get(1).addNoEntry(1); // one No Entry card is placed on the second Archipelago
        assertEquals(g.getArchipelagos().get(0).checkNoEntry(), 0); // checks if there are 0 No Entry Cards on the first archipelago before calling the checkArchipelagosStructure method
        assertDoesNotThrow(() -> g.checkArchipelagosStructure()); // checks if the EndGame Exception is still not thrown when the total number of archipelagos is > 3
        assertEquals(g.getArchipelagos().size(), 11); // checks if two archipelagos have been connected after calling the checkArchipelagosStructure method
        assertEquals(g.getArchipelagos().get(0).checkNoEntry(), 1); // checks if the number of No Entry cards has been updated when the second archipelago has been connected to the first

        Game g1 = new Game();
        g1.setNumberPlayers(4);
        g1.setExpertMode(true);
        g1.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g1.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g1.joinGame("p3", Tower.BLACK, Wizard.WIZARD3);
        g1.joinGame("p4", Tower.WHITE, Wizard.WIZARD4);
        g1.startGame(v);
        for (Archipelago a : g1.getArchipelagos()) { // Mother Nature won't be on any Archipelago at the end of the for loop
            if (a.checkMotherNature())
                a.updateMotherNature();
        }
        g1.getArchipelagos().get(0).setTower(Tower.BLACK);
        g1.getArchipelagos().get(1).setTower(Tower.BLACK);
        g1.getArchipelagos().get(1).updateMotherNature(); // Mother Nature is placed on the second Archipelago
        assertDoesNotThrow(() -> g1.checkArchipelagosStructure()); // checks if the EndGame Exception is still not thrown when the total number of archipelagos is > 3
        assertTrue(g1.getArchipelagos().get(0).checkMotherNature()); // checks if Mother Nature has been replaced on the first Archipelago when the second was connected to the first

        assertFalse(g1.getArchipelagos().get(g1.getArchipelagos().size() - 1).checkMotherNature()); // checks if Mother Nature is not on the last Archipelago
        g1.getArchipelagos().get(g1.getArchipelagos().size() - 1).setTower(Tower.WHITE);
        g1.getArchipelagos().get(0).setTower(Tower.WHITE);
        assertDoesNotThrow(() -> g1.checkArchipelagosStructure()); // checks if the EndGame Exception is still not thrown when the total number of archipelagos is > 3
        assertTrue(g1.getArchipelagos().get(g1.getArchipelagos().size() - 1).checkMotherNature()); // checks if Mother Nature has been replaced on the last Archipelago when the first was connected to the last

        Game g2 = new Game();
        g2.setNumberPlayers(4);
        g2.setExpertMode(true);
        g2.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g2.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g2.joinGame("p3", Tower.BLACK, Wizard.WIZARD3);
        g2.joinGame("p4", Tower.WHITE, Wizard.WIZARD4);
        g2.startGame(v);
        for (int i = 0; i < 10; i++) // the first ten archipelagos have the GREY Tower on them, while the last two do not have a Tower
            g2.getArchipelagos().get(i).setTower(Tower.GREY);
        assertThrowsExactly(EndGameException.class, () -> g2.checkArchipelagosStructure()); // checks if the Archipelagos with the same Tower have been connected and an EndGame Exception is thrown when the total number of archipelagos is equal to 3
    }

    @Test
    void getNumberPlayers() {
        Game g = new Game();
        g.setNumberPlayers(4);
        assertEquals(g.getNumberPlayers(), 4); // checks if the returned value is equal to the value previously set
        g.setNumberPlayers(2);
        assertEquals(g.getNumberPlayers(), 2); // checks if the returned value is equal to the value previously set
    }

    @Test
    void checkTeachers() {
        // An ArrayList of View (containing a single view) is created in order to check if View method 'startGame' is correctly called by Game method 'startGame'
        new Server(12346);
        Executors.newCachedThreadPool();
        ServerSocket serverSocket = null;
        Socket socketC;
        Socket socketS = null;
        ObjectInputStream inS = null;
        ObjectOutputStream outS = null;
        ArrayList<View> v = new ArrayList<>();
        boolean quit = false;
        while (!quit) {
            try {
                serverSocket = new ServerSocket(12346);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socketC = new Socket("127.0.0.1", 12346);
                new ObjectOutputStream(socketC.getOutputStream());
                socketS = serverSocket.accept();
                inS = new ObjectInputStream(socketS.getInputStream());
                outS = new ObjectOutputStream(socketS.getOutputStream());
                new ObjectInputStream(socketC.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            v.add(new VirtualView(socketS, inS, outS));
            quit = true;
        }

        Game g = new Game();
        assertEquals(g.getPlayers().size(), 0);
        assertEquals(g.getArchipelagos().size(), 0);
        assertEquals(g.getClouds().size(), 0);
        assertEquals(g.getNumberPlayers(), -1);
        g.setNumberPlayers(2);
        g.setExpertMode(false);
        g.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g.startGame(v);

        for (int i = 0; i < g.getPlayers().get(0).getBoard().getEntrance().length; i++)
            g.getPlayers().get(0).getBoard().getEntrance()[i] = null;
        for (int i = 0; i < g.getPlayers().get(1).getBoard().getEntrance().length; i++)
            g.getPlayers().get(1).getBoard().getEntrance()[i] = null;

        g.getPlayers().get(0).getBoard().moveToEntrance(Student.RED);
        g.getPlayers().get(0).getBoard().moveToEntrance(Student.YELLOW);
        g.getPlayers().get(0).getBoard().moveStudentToHall(0);
        g.getPlayers().get(0).getBoard().moveStudentToHall(1);

        g.getPlayers().get(1).getBoard().moveToEntrance(Student.GREEN);
        g.getPlayers().get(1).getBoard().moveStudentToHall(0);

        assertFalse(g.getPlayers().get(0).getBoard().isProfessorPresent(Student.RED));
        assertFalse(g.getPlayers().get(0).getBoard().isProfessorPresent(Student.YELLOW));
        assertFalse(g.getPlayers().get(1).getBoard().isProfessorPresent(Student.GREEN));
        g.checkTeachers();
        assertTrue(g.getPlayers().get(0).getBoard().isProfessorPresent(Student.RED));
        assertTrue(g.getPlayers().get(0).getBoard().isProfessorPresent(Student.YELLOW));
        assertTrue(g.getPlayers().get(1).getBoard().isProfessorPresent(Student.GREEN));

        g.getPlayers().get(0).getBoard().moveToEntrance(Student.GREEN);
        g.getPlayers().get(0).getBoard().moveToEntrance(Student.GREEN);
        g.getPlayers().get(0).getBoard().moveStudentToHall(0);
        g.getPlayers().get(0).getBoard().moveStudentToHall(1);
        assertFalse(g.getPlayers().get(0).getBoard().isProfessorPresent(Student.GREEN));
        assertTrue(g.getPlayers().get(1).getBoard().isProfessorPresent(Student.GREEN));
        g.checkTeachers();
        assertTrue(g.getPlayers().get(0).getBoard().isProfessorPresent(Student.GREEN));
        assertFalse(g.getPlayers().get(1).getBoard().isProfessorPresent(Student.GREEN));

    }

    @Test
    void useCharacter() {
        // An ArrayList of View (containing a single view) is created in order to check if View method 'characterUpdate' is correctly called by Game method 'useCharacter'
        new Server(12347);
        Executors.newCachedThreadPool();
        ServerSocket serverSocket = null;
        Socket socketC;
        Socket socketS = null;
        ObjectInputStream inS = null;
        ObjectOutputStream outS = null;
        ArrayList<View> v = new ArrayList<>();
        boolean quit = false;
        while (!quit) {
            try {
                serverSocket = new ServerSocket(12347);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socketC = new Socket("127.0.0.1", 12347);
                new ObjectOutputStream(socketC.getOutputStream());
                socketS = serverSocket.accept();
                inS = new ObjectInputStream(socketS.getInputStream());
                outS = new ObjectOutputStream(socketS.getOutputStream());
                new ObjectInputStream(socketC.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            v.add(new VirtualView(socketS, inS, outS));
            quit = true;
        }

        Game g = new Game();
        g.setNumberPlayers(2);
        g.setExpertMode(true);
        g.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g.startGame(v);

        // checks if the Herald effect is activated as soon as the Character card is used
        for (int i = 0; i < 3; i++)
            g.getCharacters()[i] = null;
        g.getCharacters()[0] = new Herald(); // the first Character of the game is a Herald
        ArrayList<Parameter> p = new ArrayList<>();
        p.add(new IntegerParameter("index", 0));
        g.getArchipelagos().get(0).getIslands().get(0).addStudent(Student.BLUE);
        g.getPlayers().get(0).getBoard().getEntrance()[0] = Student.BLUE;
        g.getPlayers().get(0).getBoard().moveStudentToHall(0);
        g.checkTeachers();
        assertNull(g.getArchipelagos().get(0).getTower()); // checks if there are no towers on the first archipelago
        assertDoesNotThrow(() -> g.useCharacter(0, p)); // checks if the EndGame Exception is not thrown when the Herald is used and the total number of archipelagos is still > 3
        assertEquals(g.getArchipelagos().get(0).getTower(), g.getPlayers().get(0).getBoard().getTowerColor()); // checks if the influence is calculated when the Herald card is used
    }

    @Test
    void chooseCloud() {
        // An ArrayList of View (containing a single view) is created in order to check if View methods 'cloudUpdate' and 'playersUpdate' are correctly called by Game method 'chooseCloud'
        new Server(1234);
        Executors.newCachedThreadPool();
        ServerSocket serverSocket = null;
        Socket socketC;
        Socket socketS = null;
        ObjectInputStream inS = null;
        ObjectOutputStream outS = null;
        ArrayList<View> v = new ArrayList<>();
        boolean quit = false;
        while (!quit) {
            try {
                serverSocket = new ServerSocket(1234);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socketC = new Socket("127.0.0.1", 1234);
                new ObjectOutputStream(socketC.getOutputStream());
                socketS = serverSocket.accept();
                inS = new ObjectInputStream(socketS.getInputStream());
                outS = new ObjectOutputStream(socketS.getOutputStream());
                new ObjectInputStream(socketC.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            v.add(new VirtualView(socketS, inS, outS));
            quit = true;
        }

        Game g = new Game();
        g.setNumberPlayers(3);
        g.setExpertMode(true);
        g.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g.joinGame("p3", Tower.GREY, Wizard.WIZARD3);
        g.setExpertMode(false);
        g.startGame(v);
        for (Student s : g.getPlayer("p1").getBoard().getEntrance()) // p1's entrance is emptied
            s = null;
        ArrayList<Student> s = g.getClouds().get(0).getStudents(); // 's' contains the students of the first Cloud
        g.chooseCloud("p1", 0);
        for (int i = 0; i < s.size(); i++) {
            assertEquals(s.get(i), g.getPlayer("p1").getBoard().getEntrance()[i]); // checks if all the students of the chosen Cloud have been moved to the first player's entrance
        }
        assertEquals(g.getClouds().get(0).getStudents().size(), 0); // checks if the chosen Cloud has been emptied after the students have been moved
    }

    @Test
    void setCurrentPlayer() {
        Game g = new Game();
        Board b = new Board(8, Tower.BLACK);
        Player p = new Player("p1", false, b, Wizard.WIZARD1);
        assertNotEquals(g.getCurrentPlayer(), p); // checks if 'p' is not the current Player before the setCurrentPlayer method is called
        g.setCurrentPlayer(p);
        assertEquals(g.getCurrentPlayer(), p); // checks if the current Player has been correctly updated
    }

    @Test
    void getCoins() {
        Game g = new Game();
        assertEquals(g.getCoins(), 0); // checks if the Game has 0 coins when instantiated
        g.setCoins(2);
        assertEquals(g.getCoins(), 2); // checks if the number of coins has been correctly updated
        g.addCoin();
        assertEquals(g.getCoins(), 3); // checks if the amount of coins has correctly increased
    }

    @Test
    void moveMotherNature() {
        // An ArrayList of View (containing a single view) is created in order to check if View update methods are correctly called by Game method 'moveMotherNature'
        new Server(12348);
        Executors.newCachedThreadPool();
        ServerSocket serverSocket = null;
        Socket socketC;
        Socket socketS = null;
        ObjectInputStream inS = null;
        ObjectOutputStream outS = null;
        ArrayList<View> v = new ArrayList<>();
        boolean quit = false;
        while (!quit) {
            try {
                serverSocket = new ServerSocket(12348);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socketC = new Socket("127.0.0.1", 12348);
                new ObjectOutputStream(socketC.getOutputStream());
                socketS = serverSocket.accept();
                inS = new ObjectInputStream(socketS.getInputStream());
                outS = new ObjectOutputStream(socketS.getOutputStream());
                new ObjectInputStream(socketC.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            v.add(new VirtualView(socketS, inS, outS));
            quit = true;
        }
        Game g = new Game();
        g.setNumberPlayers(3);
        g.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g.joinGame("p3", Tower.BLACK, Wizard.WIZARD3);
        g.setExpertMode(true);
        g.startGame(v);

        int mnPos = -1;
        for (int i = 0; i < 12 && mnPos == -1; i++) {
            if (g.getArchipelagos().get(i).checkMotherNature()) // 'mnPos' is the current Mother Nature position
                mnPos = i;
        }
        assertDoesNotThrow(() -> g.moveMotherNature(3)); // check if the EndGame exception is not thrown when it is certain that the number of archipelagos and towers has not changed
        int newMnPos = -1;
        for (int i = 0; i < 12 && newMnPos == -1; i++) {
            if (g.getArchipelagos().get(i).checkMotherNature()) // 'newMNPos' is the updated Mother Nature position
                newMnPos = i;
        }
        assertEquals(newMnPos, (mnPos + 3) % 12); // checks if the index of Mother Nature position has increased by the specified number of steps (= 3)

        // checks if the MagicPostman effect is used when the MagicPostman is the active Character
        g.getCharacters()[0] = new MagicPostman();
        ArrayList<Parameter> p = new ArrayList<>();
        p.add(new IntegerParameter("steps", 2)); // '2' are the additional Mother Nature steps
        g.getCharacters()[0].task(p);
        g.setActiveCharacter(g.getCharacters()[0]);
        assertDoesNotThrow(() -> g.moveMotherNature(3));
        mnPos = newMnPos;
        newMnPos = -1;
        for (int i = 0; i < 12 && newMnPos == -1; i++) {
            if (g.getArchipelagos().get(i).checkMotherNature())
                newMnPos = i;
        }
        assertEquals(newMnPos, (mnPos + 3 + 2) % 12); // checks if Mother Nature has been moved by 2 additional steps when the MagicPostman effect has been used


        Game g2 = new Game();
        g2.setNumberPlayers(4);
        g2.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g2.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g2.joinGame("p3", Tower.BLACK, Wizard.WIZARD3);
        g2.joinGame("p4", Tower.WHITE, Wizard.WIZARD4);
        g2.setExpertMode(true);
        g2.startGame(v);
        mnPos = -1;
        for (int i = 0; i < g2.getArchipelagos().size(); i++) {
            if (g2.getArchipelagos().get(i).checkMotherNature())
                mnPos = i;
        }
        g2.getCharacters()[0] = new HerbsGranny();
        int hgNoEntry = ((HerbsGranny) g2.getCharacters()[0]).getNoEntry();
        g2.getArchipelagos().get((mnPos + 1) % (g2.getArchipelagos().size())).addNoEntry(1);
        assertEquals(g2.getArchipelagos().get((mnPos + 1) % (g2.getArchipelagos().size())).checkNoEntry(), 1); // checks if 1 No Entry card has been added to the archipelago
        assertDoesNotThrow(() -> g2.moveMotherNature(1));
        assertEquals(g2.getArchipelagos().get((mnPos + 1) % (g2.getArchipelagos().size())).checkNoEntry(), 0); // checks if the No Entry card has been removed from the archipelago when Mother Nature has moved on that archipelago
        assertEquals(((HerbsGranny) g2.getCharacters()[0]).getNoEntry(), hgNoEntry + 1); // checks if the removed No Entry card has been replaced on the Character card
        mnPos = (mnPos + 1) % (g2.getArchipelagos().size());
        assertDoesNotThrow(() -> g2.moveMotherNature(1));
        // checks if any Tower is placed on the Archipelagos when Mother Nature was moved
        assertEquals(g2.getTeams()[0].getPlayers()[0].getBoard().getNumberTowers(), 8);
        assertEquals(g2.getTeams()[1].getPlayers()[0].getBoard().getNumberTowers(), 8);
        assertEquals(g2.getTeams()[0].getPlayers()[1].getBoard().getNumberTowers(), 0);
        assertEquals(g2.getTeams()[1].getPlayers()[1].getBoard().getNumberTowers(), 0);


        Game g3 = new Game();
        g3.setNumberPlayers(4);
        g3.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g3.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g3.joinGame("p3", Tower.BLACK, Wizard.WIZARD3);
        g3.joinGame("p4", Tower.WHITE, Wizard.WIZARD4);
        g3.setExpertMode(true);
        g3.startGame(v);
        for (Archipelago a : g3.getArchipelagos()) { // each Archipelago has at least one RED Student
            a.getIslands().get(0).addStudent(Student.RED);
        }
        g3.getTeams()[0].getPlayers()[0].getBoard().updatePresenceOfProfessor(Student.RED); // the first player of the first team has the RED Professor
        assertDoesNotThrow(() -> g3.moveMotherNature(1));
        assertEquals(g3.getTeams()[0].getPlayers()[0].getBoard().getNumberTowers(), 7); // checks if the number of towers of the player who possesses the RED Professor has decreased
        g3.getTeams()[0].getPlayers()[0].getBoard().updatePresenceOfProfessor(Student.RED);
        g3.getTeams()[1].getPlayers()[0].getBoard().updatePresenceOfProfessor(Student.RED); // the RED Professor is controlled by the first player of the second team
        assertDoesNotThrow(() -> g3.moveMotherNature(1));
        assertEquals(g3.getTeams()[1].getPlayers()[0].getBoard().getNumberTowers(), 7); // checks if the number of towers of the player who possesses the RED Professor has decreased

        // Team 1 Tower placement
        Game g4 = new Game();
        g4.setNumberPlayers(4);
        g4.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g4.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g4.joinGame("p3", Tower.BLACK, Wizard.WIZARD3);
        g4.joinGame("p4", Tower.WHITE, Wizard.WIZARD4);
        g4.setExpertMode(true);
        g4.startGame(v);
        int i = 0;
        for (Archipelago a : g4.getArchipelagos()) { // each archipelago has at least one Student for each color
            a.getIslands().get(0).addStudent(Student.RED);
            a.getIslands().get(0).addStudent(Student.BLUE);
            a.getIslands().get(0).addStudent(Student.YELLOW);
            a.getIslands().get(0).addStudent(Student.PINK);
            a.getIslands().get(0).addStudent(Student.GREEN);
            if (a.checkMotherNature())
                mnPos = i;
            i++;
        }
        // the first Player of the first Team controls every Professor
        g4.getTeams()[0].getPlayers()[0].getBoard().updatePresenceOfProfessor(Student.RED);
        g4.getTeams()[0].getPlayers()[0].getBoard().updatePresenceOfProfessor(Student.BLUE);
        g4.getTeams()[0].getPlayers()[0].getBoard().updatePresenceOfProfessor(Student.YELLOW);
        g4.getTeams()[0].getPlayers()[0].getBoard().updatePresenceOfProfessor(Student.PINK);
        g4.getTeams()[0].getPlayers()[0].getBoard().updatePresenceOfProfessor(Student.GREEN);
        g4.getArchipelagos().get((mnPos + 1) % 12).setTower(Tower.WHITE);
        // checks if only the first Player of the Team has the towers
        assertEquals(g4.getTeams()[0].getPlayers()[0].getBoard().getNumberTowers(), 8);
        assertEquals(g4.getTeams()[0].getPlayers()[1].getBoard().getNumberTowers(), 0);
        assertDoesNotThrow(() -> g4.moveMotherNature(1));
        // checks if only the towers of the first player have been reduced after conquering the archipelago
        assertEquals(g4.getTeams()[0].getPlayers()[0].getBoard().getNumberTowers(), 7);
        assertEquals(g4.getTeams()[0].getPlayers()[1].getBoard().getNumberTowers(), 0);
        // checks if the Tower has been correctly replaced with the Tower of the Team with the highest influence
        assertEquals(g4.getArchipelagos().get((mnPos + 1) % 12).getTower(), Tower.BLACK);

        // Team 2 Tower placement
        Game g5 = new Game();
        g5.setNumberPlayers(4);
        g5.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g5.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g5.joinGame("p3", Tower.BLACK, Wizard.WIZARD3);
        g5.joinGame("p4", Tower.WHITE, Wizard.WIZARD4);
        g5.setExpertMode(true);
        g5.startGame(v);
        i = 0;
        for (Archipelago a : g5.getArchipelagos()) { // each archipelago has at least one Student for each color
            a.getIslands().get(0).addStudent(Student.RED);
            a.getIslands().get(0).addStudent(Student.BLUE);
            a.getIslands().get(0).addStudent(Student.YELLOW);
            a.getIslands().get(0).addStudent(Student.PINK);
            a.getIslands().get(0).addStudent(Student.GREEN);
            if (a.checkMotherNature())
                mnPos = i;
            i++;
        }
        // the first Player of the second Team controls every Professor
        g5.getTeams()[1].getPlayers()[0].getBoard().updatePresenceOfProfessor(Student.RED);
        g5.getTeams()[1].getPlayers()[0].getBoard().updatePresenceOfProfessor(Student.BLUE);
        g5.getTeams()[1].getPlayers()[0].getBoard().updatePresenceOfProfessor(Student.YELLOW);
        g5.getTeams()[1].getPlayers()[0].getBoard().updatePresenceOfProfessor(Student.PINK);
        g5.getTeams()[1].getPlayers()[0].getBoard().updatePresenceOfProfessor(Student.GREEN);
        g5.getArchipelagos().get((mnPos + 1) % 12).setTower(Tower.BLACK);
        // checks if only the first Player of the Team has the towers
        assertEquals(g5.getTeams()[1].getPlayers()[0].getBoard().getNumberTowers(), 8);
        assertEquals(g4.getTeams()[1].getPlayers()[1].getBoard().getNumberTowers(), 0);
        assertDoesNotThrow(() -> g5.moveMotherNature(1));
        // checks if only the towers of the first player have been reduced after conquering the archipelago
        assertEquals(g5.getTeams()[1].getPlayers()[0].getBoard().getNumberTowers(), 7);
        assertEquals(g4.getTeams()[1].getPlayers()[1].getBoard().getNumberTowers(), 0);
        // checks if the Tower has been correctly replaced with the Tower of the Team with the highest influence
        assertEquals(g5.getArchipelagos().get((mnPos + 1) % 12).getTower(), Tower.WHITE);


        // Checking characters :

        // Farmer is the active Character of the round
        Game g6 = new Game();
        g6.setNumberPlayers(4);
        g6.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g6.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g6.joinGame("p3", Tower.BLACK, Wizard.WIZARD3);
        g6.joinGame("p4", Tower.WHITE, Wizard.WIZARD4);
        g6.setExpertMode(true);
        g6.startGame(v);
        g6.getCharacters()[0] = new Farmer(g6.getPlayers());
        p = new ArrayList<>();
        p.add(new PlayerParameter("player2", g6.getPlayers().get(1)));
        g6.getCharacters()[0].task(p);
        g6.setActiveCharacter(g6.getCharacters()[0]);
        i = 0;
        for (Archipelago a : g6.getArchipelagos()) { // each Archipelago contains at least one RED Student
            a.getIslands().get(0).addStudent(Student.RED);
            if (a.checkMotherNature())
                mnPos = i;
            i++;
        }
        g6.getPlayers().get(0).getBoard().updatePresenceOfProfessor(Student.RED); // 'p1' controls the RED Professor
        g6.getPlayers().get(0).getBoard().getHallStudents().put(Student.RED, 1); // 'p1' has 1 RED Student in the hall
        g6.getPlayers().get(1).getBoard().getHallStudents().put(Student.RED, 1); // 'p2' has 1 RED Student in the hall
        assertEquals(g6.getPlayers().get(1).getBoard().getNumberTowers(), 8); // checks if 'p2' has the correct amount of towers
        assertDoesNotThrow(() -> g6.moveMotherNature(1));
        // checks if the Farmer's effect is activated when moveMotherNature() is called
        assertEquals(g6.getPlayers().get(1).getBoard().getNumberTowers(), 7); // the number of towers of 'p2' has been correctly updated
        assertTrue(g6.getPlayers().get(0).getBoard().isProfessorPresent(Student.RED)); // 'p1' has still the RED Professor
        assertEquals(g6.getPlayers().get(1).getBoard().getTowerColor(), g6.getArchipelagos().get((mnPos + 1) % (g6.getArchipelagos().size())).getTower()); // checks if the Tower of 'p2' has been placed on the Archipelago


        // Centaur is the active Character of the round
        Game g7 = new Game();
        g7.setNumberPlayers(4);
        g7.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g7.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g7.joinGame("p3", Tower.BLACK, Wizard.WIZARD3);
        g7.joinGame("p4", Tower.WHITE, Wizard.WIZARD4);
        g7.setExpertMode(true);
        g7.startGame(v);
        g7.getCharacters()[0] = new Centaur();
        p = new ArrayList<>();
        g7.getCharacters()[0].task(p);
        g7.setActiveCharacter(g7.getCharacters()[0]);
        i = 0;
        for (Archipelago a : g7.getArchipelagos()) { // each Archipelago only contains one RED Student
            a.getIslands().get(0).getStudents().clear();
            a.getIslands().get(0).addStudent(Student.RED);
            if (a.checkMotherNature())
                mnPos = i;
            i++;
        }
        g7.getArchipelagos().get((mnPos + 1) % 12).setTower(Tower.WHITE);
        g7.getPlayers().get(0).getBoard().updatePresenceOfProfessor(Student.RED); // the first Player controls the RED Professor
        assertDoesNotThrow(() -> g7.moveMotherNature(1));
        // checks if the Centaur's effect is activated when moveMotherNature() is called
        assertEquals(g7.getArchipelagos().get((mnPos + 1) % 12).getTower(), Tower.BLACK); // checks if the Tower has been correctly replaced (the first Team has the highest influence on the Archipelago)
        assertEquals(g7.getPlayers().get(0).getBoard().getNumberTowers(), 7); // checks if the first Player's towers have been correctly updated


        // Knight is the active Character of the round
        Game g8 = new Game();
        g8.setNumberPlayers(4);
        g8.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g8.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g8.joinGame("p3", Tower.BLACK, Wizard.WIZARD3);
        g8.joinGame("p4", Tower.WHITE, Wizard.WIZARD4);
        g8.setExpertMode(true);
        g8.startGame(v);
        g8.getCharacters()[0] = new Knight();
        p = new ArrayList<>();
        p.add(new PlayerParameter("player 1", g8.getPlayers().get(0)));
        g8.getCharacters()[0].task(p);
        g8.setActiveCharacter(g8.getCharacters()[0]);
        i = 0;
        for (Archipelago a : g8.getArchipelagos()) {
            if (a.checkMotherNature())
                mnPos = i;
            i++;
        }
        assertNull(g8.getArchipelagos().get((mnPos + 1) % 12).getTower()); // there is no Tower on the Archipelago where Mother Nature will end her movements
        assertDoesNotThrow(() -> g8.moveMotherNature(1));
        // checks if the Knight's effect is activated when moveMotherNature() is called
        assertEquals(g8.getArchipelagos().get((mnPos + 1) % 12).getTower(), Tower.BLACK); // checks if Team 1 has the highest influence on the Archipelago due to the 2 additional points given by the Character's effect
        assertEquals(g8.getPlayers().get(0).getBoard().getNumberTowers(), 7);


        // MushroomHunter is the active Character of the round
        Game g9 = new Game();
        g9.setNumberPlayers(4);
        g9.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g9.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g9.joinGame("p3", Tower.BLACK, Wizard.WIZARD3);
        g9.joinGame("p4", Tower.WHITE, Wizard.WIZARD4);
        g9.setExpertMode(true);
        g9.startGame(v);
        g9.getCharacters()[0] = new MushroomHunter();
        p = new ArrayList<>();
        p.add(new StudentParameter("color", Student.RED));
        g9.getCharacters()[0].task(p);
        g9.setActiveCharacter(g9.getCharacters()[0]);
        i = 0;
        for (Archipelago a : g9.getArchipelagos()) { // each Archipelago only contains one RED Student
            a.getIslands().get(0).getStudents().clear();
            a.getIslands().get(0).addStudent(Student.RED);
            if (a.checkMotherNature())
                mnPos = i;
            i++;
        }
        assertNull(g9.getArchipelagos().get((mnPos + 1) % 12).getTower()); // checks if there are no Towers placed on the Archipelago
        g9.getPlayers().get(0).getBoard().updatePresenceOfProfessor(Student.RED); // 'p1' controls the RED Professor
        assertEquals(g9.getPlayers().get(0).getBoard().getNumberTowers(), 8); // checks if 'p1' has the correct amount of towers
        assertDoesNotThrow(() -> g9.moveMotherNature(1));
        // checks if the MushroomHunter's effect is activated when moveMotherNature() is called
        assertNull(g9.getArchipelagos().get((mnPos + 1) % 12).getTower()); // checks if still there isn't any Tower on the Archipelago because RED Students are not considered during the influence calculation
        assertEquals(g9.getPlayers().get(0).getBoard().getNumberTowers(), 8); // checks if 'p1' has the same number of towers as before
    }

    @Test
    void joinGame() {
        Game g = new Game();
        g.setNumberPlayers(3);
        assertEquals(g.getPlayers().size(), 0);
        g.joinGame("p", Tower.BLACK, Wizard.WIZARD1);
        assertEquals(g.getPlayers().size(), 1); // checks if the number of players in the game corresponds to the number of player that joined the game
        assertEquals(g.getPlayers().get(0).getNickname(), "p"); // checks if the player's nickname is equal to the String passed to the joinGame method
        assertEquals(g.getPlayers().get(0).getBoard().getTowerColor(), Tower.BLACK); // checks if the player's Tower color is equal to the color passed to the joinGame method
        assertEquals(g.getPlayers().get(0).getWizard(), Wizard.WIZARD1);  // checks if the player's wizard is equal to the wizard passed to the joinGame method
        assertEquals(g.getPlayers().get(0).getBoard().getNumberTowers(), 6); // checks if the player's number of towers is correctly set according to the game's number of players

        Game g2 = new Game();
        g2.setNumberPlayers(4);
        g2.joinGame("p1", Tower.WHITE, Wizard.WIZARD2);
        assertEquals(g2.getPlayers().get(0).getBoard().getNumberTowers(), 8); // checks if the player's number of towers is correctly set according to the game's number of players (game has 4 players and 'p1' is the team leader)
        g2.joinGame("p2", Tower.BLACK, Wizard.WIZARD1);
        g2.joinGame("p3", Tower.BLACK, Wizard.WIZARD3);
        assertEquals(g2.getPlayers().get(2).getBoard().getNumberTowers(), 0); // checks if the player's number of towers is correctly set according to the game's number of players (game has 4 players and 'p3' is not the team leader)
    }

    @Test
    void isExpertMode() {
        Game g = new Game();
        assertFalse(g.isExpertMode()); // checks if the default game mode is correctly set
        g.setExpertMode(true);
        assertTrue(g.isExpertMode()); // checks if the returned value is equal to the set value for 'expertMode'
        g.setExpertMode(false);
        assertFalse(g.isExpertMode()); // checks if the returned value is equal to the set value for 'expertMode'
    }

    @Test
    void isBagEmpty() {
        Game g = new Game();
        assertFalse(g.isBagEmpty()); // checks if, when a Game is instantiated, it has a Bag which is not empty
    }

    @Test
    void fillClouds(){
        // An ArrayList of View (containing a single view) is created in order to check if 'cloudUpdate' View method is correctly called by 'fillClouds' Game method
        new Server(12349);
        Executors.newCachedThreadPool();
        ServerSocket serverSocket = null;
        Socket socketC;
        Socket socketS = null;
        ObjectInputStream inS = null;
        ObjectOutputStream outS = null;
        ArrayList<View> v = new ArrayList<>();
        boolean quit = false;
        while (!quit) {
            try {
                serverSocket = new ServerSocket(12349);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socketC = new Socket("127.0.0.1", 12349);
                new ObjectOutputStream(socketC.getOutputStream());
                socketS = serverSocket.accept();
                inS = new ObjectInputStream(socketS.getInputStream());
                outS = new ObjectOutputStream(socketS.getOutputStream());
                new ObjectInputStream(socketC.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            v.add(new VirtualView(socketS, inS, outS));
            quit = true;
        }

        Game g = new Game();
        g.setNumberPlayers(2);
        g.setExpertMode(false);
        g.joinGame("p1", Tower.BLACK, Wizard.WIZARD1);
        g.joinGame("p2", Tower.WHITE, Wizard.WIZARD2);
        g.startGame(v);

        for(Cloud c : g.getClouds()) {
            c.clearCloud(); // each Cloud in Game is emptied
            assertEquals(0, c.getStudents().size()); // checks if each Cloud has been correctly emptied
        }

        g.fillClouds();
        for(Cloud c : g.getClouds())
            assertEquals(3, c.getStudents().size()); // checks if each Cloud in Game has been filled according to the number of players in Game
    }
}
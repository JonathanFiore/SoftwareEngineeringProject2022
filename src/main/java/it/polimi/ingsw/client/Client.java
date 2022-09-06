package it.polimi.ingsw.client;

import it.polimi.ingsw.client.gui.JavaFXGUI;
import java.util.Scanner;

/**
 * This is client app
 */
public class Client {

    public static void main(String[] args) {

        ViewCLI v;
        String s;
        Scanner in = new Scanner(System.in);
        System.out.print("Choose CLI[0] or GUI[1]: ");
        s = in.nextLine();

        while (!s.equals("0") && !s.equals("1")) {
            System.out.print("Invalid choice. Choose CLI[0] or GUI[1]: ");
            s = in.nextLine();
        }

        if (Integer.parseInt(s) == 0) {
            v = new ViewCLI();
            v.startCLI();
        } else {
            JavaFXGUI.main(args);
        }
    }
}

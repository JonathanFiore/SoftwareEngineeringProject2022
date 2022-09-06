package it.polimi.ingsw.client.gui.controllers;

/**
 * This class is used to check the values of the Ip address and number of port entered by a player
 */
public abstract class InputChecker {

    /**
     * This method is used to check if the Ip address is valid
     * @param s it's the Ip address written by the player
     * @return true if the Ip address is valid, false if it isn't
     */
    public static boolean checkIp(String s) {
        boolean valid = true;
        if (s.length() < 7 || s.length() > 15) // length check
            valid = false;
        else if ((s.charAt(0) < '0' || s.charAt(0) > '9') || (s.charAt(s.length() - 1) < '0' || s.charAt(s.length() - 1) > '9')) // check first and last digit
            valid = false;
        else {
            int dots = 0;
            for (int i = 0; i < s.length() && valid; i++) { // check if every digit is a number or a dot
                if ((s.charAt(i) < '0' || s.charAt(i) > '9') && s.charAt(i) != '.')
                    valid = false;
                else if (s.charAt(i) == '.')
                    dots++;
                if (dots == 4)
                    valid = false;
            }
            if (dots != 3) // ip must have 3 dots
                valid = false;
            if (valid) {
                String num = "";
                for (int i = 0; i < s.length() && valid; i++) {
                    if (s.charAt(i) >= '0' && s.charAt(i) <= '9') { // s[i] is a number
                        num += s.charAt(i);
                        if (num.length() == 4)
                            valid = false;
                    } else { // s[i] is a dot
                        if (s.charAt(i - 1) == '.' || Integer.parseInt(num) > 255)
                            valid = false;
                        else if (num.length() > 1 && num.charAt(0) == '0')
                            valid = false;
                        num = "";
                    }
                }
                if (valid && Integer.parseInt(num) > 255 || (num.length() > 1 && num.charAt(0) == '0')) // checks the last numeric portion of the ip
                    valid = false;
            }
        }
        return valid;
    }

    /**
     * This method is used to check if the port numer is valid
     * @param port it's the port number written by the player
     * @return true if the port number is valid, false if it isn't
     */
    public static boolean checkPort(String port){
        boolean okPort = true;
        int p;

        if (port.length() >= 4 && port.length() <= 5) {
            try {
                p = Integer.parseInt(port);
                if(p<1025 || p>65536)
                    okPort=false;
            } catch (NumberFormatException e) {
                okPort = false;
            }
        } else
            okPort = false;

        return okPort;
    }
}

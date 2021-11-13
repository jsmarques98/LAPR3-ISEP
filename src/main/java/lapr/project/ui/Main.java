package lapr.project.ui;

import lapr.project.store.ShipStore;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 * @author Nuno Bettencourt <nmb@isep.ipp.pt> on 24/05/16.
 */
class Main {

    /**
     * Logger class.
     */
    private static final Logger LOGGER = Logger.getLogger("MainLog");

    /**
     * Private constructor to hide implicit public one.
     */
    private Main() {

    }

    /**
     * Application main method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        ShipStore st = new ShipStore();
        String option = new String();
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        while (!option.equals("0")) {
            System.out.println("Please make your selection");
            System.out.println("1) Search ship");
            System.out.println("2) Search ship summary");
            System.out.println("0) Leave");
            option = read.readLine();
            String value;
            switch (option) {
                case "1":
                    System.out.println("Insert the value: ");
                    value = read.readLine();
                    System.out.println(st.findShip(value));
                    break;
                case "2":
                    System.out.println("Insert the value: ");
                    value = read.readLine();
                    System.out.println(st.shipSummary(value));
                    break;
                case "0":
                    System.out.println("bye");
                    break;
            }
        }
    }
}



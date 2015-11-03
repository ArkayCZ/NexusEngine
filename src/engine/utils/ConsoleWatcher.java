package engine.utils;

import java.util.Scanner;

/**
 * Created by vesel on 03.11.2015.
 */
public class ConsoleWatcher {

    private Scanner mScanner;

    public ConsoleWatcher() {
        mScanner = new Scanner(System.in);
    }

    public String getCommand() {
        return mScanner.nextLine();
    }

    public void executeCommand(String command) {
        if(command.equals("togglefps"))
            Settings.FPS_LOGGING_ENABLED = !Settings.FPS_LOGGING_ENABLED;
        else if(command.equals("togglecollisions"))
            Settings.COLLISIONS_ENABLED = !Settings.COLLISIONS_ENABLED;
    }

}

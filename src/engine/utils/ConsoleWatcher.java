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
        if(command.equals("tfps")) {
            Settings.FPS_LOGGING_ENABLED = !Settings.FPS_LOGGING_ENABLED;
            Log.i("FPS logging enabled: " + Settings.FPS_LOGGING_ENABLED);
        }
        else if(command.equals("tcl")) {
            Settings.COLLISIONS_ENABLED = !Settings.COLLISIONS_ENABLED;
            Log.i("Collisions enabled: " + Settings.COLLISIONS_ENABLED);

        }
        else if(command.equals("tmouse")) {
            Settings.MOUSE_CAMERA = !Settings.MOUSE_CAMERA;
            Log.i("Mouse camera rotation enabled: " + Settings.MOUSE_CAMERA);
        }
        else if(command.equals("tkey")) {
            Settings.KEY_CAMERA = !Settings.KEY_CAMERA;
            Log.i("Key camera rotation enabled: " + Settings.KEY_CAMERA);
        }
        else
            Log.w("Unknown command.");
    }

}

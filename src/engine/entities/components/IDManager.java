package engine.entities.components;

/**
 * Created by vesel on 21.01.2016.
 * Used for assigning component ID's.
 */
public class IDManager {

    private static int sLastID = -1;

    public static int getID() {
        sLastID++;
        return sLastID;
    }


}

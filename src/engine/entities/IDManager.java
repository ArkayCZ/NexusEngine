package engine.entities;

/**
 * Created by vesel on 21.01.2016.
 * Used for assigning component and entity ID's.
 */
public class IDManager {

    private static int sLastComponentID = -1;
    private static int sLastEntityID = -1;

    public static int getComponentID() {
        sLastComponentID++;
        return sLastComponentID;
    }

    public static int getEntityID() {
        sLastEntityID++;
        return sLastEntityID;
    }
}

package utils;

/**
 * Created by vesel on 30.10.2015.
 */
public class Log {

    private Log() {}

    public static void e(String message) {
        System.err.println("[NexEngine][error] " + message);
    }

    public static void i(String message) {
        System.out.println("[NexEngine][info] " + message);
    }

    public static void r(String message) {
        System.out.println("[NexEngine][report] " + message);
    }

    public static void w(String message) { System.err.println("[NexEngine][warning] " + message); }

}

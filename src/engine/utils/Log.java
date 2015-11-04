package engine.utils;

/**
 * Created by vesel on 30.10.2015.
 */
public class Log {

    private Log() {}

    public static void e(String message) {
        System.err.println("[error] " + message);
    }

    public static void i(String message) {
        System.out.println("[info] " + message);
    }

    public static void r(String message) {
        System.out.println("[report] " + message);
    }

    public static void w(String message) { System.err.println("[warning] " + message); }

}

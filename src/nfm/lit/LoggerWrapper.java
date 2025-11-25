package nfm.lit;
import fallk.logmaster.HLogger;

public class LoggerWrapper {

    public static void info(String message) {
        HLogger.info(message);

        // print to DevTool console if available
        if (GameSparker.devTool != null) {
            GameSparker.devTool.print("[INFO] " + message);
        }
    }
    public static void warn(String message) {
        HLogger.warn(message);

        if (GameSparker.devTool != null) {
            GameSparker.devTool.print("[WARN] " + message);
        }
    }
    public static void error(String message) {
        HLogger.error(message);

        if (GameSparker.devTool != null) {
            GameSparker.devTool.print("[ERROR] " + message);
        }
    }
    public static void error(Exception message) {
        if (GameSparker.devTool != null) {
            GameSparker.devTool.print("[EXCEPTION] " + message);
        }
    }
}
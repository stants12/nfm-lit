package fallk.logmaster;

/**
 * Simple replacement for HLogger to avoid missing dependencies.
 * Provides basic logging functionality for the NFM application.
 */
public class HLogger {
    
    public static void info(String message) {
        System.out.println("[INFO] " + message);
    }
    
    public static void warn(String message) {
        System.out.println("[WARN] " + message);
    }
    
    public static void error(String message) {
        System.err.println("[ERROR] " + message);
    }
    
    public static void debug(String message) {
        System.out.println("[DEBUG] " + message);
    }
    
    public static void trace(String message) {
        System.out.println("[TRACE] " + message);
    }
}

package nfm.lit;

/**
 * Centralized configuration for car parameters and constants.
 */
public class CarConfig {
    // Wheel parameters
    public static final float DEFAULT_WHEEL_SIZE = 2.0f;
    public static final float DEFAULT_WHEEL_DEPTH = 3.0f;
    public static final int DEFAULT_GROUND = 0;
    public static final int DEFAULT_MAST = 0;
    public static final int[] DEFAULT_WHEEL_COLOR = {120, 120, 120};
    
    // Car models
    public static final String[] CAR_MODELS = {
        "2000tornados", "formula7", "canyenaro", "lescrab", "nimi", "maxrevenge", 
        "leadoxide", "koolkat", "drifter", "policecops", "mustang", "king", 
        "audir8", "masheen", "radicalone", "drmonster"
    };
    
    private CarConfig() {}
}

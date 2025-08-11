package nfm.lit;

/**
 * Centralized configuration for stage parameters and constants.
 */
public class StageConfig {
    // Stage parameters
    public static final int DEFAULT_STAGE_WIDTH = 1000;
    public static final int DEFAULT_STAGE_HEIGHT = 800;
    public static final String DEFAULT_STAGE_NAME = "hogan rewish";
    
    // System parameters
    public static final String OPERATING_SYSTEM = System.getProperty("os.name").toLowerCase();
    public static final boolean IS_UNIX = OPERATING_SYSTEM.indexOf("nix") == 0 || OPERATING_SYSTEM.indexOf("nux") == 0;
    public static final boolean IS_WINDOWS = OPERATING_SYSTEM.indexOf("win") == 0;
    public static final boolean IS_MAC = OPERATING_SYSTEM.indexOf("mac") == 0;
    public static final String IS_64_BIT = System.getProperty("sun.arch.data.model").equals("64") ? "64" : "32";
    public static final String WORKING_DIRECTORY = ".";
    public static final boolean DEBUG = true;
    
    // Stage configuration
    public static final boolean SPLASH_SCREEN_ENABLED = true;
    public static final String STAGE_DIR = "data/stages/";
    public static final String COOKIE_DIR_TEMP = "data/cookies/";
    public static final String COOKIE_DIR_ZIP = "data/cookies.radq";
    
    // Track models
    public static final String[] TRACK_MODELS = {
        "road", "froad", "twister2", "twister1", "turn", "offroad", "bumproad", "offturn", 
        "nroad", "nturn", "roblend", "noblend", "rnblend", "roadend", "offroadend", 
        "hpground", "ramp30", "cramp35", "dramp15", "dhilo15", "slide10", "takeoff", 
        "sramp22", "offbump", "offramp", "sofframp", "halfpipe", "spikes", "rail",
        "thewall", "checkpoint", "fixpoint", "offcheckpoint", "sideoff", "bsideoff", 
        "uprise", "riseroad", "sroad", "soffroad", "tside", "launchpad", "thenet", 
        "speedramp", "offhill", "slider", "uphill", "roll1", "roll2", "roll3", "roll4", 
        "roll5", "roll6", "opile1", "opile2", "aircheckpoint", "tree1", "tree2", "tree3", 
        "tree4", "tree5", "tree6", "tree7", "tree8", "cac1", "cac2", "cac3", "8sroad", 
        "8soffroad", "singlewallroad", "thewall2"
    };
    
    // Checkpoint parameters
    public static final int MAX_CHECKPOINTS = 1400;
    public static final int MAX_SPECIAL_POINTS = 5;
    public static final int MAX_CARS = 51;
    public static final int DEFAULT_POSITION = 50;
    
    private StageConfig() {}
}

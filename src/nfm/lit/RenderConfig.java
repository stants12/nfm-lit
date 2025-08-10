package nfm.lit;

/**
 * Centralized configuration for rendering constants and settings.
 */
public class RenderConfig {
    // Screen dimensions
    public static final int SCREEN_WIDTH = 670;
    public static final int SCREEN_HEIGHT = 400;

    // Main menu layout
    public static final int MAIN_MENU_HEIGHT_ORIGIN = (int) (SCREEN_HEIGHT * 0.2);
    public static final int MAIN_MENU_BUTTON_HEIGHT = 22;
    public static final int MAIN_MENU_OP_0_Y = 246 + MAIN_MENU_HEIGHT_ORIGIN;
    public static final int MAIN_MENU_OP_0_WIDTH = 110;
    public static final int MAIN_MENU_OP_1_Y = 275 + MAIN_MENU_HEIGHT_ORIGIN;
    public static final int MAIN_MENU_OP_1_WIDTH = 196;
    public static final int MAIN_MENU_OP_2_Y = 306 + MAIN_MENU_HEIGHT_ORIGIN;
    public static final int MAIN_MENU_OP_2_WIDTH = 85;

    // Colors (example: credits screen)
    protected static final int[] CRED_COLORS = {25, 50, 100};

    // UI positions
    public static final int PRESS_ENTER_TO_CONTINUE_HEIGHT = SCREEN_HEIGHT - 100;

    // Prevent instantiation
    private RenderConfig() {}

    // Add more rendering-related constants here as needed
}

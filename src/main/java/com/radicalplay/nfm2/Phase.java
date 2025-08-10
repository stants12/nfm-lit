package com.radicalplay.nfm2;
/**
 * Game plases, lovingly known as `fase` in the code. The game is always in one of these phases.
 * The game phase controls what the game renders and how it responds to inputs.
 * 
 * All legacy fase codes are listed for reference. New phases you may add do not need this.
 * 
 * @author Jacherr
 */
public enum Phase {
    /**
     * -8000 (saves the game and switches to 10 (used after beating a stage))
    */ 
    SAVEGAME(-8000),
    /**
     * -205 - really unsure but seems to just hand over to POSTGAME? this might be able to be removed (TODO)
     */
    POSTGAMEHANDOVER(-205),
    /**
     * -9 - loads music and "fly-in" for car select before switching to CARSELECT
     */
    CARSELECTTRIGGER(-9),
    /**
     * -8 - shown when you try and instant replay too early
     */
    NOTENOUGHREPLAYDATA(-8),
    /**
     * -7
     */
    PAUSEMENU(-7),
    /**
     * -6 - this takes a screenshot of the current screen in order to display it in the background of pause, then goes to PAUSE.
     * may also be used for other background screenshots such as POSTGAME
     */
    PAUSETRIGGER(-6),
    /**
     * -5 - final static screen after beating/losing a stage
     */
    POSTGAME(-5),
    /**
     * -4 - responsible for fading from gameplay to the POSTGAME
     */
    POSTGAMEFADEOUT(-4),
    /**
     * -3 - used after a stage is concluded (if there is one, there isn't always)
     */
    GAMEHIGHLIGHT(-3),
    /**
     * -2 - when the game freezes and pans around an event such as getting wasted or a stunt during a GAMEHIGHLIGHT or INSTANTREPLAY
     */
    CAUGHTHIGHLIGHT(-2),
    /**
     * -1
     */
    INSTANTREPLAY(-1),
    /**
     * 0 - actually playing a stage
     */
    INGAME(0),
    /**
     * 1
     */
    STAGESELECT(1),
    /**
     * 2 - seems to be set to this to initialise the spinning reveal of a track?
     */
    STAGESELECTTRIGGER(2),
    /**
     * 3
     */
    ERRORLOADINGSTAGE(3),
    /**
     * 4
     */
    LOCKEDSTAGE(4),
    /**
     * 5 - this seems to trigger loading of game music before switching to PREGAME
     */
    PREGAMEMUSIC(5),
    /**
     * 6 - the screen that appears when you select a stage with any tips etc.
     */
    PREGAME(6),
    /**
     * 7
     */
    CARSELECT(7),
    /**
     * 8
     */
    CREDITS(8),
    /**
     * 9 - SEEMS to be related to the "click here to start" screen before handing over to MAINMENU
     */
    AWAITLOADDISMISSAL(9),
    /**
     * 10
     */
    MAINMENU(10),
    /**
     * 11
     */
    INSTRUCTIONS(11),
    /**
     * 58 - runs before stage select and seem to do some debug checks for player count before switching to STAGESELECTTRIGGER
     */
    NPLAYERSCHECK(58),
    /**
     * 111 - this is the phase the game launches with
     */
    LOADING(111),
    /*
     * 176 - draws the 3D environment when necessary
     */
    DRAWENVIRONMENT(176),
    /**
     * 205 - saves the last selected car after loading a track then hands to PREGAMEMUSIC
     */
    SELECTEDCARSAVE(205), 
    /**
     * 9000 - hidden settings menu (may be removed)
     */
    CUSTOMSETTINGS(9000),
    /**
     * 9001 - for loading a sage (custom)
     */
    LOADSTAGE(9001),
    LOADSTAGE2(9004);

    public final int value;
    
    private Phase(int v)
    {
        this.value = v;
    }

    public static Phase valueOfValue(int label) {
        for (Phase e : values()) {
            if (e.value == label) {
                return e;
            }
        }
        return null;
    }
}

package nfm.lit.audio;
//package nfm.territories.music;

// Credits to DragShot
// for his shit

import ds.nfm.ModuleLoader;
import ds.nfm.ModuleSlayer;

import nfm.lit.GameSparker;

import java.net.URL;

/**
 * The Class RadicalMod.
 */
public class RadicalMod implements RadicalMusic {

    private static String name = "";
    SuperClip sClip;
    boolean playing;
    int loaded;
    int rvol;

    String imod;

    String pmod;

    /**
     * Instantiates a new radical mod.
     */
    public RadicalMod() {
        playing = false;
        loaded = 0;
        rvol = 0;
        imod = "";
        pmod = "";
        loaded = 0;
        System.gc();
    }

    /**
     * Instantiates a new radical mod.
     *
     * @param string the string
     * @param i      the i
     * @param i_0    the i_0
     * @param i_1    the i_1
     * @param bool   the bool
     * @param bool_2 the bool_2
     */
    public RadicalMod(String string, int i, int i_0, final int i_1, final boolean bool, final boolean bool_2) {
        playing = false;
        loaded = 0;
        rvol = 0;
        imod = "";
        pmod = "";
        final int i_3 = 22000;
        i_0 = (int) (i_0 / 8000F * 2.0F * i_3);
        i = (int) (i * 0.8F);
        try {
            ds.nfm.Module dsModule;
            if (!bool_2) {
                dsModule = ModuleLoader.loadMod(string);
            } else {
                string = string.replace(' ', '_');
                final URL url = new URL("http://multiplayer.needformadness.com/tracks/music/" + string + ".zip");
                dsModule = ModuleLoader.loadMod(url);
            }
            if (dsModule.isLoaded()) {
                name = dsModule.getName();
                if (name.trim().equals("")) {
                    name = "Untitled";
                }
                final ModuleSlayer moduleslayer = ModuleLoader.prepareSlayer(dsModule, i_0, i, i_1);
                final byte is[] = moduleslayer.turnbytesNorm(bool);
                if (bool) {
                    rvol = moduleslayer.olav;
                }
                sClip = new SuperClip(is, moduleslayer.oln, i_3);
                sClip.rollBackPos = moduleslayer.rollBackPos;
                sClip.rollBackTrig = moduleslayer.oln - moduleslayer.rollBackTrig;
                loaded = 2;
            }
        } catch (final Exception exception) {
            exception.printStackTrace();
            if (GameSparker.DEBUG) {
                System.out.println("Error downloading and making Mod: " + exception);
            }
            loaded = 0;
        }
        System.runFinalization();
        System.gc();
    }

    /**
     * Instantiates a new radical mod.
     *
     * @param string the string
     */
    public RadicalMod(final String string) {
        playing = false;
        loaded = 0;
        rvol = 0;
        imod = "";
        pmod = "";
        loaded = 1;
        imod = string;
        loadimod(false);
    }

    /**
     * Instantiates a new radical mod.
     *
     * @param string the string
     * @param bool   the bool
     */
    public RadicalMod(final String string, final boolean bool) {
        playing = false;
        loaded = 0;
        rvol = 0;
        imod = "";
        pmod = "";
        loaded = 1;
        pmod = string;
        loadpmod(true);
    }

    /**
     * Loadimod.
     *
     * @param bool the bool
     */
    private void loadimod(final boolean bool) {
        if (loaded == 1) {
            final int i = 44000;
            int i_6 = 160;
            if (bool) {
                i_6 = 300;
            }
            final int i_7 = 125;
            try {
                final ds.nfm.Module dsModule = ModuleLoader.loadMod(imod);
                if (dsModule.isLoaded()) {
                    name = dsModule.getName();
                    if (name.trim().equals("")) {
                        name = "Untitled";
                    }
                    final ModuleSlayer moduleslayer = ModuleLoader.prepareSlayer(dsModule, i, i_6, i_7);
                    final byte is[] = moduleslayer.turnbytesNorm(bool);
                    if (bool) {
                        rvol = moduleslayer.olav;
                    }
                    sClip = new SuperClip(is, moduleslayer.oln, 22000);
                    sClip.rollBackPos = moduleslayer.rollBackPos;
                    sClip.rollBackTrig = moduleslayer.oln - moduleslayer.rollBackTrig;
                    loaded = 2;
                }
            } catch (final Exception exception) {
                if (GameSparker.DEBUG) {
                    System.out.println("Error making a imod: " + exception);
                }
                loaded = 0;
            }
            System.runFinalization();
            System.gc();
        }
    }

    /**
     * Loadpmod.
     *
     * @param bool the bool
     */
    private void loadpmod(final boolean bool) {
        if (loaded == 1) {
            final int i = 44000;
            int i_10 = 160;
            if (bool) {
                i_10 = 300;
            }
            final int i_11 = 125;
            try {
                final ds.nfm.Module dsModule = ModuleLoader.loadMod(pmod);
                if (dsModule.isLoaded()) {
                    name = dsModule.getName();
                    if (name.trim().equals("")) {
                        name = "Untitled";
                    }
                    final ModuleSlayer moduleslayer = ModuleLoader.prepareSlayer(dsModule, i, i_10, i_11);
                    final byte is[] = moduleslayer.turnbytesNorm(bool);
                    if (bool) {
                        rvol = moduleslayer.olav;
                    }
                    sClip = new SuperClip(is, moduleslayer.oln, 22000);
                    sClip.rollBackPos = moduleslayer.rollBackPos;
                    sClip.rollBackTrig = moduleslayer.oln - moduleslayer.rollBackTrig;
                    loaded = 2;
                }
            } catch (final Exception exception) {
                if (GameSparker.DEBUG) {
                    System.out.println("Error making a imod: " + exception);
                }
                loaded = 0;
            }
            System.runFinalization();
            System.gc();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.trashers.skyline.singleplayer.RadicalMusic#play()
     */
    @Override
    public void play() {
        if (!playing && loaded == 2) {
            sClip.play();
            if (sClip.stoped == 0) {
                playing = true;
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.trashers.skyline.singleplayer.RadicalMusic#unload()
     */
    @Override
    public void unload() {
        if (playing && loaded == 2) {
            sClip.stop();
            playing = false;
        }
        try {
            sClip.close();
            sClip = null;
        } catch (final Exception exception) {
        }
        try {
            imod = null;
        } catch (final Exception exception) {
        }
        System.gc();
        loaded = 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.trashers.skyline.singleplayer.RadicalMusic#resume()
     */
    @Override
    @Deprecated
    public void resume() {
        if (!playing && loaded == 2) {
            sClip.resume();
            if (sClip.stoped == 0) {
                playing = true;
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.trashers.skyline.singleplayer.RadicalMusic#stop()
     */
    @Override
    @Deprecated
    public void stop() {
        if (playing && loaded == 2) {
            sClip.stop();
            playing = false;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.trashers.skyline.singleplayer.RadicalMusic#isPaused()
     */
    @Override
    public boolean isPaused() {
        return !playing;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.trashers.skyline.singleplayer.RadicalMusic#setPaused(boolean)
     */
    @Override
    public void setPaused(final boolean pause) {
        if (pause)
            if (playing && loaded == 2) {
                sClip.stop();
                playing = false;
            } else {
            }
        else if (!playing && loaded == 2) {
            sClip.resume();
            if (sClip.stoped == 0) {
                playing = true;
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.trashers.skyline.singleplayer.RadicalMusic#getType()
     */
    @Override
    public Type getType() {
        return Type.TYPE_MOD;
    }

}

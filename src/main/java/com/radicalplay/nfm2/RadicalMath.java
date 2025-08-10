package com.radicalplay.nfm2;

/**
 * Contains many of the math functions for various purposes.
 *
 * @author Rafa, Kaffeinated, Omar Wally
 */
class RadicalMath {
    private static final float[] tcos = new float[360];
    private static final float[] tsin = new float[360];

    static {
        for (int i = 0; i < 360; i++) {
            tcos[i] = (float) Math.cos(i * 0.01745329251994329576922);
        }
        //3.14159265358979323846 / 180 = 0.01745329251994329576922
        for (int i = 0; i < 360; i++) {
            tsin[i] = (float) Math.sin(i * 0.01745329251994329576922);
        }
    }

    static public float cos(int i) {
        return tcos[Math.floorMod(i, 360)];
    }

    static public float sin(int i) {
        return tsin[Math.floorMod(i, 360)];
    }
}

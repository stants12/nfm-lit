package com.radicalplay.nfm2;

import fallk.logmaster.HLogger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * Contains often used functions, and some new ones
 *
 * @author Rafa
 * @author Kaffeinated
 * @author Omar Waly
 */
public class Utility {

    private static long startTime;

    /**
     * Gets a value from a string in format:
     * string(value1,value2,value3...)
     *
     * @param string The variable name (e.g: foo(bar) = foo)
     * @param source The string (single line) to get the value from
     * @param i      The position of the value (starting from 0)
     * @return An integer containing the value
     */
    public static int getvalue(final String string, final String source, final int i) {
        return (int) Float.parseFloat(getstring(string, source, i));
    }
    public static float getfloat(final String string, final String source, final int i) {
        return Float.parseFloat(getstring(string, source, i));
    }

    /**
     * Turns a 3D XY coordinate into a 2D X perspective coordinate.
     *
     * @param i The 3D X point
     * @param j The 3D Y point
     * @return The 2D X coordinate.
     */
    public static int xs(final int i, int j) {
        if (j < Medium.cz) {
            j = Medium.cz;
        }
        return (j - Medium.focus_point) * (Medium.cx - i) / j + i;
    }

    public static int cXs(final int i, int j) {
        if (j < 50) {
            j = 50;
        }
        return (j - Medium.focus_point) * (Medium.cx - i) / j + i;
    }

    /**
     * Turns a 3D ZY coordinate into a 2D Y perspective coordinate.
     *
     * @param i The 3D Z point
     * @param j The 3D Y point
     * @param m There are two different ys() methods within NFM2, this integer is
     *          used to unify them while providing a way to distinguish between
     *          them.
     *          0 is for using Medium.cz (normal)
     *          1 is for using 10 (only found in Medium)
     * @return The 2D Y coordinate.
     */
    public static int ys(final int i, int j, int m) {
        int value = 0;
        switch (m) {
            case 0:
                value = Medium.cz;
                break;
            case 1:
                value = 10;
                break;
            default:
                break;
        }
        if (j < value) {
            j = value;
        }
        return (j - Medium.focus_point) * (Medium.cy - i) / j + i;
    }

    public static int cYs(final int i, int j) {
        if (j < 50) {
            j = 50;
        }
        return (j - Medium.focus_point) * (Medium.cy - i) / j + i;
    }

    /**
     * Gets an integer from a string in format:
     * string(int1,int2,int3...)
     *
     * @param string The beginning of the string (e.g: foo(0,0,1) = foo)
     * @param source The string (single line) to get the value from
     * @param i      The position of the value (starting from 0)
     * @return An integer containing the value
     */
    public static int getint(final String string, final String source, final int i) {
        return Integer.parseInt(getstring(string, source, i));
    }

    /**
     * Gets a substring, nicely
     *
     * @param string The beginning of the string (e.g: foo(0,0,1) = foo)
     * @param source The string (single line) to get the value from
     * @param i      The position of the string (starting from 0)
     * @return A string
     */
    public static String getstring(final String string, final String source, final int i) {
        int var = 0;
        StringBuilder part = new StringBuilder();
        for (int k = string.length() + 1; k < source.length(); k++) {
            char strChar = source.charAt(k);
            if (strChar == ',' || strChar == ')') {
                var++;
                k++;
            }
            if (var == i) {
                part.append(source.charAt(k));
            }
        }
        return part.toString();
    }

    public static int spy(int i, int j) {
        return (int) Math.sqrt((i - Medium.cx) * (i - Medium.cx) + j * j);
    }

    public static float pys(int x1, int x2, int y1, int y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public static int rpy(float x1, float x2, float y1, float y2, float z1, float z2) {
        return (int) ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
    }

    public static int py(int x1, int x2, int y1, int y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }

    /**
     * Gets the X coordinate an image should be loaded at in order for it to be
     * centered.
     * 
     * @param image The image
     * @return The X coordinate
     */
    public static int centeredImageX(Image image) {
        return (GameFacts.screenWidth / 2) - (image.getWidth(null) / 2);
    }

    /**
     * Gets the Y coordinate an image should be loaded at in order for it to be
     * centered.
     * 
     * @param image The image
     * @return The Y coordinate
     */
    public static int centeredImageY(Image image) {
        return (GameFacts.screenWidth / 2) - (image.getHeight(null) / 2);
    }

    /**
     * Gets the X coordinate an arbitary width component should be loaded at in
     * order for it to be centered.
     * 
     * @param width The width of the component
     * @return The X coordinate
     */
    public static int centeredWidthX(int width) {
        return (GameFacts.screenWidth / 2) - (width / 2);
    }

    /**
     * Gets the Y coordinate an arbitary height component should be loaded at in
     * order for it to be centered.
     * 
     * @param height The height of the component
     * @return The Y coordinate
     */
    public static int centeredHeightY(int height) {
        return (GameFacts.screenHeight / 2) - (height / 2);
    }

    /*
     * Users of this method should use Eclipse's "Refactor > Inline Method" feature
     * to inline calls to it.
     * It's a very simple method and the few nanoseconds spent to call it could be
     * crucial especially if used
     * inside of a large loop.
     * 
     * The usage of Math.pow doesn't affect performance since the Hotspot will
     * inline (turn into x * x) calls to it.
     */
    public static int distance(final int x1, final int y1, final int x2, final int y2) {
        return (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static void rot(float[] af, float[] af1, int i, int j, int k, int l) {
        if (k != 0) {
            for (int i1 = 0; i1 < l; i1++) {
                float f = af[i1];
                float f1 = af1[i1];
                af[i1] = i + ((f - i) * RadicalMath.cos(k) - (f1 - j) * RadicalMath.sin(k));
                af1[i1] = j + ((f - i) * RadicalMath.sin(k) + (f1 - j) * RadicalMath.cos(k));
            }

        }
    }

    public static void rot(int[] ai, int[] ai1, int i, int j, int k, int l) {
        if (k != 0) {
            for (int i1 = 0; i1 < l; i1++) {
                int j1 = ai[i1];
                int k1 = ai1[i1];
                ai[i1] = i + (int) ((j1 - i) * RadicalMath.cos(k) - (k1 - j) * RadicalMath.sin(k));
                ai1[i1] = j + (int) ((j1 - i) * RadicalMath.sin(k) + (k1 - j) * RadicalMath.cos(k));
            }

        }
    }

    public static int[] rotSingle(int poly1, int poly2, final int center1, final int center2, final int angle, final float sin_ang, final float cos_ang) {
        if (angle != 0) {
            final int j1 = poly1;
            final int k1 = poly2;
            poly1 = center1 + (int) ((j1 - center1) * cos_ang - (k1 - center2) * sin_ang);
            poly2 = center2 + (int) ((j1 - center1) * sin_ang + (k1 - center2) * cos_ang);
        }
        return new int[] {
                poly1, poly2
        };
    }

    public static void startTimer() {
        HLogger.info("Timer started!");
        startTime = System.nanoTime();
    }

    public static void stopTimer() {
        long endTime = System.nanoTime();
        long finalTime = (endTime - startTime) / 1000000;
        startTime = 0;

        HLogger.info("Timer ended at " + finalTime + " ms");
    }

}

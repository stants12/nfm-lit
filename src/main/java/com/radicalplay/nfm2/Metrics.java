package com.radicalplay.nfm2;
import java.util.Queue;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Tracks various metrics for display when debug mode is available. This is most
 * useful for debugging and development purposes.
 */
public class Metrics {
    private final int numFrameTimeSamples = 50;
    private final Queue<Integer> frameTimes = new LinkedList<Integer>();

    private int lastFrameTime = 0;

    /* controls the look and position of the frametime readout */
    private final int MAX_PERMITTED_FT = 48;
    private final Color FT_BOX_COLOR = new Color(255, 255, 255, 200);
    private final Color FT_READOUT_TEXT_COLOR = new Color(0, 0, 0, 200);
    private final Color FT_READOUT_EXCEED_TEXT_COLOR = new Color(255, 0, 0, 200);
    private final int FT_READOUT_X = 10;
    private final int FT_READOUT_Y = 250;

    private void renderFrameTimeLatest(Graphics2D rd) {
        int percent_ft_used = (int) (((double) lastFrameTime / (double) MAX_PERMITTED_FT) * 100);
        rd.setFont(new Font("SansSerif", 1, 11));
        String out = String.format("Frametime: %d/%d ms (%d%%)", lastFrameTime, MAX_PERMITTED_FT, percent_ft_used);
        FontMetrics metrics = rd.getFontMetrics();
        Rectangle2D b = metrics.getStringBounds(out, rd);
        rd.setColor(FT_BOX_COLOR);
        /* add some padding */
        rd.fillRect(FT_READOUT_X - 5, FT_READOUT_Y - 13, (int) b.getWidth() + 10, (int) b.getHeight() + 5);
        rd.setColor(percent_ft_used >= 100 ? FT_READOUT_EXCEED_TEXT_COLOR : FT_READOUT_TEXT_COLOR);
        rd.drawString(out, FT_READOUT_X, FT_READOUT_Y);
    }

    /* controls the look and potition of frametime graph */
    private final int FT_GRAPH_X_START = 5;
    private final int FT_GRAPH_Y_START = 275;
    private final int FT_GRAPH_WIDTH = 150;
    private final int FT_GRAPH_HEIGHT = 50;
    private final Color FT_GRAPH_BOX_COLOR = new Color(0, 0, 0, 128);
    private final Color FT_GRAPH_LINE_COLOR = new Color(255, 255, 0, 128);

    private void renderFrameTimeGraph(Graphics2D rd) {
        rd.setColor(FT_GRAPH_BOX_COLOR);
        rd.fillRect(FT_GRAPH_X_START, FT_GRAPH_Y_START, FT_GRAPH_WIDTH, FT_GRAPH_HEIGHT);

        /* need to get the scaling right */
        int max_ft = getMaxRecordedFrameTime();
        /*
         * use this to define the scale of the Y axis from 0 to max_ft
         * this is used to determine the exact x,y each recording should be at
         */
        Iterator<Integer> iter = frameTimes.iterator();
        int i = 0;

        int prevSampleX = 0;
        int prevSampleY = 0;
        while (iter.hasNext()) {
            int next = iter.next();
            double x_across = (double) i / (double) numFrameTimeSamples;
            /* offset from the X start of the box */
            int x_off = (int) (x_across * (double) FT_GRAPH_WIDTH);
            int point_x = x_off + FT_GRAPH_X_START;

            /* % height compared to max */
            double y_up = (double) next / (double) max_ft;
            int y_off = FT_GRAPH_HEIGHT - (int)((double) y_up * (double) FT_GRAPH_HEIGHT);
            int point_y = y_off + FT_GRAPH_Y_START;

            rd.setColor(FT_GRAPH_LINE_COLOR);
            /* ignore the initialised values for these */
            if (prevSampleY != 0) {
                rd.drawLine(point_x, point_y, prevSampleX, prevSampleY);
            }

            prevSampleX = point_x;
            prevSampleY = point_y;
            i++;
        }
    }

    private int getMaxRecordedFrameTime() {
        int out = 0;
        Iterator<Integer> l = frameTimes.iterator();

        while (l.hasNext()) {
            int next = l.next();
            if (next > out) {
                out = next;
            }
        }

        return out;
    }

    public void addFrameTimeSample(int frametime) {
        frameTimes.add(frametime);
        if (frameTimes.size() > numFrameTimeSamples) {
            frameTimes.remove();
        }
        lastFrameTime = frametime;
    }

    public void render(Graphics2D rd) {
        renderFrameTimeLatest(rd);
        renderFrameTimeGraph(rd);
    }
}
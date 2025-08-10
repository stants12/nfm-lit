package com.radicalplay.nfm2;

import com.radicalplay.nfm2.gui.SingleComponentAspectRatioKeeperLayout;
import fallk.logmaster.HLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

class RunApp extends Panel {
    private static JFrame frame;
    private static GameSparker applet;
    private static ArrayList<Image> icons;

    /**
     * Fetches icons of 16, 32 and 48 pixels from the 'data' folder.
     *
     * @return icons - ArrayList of icon locations
     */
    private static ArrayList<Image> getIcons() {
        if (icons == null) {
            icons = new ArrayList<>();
            int[] resols = {
                    16, 32, 48
            };
            for (int res : resols) {
                icons.add(Toolkit.getDefaultToolkit().createImage("data/misc/ico_" + res + ".png"));
            }
        }
        return icons;
    }

    public static void main(String[] strings) {
        HLogger.info("Need For Madness NG"); // Change this to the message of your preference
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            HLogger.warn("Could not setup System Look&Feel: " + ex.toString());
        }
        startup();
    }

    private static void startup() {
        frame = new JFrame("Need For Madness NG");// Change this to the name of your preference
        frame.setBackground(new Color(0, 0, 0));
        frame.setIgnoreRepaint(true);
        frame.setIconImages(getIcons());

        applet = new GameSparker();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowevent) {
                exitSequence();
            }
        });
        applet.setPreferredSize(new Dimension(GameFacts.screenWidth, GameFacts.screenHeight));// The resolution of your game goes here

        JPanel wrapperPanel = new JPanel(new SingleComponentAspectRatioKeeperLayout());
        wrapperPanel.add(applet);
        wrapperPanel.setBackground(new Color(0, 0, 0));

        frame.getContentPane().add(wrapperPanel);
        frame.setResizable(true);
        frame.pack();
        frame.setMinimumSize(frame.getSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        applet.init();
        applet.start();
    }

    private static void exitSequence() {
        applet.stop();
        frame.removeAll();
        try {
            Thread.sleep(200L);
        } catch (Exception exception) {
        }
        applet = null;
        System.exit(0);
    }
}

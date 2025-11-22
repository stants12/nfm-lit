package nfm.lit;
import fallk.logmaster.HLogger;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import java.awt.event.KeyEvent;

class RunApp extends Panel {
    /**
     *
     */
    private static final long serialVersionUID = -8590687589434803725L;

    private static Frame frame;
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
        //System.runFinalizersOnExit(true);
        HLogger.info("Need For Madness: LIT"); // Change this to the message of your preference
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            HLogger.warn("Could not setup System Look&Feel: " + ex.toString());
        }
        startup();
    }

    private static void startup() {
        frame = new Frame("Need For Madness: World");// Change this to the name of your preference
        frame.setBackground(new Color(0, 0, 0));
        frame.setIgnoreRepaint(true);
        frame.setIconImages(getIcons());

        applet = new GameSparker();

        applet.setStub(new DesktopStub());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowevent) {
                exitSequence();
            }
        });
        applet.setPreferredSize(new Dimension(GameFacts.screenWidth, GameFacts.screenHeight));// The resolution of your game goes here
        frame.add("Center", applet);
        frame.setResizable(true);// If you plan to make you game support changes in resolution, you can comment out this line.
        frame.pack();
        frame.setMinimumSize(frame.getSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // repaint when frame is resized
        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                applet.repaint();
            }
        });

        // key listener for toggling fullscreen modes, temp
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (e.getKeyCode() == KeyEvent.VK_F11) {
                    toggleFullscreen();
                }
            }
            return false;
        });

        applet.init();
        applet.start();
    }

    private static boolean isFullscreen = false;
    private static boolean isBorderless = false;

    private static void toggleFullscreen() {
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        if (isFullscreen) {
            frame.dispose();
            frame.setUndecorated(false);
            frame.setResizable(true);
            frame.setVisible(true);
            device.setFullScreenWindow(null);
            isFullscreen = false;
            isBorderless = false;
            System.out.println("Windowed mode.");
        } else if (isBorderless) {
            frame.dispose();
            frame.setUndecorated(true);
            frame.setResizable(false);
            frame.setVisible(true);
            device.setFullScreenWindow(frame);
            isFullscreen = true;
            isBorderless = false;
            System.out.println("Fullscreen mode.");
        } else {
            frame.dispose();
            frame.setUndecorated(true);
            frame.setResizable(false);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            frame.setVisible(true);
            isBorderless = true;
            isFullscreen = false;
            System.out.println("Borderless fullscreen mode.");
        }
    }

    public static void exitSequence() {
        applet.stop();
        frame.removeAll();
        try {
            Thread.sleep(200L);
        } catch (Exception exception) {
        }
        applet.destroy();
        applet = null;
        System.exit(0);
    }
}
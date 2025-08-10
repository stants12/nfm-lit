package io.github.uwx;

import com.radicalplay.nfm2.gui.AspectRatioListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public abstract class AppletPolyfill extends JPanel implements AspectRatioListener {
    public AppletPolyfill() {
        Map<Integer, Integer> legacyEventKeyMap = new HashMap<>() {{
            put(KeyEvent.VK_HOME, Event.HOME);
            put(KeyEvent.VK_END, Event.END);
            put(KeyEvent.VK_PAGE_UP, Event.PGUP);
            put(KeyEvent.VK_PAGE_DOWN, Event.PGDN);
            put(KeyEvent.VK_UP, Event.UP);
            put(KeyEvent.VK_DOWN, Event.DOWN);
            put(KeyEvent.VK_LEFT, Event.LEFT);
            put(KeyEvent.VK_RIGHT, Event.RIGHT);
            put(KeyEvent.VK_F1, Event.F1);
            put(KeyEvent.VK_F2, Event.F2);
            put(KeyEvent.VK_F3, Event.F3);
            put(KeyEvent.VK_F4, Event.F4);
            put(KeyEvent.VK_F5, Event.F5);
            put(KeyEvent.VK_F6, Event.F6);
            put(KeyEvent.VK_F7, Event.F7);
            put(KeyEvent.VK_F8, Event.F8);
            put(KeyEvent.VK_PRINTSCREEN, Event.PRINT_SCREEN);
            put(KeyEvent.VK_SCROLL_LOCK, Event.SCROLL_LOCK);
            put(KeyEvent.VK_CAPS_LOCK, Event.CAPS_LOCK);
            put(KeyEvent.VK_NUM_LOCK, Event.NUM_LOCK);
            put(KeyEvent.VK_PAUSE, Event.PAUSE);
            put(KeyEvent.VK_INSERT, Event.INSERT);
            put(KeyEvent.VK_ENTER, Event.ENTER);
            put(KeyEvent.VK_BACK_SPACE, Event.BACK_SPACE);
            put(KeyEvent.VK_TAB, Event.TAB);
            put(KeyEvent.VK_ESCAPE, Event.ESCAPE);
            put(KeyEvent.VK_DELETE, Event.DELETE);
            put(KeyEvent.VK_CANCEL, Event.ESCAPE);

            put(KeyEvent.VK_CLEAR, Event.ESCAPE);
            put(KeyEvent.VK_SHIFT, Event.ESCAPE);
            put(KeyEvent.VK_CONTROL, Event.ESCAPE);
            put(KeyEvent.VK_ALT, Event.ESCAPE);

            put(KeyEvent.VK_EURO_SIGN, 0x20AC);
            put(KeyEvent.VK_SPACE, 0x20);
            put(KeyEvent.VK_EXCLAMATION_MARK, 0x21);
            put(KeyEvent.VK_QUOTEDBL, 0x22);
            put(KeyEvent.VK_NUMBER_SIGN, 0x23);
            put(KeyEvent.VK_DOLLAR, 0x24);
            put(KeyEvent.VK_AMPERSAND, 0x26);
            put(KeyEvent.VK_QUOTE, 0x27);
            put(KeyEvent.VK_LEFT_PARENTHESIS, 0x28);
            put(KeyEvent.VK_RIGHT_PARENTHESIS, 0x29);
            put(KeyEvent.VK_ASTERISK, 0x2A);
            put(KeyEvent.VK_PLUS, 0x2B);
            put(KeyEvent.VK_COMMA, 0x2C);
            put(KeyEvent.VK_MINUS, 0x2D);
            put(KeyEvent.VK_PERIOD, 0x2E);
            put(KeyEvent.VK_SLASH, 0x2F);
            put(KeyEvent.VK_0, 0x30);
            put(KeyEvent.VK_1, 0x31);
            put(KeyEvent.VK_2, 0x32);
            put(KeyEvent.VK_3, 0x33);
            put(KeyEvent.VK_4, 0x34);
            put(KeyEvent.VK_5, 0x35);
            put(KeyEvent.VK_6, 0x36);
            put(KeyEvent.VK_7, 0x37);
            put(KeyEvent.VK_8, 0x38);
            put(KeyEvent.VK_9, 0x39);
            put(KeyEvent.VK_COLON, 0x3A);
            put(KeyEvent.VK_SEMICOLON, 0x3B);
            put(KeyEvent.VK_LESS, 0x3C);
            put(KeyEvent.VK_EQUALS, 0x3D);
            put(KeyEvent.VK_GREATER, 0x3E);
            put(KeyEvent.VK_AT, 0x40);
            put(KeyEvent.VK_A, 0x41);
            put(KeyEvent.VK_B, 0x42);
            put(KeyEvent.VK_C, 0x43);
            put(KeyEvent.VK_D, 0x44);
            put(KeyEvent.VK_E, 0x45);
            put(KeyEvent.VK_F, 0x46);
            put(KeyEvent.VK_G, 0x47);
            put(KeyEvent.VK_H, 0x48);
            put(KeyEvent.VK_I, 0x49);
            put(KeyEvent.VK_J, 0x4A);
            put(KeyEvent.VK_K, 0x4B);
            put(KeyEvent.VK_L, 0x4C);
            put(KeyEvent.VK_M, 0x4D);
            put(KeyEvent.VK_N, 0x4E);
            put(KeyEvent.VK_O, 0x4F);
            put(KeyEvent.VK_P, 0x50);
            put(KeyEvent.VK_Q, 0x51);
            put(KeyEvent.VK_R, 0x52);
            put(KeyEvent.VK_S, 0x53);
            put(KeyEvent.VK_T, 0x54);
            put(KeyEvent.VK_U, 0x55);
            put(KeyEvent.VK_V, 0x56);
            put(KeyEvent.VK_W, 0x57);
            put(KeyEvent.VK_X, 0x58);
            put(KeyEvent.VK_Y, 0x59);
            put(KeyEvent.VK_Z, 0x5A);
            put(KeyEvent.VK_OPEN_BRACKET, 0x5B);
            put(KeyEvent.VK_BACK_SLASH, 0x5C);
            put(KeyEvent.VK_CLOSE_BRACKET, 0x5D);
            put(KeyEvent.VK_CIRCUMFLEX, 0x5E);
            put(KeyEvent.VK_UNDERSCORE, 0x5F);
            put(KeyEvent.VK_BACK_QUOTE, 0x60);
            put(KeyEvent.VK_BRACELEFT, 0x7B);
            put(KeyEvent.VK_BRACERIGHT, 0x7D);
            put(KeyEvent.VK_INVERTED_EXCLAMATION_MARK, 0xA1);
        }};

        Event sharedEvt = new Event(null, 0, null);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseDown(sharedEvt, e.getX(), e.getY());
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseMove(sharedEvt, e.getX(), e.getY());
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (legacyEventKeyMap.containsKey(e.getKeyCode())) {
                    keyDown(sharedEvt, legacyEventKeyMap.get(e.getKeyCode()));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (legacyEventKeyMap.containsKey(e.getKeyCode())) {
                    keyUp(sharedEvt, legacyEventKeyMap.get(e.getKeyCode()));
                }
            }
        });

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                gotFocus(sharedEvt, e.getSource());
            }

            @Override
            public void focusLost(FocusEvent e) {
                lostFocus(sharedEvt, e.getSource());
            }
        });

        setFocusable(true);
        requestFocusInWindow();
    }

    public void stop() {}

    public abstract void start();

    public abstract void init();

    @Override
    public void ratioChanged(double newRatio) {

    }
}

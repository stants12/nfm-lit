package com.radicalplay.nfm2;

import com.radicalplay.nfm2.music.RadicalIBXM;
import com.radicalplay.nfm2.music.RadicalMidi;
import com.radicalplay.nfm2.music.RadicalMp3;
import com.radicalplay.nfm2.music.RadicalMusic;
import fallk.logmaster.HLogger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

class xtGraphics implements Runnable {
    public Metrics gameMetrics = new Metrics();

    SoundManager sm = new SoundManager();
    /**
     * starting colors for the special screen in the credits
     */
    private final int[] credColors = {
            25, 50, 100
    };

    private Graphics2D rd;
    private final GameSparker app;
    public Phase fase;
    private Phase oldfase;
    public int starcnt;
    public int unlocked;
    private int lockcnt;
    public int opselect;
    private boolean shaded;
    public int flipo;
    private boolean nextc;
    private int gatey;
    public int looped;
    public final int[] sc;
    public boolean setnumber;
    private boolean holdit;
    private int holdcnt;
    public boolean winner;
    private final int[] flexpix;
    private final int[] smokey;
    private Image fleximg;
    private int flatrstart;
    private Thread runner;
    private int runtyp;
    private Image odmg;
    private Image opwr;
    private Image opos;
    private Image owas;
    private Image olap;
    private Image oyourwasted;
    private Image oyoulost;
    private Image oyouwon;
    private Image oyouwastedem;
    private Image ogameh;
    private Image oloadingmusic;
    private Image oflaot;
    private Image dmg;
    private Image pwr;
    private Image pos;
    private Image was;
    private Image lap;
    private Image select;
    private Image loadingmusic;
    private Image yourwasted;
    private Image youlost;
    private Image youwon;
    private Image youwastedem;
    private Image gameh;
    private Image congrd;
    private Image gameov;
    private Image carsbg;
    private Image pgate;
    private Image selectcar;
    private Image statb;
    private Image statbo;
    public Image mdness;
    private Image paused;
    private Image radicalplay;
    private Image logocars;
    private Image logomadnes;
    private Image logomadbg;
    private Image byrd;
    private Image opback;
    private Image nfmcoms;
    private Image opti;
    private Image bgmain;
    /* GameSparker.catchlink uses this */
    public Image rpro;
    private Image nfmcom;
    private Image flaot;
    private Image fixhoop;
    private Image sarrow;
    private Image stunts;
    private Image racing;
    private Image wasting;
    private Image plus;
    private Image space;
    private Image arrows;
    private Image chil;
    private Image ory;
    private Image kz;
    private Image kx;
    private Image kv;
    private Image kp;
    private Image km;
    private Image kn;
    private Image kenter;
    private Image nfm;
    private final Image[][] trackbg;
    public final Image[] dude;
    private final Image[] dudeb;
    private int duds;
    private int dudo;
    private final Image[] next;
    private final Image[] back;
    private final Image[] contin;
    private final Image[] ostar;
    private final Image[] star;
    private int pcontin;
    private int pnext;
    private int pback;
    private int pstar;

    private final Image[] ocntdn;
    private final Image[] cntdn;
    private int gocnt;
    private final boolean[] pengs;
    private boolean aird;
    private boolean grrd;
    private boolean pwastd;
    public boolean mutes;
    private RadicalMusic intertrack;
    RadicalMusic strack;
    public boolean loadedt;
    private int lastload;
    private boolean mutem;
    private boolean macn;
    private boolean arrace;
    private int ana;
    private int cntan;
    private int cntovn;
    private boolean flk;
    private int tcnt;
    private boolean tflk;
    private String say;
    private boolean wasay;
    private int clear;
    private int posit;
    private int wasted;
    private int laps;
    private final int[] dested;
    private int dmcnt;
    private boolean dmflk;
    private int pwcnt;
    private boolean pwflk;
    private final String[][] adj = {
            {
                    "Cool", "Alright", "Nice"
            }, {
                    "Wicked", "Amazing", "Super"
            }, {
                    "Awesome", "Ripping", "Radical"
            }, {
                    "What the...?", "Your a super star!!!!", "Who are you again...?"
            }, {
                    "surf style", "off the lip", "bounce back"
            }
    };
    private final String[] exlm = {
            "!", "!!", "!!!"
    };
    private String loop;
    private String spin;
    private String asay;
    private int auscnt;
    private boolean aflk;
    private final int[] sndsize = { //need to add more if you  want more stages :D - Addict
            106, 76, 56, 116, 92, 208, 70, 80, 152, 102, 27, 65, 52, 30, 151, 129, 70, 0
    };
    private final Image hello;
    private final Image sign;
    private final Image loadbar;
    private int kbload;
    public int dnload;
    private float shload;
    private int radpx;
    private int pin;
    private final int[] bgmy = {
            0, GameFacts.screenHeight
    };
    private final int[] trkx = {
            0, GameFacts.screenWidth
    };
    private int trkl;
    private int trklim;
    private final float hipno = 1.0F;
    private int flkat;
    private int movly;

    private int press_enter_to_continue_height = GameFacts.screenHeight - 100;

    /* TODO: define each page's constants in their own files */
    /* main menu */
    private int main_menu_height_origin = (int) (GameFacts.screenHeight * 0.2);

    private int main_menu_button_height = 22;
    private int main_menu_op_0_y = 246 + main_menu_height_origin;
    private int main_menu_op_0_width = 110;
    private int main_menu_op_1_y = 275 + main_menu_height_origin;
    private int main_menu_op_1_width = 196;
    private int main_menu_op_2_y = 306 + main_menu_height_origin;
    private int main_menu_op_2_width = 85;

    private int insano_mainmenu_x;
    private int insano_mainmenu_y;
    /* these exist to reset the head to origin */
    private int insano_mainmenu_x_original;
    private int insano_mainmenu_y_original;

    private int insano_mainmenu_target_x;
    private int insano_mainmenu_target_y;

    /* car select */
    private int car_select_next_back_button_y = (int) (GameFacts.screenHeight * 0.68);
    private int car_select_back_button_x = 30;
    private int car_select_next_button_x = GameFacts.screenWidth - 90;
    private int car_select_continue_button_y = GameFacts.screenHeight - 120;

    /* stage select */
    private int stage_select_next_back_button_y = (int) (GameFacts.screenHeight * 0.5);;
    private int stage_select_back_button_x = car_select_back_button_x;
    private int stage_select_next_button_x = car_select_next_button_x;
    private int stage_select_continue_button_y = GameFacts.screenHeight - 80;
    private int stage_select_text_y = 25;

    /* post-game continue */
    private int post_game_continue_button_y = stage_select_continue_button_y;

    /* pause */
    /* set in loadimages */
    private int pausebox_y = 0;
    private int pausebox_option_gap = 26;
    private int pausebox_option_start_off = 37;
    private int pausebox_op_0_width = 137;
    private int pausebox_op_1_width = 155;
    private int pausebox_op_2_width = 190;
    private int pausebox_op_3_width = 109;
    private int pausebox_op_height = 22;
    /* the options arent quite centered so correct for it */
    private int pausebox_op_x_correction = -3;

    /* credits */
    private int credits_next_button_y = GameFacts.screenHeight - 100;

    /* instructions */
    private int instructions_next_back_button_y = stage_select_next_back_button_y;
    private int instructions_next_button_x = stage_select_next_button_x;
    private int instructions_back_button_x = stage_select_back_button_x;
    private int instructions_continue_button_x = instructions_next_button_x - 5;

    private final int[] pgatx = {
            146, 175, 215, 267, 334, 401, 452, 493, 521
    };
    private final int[] pgaty = {
            168, 188, 201, 212, 219, 214, 203, 189, 171
    };
    private final int[] pgady;
    private final boolean[] pgas;
    private int lxm;
    private int lym;
    private int pwait;
    private int stopcnt;
    private int cntwis;
    private int crshturn;
    private int bfcrash;
    private int bfskid;
    private boolean crashup;
    private boolean skidup;
    private int skflg;
    private int dskflg;
    private int flatr;
    private int flyr;
    private int flyrdest;
    private int flang;
    private int flangados;
    private float blackn;
    private float blacknados;


    static int practicemode = 0;

    private int dev_up = 0;
    private int dev_down = 0;
    private int dev_left = 0;
    private int dev_right = 0;
    public boolean devtriggered = false;

    public boolean fixedsort = false;

    public boolean nplayers_debug = false;
    public int nplayers_override = 7;

    public boolean debugmode = false;

    public int spectate = 0;

    public int nfmmode = 2;

    public boolean arrowDisabled = false;
    public boolean opstatusDisabled = false;

    private int car_select_delay = 3;   // this is the most optimal value, anything less is shit and causes tick sound to go nuts

    /**
     * Filter images
     *
     * @param img  Image to filter
     * @param type Integer of what filter to apply
     * @author Kaffeinated
     */
    public void filterImage(Image img, int type) {
        BufferedImage buff_img = new BufferedImage(img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D rd_sep = buff_img.createGraphics();
        rd_sep.drawImage(img, 0, 0, null);
        rd_sep.dispose();
        // now buff_img = BufferedImage img
        if (type == 0) { //////// grayscale
            BufferedImage gray = new BufferedImage(buff_img.getWidth(), buff_img.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
            op.filter(buff_img, gray);
            rd.drawImage(gray, 0, 0, null);
        }
        if (type == 1) { ///////// sepia tone
            BufferedImage sepia = new BufferedImage(buff_img.getWidth(), buff_img.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            int sepiaDepth = 20;
            int w = buff_img.getWidth();
            int h = buff_img.getHeight();
            int[] pixels = new int[w * h * 4];
            buff_img.getRaster().getPixels(0, 0, w, h, pixels);
            for (int x = 0; x < buff_img.getWidth(); x++) {
                for (int y = 0; y < buff_img.getHeight(); y++) {
                    int rgb = buff_img.getRGB(x, y);
                    Color color = new Color(rgb, true);
                    int r = color.getRed();
                    int g = color.getGreen();
                    int b = color.getBlue();
                    int gry = (r + g + b) / 3;
                    r = g = b = gry;
                    r = r + (sepiaDepth * 2);
                    g = g + sepiaDepth;
                    if (r > 255) {
                        r = 255;
                    }
                    if (g > 255) {
                        g = 255;
                    }
                    if (b > 255) {
                        b = 255;
                    }
                    b -= 20;
                    if (b < 0) {
                        b = 0;
                    }
                    if (b > 255) {
                        b = 255;
                    }
                    color = new Color(r, g, b, color.getAlpha());
                    sepia.setRGB(x, y, color.getRGB());
                }
            }
            rd.drawImage(sepia, 0, 0, null);
        }
        if (type == 2) { /////////// inverts colors
            BufferedImage invert = new BufferedImage(buff_img.getWidth(), buff_img.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            int w = buff_img.getWidth();
            int h = buff_img.getHeight();
            int[] pixels = new int[w * h * 4];
            buff_img.getRaster().getPixels(0, 0, w, h, pixels);
            for (int x = 0; x < buff_img.getWidth(); x++) {
                for (int y = 0; y < buff_img.getHeight(); y++) {
                    int rgb = buff_img.getRGB(x, y);
                    Color color = new Color(rgb, true);
                    int r = color.getRed();
                    int g = color.getGreen();
                    int b = color.getBlue();
                    r = 255 - r;
                    g = 255 - g;
                    b = 255 - b;
                    if (r > 255) {
                        r = 255;
                    }
                    if (r < 0) {
                        r = 0;
                    }
                    if (g > 255) {
                        g = 255;
                    }
                    if (g < 0) {
                        g = 0;
                    }
                    if (b > 255) {
                        b = 255;
                    }
                    if (b < 0) {
                        b = 0;
                    }
                    color = new Color(r, g, b, color.getAlpha());
                    invert.setRGB(x, y, color.getRGB());
                }
            }
            rd.drawImage(invert, 0, 0, null);
        }
        if (type == 3) { /////// alternate invert
            BufferedImage altinvert = new BufferedImage(buff_img.getWidth(), buff_img.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            int w = buff_img.getWidth();
            int h = buff_img.getHeight();
            int[] pixels = new int[w * h * 4];
            buff_img.getRaster().getPixels(0, 0, w, h, pixels);
            for (int x = 0; x < buff_img.getWidth(); x++) {
                for (int y = 0; y < buff_img.getHeight(); y++) {
                    int rgb = buff_img.getRGB(x, y);
                    Color color = new Color(rgb, true);
                    int r = color.getRed();
                    int g = color.getGreen();
                    int b = color.getBlue();
                    r = Medium.csky[0] - r;
                    g = Medium.csky[1] - g;
                    b = Medium.csky[2] - b;
                    if (r > 255) {
                        r = 255;
                    }
                    if (r < 0) {
                        r = 0;
                    }
                    if (g > 255) {
                        g = 255;
                    }
                    if (g < 0) {
                        g = 0;
                    }
                    if (b > 255) {
                        b = 255;
                    }
                    if (b < 0) {
                        b = 0;
                    }
                    color = new Color(r, g, b, color.getAlpha());
                    altinvert.setRGB(x, y, color.getRGB());
                }
            }
            rd.drawImage(altinvert, 0, 0, null);
        }
        if (type == 4) { /////// washout type filter
            BufferedImage washout = new BufferedImage(buff_img.getWidth(), buff_img.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            int w = buff_img.getWidth();
            int h = buff_img.getHeight();
            int[] pixels = new int[w * h * 4];
            buff_img.getRaster().getPixels(0, 0, w, h, pixels);
            for (int x = 0; x < buff_img.getWidth(); x++) {
                for (int y = 0; y < buff_img.getHeight(); y++) {
                    int rgb = buff_img.getRGB(x, y);
                    Color color = new Color(rgb, true);
                    int r = color.getRed();
                    int g = color.getGreen();
                    int b = color.getBlue();
                    int nr = r / 2;
                    int ng = g / 2;
                    r = nr + r;
                    g = ng + g;
                    b = b + (b / 2);
                    if (r > 255) {
                        r = 255;
                    }
                    if (r < 0) {
                        r = 0;
                    }
                    if (g > 255) {
                        g = 255;
                    }
                    if (g < 0) {
                        g = 0;
                    }
                    if (b > 255) {
                        b = 255;
                    }
                    if (b < 0) {
                        b = 0;
                    }
                    color = new Color(r, g, b, color.getAlpha());
                    washout.setRGB(x, y, color.getRGB());
                }
            }
            rd.drawImage(washout, 0, 0, null);
        }
    }

    /**
     * Special color effect in credits
     *
     * @param image the image
     * @return image the same image but filtered with color
     * @author Kaffeinated
     */
    public Image credsnap(Image image) {
        int i = GameFacts.screenHeight; // image.getHeight(null);
        int j = image.getWidth(null);
        int ai[] = new int[j * i];

        if (credColors[0] < 200) {
            credColors[0] += 5;
        } else {
            do {
                credColors[0] -= 5;
            } while (credColors[0] > 25);
        }
        if (credColors[1] < 100) {
            credColors[1] += 5;
        } else {
            do {
                credColors[1] -= 10;
            } while (credColors[1] > 50);
        }
        if (credColors[2] < 30) {
            credColors[2] += 5;
        } else {
            do {
                credColors[2] -= 5;
            } while (credColors[2] > 100);
        }
        PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, j, i, ai, 0, j);
        try {
            pixelgrabber.grabPixels();
        } catch (InterruptedException _ex) {
        }
        for (int k = 0; k < j * i; k++) {
            Color color = new Color(ai[k]);
            int l = (int) (color.getRed() * (credColors[0] / 100F));
            if (l > 225) {
                l = 225;
            }
            if (l < 0) {
                l = 0;
            }
            int i1 = (int) (color.getGreen() * (credColors[1] / 100F));
            if (i1 > 225) {
                i1 = 225;
            }
            if (i1 < 0) {
                i1 = 0;
            }
            int j1 = (int) (color.getBlue() * (credColors[2] / 100F));
            if (j1 > 225) {
                j1 = 225;
            }
            if (j1 < 0) {
                j1 = 0;
            }
            Color color2 = new Color(l, i1, j1, 50); /// last is alpha
            ai[k] = color2.getRGB();
        }
        return app.createImage(new MemoryImageSource(j, i, ai, 0, j));
    }

    public int colorinvert(int r, int g, int b) {
        int hex = (0xff << 24) | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
        return 0xFFFFFF - hex;
    }

    /**
     * drawcs for images
     *
     * @param img image to draw
     * @param y   y value
     * @author Kaffeinated
     */
    public void paintcs(Image img, int y) {
        rd.drawImage(img, (img.getWidth(null) / 2) - GameFacts.screenWidth, y, null);
    }

    /**
     * draw text over conto
     *
     * @param text   text to draw
     * @param contos conto to overlay
     * @author Rafa
     * @author Kaffeinated
     */
    public void drawOver(String text, ContO contos) {
        int x = Medium.cx + (contos.x - Medium.x - Medium.cx);
        int y = Medium.cy + (contos.y - Medium.y - Medium.cy);
        int z = Medium.cz + (contos.z - Medium.z - Medium.cz);

        x = Utility.rotSingle(x, z, Medium.cx, Medium.cz, Medium.xz, RadicalMath.sin(Medium.xz),
                RadicalMath.cos(Medium.xz))[0];
        y = Utility.rotSingle(y, z, Medium.cy, Medium.cz, Medium.zy, RadicalMath.sin(Medium.zy),
                RadicalMath.cos(Medium.zy))[0];
        z = Utility.rotSingle(y, z, Medium.cy, Medium.cz, Medium.zy, RadicalMath.sin(Medium.zy),
                RadicalMath.cos(Medium.zy))[1];

        final int xScreenCoor = Utility.xs(x, z);
        final int yScreenCoor = Utility.cYs(y, z);

        rd.drawString(text, xScreenCoor, yScreenCoor);
    }

    public void carspergame(CheckPoints checkpoints) {
        if (!setnumber) {
            setnumber = true;

            if (!nplayers_debug) {
                switch (checkpoints.stage) {
                    // case 2:
                    // GameFacts.numberOfPlayers = 10;
                    // break;
                    // case 5:
                    // GameFacts.numberOfPlayers = 13;
                    // break;
                    default:
                        GameFacts.numberOfPlayers = 7;
                        break;
                }
            } else {
                GameFacts.numberOfPlayers = nplayers_override;
            }

            if (GameFacts.numberOfPlayers == 1) {
                practicemode = 1;
            } else {
                practicemode = 0;
            }
        } else {
            fase = Phase.STAGESELECTTRIGGER;
        }
    }

    private boolean over(Image image, int i, int j, int k, int l) {
        int i1 = image.getHeight(null);
        int j1 = image.getWidth(null);
        return i > k - 5 && i < k + j1 + 5 && j > l - 5 && j < l + i1 + 5;
    }

    public void cantgo(Control control) {
        pnext = 0;
        trackbg(true);

        rd.setFont(new Font("SansSerif", Font.BOLD, 13));
        FontHandler.fMetrics = rd.getFontMetrics();

        drawcs(400, "This stage will be unlocked when stage " + unlocked + " is complete!", 177, 177, 177, 3);

        int i = 0;
        int pgate_gap = 30;
        int pgate_amt = 9;
        int pgate_start = Utility.centeredWidthX((pgate_amt - 1) * pgate_gap);
        do {
            rd.drawImage(pgate, pgate_start + i * pgate_gap, 190, null);
        } while (++i < pgate_amt);

        rd.setFont(new Font("SansSerif", Font.BOLD, 11));
        FontHandler.fMetrics = rd.getFontMetrics();
        if (aflk) {
            drawcs(300, "[ Stage " + (unlocked + 1) + " Locked ]", 255, 128, 0, 3);
            aflk = false;
        } else {
            drawcs(300, "[ Stage " + (unlocked + 1) + " Locked ]", 255, 0, 0, 3);
            aflk = true;
        }

        rd.drawImage(select, Utility.centeredImageX(select), stage_select_text_y, null);
        rd.drawImage(back[pback], stage_select_back_button_x, stage_select_next_back_button_y, null);

        rd.setFont(new Font("SansSerif", Font.BOLD, 11));
        FontHandler.fMetrics = rd.getFontMetrics();
        drawcs(GameFacts.screenWidth - 5, "You can also use Keyboard Arrows and Enter to navigate.", 82, 90, 0, 3);
        lockcnt--;
        if (lockcnt == 0 || control.enter || control.handb || control.left) {
            sm.play("tick");
            control.left = false;
            control.handb = false;
            control.enter = false;
            fase = Phase.STAGESELECT;
        }
    }

    public void loadingstage(int i) {
        /*
         * 
         * looks better without idc fight me
         * 
         */
        trackbg(false);
        rd.setColor(new Color(177, 177, 177));
        rd.fillRoundRect(200, 150, 270, 52, 20, 40);
        rd.setColor(new Color(120, 120, 120));
        rd.drawRoundRect(200, 150, 270, 52, 20, 40);

        rd.setFont(new Font("SansSerif", Font.BOLD, 13));
        FontHandler.fMetrics = rd.getFontMetrics();
        drawcs(180, "Loading Stage " + i + ", please wait...", 0, 0, 0, 3);
        rd.drawImage(select, Utility.centeredImageX(select), stage_select_text_y, null);

        // rd.drawImage(br, 0, 0, null);
        rd.setFont(new Font("SansSerif", Font.BOLD, 11));
        FontHandler.fMetrics = rd.getFontMetrics();
        drawcs(GameFacts.screenHeight - 5, "You can also use Keyboard Arrows and Enter to navigate.", 82, 90, 0, 3);

        // app.repaint();

        // if (lastload != -22) {
        // lastload = -22;
        // } else {
        // stages.stop();
        // }
    }

    public void menusettings(Control control) {

        app.repaint();

        if (flipo == 0) {
            bgmy[0] = 0;
            bgmy[1] = GameFacts.screenHeight;
            app.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

        rd.setColor(new Color(100, 100, 100));
        rd.fillRect(0, 0, GameFacts.screenWidth, GameFacts.screenHeight);

        rd.setFont(new Font("SansSerif", Font.BOLD, 13));
        FontHandler.fMetrics = rd.getFontMetrics();

        drawcs(20, "Number of Players: " + GameFacts.numberOfPlayers, 255, 255, 255, 3);
        drawcs(40, "Back", 255, 255, 255, 3);

        if (control.up) {
            opselect--;
            if (opselect == -1) {
                opselect = 1;
            }
            control.up = false;
        }
        if (control.down) {
            opselect++;
            if (opselect == 2) {
                opselect = 0;
            }
            control.down = false;
        }
        if (opselect == 0) {
            rd.setFont(new Font("SansSerif", Font.BOLD, 13));
            FontHandler.fMetrics = rd.getFontMetrics();
            if (aflk) {     // this is aids
                drawcs(20, "Number of Players: " + GameFacts.numberOfPlayers, 255, 0, 0, 3);
                rd.setColor(new Color(200, 255, 0));
                aflk = false;
            } else {
                drawcs(20, "Number of Players: " + GameFacts.numberOfPlayers, 0, 0, 0, 3);
                rd.setColor(new Color(255, 128, 0));
                aflk = true;
            }
        }
        if (opselect == 1) {
            if (shaded) {
                rd.setColor(new Color(140, 70, 0));
                rd.fillRect(234, 275, 196, 22);
                aflk = false;
            }
            rd.setFont(new Font("SansSerif", Font.BOLD, 13));
            FontHandler.fMetrics = rd.getFontMetrics();
            if (aflk) {
                drawcs(40, "Back", 255, 0, 0, 3);
                aflk = false;
            } else {
                drawcs(40, "Back", 0, 0, 0, 3);
                aflk = true;
            }
        }

        if (control.enter || control.handb) {
            if (opselect == 1) {
                fase = oldfase;
            }
            control.enter = false;
            control.handb = false;
        }
        if (control.left) {
            if (opselect == 0) {
                GameFacts.numberOfPlayers--;
                if (GameFacts.numberOfPlayers == 0) {
                    GameFacts.numberOfPlayers = 51;
                }
            }
            control.left = false;
        }
        if (control.right) {
            if (opselect == 0) {
                GameFacts.numberOfPlayers++;
                if (GameFacts.numberOfPlayers == 52) {
                    GameFacts.numberOfPlayers = 1;
                }
            }
            control.right = false;
        }
    }

    public void inst(Control control) {
        if (flipo == 0) {
            flipo = 1;
            bgmy[0] = 0;
            bgmy[1] = GameFacts.screenHeight;
        }
        if (flipo == 2) {
            flipo = 3;
            dudo = 200;
        }
        if (flipo == 4) {
            flipo = 5;
            dudo = 250;
        }
        if (flipo == 6) {
            flipo = 7;
            dudo = 200;
        }
        if (flipo == 8) {
            flipo = 9;
            dudo = 250;
        }
        if (flipo == 10) {
            flipo = 11;
            dudo = 200;
        }
        if (flipo == 12) {
            flipo = 13;
            dudo = 200;
        }
        if (flipo == 14) {
            flipo = 15;
            dudo = 100;
        }
        int i = 0;
        do {
            rd.drawImage(bgmain, 0, bgmy[i], null);
            bgmy[i] -= 2;
            if (bgmy[i] <= -GameFacts.screenHeight) {
                bgmy[i] = GameFacts.screenHeight;
            }
        } while (++i < 2);
        aflk = !aflk;
        int hint_all_width = dude[duds].getWidth(null) + oflaot.getWidth(null);
        int start_x = Utility.centeredWidthX(hint_all_width);
        int flaot_x = start_x + dude[duds].getWidth(null);
        int text_x = flaot_x + 70;
        if (flipo != 1) {
            if (dudo > 0) {
                if (aflk) {
                    if (RadicalRand.bool()) {
                        duds = (int) (RadicalRand.random() * 3D);
                    } else {
                        duds = (int) (RadicalRand.random() * 2D);
                    }
                }
                dudo--;
            } else {
                duds = 0;
            }
            rd.drawImage(dude[duds], start_x, 10, null);
            rd.drawImage(oflaot, flaot_x, 42, null);
        }
        rd.setColor(new Color(0, 0, 0));
        rd.setFont(new Font("SansSerif", Font.BOLD, 13));

        int dudetext_base_y = 65;
        int dudetext_line_gap = 20;

        if (flipo == 3 || flipo == 5) {// 2-3 page
            if (flipo == 3) {
                rd.drawString("Hello!  This will be changed eventually.", text_x, dudetext_base_y);

                //
                // rd.drawImage(nfm, 519, 30, null);
                rd.drawString("In this game there are two ways to complete a stage.", text_x,
                        dudetext_base_y + dudetext_line_gap * 2);
                rd.drawString("One is by racing and finishing in first place, the other is by", text_x,
                        dudetext_base_y + dudetext_line_gap * 3);
                rd.drawString("wasting and crashing all the other cars in the stage!", text_x,
                        dudetext_base_y + dudetext_line_gap * 4);
            } else {
                rd.setColor(new Color(100, 100, 100));
                rd.drawString("While racing, you will need to focus on going fast and passing", text_x,
                        dudetext_base_y);
                rd.drawString("through all the checkpoints in the track. To complete a lap, you", text_x,
                        dudetext_base_y + dudetext_line_gap);
                rd.drawString("must not miss a checkpoint.", text_x, dudetext_base_y + dudetext_line_gap * 2);
                rd.drawString("While wasting, you will just need to chase the other cars and", text_x,
                        dudetext_base_y + dudetext_line_gap * 3);
                rd.drawString("crash into them (without worrying about track and checkpoints).", text_x,
                        dudetext_base_y + dudetext_line_gap * 4);
                rd.setColor(new Color(0, 0, 0));
            }

            int middle = Utility.centeredImageX(ory);
            rd.drawImage(racing, middle - racing.getWidth(null) - 30, 200, null);
            rd.drawImage(ory, middle, 250, null);
            rd.drawImage(wasting, middle + ory.getWidth(null) + 30, 200, null);
            rd.setFont(new Font("SansSerif", Font.BOLD, 11));
            rd.drawString("Checkpoint", middle - ory.getWidth(null) - 15, 200);
            rd.setFont(new Font("SansSerif", Font.BOLD, 13));
            rd.drawString("Drive your car using the Arrow Keys and Spacebar :", start_x, 330);
            rd.drawImage(space, start_x + 50, 370, null);
            rd.drawImage(arrows, middle + 120, 340, null);
            rd.setFont(new Font("SansSerif", Font.BOLD, 11));
            rd.drawString("(When your car is on the ground Spacebar is for Handbrake)", start_x, 350);
            rd.drawString("Accelerate", middle + 130, 335);
            rd.drawString("Brake/Reverse", middle + 120, 415);
            rd.drawString("Turn left", middle + 70, 390);
            rd.drawString("Turn right", middle + 210, 390);

        }
        if (flipo == 7 || flipo == 9) { // 4-5 page
            if (flipo == 7) {
                rd.drawString("Whether you are racing or wasting the other cars you will need", text_x,
                        dudetext_base_y);
                rd.drawString("to power up your car.", text_x, dudetext_base_y + dudetext_line_gap);
                rd.drawString("=> More 'Power' makes your car become faster and stronger!", text_x,
                        dudetext_base_y + dudetext_line_gap * 2);
                rd.drawString("To power up your car (and keep it powered up) you will need to", text_x,
                        dudetext_base_y + dudetext_line_gap * 3);
                rd.drawString("perform stunts!", text_x, dudetext_base_y + dudetext_line_gap * 4);

            } else {
                rd.drawString("The better the stunt the more power you get!", text_x, dudetext_base_y);
                rd.setColor(new Color(100, 100, 100));
                rd.drawString("Forward looping pushes your car forwards in the air and helps", text_x,
                        dudetext_base_y + dudetext_line_gap);
                rd.drawString("when racing. Backward looping pushes your car upwards giving it", text_x,
                        dudetext_base_y + dudetext_line_gap * 2);
                rd.drawString("more hang time in the air making it easier to control its landing.", text_x,
                        dudetext_base_y + dudetext_line_gap * 3);
                rd.drawString("Left and right rolls shift your car in the air left and right slightly.", text_x,
                        dudetext_base_y + dudetext_line_gap * 4);

            }
            rd.drawImage(stunts, Utility.centeredImageX(stunts), 200, null);

            rd.setFont(new Font("SansSerif", Font.BOLD, 13));
            rd.drawString("To perform stunts. When your car is in the AIR;", start_x, 330);
            rd.drawString("Press combo Spacebar + Arrow Keys :", start_x, 350);
            rd.drawImage(space, start_x + 50, 370, null);

            /* use same middle as pervious page to keep alignments the same */
            int middle = Utility.centeredImageX(ory);
            rd.drawImage(arrows, middle + 120, 340, null);
            rd.setFont(new Font("SansSerif", Font.BOLD, 11));
            rd.setColor(new Color(0, 0, 0));
            rd.drawString("Forward Loop", middle + 125, 335);
            rd.drawString("Backward Loop", middle + 120, 415);
            rd.drawString("Left Roll", middle + 70, 390);
            rd.drawString("Right Roll", middle + 210, 390);

        }
        if (flipo == 11 || flipo == 13) { // 6-7 page
            if (flipo == 11) {
                rd.drawString("When wasting cars, to help you find the other cars in the stage,", text_x,
                        dudetext_base_y);
                rd.drawString("press [ A ] to toggle the guidance arrow from pointing to the track", text_x,
                        dudetext_base_y + dudetext_line_gap);
                rd.drawString("to pointing to the cars.", text_x, dudetext_base_y + dudetext_line_gap * 2);
                rd.drawString("When your car is damaged. You fix it (and reset its 'Damage') by", text_x,
                        dudetext_base_y + dudetext_line_gap * 3);
                rd.drawString("jumping through the electrified hoop.", text_x, dudetext_base_y + dudetext_line_gap * 4);
            } else {
                rd.setColor(new Color(100, 100, 100));
                rd.drawString("You will find that in some stages it's easier to waste the other cars", text_x,
                        dudetext_base_y);
                rd.drawString("and in some others it's easier to race and finish in first place.", text_x,
                        dudetext_base_y + dudetext_line_gap);
                rd.drawString("It is up to you to decide when to waste and when to race.", text_x,
                        dudetext_base_y + dudetext_line_gap * 2);
                rd.drawString("And remember, 'Power' is an important factor in the game. You", text_x,
                        dudetext_base_y + dudetext_line_gap * 3);
                rd.drawString("will need it whether you are racing or wasting!", text_x,
                        dudetext_base_y + dudetext_line_gap * 4);
                rd.setColor(new Color(0, 0, 0));
            }

            int middle = GameFacts.screenWidth / 2;
            int fixhoop_x = middle - fixhoop.getWidth(null) - 100;
            int guidance_x = middle + 100;

            rd.drawImage(fixhoop, fixhoop_x, 193, null);
            rd.drawImage(sarrow, guidance_x, 203, null);
            rd.setFont(new Font("SansSerif", Font.BOLD, 11));
            rd.drawString("The Electrified Hoop", fixhoop_x + 5, 191);
            rd.drawString("Jumping through it fixes your car.", fixhoop_x - 30, 313);
            rd.drawString("Make guidance arrow point to cars.", guidance_x, 191);
        }
        if (flipo == 15) { // 8
            rd.drawString("There is a total of 17 stages!", text_x, dudetext_base_y);
            rd.drawString("Every two stages completed a new car will be unlocked!", text_x,
                    dudetext_base_y + dudetext_line_gap);
            rd.drawString("I am Coach Insano by the way.", text_x, dudetext_base_y + dudetext_line_gap * 3);
            rd.drawString("I am your coach and narrator in this game!  Good Luck!", text_x,
                    dudetext_base_y + dudetext_line_gap * 4);

            int middle = GameFacts.screenWidth / 2;
            int col1_base_x = middle - 300;
            int col2_base_x = middle + 50;

            drawcs(185, "Other Controls :", 0, 0, 0, 0);

            rd.setFont(new Font("SansSerif", Font.BOLD, 11));
            rd.drawImage(kz, col1_base_x, 200, null);
            rd.drawString("OR", col1_base_x + kz.getWidth(null) + 10, 226);
            rd.drawImage(kx, col1_base_x + 70, 200, null);
            rd.drawString("=> To look behind you while driving.", col1_base_x + 110, 226);
            rd.drawImage(kv, col1_base_x, 250, null);
            rd.drawString("Change Views", col1_base_x + kv.getWidth(null) + 10, 276);
            rd.drawImage(kp, col1_base_x, 300, null);
            rd.drawString("OR", col1_base_x + kp.getWidth(null) + 10, 326);
            rd.drawImage(kenter, col1_base_x + 70, 300, null);
            rd.drawString("Pause Game", col1_base_x + 200, 326);
            rd.drawImage(km, col2_base_x, 200, null);
            rd.drawString("Mute Music", col2_base_x + km.getWidth(null) + 10, 226);
            rd.drawImage(kn, col2_base_x, 250, null);
            rd.drawString("Mute Sound Effects", col2_base_x + kn.getWidth(null) + 10, 276);
        }
        if (flipo == 1) {// first papge
            rd.setFont(new Font("SansSerif", Font.BOLD, 13));
            FontHandler.fMetrics = rd.getFontMetrics();
            drawcs(20, "Main Game Controls", 0, 0, 0, 3);

            String arrkey_txt = "Drive your car using the Arrow Keys:";
            int arrkey_txt_width = FontHandler.fMetrics.stringWidth(arrkey_txt);
            /* relative X for column 1 page 1 */
            int col_1_x_start = Utility.centeredWidthX(arrkey_txt_width) - 150;
            /* same for column 2 */
            int col_2_x_start = Utility.centeredImageX(arrows) + 150;

            rd.drawString(arrkey_txt, col_1_x_start + 20, 55);
            rd.drawString("On the GROUND Spacebar is for Handbrake", col_1_x_start, 76);
            rd.drawImage(space, col_1_x_start + 30, 90, null);
            rd.drawImage(arrows, col_2_x_start, 58, null);
            rd.setFont(new Font("SansSerif", Font.BOLD, 11));
            FontHandler.fMetrics = rd.getFontMetrics();
            rd.drawString("Accelerate", col_2_x_start + 15, 54);
            rd.drawString("Brake/Reverse", col_2_x_start + 5, 132);
            rd.drawString("Turn left", col_2_x_start - 50, 110);
            rd.drawString("Turn right", col_2_x_start + 85, 110);

            drawcs(150, "--------------------------------------------------------------------------------"
                    + "--------------------------------------------------------------------", 0, 0, 0, 3);
            rd.setFont(new Font("SansSerif", Font.BOLD, 13));
            FontHandler.fMetrics = rd.getFontMetrics();
            rd.drawString("To perform stunts:", col_1_x_start, 175);
            rd.drawString("In the AIR press combo Spacebar + Arrow Keys :", col_1_x_start, 195);
            rd.drawImage(space, col_1_x_start + 30, 220, null);

            rd.drawImage(arrows, col_2_x_start, 188, null);
            rd.setFont(new Font("SansSerif", Font.BOLD, 11));
            FontHandler.fMetrics = rd.getFontMetrics();
            rd.setColor(new Color(0, 0, 0));
            rd.drawString("Forward Loop", col_2_x_start + 3, 184);
            rd.drawString("Backward Loop", col_2_x_start + 3, 262);
            rd.drawString("Left Roll", col_2_x_start - 50, 240);
            rd.drawString("Right Roll", col_2_x_start + 85, 240);

            rd.drawImage(stunts, Utility.centeredImageX(stunts), 290, null);
        }
        if (flipo >= 1 && flipo <= 13) {
            rd.drawImage(next[pnext], instructions_next_button_x, instructions_next_back_button_y, null);
        }
        if (flipo >= 3 && flipo <= 15) {
            rd.drawImage(back[pback], instructions_back_button_x, instructions_next_back_button_y, null);
        }
        if (flipo == 15) {
            rd.drawImage(contin[pcontin], instructions_continue_button_x, instructions_next_back_button_y, null);
        }
        if (control.enter || control.right) {
            if (flipo >= 1 && flipo <= 13) {
                flipo++;
            }
            if (control.enter && flipo == 15) {
                flipo = 0;
                fase = oldfase;
                rd.setFont(new Font("SansSerif", Font.BOLD, 11));
                FontHandler.fMetrics = rd.getFontMetrics();
            }
            control.enter = false;
            control.right = false;
        }
        if (control.left) {
            if (flipo >= 3 && flipo <= 15) {
                flipo -= 3;
            }
            control.left = false;
        }
    }

    public void fleximage(Image image, int i, int j) {
        if (i == 0) {
            PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, GameFacts.screenWidth, GameFacts.screenHeight,
                    flexpix, 0, GameFacts.screenWidth);
            try {
                pixelgrabber.grabPixels();
            } catch (InterruptedException _ex) {
            }
        }
        int k = 0;
        int l = 0;
        int i1 = 0;
        int j1 = 0;
        int k1 = (int) (RadicalRand.random() * 128D);
        int l1 = (int) (5D + RadicalRand.random() * 15D);
        int i2 = 0;
        do {
            Color color = new Color(flexpix[i2]);
            int j2 = 0;
            int k2 = 0;
            int l2 = 0;
            if (k == 0) {
                j2 = color.getRed();
                l = j2;
                k2 = color.getGreen();
                i1 = k2;
                l2 = color.getBlue();
                j1 = l2;
            } else {
                j2 = (int) ((color.getRed() + l * 0.38F * i) / (1.0F + 0.38F * i));
                l = j2;
                k2 = (int) ((color.getGreen() + i1 * 0.38F * i) / (1.0F + 0.38F * i));
                i1 = k2;
                l2 = (int) ((color.getBlue() + j1 * 0.38F * i) / (1.0F + 0.38F * i));
                j1 = l2;
            }
            if (++k == GameFacts.screenWidth) {
                k = 0;
            }
            int i3 = (j2 * 17 + k2 + l2 + k1) / 22;
            int j3 = (k2 * 17 + j2 + l2 + k1) / 22;
            int k3 = (l2 * 17 + j2 + k2 + k1) / 22;
            if (j == 17) {
                i3 = (j2 * 17 + k2 + l2 + k1) / 22;
                j3 = (k2 * 17 + j2 + l2 + k1) / 21;
                k3 = (l2 * 17 + j2 + k2 + k1) / 20;
            }
            if (--l1 == 0) {
                k1 = (int) (RadicalRand.random() * 128D);
                l1 = (int) (5D + RadicalRand.random() * 15D);
            }
            Color color1 = new Color(i3, j3, k3);
            flexpix[i2] = color1.getRGB();
        } while (++i2 < flexpix.length);
        fleximg = app.createImage(new MemoryImageSource(GameFacts.screenWidth, GameFacts.screenHeight, flexpix, 0, GameFacts.screenWidth));
        rd.drawImage(fleximg, 0, 0, null);
    }

    private void arrow(int i, int j, CheckPoints checkpoints, ContO[] conto, boolean flag) {
        int[] ai = new int[7];
        int[] ai1 = new int[7];
        int[] ai2 = new int[7];
        /**
         * Base X position
         */
        int c = GameFacts.screenWidth / 2;
        byte byte0 = -90;
        /**
         * Base Y position
         */
        int c1 = GameFacts.screenHeight; // 2BC
        int k = 0;
        do {
            ai1[k] = byte0;
        } while (++k < 7);
        ai[0] = c;
        ai2[0] = c1 + 110;
        ai[1] = c - 35;
        ai2[1] = c1 + 50;
        ai[2] = c - 15;
        ai2[2] = c1 + 50;
        ai[3] = c - 15;
        ai2[3] = c1 - 50;
        ai[4] = c + 15;
        ai2[4] = c1 - 50;
        ai[5] = c + 15;
        ai2[5] = c1 + 50;
        ai[6] = c + 35;
        ai2[6] = c1 + 50;
        k = 0;
        int l = 0;
        if (!flag) {
            char c2 = 0;
            if (checkpoints.x[i] - checkpoints.opx[0] >= 0) {
                c2 = 180;
            }
            k = (int) (90 + c2 + Math.atan((double) (checkpoints.z[i] - checkpoints.opz[0]) / (double) (checkpoints.x[i] - checkpoints.opx[0])) / 0.017453292519943295D);
        } else {
            int k1 = -1;
            boolean flag1 = false;
            int l2 = 1;
            do {
                if ((Utility.py(checkpoints.opx[0] / 100, checkpoints.opx[l2] / 100, checkpoints.opz[0] / 100,
                        checkpoints.opz[l2] / 100) < k1 || k1 == -1) && (!flag1 || checkpoints.onscreen[l2] != 0)
                        && checkpoints.dested[l2] == 0) {
                    l = l2;
                    k1 = Utility.py(checkpoints.opx[0] / 100, checkpoints.opx[l2] / 100, checkpoints.opz[0] / 100,
                            checkpoints.opz[l2] / 100);
                    if (checkpoints.onscreen[l2] != 0) {
                        flag1 = true;
                    }
                }
            } while (++l2 < GameFacts.numberOfPlayers);
            l2 = 0;
            if (checkpoints.opx[l] - checkpoints.opx[0] >= 0) {
                l2 = 180;
            }
            k = (int) (90 + l2 + Math.atan((double) (checkpoints.opz[l] - checkpoints.opz[0])
                    / (double) (checkpoints.opx[l] - checkpoints.opx[0])) / 0.017453292519943295D);
            drawcs(13, "[ " + StatList.names[sc[l]] + " ]", 76, 67, 240, 0);
            /*
             * example use of drawOver
             */
            // drawOver(names[sc[l]], conto[l]);
        }
        k += Medium.xz;
        while (k < 0) {
            k += 360;
        }
        while (k > 180) {
            k -= 360;
        }
        if (!flag) {
            if (k > 130) {
                k = 130;
            }
            if (k < -130) {
                k = -130;
            }
        } else {
            if (k > 100) {
                k = 100;
            }
            if (k < -100) {
                k = -100;
            }
        }
        if (Math.abs(ana - k) < 180) {
            if (Math.abs(ana - k) < 10) {
                ana = k;
            } else if (ana < k) {
                ana += 10;
            } else {
                ana -= 10;
            }
        } else {
            if (k < 0) {
                ana += 15;
                if (ana > 180) {
                    ana -= 360;
                }
            }
            if (k > 0) {
                ana -= 15;
                if (ana < -180) {
                    ana += 360;
                }
            }
        }
        Utility.rot(ai, ai2, c, c1, ana, 7);
        k = Math.abs(ana);
        if (!flag) {
            if (k > 7 || j > 0 || j == -2 || cntan != 0) {
                int i1 = 0;
                do {
                    ai[i1] = Utility.cXs(ai[i1], ai2[i1]);
                    ai1[i1] = Utility.cYs(ai1[i1], ai2[i1]);
                } while (++i1 < 7);
                i1 = (int) (190F + 190F * (Medium.snap[0] / 100F));
                if (i1 > 255) {
                    i1 = 255;
                }
                if (i1 < 0) {
                    i1 = 0;
                }
                int l1 = (int) (255F + 255F * (Medium.snap[1] / 100F));
                if (l1 > 255) {
                    l1 = 255;
                }
                if (l1 < 0) {
                    l1 = 0;
                }
                int j2 = 0;
                if (j <= 0) {
                    if (k <= 45 && j != -2 && cntan == 0) {
                        i1 = (i1 * k + Medium.csky[0] * (45 - k)) / 45;
                        l1 = (l1 * k + Medium.csky[1] * (45 - k)) / 45;
                        j2 = (j2 * k + Medium.csky[2] * (45 - k)) / 45;
                    }
                    if (k >= 90) {
                        int i3 = (int) (255F + 255F * (Medium.snap[0] / 100F));
                        if (i3 > 255) {
                            i3 = 255;
                        }
                        if (i3 < 0) {
                            i3 = 0;
                        }
                        i1 = (i1 * (140 - k) + i3 * (k - 90)) / 50;
                        if (i1 > 255) {
                            i1 = 255;
                        }
                    }
                } else if (flk) {
                    i1 = (int) (255F + 255F * (Medium.snap[0] / 100F));
                    if (i1 > 255) {
                        i1 = 255;
                    }
                    if (i1 < 0) {
                        i1 = 0;
                    }
                    flk = false;
                } else {
                    i1 = (int) (255F + 255F * (Medium.snap[0] / 100F));
                    if (i1 > 255) {
                        i1 = 255;
                    }
                    if (i1 < 0) {
                        i1 = 0;
                    }
                    l1 = (int) (220F + 220F * (Medium.snap[1] / 100F));
                    if (l1 > 255) {
                        l1 = 255;
                    }
                    if (l1 < 0) {
                        l1 = 0;
                    }
                    flk = true;
                }
                rd.setColor(new Color(i1, l1, j2));
                rd.fillPolygon(ai, ai1, 7);
                i1 = (int) (115F + 115F * (Medium.snap[0] / 100F));
                if (i1 > 255) {
                    i1 = 255;
                }
                if (i1 < 0) {
                    i1 = 0;
                }
                l1 = (int) (170F + 170F * (Medium.snap[1] / 100F));
                if (l1 > 255) {
                    l1 = 255;
                }
                if (l1 < 0) {
                    l1 = 0;
                }
                j2 = 0;
                if (j <= 0) {
                    if (k <= 45 && j != -2 && cntan == 0) {
                        i1 = (i1 * k + Medium.csky[0] * (45 - k)) / 45;
                        l1 = (l1 * k + Medium.csky[1] * (45 - k)) / 45;
                        j2 = (j2 * k + Medium.csky[2] * (45 - k)) / 45;
                    }
                } else if (flk) {
                    i1 = (int) (255F + 255F * (Medium.snap[0] / 100F));
                    if (i1 > 255) {
                        i1 = 255;
                    }
                    if (i1 < 0) {
                        i1 = 0;
                    }
                    l1 = 0;
                }
                rd.setColor(new Color(i1, l1, j2));
                rd.drawPolygon(ai, ai1, 7);
            }
        } else {
            int j1 = 0;
            do {
                ai[j1] = Utility.cXs(ai[j1], ai2[j1]);
                ai1[j1] = Utility.cYs(ai1[j1], ai2[j1]);
            } while (++j1 < 7);
            j1 = (int) (159F + 159F * (Medium.snap[0] / 100F));
            if (j1 > 255) {
                j1 = 255;
            }
            if (j1 < 0) {
                j1 = 0;
            }
            int i2 = (int) (207F + 207F * (Medium.snap[1] / 100F));
            if (i2 > 255) {
                i2 = 255;
            }
            if (i2 < 0) {
                i2 = 0;
            }
            int k2 = (int) (255F + 255F * (Medium.snap[2] / 100F));
            if (k2 > 255) {
                k2 = 255;
            }
            if (k2 < 0) {
                k2 = 0;
            }
            rd.setColor(new Color(j1, i2, k2));
            rd.fillPolygon(ai, ai1, 7);
            j1 = (int) (120F + 120F * (Medium.snap[0] / 100F));
            if (j1 > 255) {
                j1 = 255;
            }
            if (j1 < 0) {
                j1 = 0;
            }
            i2 = (int) (114F + 114F * (Medium.snap[1] / 100F));
            if (i2 > 255) {
                i2 = 255;
            }
            if (i2 < 0) {
                i2 = 0;
            }
            k2 = (int) (255F + 255F * (Medium.snap[2] / 100F));
            if (k2 > 255) {
                k2 = 255;
            }
            if (k2 < 0) {
                k2 = 0;
            }
            rd.setColor(new Color(j1, i2, k2));
            rd.drawPolygon(ai, ai1, 7);
        }
    }

    public void levelhigh(int i, int j, int k, int l, int i1) {
        rd.drawImage(gameh, Utility.centeredImageX(gameh), 20, null);
        byte byte0 = 16; ///////////// change this to 1 to relock the game
        char c = '0';
        char c1 = '`';
        if (l < 50) {
            if (aflk) {
                byte0 = 106;
                c = '\260';
                c1 = '\377';
                aflk = false;
            } else {
                aflk = true;
            }
        }
        if (i != 0) {
            if (k == 0) {
                drawcs(60, "You Wasted 'em!", byte0, c, c1, 0);
            } else if (k == 1) {
                drawcs(60, "Close Finish!", byte0, c, c1, 0);
            } else {
                drawcs(60, "Close Finish!  Almost got it!", byte0, c, c1, 0);
            }
        } else if (j == 229) {
            drawcs(60, "Wasted!", byte0, c, c1, 0);
        } else if (i1 > 2) {
            drawcs(60, "Stunts!", byte0, c, c1, 0);
        } else {
            drawcs(60, "Best Stunt!", byte0, c, c1, 0);
        }
        drawcs(press_enter_to_continue_height, "Press  [ Enter ]  to continue", 0, 0, 0, 0);
    }

    public void playsounds(Madness madness, Control control, int i) {
        if (fase == Phase.INGAME && starcnt < 35 && cntwis != 8 && !mutes) {
            boolean flag = control.up && madness.speed > 0.0F || control.down && madness.speed < 10F;
            boolean flag1 = madness.skid == 1 && control.handb
                    || Math.abs(madness.scz[0]
                            - (madness.scz[1] + madness.scz[0] + madness.scz[2] + madness.scz[3]) / 4F) > 1.0F
                    || Math.abs(madness.scx[0]
                            - (madness.scx[1] + madness.scx[0] + madness.scx[2] + madness.scx[3]) / 4F) > 1.0F;
            boolean flag2 = false;
            if (control.up && madness.speed < 10F) {
                flag1 = true;
                flag = true;
                flag2 = true;
            }
            if (flag && madness.mtouch) {
                if (!madness.capsized) {
                    if (!flag1) {
                        if (madness.power != 98F) {
                            if (Math.abs(madness.speed) > 0.0F
                                    && Math.abs(madness.speed) <= madness.stat.swits[0]) {
                                int j = (int) ((3F * Math.abs(madness.speed)) / madness.stat.swits[0]);
                                if (j == 2) {
                                    if (pwait == 0) {
                                        j = 0;
                                    } else {
                                        pwait--;
                                    }
                                } else {
                                    pwait = 7;
                                }
                                sparkeng(madness.stat, j);
                            }
                            if (Math.abs(madness.speed) > madness.stat.swits[0]
                                    && Math.abs(madness.speed) <= madness.stat.swits[1]) {
                                int k = (int) ((3F * (Math.abs(madness.speed) - madness.stat.swits[0]))
                                        / (madness.stat.swits[1] - madness.stat.swits[0]));
                                if (k == 2) {
                                    if (pwait == 0) {
                                        k = 0;
                                    } else {
                                        pwait--;
                                    }
                                } else {
                                    pwait = 7;
                                }
                                sparkeng(madness.stat, k);
                            }
                            if (Math.abs(madness.speed) > madness.stat.swits[1]
                                    && Math.abs(madness.speed) <= madness.stat.swits[2]) {
                                int l = (int) ((3F * (Math.abs(madness.speed) - madness.stat.swits[1]))
                                        / (madness.stat.swits[2] - madness.stat.swits[1]));
                                sparkeng(madness.stat, l);
                            }
                        } else {
                            byte byte0 = 2;
                            if (pwait == 0) {
                                if (Math.abs(madness.speed) > madness.stat.swits[1]) {
                                    byte0 = 3;
                                }
                            } else {
                                pwait--;
                            }
                            sparkeng(madness.stat, byte0);
                        }
                    } else {
                        sparkeng(madness.stat, -1);
                        if (flag2) {
                            if (stopcnt <= 0) {
                                sm.loop("air" + 5);
                                stopcnt = 10;
                            }
                        } else if (stopcnt <= -2) {
                            sm.loop("air" + (2 + (int) (Medium.random() * 3F)));
                            stopcnt = 7;
                        }
                    }
                } else {
                    sparkeng(madness.stat, 3);
                }
                grrd = false;
                aird = false;
            } else {
                pwait = 15;
                if (!madness.mtouch && !grrd && Medium.random() > 0.5) {
                    sm.loop("air" + (int) (Medium.random() * 4F));
                    stopcnt = 5;
                    grrd = true;
                }
                if (!madness.wtouch && !aird) {
                    stopairs();
                    sm.loop("air" + (int) (Medium.random() * 4F));
                    stopcnt = 10;
                    aird = true;
                }
                sparkeng(madness.stat, -1);
            }
            if (madness.cntdest != 0 && cntwis < 7) {
                if (!pwastd) {
                    sm.loop("wasted");
                    pwastd = true;
                }
            } else {
                if (pwastd) {
                    sm.play("wasted");
                    pwastd = false;
                }
                if (cntwis == 7 && !mutes) {
                    sm.stop("wasted");
                    sm.play("firewasted");
                }
            }
        } else {
            sparkeng(madness.stat, -2);
            if (pwastd) {
                sm.stop("wasted");
                pwastd = false;
            }
        }
        if (stopcnt != -20) {
            if (stopcnt == 1) {
                stopairs();
            }
            stopcnt--;
        }
        if (bfcrash != 0) {
            bfcrash--;
        }
        if (bfskid != 0) {
            bfskid--;
        }
        if (madness.newcar) {
            cntwis = 0;
        }
        if (fase == Phase.INGAME || fase == Phase.PREGAME || fase == Phase.INSTANTREPLAY
                || fase == Phase.CAUGHTHIGHLIGHT || fase == Phase.GAMEHIGHLIGHT || fase == Phase.POSTGAMEFADEOUT
                || fase == Phase.POSTGAME) {
            if (mutes != Control.mutes) {
                mutes = Control.mutes;
            }
            if (Control.mutem != mutem) {
                mutem = Control.mutem;
                if (mutem) {
                    if (loadedt) {
                        strack.stop();
                    }
                } else if (loadedt) {
                    strack.resume();
                }
            }
        }
        if (madness.cntdest != 0 && cntwis < 7) {
            if (madness.dest) {
                cntwis++;
            }
        } else {
            if (madness.cntdest == 0) {
                cntwis = 0;
            }
            if (cntwis == 7) {
                cntwis = 8;
            }
        }
    }

    public void crash(float f, int i) {
        if (bfcrash == 0) {
            if (i == 0) {
                if (Math.abs(f) > 25F && Math.abs(f) < 170F) {
                    if (!mutes) {
                        sm.play("lowcrash" + crshturn);
                    }
                    bfcrash = 2;
                }
                if (Math.abs(f) >= 170F) {
                    if (!mutes) {
                        sm.play("crash" + crshturn);
                    }
                    bfcrash = 2;
                }
                if (Math.abs(f) > 25F) {
                    if (crashup) {
                        crshturn--;
                    } else {
                        crshturn++;
                    }
                    if (crshturn == -1) {
                        crshturn = 2;
                    }
                    if (crshturn == 3) {
                        crshturn = 0;
                    }
                }
            }
            if (i == -1) {
                if (Math.abs(f) > 25F && Math.abs(f) < 170F) {
                    if (!mutes) {
                        sm.play("lowcrash" + 2);
                    }
                    bfcrash = 2;
                }
                if (Math.abs(f) > 170F) {
                    if (!mutes) {
                        sm.play("crash" + 2);
                    }
                    bfcrash = 2;
                }
            }
            if (i == 1) {
                if (!mutes) {
                    sm.play("tires");
                }
                bfcrash = 3;
            }
        }
    }

    public void replyn() {
        if (aflk) {
            drawcs(30, "Replay  > ", 0, 0, 0, 0);
            aflk = false;
        } else {
            drawcs(30, "Replay  >>", 0, 128, 255, 0);
            aflk = true;
        }
    }

    private Image pressed(Image image) {
        int i = image.getHeight(null);
        int j = image.getWidth(null);
        int[] ai = new int[j * i];
        PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, j, i, ai, 0, j);
        try {
            pixelgrabber.grabPixels();
        } catch (InterruptedException _ex) {
        }
        for (int k = 0; k < j * i; k++) {
            if (ai[k] != ai[j * i - 1]) {
                ai[k] = 0xff000000;
            }
        }

        return app.createImage(new MemoryImageSource(j, i, ai, 0, j));
    }

    private Image dodgen(Image image) {
        int i = image.getHeight(null);
        int j = image.getWidth(null);
        int[] ai = new int[j * i];
        PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, j, i, ai, 0, j);
        try {
            pixelgrabber.grabPixels();
        } catch (InterruptedException _ex) {
        }
        for (int k = 0; k < j * i; k++) {
            Color color = new Color(ai[k]);
            int l = color.getRed() * 3 + 90;
            if (l > 255) {
                l = 255;
            }
            if (l < 0) {
                l = 0;
            }
            int i1 = color.getGreen() * 3 + 90;
            if (i1 > 255) {
                i1 = 255;
            }
            if (i1 < 0) {
                i1 = 0;
            }
            int j1 = color.getBlue() * 3 + 90;
            if (j1 > 255) {
                j1 = 255;
            }
            if (j1 < 0) {
                j1 = 0;
            }
            Color color1 = new Color(l, i1, j1);
            ai[k] = color1.getRGB();
        }

        return app.createImage(new MemoryImageSource(j, i, ai, 0, j));
    }

    private void smokeypix(byte[] abyte0) {
        Image image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(abyte0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, 466, 202, smokey, 0, 466);
        try {
            pixelgrabber.grabPixels();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void stoploading() {
        loading();
        app.repaint();
        runner = null;
        runtyp = 0;
    }

    public void nofocus() {
        rd.setColor(new Color(255, 255, 255));
        rd.fillRect(0, 0, GameFacts.screenWidth, 20);
        rd.fillRect(0, 0, 20, GameFacts.screenHeight);
        rd.fillRect(0, GameFacts.screenHeight - 20, GameFacts.screenWidth, 20);
        rd.fillRect(GameFacts.screenWidth - 20, 0, 20, GameFacts.screenHeight);
        rd.setColor(new Color(192, 192, 192));
        rd.drawRect(20, 20, GameFacts.screenWidth - 40, GameFacts.screenHeight - 40);
        rd.setColor(new Color(0, 0, 0));
        rd.drawRect(22, 22, GameFacts.screenWidth - 44, GameFacts.screenHeight - 44);
        rd.setFont(new Font("SansSerif", Font.BOLD, 11));
        FontHandler.fMetrics = rd.getFontMetrics();
        drawcs(14, "Game lost its focus.   Click screen with mouse to continue.", 100, 100, 100, 3);
        drawcs(GameFacts.screenHeight - 5, "Game lost its focus.   Click screen with mouse to continue.", 100, 100, 100,
                3);
    }

    private boolean overon(int i, int j, int k, int l, int i1, int j1) {
        return i1 > i && i1 < i + k && j1 > j && j1 < j + l;
    }

    public void pauseimage(Image image) {
        PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, GameFacts.screenWidth, GameFacts.screenHeight,
                flexpix, 0, GameFacts.screenWidth);
        try {
            pixelgrabber.grabPixels();
        } catch (InterruptedException _ex) {
        }
        int k = 0;
        int l = 0;
        int px_index = 0;
        do {
            Color color = new Color(flexpix[px_index]);
            int j1 = 0;
            if (l == 0) {
                j1 = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                k = j1;
            } else {
                j1 = (color.getRed() + color.getGreen() + color.getBlue() + k * 30) / 33;
                k = j1;
            }
            if (++l == 900) {
                l = 0;
            }
            Color color1 = new Color(j1, j1, j1);
            flexpix[px_index] = color1.getRGB();
        } while (++px_index < GameFacts.screenHeight * GameFacts.screenWidth);
        fleximg = app.createImage(new MemoryImageSource(GameFacts.screenWidth, GameFacts.screenHeight, flexpix, 0,
                GameFacts.screenWidth));
        rd.drawImage(fleximg, 0, 0, null);
        Medium.flex = 0;
    }

    public void loadstrack(final int i) {
        // ACV: Large music files should not be in zip files, so why
        // not load/read them directly? Checks if the specific file
        // format exists, then load the music according to stage number.
        // I haven't applied these changes to all other tracks yet.

        // thank you for your service acv
        String path = "data/music/" + GameSparker.stageSubDir + "stage" + i;

        if (CheckPoints.customTrack) {
            path = "data/music/custom/" + CheckPoints.trackname;
            HLogger.info(path);
        }

        strack = loadMusicFile(path);

        loadedt = true;
    }

    private RadicalMusic loadMusicFile(String fileWithoutExtension) {
        Map<String, Function<byte[], RadicalMusic>> formats = new HashMap<>() {{
            put("mod", RadicalIBXM::new);
            put("s3m", RadicalIBXM::new);
            put("xm", RadicalIBXM::new);
            put("mp3", RadicalMp3::new);
            put("mid", RadicalMidi::new);
        }};

        FileFromZip fileFromZip = null;

        try {
            if (new File(fileWithoutExtension + ".zip").exists()) {
                fileFromZip = loadFirstFileFromZip(fileWithoutExtension + ".zip");
            }
            if (new File(fileWithoutExtension + ".zipo").exists()) {
                fileFromZip = loadFirstFileFromZip(fileWithoutExtension + ".zipo");
            }
            if (new File(fileWithoutExtension + ".radq").exists()) {
                fileFromZip = loadFirstFileFromZip(fileWithoutExtension + ".radq");
            }

            if (fileFromZip == null) {
                for (String format : formats.keySet()) {
                    if (new File(fileWithoutExtension + "." + format).exists()) {
                        fileFromZip = new FileFromZip(
                                Files.readAllBytes(Paths.get(fileWithoutExtension + "." + format)),
                                "dummy." + format
                        );
                        break;
                    }
                }
            }

            if (fileFromZip == null) {
                throw new RuntimeException("No file found");
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        String extension = fileFromZip.name.substring(fileFromZip.name.lastIndexOf(".") + 1).toLowerCase(Locale.ROOT);
        return formats.get(extension).apply(fileFromZip.data);
    }

    private FileFromZip loadFirstFileFromZip(String zipFile) throws IOException {
        ZipInputStream zipinputstream = new ZipInputStream(new FileInputStream(zipFile));
        ZipEntry zipentry = zipinputstream.getNextEntry();
        int size = (int) zipentry.getSize();
        byte[] modf = new byte[size];
        int offset = 0;
        for (int bytesRead; size > 0; size -= bytesRead) {
            bytesRead = zipinputstream.read(modf, offset, size);
            offset += bytesRead;
        }
        return new FileFromZip(modf, zipentry.getName());
    }

    record FileFromZip(byte[] data, String name) {}

    public void loadmusic(int i, int j) {
        hipnoload(i, false);
        app.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        app.repaint();

        boolean flag = false;

        if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 7 || i == 8 || i == 9 || i == 10 || i == 12
                || i == 13 || i == 16) {
            flag = true;
        }
        if (flag) {
            runtyp = i;
            runner = new Thread(this);
            runner.start();
        }
        loadstrack(i);
        /// aaaaaaaa

        if (flag) {
            runner = null;
            runtyp = 0;
        }
        System.gc();

        strack.play();
        /*
         * if (loadedt && mutem)
         * strack.setPaused(true);
         */

        app.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        fase = Phase.PREGAME;
        pcontin = 0;
        mutem = false;
        mutes = false;
    }

    /**
     * Loads images from images.radq
     *
     * @author Omar Wally
     */
    private void loadimages() {
        dnload += 12;
        int howManyImages = 0;

        try {
            ZipInputStream zipinputstream = new ZipInputStream(new FileInputStream("data/images.radq"));
            for (ZipEntry zipentry = zipinputstream.getNextEntry(); zipentry != null; zipentry = zipinputstream.getNextEntry()) {
                int i = (int) zipentry.getSize();
                String s = zipentry.getName();
                byte[] abyte0 = new byte[i];
                int j = 0;
                int k;
                for (; i > 0; i -= k) {
                    k = zipinputstream.read(abyte0, j, i);
                    j += k;
                }
                if ("cars.gif".equals(s)) {
                    carsbg = loadimage(abyte0);
                }
                if ("smokey.gif".equals(s)) {
                    smokeypix(abyte0);
                }
                if ("gameh.gif".equals(s)) {
                    ogameh = loadimage(abyte0);
                }
                if ("gameov.gif".equals(s)) {
                    gameov = loadimage(abyte0);
                }
                if ("lap.gif".equals(s)) {
                    olap = loadimage(abyte0);
                }
                if ("paused.gif".equals(s)) {
                    paused = loadimage(abyte0);
                    pausebox_y = Utility.centeredImageY(paused) / 4;
                }
                if ("select.gif".equals(s)) {
                    select = loadimage(abyte0);
                }
                if ("yourwasted.gif".equals(s)) {
                    oyourwasted = loadimage(abyte0);
                }
                if ("youwastedem.gif".equals(s)) {
                    oyouwastedem = loadimage(abyte0);
                }
                if ("d1.gif".equals(s)) {
                    dude[0] = loadimage(abyte0);
                    /* setup initial position since it's dynamic */
                    insano_mainmenu_x = ((GameFacts.screenWidth / 2) - dude[0].getWidth(null) / 2) + 10;
                    insano_mainmenu_x_original = insano_mainmenu_x;
                    insano_mainmenu_y = 2 + main_menu_height_origin;
                    insano_mainmenu_y_original = insano_mainmenu_y;
                }
                if ("d2.gif".equals(s)) {
                    dude[1] = loadimage(abyte0);
                }
                if ("d3.gif".equals(s)) {
                    dude[2] = loadimage(abyte0);
                }
                if ("float.gif".equals(s)) {
                    oflaot = loadimage(abyte0);
                }
                if ("1c.gif".equals(s)) {
                    ocntdn[1] = loadimage(abyte0);
                }
                if ("2c.gif".equals(s)) {
                    ocntdn[2] = loadimage(abyte0);
                }
                if ("3c.gif".equals(s)) {
                    ocntdn[3] = loadimage(abyte0);
                }
                if ("bgmain.jpg".equals(s)) {
                    bgmain = loadimage(abyte0);
                }
                if ("loadingmusic.gif".equals(s)) {
                    oloadingmusic = loadimage(abyte0);
                }
                if ("radicalplay.gif".equals(s)) {
                    radicalplay = loadimage(abyte0);
                }
                if ("back.gif".equals(s)) {
                    back[0] = loadimage(abyte0);
                    back[1] = bressed(back[0]);
                }
                if ("continue2.gif".equals(s)) {
                    contin[0] = loadimage(abyte0);
                    contin[1] = bressed(contin[0]);
                }
                if ("next.gif".equals(s)) {
                    next[0] = loadimage(abyte0);
                    next[1] = bressed(next[0]);
                }
                if ("pgate.gif".equals(s)) {
                    pgate = loadimage(abyte0);
                }
                if ("rpro.gif".equals(s)) {
                    rpro = loadimage(abyte0);
                }
                if ("selectcar.gif".equals(s)) {
                    selectcar = loadimage(abyte0);
                }
                if ("track1.jpg".equals(s)) {
                    trackbg[0][0] = loadimage(abyte0);
                    trackbg[1][0] = dodgen(trackbg[0][0]);
                }
                if ("track2.jpg".equals(s)) {
                    trackbg[0][1] = loadimage(abyte0);
                    trackbg[1][1] = dodgen(trackbg[0][1]);
                }
                if ("youlost.gif".equals(s)) {
                    oyoulost = loadimage(abyte0);
                }
                if ("youwon.gif".equals(s)) {
                    oyouwon = loadimage(abyte0);
                }
                if ("0c.gif".equals(s)) {
                    ocntdn[0] = loadimage(abyte0);
                }
                if ("damage.gif".equals(s)) {
                    odmg = loadimage(abyte0);
                }
                if ("power.gif".equals(s)) {
                    opwr = loadimage(abyte0);
                }
                if ("position.gif".equals(s)) {
                    opos = loadimage(abyte0);
                }
                if ("wasted.gif".equals(s)) {
                    owas = loadimage(abyte0);
                }
                if ("start1.gif".equals(s)) {
                    ostar[0] = loadimage(abyte0);
                }
                if ("start2.gif".equals(s)) {
                    ostar[1] = loadimage(abyte0);
                    star[2] = pressed(ostar[1]);
                }
                if ("congrad.gif".equals(s)) {
                    congrd = loadimage(abyte0);
                }
                if ("statb.gif".equals(s)) {
                    statb = loadimage(abyte0);
                }
                if ("statbo.gif".equals(s)) {
                    statbo = loadimage(abyte0);
                }
                if ("madness.gif".equals(s)) {
                    mdness = loadimage(abyte0);
                }
                if ("fixhoop.gif".equals(s)) {
                    fixhoop = loadimage(abyte0);
                }
                if ("arrow.gif".equals(s)) {
                    sarrow = loadimage(abyte0);
                }
                if ("stunts.gif".equals(s)) {
                    stunts = loadimage(abyte0);
                }
                if ("racing.gif".equals(s)) {
                    racing = loadimage(abyte0);
                }
                if ("wasting.gif".equals(s)) {
                    wasting = loadimage(abyte0);
                }
                if ("plus.gif".equals(s)) {
                    plus = loadimage(abyte0);
                }
                if ("space.gif".equals(s)) {
                    space = loadimage(abyte0);
                }
                if ("arrows.gif".equals(s)) {
                    arrows = loadimage(abyte0);
                }
                if ("chil.gif".equals(s)) {
                    chil = loadimage(abyte0);
                }
                if ("ory.gif".equals(s)) {
                    ory = loadimage(abyte0);
                }
                if ("kz.gif".equals(s)) {
                    kz = loadimage(abyte0);
                }
                if ("kx.gif".equals(s)) {
                    kx = loadimage(abyte0);
                }
                if ("kv.gif".equals(s)) {
                    kv = loadimage(abyte0);
                }
                if ("kp.gif".equals(s)) {
                    kp = loadimage(abyte0);
                }
                if ("km.gif".equals(s)) {
                    km = loadimage(abyte0);
                }
                if ("kn.gif".equals(s)) {
                    kn = loadimage(abyte0);
                }
                if ("kenter.gif".equals(s)) {
                    kenter = loadimage(abyte0);
                }
                if ("nfm.gif".equals(s)) {
                    nfm = loadimage(abyte0);
                }
                if ("options.gif".equals(s)) {
                    opti = loadimage(abyte0);
                }
                if ("opback.gif".equals(s)) {
                    opback = loadimage(abyte0);
                }
                if ("logocars.gif".equals(s)) {
                    logocars = loadimage(abyte0);
                }
                if ("logomadmess.gif".equals(s)) {
                    logomadnes = loadimage(abyte0);
                }
                if ("logomadbg.gif".equals(s)) {
                    logomadbg = loadimage(abyte0);
                }
                if ("byrd.gif".equals(s)) {
                    byrd = loadimage(abyte0);
                }
                if ("nfmcoms.gif".equals(s)) {
                    nfmcoms = loadimage(abyte0);
                }
                if ("nfmcom.gif".equals(s)) {
                    nfmcom = loadimage(abyte0);
                }
                howManyImages++;
                dnload += 3;
            }
            zipinputstream.close();
            HLogger.info("Images loaded: " + howManyImages);
        } catch (IOException e) {
            HLogger.error("Error Reading Images: " + e);
            e.printStackTrace();
        }
        System.gc();
    }

    public void pausedgame(int i, Control control, Record record) {
        rd.drawImage(fleximg, 0, 0, null);
        /* add the blue lines behind the pause box */
        int current_line_y = pausebox_y + 5;
        rd.setColor(new Color(60, 135, 220, 128));
        for (; current_line_y < pausebox_y + paused.getHeight(null) - 5; current_line_y += 4) {
            rd.drawLine(Utility.centeredImageX(paused), current_line_y,
                    Utility.centeredImageX(paused) + paused.getWidth(null), current_line_y);
        }

        if (control.up) {
            opselect--;
            if (opselect == -1) {
                opselect = 3;
            }
            control.up = false;
        }
        if (control.down) {
            opselect++;
            if (opselect == 4) {
                opselect = 0;
            }
            control.down = false;
        }
        if (opselect == 0) {
            rd.setColor(new Color(64, 143, 223));
            rd.fillRoundRect(Utility.centeredWidthX(pausebox_op_0_width) + pausebox_op_x_correction,
                    pausebox_y + pausebox_option_start_off,
                    pausebox_op_0_width, pausebox_op_height, 7, 20);
            if (shaded) {
                rd.setColor(new Color(225, 200, 255));
            } else {
                rd.setColor(new Color(0, 89, 223));
            }
            rd.drawRoundRect(Utility.centeredWidthX(pausebox_op_0_width) + pausebox_op_x_correction,
                    pausebox_y + pausebox_option_start_off,
                    pausebox_op_0_width, pausebox_op_height, 7, 20);
        }
        if (opselect == 1) {
            rd.setColor(new Color(64, 143, 223));
            rd.fillRoundRect(Utility.centeredWidthX(pausebox_op_1_width) + pausebox_op_x_correction,
                    pausebox_y + pausebox_option_start_off + pausebox_option_gap,
                    pausebox_op_1_width, pausebox_op_height, 7, 20);
            if (shaded) {
                rd.setColor(new Color(225, 200, 255));
            } else {
                rd.setColor(new Color(0, 89, 223));
            }
            rd.drawRoundRect(Utility.centeredWidthX(pausebox_op_1_width) + pausebox_op_x_correction,
                    pausebox_y + pausebox_option_start_off + pausebox_option_gap,
                    pausebox_op_1_width, pausebox_op_height, 7, 20);
        }
        if (opselect == 2) {
            rd.setColor(new Color(64, 143, 223));
            rd.fillRoundRect(Utility.centeredWidthX(pausebox_op_2_width) + pausebox_op_x_correction,
                    pausebox_y + pausebox_option_start_off + pausebox_option_gap * 2,
                    pausebox_op_2_width, pausebox_op_height, 7, 20);
            if (shaded) {
                rd.setColor(new Color(225, 200, 255));
            } else {
                rd.setColor(new Color(0, 89, 223));
            }
            rd.drawRoundRect(Utility.centeredWidthX(pausebox_op_2_width) + pausebox_op_x_correction,
                    pausebox_y + pausebox_option_start_off + pausebox_option_gap * 2,
                    pausebox_op_2_width, pausebox_op_height, 7, 20);
        }
        if (opselect == 3) {
            rd.setColor(new Color(64, 143, 223));
            rd.fillRoundRect(Utility.centeredWidthX(pausebox_op_3_width) + pausebox_op_x_correction,
                    pausebox_y + pausebox_option_start_off + pausebox_option_gap * 3,
                    pausebox_op_3_width, pausebox_op_height, 7, 20);
            if (shaded) {
                rd.setColor(new Color(225, 200, 255));
            } else {
                rd.setColor(new Color(0, 89, 223));
            }
            rd.drawRoundRect(Utility.centeredWidthX(pausebox_op_3_width) + pausebox_op_x_correction,
                    pausebox_y + pausebox_option_start_off + pausebox_option_gap * 3,
                    pausebox_op_3_width, pausebox_op_height, 7, 20);
        }
        rd.drawImage(paused, Utility.centeredImageX(paused), pausebox_y, null);
        if (control.enter || control.handb) {
            if (opselect == 0) {
                if (!mutem) {
                    if (loadedt) {
                        strack.resume();
                    }
                }
                fase = Phase.INGAME;
            }
            if (opselect == 1) {
                if (record.caught >= 300) {
                    if (loadedt) {
                        strack.resume();
                    }
                    fase = Phase.INSTANTREPLAY;
                } else {
                    fase = Phase.NOTENOUGHREPLAYDATA;
                }
            }
            if (opselect == 2) {
                if (loadedt) {
                    strack.stop();
                }
                oldfase = Phase.PAUSEMENU;
                fase = Phase.INSTRUCTIONS;
            }
            if (opselect == 3) {
                if (loadedt) {
                    strack.stop();
                    strack.unload();
                }
                fase = Phase.MAINMENU;
                opselect = 0;
            }
            control.enter = false;
            control.handb = false;
        }
    }

    public void credits(Control control) {
        if (flipo == 0) {
            sm.play("powerup");
            rad(0);
            flipo = 1;
            bgmy[0] = 0;
            bgmy[1] = GameFacts.screenHeight;
        }
        if (flipo >= 1 && flipo <= 100) {
            rad(flipo);
            flipo++;
            if (flipo == 100) {
                flipo = 1;
            }
        }
        if (flipo == 101) {
            int i = 0;
            do {
                rd.drawImage(bgmain, 0, bgmy[i], null);
                bgmy[i] -= 10;
                if (bgmy[i] <= -GameFacts.screenHeight) {
                    bgmy[i] = GameFacts.screenHeight;
                }
            } while (++i < 2);
            rd.drawImage(mdness, Utility.centeredImageX(mdness), 7, null);
            rd.setFont(new Font("SansSerif", Font.BOLD, 13));
            FontHandler.fMetrics = rd.getFontMetrics();
            drawcs(65, "At Radicalplay.com", 0, 0, 0, 3);
            drawcs(100, "Cartoon 3D Engine, Game Programming, 3D Models, Graphics and Sound Effects", 0, 0, 0, 3);
            drawcs(120, "Everything By Omar Waly", 70, 70, 70, 3);
            rd.setFont(new Font("SansSerif", Font.BOLD, 13));
            FontHandler.fMetrics = rd.getFontMetrics();
            drawcs(180, "Thanks for Game Testing", 0, 0, 0, 3);
            rd.setFont(new Font("SansSerif", Font.BOLD, 11));
            FontHandler.fMetrics = rd.getFontMetrics();
            drawcs(200, "Soufy H Abutaleb, Sherif Abouzeid,", 90, 90, 90, 3);
            drawcs(215, "Kareem Mansour, Youssef Wahby,", 90, 90, 90, 3);
            drawcs(230, "Taymour Farid, Mahmoud Waly", 90, 90, 90, 3);
            drawcs(245, "and Mahmoud Ezzeldin (Turbo)", 90, 90, 90, 3);
            rd.setFont(new Font("SansSerif", Font.BOLD, 13));
            FontHandler.fMetrics = rd.getFontMetrics();
            drawcs(340, "Music was obtained from the ModArchive.org", 0, 0, 0, 3);
            rd.setFont(new Font("SansSerif", Font.BOLD, 11));
            FontHandler.fMetrics = rd.getFontMetrics();
            drawcs(360, "All tracks where remixed to fit game by Omar Waly", 90, 90, 90, 3);
            drawcs(380, "For more details about the music: http://www.radicalplay.com/madcars/music.html", 90, 90, 90,
                    3);
        }
        if (flipo == 102) {
            int i = 0;
            do {
                rd.drawImage(bgmain, 0, bgmy[i], null);
                bgmy[i] -= 10;
                if (bgmy[i] <= -GameFacts.screenHeight) {
                    bgmy[i] = GameFacts.screenHeight;
                }
            } while (++i < 2);
            rd.setFont(new Font("SansSerif", Font.BOLD, 13));
            FontHandler.fMetrics = rd.getFontMetrics();
            //////////////////////////////////////////////////////////////////////
            drawcs(100, "These files have been edited and enhanced by Kaffeinated", 0, 0, 0, 3);

            drawcs(180, "Thanks for help and suggestions goes to", 0, 0, 0, 3);
            rd.setFont(new Font("SansSerif", Font.BOLD, 11));
            FontHandler.fMetrics = rd.getFontMetrics();
            drawcs(200, "Chaotic for help with NFMM graphics", 90, 90, 90, 3);
            drawcs(215, "DragShot for his opponent status and desktop hack", 90, 90, 90, 3);
            drawcs(230, "Ten Graves, Phyrexian, and Hyde233 for bug fixes", 90, 90, 90, 3);

            //////////////////////////////////////////////////////////////////////
        }
        if (flipo == 103) {
            int j = 0;
            do {
                rd.drawImage(bgmain, 0, bgmy[j], null);
                bgmy[j] -= 16;
                if (bgmy[j] <= -GameFacts.screenHeight) {
                    bgmy[j] = GameFacts.screenHeight;
                }
            } while (++j < 2);
            rd.drawImage(nfmcom, Utility.centeredImageX(nfmcom), 170, null);
        }

        rd.drawImage(next[pnext], 600, credits_next_button_y, null);

        if (control.enter || control.handb || control.right) {
            if (flipo >= 1 && flipo <= 100) {
                flipo = 101;
                app.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            } else {
                flipo++;
            }
            if (flipo == 104) {
                flipo = 0;
                fase = Phase.MAINMENU;
            }
            control.enter = false;
            control.handb = false;
            control.right = false;
        }
    }

    public void stat(Madness[] madness, CheckPoints checkpoints, Control control, ContO[] conto, boolean flag) {
        if (holdit) {
            holdcnt++;
            if (Medium.flex != 0) {
                Medium.flex = 0;
            }
            if (control.enter || holdcnt > 250) {
                fase = Phase.CAUGHTHIGHLIGHT;
                control.enter = false;
            }
        } else {
            if (holdcnt != 0) {
                holdcnt = 0;
            }
            if (control.enter) {
                if (loadedt) {
                    strack.stop();
                }
                fase = Phase.PAUSETRIGGER;
                control.enter = false;
            }
        }
        if (fase != Phase.CAUGHTHIGHLIGHT) {
            holdit = false;
            if (checkpoints.wasted == GameFacts.numberOfPlayers - 1 && practicemode == 0) {
                if (Medium.flex != 2) {
                    rd.setColor(new Color(Medium.csky[0], Medium.csky[1], Medium.csky[2]));
                    rd.fillRect(Utility.centeredImageX(youwastedem), 70, youwastedem.getWidth(null),
                            youwastedem.getHeight(null));
                    rd.setColor(new Color(Medium.cfade[0], Medium.cfade[1], Medium.cfade[2]));
                    rd.drawRect(Utility.centeredImageX(youwastedem), 70, youwastedem.getWidth(null),
                            youwastedem.getHeight(null));
                }
                rd.drawImage(youwastedem, Utility.centeredImageX(youwastedem), 70, null);
                if (aflk) {
                    drawcs(120, "You won, all cars have been wasted!", 0, 0, 0, 0);
                    aflk = false;
                } else {
                    drawcs(120, "You won, all cars have been wasted!", 0, 128, 255, 0);
                    aflk = true;
                }
                drawcs(press_enter_to_continue_height, "Press  [ Enter ]  to continue", 0, 0, 0, 0);
                checkpoints.haltall = true;
                holdit = true;
                winner = true;
            }
            if (!holdit && madness[0].dest && cntwis == 8) {
                if (Medium.flex != 2) {
                    rd.setColor(new Color(Medium.csky[0], Medium.csky[1], Medium.csky[2]));
                    rd.fillRect(Utility.centeredImageX(yourwasted), 70, yourwasted.getWidth(null),
                            yourwasted.getHeight(null));
                    rd.setColor(new Color(Medium.cfade[0], Medium.cfade[1], Medium.cfade[2]));
                    rd.drawRect(Utility.centeredImageX(yourwasted), 70, yourwasted.getWidth(null),
                            yourwasted.getHeight(null));
                }
                rd.drawImage(yourwasted, Utility.centeredImageX(yourwasted), 70, null);
                drawcs(press_enter_to_continue_height, "Press  [ Enter ]  to continue", 0, 0, 0, 0);
                holdit = true;
                winner = false;
            }
            if (!holdit) {
                int i = 0;
                do {
                    if (checkpoints.clear[i] == checkpoints.nlaps * checkpoints.nsp && checkpoints.pos[i] == 0) {
                        if (i == 0) {
                            if (Medium.flex != 2) {
                                rd.setColor(new Color(Medium.csky[0], Medium.csky[1], Medium.csky[2]));
                                rd.fillRect(Utility.centeredImageX(youwon), 70, youwon.getWidth(null),
                                        youwon.getHeight(null));
                                rd.setColor(new Color(Medium.cfade[0], Medium.cfade[1], Medium.cfade[2]));
                                rd.drawRect(Utility.centeredImageX(youwon), 70, youwon.getWidth(null),
                                        youwon.getHeight(null));
                            }
                            rd.drawImage(youwon, Utility.centeredImageX(youwon), 70, null);
                            if (aflk) {
                                drawcs(120, "You finished first, nice job!", 0, 0, 0, 0);
                                aflk = false;
                            } else {
                                drawcs(120, "You finished first, nice job!", 0, 128, 255, 0);
                                aflk = true;
                            }
                            winner = true;
                        } else {
                            if (Medium.flex != 2) {
                                rd.setColor(new Color(Medium.csky[0], Medium.csky[1], Medium.csky[2]));
                                rd.fillRect(Utility.centeredImageX(youlost), 70, youlost.getWidth(null),
                                        youlost.getHeight(null));
                                rd.setColor(new Color(Medium.cfade[0], Medium.cfade[1], Medium.cfade[2]));
                                rd.drawRect(Utility.centeredImageX(youlost), 70, youlost.getWidth(null),
                                        youlost.getHeight(null));
                            }
                            rd.drawImage(youlost, Utility.centeredImageX(youlost), 70, null);
                            if (aflk) {
                                drawcs(120, StatList.names[sc[i]] + " finished first, race over!", 0, 0, 0, 0);
                                aflk = false;
                            } else {
                                drawcs(120, StatList.names[sc[i]] + " finished first, race over!", 0, 128, 255, 0);
                                aflk = true;
                            }
                            winner = false;
                        }
                        drawcs(press_enter_to_continue_height, "Press  [ Enter ]  to continue", 0, 0, 0, 0);
                        checkpoints.haltall = true;
                        holdit = true;
                    }
                } while (++i < GameFacts.numberOfPlayers);
            }
            if (flag) {
                if (!arrowDisabled) {
                    if (checkpoints.stage != 110 && arrace != control.arrace) {
                        arrace = control.arrace;
                        if (arrace) {
                            wasay = true;
                            say = " Arrow now pointing at  Cars  <";
                            tcnt = -5;
                        }
                        if (!arrace) {
                            wasay = false;
                            say = " Arrow now pointing at  Track  <";
                            tcnt = -5;
                            cntan = 20;
                        }
                    }
                }
                if (!holdit && fase != Phase.PAUSETRIGGER && starcnt == 0) {
                    rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    if (!arrowDisabled) {
                        arrow(madness[spectate].point, madness[spectate].missedcp, checkpoints, conto, arrace);
                    }

                    if (!opstatusDisabled) {
                        int num_cars = GameFacts.numberOfPlayers;
                        for (int array_one = 0; array_one < num_cars; array_one++) {
                            boolean flag_status = false;
                            for (int array_two = 0; array_two < num_cars; array_two++) {
                                if (checkpoints.pos[array_two] == array_one && checkpoints.dested[array_two] == 0
                                        && !flag_status) {
                                    int y_value = 30; // use to move status up or down
                                    int x_value = GameFacts.screenWidth - 700; // use to move status left or right

                                    rd.setColor(new Color(0, 0, 100));
                                    if (array_one == 0)
                                        rd.drawString("1st", 543 + x_value, 76 + 30 + y_value * array_one);
                                    if (array_one == 1)
                                        rd.drawString("2nd", 541 + x_value, 76 + 30 + y_value * array_one);
                                    if (array_one == 2)
                                        rd.drawString("3rd", 541 + x_value, 76 + 30 + y_value * array_one);
                                    if (array_one >= 3)
                                        rd.drawString((array_one + 1) + "th", 541 + x_value, 76 + y_value + 30 * array_one);
                                    rd.setColor(new Color(0, 0, 0));
                                    rd.drawString(StatList.names[sc[array_two]],
                                            600 - ((FontHandler.fMetrics.stringWidth(StatList.names[sc[array_two]])) / 2) + x_value,
                                            70 + y_value + 30 * array_one);
                                    if (madness[0].im == array_two) {
                                        int red = (int) (159.0F + (159.0F * ((float) Medium.snap[0] / 100.0F)));
                                        if (red > 255)
                                            red = 255;
                                        if (red < 0)
                                            red = 0;
                                        int green = (int) (207.0F + (207.0F * ((float) Medium.snap[1] / 100.0F)));
                                        if (green > 255)
                                            green = 255;
                                        if (green < 0)
                                            green = 0;
                                        int blue = (int) (255.0F + (255.0F * ((float) Medium.snap[2] / 100.0F)));
                                        if (blue > 255)
                                            blue = 255;
                                        if (blue < 0)
                                            blue = 0;
                                        rd.setColor(new Color(red, green, blue));
                                        rd.drawRect(531 + x_value, 58 + y_value + 30 * array_one, 114, 25);
                                        rd.drawRect(532 + x_value, 59 + y_value + 30 * array_one, 112, 23);
                                    }
                                    if (arrace) {
                                        int dmg = (int) (60F * ((float) madness[array_two].hitmag
                                                / (float) madness[array_two].stat.maxmag));

                                        int red = 244;
                                        int green = 244;
                                        int blue = 11;

                                        if (dmg > 20)
                                            green = (int) (244F - 233F * ((float) (dmg - 20) / 40F));

                                        red = (int) ((float) red + (float) red * ((float) Medium.snap[0] / 100F));
                                        if (dmg > 60)
                                            dmg = 60;
                                        if (red > 255)
                                            red = 255;
                                        if (red < 0)
                                            red = 0;

                                        green = (int) ((float) green + (float) green * ((float) Medium.snap[1] / 100F));
                                        if (green > 255)
                                            green = 255;
                                        if (green < 0)
                                            green = 0;

                                        blue = (int) ((float) blue + (float) blue * ((float) Medium.snap[2] / 100F));
                                        if (blue > 255)
                                            blue = 255;
                                        if (blue < 0)
                                            blue = 0;

                                        rd.setColor(new Color(red, green, blue));
                                        rd.fillRect(565 + x_value, 75 + y_value + 30 * array_one, dmg, 5);
                                    } else {
                                        int pwr = (int) (60F * (madness[array_two].power / 98F));
                                        if (pwr > 98) {
                                            pwr = 98;
                                        }
                                        rd.setColor(new Color(45, 150, 255));
                                        rd.fillRect(565 + x_value, 75 + y_value + 30 * array_one, pwr, 5);
                                    }
                                    rd.setColor(new Color(0, 0, 0));
                                    rd.drawRect(565 + x_value, 75 + y_value + 30 * array_one, 60, 5);

                                    flag_status = true;
                                }
                            }
                        }
                    }
                    rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                    if (!arrace && auscnt == 45 && madness[0].capcnt == 0) {
                        if (madness[0].missedcp > 0) {
                            if (madness[0].missedcp > 15 && madness[0].missedcp < 50) {
                                if (flk) {
                                    drawcs(70, "Checkpoint Missed!", 255, 0, 0, 0);
                                } else {
                                    drawcs(70, "Checkpoint Missed!", 255, 150, 0, 2);
                                }
                            }
                            madness[0].missedcp++;
                            if (madness[0].missedcp == 70) {
                                madness[0].missedcp = -2;
                            }
                        } else if (madness[0].mtouch && cntovn < 70) {
                            if (Math.abs(ana) > 100) {
                                cntan++;
                            } else if (cntan != 0) {
                                cntan--;
                            }
                            if (cntan > 40) {
                                cntovn++;
                                cntan = 40;
                                if (flk) {
                                    drawcs(70, "Wrong Way!", 255, 150, 0, 0);
                                    flk = false;
                                } else {
                                    drawcs(70, "Wrong Way!", 255, 0, 0, 2);
                                    flk = true;
                                }
                            }
                        }
                    }
                }

                if (Medium.flex != 2 || Medium.flex == 2) {
                    int bar_x = GameFacts.screenWidth - dmg.getWidth(null) - 50;

                    rd.drawImage(dmg, bar_x, 7, null);
                    rd.drawImage(pwr, bar_x, 27, null);
                    rd.drawImage(lap, 19, 7, null);
                    rd.setColor(new Color(0, 0, 100));
                    rd.drawString((madness[spectate].nlaps + 1) + " / " + checkpoints.nlaps, 51, 18);
                    rd.drawImage(was, 92, 7, null);
                    rd.setColor(new Color(0, 0, 100));
                    rd.drawString(checkpoints.wasted + " / " + (GameFacts.numberOfPlayers - 1), 150, 18);
                    rd.drawImage(pos, 42, 27, null);
                    rd.setFont(new Font("SansSerif", Font.BOLD, 15));
                    FontHandler.fMetrics = rd.getFontMetrics();
                    String suffix = "";
                    int position = checkpoints.pos[0] + 1;
                    if ((position - 1) % 10 == 0 && position != 11) {
                        suffix = "st";
                    }
                    if ((position - 2) % 10 == 0 && position != 12) {
                        suffix = "nd";
                    }
                    if ((position - 3) % 10 == 0 && position != 13) {
                        suffix = "rd";
                    }
                    if (position % 10 == 0 || position % 10 >= 4 || position == 11 || position == 12
                            || position == 13) {
                        suffix = "th";
                    }
                    rd.drawString(position + suffix, 110, 43);
                    rd.setFont(new Font("SansSerif", Font.BOLD, 11));
                    FontHandler.fMetrics = rd.getFontMetrics();
                    drawstat(madness[spectate].stat.maxmag, madness[spectate].hitmag, madness[spectate].newcar,
                            madness[spectate].power, bar_x);

                    if (GameSparker.DEBUG) {
                        if (debugmode) {
                            rd.setColor(new Color(0, 0, 0));
                            rd.drawString("pcleared: " + madness[spectate].pcleared, 20, 80);
                            rd.drawString("checkpoints.clear[" + spectate + "]: " + checkpoints.clear[spectate], 20,
                                    95);
                            rd.drawString("X: " + conto[spectate].x, 20, 400);
                            rd.drawString("Y: " + conto[spectate].y, 20, 420);
                            rd.drawString("Z: " + conto[spectate].z, 20, 440);
                        }
                    }
                }
            }
            if (!holdit) {
                if (starcnt != 0 && starcnt <= 35) {
                    if (starcnt == 35 && !mutes) {
                        sm.play("three");
                    }
                    if (starcnt == 24) {
                        gocnt = 2;
                        if (!mutes) {
                            sm.play("two");
                        }
                    }
                    if (starcnt == 13) {
                        gocnt = 1;
                        if (!mutes) {
                            sm.play("one");
                        }
                    }
                    if (starcnt == 2) {
                        gocnt = 0;
                        if (!mutes) {
                            sm.play("go");
                        }
                    }
                    duds = 0;
                    if (starcnt <= 37 && starcnt > 32) {
                        duds = 1;
                    }
                    if (starcnt <= 26 && starcnt > 21) {
                        duds = 1;
                    }
                    if (starcnt <= 15 && starcnt > 10) {
                        duds = 1;
                    }
                    if (starcnt <= 4) {
                        duds = 2;
                        Medium.flex = 0;
                    }
                    if (dudo != -1) {
                        rd.drawImage(dudeb[duds], Utility.centeredImageX(dudeb[duds]) - 50, 0, null);
                    }
                    if (gocnt != 0) {
                        rd.drawImage(cntdn[gocnt], Utility.centeredImageX(dudeb[duds]) + 50, 50, null);
                    } else {
                        rd.drawImage(cntdn[gocnt], Utility.centeredImageX(dudeb[duds]) + 60, 50, null);
                    }
                }
                if (looped != 0 && madness[0].loop == 2) {
                    looped = 0;
                }
                if (madness[0].power < 45F) {
                    if (tcnt == 30 && auscnt == 45 && madness[0].mtouch && madness[0].capcnt == 0) {
                        if (looped != 2) {
                            if (pwcnt < 70 || pwcnt < 160 && looped != 0) {
                                if (pwflk) {
                                    drawcs(110, "Power low, perform stunt!", 0, 0, 200, 0);
                                    pwflk = false;
                                } else {
                                    drawcs(110, "Power low, perform stunt!", 255, 100, 0, 0);
                                    pwflk = true;
                                }
                            }
                        } else if (pwcnt < 250) {
                            if (pwflk) {
                                drawcs(105, "> >  Press Enter for GAME INSTRUCTIONS!  < <", 0, 0, 200, 0);
                                drawcs(120, "To learn how to preform STUNTS!", 0, 0, 200, 0);
                                pwflk = false;
                            } else {
                                drawcs(105, "> >  Press Enter for GAME INSTRUCTIONS!  < <", 255, 100, 0, 0);
                                drawcs(120, "To learn how to preform STUNTS!", 255, 100, 0, 0);
                                pwflk = true;
                            }
                        }
                        pwcnt++;
                        if (pwcnt == 300) {
                            pwcnt = 0;
                            if (looped != 0) {
                                looped++;
                                if (looped == 3) {
                                    looped = 1;
                                }
                            }
                        }
                    }
                } else if (pwcnt != 0) {
                    pwcnt = 0;
                }
                if (madness[0].capcnt == 0) {
                    if (tcnt < 30) {
                        if (tflk) {
                            if (!wasay) {
                                drawcs(105, say, 0, 0, 0, 0);
                            } else {
                                drawcs(105, say, 0, 0, 0, 0);
                            }
                            tflk = false;
                        } else {
                            if (!wasay) {
                                drawcs(105, say, 0, 128, 255, 0);
                            } else {
                                drawcs(105, say, 255, 128, 0, 0);
                            }
                            tflk = true;
                        }
                        tcnt++;
                    } else if (wasay) {
                        wasay = false;
                    }
                    if (auscnt < 45) {
                        if (aflk) {
                            drawcs(85, asay, 98, 176, 255, 0);
                            aflk = false;
                        } else {
                            drawcs(85, asay, 0, 128, 255, 0);
                            aflk = true;
                        }
                        auscnt++;
                    }
                } else if (tflk) {
                    drawcs(110, "Bad Landing!", 0, 0, 200, 0);
                    tflk = false;
                } else {
                    drawcs(110, "Bad Landing!", 255, 100, 0, 0);
                    tflk = true;
                }
                if (madness[0].trcnt == 10) {
                    loop = "";
                    spin = "";
                    asay = "";
                    int j;
                    for (j = 0; madness[0].travzy > 225; j++) {
                        madness[0].travzy -= 360;
                    }

                    while (madness[0].travzy < -225) {
                        madness[0].travzy += 360;
                        j--;
                    }
                    if (j == 1) {
                        loop = "Forward loop";
                    }
                    if (j == 2) {
                        loop = "double Forward";
                    }
                    if (j == 3) {
                        loop = "triple Forward";
                    }
                    if (j >= 4) {
                        loop = "massive Forward looping";
                    }
                    if (j == -1) {
                        loop = "Backloop";
                    }
                    if (j == -2) {
                        loop = "double Back";
                    }
                    if (j == -3) {
                        loop = "triple Back";
                    }
                    if (j <= -4) {
                        loop = "massive Back looping";
                    }
                    if (j == 0) {
                        if (madness[0].ftab && madness[0].btab) {
                            loop = "Tabletop and reversed Tabletop";
                        } else if (madness[0].ftab || madness[0].btab) {
                            loop = "Tabletop";
                        }
                    }
                    if (j > 0 && madness[0].btab) {
                        loop = "Hanged " + loop;
                    }
                    if (j < 0 && madness[0].ftab) {
                        loop = "Hanged " + loop;
                    }
                    if (!("").equals(loop)) {
                        asay += " " + loop;
                    }
                    j = 0;
                    for (madness[0].travxy = Math.abs(madness[0].travxy); madness[0].travxy > 270;) {
                        madness[0].travxy -= 360;
                        j++;
                    }

                    if (j == 0 && madness[0].rtab) {
                        if (("").equals(loop)) {
                            spin = "Tabletop";
                        } else {
                            spin = "Flipside";
                        }
                    }
                    if (j == 1) {
                        spin = "Rollspin";
                    }
                    if (j == 2) {
                        spin = "double Rollspin";
                    }
                    if (j == 3) {
                        spin = "triple Rollspin";
                    }
                    if (j >= 4) {
                        spin = "massive Roll spinning";
                    }
                    j = 0;
                    boolean flag1 = false;
                    for (madness[0].travxz = Math.abs(madness[0].travxz); madness[0].travxz > 90;) {
                        madness[0].travxz -= 180;
                        if ((j += 180) > GameFacts.screenWidth) {
                            j = GameFacts.screenWidth;
                            flag1 = true;
                        }
                    }

                    if (j != 0) {
                        if (("").equals(loop) && ("").equals(spin)) {
                            asay += " " + j;
                            if (flag1) {
                                asay += " and beyond";
                            }
                        } else {
                            if (!("").equals(loop)) {
                                if (("").equals(loop)) {
                                    asay += " " + spin;
                                } else {
                                    asay += " with " + spin;
                                }
                            }
                            asay += " by " + j;
                            if (flag1) {
                                asay += " and beyond";
                            }
                        }
                    } else if (!("").equals(spin)) {
                        if (("").equals(loop)) {
                            asay += " " + spin;
                        } else {
                            asay += " by " + spin;
                        }
                    }
                    if (!("").equals(asay)) {
                        auscnt -= 15;
                    }
                    if (!("").equals(loop)) {
                        auscnt -= 25;
                    }
                    if (!("").equals(spin)) {
                        auscnt -= 25;
                    }
                    if (j != 0) {
                        auscnt -= 25;
                    }
                    if (auscnt < 45) {
                        if (!mutes) {
                            sm.play("powerup");
                        }
                        if (auscnt < -20) {
                            auscnt = -20;
                        }
                        byte byte0 = 0;
                        if (madness[0].powerup > 20F) {
                            byte0 = 1;
                        }
                        if (madness[0].powerup > 40F) {
                            byte0 = 2;
                        }
                        if (madness[0].powerup > 150F) {
                            byte0 = 3;
                        }
                        if (madness[0].surfer) {
                            asay = " " + adj[4][(int) (Medium.random() * 3F)] + asay;
                        }
                        if (byte0 != 3) {
                            asay = adj[byte0][(int) (Medium.random() * 3F)] + asay + exlm[byte0];
                        } else {
                            asay = adj[byte0][(int) (Medium.random() * 3F)];
                        }
                        if (!wasay) {
                            tcnt = auscnt;
                            if (madness[0].power != 98F) {
                                say = "Power Up " + (int) ((100F * madness[0].powerup) / 98F) + "%";
                            } else {
                                say = "Power To The MAX";
                            }
                            skidup = !skidup;
                        }
                    }
                }
                if (madness[0].newcar) {
                    if (!wasay) {
                        say = "Car Fixed";
                        tcnt = 0;
                    }
                    crashup = !crashup;
                }
                if (clear != madness[0].clear && madness[0].clear != 0) {
                    if (!wasay) {
                        say = "Checkpoint!";
                        tcnt = 15;
                    }
                    clear = madness[0].clear;
                    if (!mutes) {
                        sm.play("checkpoint");
                    }
                    cntovn = 0;
                    if (cntan != 0) {
                        cntan = 0;
                    }
                }
                int k = 1;
                do {
                    if (dested[k] != checkpoints.dested[k]) {
                        dested[k] = checkpoints.dested[k];
                        if (dested[k] == 1) {
                            wasay = true;
                            say = StatList.names[sc[k]] + " has been wasted!";
                            tcnt = -15;
                        }
                        if (dested[k] == 2) {
                            wasay = true;
                            say = "You wasted " + StatList.names[sc[k]] + "!";
                            tcnt = -15;
                        }
                    }
                } while (++k < GameFacts.numberOfPlayers);
            }
        }
        if (Medium.lightn != -1 && checkpoints.stage == 16) {
            // supposed to pulse during sections of the music, w/e
            Medium.lton = true;
        }
    }

    public void finish(CheckPoints checkpoints, ContO[] aconto, Control control) {
        rd.drawImage(fleximg, 0, 0, null);
        if (winner) {
            if (checkpoints.stage == unlocked) {
                if (checkpoints.stage != GameFacts.numberOfStages) {
                    rd.drawImage(congrd, Utility.centeredImageX(congrd), 30, null);
                    drawcs(80, "Stage " + checkpoints.stage + " Completed!", 170, 170, 170, 3);
                } else {
                    rd.drawImage(congrd, Utility.centeredImageX(congrd) + (int) (Medium.random() * 10F), 30, null);
                }
                byte byte0 = 0;
                int i = 0;
                pin = 60;
                if (checkpoints.stage == 2) {
                    byte0 = 8;
                    i = 265;
                    pin = 0;
                    sc[0] = 8;
                }
                if (checkpoints.stage == 4) {
                    byte0 = 9;
                    i = 240;
                    pin = 0;
                    sc[0] = 9;
                }
                if (checkpoints.stage == 6) {
                    byte0 = 10;
                    i = 290;
                    pin = 0;
                    sc[0] = 10;
                }
                if (checkpoints.stage == 8) {
                    byte0 = 11;
                    i = 226;
                    pin = 0;
                    sc[0] = 11;
                }
                if (checkpoints.stage == 10) {
                    byte0 = 12;
                    i = 200;
                    pin = 0;
                    sc[0] = 12;
                }
                if (checkpoints.stage == 12) {
                    byte0 = 13;
                    i = 200;
                    pin = 0;
                    sc[0] = 13;
                }
                if (checkpoints.stage == 14) {
                    byte0 = 14;
                    i = 270;
                    pin = 0;
                    sc[0] = 14;
                }
                if (checkpoints.stage == 16) {
                    byte0 = 15;
                    i = 290;
                    pin = 0;
                    sc[0] = 15;
                }
                if (checkpoints.stage != GameFacts.numberOfStages) {
                    rd.setFont(new Font("SansSerif", Font.BOLD, 13));
                    FontHandler.fMetrics = rd.getFontMetrics();
                    int carUnlockedBoxHeight = 125;
                    if (aflk) {
                        drawcs(Utility.centeredHeightY(carUnlockedBoxHeight) - 40 + pin,
                                "Stage " + (checkpoints.stage + 1) + " is now unlocked!", 176, 196, 0, 3);
                    } else {
                        drawcs(Utility.centeredHeightY(carUnlockedBoxHeight) - 40 + pin,
                                "Stage " + (checkpoints.stage + 1) + " is now unlocked!", 247, 255, 165, 3);
                    }
                    if (byte0 != 0) {
                        if (aflk) {
                            drawcs(Utility.centeredHeightY(carUnlockedBoxHeight) - 20, "And:", 176, 196, 0, 3);
                        } else {
                            drawcs(Utility.centeredHeightY(carUnlockedBoxHeight) - 20, "And:", 247, 255, 165, 3);
                        }
                        rd.setColor(new Color(236, 226, 202));
                        float f = (float) RadicalRand.random();
                        if (f < 0.69999999999999996D) {
                            rd.drawRect(Utility.centeredWidthX(349), Utility.centeredHeightY(carUnlockedBoxHeight), 349,
                                    126);
                        } else {
                            rd.fillRect(Utility.centeredWidthX(350), Utility.centeredHeightY(carUnlockedBoxHeight), 350,
                                    127);
                        }
                        rd.setColor(new Color(255, 209, 89));
                        rd.fillRect(Utility.centeredWidthX(348), Utility.centeredHeightY(carUnlockedBoxHeight), 348, 4);
                        rd.fillRect(Utility.centeredWidthX(348), Utility.centeredHeightY(carUnlockedBoxHeight), 4, 125);
                        rd.fillRect(Utility.centeredWidthX(348),
                                Utility.centeredHeightY(carUnlockedBoxHeight) + carUnlockedBoxHeight, 348, 4);
                        rd.fillRect(Utility.centeredWidthX(348) + 345, Utility.centeredHeightY(carUnlockedBoxHeight), 4,
                                125);
                        aconto[byte0].y = i;
                        Medium.crs = true;
                        Medium.x = -(GameFacts.screenWidth / 2);
                        Medium.y = -130;
                        Medium.z = -50;
                        Medium.xz = 0;
                        Medium.zy = 0;
                        Medium.ground = 2470;
                        aconto[byte0].z = 1000;
                        aconto[byte0].x = 0;
                        aconto[byte0].xz += 5;
                        aconto[byte0].zy = 0;
                        aconto[byte0].wzy -= 10;
                        aconto[byte0].d(rd);
                        if (f < 0.10000000000000001D) {
                            rd.setColor(new Color(236, 226, 202));
                            int j = 0;
                            do {
                                rd.drawLine(Utility.centeredWidthX(348),
                                        Utility.centeredHeightY(carUnlockedBoxHeight) + 4 * 2 + 4 * j,
                                        Utility.centeredWidthX(348) + 348,
                                        Utility.centeredHeightY(carUnlockedBoxHeight) + 4 * 2 + 4 * j);
                            } while (++j < 30);
                        }
                        String s = "";
                        if (byte0 == 13) {
                            s = " ";
                        }
                        if (aflk) {
                            drawcs(Utility.centeredHeightY(carUnlockedBoxHeight) + carUnlockedBoxHeight + 20,
                                    StatList.names[byte0] + s + " has been unlocked!", 176, 196, 0, 3);
                        } else {
                            drawcs(Utility.centeredHeightY(carUnlockedBoxHeight) + carUnlockedBoxHeight + 20,
                                    StatList.names[byte0] + s + " has been unlocked!", 247, 255, 165, 3);
                        }
                        pin = 180;
                    }
                    rd.setFont(new Font("SansSerif", Font.BOLD, 11));
                    FontHandler.fMetrics = rd.getFontMetrics();
                    drawcs(Utility.centeredHeightY(carUnlockedBoxHeight) + carUnlockedBoxHeight + 40,
                            "GAME SAVED", 230, 167, 0, 3);
                    if (pin == 60) {
                        pin = 30;
                    } else {
                        pin = 0;
                    }
                } else {
                    rd.setFont(new Font("SansSerif", Font.BOLD, 13));
                    FontHandler.fMetrics = rd.getFontMetrics();
                    if (aflk) {
                        drawcs(90, "Woohoooo you finished the game!!!", 144, 167, 255, 3);
                    } else {
                        drawcs(90, "Woohoooo you finished the game!!!", 228, 240, 255, 3);
                    }
                    if (aflk) {
                        drawcs(140, "Your Awesome!", 144, 167, 255, 3);
                    } else {
                        drawcs(142, "Your Awesome!", 228, 240, 255, 3);
                    }
                    if (aflk) {
                        drawcs(190, "You're truly a RADICAL GAMER!", 144, 167, 255, 3);
                    } else {
                        drawcs(190, "You're truly a RADICAL GAMER!", 255, 100, 100, 3);
                    }
                    rd.setColor(new Color(0, 0, 0));
                    rd.fillRect(0, 205, GameFacts.screenWidth, 62);
                    rd.drawImage(radicalplay, radpx + (int) (8D * RadicalRand.random() - 4D), 205, null);
                    if (radpx != 147) {
                        radpx += 40;
                        if (radpx > GameFacts.screenWidth) {
                            radpx = -453;
                        }
                    }
                    if (flipo == 40) {
                        radpx = 148;
                    }
                    flipo++;
                    if (flipo == 70) {
                        flipo = 0;
                    }
                    if (radpx == 147) {
                        rd.setFont(new Font("SansSerif", Font.BOLD, 11));
                        FontHandler.fMetrics = rd.getFontMetrics();
                        if (aflk) {
                            drawcs(259, "A Game by Radicalplay.com", 144, 167, 255, 3);
                        } else {
                            drawcs(259, "A Game by Radicalplay.com", 228, 240, 255, 3);
                        }
                    }
                    if (aflk) {
                        drawcs(300, "Now get up and dance!", 144, 167, 255, 3);
                    } else {
                        drawcs(300, "Now get up and dance!", 228, 240, 255, 3);
                    }
                    pin = 0;
                }
                aflk = !aflk;
            } else {
                pin = 30;
                rd.drawImage(congrd, Utility.centeredImageX(congrd), 87, null);
                drawcs(137, "Stage " + checkpoints.stage + " Completed!", 170, 170, 170, 3);
                drawcs(154, checkpoints.name, 128, 128, 128, 3);
            }
        } else {
            pin = 30;
            rd.drawImage(gameov, Utility.centeredImageX(gameov), 117, null);
            drawcs(167, "Failed to Complete Stage " + checkpoints.stage + "!", 170, 170, 170, 3);
            drawcs(184, checkpoints.name, 128, 128, 128, 3);
        }
        rd.drawImage(contin[pcontin], Utility.centeredImageX(contin[pcontin]), post_game_continue_button_y, null);
        if (control.enter || control.handb) {
            if (loadedt) {
                strack.stop();
                strack.unload();
            }
            if (checkpoints.stage == unlocked && winner && unlocked != GameFacts.numberOfStages) {
                checkpoints.stage++;
                unlocked++;
                HLogger.info(unlocked);
            }
            fase = Phase.SAVEGAME;
            flipo = 0;
            control.enter = false;
            control.handb = false;
        }
    }

    /**
     * unique car sorter
     *
     * @param i checkpoints.stage
     * @author rafa
     */
    private void sortcars(int i) {
        if (i != 0) {
            int lastcar = GameFacts.numberOfPlayers;

            if (GameFacts.numberOfPlayers == 1) {
                return; // if there's only 1 car (the player), don't sort anything
            }

            int maxId = GameFacts.numberOfPlayers + (i + 1) / 2;
            if (maxId > GameFacts.numberOfCars - 1) {
                maxId = GameFacts.numberOfCars - 1;
            }

            if (sc[0] != maxId) {
                if (i != -1) {
                    sc[GameFacts.numberOfPlayers - 1] = maxId;
                    lastcar--; // boss car won't be randomized if stage isnt custom
                }
            }

            // DEBUG: Prints the range of possible cars to the console
            // HLogger.info("Minimum car: " + cd.names[(i - 1) / 2] + ", maximum car: " +
            // cd.names[nplayers + ((i - 1) / 2)] + ", therefore: " + (((i - 1) / 2) -
            // (nplayers + ((i - 1) / 2))) + " car difference");

            if (i == -1)
                i = GameFacts.numberOfCars + 2;

            // create a list of car ids, each item completely unique
            ArrayList<Integer> list = new ArrayList<>();
            for (int k = i / 2; k < GameFacts.numberOfPlayers + (i / 2); k++) {
                if (k == sc[0])
                    continue;
                list.add(k);
            }
            // randomize the order of this list (shuffle it like a deck of cards)
            Collections.shuffle(list);

            // which item of the list should be picked
            int k = 0;

            for (int j = 1; j < lastcar; j++) {

                // get an item from the "deck" - this can be any item as long as it's unique
                sc[j] = list.get(k);
                k++;

                // if there are more cars than tracks, reduce the car index number until it
                // fits.
                // unfortunately i have no idea how to make this work properly so we'll just
                // have to ignore the duplicates here
                while (sc[j] > GameFacts.numberOfCars - 1) {
                    HLogger.error("Car " + j + " is out of bounds");
                    sc[j] -= (int) (RadicalRand.random() * 5);
                }
                // HLogger.info("sc of " + j + " is " + sc[j]);
            }
        }
        // this error will never be thrown in a deployment environment
        // it is only here for extra safety
        for (int j = 0; j < GameFacts.numberOfPlayers; j++) {
            if (sc[j] > GameFacts.numberOfCars - 1)
                throw new RuntimeException("there are too many tracks and not enough cars");
        }
    }

    private void sparkeng(final Stat stat, int i) {
        i++;
        int j = 0;
        do {
            if (i == j) {
                if (!pengs[j]) {
                    sm.loop("engs" + stat.engine + j);
                    pengs[j] = true;
                }
            } else if (pengs[j]) {
                sm.stop("engs" + stat.engine + j);
                pengs[j] = false;
            }
        } while (++j < 5);
    }

    /*
     * Draws a centered string.
     */
    public void drawcs(int yPos, String s, int red, int green, int blue, int i1) {
        if (i1 != 3 && i1 != 4) {
            red = (int) (red + red * (Medium.snap[0] / 100F));
            if (red > 255) {
                red = 255;
            }
            if (red < 0) {
                red = 0;
            }
            green = (int) (green + green * (Medium.snap[1] / 100F));
            if (green > 255) {
                green = 255;
            }
            if (green < 0) {
                green = 0;
            }
            blue = (int) (blue + blue * (Medium.snap[2] / 100F));
            if (blue > 255) {
                blue = 255;
            }
            if (blue < 0) {
                blue = 0;
            }
        }
        if (i1 == 4) {
            red = (int) (red - red * (Medium.snap[0] / 100F));
            if (red > 255) {
                red = 255;
            }
            if (red < 0) {
                red = 0;
            }
            green = (int) (green - green * (Medium.snap[1] / 100F));
            if (green > 255) {
                green = 255;
            }
            if (green < 0) {
                green = 0;
            }
            blue = (int) (blue - blue * (Medium.snap[2] / 100F));
            if (blue > 255) {
                blue = 255;
            }
            if (blue < 0) {
                blue = 0;
            }
        }
        if (i1 == 1) {
            rd.setColor(new Color(0, 0, 0));
            rd.drawString(s, (GameFacts.screenWidth / 2 - FontHandler.fMetrics.stringWidth(s) / 2) + 1, yPos + 1);
        }
        if (i1 == 2) {
            red = (red * 2 + Medium.csky[0]) / 3;
            if (red > 255) {
                red = 255;
            }
            if (red < 0) {
                red = 0;
            }
            green = (green * 2 + Medium.csky[1]) / 3;
            if (green > 255) {
                green = 255;
            }
            if (green < 0) {
                green = 0;
            }
            blue = (blue * 2 + Medium.csky[2]) / 3;
            if (blue > 255) {
                blue = 255;
            }
            if (blue < 0) {
                blue = 0;
            }
        }
        rd.setColor(new Color(red, green, blue));
        rd.drawString(s, GameFacts.screenWidth / 2 - FontHandler.fMetrics.stringWidth(s) / 2, yPos);
    }

    public void trackbg(boolean flag) {
        int i = 0;
        trkl++;
        if (trkl > trklim) {
            i = 1;
            trklim = (int) (RadicalRand.random() * 40D);
            trkl = 0;
        }
        if (flag) {
            i = 0;
        }
        int j = 0;
        do {
            rd.drawImage(trackbg[i][j], trkx[j], 0, null);
            trkx[j]--;
            if (trkx[j] <= -GameFacts.screenWidth) {
                trkx[j] = GameFacts.screenWidth;
            }
        } while (++j < 2);
    }

    public void stageselect(CheckPoints checkpoints, Control control) {
        intertrack.play();

        // rd.setFont(new Font("Adventure", 1, 30));
        // drawcs(40, "S E L E C T S T A G E", 255, 128, 0, 3);

        rd.setColor(new Color(20, 20, 20, 100));
        rd.fillRoundRect(Utility.centeredWidthX(145), 20, 145, 27, 23, 30);
        rd.drawImage(select, Utility.centeredImageX(select), stage_select_text_y, null);

        if (checkpoints.stage > 0) {
            if (checkpoints.stage != 1) {
                rd.drawImage(back[pback], stage_select_back_button_x, stage_select_next_back_button_y, null);
            }
            if (checkpoints.stage != GameFacts.numberOfStages) {
                rd.drawImage(next[pnext], stage_select_next_button_x, stage_select_next_back_button_y, null);
            }
        }

        // rectangle borrowed from g6
        rd.setColor(new Color(20, 20, 20, 100));
        rd.fillRoundRect(Utility.centeredWidthX(350), 60, 350, 50, 23, 30);

        rd.setFont(new Font("SansSerif", Font.BOLD, 13));
        FontHandler.fMetrics = rd.getFontMetrics();

        if (checkpoints.stage != GameFacts.numberOfStages && checkpoints.stage > 0) {
            drawcs(80, "< Stage " + checkpoints.stage + " >", 255, 255, 255, 3);
        }
        if (checkpoints.stage == GameFacts.numberOfStages) {
            drawcs(80, "< Final Stage >", 255, 255, 255, 3);
        }
        if (checkpoints.stage < 0) {
            drawcs(80, "< Custom Stage >", 255, 255, 255, 3);
        }
        drawcs(100, "| " + checkpoints.name + " |", 210, 210, 210, 3);
        rd.drawImage(contin[pcontin], Utility.centeredImageX(contin[pcontin]), stage_select_continue_button_y, null);
        rd.setFont(new Font("SansSerif", Font.BOLD, 11));
        FontHandler.fMetrics = rd.getFontMetrics();
        drawcs(GameFacts.screenHeight - 5, "You can also use Keyboard Arrows and Enter to navigate.", 82, 90, 0, 3);
        if (control.handb || control.enter) {
            sm.play("tick");
            asay = "Stage " + checkpoints.stage + ":  " + checkpoints.name + " ";
            dudo = 150;
            Medium.trk = false;
            Medium.focus_point = 500;
            fase = Phase.SELECTEDCARSAVE;
            control.handb = false;
            control.enter = false;
            intertrack.stop();
            intertrack.unload();
        }
        if (control.right && checkpoints.stage < GameFacts.numberOfStages) {
            sm.play("tick");
            if (checkpoints.stage != unlocked) {
                checkpoints.stage++;
                fase = Phase.NPLAYERSCHECK;
                control.right = false;
            } else {
                fase = Phase.LOCKEDSTAGE;
                lockcnt = 100;
                control.right = false;
            }
        }
        if (control.left && checkpoints.stage > 1) {
            sm.play("tick");
            checkpoints.stage--;
            fase = Phase.NPLAYERSCHECK;
            control.left = false;
        }
    }

    public void snap(int i) {
        dmg = loadsnap(odmg);
        pwr = loadsnap(opwr);
        was = loadsnap(owas);
        lap = loadsnap(olap);
        pos = loadsnap(opos);
        int j = 0;
        do {
            cntdn[j] = loadsnap(ocntdn[j]);
        } while (++j < 4);
        yourwasted = loadsnap(oyourwasted);
        youlost = loadsnap(oyoulost);
        youwon = loadsnap(oyouwon);
        youwastedem = loadsnap(oyouwastedem);
        gameh = loadsnap(ogameh);
        loadingmusic = loadopsnap(oloadingmusic, i, 76);
        star[0] = loadopsnap(ostar[0], i, 0);
        star[1] = loadopsnap(ostar[1], i, 0);
        flaot = loadopsnap(oflaot, i, 1);
    }

    private Image loadsnap(Image image) {
        int i = image.getHeight(null);
        int j = image.getWidth(null);
        int[] ai = new int[j * i];
        PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, j, i, ai, 0, j);
        try {
            pixelgrabber.grabPixels();
        } catch (InterruptedException _ex) {
        }
        for (int k = 0; k < j * i; k++) {
            if (ai[k] != 0xffc0c0c0 && ai[k] != ai[j * i - 1]) {
                Color color = new Color(ai[k]);
                int l = (int) (color.getRed() + color.getRed() * (Medium.snap[0] / 100F));
                if (l > 225) {
                    l = 225;
                }
                if (l < 0) {
                    l = 0;
                }
                int i1 = (int) (color.getGreen() + color.getGreen() * (Medium.snap[1] / 100F));
                if (i1 > 225) {
                    i1 = 225;
                }
                if (i1 < 0) {
                    i1 = 0;
                }
                int j1 = (int) (color.getBlue() + color.getBlue() * (Medium.snap[2] / 100F));
                if (j1 > 225) {
                    j1 = 225;
                }
                if (j1 < 0) {
                    j1 = 0;
                }
                Color color2 = new Color(l, i1, j1);
                ai[k] = color2.getRGB();
            } else if (ai[k] == 0xffc0c0c0) {
                Color color1 = new Color(Medium.csky[0], Medium.csky[1], Medium.csky[2]);
                ai[k] = color1.getRGB();
            }
        }

        return app.createImage(new MemoryImageSource(j, i, ai, 0, j));
    }

    public void resetstat(int i) {
        arrace = false;
        ana = 0;
        cntan = 0;
        cntovn = 0;
        tcnt = 30;
        wasay = false;
        clear = 0;
        dmcnt = 0;
        pwcnt = 0;
        auscnt = 45;
        pnext = 0;
        pback = 0;
        starcnt = 130;
        gocnt = 3;
        grrd = true;
        aird = true;
        bfcrash = 0;
        cntwis = 0;
        bfskid = 0;
        pwait = 7;
        holdcnt = 0;
        holdit = false;
        winner = false;
        setnumber = false;
        wasted = 0;
        int j = 0;
        do {
            dested[j] = 0;
        } while (++j < GameFacts.numberOfPlayers);
        sortcars(i);
    }

    /**
     * x_origin defines the relative position horizontally at which to draw the bars
     */
    private void drawstat(int i, int j, boolean flag, float f, int x_origin) {
        int ai[] = new int[4];
        int ai1[] = new int[4];
        int bar_x_origin = x_origin + 62;

        if (flag) {
            ai[0] = bar_x_origin;
            ai1[0] = 11;
            ai[1] = bar_x_origin;
            ai1[1] = 19;
            ai[2] = 630;
            ai1[2] = 19;
            ai[3] = 630;
            ai1[3] = 11;
            rd.setColor(new Color(Medium.csky[0], Medium.csky[1], Medium.csky[2]));
            rd.fillPolygon(ai, ai1, 4);
        }
        if (j > i) {
            j = i;
        }
        int k = (int) (98F * ((float) j / (float) i));
        ai[0] = bar_x_origin;
        ai1[0] = 11;
        ai[1] = bar_x_origin;
        ai1[1] = 20;
        ai[2] = bar_x_origin + k;
        ai1[2] = 20;
        ai[3] = bar_x_origin + k;
        ai1[3] = 11;
        int l = 244;
        int i1 = 244;
        int j1 = 11;
        if (k > 33) {
            i1 = (int) (244F - 233F * ((k - 33) / 65F));
        }
        if (k > 70) {
            if (dmcnt < 10) {
                if (dmflk) {
                    i1 = 170;
                    dmflk = false;
                } else {
                    dmflk = true;
                }
            }
            dmcnt++;
            if (dmcnt > 167D - k * 1.5D) {
                dmcnt = 0;
            }
        }
        l = (int) (l + l * (Medium.snap[0] / 100F));
        if (l > 255) {
            l = 255;
        }
        if (l < 0) {
            l = 0;
        }
        i1 = (int) (i1 + i1 * (Medium.snap[1] / 100F));
        if (i1 > 255) {
            i1 = 255;
        }
        if (i1 < 0) {
            i1 = 0;
        }
        j1 = (int) (j1 + j1 * (Medium.snap[2] / 100F));
        if (j1 > 255) {
            j1 = 255;
        }
        if (j1 < 0) {
            j1 = 0;
        }
        rd.setColor(new Color(l, i1, j1));
        rd.fillPolygon(ai, ai1, 4);
        ai[0] = bar_x_origin;
        ai1[0] = 31;
        ai[1] = bar_x_origin;
        ai1[1] = 40;
        ai[2] = (int) (bar_x_origin + f);
        ai1[2] = 40;
        ai[3] = (int) (bar_x_origin + f);
        ai1[3] = 31;
        l = 128;
        if (f == 98F) {
            l = 64;
        }
        i1 = (int) (190D + f * 0.37D);
        j1 = 244;
        if (auscnt < 45 && aflk) {
            l = 128;
            i1 = 244;
            j1 = 244;
        }
        l = (int) (l + l * (Medium.snap[0] / 100F));
        if (l > 255) {
            l = 255;
        }
        if (l < 0) {
            l = 0;
        }
        i1 = (int) (i1 + i1 * (Medium.snap[1] / 100F));
        if (i1 > 255) {
            i1 = 255;
        }
        if (i1 < 0) {
            i1 = 0;
        }
        j1 = (int) (j1 + j1 * (Medium.snap[2] / 100F));
        if (j1 > 255) {
            j1 = 255;
        }
        if (j1 < 0) {
            j1 = 0;
        }
        rd.setColor(new Color(l, i1, j1));
        rd.fillPolygon(ai, ai1, 4);
        if (Medium.flex == 2 && f != 98F) {
            ai[0] = (int) (bar_x_origin + f);
            ai1[0] = 31;
            ai[1] = (int) (bar_x_origin + f);
            ai1[1] = 39;
            ai[2] = 630;
            ai1[2] = 39;
            ai[3] = 630;
            ai1[3] = 31;
            rd.setColor(new Color(Medium.csky[0], Medium.csky[1], Medium.csky[2]));
            rd.fillPolygon(ai, ai1, 4);
        }
    }

    /*
     * "pressed" lol
     */
    private Image bressed(Image image) {
        int i = image.getHeight(null);
        int j = image.getWidth(null);
        int[] ai = new int[j * i];
        PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, j, i, ai, 0, j);
        try {
            pixelgrabber.grabPixels();
        } catch (InterruptedException _ex) {
        }
        Color color = new Color(247, 255, 165);
        for (int k = 0; k < j * i; k++) {
            if (ai[k] != ai[j * i - 1]) {
                ai[k] = color.getRGB();
            }
        }

        return app.createImage(new MemoryImageSource(j, i, ai, 0, j));
    }

    private void loading() {
        rd.setColor(new Color(0, 0, 0));
        rd.fillRect(0, 0, GameFacts.screenWidth, GameFacts.screenHeight);
        rd.drawImage(sign, Utility.centeredImageX(sign), 10, null);
        rd.drawImage(hello, Utility.centeredImageX(hello), 80, null);
        rd.setColor(new Color(198, 214, 255));
        rd.fillRoundRect(Utility.centeredWidthX(300), 315, 300, 80, 30, 70);
        rd.setColor(new Color(128, 167, 255));
        rd.drawRoundRect(Utility.centeredWidthX(300), 315, 300, 80, 30, 70);
        rd.drawImage(loadbar, Utility.centeredImageX(loadbar), 340, null);
        rd.setFont(new Font("SansSerif", Font.BOLD, 11));
        FontHandler.fMetrics = rd.getFontMetrics();
        drawcs(333, "Loading game, please wait.", 0, 0, 0, 3);
        shload += ((dnload + 10F) - shload) / 100F;
        if (shload > kbload) {
            shload = kbload;
        }
        if (dnload == kbload) {
            shload = kbload;
        }
        drawcs(385, (int) (((26F + (shload / kbload) * 200F) / 226F) * 100F) + " % loaded    |    "
                + (kbload - (int) shload) + " KB remaining", 32, 64, 128, 3);
        rd.setColor(new Color(32, 64, 128));
        rd.fillRect(Utility.centeredImageX(loadbar) + 5, 346, 26 + (int) ((shload / kbload) * 200F), 10);
    }

    public xtGraphics(Graphics2D graphics2d, GameSparker applet) {
        fase = Phase.LOADING;
        oldfase = Phase.INGAME;
        starcnt = 0;
        unlocked = 1;
        lockcnt = 0;
        opselect = 1;
        shaded = false;
        flipo = 0;
        nextc = false;
        gatey = 0;
        looped = 1;
        sc = new int[51];
        holdit = false;
        holdcnt = 0;
        winner = false;
        flexpix = new int[GameFacts.screenHeight * GameFacts.screenWidth];
        smokey = new int[0x16fb4];
        flatrstart = 0;
        runtyp = 0;
        trackbg = new Image[2][2];
        dude = new Image[3];
        dudeb = new Image[3];
        duds = 0;
        dudo = 0;
        next = new Image[2];
        back = new Image[2];
        contin = new Image[2];
        ostar = new Image[2];
        star = new Image[3];
        pcontin = 0;
        pnext = 0;
        pback = 0;
        pstar = 0;
        ocntdn = new Image[4];
        cntdn = new Image[4];
        gocnt = 0;
        pengs = new boolean[5];
        aird = false;
        grrd = false;
        setnumber = false;
        pwastd = false;
        mutes = false;
        // tracks = new RadicalMusic[GameFacts.numberOfStages];
        // loadedt = new boolean[GameFacts.numberOfStages];
        lastload = -1;
        mutem = false;
        macn = false;
        arrace = false;
        ana = 0;
        cntan = 0;
        cntovn = 0;
        flk = false;
        tcnt = 30;
        tflk = false;
        say = "";
        wasay = false;
        clear = 0;
        posit = 0;
        wasted = 0;
        laps = 0;
        dested = new int[51];
        dmcnt = 0;
        dmflk = false;
        pwcnt = 0;
        pwflk = false;
        loop = "";
        spin = "";
        asay = "";
        auscnt = 45;
        aflk = false;
        kbload = 0;
        dnload = 0;
        shload = 0.0F;
        radpx = 147;
        pin = 60;
        trkl = 0;
        trklim = (int) (Math.random() * 40D);
        flkat = (int) (60D + 140D * Math.random());
        movly = (int) (100D + 100D * Math.random());
        /* this is later set properly in loadimages() */
        insano_mainmenu_x = GameFacts.screenWidth / 2;
        insano_mainmenu_y = 2 + main_menu_height_origin;
        insano_mainmenu_target_x = 0;
        insano_mainmenu_target_y = 0;
        pgady = new int[9];
        pgas = new boolean[9];
        lxm = -10;
        lym = -10;
        pwait = 7;
        stopcnt = 0;
        cntwis = 0;
        crshturn = 0;
        bfcrash = 0;
        bfskid = 0;
        crashup = false;
        skidup = false;
        skflg = 0;
        dskflg = 0;
        flatr = 0;
        flyr = 0;
        flyrdest = 0;
        flang = 0;
        flangados = 0;
        blackn = 0.0F;
        blacknados = 0.0F;
        app = applet;
        rd = graphics2d;
        try {
            hello = ImageIO.read(new File("data/aimg/hello.gif"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            sign = ImageIO.read(new File("data/aimg/sign.gif"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            loadbar = ImageIO.read(new File("data/aimg/loadbar.gif"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int i = 0;
        loadedt = false;
    }

    public void maini(Control control, CheckPoints checkpoints, Madness madness[], ContO conto[], ContO conto1[]) {
        // might redo the entire menu its horrible

        int menuItems = 4;

        if (GameSparker.DEBUG) {
            if (!devtriggered) {
                HLogger.info("Developer Console triggered");

                DevTool console = new DevTool(checkpoints, madness, conto, conto1, this);
                console.showConsole();
                devtriggered = true;
            }
        }

        if (flipo == 0) {
            bgmy[0] = 0;
            bgmy[1] = GameFacts.screenHeight;
            app.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

        /* handle the moving background in main menu */
        int i = 0;
        do {
            rd.drawImage(bgmain, 0, bgmy[i], null);
            bgmy[i] -= 20;
            if (bgmy[i] <= -GameFacts.screenHeight) {
                bgmy[i] = GameFacts.screenHeight;
            }
        } while (++i < 2);

        /* handle the light red/ping background behind "need for madness!?" text */
        int logomadbg_xpos = Utility.centeredImageX(logomadbg);
        if (flipo > flkat) {
            rd.drawImage(logomadbg, logomadbg_xpos + (int) (4D - Math.random() * 8D),
                    143 + main_menu_height_origin + (int) (4D - Math.random() * 8D), null);
        } else {
            rd.drawImage(logomadbg, logomadbg_xpos, 143 + main_menu_height_origin, null);
        }

        /* handle initial insano position */
        rd.drawImage(dude[0], insano_mainmenu_x, insano_mainmenu_y, null);

        /* handle cars surrounding "need for madness!? text" */
        rd.drawImage(logocars, Utility.centeredImageX(logocars), 28 + main_menu_height_origin, null);

        if (flipo > flkat) {
            rd.drawImage(logomadnes, Utility.centeredImageX(logomadnes) + (int) (4D - Math.random() * 8D),
                    148 + main_menu_height_origin + (int) (4D - Math.random() * 8D), null);
        } else {
            rd.drawImage(logomadnes, Utility.centeredImageX(logomadnes), 148 + main_menu_height_origin, null);
        }

        flipo++;

        if (flipo > flkat + 36) {
            flipo = 1;
            flkat = (int) (60D + 140D * Math.random());
        }

        /* handles moving insano around in the menu */
        if (movly <= 10) {
            if (movly == 10 || movly == 8 || movly == 6 || movly == 4 || movly == 2) {
                insano_mainmenu_target_x = (int) (insano_mainmenu_x + 200 - 500D * Math.random());
                insano_mainmenu_target_y = (int) (insano_mainmenu_y + 200 - 500D * Math.random());
                if (movly == 2) {
                    insano_mainmenu_target_x = insano_mainmenu_x_original;
                    insano_mainmenu_target_y = insano_mainmenu_y_original;
                }
                movly--;
            }
            insano_mainmenu_x += (insano_mainmenu_target_x - insano_mainmenu_x) / 15;
            insano_mainmenu_y += (insano_mainmenu_target_y - insano_mainmenu_y) / 15;
            if (movly != 1) {
                if (Utility.pys(insano_mainmenu_x, insano_mainmenu_target_x, insano_mainmenu_y,
                        insano_mainmenu_target_y) < 20F) {
                    movly--;
                }
            } else {
                if (insano_mainmenu_x > insano_mainmenu_target_x) {
                    insano_mainmenu_x--;
                } else {
                    insano_mainmenu_x++;
                }
                if (insano_mainmenu_y > insano_mainmenu_target_y) {
                    insano_mainmenu_y--;
                } else {
                    insano_mainmenu_y++;
                }
                if (Utility.pys(insano_mainmenu_x, insano_mainmenu_target_x, insano_mainmenu_y,
                        insano_mainmenu_target_y) < 2.0F) {
                    movly--;
                }
            }
            if (movly == 0) {
                insano_mainmenu_x = insano_mainmenu_x_original;
                insano_mainmenu_y = insano_mainmenu_y_original;
                movly = (int) (100D + 100D * Math.random());
            }
        } else if (flipo >= movly) {
            movly = 10;
        }

        rd.drawImage(opback, Utility.centeredImageX(opback), 212 + main_menu_height_origin, null);
        rd.drawImage(nfmcoms, Utility.centeredImageX(nfmcoms), 195 + main_menu_height_origin, null);
        rd.drawImage(byrd, Utility.centeredImageX(byrd), 383 + main_menu_height_origin, null);

        if (control.up) {
            opselect--;
            if (opselect == -1) {
                opselect = menuItems - 1;
            }
            control.up = false;
        }
        if (control.down) {
            opselect++;
            if (opselect == menuItems + 1) {
                opselect = 0;
            }
            control.down = false;
        }
        if (opselect == 0) {
            if (shaded) {
                rd.setColor(new Color(140, 70, 0));
                rd.fillRect(Utility.centeredWidthX(main_menu_op_0_width), main_menu_op_0_y, main_menu_op_0_width,
                        main_menu_button_height);
                aflk = false;
            }
            if (aflk) {
                rd.setColor(new Color(200, 255, 0));
                aflk = false;
            } else {
                rd.setColor(new Color(255, 128, 0));
                aflk = true;
            }
            rd.drawRoundRect(Utility.centeredWidthX(main_menu_op_0_width), main_menu_op_0_y, main_menu_op_0_width,
                    main_menu_button_height, 7, 20);
        } else {
            rd.setColor(new Color(0, 0, 0));
            rd.drawRoundRect(Utility.centeredWidthX(main_menu_op_0_width), main_menu_op_0_y, main_menu_op_0_width,
                    main_menu_button_height, 7, 20);
        }
        if (opselect == 1) {
            if (shaded) {
                rd.setColor(new Color(140, 70, 0));
                rd.fillRect(Utility.centeredWidthX(main_menu_op_1_width), main_menu_op_1_y, main_menu_op_1_width,
                        main_menu_button_height);
                aflk = false;
            }
            if (aflk) {
                rd.setColor(new Color(200, 128, 0));
                aflk = false;
            } else {
                rd.setColor(new Color(255, 128, 0));
                aflk = true;
            }
            rd.drawRoundRect(Utility.centeredWidthX(main_menu_op_1_width), main_menu_op_1_y, main_menu_op_1_width,
                    main_menu_button_height, 7, 20);
        } else {
            rd.setColor(new Color(0, 0, 0));
            rd.drawRoundRect(Utility.centeredWidthX(main_menu_op_1_width), main_menu_op_1_y, main_menu_op_1_width,
                    main_menu_button_height, 7, 20);
        }
        if (opselect == 2) {
            if (shaded) {
                rd.setColor(new Color(140, 70, 0));
                rd.fillRect(Utility.centeredWidthX(main_menu_op_2_width), main_menu_op_2_y, main_menu_op_2_width,
                        main_menu_button_height);
                aflk = false;
            }
            if (aflk) {
                rd.setColor(new Color(200, 0, 0));
                aflk = false;
            } else {
                rd.setColor(new Color(255, 128, 0));
                aflk = true;
            }
            rd.drawRoundRect(Utility.centeredWidthX(main_menu_op_2_width), main_menu_op_2_y, main_menu_op_2_width,
                    main_menu_button_height, 7, 20);
        } else {
            rd.setColor(new Color(0, 0, 0));
            rd.drawRoundRect(Utility.centeredWidthX(main_menu_op_2_width), main_menu_op_2_y, main_menu_op_2_width,
                    main_menu_button_height, 7, 20);
        }
        rd.drawImage(opti, Utility.centeredImageX(opti), 250 + main_menu_height_origin, null);
        if (control.enter || control.handb) {
            if (opselect == 0) {
                if (unlocked == 1 && oldfase == Phase.INGAME) {
                    oldfase = Phase.CARSELECTTRIGGER;
                    fase = Phase.INSTRUCTIONS;
                } else {
                    fase = Phase.CARSELECTTRIGGER;
                }
            }
            if (opselect == 1) {
                oldfase = Phase.MAINMENU;
                fase = Phase.INSTRUCTIONS;
            }
            if (opselect == 2) {
                fase = Phase.CREDITS;
            }
            if (opselect == 3) {
                oldfase = Phase.MAINMENU;
                opselect = 0;
                fase = Phase.CUSTOMSETTINGS;
            }
            flipo = 0;
            control.enter = false;
            control.handb = false;
        }
        if (shaded) {
            app.repaint();
            try {
                Thread.sleep(100L);
            } catch (InterruptedException _ex) {
            }
        }
    }

    public void blendude(Image image) {
        if (!macn) {
            if (RadicalRand.bool()) {
                dudo = 217;
            } else {
                dudo = 331;
            }
            int ai[] = new int[19520];
            PixelGrabber pixelgrabber = new PixelGrabber(image, dudo, 0, 122, 160, ai, 0, 122);
            try {
                pixelgrabber.grabPixels();
            } catch (InterruptedException _ex) {
                dudo = -1;
            }
            int j = 0;
            do {
                int ai1[] = new int[19520];
                PixelGrabber pixelgrabber1 = new PixelGrabber(dude[j], 0, 10, 122, 160, ai1, 0, 122);
                try {
                    pixelgrabber1.grabPixels();
                } catch (InterruptedException _ex) {
                    dudo = -1;
                }
                if (dudo != -1) {
                    int k = 0;
                    do {
                        if (ai1[k] != ai1[0]) {
                            Color color = new Color(ai1[k]);
                            Color color1 = new Color(ai[k]);
                            int l = (color.getRed() + color1.getRed() * 3) / 4;
                            if (l > 255) {
                                l = 255;
                            }
                            if (l < 0) {
                                l = 0;
                            }
                            int i1 = (color.getGreen() + color1.getGreen() * 3) / 4;
                            if (i1 > 255) {
                                i1 = 255;
                            }
                            if (i1 < 0) {
                                i1 = 0;
                            }
                            int j1 = (color.getBlue() + color1.getBlue() * 3) / 4;
                            if (j1 > 255) {
                                j1 = 255;
                            }
                            if (j1 < 0) {
                                j1 = 0;
                            }
                            Color color2 = new Color(l, i1, j1);
                            ai1[k] = color2.getRGB();
                        }
                    } while (++k < 19520);
                    dudeb[j] = app.createImage(new MemoryImageSource(122, 160, ai1, 0, 122));
                }
            } while (++j < 3);
        } else {
            if (RadicalRand.bool()) {
                dudo = 176;
            } else {
                dudo = 372;
            }
            int i = 0;
            do {
                dudeb[i] = dude[i];
            } while (++i < 3);
        }
    }

    public void musicomp(int i, Control control) {
        hipnoload(i, true);
        if (control.handb || control.enter) {
            System.gc();
            fase = Phase.INGAME;
            control.handb = false;
            control.enter = false;
        }
    }

    private void drawSmokeCarsbg() {
        if (Math.abs(flyr - flyrdest) > 20) {
            if (flyr > flyrdest) {
                flyr -= 20;
            } else {
                flyr += 20;
            }
        } else {
            flyr = flyrdest;
            flyrdest = (int) ((flyr + Medium.random() * 160F) - 80F);
        }
        if (flyr > 160) {
            flyr = 160;
        }
        if (flatr > 170) {
            flatrstart++;
            flatr = flatrstart * 3;
            flyr = (int) (Medium.random() * 160F - 80F);
            flyrdest = (int) ((flyr + Medium.random() * 160F) - 80F);
            flang = 1;
            flangados = (int) (Medium.random() * 6F + 2.0F);
            blackn = 0.0F;
            blacknados = Medium.random() * 0.4F;
        }
        int i = 0;
        do {
            int j = 0;
            do {
                if (smokey[i + j * 466] != smokey[0]) {
                    float f = Utility.pys(i, 233, j, flyr);
                    int k = (int) (((i - 233) / f) * flatr);
                    int l = (int) (((j - flyr) / f) * flatr);
                    int i1 = i + k + 100 + (j + l + 110) * GameFacts.screenWidth;
                    if (i + k + 100 < GameFacts.screenWidth && i + k + 100 > 0 && j + l + 110 < GameFacts.screenHeight
                            && j + l + 110 > 0 && i1 < 0xE1000
                            && i1 >= 0) {
                        Color color = new Color(flexpix[i1]);
                        Color color1 = new Color(smokey[i + j * 466]);
                        float f1 = (255F - color1.getRed()) / 255F;
                        int j1 = (int) ((color.getRed() * (flang * f1)
                                + color1.getRed() * (1.0F - f1)) / (flang * f1 + (1.0F - f1) + blackn));
                        if (j1 > 255) {
                            j1 = 255;
                        }
                        if (j1 < 0) {
                            j1 = 0;
                        }
                        Color color2 = new Color(j1, j1, j1);
                        flexpix[i1] = color2.getRGB();
                    }
                }
            } while (++j < 202);
        } while (++i < 466);
        blackn += blacknados;
        flang += flangados;
        flatr += 10 + flatrstart * 2;
        Image image = app.createImage(new MemoryImageSource(GameFacts.screenWidth, GameFacts.screenHeight, flexpix, 0, GameFacts.screenWidth));
        rd.drawImage(image, 0, 0, null);
    }

    public void loaddata() {
        kbload = 950;
        String s2 = System.getProperty("os.name");
        if (!s2.startsWith("Win")) {
            macn = true;
        }
        runtyp = 176;

        runner = new Thread(this);
        runner.start();

        loadimages();
        //loadnetworkimages();

        // cars = new RadicalMusic("music/cars.radq", 200, 7900, 125, macn);
        // dnload += 27;

        // cars = new RadicalMusic("music/cars.mp3");
        // dnload += 3718;

        /*
         * int j = 0;
         * do {
         * int k = 0;
         * do {
         * engs[k][j] = getSound("sounds/" + s + "" + k + "" + j + ".au");
         * dnload += 3;
         * } while (++k < 5);
         * pengs[j] = false;
         * } while (++j < 5);
         */
        // stages = new RadicalMusic("music/stages.radq", 135, 7800, 125, macn);
        // dnload += 91;

        /*
         * j = 0;
         * do {
         * air[j] = getSound("sounds/" + s + "air" + j + ".au");
         * dnload += 2;
         * } while (++j < 6);
         * j = 0;
         * do {
         * crash[j] = getSound("sounds/" + s + "crash" + (j + 1) + "." + s1);
         * if (i == 2) {
         * dnload += 10;
         * } else {
         * dnload += 7;
         * }
         * } while (++j < 3);
         * j = 0;
         * do {
         * lowcrash[j] = getSound("sounds/" + s + "lowcrash" + (j + 1) + "." + s1);
         * if (i == 2) {
         * dnload += 10;
         * } else {
         * dnload += 3;
         */
        loadsounds();
    }

    public void loadsounds() {
        this.dnload += 3;

        try {
            File soundsZip = new File("data/sounds.radq");
            ZipInputStream soundsInputStream = new ZipInputStream(new FileInputStream(soundsZip));

            for (ZipEntry soundEntry = soundsInputStream.getNextEntry(); soundEntry != null; soundEntry = soundsInputStream.getNextEntry()) {
                int size = (int) soundEntry.getSize();
                String name = soundEntry.getName();
                byte[] sound = new byte[size];

                int z;
                for (int x = 0; size > 0; size -= z) {
                    z = soundsInputStream.read(sound, x, size);
                    x += z;
                }

                int i;
                for (i = 0; i < 5; ++i) {
                    for (int v = 0; v < 5; ++v) {
                        if (name.equals(v + "" + i + ".wav")) {
                            sm.add("engs" + v + i, new SoundClipUnthreaded(sound));
                        }
                    }
                }

                for (i = 0; i < 6; ++i) {
                    if (name.equals("air" + i + ".wav")) {
                        sm.add("air" + i, new SoundClipUnthreaded(sound));
                    }
                }

                for (i = 0; i < 3; ++i) {
                    if (name.equals("crash" + (i + 1) + ".wav")) {
                        sm.add("crash" + i, new SoundClipUnthreaded(sound));
                    }
                }

                for (i = 0; i < 3; ++i) {
                    if (name.equals("lowcrash" + (i + 1) + ".wav")) {
                        sm.add("lowcrash" + i, new SoundClipUnthreaded(sound));
                    }
                }

                for (i = 0; i < 3; ++i) {
                    if (name.equals("skid" + (i + 1) + ".wav")) {
                        sm.add("skid" + i, new SoundClipUnthreaded(sound));
                    }
                }

                for (i = 0; i < 3; ++i) {
                    if (name.equals("dustskid" + (i + 1) + ".wav")) {
                        sm.add("dustskid" + i, new SoundClipUnthreaded(sound));
                    }
                }

                if (name.equals("powerup.wav")) {
                    sm.add("powerup", new SoundClipThreaded(sound));
                }

                if (name.equals("tires.wav")) {
                    sm.add("tires", new SoundClipUnthreaded(sound));
                }

                if (name.equals("checkpoint.wav")) {
                    sm.add("checkpoint", new SoundClipUnthreaded(sound));
                }

                if (name.equals("carfixed.wav")) {
                    sm.add("carfixed", new SoundClipThreaded(sound));
                }

                if (name.equals("three.wav")) {
                    sm.add("three", new SoundClipUnthreaded(sound));
                }

                if (name.equals("two.wav")) {
                    sm.add("two", new SoundClipUnthreaded(sound));
                }

                if (name.equals("one.wav")) {
                    sm.add("one", new SoundClipUnthreaded(sound));
                }

                if (name.equals("go.wav")) {
                    sm.add("go", new SoundClipUnthreaded(sound));
                }

                if (name.equals("wasted.wav")) {
                    sm.add("wasted", new SoundClipUnthreaded(sound));
                }

                if (name.equals("firewasted.wav")) {
                    sm.add("firewasted", new SoundClipUnthreaded(sound));
                }

                if (name.equals("tick.wav")) {
                    sm.add("tick", new SoundClipThreaded(sound));
                }

                if (name.equals("tliu.wav")) {
                    sm.add("tliu", new SoundClipThreaded(sound));
                }

                if (name.equals("woosh.wav")) {
                    sm.add("woosh", new SoundClipThreaded(sound));
                }

                if (name.equals("cosmic.wav")) {
                    sm.add("cosmic", new SoundClipThreaded(sound));
                }

                this.dnload += 5;
            }

            /*
             * } while (++j < 3);
             * tires = getSound("sounds/" + s + "tires." + s1);
             * if (i == 2) {
             * dnload += 24;
             * } else {
             * dnload += 4;
             * }
             * checkpoint = getSound("sounds/" + s + "checkpoint." + s1);
             * if (i == 2) {
             * dnload += 24;
             * } else {
             * dnload += 6;
             * }
             * carfixed = getSound("sounds/" + s + "carfixed." + s1);
             * if (i == 2) {
             * dnload += 24;
             * } else {
             * dnload += 10;
             * }
             * powerup = getSound("sounds/" + s + "powerup." + s1);
             * if (i == 2) {
             * dnload += 42;
             * } else {
             * dnload += 8;
             * }
             * three = getSound("sounds/" + s + "three." + s1);
             * if (i == 2) {
             * dnload += 24;
             * } else {
             * dnload += 4;
             * }
             * two = getSound("sounds/" + s + "two." + s1);
             * if (i == 2) {
             * dnload += 24;
             * } else {
             * dnload += 2;
             * }
             * one = getSound("sounds/" + s + "one." + s1);
             * if (i == 2) {
             * dnload += 24;
             * } else {
             * dnload += 4;
             * }
             * go = getSound("sounds/" + s + "go." + s1);
             * if (i == 2) {
             * dnload += 24;
             * } else {
             * dnload += 4;
             * }
             * wastd = getSound("sounds/" + s + "wasted." + s1);
             * if (i == 2) {
             * dnload += 24;
             * } else {
             * dnload += 4;
             * }
             * firewasted = getSound("sounds/" + s + "firewasted." + s1);
             * if (i == 2) {
             * dnload += 24;
             * } else {
             * dnload += 10;
             */

            soundsInputStream.close();
        } catch (Exception var12) {
            HLogger.error("Error Loading Sounds: " + var12);
        }

        /*
         * j = 0;
         * do {
         * skid[j] = getSound("sounds/" + s + "skid" + (j + 1) + "." + s1);
         * if (i == 2) {
         * dnload += 22;
         * } else {
         * dnload += 6;
         * }
         * } while (++j < 3);
         * 
         * 
         * }
         * j = 0;
         * do {
         * dustskid[j] = getSound("sounds/" + s + "dustskid" + (j + 1) + "." + s1);
         * if (i == 2) {
         * dnload += 22;
         * } else {
         * dnload += 7;
         * }
         * } while (++j < 3);
         */

        System.gc();
    }

    public void clicknow() {
        rd.setColor(new Color(198, 214, 255));
        rd.fillRoundRect(GameFacts.screenWidth / 2 - 150, 315, 300, 80, 30, 70);
        rd.setColor(new Color(128, 167, 255));
        rd.drawRoundRect(GameFacts.screenWidth / 2 - 150, 315, 300, 80, 30, 70);
        if (aflk) {
            drawcs(355, "Click here to Start", 0, 0, 0, 3);
            aflk = false;
        } else {
            drawcs(355, "Click here to Start", 0, 67, 200, 3);
            aflk = true;
        }
    }

    private Image loadimage(byte[] abyte0) {
        try {
            return ImageIO.read(new ByteArrayInputStream(abyte0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void rad(int i) {
        if (i == 0) {
            radpx = Utility.centeredImageX(radicalplay);
            pin = 0;
        }

        trackbg(false);
        rd.setColor(new Color(0, 0, 0));
        rd.fillRect(0, 110, GameFacts.screenWidth, 59);

        if (pin != 0) {
            rd.drawImage(radicalplay, radpx + (int) (8D * Math.random() - 4D), 110, null);
        } else {
            rd.drawImage(radicalplay, Utility.centeredImageX(radicalplay), 110, null);
        }

        if (pin == 1) {
            radpx += GameFacts.screenWidth / 20;
            if (radpx > GameFacts.screenWidth) {
                radpx = -453;
            }
        }

        if (Math.abs(radpx - Utility.centeredImageX(radicalplay)) < GameFacts.screenWidth / 20) {
            radpx = Utility.centeredImageX(radicalplay);
            pin = 0;
        }

        if (i == 40) {
            radpx = Utility.centeredImageX(radicalplay);
            pin = 1;
        }

        if (radpx == Utility.centeredImageX(radicalplay)) {
            rd.setFont(new Font("SansSerif", Font.BOLD, 11));
            FontHandler.fMetrics = rd.getFontMetrics();
            drawcs(160 + (int) (5F * Medium.random()), "Radicalplay.com", 112, 120, 143, 3);
        }

        rd.setFont(new Font("SansSerif", Font.BOLD, 11));
        FontHandler.fMetrics = rd.getFontMetrics();
        if (aflk) {
            drawcs(190, "And we are never going to find the new unless we get a little crazy...", 112, 120, 143, 3);
            aflk = false;
        } else {
            drawcs(192, "And we are never going to find the new unless we get a little crazy...", 150, 150, 150, 3);
            aflk = true;
        }
        rd.drawImage(rpro, Utility.centeredImageX(rpro), 240, null);
    }

    public void skid(int i, float f) {
        if (bfcrash == 0 && bfskid == 0 && f > 150F) {
            if (i == 0) {
                if (!mutes) {
                    sm.play("skid" + skflg);
                }
                if (skidup) {
                    skflg++;
                } else {
                    skflg--;
                }
                if (skflg == 3) {
                    skflg = 0;
                }
                if (skflg == -1) {
                    skflg = 2;
                }
            } else {
                if (!mutes) {
                    sm.play("dustskid" + dskflg);
                }
                if (skidup) {
                    dskflg++;
                } else {
                    dskflg--;
                }
                if (dskflg == 3) {
                    dskflg = 0;
                }
                if (dskflg == -1) {
                    dskflg = 2;
                }
            }
            bfskid = 35;
        }
    }

    public void cantreply() {
        rd.setColor(new Color(64, 143, 223));
        rd.fillRoundRect(Utility.centeredWidthX(500), 73, 500, 23, 7, 20);
        rd.setColor(new Color(0, 89, 223));
        rd.drawRoundRect(Utility.centeredWidthX(500), 73, 500, 23, 7, 20);
        drawcs(89, "Sorry not enough replay data to play available, please try again later.", 255, 255, 255, 1);
    }

    public void stopallnow() {
        int i = 0;
        if (loadedt) {
            strack.unload();
            strack = null;
        }
        loadedt = false;
        i = 0;
        do {
            for (int x = 0; x < 5; x++) {
                sm.stop("engs" + x + i);
            }
        } while (++i < 5);
        i = 0;
        do {
            sm.stop("air" + i);
        } while (++i < 6);
        sm.stop("wasted");
        if (intertrack != null) {
            intertrack.unload();
        }
        intertrack = null;
        // if (intercar != null) {
        //     intercar.unload();
        // }
        // intercar = null;
    }

    void loadIntertrack(String track) {     //load interface track
        intertrack = loadMusicFile("data/music/interface/" + track);
    }

    public void inishcarselect() {
        carsbginflex();
        flatrstart = 0;
        Medium.lightson = false;
        /// aaaaaaaa
        loadIntertrack("cars");
        pnext = 0;
        pback = 0;
    }

    /* Draws the text and bar for a single car selection stat. */
    private void draw_carselect_stat(String text, int bar_x, int y, float blocked_off_part_size) {
        int stringWidth = FontHandler.fMetrics.stringWidth(text);

        /* draw text and gradient bar */
        rd.setColor(new Color(181, 120, 40));
        rd.drawString(text, bar_x - stringWidth - 5, y + 6);
        rd.drawImage(statb, bar_x, y, null);

        /* replace some of the gradient bar with black */
        rd.setColor(new Color(0, 0, 0));
        rd.fillRect((int) ((float) bar_x + 156F * blocked_off_part_size), y,
                (int) (156F * (1.0F - blocked_off_part_size) + 1.0F), statbo.getHeight(null));

        /* draw the overlay */
        rd.drawImage(statbo, bar_x, y, null);
    }

    public void carselect(Control control, ContO aconto[], Madness madness) {
        /* this is a mess... */

        if (flatrstart == 6) {
            rd.drawImage(carsbg, 0, 0, null);
            // } else if (flatrstart <= 1) {
            // drawSmokeCarsbg();
        } else {
            rd.setColor(new Color(255, 255, 255, 50));
            rd.fillRect(0, 0, GameFacts.screenWidth, GameFacts.screenHeight);
            carsbginflex();
            flatrstart = 6;
        }
        intertrack.play();
        rd.drawImage(selectcar, Utility.centeredImageX(selectcar), 12, null);
        Medium.crs = true;
        Medium.x = -GameFacts.screenWidth/2;
        Medium.y = -(int)(GameFacts.screenHeight*0.83);
        Medium.z = 200;
        Medium.xz = 0;
        Medium.zy = 10;
        Medium.ground = 570;
        aconto[sc[0]].d(rd);

        if (GameSparker.DEBUG) {
            if (debugmode) {
                rd.setColor(new Color(255, 255, 255));
                rd.drawString("sc[" + sc[0] + "]", 33, 50);
            }
        }

        if (flipo == 0) {
            rd.setFont(new Font("SansSerif", Font.BOLD, 13));
            FontHandler.fMetrics = rd.getFontMetrics();
            byte byte0 = 0;
            if (flatrstart < 6) {
                byte0 = 2;
            }
            if (aflk) {
                drawcs(70 + byte0, StatList.names[sc[0]], 240, 240, 240, 3);
                aflk = false;
            } else {
                drawcs(70, StatList.names[sc[0]], 176, 176, 176, 3);
                aflk = true;
            }
            aconto[sc[0]].z = 950;
            if (sc[0] == 13) {
                aconto[sc[0]].z = 1000;
            }
            aconto[sc[0]].y = -34 - aconto[sc[0]].grat;
            aconto[sc[0]].x = 0;
            aconto[sc[0]].xz += 5;
            aconto[sc[0]].zy = 0;
            aconto[sc[0]].wzy -= 10;
            if (aconto[sc[0]].wzy < -45) {
                aconto[sc[0]].wzy += 45;
            }

            car_select_delay = 0;

            if (sc[0] != 0) {
                rd.drawImage(back[pback], car_select_back_button_x, car_select_next_back_button_y, null);
            }
            if (sc[0] != GameFacts.numberOfCars - 1) {
                rd.drawImage(next[pnext], car_select_next_button_x, car_select_next_back_button_y, null);
            }
            if ((sc[0] - 7) * 2 >= unlocked) {
                // if (gatey == 300) {
                // int i = 0;
                // do {
                // pgas[i] = false;
                // pgady[i] = 0;
                // } while (++i < 9);
                // pgas[0] = true;
                // }
                // int j = 0;
                // do {
                // rd.drawImage(pgate, pgatx[j], (pgaty[j] + pgady[j]) - gatey, null);
                // if (flatrstart == 6) {
                // if (pgas[j]) {
                // pgady[j] -= ((80 + 100 / (j + 1)) - Math.abs(pgady[j])) / 3;
                // if (pgady[j] < -(70 + 100 / (j + 1))) {
                // pgas[j] = false;
                // if (j != 8) {
                // pgas[j + 1] = true;
                // }
                // }
                // } else {
                // pgady[j] += ((80 + 100 / (j + 1)) - Math.abs(pgady[j])) / 3;
                // if (pgady[j] > 0) {
                // pgady[j] = 0;
                // }
                // }
                // }
                // } while (++j < 9);
                // if (gatey != 0) {
                // gatey -= 100;
                // }
                aconto[sc[0]].blackout = true;
                if (flatrstart == 6) {
                    drawcs((int) (GameFacts.screenHeight * 0.91), "[ Car Locked ]", 210, 210, 210, 3);
                    drawcs((int) (GameFacts.screenHeight * 0.94),
                            "This car unlocks when stage " + (sc[0] - 7) * 2 + " is completed...", 181, 120, 40, 3);
                }
                if (aflk) {
                    drawcs((int) (GameFacts.screenHeight * 0.1) + byte0, "[ LOCKED ]", 240, 240, 240, 3);
                    aflk = false;
                } else {
                    drawcs((int) (GameFacts.screenHeight * 0.1), "[ LOCKED ]", 176, 176, 176, 3);
                    aflk = true;
                }
            } else {
                if (aflk) {
                    drawcs((int) (GameFacts.screenHeight * 0.1) + byte0, StatList.names[sc[0]], 240, 240, 240, 3);
                    aflk = false;
                } else {
                    drawcs((int) (GameFacts.screenHeight * 0.1), StatList.names[sc[0]], 176, 176, 176, 3);
                    aflk = true;
                }

                if (flatrstart == 6) {
                    int leftside_statbar_x = GameFacts.screenWidth / 4 - statb.getWidth(null) / 2;
                    int rightside_statbar_x = (GameFacts.screenWidth / 4) * 3 - statb.getWidth(null) / 2;
                    int statbar_initial_y = (int) (GameFacts.screenHeight * 0.85);
                    int statbar_y_gap = 15;

                    rd.setFont(new Font("SansSerif", Font.BOLD, 11));
                    FontHandler.fMetrics = rd.getFontMetrics();

                    /*
                     * the `f` variable holds the current stat's actual level, to pass to
                     * draw_carselect_stat
                     */

                    float f = (StatList.swits[sc[0]][2] - 220) / 90F;
                    if (f < 0.20000000000000001D) {
                        f = 0.2F;
                    }
                    draw_carselect_stat("Top Speed:", leftside_statbar_x, statbar_initial_y, f);

                    f = (StatList.acelf[sc[0]][1] * StatList.acelf[sc[0]][0] * StatList.acelf[sc[0]][2]
                            * StatList.grip[sc[0]]) / 7700F;
                    if (f > 1.0F) {
                        f = 1.0F;
                    }
                    draw_carselect_stat("Acceleration:", leftside_statbar_x, statbar_initial_y + statbar_y_gap, f);

                    f = StatList.dishandle[sc[0]];
                    draw_carselect_stat("Handling:", leftside_statbar_x, statbar_initial_y + statbar_y_gap * 2, f);

                    f = (StatList.airc[sc[0]] * StatList.airs[sc[0]] * StatList.bounce[sc[0]] + 28F) / 139F;
                    if (f > 1.0F) {
                        f = 1.0F;
                    }
                    draw_carselect_stat("Stunts:", rightside_statbar_x, statbar_initial_y, f);

                    float f1 = 0.5F;
                    if (sc[0] == 9) {
                        f1 = 0.8F;
                    }
                    f = (StatList.moment[sc[0]] + f1) / 2.6F;
                    if (f > 1.0F) {
                        f = 1.0F;
                    }
                    draw_carselect_stat("Strength:", rightside_statbar_x, statbar_initial_y + statbar_y_gap, f);

                    f = StatList.outdam[sc[0]];
                    draw_carselect_stat("Endurance:", rightside_statbar_x, statbar_initial_y + statbar_y_gap * 2, f);
                }
                rd.drawImage(contin[pcontin], Utility.centeredImageX(contin[pcontin]), car_select_continue_button_y,
                        null);
            }
        } else {
            pback = 0;
            pnext = 0;
            gatey = 300;
            car_select_delay--;
            if (flipo > 10) {
                aconto[sc[0]].y -= 100;
                if (nextc) {
                    aconto[sc[0]].zy += 20;
                } else {
                    aconto[sc[0]].zy -= 20;
                }
            } else {
                if (flipo == 10) {
                    if (nextc) {
                        sc[0]++;
                        if (sc[0] == GameFacts.numberOfCars)
                            sc[0] = 0;
                    } else {
                        sc[0]--;
                        if (sc[0] == -1)
                            sc[0] = GameFacts.numberOfCars - 1;
                    }
                    aconto[sc[0]].z = 950;
                    aconto[sc[0]].y = -34 - aconto[sc[0]].grat - 1100;
                    aconto[sc[0]].x = 0;
                    aconto[sc[0]].zy = 0;
                }
                aconto[sc[0]].y += 100;
                System.out.println(flipo + ", " + aconto[sc[0]].y);
            }
            flipo--;
        }
        rd.setFont(new Font("SansSerif", Font.BOLD, 11));
        FontHandler.fMetrics = rd.getFontMetrics();
        drawcs(GameFacts.screenHeight - 5, "You can also use Keyboard Arrows and Enter to navigate.", 82, 90, 0, 3);
        if (control.right && car_select_delay == 0) {
            sm.play("tick");
            control.right = false;
            if (flatrstart > 1) {
                flatrstart = 0;
            }
            nextc = true;
            flipo = 10;
            car_select_delay = 3;
            // sc[0]++;
        }
        if (control.left && car_select_delay == 0) {
            sm.play("tick");
            control.left = false;
            if (flatrstart > 1) {
                flatrstart = 0;
            }
            nextc = false;
            flipo = 10;
            car_select_delay = 3;
            // sc[0]--;
        }
        if (control.handb || control.enter) {
            sm.play("tick");
            if (flipo == 0 && (sc[0] - 7) * 2 < unlocked) {
                lastload = -11;
                intertrack.stop();
                intertrack.unload();
                loadIntertrack("red_dream_nfmmix");
                Medium.crs = false;
                fase = Phase.NPLAYERSCHECK;
            }
            control.handb = false;
            control.enter = false;
        }
    }

    public void ctachm(int i, int j, int k, Control control) {
        if (fase == Phase.STAGESELECT) {
            if (k == 1) {
                if (over(next[0], i, j, stage_select_next_button_x, stage_select_next_back_button_y)) {
                    pnext = 1;
                }
                if (over(back[0], i, j, stage_select_back_button_x, stage_select_next_back_button_y)) {
                    pback = 1;
                }
                if (over(contin[0], i, j, Utility.centeredImageX(contin[0]), stage_select_continue_button_y)) {
                    pcontin = 1;
                }
            }
            if (k == 2) {
                if (pnext == 1) {
                    control.right = true;
                }
                if (pback == 1) {
                    control.left = true;
                }
                if (pcontin == 1) {
                    control.enter = true;
                }
            }
        }
        if (fase == Phase.ERRORLOADINGSTAGE) {
            if (k == 1 && over(contin[0], i, j, 290, 325)) {
                pcontin = 1;
            }
            if (k == 2 && pcontin == 1) {
                control.enter = true;
                pcontin = 0;
            }
        }
        if (fase == Phase.LOCKEDSTAGE) {
            if (k == 1 && over(back[0], i, j, stage_select_back_button_x, stage_select_next_back_button_y)) {
                pback = 1;
            }
            if (k == 2 && pback == 1) {
                control.enter = true;
                pback = 0;
            }
        }
        if (fase == Phase.PREGAME) {
            if (k == 1 && (over(star[0], i, j, 294, 360) || over(star[0], i, j, 294, 270))) {
                pstar = 2;
            }
            if (k == 2 && pstar == 2) {
                control.enter = true;
                pstar = 1;
            }
        }
        if (fase == Phase.CARSELECT) {
            if (k == 1) {
                if (over(next[0], i, j, car_select_next_button_x, car_select_next_back_button_y)) {
                    pnext = 1;
                }
                if (over(back[0], i, j, car_select_back_button_x, car_select_next_back_button_y)) {
                    pback = 1;
                }
                if (over(contin[0], i, j, Utility.centeredImageX(contin[0]), car_select_continue_button_y)) {
                    pcontin = 1;
                }
            }
            if (k == 2) {
                if (pnext == 1) {
                    control.right = true;
                }
                if (pback == 1) {
                    control.left = true;
                }
                if (pcontin == 1) {
                    control.enter = true;
                    pcontin = 0;
                }
            }
        }
        if (fase == Phase.POSTGAME) {
            lxm = i;
            lym = j;
            if (k == 1 && over(contin[0], i, j, Utility.centeredImageX(contin[0]), post_game_continue_button_y)) {
                pcontin = 1;
            }
            if (k == 2 && pcontin == 1) {
                control.enter = true;
                pcontin = 0;
            }
        }
        if (fase == Phase.PAUSEMENU) {
            if (k == 1) {
                if (overon(Utility.centeredWidthX(pausebox_op_0_width) + pausebox_op_x_correction,
                        pausebox_y + pausebox_option_start_off,
                        pausebox_op_0_width, pausebox_op_height, i, j)) {
                    opselect = 0;
                    shaded = true;
                }
                if (overon(Utility.centeredWidthX(pausebox_op_1_width) + pausebox_op_x_correction,
                        pausebox_y + pausebox_option_start_off + pausebox_option_gap,
                        pausebox_op_1_width, pausebox_op_height, i, j)) {
                    opselect = 1;
                    shaded = true;
                }
                if (overon(Utility.centeredWidthX(pausebox_op_2_width) + pausebox_op_x_correction,
                        pausebox_y + pausebox_option_start_off + pausebox_option_gap * 2,
                        pausebox_op_2_width, pausebox_op_height, i, j)) {
                    opselect = 2;
                    shaded = true;
                }
                if (overon(Utility.centeredWidthX(pausebox_op_3_width) + pausebox_op_x_correction,
                        pausebox_y + pausebox_option_start_off + pausebox_option_gap * 3,
                        pausebox_op_3_width, pausebox_op_height, i, j)) {
                    opselect = 3;
                    shaded = true;
                }
            }
            if (k == 2 && shaded) {
                control.enter = true;
                shaded = false;
            }
            if (k == 0 && (i != lxm || j != lym)) {
                if (overon(Utility.centeredWidthX(pausebox_op_0_width) + pausebox_op_x_correction,
                        pausebox_y + pausebox_option_start_off,
                        pausebox_op_0_width, pausebox_op_height, i, j)) {
                    opselect = 0;
                }
                if (overon(Utility.centeredWidthX(pausebox_op_1_width) + pausebox_op_x_correction,
                        pausebox_y + pausebox_option_start_off + pausebox_option_gap,
                        pausebox_op_1_width, pausebox_op_height, i, j)) {
                    opselect = 1;
                }
                if (overon(Utility.centeredWidthX(pausebox_op_2_width) + pausebox_op_x_correction,
                        pausebox_y + pausebox_option_start_off + pausebox_option_gap * 2,
                        pausebox_op_2_width, pausebox_op_height, i, j)) {
                    opselect = 2;
                }
                if (overon(Utility.centeredWidthX(pausebox_op_3_width) + pausebox_op_x_correction,
                        pausebox_y + pausebox_option_start_off + pausebox_option_gap * 3,
                        pausebox_op_3_width, pausebox_op_height, i, j)) {
                    opselect = 3;
                }
                lxm = i;
                lym = j;
            }
        }
        if (fase == Phase.MAINMENU) {
            if (k == 1) {
                if (overon(Utility.centeredWidthX(main_menu_op_0_width), main_menu_op_0_y, main_menu_op_0_width,
                        main_menu_button_height, i, j)) {
                    opselect = 0;
                    shaded = true;
                }
                if (overon(Utility.centeredWidthX(main_menu_op_1_width), main_menu_op_1_y, main_menu_op_1_width,
                        main_menu_button_height, i, j)) {
                    opselect = 1;
                    shaded = true;
                }
                if (overon(Utility.centeredWidthX(main_menu_op_2_width), main_menu_op_2_y, main_menu_op_2_width,
                        main_menu_button_height, i, j)) {
                    opselect = 2;
                    shaded = true;
                }
            }
            if (k == 2 && shaded) {
                control.enter = true;
                shaded = false;
            }
            if (k == 0 && (i != lxm || j != lym)) {
                if (overon(Utility.centeredWidthX(main_menu_op_0_width), main_menu_op_0_y, main_menu_op_0_width,
                        main_menu_button_height, i, j)) {
                    opselect = 0;
                }
                if (overon(Utility.centeredWidthX(main_menu_op_1_width), main_menu_op_1_y, main_menu_op_1_width,
                        main_menu_button_height, i, j)) {
                    opselect = 1;
                }
                if (overon(Utility.centeredWidthX(main_menu_op_2_width), main_menu_op_2_y, main_menu_op_2_width,
                        main_menu_button_height, i, j)) {
                    opselect = 2;
                }
                lxm = i;
                lym = j;
            }
        }
        if (fase == Phase.INSTRUCTIONS) {
            if (flipo >= 1 && flipo <= 13) {
                if (k == 1 && over(next[0], i, j, instructions_next_button_x, instructions_next_back_button_y)) {
                    pnext = 1;
                }
                if (k == 2 && pnext == 1) {
                    control.right = true;
                    pnext = 0;
                }
            }
            if (flipo >= 3 && flipo <= 15) {
                if (k == 1 && over(back[0], i, j, instructions_back_button_x, instructions_next_back_button_y)) {
                    pback = 1;
                }
                if (k == 2 && pback == 1) {
                    control.left = true;
                    pback = 0;
                }
            }
            if (flipo == 15) {
                if (k == 1 && over(contin[0], i, j, instructions_continue_button_x, instructions_next_back_button_y)) {
                    pcontin = 1;
                }
                if (k == 2 && pcontin == 1) {
                    control.enter = true;
                    pcontin = 0;
                }
            }
        }
        if (fase == Phase.CREDITS) {

            if (k == 1 && over(next[0], i, j, Utility.centeredImageX(next[0]), credits_next_button_y)) {
                pnext = 1;
            }

            if (k == 2 && pnext == 1) {
                control.enter = true;
                pnext = 0;
            }
        }
    }

    private void stopairs() {
        int i = 0;
        do {
            sm.stop("air" + i);
        } while (++i < 6);
    }

    @Override
    public void run() {
        while (runtyp != 0) {
            if (runtyp >= 1 && runtyp <= 17) {
                hipnoload(runtyp, false);
            }
            if (runtyp == 176) {
                loading();
            }
            app.repaint();
            try {
                Thread.sleep(20L);
            } catch (InterruptedException _ex) {
            }
        }
    }

    public void loadingfailed(CheckPoints checkpoints, Control control, String error) {
        trackbg(false);
        rd.drawImage(select, Utility.centeredImageX(select), stage_select_text_y, null);
        if (checkpoints.stage != 1) {
            rd.drawImage(back[pback], stage_select_back_button_x, stage_select_next_back_button_y, null);
        }
        if (checkpoints.stage != GameFacts.numberOfStages) {
            rd.drawImage(next[pnext], stage_select_next_button_x, stage_select_next_back_button_y, null);
        }
        rd.setFont(new Font("SansSerif", Font.BOLD, 13));
        FontHandler.fMetrics = rd.getFontMetrics();
        drawcs(140, "Error Loading Stage " + checkpoints.stage, 200, 0, 0, 3);
        drawcs(170, error, 177, 177, 177, 3);
        drawcs(220, "Check console for more info.", 177, 177, 177, 3);
        rd.drawImage(contin[pcontin], Utility.centeredImageX(contin[pcontin]), stage_select_continue_button_y, null);
        rd.setFont(new Font("SansSerif", Font.BOLD, 11));
        FontHandler.fMetrics = rd.getFontMetrics();
        drawcs(GameFacts.screenHeight - 5, "You can also use Keyboard Arrows and Enter to navigate.", 82, 90, 0, 3);
        if (control.handb || control.enter) {
            fase = Phase.NPLAYERSCHECK;
            control.handb = false;
            control.enter = false;
        }
        if (control.right && checkpoints.stage < 17) {
            if (checkpoints.stage != unlocked) {
                checkpoints.stage++;
                fase = Phase.NPLAYERSCHECK;
                control.right = false;
            } else {
                fase = Phase.LOCKEDSTAGE;
                lockcnt = 100;
                control.right = false;
            }
        }
        if (control.left && checkpoints.stage > 1) {
            checkpoints.stage--;
            fase = Phase.NPLAYERSCHECK;
            control.left = false;
        }
    }

    public void hipnoload(int i, boolean flag) {
        int j = (int) (230F - 230F * (Medium.snap[0] / (100F * hipno)));
        if (j > 255) {
            j = 255;
        }
        if (j < 0) {
            j = 0;
        }
        int l = (int) (230F - 230F * (Medium.snap[1] / (100F * hipno)));
        if (l > 255) {
            l = 255;
        }
        if (l < 0) {
            l = 0;
        }
        int j1 = (int) (230F - 230F * (Medium.snap[2] / (100F * hipno)));
        if (j1 > 255) {
            j1 = 255;
        }
        if (j1 < 0) {
            j1 = 0;
        }
        if (i == 1) {
            j = 230;
            l = 230;
            j1 = 230;
        }
        rd.setColor(new Color(j, l, j1));
        rd.fillRect(0, 0, GameFacts.screenWidth, GameFacts.screenHeight);
        rd.setFont(new Font("SansSerif", Font.BOLD, 13));
        FontHandler.fMetrics = rd.getFontMetrics();
        drawcs(25, asay, 0, 0, 0, 3);

        /* this is such a stupid way to accommodate for the stages with hints... */
        byte byte0 = -90;
        if (i == 1 || i == 2 || i == 3 || i == 4 || i == 7 || i == 8 || i == 9 || i == 10 || i == 12
                || i == 13 || i == 16) {
            byte0 = 0;
        }
        if (byte0 == 0) {
            if (dudo > 0) {
                if (aflk) {
                    if (RadicalRand.bool()) {
                        duds = (int) (RadicalRand.random() * 3D);
                    } else {
                        duds = (int) (RadicalRand.random() * 2D);
                    }
                    aflk = false;
                } else {
                    aflk = true;
                }
                dudo--;
            } else {
                duds = 0;
            }
            int hint_all_width = dude[duds].getWidth(null) + flaot.getWidth(null);
            int start_x = Utility.centeredWidthX(hint_all_width);
            int flaot_x = start_x + dude[duds].getWidth(null);
            int text_x = flaot_x + 70;
            rd.drawImage(dude[duds], start_x, 10, null);
            rd.drawImage(flaot, flaot_x, 42, null);
            int k = (int) (80F - 80F * (Medium.snap[0] / (50F * hipno)));
            if (k > 255) {
                k = 255;
            }
            if (k < 0) {
                k = 0;
            }
            int i1 = (int) (80F - 80F * (Medium.snap[1] / (50F * hipno)));
            if (i1 > 255) {
                i1 = 255;
            }
            if (i1 < 0) {
                i1 = 0;
            }
            int k1 = (int) (80F - 80F * (Medium.snap[2] / (50F * hipno)));
            if (k1 > 255) {
                k1 = 255;
            }
            if (k1 < 0) {
                k1 = 0;
            }
            if (i == 1) {
                k = 80;
                i1 = 80;
                k1 = 80;
            }
            rd.setColor(new Color(k, i1, k1));
            rd.setFont(new Font("SansSerif", Font.BOLD, 13));
            if (i == 1) {
                rd.drawString("Hey!  Don't forget, to complete a lap you must pass through", text_x, 67);
                rd.drawString("all checkpoints in the track!", text_x, 87);
            }
            if (i == 2) {
                rd.drawString("Remember, the more power you have the faster your car will be!", text_x, 67);
            }
            if (i == 3) {
                rd.drawString("Watch out!  Look out!  The policeman might be out to get you!", text_x, 67);
                rd.drawString("Don't upset him or you'll be arrested!", text_x, 87);
                rd.drawString("Better run, run, run.", text_x, 127);
            }
            if (i == 4) {
                rd.drawString("Don't waste your time.  Waste them instead!", text_x, 67);
                rd.drawString("Try a taste of sweet revenge here (if you can)!", text_x, 87);
                rd.drawString("Press [ A ] to make the guidance arrow point to cars instead of to", text_x, 127);
                rd.drawString("the track.", text_x, 147);
            }
            if (i == 7) {
                rd.drawString("Welcome to the realm of the king...", text_x, 67);
                rd.drawString("The key word here is 'POWER'.  The more you have of it the faster", text_x, 107);
                rd.drawString("and STRONGER you car will be!", text_x, 127);
            }
            if (i == 8) {
                rd.drawString("Watch out, EL KING is out to get you now!", text_x, 67);
                rd.drawString("He seems to be seeking revenge?", text_x, 87);
                rd.drawString("(To fly longer distances in the air try drifting your car on the ramp", text_x, 127);
                rd.drawString("before take off).", text_x, 147);
            }
            if (i == 9) {
                rd.drawString("It\u2019s good to be the king!", text_x, 67);
            }
            if (i == 10) {
                rd.drawString("Remember, forward loops give your car a push forwards in the air", text_x, 67);
                rd.drawString("and help in racing.", text_x, 87);
                rd.drawString("(You may need to do more forward loops here.  Also try keeping", text_x, 127);
                rd.drawString("your power at maximum at all times.  Try not to miss a ramp).", text_x, 147);
            }
            if (i == 12) {
                rd.drawString("Watch out!  Beware!  Take care!", text_x, 67);
                rd.drawString("MASHEEN is hiding out there some where, don't get mashed now!", text_x, 87);
            }
            if (i == 13) {
                rd.drawString("Anyone for a game of Digger?!", text_x, 67);
                rd.drawString("You can have fun using MASHEEN here!", text_x, 87);
            }
            if (i == 16) {
                rd.drawString("This is it!  This is the toughest stage in the game!", text_x, 67);
                rd.drawString("This track is actually a 4D object projected onto the 3D world.", text_x, 107);
                rd.drawString("It's been broken down, separated and, in many ways, it is also a", text_x, 127);
                rd.drawString("maze!  GOOD LUCK!", text_x, 147);
            }
        }
        rd.drawImage(loadingmusic, Utility.centeredImageX(loadingmusic), 180 + byte0, null);
        rd.setFont(new Font("SansSerif", Font.BOLD, 11));
        FontHandler.fMetrics = rd.getFontMetrics();
        if (!flag) {
            // drawcs(315 + byte0, "" + sndsize[i - 1] + " KB", 0, 0, 0, 3);
            drawcs(350 + byte0, " Please Wait...", 0, 0, 0, 3);
        } else {
            drawcs(340 + byte0, "Loading complete!  Press Start to begin...", 0, 0, 0, 3);
            rd.drawImage(star[pstar], Utility.centeredImageX(star[pstar]), 360 + byte0, null);
            if (pstar != 2) {
                if (pstar == 0) {
                    pstar = 1;
                } else {
                    pstar = 0;
                }
            }
        }
    }

    private Image loadopsnap(Image image, int i, int j) {
        int k = image.getHeight(null);
        int l = image.getWidth(null);
        int ai[] = new int[l * k];
        PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, l, k, ai, 0, l);
        try {
            pixelgrabber.grabPixels();
        } catch (InterruptedException _ex) {
        }
        int i1 = 0;
        if (j == 1) {
            i1 = ai[61993];
        }
        for (int j1 = 0; j1 < l * k; j1++) {
            if (ai[j1] != ai[j]) {
                Color color = new Color(ai[j1]);
                int k1 = 0;
                int l1 = 0;
                int i2 = 0;
                if (j == 1 && ai[j1] == i1) {
                    k1 = (int) (237F - 237F * (Medium.snap[0] / (150F * hipno)));
                    if (k1 > 255) {
                        k1 = 255;
                    }
                    if (k1 < 0) {
                        k1 = 0;
                    }
                    l1 = (int) (237F - 237F * (Medium.snap[1] / (150F * hipno)));
                    if (l1 > 255) {
                        l1 = 255;
                    }
                    if (l1 < 0) {
                        l1 = 0;
                    }
                    i2 = (int) (237F - 237F * (Medium.snap[2] / (150F * hipno)));
                    if (i2 > 255) {
                        i2 = 255;
                    }
                    if (i2 < 0) {
                        i2 = 0;
                    }
                    if (i == 1) {
                        k1 = 250;
                        l1 = 250;
                        i2 = 250;
                    }
                } else {
                    k1 = (int) (color.getRed()
                            - color.getRed() * (Medium.snap[0] / (50F * hipno)));
                    if (k1 > 255) {
                        k1 = 255;
                    }
                    if (k1 < 0) {
                        k1 = 0;
                    }
                    l1 = (int) (color.getGreen()
                            - color.getGreen() * (Medium.snap[1] / (50F * hipno)));
                    if (l1 > 255) {
                        l1 = 255;
                    }
                    if (l1 < 0) {
                        l1 = 0;
                    }
                    i2 = (int) (color.getBlue()
                            - color.getBlue() * (Medium.snap[2] / (50F * hipno)));
                    if (i2 > 255) {
                        i2 = 255;
                    }
                    if (i2 < 0) {
                        i2 = 0;
                    }
                    if (i == 1) {
                        k1 = color.getRed();
                        l1 = color.getGreen();
                        i2 = color.getBlue();
                    }
                }
                Color color1 = new Color(k1, l1, i2);
                ai[j1] = color1.getRGB();
            }
        }

        return app.createImage(new MemoryImageSource(l, k, ai, 0, l));
    }

    private void carsbginflex() {
        flatr = 0;
        flyr = (int) (Medium.random() * 160F - 80F);
        flyrdest = (int) ((flyr + Medium.random() * 160F) - 80F);
        flang = 1;
        flangados = (int) (Medium.random() * 6F + 2.0F);
        blackn = 0.0F;
        blacknados = Medium.random() * 0.4F;
        PixelGrabber pixelgrabber = new PixelGrabber(carsbg, 0, 0, GameFacts.screenWidth, GameFacts.screenHeight, flexpix, 0, GameFacts.screenWidth);
        try {
            pixelgrabber.grabPixels();
        } catch (InterruptedException _ex) {
        }
    }

    public void setGraphics(Graphics2D rd) {
        this.rd = rd;
    }
}

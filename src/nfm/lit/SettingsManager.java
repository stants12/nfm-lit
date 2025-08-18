package nfm.lit;

import java.io.*;
import java.util.Properties;

public class SettingsManager {
    private static final String SETTINGS_FILE = "data/user/config.cfg";
    private Properties props = new Properties();

    public SettingsManager() {
        load();
    }

    public void setMenuStage(int stage) {
        props.setProperty("menuStage", String.valueOf(stage));
    }

    public int getMenuStage() {
        return Integer.parseInt(props.getProperty("menuStage", "1"));
    }

    public void setMenuMusic(String music) {
        props.setProperty("menuMusic", music);
    }

    public String getMenuMusic() {
        return props.getProperty("menuMusic", "stages");
    }

    public void save() {
        try (FileOutputStream out = new FileOutputStream(SETTINGS_FILE)) {
            props.store(out, "config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try (FileInputStream in = new FileInputStream(SETTINGS_FILE)) {
            props.load(in);
        } catch (IOException e) {
            // Defaults will be used if file doesn't exist
        }
    }
}
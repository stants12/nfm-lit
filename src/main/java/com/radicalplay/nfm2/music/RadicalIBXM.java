package com.radicalplay.nfm2.music;

import fallk.logmaster.HLogger;
import ibxm.Channel;
import ibxm.IBXM;
import ibxm.Module;
import ibxm.WavInputStream;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class RadicalIBXM implements RadicalMusic {
    private AudioInputStream sound;
    private Clip clip;

    public RadicalIBXM(byte[] data) {
        try {
            IBXM ibxm = new IBXM(new Module(new ByteArrayInputStream(data)), 48000);
            ibxm.setInterpolation(Channel.NEAREST);

            int duration = ibxm.calculateSongDuration();
            WavInputStream in = new WavInputStream(ibxm, duration, 0);

            sound = AudioSystem.getAudioInputStream(new BufferedInputStream(in));
            clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, sound.getFormat()));
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            HLogger.error(e);
        }
    }

    @Override
    public void setPaused(boolean paused) {
        if (clip != null) {
            if (paused) {
                clip.stop();
            } else {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }

    @Override
    public void play() {
        if (clip != null) {
            try {
                if (!clip.isOpen()) {
                    clip.open(sound);
                }
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception ex) {
                HLogger.error(ex);
            }
        }
    }

    @Override
    public void unload() {
        if (clip != null) {
            clip.close();

            try {
                sound.close();
            } catch (IOException e) {
                HLogger.error(e);
            }

            clip = null;
            sound = null;
        }
    }
}

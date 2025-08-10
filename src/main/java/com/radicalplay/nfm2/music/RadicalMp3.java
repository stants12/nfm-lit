package com.radicalplay.nfm2.music;

import fallk.logmaster.HLogger;
import ibxm.Channel;
import ibxm.IBXM;
import ibxm.Module;
import ibxm.WavInputStream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.PausablePlayer;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class RadicalMp3 implements RadicalMusic {
    private PausablePlayer player;

    public RadicalMp3(byte[] data) {
        try {
            player = new PausablePlayer(new ByteArrayInputStream(data));
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPaused(boolean paused) {
        if (player != null) {
            if (paused) {
                player.pause();
            } else {
                player.resume();
            }
        }
    }

    @Override
    public void play() {
        if (player != null) {
            try {
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void unload() {
        if (player != null) {
            player.close();
        }
    }
}

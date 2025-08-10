package com.radicalplay.nfm2.music;

public interface RadicalMusic {
    void setPaused(boolean paused);

    default void stop() {
        setPaused(true);
    }

    default void resume() {
        setPaused(false);
    }

    void play();

    void unload();
}

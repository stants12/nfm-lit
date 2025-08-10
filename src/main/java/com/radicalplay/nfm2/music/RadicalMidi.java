package com.radicalplay.nfm2.music;

import fallk.logmaster.HLogger;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import java.io.ByteArrayInputStream;

public class RadicalMidi implements RadicalMusic {
    private Sequencer sequencer;
    private boolean paused;
    private final ByteArrayInputStream fi;

    public RadicalMidi(final byte[] data) {
        fi = new ByteArrayInputStream(data);
        try {
            // Obtains the default Sequencer connected to a default device.
            sequencer = MidiSystem.getSequencer();
            // Opens the device, indicating that it should now acquire any
            // system resources it requires and become operational.
            sequencer.open();

        } catch (final Exception ex) {
            ex.printStackTrace();
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see com.trashers.skyline.singleplayer.RadicalMusic#resume()
     */
    @Deprecated
    @Override
    /**
     * Resumes playback of the midi.
     */
    public void resume() {
        play();
    }

    /**
     * Begins playing the midi.
     */
    @Override
    public void play() {
        try {
            // Sets the current sequence on which the sequencer operates.
            // The stream must point to MIDI file data.
            sequencer.setSequence(fi);

            // loop forever
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);

            // Starts playback of the MIDI data in the currently loaded
            // sequence.
            sequencer.start();
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Sets the paused state. Music may not immediately pause.
     *
     * @param paused the new paused
     */
    @Override
    public void setPaused(final boolean paused) {
        if (this.paused != paused && sequencer != null && sequencer.isOpen()) {
            this.paused = paused;
            if (paused) {
                sequencer.stop();
            } else {
                sequencer.start();
            }
        }
    }

    /**
     * Stops the midi sequencer.
     */
    @Override
    @Deprecated
    public void stop() {
        HLogger.info("Stopping Midi file...");
        try {
            sequencer.stop();
        } catch (final Exception ex) {
            HLogger.error("Error stopping Midi file:");
            ex.printStackTrace();
        }
    }

    /**
     * Closes the midi sequencer.
     */
    @Override
    public void unload() {
        HLogger.info("Stopping Midi file...");
        try {
            sequencer.stop();
        } catch (final Exception ex) {
            HLogger.error("Error stopping Midi file:");
            ex.printStackTrace();
        }
    }
}

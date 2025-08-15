package nfm.lit.audio;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.zip.ZipInputStream;

import nfm.lit.GameSparker;
import nfm.lit.xtGraphics;

/**
 * This class is used to load music files for NativeBASS from a .zip by
 * temporarily extracting it. <br>
 * <br>
 * This is a utility class, so it can't be inherited.
 *
 * @author Rafael
 */
public final class TrackZipLoader {

    public static RadicalMusic loadZip(String file, final boolean download) throws FileNotFoundException, IOException {

        if (download) {
            downloadMusic(file);
            file = "data/music/temp_" + file + ".radq";
        }

        final File f = new File(file);

        file = file.toLowerCase(); // TODO check if causes problems on linux
        // systems

        if (file.endsWith(".radq") || file.endsWith(".zipo") || file.endsWith(".zip")) {
            if (f.exists()) {
                final ZipInputStream zis = new ZipInputStream(new FileInputStream(f));
                final String zipmodulename = zis.getNextEntry().getName().toLowerCase();
                if (zipmodulename.endsWith(".mod") || zipmodulename.endsWith(".xm") || zipmodulename.endsWith(".s3m") // nfmm
                        // supported
                        // files
                        || zipmodulename.endsWith(".it") || zipmodulename.endsWith(".mtm")
                        || zipmodulename.endsWith(".umx") || zipmodulename.endsWith(".mo3") // nativebass-supported
                        // modules
                        || zipmodulename.endsWith(".mp3") || zipmodulename.endsWith(".mp2")
                        || zipmodulename.endsWith(".mp1") || zipmodulename.endsWith(".ogg") // nativebass-supported
                        // audio
                        // formats
                        || zipmodulename.endsWith(".wav") || zipmodulename.endsWith(".aif") // nativebass-supported
                    // audio
                    // formats
                        ) {

                    final File themodfile = extractzip(zis, zipmodulename);

                    return new RadicalBASS(themodfile);

                } else if (zipmodulename.endsWith(".mid")) { // prefer
                    // radicalmidi
                    // over
                    // nativebass
                    // midi since
                    // it's javax
                    // (not a
                    // library)

                    final File themodfile = extractzip(zis, zipmodulename);

                    return new RadicalMidi("" + themodfile); // it's ok to rely
                    // on .zip and
                    // .mid here
                    // since we know
                    // it ends with
                    // a zip and a
                    // mid
                }
                zis.close();
            }

			/*
             * if (new File("music/stage" + i + ".mid").exists()) { strack = new
			 * RadicalMidi("music/stage" + i + ".mid"); } else if (new
			 * File("music/stage" + i + ".mp3").exists()) { strack = new
			 * RadicalMp3("music/stage" + i + ".mp3"); } else if (new
			 * File("music/stage" + i + ".ogg").exists()) { strack = new
			 * RadicalOgg("music/stage" + i + ".ogg"); }
			 */
        }
        throw new FileNotFoundException("radq file " + file + " does not exist");
        // old fallback method
        // return new RadicalMod(file, 550, 8000, 125, false, false);

    }
    
    /**
     * It's generally a bad idea to put large music files inside zips as they would
     * take a long time to extract, download and read from them, so
     * this method loads the music (mainly midi supported ones) directly
     * for reduced loading times.
     * This may not be the most optimised/efficient way of doing this,
     * so if you have any better ideas on this, let me know.
     * -ACV
     * @param file The string filepath to the music
     * @return load music via RadicalBASS class or RadicalMidi if Midi
     * @throws FileNotFoundException if the file does not exist
     * @throws IOException
     */
    public static RadicalMusic loadMusic(String file) throws FileNotFoundException, IOException
    {
    	final File f = new File(file);
    	
    	if(file.endsWith(".ogg") || file.endsWith(".mp3") || file.endsWith(".wav")
            || file.endsWith(".mod")
            || file.endsWith(".xm")
            || file.endsWith(".it")
            || file.endsWith(".s3m")
            )
    	{
    		if(f.exists())
    		{
    			return new RadicalBASS(f);
    		}
    	} else
    	if(file.endsWith(".mid"))
    	{
    		if(f.exists())
    		{
    			return new RadicalMidi(file);
    		}
    	}
    	
    	throw new FileNotFoundException(file + " does not exist");
    }
    
    private static void downloadMusic(final String songname) throws IOException {
        // prepares download
        String string = "http://multiplayer.needformadness.com/tracks/music/" + songname + ".zip";
        string = string.replace(' ', '_');
        final URL url = new URL(string);
        final int connLength = url.openConnection().getContentLength();

        final File themodfile = new File("data/music/temp_" + songname + ".zip");
        if (themodfile.exists()) {
            themodfile.delete();
        }

        // writes to file
        final DataInputStream datainputstream = new DataInputStream(url.openStream());
        final byte[] is = new byte[connLength];
        datainputstream.readFully(is);
        datainputstream.close();
        final FileOutputStream fileoutputstream = new FileOutputStream(themodfile);
        fileoutputstream.write(is);
        fileoutputstream.close();
    }

    private static File extractzip(final ZipInputStream zis, final String zipmodulename)
            throws FileNotFoundException, IOException {
        final File themodfile = new File("data/music/temp_" + zipmodulename);

        if (themodfile.exists()) {
            themodfile.delete();
        }

        // SIDE NOTE
        // although it may seem smart to have it simply load the file that's
        // already there
        // if it hasn't been deleted,
        // it would cause conflicts if 2 stages were to have the same
        // music file name inside the zip.
        // a potential solution would be to extract the module file and give it
        // the name
        // of the zip file instead of the zip entry's file, but fuck that(tm)

        final FileOutputStream fout = new FileOutputStream("data/music/temp_" + zipmodulename);
        for (int c = zis.read(); c != -1; c = zis.read()) {
            fout.write(c);
        }
        zis.closeEntry();
        zis.close();
        fout.close();

        // delete file
        themodfile.deleteOnExit(); // TODO check if there's a way to use
        // delete()

        return themodfile;
    }

    // XXX ---- VERY HACKY WAY TO DELETE FILES WHEN FILE.DELETEONEXIT CAN'T DO
    // IT
    public static void deletetempfiles() throws IOException {
        if (xtGraphics.loadedt) {
            xtGraphics.strack.setPaused(true); // potentially unnecessary
            xtGraphics.strack.unload();
        }

        Files.walk(Paths.get("./data/music/")).forEach(new Consumer<Path>() {
            @Override
            public void accept(final Path filePath) {// potentially non-working
                if (Files.isRegularFile(filePath)) {
                    if (GameSparker.DEBUG) {
                        System.out.println(filePath);
                    }
                    if (filePath.toFile().getName().indexOf("temp_") != -1) {
                        filePath.toFile().delete();
                    }
                }
            }
        });
    }

}

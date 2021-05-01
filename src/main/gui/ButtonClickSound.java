package gui;

import javax.sound.sampled.*;
import java.io.File;

// represents the clicking sound played when a button is pressed
public class ButtonClickSound {
    private  Clip line;

    // EFFECTS: loads the sound file of the clicking noise
    public ButtonClickSound() {
        try {
            String sep = System.getProperty("file.separator");
            File soundFile = new File(System.getProperty("user.dir") + sep
                    + "resources" + sep + "click.wav");
            AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = sound.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                line = null;
            } else {
                line = (Clip) AudioSystem.getLine(info);
                //line.setLoopPoints(0, -1);
                line.open(sound);
            }
        } catch (Exception ex) {
            line = null;
        }
    }

    // MODIFIES: this
    // EFFECTS: restarts the sound file and plays it
    public void playSound() {
        line.setMicrosecondPosition(0);
        line.start();
    }
}

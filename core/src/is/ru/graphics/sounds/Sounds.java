package is.ru.graphics.sounds;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

/**
 * Created by Kárii on 24.10.2016.
 */
public class Sounds {
    public static Clip clip = null;
    public static void playSound() throws Exception{
        if (clip != null && clip.isOpen()) clip.close();
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music/backgroundSound.wav").getAbsoluteFile());
        clip = AudioSystem.getClip();

        clip.open(audioInputStream);
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(0f);

        System.out.println(clip.getFrameLength() + " | " + clip.getFramePosition());
        clip.start();


    }
}

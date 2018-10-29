// Andrew Cash
// Programming Paradigms
// Fall 2018
// Assignment 6

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;

class Sounds
{

    Sounds()
    {
    }

    void playCoinSound() {
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/coinGet.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }

        catch(Exception ex)
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    void playCoinCollect() {
        try
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/collectCoin.wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }

        catch(Exception ex)
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
            }
        }


}

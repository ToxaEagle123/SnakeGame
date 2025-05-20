package org.example;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sounds {
    public static void play(String fileName) {
        try {
            URL soundURL = Sounds.class.getResource("/sounds/" + fileName);
            if (soundURL == null) {
                System.err.println("Музыка/звук" + fileName + " не найден. Напишите разработчику.");
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
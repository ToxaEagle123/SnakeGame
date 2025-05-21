package org.example;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sounds {
    private static final Logger logger = Logger.getLogger(Sounds.class.getName());

    public static void play(String fileName) {
        try {
            URL soundURL = Sounds.class.getResource("/sounds/" + fileName);
            if (soundURL == null) {
                logger.warning("Музыка/звук" + fileName + " не найден. Напишите разработчику.");
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            logger.log(Level.SEVERE, "Ошибка при попытке воспроизвести звук " + fileName, e);
        }
    }
}
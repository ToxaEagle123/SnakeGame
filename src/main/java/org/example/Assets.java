package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Assets {
    public static Image headUp, headDown, headLeft, headRight, body, apple, errorImg, backgroundGame, backgroundMenu, playButton, exitButton, highscoreButton, backButton;
    public static Font pixelFont;

    public static void load() {
        headUp = loadImage("/headUp.png");
        headDown = loadImage("/headDown.png");
        headLeft = loadImage("/headLeft.png");
        headRight = loadImage("/headRight.png");

        body = loadImage("/body.png");

        apple = loadImage("/apple.png");

        pixelFont = FontLoader.loadFont("pixelFont.ttf", 24f);

        backgroundGame = loadImage("/backgroundGame.png");
        backgroundMenu = loadImage("/backgroundMenu.png");

        playButton = loadImage("/playButton.png");
        exitButton = loadImage("/exitButton.png");
        highscoreButton = loadImage("/highscoreButton.png");
        backButton = loadImage("/backButton.png");

        errorImg = loadImage("/errorImg.png");
    }

    private static Image loadImage(String path) {
        URL resource = Assets.class.getResource(path);
        if (resource == null) {
            System.err.println("Картинку съела змейка " + path + ". Напишите разработчику.");

            URL errorRes = Assets.class.getResource("/errorImg.png");
            if (errorRes != null) {
                return new ImageIcon(errorRes).getImage();
            } else {
                System.err.println("Файл errorImg.png тоже отсутствует! Да как так, блин.");
                return new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            }
        }
        return new ImageIcon(resource).getImage();
    }
}
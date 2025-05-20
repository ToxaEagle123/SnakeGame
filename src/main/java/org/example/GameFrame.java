package org.example;

import javax.swing.*;
import java.net.URL;

public class GameFrame extends JFrame {
    MainMenu mainMenuPanel;
    GamePanel gamePanel;

    public GameFrame() {
        Assets.load();

        setTitle("Snake The Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        mainMenuPanel = new MainMenu(this);
        gamePanel = new GamePanel();

        setContentPane(mainMenuPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        URL logoUrl = Main.class.getResource("/SnakeGameLogo.png");
        if (logoUrl != null) {
            setIconImage(new ImageIcon(logoUrl).getImage());
        } else {
            System.err.println("Ошибка! Картинка с лого была съедена змейкой.");
        }

        setVisible(true); // Отображение окна в конце

    }

    public void showGame() {
        setContentPane(gamePanel);
        revalidate();
        gamePanel.requestFocusInWindow();
    }
}

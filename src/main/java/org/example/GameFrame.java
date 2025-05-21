package org.example;

import javax.swing.*;
import java.net.URL;

public class GameFrame extends JFrame {
    MainMenu mainMenuPanel;
    GamePanel gamePanel;

    HighscoreManager highscoreManager;
    HighscorePanel highscorePanel;

    public GameFrame() {
        Assets.load();

        setTitle("Snake The Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        highscoreManager = new HighscoreManager();

        mainMenuPanel = new MainMenu(this);
        gamePanel = new GamePanel(this);
        highscorePanel = new HighscorePanel(this, highscoreManager);

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
        if (gamePanel != null) {
            gamePanel.stopGame();
            remove(gamePanel);
        }

        gamePanel = new GamePanel(this);
        setContentPane(gamePanel);
        revalidate();
        repaint();
        gamePanel.requestFocusInWindow();
    }

    public void showMainMenu() {
        getContentPane().removeAll();
        setContentPane(mainMenuPanel);
        revalidate();
        mainMenuPanel.repaint();
    }

    public void showHighscores() {
        highscorePanel = new HighscorePanel(this, highscoreManager);
        setContentPane(highscorePanel);
        revalidate();
        highscorePanel.refresh();
    }

    public HighscoreManager getHighscoreManager() {
        return highscoreManager;
    }
}

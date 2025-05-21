package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class HighscorePanel extends JPanel {
    private final GameFrame gameFrame;
    private final HighscoreManager highscoreManager;

    public HighscorePanel(GameFrame frame, HighscoreManager manager) {
        this.gameFrame = frame;
        this.highscoreManager = manager;

        setLayout(null);
        setPreferredSize(new Dimension(600, 600));

        JButton backButton = new JButton(new ImageIcon(Assets.backButton));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setOpaque(false);
        backButton.setBounds(200, 450, 200, 50);
        backButton.addActionListener((ActionEvent e) -> gameFrame.showMainMenu());
        add(backButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(Assets.backgroundMenu, 0, 0, getWidth(), getHeight(), this);

        Font pixelFont = FontLoader.loadFont("pixelFont.ttf", 24f);
        g.setFont(pixelFont);
        g.setColor(Color.WHITE);

        g.drawString("Рекорды", 220, 100);

        Font scoreFont = FontLoader.loadFont("pixelFont.ttf", 18f);
        g.setFont(scoreFont);

        List<Integer> scores = highscoreManager.getScores();
        if (scores.isEmpty()) {
            g.drawString("Рекорды отсутствуют, нужно играть!", 210, 150);
        } else {
            for (int i = 0; i < scores.size(); i++) {
                g.drawString((i + 1) + ". " + scores.get(i), 250, 150 + i * 40);
            }
        }
    }

    public void refresh() {
        repaint();
    }

}
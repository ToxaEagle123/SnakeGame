package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenu extends JPanel {
    private final GameFrame gameFrame;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Assets.backgroundMenu, 0, 0, getWidth(), getHeight(), this);
    }

    public MainMenu(GameFrame frame) {
        this.gameFrame = frame;
        setLayout(null);
        setBackground(Color.BLACK);

        // Размер окна
        setPreferredSize(new Dimension(600, 600));

        JButton playButton = new JButton(new ImageIcon(Assets.playButton));
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setFocusPainted(false);
        playButton.setOpaque(false);
        playButton.setBounds(200, 200, 200, 50);
        playButton.addActionListener((ActionEvent e) -> gameFrame.showGame());
        add(playButton);

        JButton exitButton = new JButton(new ImageIcon(Assets.exitButton));
        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(false);
        exitButton.setBounds(200, 270, 200, 50);
        exitButton.addActionListener((ActionEvent e) -> System.exit(0));
        add(exitButton);

        Font pixelFont = FontLoader.loadFont("pixelFont.ttf", 10f);

        JLabel versionLabel = new JLabel("v0.1.0 Beta");
        versionLabel.setFont(pixelFont);
        versionLabel.setForeground(Color.WHITE);
        versionLabel.setBounds(450, 540, 110, 20);
        add(versionLabel);
    }
}

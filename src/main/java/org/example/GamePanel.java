package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    final int WIDTH = 600;
    final int HEIGHT = 600;
    final int TILE = 32;

    Snake snake = new Snake();
    Food food;

    boolean isRunning = false;
    boolean isGameOver = false;

    Timer timer;
    Random rand = new Random();

    int score = 0;

    private GameFrame gameFrame;

    boolean isFreezing = false;
    long freezeStartTime;

    public GamePanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);
        start();
    }

    Direction currentDir = Direction.RIGHT; // Актуальное направление игрока
    Direction nextDir = Direction.RIGHT;    // Куда игрок хочет повернуть

    void start() {
        stopGame(); // << добавь эту строку в начало, чтобы гарантированно всё очистилось

        snake.reset();
        currentDir = Direction.RIGHT;
        nextDir = Direction.RIGHT;
        score = 0;
        spawnFood();
        isRunning = true;
        isGameOver = false;

        if (timer != null) timer.stop();
        timer = new Timer(150, this); // Таймер для изменения скорости
        timer.start();
    }

    void spawnFood() {
        int cols = WIDTH / TILE;
        int rows = HEIGHT / TILE;
        food = new Food(rand.nextInt(cols), rand.nextInt(rows));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Фон
        g.drawImage(Assets.backgroundGame, 0, 0, WIDTH, HEIGHT, this);
        if (isRunning) {
            // Вкусняшки для змейки 🍎
            g.drawImage(Assets.apple, food.x * TILE, food.y * TILE, TILE, TILE, this);

            // Змейка 🐍
            snake.draw(g, TILE, currentDir);

            // Счет 💯
            g.setColor(Color.white);
            g.setFont(Assets.pixelFont.deriveFont(18f));
            g.drawString("Счет: " + score, 10, 20);

        } else if (isGameOver) {
            gameOver(g);
        }
    }

    void move() {
        if ((currentDir == Direction.UP && nextDir != Direction.DOWN) ||
                (currentDir == Direction.DOWN && nextDir != Direction.UP) ||
                (currentDir == Direction.LEFT && nextDir != Direction.RIGHT) ||
                (currentDir == Direction.RIGHT && nextDir != Direction.LEFT)) {
            currentDir = nextDir;
        }

        Point newHead = snake.move(currentDir, WIDTH / TILE, HEIGHT / TILE);

        if (snake.isColliding(newHead)) {
            isRunning = false;
            isFreezing = true;
            freezeStartTime = System.currentTimeMillis();
            Sounds.play("gameOver.wav");
            return;
        }

        if (newHead.equals(food)) {
            Sounds.play("apple.wav");
            score++;
            snake.grow();
            spawnFood();
        }
    }

    void gameOver(Graphics g) {
        String msg = "Копец";
        String scoreMsg = "Счет: " + score;
        String restartMsg = "'Enter' - рестарт";
        String spaceMsg = "'Space' - главное меню";
        String escMsg = "'Esc' - выход из игры :(";
        FontMetrics fm = g.getFontMetrics();

        g.setColor(Color.red);
        g.setFont(Assets.pixelFont.deriveFont(40f));
        g.drawString(msg, (int) ((WIDTH - fm.stringWidth(msg)) / 2.9), HEIGHT / 2 - 40);

        g.setColor(Color.WHITE);
        g.setFont(Assets.pixelFont.deriveFont(23f));
        g.drawString(scoreMsg, (WIDTH - g.getFontMetrics().stringWidth(scoreMsg)) / 2, HEIGHT / 2);

        g.setFont(Assets.pixelFont.deriveFont(19f));
        g.drawString(restartMsg, (WIDTH - g.getFontMetrics().stringWidth(restartMsg)) / 2, HEIGHT / 2 + 40);

        g.setFont(Assets.pixelFont.deriveFont(19f));
        g.drawString(spaceMsg, (WIDTH - g.getFontMetrics().stringWidth(spaceMsg)) / 2, HEIGHT / 2 + 60);

        g.setFont(Assets.pixelFont.deriveFont(14f));
        g.drawString(escMsg, (WIDTH - g.getFontMetrics().stringWidth(escMsg)) / 2, 2 + 560);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            move();
        } else if (isFreezing) {
            long now = System.currentTimeMillis();
            if (now - freezeStartTime >= 2000) { // 2 секунды
                isFreezing = false;
                isGameOver = true;
                gameFrame.getHighscoreManager().addScore(score);
            }
        }

        repaint();
    }

    public void stopGame() {
        if (timer != null) {
            timer.stop();
        }
        isRunning = false;
        isGameOver = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!isRunning && isGameOver && key == KeyEvent.VK_ENTER) start();

        if (key == KeyEvent.VK_LEFT) nextDir = Direction.LEFT;
        else if (key == KeyEvent.VK_RIGHT) nextDir = Direction.RIGHT;
        else if (key == KeyEvent.VK_UP) nextDir = Direction.UP;
        else if (key == KeyEvent.VK_DOWN) nextDir = Direction.DOWN;
        else if (key == KeyEvent.VK_ESCAPE) System.exit(0);
        else if (key == KeyEvent.VK_SPACE) {
            stopGame();
            gameFrame.showMainMenu();
        }

    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}

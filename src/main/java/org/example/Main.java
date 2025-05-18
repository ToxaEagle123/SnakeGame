package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

public class Main extends JPanel implements ActionListener, KeyListener {
    final int WIDTH = 600;
    final int HEIGHT = 600;
    final int TILE = 32;

    Image headUp, headDown, headLeft, headRight;

    LinkedList<Point> snake = new LinkedList<>();
    Point food;

    char dir = 'R'; // направление: R, L, U, D
    boolean isRunning = false;
    boolean isGameOver = false;

    Timer timer;
    Random rand = new Random();

    int score = 0;

    public Main() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);
        headUp = new ImageIcon(getClass().getResource("/headUp.png")).getImage();
        headDown = new ImageIcon(getClass().getResource("/headDown.png")).getImage();
        headLeft = new ImageIcon(getClass().getResource("/headLeft.png")).getImage();
        headRight = new ImageIcon(getClass().getResource("/headRight.png")).getImage();

        start();
    }

    void start() {
        snake.clear();
        snake.add(new Point(5, 5));
        dir = 'R';
        score = 0;
        spawnFood();
        isRunning = true;
        isGameOver = false;

        if (timer != null) timer.stop();
        timer = new Timer(150, this);
        timer.start();
    }

    void spawnFood() {
        int cols = WIDTH / TILE;
        int rows = HEIGHT / TILE;
        food = new Point(rand.nextInt(cols), rand.nextInt(rows));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isRunning) {
            // Вкусняшки для змейки
            g.setColor(Color.red);
            g.fillOval(food.x * TILE, food.y * TILE, TILE, TILE);

            // Змейка
            for (int i = 0; i < snake.size(); i++) {
                Point p = snake.get(i);
                if (i == 0) {
                    Image currentHeadImage;
                    switch (dir) {
                        case 'U':
                            currentHeadImage = headUp;
                            break;
                        case 'D':
                            currentHeadImage = headDown;
                            break;
                        case 'L':
                            currentHeadImage = headLeft;
                            break;
                        case 'R':
                        default:
                            currentHeadImage = headRight;
                            break;
                    }
                    g.drawImage(currentHeadImage, p.x * TILE, p.y * TILE, TILE, TILE, this);
                } else {
                    g.setColor(Color.decode("#6abe30"));
                    g.fillRect(p.x * TILE, p.y * TILE, TILE, TILE);
                }
            }

            // Счет
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.drawString("Счет: " + score, 10, 20);
        } else if (isGameOver) {
            gameOver(g);
        }
    }

    void move() {
        Point head = snake.getFirst();
        int x = head.x;
        int y = head.y;

        switch (dir) {
            case 'U' -> y--;
            case 'D' -> y++;
            case 'L' -> x--;
            case 'R' -> x++;
        }

        int cols = WIDTH / TILE;
        int rows = HEIGHT / TILE;
        x = (x + cols) % cols;
        y = (y + rows) % rows;

        Point newHead = new Point(x, y);

        if (snake.contains(newHead)) {
            isRunning = false;
            isGameOver = true;
            return;
        }

        snake.addFirst(newHead);

        if (newHead.equals(food)) {
            score += 1;
            spawnFood();
        } else {
            snake.removeLast();
        }
    }

    void gameOver(Graphics g) {
        String msg = "Копец";
        String scoreMsg = "Счет: " + score;
        String restartMsg = "Нажми 'Enter' для рестарта";
        String escMsg = "'Esc' - для выхода :(";

        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics fm = g.getFontMetrics();
        g.drawString(msg, (WIDTH - fm.stringWidth(msg)) / 2, HEIGHT / 2 - 40);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 23));
        g.drawString(scoreMsg, (WIDTH - g.getFontMetrics().stringWidth(scoreMsg)) / 2, HEIGHT / 2);

        g.setFont(new Font("Arial", Font.PLAIN, 19));
        g.drawString(restartMsg, (WIDTH - g.getFontMetrics().stringWidth(restartMsg)) / 2, HEIGHT / 2 + 40);

        g.setFont(new Font ("Arial", Font.PLAIN, 14));
        g.drawString(escMsg, (WIDTH - g.getFontMetrics().stringWidth(escMsg)) / 2, 2 + 360);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (!isRunning && isGameOver && key == KeyEvent.VK_ENTER) {
            start();
        }

        if (key == KeyEvent.VK_LEFT && dir != 'R') dir = 'L';
        else if (key == KeyEvent.VK_RIGHT && dir != 'L') dir = 'R';
        else if (key == KeyEvent.VK_UP && dir != 'D') dir = 'U';
        else if (key == KeyEvent.VK_DOWN && dir != 'U') dir = 'D';
        else if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake The Game \uD83D\uDC0D");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new Main());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
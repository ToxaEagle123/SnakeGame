package org.example;

import java.awt.*;
import java.util.LinkedList;

public class Snake {
    LinkedList<Point> body = new LinkedList<>();

    public void reset() {
        body.clear();
        body.add(new Point(5, 5));
    }

    public Point move(Direction dir, int cols, int rows) {
        Point head = body.getFirst();
        int x = head.x;
        int y = head.y;

        switch (dir) {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
        }

        x = (x + cols) % cols;
        y = (y + rows) % rows;

        Point newHead = new Point(x, y);
        body.addFirst(newHead);
        body.removeLast();
        return newHead;
    }

    public boolean isColliding(Point newHead) {
        return body.subList(1, body.size()).contains(newHead);
    }

    public void grow() {
        body.addLast(body.getLast());
    }

    public void draw(Graphics g, int tileSize, Direction dir) {
        for (int i = 0; i < body.size(); i++) {
            Point p = body.get(i);
            if (i == 0) {
                Image headImg = switch (dir) {
                    case UP -> Assets.headUp;
                    case DOWN -> Assets.headDown;
                    case LEFT -> Assets.headLeft;
                    case RIGHT -> Assets.headRight;
                };
                g.drawImage(headImg, p.x * tileSize, p.y * tileSize, tileSize, tileSize, null);
            } else {
                g.drawImage(Assets.body, p.x * tileSize, p.y * tileSize, tileSize, tileSize, null);
            }
        }
    }
}
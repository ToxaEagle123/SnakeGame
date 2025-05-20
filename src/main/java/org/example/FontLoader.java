package org.example;

import java.awt.*;
import java.io.InputStream;

public class FontLoader {
    public static Font loadFont(String path, float size) {
        try {
            InputStream is = FontLoader.class.getResourceAsStream("/fonts/" + path);
            if (is == null) {
                System.err.println("Не найден шрифт: " + path);
                return new Font("Monospaced", Font.PLAIN, (int) size);
            }

            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("Monospaced", Font.PLAIN, (int) size);
        }
    }
}
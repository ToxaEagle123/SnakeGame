package org.example;

import java.awt.*;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FontLoader {
    private static final Logger logger = Logger.getLogger(FontLoader.class.getName());

    public static Font loadFont(String path, float size) {
        try {
            InputStream is = FontLoader.class.getResourceAsStream("/fonts/" + path);
            if (is == null) {
                logger.warning("Не найден шрифт: " + path);
                return new Font("Monospaced", Font.PLAIN, (int) size);
            }

            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(size);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка при загрузке шрифта " + path, e);
            return new Font("Monospaced", Font.PLAIN, (int) size);
        }
    }
}
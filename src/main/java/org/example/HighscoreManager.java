package org.example;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HighscoreManager {
    private static final Logger logger = Logger.getLogger(HighscoreManager.class.getName());
    private static final String FILE_NAME = System.getProperty("user.home") + "/.snake_highscores.dat";

    private List<Integer> scores;

    public HighscoreManager() {
        scores = loadScores();
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void addScore(int score) {
        if (!scores.contains(score)) {
            scores.add(score);
        }
        scores.sort(Collections.reverseOrder());

        if (scores.size() > 5) {
            scores = new ArrayList<>(scores.subList(0, 5));
        }

        saveScores();
    }

    @SuppressWarnings("unchecked")
    private List<Integer> loadScores() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Integer>) ois.readObject();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка при загрузке рекордов", e);
            return new ArrayList<>();
        }
    }

    private void saveScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(scores);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка при сохранении рекордов", e);
        }
    }
}

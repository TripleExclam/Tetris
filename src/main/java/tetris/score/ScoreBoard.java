package tetris.score;

import java.util.*;

public class ScoreBoard {
    private static final int LINE_CLEAR = 100;
    private static final int MULTIPLIER = 2;

    private int currentScore;
    private boolean tetris;
    private Map<String, Integer> scoreBoard;

    public ScoreBoard() {
        currentScore = 0;
        tetris = false;
        scoreBoard = new TreeMap<>();
        addScore("Jesus", 60);
        addScore("Steve", 120);
    }

    public void computeScore(int linesCleared) {
        if (linesCleared == 0) {
            return;
        }
        currentScore += linesCleared * MULTIPLIER * LINE_CLEAR + ((tetris) ? 400 : 0);
        tetris = linesCleared == 4;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public List<String> getAlphabeticalScores() {
        List<String> scores = new ArrayList<>();

        for (String name : scoreBoard.keySet()) {
            scores.add(name + " : " + scoreBoard.get(name));
        }

        scores.sort(String::compareTo);

        return scores;
    }

    public List<String> getNumericalScores() {
        List<String> scores = new ArrayList<>(scoreBoard.keySet());

        scores.sort(Comparator.comparing(item -> scoreBoard.get(item)).reversed());

        for (int i = 0; i < scores.size(); i++) {
            scores.set(i, scores.get(i) + " : " + scoreBoard.get(scores.get(i)));
        }

        return scores;
    }

    public void addScore(String name, int score) {
        scoreBoard.put(name, score);
    }

}

package tetris.score;

import tetris.block.Tile;
import tetris.util.Matrix;

import java.util.*;

public class ScoreBoard {
    private static final int LINE_CLEAR = 100;
    private static final int MULTIPLIER = 2;

    private int currentScore;
    private int holes;
    private int gradient;
    private int blocksPlaced;
    private double avgHeight;

    private boolean tetris;
    private Map<String, Integer> scoreBoard;

    public ScoreBoard() {
        currentScore = 0;
        holes = 0;
        gradient = 0;
        avgHeight = 0;
        blocksPlaced = 0;
        tetris = false;
        scoreBoard = new TreeMap<>();
        addScore("Jesus", 60);
        addScore("Steve", 120);
    }

    public int getGradientHoles() {
        return gradient + holes;
    }

    public int getBlocksPlaced() {
        return blocksPlaced;
    }

    public int calculateHoles(Matrix board) {
        int holes = 0;
        int row = 0;

        for (int col = 0; col < board.getWidth(); col++) {
            while (row < board.getHeight() && board.get(row, col) == Tile.EMP) {
                row++;
            }

            for (int i = ++row; i < board.getHeight(); i++) {
                if (board.get(i, col) == Tile.EMP) {
                    holes++;
                }
            }

            row = 0;
        }
        this.holes = holes;
        return holes;
    }

    public int calculateGradient(Matrix board) {
        int highest = Integer.MAX_VALUE;
        int grad = 0;
        int row = 0;
        int previousRow = 0;

        for (int col = 0; col < board.getWidth(); col++) {
            while (row < board.getHeight() && board.get(row, col) == Tile.EMP) {
                if (col == 0) {
                    previousRow++;
                }
                row++;
            }
            if (row < highest) {
                highest = row;
            }
            grad += Math.abs(row - previousRow);
            previousRow = row;
            row = 0;
        }
        gradient = grad;
        avgHeight += board.getHeight() - highest;
        return grad;
    }

    public void computeScore(int linesCleared) {
        blocksPlaced++;
        if (linesCleared == 0) {
            return;
        }
        currentScore += linesCleared * MULTIPLIER * LINE_CLEAR + ((tetris) ? 400 : 0);
        tetris = linesCleared == 4;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public double getAvgHeight() {
        return avgHeight / blocksPlaced;
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

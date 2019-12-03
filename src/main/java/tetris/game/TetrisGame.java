package tetris.game;

import tetris.board.TetrisBoard;
import tetris.score.ScoreBoard;

import java.util.Random;

public class TetrisGame {
    private TetrisBoard board;
    private ScoreBoard scoreBoard;

    private int holesCreated;

    private boolean gameOver;
    private boolean paused;

    public TetrisGame() {
        board = new TetrisBoard();
        holesCreated = 0;
        scoreBoard = new ScoreBoard();
        gameOver = false;
        paused = false;
    }

    public TetrisGame(int seed) {
        this();
        board = new TetrisBoard(new Random(seed));
    }

    public TetrisGame copy() {
        TetrisGame game = new TetrisGame();
        game.setBoard(getBoard().copy());
        game.setScoreBoard(getScoreBoard().copy());
        return game;
    }

    public void tick(int tick) {
        if (paused || gameOver) {
            return;
        }

        int previousScore = scoreBoard.getCurrentScore();
        int previousHoles = scoreBoard.calculateHoles(getBoard().getBoard());

        if (board.tickBoard(tick)) {
            board.setPiece();
            checkGameOver();
            scoreBoard.calculateGradient(getBoard().getBoard());
            scoreBoard.calculateHoles(getBoard().getBoard());
            scoreBoard.computeScore(board.clearLines());
            holesCreated += scoreBoard.calculateHoles(getBoard().getBoard()) - previousHoles;
        }

        if (scoreBoard.getCurrentScore() > previousScore + 100) {
            board.increaseLevel();
        }
    }

    public int getHolesCreated() {
        return holesCreated;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public TetrisBoard getBoard() {
        return board;
    }

    public void checkGameOver() {
        TetrisBoard board = getBoard();

        for (int i = 0; i < TetrisBoard.getWidth(); i++) {
            if (!board.getBoard().isEmpty(0)) {
                gameOver = true;
            }
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void endGame() {
        gameOver = true;
    }

    public void togglePause() {
        paused = !paused;
    }

    public void setBoard(TetrisBoard board) {
        this.board = board;
    }

    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }
}

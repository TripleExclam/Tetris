package tetris.game;

import tetris.block.TetrisBlock;
import tetris.block.Tile;
import tetris.board.TetrisBoard;
import tetris.score.ScoreBoard;
import tetris.util.Matrix;

public class TetrisGame {
    private TetrisBoard board;
    private ScoreBoard scoreBoard;

    private int scoreCutOff = 500;
    private boolean gameOver;
    private boolean paused;

    public TetrisGame() {
        board = new TetrisBoard();
        scoreBoard = new ScoreBoard();
        gameOver = false;
        paused = false;
    }

    public void tick(int tick) {
        if (paused || gameOver) {
            return;
        }
        if (board.tickBlock(tick)) {
            board.setPiece(new TetrisBlock(Tile.getRandom()));
            checkGameOver();
            scoreBoard.computeScore(board.clearLines());
        }

        if (scoreBoard.getCurrentScore() > scoreCutOff) {
            scoreCutOff *= 2;
            board.increaseLevel();
        }
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public TetrisBoard getBoard() {
        return board;
    }

    public void checkGameOver() {
        Matrix board = getBoard().getBoard();

        for (int i = 0; i < board.getWidth(); i++) {
            if (!board.get(0, i).equals(Tile.EMP)) {
                gameOver = true;
            }
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void togglePause() {
        paused = !paused;
    }
}

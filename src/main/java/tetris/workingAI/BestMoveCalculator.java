package tetris.workingAI;

import tetris.board.TetrisBoard;
import tetris.game.Action;
import tetris.game.Player;
import tetris.game.TetrisGame;
import tetris.score.ScoreBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class BestMoveCalculator implements Player {
    private static final double HOLE_WEIGHT = 2;
    private static final double GRADIENT_WEIGHT = 0.28;
    private List<Action> rotation = new ArrayList<>();
    private TetrisGame game;

    public void makeMove(TetrisGame game) {
        this.game = game;
        makeBestMove();
    }

    private void makeBestMove() {
        TetrisGame game = this.game.copy();
        TreeMap<Double, List<Action>> moveList = new TreeMap<>();

        int currrentX = game.getBoard().getPiece().getLeftMostX();


        for (int i = 0; i < TetrisBoard.getWidth(); i++) {
            for (int j = 0; j < 4; j++) {
                List<Action> moves = getNextMove(i - currrentX);
                moveList.put(computeScore(moves), moves);
            }
        }

        List<Action> bestMoves = moveList.get(moveList.lastKey());

        for (Action move : bestMoves) {
            this.game.getBoard().move(move);
        }
        this.game.getBoard().move(Action.DOWN);
    }

    private double computeScore(List<Action> moves) {
        double score = 0;

        TetrisGame gameState = this.game.copy();
        TetrisBoard board = gameState.getBoard();
        ScoreBoard scoreBoard = gameState.getScoreBoard();

        board.move(Action.DOWN);
        for (Action move : moves) {
            board.move(move);
        }

        double previousHeight = scoreBoard.getAvgHeight();
        int previousHoles = scoreBoard.calculateHoles(board.getBoard());
        int tick = 1;

        while (!board.tickBoard(tick++)) {
            // Wait till a piece lands
        }

        score += (double) board.getPiece().getHighestY() / 2;
        board.setPiece();
        gameState.checkGameOver();
        gameState.getScoreBoard().computeScore(board.clearLines());

        score -= (gameState.isGameOver()) ? 100 : 0;
        score += (gameState.getScoreBoard().getAvgHeight() < previousHeight) ? 1 : 0;
        score += HOLE_WEIGHT * (previousHoles - scoreBoard.calculateHoles(board.getBoard()));
        score += (scoreBoard.getScoreChange() > 0) ? 8 : 0;
        score -= GRADIENT_WEIGHT * gameState.getScoreBoard().calculateGradient(board.getBoard());

        return score;
    }

    private List<Action> getNextMove(int steps) {
        List<Action> moves = new ArrayList<>();

        Action direction = (steps < 0) ? Action.LEFT : Action.RIGHT;

        for (int i = 0; i < Math.abs(steps); i++) {
            moves.add(direction);
        }

        updateRotation(moves);

        return moves;
    }


    private void updateRotation(List<Action> moves) {
        moves.addAll(rotation);

        if (rotation.size() == 3) {
            rotation.clear();
        } else {
            rotation.add(Action.ROTATE_RIGHT);
        }
    }
}

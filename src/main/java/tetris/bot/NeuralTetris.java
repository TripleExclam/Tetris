package tetris.bot;

import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.neat.NEATNetwork;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;
import tetris.block.Tile;
import tetris.board.TetrisBoard;
import tetris.game.Action;
import tetris.game.Player;
import tetris.game.TetrisGame;


public class NeuralTetris implements Player {
    private NEATNetwork network;

    private NormalizedField boardStats;
    private NormalizedField score;

    private int goodMoves;

    public NeuralTetris(NEATNetwork network) {
        boardStats = new NormalizedField(NormalizationAction.Normalize, "tile",
                Tile.values().length - 1, 0, 0.9, -0.9);
        score = new NormalizedField(NormalizationAction.Normalize, "score",
                2000, -2000, 0.9, -0.9);
        this.network = network;
        goodMoves = 0;
    }

    public void makeMove(TetrisGame game) {
        game.getBoard().move(Action.DOWN);
        if (game.getHolesCreated() == 0) {
            goodMoves += (game.getScoreBoard().getScoreChange() > 0) ? 5 : 1;
        }

        if (game.getScoreBoard().getAvgHeight() > 8) {
            game.endGame();
        }

        MLData input = new BasicMLData(TetrisBoard.getHeight() * TetrisBoard.getWidth());

        TetrisBoard board = game.getBoard().copy();
        board.lockPiece(game.getBoard().getPiece());;

        int index = 0;
        for (int i = 0; i < TetrisBoard.getHeight(); i++) {
            for (int j = 0; j < TetrisBoard.getWidth(); j++) {
                input.setData(index++,
                        this.boardStats.normalize(board.getBoard().get(i, j).ordinal()));
            }
        }

        MLData output = this.network.compute(input);

        double[] outputs = output.getData();

        int max = 0;
        for (int i = 0; i < output.size(); i++) {
            if (outputs[i] > outputs[max]) {
                max = i;
            }
        }

        Action[] moves = {Action.NONE, Action.NONE, Action.RIGHT,
                Action.LEFT, Action.NONE};
        game.getBoard().move(moves[max]);
    }

    public double scorePilot() {
        TetrisGame sim = new TetrisGame();
        int tick = 1;

        while(!sim.isGameOver() && tick < (Integer.MAX_VALUE >> 1)) {
            makeMove(sim);
            sim.tick(tick++);
        }

        return this.score.normalize(goodMoves);
    }
}

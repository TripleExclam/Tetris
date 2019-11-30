package tetris.bot;

import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.neural.networks.BasicNetwork;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizedField;
import tetris.block.Tile;
import tetris.board.TetrisBoard;
import tetris.game.Action;
import tetris.game.TetrisGame;


public class NeuralTetris {
    private BasicNetwork network;

    private NormalizedField boardStats;
    private NormalizedField score;

    public NeuralTetris(BasicNetwork network, boolean track) {
        boardStats = new NormalizedField(NormalizationAction.Normalize, "tile",
                Tile.values().length - 1, 0, -0.9, 0.9);
        score = new NormalizedField(NormalizationAction.Normalize, "score",
                1000, -100, 0.9, -0.9);

        this.network = network;
    }

    public void getMove(TetrisGame game) {

        MLData input = new BasicMLData(TetrisBoard.getHeight() * TetrisBoard.getWidth());

        TetrisBoard board = game.getBoard().copy();
        board.lockPiece(game.getBoard().getPiece());;

        for (int i = 0; i < TetrisBoard.getHeight(); i++) {
            for (int j = 0; j < TetrisBoard.getWidth(); j++) {
                input.setData(i * TetrisBoard.getWidth() + j,
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

        switch (max) {
            case 0:
                game.getBoard().move(Action.RIGHT);
                break;
            case 1:
                game.getBoard().move(Action.LEFT);
                break;
            case 2:
                game.getBoard().move(Action.ROTATE_LEFT);
                break;
            case 3:
                game.getBoard().move(Action.ROTATE_RIGHT);
                break;
        }

        game.getBoard().move(Action.DOWN);
    }

    public double scorePilot() {
        TetrisGame sim = new TetrisGame();
        int tick = 1;
        double score = 0;

        while(!sim.isGameOver()) {
            if (tick % 2 == 0) {
                getMove(sim);
            }
            sim.tick(tick++);

            if (sim.getHolesCreated() > 4) {
                break;
            }
        }
        if (sim.getScoreBoard().getCurrentScore() > 0) {
            System.out.println("BLOCKS PLACED: " + sim.getScoreBoard().getBlocksPlaced());
            System.out.println("AVG HEIGHT:" + sim.getScoreBoard().getAvgHeight());
            System.out.println("SCORE:" + this.score.normalize(score));
        }

        return this.score.normalize(sim.getScoreBoard().getBlocksPlaced() - 1
                - sim.getScoreBoard().getGradientHoles()
                + sim.getScoreBoard().getCurrentScore()
                - sim.getHolesCreated());
    }
}

package tetris.bot;

import org.encog.ml.CalculateScore;
import org.encog.ml.MLMethod;
import org.encog.neural.networks.BasicNetwork;

public class TetrisScore implements CalculateScore {
    @Override
    public double calculateScore(MLMethod network) {
        NeuralTetris pilot = new NeuralTetris((BasicNetwork) network, false);
        return pilot.scorePilot();
    }


    public boolean shouldMinimize() {
        return false;
    }


    @Override
    public boolean requireSingleThreaded() {
        return false;
    }

}

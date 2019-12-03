package tetris.bot;

import org.encog.ml.CalculateScore;
import org.encog.ml.MLMethod;
import org.encog.neural.neat.NEATNetwork;


public class TetrisScore implements CalculateScore {

    @Override
    public double calculateScore(MLMethod network) {
        NeuralTetris pilot = new NeuralTetris((NEATNetwork) network);
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

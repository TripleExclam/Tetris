package tetris.bot;


import org.encog.ml.MLMethod;
import org.encog.ml.ea.train.EvolutionaryAlgorithm;
import org.encog.neural.hyperneat.substrate.Substrate;
import org.encog.neural.neat.NEATNetwork;
import org.encog.neural.neat.NEATPopulation;
import org.encog.neural.neat.NEATUtil;
import tetris.board.TetrisBoard;
import tetris.display.Launcher;
import tetris.display.MainViewModel;
import tetris.workingAI.BestMoveCalculator;
import tetris.workingAI.SubstrateGenerator;

/**
 * This program demonstrates HyperNEAT.
 *
 * The objective is to distinguish a large object from a small object in a two-
 * dimensional visual field. Because the same principle determines the
 * difference between small and large objects regardless of their location in
 * the retina, this task is well suited to testing the ability of HyperNEAT to
 * discover and exploit regularities.
 *
 * This program will display two rectangles, one large, and one small. The
 * program seeks to place the red position indicator in the middle of the larger
 * rectangle. The program trains and attempts to gain the maximum score of 110.
 * Once training is complete, you can run multiple test cases and see the
 * program attempt to find the center.
 *
 * One unique feature of HyperNEAT is that the resolution can be adjusted after
 * training has occured. This allows you to efficiently train on a small data
 * set and run with a much larger.
 *
 */
public class NEATExample {

    private boolean requestStop = false;
    private NEATPopulation pop;
    private EvolutionaryAlgorithm train;

    public NEATExample() {}

    public MLMethod getNetwork() {
        return pop.getCODEC().decode(train.getBestGenome());
    }

    public void resetTraining() {


        Substrate substrate = SubstrateGenerator.generate(
                TetrisBoard.getHeight() * TetrisBoard.getWidth(), 3);
        substrate.createHiddenNode();
        TetrisScore score = new TetrisScore();
        pop = new NEATPopulation(substrate, 1000);
        pop.setActivationCycles(1);
        pop.reset();
        pop.setInputCount(TetrisBoard.getHeight() * TetrisBoard.getWidth());
        pop.setOutputCount(3);
        train = NEATUtil.constructNEATTrainer(pop, score);
    }

    public void run() {

        if (this.pop == null) {
            resetTraining();
        }

        int stagnation = 0;
        double previousError = 0;
        this.requestStop = false;
        MainViewModel viewModel = new MainViewModel(new BestMoveCalculator());

        viewModel.isGameOver().addListener((observable, oldValue, newValue) -> {
            if (oldValue || requestStop) {
                return;
            }

            viewModel.restart(new NeuralTetris((NEATNetwork) getNetwork()));
        });

        Launcher.launchNeural(viewModel);

        Thread display = new Thread(new Launcher());
        display.start();

        while (!this.requestStop && stagnation < 100) {
            this.train.iteration();
            stagnation = (previousError == train.getError()) ? stagnation + 1 : 0;
            if (stagnation % 20 == 0) {
                System.out.println(train.getError() + " error on iteration " + train.getIteration());
            }
            previousError = train.getError();
        }
        requestStop = true;

        this.train.finishTraining();
    }


}
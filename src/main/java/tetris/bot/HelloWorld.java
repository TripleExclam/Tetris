package tetris.bot;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.MLResettable;
import org.encog.ml.genetic.MLMethodGeneticAlgorithm;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.pattern.FeedForwardPattern;
import org.encog.persist.EncogDirectoryPersistence;
import tetris.board.TetrisBoard;
import tetris.display.Launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;


/**
 * XOR: This example is essentially the "Hello World" of neural network
 * programming.  This example shows how to construct an Encog neural
 * network to predict the output from the XOR operator.  This example
 * uses backpropagation to train the neural network.
 *
 * This example attempts to use a minimum of Encog features to create and
 * train the neural network.  This allows you to see exactly what is going
 * on.  For a more advanced example, that uses Encog factories, refer to
 * the XORFactory example.
 *
 */
public class HelloWorld {
    private static String saveName = "BestNetwork" + new Random().nextInt();

    /**
     * The main method.
     *
     * @param args No arguments are used.
     */
    public static void main(final String args[]) {

        // create a neural network, without using a factory
        BasicNetwork network;

        // create training data
        MLTrain train = new MLMethodGeneticAlgorithm(() -> {
            final BasicNetwork result = createNewNetwork();
            ((MLResettable)result).reset();
            return result;
        }, new TetrisScore(), 1000);

        // train the neural network
        int player = 1;

        for(int i = 0; i < 100; i++) {
            System.out.println("Iteration " + i);
            train.iteration();
            System.out.println("Player #" + player + " Score:" + train.getError());
            player++;
        }
        train.finishTraining();

        System.out.println("\nHow the winning network went:");
        network = (BasicNetwork) train.getMethod();
        EncogDirectoryPersistence.saveObject(new File(saveName), network);

        Launcher.launchNeural(network);

        Encog.getInstance().shutdown();
    }

    public static BasicNetwork createNewNetwork() {
        FeedForwardPattern pattern = new FeedForwardPattern();
        pattern.setInputNeurons(TetrisBoard.getHeight() * TetrisBoard.getWidth());
        pattern.addHiddenLayer(100);
        pattern.setOutputNeurons(4);
        pattern.setActivationFunction(new ActivationTANH());
        BasicNetwork network = (BasicNetwork) pattern.generate();
        network.reset();
        return network;
    }

    public static BasicNetwork createNetwork() {
        BasicNetwork network = null;
        try {
            network = (BasicNetwork) EncogDirectoryPersistence.loadObject(
                    new FileInputStream("BestNetwork"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return network;
    }
}
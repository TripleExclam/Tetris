package tetris.bot;

import org.encog.Encog;


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
    private static String saveName = "BestNetwork";

    /**
     * The main method.
     *
     * @param args No arguments are used.
     */
    public static void main(final String args[]) {

        NEATExample neat = new NEATExample();
        neat.run();

        // EncogDirectoryPersistence.saveObject(new File(saveName), network);
        // test the neural network
        System.out.println("Trainig done!");

        Encog.getInstance().shutdown();
    }

}
package tetris.workingAI;

import org.encog.neural.hyperneat.substrate.Substrate;
import org.encog.neural.hyperneat.substrate.SubstrateFactory;
import org.encog.neural.hyperneat.substrate.SubstrateNode;

public class SubstrateGenerator extends SubstrateFactory {
    public static Substrate generate(int inputs, int outputs) {

        Substrate substrate = new Substrate(2);
        double inputTick = 2.0 / inputs;
        double outputTick = 2.0 / outputs;
        double inputOrig = -1.0 + (inputTick / 2.0);
        double outputOrig = -1.0 + (inputTick / 2.0);

        // create the input layer

        for (int i = 0; i < inputs; i++) {
            SubstrateNode inputNode = substrate.createInputNode();
            inputNode.getLocation()[0] = -1;
            inputNode.getLocation()[1] = inputOrig + (i * inputTick);
        }

        // create the hideen and output layer (and connect to input layer)

        for (int i = 0; i < outputs; i++) {
            SubstrateNode outputNode = substrate.createOutputNode();
            outputNode.getLocation()[0] = 1;
            outputNode.getLocation()[1] = outputOrig + (i * outputTick);
        }

        for (int i = 0; i < 5; i++) {
            SubstrateNode hidden = substrate.createHiddenNode();
            hidden.getLocation()[0] = -1;
            hidden.getLocation()[1] = inputOrig + (i * inputTick);
            for (SubstrateNode input : substrate.getInputNodes()) {
                for (SubstrateNode output : substrate.getOutputNodes()) {
                    substrate.createLink(input, hidden);
                    substrate.createLink(hidden, output);
                }
            }

        }


        return substrate;
    }

}

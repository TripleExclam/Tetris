package tetris.display;

import javafx.application.Application;
import javafx.stage.Stage;
import org.encog.neural.networks.BasicNetwork;
import tetris.bot.NeuralTetris;


public class Launcher extends Application {

    private static NeuralTetris bot = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        var params = getParameters().getRaw();
        if (bot != null) {
            var view = new MainView(primaryStage, new MainViewModel(bot));
            view.run();
        } else {
            var view = new MainView(primaryStage, new MainViewModel());
            view.run();
        }
    }

    public static void launchNeural(BasicNetwork network) {
        bot = new NeuralTetris(network, false);
        main(new String[] {});
    }
}

package tetris.display;

import javafx.application.Application;
import javafx.stage.Stage;
import tetris.workingAI.BestMoveCalculator;


public class Launcher extends Application implements Runnable {

    private static MainViewModel viewModel = null;

    @Override
    public void run() {
        main(new String[] {});
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        MainView view;
        if (viewModel != null) {
            view = new MainView(primaryStage, viewModel);
        } else {
            view = new MainView(primaryStage, new MainViewModel(new BestMoveCalculator()));
        }
        view.run();
    }

    public static void launchNeural(MainViewModel view) {
        viewModel = view;
    }
}

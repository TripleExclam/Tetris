import javafx.application.Application;
import javafx.stage.Stage;
import tetris.display.MainView;
import tetris.display.MainViewModel;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        var params = getParameters().getRaw();

        var view = new MainView(primaryStage, new MainViewModel());
        view.run();
    }
}

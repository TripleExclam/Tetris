package tetris.display;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.LinkedList;

public class MainView {
    private static int WINDOW_WIDTH = 750;
    private static int WINDOW_HEIGHT = 750;

    private MainViewModel viewModel;
    private BoardView boardView;
    private Stage root;

    private Group rootGroup;
    // button press action queue
    private LinkedList<String> input;

    public MainView(Stage parent, MainViewModel viewModel) {
        root = parent;
        this.viewModel = viewModel;

        // set the title
        root.setTitle("Tetris");

        // set the window size
        root.setWidth(WINDOW_WIDTH);
        root.setHeight(WINDOW_HEIGHT);

        // create the scene
        rootGroup = new Group();

        Scene rootScene = new Scene(rootGroup);
        root.setScene(rootScene);
        rootScene.setUserAgentStylesheet("style.css");

        // enable the event handlers
        input = new LinkedList<>();

        // This grabs key presses and adds it to the queue
        rootScene.setOnKeyPressed(event -> {
            String code = event.getCode().toString();
            input.push(code);
        });

        rootScene.setOnKeyReleased(event -> {
            String code = event.getCode().toString();
            if (code == "S") {
                input.push("SLOW");
            }
        });

        boardView = new BoardView(viewModel.getBoardVM());
        createWindow();
    }

    /*
     * Creates the top level window at the fixed width and height.
     */
    private void createWindow() {
        HBox mainArea = new HBox();
        mainArea.setAlignment(Pos.CENTER);

        mainArea.setMinWidth(WINDOW_WIDTH);
        mainArea.setMinHeight(WINDOW_HEIGHT);

        Label mapTitle = new Label();
        mapTitle.setTextAlignment(TextAlignment.CENTER);
        mapTitle.setMaxHeight(20);
        mapTitle.setMaxWidth(Double.MAX_VALUE);
        mapTitle.setAlignment(Pos.CENTER);
        mapTitle.textProperty().bindBidirectional(viewModel.getTitle());
        mapTitle.getStyleClass().add("header-text");

        VBox gameSide = new VBox(); // Boxes left to right
        gameSide.setAlignment(Pos.CENTER);

        VBox boardSide = new VBox();
        boardSide.setAlignment(Pos.CENTER);

        VBox infoSide = new VBox();
        infoSide.setAlignment(Pos.CENTER);

        boardSide.getChildren().addAll(mapTitle, boardView.getPane());

        mainArea.getChildren().addAll(gameSide, boardSide, infoSide);

        rootGroup.getChildren().add(mainArea);
    }

    /**
     * Ticks and updates the game though the ViewModel. Also
     * applies queued user input.
     * @given
     */
    public void run() {
        new AnimationTimer() {

            public void handle(long currentNanoTime) {
                while (!input.isEmpty()) {
                    String key = input.pop();
                    viewModel.getBoardVM().move(key);
                }
                viewModel.getBoardVM().tick();
                boardView.redraw();
            }
        }.start();

        // Show the game screen
        this.root.show();
    }

}

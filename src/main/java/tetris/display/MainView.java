package tetris.display;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import tetris.util.FastAnimationTimer;

import java.util.LinkedList;
import java.util.Queue;

public class MainView {

    private MainViewModel viewModel;
    private BoardView boardView;
    private ScoreView scoreView;
    private Stage root;
    private VBox rootGroup;

    // button press action queue
    private Queue<String> input;

    public MainView(Stage parent, MainViewModel viewModel) {
        root = parent;
        this.viewModel = viewModel;

        // set the title
        root.setTitle("Tetris");

        // create the scene
        rootGroup = new VBox();

        Scene rootScene = new Scene(rootGroup);
        root.setScene(rootScene);
        rootScene.setUserAgentStylesheet("style.css");

        // enable the event handlers
        input = new LinkedList<>();

        // This grabs key presses and adds it to the queue
        rootScene.setOnKeyPressed(event -> {
            String code = event.getCode().toString();
            input.add(code);
        });

        rootScene.setOnKeyReleased(event -> {
            String code = event.getCode().toString();
            if (code == "S") {
                input.add("SLOW");
            }
        });


        viewModel.isGameOver().addListener((observable, oldValue, newValue) -> {
            if (oldValue) {
                return;
            }
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setOnHidden(closeEvent -> {
                viewModel.restart(null);
                String name = dialog.getEditor().getText();

                if (name != null && !name.isEmpty()) {
                    viewModel.getScoreVM().setPlayerScore(name,
                            viewModel.getScoreVM().getCurrentScore());
                }
            });
            dialog.setTitle("Game Over");
            dialog.setContentText("Please enter your name:");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            dialog.show();
        });

        boardView = new BoardView(viewModel.getBoardVM());
        scoreView = new ScoreView(viewModel.getScoreVM());

        createWindow();
    }

    /*
     * Creates the top level window at the fixed width and height.
     */
    private void createWindow() {
        HBox mainArea = new HBox();
        mainArea.setAlignment(Pos.CENTER);

        Label mapTitle = new Label();
        mapTitle.setTextAlignment(TextAlignment.CENTER);
        mapTitle.setAlignment(Pos.CENTER);
        mapTitle.setMaxHeight(20);
        mapTitle.setMaxWidth(Double.MAX_VALUE);
        mapTitle.setAlignment(Pos.CENTER);
        mapTitle.textProperty().bindBidirectional(viewModel.getTitle());
        mapTitle.getStyleClass().add("header-text");

        VBox gameSide = new VBox(); // Boxes left to right
        gameSide.setAlignment(Pos.CENTER);

        VBox boardSide = new VBox();
        boardSide.setAlignment(Pos.CENTER);

        boardSide.getChildren().addAll(boardView.getPane());
        gameSide.getChildren().addAll(scoreView.getPane());

        mainArea.getChildren().addAll(boardSide, gameSide);

        rootGroup.getChildren().addAll(mapTitle, mainArea);
    }

    /**
     * Ticks and updates the game though the ViewModel. Also
     * applies queued user input.
     * @given
     */
    public void run() {
        new FastAnimationTimer(0) {

            @Override
            public void handle() {
                String key = (input.isEmpty()) ? "NONE" : input.remove();
                viewModel.getBoardVM().move(key);
                viewModel.update();
                boardView.redraw();
                scoreView.redrawNextBlock();
            }

        }.start();

        // Show the game screen
        this.root.show();
    }

}

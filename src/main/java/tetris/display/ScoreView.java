package tetris.display;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class ScoreView {

    private static final int SCORE_HEIGHT = 600;
    private static final int SCORE_WIDTH = 300;
    private static final int PREVIEW_LENGTH = BoardView.BLOCK_SIZE * (6);

    private ScoreViewModel viewModel;
    private VBox mainPane;
    private Canvas nextBlock;

    /**
     * Construct a View that displays the graphical board stored in
     * the given viewModel.
     * @param viewModel the mode to display in the view.
     * @given
     */
    public ScoreView(ScoreViewModel viewModel) {
        this.viewModel = viewModel;

        nextBlock = new Canvas(PREVIEW_LENGTH, PREVIEW_LENGTH);

        mainPane = new VBox();
        mainPane.setMinHeight(SCORE_HEIGHT);
        mainPane.setMinWidth(SCORE_WIDTH);

        addComponents(mainPane);
    }

    private void addComponents(VBox mainPane) {
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setSpacing(2);
        mainPane.setStyle(
                "-fx-background-color: #00033D;" + "-fx-text-fill: white;");


        Label highScoresTitle = new Label("High Scores");
        highScoresTitle.setTextAlignment(TextAlignment.CENTER);
        highScoresTitle.setAlignment(Pos.CENTER);
        highScoresTitle.setMaxWidth(Double.MAX_VALUE);
        highScoresTitle.setStyle(
                "" + "-fx-text-fill: yellow;" + "-fx-font: 14px Tahoma;"
                        + "-fx-padding: 10 0 5 0;" + "-fx-underline: true;");

        Button sortByBtn = new Button("Sorting By: ?");
        sortByBtn.setMaxWidth(180);
        sortByBtn.setAlignment(Pos.CENTER);
        sortByBtn.setOnAction(e -> viewModel.switchScoreOrder());
        sortByBtn.textProperty().bindBidirectional(viewModel.getSortedBy());
        ListView<String> scoreList = new ListView<>();
        scoreList.setStyle("" + "-fx-background-color: transparent;");

        scoreList.setItems(viewModel.getScores());

        Label currentScoreTitle = new Label("Current Score");
        currentScoreTitle.setTextAlignment(TextAlignment.CENTER);
        currentScoreTitle.setStyle(
                "" + "-fx-text-fill: yellow;" + "-fx-font: 14px Tahoma;"
                        + "-fx-padding: 10 0 5 0;" + "-fx-underline: true;");
        Label currentScore = new Label("Score: ??");
        currentScore.setStyle("-fx-text-fill: white;");
        currentScore.setTextAlignment(TextAlignment.CENTER);
        currentScore.textProperty()
                .bindBidirectional(viewModel.getCurrentScoreProperty());


        Label currentFitness = new Label("Gradient: ??");
        currentFitness.setStyle("-fx-text-fill: white;");
        currentFitness.setTextAlignment(TextAlignment.CENTER);
        currentFitness.textProperty()
                .bindBidirectional(viewModel.getCurrentFitnessProperty());

        Label currentHeight = new Label("Average height: ??");
        currentHeight.setStyle("-fx-text-fill: white;");
        currentHeight.setTextAlignment(TextAlignment.CENTER);
        currentHeight.textProperty()
                .bindBidirectional(viewModel.getCurrentHeightProperty());

        Label blocksPlaced = new Label("Blocks Placed: ??");
        blocksPlaced.setStyle("-fx-text-fill: white;");
        blocksPlaced.setTextAlignment(TextAlignment.CENTER);
        blocksPlaced.textProperty()
                .bindBidirectional(viewModel.getBlocksPlacedProperty());

        Label holesCreated = new Label("Holes Created: ??");
        holesCreated.setStyle("-fx-text-fill: white;");
        holesCreated.setTextAlignment(TextAlignment.CENTER);
        holesCreated.textProperty()
                .bindBidirectional(viewModel.getHolesCreatedProperty());

        scoreList.setMaxHeight(150);

        VBox.setVgrow(scoreList, Priority.ALWAYS);

        mainPane.getChildren().addAll(currentScoreTitle,
                currentScore, currentFitness, currentHeight, blocksPlaced, holesCreated);
        mainPane.getChildren().addAll(highScoresTitle, sortByBtn, scoreList);
        mainPane.getChildren().add(nextBlock);
    }

    /**
     * Gets the main entry point for this View.
     * @return the root pane for the view.
     * @given
     */
    public Pane getPane() {
        return this.mainPane;
    }

    public void redrawNextBlock() {
        GraphicsContext gc = nextBlock.getGraphicsContext2D();

        gc.setFill(Color.web(BoardView.BACKGROUND_COLOUR));
        gc.fillRect(0, 0, PREVIEW_LENGTH, PREVIEW_LENGTH);

        BoardView.drawPiece(gc, viewModel.getPiece());
    }

}

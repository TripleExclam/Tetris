package tetris.display;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import tetris.block.Blocks;
import tetris.block.TetrisBlock;
import tetris.board.TetrisBoard;


public class BoardView {

    private static final int CANVAS_HEIGHT = 600;
    private static final int BLOCK_SIZE = CANVAS_HEIGHT / TetrisBoard.getHeight();
    private static final int CANVAS_WIDTH = BLOCK_SIZE * TetrisBoard.getWidth();

    private BoardViewModel viewModel;
    private Pane mainPane;
    private Canvas playArea;

    /**
     * Construct a View that displays the graphical board stored in
     * the given viewModel.
     * @param viewModel the mode to display in the view.
     * @given
     */
    public BoardView(BoardViewModel viewModel) {
        this.viewModel = viewModel;

        mainPane = new GridPane();
        playArea = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);

        mainPane.getChildren().addAll(playArea);
    }

    /**
     * Gets the main entry point for this View.
     * @return the root pane for the view.
     * @given
     */
    public Pane getPane() {
        return this.mainPane;
    }

    /**
     * Redraws the the board play area.
     * @given
     */
    public void redraw() {
        GraphicsContext context = playArea.getGraphicsContext2D();

        drawCanvas(context);
        drawBoard(context);
        drawPiece(context);
    }

    /*
     * Draws the background fill on the given context.
     */
    private void drawCanvas(GraphicsContext gc) {
        gc.setFill(Color.web("#00033D"));
        gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    /*
     * Draws the board of the game on the given context applying the
     * offset given to each point.
     */
    private void drawBoard(GraphicsContext gc) {
        var board = viewModel.getBoard();

        for (int x = 0; x < TetrisBoard.getWidth(); x++) {
            for (int y = 0; y < TetrisBoard.getHeight(); y++) {
                drawCell(gc, x, y, board.getBoard().get(y, x).getColour());
            }
        }
    }

    private void drawPiece(GraphicsContext gc) {
        TetrisBlock piece = viewModel.getBoard().getPiece();

        if (piece == null) {
            return;
        }

        for (int i = piece.getxCoord(); i < piece.getxCoord() + Blocks.getLength(); i++) {
            for (int j = piece.getyCoord(); j < piece.getyCoord() + Blocks.getLength(); j++) {
                if (!piece.isEmpty(i, j)) {
                    drawCell(gc, i, j, piece.getType().getColour());
                }
            }
        }

    }

    private void drawCell(GraphicsContext gc, int x, int y, String colour) {
        int xCoord = x * BLOCK_SIZE;
        int yCoord = y * BLOCK_SIZE;

        gc.setFill(Color.web(colour));
        gc.setStroke(Color.web("#f71616"));
        gc.fillRect(xCoord, yCoord, BLOCK_SIZE, BLOCK_SIZE);
        gc.strokeRect(xCoord, yCoord, BLOCK_SIZE, BLOCK_SIZE);
    }

}

package tetris.display;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import tetris.block.TetrisBlock;
import tetris.board.TetrisBoard;


public class BoardView {

    private static final String EDGE_COLOUR = "#f71616";
    public static final String BACKGROUND_COLOUR = "#00033D";

    private static final int HEIGHT = 600;
    public static final int BLOCK_SIZE = HEIGHT / TetrisBoard.getHeight();
    private static final int WIDTH = BLOCK_SIZE * TetrisBoard.getWidth();

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
        playArea = new Canvas(WIDTH, HEIGHT);

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
        drawPiece(context, viewModel.getBoard().getPiece());
    }

    /*
     * Draws the background fill on the given context.
     */
    public static void drawCanvas(GraphicsContext gc) {
        gc.setFill(Color.web(BACKGROUND_COLOUR));
        gc.fillRect(0, 0, WIDTH, HEIGHT);
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

    /**
     * Draw the piece if it falls within the board.
     *
     * @param gc
     * @param piece
     */
    public static void drawPiece(GraphicsContext gc, TetrisBlock piece) {
        if (piece == null) {
            return;
        }

        for (int i = 0; i < piece.getLength(); i++) {
            for (int j = 0; j < piece.getLength(); j++) {

                if (!piece.isEmpty(i, j)) {
                    drawCell(gc, i + piece.getxCoord(), j + piece.getyCoord(),
                            piece.getType().getColour());
                }

            }
        }

    }

    private static void drawCell(GraphicsContext gc, int x, int y, String colour) {
        if (x < 0 || x >= TetrisBoard.getWidth() || y < 0 || y >= TetrisBoard.getHeight()) {
            return;
        }

        int xCoord = x * BLOCK_SIZE;
        int yCoord = y * BLOCK_SIZE;

        gc.setFill(Color.web(colour));
        gc.setStroke(Color.web(EDGE_COLOUR));
        gc.fillRect(xCoord, yCoord, BLOCK_SIZE, BLOCK_SIZE);
        gc.strokeRect(xCoord, yCoord, BLOCK_SIZE, BLOCK_SIZE);
    }

}

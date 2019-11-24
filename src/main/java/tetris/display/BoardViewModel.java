package tetris.display;

import tetris.block.TetrisBlock;
import tetris.block.Tile;
import tetris.board.TetrisBoard;
import tetris.game.Action;

public class BoardViewModel {

    private TetrisBoard board;
    private int ticks;

    public BoardViewModel() {
        board = new TetrisBoard();
        ticks = 1;
    }

    public TetrisBoard getBoard() {
        return board;
    }

    public void tick() {
        if (board.tickBlock(ticks)) {
            board.setPiece(new TetrisBlock(Tile.getRandom()));
        }

        this.ticks++;
    }


    public void move(String action) {

        switch(action.toUpperCase()) {
            case "A":
                board.move(Action.LEFT);
                break;
            case "D":
                board.move(Action.RIGHT);
                break;
            case "W":
                board.move(Action.ROTATE_LEFT);
                break;
            case "S":
                board.move(Action.DOWN);
                break;
            case "SLOW":
                board.move(Action.UP);
                break;
        }
    }

}

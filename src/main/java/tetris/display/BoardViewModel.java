package tetris.display;

import tetris.board.TetrisBoard;
import tetris.game.Action;
import tetris.game.TetrisGame;

public class BoardViewModel {

    private TetrisGame game;
    private int ticks;

    public BoardViewModel(TetrisGame game) {
        this.game = game;
        ticks = 1;
    }

    public TetrisBoard getBoard() {
        return game.getBoard();
    }

    public void tick() {
        game.tick(this.ticks++);
    }


    public void move(String action) {
        TetrisBoard board = game.getBoard();

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

package tetris.display;

import tetris.board.TetrisBoard;
import tetris.bot.NeuralTetris;
import tetris.game.Action;
import tetris.game.TetrisGame;

public class BoardViewModel {

    private TetrisGame game;
    private NeuralTetris bot = null;

    public BoardViewModel(TetrisGame game) {
        this.game = game;
    }

    public BoardViewModel(TetrisGame game, NeuralTetris bot) {
        this(game);
        this.bot = bot;
    }

    public TetrisBoard getBoard() {
        return game.getBoard();
    }


    public void move(String action) {
        if (bot != null) {
            bot.getMove(game);
            return;
        }
        TetrisBoard board = game.getBoard();
        switch(action.toUpperCase()) {
            case "NONE":
                return;
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

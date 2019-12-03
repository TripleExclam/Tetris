package tetris.display;

import tetris.board.TetrisBoard;
import tetris.game.Action;
import tetris.game.Player;
import tetris.game.TetrisGame;

public class BoardViewModel {

    private TetrisGame game;
    private Player bot = null;
    private int blocksPlaced = 0;

    public BoardViewModel(TetrisGame game) {
        this.game = game;
        blocksPlaced = game.getScoreBoard().getBlocksPlaced();
    }

    public BoardViewModel(TetrisGame game, Player bot) {
        this(game);
        this.bot = bot;
    }

    public TetrisBoard getBoard() {
        return game.getBoard();
    }

    public void reset(TetrisGame game, Player bot) {
        this.game = game;
        this.bot = bot;
        getBoard().clearBoard();
    }

    public void move(String action) {
        if (bot != null && blocksPlaced < game.getScoreBoard().getBlocksPlaced()) {
            blocksPlaced = game.getScoreBoard().getBlocksPlaced();
            bot.makeMove(game);
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

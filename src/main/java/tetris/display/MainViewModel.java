package tetris.display;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tetris.game.Player;
import tetris.game.TetrisGame;

public class MainViewModel {
    private BoardViewModel boardView;
    private ScoreViewModel scoreView;
    private TetrisGame game;

    private BooleanProperty gameOver = new SimpleBooleanProperty(false);
    private BooleanProperty isPaused = new SimpleBooleanProperty(false);

    private int tick;

    public MainViewModel() {
        game = new TetrisGame();
        boardView = new BoardViewModel(game);
        scoreView = new ScoreViewModel(game);
        tick = 1;
    }

    public MainViewModel(Player bot) {
        this();
        boardView = new BoardViewModel(game, bot);
    }

    public StringProperty getTitle() {
        return new SimpleStringProperty("TETRIS");
    }

    public BoardViewModel getBoardVM() {
        return boardView;
    }

    public ScoreViewModel getScoreVM() {
        return scoreView;
    }


    public void update() {
        game.tick(this.tick++);
        scoreView.update();
        gameOver.setValue(game.isGameOver());
    }


    public BooleanProperty isGameOver() {
        return gameOver;
    }

    public BooleanProperty isPaused() {
        return isPaused;
    }

    public void restart(Player bot) {
        game = new TetrisGame();
        boardView.reset(game, bot);
        scoreView.reset(game);
        update();
    }

}

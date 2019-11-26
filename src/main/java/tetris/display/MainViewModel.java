package tetris.display;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tetris.game.TetrisGame;

public class MainViewModel {
    private BoardViewModel boardView;
    private ScoreViewModel scoreView;
    private TetrisGame game;

    private BooleanProperty gameOver = new SimpleBooleanProperty(false);
    private BooleanProperty isPaused = new SimpleBooleanProperty(false);

    public MainViewModel() {
        game = new TetrisGame();
        boardView = new BoardViewModel(game);
        scoreView = new ScoreViewModel(game);
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
        boardView.tick();
        scoreView.update();
        gameOver.setValue(game.isGameOver());
    }


    public BooleanProperty isGameOver() {
        return gameOver;
    }

    public BooleanProperty isPaused() {
        return isPaused;
    }

    public void restart() {
        game = new TetrisGame();
        boardView = new BoardViewModel(game);
        scoreView = new ScoreViewModel(game);
        update();
    }
}

package tetris.display;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tetris.block.TetrisBlock;
import tetris.game.TetrisGame;


public class ScoreViewModel {
    private TetrisGame game;
    private StringProperty currentScore;
    private StringProperty sortedAs;
    private ObservableList<String> scores;

    private boolean alphaSort;

    public ScoreViewModel(TetrisGame game) {
        this.game = game;
        currentScore = new SimpleStringProperty();
        sortedAs = new SimpleStringProperty("Alphabetically");
        scores = FXCollections.observableArrayList(game.getScoreBoard().getAlphabeticalScores());
        alphaSort = true;
    }

    public StringProperty getCurrentScoreProperty() {
        return currentScore;
    }


    public void update() {
        currentScore.setValue("Score : " + game.getScoreBoard().getCurrentScore());

        scores.clear();
        scores.addAll((alphaSort) ? game.getScoreBoard().getAlphabeticalScores() :
                        game.getScoreBoard().getNumericalScores());
    }

    public void switchScoreOrder() {
        alphaSort = !alphaSort;
        sortedAs.setValue(alphaSort ? "Alphabetically" : "Numerically");
    }

    public Property<String> getSortedBy() {
        return this.sortedAs;
    }

    public ObservableList<String> getScores() {
        return scores;
    }

    public TetrisBlock getPiece() {
        return game.getBoard().getNextPiece();
    }

    public int getCurrentScore() {
        return game.getScoreBoard().getCurrentScore();
    }

    public void setPlayerScore(String name, int currentScore) {
        game.getScoreBoard().addScore(name, currentScore);
    }
}

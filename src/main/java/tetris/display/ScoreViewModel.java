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
    private StringProperty currentFitness;
    private StringProperty currentScore;
    private StringProperty currentHeight;
    private StringProperty sortedAs;
    private StringProperty blocksPlaced;
    private StringProperty holesCreated;

    private ObservableList<String> scores;

    private boolean alphaSort;

    public ScoreViewModel(TetrisGame game) {
        this.game = game;
        currentScore = new SimpleStringProperty();
        currentFitness = new SimpleStringProperty();
        currentHeight =  new SimpleStringProperty();
        blocksPlaced = new SimpleStringProperty();
        holesCreated = new SimpleStringProperty();
        sortedAs = new SimpleStringProperty("Alphabetically");
        scores = FXCollections.observableArrayList(game.getScoreBoard().getAlphabeticalScores());
        alphaSort = true;
    }

    public StringProperty getCurrentScoreProperty() {
        return currentScore;
    }


    public StringProperty getBlocksPlacedProperty() {
        return blocksPlaced;
    }

    public StringProperty getCurrentFitnessProperty() {
        return currentFitness;
    }

    public StringProperty getCurrentHeightProperty() {
        return currentHeight;
    }

    public StringProperty getHolesCreatedProperty() {
        return holesCreated;
    }

    public void update() {
        currentScore.setValue("Score : " + game.getScoreBoard().getCurrentScore());
        currentFitness.setValue("Fitness : " + game.getScoreBoard().getGradientHoles());
        currentHeight.setValue("Average Height : " + game.getScoreBoard().getAvgHeight());
        blocksPlaced.setValue("Blocks Placed : " + game.getScoreBoard().getBlocksPlaced());
        holesCreated.setValue("Holes Created : " + game.getHolesCreated());

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

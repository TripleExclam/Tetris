package tetris.display;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainViewModel {
    BoardViewModel boardView;

    public MainViewModel() {
        boardView = new BoardViewModel();
    }

    public StringProperty getTitle() {
        return new SimpleStringProperty("TETRIS");
    }

    public BoardViewModel getBoardVM() {
        return boardView;
    }



}

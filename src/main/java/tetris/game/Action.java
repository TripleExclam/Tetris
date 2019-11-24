package tetris.game;

public enum Action {
    UP(null),
    DOWN(UP),
    LEFT(null),
    RIGHT(LEFT),
    ROTATE_LEFT(null),
    ROTATE_RIGHT(ROTATE_LEFT);

    static {
        UP.opposite = DOWN;
        LEFT.opposite = RIGHT;
        ROTATE_LEFT.opposite = ROTATE_RIGHT;
    }

    private Action opposite;

    Action(Action opposite) {
        if (opposite != null) {
            this.opposite = opposite;
        }
    }

    public Action getOpposite() {
        return opposite;
    }
}

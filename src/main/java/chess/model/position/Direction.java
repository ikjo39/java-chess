package chess.model.position;

public enum Direction {

    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_UP(UP.x * 2, UP.y * 2),
    DOWN_DOWN(DOWN.x * 2, DOWN.y * 2),
    UP_LEFT(LEFT.x, UP.y),
    UP_RIGHT(RIGHT.x, UP.y),
    DOWN_LEFT(LEFT.x, DOWN.y),
    DOWN_RIGHT(RIGHT.x, DOWN.y),
    UP_UP_LEFT(LEFT.x, UP_UP.y),
    UP_UP_RIGHT(RIGHT.x, UP_UP.y),
    UP_LEFT_LEFT(LEFT.x * 2, UP.y),
    UP_RIGHT_RIGHT(RIGHT.x * 2, UP.y),
    DOWN_DOWN_LEFT(LEFT.x, DOWN_DOWN.y),
    DOWN_DOWN_RIGHT(RIGHT.x, DOWN_DOWN.y),
    DOWN_LEFT_LEFT(LEFT.x * 2, DOWN.y),
    DOWN_RIGHT_RIGHT(RIGHT.x * 2, DOWN.y);

    private final int x;
    private final int y;

    Direction(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isDiagonal() {
        return x != 0 && y != 0 && Math.abs(x) == Math.abs(y);
    }

    public boolean isVertical() {
        return x == 0 && y != 0;
    }

    public boolean isDoubleForward() {
        return UP_UP.equals(this) || DOWN_DOWN.equals(this);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

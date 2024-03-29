package chess.model.position;

import java.util.Arrays;

public enum Rank {
    EIGHT(1),
    SEVEN(2),
    SIX(3),
    FIVE(4),
    FOUR(5),
    THREE(6),
    TWO(7),
    ONE(8);

    private final int coordinate;

    Rank(final int coordinate) {
        this.coordinate = coordinate;
    }

    public static Rank from(final int coordinate) {
        return Arrays.stream(values())
                .filter(rank -> rank.coordinate == coordinate)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Rank 이름입니다."));
    }

    public boolean canMove(final int step) {
        return canMoveUp(step) && canMoveDown(step);
    }

    public Rank findNextRank(final int offset) {
        final int nextCoordinate = offset + coordinate;
        return Arrays.stream(values())
                .filter(file -> file.coordinate == nextCoordinate)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Rank 범위를 벗어났습니다."));
    }

    private boolean canMoveUp(final int step) {
        return coordinate + step <= values().length;
    }

    private boolean canMoveDown(final int step) {
        return coordinate + step >= 1;
    }
}

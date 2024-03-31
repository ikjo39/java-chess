package chess.model.position;

import java.util.Arrays;

public enum Rank {
    EIGHT(1, "8"),
    SEVEN(2, "7"),
    SIX(3, "6"),
    FIVE(4, "5"),
    FOUR(5, "4"),
    THREE(6, "3"),
    TWO(7, "2"),
    ONE(8, "1");

    private final int coordinate;
    private final String name;

    Rank(final int coordinate, String name) {
        this.coordinate = coordinate;
        this.name = name;
    }

    public static Rank from(final String name) {
        return Arrays.stream(values())
                .filter(rank -> rank.name.equals(name))
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

    public String getName() {
        return name;
    }
}

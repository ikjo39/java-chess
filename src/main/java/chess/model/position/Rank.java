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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Rank 좌표입니다."));
    }

    public int minus(final Rank other) {
        return other.coordinate - this.coordinate;
    }

    public Rank findNextRank(final int offset) {
        final int nextCoordinate = offset + coordinate;
        return Arrays.stream(values())
                .filter(file -> file.coordinate == nextCoordinate)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 Rank 좌표입니다."));
    }
}

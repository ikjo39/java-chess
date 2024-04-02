package chess.model.piece;

import java.util.Arrays;

public enum Side {
    BLACK,
    WHITE,
    EMPTY;

    public static Side from(String name) {
        return Arrays.stream(values())
                .filter(side -> side.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 이름의 진영이 존재하지 않습니다."));
    }

    public boolean isEmpty() {
        return EMPTY.equals(this);
    }

    public boolean isEnemy(final Side other) {
        if (isWhite()) {
            return other.isBlack();
        }
        if (isBlack()) {
            return other.isWhite();
        }
        return false;
    }

    public Side getEnemy() {
        if (isWhite()) {
            return BLACK;
        }
        if (isBlack()) {
            return WHITE;
        }
        throw new IllegalStateException("빈 진영의 적을 탐색할 수 없습니다.");
    }

    private boolean isWhite() {
        return WHITE.equals(this);
    }

    private boolean isBlack() {
        return BLACK.equals(this);
    }
}

package chess.model.piece;

public enum Side {
    BLACK,
    WHITE,
    EMPTY;

    public boolean isWhite() {
        return WHITE.equals(this);
    }

    public boolean isBlack() {
        return BLACK.equals(this);
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
}

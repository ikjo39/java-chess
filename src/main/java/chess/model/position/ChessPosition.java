package chess.model.position;

import java.util.Objects;

public class ChessPosition {
    private final File file;
    private final Rank rank;

    public ChessPosition(final File file, final Rank rank) {
        this.file = file;
        this.rank = rank;
    }

    public boolean canMove(final Direction direction) {
        return file.canMove(direction.getX())
                && rank.canMove(direction.getY());
    }

    public boolean canMoveVertical(final int step) {
        return rank.canMove(step);
    }

    public boolean hasSameFile(final ChessPosition target) {
        return file.equals(target.file);
    }

    public ChessPosition move(final Direction direction) {
        if (canMove(direction)) {
            final File nextFile = file.findNextFile(direction.getX());
            final Rank nextRank = rank.findNextRank(direction.getY());
            return new ChessPosition(nextFile, nextRank);
        }
        throw new IllegalArgumentException("해당 방향으로 움직일 수 없습니다");
    }

    public ChessPosition moveVertical(final int step) {
        if (canMoveVertical(step)) {
            final Rank nextRank = rank.findNextRank(step);
            return new ChessPosition(file, nextRank);
        }
        throw new IllegalArgumentException("해당 방향으로 움직일 수 없습니다");
    }

    public String getName() {
        return file.getName() + rank.getName();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPosition that = (ChessPosition) o;
        return file == that.file && rank == that.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }

    @Override
    public String toString() {
        return "ChessPosition{" +
                "file=" + file +
                ", rank=" + rank +
                '}';
    }
}

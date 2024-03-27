package chess.model.position;

import java.util.Objects;

public class ChessPosition {
    private final File file;
    private final Rank rank;

    public ChessPosition(final File file, final Rank rank) {
        this.file = file;
        this.rank = rank;
    }

    public Distance calculateDistance(final ChessPosition other) {
        final int fileDifference = file.minus(other.file);
        final int rankDifference = rank.minus(other.rank);
        return new Distance(fileDifference, rankDifference);
    }

    public File findNextFile(final int offset) {
        return file.findNextFile(offset);
    }

    public Rank findNextRank(final int offset) {
        return rank.findNextRank(offset);
    }

    public File getFile() {
        return file;
    }

    @Override
    public boolean equals(Object o) {
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

    public boolean hasSameFile(final ChessPosition target) {
        return file.equals(target.file);
    }
}

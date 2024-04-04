package chess.model.board;

import java.util.Objects;

public final class Point {
    private static final Point DEFAULT_POINT = new Point(0);

    private final double value;

    private Point(final double value) {
        this.value = value;
    }

    public static Point from(final double value) {
        return new Point(value);
    }

    public static Point getDefaults() {
        return DEFAULT_POINT;
    }

    public Point sum(final Point other) {
        return new Point(this.value + other.value);
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        return Double.compare(value, other.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Point{" +
                "value=" + value +
                '}';
    }
}

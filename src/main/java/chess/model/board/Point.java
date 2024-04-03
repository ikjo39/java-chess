package chess.model.board;

import java.util.Objects;

public final class Point {
    private final double value;

    private Point(final double value) {
        this.value = value;
    }

    public static Point from(final double value) {
        return new Point(value);
    }

    public static Point getDefaults() {
        return new Point(0);
    }

    public Point sum(final Point other) {
        return new Point(this.value + other.value);
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return Double.compare(value, point.value) == 0;
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

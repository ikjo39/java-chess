package chess.model.board;

import chess.model.piece.Side;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Points {
    private final Map<Side, Point> points;

    public Points(final Map<Side, Point> points) {
        this.points = new HashMap<>(points);
    }

    public List<Side> calculateWinner() {
        return points.keySet()
                .stream()
                .filter(key -> points.get(key).equals(calculateMaxPoint()))
                .toList();
    }

    private Point calculateMaxPoint() {
        return points.values()
                .stream()
                .max(Comparator.comparingDouble(Point::getValue))
                .orElse(Point.getDefaults());
    }

    public Map<Side, Point> getPoints() {
        return Collections.unmodifiableMap(points);
    }
}

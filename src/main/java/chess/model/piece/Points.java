package chess.model.piece;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class Points {
    private final Map<Side, Point> points;

    public Points(final Map<Side, Point> points) {
        this.points = new HashMap<>(points);
    }

    public List<Side> calculateWinner() {
        return points.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getValue() == calculateMaxPoint())
                .map(Entry::getKey)
                .toList();
    }

    private double calculateMaxPoint() {
        return points.values()
                .stream()
                .mapToDouble(Point::getValue)
                .max()
                .orElse(0);
    }

    public Map<Side, Point> getPoints() {
        return Collections.unmodifiableMap(points);
    }
}

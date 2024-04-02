package chess.model.board;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

import chess.model.piece.Piece;
import chess.model.piece.Side;
import chess.model.position.ChessPosition;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PointCalculator {
    private static final int PAWN_COUNT_WHEN_SOLID_IN_FILE = 1;
    private static final double POINT_WHEN_SAME_FILE_PAWN = -0.5;

    private final Map<ChessPosition, Piece> board;

    public PointCalculator(final Map<ChessPosition, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public Points calculate() {
        return Arrays.stream(Side.values())
                .filter(side -> !side.isEmpty())
                .collect(collectingAndThen(toMap(Function.identity(), this::calculatePiecePoints), Points::new));
    }

    private Point calculatePiecePoints(final Side side) {
        final Point totalSum = calculateTotalSum(side);
        final Point minusPoint = calculateSameFilePawn(side);
        return totalSum.sum(minusPoint);
    }

    private Point calculateTotalSum(final Side side) {
        return board.values()
                .stream()
                .filter(piece -> piece.isSameSide(side))
                .map(Piece::getPoint)
                .reduce(Point.getDefaults(), Point::sum);
    }

    private Point calculateSameFilePawn(final Side side) {
        final long sameFilePawn = getSameFilePawnCount(side);
        return Point.from(sameFilePawn * POINT_WHEN_SAME_FILE_PAWN);
    }

    private long getSameFilePawnCount(final Side side) {
        final Set<ChessPosition> positions = findAllSamSidePawns(side);

        return positions.stream()
                .filter(position -> hasSameFilePawn(position, positions))
                .count();
    }

    private Set<ChessPosition> findAllSamSidePawns(final Side side) {
        return board.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isSameSide(side))
                .filter(entry -> entry.getValue().isPawn())
                .map(Entry::getKey)
                .collect(Collectors.toSet());
    }

    private boolean hasSameFilePawn(final ChessPosition position, final Set<ChessPosition> positions) {
        return calculatePawnCountSameFile(position, positions) != PAWN_COUNT_WHEN_SOLID_IN_FILE;
    }

    private long calculatePawnCountSameFile(final ChessPosition position, final Set<ChessPosition> positions) {
        return positions.stream()
                .filter(position::hasSameFile)
                .count();
    }
}

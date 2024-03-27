package chess.model.board;

import chess.model.piece.Piece;
import chess.model.piece.Point;
import chess.model.piece.Side;
import chess.model.position.ChessPosition;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChessBoard {
    private static final int KING_COUNT = 2;
    private static final double POINT_WHEN_SAME_FILE_PAWN = 0.5;
    private final Map<ChessPosition, Piece> board;

    public ChessBoard(final Map<ChessPosition, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public void move(final ChessPosition sourcePosition, final ChessPosition targetPosition) {
        final Piece sourcePiece = board.get(sourcePosition);
        final Piece targetPiece = board.get(targetPosition);
        validateSourcePiece(sourcePiece);

        final List<ChessPosition> path = sourcePiece.findPath(sourcePosition, targetPosition, targetPiece);
        validatePathContainsPiece(path);
        changePositions(sourcePosition, targetPosition, sourcePiece, targetPiece);
    }

    public Map<Side, Point> calculatePoints() {
        return Arrays.stream(Side.values())
                .filter(side -> !side.isEmpty())
                .collect(Collectors.toMap(Function.identity(), this::getPoint));
    }

    private Point getPoint(Side side) {
        final double totalSum = calculateTotalSum(side);
        final long sameFilePawn = getSameFilePawnCount(side);
        return new Point(totalSum - sameFilePawn * POINT_WHEN_SAME_FILE_PAWN);
    }

    private double calculateTotalSum(final Side side) {
        return board.values()
                .stream()
                .filter(piece -> piece.isSameSide(side))
                .mapToDouble(Piece::getPoint)
                .sum();
    }

    private long getSameFilePawnCount(final Side side) {
        final Set<ChessPosition> positions = board.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isSameSide(side))
                .filter(entry -> entry.getValue().isPawn())
                .map(Entry::getKey)
                .collect(Collectors.toSet());

        return positions.stream()
                .filter(position -> positions.stream()
                        .filter(position::hasSameFile)
                        .count() != 1)
                .count();
    }

    private void changePositions(
            final ChessPosition sourcePosition, final ChessPosition targetPosition,
            final Piece sourcePiece, final Piece targetPiece
    ) {
        board.put(targetPosition, sourcePiece);
        board.put(sourcePosition, sourcePiece.catchTargetPiece(targetPiece));
    }

    private void validateSourcePiece(final Piece sourcePiece) {
        if (sourcePiece.isEmpty()) {
            throw new IllegalArgumentException("Source에 기물이 존재하지 않습니다.");
        }
    }

    private void validatePathContainsPiece(final List<ChessPosition> path) {
        int repeatCount = path.size() - 1;
        IntStream.range(0, repeatCount)
                .mapToObj(path::get)
                .map(board::get)
                .forEach(this::validatePathContainsPiece);
    }

    private void validatePathContainsPiece(final Piece found) {
        if (!found.isEmpty()) {
            throw new IllegalArgumentException("이동 경로에 기물이 존재하여 움직일 수 없습니다.");
        }
    }

    public Map<ChessPosition, Piece> getBoard() {
        return board;
    }

    public boolean checkChessEnd() {
        return calculateKingCount() != KING_COUNT;
    }

    private int calculateKingCount() {
        return (int) board.values()
                .stream()
                .filter(Piece::isKing)
                .count();
    }
}

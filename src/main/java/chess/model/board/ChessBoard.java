package chess.model.board;

import chess.model.piece.Empty;
import chess.model.piece.Piece;
import chess.model.piece.Point;
import chess.model.piece.Side;
import chess.model.position.ChessPosition;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChessBoard {
    private static final int KING_COUNT = 2;
    private static final double POINT_WHEN_SAME_FILE_PAWN = 0.5;
    private final Map<ChessPosition, Piece> board;

    public ChessBoard(final Map<ChessPosition, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public void move(final ChessPosition sourcePosition, final ChessPosition targetPosition) {
        final Piece sourcePiece = findPiece(sourcePosition);
        final Piece targetPiece = findPiece(targetPosition);
        validateSourcePiece(sourcePiece);
        validateSameSide(sourcePiece, targetPiece);
        if (sourcePiece.canMove(sourcePosition, targetPosition, this)) {
            changePositions(sourcePosition, targetPosition, sourcePiece, targetPiece);
            return;
        }
        throw new IllegalArgumentException("Target 위치로 움직일 수 없습니다.");
    }

    public boolean checkChessEnd() {
        return calculateKingCount() != KING_COUNT;
    }

    public boolean isSameSide(ChessPosition position, Side side) {
        return findPiece(position).isSameSide(side);
    }

    public boolean isEmpty(ChessPosition position) {
        return findPiece(position).isEmpty();
    }

    public boolean isEnemy(ChessPosition position, Side side) {
        return findPiece(position).isEnemy(side);
    }

    public Map<Side, Point> calculatePoints() {
        return Arrays.stream(Side.values())
                .filter(side -> !side.isEmpty())
                .collect(Collectors.toMap(Function.identity(), this::calculatePoint));
    }

    private void validateSameSide(final Piece sourcePiece, final Piece targetPiece) {
        if (sourcePiece.isSameSide(targetPiece)) {
            throw new IllegalArgumentException("아군 기물이 있어 움직일 수 없습니다.");
        }
    }

    private void validateSourcePiece(final Piece sourcePiece) {
        if (sourcePiece.isEmpty()) {
            throw new IllegalArgumentException("Source에 기물이 존재하지 않습니다.");
        }
    }

    private void changePositions(
            final ChessPosition sourcePosition, final ChessPosition targetPosition,
            final Piece sourcePiece, final Piece targetPiece) {
        board.put(targetPosition, sourcePiece);
        board.put(sourcePosition, sourcePiece.catchTargetPiece(targetPiece));
    }

    private int calculateKingCount() {
        return (int) board.values()
                .stream()
                .filter(Piece::isKing)
                .count();
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

    private double calculateTotalSum(final Side side) {
        return board.values()
                .stream()
                .filter(piece -> piece.isSameSide(side))
                .mapToDouble(Piece::getPoint)
                .sum();
    }

    private Point calculatePoint(Side side) {
        final double totalSum = calculateTotalSum(side);
        final long sameFilePawn = getSameFilePawnCount(side);
        return new Point(totalSum - sameFilePawn * POINT_WHEN_SAME_FILE_PAWN);
    }

    private Piece findPiece(final ChessPosition position) {
        return board.getOrDefault(position, new Empty());
    }

    public Map<ChessPosition, Piece> getBoard() {
        return board;
    }
}

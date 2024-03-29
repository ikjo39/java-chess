package chess.model.board;

import chess.model.piece.Empty;
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

public class ChessBoard {
    private static final int KING_COUNT = 2;
    private static final int PAWN_COUNT_WHEN_SOLID_IN_FILE = 1;
    private static final double POINT_WHEN_SAME_FILE_PAWN = 0.5;

    private final Map<ChessPosition, Piece> board;
    private final Side turn;

    public ChessBoard(final Map<ChessPosition, Piece> board) {
        this(board, Side.WHITE);
    }

    public ChessBoard(final Map<ChessPosition, Piece> board, final Side turn) {
        this.board = new HashMap<>(board);
        this.turn = turn;
    }

    public ChessBoard move(final ChessPosition sourcePosition, final ChessPosition targetPosition) {
        final Piece sourcePiece = findPiece(sourcePosition);
        final Piece targetPiece = findPiece(targetPosition);
        validateSourcePiece(sourcePiece);
        validateTurn(sourcePiece);
        validateSameSide(sourcePiece, targetPiece);
        if (sourcePiece.canMove(sourcePosition, targetPosition, this)) {
            changePositions(sourcePosition, targetPosition, sourcePiece, targetPiece);
            return new ChessBoard(board, turn.getEnemy());
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

    private void validateTurn(final Piece sourcePiece) {
        if (!sourcePiece.isSameSide(turn)) {
            throw new IllegalArgumentException("반대편 기물을 움직일 차례입니다.");
        }
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

    private boolean hasSameFilePawn(final ChessPosition position, final Set<ChessPosition> positions) {
        return calculatePawnCountSameFile(position, positions) != PAWN_COUNT_WHEN_SOLID_IN_FILE;
    }

    private int calculateKingCount() {
        return (int) board.values()
                .stream()
                .filter(Piece::isKing)
                .count();
    }

    private long getSameFilePawnCount(final Side side) {
        final Set<ChessPosition> positions = findAllSamSidePawns(side);

        return positions.stream()
                .filter(position -> hasSameFilePawn(position, positions))
                .count();
    }

    private long calculatePawnCountSameFile(final ChessPosition position, final Set<ChessPosition> positions) {
        return positions.stream()
                .filter(position::hasSameFile)
                .count();
    }

    private double calculateTotalSum(final Side side) {
        return board.values()
                .stream()
                .filter(piece -> piece.isSameSide(side))
                .mapToDouble(Piece::getPoint)
                .sum();
    }

    private Set<ChessPosition> findAllSamSidePawns(final Side side) {
        return board.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isSameSide(side))
                .filter(entry -> entry.getValue().isPawn())
                .map(Entry::getKey)
                .collect(Collectors.toSet());
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

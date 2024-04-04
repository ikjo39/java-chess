package chess.model.board;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

import chess.dto.ChessBoardDto;
import chess.dto.PieceDto;
import chess.model.piece.Empty;
import chess.model.piece.Piece;
import chess.model.piece.Side;
import chess.model.position.ChessPosition;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChessBoard {
    private static final int NORMAL_KING_COUNT = 2;
    private static final int PAWN_COUNT_WHEN_SOLID_IN_FILE = 1;
    private static final double POINT_WHEN_SAME_FILE_PAWN = -0.5;

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
        validatePieces(sourcePiece, targetPiece);
        validateCanNotMoveTarget(sourcePosition, targetPosition, sourcePiece);
        changePositions(sourcePosition, targetPosition, sourcePiece);
        return new ChessBoard(board, turn.getEnemy());
    }

    public boolean checkChessEnd() {
        return calculateKingCount() != NORMAL_KING_COUNT;
    }

    public boolean isSameSide(final ChessPosition position, final Side side) {
        return findPiece(position).isSameSide(side);
    }

    public boolean isNotEmpty(final ChessPosition position) {
        return !findPiece(position).isEmpty();
    }

    public boolean isEnemy(final ChessPosition position, final Side side) {
        return findPiece(position).isEnemy(side);
    }

    public ChessBoardDto convertDto() {
        return ChessBoardDto.from(convertPieceDtos(), turn.name());
    }

    public Points calculate() {
        return Arrays.stream(Side.values())
                .filter(side -> !side.isEmpty())
                .collect(collectingAndThen(
                        toMap(
                                Function.identity(),
                                this::calculatePiecePoints,
                                (key1, key2) -> {
                                    throw new IllegalArgumentException("Duplicated Keys");
                                },
                                () -> new EnumMap<>(Side.class)
                        ),
                        Points::new));
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

    private long calculatePawnCountSameFile(final ChessPosition position, final Set<ChessPosition> positions) {
        return positions.stream()
                .filter(position::hasSameFile)
                .count();
    }

    private long getSameFilePawnCount(final Side side) {
        final Set<ChessPosition> positions = findAllSamSidePawns(side);
        return positions.stream()
                .filter(position -> hasSameFilePawn(position, positions))
                .count();
    }

    private void validatePieces(final Piece sourcePiece, final Piece targetPiece) {
        validateSourcePiece(sourcePiece);
        validateTurn(sourcePiece);
        validateSameSide(sourcePiece, targetPiece);
    }

    private void validateCanNotMoveTarget(final ChessPosition sourcePosition,
                                          final ChessPosition targetPosition,
                                          final Piece sourcePiece) {
        if (!sourcePiece.canMove(sourcePosition, targetPosition, this)) {
            throw new IllegalArgumentException("Target 위치로 움직일 수 없습니다.");
        }
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

    private void changePositions(final ChessPosition sourcePosition,
                                 final ChessPosition targetPosition,
                                 final Piece sourcePiece) {
        board.put(targetPosition, sourcePiece);
        board.put(sourcePosition, new Empty());
    }

    private Piece findPiece(final ChessPosition position) {
        return board.getOrDefault(position, new Empty());
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

    private Set<ChessPosition> findAllSamSidePawns(final Side side) {
        return board.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isSameSide(side))
                .filter(entry -> entry.getValue().isPawn())
                .map(Entry::getKey)
                .collect(Collectors.toSet());
    }

    private Set<PieceDto> convertPieceDtos() {
        return board.entrySet()
                .stream()
                .map(entry -> PieceDto.from(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
    }

    public Map<ChessPosition, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }

    public Side getTurn() {
        return turn;
    }
}

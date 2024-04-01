package chess.model.board;

import chess.dto.ChessBoardDto;
import chess.dto.PieceDto;
import chess.model.piece.Empty;
import chess.model.piece.Piece;
import chess.model.piece.Side;
import chess.model.position.ChessPosition;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ChessBoard {
    private static final int KING_COUNT = 2;

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

    public boolean isNotEmpty(ChessPosition position) {
        return !findPiece(position).isEmpty();
    }

    public boolean isEnemy(ChessPosition position, Side side) {
        return findPiece(position).isEnemy(side);
    }

    public PointCalculator getPointCalculator() {
        return new PointCalculator(Collections.unmodifiableMap(board));
    }

    public ChessBoardDto convertDto() {
        return ChessBoardDto.from(board.entrySet()
                .stream()
                .map(entry -> PieceDto.from(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet()), turn.name());
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

    private int calculateKingCount() {
        return (int) board.values()
                .stream()
                .filter(Piece::isKing)
                .count();
    }

    private Piece findPiece(final ChessPosition position) {
        return board.getOrDefault(position, new Empty());
    }

    public Map<ChessPosition, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }

    public Side getTurn() {
        return turn;
    }
}

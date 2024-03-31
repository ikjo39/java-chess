package chess.dto;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

import chess.model.board.ChessBoard;
import chess.model.piece.Side;
import java.util.Objects;
import java.util.Set;

public final class ChessBoardDto {
        private final Set<PieceDto> pieces;
        private final String turn;

    private ChessBoardDto(Set<PieceDto> pieces, String turn) {
        this.pieces = pieces;
        this.turn = turn;
    }

    public static ChessBoardDto from(Set<PieceDto> pieces, String turn) {
        return new ChessBoardDto(pieces, turn);
    }

    public ChessBoard convert() {
        return pieces.stream()
                .collect(collectingAndThen(
                        toMap(PieceDto::convertPosition, PieceDto::convertPiece),
                        board -> new ChessBoard(board, Side.from(turn))
                ));
    }

    public Set<PieceDto> pieces() {
        return pieces;
    }

    public String turn() {
        return turn;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ChessBoardDto) obj;
        return Objects.equals(this.pieces, that.pieces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieces);
    }

    @Override
    public String toString() {
        return "ChessBoardDto[" +
                "pieces=" + pieces + ']';
    }
}

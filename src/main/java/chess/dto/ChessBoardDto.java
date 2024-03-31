package chess.dto;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

import chess.model.board.ChessBoard;
import java.util.Objects;
import java.util.Set;

public final class ChessBoardDto {
    private final Set<PieceDto> pieces;

    private ChessBoardDto(Set<PieceDto> pieces) {
        this.pieces = pieces;
    }

    public static ChessBoardDto from(Set<PieceDto> pieces) {
        return new ChessBoardDto(pieces);
    }

    public ChessBoard convert() {
        return pieces.stream()
                .collect(collectingAndThen(toMap(PieceDto::convertPosition, PieceDto::convertPiece), ChessBoard::new));
    }

    public Set<PieceDto> pieces() {
        return pieces;
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

package chess.dto;

import chess.model.piece.Piece;
import chess.model.piece.PieceText;
import chess.model.position.ChessPosition;
import chess.model.position.File;
import chess.model.position.Rank;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public final class PieceDto {
    private final String position;
    private final String type;

    private PieceDto(String position, String type) {
        this.position = position;
        this.type = type;
    }

    public static PieceDto from(String position, String type) {
        return new PieceDto(position, type);
    }

    public static PieceDto from(ChessPosition chessPosition, Piece piece) {
        PieceText pieceText = PieceText.from(piece);
        return new PieceDto(chessPosition.getName(), pieceText.getName());
    }

    public ChessPosition convertPosition() {
        String[] positionTexts = position.split("");
        File file = File.from(positionTexts[0]);
        Rank rank = Rank.from(convertRankText(positionTexts));
        return new ChessPosition(file, rank);
    }

    public Piece convertPiece() {
        return PieceText.from(type)
                .getPiece();
    }

    private String convertRankText(String[] positionTexts) {
        return Arrays.stream(positionTexts)
                .skip(1)
                .collect(Collectors.joining(""));
    }

    public String position() {
        return position;
    }

    public String type() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (PieceDto) obj;
        return Objects.equals(this.position, that.position) &&
                Objects.equals(this.type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, type);
    }

    @Override
    public String toString() {
        return "PieceDto[" +
                "position=" + position + ", " +
                "type=" + type + ']';
    }
}

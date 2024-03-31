package chess.model.piece;

import java.util.Arrays;
import java.util.function.Predicate;

public enum PieceText {
    WHITE_PAWN("p", piece -> piece instanceof Pawn && piece.isSameSide(Side.WHITE), new WhitePawn()),
    WHITE_ROOK("r", piece -> piece instanceof Rook && piece.isSameSide(Side.WHITE), new Rook(Side.WHITE)),
    WHITE_KNIGHT("n", piece -> piece instanceof Knight && piece.isSameSide(Side.WHITE), new Knight(Side.WHITE)),
    WHITE_BISHOP("b", piece -> piece instanceof Bishop && piece.isSameSide(Side.WHITE), new Bishop(Side.WHITE)),
    WHITE_QUEEN("q", piece -> piece instanceof Queen && piece.isSameSide(Side.WHITE), new Queen(Side.WHITE)),
    WHITE_KING("k", piece -> piece instanceof King && piece.isSameSide(Side.WHITE), new King(Side.WHITE)),

    BLACK_PAWN("P", piece -> piece instanceof Pawn && piece.isSameSide(Side.BLACK), new BlackPawn()),
    BLACK_ROOK("R", piece -> piece instanceof Rook && piece.isSameSide(Side.BLACK), new Rook(Side.BLACK)),
    BLACK_KNIGHT("N", piece -> piece instanceof Knight && piece.isSameSide(Side.BLACK), new Knight(Side.BLACK)),
    BLACK_BISHOP("B", piece -> piece instanceof Bishop && piece.isSameSide(Side.BLACK), new Bishop(Side.BLACK)),
    BLACK_QUEEN("Q", piece -> piece instanceof Queen && piece.isSameSide(Side.BLACK), new Queen(Side.BLACK)),
    BLACK_KING("K", piece -> piece instanceof King && piece.isSameSide(Side.BLACK), new King(Side.BLACK)),

    EMPTY(".", Piece::isEmpty, new Empty())
    ;

    private final String name;
    private final Predicate<Piece> typeDiscriminator;
    private final Piece piece;

    PieceText(String name, Predicate<Piece> typeDiscriminator, Piece piece) {
        this.name = name;
        this.typeDiscriminator = typeDiscriminator;
        this.piece = piece;
    }

    public static PieceText from(String value) {
        return Arrays.stream(values())
                .filter(pieceText -> pieceText.name.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("기물 텍스트가 존재하지 않습니다."));
    }

    public static PieceText from(Piece piece) {
        return Arrays.stream(values())
                .filter(pieceText -> pieceText.typeDiscriminator.test(piece))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("기물 텍스트가 존재하지 않습니다."));
    }

    public String getName() {
        return name;
    }

    public Piece getPiece() {
        return piece;
    }
}

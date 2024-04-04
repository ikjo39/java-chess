package chess.model.piece;

import java.util.Arrays;
import java.util.function.Predicate;

public enum PieceText {
    WHITE_PAWN(
            "p",
            type -> type instanceof Pawn && type.isSameSide(Side.WHITE),
            new WhitePawn()
    ),
    WHITE_ROOK(
            "r",
            type -> type instanceof Rook && type.isSameSide(Side.WHITE),
            new Rook(Side.WHITE)
    ),
    WHITE_KNIGHT(
            "n",
            type -> type instanceof Knight && type.isSameSide(Side.WHITE),
            new Knight(Side.WHITE)
    ),
    WHITE_BISHOP(
            "b",
            type -> type instanceof Bishop && type.isSameSide(Side.WHITE),
            new Bishop(Side.WHITE)
    ),
    WHITE_QUEEN(
            "q",
            type -> type instanceof Queen && type.isSameSide(Side.WHITE),
            new Queen(Side.WHITE)
    ),
    WHITE_KING(
            "k",
            type -> type instanceof King && type.isSameSide(Side.WHITE),
            new King(Side.WHITE)
    ),

    BLACK_PAWN(
            "P",
            type -> type instanceof Pawn && type.isSameSide(Side.BLACK),
            new BlackPawn()
    ),
    BLACK_ROOK(
            "R",
            type -> type instanceof Rook && type.isSameSide(Side.BLACK),
            new Rook(Side.BLACK)
    ),
    BLACK_KNIGHT(
            "N",
            type -> type instanceof Knight && type.isSameSide(Side.BLACK),
            new Knight(Side.BLACK)
    ),
    BLACK_BISHOP(
            "B",
            type -> type instanceof Bishop && type.isSameSide(Side.BLACK),
            new Bishop(Side.BLACK)
    ),
    BLACK_QUEEN(
            "Q",
            type -> type instanceof Queen && type.isSameSide(Side.BLACK),
            new Queen(Side.BLACK)
    ),
    BLACK_KING(
            "K",
            type -> type instanceof King && type.isSameSide(Side.BLACK),
            new King(Side.BLACK)
    ),

    EMPTY(
            ".",
            Piece::isEmpty,
            new Empty()
    );

    private final String name;
    private final Predicate<Piece> typeDiscriminator;
    private final Piece piece;

    PieceText(final String name,
              final Predicate<Piece> typeDiscriminator,
              final Piece piece) {
        this.name = name;
        this.typeDiscriminator = typeDiscriminator;
        this.piece = piece;
    }

    public static PieceText from(final String value) {
        return Arrays.stream(values())
                .filter(pieceText -> pieceText.name.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 값과 일치하는 기물 문구가 존재하지 않습니다."));
    }

    public static PieceText from(final Piece piece) {
        return Arrays.stream(values())
                .filter(pieceText -> pieceText.typeDiscriminator.test(piece))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("해당 기물과 일치하는 기물 문구가 존재하지 않습니다."));
    }

    public String getName() {
        return name;
    }

    public Piece getPiece() {
        return piece;
    }
}

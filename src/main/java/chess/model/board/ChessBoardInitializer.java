package chess.model.board;

import chess.model.piece.Bishop;
import chess.model.piece.BlackPawn;
import chess.model.piece.Empty;
import chess.model.piece.King;
import chess.model.piece.Knight;
import chess.model.piece.Piece;
import chess.model.piece.Queen;
import chess.model.piece.Rook;
import chess.model.piece.Side;
import chess.model.piece.WhitePawn;
import chess.model.position.ChessPosition;
import chess.model.position.File;
import chess.model.position.Rank;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChessBoardInitializer {
    private ChessBoardInitializer() {
    }

    public static Map<ChessPosition, Piece> create() {
        final Map<ChessPosition, Piece> board = createInitialBoard();
        board.putAll(createSpecialPieces(Side.BLACK));
        board.putAll(createPawns());
        board.putAll(createSpecialPieces(Side.WHITE));
        return board;
    }

    private static Map<ChessPosition, Piece> createInitialBoard() {
        return Arrays.stream(Rank.values())
                .flatMap(rank -> Arrays.stream(File.values())
                        .map(file -> new ChessPosition(file, rank)))
                .collect(Collectors.toMap(Function.identity(), chessPosition -> new Empty()));
    }

    private static Map<ChessPosition, Piece> createSpecialPieces(final Side side) {
        final Rank rank = convertSpecialPieceRankWithSide(side);
        return Map.of(
                new ChessPosition(File.A, rank), new Rook(side),
                new ChessPosition(File.B, rank), new Knight(side),
                new ChessPosition(File.C, rank), new Bishop(side),
                new ChessPosition(File.D, rank), new Queen(side),
                new ChessPosition(File.E, rank), new King(side),
                new ChessPosition(File.F, rank), new Bishop(side),
                new ChessPosition(File.G, rank), new Knight(side),
                new ChessPosition(File.H, rank), new Rook(side)
        );
    }

    private static Map<ChessPosition, Piece> createPawns() {
        return Map.ofEntries(
                Map.entry(new ChessPosition(File.A, Rank.TWO), new WhitePawn()),
                Map.entry(new ChessPosition(File.B, Rank.TWO), new WhitePawn()),
                Map.entry(new ChessPosition(File.C, Rank.TWO), new WhitePawn()),
                Map.entry(new ChessPosition(File.D, Rank.TWO), new WhitePawn()),
                Map.entry(new ChessPosition(File.E, Rank.TWO), new WhitePawn()),
                Map.entry(new ChessPosition(File.F, Rank.TWO), new WhitePawn()),
                Map.entry(new ChessPosition(File.G, Rank.TWO), new WhitePawn()),
                Map.entry(new ChessPosition(File.H, Rank.TWO), new WhitePawn()),
                Map.entry(new ChessPosition(File.A, Rank.SEVEN), new BlackPawn()),
                Map.entry(new ChessPosition(File.B, Rank.SEVEN), new BlackPawn()),
                Map.entry(new ChessPosition(File.C, Rank.SEVEN), new BlackPawn()),
                Map.entry(new ChessPosition(File.D, Rank.SEVEN), new BlackPawn()),
                Map.entry(new ChessPosition(File.E, Rank.SEVEN), new BlackPawn()),
                Map.entry(new ChessPosition(File.F, Rank.SEVEN), new BlackPawn()),
                Map.entry(new ChessPosition(File.G, Rank.SEVEN), new BlackPawn()),
                Map.entry(new ChessPosition(File.H, Rank.SEVEN), new BlackPawn())
        );
    }

    private static Rank convertSpecialPieceRankWithSide(final Side side) {
        if (side == Side.BLACK) {
            return Rank.EIGHT;
        }
        return Rank.ONE;
    }
}

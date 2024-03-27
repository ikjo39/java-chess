package chess.model.piece;

import chess.model.position.ChessPosition;
import chess.model.position.File;
import chess.model.position.Rank;
import java.util.Arrays;
import java.util.List;

public class BlackPawn extends Pawn {
    private static final List<ChessPosition> INITIAL_BLACK_POSITION = Arrays.stream(File.values())
            .map(file -> new ChessPosition(file, Rank.SEVEN))
            .toList();

    public BlackPawn() {
        super(Side.BLACK);
    }

    @Override
    protected boolean isPawnInitialPosition(final ChessPosition source) {
        return INITIAL_BLACK_POSITION.contains(source);
    }
}

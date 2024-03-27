package chess.model.piece;

import chess.model.position.ChessPosition;
import chess.model.position.File;
import chess.model.position.Rank;
import java.util.Arrays;
import java.util.List;

public class WhitePawn extends Pawn {
    private static final List<ChessPosition> INITIAL_WHITE_POSITION = Arrays.stream(File.values())
            .map(file -> new ChessPosition(file, Rank.TWO))
            .toList();

    public WhitePawn() {
        super(Side.WHITE);
    }

    @Override
    protected boolean isPawnInitialPosition(final ChessPosition source) {
        return INITIAL_WHITE_POSITION.contains(source);
    }
}

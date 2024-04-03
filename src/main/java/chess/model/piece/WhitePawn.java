package chess.model.piece;

import static chess.model.position.Direction.UP;
import static chess.model.position.Direction.UP_LEFT;
import static chess.model.position.Direction.UP_RIGHT;
import static chess.model.position.Direction.UP_UP;

import chess.model.board.ChessBoard;
import chess.model.position.ChessPosition;
import chess.model.position.Direction;
import chess.model.position.File;
import chess.model.position.Rank;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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

    @Override
    protected boolean canMoveVerticalPaths(final ChessPosition source,
                                           final ChessBoard chessBoard,
                                           final Direction direction) {
        for (int i = direction.getY(); i <= -1; i++) {
            if (!source.canMoveVertical(i) || chessBoard.isNotEmpty(source.moveVertical(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected Set<Direction> availableDirections() {
        return Set.of(UP, UP_UP, UP_LEFT, UP_RIGHT);
    }
}

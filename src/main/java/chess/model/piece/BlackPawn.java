package chess.model.piece;

import static chess.model.position.Direction.DOWN;
import static chess.model.position.Direction.DOWN_DOWN;
import static chess.model.position.Direction.DOWN_LEFT;
import static chess.model.position.Direction.DOWN_RIGHT;

import chess.model.board.ChessBoard;
import chess.model.position.ChessPosition;
import chess.model.position.Direction;
import chess.model.position.File;
import chess.model.position.Rank;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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

    @Override
    protected boolean canMoveVerticalPaths(final Direction direction, final ChessBoard chessBoard,
                                           final ChessPosition source) {
        for (int i = 1; i <= direction.getY(); i++) {
            if (!source.canMoveVertical(i) || !chessBoard.isEmpty(source.moveVertical(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected Set<Direction> availableDirections() {
        return Set.of(DOWN, DOWN_DOWN, DOWN_LEFT, DOWN_RIGHT);
    }
}

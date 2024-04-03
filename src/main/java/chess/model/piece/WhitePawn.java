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
import java.util.stream.IntStream;

public class WhitePawn extends Pawn {
    private static final int MINIMUM_WHITE_PAWN_MOVING_STEP = -1;
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
        return IntStream.rangeClosed(direction.getY(), MINIMUM_WHITE_PAWN_MOVING_STEP)
                .allMatch(step -> canMoveVertical(source, chessBoard, step));
    }

    @Override
    protected Set<Direction> availableDirections() {
        return Set.of(UP, UP_UP, UP_LEFT, UP_RIGHT);
    }
}

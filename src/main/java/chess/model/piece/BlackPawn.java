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
import java.util.stream.IntStream;

public class BlackPawn extends Pawn {
    private static final int MINIMUM_BLACK_PAWN_MOVING_STEP = 1;
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
    protected boolean canMoveVerticalPaths(final ChessPosition source,
                                           final ChessBoard chessBoard,
                                           final Direction direction) {
        return IntStream.rangeClosed(MINIMUM_BLACK_PAWN_MOVING_STEP, direction.getY())
                .allMatch(step -> canMoveVertical(source, chessBoard, step));
    }

    @Override
    protected Set<Direction> availableDirections() {
        return Set.of(DOWN, DOWN_DOWN, DOWN_LEFT, DOWN_RIGHT);
    }
}

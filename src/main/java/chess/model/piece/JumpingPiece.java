package chess.model.piece;

import chess.model.board.ChessBoard;
import chess.model.position.ChessPosition;
import chess.model.position.Direction;
import java.util.HashSet;
import java.util.Set;

public abstract class JumpingPiece extends Piece {
    protected JumpingPiece(final Side side) {
        super(side);
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isPawn() {
        return false;
    }

    @Override
    protected Set<ChessPosition> calculatePaths(final ChessPosition source, final ChessPosition target,
                                                final ChessBoard chessBoard) {
        final Set<ChessPosition> paths = new HashSet<>();
        final Set<Direction> directions = availableDirections();
        directions.forEach(direction -> addAllPossiblePaths(source, chessBoard, direction, paths));
        return paths;
    }

    private void addAllPossiblePaths(ChessPosition source, ChessBoard chessBoard, Direction direction, Set<ChessPosition> paths) {
        ChessPosition current = source;
        while (current.canMove(direction)) {
            current = current.move(direction);
            if (chessBoard.isSameSide(current, side)) {
                break;
            }
            paths.add(current);
            if (chessBoard.isEnemy(current, side)) {
                break;
            }
        }
    }
}

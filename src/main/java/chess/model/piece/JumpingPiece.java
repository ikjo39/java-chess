package chess.model.piece;

import chess.model.board.ChessBoard;
import chess.model.position.ChessPosition;
import chess.model.position.Direction;
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
    protected void addPossiblePaths(final ChessPosition source, final ChessBoard chessBoard,
                                    final Set<ChessPosition> paths, final Set<Direction> directions) {
        directions.forEach(direction -> addPossiblePaths(source, chessBoard, paths, direction));
    }

    @Override
    protected void addPossiblePaths(final ChessPosition source, final ChessBoard chessBoard,
                                    final Set<ChessPosition> paths, final Direction direction) {
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

package chess.model.piece;

import chess.model.board.ChessBoard;
import chess.model.board.Point;
import chess.model.position.ChessPosition;
import chess.model.position.Direction;
import java.util.Set;

public class Empty extends Piece {
    public Empty() {
        super(Side.EMPTY);
    }

    @Override
    public boolean canMove(final ChessPosition source, final ChessPosition target, final ChessBoard chessBoard) {
        return false;
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
    public Point getPoint() {
        return Point.getDefaults();
    }

    @Override
    protected Set<Direction> availableDirections() {
        throw new UnsupportedOperationException("Source는 빈 기물일 수 없습니다.");
    }

    @Override
    protected void addPossiblePaths(final ChessPosition source, final ChessBoard chessBoard,
                                    final Set<ChessPosition> paths, final Set<Direction> directions) {
        throw new UnsupportedOperationException("빈 기물은 경로를 탐색할 수 없습니다.");
    }

    @Override
    protected void addPossiblePaths(final ChessPosition source, final ChessBoard chessBoard,
                                    final Set<ChessPosition> paths, final Direction direction) {
        throw new UnsupportedOperationException("빈 기물은 경로를 탐색할 수 없습니다.");
    }
}

package chess.model.piece;

import chess.model.position.ChessPosition;
import chess.model.position.Distance;
import java.util.List;

public class Queen extends Piece {

    private static final int QUEEN_POINT = 9;

    public Queen(final Side side) {
        super(side);
    }

    @Override
    public List<ChessPosition> findPath(final ChessPosition source, final ChessPosition target,
                                        final Piece targetPiece) {
        checkValidTargetPiece(targetPiece);
        final Distance distance = target.calculateDistance(source);
        if (canMove(distance)) {
            return distance.findPath(source);
        }
        throw new IllegalStateException("퀸은 해당 경로로 이동할 수 없습니다.");
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
    public double getPoint() {
        return QUEEN_POINT;
    }

    private boolean canMove(final Distance distance) {
        return distance.isCrossMovement() || distance.isDiagonalMovement();
    }
}

package chess.model.piece;

import chess.model.position.ChessPosition;
import chess.model.position.Distance;
import java.util.List;

public class Knight extends Piece {
    private static final int DISPLACEMENT = 3;
    private static final double KNIGHT_POINT = 2.5;

    public Knight(final Side side) {
        super(side);
    }

    @Override
    public List<ChessPosition> findPath(final ChessPosition source, final ChessPosition target,
                                        final Piece targetPiece) {
        checkValidTargetPiece(targetPiece);
        final Distance distance = target.calculateDistance(source);
        if (canMove(distance)) {
            return List.of(target);
        }
        throw new IllegalStateException("나이트는 해당 경로로 이동할 수 없습니다.");
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
        return KNIGHT_POINT;
    }

    private boolean canMove(final Distance distance) {
        return distance.hasSame(DISPLACEMENT)
                && !distance.isCrossMovement()
                && !distance.isDiagonalMovement();
    }
}

package chess.model.piece;

import chess.model.position.ChessPosition;
import chess.model.position.Distance;
import java.util.List;

public abstract class Pawn extends Piece {
    private static final int DISPLACEMENT = 1;
    private static final int INITIAL_SPECIAL_DISPLACEMENT = 2;
    private static final int PAWN_POINT = 1;

    protected Pawn(final Side side) {
        super(side);
    }

    protected abstract boolean isPawnInitialPosition(final ChessPosition source);

    @Override
    public List<ChessPosition> findPath(
            final ChessPosition source, final ChessPosition target, final Piece targetPiece
    ) {
        checkValidTargetPiece(targetPiece);
        final Distance distance = target.calculateDistance(source);
        validateForwardPath(source, targetPiece, distance);
        if (canCrossMove(source, distance) || canDiagonalMove(targetPiece, distance)) {
            return distance.findPath(source);
        }
        throw new IllegalStateException("폰은 해당 경로로 이동할 수 없습니다.");
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isPawn() {
        return true;
    }

    @Override
    public double getPoint() {
        return PAWN_POINT;
    }

    private void validateForwardPath(
            final ChessPosition source, final Piece targetPiece, final Distance distance
    ) {
        if (!targetPiece.isEmpty() && canCrossMove(source, distance)) {
            throw new IllegalArgumentException("타겟 위치에 기물이 존재하여 전진할 수 없습니다.");
        }
    }

    private boolean canCrossMove(final ChessPosition source, final Distance distance) {
        if (isPawnInitialPosition(source)) {
            return canMoveForwardWith(distance, DISPLACEMENT) ||
                    canMoveForwardWith(distance, INITIAL_SPECIAL_DISPLACEMENT);
        }
        return canMoveForwardWith(distance, DISPLACEMENT);
    }

    private boolean canDiagonalMove(final Piece targetPiece, final Distance distance) {
        return isPossibleDiagonal(distance)
                && !targetPiece.isEmpty()
                && !isSameSide(targetPiece);
    }

    private boolean canMoveForwardWith(final Distance distance, final int displacement) {
        return distance.isForward(side) && distance.hasSame(displacement);
    }

    private boolean isPossibleDiagonal(final Distance distance) {
        return distance.isDiagonalMovement() && distance.hasSame(DISPLACEMENT);
    }
}

package chess.model.piece;

import chess.model.position.ChessPosition;
import chess.model.position.Distance;
import java.util.List;

public class Rook extends Piece {
    private static final int ROOK_POINT = 5;

    public Rook(Side side) {
        super(side);
    }

    @Override
    public List<ChessPosition> findPath(final ChessPosition source, final ChessPosition target,
                                        final Piece targetPiece) {
        checkValidTargetPiece(targetPiece);
        final Distance distance = target.calculateDistance(source);
        if (distance.isCrossMovement()) {
            return distance.findPath(source);
        }
        throw new IllegalStateException("룩은 해당 경로로 이동할 수 없습니다.");
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
        return ROOK_POINT;
    }
}

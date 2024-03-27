package chess.model.piece;

import chess.model.position.ChessPosition;
import chess.model.position.Distance;
import java.util.List;

public class King extends Piece {
    private static final int DISPLACEMENT = 1;
    private static final int KING_POINT = 0;

    public King(final Side side) {
        super(side);
    }

    @Override
    public List<ChessPosition> findPath(
            final ChessPosition source, final ChessPosition target, final Piece targetPiece
    ) {
        checkValidTargetPiece(targetPiece);
        final Distance distance = target.calculateDistance(source);
        if (distance.hasSame(DISPLACEMENT)) {
            return List.of(target);
        }
        throw new IllegalStateException("왕은 해당 경로로 이동할 수 없습니다.");
    }

    @Override
    public boolean isKing() {
        return true;
    }

    @Override
    public boolean isPawn() {
        return false;
    }

    @Override
    public double getPoint() {
        return KING_POINT;
    }
}

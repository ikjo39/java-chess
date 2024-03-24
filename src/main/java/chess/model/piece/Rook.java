package chess.model.piece;

import chess.model.position.ChessPosition;
import chess.model.position.Distance;
import java.util.List;

public class Rook extends Piece {
    public Rook(Side side) {
        super(side);
    }

    @Override
    public List<ChessPosition> findPath(ChessPosition source, ChessPosition target, Piece targetPiece) {
        checkValidTargetPiece(targetPiece);
        Distance distance = target.calculateDistance(source);
        if (distance.isCrossMovement()) {
            return distance.findPath(source);
        }
        throw new IllegalStateException("룩은 해당 경로로 이동할 수 없습니다.");
    }
}

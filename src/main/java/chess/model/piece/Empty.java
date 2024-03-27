package chess.model.piece;

import chess.model.position.ChessPosition;
import java.util.List;

public class Empty extends Piece {
    public Empty() {
        super(Side.EMPTY);
    }

    @Override
    public List<ChessPosition> findPath(
            final ChessPosition source, final ChessPosition target, final Piece targetPiece
    ) {
        throw new IllegalStateException("해당 경로로 이동할 수 없습니다.");
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
        return 0;
    }
}

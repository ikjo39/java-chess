package chess.model.piece;

import static chess.model.Fixture.A1;
import static chess.model.Fixture.B7;
import static chess.model.Fixture.C2;
import static chess.model.Fixture.C3;
import static chess.model.Fixture.D7;
import static chess.model.Fixture.E5;
import static chess.model.Fixture.F2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.model.board.ChessBoard;
import chess.model.board.ChessBoardInitializer;
import chess.model.board.Point;
import chess.model.position.ChessPosition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmptyTest {

    @Test
    @DisplayName("빈 기물은 항상 움직일 수 없다.")
    void canMove() {
        //given
        final ChessPosition now = C2;
        final Empty empty = new Empty();
        ChessBoard chessBoard = new ChessBoard(ChessBoardInitializer.create());

        //when //then
        assertAll(
                () -> assertThat(empty.canMove(now, A1, chessBoard)).isFalse(),
                () -> assertThat(empty.canMove(now, B7, chessBoard)).isFalse(),
                () -> assertThat(empty.canMove(now, C3, chessBoard)).isFalse(),
                () -> assertThat(empty.canMove(now, D7, chessBoard)).isFalse(),
                () -> assertThat(empty.canMove(now, E5, chessBoard)).isFalse(),
                () -> assertThat(empty.canMove(now, F2, chessBoard)).isFalse()
        );
    }

    @Test
    @DisplayName("왕인지 판단한다.")
    void isKing() {
        //given
        final Empty empty = new Empty();

        //when
        final boolean result = empty.isKing();

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("폰인지 판단한다.")
    void isPawn() {
        //given
        final Empty empty = new Empty();

        //when
        final boolean result = empty.isPawn();

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("점수를 반환한다.")
    void getPoint() {
        //given
        final Empty empty = new Empty();

        //when
        final Point result = empty.getPoint();

        //then
        assertThat(result).isEqualTo(Point.from(0));
    }
}

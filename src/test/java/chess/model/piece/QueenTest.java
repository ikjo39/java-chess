package chess.model.piece;

import static chess.model.Fixture.A1;
import static chess.model.Fixture.A7;
import static chess.model.Fixture.B6;
import static chess.model.Fixture.B8;
import static chess.model.Fixture.C7;
import static chess.model.Fixture.C8;
import static chess.model.Fixture.D5;
import static chess.model.Fixture.D7;
import static chess.model.Fixture.E1;
import static chess.model.Fixture.E3;
import static chess.model.Fixture.E4;
import static chess.model.Fixture.E6;
import static chess.model.Fixture.E7;
import static chess.model.Fixture.E8;
import static chess.model.Fixture.F1;
import static chess.model.Fixture.F2;
import static chess.model.Fixture.F3;
import static chess.model.Fixture.F4;
import static chess.model.Fixture.G2;
import static chess.model.Fixture.G4;
import static chess.model.Fixture.G8;
import static chess.model.Fixture.H3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.model.board.ChessBoard;
import chess.model.board.Point;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class QueenTest {

    private ChessBoard chessBoard;

    /*
    퀸의 위치(E6)
        .KR..... 8
        P.PB.... 7
        .P..Q... 6
        ...b.... 5
        .....Xq. 4
        .....p.p 3
        .....pp. 2
        ....rk.. 1
        abcdefgh
    */
    @BeforeEach
    void setUp() {
        chessBoard = new ChessBoard(Map.ofEntries(
                Map.entry(B8, new King(Side.BLACK)),
                Map.entry(C8, new Rook(Side.BLACK)),
                Map.entry(A7, new BlackPawn()),
                Map.entry(C7, new BlackPawn()),
                Map.entry(D7, new Bishop(Side.BLACK)),
                Map.entry(B6, new BlackPawn()),
                Map.entry(E6, new Queen(Side.BLACK)),
                Map.entry(D5, new Bishop(Side.WHITE)),
                Map.entry(F4, new Knight(Side.WHITE)),
                Map.entry(G4, new Queen(Side.WHITE)),
                Map.entry(F3, new WhitePawn()),
                Map.entry(H3, new WhitePawn()),
                Map.entry(F2, new WhitePawn()),
                Map.entry(G2, new WhitePawn()),
                Map.entry(E1, new Rook(Side.WHITE)),
                Map.entry(F1, new King(Side.WHITE))));
    }

    @Test
    @DisplayName("퀸가 이동할 수 있는지 판단한다.")
    void canMove() {
        //given
        final Queen queen = new Queen(Side.BLACK);

        //when //then
        assertAll(
                () -> assertThat(queen.canMove(E6, E8, chessBoard)).isTrue(),
                () -> assertThat(queen.canMove(E6, E7, chessBoard)).isTrue(),
                () -> assertThat(queen.canMove(E6, E4, chessBoard)).isTrue(),
                () -> assertThat(queen.canMove(E6, E3, chessBoard)).isTrue(),
                () -> assertThat(queen.canMove(E6, G8, chessBoard)).isTrue()
        );
    }

    @Test
    @DisplayName("퀸가 갈 수 있는 위치에 적 기물이 있으면 이동할 수 있다.")
    void canMoveWhenEnemy() {
        //given
        final Queen queen = new Queen(Side.BLACK);

        //when
        final boolean result = queen.canMove(E6, D5, chessBoard);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("아군 기물이 있다면 이동할 수 없다.")
    void canNotMove() {
        //given
        final Queen queen = new Queen(Side.BLACK);
        final ChessBoard chessBoardQueenWithSameSide = chessBoard;

        //when
        final boolean result = queen.canMove(E6, D7, chessBoardQueenWithSameSide);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("퀸의 움직임으로 갈 수 없다면 움직일 수 없다.")
    void canNotMoveWithInvalidMove() {
        //given
        final Queen queen = new Queen(Side.BLACK);

        //when
        final boolean result = queen.canMove(E6, A1, chessBoard);

        //then
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"BLACK", "WHITE"})
    @DisplayName("왕인지 판단한다.")
    void isKing(final Side side) {
        //given
        final Queen queen = new Queen(side);

        //when
        final boolean result = queen.isKing();

        //then
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"BLACK", "WHITE"})
    @DisplayName("폰인지 판단한다.")
    void isPawn(final Side side) {
        //given
        final Queen queen = new Queen(side);

        //when
        final boolean result = queen.isPawn();

        //then
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"BLACK", "WHITE"})
    @DisplayName("점수를 반환한다.")
    void getPoint(final Side side) {
        //given
        final Queen queen = new Queen(side);

        //when
        final Point result = queen.getPoint();

        //then
        assertThat(result).isEqualTo(Point.from(9));
    }
}

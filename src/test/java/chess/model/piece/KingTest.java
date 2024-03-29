package chess.model.piece;

import static chess.model.Fixture.A4;
import static chess.model.Fixture.A7;
import static chess.model.Fixture.A8;
import static chess.model.Fixture.B6;
import static chess.model.Fixture.B7;
import static chess.model.Fixture.B8;
import static chess.model.Fixture.C6;
import static chess.model.Fixture.C7;
import static chess.model.Fixture.C8;
import static chess.model.Fixture.D7;
import static chess.model.Fixture.E1;
import static chess.model.Fixture.E6;
import static chess.model.Fixture.F1;
import static chess.model.Fixture.F2;
import static chess.model.Fixture.F3;
import static chess.model.Fixture.F4;
import static chess.model.Fixture.G2;
import static chess.model.Fixture.G4;
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

class KingTest {

    private ChessBoard chessBoard;

    /*
        .KR..... 8
        P.PB.... 7
        .P..Q... 6
        ........ 5
        .....nq. 4
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
                Map.entry(C6, new Knight(Side.WHITE)),
                Map.entry(E6, new Queen(Side.BLACK)),
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
    @DisplayName("킹이 이동할 수 있는지 판단한다.")
    void canMove() {
        //given
        King king = new King(Side.BLACK);

        //when //then
        assertAll(
                () -> assertThat(king.canMove(B8, A8, chessBoard)).isTrue(),
                () -> assertThat(king.canMove(B8, B7, chessBoard)).isTrue()
        );
    }

    @Test
    @DisplayName("킹이 갈 수 있는 위치에 적 기물이 있으면 이동할 수 있다.")
    void canMoveWhenEnemy() {
        //given
        King king = new King(Side.WHITE);

        //when
        boolean result = king.canMove(B8, C8, chessBoard);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("아군 기물이 있다면 이동할 수 없다.")
    void canNotMove() {
        //given
        final King king = new King(Side.BLACK);

        //when
        boolean result = king.canMove(B8, C8, chessBoard);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("킹의 움직임으로 갈 수 없다면 움직일 수 없다.")
    void canNotMoveWithInvalidMove() {
        //given
        King king = new King(Side.WHITE);

        //when
        boolean result = king.canMove(B8, A4, chessBoard);

        //then
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"BLACK", "WHITE"})
    @DisplayName("왕인지 판단한다.")
    void isKing(Side side) {
        //given
        final King king = new King(side);

        //when
        final boolean result = king.isKing();

        //then
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {"BLACK", "WHITE"})
    @DisplayName("폰인지 판단한다.")
    void isPawn(Side side) {
        //given
        final King king = new King(side);

        //when
        final boolean result = king.isPawn();

        //then
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"BLACK", "WHITE"})
    @DisplayName("점수를 반환한다.")
    void getPoint(Side side) {
        //given
        final King king = new King(side);

        //when
        final Point result = king.getPoint();

        //then
        assertThat(result).isEqualTo(Point.from(0));
    }
}

package chess.model.piece;

import static chess.model.Fixture.A7;
import static chess.model.Fixture.B6;
import static chess.model.Fixture.B8;
import static chess.model.Fixture.C5;
import static chess.model.Fixture.C6;
import static chess.model.Fixture.C7;
import static chess.model.Fixture.C8;
import static chess.model.Fixture.D4;
import static chess.model.Fixture.D6;
import static chess.model.Fixture.D7;
import static chess.model.Fixture.E1;
import static chess.model.Fixture.E3;
import static chess.model.Fixture.E6;
import static chess.model.Fixture.F1;
import static chess.model.Fixture.F2;
import static chess.model.Fixture.F3;
import static chess.model.Fixture.F4;
import static chess.model.Fixture.F5;
import static chess.model.Fixture.F8;
import static chess.model.Fixture.G2;
import static chess.model.Fixture.G4;
import static chess.model.Fixture.H3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.model.board.ChessBoard;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BishopTest {
    private ChessBoard chessBoard;

    /*
        .KR..... 8
        P.PB.... 7
        .Pn.Q... 6
        ..B..... 5
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
                Map.entry(C5, new Bishop(Side.BLACK)),
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
    @DisplayName("비숍이 이동할 수 있는지 판단한다.")
    void canMove() {
        //given
        Bishop bishop = new Bishop(Side.BLACK);

        //when //then
        assertAll(
                () -> assertThat(bishop.canMove(C5, D6, chessBoard)).isTrue(),
                () -> assertThat(bishop.canMove(C5, F8, chessBoard)).isTrue(),
                () -> assertThat(bishop.canMove(C5, D4, chessBoard)).isTrue(),
                () -> assertThat(bishop.canMove(C5, E3, chessBoard)).isTrue()
        );
    }

    @Test
    @DisplayName("비숍이 갈 수 있는 위치에 적 기물이 있으면 이동할 수 있다.")
    void canMoveWhenEnemy() {
        //given
        Bishop bishop = new Bishop(Side.BLACK);

        //when
        boolean result = bishop.canMove(D7, C6, chessBoard);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("아군 기물이 있다면 이동할 수 없다.")
    void canNotMove() {
        //given
        Bishop bishop = new Bishop(Side.BLACK);

        //when
        boolean result = bishop.canMove(D7, F5, chessBoard);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("비숍 움직임으로 갈 수 없다면 움직일 수 없다.")
    void canNotMoveInvalidPosition() {
        //given
        Bishop bishop = new Bishop(Side.BLACK);

        //when //then
        assertThat(bishop.canMove(D7, D6, chessBoard)).isFalse();
    }


    @ParameterizedTest
    @CsvSource(value = {"BLACK", "WHITE"})
    @DisplayName("왕인지 판단한다.")
    void isKing(Side side) {
        //given
        final Bishop bishop = new Bishop(side);

        //when
        final boolean result = bishop.isKing();

        //then
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"BLACK", "WHITE"})
    @DisplayName("폰인지 판단한다.")
    void isPawn(Side side) {
        //given
        final Bishop bishop = new Bishop(side);

        //when
        final boolean result = bishop.isPawn();

        //then
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"BLACK", "WHITE"})
    @DisplayName("점수를 반환한다.")
    void getPoint(Side side) {
        //given
        final Bishop bishop = new Bishop(side);

        //when
        final double result = bishop.getPoint();

        //then
        assertThat(result).isEqualTo(3);
    }
}

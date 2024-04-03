package chess.model.piece;

import static chess.model.Fixture.A1;
import static chess.model.Fixture.A7;
import static chess.model.Fixture.B6;
import static chess.model.Fixture.B8;
import static chess.model.Fixture.C7;
import static chess.model.Fixture.C8;
import static chess.model.Fixture.D7;
import static chess.model.Fixture.E1;
import static chess.model.Fixture.E6;
import static chess.model.Fixture.E7;
import static chess.model.Fixture.F1;
import static chess.model.Fixture.F2;
import static chess.model.Fixture.F3;
import static chess.model.Fixture.F4;
import static chess.model.Fixture.F7;
import static chess.model.Fixture.G2;
import static chess.model.Fixture.G4;
import static chess.model.Fixture.G5;
import static chess.model.Fixture.G6;
import static chess.model.Fixture.G7;
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

class RookTest {
    private ChessBoard chessBoard;

    /*
        룩의 위치 (G7)
        .KR..... 8
        P.PB..X. 7
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
                Map.entry(G7, new Rook(Side.BLACK)),
                Map.entry(B6, new BlackPawn()),
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
    @DisplayName("룩이 이동할 수 있는지 판단한다.")
    void canMove() {
        //given
        final Rook rook = new Rook(Side.BLACK);

        //when //then
        assertAll(
                () -> assertThat(rook.canMove(G7, F7, chessBoard)).isTrue(),
                () -> assertThat(rook.canMove(G7, E7, chessBoard)).isTrue(),
                () -> assertThat(rook.canMove(G7, G6, chessBoard)).isTrue(),
                () -> assertThat(rook.canMove(G7, G5, chessBoard)).isTrue(),
                () -> assertThat(rook.canMove(G7, G8, chessBoard)).isTrue()
        );
    }

    @Test
    @DisplayName("룩이 갈 수 있는 위치에 적 기물이 있으면 이동할 수 있다.")
    void canMoveWhenEnemy() {
        //given
        final Rook rook = new Rook(Side.BLACK);

        //when
        final boolean result = rook.canMove(G7, G4, chessBoard);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("아군 기물이 있다면 이동할 수 없다.")
    void canNotMove() {
        //given
        final Rook rook = new Rook(Side.BLACK);
        final ChessBoard chessBoardRookWithSameSide = chessBoard;

        //when
        final boolean result = rook.canMove(G7, D7, chessBoardRookWithSameSide);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("룩의 움직임으로 갈 수 없다면 움직일 수 없다.")
    void canNotMoveWithInvalidMove() {
        //given
        final Rook rook = new Rook(Side.BLACK);

        //when
        final boolean result = rook.canMove(E6, A1, chessBoard);

        //then
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"BLACK", "WHITE"})
    @DisplayName("왕인지 판단한다.")
    void isKing(final Side side) {
        //given
        final Rook rook = new Rook(side);

        //when
        final boolean result = rook.isKing();

        //then
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"BLACK", "WHITE"})
    @DisplayName("폰인지 판단한다.")
    void isPawn(final Side side) {
        //given
        final Rook rook = new Rook(side);

        //when
        final boolean result = rook.isPawn();

        //then
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"BLACK", "WHITE"})
    @DisplayName("점수를 반환한다.")
    void getPoint(final Side side) {
        //given
        final Rook rook = new Rook(side);

        //when
        final Point result = rook.getPoint();

        //then
        assertThat(result).isEqualTo(Point.from(5));
    }
}

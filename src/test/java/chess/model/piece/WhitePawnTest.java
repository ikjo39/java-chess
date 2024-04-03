package chess.model.piece;

import static chess.model.Fixture.A3;
import static chess.model.Fixture.A7;
import static chess.model.Fixture.A8;
import static chess.model.Fixture.B2;
import static chess.model.Fixture.B3;
import static chess.model.Fixture.B4;
import static chess.model.Fixture.B5;
import static chess.model.Fixture.B6;
import static chess.model.Fixture.B8;
import static chess.model.Fixture.C7;
import static chess.model.Fixture.C8;
import static chess.model.Fixture.D7;
import static chess.model.Fixture.E1;
import static chess.model.Fixture.E4;
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

class WhitePawnTest {

    private ChessBoard chessBoard;

    /*
        .KR..... 8
        P.PB.... 7
        .P..Q... 6
        ........ 5
        ....Pnq. 4
        .....p.p 3
        .p...pp. 2
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
                Map.entry(E4, new BlackPawn()),
                Map.entry(F4, new Knight(Side.WHITE)),
                Map.entry(G4, new Queen(Side.WHITE)),
                Map.entry(F3, new WhitePawn()),
                Map.entry(H3, new WhitePawn()),
                Map.entry(B2, new WhitePawn()),
                Map.entry(F2, new WhitePawn()),
                Map.entry(G2, new WhitePawn()),
                Map.entry(E1, new Rook(Side.WHITE)),
                Map.entry(F1, new King(Side.WHITE)))
        );
    }

    @Test
    @DisplayName("초기 위치라면 폰은 한칸 또는 두칸 앞으로 움직일 수 있다.")
    void canMove() {
        //given
        final Pawn whitePawn = new WhitePawn();

        //when //then
        assertAll(
                () -> assertThat(whitePawn.canMove(B2, B3, chessBoard)).isTrue(),
                () -> assertThat(whitePawn.canMove(B2, B4, chessBoard)).isTrue(),
                () -> assertThat(whitePawn.canMove(B2, B5, chessBoard)).isFalse()
        );
    }

    @Test
    @DisplayName("초기 위치가 아니라면 폰은 한칸만 움직일 수 있다.")
    void canMoveNotInitial() {
        //given
        final Pawn whitePawn = new WhitePawn();

        //when //then
        assertAll(
                () -> assertThat(whitePawn.canMove(A7, A8, chessBoard)).isTrue(),
                () -> assertThat(whitePawn.canMove(A7, A3, chessBoard)).isFalse()
        );
    }

    @Test
    @DisplayName("적 기물이 대각선 앞에 있다면 이동할 수 있다.")
    void canMoveDiagonal() {
        //given
        final WhitePawn whitePawn = new WhitePawn();

        //when
        final boolean result = whitePawn.canMove(F3, E4, chessBoard);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("아군 기물이 있다면 이동할 수 없다.")
    void canNotMove() {
        //given
        final WhitePawn whitePawn = new WhitePawn();

        //when
        final boolean result = whitePawn.canMove(F3, F2, chessBoard);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("폰의 움직임으로 갈 수 없다면 이동할 수 없다.")
    void canNotMoveInvalidDirection() {
        //given
        final WhitePawn whitePawn = new WhitePawn();

        //when
        final boolean result = whitePawn.canMove(F3, B5, chessBoard);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("왕인지 판단한다.")
    void isKing() {
        //given
        final Pawn pawn = new WhitePawn();

        //when
        final boolean result = pawn.isKing();

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("폰인지 판단한다.")
    void isPawn() {
        //given
        final Pawn pawn = new WhitePawn();

        //when
        final boolean result = pawn.isPawn();

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("점수를 반환한다.")
    void getPoint() {
        //given
        final Pawn pawn = new WhitePawn();

        //when
        final Point result = pawn.getPoint();

        //then
        assertThat(result).isEqualTo(Point.from(1));
    }
}

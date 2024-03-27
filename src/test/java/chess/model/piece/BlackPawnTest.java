package chess.model.piece;

import static chess.model.Fixture.B5;
import static chess.model.Fixture.B6;
import static chess.model.Fixture.B7;
import static chess.model.Fixture.C2;
import static chess.model.Fixture.C3;
import static chess.model.Fixture.C4;
import static chess.model.Fixture.D4;
import static chess.model.Fixture.H3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.model.position.ChessPosition;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BlackPawnTest {
    @ParameterizedTest
    @MethodSource("getPathsWhenPawnInB2")
    @DisplayName("초기 위치에 있는 Pawn이 타켓 위치까지 움직이는 경로를 찾는다.")
    void findPathInitialPosition(ChessPosition target, List<ChessPosition> expected) {
        // given
        Piece pawn = new BlackPawn();

        // when
        List<ChessPosition> path = pawn.findPath(B7, target, new Empty());

        // then
        assertThat(path).isEqualTo(expected);
    }

    @Test
    @DisplayName("초기 위치가 아닌 Pawn이 타켓 위치까지 움직이는 경로를 찾는다.")
    void findPath() {
        // given
        Piece pawn = new BlackPawn();

        // when
        List<ChessPosition> path = pawn.findPath(C4, C3, new Empty());

        // then
        assertThat(path).isEqualTo(List.of(C3));
    }

    @Test
    @DisplayName("Pawn의 대각선에 적 기물이 있다면 움직이는 경로를 찾는다.")
    void findPathCatchEnemy() {
        // given
        Piece pawn = new BlackPawn();
        Piece targetPiece = new WhitePawn();

        // when
        List<ChessPosition> path = pawn.findPath(D4, C3, targetPiece);

        // then
        assertThat(path).isEqualTo(List.of(C3));
    }

    @Test
    @DisplayName("타겟 위치에 아군 기물이 존재하면 예외가 발생한다.")
    void findPathWhenInvalidTarget() {
        // given
        Piece pawn = new BlackPawn();
        Piece targetPiece = new Rook(Side.WHITE);

        //when //then
        assertThatThrownBy(() -> pawn.findPath(C3, C2, targetPiece))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("타겟 위치에 적 기물이 존재하면 예외가 발생한다.")
    void findPathWhenInvalidEnemyTarget() {
        // given
        Piece pawn = new BlackPawn();
        Piece targetPiece = new Rook(Side.BLACK);

        //when //then
        assertThatThrownBy(() -> pawn.findPath(C3, C2, targetPiece))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Pawn 움직임으로 타겟 위치에 도달할 수 없다면 예외가 발생한다.")
    void findPathWhenCanNotReachTargetPiece() {
        // given
        Piece pawn = new BlackPawn();

        // when // then
        assertThatThrownBy(() -> pawn.findPath(C2, H3, new Empty()))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("왕인지 판단한다.")
    void isKing() {
        //given
        final Pawn pawn = new BlackPawn();

        //when
        final boolean result = pawn.isKing();

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("폰인지 판단한다.")
    void isPawn() {
        //given
        final Pawn pawn = new BlackPawn();

        //when
        final boolean result = pawn.isPawn();

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("점수를 반환한다.")
    void getPoint() {
        //given
        final Pawn pawn = new BlackPawn();

        //when
        final double result = pawn.getPoint();

        //then
        assertThat(result).isEqualTo(1);
    }

    private static Stream<Arguments> getPathsWhenPawnInB2() {
        return Stream.of(
                Arguments.arguments(B6, List.of(B6)),
                Arguments.arguments(B5, List.of(B6, B5))
        );
    }
}

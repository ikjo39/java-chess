package chess.model.piece;

import static chess.model.Fixture.B1;
import static chess.model.Fixture.B2;
import static chess.model.Fixture.B3;
import static chess.model.Fixture.C1;
import static chess.model.Fixture.C2;
import static chess.model.Fixture.C3;
import static chess.model.Fixture.D1;
import static chess.model.Fixture.D2;
import static chess.model.Fixture.D3;
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
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class KingTest {
    @ParameterizedTest
    @MethodSource("getPathsWhenKingInC2")
    @DisplayName("King이 타켓 위치까지 움직이는 경로를 찾는다.")
    void findPath(ChessPosition target, List<ChessPosition> expected) {
        // given
        King king = new King(Side.WHITE);

        // when
        List<ChessPosition> path = king.findPath(C2, target, new Empty());

        // then
        assertThat(path).isEqualTo(expected);
    }

    @Test
    @DisplayName("타겟 위치에 아군 기물이 존재하면 예외가 발생한다.")
    void findPathWhenInvalidTarget() {
        // given
        King king = new King(Side.WHITE);
        Pawn targetPiece = new Pawn(Side.WHITE);

        // when // then
        assertThatThrownBy(() -> king.findPath(C2, D3, targetPiece))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("King 움직임으로 타겟 위치에 도달할 수 없다면 빈 리스트를 반환한다.")
    void findPathWhenCanNotReachTargetPiece() {
        // given
        King king = new King(Side.BLACK);

        // when // then
        assertThatThrownBy(() -> king.findPath(C2, H3, new Empty()))
                .isInstanceOf(IllegalStateException.class);
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

    private static Stream<Arguments> getPathsWhenKingInC2() {
        return Stream.of(
                Arguments.of(B1, List.of(B1)),
                Arguments.of(C1, List.of(C1)),
                Arguments.of(D1, List.of(D1)),
                Arguments.of(B2, List.of(B2)),
                Arguments.of(D2, List.of(D2)),
                Arguments.of(B3, List.of(B3)),
                Arguments.of(C3, List.of(C3)),
                Arguments.of(D3, List.of(D3))
        );
    }
}

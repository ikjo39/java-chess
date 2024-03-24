package chess.model.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.model.position.ChessPosition;
import chess.model.position.File;
import chess.model.position.Rank;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RookTest {
    @ParameterizedTest
    @MethodSource("provideTargetPositionAndResult")
    @DisplayName("Rook이 타켓 위치까지 움직이는 경로를 찾는다.")
    void findPath(ChessPosition target, List<ChessPosition> expected) {
        // given
        ChessPosition source = new ChessPosition(File.C, Rank.TWO);
        Rook rook = new Rook(Side.WHITE);

        // when
        List<ChessPosition> path = rook.findPath(source, target, new Empty());

        // then
        assertThat(path).isEqualTo(expected);
    }

    @Test
    @DisplayName("타겟 위치에 아군 기물이 존재하면 예외가 발생한다.")
    void findPathWhenInvalidTarget() {
        // given
        ChessPosition source = new ChessPosition(File.C, Rank.TWO);
        ChessPosition target = new ChessPosition(File.D, Rank.THREE);
        Rook rook = new Rook(Side.WHITE);
        Pawn targetPiece = new Pawn(Side.WHITE);

        // when // then
        assertThatThrownBy(() -> rook.findPath(source, target, targetPiece))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Rook 움직임으로 타겟 위치에 도달할 수 없다면 빈 리스트를 반환한다.")
    void findPathWhenCanNotReachTargetPiece() {
        // given
        ChessPosition source = new ChessPosition(File.C, Rank.TWO);
        ChessPosition target = new ChessPosition(File.H, Rank.THREE);
        Rook rook = new Rook(Side.BLACK);

        // when
        List<ChessPosition> path = rook.findPath(source, target, new Empty());

        // then
        assertThat(path).isEmpty();
    }

    private static Stream<Arguments> provideTargetPositionAndResult() {
        return Stream.of(
                Arguments.of(
                        new ChessPosition(File.C, Rank.THREE),
                        List.of(
                                new ChessPosition(File.C, Rank.THREE)
                        )
                ),
                Arguments.of(
                        new ChessPosition(File.C, Rank.ONE),
                        List.of(
                                new ChessPosition(File.C, Rank.ONE)
                        )
                ),
                Arguments.of(
                        new ChessPosition(File.B, Rank.TWO),
                        List.of(
                                new ChessPosition(File.B, Rank.TWO)
                        )
                ),
                Arguments.of(
                        new ChessPosition(File.D, Rank.TWO),
                        List.of(
                                new ChessPosition(File.D, Rank.TWO)
                        )
                ),
                Arguments.of(
                        new ChessPosition(File.A, Rank.TWO),
                        List.of(
                                new ChessPosition(File.B, Rank.TWO),
                                new ChessPosition(File.A, Rank.TWO)
                        )
                ),
                Arguments.of(
                        new ChessPosition(File.C, Rank.EIGHT),
                        List.of(
                                new ChessPosition(File.C, Rank.THREE),
                                new ChessPosition(File.C, Rank.FOUR),
                                new ChessPosition(File.C, Rank.FIVE),
                                new ChessPosition(File.C, Rank.SIX),
                                new ChessPosition(File.C, Rank.SEVEN),
                                new ChessPosition(File.C, Rank.EIGHT)
                        )
                )
        );
    }
}

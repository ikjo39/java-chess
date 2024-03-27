package chess.model.position;

import static chess.model.Fixture.A1;
import static chess.model.Fixture.B1;
import static chess.model.Fixture.B2;
import static chess.model.Fixture.B6;
import static chess.model.Fixture.C1;
import static chess.model.Fixture.C2;
import static chess.model.Fixture.C4;
import static chess.model.Fixture.C5;
import static chess.model.Fixture.C6;
import static chess.model.Fixture.C7;
import static chess.model.Fixture.C8;
import static chess.model.Fixture.E6;
import static chess.model.Fixture.H4;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChessPositionTest {
    @ParameterizedTest
    @MethodSource("getPositionAndDiffToCompare")
    @DisplayName("다른 위치와 거리를 계산한다.")
    void calculateDistance(ChessPosition now, ChessPosition other, int fileDiff, int rankDiff) {
        // when
        Distance distance = now.calculateDistance(other);

        // then
        assertAll(
                () -> assertThat(distance.getFileDifference()).isEqualTo(fileDiff),
                () -> assertThat(distance.getRankDifference()).isEqualTo(rankDiff)
        );
    }

    @Test
    @DisplayName("두 위치가 같은 File에 존재하는지 판단한다.")
    void hasSameFile() {
        //given
        final ChessPosition source = C2;

        //when //then
        assertAll(
                () -> assertThat(source.hasSameFile(C1)).isTrue(),
                () -> assertThat(source.hasSameFile(C2)).isTrue(),
                () -> assertThat(source.hasSameFile(C4)).isTrue(),
                () -> assertThat(source.hasSameFile(C5)).isTrue(),
                () -> assertThat(source.hasSameFile(C6)).isTrue(),
                () -> assertThat(source.hasSameFile(C7)).isTrue(),
                () -> assertThat(source.hasSameFile(C8)).isTrue(),
                () -> assertThat(source.hasSameFile(A1)).isFalse(),
                () -> assertThat(source.hasSameFile(B2)).isFalse()
        );
    }

    private static Stream<Arguments> getPositionAndDiffToCompare() {
        return Stream.of(
                Arguments.of(C6, A1, 2, 5),
                Arguments.of(E6, H4, -3, 2),
                Arguments.of(A1, B2, -1, -1),
                Arguments.of(B6, B1, 0, 5)
        );
    }
}

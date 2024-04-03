package chess.model.board;

import static org.assertj.core.api.Assertions.assertThat;

import chess.model.piece.Side;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PointsTest {

    @ParameterizedTest
    @CsvSource(value = {"10,30,BLACK", "30,10,WHITE"})
    @DisplayName("우승 진영을 반환한다.")
    void calculateWinner(final int white, final int black, final Side expected) {
        //given
        final Map<Side, Point> sidePoints = Map.of(
                Side.WHITE, Point.from(white),
                Side.BLACK, Point.from(black)
        );

        //when
        final Points points = new Points(sidePoints);
        final List<Side> sides = points.calculateWinner();

        //then
        assertThat(sides).containsExactly(expected);
    }

    @Test
    @DisplayName("우승 진영을 반환하되 동점이면 두 팀 모두 반환한다.")
    void calculateWinnerTie() {
        //given
        final Map<Side, Point> sidePoints = Map.of(
                Side.BLACK, Point.from(10),
                Side.WHITE, Point.from(10)
        );

        //when
        final Points points = new Points(sidePoints);
        final List<Side> sides = points.calculateWinner();

        //then
        assertThat(sides).containsExactlyInAnyOrder(Side.BLACK, Side.WHITE);
    }
}

package chess.model.position;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DirectionTest {

    @ParameterizedTest
    @CsvSource(value = {
            "UP,false", "DOWN,false", "LEFT,false",
            "UP_LEFT,true", "UP_RIGHT,true", "DOWN_LEFT,true", "DOWN_RIGHT,true"})
    @DisplayName("대각선 움직임인지 판단한다.")
    void isDiagonal(Direction given, boolean expected) {
        //when
        boolean result = given.isDiagonal();

        //then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "UP,true", "DOWN,true",
            "UP_LEFT,false", "UP_RIGHT,false", "DOWN_RIGHT,false"})
    @DisplayName("수직 움직임인지 판단한다.")
    void isVertical(Direction given, boolean expected) {
        //when
        boolean result = given.isVertical();

        //then
        assertThat(result).isEqualTo(expected);
    }
}

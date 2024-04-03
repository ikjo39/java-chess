package chess.model.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SideTest {
    @ParameterizedTest
    @CsvSource(value = {"EMPTY,true", "WHITE,false", "BLACK,false"})
    @DisplayName("흰색 진영인지 판단한다.")
    void isEmpty(final Side given, final boolean expected) {
        //when
        final boolean result = given.isEmpty();

        //then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "WHITE,WHITE,false", "WHITE,BLACK,true", "WHITE,EMPTY,false",
            "BLACK,WHITE,true", "BLACK,BLACK,false", "BLACK,EMPTY,false",
            "EMPTY,WHITE,false", "EMPTY,BLACK,false", "EMPTY,EMPTY,false",
    })
    @DisplayName("적 기물인지 판단한다.")
    void isEnemy(final Side now,
                 final Side other,
                 final boolean expected) {
        //when
        final boolean result = now.isEnemy(other);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"WHITE,BLACK", "BLACK,WHITE"})
    @DisplayName("적 진영을 반환한다.")
    void getEnemy(final Side given, final Side expected) {
        //when
        final Side result = given.getEnemy();

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("빈 진영의 적을 반환하려하면 예외가 발생한다.")
    void getEnemy() {
        //given
        final Side given = Side.EMPTY;

        //when         //then
        assertThatThrownBy(given::getEnemy)
                .isInstanceOf(IllegalStateException.class);
    }
}

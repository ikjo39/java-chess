package chess.model.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PieceTest {
    @ParameterizedTest
    @CsvSource(value = {
            "WHITE,BLACK,false", "BLACK,WHITE,false",
            "WHITE,WHITE,true", "BLACK,BLACK,true",
            "WHITE,EMPTY,false", "BLACK,EMPTY,false",
    })
    @DisplayName("같은 진영인지 판단한다.")
    void isSameSide(Side sourceSide, Side targetSide, boolean expected) {
        //given
        final Piece source = new Bishop(sourceSide);
        //when
        final boolean result = source.isSameSide(targetSide);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"WHITE,BLACK", "BLACK,WHITE",})
    @DisplayName("같은 진영인지 판단한다.")
    void getEnemy(Side given, Side expected) {
        //given
        final Piece source = new Bishop(given);

        //when
        Side result = source.getEnemy();

        //then
        assertThat(result).isEqualTo(expected);
    }
}

package chess.model.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PieceTest {

    @ParameterizedTest
    @CsvSource(value = {
            "WHITE,BLACK,EMPTY", "BLACK,WHITE,EMPTY",
            "WHITE,WHITE,WHITE", "BLACK,BLACK,BLACK",
            "WHITE,EMPTY,EMPTY", "BLACK,EMPTY,EMPTY",
    })
    @DisplayName("적 기물을 잡으면 적 기물은 빈 기물이 된다.")
    void catchTargetPiece(Side sourceSide, Side targetSide, Side expected) {
        //given
        Piece source = new Bishop(sourceSide);
        Piece target = new Rook(targetSide);

        //when
        Piece result = source.catchTargetPiece(target);

        //then
        assertThat(result.side).isEqualTo(expected);
    }

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
}

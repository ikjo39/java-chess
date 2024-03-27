package chess.model.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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


    @Test
    @DisplayName("기물이 King인지 판단한다.")
    void isKing() {
        // given
        final Piece given = new King(Side.WHITE);

        // when
        final boolean result = given.isKing();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("기물이 King인지 판단한다.")
    void isNotKing() {
        // given
        final Piece given = new Pawn(Side.WHITE);

        // when
        final boolean result = given.isKing();

        // then
        assertThat(result).isFalse();
    }
}

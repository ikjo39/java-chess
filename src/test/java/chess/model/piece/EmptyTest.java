package chess.model.piece;

import static chess.model.Fixture.F2;
import static chess.model.Fixture.F4;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmptyTest {
    @Test
    @DisplayName("경로 찾기를 시도하면 빈 배열을 반환한다.")
    void findPath() {
        //given
        Piece sourcePiece = new Empty();
        Piece targetPiece = new Empty();

        //when //then
        assertThatThrownBy(() -> sourcePiece.findPath(F2, F4, targetPiece))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("왕인지 판단한다.")
    void isKing() {
        //given
        final Empty empty = new Empty();

        //when
        final boolean result = empty.isKing();

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("점수를 반환한다.")
    void getPoint() {
        //given
        final Empty empty = new Empty();

        //when
        final double result = empty.getPoint();

        //then
        assertThat(result).isEqualTo(0);
    }
}

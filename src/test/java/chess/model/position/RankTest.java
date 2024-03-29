package chess.model.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class RankTest {
    @ParameterizedTest
    @ValueSource(strings = {"-50", "-2", "-1", "0", "9", "10", "11", "244"})
    @DisplayName("유효하지 않는 좌표로 Rank를 생성하면 예외가 발생한다.")
    void from(String given) {
        //when //then
        assertThatThrownBy(() -> Rank.from(given))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"FOUR,2,TWO", "FOUR,3,ONE", "THREE,-5,EIGHT"})
    @DisplayName("이동할 칸 수 만큼 증가한 랭크를 반환한다.")
    void findNextRank(Rank given, int offset, Rank expected) {
        //when
        Rank result = given.findNextRank(offset);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"TWO,-7", "ONE,20", "EIGHT,-1"})
    @DisplayName("이동할 수 없다면 예외가 발생한다.")
    void findNextRankExceedRange(Rank given, int offset) {
        //when //then
        assertThatThrownBy(() -> given.findNextRank(offset))
                .isInstanceOf(IllegalStateException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"-4,false", "-3,false", "-2,true", "-1,true", "4,true", "5,true", "6,false"})
    @DisplayName("해당 거리만큼 움직일 수 있는지 판단한다.")
    void canMove_C(int given, boolean expected) {
        //given
        File file = File.C;

        //when
        boolean result = file.canMove(given);

        //then
        assertThat(result).isEqualTo(expected);
    }
}

package chess.model.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class FileTest {
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "1", "12", "ab", "z"})
    @DisplayName("유효하지 않는 좌표로 File를 생성하면 예외가 발생한다.")
    void from(String given) {
        //when //then
        assertThatThrownBy(() -> File.from(given))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"B,2,D", "A,3,D", "H,-5,C"})
    @DisplayName("이동할 칸 수 만큼 증가한 랭크를 반환한다.")
    void findNextFile(File given, int offset, File expected) {
        //when
        File result = given.findNextFile(offset);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"B,-6", "A,20", "H,1"})
    @DisplayName("이동할 수 없다면 예외가 발생한다.")
    void findNextFileExceedRange(File given, int offset) {
        //when //then
        assertThatThrownBy(() -> given.findNextFile(offset))
                .isInstanceOf(IllegalStateException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"-6,false", "-5,true", "-4,true", "1,true", "2,true", "3,false", "4,false"})
    @DisplayName("해당 거리만큼 움직일 수 있는지 판단한다.")
    void canMove_THREE(int given, boolean expected) {
        //given
        Rank rank = Rank.THREE;

        //when
        boolean result = rank.canMove(given);

        //then
        assertThat(result).isEqualTo(expected);
    }
}

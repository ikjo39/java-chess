package chess.model.position;

import static chess.model.Fixture.A1;
import static chess.model.Fixture.A8;
import static chess.model.Fixture.B2;
import static chess.model.Fixture.C1;
import static chess.model.Fixture.C2;
import static chess.model.Fixture.C4;
import static chess.model.Fixture.C5;
import static chess.model.Fixture.C6;
import static chess.model.Fixture.C7;
import static chess.model.Fixture.C8;
import static chess.model.Fixture.D3;
import static chess.model.Fixture.D4;
import static chess.model.Fixture.D6;
import static chess.model.Fixture.E3;
import static chess.model.Fixture.E4;
import static chess.model.Fixture.E5;
import static chess.model.Fixture.E6;
import static chess.model.Fixture.E7;
import static chess.model.Fixture.F3;
import static chess.model.Fixture.F4;
import static chess.model.Fixture.F5;
import static chess.model.Fixture.F6;
import static chess.model.Fixture.F7;
import static chess.model.Fixture.G3;
import static chess.model.Fixture.G4;
import static chess.model.Fixture.G5;
import static chess.model.Fixture.G6;
import static chess.model.Fixture.G7;
import static chess.model.Fixture.H4;
import static chess.model.Fixture.H6;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChessPositionTest {
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

    @ParameterizedTest
    @CsvSource(value = {"UP,true", "LEFT,true", "DOWN,false", "DOWN_LEFT,false"})
    @DisplayName("방향으로 지정한 위치를 갈 수 있는지 판단한다.")
    void canMove_C1(final Direction direction, final boolean expected) {
        // given
        final ChessPosition now = C1;

        // when
        final boolean result = now.canMove(direction);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("방향으로 이동한다.")
    void move_F5() {
        // given
        final ChessPosition now = F5;

        // when //then
        assertAll(
                () -> assertThat(now.move(Direction.UP)).isEqualTo(F6),
                () -> assertThat(now.move(Direction.DOWN)).isEqualTo(F4),
                () -> assertThat(now.move(Direction.LEFT)).isEqualTo(E5),
                () -> assertThat(now.move(Direction.RIGHT)).isEqualTo(G5),
                () -> assertThat(now.move(Direction.UP_UP)).isEqualTo(F7),
                () -> assertThat(now.move(Direction.DOWN_DOWN)).isEqualTo(F3),
                () -> assertThat(now.move(Direction.UP_LEFT)).isEqualTo(E6),
                () -> assertThat(now.move(Direction.UP_RIGHT)).isEqualTo(G6),
                () -> assertThat(now.move(Direction.DOWN_LEFT)).isEqualTo(E4),
                () -> assertThat(now.move(Direction.DOWN_RIGHT)).isEqualTo(G4),
                () -> assertThat(now.move(Direction.UP_UP_LEFT)).isEqualTo(E7),
                () -> assertThat(now.move(Direction.UP_UP_RIGHT)).isEqualTo(G7),
                () -> assertThat(now.move(Direction.UP_LEFT_LEFT)).isEqualTo(D6),
                () -> assertThat(now.move(Direction.UP_RIGHT_RIGHT)).isEqualTo(H6),
                () -> assertThat(now.move(Direction.DOWN_DOWN_LEFT)).isEqualTo(E3),
                () -> assertThat(now.move(Direction.DOWN_DOWN_RIGHT)).isEqualTo(G3),
                () -> assertThat(now.move(Direction.DOWN_LEFT_LEFT)).isEqualTo(D4),
                () -> assertThat(now.move(Direction.DOWN_RIGHT_RIGHT)).isEqualTo(H4)
        );
    }

    @Test
    @DisplayName("방향으로 갈 수 없으면 예외가 발생한다.")
    void move_C1_Exception() {
        // given
        final Direction direction = Direction.DOWN;

        // when // then
        assertThatThrownBy(() -> C1.move(direction))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"-7,false", "-6,true", "0,true", "1,true", "2,false", "3,false"})
    @DisplayName("수직 방향으로 움직일 수 있는지 판단한다.")
    void canMoveVertical_C2(final int given, final boolean expected) {
        //when
        final boolean result = C2.canMoveVertical(given);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("수직 방향으로 이동한다.")
    void moveVertical_C1() {
        // given
        final int step = -1;

        // when
        final ChessPosition result = C1.moveVertical(step);

        // then
        assertThat(result).isEqualTo(C2);
    }

    @Test
    @DisplayName("수직 방향으로 갈 수 없으면 예외가 발생한다.")
    void moveVertical_C1_Exception() {
        // given
        final int step = 1;

        // when // then
        assertThatThrownBy(() -> C1.moveVertical(step))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이름을 반환한다.")
    void getName() {
        //when //then
        assertAll(
                () -> assertThat(C1.getName()).isEqualTo("c1"),
                () -> assertThat(A8.getName()).isEqualTo("a8"),
                () -> assertThat(D3.getName()).isEqualTo("d3"),
                () -> assertThat(H4.getName()).isEqualTo("h4")
        );
    }
}

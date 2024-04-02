package chess.model.piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class PieceTextTest {

    @ParameterizedTest
    @CsvSource(value = {"k,WHITE_KING", ".,EMPTY", "Q,BLACK_QUEEN"})
    @DisplayName("기물 표기로 기물 텍스트를 생성한다.")
    void fromName(final String given, final PieceText expected) {
        //when
        final PieceText result = PieceText.from(given);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("createPieceAndPieceText")
    @DisplayName("기물로 기물텍스트를 생성한다.")
    void fromPiece(final Piece given, PieceText expected) {
        //when
        final PieceText result = PieceText.from(given);

        //then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> createPieceAndPieceText() {
        return Stream.of(
                Arguments.of(new WhitePawn(), PieceText.WHITE_PAWN),
                Arguments.of(new Rook(Side.WHITE), PieceText.WHITE_ROOK),
                Arguments.of(new Knight(Side.WHITE), PieceText.WHITE_KNIGHT),
                Arguments.of(new Bishop(Side.WHITE), PieceText.WHITE_BISHOP),
                Arguments.of(new Queen(Side.WHITE), PieceText.WHITE_QUEEN),
                Arguments.of(new King(Side.WHITE), PieceText.WHITE_KING),
                Arguments.of(new BlackPawn(), PieceText.BLACK_PAWN),
                Arguments.of(new Rook(Side.BLACK), PieceText.BLACK_ROOK),
                Arguments.of(new Knight(Side.BLACK), PieceText.BLACK_KNIGHT),
                Arguments.of(new Bishop(Side.BLACK), PieceText.BLACK_BISHOP),
                Arguments.of(new Queen(Side.BLACK), PieceText.BLACK_QUEEN),
                Arguments.of(new King(Side.BLACK), PieceText.BLACK_KING),
                Arguments.of(new Empty(), PieceText.EMPTY)
        );
    }
}

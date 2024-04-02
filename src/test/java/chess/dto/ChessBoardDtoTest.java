package chess.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.model.board.ChessBoard;
import chess.model.piece.Side;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessBoardDtoTest {

    @Test
    @DisplayName("체스 보드로 변환한다.")
    void convert() {
        //given
        final Set<PieceDto> pieces = Set.of(
                PieceDto.from("a1", "k"),
                PieceDto.from("d3", "R"),
                PieceDto.from("d7", "p"),
                PieceDto.from("a7", "B"),
                PieceDto.from("h8", "K")
        );
        final ChessBoardDto chessBoardDto = ChessBoardDto.from(pieces, "BLACK");

        //when
        final ChessBoard result = chessBoardDto.convert();

        //then
        assertAll(
                () -> assertThat(result.getBoard()).hasSize(pieces.size()),
                () -> assertThat(result.getTurn()).isEqualTo(Side.BLACK)
        );
    }
}

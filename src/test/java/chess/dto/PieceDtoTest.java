package chess.dto;

import static chess.model.Fixture.A1;
import static org.assertj.core.api.Assertions.assertThat;

import chess.model.piece.King;
import chess.model.piece.Piece;
import chess.model.position.ChessPosition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDtoTest {

    @Test
    @DisplayName("체스 위치로 변환한다.")
    void convertPosition() {
        //given
        final PieceDto pieceDto = PieceDto.from("a1", "k");

        //when
        final ChessPosition result = pieceDto.convertPosition();

        //then
        assertThat(result).isEqualTo(A1);
    }

    @Test
    @DisplayName("체스 기물로 변환한다.")
    void convertPiece() {
        //given
        final PieceDto pieceDto = PieceDto.from("a1", "k");

        //when
        final Piece result = pieceDto.convertPiece();

        //then
        assertThat(result).isInstanceOf(King.class);
    }
}
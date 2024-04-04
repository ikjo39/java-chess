package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;

import chess.dto.ChessBoardDto;
import chess.dto.PieceDto;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardDaoImplTest {

    private BoardDao boardDao;

    @BeforeEach
    void setUp() {
        boardDao = new TestBoardDao();
        final Set<PieceDto> pieces = Set.of(
                PieceDto.from("a8", "R"),
                PieceDto.from("b7", "N"),
                PieceDto.from("d5", "q")
        );
        boardDao.addAll(ChessBoardDto.from(pieces, "WHITE"));
    }

    @AfterEach
    void tearDown() {
        boardDao.deleteAll();
    }

    @Test
    @DisplayName("체스보드 테이블에 데이터를 추가한다.")
    void addAll() {
        //given
        final Set<PieceDto> pieces = Set.of(
                PieceDto.from("a1", "r"),
                PieceDto.from("b1", "k"),
                PieceDto.from("c7", "K")
        );
        final ChessBoardDto given = ChessBoardDto.from(pieces, "WHITE");

        //when
        boardDao.addAll(given);

        //then
        assertThat(boardDao.findAll().pieces()).hasSize(6);
    }

    @Test
    @DisplayName("모든 정보를 가져온다.")
    void findAll() {
        //when
        final ChessBoardDto board = boardDao.findAll();

        //then
        assertThat(board.pieces()).hasSize(3);
    }

    @Test
    @DisplayName("데이터 개수를 반환한다.")
    void count() {
        //when
        final int count = boardDao.count();

        //then
        assertThat(count).isEqualTo(3);
    }

    @Test
    @DisplayName("모든 데이터를 삭제한다.")
    void deleteAll() {
        //when
        boardDao.deleteAll();

        //then
        assertThat(boardDao.count()).isEqualTo(0);
    }
}

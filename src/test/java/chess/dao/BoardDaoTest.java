package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.dto.ChessBoardDto;
import chess.dto.PieceDto;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardDaoTest {

    private BoardDao boardDao;

    @BeforeEach
    void setUp() {
        boardDao = new BoardDao();
        Set<PieceDto> pieces = Set.of(
                PieceDto.from("a8", "R"),
                PieceDto.from("b7", "N"),
                PieceDto.from("d5", "q")
        );
        boardDao.add(ChessBoardDto.from(pieces, "WHITE"));
    }

    @AfterEach
    void tearDown() {
        boardDao.deleteAll();
    }

    @Test
    @DisplayName("체스보드 테이블에 데이터를 추가한다.")
    void add() {
        //given
        Set<PieceDto> pieces = Set.of(
                PieceDto.from("a1", "r"),
                PieceDto.from("b1", "k"),
                PieceDto.from("c7", "K")
        );
        final ChessBoardDto given = ChessBoardDto.from(pieces, "WHITE");

        //when
        int[] ints = boardDao.add(given);

        //then
        assertThat(ints).containsExactly(1, 1, 1);
    }

    @Test
    @DisplayName("위치로 테이블 정보를 조회힌다.")
    void findByPosition() {
        //given
        String position = "a8";

        //when
        PieceDto piece = boardDao.findByPosition(position);

        //then
        assertAll(
                () -> assertThat(piece.position()).isEqualTo(position),
                () -> assertThat(piece.type()).isEqualTo("R")
        );
    }

    @Test
    @DisplayName("체스보드 테이블의 모든 정보를 가져온다.")
    void findAll() {
        //when
        ChessBoardDto board = boardDao.findAll();

        //then
        assertThat(board.pieces()).hasSize(3);
    }

    @Test
    @DisplayName("데이터 개수를 반환한다.")
    void count() {
        //when
        int count = boardDao.count();

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
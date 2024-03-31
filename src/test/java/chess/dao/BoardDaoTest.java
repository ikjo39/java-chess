package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
        boardDao.add(new ChessBoardDto(Set.of(
                new PieceDto("a8", "R"),
                new PieceDto("b7", "N"),
                new PieceDto("d5", "q")
        )));
    }

    @AfterEach
    void tearDown() {
        boardDao.deleteAll();
    }

    @Test
    @DisplayName("체스보드 테이블에 데이터를 추가한다.")
    void add() {
        //given
        final ChessBoardDto given = new ChessBoardDto(Set.of(
                new PieceDto("a1", "r"),
                new PieceDto("b1", "k"),
                new PieceDto("c7", "K")
        ));

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
    @DisplayName("위치로 테이블 정보를 조회한다.")
    void put() {
        //given
        String position = "a8";
        PieceDto pieceDto = new PieceDto(position, "q");

        //when
        int count = boardDao.put(pieceDto);
        PieceDto piece = boardDao.findByPosition(position);

        //then
        assertAll(
                () -> assertThat(count).isEqualTo(1),
                () -> assertThat(piece.position()).isEqualTo(position),
                () -> assertThat(piece.type()).isEqualTo("q")
        );
    }

    @Test
    @DisplayName("위치로 테이블 정보를 수정한다.")
    void putWhenNoPosition() {
        //given
        String position = "a8";
        PieceDto pieceDto = new PieceDto(position, "R");

        //when
        int count = boardDao.put(pieceDto);
        PieceDto piece = boardDao.findByPosition(position);

        //then
        assertAll(
                () -> assertThat(count).isEqualTo(1),
                () -> assertThat(piece.position()).isEqualTo(position),
                () -> assertThat(piece.type()).isEqualTo("R")
        );
    }
}
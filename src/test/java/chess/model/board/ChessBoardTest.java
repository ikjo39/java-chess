package chess.model.board;

import static chess.model.Fixture.A1;
import static chess.model.Fixture.A2;
import static chess.model.Fixture.A6;
import static chess.model.Fixture.B2;
import static chess.model.Fixture.B4;
import static chess.model.Fixture.C5;
import static chess.model.Fixture.D2;
import static chess.model.Fixture.F2;
import static chess.model.Fixture.F7;
import static chess.model.Fixture.G2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.dto.ChessBoardDto;
import chess.model.piece.BlackPawn;
import chess.model.piece.King;
import chess.model.piece.Piece;
import chess.model.piece.Side;
import chess.model.piece.WhitePawn;
import chess.model.position.ChessPosition;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChessBoardTest {

    @Test
    @DisplayName("Source 위치의 기물을 Target 위치로 이동한다.")
    void move() {
        //given
        final ChessBoard chessBoard = createInitializedChessBoard(Side.WHITE);
        ChessPosition source = B2;
        ChessPosition target = B4;

        //when
        ChessBoard movedBoard = chessBoard.move(source, target);
        Map<ChessPosition, Piece> board = movedBoard.getBoard();
        Piece sourcePiece = board.get(source);
        Piece targetPiece = board.get(target);

        //then
        assertAll(
                () -> assertThat(sourcePiece.isEmpty()).isTrue(),
                () -> assertThat(targetPiece.isEmpty()).isFalse()
        );
    }

    @Test
    @DisplayName("Source 위치에 기물이 없으면 예외가 발생한다.")
    void moveEmptySource() {
        //given
        final ChessBoard chessBoard = createInitializedChessBoard(Side.WHITE);

        //when //then
        assertThatThrownBy(() -> chessBoard.move(C5, D2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("현재 차례가 아닌 기물을 움직이려하면 예외가 발생한다.")
    void moveInvalidTurn() {
        //given
        final ChessBoard chessBoard = createInitializedChessBoard(Side.BLACK);

        //when // then
        assertThatThrownBy(() -> chessBoard.move(B2, B4))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("아군 기물이 있는 곳으로 이동하면 예외가 발생한다.")
    void moveWhenTargetPieceSameSide() {
        //given
        final ChessBoard chessBoard = createInitializedChessBoard(Side.WHITE);

        //when //then
        assertThatThrownBy(() -> chessBoard.move(A1, A2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이동할 수 없다면 예외가 발생한다.")
    void moveWhenPathContainsPiece() {
        //given
        final ChessBoard chessBoard = createInitializedChessBoard(Side.WHITE);

        //when //then
        assertThatThrownBy(() -> chessBoard.move(A1, A6))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("createChessBoardWithDynamicKingCount")
    @DisplayName("체스가 더 진행 가능한지 판단한다.")
    void checkChessEnd(ChessBoard given, boolean expected) {
        // when
        final boolean result = given.checkChessEnd();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("DTO로 변환한다.")
    void convertDto() {
        //given
        final ChessBoard given = createInitializedChessBoard(Side.WHITE);

        //when
        final ChessBoardDto chessBoardDto = given.convertDto();

        //then
        assertThat(chessBoardDto.pieces()).hasSize(given.getBoard().size());
    }

    private static Stream<Arguments> createChessBoardWithDynamicKingCount() {
        return Stream.of(
                Arguments.of(
                        new ChessBoard(Map.of(
                                F2, new King(Side.BLACK))
                        ),
                        true
                ),
                Arguments.of(
                        new ChessBoard(Map.of(
                                F2, new King(Side.BLACK),
                                F7, new King(Side.BLACK))
                        ),
                        false
                ),
                Arguments.of(
                        new ChessBoard(Map.of(
                                F2, new BlackPawn(),
                                G2, new WhitePawn())),
                        true
                )
        );
    }

    private ChessBoard createInitializedChessBoard(Side side) {
        return new ChessBoard(ChessBoardInitializer.create(), side);
    }
}

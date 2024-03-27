package chess.model.board;

import static chess.model.Fixture.A1;
import static chess.model.Fixture.A6;
import static chess.model.Fixture.B2;
import static chess.model.Fixture.B4;
import static chess.model.Fixture.C5;
import static chess.model.Fixture.D2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.model.piece.King;
import chess.model.piece.Pawn;
import chess.model.piece.Piece;
import chess.model.piece.Side;
import chess.model.position.ChessPosition;
import chess.model.position.File;
import chess.model.position.Rank;
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
        final ChessBoard chessBoard = createInitializedChessBoard();
        ChessPosition source = B2;
        ChessPosition target = B4;

        //when
        chessBoard.move(source, target);
        Map<ChessPosition, Piece> board = chessBoard.getBoard();
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
        final ChessBoard chessBoard = createInitializedChessBoard();

        //when //then
        assertThatThrownBy(() -> chessBoard.move(C5, D2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이동 경로에 기물이 존재한다면 예외가 발생한다.")
    void moveWhenPathContainsPiece() {
        //given
        final ChessBoard chessBoard = createInitializedChessBoard();

        //when //then
        assertThatThrownBy(() -> chessBoard.move(A1, A6))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("createChessBoardWithDynamicKingCount")
    @DisplayName("체스가 더 진행 가능한지 판단한다.")
    void checkChessEnd(ChessBoard given, boolean expected) {
        // when
        boolean result = given.checkChessEnd();

        // then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> createChessBoardWithDynamicKingCount() {
        return Stream.of(
                Arguments.of(
                        new ChessBoard(
                                Map.of(new ChessPosition(File.F, Rank.TWO), new King(Side.BLACK))
                        ),
                        true
                ),
                Arguments.of(
                        new ChessBoard(
                                Map.of(
                                        new ChessPosition(File.F, Rank.TWO), new King(Side.BLACK),
                                        new ChessPosition(File.F, Rank.SEVEN), new King(Side.BLACK)
                                )
                        ),
                        false
                ),
                Arguments.of(
                        new ChessBoard(
                                Map.of(
                                        new ChessPosition(File.F, Rank.TWO), new Pawn(Side.BLACK),
                                        new ChessPosition(File.G, Rank.TWO), new Pawn(Side.WHITE)
                                )
                        ),
                        true
                )
        );
    }

    private ChessBoard createInitializedChessBoard() {
        return new ChessBoard(ChessBoardInitializer.create());
    }
}

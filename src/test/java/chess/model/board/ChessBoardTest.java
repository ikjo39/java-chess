package chess.model.board;

import static chess.model.Fixture.A1;
import static chess.model.Fixture.A6;
import static chess.model.Fixture.A7;
import static chess.model.Fixture.B2;
import static chess.model.Fixture.B4;
import static chess.model.Fixture.B6;
import static chess.model.Fixture.B8;
import static chess.model.Fixture.C5;
import static chess.model.Fixture.C7;
import static chess.model.Fixture.C8;
import static chess.model.Fixture.D2;
import static chess.model.Fixture.D7;
import static chess.model.Fixture.E1;
import static chess.model.Fixture.E6;
import static chess.model.Fixture.F1;
import static chess.model.Fixture.F2;
import static chess.model.Fixture.F3;
import static chess.model.Fixture.F4;
import static chess.model.Fixture.F7;
import static chess.model.Fixture.G2;
import static chess.model.Fixture.G4;
import static chess.model.Fixture.H3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.model.piece.Bishop;
import chess.model.piece.BlackPawn;
import chess.model.piece.King;
import chess.model.piece.Knight;
import chess.model.piece.Piece;
import chess.model.piece.Point;
import chess.model.piece.Queen;
import chess.model.piece.Rook;
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
        final boolean result = given.checkChessEnd();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("체스판 위 진영 별 점수를 계산한다.")
    void calculatePoints() {
        // given
        final ChessBoard chessBoard = createInitializedChessBoard();

        // when
        final Map<Side, Point> totalPoints = chessBoard.calculatePoints();
        final double whitePoint = totalPoints.get(Side.WHITE).getValue();
        final double blackPoint = totalPoints.get(Side.BLACK).getValue();

        // then
        assertAll(
                () -> assertThat(whitePoint).isEqualTo(38),
                () -> assertThat(blackPoint).isEqualTo(38)
        );
    }

    @ParameterizedTest
    @MethodSource("createChessBoardDynamicPiecesWithPoints")
    @DisplayName("체스판 위 진영 별 점수를 계산한다.")
    void calculatePointsDynamicPieces(ChessBoard chessBoard, double whiteExpected, double blackExpected) {
        // when
        final Map<Side, Point> totalPoints = chessBoard.calculatePoints();
        final double whitePoint = totalPoints.get(Side.WHITE).getValue();
        final double blackPoint = totalPoints.get(Side.BLACK).getValue();

        // then
        assertAll(
                () -> assertThat(whitePoint).isEqualTo(whiteExpected),
                () -> assertThat(blackPoint).isEqualTo(blackExpected)
        );
    }

    private static Stream<Arguments> createChessBoardDynamicPiecesWithPoints() {
        return Stream.of(
                /*
                    .KR..... 8
                    P.PB.... 7
                    .P..Q... 6
                    ........ 5
                    .....nq. 4
                    .....p.p 3
                    .....pp. 2
                    ....rk.. 1
                    abcdefgh
                 */
                Arguments.of(
                        new ChessBoard(
                                Map.ofEntries(
                                        Map.entry(B8, new King(Side.BLACK)),
                                        Map.entry(C8, new Rook(Side.BLACK)),
                                        Map.entry(A7, new BlackPawn()),
                                        Map.entry(C7, new BlackPawn()),
                                        Map.entry(D7, new Bishop(Side.BLACK)),
                                        Map.entry(B6, new BlackPawn()),
                                        Map.entry(E6, new Queen(Side.BLACK)),
                                        Map.entry(F4, new Knight(Side.WHITE)),
                                        Map.entry(G4, new Queen(Side.WHITE)),
                                        Map.entry(F3, new WhitePawn()),
                                        Map.entry(H3, new WhitePawn()),
                                        Map.entry(F2, new WhitePawn()),
                                        Map.entry(G2, new WhitePawn()),
                                        Map.entry(E1, new Rook(Side.WHITE)),
                                        Map.entry(F1, new King(Side.WHITE))
                                )
                        ), 19.5, 20
                ),
                Arguments.of(
                        new ChessBoard(ChessBoardInitializer.create()),
                        38, 38
                )
        );
    }

    private static Stream<Arguments> createChessBoardWithDynamicKingCount() {
        return Stream.of(
                Arguments.of(
                        new ChessBoard(
                                Map.of(F2, new King(Side.BLACK))
                        ),
                        true
                ),
                Arguments.of(
                        new ChessBoard(
                                Map.of(
                                        F2, new King(Side.BLACK),
                                        F7, new King(Side.BLACK)
                                )
                        ),
                        false
                ),
                Arguments.of(
                        new ChessBoard(
                                Map.of(
                                        F2, new BlackPawn(),
                                        G2, new WhitePawn()
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

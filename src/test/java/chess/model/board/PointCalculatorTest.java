package chess.model.board;

import static chess.model.Fixture.A7;
import static chess.model.Fixture.B6;
import static chess.model.Fixture.B8;
import static chess.model.Fixture.C7;
import static chess.model.Fixture.C8;
import static chess.model.Fixture.D7;
import static chess.model.Fixture.E1;
import static chess.model.Fixture.E6;
import static chess.model.Fixture.F1;
import static chess.model.Fixture.F2;
import static chess.model.Fixture.F3;
import static chess.model.Fixture.F4;
import static chess.model.Fixture.G2;
import static chess.model.Fixture.G4;
import static chess.model.Fixture.H3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.model.piece.Bishop;
import chess.model.piece.BlackPawn;
import chess.model.piece.King;
import chess.model.piece.Knight;
import chess.model.piece.Queen;
import chess.model.piece.Rook;
import chess.model.piece.Side;
import chess.model.piece.WhitePawn;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PointCalculatorTest {

    @ParameterizedTest
    @MethodSource("createChessBoardDynamicPiecesWithPoints")
    @DisplayName("체스판 위 진영 별 점수를 계산한다.")
    void calculatePointsDynamicPieces(PointCalculator pointCalculator, double whiteExpected, double blackExpected) {
        // when
        Points points = pointCalculator.calculatesPoints();
        final double whitePoint = points.getPoints().get(Side.WHITE).getValue();
        final double blackPoint = points.getPoints().get(Side.BLACK).getValue();

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
                        new PointCalculator(Map.ofEntries(
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
                                Map.entry(F1, new King(Side.WHITE)))
                        ),
                        19.5,
                        20
                ),
                Arguments.of(
                        new PointCalculator(ChessBoardInitializer.create()),
                        38,
                        38
                )
        );
    }
}
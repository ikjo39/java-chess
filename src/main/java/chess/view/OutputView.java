package chess.view;

import chess.model.board.ChessBoard;
import chess.model.board.Point;
import chess.model.board.Points;
import chess.model.piece.Piece;
import chess.model.piece.PieceText;
import chess.model.piece.Side;
import chess.model.position.ChessPosition;
import chess.model.position.File;
import chess.model.position.Rank;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {

    private static final String SIDE_POINTS_FORMAT = "%s 점수: %s";
    private static final String DOMINANT_FORMAT = "우세 진영: %s";
    private static final String MULTIPLE_WINNER_DELIMITER = ", ";
    private static final DecimalFormat POINT_FORMAT = new DecimalFormat("#,###.#");

    public void printException(final String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printChessBoard(final ChessBoard chessBoard) {
        final Map<ChessPosition, Piece> board = chessBoard.getBoard();
        System.out.println(convertChessBoardText(board));
    }

    public void printPoints(final Points points) {
        final Map<Side, Point> pointsWithSide = points.getPoints();
        System.out.println(getSidePointsFormat(pointsWithSide));
        System.out.println(getWinnerFormat(points.calculateWinner()));
    }

    private String convertChessBoardText(final Map<ChessPosition, Piece> board) {
        return Arrays.stream(Rank.values())
                .map(rank -> convertPieceTextsInOneRank(board, rank))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String convertPieceTextsInOneRank(final Map<ChessPosition, Piece> board, final Rank rank) {
        return Arrays.stream(File.values())
                .map(file -> new ChessPosition(file, rank))
                .map(board::get)
                .map(PieceText::from)
                .map(PieceText::getName)
                .collect(Collectors.joining(""));
    }

    private String getSidePointsFormat(final Map<Side, Point> pointsWithSide) {
        return pointsWithSide.keySet()
                .stream()
                .map(side -> getSidePointFormat(side, pointsWithSide))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String getSidePointFormat(final Side side, final Map<Side, Point> pointsWithSide) {
        final String pointFormat = POINT_FORMAT.format(pointsWithSide.get(side).getValue());
        return String.format(SIDE_POINTS_FORMAT, side.name(), pointFormat);
    }

    private String getWinnerFormat(final List<Side> side) {
        final String result = getWinnerText(side);
        return String.format(DOMINANT_FORMAT, result);
    }

    private String getWinnerText(final List<Side> side) {
        return side.stream()
                .map(Side::name)
                .collect(Collectors.joining(MULTIPLE_WINNER_DELIMITER));
    }
}

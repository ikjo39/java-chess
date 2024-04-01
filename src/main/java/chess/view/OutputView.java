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
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class OutputView {

    private static final String SIDE_POINTS_FORMAT = "%s 점수: %s";
    private static final String DOMINANT_FORMAT = "우세 진영: %s";
    private static final DecimalFormat POINT_FORMAT = new DecimalFormat("#,###.#");

    public void printException(final String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printChessBoard(final ChessBoard chessBoard) {
        final Map<ChessPosition, Piece> board = chessBoard.getBoard();
        System.out.println(convertChessBoardText(board));
    }

    public void printPoints(final Points points) {
        Map<Side, Point> pointsWithSide = points.getPoints();
        System.out.println(getSidePointsFormat(pointsWithSide));
        System.out.println(getWinnerFormat(points.calculateWinner()));
    }

    private String convertChessBoardText(final Map<ChessPosition, Piece> board) {
        return Arrays.stream(Rank.values())
                .map(rank -> convertPieceTextsInOneRank(board, rank))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String convertPieceTextsInOneRank(Map<ChessPosition, Piece> board, Rank rank) {
        return Arrays.stream(File.values())
                .map(file -> new ChessPosition(file, rank))
                .map(board::get)
                .map(PieceText::from)
                .map(PieceText::getName)
                .collect(Collectors.joining(""));
    }

    private String getSidePointsFormat(final Map<Side, Point> pointsWithSide) {
        return pointsWithSide.entrySet()
                .stream()
                .map(this::getSidePointFormat)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String getSidePointFormat(final Entry<Side, Point> entry) {
        String side = SideText.from(entry.getKey()).getText();
        String pointFormat = POINT_FORMAT.format(entry.getValue().getValue());
        return String.format(SIDE_POINTS_FORMAT, side, pointFormat);
    }

    private String getWinnerFormat(final List<Side> side) {
        String result = side.stream()
                .map(SideText::from)
                .map(SideText::getText)
                .collect(Collectors.joining(", "));
        return String.format(DOMINANT_FORMAT, result);
    }
}

package chess.controller;

import chess.dao.BoardDao;
import chess.dao.TableDao;
import chess.model.board.ChessBoard;
import chess.model.board.ChessBoardInitializer;
import chess.model.board.PointCalculator;
import chess.model.board.Points;
import chess.model.position.ChessPosition;
import chess.view.GameArguments;
import chess.view.GameCommand;
import chess.view.InputView;
import chess.view.MoveArguments;
import chess.view.OutputView;
import java.util.Objects;
import java.util.function.Supplier;

public class ChessController {
    private final InputView inputView;
    private final OutputView outputView;

    public ChessController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        TableDao.createChessBoardIfNotExist();
        final GameCommand gameCommand = retryOnException(
                () -> GameCommand.createFirstGameCommand(inputView.readGameCommand()));
        if (gameCommand.isEnd()) {
            return;
        }
        BoardDao boardDao = new BoardDao();
        final ChessBoard chessBoard = getChessBoard(boardDao);
        outputView.printChessBoard(chessBoard);
        ChessBoard finalChessBoard;
        finalChessBoard = retryOnException(() -> playChess(chessBoard));
        printPoints(finalChessBoard);
        updateBoardChange(boardDao, finalChessBoard);
    }

    private ChessBoard getChessBoard(BoardDao boardDao) {
        ChessBoard chessBoard;
        if (boardDao.count() != 0) {
            return getDataFromDb(boardDao);
        }
        chessBoard = new ChessBoard(ChessBoardInitializer.create());
        return chessBoard;
    }

    private ChessBoard getDataFromDb(BoardDao boardDao) {
        return boardDao.findAll()
                .convert();
    }

    private ChessBoard playChess(ChessBoard chessBoard) {
        while (!chessBoard.checkChessEnd()) {
            final GameArguments gameArguments = inputView.readGameArguments();
            final GameCommand gameCommand = gameArguments.gameCommand();
            if (gameCommand.isEnd()) {
                break;
            }
            if (gameCommand.isStatus()) {
                printPoints(chessBoard);
                continue;
            }
            final MoveArguments moveArguments = gameArguments.moveArguments();
            chessBoard = move(chessBoard, moveArguments);
        }
        return chessBoard;
    }

    private ChessBoard move(ChessBoard chessBoard, final MoveArguments moveArguments) {
        final ChessPosition source = moveArguments.createSourcePosition();
        final ChessPosition target = moveArguments.createTargetPosition();
        chessBoard = chessBoard.move(source, target);
        outputView.printChessBoard(chessBoard);
        return chessBoard;
    }

    private void printPoints(final ChessBoard chessBoard) {
        final PointCalculator pointCalculator = chessBoard.getPointCalculator();
        final Points points = pointCalculator.calculatesPoints();
        outputView.printPoints(points);
    }

    private void updateBoardChange(BoardDao boardDao, ChessBoard finalChessBoard) {
        boardDao.deleteAll();
        if (finalChessBoard.checkChessEnd()) {
            return;
        }
        boardDao.add(finalChessBoard.convertDto());
    }

    private <T> T retryOnException(final Supplier<T> retryOperation) {
        boolean retry = true;
        T result = null;
        while (retry) {
            result = tryOperation(retryOperation);
            retry = Objects.isNull(result);
        }
        return result;
    }

    private <T> T tryOperation(final Supplier<T> operation) {
        try {
            return operation.get();
        } catch (IllegalArgumentException | IllegalStateException e) {
            outputView.printException(e.getMessage());
            return null;
        }
    }
}

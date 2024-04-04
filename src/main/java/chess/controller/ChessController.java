package chess.controller;

import chess.dao.ChessGameDao;
import chess.dao.TableDao;
import chess.dto.ChessBoardDto;
import chess.model.board.ChessBoard;
import chess.model.board.ChessBoardInitializer;
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
    private final ChessGameDao chessGameDao;

    public ChessController(final InputView inputView,
                           final OutputView outputView,
                           final ChessGameDao chessGameDao) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.chessGameDao = chessGameDao;
    }

    public void run() {
        TableDao.createChessGameIfNotExist();
        final GameCommand gameCommand = retryOnException(inputView::readGameCommand);
        if (!gameCommand.isEnd()) {
            final ChessBoard chessBoard = getChessBoard(chessGameDao);
            updateBoardChange(chessGameDao, chessBoard);
            outputView.printChessBoard(chessBoard);
            final ChessBoard movedChessBoard = retryOnException(() -> playChess(chessGameDao, gameCommand));
            printScoreWhenEnd(movedChessBoard, chessBoard);
        }
    }

    private ChessBoard getChessBoard(final ChessGameDao chessGameDao) {
        if (chessGameDao.hasAnyData()) {
            return getAllDataFrom(chessGameDao);
        }
        return new ChessBoard(ChessBoardInitializer.create());
    }

    private ChessBoard getAllDataFrom(final ChessGameDao chessGameDao) {
        final ChessBoardDto chessBoardDto = chessGameDao.findAll();
        return chessBoardDto.convert();
    }

    private ChessBoard playChess(final ChessGameDao chessGameDao, GameCommand gameCommand) {
        ChessBoard chessBoard = getAllDataFrom(chessGameDao);
        while (!chessBoard.checkChessEnd() && !gameCommand.isEnd()) {
            final GameArguments gameArguments = inputView.readGameArguments();
            gameCommand = gameArguments.gameCommand();
            chessBoard = runWithGameCommand(chessGameDao, gameCommand, gameArguments, chessBoard);
        }
        return chessBoard;
    }

    private ChessBoard runWithGameCommand(final ChessGameDao chessGameDao,
                                          final GameCommand gameCommand,
                                          final GameArguments gameArguments,
                                          ChessBoard chessBoard) {
        if (gameCommand.isStatus()) {
            outputView.printPoints(chessBoard.calculate());
        }
        if (gameCommand.isMove()) {
            final MoveArguments moveArguments = gameArguments.moveArguments();
            chessBoard = move(chessBoard, moveArguments);
            outputView.printChessBoard(chessBoard);
            updateBoardChange(chessGameDao, chessBoard);
        }
        return chessBoard;
    }

    private ChessBoard move(final ChessBoard chessBoard, final MoveArguments moveArguments) {
        final ChessPosition source = moveArguments.createSourcePosition();
        final ChessPosition target = moveArguments.createTargetPosition();
        return chessBoard.move(source, target);
    }

    private void updateBoardChange(final ChessGameDao chessGameDao, final ChessBoard chessBoard) {
        chessGameDao.deleteAll();
        if (!chessBoard.checkChessEnd()) {
            chessGameDao.addAll(chessBoard.convertDto());
        }
    }

    private void printScoreWhenEnd(final ChessBoard movedChessBoard, final ChessBoard chessBoard) {
        if (movedChessBoard.checkChessEnd()) {
            outputView.printPoints(chessBoard.calculate());
        }
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

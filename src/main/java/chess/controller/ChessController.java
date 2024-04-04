package chess.controller;

import chess.dao.BoardDao;
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
    private final BoardDao boardDao;

    public ChessController(final InputView inputView,
                           final OutputView outputView,
                           final BoardDao boardDao) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.boardDao = boardDao;
    }

    public void run() {
        TableDao.createChessBoardIfNotExist();
        final GameCommand gameCommand = retryOnException(inputView::readGameCommand);
        if (!gameCommand.isEnd()) {
            final ChessBoard chessBoard = getChessBoard(boardDao);
            updateBoardChange(boardDao, chessBoard);
            outputView.printChessBoard(chessBoard);
            final ChessBoard movedChessBoard = retryOnException(() -> playChess(boardDao, gameCommand));
            printScoreWhenEnd(movedChessBoard, chessBoard);
        }
    }

    private ChessBoard getChessBoard(final BoardDao boardDao) {
        if (boardDao.count() != 0) {
            return getAllDataFrom(boardDao);
        }
        return new ChessBoard(ChessBoardInitializer.create());
    }

    private ChessBoard getAllDataFrom(final BoardDao boardDao) {
        final ChessBoardDto chessBoardDto = boardDao.findAll();
        return chessBoardDto.convert();
    }

    private ChessBoard playChess(final BoardDao boardDao, GameCommand gameCommand) {
        ChessBoard chessBoard = getAllDataFrom(boardDao);
        while (!chessBoard.checkChessEnd() && !gameCommand.isEnd()) {
            final GameArguments gameArguments = inputView.readGameArguments();
            gameCommand = gameArguments.gameCommand();
            chessBoard = runWithGameCommand(boardDao, gameCommand, gameArguments, chessBoard);
        }
        return chessBoard;
    }

    private ChessBoard runWithGameCommand(final BoardDao boardDao,
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
            updateBoardChange(boardDao, chessBoard);
        }
        return chessBoard;
    }

    private ChessBoard move(final ChessBoard chessBoard, final MoveArguments moveArguments) {
        final ChessPosition source = moveArguments.createSourcePosition();
        final ChessPosition target = moveArguments.createTargetPosition();
        return chessBoard.move(source, target);
    }

    private void updateBoardChange(final BoardDao boardDao, final ChessBoard chessBoard) {
        boardDao.deleteAll();
        if (!chessBoard.checkChessEnd()) {
            boardDao.addAll(chessBoard.convertDto());
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

package chess.controller;

import chess.dao.BoardRepository;
import chess.dao.TableDao;
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
    private final BoardRepository boardRepository;

    public ChessController(final InputView inputView,
                           final OutputView outputView,
                           final BoardRepository boardRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.boardRepository = boardRepository;
    }

    public void run() {
        TableDao.createChessBoardIfNotExist();
        final GameCommand gameCommand = retryOnException(inputView::readGameCommand);
        if (!gameCommand.isEnd()) {
            final ChessBoard chessBoard = getChessBoard(boardRepository);
            updateBoardChange(boardRepository, chessBoard);
            outputView.printChessBoard(chessBoard);
            retryOnException(() -> playChess(boardRepository, gameCommand));
        }
    }

    private ChessBoard getChessBoard(final BoardRepository boardRepository) {
        if (boardRepository.count() != 0) {
            return getAllDataFrom(boardRepository);
        }
        return new ChessBoard(ChessBoardInitializer.create());
    }

    private ChessBoard getAllDataFrom(final BoardRepository boardRepository) {
        return boardRepository.findAll()
                .convert();
    }

    private ChessBoard playChess(final BoardRepository boardRepository, GameCommand gameCommand) {
        ChessBoard chessBoard = getAllDataFrom(boardRepository);
        while (!gameCommand.isEnd()) {
            final GameArguments gameArguments = inputView.readGameArguments();
            gameCommand = gameArguments.gameCommand();
            if (chessBoard.checkChessEnd()) {
                outputView.printPoints(chessBoard.calculate());
                break;
            }
            if (gameCommand.isStatus()) {
                outputView.printPoints(chessBoard.calculate());
            }
            if (gameCommand.isMove()) {
                final MoveArguments moveArguments = gameArguments.moveArguments();
                chessBoard = move(chessBoard, moveArguments);
                outputView.printChessBoard(chessBoard);
                updateBoardChange(boardRepository, chessBoard);
            }
        }
        return chessBoard;
    }

    private ChessBoard move(final ChessBoard chessBoard, final MoveArguments moveArguments) {
        final ChessPosition source = moveArguments.createSourcePosition();
        final ChessPosition target = moveArguments.createTargetPosition();
        return chessBoard.move(source, target);
    }

    private void updateBoardChange(final BoardRepository boardRepository, final ChessBoard finalChessBoard) {
        boardRepository.deleteAll();
        if (finalChessBoard.checkChessEnd()) {
            boardRepository.add(finalChessBoard.convertDto());
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

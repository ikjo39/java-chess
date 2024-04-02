package chess.controller;

import chess.dao.BoardRepository;
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
    private final BoardRepository boardRepository;

    public ChessController(final InputView inputView, final OutputView outputView, BoardRepository boardRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.boardRepository = boardRepository;
    }

    public void run() {
        TableDao.createChessBoardIfNotExist();
        final GameCommand gameCommand = retryOnException(
                () -> GameCommand.createFirstGameCommand(inputView.readGameCommand()));
        if (gameCommand.isEnd()) {
            return;
        }
        final ChessBoard chessBoard = getChessBoard(boardRepository);
        updateBoardChange(boardRepository, chessBoard);
        outputView.printChessBoard(chessBoard);
        printPoints(retryOnException(() -> playChess(boardRepository)));
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

    private ChessBoard playChess(final BoardRepository boardRepository) {
        ChessBoard chessBoard = getAllDataFrom(boardRepository);
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
            outputView.printChessBoard(chessBoard);
            updateBoardChange(boardRepository, chessBoard);
        }
        return chessBoard;
    }

    private ChessBoard move(ChessBoard chessBoard, final MoveArguments moveArguments) {
        final ChessPosition source = moveArguments.createSourcePosition();
        final ChessPosition target = moveArguments.createTargetPosition();
        return chessBoard.move(source, target);
    }

    private void printPoints(final ChessBoard chessBoard) {
        final PointCalculator pointCalculator = chessBoard.getPointCalculator();
        final Points points = pointCalculator.calculate();
        outputView.printPoints(points);
    }

    private void updateBoardChange(BoardRepository boardRepository, ChessBoard finalChessBoard) {
        boardRepository.deleteAll();
        if (finalChessBoard.checkChessEnd()) {
            return;
        }
        boardRepository.add(finalChessBoard.convertDto());
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

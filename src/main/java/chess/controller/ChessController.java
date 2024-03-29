package chess.controller;

import chess.model.board.ChessBoard;
import chess.model.board.ChessBoardInitializer;
import chess.model.piece.Points;
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
        final GameCommand gameCommand = retryOnException(
                () -> GameCommand.createFirstGameCommand(inputView.readGameCommand())
        );
        if (gameCommand.isEnd()) {
            return;
        }
        ChessBoard chessBoard = new ChessBoard(ChessBoardInitializer.create());
        outputView.printChessBoard(chessBoard);
        retryOnException(() -> playChess(chessBoard));
        printPoints(chessBoard);
    }

    private void playChess(ChessBoard chessBoard) {
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
            move(chessBoard, moveArguments);
        }
    }

    private void move(ChessBoard chessBoard, final MoveArguments moveArguments) {
        final ChessPosition source = moveArguments.createSourcePosition();
        final ChessPosition target = moveArguments.createTargetPosition();
        chessBoard = chessBoard.move(source, target);
        outputView.printChessBoard(chessBoard);
    }

    private void printPoints(final ChessBoard chessBoard) {
        Points points = new Points(chessBoard.calculatePoints());
        outputView.printPoints(points);
    }

    private void retryOnException(final Runnable retryOperation) {
        boolean retry = true;
        while (retry) {
            retry = tryOperation(retryOperation);
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

    private boolean tryOperation(final Runnable operation) {
        try {
            operation.run();
            return false;
        } catch (IllegalArgumentException | IllegalStateException e) {
            outputView.printException(e.getMessage());
            return true;
        }
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

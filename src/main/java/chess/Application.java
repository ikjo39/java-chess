package chess;

import chess.controller.ChessController;
import chess.dao.BoardDao;
import chess.dao.BoardRepository;
import chess.view.InputView;
import chess.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        BoardRepository boardRepository = new BoardDao();
        ChessController chessController = new ChessController(inputView, outputView, boardRepository);
        chessController.run();
    }
}

package chess;

import chess.controller.ChessController;
import chess.dao.BoardDao;
import chess.dao.BoardDaoImpl;
import chess.view.InputView;
import chess.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        BoardDao boardDao = new BoardDaoImpl();
        ChessController chessController = new ChessController(inputView, outputView, boardDao);
        chessController.run();
    }
}

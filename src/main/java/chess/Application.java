package chess;

import chess.controller.ChessController;
import chess.dao.ChessGameDao;
import chess.dao.ChessGameDaoImpl;
import chess.view.InputView;
import chess.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        ChessGameDao boardDao = new ChessGameDaoImpl();
        ChessController chessController = new ChessController(inputView, outputView, boardDao);
        chessController.run();
    }
}

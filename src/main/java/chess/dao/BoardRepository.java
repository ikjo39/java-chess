package chess.dao;

import chess.dto.ChessBoardDto;
import chess.dto.PieceDto;

public interface BoardRepository {
    void add(ChessBoardDto chessBoardDto);

    PieceDto findByPosition(String data);

    ChessBoardDto findAll();

    int count();

    void deleteAll();
}

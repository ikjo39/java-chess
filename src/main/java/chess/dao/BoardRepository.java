package chess.dao;

import chess.dto.ChessBoardDto;
import chess.dto.PieceDto;

public interface BoardRepository {
    void add(final ChessBoardDto chessBoardDto);

    PieceDto findByPosition(final String data);

    ChessBoardDto findAll();

    int count();

    void deleteAll();
}

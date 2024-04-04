package chess.dao;

import chess.dto.ChessBoardDto;

public interface BoardRepository {
    void addAll(final ChessBoardDto chessBoardDto);

    ChessBoardDto findAll();

    int count();

    void deleteAll();
}

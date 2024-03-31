package chess.dao;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

import chess.dto.ChessBoardDto;
import chess.dto.PieceDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class TestBoardDao implements BoardRepository {

    private static final Map<Integer, ChessBoards> BOARDS_CACHE = new HashMap<>();
    private static int count = 0;

    @Override
    public void add(ChessBoardDto chessBoardDto) {
        List<ChessBoards> chessBoards = convertChessBoards(chessBoardDto);
        chessBoards.forEach(chessBoard ->
                BOARDS_CACHE.put(++count, chessBoard)
        );
    }

    private List<ChessBoards> convertChessBoards(ChessBoardDto chessBoardDto) {
        Set<PieceDto> pieces = chessBoardDto.pieces();
        String turn = chessBoardDto.turn();
        return pieces.stream()
                .map(pieceDto -> new ChessBoards(pieceDto.position(), pieceDto.type(), turn))
                .toList();
    }

    @Override
    public PieceDto findByPosition(String data) {
        return BOARDS_CACHE.values()
                .stream()
                .filter(chessBoards -> chessBoards.position().equals(data))
                .map(chessBoard -> PieceDto.from(chessBoard.position(), chessBoard.type()))
                .findFirst()
                .get();
    }

    @Override
    public ChessBoardDto findAll() {
        String turn = BOARDS_CACHE.get(1).turn();
        return BOARDS_CACHE.values()
                .stream()
                .map(chessBoard -> PieceDto.from(chessBoard.position(), chessBoard.type()))
                .collect(collectingAndThen(toSet(), set -> ChessBoardDto.from(set, turn)));
    }

    @Override
    public int count() {
        return BOARDS_CACHE.values().size();
    }

    @Override
    public void deleteAll() {
        BOARDS_CACHE.clear();
        count = 0;
    }
}

package chess.dao;

import java.util.Set;

public record ChessBoardDto(Set<PieceDto> pieces) {
}

package chess.view;

import chess.model.piece.Side;
import java.util.Arrays;

public enum SideText {
    WHITE(Side.WHITE, "백"),
    BLACK(Side.BLACK, "흑");

    private final Side side;
    private final String text;

    SideText(Side side, String text) {
        this.side = side;
        this.text = text;
    }

    public static SideText from(Side given) {
        return Arrays.stream(values())
                .filter(sidePoint -> sidePoint.side.equals(given))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("진영은 흑과 백 중 하나여야 합니다."));
    }

    public String getText() {
        return text;
    }
}

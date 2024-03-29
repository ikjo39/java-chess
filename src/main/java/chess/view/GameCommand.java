package chess.view;

import java.util.Arrays;

public enum GameCommand {
    START("start"),
    MOVE("move"),
    STATUS("status"),
    END("end");

    private final String text;

    GameCommand(final String text) {
        this.text = text;
    }

    public static GameCommand createFirstGameCommand(final String input) {
        return Arrays.stream(values())
                .filter(value -> value.text.equals(input))
                .filter(value -> START.equals(value) || END.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("start 혹은 end를 입력해주세요."));
    }

    public static GameCommand createMoveCommand(final String input) {
        return Arrays.stream(values())
                .filter(value -> value.text.equals(input))
                .filter(value -> !START.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("move, end, status 중 하나를 입력해주세요."));
    }

    public boolean isEnd() {
        return this.equals(END);
    }

    public boolean isStatus() {
        return this.equals(STATUS);
    }
}

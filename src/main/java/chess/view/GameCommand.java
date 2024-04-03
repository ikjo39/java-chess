package chess.view;

import java.util.Arrays;
import java.util.regex.Pattern;

public enum GameCommand {
    START(Pattern.compile("start")),
    MOVE(Pattern.compile("move\\s([a-zA-Z])(\\d)\\s([a-zA-Z])(\\d)")),
    STATUS(Pattern.compile("status")),
    END(Pattern.compile("end"));

    private final Pattern pattern;

    GameCommand(final Pattern pattern) {
        this.pattern = pattern;
    }

    public static GameCommand createFirstGameCommand(final String input) {
        return Arrays.stream(values())
                .filter(value -> value.pattern.matcher(input).matches())
                .filter(value -> START.equals(value) || END.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("start 혹은 end를 입력해주세요."));
    }

    public static GameCommand createMoveCommand(final String input) {
        return Arrays.stream(values())
                .filter(value -> value.pattern.matcher(input).matches())
                .filter(value -> !START.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("move, end, status 중 하나를 입력해주세요."));
    }

    public boolean isEnd() {
        return END.equals(this);
    }

    public boolean isStatus() {
        return STATUS.equals(this);
    }

    public boolean isMove() {
        return MOVE.equals(this);
    }
}

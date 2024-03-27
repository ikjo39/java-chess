package chess.view;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String readGameCommand() {
        final String introductionText = """
                > 체스 게임을 시작합니다.
                > 게임 시작 : start
                > 게임 종료 : end
                > 게임 이동 : move source위치 target위치 - 예. move b2 b3""";
        System.out.println(introductionText);
        final String input = scanner.nextLine();
        validateInput(input);
        return input;
    }

    public GameArguments readGameArguments() {
        final String input = scanner.nextLine();
        final List<String> inputs = Arrays.stream(input.split(" "))
                .toList();
        final GameCommand moveCommand = GameCommand.createMoveCommand(inputs.get(0));
        if (moveCommand.isEnd() || moveCommand.isStatus()) {
            return new GameArguments(moveCommand, null);
        }
        return new GameArguments(moveCommand, MoveArguments.from(inputs));
    }

    private void validateInput(final String command) {
        if (Objects.isNull(command) || command.isBlank()) {
            throw new IllegalArgumentException("입력값은 공백일 수 없습니다.");
        }
    }
}

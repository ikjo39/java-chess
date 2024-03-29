package chess.view;

import chess.model.position.ChessPosition;
import chess.model.position.File;
import chess.model.position.Rank;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoveArguments {
    private static final int ARGUMENTS_SIZE = 4;
    private static final String MOVE_REGEX = "([a-zA-Z])(\\d)";
    private static final Pattern MOVE_PATTERN = Pattern.compile(MOVE_REGEX);

    private final String sourceRank;
    private final String targetRank;
    private final String sourceFile;
    private final String targetFile;

    private MoveArguments(
            final String sourceFile, final String sourceRank, final String targetFile, final String targetRank
    ) {
        this.sourceFile = sourceFile;
        this.sourceRank = sourceRank;
        this.targetFile = targetFile;
        this.targetRank = targetRank;
    }

    public static MoveArguments from(final List<String> inputs) {
        List<String> arguments = convertArguments(inputs);
        validateArgumentsSize(arguments);
        return new MoveArguments(arguments.get(0), arguments.get(1),
                arguments.get(2), arguments.get(3));
    }

    private static List<String> convertArguments(final List<String> arguments) {
        return arguments.stream()
                .skip(1)
                .filter(MoveArguments::validateMoveArgument)
                .flatMap(s -> Arrays.stream(s.split("")))
                .toList();
    }

    private static void validateArgumentsSize(final List<String> results) {
        if (results.size() != ARGUMENTS_SIZE) {
            throw new IllegalArgumentException("Source 위치와 Target 위치가 정확하지 않습니다.");
        }
    }

    private static boolean validateMoveArgument(final String argument) {
        final Matcher matcher = MOVE_PATTERN.matcher(argument);
        return matcher.matches();
    }

    public ChessPosition createSourcePosition() {
        return new ChessPosition(File.from(sourceFile), Rank.from(sourceRank));
    }

    public ChessPosition createTargetPosition() {
        return new ChessPosition(File.from(targetFile), Rank.from(targetRank));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MoveArguments that = (MoveArguments) o;
        return Objects.equals(sourceRank, that.sourceRank) && Objects.equals(targetRank,
                that.targetRank) && Objects.equals(sourceFile, that.sourceFile) && Objects.equals(
                targetFile, that.targetFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceRank, targetRank, sourceFile, targetFile);
    }
}

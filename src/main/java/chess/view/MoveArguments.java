package chess.view;

import chess.model.position.ChessPosition;
import chess.model.position.File;
import chess.model.position.Rank;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MoveArguments {
    private static final int ARGUMENTS_SIZE = 4;

    private final String sourceFile;
    private final String sourceRank;
    private final String targetFile;
    private final String targetRank;

    private MoveArguments(final String sourceFile,
                          final String sourceRank,
                          final String targetFile,
                          final String targetRank) {
        this.sourceFile = sourceFile;
        this.sourceRank = sourceRank;
        this.targetFile = targetFile;
        this.targetRank = targetRank;
    }

    public static MoveArguments from(final String input) {
        final List<String> arguments = convertArguments(input);
        validateArgumentsSize(arguments);
        return new MoveArguments(arguments.get(0), arguments.get(1),
                arguments.get(2), arguments.get(3));
    }

    private static List<String> convertArguments(final String arguments) {
        return Arrays.stream(arguments.split(" "))
                .skip(1)
                .flatMap(s -> Arrays.stream(s.split("")))
                .toList();
    }

    private static void validateArgumentsSize(final List<String> results) {
        if (results.size() != ARGUMENTS_SIZE) {
            throw new IllegalArgumentException("Source 위치와 Target 위치가 정확하지 않습니다.");
        }
    }

    public ChessPosition createSourcePosition() {
        return new ChessPosition(File.from(sourceFile), Rank.from(sourceRank));
    }

    public ChessPosition createTargetPosition() {
        return new ChessPosition(File.from(targetFile), Rank.from(targetRank));
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final MoveArguments other = (MoveArguments) obj;
        return Objects.equals(this.sourceFile, other.sourceFile)
                && Objects.equals(this.sourceRank, other.sourceRank)
                && Objects.equals(this.targetFile, other.targetFile)
                && Objects.equals(this.targetRank, other.targetRank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceFile, sourceRank, targetFile, targetRank);
    }

    @Override
    public String toString() {
        return "MoveArguments{" +
                "sourceFile='" + sourceFile + '\'' +
                ", sourceRank='" + sourceRank + '\'' +
                ", targetFile='" + targetFile + '\'' +
                ", targetRank='" + targetRank + '\'' +
                '}';
    }
}

package chess.model.position;

import java.util.Arrays;

public enum File {
    A(1, "a"),
    B(2, "b"),
    C(3, "c"),
    D(4, "d"),
    E(5, "e"),
    F(6, "f"),
    G(7, "g"),
    H(8, "h");

    private final int coordinate;
    private final String name;

    File(final int coordinate, final String name) {
        this.coordinate = coordinate;
        this.name = name;
    }

    public static File from(final String name) {
        return Arrays.stream(values())
                .filter(file -> file.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 file 이름입니다."));
    }

    public boolean canMove(final int step) {
        return canMoveLeft(step) && canMoveRight(step);
    }

    public File findNextFile(final int offset) {
        return Arrays.stream(values())
                .filter(file -> hasSameCoordinate(offset, file))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("File의 이동가능한 범위를 벗어났습니다."));
    }

    private boolean hasSameCoordinate(final int offset, final File file) {
        return file.coordinate == offset + coordinate;
    }

    private boolean canMoveLeft(final int step) {
        return coordinate + step >= 1;
    }

    private boolean canMoveRight(final int step) {
        return coordinate + step <= values().length;
    }

    public String getName() {
        return name;
    }
}

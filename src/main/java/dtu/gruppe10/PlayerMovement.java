package dtu.gruppe10;

import java.util.Arrays;

public class PlayerMovement {
    public final int Start;
    public final int End;
    public final MovementType Type;
    public final boolean PassedStart;

    private PlayerMovement(int startIndex, int endIndex, MovementType type, boolean passedStart) {
        this.Start = startIndex;
        this.End = endIndex;
        this.Type = type;
        this.PassedStart = passedStart;
    }

    public int[] getFieldIndexes(int totalFieldCount, boolean includeStart, boolean includeEnd) {
        int passedFieldCount;
        switch (this.Type) {
            case FORWARD -> passedFieldCount = this.End - this.Start - 1;
            case BACKWARD -> passedFieldCount = this.Start - this.End - 1;
            default -> passedFieldCount = 0;
        }

        if (PassedStart) {
            passedFieldCount += totalFieldCount;
        }

        int bonusFieldCount = 0;
        if (includeStart) {
            bonusFieldCount++;
        }
        if (includeEnd) {
            bonusFieldCount++;
        }

        int[] fields = new int[passedFieldCount + bonusFieldCount];
        int currentIndex = 0;

        if (includeStart) {
            fields[currentIndex++] = this.Start;
        }

        int direction = (this.Type == MovementType.BACKWARD) ? -1 : 1;
        for (int i = 0; i < passedFieldCount; i++) {
            fields[currentIndex++] = (this.Start + direction * (i + 1)) % totalFieldCount;
        }

        if (includeEnd) {
            fields[currentIndex] = this.End;
        }

        return fields;
    }

    public static PlayerMovement ForwardMove(int startIndex, int endIndex) {
        return new PlayerMovement(startIndex, endIndex, MovementType.FORWARD, endIndex < startIndex);
    }

    public static PlayerMovement BackwardMove(int startIndex, int endIndex) {
        return new PlayerMovement(startIndex, endIndex, MovementType.BACKWARD, endIndex > startIndex);
    }

    public static PlayerMovement DirectMove(int startIndex, int endIndex) {
        return new PlayerMovement(startIndex, endIndex, MovementType.DIRECT, false);
    }
}
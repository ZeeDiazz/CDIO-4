package dtu.gruppe10;


import dtu.gruppe10.fields.Field;

public class PlayerMovement {
    public final int StartIndex;
    public final int EndIndex;
    public final MovementType Type;
    public final boolean PassedStart;

    private PlayerMovement(int startIndex, int endIndex, MovementType type, boolean passedStart) {
        this.StartIndex = startIndex;
        this.EndIndex = endIndex;
        this.Type = type;
        this.PassedStart = passedStart;
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

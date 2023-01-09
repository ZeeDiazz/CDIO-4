package dtu.gruppe10.players;


import dtu.gruppe10.fields.Field;

public class PlayerMovement {
    public final int StartIndex;
    public final int EndIndex;
    public final boolean PassedStart;
    public final Field EndField;

    public PlayerMovement(int startIndex, int endIndex, Field endField) {
        this.StartIndex = startIndex;
        this.EndIndex = endIndex;
        this.PassedStart = endIndex < startIndex;
        this.EndField = endField;
    }

    @Override
    public String toString() {
        return String.format("Moving from %d to %d (%s). Passed start: %b", StartIndex, EndIndex, EndField.toString(), PassedStart);
    }
}

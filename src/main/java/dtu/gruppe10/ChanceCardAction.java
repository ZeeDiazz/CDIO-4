package dtu.gruppe10;

public class ChanceCardAction {
    public final ChanceCardEvent Event;
    public final int Value;

    public ChanceCardAction(ChanceCardEvent event, int value) {
        this.Event = event;
        this.Value = value;
    }
    public String toString() {
        return Event.toString() + " (value: " + Value + ")";
    }
}


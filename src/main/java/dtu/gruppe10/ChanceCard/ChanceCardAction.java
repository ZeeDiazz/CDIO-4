package dtu.gruppe10.ChanceCard;

public class ChanceCardAction {
    public final ChanceCardEvent Event;
    public final int Value;

    public ChanceCardAction(ChanceCardEvent event, int value) {
        this.Event = event;
        this.Value = value;
    }

    //public void MoveToLocationAction(int location) {
        //this.location = location;
    //}



    public String toString() {
        return Event.toString() + " (value: " + Value + ")";
    }

}



package dtu.gruppe10.FieldTypes;

import dtu.gruppe10.Player;

public abstract class Field {
    protected String name;
    protected boolean landedOn;

    public abstract void whenLandedOn(Player player);
}

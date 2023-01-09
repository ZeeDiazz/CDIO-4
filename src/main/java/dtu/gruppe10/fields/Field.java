package dtu.gruppe10.fields;

import dtu.gruppe10.Player;

public abstract class Field {
    public final int ID;
    public final FieldType Type;

    public Field(int id, FieldType type) {
        this.ID = id;
        this.Type = type;
    }
}

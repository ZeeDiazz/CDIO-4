package dtu.gruppe10.FieldTypes;

import java.util.ArrayList;

public class ArrayOfFields {

    private ArrayList<Field> deckOfFields;
    private Field[] fields;

    public ArrayOfFields() {
        this.deckOfFields = new ArrayList<>();
    }

    public void add(Field field) {
        this.deckOfFields.add(field);
    }

    public void addAllFields() {
        // Indlæs fra Field.csv

    }


    public void addToArray() {
        this.fields = deckOfFields.toArray(new Field[0]);
    }

}

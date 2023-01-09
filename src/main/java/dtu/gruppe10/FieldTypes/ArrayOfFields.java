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
        this.fields = new Field[this.deckOfFields.size()];
        for (int i = 0; i < this.deckOfFields.size(); i++) {
            this.fields[i] = this.deckOfFields.get(i);
        }
    }

}

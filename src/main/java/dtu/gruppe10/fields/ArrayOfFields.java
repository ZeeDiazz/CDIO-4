package dtu.gruppe10.fields;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ArrayOfFields {

    private ArrayList<Field> deckOfFields;


    public ArrayOfFields() {
        this.deckOfFields = new ArrayList<>();
    }

    public void add(Field field) {
        this.deckOfFields.add(field);

    }

    public void addAllFields() {
        // Indlæs fra Field.csv


    }

    public Field[] addToArray() {
        return deckOfFields.toArray(new Field[0]);
    }

}

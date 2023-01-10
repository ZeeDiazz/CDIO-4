package dtu.gruppe10.fields;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

    public void addAllFields() throws FileNotFoundException {
        // Indlæs fra Field.csv
        BufferedReader br = new BufferedReader(new FileReader("fields.CSV"));


    }

    public Field[] addToArray() {
        return deckOfFields.toArray(new Field[0]);
    }

}

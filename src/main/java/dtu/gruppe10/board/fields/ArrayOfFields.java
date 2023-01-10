package dtu.gruppe10.board.fields;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

    public void addAllFields() throws IOException {
        // Load the fields.CSV
        BufferedReader reader = new BufferedReader(new FileReader("fields.CSV"));
        String textLine;

        //While there is text line in CSV
        while ((textLine = reader.readLine()) != null) {
            String[] texts = textLine.split(";"); //Spilts the text if ";"
        }

    }

    public Field[] addToArray() {
        return deckOfFields.toArray(new Field[0]);
    }

}

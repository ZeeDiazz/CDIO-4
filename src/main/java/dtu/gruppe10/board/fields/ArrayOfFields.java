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
        int counter = 0;

        //While there is text line in CSV
        while ((textLine = reader.readLine()) != null) {
            String[] texts = textLine.split(";"); //Spilts the text if ";"

            if (texts[2].equals("start")) {
                this.add(new StartField(counter));
                counter++;
            } else if (texts[2].equals("street")) {
                this.add(new StreetField(counter, Integer.valueOf(texts[3]), new int[]{Integer.valueOf(texts[5]), Integer.valueOf(texts[6]), Integer.valueOf(texts[7]), Integer.valueOf(texts[8]), Integer.valueOf(texts[9]), Integer.valueOf(texts[10])}, StreetColor.RED, 0));
                counter++;
            } else if (texts[2].equals("chance")) {
                this.add(new ChanceField(counter));
                counter++;
            } else if (texts[2].equals("ferry")) {
                this.add(new StreetField(counter, Integer.valueOf(texts[3]), new int[]{Integer.valueOf(texts[5]), Integer.valueOf(texts[6]), Integer.valueOf(texts[7]), Integer.valueOf(texts[8])}, StreetColor.RED, 0));
                counter++;
            }



        }
    }

    public Field[] addToArray() {
        return deckOfFields.toArray(new Field[0]);
    }

}

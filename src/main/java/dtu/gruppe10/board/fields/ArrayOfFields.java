package dtu.gruppe10.board.fields;

import java.io.BufferedReader;
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

    public void readFieldData() throws IOException {
        // Load the fields.CSV
        BufferedReader reader = new BufferedReader(new FileReader("fields.CSV"));
        String textLine;
        int counter = 0;
        int jailCounter = 0;

        //While there is text line in CSV
        while ((textLine = reader.readLine()) != null) {
            String[] texts = textLine.split(","); //Splits the text if ","

            switch (texts[2]) {
                case "start":
                    this.add(new StartField(counter));
                    break;
                case "street":
                    this.add(new StreetField(counter, Integer.valueOf(texts[3]), new int[]{Integer.valueOf(texts[5]), Integer.valueOf(texts[6]), Integer.valueOf(texts[7]), Integer.valueOf(texts[8]), Integer.valueOf(texts[9]), Integer.valueOf(texts[10])}, StreetColor.RED, 0));
                    break;
                case "chance":
                    this.add(new ChanceField(counter));
                    break;
                case "ferry":
                    this.add(new FerryField(counter, Integer.valueOf(texts[3]), new int[]{Integer.valueOf(texts[5]), Integer.valueOf(texts[6]), Integer.valueOf(texts[7]), Integer.valueOf(texts[8])}));
                    break;
                case "tax":
                    this.add(new TaxField(counter, Integer.valueOf(texts[3])));
                    break;
                case "jail":
                    if (jailCounter == 0) {
                        this.add(new JailField(counter));
                        jailCounter++;
                    } else {
                        this.add(new GoToJailField(counter));
                    }
                    break;
            }
            counter++;
        }
    }

    public Field[] getFields() {
        return deckOfFields.toArray(new Field[0]);
    }
}

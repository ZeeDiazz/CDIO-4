package dtu.gruppe10.board.fields;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ArrayOfFields {

    private ArrayList<Field> deckOfFields;

    public ArrayOfFields() {
        this.deckOfFields = new ArrayList<>();
    }

    public void add(Field field) {
        this.deckOfFields.add(field);
    }

    public void readFieldData() throws IOException {
        // Load the fields.CSV
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/modifiedFields - fields.csv"));
        String textLine;
        int jailCounter = 0;

        //While there is text line in CSV
        while ((textLine = reader.readLine()) != null) {
            String[] parts = textLine.split(","); //Splits the text if ","

            switch (parts[1]) {
                case "start":
                    this.add(new StartField(Integer.parseInt(parts[0])));
                    break;
                case "street":
                    this.add(new StreetField(Integer.parseInt(parts[0]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), new int[]{Integer.parseInt(parts[6]), Integer.parseInt(parts[7]), Integer.parseInt(parts[8]), Integer.parseInt(parts[9]), Integer.parseInt(parts[10]), Integer.parseInt(parts[11])}, selectColor(parts[3]), Integer.parseInt(parts[12])));
                    break;
                case "chance":
                    this.add(new ChanceField(Integer.parseInt(parts[0])));
                    break;
                case "ferry":
                    this.add(new FerryField(Integer.parseInt(parts[0]), Integer.parseInt(parts[4]), new int[]{Integer.parseInt(parts[6]), Integer.parseInt(parts[7]), Integer.parseInt(parts[8]), Integer.parseInt(parts[9])}));
                    break;
                case "tax":
                    this.add(new TaxField(Integer.parseInt(parts[0]), Integer.parseInt(parts[4])));
                    break;
                case "jail":
                    if (jailCounter == 0) {
                        this.add(new JailField(Integer.parseInt(parts[0])));
                        jailCounter++;
                    } else {
                        this.add(new GoToJailField(Integer.parseInt(parts[0])));
                    }
                    break;
                case "brewery":
                    this.add(new BreweryField(Integer.parseInt(parts[0]), Integer.parseInt(parts[4]), new int[]{Integer.parseInt(parts[6]), Integer.parseInt(parts[7])}));
                    break;
                case "refugee":
                    this.add(new FreeParkingField(Integer.parseInt(parts[0])));
                    break;
            }
        }
    }

    public Field[] getFields() {
        return deckOfFields.toArray(new Field[0]);
    }


    public StreetColor selectColor(String color) {
        switch (color) {
            case "orange":
                return StreetColor.ORANGE;
            case "red":
                return StreetColor.RED;
            case "green":
                return StreetColor.GREEN;
            case "yellow":
                return StreetColor.YELLOW;
            case "grey":
                return StreetColor.GREY;
            case "purple":
                return StreetColor.PURPLE;
            case "cyan":
                return StreetColor.CYAN;
            case "pink":
                return StreetColor.PINK;
            case "white":
                return StreetColor.WHITE;
            case "black":
                return StreetColor.BLACK;
        }

        return StreetColor.BLACK;
    }
}
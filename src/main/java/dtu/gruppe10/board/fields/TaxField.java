package dtu.gruppe10.board.fields;

public class TaxField extends Field {
    public final int Price;

    public TaxField(int id, int price) {
        super(id, FieldType.TAX);

        this.Price = price;
    }
}

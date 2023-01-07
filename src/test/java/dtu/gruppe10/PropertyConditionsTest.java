package dtu.gruppe10;

import dtu.gruppe10.FieldTypes.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PropertyConditionsTest {

    @Before
    public void initialize(){
        Property field = new Property("Rødovrevej", 1200, PropertyColor.GREEN, 50, 250);
    }

    @Test
    public void propertyDoesNotHaveOwnerAtFirst(){
        Property field = new Property("Rødovrevej", 1200, PropertyColor.GREEN, 50, 250);
        assertEquals(null, field.owner);
    }

    @Test
    public void rentIsEqualRent0(){
        int rent0 = 583;
        Property field = new Property("Rødovrevej", 1200, PropertyColor.GREEN, rent0, 250);
        assertEquals(rent0, field.getCurrentRent());
    }








}

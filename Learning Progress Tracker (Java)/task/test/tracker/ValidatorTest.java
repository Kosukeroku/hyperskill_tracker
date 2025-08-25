package tracker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {

    @Test
    public void testValidFirstNames() {
        assertTrue(Validator.isValidFirstName("John"));
        assertTrue(Validator.isValidFirstName("Jean-Claude"));
        assertTrue(Validator.isValidFirstName("O'Bryan"));
    }

    @Test
    public void testInvalidFirstNames() {
        assertFalse(Validator.isValidFirstName(""));
        assertFalse(Validator.isValidFirstName(" "));
        assertFalse(Validator.isValidFirstName("*"));
        assertFalse(Validator.isValidFirstName("Juli ette"));
        assertFalse(Validator.isValidFirstName("Jиhn"));
        assertFalse(Validator.isValidFirstName("1Claude"));
        assertFalse(Validator.isValidFirstName("Jean-"));
        assertFalse(Validator.isValidFirstName("'Jean"));
    }


    @Test
    public void testValidLastNames() {
        assertTrue(Validator.isValidLastName("Scott"));
        assertTrue(Validator.isValidLastName("Burk-Valley"));
        assertTrue(Validator.isValidLastName("De la Crua"));
        assertTrue(Validator.isValidLastName("La'Fleur"));
    }

    @Test
    public void testInvalidLastNames() {
        assertFalse(Validator.isValidLastName(""));
        assertFalse(Validator.isValidLastName(" "));
        assertFalse(Validator.isValidLastName("Smith!"));
        assertFalse(Validator.isValidLastName("'Wilson"));
        assertFalse(Validator.isValidLastName("Jason123"));
        assertFalse(Validator.isValidLastName("Mirk'"));
        assertFalse(Validator.isValidLastName("Jиn"));
    }

    @Test
    public void testValidEmail() {
        assertTrue(Validator.isValidEmail("john@gmail.com"));
        assertTrue(Validator.isValidEmail("123@g.c"));
        assertTrue(Validator.isValidEmail("mark.lee_teacher22@gmail.co.uk"));
    }

    @Test
    public void testInvalidEmail() {
        assertFalse(Validator.isValidEmail(""));
        assertFalse(Validator.isValidEmail(" "));
        assertFalse(Validator.isValidEmail("john@"));
        assertFalse(Validator.isValidEmail("john@gmail"));
        assertFalse(Validator.isValidEmail("@mail.com"));
        assertFalse(Validator.isValidEmail("johngmail.com"));
        assertFalse(Validator.isValidEmail("jиhn@gmail.com"));
        assertFalse(Validator.isValidEmail("jo hn@gmail.com"));
    }
}

package tracker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    @Test
    public void testValidStudentCreation() {

        String name = "Jack";
        String lastName = "Smith";
        String email = "jsmith@gmail.com";

        Student student = new Student(name, lastName, email);
        assertEquals(name, student.getFirstName());
        assertEquals(lastName, student.getLastName());
        assertEquals(email, student.getEmail());
    }

    @Test
    public void testInvalidFirstNameThrowsException() {
        String name = "Jack1";
        String lastName = "Smith";
        String email = "jsmith@gmail.com";

        IllegalArgumentException firstNameException = assertThrows(IllegalArgumentException.class, () -> new Student(name, lastName, email));
        assertEquals("Incorrect first name", firstNameException.getMessage());
    }

    @Test
    public void testInvalidLastNameThrowsException() {
        String name = "Jack";
        String lastName = "Smыth";
        String email = "jsmith@gmail.com";

        IllegalArgumentException lastNameException = assertThrows(IllegalArgumentException.class, () -> new Student(name, lastName, email));
        assertEquals("Incorrect last name", lastNameException.getMessage());
    }

    @Test
    public void testInvalidEmailThrowsException() {
        String name = "Jack";
        String lastName = "Smith";
        String email = "jsmith";

        IllegalArgumentException emailException = assertThrows(IllegalArgumentException.class, () -> new Student(name, lastName, email));
        assertEquals("Incorrect email", emailException.getMessage());
    }

    @Test
    public void testInvalidCredentialsThrowsException() {
        String name = "Jack1";
        String lastName = "Smыth";
        String email = "jsmith";

        IllegalArgumentException credentialsException = assertThrows(IllegalArgumentException.class, () -> new Student(name, lastName, email));
        assertEquals("Incorrect credentials", credentialsException.getMessage());
    }

    @Test
    public void testId() {
        Student student1 = new Student("Jack", "Smith", "jacksmith@gmail.com");
        Student student2 = new Student("Jim", "Smith", "jimsmith@gmail.com");

        assertEquals(10000, student1.getId());
        assertEquals(10001, student2.getId());
    }

}

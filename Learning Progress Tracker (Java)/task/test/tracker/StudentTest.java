package tracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    @BeforeEach
    public void setUp() {
        try {
            java.lang.reflect.Field nextIdField = Student.class.getDeclaredField("nextId");
            nextIdField.setAccessible(true);
            nextIdField.set(null, 10000);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

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
        assertEquals("Incorrect first name.", firstNameException.getMessage());
    }

    @Test
    public void testInvalidLastNameThrowsException() {
        String name = "Jack";
        String lastName = "Smыth";
        String email = "jsmith@gmail.com";

        IllegalArgumentException lastNameException = assertThrows(IllegalArgumentException.class, () -> new Student(name, lastName, email));
        assertEquals("Incorrect last name.", lastNameException.getMessage());
    }

    @Test
    public void testInvalidEmailThrowsException() {
        String name = "Jack";
        String lastName = "Smith";
        String email = "jsmith";

        IllegalArgumentException emailException = assertThrows(IllegalArgumentException.class, () -> new Student(name, lastName, email));
        assertEquals("Incorrect email.", emailException.getMessage());
    }

    @Test
    public void testInvalidCredentialsThrowsException() {
        String name = "Jack1";
        String lastName = "Smыth";
        String email = "jsmith";

        IllegalArgumentException credentialsException = assertThrows(IllegalArgumentException.class, () -> new Student(name, lastName, email));
        assertEquals("Incorrect credentials.", credentialsException.getMessage());
    }

    @Test
    public void testId() {
        Student student1 = new Student("Jack", "Smith", "jacksmith@gmail.com");
        Student student2 = new Student("Jim", "Smith", "jimsmith@gmail.com");

        assertEquals(10000, student1.getId());
        assertEquals(10001, student2.getId());
    }

    @Test
    public void testZeroPointsAtCreation() {
        Student student = new Student("Jack", "Smith", "jacksmith@gmail.com");
        assertEquals(0, student.getPoints().get(Course.JAVA));
        assertEquals(0, student.getPoints().get(Course.DSA));
        assertEquals(0, student.getPoints().get(Course.DATABASES));
        assertEquals(0, student.getPoints().get(Course.SPRING));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            " ",
            "-1 2 3 4",
            "5 6 7",
            "8 9 10 d",
            "11 12 13 *"
    })
    public void testInvalidPointsAdding(String points) {
        Student student = new Student("Jack", "Smith", "jacksmith@gmail.com");
        IllegalArgumentException incorrectPointsFormat = assertThrows(IllegalArgumentException.class, () -> student.addPoints(points));
        assertEquals("Incorrect points format.", incorrectPointsFormat.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1 2 3 4",
            "5 6 7 8",
            "9 10 11 12",
    })
    public void testValidPointsAdding(String points) {
        Student student = new Student("Jack", "Smith", "jacksmith@gmail.com");
        student.addPoints(points);
        assertEquals(4, student.getPoints().size());

        String[] pointsArray = points.split("\\s+");
        int javaPoints = Integer.parseInt(pointsArray[0]);
        int dsaPoints = Integer.parseInt(pointsArray[1]);
        int databasesPoints = Integer.parseInt(pointsArray[2]);
        int springPoints = Integer.parseInt(pointsArray[3]);

        assertEquals(javaPoints, student.getPoints().get(Course.JAVA));
        assertEquals(dsaPoints, student.getPoints().get(Course.DSA));
        assertEquals(databasesPoints, student.getPoints().get(Course.DATABASES));
        assertEquals(springPoints, student.getPoints().get(Course.SPRING));
    }

    @Test
    public void testCumulativePointsAdding() {
        Student student = new Student("Jack", "Smith", "jacksmith@gmail.com");
        student.addPoints("1 2 3 4");
        student.addPoints("1 2 3 4");

        assertEquals(2, student.getPoints().get(Course.JAVA));
        assertEquals(4, student.getPoints().get(Course.DSA));
        assertEquals(6, student.getPoints().get(Course.DATABASES));
        assertEquals(8, student.getPoints().get(Course.SPRING));
    }

    @Test
    public void testToString() {
        Student student = new Student("Jack", "Smith", "jacksmith@gmail.com");
        student.addPoints("1 2 3 4");
        assertEquals("10000 points: Java=1; DSA=2; Databases=3; Spring=4;", student.toString());
    }
}

package tracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class StudentBookTest {
    private StudentBook studentBook;

    @BeforeEach
    public void setUp() {
        studentBook = new StudentBook();
    }

    @Test
    public void testStudentAdding() {
        StudentBook studentBook = new StudentBook();
        assertEquals(0, studentBook.getStudentCount());

        Student student1 = new Student("Jack", "Copper", "jack@mail.com");
        studentBook.addStudent(student1);
        assertEquals(1, studentBook.getStudentCount());

        Student student2 = new Student("Juliette", "Ewans", "julie@mail.com");
        studentBook.addStudent(student2);
        assertEquals(2, studentBook.getStudentCount());
    }

    @Test
    public void testEmailUniquenessValidation() {
        Student student = new Student("Jack", "Copper", "jack@mail.com");
        studentBook.addStudent(student);

        assertFalse(studentBook.isEmailUnique("jack@mail.com"));
        assertTrue(studentBook.isEmailUnique("julie@mail.com"));
    }

    @Test
    public void testStudentsPrinting() {
        Student student1 = new Student("Jack", "Copper", "jack@mail.com");
        Student student2 = new Student("Juliette", "Ewans", "julie@mail.com");

        studentBook.addStudent(student1);
        studentBook.addStudent(student2);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        System.setOut(new PrintStream(baos));

        studentBook.printStudents();

        String output = baos.toString().replace("\r", "");
        String expectedOutput = "Students: \n" +
                student1.getId() + "\n" +
                student2.getId() + "\n";

        assertEquals(expectedOutput, output);

        System.setOut(System.out);
    }

    @Test
    public void testSuccessfulPointAddition() {
        Student student = new Student("Jack", "Sullivan", "jack@js.com");
        studentBook.addStudent(student);

        String studentAndPoints = student.getId() + " 1 2 3 4";
        studentBook.addPointsToStudent(studentAndPoints);

        assertEquals(1, student.getPoints().get(Course.JAVA));
        assertEquals(2, student.getPoints().get(Course.DSA));
        assertEquals(3, student.getPoints().get(Course.DATABASES));
        assertEquals(4, student.getPoints().get(Course.SPRING));

    }

    @Test
    public void testNonExistantStudentPointAddition() {
        Student student1 = new Student("Jack", "Sullivan", "jack@js.com");
        Student student2 = new Student("Juliette", "Milton", "julie@mail.com");
        studentBook.addStudent(student1);

        IllegalArgumentException studentNotFound = assertThrows(IllegalArgumentException.class, () -> {
            studentBook.addPointsToStudent(student2.getId() + " 1 2 3 4");
        });

        assertEquals("No student found for id=" + student2.getId(), studentNotFound.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "1 1", "1 1 1", "-1 1 1 1"})
    public void testInvalidFormatSPointAddition(String input) {
        Student student = new Student("Jack", "Sullivan", "jack@js.com");
        studentBook.addStudent(student);

        IllegalArgumentException wrongFormat = assertThrows(IllegalArgumentException.class, () -> {
            studentBook.addPointsToStudent(student.getId() + " " + input);
        });

        assertEquals("Incorrect points format.", wrongFormat.getMessage());
    }
}

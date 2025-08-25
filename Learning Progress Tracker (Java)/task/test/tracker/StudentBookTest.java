package tracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}

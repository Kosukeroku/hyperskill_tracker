package tracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class UserInterfaceTest {
    private UserInterface ui;
    private StudentBook studentBook;

    @BeforeEach
    public void setUp() {
        studentBook = new StudentBook();
        ui = new UserInterface(new Scanner(""), studentBook);
    }

    @Test
    public void testEmailUniquenessCheck() {
        String student1 = "Jack Wilson jack@mail.com";
        String student2 = "John Wilson john@mail.com";
        String student3 = "Jack Hilton jack@mail.com";

        Student jackW = ui.getStudent(student1);
        studentBook.addStudent(jackW);

        assertDoesNotThrow(() -> {
            Student jackH = ui.getStudent(student2);
        });

        IllegalArgumentException nonUniqueEmailException = assertThrows(IllegalArgumentException.class, () -> {
            Student jackJ = ui.getStudent(student3);
        });

        assertEquals("This email is already taken.", nonUniqueEmailException.getMessage());

    }

    @Test
    public void testGetStudentParsing() {
        Student student1 = ui.getStudent("John Doe john@example.com");
        assertEquals("John", student1.getFirstName());
        assertEquals("Doe", student1.getLastName());
        assertEquals("john@example.com", student1.getEmail());

        Student student2 = ui.getStudent("Jean-Claude van Helsing j@e.c");
        assertEquals("Jean-Claude", student2.getFirstName());
        assertEquals("van Helsing", student2.getLastName());
        assertEquals("j@e.c", student2.getEmail());
    }

}

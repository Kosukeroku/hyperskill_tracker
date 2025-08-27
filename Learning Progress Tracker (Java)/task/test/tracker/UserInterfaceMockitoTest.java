package tracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserInterfaceMockitoTest {

    @Mock
    private StudentBook mockStudentBook;

    @Mock
    private Scanner mockScanner;

    private UserInterface ui;

    @BeforeEach
    public void setUp() {
        ui = new UserInterface(mockScanner, mockStudentBook);
    }

    @Test
    public void testImmediatelyQuitting() {
        when(mockScanner.nextLine()).thenReturn("exit");
        assertDoesNotThrow(() -> ui.start());
        verifyNoInteractions(mockStudentBook);
    }

    @Test
    public void testAddingThreeStudents() {
        when(mockScanner.nextLine())
                .thenReturn("add students")
                .thenReturn("Jack Wilson wjack@mail.com")
                .thenReturn("John Wilson wjohn@mail.com")
                .thenReturn("Julie Wilson wjulie@mail.com")
                .thenReturn("back")
                .thenReturn("exit");


        when(mockStudentBook.isEmailUnique(eq("wjack@mail.com"))).thenReturn(true);
        when(mockStudentBook.isEmailUnique(eq("wjohn@mail.com"))).thenReturn(true);
        when(mockStudentBook.isEmailUnique(eq("wjulie@mail.com"))).thenReturn(true);
        assertDoesNotThrow(() -> ui.start());
        verify(mockStudentBook, times(3)).addStudent(any(Student.class));
    }

    @Test
    public void testAddPointsCommandWorkflow() {
        when(mockScanner.nextLine())
                .thenReturn("add points")
                .thenReturn("10000 1 1 1 1")
                .thenReturn("10001 2 2 2 2")
                .thenReturn("back")
                .thenReturn("exit"); // Exit after adding points


        UserInterface ui = new UserInterface(mockScanner, mockStudentBook);

        assertDoesNotThrow(() -> ui.start());

        verify(mockStudentBook, times(1)).addPointsToStudent(eq("10000 1 1 1 1"));
        verify(mockStudentBook, times(1)).addPointsToStudent(eq("10001 2 2 2 2"));
    }

    @Test
    public void testListPointsWithStudentsCommandWorkflow() {
        when(mockScanner.nextLine())
                .thenReturn("list")
                .thenReturn("exit");

        when(mockStudentBook.getStudentCount()).thenReturn(2);
        UserInterface ui = new UserInterface(mockScanner, mockStudentBook);
        assertDoesNotThrow(() -> ui.start());

        verify(mockStudentBook, times(1)).getStudentCount();
        verify(mockStudentBook, times(1)).printStudents();
    }

    @Test
    public void testListPointsNoStudentsCommandWorkflow() {
        when(mockScanner.nextLine())
                .thenReturn("list")
                .thenReturn("exit");

        when(mockStudentBook.getStudentCount()).thenReturn(0);
        UserInterface ui = new UserInterface(mockScanner, mockStudentBook);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalStream = System.out;
        System.setOut(new PrintStream(baos));

        try {
            assertDoesNotThrow(() -> ui.start());

            verify(mockStudentBook, times(1)).getStudentCount();
            verify(mockStudentBook, never()).printStudents();

            String output = baos.toString();
            assertTrue(output.contains("No students found"), "Should print 'No students found' message.");
        } finally {
            System.setOut(originalStream);
        }
    }

    @Test
    public void testFindCommandValidId() {

        when(mockScanner.nextLine())
                .thenReturn("find")
                .thenReturn("10000")
                .thenReturn("back")
                .thenReturn("exit");

        Student mockStudent = mock(Student.class);
        when(mockStudent.toString()).thenReturn("10000 points: Java=5; DSA=10; Databases=15; Spring=20;");
        when(mockStudentBook.getStudentById(eq(10000))).thenReturn(mockStudent);
        when(mockStudentBook.getStudentCount()).thenReturn(1);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            UserInterface ui = new UserInterface(mockScanner, mockStudentBook);

            assertDoesNotThrow(() -> ui.start());

            verify(mockStudentBook, times(1)).getStudentById(eq(10000));
            verify(mockStudentBook, times(1)).getStudentCount(); // Called during 'back'

            // Verify the student's toString output was printed
            String output = outContent.toString();
            assertTrue(output.contains("10000 points: Java=5; DSA=10; Databases=15; Spring=20;"), "Should print student details.");

        } finally {
            // Restore original System.out
            System.setOut(originalOut);
        }
    }

    @Test
    public void testFindCommandInvalidId() {

        when(mockScanner.nextLine())
                .thenReturn("find")
                .thenReturn("99999")
                .thenReturn("back")
                .thenReturn("exit");


        when(mockStudentBook.getStudentById(eq(99999))).thenThrow(new IllegalArgumentException("No student is found for id=99999"));

        when(mockStudentBook.getStudentCount()).thenReturn(0);


        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            UserInterface ui = new UserInterface(mockScanner, mockStudentBook);

            assertDoesNotThrow(() -> ui.start());

            verify(mockStudentBook, times(1)).getStudentById(eq(99999));
            verify(mockStudentBook, times(1)).getStudentCount(); // Called during 'back'

            String output = outContent.toString();
            assertTrue(output.contains("No student is found for id=99999"), "Should print error message for invalid ID.");

        } finally {
            System.setOut(originalOut);
        }
    }
}
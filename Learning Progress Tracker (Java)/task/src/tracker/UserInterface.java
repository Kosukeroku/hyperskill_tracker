package tracker;

import java.io.IOException;
import java.util.Scanner;

public class UserInterface {
    private static final String EXIT_COMMAND = "exit";
    private static final String ADD_COMMAND = "add students";
    private static final String BACK_COMMAND = "back";
    private static final String LIST_COMMAND = "list";

    private static final String GREETING_MESSAGE = "Learning Progress Tracker";
    private static final String NO_INPUT_MESSAGE = "No input.";
    private static final String BYE_MESSAGE = "Bye!";
    private static final String UNKNOWN_COMMAND_MESSAGE = "Error: unknown command!";
    private static final String ADDING_INFO_MESSAGE = "Enter student credentials or 'back' to return: ";
    private static final String ADDING_SUCCESS_MESSAGE = "The student has been added.";
    private static final String ADDING_RESULT_MESSAGE = "Total: %s student(s) added.\n";
    private static final String EXIT_HINT_MESSAGE = "Enter 'exit' to exit the program.";
    private static final String EMAIL_TAKEN_MESSAGE = "This email is already taken.";
    private static final String EMPTY_BOOK_MESSAGE = "No students found.";

    private final Scanner scanner;
    private StudentBook studentBook;

    public UserInterface(Scanner scanner, StudentBook studentBook) {
        this.scanner = scanner;
        this.studentBook = studentBook;
    }

    public void start() throws IOException {
        System.out.println(GREETING_MESSAGE);

        while (true) {
            String command = askCommand().trim().toLowerCase();

            if (command.isBlank()) {
                System.out.println(NO_INPUT_MESSAGE);
                continue;
            }

            switch (command) {
                case EXIT_COMMAND -> {
                    System.out.println(BYE_MESSAGE);
                    return;
                }
                case BACK_COMMAND -> {
                    System.out.println(EXIT_HINT_MESSAGE);
                }
                case ADD_COMMAND -> {
                    handleAddStudents();
                }
                case LIST_COMMAND -> {
                    handleListStudents();
                }
                default -> {
                    System.out.println(UNKNOWN_COMMAND_MESSAGE);
                }
            }
        }
    }

    private void handleAddStudents() {
        System.out.println(ADDING_INFO_MESSAGE);

        while (true) {
            String input = askCommand();
            if (input.equalsIgnoreCase(BACK_COMMAND)) {
                System.out.printf(ADDING_RESULT_MESSAGE, studentBook.getStudentCount());
                break;
            }
            try {
                Student newStudent = getStudent(input);
                studentBook.addStudent(newStudent);
                System.out.println(ADDING_SUCCESS_MESSAGE);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void handleListStudents() {
        if (studentBook.getStudentCount() == 0) {
            System.out.println(EMPTY_BOOK_MESSAGE);
        } else {
            studentBook.printStudents();
        }
    }

    public String askCommand() {
        return scanner.nextLine();
    }

    public Student getStudent(String input) throws IllegalArgumentException {
        String[] inputArray = input.trim().split("\\s+");

        String firstName = inputArray[0];
        String email = inputArray[inputArray.length - 1];

        if (!studentBook.isEmailUnique(email)) {
            throw new IllegalArgumentException(EMAIL_TAKEN_MESSAGE);
        }
        StringBuilder lastNameBuilder = new StringBuilder();
        for (int i = 1; i < inputArray.length - 1; i++) {
            if (!lastNameBuilder.isEmpty()) {
                lastNameBuilder.append(" ");
            }
            lastNameBuilder.append(inputArray[i]);
        }
        String lastName = lastNameBuilder.toString();


        return new Student(firstName, lastName, email);


    }
}


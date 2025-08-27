package tracker;

import java.util.ArrayList;
import java.util.List;

public class StudentBook {
    private List<Student> students;
    private static final String STUDENTS_HEADER = "Students: ";
    private static final String INCORRECT_POINTS_FORMAT_MESSAGE = "Incorrect points format.";
    private static final String NO_STUDENT_FOUND_MESSAGE = "No student found for id=%s";
    private static final String POINTS_SUCCESSFUL_UPDATE_MESSAGE = "Points updated.";


    public StudentBook() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public int getStudentCount() {
        return students.size();
    }

    public boolean isEmailUnique(String email) {
        return students.stream()
                .map(Student::getEmail)
                .noneMatch(s -> s.equalsIgnoreCase(email));
    }

    public void printStudents() {
        System.out.println(STUDENTS_HEADER);

        for (Student student : students) {
            System.out.print(student.getId());
            boolean hasAnyPoints = student.getPoints().values().stream()
                    .anyMatch(n -> n != 0);
            if (hasAnyPoints) {
                student.getPoints().values().stream()
                        .map(points -> " " + points)
                        .forEach(System.out::print);
            }
            System.out.println();
        }
    }

    public Student getStudentById(int id) throws IllegalArgumentException {
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format(NO_STUDENT_FOUND_MESSAGE, id)));
    }

    public void addPointsToStudent(String studentAndPoints) throws IllegalArgumentException {
        int index = studentAndPoints.trim().indexOf(' ');
        if (index == -1) {
            throw new IllegalArgumentException(INCORRECT_POINTS_FORMAT_MESSAGE);
        }
        String idString = studentAndPoints.substring(0, index);
        try {
            int id = Integer.parseInt(idString);
            Student student = getStudentById(id);
            student.addPoints(studentAndPoints.trim().substring(index + 1));
            System.out.println(POINTS_SUCCESSFUL_UPDATE_MESSAGE);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format(NO_STUDENT_FOUND_MESSAGE, idString));
        }


    }

}

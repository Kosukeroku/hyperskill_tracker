package tracker;

import java.util.ArrayList;
import java.util.List;

public class StudentBook {
    private List<Student> students;
    private static final String STUDENTS_HEADER = "Students: ";

    public StudentBook() {
        students = new ArrayList<Student>();
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
        students.stream()
                .map(Student::getId)
                .forEach(System.out::println);
    }
}

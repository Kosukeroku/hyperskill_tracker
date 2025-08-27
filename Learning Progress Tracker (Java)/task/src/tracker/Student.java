package tracker;

import java.util.EnumMap;
import java.util.Map;

public class Student {
    private String firstName;
    private String lastName;
    private String email;
    private int id;
    private EnumMap<Course, Integer> points;
    private static int nextId = 10_000;

    private static final String POINTS_REGEX = "(1000|[1-9][0-9]{0,2}|0)";

    private static final String INCORRECT_CREDENTIALS_MESSAGE = "Incorrect credentials.";
    private static final String INCORRECT_FIRST_NAME_MESSAGE = "Incorrect first name.";
    private static final String INCORRECT_LAST_NAME_MESSAGE = "Incorrect last name.";
    private static final String INCORRECT_EMAIL_MESSAGE = "Incorrect email.";
    private static final String INCORRECT_POINTS_FORMAT_MESSAGE = "Incorrect points format.";


    public Student(String firstName, String lastName, String email) throws IllegalArgumentException {
        boolean firstNameValid = Validator.isValidFirstName(firstName);
        boolean lastNameValid = Validator.isValidLastName(lastName);
        boolean emailValid = Validator.isValidEmail(email);

        int validCount = (firstNameValid ? 1 : 0) + (lastNameValid ? 1 : 0) + (emailValid ? 1 : 0);

        if (validCount <= 1) {
            throw new IllegalArgumentException(INCORRECT_CREDENTIALS_MESSAGE);
        }

        if (!firstNameValid) {
            throw new IllegalArgumentException(INCORRECT_FIRST_NAME_MESSAGE);
        }

        if (!lastNameValid) {
            throw new IllegalArgumentException(INCORRECT_LAST_NAME_MESSAGE);
        }

        if (!emailValid) {
            throw new IllegalArgumentException(INCORRECT_EMAIL_MESSAGE);
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = nextId++;
        this.points = new EnumMap<>(Course.class);
        for (Course course : Course.values()) {
            points.put(course, 0);
        }
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public int getId() {
        return id;
    }
    public EnumMap<Course, Integer> getPoints() {
        return points;
    }

    public void addPoints(String pointsString) throws IllegalArgumentException {
        String[] pointsArray = pointsString.trim().split("\\s+");
        if (pointsArray.length != 4) {
            throw new IllegalArgumentException(INCORRECT_POINTS_FORMAT_MESSAGE);
        }

        for (String p : pointsArray) {
            if (!p.matches(POINTS_REGEX)) {
                throw new IllegalArgumentException(INCORRECT_POINTS_FORMAT_MESSAGE);
            }
        }

        for (int i = 0; i < points.size(); i++) {
            int pointsToAdd = Integer.parseInt(pointsArray[i]);
            Course course = Course.values()[i];
            points.compute(course, (k, currentPoints) -> currentPoints + pointsToAdd);
        }
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        info.append(this.id);
        info.append(" points:");
        for (Map.Entry<Course,Integer> p : points.entrySet()) {
            info.append(" ").append(p.getKey().getName()).append("=").append(p.getValue()).append(";");
        }
        return info.toString();
    }
}

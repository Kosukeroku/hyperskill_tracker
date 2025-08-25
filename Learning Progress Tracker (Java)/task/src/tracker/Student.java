package tracker;

public class Student {
    private String firstName;
    private String lastName;
    private String email;
    private int id;
    private static int nextId = 10_000;

    private static final String INCORRECT_CREDENTIALS_MESSAGE = "Incorrect credentials";
    private static final String INCORRECT_FIRST_NAME_MESSAGE = "Incorrect first name";
    private static final String INCORRECT_LAST_NAME_MESSAGE = "Incorrect last name";
    private static final String INCORRECT_EMAIL_MESSAGE = "Incorrect email";

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
}

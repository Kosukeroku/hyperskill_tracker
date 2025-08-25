package tracker;

public class Validator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z0-9]+$";
    private static final String FIRST_NAME_REGEX = "^[A-Za-z]([A-Za-z]|[-'](?![-']))*[A-Za-z]$|^[A-Za-z]{2,}$";
    private static final String LAST_NAME_REGEX = "^[A-Za-z]([A-Za-z ]|[-](?![-'])|['-](?![ '-]))*[A-Za-z]$|^[A-Za-z]{2,}$";

    public static boolean isValidEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static boolean isValidFirstName(String firstName) {
        return firstName.matches(FIRST_NAME_REGEX);
    }

    public static boolean isValidLastName(String lastName) {
        return lastName.matches(LAST_NAME_REGEX);
    }
}

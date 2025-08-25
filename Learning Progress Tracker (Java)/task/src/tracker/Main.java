package tracker;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        StudentBook studentBook = new StudentBook();
        UserInterface ui = new UserInterface(scanner, studentBook);

        ui.start();

    }
}

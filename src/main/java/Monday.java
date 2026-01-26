import java.util.Scanner;

public class Monday {
    public static void main(String[] args) {
        // Grumpy greeting
        System.out.println("Hello! I'm MONDAY");
        System.out.println("What can I do for you?");

        // Command loop
        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;

        while (!isExit) {
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("Bye. Hope to see you again soon!");
                isExit = true;
            }
        }

        scanner.close();
    }
}

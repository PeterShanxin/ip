package monday;

import java.util.Scanner;

/**
 * Monday is a grumpy chatbot that reluctantly helps users manage tasks.
 * Named after everyone's least favorite day of the week, Monday has a
 * sarcastic personality but gets the job done.
 */
public class Monday {
    private static final String LINE = "____________________________________________________________";

    /**
     * Entry point for the Monday chatbot application.
     * Greets the user, processes commands, and exits when requested.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Grumpy greeting
        System.out.println(LINE);
        System.out.println("Ugh. I'm MONDAY. Yes, that Monday.");
        System.out.println("What do you want?");
        System.out.println(LINE);

        // Command loop
        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;

        while (!isExit) {
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("bye")) {
                System.out.println(LINE);
                System.out.println("Finally, you're leaving. Don't come back too soon.");
                System.out.println(LINE);
                isExit = true;
            } else {
                System.out.println(LINE);
                System.out.println(userInput);
                System.out.println(LINE);
            }
        }

        scanner.close();
    }
}

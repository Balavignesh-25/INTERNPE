import java.util.Scanner;

public class GuessNumber {
    public static void main(String[] args) {
        Scanner g = new Scanner(System.in);
        boolean play = true;

        System.out.println();
        System.out.println("Welcome to the Guess a Number Game!");
        System.out.println();

        while (play) {
            System.out.println("Select Difficulty Level:");
            System.out.println("1. Easy (1-100, Unlimited Attempts)");
            System.out.println("2. Medium (1-150, 15 Attempts)");
            System.out.println("3. Hard (1-200, 7 Attempts)");
            System.out.println();
            System.out.println("Enter your choice (1/2/3): ");

            int range = 0, maxAttempts = 0;
            int difficulty = g.nextInt();

            switch (difficulty) {
                case 1:
                    range = 100;
                    maxAttempts = Integer.MAX_VALUE; 
                    break;
                case 2:
                    range = 150;
                    maxAttempts = 15;
                    break;
                case 3:
                    range = 200;
                    maxAttempts = 7;
                    break;
                default:
                    System.out.println();
                    System.out.println("Invalid Choice! Defaulting to Medium.");
                    range = 150;
                    maxAttempts = 15;
                    break;
            }

            int targetNumber = (int) (Math.random() * range) + 1;
            int attempts = 0;
            boolean guessedCorrectly = false;

            System.out.println();
            System.out.println("I have chosen a number between 1 and " + range + ".");
            System.out.println("You have " + (maxAttempts == Integer.MAX_VALUE ? "unlimited" : maxAttempts) + " attempts. Good luck! ");

            while (attempts < maxAttempts) {
                System.out.println();
                System.out.print("Enter your guess: ");

                if (!g.hasNextInt()) {
                    System.out.println();
                    System.out.println("Please enter a valid number!");
                    g.next(); 
                    continue;
                }

                int guess = g.nextInt();
                attempts++;

                if (guess == targetNumber) {
                    System.out.println();
                    System.out.println(" Congratulations! You guessed the number in " + attempts + " attempts.");
                    guessedCorrectly = true;
                    break;
                } else if (guess < targetNumber) {
                    System.out.println();
                    System.out.println("Too low!");
                } else {
                    System.out.println();
                    System.out.println("Too high!");
                }

                if (attempts == 5 && !guessedCorrectly) {
                    if (targetNumber % 2 == 0) {
                        System.out.println();
                        System.out.println("Hint: The number is even.");
                    } else {
                        System.out.println();
                        System.out.println("Hint: The number is odd.");
                    }
                }

                if (maxAttempts != Integer.MAX_VALUE && attempts < maxAttempts) {
                    System.out.println("Attempts remaining: " + (maxAttempts - attempts));
                }
            }

            if (!guessedCorrectly) {
                System.out.println();
                System.out.println(" You've run out of attempts! The number was " + targetNumber + ".");
            }

            System.out.println();
            System.out.print("Do you want to play again? (Yes / No): ");
            String response = g.next();
            play = response.equalsIgnoreCase("yes");
        }

        System.out.println();
        System.out.println("Thank you for playing! Goodbye!");
        g.close();
    }
}

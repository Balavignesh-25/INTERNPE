import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println();
        System.out.println("Welcome to the Ultimate Rock-Paper-Scissors Game!");
        System.out.println("Can you beat the Computer? Let's Find Out!");
        System.out.println();

        boolean playAgain;
        do {
            int userScore = 0, computerScore = 0;
            int userStreak = 0, computerStreak = 0;

            System.out.print("Enter the Number of Rounds you want to Play: ");
            int totalRounds = scanner.nextInt();
            scanner.nextLine();

            System.out.println("\nLet the Game Begin!");

            for (int round = 1; round <= totalRounds; round++) {
                System.out.println("\nRound " + round + ":");
                System.out.print("Choose Rock, Paper, or Scissors: ");
                String userChoice = scanner.nextLine().trim();

                if (!userChoice.equalsIgnoreCase("Rock") &&
                    !userChoice.equalsIgnoreCase("Paper") &&
                    !userChoice.equalsIgnoreCase("Scissors")) {
                    System.out.println("Invalid Input! Please choose Rock, Paper, or Scissors.");
                    round--;
                    continue;
                }

                String[] choices = {"Rock", "Paper", "Scissors"};
                String computerChoice = choices[random.nextInt(3)];
                System.out.println("Computer Chose: " + computerChoice);

                if (userChoice.equalsIgnoreCase(computerChoice)) {
                    System.out.println("It's a Tie! Great Minds Think Alike.");
                    userStreak = 0;
                    computerStreak = 0;
                } else if (
                    (userChoice.equalsIgnoreCase("Rock") && computerChoice.equals("Scissors")) ||
                    (userChoice.equalsIgnoreCase("Paper") && computerChoice.equals("Rock")) ||
                    (userChoice.equalsIgnoreCase("Scissors") && computerChoice.equals("Paper"))
                ) {
                    System.out.println("You WIN this Round! " + userChoice + " beats " + computerChoice + ".");
                    userScore++;
                    userStreak++;
                    computerStreak = 0;
                } else {
                    System.out.println("You LOSE this Round. " + computerChoice + " beats " + userChoice + ".");
                    computerScore++;
                    computerStreak++;
                    userStreak = 0;
                }

                if (userStreak >= 3) {
                    System.out.println("You're on a Winning Streak ! Keep it up!");
                } else if (computerStreak >= 3) {
                    System.out.println("The Computer is on Fire! Can you Turn it around?");
                }

                System.out.println("Score: You " + userScore + " - " + computerScore + " Computer");
            }

            System.out.println("\n--- Match Results ---");
            if (userScore > computerScore) {
                System.out.println("Congratulations! You WON the match with a score of " + userScore + " - " + computerScore + ".");
            } else if (computerScore > userScore) {
                System.out.println("The computer wins the match with a score of " + computerScore + " - " + userScore + ".");
            } else {
                System.out.println("It's a Tie! Both Scored " + userScore + ".");
            }

            System.out.print("\nDo you want to Play Another Match? (yes/no): ");
            String response = scanner.nextLine().trim();
            playAgain = response.equalsIgnoreCase("yes");

        } while (playAgain);

        System.out.println("\nThanks for playing Rock-Paper-Scissors! See you next time!");
        System.out.println();
        scanner.close();
    }
}

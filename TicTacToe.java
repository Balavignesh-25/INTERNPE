import java.util.Scanner;
import java.util.Random;

public class TicTacToe {
    static char[] board;
    static char currentPlayer;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Tic Tac Toe!");
        boolean playAgain = true;

        while (playAgain) {
            board = new char[10];
            initializeBoard();

            System.out.println("Choose Game Mode:");
            System.out.println("1. Single Player (vs AI)");
            System.out.println("2. Two Player");
            int mode = scanner.nextInt();

            boolean gameOn = true;
            currentPlayer = 'X';

            System.out.println("Game Starts!");
            printBoard();

            while (gameOn) {
                if (mode == 1 && currentPlayer == 'O') {
                    makeAIMove();
                } else {
                    System.out.println("Player " + currentPlayer + ", Enter your Move (1-9): ");
                    int position = scanner.nextInt();

                    if (isValidMove(position)) {
                        makeMove(position, currentPlayer);
                    } else {
                        System.out.println("Invalid Move. Try Again.");
                        continue;
                    }
                }

                printBoard();

                if (isWinner(currentPlayer)) {
                    System.out.println("Player " + currentPlayer + " Wins!");
                    gameOn = false;
                } else if (isBoardFull()) {
                    System.out.println("It's a Draw!");
                    gameOn = false;
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            }

            System.out.print("Do you want to Play Again? (Yes/No): ");
            playAgain = scanner.next().equalsIgnoreCase("yes");
        }

        System.out.println("Thank you for Playing Tic Tac Toe!");
        scanner.close();
    }

    static void initializeBoard() {
        for (int i = 1; i <= 9; i++) {
            board[i] = (char) ('0' + i);
        }
    }

    static void printBoard() {
        System.out.println();
        System.out.println(" " + board[1] + " | " + board[2] + " | " + board[3]);
        System.out.println("---+---+---");
        System.out.println(" " + board[4] + " | " + board[5] + " | " + board[6]);
        System.out.println("---+---+---");
        System.out.println(" " + board[7] + " | " + board[8] + " | " + board[9]);
        System.out.println();
    }

    static boolean isValidMove(int position) {
        return position >= 1 && position <= 9 && board[position] == (char) ('0' + position);
    }

    static void makeMove(int position, char player) {
        board[position] = player;
    }

    static void makeAIMove() {
        Random random = new Random();
        int position;
        do {
            position = random.nextInt(9) + 1;
        } while (!isValidMove(position));
        System.out.println("AI chooses Position: " + position);
        makeMove(position, 'O');
    }

    static boolean isWinner(char player) {
        return (board[1] == player && board[2] == player && board[3] == player) ||
               (board[4] == player && board[5] == player && board[6] == player) ||
               (board[7] == player && board[8] == player && board[9] == player) ||
               (board[1] == player && board[4] == player && board[7] == player) ||
               (board[2] == player && board[5] == player && board[8] == player) ||
               (board[3] == player && board[6] == player && board[9] == player) ||
               (board[1] == player && board[5] == player && board[9] == player) ||
               (board[3] == player && board[5] == player && board[7] == player);
    }

    static boolean isBoardFull() {
        for (int i = 1; i <= 9; i++) {
            if (board[i] == (char) ('0' + i)) return false;
        }
        return true;
    }
}

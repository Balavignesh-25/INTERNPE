import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ConnectFourDarkMode extends JFrame {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final int CELL_SIZE = 60;
    private static final Color PLAYER_ONE_COLOR = Color.RED;
    private static final Color PLAYER_TWO_COLOR = Color.YELLOW;
    private static final Color EMPTY_COLOR = new Color(50, 50, 50);
    private static final Color BACKGROUND_COLOR = new Color(30, 30, 30);
    
    private char[][] board = new char[ROWS][COLUMNS];
    private boolean playerOneTurn = true;
    private boolean vsAI = false;
    private JPanel boardPanel;

    public ConnectFourDarkMode() {
        setTitle("Connect Four");
        setSize(COLUMNS * CELL_SIZE, (ROWS + 2) * CELL_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLocationRelativeTo(null);

        showModeSelection();
        
        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };
        boardPanel.setPreferredSize(new Dimension(COLUMNS * CELL_SIZE, ROWS * CELL_SIZE));
        boardPanel.setBackground(BACKGROUND_COLOR);
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = e.getX() / CELL_SIZE;
                if (!dropDisc(column) || (vsAI && !playerOneTurn)) {
                    aiMove();
                }
            }
        });

        add(boardPanel, BorderLayout.CENTER);
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> resetGame());
        add(restartButton, BorderLayout.SOUTH);
        
        resetGame();
        setVisible(true);
    }

    private void showModeSelection() {
        String[] options = {"Player vs AI", "Player vs Player"};
        int choice = JOptionPane.showOptionDialog(this, "Select Game Mode", "Game Mode", 
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        vsAI = (choice == 0);
    }

    private void drawBoard(Graphics g) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                g.setColor(getColor(board[row][col]));
                g.fillOval(col * CELL_SIZE + 5, row * CELL_SIZE + 5, CELL_SIZE - 10, CELL_SIZE - 10);
            }
        }
    }

    private Color getColor(char player) {
        return switch (player) {
            case 'X' -> PLAYER_ONE_COLOR;
            case 'O' -> PLAYER_TWO_COLOR;
            default -> EMPTY_COLOR;
        };
    }

    private boolean dropDisc(int col) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][col] == '.') {
                board[row][col] = playerOneTurn ? 'X' : 'O';
                playerOneTurn = !playerOneTurn;
                boardPanel.repaint();
                if (checkWin()) {
                    if (vsAI) {
                        if (playerOneTurn) {
                            JOptionPane.showMessageDialog(this, "AI Wins!");
                        } else {
                            JOptionPane.showMessageDialog(this, "Player Wins!");
                        }
                    } else {
                        if (playerOneTurn) {
                            JOptionPane.showMessageDialog(this, "Player 2 Wins!");
                        } else {
                            JOptionPane.showMessageDialog(this, "Player 1 Wins!");
                        }
                    }
                    resetGame();
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    private void aiMove() {
        int bestCol = -1;
        int bestScore = Integer.MIN_VALUE;
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = ROWS - 1; row >= 0; row--) {
                if (board[row][col] == '.') {
                    board[row][col] = 'O';
                    int score = minimax(0, false);
                    board[row][col] = '.';
                    if (score > bestScore) {
                        bestScore = score;
                        bestCol = col;
                    }
                    break;
                }
            }
        }
        dropDisc(bestCol);
    }

    private int minimax(int depth, boolean isMaximizing) {
        if (checkWin()) return isMaximizing ? -10 : 10;
        if (depth >= 4) return 0;

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = ROWS - 1; row >= 0; row--) {
                if (board[row][col] == '.') {
                    board[row][col] = isMaximizing ? 'O' : 'X';
                    int score = minimax(depth + 1, !isMaximizing);
                    board[row][col] = '.';
                    bestScore = isMaximizing ? Math.max(score, bestScore) : Math.min(score, bestScore);
                    break;
                }
            }
        }
        return bestScore;
    }

    private boolean checkWin() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (board[row][col] != '.' &&
                        (checkDirection(row, col, 1, 0) || checkDirection(row, col, 0, 1) ||
                                checkDirection(row, col, 1, 1) || checkDirection(row, col, 1, -1))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int row, int col, int rowDir, int colDir) {
        char currentPlayer = board[row][col];
        int count = 1;
        for (int i = 1; i < 4; i++) {
            int r = row + i * rowDir;
            int c = col + i * colDir;
            if (r >= 0 && r < ROWS && c >= 0 && c < COLUMNS && board[r][c] == currentPlayer) {
                count++;
            } else {
                break;
            }
        }
        return count >= 4;
    }

    private void resetGame() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = '.';
            }
        }
        playerOneTurn = true;
        boardPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ConnectFourDarkMode::new);
    }
}

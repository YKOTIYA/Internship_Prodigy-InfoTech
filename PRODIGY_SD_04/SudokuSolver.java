import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

@SuppressWarnings("unused")
public class SudokuSolver extends JFrame implements ActionListener {
    private JTextField[][] textFields;
    private JButton solveButton;
    private JButton clearButton;

    public SudokuSolver() {
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sudoku grid panel
        JPanel gridPanel = new JPanel(new GridLayout(9, 9, 2, 2));
        gridPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        textFields = new JTextField[9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                textFields[row][col] = new JTextField();
                textFields[row][col].setHorizontalAlignment(JTextField.CENTER);
                textFields[row][col].setFont(new Font("Arial", Font.PLAIN, 20));
                gridPanel.add(textFields[row][col]);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        solveButton = new JButton("Solve");
        solveButton.addActionListener(this);
        buttonPanel.add(solveButton);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == solveButton) {
            int[][] grid = new int[9][9];
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    String value = textFields[row][col].getText();
                    if (!value.isEmpty()) {
                        grid[row][col] = Integer.parseInt(value);
                    } else {
                        grid[row][col] = 0;
                    }
                }
            }

            if (solveSudoku(grid)) {
                updateTextFields(grid);
            } else {
                JOptionPane.showMessageDialog(this, "No solution found.");
            }
        } else if (e.getSource() == clearButton) {
            clearGrid();
        }
    }

    private boolean solveSudoku(int[][] grid) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(grid, row, col, num)) {
                            grid[row][col] = num;
                            if (solveSudoku(grid)) {
                                return true;
                            } else {
                                grid[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int[][] grid, int row, int col, int num) {
        // Check row
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num) {
                return false;
            }
        }

        // Check column
        for (int i = 0; i < 9; i++) {
            if (grid[i][col] == num) {
                return false;
            }
        }

        // Check 3x3 box
        int boxRow = row / 3 * 3;
        int boxCol = col / 3 * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[boxRow + i][boxCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private void updateTextFields(int[][] grid) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                textFields[row][col].setText(String.valueOf(grid[row][col]));
            }
        }
    }

    private void clearGrid() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                textFields[row][col].setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokuSolver().setVisible(true);
            }
        });
    }
}
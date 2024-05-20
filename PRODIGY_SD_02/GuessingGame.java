import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GuessingGame extends JFrame implements ActionListener {
    private JTextField inputField;
    private JButton guessButton, resetButton;
    private JLabel feedbackLabel, attemptsLabel;
    private int randomNumber, attempts;

    public GuessingGame() {
        // Set up the frame
        setTitle("Guessing Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(250, 250, 210)); // Light yellow background

        // Create a random number
        Random random = new Random();
        randomNumber = random.nextInt(100) + 1; // Random number between 1 and 100
        attempts = 0;

        // Create input field
        inputField = new JTextField(10);
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));

        // Create guess button
        guessButton = new JButton("Guess");
        guessButton.addActionListener(this);
        guessButton.setFont(new Font("Arial", Font.BOLD, 16));
        guessButton.setBackground(new Color(255, 215, 0)); // Golden yellow button
        guessButton.setForeground(Color.DARK_GRAY);

        // Create reset button
        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.setBackground(new Color(255, 215, 0)); // Golden yellow button
        resetButton.setForeground(Color.DARK_GRAY);

        // Create feedback label
        feedbackLabel = new JLabel();
        feedbackLabel.setFont(new Font("Arial", Font.BOLD, 18));
        feedbackLabel.setForeground(new Color(139, 69, 19)); // Brown text color

        // Create attempts label
        attemptsLabel = new JLabel("Attempts: 0");
        attemptsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        attemptsLabel.setForeground(new Color(139, 69, 19)); // Brown text color

        // Add components to the frame
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(new JLabel("Enter your guess:"), gbc);

        gbc.gridx = 1;
        add(inputField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(guessButton, gbc);

        gbc.gridy = 2;
        add(resetButton, gbc);

        gbc.gridy = 3;
        add(feedbackLabel, gbc);

        gbc.gridy = 4;
        add(attemptsLabel, gbc);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guessButton) {
            try {
                int guess = Integer.parseInt(inputField.getText());
                attempts++;

                if (guess == randomNumber) {
                    feedbackLabel.setText("Congratulations! You guessed the number.");
                    guessButton.setEnabled(false);
                    resetButton.setEnabled(true);
                } else if (guess < randomNumber) {
                    feedbackLabel.setText("Too low, try again.");
                } else {
                    feedbackLabel.setText("Too high, try again.");
                }

                attemptsLabel.setText("Attempts: " + attempts);
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Invalid input. Please enter a number.");
            }
        } else if (e.getSource() == resetButton) {
            // Reset the game
            Random random = new Random();
            randomNumber = random.nextInt(100) + 1;
            attempts = 0;
            inputField.setText("");
            feedbackLabel.setText("");
            attemptsLabel.setText("Attempts: 0");
            guessButton.setEnabled(true);
            resetButton.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        new GuessingGame();
    }
}
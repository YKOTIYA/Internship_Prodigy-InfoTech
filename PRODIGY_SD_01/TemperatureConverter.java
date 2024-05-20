import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TemperatureConverter extends JFrame implements ActionListener {
    private JTextField inputField;
    private JComboBox<String> inputUnitDropdown, outputUnitDropdown;
    private JLabel outputLabel;

    public TemperatureConverter() {
        // Set up the frame
        setTitle("Temperature Converter");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setLayout(new GridBagLayout());

        // Create a panel for input components
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input"));

        // Create input field
        inputField = new JTextField(10);
        inputPanel.add(new JLabel("Temperature:"));
        inputPanel.add(inputField);

        // Create input unit dropdown
        String[] units = {"Celsius", "Fahrenheit", "Kelvin"};
        inputUnitDropdown = new JComboBox<>(units);
        inputPanel.add(new JLabel("Unit:"));
        inputPanel.add(inputUnitDropdown);

        // Create a panel for output components
        JPanel outputPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));

        // Create output unit dropdown
        outputUnitDropdown = new JComboBox<>(units);
        outputPanel.add(outputUnitDropdown);

        // Create convert button
        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(this);
        outputPanel.add(convertButton);

        // Create output label
        outputLabel = new JLabel();
        outputLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Add components to the frame
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(inputPanel, gbc);

        gbc.gridy = 1;
        add(outputPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(outputLabel, gbc);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            double inputValue = Double.parseDouble(inputField.getText());
            String inputUnit = (String) inputUnitDropdown.getSelectedItem();
            String outputUnit = (String) outputUnitDropdown.getSelectedItem();

            double convertedValue = convertTemperature(inputValue, inputUnit, outputUnit);
            outputLabel.setText(String.format("%.2f %s", convertedValue, outputUnit));
        } catch (NumberFormatException ex) {
            outputLabel.setText("Invalid input");
        }
    }

    private double convertTemperature(double value, String inputUnit, String outputUnit) {
        double celsius;

        // Convert input value to Celsius
        switch (inputUnit) {
            case "Celsius":
                celsius = value;
                break;
            case "Fahrenheit":
                celsius = (value - 32) * 5 / 9;
                break;
            case "Kelvin":
                celsius = value - 273.15;
                break;
            default:
                throw new IllegalArgumentException("Invalid input unit");
        }

        // Convert Celsius to output unit
        switch (outputUnit) {
            case "Celsius":
                return celsius;
            case "Fahrenheit":
                return celsius * 9 / 5 + 32;
            case "Kelvin":
                return celsius + 273.15;
            default:
                throw new IllegalArgumentException("Invalid output unit");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        new TemperatureConverter();
    }
}
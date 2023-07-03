package Task1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Task1_GUI extends JFrame {
    private int minRange;
    private int maxRange;
    private int maxAttempts;
    private int score;
    private int targetNumber;
    private int attemptsLeft;
    private JTextField minRangeField;
    private JTextField maxRangeField;
    private JTextField maxAttemptsField;
    private JButton startButton;
    private JTextField guessField;
    private JButton guessButton;
    private JTextArea outputTextArea;
    private JButton restartButton;

    public Task1_GUI() {
        setTitle("Number Guessing Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel setupPanel = createSetupPanel();
        add(setupPanel, BorderLayout.NORTH);

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel guessLabel = new JLabel("Enter your guess: ");
        inputPanel.add(guessLabel);

        guessField = new JTextField(10);
        inputPanel.add(guessField);

        guessButton = new JButton("Guess");
        guessButton.setEnabled(false);
        guessButton.addActionListener(new GuessButtonListener());
        inputPanel.add(guessButton);

        restartButton = new JButton("Restart");
        restartButton.setEnabled(false);
        restartButton.addActionListener(new RestartButtonListener());
        inputPanel.add(restartButton);

        add(inputPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    private JPanel createSetupPanel() {
        JPanel setupPanel = new JPanel();
        setupPanel.setLayout(new FlowLayout());

        JLabel minRangeLabel = new JLabel("Lower limit of range: ");
        setupPanel.add(minRangeLabel);

        minRangeField = new JTextField(5);
        setupPanel.add(minRangeField);

        JLabel maxRangeLabel = new JLabel("Upper limit of range: ");
        setupPanel.add(maxRangeLabel);

        maxRangeField = new JTextField(5);
        setupPanel.add(maxRangeField);

        JLabel maxAttemptsLabel = new JLabel("Maximum attempts: ");
        setupPanel.add(maxAttemptsLabel);

        maxAttemptsField = new JTextField(5);
        setupPanel.add(maxAttemptsField);

        startButton = new JButton("Start");
        startButton.addActionListener(new StartButtonListener());
        setupPanel.add(startButton);

        return setupPanel;
    }

    private void handleStart() {
        try {
            minRange = Integer.parseInt(minRangeField.getText());
            maxRange = Integer.parseInt(maxRangeField.getText());
            maxAttempts = Integer.parseInt(maxAttemptsField.getText());

            score = 0;
            targetNumber = new Random().nextInt(maxRange - minRange + 1) + minRange;
            attemptsLeft = maxAttempts;

            outputTextArea.setText("I'm thinking of a number between " + minRange + " and " + maxRange + ".\n");
            outputTextArea.append("Attempts left: " + attemptsLeft + "\n");
            outputTextArea.append("\n");
            
            startButton.setEnabled(false);
            guessButton.setEnabled(true);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(Task1_GUI.this, "Invalid input. Please enter valid numbers.");
        }
    }

    private void handleGuess(int guess) {
        attemptsLeft--;
       
        if (guess == targetNumber) {
            score++;
            outputTextArea.append("Hurray!!!!! Your guess is right.\n");
            outputTextArea.append("The correct number was: " + targetNumber + "\n");
            outputTextArea.append("Your score is: " + score + "\n");
            targetNumber = new Random().nextInt(maxRange - minRange + 1) + minRange;
            guessButton.setEnabled(true);
            restartButton.setEnabled(true);
        } else if (guess < targetNumber) {
            outputTextArea.append("HINT: I think the number is greater than "+guess+" .\n");
        } else {
            outputTextArea.append("HINT: I think the number is less than "+guess+" .\n");
        }

        outputTextArea.append("Attempts left: " + attemptsLeft + "\n");
        outputTextArea.append("\n");
        
        if (attemptsLeft == 0) {
            outputTextArea.append("Out of attempts\n");
            outputTextArea.append("The correct number was: " + targetNumber + "\n");
            outputTextArea.append("Your final score is: " + score + "\n");
            guessButton.setEnabled(false);
            restartButton.setEnabled(true);
        }
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleStart();
        }
    }

    private class GuessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int guess = Integer.parseInt(guessField.getText());
                handleGuess(guess);
                guessField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(Task1_GUI.this, "Invalid input. Please enter a valid number.");
            }
        }
    }

    private class RestartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            startButton.setEnabled(true);
            guessButton.setEnabled(false);
            restartButton.setEnabled(false);
            outputTextArea.setText("");
            minRangeField.setText("");
            maxRangeField.setText("");
            maxAttemptsField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Task1_GUI();
            }
        });
    }
}

package Task2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Task2_GUI extends JFrame {
    private JTextArea inputTextArea;
    private JButton processButton;
    private JTextArea outputTextArea;

    public Task2_GUI() {
        setTitle("Word Count");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.NORTH);

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        processButton = new JButton("Process");
        processButton.addActionListener(new ProcessButtonListener());
        buttonPanel.add(processButton);

        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        JLabel inputLabel = new JLabel("Input Text or File Path:");
        inputPanel.add(inputLabel, BorderLayout.NORTH);

        inputTextArea = new JTextArea();
        inputTextArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(inputTextArea);
        inputPanel.add(scrollPane, BorderLayout.CENTER);

        return inputPanel;
    }

    private void processInput() {
        String input = inputTextArea.getText();

        java.util.List<String> words = getWords(input);

        // Counting words
        int totalCount = countWords(words);
        outputTextArea.append("Total word count: " + totalCount + "\n");

        // Counting unique words
        int uniqueCount = countUniqueWords(words);
        outputTextArea.append("Unique word count: " + uniqueCount + "\n");

        // Frequency of each word
        Map<String, Long> wordFrequency = getWordFrequency(words);
        outputTextArea.append("Word frequency:\n");
        for (Map.Entry<String, Long> entry : wordFrequency.entrySet()) {
            outputTextArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
        }
    }

    private java.util.List<String> getWords(String input) {
        if (isFile(input)) {
            return getWordsFromFile(input);
        } else {
            return getWordsFromString(input);
        }
    }

    private boolean isFile(String input) {
        File file = new File(input);
        return file.isFile();
    }

    private java.util.List<String> getWordsFromFile(String filePath) {
        java.util.List<String> wordList = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File(filePath))) {
            while (fileScanner.hasNext()) {
                wordList.add(fileScanner.next());
            }
        } catch (FileNotFoundException e) {
            outputTextArea.append("File not found: " + filePath + "\n");
        }
        return wordList;
    }

    private java.util.List<String> getWordsFromString(String input) {
        return Arrays.stream(input.split("\\s+|\\p{Punct}"))
                .collect(Collectors.toList());
    }

    private int countWords(java.util.List<String> words) {
        return words.size();
    }

    private int countUniqueWords(java.util.List<String> words) {
        return (int) words.stream()
                .distinct()
                .count();
    }

    private Map<String, Long> getWordFrequency(java.util.List<String> words) {
        return words.stream()
                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));
    }

    private class ProcessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            outputTextArea.setText("");
            processInput();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Task2_GUI();
            }
        });
    }
}

package Task2;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Task2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter a text or provide the path to a file:");
        String input = scanner.nextLine();

        List<String> words = getWords(input);

        // Counting words
        int totalCount = countWords(words);
        System.out.println("Total word count: " + totalCount);

        // Counting unique words
        int uniqueCount = countUniqueWords(words);
        System.out.println("Unique word count: " + uniqueCount);

        // Frequency of each word
        Map<String, Long> wordFrequency = getWordFrequency(words);
        System.out.println("Word frequency:");
        for (Map.Entry<String, Long> entry : wordFrequency.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    // Splitting input into a list of words
    private static List<String> getWords(String input) {
        if (isFile(input)) {
            return getWordsFromFile(input);
        } else {
            return getWordsFromString(input);
        }
    }

    // Checking if the input is a file
    private static boolean isFile(String input) {
        File file = new File(input);
        return file.isFile();
    }

    // Reading words from a file
    private static List<String> getWordsFromFile(String filePath) {
        List<String> wordList = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File(filePath))) {
            while (fileScanner.hasNext()) {
                wordList.add(fileScanner.next());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
        return wordList;
    }

    // Splitting input string into words
    private static List<String> getWordsFromString(String input) {
        return Arrays.stream(input.split("\\s+|\\p{Punct}"))
                .collect(Collectors.toList());
    }

    // Counting total number of words
    private static int countWords(List<String> words) {
        return words.size();
    }

    // Counting number of unique words
    private static int countUniqueWords(List<String> words) {
        return (int) words.stream()
                .distinct()
                .count();
    }

    // Calculating frequency of each word
    private static Map<String, Long> getWordFrequency(List<String> words) {
        return words.stream()
                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));
    }
}

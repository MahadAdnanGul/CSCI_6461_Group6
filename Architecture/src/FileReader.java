
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    public static void ReadFiles(String fileName) {
        String filePath = fileName;
        try {
            // Create a Scanner object to read from the file
            Scanner scanner = new Scanner(new File(filePath));

            // Iterate through each word in the file
            while (scanner.hasNext()) {
                // Read the next word
                String word = scanner.next();

                // Print or process the word as needed
                System.out.println(word);
            }
            // Close the scanner to release resources
            scanner.close();
        } catch (FileNotFoundException e) {
            // Handle file not found exception
            System.err.println("File not found: " + filePath);
            e.printStackTrace();
        }
    }
}
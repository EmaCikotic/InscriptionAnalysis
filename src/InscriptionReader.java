import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InscriptionReader {

    public void readFile() {
        try (BufferedReader br =
                     new BufferedReader(new FileReader("data/text_inscriptions.txt"))) {

            String line = br.readLine();

            if (line != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
}
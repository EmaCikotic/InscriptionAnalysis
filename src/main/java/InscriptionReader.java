import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper; //https://www.baeldung.com/jackson-object-mapper-tutorial

public class InscriptionReader {
    public void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/text_inscriptions.txt"))) {

            String line = br.readLine();

            if (line != null) {
                ObjectMapper mapper = new ObjectMapper();
                Inscription inscription = mapper.readValue(line, Inscription.class);

                System.out.println("id: " + inscription.getId());
                System.out.println("content: " + inscription.getContent());
                System.out.println("block number: " + inscription.getBlockNo());
                System.out.println("number: " + inscription.getNumber());
                System.out.println("timestamp: " + inscription.getTimestamp());
                System.out.println("content length: " + inscription.getContentLength());
                System.out.println("value: " + inscription.getValue());
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
}
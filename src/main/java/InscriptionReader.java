import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper; //https://www.baeldung.com/jackson-object-mapper-tutorial

public class InscriptionReader {
    public void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/text_inscriptions.txt"))) {

            ObjectMapper mapper = new ObjectMapper(); //reuse it for every inscriptions
            String line = br.readLine();

            int counter =0; //count inscriptions

           while (line != null) {

                Inscription inscription = mapper.readValue(line, Inscription.class);
                counter++;
               line = br.readLine(); //read the next line

           }
            System.out.println("Total inscriptions: " +counter);
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
}
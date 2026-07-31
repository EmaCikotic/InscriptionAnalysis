import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper; //https://www.baeldung.com/jackson-object-mapper-tutorial
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class InscriptionReader {
    public void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/text_inscriptions.txt"))) {

            ObjectMapper mapper = new ObjectMapper(); //reuse it for every inscriptions
            String line = br.readLine();

            int counter =0; //count inscriptions

            long earliestTimestamp = Long.MAX_VALUE;
            long latestTimestamp = Long.MIN_VALUE;

           while (line != null) {

                Inscription inscription = mapper.readValue(line, Inscription.class);
                long timestamp=inscription.getTimestamp();

               if (timestamp < earliestTimestamp)  earliestTimestamp = timestamp;

               if (timestamp > latestTimestamp)  latestTimestamp = timestamp;
               counter++;
               line = br.readLine(); //read the next line

           }

           //getting the actual date not seconds
            LocalDate earliestDate = Instant.ofEpochSecond(earliestTimestamp).atZone(ZoneOffset.UTC).toLocalDate();
            LocalDate latestDate = Instant.ofEpochSecond(latestTimestamp).atZone(ZoneOffset.UTC).toLocalDate();

            System.out.println("Dataset period:");
            System.out.println("From: " + earliestDate);
            System.out.println("To: " + latestDate);
            System.out.println("Total inscriptions: " + String.format("%,d", counter));

        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
}
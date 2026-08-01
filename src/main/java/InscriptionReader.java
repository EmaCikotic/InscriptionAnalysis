import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper; //https://www.baeldung.com/jackson-object-mapper-tutorial
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.YearMonth;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;

public class InscriptionReader {
    public void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/text_inscriptions.txt"))) {

            ObjectMapper mapper = new ObjectMapper(); //reuse it for every inscriptions
            String line = br.readLine();

            int counter =0; //count inscriptions

            long earliestTimestamp = Long.MAX_VALUE;
            long latestTimestamp = Long.MIN_VALUE;

            Map<YearMonth, Integer> monthlyActivity = new TreeMap<>();
            Map<YearMonth, Set<String>> uniqueActivity = new HashMap<>();

           while (line != null) {

                Inscription inscription = mapper.readValue(line, Inscription.class);
                long timestamp=inscription.getTimestamp();

               // Convert timestamp -> LocalDate -> YearMonth
               LocalDate date = Instant.ofEpochSecond(timestamp).atZone(ZoneOffset.UTC).toLocalDate();

               YearMonth month = YearMonth.from(date);

               //for unique entries
               Set<String> contents = uniqueActivity.computeIfAbsent(month, k -> new HashSet<>());
               contents.add(inscription.getContent());

               monthlyActivity.put(month, monthlyActivity.getOrDefault(month, 0) + 1);


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

            System.out.println("\nMonthly activity:");

            for (Map.Entry<YearMonth, Integer> entry : monthlyActivity.entrySet()) {
                System.out.println(entry.getKey() + " -> " + String.format("%,d", entry.getValue()));
            }

            System.out.println("                ");
            System.out.println("Unique entries: ");

            for (Map.Entry<YearMonth, Set<String>> entry : uniqueActivity.entrySet()) {
                System.out.println(entry.getKey() + " -> " + String.format("%,d",entry.getValue().size()));
            }

        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
}
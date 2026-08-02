import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper; //https://www.baeldung.com/jackson-object-mapper-tutorial
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.YearMonth;
import java.util.*;
import java.io.PrintWriter;
import java.io.FileWriter;

public class InscriptionReader {
    public void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/text_inscriptions.txt"))) {

            ObjectMapper mapper = new ObjectMapper(); //reuse it for every inscriptions
            String line = br.readLine();

            int counter =0; //count inscriptions

            long earliestTimestamp = Long.MAX_VALUE;
            long latestTimestamp = Long.MIN_VALUE;

            Map<YearMonth, Integer> monthlyActivity = new TreeMap<>();
            Map<YearMonth, Set<String>> uniqueActivity = new TreeMap<>();
            Map<String, Integer> contentFrequency = new HashMap<>(); //no need to be sorted
            Map<String, Integer> contentTypes = new TreeMap<>();
            Set<String> otherContents = new HashSet<>(); //export "other" type to csv

           while (line != null) {

                Inscription inscription = mapper.readValue(line, Inscription.class);

                long timestamp=inscription.getTimestamp();
                String content = inscription.getContent();

               // Convert timestamp -> LocalDate -> YearMonth
               LocalDate date = Instant.ofEpochSecond(timestamp).atZone(ZoneOffset.UTC).toLocalDate();

               YearMonth month = YearMonth.from(date);

               //for unique entries
               Set<String> contents = uniqueActivity.computeIfAbsent(month, k -> new HashSet<>());
               contents.add(content);

               String type; //JSON, HTML, Reference, Plain text

               //classfying into content type
               if (content == null || content.isBlank()) {
                   type = "Empty";
               }
               else if (content.startsWith("/content/")) {
                   type = "Reference";
               }
               else if (content.stripLeading().startsWith("<")) {
                   type = "HTML";
               }
               else if (content.stripLeading().startsWith("{")) {
                   type = "JSON";
               }
               else if (content.stripLeading().startsWith("http://")
                       || content.stripLeading().startsWith("https://")) {
                   type = "URL";
               }
               else if (content.toLowerCase().endsWith(".bitmap")) {
                   type = "Bitmap";
               }
               else if (content.matches(".*\\.[A-Za-z0-9]+$")) { // .bitnats, .btc, .block, .uniworlds, .ordimap, .sats, .x, .ai, etc.
                   type = "Namespace";
               }
               else if (content.codePointCount(0, content.length()) == 1) {
                   type = "Single Character";
               }
               else {
                   type = "Other";
                   otherContents.add(content);
               }
               contentTypes.put(type, contentTypes.getOrDefault(type, 0) + 1);

               monthlyActivity.put(month, monthlyActivity.getOrDefault(month, 0) + 1);
               if (content != null && !content.isBlank()) {
                   contentFrequency.put(content, contentFrequency.getOrDefault(content, 0) + 1);
               }

               if (timestamp < earliestTimestamp)  earliestTimestamp = timestamp;

               if (timestamp > latestTimestamp)  latestTimestamp = timestamp;

               counter++;

               line = br.readLine(); //read the next line

           }

            try (PrintWriter writer =
                         new PrintWriter(new FileWriter("output/other_contents.csv"))) {

                writer.println("Content");

                for (String content : otherContents) {

                    content = content
                            .replace("\"", "\"\"")
                            .replace("\n", "\\n")
                            .replace("\r", "\\r");

                    writer.println("\"" + content + "\"");
                }
            }

           //getting the actual date not seconds
            LocalDate earliestDate = Instant.ofEpochSecond(earliestTimestamp).atZone(ZoneOffset.UTC).toLocalDate();
            LocalDate latestDate = Instant.ofEpochSecond(latestTimestamp).atZone(ZoneOffset.UTC).toLocalDate();

            System.out.println("Dataset period:");
            System.out.println("From: " + earliestDate);
            System.out.println("To: " + latestDate);
            System.out.println("Total inscriptions: " + String.format("%,d", counter));

            System.out.println("\nMonthly Statistics:");
            System.out.printf("%-10s %-12s %-12s %-12s%n", "Month", "Total", "Unique", "Duplicates");
            System.out.println("------------------------------------------------------------");

            for (YearMonth month : monthlyActivity.keySet()) {

                int total = monthlyActivity.get(month);
                int unique = uniqueActivity.get(month).size();
                int duplicates = total - unique;

                System.out.printf("%-10s %-12s %-12s %-12s%n",
                        month,
                        String.format("%,d", total),
                        String.format("%,d", unique),
                        String.format("%,d", duplicates));
            }

            //the most repeated content
            List<Map.Entry<String, Integer>> sortedContents = new ArrayList<>(contentFrequency.entrySet());

            sortedContents.sort((a, b) -> b.getValue().compareTo(a.getValue()));

            //writing to a file
            try (PrintWriter writer = new PrintWriter(new FileWriter("output/content_frequency.csv"))) {

                writer.println("Occurrences,Content");

                for (Map.Entry<String, Integer> entry : sortedContents) {

                    String content = entry.getKey()
                            .replace("\"", "\"\"")
                            .replace("\n", "\\n")
                            .replace("\r", "\\r");

                    writer.println(entry.getValue() + ",\"" + content + "\"");
                }
            }
            System.out.println("writing to CSV done");

            System.out.println("\nContent Types:  ");

            for (Map.Entry<String, Integer> entry : contentTypes.entrySet()) {
                System.out.printf("%-15s %s%n",
                        entry.getKey(),
                        String.format("%,d", entry.getValue()));
            }

         } catch (IOException e) {
        System.out.println("Error reading file: " + e.getMessage());
    }
    }
}
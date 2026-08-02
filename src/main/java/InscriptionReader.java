import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InscriptionReader {

    private final ObjectMapper mapper = new ObjectMapper();
    private final ContentClassifier classifier = new ContentClassifier();

    public InscriptionStatistics readFile(String filePath) {
        InscriptionStatistics statistics = new InscriptionStatistics();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = reader.readLine()) != null) {

                Inscription inscription = mapper.readValue(line, Inscription.class);
                String type = classifier.classify(inscription.getContent());
                statistics.process(inscription, type);
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Error reading file: " + e.getMessage(),
                    e
            );
        }

        return statistics;
    }
}
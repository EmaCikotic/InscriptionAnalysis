import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class InscriptionReader {

    private final ObjectMapper mapper = new ObjectMapper();
    private final ContentClassifier classifier = new ContentClassifier();
    private final SecurityScanner securityScanner = new SecurityScanner();
    private final BehavioralSecurityScanner behavioralSecurityScanner =   new BehavioralSecurityScanner();

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

    public SecurityStatistics scanSecurity(String filePath) {

        SecurityStatistics securityStatistics =
                new SecurityStatistics();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = reader.readLine()) != null) {

                Inscription inscription =
                        mapper.readValue(line, Inscription.class);

                Map<String, List<String>> matches =
                        securityScanner.scan(
                                inscription.getContent()
                        );

                securityStatistics.process(
                        inscription,
                        matches
                );
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Error reading file: " + e.getMessage(),
                    e
            );
        }

        return securityStatistics;
    }
    public BehavioralSecurityStatistics scanBehaviors(
            String filePath) {

        BehavioralSecurityStatistics statistics =
                new BehavioralSecurityStatistics();

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(filePath)
                     )) {

            String line;

            while ((line = reader.readLine()) != null) {

                Inscription inscription =
                        mapper.readValue(
                                line,
                                Inscription.class
                        );

                Map<String, List<String>> behaviors =
                        behavioralSecurityScanner.scan(
                                inscription.getContent()
                        );

                statistics.process(
                        inscription,
                        behaviors
                );
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Error reading file: "
                            + e.getMessage(),
                    e
            );
        }

        return statistics;
    }
}
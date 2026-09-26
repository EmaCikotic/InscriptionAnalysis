import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.YearMonth;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class CsvExporter {

    public void exportOtherContents(Set<String> otherContents, String filePath)
            throws IOException {

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {

            writer.println("Content");

            for (String content : otherContents) {

                if (content == null) {
                    content = "";
                }

                content = content.replace("\"", "\"\"");
                content = content.replace("\n", "\\n");
                content = content.replace("\r", "\\r");

                writer.println("\"" + content + "\"");
            }
        }
    }

    public void exportContentFrequency(Map<String, Integer> contentFrequency, String filePath) throws IOException {

        List<Map.Entry<String, Integer>> sortedContents =  new ArrayList<>(contentFrequency.entrySet());

        sortedContents.sort((first, second) ->  Integer.compare(second.getValue(), first.getValue()));

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {

            writer.println("Occurrences,Content");

            for (Map.Entry<String, Integer> entry : sortedContents) {

                String content = entry.getKey();

                if (content == null) {
                    content = "";
                }

                content = content.replace("\"", "\"\"");
                content = content.replace("\n", "\\n");
                content = content.replace("\r", "\\r");

                writer.println(entry.getValue() + ",\"" + content + "\"");
            }
        }
    }
    public void exportMonthlyStatistics(Map<YearMonth, Integer> monthlyActivity,Map<YearMonth, Set<String>> uniqueActivity , String filePath)
            throws IOException {

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {

            writer.println("Month,Total,Unique,Duplicates,DuplicatePercentage");

            for (YearMonth month : monthlyActivity.keySet()) {
                int total = monthlyActivity.get(month);
                int unique = uniqueActivity.get(month).size();
                int duplicates = total - unique;
                double duplicatePercentage = (duplicates * 100.0) / total;

                writer.println(
                        month + "," +
                                total + "," +
                                unique + "," +
                                duplicates + "," +
                                String.format(Locale.US, "%.2f", duplicatePercentage)
                );

            }

        }
    }

    public void exportContentTypes (Map<String, Integer> contentTypes, int totalInscriptions ,String filePath)
            throws IOException {
        try(PrintWriter writer = new PrintWriter(new FileWriter(filePath))){

            writer.println("Type,Count,Percentage");

            for(String type : contentTypes.keySet()) {
                int count = contentTypes.get(type);
                double  percentage =  ((count * 100.0) / totalInscriptions);

                writer.println(
                        type + "," +
                        count + "," +
                                String.format(Locale.US, "%.2f", percentage)
                );
            }


        }

    }
    public void exportAverageContentLengthPerType(Map<String, Integer> contentTypes, Map<String, Long> contentTypeLengths, String filePath)
            throws IOException {
        try(PrintWriter writer = new PrintWriter(new FileWriter(filePath))){

            writer.println("Type,Average Length");

            for(String type : contentTypes.keySet()) {
               int  count =contentTypes.get(type);
               long totalLength= contentTypeLengths.get(type);
               double  averageLength= (double) totalLength/count;

                writer.println(
                        type + "," +
                                String.format(Locale.US, "%.2f", averageLength)
                );
            }


        }

    }
    public void exportSecurityCandidates(
            List<SecurityCandidate> candidates,
            String filePath
    ) throws IOException {

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(filePath))) {

            writer.println(
                    "ID,Block,Timestamp,ContentLength,Categories,Indicators,Content"
            );

            for (SecurityCandidate candidate : candidates) {

                Inscription inscription = candidate.getInscription();

                String categories =
                        String.join(
                                "|",
                                candidate.getMatches().keySet()
                        );

                List<String> allIndicators = new ArrayList<>();

                for (List<String> indicators :
                        candidate.getMatches().values()) {

                    allIndicators.addAll(indicators);
                }

                String indicatorString =
                        String.join("|", allIndicators);

                String content =
                        inscription.getContent();

                if (content == null) {
                    content = "";
                }

                // Proper CSV escaping
                content = content.replace("\"", "\"\"");

                categories = categories.replace("\"", "\"\"");
                indicatorString =
                        indicatorString.replace("\"", "\"\"");

                writer.println(
                        "\"" + inscription.getId() + "\"," +
                                inscription.getBlockNo() + "," +
                                inscription.getTimestamp() + "," +
                                inscription.getContentLength() + "," +
                                "\"" + categories + "\"," +
                                "\"" + indicatorString + "\"," +
                                "\"" + content + "\""
                );
            }
        }
    }
    private String escapeCsv(String value) {

        if (value == null) {
            return "";
        }

        return value.replace("\"", "\"\"");
    }
    private String sha256(String content) {

        try {

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hashBytes =
                    digest.digest(
                            content.getBytes(StandardCharsets.UTF_8)
                    );

            StringBuilder hex = new StringBuilder();

            for (byte b : hashBytes) {
                hex.append(
                        String.format("%02x", b)
                );
            }

            return hex.toString();

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException(
                    "SHA-256 algorithm not available",
                    e
            );
        }
    }
    public void exportBehavioralCandidates(
            List<SecurityCandidate> candidates,
            String filePath
    ) throws IOException {

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(filePath))) {

            writer.println(
                    "ID,Block,Timestamp,ContentLength,ContentHash,Behaviors,MatchedPatterns,Content"
            );

            for (SecurityCandidate candidate : candidates) {

                Inscription inscription = candidate.getInscription();

                String content = inscription.getContent();

                if (content == null) {
                    content = "";
                }

                String hash = sha256(content);

                String behaviors = String.join(
                        "|",
                        candidate.getMatches().keySet()
                );

                List<String> allPatterns = new ArrayList<>();

                for (List<String> patterns :
                        candidate.getMatches().values()) {

                    allPatterns.addAll(patterns);
                }

                String matchedPatterns =
                        String.join("|", allPatterns);

                writer.println(
                        "\"" + escapeCsv(inscription.getId()) + "\"," +
                                inscription.getBlockNo() + "," +
                                inscription.getTimestamp() + "," +
                                inscription.getContentLength() + "," +
                                "\"" + hash + "\"," +
                                "\"" + escapeCsv(behaviors) + "\"," +
                                "\"" + escapeCsv(matchedPatterns) + "\"," +
                                "\"" + escapeCsv(content) + "\""
                );
            }
        }
    }
}
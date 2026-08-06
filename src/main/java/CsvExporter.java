import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.YearMonth;
import java.util.*;

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

    public void exportContentTypes(Map<YearMonth, Integer> monthlyActivity,Map<YearMonth, Set<String>> uniqueActivity , String filePath)
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
}
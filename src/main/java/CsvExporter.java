import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public void exportContentFrequency(Map<String, Integer> contentFrequency,
                                       String filePath)
            throws IOException {

        List<Map.Entry<String, Integer>> sortedContents =
                new ArrayList<>(contentFrequency.entrySet());

        sortedContents.sort((first, second) ->
                Integer.compare(second.getValue(), first.getValue()));

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
}
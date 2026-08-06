import java.io.IOException;
import java.time.YearMonth;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        InscriptionReader reader = new InscriptionReader();

        InscriptionStatistics statistics = reader.readFile("data/text_inscriptions.txt");

        printSummary(statistics);
        printMonthlyStatistics(statistics);
        printContentTypes(statistics);
        printContentLengthStatistics(statistics);
        printContentLengthDistribution(statistics);
        printValueStatistics(statistics);


        CsvExporter exporter = new CsvExporter();
        exporter.exportOtherContents(statistics.getOtherContents(), "output/other_contents.csv");
        exporter.exportContentFrequency(statistics.getContentFrequency(), "output/content_frequency.csv");
        exporter.exportMonthlyStatistics(statistics.getMonthlyActivity(), statistics.getUniqueActivity(), "output/monthly_statistics.csv");

        System.out.println("\nWriting to CSV done.");
    }

    private static void printSummary(InscriptionStatistics statistics) {

        System.out.println("Dataset period:");
        System.out.println("From: " + statistics.getEarliestDate());
        System.out.println("To: " + statistics.getLatestDate());
        System.out.println("Total inscriptions: " + String.format("%,d", statistics.getTotalCount()));
    }

    //add % as well
    private static void printMonthlyStatistics(InscriptionStatistics statistics) {
        System.out.println("\nMonthly Statistics:");
        System.out.printf(
                "%-10s %-12s %-12s %-12s %-12s%n",
                "Month",
                "Total",
                "Unique",
                "Duplicates",
                "Duplicate %"
        );

        System.out.println(
                "------------------------------------------------------------"
        );

        Map<YearMonth, Integer> monthlyActivity = statistics.getMonthlyActivity();
        Map<YearMonth, Set<String>> uniqueActivity = statistics.getUniqueActivity();

        for (YearMonth month : monthlyActivity.keySet()) {
            int total = monthlyActivity.get(month);
            int unique = uniqueActivity.get(month).size();
            int duplicates = total - unique;
            double duplicatePercentage = (duplicates * 100.0) / total;

            System.out.printf(
                    "%-10s %-12s %-12s %-12s %10.2f%%%n",
                    month,
                    String.format("%,d", total),
                    String.format("%,d", unique),
                    String.format("%,d", duplicates),
                    duplicatePercentage
            );
        }
    }

    private static void printContentTypes(InscriptionStatistics statistics) {

        System.out.println("\nContent Types:");

        Map<String, Integer> contentTypes = statistics.getContentTypes();

        int total =statistics.getTotalCount();

        for (String type : contentTypes.keySet()) {

            int count = contentTypes.get(type);

            double percentage = (count *100.00) /total;

            System.out.printf(
                    "%-18s %-12s (%6.2f%%)%n",
                    type,
                    String.format("%,d", count),
                    percentage
            );
        }
    }

    private static void printContentLengthStatistics(InscriptionStatistics statistics) {

        System.out.println("\nContent Length Statistics:");

        System.out.println("Minimum length: " + statistics.getMinimumContentLength()+ " bytes");
        System.out.println("Maximum length: " + statistics.getMaximumContentLength()+ " bytes");
        System.out.printf("Average length: %.2f bytes%n", statistics.getAverageContentLength());
    }

    private static void printContentLengthDistribution(InscriptionStatistics statistics) {

        System.out.println("\nContent Length Distribution:");

        Map<String, Integer> distribution = statistics.getContentLengthDistribution();

        String[] ranges = {
                "0 bytes",
                "1-10 bytes",
                "11-50 bytes",
                "51-100 bytes",
                "101-500 bytes",
                "501-1000 bytes",
                "Over 1000 bytes"
        };

        for (String range : ranges) {
            int count = distribution.get(range);

            System.out.printf("%-20s %s%n", range, String.format("%,d", count)
            );
        }
    }
    private static void printValueStatistics(InscriptionStatistics statistics) {

        System.out.println("\nValue Statistics:");

        //sats removed for now
        System.out.println("Minimum value: " + statistics.getMinimumValue());
        System.out.println("Maximum value: " + statistics.getMaximumValue());
        System.out.printf("Average value: %.2f %n", statistics.getAverageValue());
    }
}
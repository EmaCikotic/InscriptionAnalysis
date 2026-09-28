import java.io.IOException;
import java.time.YearMonth;
import java.util.List;
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
        printAverageContentLengthPerType(statistics);
        printValueStatistics(statistics);


       System.out.println("\n==============================");
        System.out.println("SECURITY EXPLORATORY ANALYSIS");
        System.out.println("==============================");

        System.out.println("Scanning inscriptions");
        SecurityStatistics securityStatistics =  reader.scanSecurity("data/text_inscriptions.txt");
        System.out.println("Scanning done");


        System.out.println(
                "\nInscriptions with at least one security indicator: "
                        + String.format(
                        "%,d",
                        securityStatistics.getInscriptionsWithAnyMatch()
                )
        );

        System.out.println("\n==============================");
        System.out.println("BEHAVIORAL SECURITY ANALYSIS");
        System.out.println("==============================");

        System.out.println("Scanning behaviors...");

        BehavioralSecurityStatistics behavioralStatistics =
                reader.scanBehaviors(
                        "data/text_inscriptions.txt"
                );

        System.out.println("Behavioral scan done.");

        System.out.println(
                "\nInscriptions with at least one behavior: "
                        + String.format(
                        "%,d",
                        behavioralStatistics
                                .getInscriptionsWithAnyBehavior()
                )
        );

        System.out.println("\nBehavior counts:");

        for (Map.Entry<String, Integer> entry :
                behavioralStatistics
                        .getBehaviorCounts()
                        .entrySet()) {

            System.out.printf(
                    "%-30s %,d%n",
                    entry.getKey(),
                    entry.getValue()
            );
        }

        System.out.println(
                "\nStrong behavioral candidates: "
                        + String.format(
                        "%,d",
                        behavioralStatistics
                                .getCandidates()
                                .size()
                )
        );

        System.out.println("\nCategory counts:");

        for (Map.Entry<String, Integer> entry :
                securityStatistics.getCategoryCounts().entrySet()) {

            System.out.printf(
                    "%-25s %,d%n",
                    entry.getKey(),
                    entry.getValue()
            );
        }

        System.out.println("\nIndividual indicator counts:");

        for (Map.Entry<String, Integer> entry :
                securityStatistics.getIndicatorCounts().entrySet()) {

            System.out.printf(
                    "%-25s %,d%n",
                    entry.getKey(),
                    entry.getValue()
            );
        }

        System.out.println("\nExample inscription IDs:");

        for (Map.Entry<String, List<String>> entry :
                securityStatistics.getExampleIds().entrySet()) {

            System.out.println("\n" + entry.getKey());

            for (String id : entry.getValue()) {
                System.out.println("  " + id);
            }
        }

        System.out.println("\n==============================");
        System.out.println("INSCRIPTION REFERENCE ANALYSIS");
        System.out.println("==============================");

        System.out.println("Scanning references...");

        ReferenceStatistics referenceStatistics =
                reader.scanReferences("data/text_inscriptions.txt");

        System.out.println("Reference scan done.");

        System.out.println(
                "\nInscriptions with references: "
                        + String.format(
                        "%,d",
                        referenceStatistics.getInscriptionsWithReferences()
                )
        );

        System.out.println(
                "Total references: "
                        + String.format(
                        "%,d",
                        referenceStatistics.getTotalReferences()
                )
        );

        System.out.println(
                "Unique referenced inscriptions: "
                        + String.format(
                        "%,d",
                        referenceStatistics.getUniqueReferencedCount()
                )
        );

        System.out.println("\nChecking referenced targets...");

        Set<String> datasetIds =
                reader.readAllIds("data/text_inscriptions.txt");

        int present = 0;
        int missing = 0;

        for (String targetId :
                referenceStatistics.getUniqueReferencedIds()) {

            if (datasetIds.contains(targetId)) {
                present++;
            } else {
                missing++;
            }
        }

        System.out.println(
                "Referenced targets present in dataset: "
                        + String.format("%,d", present)
        );

        System.out.println(
                "Referenced targets missing from dataset: "
                        + String.format("%,d", missing)
        );

        System.out.println("\nTop 10 most referenced inscriptions:");

        referenceStatistics.getTargetFrequency()
                .entrySet()
                .stream()
                .sorted(
                        Map.Entry.<String, Integer>comparingByValue()
                                .reversed()
                )
                .limit(10)
                .forEach(entry ->
                        System.out.printf(
                                "%,6d  %s%n",
                                entry.getValue(),
                                entry.getKey()
                        )
                );

        DependencyGraphAnalyzer graphAnalyzer = new DependencyGraphAnalyzer(referenceStatistics.getReferences());

        int maximumDepth = graphAnalyzer.getMaximumDepth();

        System.out.println("Maximum observable dependency depth: " + maximumDepth);

        System.out.println(
                "\nDependency chains with depth >= 2:"
        );

        graphAnalyzer.printChainsWithMinimumDepth(2);



        CsvExporter exporter = new CsvExporter();
        exporter.exportOtherContents(statistics.getOtherContents(), "output/other_contents.csv");
        exporter.exportContentFrequency(statistics.getContentFrequency(), "output/content_frequency.csv");
        exporter.exportMonthlyStatistics(statistics.getMonthlyActivity(), statistics.getUniqueActivity(), "output/monthly_statistics.csv");
        exporter.exportContentTypes(statistics.getContentTypes(), statistics.getTotalCount(), "output/content_types.csv");
        exporter.exportAverageContentLengthPerType(statistics.getContentTypes(), statistics.getContentTypeLengths(), "output/average_content_length_per_type.csv");
        exporter.exportSecurityCandidates(securityStatistics.getCandidates(), "output/security_candidates.csv");
        exporter.exportBehavioralCandidates(behavioralStatistics.getCandidates(), "output/behavioral_candidates.csv");
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

    private static void printAverageContentLengthPerType(InscriptionStatistics statistics) {

        System.out.println("\nAverage Content Length per Type:");

        Map<String, Integer> contentTypes = statistics.getContentTypes();
        Map<String, Long> contentTypeLengths = statistics.getContentTypeLengths();

        System.out.printf("%-18s %-20s%n", "Type", "Average Length (bytes)");

        for (String type : contentTypes.keySet()) {

            int count = contentTypes.get(type);
            long totalLength = contentTypeLengths.get(type);

            double averageLength = (double) totalLength / count;

            System.out.printf("%-18s %.2f%n", type, averageLength);
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
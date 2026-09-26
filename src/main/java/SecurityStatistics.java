import java.util.*;

public class SecurityStatistics {

    private int inscriptionsWithAnyMatch = 0;

    private final Map<String, Integer> categoryCounts =
            new LinkedHashMap<>();

    private final Map<String, Integer> indicatorCounts =
            new LinkedHashMap<>();

    private final Map<String, List<String>> exampleIds =
            new LinkedHashMap<>();

    private final List<SecurityCandidate> candidates =
            new ArrayList<>();

    private static final int MAX_EXAMPLES = 20;




    public void process(
            Inscription inscription,
            Map<String, List<String>> matches
    ) {

        if (matches.isEmpty()) {
            return;
        }
        if (matches.containsKey("SYSTEM_ACCESS")
                || matches.containsKey("EMAIL_PROPAGATION")) {

            candidates.add(
                    new SecurityCandidate(inscription, matches)
            );
        }

        inscriptionsWithAnyMatch++;

        for (Map.Entry<String, List<String>> entry : matches.entrySet()) {

            String category = entry.getKey();

            categoryCounts.merge(category, 1, Integer::sum);

            exampleIds.putIfAbsent(
                    category,
                    new ArrayList<>()
            );

            List<String> ids = exampleIds.get(category);

            if (ids.size() < MAX_EXAMPLES) {
                ids.add(inscription.getId());
            }

            for (String indicator : entry.getValue()) {
                indicatorCounts.merge(
                        indicator,
                        1,
                        Integer::sum
                );
            }
        }
    }


    public int getInscriptionsWithAnyMatch() {
        return inscriptionsWithAnyMatch;
    }

    public Map<String, Integer> getCategoryCounts() {
        return categoryCounts;
    }

    public Map<String, Integer> getIndicatorCounts() {
        return indicatorCounts;
    }

    public Map<String, List<String>> getExampleIds() {
        return exampleIds;
    }

    public List<SecurityCandidate> getCandidates() {
        return candidates;
    }
}
import java.util.*;

public class BehavioralSecurityStatistics {

    private int inscriptionsWithAnyBehavior = 0;

    private final Map<String, Integer> behaviorCounts =
            new LinkedHashMap<>();

    private final Map<String, List<String>> exampleIds =
            new LinkedHashMap<>();

    private final List<SecurityCandidate> candidates =
            new ArrayList<>();

    private static final int MAX_EXAMPLES = 20;


    public void process(
            Inscription inscription,
            Map<String, List<String>> behaviors
    ) {

        if (behaviors.isEmpty()) {
            return;
        }

        inscriptionsWithAnyBehavior++;

        for (String behavior : behaviors.keySet()) {

            behaviorCounts.merge(
                    behavior,
                    1,
                    Integer::sum
            );

            List<String> ids =
                    exampleIds.computeIfAbsent(
                            behavior,
                            key -> new ArrayList<>()
                    );

            if (ids.size() < MAX_EXAMPLES) {
                ids.add(inscription.getId());
            }
        }

        /*
         * SCRIPT_EXECUTION by itself is extremely common.
         *
         * For candidate analysis we are interested in inscriptions
         * containing at least one stronger behavior.
         */

        boolean strongCandidate =
                behaviors.containsKey("FILE_SYSTEM_ACCESS")
                        || behaviors.containsKey("REGISTRY_MODIFICATION")
                        || behaviors.containsKey("PROCESS_EXECUTION")
                        || behaviors.containsKey("EMAIL_AUTOMATION")
                        || behaviors.containsKey("NETWORK_ACCESS")
                        || behaviors.containsKey("OBFUSCATION");

        if (strongCandidate) {

            candidates.add(
                    new SecurityCandidate(
                            inscription,
                            behaviors
                    )
            );
        }
    }


    public int getInscriptionsWithAnyBehavior() {
        return inscriptionsWithAnyBehavior;
    }


    public Map<String, Integer> getBehaviorCounts() {
        return behaviorCounts;
    }


    public Map<String, List<String>> getExampleIds() {
        return exampleIds;
    }


    public List<SecurityCandidate> getCandidates() {
        return candidates;
    }
}
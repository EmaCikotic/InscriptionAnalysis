import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ReferenceStatistics {

    private int inscriptionsWithReferences = 0;
    private int totalReferences = 0;

    private final Set<String> uniqueReferencedIds = new HashSet<>();
    private final Map<String, Set<String>> references = new HashMap<>();

    public void addReference(String sourceId, String targetId) {

        references.computeIfAbsent(sourceId, key -> new HashSet<>()).add(targetId);

        uniqueReferencedIds.add(targetId);
        totalReferences++;
    }

    public void markInscriptionWithReference() {
        inscriptionsWithReferences++;
    }

    public int getInscriptionsWithReferences() {
        return inscriptionsWithReferences;
    }

    public int getTotalReferences() {
        return totalReferences;
    }

    public int getUniqueReferencedCount() {
        return uniqueReferencedIds.size();
    }

    public Map<String, Set<String>> getReferences() {
        return references;
    }
}
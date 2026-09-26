import java.util.List;
import java.util.Map;

public class SecurityCandidate {

    private final Inscription inscription;
    private final Map<String, List<String>> matches;

    public SecurityCandidate(Inscription inscription, Map<String, List<String>> matches) {
        this.inscription = inscription;
        this.matches = matches;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public Map<String, List<String>> getMatches() {
        return matches;
    }
}
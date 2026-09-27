import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReferenceScanner {

    private static final Pattern CONTENT_REFERENCE =
            Pattern.compile("/content/([a-fA-F0-9]{64}i\\d+)");

    public Set<String> findReferences(String content) {

        Set<String> references = new HashSet<>();

        if (content == null || content.isBlank()) {
            return references;
        }

        Matcher matcher = CONTENT_REFERENCE.matcher(content);

        while (matcher.find()) {
            references.add(matcher.group(1));
        }

        return references;
    }
}
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SecurityScanner {

    private final Map<String, List<String>> indicators = new LinkedHashMap<>();

    public SecurityScanner() {

        indicators.put("WEB_SCRIPT", List.of(
                "<script",
                "javascript:",
                "vbscript",
                "<iframe",
                "eval(",
                "document.",
                "window."
        ));

        indicators.put("URL", List.of(
                "http://",
                "https://"
        ));

        indicators.put("SYSTEM_ACCESS", List.of(
                "createobject",
                "filesystemobject",
                "wscript",
                "regwrite",
                "regdelete"
        ));

        indicators.put("COMMAND_EXECUTION", List.of(
                "powershell",
                "cmd.exe",
                "shell.application"
        ));

        indicators.put("EMAIL_PROPAGATION", List.of(
                "outlook.application",
                "mapi"
        ));
    }

    public Map<String, List<String>> scan(String content) {

        Map<String, List<String>> matches = new LinkedHashMap<>();

        if (content == null || content.isBlank()) {
            return matches;
        }

        String lowerContent = content.toLowerCase();

        for (Map.Entry<String, List<String>> category : indicators.entrySet()) {

            for (String indicator : category.getValue()) {

                if (lowerContent.contains(indicator)) {
                    matches.computeIfAbsent(category.getKey(), key -> new java.util.ArrayList<>()).add(indicator);
                }
            }
        }

        return matches;
    }
}
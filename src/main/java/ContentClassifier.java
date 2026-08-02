public class ContentClassifier {

    public String classify(String content) {
        if (content == null || content.isBlank()) {
            return "Empty";
        }

        String strippedContent = content.stripLeading();
        String lowerContent = content.toLowerCase();

        if (content.startsWith("/content/")) {
            return "Reference";
        }

        if (strippedContent.startsWith("<")) {
            return "HTML";
        }

        if (strippedContent.startsWith("{")) {
            return "JSON";
        }

        if (strippedContent.startsWith("http://")
                || strippedContent.startsWith("https://")) {
            return "URL";
        }

        if (lowerContent.endsWith(".bitmap")) {
            return "Bitmap";
        }

        if (content.matches(".*\\.[A-Za-z0-9]+$")) {
            return "Namespace";
        }

        if (content.codePointCount(0, content.length()) == 1) {
            return "Single Character";
        }

        return "Other";
    }
}
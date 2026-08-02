import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class InscriptionStatistics {

    private int totalCount = 0;
    private long earliestTimestamp = Long.MAX_VALUE;
    private long latestTimestamp = Long.MIN_VALUE;

    private final Map<YearMonth, Integer> monthlyActivity = new TreeMap<>();
    private final Map<YearMonth, Set<String>> uniqueActivity = new TreeMap<>();
    private final Map<String, Integer> contentFrequency = new HashMap<>();
    private final Map<String, Integer> contentTypes = new TreeMap<>();
    private final Set<String> otherContents = new HashSet<>();

    public void process(Inscription inscription, String type) {

        long timestamp = inscription.getTimestamp();
        String content = inscription.getContent();

        LocalDate date = Instant.ofEpochSecond(timestamp).atZone(ZoneOffset.UTC).toLocalDate();

        YearMonth month = YearMonth.from(date);

        // inscriptions per month
        if (monthlyActivity.containsKey(month)) {
            int currentCount = monthlyActivity.get(month);
            monthlyActivity.put(month, currentCount + 1);
        } else {
            monthlyActivity.put(month, 1);
        }

        // unique contents per month
        if (!uniqueActivity.containsKey(month)) {
            uniqueActivity.put(month, new HashSet<>());
        }

        Set<String> contentsForMonth = uniqueActivity.get(month);
        contentsForMonth.add(content);

        // Count how often each non-empty content appears
        if (content != null && !content.isBlank()) {
            if (contentFrequency.containsKey(content)) {
                int currentFrequency = contentFrequency.get(content);
                contentFrequency.put(content, currentFrequency + 1);
            } else {
                contentFrequency.put(content, 1);
            }
        }

        // Count how many inscriptions belong to each content type
        if (contentTypes.containsKey(type)) {
            int currentTypeCount = contentTypes.get(type);
            contentTypes.put(type, currentTypeCount + 1);
        } else {
            contentTypes.put(type, 1);
        }


        if (type.equals("Other")) {
            otherContents.add(content);
        }


        if (timestamp < earliestTimestamp) {
            earliestTimestamp = timestamp;
        }


        if (timestamp > latestTimestamp) {
            latestTimestamp = timestamp;
        }

        totalCount++;
    }

    public LocalDate getEarliestDate() {
        return Instant.ofEpochSecond(earliestTimestamp).atZone(ZoneOffset.UTC).toLocalDate();
    }

    public LocalDate getLatestDate() {
        return Instant.ofEpochSecond(latestTimestamp).atZone(ZoneOffset.UTC).toLocalDate();
    }

    public int getTotalCount() {
        return totalCount;
    }

    public Map<YearMonth, Integer> getMonthlyActivity() {
        return monthlyActivity;
    }

    public Map<YearMonth, Set<String>> getUniqueActivity() {
        return uniqueActivity;
    }

    public Map<String, Integer> getContentFrequency() {
        return contentFrequency;
    }

    public Map<String, Integer> getContentTypes() {
        return contentTypes;
    }

    public Set<String> getOtherContents() {
        return otherContents;
    }
}
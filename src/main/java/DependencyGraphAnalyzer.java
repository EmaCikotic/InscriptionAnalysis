import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DependencyGraphAnalyzer {

    private final Map<String, Set<String>> graph;

    public DependencyGraphAnalyzer(Map<String, Set<String>> references) {
        this.graph = references;
    }

    public int getMaximumDepth() {

        int maxDepth = 0;

        for (String node : graph.keySet()) {

            int depth = calculateDepth(
                    node,
                    new HashSet<>()
            );

            maxDepth = Math.max(maxDepth, depth);
        }

        return maxDepth;
    }

    private int calculateDepth(
            String node,
            Set<String> currentPath) {

        // No outgoing references
        if (!graph.containsKey(node)) {
            return 0;
        }

        // Cycle
        if (currentPath.contains(node)) {
            return 0;
        }

        currentPath.add(node);

        int maxChildDepth = 0;

        for (String target : graph.get(node)) {

            int childDepth =
                    calculateDepth(
                            target,
                            currentPath
                    );

            maxChildDepth =
                    Math.max(
                            maxChildDepth,
                            childDepth + 1
                    );
        }

        currentPath.remove(node);

        return maxChildDepth;
    }

    public void printChainsWithMinimumDepth(int minimumDepth) {

        for (String source : graph.keySet()) {

            printChains(
                    source,
                    new ArrayList<>(),
                    new HashSet<>(),
                    minimumDepth
            );
        }
    }

    private void printChains(
            String node,
            List<String> path,
            Set<String> visited,
            int minimumDepth) {

        if (visited.contains(node)) {
            return;
        }

        visited.add(node);
        path.add(node);

        Set<String> targets = graph.get(node);

        if (targets == null || targets.isEmpty()) {

            if (path.size() - 1 >= minimumDepth) {

                System.out.println(
                        String.join(" -> ", path)
                );
            }

        } else {

            for (String target : targets) {

                printChains(
                        target,
                        path,
                        visited,
                        minimumDepth
                );
            }
        }

        path.remove(path.size() - 1);
        visited.remove(node);
    }
}
import java.util.*;

public class GraphProblem {

    public static void main(String args[]) {
        Graph graph = new Graph();
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("B", "E");
        graph.addEdge("C", "D");
        graph.addEdge("C", "E");
        graph.addEdge("C", "G");
        graph.addEdge("D", "E");
        graph.addEdge("E", "F");
        graph.addEdge("E", "2424b");
        graph.addEdge("2323a", "2424b");
        bfs(graph, "A");
        print(null);
        shortestPath(graph, "B", "G");
        print(null);
        shortestPath(graph, "G", "A");
        print(null);
        findPaths(graph, "A", "E", 3);
        print(null);
        findPaths(graph, "B", "2323a", 3);

    }

    private static class Graph {
        private Map<String, List<String>> neighbours = new HashMap<>();

        public void addEdge(String vertex1, String vertex2) {
            neighbours.computeIfAbsent(vertex1, key -> {
                return new LinkedList<>();
            }).add(vertex2);
            neighbours.computeIfAbsent(vertex2, key -> {
                return new LinkedList<>();
            }).add(vertex1);
        }
    }

    private static final void bfs(Graph graph, String source) {
        class BfsNode {
            private String vertex;
            private int count;

            private BfsNode(String vertex, int count) {
                this.vertex = vertex;
                this.count = count;
            }

        }

        List<BfsNode> list = new ArrayList<>();
        list.add(new BfsNode(source, 0));

        Set<String> visited = new HashSet<>();
        visited.add(source);

        int tail = 0;
        BfsNode tempBfsNode = null;

        while (tail < list.size()) {
            tempBfsNode = list.get(tail++);
            final int tempCount = tempBfsNode.count;
            print("distance from " + source + " to " + tempBfsNode.vertex + " : " + tempCount);
            List<String> neighoursList = graph.neighbours.get(tempBfsNode.vertex);
            if (neighoursList == null) {
                continue;
            }
            neighoursList.forEach(c -> {
                if (!visited.contains(c)) {
                    visited.add(c);
                    list.add(new BfsNode(c, tempCount + 1));
                }
            });
        }
    }

    private static final void shortestPath(Graph graph, String source, String target) {
        findPaths(graph, source, target, 1);
    }

    private static final void findThreePath(Graph graph, String source, String target) {
        findPaths(graph, source, target, 3);
    }

    private static final void findPaths(Graph graph, String source, String target, int count) {
        int tempCount = 0;
        Set<String> visited = new HashSet<>();
        if (source.equals(target)) {
            tempCount++;
            print(source.toString());
            if (tempCount >= count) {
                return;
            }
        } else {
            visited.add(source);
        }

        List<String> list = new ArrayList<>();
        list.add(source);


        Map<String, String> predecessor = new HashMap<>();
        int tail = 0;


        while (tail < list.size()) {
            String vertex = list.get(tail++);
            List<String> neighoursList = graph.neighbours.get(vertex);
            if (neighoursList == null) {
                continue;
            }
            for (String c : neighoursList) {
                if (!visited.contains(c)) {
                    if (c.equals(target)) {
                        StringBuilder path = new StringBuilder().append(c);
                        tempCount++;
                        String temp = vertex;
                        while (!temp.equals(source)) {
                            path.append(" >- ");
                            path.append(temp);
                            temp = predecessor.get(temp);
                        }
                        path.append(" >- ");
                        path.append(temp);
                        print(path.reverse().toString());
                        if (tempCount >= count) {
                            return;
                        }
                    } else {
                        visited.add(c);
                    }
                    predecessor.put(c, vertex);
                    list.add(c);
                }
            }
        }
    }


    private static void print(String str) {
        System.out.println(str == null ? "" : str);
    }
}
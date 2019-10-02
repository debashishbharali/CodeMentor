import java.util.*;

public class GraphProblem {

    public static void main(String args[]) {
        Graph graph = new Graph();
        graph.addEdge('A', 'B');
        graph.addEdge('B', 'C');
        graph.addEdge('B', 'E');
        graph.addEdge('C', 'D');
        graph.addEdge('C', 'E');
        graph.addEdge('C', 'G');
        graph.addEdge('D', 'E');
        graph.addEdge('E', 'F');
        bfs(graph, 'A');
        print(null);
        shortestPath(graph, 'B', 'G');
        print(null);
        shortestPath(graph, 'G', 'A');
        print(null);
        findPaths(graph, 'A', 'E', 3);
        print(null);
        findPaths(graph, 'B', 'B', 3);

    }

    private static class Graph {
        private Map<Character, List<Character>> neighbours = new HashMap<>();

        public void addEdge(Character vertex1, Character vertex2) {
            neighbours.computeIfAbsent(vertex1, key -> {
                return new LinkedList<>();
            }).add(vertex2);
            neighbours.computeIfAbsent(vertex2, key -> {
                return new LinkedList<>();
            }).add(vertex1);
        }
    }

    private static final void bfs(Graph graph, Character source) {
        class BfsNode {
            private Character vertex;
            private int count;

            private BfsNode(Character vertex, int count) {
                this.vertex = vertex;
                this.count = count;
            }

        }

        List<BfsNode> list = new ArrayList<>();
        list.add(new BfsNode(source, 0));

        Set<Character> visited = new HashSet<>();
        visited.add(source);

        int tail = 0;
        BfsNode tempBfsNode = null;

        while (tail < list.size()) {
            tempBfsNode = list.get(tail++);
            final int tempCount = tempBfsNode.count;
            print("distance from " + source + " to " + tempBfsNode.vertex + " : " + tempCount);
            List<Character> neighoursList = graph.neighbours.get(tempBfsNode.vertex);
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

    private static final void shortestPath(Graph graph, Character source, Character target) {
        findPaths(graph, source, target, 1);
    }

    private static final void findThreePath(Graph graph, Character source, Character target) {
        findPaths(graph, source, target, 3);
    }

    private static final void findPaths(Graph graph, Character source, Character target, int count) {
        int tempCount = 0;
        Set<Character> visited = new HashSet<>();
        if (source.equals(target)) {
            tempCount++;
            print(source.toString());
            if (tempCount >= count) {
                return;
            }
        } else {
            visited.add(source);
        }

        List<Character> list = new ArrayList<>();
        list.add(source);




        Map<Character, Character> predecessor = new HashMap<>();
        int tail = 0;


        while (tail < list.size()) {
            Character vertex = list.get(tail++);
            List<Character> neighoursList = graph.neighbours.get(vertex);
            if (neighoursList == null) {
                continue;
            }
            for (Character c : neighoursList) {
                if (!visited.contains(c)) {
                    if (c.equals(target)) {
                        StringBuilder path = new StringBuilder().append(c);
                        tempCount++;
                        Character temp = vertex;
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


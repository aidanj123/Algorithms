import java.util.Scanner;
public class testDFS {
    public static void main(String[] args) {
        System.out.println("Enter the number of nodes in the graph: ");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println("Enter the edges of the graph: ");
        int e = scanner.nextInt();

        graph g = new graph(n);
        for (int i = 0; i < e; i++) {
            int sourceIndex = (int) (Math.random() * n);
            int destinationIndex = (int) (Math.random() * n);
            node source = g.nodes[sourceIndex];
            node destination = g.nodes[destinationIndex];
            if (g.adjacencyList[sourceIndex].contains(destination) || source == destination) {
                i--;
            } else {
                g.addEdge(source, destination);
            }
        }

        double startTime = System.nanoTime();
        g.dfs();
        double endTime = System.nanoTime();
        System.out.println("DFS completed in: " + (endTime - startTime));

        

        scanner.close();
    }
}
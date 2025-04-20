import java.util.LinkedList;

public class graph {
    int n; 
    public node[] nodes;
    public LinkedList<node>[] adjacencyList;

    public graph(int n) {
        this.n = n; 
        adjacencyList = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
        nodes = new node[n]; 
        for (int i = 0; i < n; i++) {
            nodes[i] = new node("white", i, 0, 0);
        }
    }

    public void addEdge(node source, node destination) {
        adjacencyList[source.number].add(destination);
    }
    public void dfs() {
        int[] time = {0}; 
        for (int i = 0; i < n; i++) {
            if (nodes[i].color.equals("white")) {
                dfsVisit(nodes[i], time);
            }
        }
    }

    public void dfsVisit(node u, int[] time) {
        time[0]++;
        u.discoveryTime = time[0];
        u.color = "gray"; 
        for (node v : adjacencyList[u.number]) {
            if (v.color.equals("white")) { 
                dfsVisit(v, time);
            }
        }
        u.color = "black"; 
        time[0]++;
        u.finishTime = time[0];
    }
    
}

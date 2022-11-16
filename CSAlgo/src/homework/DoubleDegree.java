package homework;

import edu.princeton.cs.algs4.*;

public class DoubleDegree {

    public static Graph readG(String filename) {
        In file = new In(filename);
        int vertices = file.readInt();
        int edges = file.readInt();
        Graph g = new Graph(vertices);
        while (!file.isEmpty()) {
            int v = file.readInt();
            int u = file.readInt();
            g.addEdge(v - 1, u - 1);
        }
        return g;
    }

    public static void main(String[] args) {
        Graph g = readG(args[0]);
        for (int i = 0; i < g.V(); i++) {
            StdOut.print(SumOfNeighbors(g, i) + " ");
        }
    }


    public static int SumOfNeighbors(Graph G, int i){
        int result = 0;
        for(int c: G.adj(i)){
            result += G.degree(c);
        }
        return result;
    }
}
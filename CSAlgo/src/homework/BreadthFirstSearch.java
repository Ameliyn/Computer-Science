package homework;

import edu.princeton.cs.algs4.*;

public class BreadthFirstSearch {

    public static Digraph readG(String filename) {
        In file = new In(filename);
        int vertices = file.readInt();
        int edges = file.readInt();
        Digraph g = new Digraph(vertices);
        while (!file.isEmpty()) {
            int v = file.readInt();
            int u = file.readInt();
            g.addEdge(v - 1, u - 1);
        }
        return g;
    }

    public static void main(String[] args) {
        Digraph g = readG(args[0]);
        BreadthFirstDirectedPaths b = new BreadthFirstDirectedPaths(g, 0);
        for(int i = 0; i < g.V(); i++){
            int dist = b.distTo(i);
            if(dist == 2147483647) dist = -1;
            StdOut.print(dist + " ");
        }
    }
}
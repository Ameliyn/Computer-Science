package homework;

import edu.princeton.cs.algs4.*;

public class TopologicGraph {

    public static Digraph readG(String filename) {
        In file = new In(filename);
        int vertices = file.readInt() + 1;
        int edges = file.readInt();
        Digraph g = new Digraph(vertices);
        while (!file.isEmpty()) {
            int v = file.readInt();
            int u = file.readInt();
            g.addEdge(v, u);
        }
        return g;
    }

    public static void main(String args[]){
        Digraph g = readG(args[0]);

        Topological t = new Topological(g);
        StdOut.println(t.order());
    }
}

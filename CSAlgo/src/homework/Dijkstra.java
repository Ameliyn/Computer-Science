package homework;

import edu.princeton.cs.algs4.*;

public class Dijkstra {

    public static EdgeWeightedDigraph readG(String filename) {
        In file = new In(filename);
        int vertices = file.readInt();
        int edges = file.readInt();
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(vertices);
        while (!file.isEmpty()) {
            int tail = file.readInt();
            int head = file.readInt();
            int weight = file.readInt();
            DirectedEdge d = new DirectedEdge(tail-1, head-1, weight);
            g.addEdge(d);
        }
        return g;
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph g = readG(args[0]);
        DijkstraSP b = new DijkstraSP(g, 0);
        for(int i = 0; i < g.V(); i++){
            double dist = b.distTo(i);
            if(dist == Double.POSITIVE_INFINITY) dist = -1;
            StdOut.print((int)dist + " ");
        }
    }
}
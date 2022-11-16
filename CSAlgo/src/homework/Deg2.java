package homework;

import edu.princeton.cs.algs4.*;

public class Deg2 {

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
//        StdOut.println(DegreeList(g));
        for (int a: DegreeList(g)) {
            StdOut.print(a + " ");
        }
    }

    public static int[] DegreeList(Graph g){
        int[] result = new int[g.V()];
        for(int i = 0; i < g.V(); i++){
            result[i] = g.degree(i);
        }
        g.V();
        return result;
    }

}
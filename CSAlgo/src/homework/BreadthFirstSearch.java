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
        StdOut.println(g);
    }

    private java.util.ArrayList<Integer> visitedLocations = new java.util.ArrayList<Integer>();
    public static int shortestPath(Digraph g, int to, int from){
        int numberSteps = 0;
        if(to == from) return numberSteps;
        else{
            //for everything adjacent to us
            for(int c: g.adj(from)){
                numberSteps = 1 + shortestPath(g, to, c);
            }
        }
        return numberSteps;
    }
}
package homework;

import edu.princeton.cs.algs4.*;

public class ConnectedComponents {

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
    private static Integer[] representative;

    public static void main(String args[]){
        Graph g = readG(args[0]);
        CC con = new CC(g);
        StdOut.println(con.count());
//
//        representative = new Integer[g.V()];
//
//        for(int i = 0; i < representative.length; i++){
//            representative[i] = i;
//        }
//
//        int numConnected = 0;
//        for(int i = 0; i < g.V(); i++){
//            if(representative[i] == i){
//                Explore(g, i, i);
//                numConnected++;
//            }
//        }
//
//        StdOut.println(numConnected + " ");
//
    }

//    private static void Explore(Digraph g, int root, int rep){
//        representative[root] = rep;
//        for(int c: g.adj(root)){
//            if(representative[c] != rep)
//                Explore(g, c, rep);
//        }
//    }
}

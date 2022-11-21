package homework;

import edu.princeton.cs.algs4.*;

import java.util.ArrayDeque;

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
        for(int i = 0; i < g.V(); i++){
            StdOut.print(BFS(g, i, 0) + " ");
        }
    }

    public static int BFS(Digraph g, int to, int from)
    {
        if(g.indegree(to) == 0) return -1;
        int[] weights = new int[g.V()];
        weights[from] = 0;
        Queue<Integer> q =  new Queue<>();


        q.enqueue(from);
        boolean[] discovered = new boolean[g.V()];
        while(!q.isEmpty()){
            if(to == from) return weights[from];
            discovered[from] = true;

            //if not the right place, enqueue the adjacent values
            for(int c: g.adj(from)){
                if(!discovered[c]){
                    weights[c] = weights[from] + 1;
                    q.enqueue(c);
                }
            }
            from = q.dequeue();
        }

        return -1;
    }
}
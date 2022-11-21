package homework;

import edu.princeton.cs.algs4.*;

public class Acyclicity {

    public static Digraph[] readG(String filename) {
        In file = new In(filename);

        String[] allLines = file.readAllLines();
        file.close();

        int numGraphs = Integer.parseInt(allLines[0]);
        Digraph[] g = new Digraph[numGraphs];

        int lineNumber = 2;
        String[] s = allLines[lineNumber].split(" ");
        lineNumber++;
        for (int i = 0; i < numGraphs; i++) {
            int vertices = Integer.parseInt(s[0]);
            int edges = Integer.parseInt(s[1]);
            g[i] = new Digraph(vertices);
            s = allLines[lineNumber].split(" ");
            lineNumber++;

            for(int j = 0; j < edges; j++) {
                int tail = Integer.parseInt(s[0]);
                int head = Integer.parseInt(s[1]);
                g[i].addEdge(tail-1, head-1);
                if (lineNumber == allLines.length) break;
                s = allLines[lineNumber].split(" ");
                lineNumber++;
            }
            if (lineNumber == allLines.length) break;
            s = allLines[lineNumber].split(" ");
            lineNumber++;
        }
        return g;
    }

    public static void main(String args[]){
        Digraph[] graphs = readG(args[0]);
        for(Digraph g : graphs){
            StdOut.print(Acyclic(g) + " ");
        }
    }

    public static int Acyclic(Digraph g){
        DirectedCycle c = new DirectedCycle(g);
        if(c.hasCycle()) return -1;
        else return 1;
    }

}

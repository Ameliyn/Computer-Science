package homework;

import edu.princeton.cs.algs4.*;

//@author Skye Russ
public class ShortestCycle {

    private static DirectedEdge[] firstEdges;

    public static EdgeWeightedDigraph[] readG(String filename) {
        In file = new In(filename);

        /*
        * Note to anyone looking at this. Running through the input in the format that they provided on
        * Rosalind was giving me LOTS OF GRIEF. I originally had it checking for empty strings to change graphs and read
        * in graphs like that.
        *
        * The only way I got this to work was to store all the lines in a MASSIVE string array which isn't ideal for
        * RAM usage but whatever, it works.
        */

        String[] allLines = file.readAllLines();
        file.close();


        int numGraphs = Integer.parseInt(allLines[0]);
        EdgeWeightedDigraph[] g = new EdgeWeightedDigraph[numGraphs];
        firstEdges = new DirectedEdge[numGraphs];

        boolean[] first = new boolean[numGraphs];
        int lineNumber = 1;
        String[] s = allLines[lineNumber].split(" ");
        lineNumber++;
        for(int i = 0; i < numGraphs; i++){
            int vertices = Integer.parseInt(s[0]);
            g[i] = new EdgeWeightedDigraph(vertices);
            s = allLines[lineNumber].split(" ");
            lineNumber++;

            while(s.length == 3){
                int tail = Integer.parseInt(s[0]);
                int head = Integer.parseInt(s[1]);
                int weight = Integer.parseInt(s[2]);
                if(!first[i]){
                    firstEdges[i] = new DirectedEdge(tail-1, head-1, weight);
                    first[i] = true;
                }
                DirectedEdge d = new DirectedEdge(tail-1, head-1, weight);
                g[i].addEdge(d);
                if(lineNumber == allLines.length) break;
                s = allLines[lineNumber].split(" ");
                lineNumber++;
            }
        }

        return g;
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph[] digraphs = readG(args[0]);
        for(int i = 0; i < digraphs.length; i++){
            DijkstraSP b = new DijkstraSP(digraphs[i], firstEdges[i].to());
            double dist = b.distTo(firstEdges[i].from()) + firstEdges[i].weight();
            if(dist == Double.POSITIVE_INFINITY) dist = -1;
            StdOut.print((int)dist + " ");
        }
    }

}

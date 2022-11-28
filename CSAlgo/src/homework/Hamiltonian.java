package homework;

import edu.princeton.cs.algs4.*;

public class Hamiltonian {

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
            StdOut.println(CheckHamiltonian(g));
        }
    }

    public static String CheckHamiltonian(Digraph g){
        Topological t = new Topological(g);
        String result = "1 ";
        int prev = -1;
        boolean flag;
        for(int vertex : t.order()){
            if(prev != -1){
                flag = false;
                for(int c : g.adj(prev)){
                    if(c == vertex){
                        flag = true;
                        break;
                    }
                }
                if(!flag) return "-1";
            }
            prev = vertex;
            result += (prev + 1) + " ";
        }
        return result;
    }
}

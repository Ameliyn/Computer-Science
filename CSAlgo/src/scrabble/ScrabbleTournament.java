package scrabble;

import edu.princeton.cs.algs4.StdOut;

/** A tournament between ScrabbleAIs. Edit the constructor to change the contestants. */
public class ScrabbleTournament {

    /** Contestants. */
    private ScrabbleAI[] players;

    public ScrabbleTournament() {
        // List contestants here
        players = new ScrabbleAI[] {
          new BrutusV2_0(),
          new BrutusV2_0()
        };
    }

    public static void main(String[] args) throws IllegalMoveException {
        new ScrabbleTournament().runMany(8);
    }

    public void runMany(int numRuns) throws IllegalMoveException{
        double[] scores = new double[players.length];
        double[] values = new double[players.length];
        for(int i = 0; i < numRuns; i++){
            if(i%2 == 0){
                double[] result = playGame(players[0], players[1]);
                scores[0] += result[0];
                scores[1] += result[1];
                values[0] += result[2];
                values[1] += result[3];
            }
            else{
                double[] result = playGame(players[1], players[0]);
                scores[0] += result[1];
                scores[1] += result[0];
                values[0] += result[3];
                values[1] += result[2];
            }
        }
        for (int i = 0; i < players.length; i++) {
            StdOut.println(players[i].toString() + ": " + scores[i] + ", Avg: " + ((float)values[i] / numRuns));
        }
    }

    /**
     * Plays two games between each pair of contestants, one with each going first. Prints the number of wins for
     * each contestant (including 0.5 wins for each tie).
     */
    public void run() throws IllegalMoveException {
        double[] scores = new double[players.length];
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < players.length; j++) {
                if (i != j) {
                    double[] result = playGame(players[i], players[j]);
                    scores[i] += result[0];
                    scores[j] += result[1];
                }
            }
        }
        for (int i = 0; i < players.length; i++) {
            StdOut.println(players[i].toString() + ": " + scores[i]);
        }
    }

    /**
     * Plays a game between a (going first) and b. Returns their tournament scores, either {1, 0} (if a wins),
     * {0, 1}, or {0.5, 0.5}.
     */
    public double[] playGame(ScrabbleAI a, ScrabbleAI b) throws IllegalMoveException {
        StdOut.println(a + " vs " + b + ":");
        Board board = new Board();
        a.setGateKeeper(new GateKeeper(board, 0));
        b.setGateKeeper(new GateKeeper(board, 1));
        long t1 = 0;//skye added
        long t2 = 0;//skye added
        long tempStart;//skye added
        int numMoves1 = 0;
        int numMoves2 = 0;
        while (!board.gameIsOver()) {
            tempStart = System.nanoTime();//skye added
            playMove(board, a, 0);
            t1 += System.nanoTime() - tempStart;//skye added
            numMoves1++;
            if (!board.gameIsOver()) {
                tempStart = System.nanoTime();//skye added
                playMove(board, b, 1);
                t2 += System.nanoTime() - tempStart;//skye added
                numMoves2++;
            }
        }
        int s0 = board.getScore(0);
        int s1 = board.getScore(1);
        StdOut.print(board);
        StdOut.println("Final score: " + a + " " + s0 + ", " + b + " " + s1);
        StdOut.println("Time Taken: " + a + " " + (float)t1/1000000000 + "s (" + ((float)t1 / (t1 + t2) * 100) + "%), " + b + " " + (float)t2/1000000000 + "s (" + ((float)t2 / (t1 + t2) * 100) + "%)"); //skye added
        StdOut.println("Avg Move Time: " + a + " " + (float)t1/numMoves1/1000000000 + "s, " + b + " " + (float)t2/numMoves2/1000000000 + "s");
        StdOut.println();
        if (s0 > s1) {
            return new double[] {1, 0, s0, s1};
        } else if (s0 < s1) {
            return new double[] {0, 1, s0, s1};
        }
        // Tie -- half credit to each player.
        return new double[] {0.5, 0.5, s0, s1};
    }

    /**
     * Asks player for a move and plays it on board.
     * @param playerNumber Player's place in the game turn order (0 or 1).
     */
    public void playMove(Board board, ScrabbleAI player, int playerNumber) throws IllegalMoveException {
        ScrabbleMove move = player.chooseMove();
        // This fixes a security hole where the AI player returns an instance of a new class implementing ScrabbleMove,
        // which then manipulates the Board.
        if (!(move instanceof PlayWord || move instanceof ExchangeTiles)){
            throw new IllegalMoveException("Bogus ScrabbleMove implementation detected!");
        }
        move.play(board, playerNumber);
    }

}

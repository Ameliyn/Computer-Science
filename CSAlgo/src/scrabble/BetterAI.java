package scrabble;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

public class BetterAI implements ScrabbleAI {
    private GateKeeper gatekeeper;
    private double[] charFrequency;

    public BetterAI(){
        charFrequency = new double[26];
        GetFreqArray();
    }

    @Override
    public void setGateKeeper(GateKeeper gateKeeper) {this.gatekeeper = gateKeeper;}

    @Override
    public ScrabbleMove chooseMove() {
        if (gatekeeper.getSquare(Location.CENTER) == Board.DOUBLE_WORD_SCORE) {
            return findTwoTileMove();
        }
        return findOneTileMove();
    }

    /**
     * GetFreqArray reads all of the valid inputs in scrabble and gets the frequencies of all of the characters
     */
    private void GetFreqArray(){
        In file = new In("words.txt");
        int[] counts = new int[26];
        int totalLetters = 0;
        while(!file.isEmpty()){
            String word = file.readLine();
            for(char c: word.toCharArray()){
                counts[c-'a']++;
                totalLetters++;
            }
        }
        for(int i = 0; i < counts.length; i++){
            charFrequency[i] = (double)counts[i] / totalLetters;
        }
    }

    /**
     * GetExchangeTiles gets the index of the least frequent tiles and exchanges them
     * @return boolean[] tiles to exchange
     */
    private boolean[] ExchangeMinimumFrequencyTiles(int numTiles){

        ArrayList<Character> hand = gatekeeper.getHand();
        if(numTiles == hand.size()){ return new boolean[]{true, true, true, true, true, true, true};}

        //Initialize minTiles
        int[] minTiles = new int[numTiles];
        for(int i = 0; i < numTiles; i++){ minTiles[i] = i;}

        //Find the minimum n tiles to replace
        for(int i = numTiles; i < hand.size(); i++){
            if(hand.get(i) == '_') continue;
            Character c = hand.get(i);
            for(int j = 0; j < numTiles; j++){
                if(hand.get(minTiles[j]) == '_') continue;
                if(charFrequency[c-'a'] < charFrequency[hand.get(minTiles[j])-'a']) {
                    minTiles[j] = i;
                    break;
                }
            }
        }
        boolean[] replace = new boolean[7];
        for(int c : minTiles){
            replace[c] = true;
        }
        return replace;
    }


    /** This is a copy from Incrementalist with exchanging three minimum frequency tiles */
    private ScrabbleMove findTwoTileMove() {
        ArrayList<Character> hand = gatekeeper.getHand();
        String bestWord = null;
        int bestScore = -1;
        for (int i = 0; i < hand.size(); i++) {
            for (int j = 0; j < hand.size(); j++) {
                if (i != j) {
                    try {
                        char a = hand.get(i);
                        char b = hand.get(j);
                        if (a == '_') {
                            if(b == '_'){
                                a = 'A';
                                for(int k = 0; k < 26; k++){
                                    b = 'A';
                                    for(int l = 0; l < 26; l++){
                                        String word = "" + (a+k) + (b+l);
                                        //try horizontal
                                        gatekeeper.verifyLegality(word, Location.CENTER, Location.HORIZONTAL);
                                        int score = gatekeeper.score(word, Location.CENTER, Location.HORIZONTAL);
                                        if (score > bestScore) {
                                            bestScore = score;
                                            bestWord = word;
                                        }
                                        //try vertical
                                        gatekeeper.verifyLegality(word, Location.CENTER, Location.VERTICAL);
                                        score = gatekeeper.score(word, Location.CENTER, Location.VERTICAL);
                                        if (score > bestScore) {
                                            bestScore = score;
                                            bestWord = word;
                                        }

                                    }
                                }
                            }
                            else{
                                a = 'A';
                                for(int k = 0; k < 26; k++){
                                    String word = "" + (a+k) + b;
                                    //try horizontal
                                    gatekeeper.verifyLegality(word, Location.CENTER, Location.HORIZONTAL);
                                    int score = gatekeeper.score(word, Location.CENTER, Location.HORIZONTAL);
                                    if (score > bestScore) {
                                        bestScore = score;
                                        bestWord = word;
                                    }
                                    //try vertical
                                    gatekeeper.verifyLegality(word, Location.CENTER, Location.VERTICAL);
                                    score = gatekeeper.score(word, Location.CENTER, Location.VERTICAL);
                                    if (score > bestScore) {
                                        bestScore = score;
                                        bestWord = word;
                                    }
                                }
                            }
                        }
                        else if (b == '_') {
                            b = 'A';
                            for(int k = 0; k < 26; k++){
                                String word = "" + a + (b+k);
                                //try horizontal
                                gatekeeper.verifyLegality(word, Location.CENTER, Location.HORIZONTAL);
                                int score = gatekeeper.score(word, Location.CENTER, Location.HORIZONTAL);
                                if (score > bestScore) {
                                    bestScore = score;
                                    bestWord = word;
                                }
                                //try vertical
                                gatekeeper.verifyLegality(word, Location.CENTER, Location.VERTICAL);
                                score = gatekeeper.score(word, Location.CENTER, Location.VERTICAL);
                                if (score > bestScore) {
                                    bestScore = score;
                                    bestWord = word;
                                }
                            }
                        }
                        else{
                            String word = "" + a + b;
                            //try horizontal
                            gatekeeper.verifyLegality(word, Location.CENTER, Location.HORIZONTAL);
                            int score = gatekeeper.score(word, Location.CENTER, Location.HORIZONTAL);
                            if (score > bestScore) {
                                bestScore = score;
                                bestWord = word;
                            }
                            //try vertical
                            gatekeeper.verifyLegality(word, Location.CENTER, Location.VERTICAL);
                            score = gatekeeper.score(word, Location.CENTER, Location.VERTICAL);
                            if (score > bestScore) {
                                bestScore = score;
                                bestWord = word;
                            }
                        }
                    } catch (IllegalMoveException e) {
                        // It wasn't legal; go on to the next one
                    }
                }
            }
        }
        if (bestScore > -1) {
            return new PlayWord(bestWord, Location.CENTER, Location.HORIZONTAL);
        }
        return new ExchangeTiles(ExchangeMinimumFrequencyTiles(4));
    }
    /**
     * Technically this tries to make a two-letter word by playing one tile; it won't find words that simply add a
     * tile to the end of an existing word.
     */
    private ScrabbleMove findOneTileMove() {
        ArrayList<Character> hand = gatekeeper.getHand();
        PlayWord bestMove = null;
        int bestScore = -1;
        for (int i = 0; i < hand.size(); i++) {
            char c = hand.get(i);
            if (c == '_') {
                c = 'E'; // This could be improved slightly by trying all possibilities for the blank
            }
            for (String word : new String[] {c + " ", " " + c}) {
                for (int row = 0; row < Board.WIDTH; row++) {
                    for (int col = 0; col < Board.WIDTH; col++) {
                        Location location = new Location(row, col);
                        for (Location direction : new Location[] {Location.HORIZONTAL, Location.VERTICAL}) {
                            try {
                                gatekeeper.verifyLegality(word, location, direction);
                                int score = gatekeeper.score(word, location, direction);
                                if (score > bestScore) {
                                    bestScore = score;
                                    bestMove = new PlayWord(word, location, direction);
                                }
                            } catch (IllegalMoveException e) {
                                // It wasn't legal; go on to the next one
                            }
                        }
                    }
                }
            }
        }
        if (bestMove != null) {
            return bestMove;
        }
        return new ExchangeTiles(ExchangeMinimumFrequencyTiles(4));
    }
}

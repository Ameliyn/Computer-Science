package scrabble;

import java.util.ArrayList;
import java.util.Random;

public class BruteForceAI implements ScrabbleAI {
    private GateKeeper gatekeeper;
    private int valueFlag = 40; //This is the point threshold that if we find a word worth this many points we play it immediately

    //These variables are for passing between methods easier
    private String bestWord;
    private int bestValue;
    private Location bestLocation;
    private Location bestDirection;
    private ArrayList<String> tempOptions;

    public BruteForceAI(){
    }

    public BruteForceAI(int valueFlag){
        this.valueFlag = valueFlag;
    }

    @Override
    public void setGateKeeper(GateKeeper gateKeeper) {this.gatekeeper = gateKeeper;}

    @Override
    public ScrabbleMove chooseMove(){
        ArrayList<Character> hand = gatekeeper.getHand();

        //Treat wilds as 'E'
        for(int i = 0; i < hand.size(); i++){
            if(hand.get(i) == '_'){
                hand.remove(i);
                hand.add('E');
            }
        }

        //allow for the word to play with a single character of other words
        hand.add(' ');

        bestWord = "";
        bestValue = -1;
        //Only allow the AI to play hand.Size - 2 length words because the bigger the word, the more the permutations
        for(int wordLength = 2; wordLength < hand.size()-2; wordLength++){

            //generate more permutations of lower length words
            //(EX. hand = "ABCDEF" for length 2 could generate "AB, DE, BA, FC")
            if(wordLength == 2) GeneratePermutations(hand, wordLength, 4);
            else if(wordLength == 3) GeneratePermutations(hand, wordLength, 3);
            else if(wordLength == 4 || wordLength == 5) GeneratePermutations(hand, wordLength, 2);
            else GeneratePermutations(hand, wordLength, 1);

            TestOptions();
            if(bestValue > valueFlag) return new PlayWord(bestWord, bestLocation, bestDirection);
        }
        //always try to play, but if we can't, exchange tiles
        if(bestValue > -1) return new PlayWord(bestWord, bestLocation, bestDirection);
        return new ExchangeTiles(new boolean[]{true, true, true, true, true, true, true});
    }

    /**
     * GeneratePermutations takes a list of characters and a length and generates a number of permutations all with
     * parameter length and stores them in tempOptions
     *
     * EX. Hand = "ABCDEF", length = 2, numOptions = 2; Calls FindPermutations({"AB", "DE"}, 0, 1) to find all permutations
     * of "AB" and "DE"
     *
     * @param hand List of characters available
     * @param length desired length of results
     * @param numOptions number of sets of character lists
     */
    private void GeneratePermutations(ArrayList<Character> hand, int length, int numOptions){
        if(length > hand.size()) return;
        tempOptions = new ArrayList<String>();
        ArrayList<String> optionSet = new ArrayList<String>();
        Random r = new Random();
        if(hand.size() == length){
            String s = "";
            for(int i = 0; i < length; i++){
                s += hand.get(i);
            }
            optionSet.add(s);
            FindPermutations(s, 0, s.length()-1);
            return;
        }
        for(int i = 0; i < numOptions; i++){
            String s = "";
            boolean[] used = new boolean[hand.size()];

            for(int j = 0; j < length; j++){
                int k = Math.abs(r.nextInt() % hand.size());
                if(!used[k]){
                    used[k] = true;
                    s += hand.get(k);
                }
                else{
                    j--;
                }
            }
            if(!optionSet.contains(s)){
                optionSet.add(s);
                FindPermutations(s, 0, s.length()-1);
            }
            else{
                i--;
            }
        }
    }

    /**
     * FindPermutations recursively finds all the permutations of a given string and adds them to tempOptions
     * @param str string to find permutations
     * @param start starting index of string
     * @param end ending index of string
     */
    private void FindPermutations(String str, int start, int end)
    {
        if (start == end)
            tempOptions.add(str);
        else {
            for (int i = start; i <= end; i++) {
                str = swap(str, start, i);
                FindPermutations(str, start + 1, end);
                str = swap(str, start, i);
            }
        }
    }

    /**
     * TestOptions tests all strings in tempOptions on all board spaces to see if it is legal and stores the best value
     */
    private void TestOptions(){
        for(String s: tempOptions){
            for(int i = 0; i < 15; i++){
                for(int j = 0; j < 15; j++){
                    try{
                        gatekeeper.verifyLegality(s, new Location(i,j), Location.VERTICAL);
                        int score = gatekeeper.score(s, new Location(i,j), Location.VERTICAL);
                        if(score > bestValue){
                            bestValue = score;
                            bestWord = s;
                            bestLocation = new Location(i,j);
                            bestDirection = Location.VERTICAL;
                            if(bestValue > valueFlag) return;
                        }

                    }catch(IllegalMoveException e){

                    }
                    try{
                        gatekeeper.verifyLegality(s, new Location(i,j), Location.HORIZONTAL);
                        int score = gatekeeper.score(s, new Location(i,j), Location.HORIZONTAL);
                        if(score > bestValue){
                            bestValue = score;
                            bestWord = s;
                            bestLocation = new Location(i,j);
                            bestDirection = Location.HORIZONTAL;
                            if(bestValue > valueFlag) return;
                        }

                    }catch(IllegalMoveException e){

                    }

                }
            }

        }
    }

    /**
     * Swap Characters at position
     * @param a string value
     * @param i position 1
     * @param j position 2
     * @return swapped string
     */
    public String swap(String a, int i, int j)
    {
        char temp;
        char[] charArray = a.toCharArray();
        temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
        return String.valueOf(charArray);
    }
}

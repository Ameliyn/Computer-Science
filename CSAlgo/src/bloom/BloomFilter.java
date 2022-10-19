package bloom;

public class BloomFilter<k> {

    private long[] bloom;

    public BloomFilter(){
        bloom = new long[65536/64];
    }

    /**
     * returns true if any of the bits from the hashing of the object are true
     * @param s
     * @return
     */
    public boolean mightContain(k s) {
        //find if any bits in the hashed value are set
        int h1 = hashHigh(s);
        int h2 = hashLow(s);
        return get(h1) || get(h2);
    }

    /**
     * Adds the given parameter to the bloom
     * @param s object to add
     */
    public void add(k s) {
        int h1 = hashLow(s);
        int h2 = hashHigh(s);

        set(h1);
        set(h2);
    }

    private void set(int i){
        int spot1 = i / 64;
        int offset1 = i % 64;
        bloom[spot1] |= (1 >> offset1);
    }

    private boolean get(int i){
        int spot = i / 64;
        int offset = i % 64;
        return (bloom[spot] >> offset & 0x1) == 1;
    }

    /**
     * counts the number of true bits in the bloom
     * @return integer number of bits set
     */
    public int trueBits() {
        int result = 0;
        for(int i = 0; i < bloom.length; i++){
            result += Long.bitCount(bloom[i]);
        }
        return result;
    }

    /**
     * returns the indexes for the low 16 bits of the hash of the object
     * @param s
     * @return
     */
    private int hashHigh(k s){
        int highSixteen = s.hashCode() >>> 16;

        //somehow make the lowSixteen number between [0,65536]
        return highSixteen;
    }

    /**
     * returns the indexes for the high 16 bits of the hash of the object
     * @param s
     * @return
     */
    private int hashLow(k s){
        int lowSixteen = s.hashCode() << 16 >>> 16;

        //somehow make the highSixteen number between [0,65536]
        return lowSixteen;
    }

    public static void main(String[] unused){
        int h = 0x87654321;
        int low = h >>> 16;
        int high = h << 16 >> 16;
        System.out.println(low/64);
        System.out.println(String.format("0x%08X", low));
        System.out.println(high/64);
        System.out.println(String.format("0x%08X", high));
    }
}

package bloom;

public class BloomFilter<k> {

    private long[] bloom;

    public BloomFilter(){
        bloom = new long[1024];
    }

    /**
     * returns true if any of the bits from the hashing of the object are true
     * @param s
     * @return
     */
    public boolean mightContain(k s) {
        return false;
    }

    /**
     * Adds the given parameter to the bloom
     * @param s object to add
     */
    public void add(k s) {
        hashLow(s);
        hashHigh(s);
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
    private int hashLow(k s){
        int lowSixteen = s.hashCode() << 16 >> 16;
        return 0;
    }

    /**
     * returns the indexes for the high 16 bits of the hash of the object
     * @param s
     * @return
     */
    private int hashHigh(k s){
        int highSixteen = s.hashCode() >> 16;
        return 0;
    }

    public static void main(String[] unused){
        int hash = 0x12345678;
        int highSixteen = hash >> 16;
        int lowSixteen = (hash << 16) >> 16;

        System.out.println(String.format("0x%08X", lowSixteen));
        System.out.println(String.format("0x%08X", highSixteen));
    }
}

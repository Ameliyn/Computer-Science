package bloom;

//@Authors: Skye Russ, Katie Shimaura, Nuzhat Hoque, Anders Stall
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
        return get(s.hashCode() << 16 >>> 16) && get(s.hashCode() >>> 16);
    }

    /**
     * Adds the given parameter to the bloom
     * @param s object to add
     */
    public void add(k s) {
        set(s.hashCode() << 16 >>> 16);
        set(s.hashCode() >>> 16);
    }

    /**
     * sets the given index [0,65536) to 1
     * @param i
     */
    private void set(int i){
        bloom[i/64] |= (0x1L << (i%64));
    }

    /**
     * returns the status for the given index [0,65536)
     * @param i
     * @return
     */
    private boolean get(int i){
        return (bloom[i/64] >>> (i%64) & 1) == 1;
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
}
package homework;

/** A dictionary associating keys with values. */
public interface Dictionary<K, V> {

    /**
     * Returns the value associated with key in this dictionary.
     * Returns null if key is not in dictionary.
     */
    public V get(K key);

    /** Returns true if this dictionary is empty. */
    public boolean isEmpty();

    /**
     * Associates key with value in this dictionary. Overwrites any value
     * previously associated with key. If value is null, removes any association
     * with key.
     */
    public void put(K key, V value);

}
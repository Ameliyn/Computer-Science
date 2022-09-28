package homework;

public class ArrayDictionary<key, value> implements Dictionary<key,value>{

    private key[] keys;
    private value[] values;
    //the end pointer on the array
    private int end = 0;

    public ArrayDictionary(){
        keys = (key[])new Object[10];
        values = (value[])new Object[10];
    }
    /**
     * returns the value associated with the given key
     * @param k
     * @return value
     */
    public value get(key k) {
        if(end == 0) return null;
        for(int i = 0; i < end; i++){
            if(keys[i] != null && keys[i].equals(k)) return values[i];
        }
        return null;
    }

    /**
     * returns if there are any pairs on the array
     * @return boolean isEmpty
     */
    public boolean isEmpty() {
        return end == 0;
    }

    /**
     * put adds the given object to the array. adding null removes the object from the array
     * @param key key
     * @param value value
     */
    public void put(key key, value value) {
        if(key == null) return;

        //handle if the key already exists
        int index = findKey(key);
        if(index != -1) {
            if(value == null) removePair(index);
            else {
                keys[index] = key;
                values[index] = value;
            }
            return;
        }

        //if the key doesn't exist and the value is null, exit
        if(value == null) return;

        //if pairs is uninitialized
        if(keys == null) {
            keys = (key[])new Object[10];
            values = (value[])new Object[10];
            keys[0] = key;
            values[0] = value;
        }
        //if no room left on array, double the size
        else if(end > keys.length){
            key[] newKeys = (key[])new Object[keys.length*2];
            value[] newValues = (value[])new Object[values.length*2];
            for(int i = 0; i < keys.length; i++){
                newKeys[i] = keys[i];
                newValues[i] = values[i];
            }
            keys = newKeys;
            values = newValues;
        }
        keys[end] = key;
        values[end] = value;
        end++;
    }

    /**
     * findKey returns the index of the key or -1 if key is not in array
     * @param k key key
     * @return int index
     */
    private int findKey(key k){
        for(int i = 0; i < end; i++){
            if(keys[i] != null && keys[i].equals(k)) return i;
        }
        return -1;
    }

    /**
     * removePair removes the pair at index and compacts the array
     * @param index index to be removed
     */
    private void removePair(int index){
        if(index >= end) return;
        for(int i = index; i < end; i++){
            if(i+1 == end) {
                keys[i] = null;
                values[i] = null;
            }
            if(keys[i] != null) {
                keys[i] = keys[i+1];
                values[i] = values[i+1];
            }
        }
        end--;
    }
}

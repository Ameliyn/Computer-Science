package homework;

public class ArrayDictionary<key, value> implements Dictionary<key,value>{

    //a helper class to store key, value pairs
    private static class pair<key, value>{
        key key;
        value value;

        public pair(key key, value value){
            this.key = key;
            this.value = value;
        }
    }

    //an array to store the pairs
    private pair<key, value>[] pairs;
    //the end pointer on the array
    private int end = 0;

    /**
     * returns the value associated with the given key
     * @param key key
     * @return value
     */
    public value get(Object key) {
        if(end == 0) return null;
        for(int i = 0; i < end; i++){
            if(pairs[i] != null && pairs[i].key.equals(key)) return pairs[i].value;
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
     * @param k key
     * @param v value
     */
    public void put(Object k, Object v) {
        if(k == null) return;
        key key = (key)k;
        value value = (value)v;

        //handle if the key already exists
        int index = findKey(key);
        if(index != -1) {
            if(value == null) removePair(index);
            else {
                pairs[index] = new pair<>(key, value);
            }
            return;
        }

        //if the key doesn't exist and the value is null, exit
        if(value == null) return;

        //if pairs is uninitialized
        if(pairs == null) {
            pairs = new pair[10];
            pairs[0] = new pair<key, value>(key, value);
        }
        //if no room left on array, double the size
        else if(end > pairs.length){
            pair<key,value>[] newPairs = new pair[pairs.length*2];
            for(int i = 0; i <  pairs.length; i++){
                newPairs[i] = pairs[i];
            }
            pairs = newPairs;
        }
        pairs[end] = new pair<key, value>(key, value);
        end++;
        //add the new pair to the end of the array, and increment the end pointer
//        insertPair(new pair<key,value>(key,value));
    }

    private void insertPair(pair p){
        try{
            Comparable key = (Comparable) p.key;
            int j = end-1;
            while(j >= 0 && key.compareTo(pairs[j]) < 0){
                pairs[j+1] = pairs[j];
                j--;
            }
            pairs[j+1] = p;
        }
        catch(Exception e){
            //unable to cast to comparable
            pairs[end] = p;
            end++;
        }
    }

    /**
     * findKey returns the index of the key or -1 if key is not in array
     * @param k key key
     * @return int index
     */
    private int findKey(key k){
        for(int i = 0; i < end; i++){
            if(pairs[i] != null && pairs[i].key.equals(k)) return i;
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
            if(i+1 == end) pairs[i] = null;
            if(pairs[i] != null) pairs[i] = pairs[i+1];
        }
        end--;
    }
}

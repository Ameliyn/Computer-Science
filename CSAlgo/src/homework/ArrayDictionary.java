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
        for(int i = 0; i < end; i++){
            if(keys[i].equals(k)) return values[i];
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
    public void put(key key, value value){
        if(key == null) return;

        //if out of space
        if(end >= keys.length){
            key[] newKeys = (key[])new Object[keys.length*2];
            value[] newValues = (value[])new Object[values.length*2];
            for(int i = 0; i < keys.length; i++){
                newKeys[i] = keys[i];
                newValues[i] = values[i];
            }
            keys = newKeys;
            values = newValues;
            keys[end] = key;
            values[end] = value;
            end++;
        }
        else if(value == null){
            boolean removalFlag = false;
            for(int i = 0; i < end; i++){
                if(!removalFlag && keys[i].equals(key)) {
                    removalFlag = true;
                }
                if(removalFlag){
                    keys[i] = keys[i+1];
                    values[i] = values[i+1];
                }
            }
            if(removalFlag) end--;
        }
        else{
            for(int i = 0; i < end; i++){
                if(keys[i].equals(key)) {
                    values[i] = value;
                    return;
                }
            }
            keys[end] = key;
            values[end] = value;
            end++;
        }
    }
}

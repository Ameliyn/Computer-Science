package inclass;

public class ArraySet<k> implements Set<k>{


    private k[] elements;
    private int index = 0;

    public ArraySet(){
        elements = (k[]) new Object[10];
    }

    @Override
    public void add(k key) {
        if(contains(key)) return;
        if(index > elements.length){
            increaseSize();
        }
        //if index = 0 we dont have a set yet

        SortedAdd(key);
    }

    /**
     * SortedAdd adds the given key into the array with an insertion paradigm (to keep the array sorted)
     * @param key
     */
    private void SortedAdd(k key){

        //if there is nothing in the array, just add the key
        if(index == 0){
            elements[0] = key;
            index++;
        }

        Comparable tempCompare = (Comparable)key;
        for(int i = 0; i < index; i++){
            if(tempCompare.compareTo((Comparable)elements[i]) > 0){
                for(int j = index; j > i; j--){
                    elements[j] = elements[j-1];
                }
                elements[i] = key;
                break;
            }
        }
        index++;
    }

    private void increaseSize(){
        k[] newElements = (k[])new Object[elements.length*2];
        for(int i = 0; i < elements.length; i++){
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    @Override
    public boolean contains(k key) {
        for(int i = 0; i < index; i++){
            if(elements[i].equals(key)) return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return index == 0;
    }

    @Override
    public void remove(k key) {
        int mode = 0;
        for(int i = 0; i < index; i++){
            if(elements[i].equals(key)) mode = 1;
            if(mode == 1) elements[i] = elements[i+1];
        }
        index--;
    }
}

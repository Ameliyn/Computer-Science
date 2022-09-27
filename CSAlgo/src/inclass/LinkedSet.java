package inclass;

public class LinkedSet<k> implements Set<k>{

    private class Link{
        public Link next;
        public Link previous;
        public k data;

        public Link(k data, Link next, Link previous){
            this.data = data;
            this.next = next;
            this.previous = previous;
        }
    }

    private Link head;
    private Link tail;
    private int size = 0;

    @Override
    public void add(k key) {
        if(contains(key)) return;
        if(size == 0){
            head = new Link(key, null, null);
            tail = head;
            size++;
        }
        else{
            tail.next = new Link(key, null, tail);
            tail = tail.next;
            size++;
        }
    }

    @Override
    public boolean contains(k key) {
        if(size == 0) return false;
        Link current = head;
        for(int i = 0; i < size; i++){
            if(current.data.equals(key)) return true;
            else{
                current = current.next;
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void remove(k key) {
        if(size == 0) return;
        Link current = head;
        for(int i = 0; i < size; i++){
            if(current.data.equals(key)){
                if(i == 0){
                    head = head.next;
                    size--;
                    break;
                }
                else if(i + 1 == size){
                    current.previous = current.next;
                    tail = tail.previous;
                }
                else{
                    current.previous = current.next;
                }
            }
            else{
                current = current.next;
            }
        }
    }
}

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


    public LinkedSet(){
        head = null;
        tail = null;
    }

    @Override
    public void add(k key) {
        if(contains(key)) return;
        if(head == null){
            head = new Link(key, null, null);
            tail = head;
        }
        else{
            tail.next = new Link(key, null, tail);
            tail = tail.next;
        }
    }

    @Override
    public boolean contains(k key) {
        if(head == null) return false;
        Link current = head;
        while(current != null){
            if(current.data.equals(key)) return true;
            else{
                current = current.next;
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public void remove(k key) {
        Link current = head;
        while(current != null){
            if(current.data.equals(key)){
                if(current.data.equals(head.data)){
                    head = head.next;
                    break;
                }
                else if(current.data.equals(tail.data)){
                    current.previous = current.next;
                    tail = tail.previous;
                    break;
                }
                else{
                    current.previous = current.next;
                    break;
                }
            }
            else{
                current = current.next;
            }
        }
    }
}

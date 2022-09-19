package homework;

public class LinkedDictionary<key, value> implements Dictionary{

    //a helper class that encompasses a link and a key,value pair
    private class Link{
        Object key;
        Object value;
        Link next;
        Link prev;

        public Link(Object key, Object value, Link next, Link prev){
            this.key = (key)key;
            this.value = (value)value;
            this.next = next;
            this.prev = prev;
        }
    }

    private int size = 0;
    private Link head;
    private Link tail;

    public value get(Object key) {
        Link point = head;
        for(int i = 0; i < size; i++){
            if(point == null) return null;
            if(point.key.equals(key)) return (value)point.value;
            point = point.next;
        }
        return null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void put(Object key, Object value) {
        Link node = find(key);
        //if node does not exist on the list
        if(node == null){
            if(value == null) return;
            if(size == 0){
                head = new Link(key,value,null,null);
                tail = head;
            }
            else{
                tail.next = new Link(key, value, null, tail);
                tail = tail.next;
            }
            size++;
        }
        else{
            //if null, remove the key,value pair
            if(value == null) {
                if(node.equals(head)){
                    if(size == 1){
                        head = null;
                        tail = null;
                        size = 0;
                        return;
                    }
                    else{
                        head = head.next;
                        size--;
                    }
                }
                else if(node.equals(tail)){
                    tail = tail.prev;
                    size--;
                }
                else{
                    node.prev = node.next;
                    size--;
                }
            }
            node.value = value;
        }
    }

    private Link find(Object key){
        if(head == null || size == 0) return null;
        Link point = head;
        for(int i = 0; i < size; i++){
            if(point == null) return null;
            if(point.key.equals(key)) return point;
            point = point.next;
        }
        return null;
    }
}

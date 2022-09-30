package homework;

public class LinkedDictionary<key, value> implements Dictionary<key, value>{

    //a helper class that encompasses a link and a key,value pair
    private class Link{
        key key;
        value value;
        Link next;
        Link prev;

        public Link(key key, value value, Link next, Link prev){
            this.key = key;
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    private Link head;

    public LinkedDictionary(){
        head = new Link(null,null,null,null);
    }

    @Override
    public value get(Object key) {
        Link point = head;
        while(point.value != null){
            if(point.key.equals(key)) return point.value;
            point = point.next;
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return head.value == null;
    }

    @Override
    public void put(key key, value value) {
        if(value == null){
            Link point = head;
            while(point.value != null){
                if(point.key.equals(key)) {
                    if(point.key.equals(head.key)){
                        head = head.next;
                    }
                    else{
                        point.prev.next = point.next;
                        point.next.prev = point.prev;
                    }
                }
                point = point.next;
            }
        }
        else{
            Link point = head;
            while(point.value != null){
                if(point.key.equals(key)) {
                    point.value = value;
                    return;
                }
                point = point.next;
            }
            point.key = key;
            point.value = value;
            point.next = new Link(null,null,null, point);
        }
    }
}

package homework;

import java.util.ArrayList;

public class GeneralTree<k> {

    class Node {
        k value;
        ArrayList<Node> children;

        private Node(k value) {
            this.value = value;
            children = new ArrayList<Node>();
        }

        public String toString() {
            String s = "(" + value;
            for(Node child : children){
                if(child.value != null)
                    s += " " + child.toString();
            }
            return s + ")";
        }
    }

    private Node root;

    public GeneralTree(){
        root = new Node(null);
    }

    public void setRoot(k value){
        if(root.value == null){
            root = new Node(value);
        }
        else{
            Node temp = new Node(value);
            temp.children.add(root);
            root = temp;
        }
    }

    public void addChild(k nodeValue, k childValue){
        Node found = findNode(nodeValue);
        if(found != null)
            found.children.add(new Node(childValue));
    }

    private Node findNode(k value){
        return findNode(value, root);
    }

    private Node findNode(k value, Node n){
        if(n.value.equals(value)) return n;
        Node temp;
        for(Node child : n.children){
            temp = findNode(value,child);
            if(temp != null) return temp;
        }
        return null;
    }

    @Override
    public String toString(){
        if(root.value == null) return "()";
        return root.toString();
    }
}

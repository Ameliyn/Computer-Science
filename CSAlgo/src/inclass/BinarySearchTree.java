package inclass;

public class BinarySearchTree<k> {

    class Node {
        k value;
        Node left;
        Node right;

        private Node(k value) {
            this.value = value;
        }

        public String toString() {
            String s = "(" + value;
            if (left != null) s += left.toString();
            if (right != null) s += right.toString();
            return s + ")";
        }
    }

    private Node root;

    public BinarySearchTree(){root = new Node(null);}
    public BinarySearchTree(k value){root = new Node(value);}

    public void Add(k value){
        Add(root, value);
    }

    private void Add(Node node, k value){
        if(node.value == null) {
            node.value = value;
        }
        else if(((Comparable)node.value).compareTo(value) > 0){
            if(node.right == null) node.right = new Node(value);
            else Add(node.right,value);
        }
        else{
            if(node.left == null) node.left = new Node(value);
            else Add(node.left,value);
        }
    }

    public boolean Contains(k key){
        return Contains(root, key);
    }

    private boolean Contains(Node n, k key){
        int c = ((Comparable)key).compareTo(n.value);
        if(c == 0) return true;
        else if(c < 0) {
            if(n.left == null) return false;
            return Contains(n.left,key);
        }
        else {
            if(n.right == null) return false;
            return Contains(n.right,key);
        }
    }

    @Override
    public String toString(){
        return root.toString();
    }
}

package inclass;

import com.sun.jdi.connect.Connector;
import edu.princeton.cs.algs4.StdOut;

public class BinaryTree {

    class Node {
        int value;
        Node left;
        Node right;

        private Node(int value) {
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

    public BinaryTree() {
        root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.left = new Node(6);
        root.right.right = new Node(7);
    }

    public String toString() {
        return root.toString();
    }

    public void traverse_inorder() {
        traverse_inorder(root);
    }

    private void traverse_inorder(Node n) {
        if (n != null) {
            traverse_inorder(n.left);
            StdOut.print(n.value);
            traverse_inorder(n.right);
        }
    }

    public void traverse_postorder() {
        traverse_postorder(root);
    }

    private void traverse_postorder(Node n) {
        if (n != null) {
            traverse_postorder(n.left);
            traverse_postorder(n.right);
            StdOut.print(n.value);
        }
    }

    private void traverse_preorder(){
        traverse_preorder(root);
    }

    private void traverse_preorder(Node n){
        if(n != null){
            StdOut.print(n.value);
            traverse_preorder(n.left);
            traverse_preorder(n.right);
        }
    }

    public static void main(String[] unused) {
        BinaryTree t = new BinaryTree();
        //StdOut.println(t.toString());
        t.traverse_inorder();
        StdOut.println("\nPostOrder");
        t.traverse_postorder();
        StdOut.println("\nPreOrder");
        t.traverse_preorder();
    }

}

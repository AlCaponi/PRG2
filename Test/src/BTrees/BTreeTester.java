/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BTrees;

/**
 *
 * @author Simon
 */
public class BTreeTester {
    
    
    public static void main(String[] args)
    {
        BinaryNode root = new BinaryNode("Element");
        BinaryNode elm1 = new BinaryNode("Element");
        BinaryNode elm2 = new BinaryNode("Element");
        BinaryNode elm3 = new BinaryNode("Element");
        BinaryNode elm4 = new BinaryNode("Element");
        root.setLeft(elm2);
        root.setRight(elm1);
        elm2.setRight(elm3);
        elm2.setLeft(elm4);
        
        //          elm 1
        //    elem 3      elm 2
        //  elm 5   elm 4
        
        System.out.println("Pre order : ");
        root.preOrder();
        System.out.println("Post order : ");
        root.postOrder();
        
        System.out.println("In order : ");
        root.inOrder();
        
        // Binary Search
        BinaryNode node1 = new BinaryNode("Element",5);
        BinaryNode node2 = new BinaryNode("Element",2);
        BinaryNode node3 = new BinaryNode("Element",8);
        BinaryNode node4 = new BinaryNode("Element",3);
        
        node1.setLeft(node2);
        node2.setRight(node4);
        node1.setRight(node3);
        
        BinaryNode itm8 = node1.search(8);
        
        BinaryTree tree = new BinaryTree(9);
        tree.addItem(itm8);
        tree.addItem(node2);
        tree.addItem(node3);
        
        BinaryNode imt2 = tree.search(2);
        
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BTrees;

/**
 *
 * @author Simon
 */
public class BinaryTree {

    BinaryNode root;

    public BinaryTree(int rootKey) {
        root = new BinaryNode("root", rootKey);
    }

    public void addItem(BinaryNode item) {
        root.Insert(item);

    }

    public BinaryNode search(int key) {
        return root.search(key);
    }
    
}

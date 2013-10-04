/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BTrees;

/**
 *
 * @author Simon
 */
class BinaryNode {

    private BinaryNode left;
    private BinaryNode right;
    private BinaryNode parent;
    private String value;
    static Integer counter = 0;
    private Integer elementNumber = 0;

    public BinaryNode(String value) {
        BinaryNode.counter++;
        elementNumber = BinaryNode.counter;
        this.value = value;
    }

    public BinaryNode(String value, Integer key) {
        this(value);
        elementNumber = key;
    }

    /**
     * @return the left
     */
    public BinaryNode getLeft() {
        return left;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(BinaryNode left) {
        left.parent = this;
        this.left = left;
    }

    /**
     * @return the right
     */
    public BinaryNode getRight() {
        return right;
    }

    /**
     * @param right the right to set
     */
    public void setRight(BinaryNode right) {
        right.parent = this;
        this.right = right;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getValue() + " " + this.elementNumber.toString();
    }

    public void print() {
        System.out.println(this.toString());
    }

    public void preOrder() {

        // 1 Konten selber, 2  Linker Knoten , 3 Rechter Knoten
        print();
        if (left != null) {
            left.preOrder();
        }
        if (right != null) {
            right.preOrder();
        }
    }

    public void postOrder() {

        //  1  Linker Knoten , 2 Rechter Knoten, 3 Konten selber
        if (left != null) {
            left.postOrder();
        }
        if (right != null) {
            right.postOrder();
        }

        print();
    }

    public void inOrder() {

        //  1  Linker Knoten, 2 Konten selber , 3 Rechter Knoten 
        if (left != null) {
            left.inOrder();
        }

        print();

        if (right != null) {
            right.inOrder();
        }


    }

    public BinaryNode search(int key) {
        BinaryNode result = null;
        if (left != null && key < elementNumber) {
            result = left.search(key);
        } else if (right != null && key > elementNumber) {
            result = right.search(key);
        } else {
            result = this;
        }
        return result;
    }
    
    public void Insert(BinaryNode item)
    {
        if(item.elementNumber < elementNumber)
            if(left != null)
                left.Insert(item);
            else
                setLeft(item);
        else
        {
            if(right != null)
                right.Insert(item);
            else
                setRight(item);
        }
    }
    
    public int getOrdnung()
    {
        int ordnung = 0;
        if(left != null)
        {
            ordnung ++;
        }
        if(right != null)
        {
            ordnung++;
        }
        return ordnung;
    }

    /**
     * @return the parent
     */
    public BinaryNode getParent() {
        return parent;
    }
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Uebungen2;

import java.util.ArrayList;

/**
 *
 * @author Christian Schweizer
 */
public class Stack {
    private ArrayList<Object> stack;
    private int top;
    
    public Stack() {
        top = 0;
    }
    
    public Object pull() {
        if(top>0) {
            return stack.remove(top-1);
        }
        else
            return 0;
    }
    
    public void push(Object o) {
        stack.add(o);
        top++;
    }
}

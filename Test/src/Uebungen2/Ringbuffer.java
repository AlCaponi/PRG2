/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Uebungen2;

import java.util.ArrayList;

/**
 *
 * @author Simon
 */
public class Ringbuffer {

    private ArrayList<Info> test;
    int head;
    int tail;
    int size;
    int capacity;

    public Ringbuffer(int capacity) {

        this.capacity = capacity;
        head = 0;
        tail = 0;
        this.test = new ArrayList<>(capacity);
    }

    public void enqueue(Info x) {
        // Fügt x am Ende in die Warteschlange ein, 
        // falls die Warteschlange nicht voll ist; 
        if (!isFull()) {

            test.add(head, x);
            size ++;
            if (head == test.size()-1) {
                head = 0;
            } else {
                head++;
            }
        }
    }

    public Info dequeue() {
        // Entfernt das erste Element aus der Warteschlange, 
        // falls die Warteschlange nicht leer ist; 
        Info element = null;
        if (!isEmpty()) {


            element = test.get(tail);
            size --;
            
            if (tail == test.size()-1) {
                tail = 0;
            } else {
                tail++;
            }

        }
        return element;
    }

    public boolean isEmpty() {
        //liefert true genau dann, wenn die Warteschlange kein Element enthält 
        return test.isEmpty();
    }

    public boolean isFull() {
        //liefert true genau dann, wenn die Warteschlange voll ist 
        return test.size() == this.capacity;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Uebungen2;

/**
 *
 * @author Simon
 */
public class Tester {
 
        public static void main(String [] args) {
            Ringbuffer buffer = new Ringbuffer(5);
            buffer.enqueue(new Info("Element 1"));
            buffer.enqueue(new Info("Element 2"));
            buffer.enqueue(new Info("Element 3"));
            buffer.enqueue(new Info("Element 4"));
            buffer.enqueue(new Info("Element 5"));
            buffer.enqueue(new Info("should not wörk"));
            
            Info elem = buffer.dequeue();
            elem = buffer.dequeue();
            elem = buffer.dequeue();
            elem = buffer.dequeue();
            elem = buffer.dequeue();
            elem = buffer.dequeue();
            // should not wörk
            elem = buffer.dequeue();
            
        }
}

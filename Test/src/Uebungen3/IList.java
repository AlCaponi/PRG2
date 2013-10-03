/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Uebungen3;

/**
 *
 * @author CHS
 */
public interface IList {
    public void add(Integer iObj);
    public boolean remove(Integer iObj);
    public boolean removeValue(int i);
    public boolean removeValues(int i);
    public int size();
    public boolean exists(int i);
    public void print();
}

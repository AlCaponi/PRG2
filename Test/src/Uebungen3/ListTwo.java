/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Uebungen3;

import java.util.LinkedList;

/**
 *
 * @author CHS
 */
public class ListTwo implements IList{
    LinkedList<Integer>  list;

    public ListTwo() {
        list = new LinkedList<>();
    }

    @Override
    public void add(Integer iObj) {
        list.add(iObj);
    }

    @Override
    public boolean remove(Integer iObj) {
        return list.remove(iObj);
    }

    @Override
    public boolean removeValue(int i) {
        list.removeFirstOccurrence(i);
        return false;
    }

    @Override
    public boolean removeValues(int i) {
        boolean returnValue = false, found;
        do {
            returnValue |= (found = list.removeLastOccurrence(i));
        } while(found);
        
        return returnValue;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean exists(int i) {
        return list.contains(i);
    }

    @Override
    public void print() {
        for(Integer iObj:list) {
            System.out.println(iObj);
        }
    }
    
}

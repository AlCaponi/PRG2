/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Uebungen3;

import java.util.ArrayList;

/**
 *
 * @author CHS
 */
public class ListOne implements IList{
    ArrayList<Integer>  list;

    public ListOne() {
        list = new ArrayList<>();
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
        for(Integer iObj:list) {
            if(iObj.intValue() == i) {
                list.remove(iObj);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeValues(int i) {
        boolean retCode = false;
        for(Integer iObj:list) {
            if(iObj == i) {
                list.remove(iObj);
                retCode = true;
            }
        }
        return retCode;
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

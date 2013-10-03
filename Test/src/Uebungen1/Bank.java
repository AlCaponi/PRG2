package Uebungen1;


import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CS
 */
public class Bank {
    private String name;
    private ArrayList<Konto> accountList;

    public Bank(String name) {
        this.name = name;
        accountList = new ArrayList();
    }
    
    public Konto openKonto() {
        Konto newKonto = new Konto();
        accountList.add(newKonto);
        return newKonto;
    }
    
    public Spar openSpar() {
        Spar newSpar = new Spar();
        accountList.add(newSpar);
        return newSpar;
    }
    
    public Giro openGiro() {
        Giro newGiro = new Giro();
        accountList.add(newGiro);
        return newGiro;
    }
    
    public int nbOfAccounts() {
        return accountList.size();
    }
    
    public void printAccounts() {
        System.out.println("Bank: "+this.name);
        for(Konto k:accountList) {
            k.print();
        }
    }
    
    public void printFund() {
        int total = 0;
        for(Konto k:accountList) {
            total += k.getSaldo();
        }
        System.out.println("Total funds: " + total);
    }
    
    public static void main(String [] args) {
        Bank myBank = new Bank("MyBank");
        Konto k;
        
        k = myBank.openGiro();
        k.payIn(100);
        
        k = myBank.openSpar();
        k.payIn(200);
        
        myBank.printAccounts();
        myBank.printFund();
    }	
}

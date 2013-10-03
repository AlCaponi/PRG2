package Uebungen1;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Simon
 */
public class Spar extends Konto {

    private double maxOut;

    public Spar() {
        super();
        maxOut = 0;
    }

    public Spar(double saldo, double rate, double maxOut) {
        super(saldo, rate);
        this.maxOut = maxOut;
    }

    @Override
    public double getSaldo() {
        return super.saldo;
    }

    public double payOut() {
        return super.rate;
    }

    @Override
    public String toString() {
        String output = "maximale auszahlung \t:\t%.2f";

        return String.format(output, this.maxOut);
    }

    @Override
    public void print() {
        super.print();
        System.out.println(this.toString());
    }
}

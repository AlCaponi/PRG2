
public class Konto {

    private static int count = 0;
    private int no;
    protected double saldo;
    protected double rate;

    public Konto() {
        count++;
        no = count;
        saldo = 0.0;
        rate = 0.0;
    }

    public Konto(double saldo, double rate) {
        count++;
        no = count;
        saldo = saldo;
        rate = rate;
    }

    public void payIn(double value) {
        saldo += value;
    }

    public void payOut(double value) {
        saldo -= value;
    }

    public double getSaldo() {
        return saldo;
    }

    public String toString() {
        String outputString = "Konto Nr.:\t %d  \n"
                + "Saldo:\t\t %.2f  \n"
                + "Zins:\t\t %.2f%% ";
        return String.format(outputString, no, saldo, rate);
    }

    public void print() {
        System.out.println(this.toString());
    }

   /* public static void main(String[] args) {
        Konto meinKonto = new Konto();
        meinKonto.payIn(10000.0);
        System.out.print(meinKonto.toString());
    }*/
}

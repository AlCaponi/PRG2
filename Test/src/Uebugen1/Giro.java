
public class Giro extends Konto
{
	private double creditLimit;
	private double creditRate;
	
	public Giro()
	{
		super();
		this.creditLimit = 0.0;
		this.creditRate = 0.0;
	}
	
    public Giro(double creditLimit, double creditRate)
    {
    	super();
    	this.creditLimit = creditLimit;
    	this.creditRate = creditRate;
    	
    }
    
    public void payOut(double value)
    {
    	//Saldo überprüfen wenn er unter 0 ist dann muss auch die kredit Limite überprüft werden
    	if(super.saldo < 0 )
    	{
    		//Wird die Abhebeung die Limite sprengen dann nicht abheben
    		if(Math.abs(super.saldo - value) > this.creditLimit )
    		{
    			return;
    		}
    	}
    	super.saldo -= value;
    }
    
    public String toString()
    {
    	String output = "Kerdit Limit\t:\t%.2f\n"+
    					"Kredit Zins\t:\t%.2f";
    	return String.format(output, this.creditLimit, this.creditRate);
    }
    
    public void Print()
    {
         super.print();
         System.out.println(this.toString());
    }
}

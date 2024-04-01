/**
 * The class Horse represents a horse in a race
 * It can move forward, fall, go back to start, 
 * and change its confidence and symbol.
 * 
 * @author Vera Malkova 
 * @version 17 March 2024
 */


public class Horse
{
    //Fields of class Horse
    private String name;
    private char symbol;
    private int distanceTravelled;
    private boolean hasFallen;
    private double confidence;
    
      
    //Constructor of class Horse

    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        this.symbol = horseSymbol;
        this.name = horseName;
        this.confidence = horseConfidence;
        this.distanceTravelled = 0;
        this.hasFallen = false;
    }
    
    
    
    //Other methods of class Horse
    public void fall()
    {
        this.hasFallen = true;
    }
    
    public double getConfidence()
    {
        return this.confidence;
    }
    
    public int getDistanceTravelled()
    {
        return this.distanceTravelled;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public char getSymbol()
    {
        return this.symbol;
    }
    
    public void goBackToStart()
    {
        this.distanceTravelled = 0;
    }
    
    public boolean hasFallen()
    {
        return this.hasFallen;
    }

    public void moveForward()
    {
        this.distanceTravelled++;
    }

    public void setConfidence(double newConfidence)
    {
        this.confidence = newConfidence;
    }
    
    public void setSymbol(char newSymbol)
    {
        this.symbol = newSymbol;
    }

    public static void test()
    {   
        // Test Horse accessor methods
        System.out.println();
        System.out.println("Testing Horse accessor methods:");
        Horse horse_a = new Horse('H', "name", 0.5);

        System.out.print("  getSymbol: " + horse_a.symbol + " ---- ");
        horse_a.symbol = 'X';
        System.out.println(horse_a.getSymbol() == 'X');
        System.out.print("  getName: " + horse_a.name + " ---- ");
        horse_a.name = "new";
        System.out.println(horse_a.getName().equals("new"));
        System.out.print("  getConfidence: " + horse_a.confidence + " ---- ");
        horse_a.confidence = 0.7;
        System.out.println(horse_a.getConfidence() == 0.7);
        System.out.print("  getDistanceTravelled: " + horse_a.distanceTravelled + " ---- ");
        horse_a.distanceTravelled = 1;
        System.out.println(horse_a.getDistanceTravelled() == 1);
        System.out.print("  hasFallen: " + horse_a.hasFallen + " ---- ");
        horse_a.hasFallen = true;
        System.out.println(horse_a.hasFallen() == true);

        // Test Horse constructor methods
        System.out.println();
        System.out.println("Testing Horse constructor method:");
        Horse horse = new Horse('H', "name", 0.5);

        System.out.print("  symbol: " + horse.symbol + " ---- ");
        System.out.println(horse.getSymbol() == 'H');
        System.out.print("  name: " + horse.name + " ---- ");
        System.out.println(horse.getName().equals("name"));
        System.out.print("  confidence: " + horse.confidence + " ---- ");
        System.out.println(horse.getConfidence() == 0.5);
        System.out.print("  distanceTravelled: " + horse.distanceTravelled + " ---- ");
        System.out.println(horse.getDistanceTravelled() == 0);
        System.out.print("  hasFallen: " + horse.hasFallen + " ---- ");
        System.out.println(horse.hasFallen() == false);
        
        // Test Horse mutator methods
        System.out.println();
        System.out.println("Testing Horse mutator methods:");

        System.out.print("  moveForward ---- ");
        horse.moveForward();
        System.out.println(horse.getDistanceTravelled() == 1);
        System.out.print("  fall ---- ");
        horse.fall();
        System.out.println(horse.hasFallen() == true);
        System.out.print("  goBackToStart ---- ");
        horse.goBackToStart();
        System.out.println(horse.getDistanceTravelled() == 0);
        System.out.print("  setConfidence: " + horse.confidence + " ---- ");
        horse.setConfidence(0.7);
        System.out.println(horse.getConfidence() == 0.7);
        System.out.print("  setSymbol: " + horse.symbol + " ---- ");
        horse.setSymbol('X');
        System.out.println(horse.getSymbol() == 'X');

        System.out.println();
        return;
    }

    public static void main(String[] args)
    {
        test();
    }
    
}

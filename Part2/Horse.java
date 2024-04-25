import java.awt.*;
import java.util.*;

/**
 * The class Horse represents a horse in a race
 * It can move forward, fall, go back to start, 
 * and change its confidence and symbol.
 * 
 * @author Vera Malkova 
 * @version 20 April 2024
 */


public class Horse
{
    //Fields of class Horse
    private String name;
    private char symbol;
    private int distanceTravelled;
    private boolean hasFallen;
    private double confidence;
    private ArrayList<String> accessories;
    private static ArrayList<Character> symbols;
    private String accessory;
    private static HashMap<String, Color> colors;
    private String symbolColour;
    
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
        this.accessories = new ArrayList<String>(
                            Arrays.asList("None" , "\uD83E\uDDE2"
                            , "\uD83E\uDDE3",  "\uD83C\uDFA9"
                            , "\uD83D\uDD76", "\uD83C\uDF80"));
        this.accessory = " ";
        if (symbols == null)
        {
            symbols = new ArrayList<Character>();
            for (int i = 9812; i <= 9823; i++) {
                symbols.add((char) i);
            }
        }
        else if (symbols.contains((Character) horseSymbol))
        {
            symbols.remove((Character) horseSymbol);
        }
        colors = new HashMap<String, Color>();
        colors.put("BLACK", Color.BLACK);
        colors.put("PURPLE", new Color(100, 0, 120));
        colors.put("BLUE", new Color(0, 50, 160));
        colors.put("CYAN", new Color(0, 90, 110));
        colors.put("GREEN", new Color(0, 80, 5));
        colors.put("RED", new Color(130, 8, 4));
        symbolColour = "BLACK";
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

    public ArrayList<String> getAccessories()
    {
        return this.accessories;
    }

    public ArrayList<Character> getSymbols()
    {
        ArrayList<Character> availableSymbols = new ArrayList<Character>(symbols);
        if ((char) 9812 <= this.symbol && this.symbol <= (char) 9823)
        {
            availableSymbols.add(this.symbol);
        }
        return availableSymbols;
    }

    public String getAccessory()
    {
        return this.accessory;
    }

    public String getSymbolColour()
    {
        return this.symbolColour;
    }

    public ArrayList<String> getColours()
    {
        return new ArrayList<String>(colors.keySet());
    }

    public Color getColour()
    {
        return colors.get(this.symbolColour);
    }

    public void setAccessory(String newAccessory)
    {
        if (newAccessory.equals("None"))
        {
            newAccessory = " ";
        }
        this.accessory = newAccessory;
    }

    public void setSymbolColour(String newSymbolColour)
    {
        this.symbolColour = newSymbolColour;
    }

    public void decreaseConfidence(int raceLength)
    {
        final double OLD_CONF = this.getConfidence();
        double percent_done = (double)this.getDistanceTravelled()/raceLength;
        double new_conf = 0.1 + (0.4 + percent_done)*OLD_CONF*5/9;
        this.setConfidence(new_conf);
    }

    public void increaseConfidence()
    {
        final double OLD_CONF = this.getConfidence();
        double new_conf = 1.8*OLD_CONF - OLD_CONF*OLD_CONF;
        new_conf = 0.1 + new_conf * 80 / 81;
        this.setConfidence(new_conf);
    }
    
    public void goBackToStart()
    {
        this.hasFallen = false;
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
        newConfidence = (int)(Math.round(newConfidence*10))/10.0;
        this.confidence = newConfidence;
    }
    
    public void setSymbol(Character newSymbol)
    {
        if (((char) 9812 <= this.symbol && this.symbol <= (char) 9823))
        {
            if (!symbols.contains(this.symbol))
            {
                symbols.add(this.symbol);
            }
        }
        this.symbol = newSymbol;
        symbols.remove(newSymbol);
    }

    public void deleteHorse()
    {
        if ((char) 9812 <= this.symbol && this.symbol <= (char) 9823)
        {
            if (!symbols.contains(this.symbol))
            {
                symbols.add(this.symbol);
            }
        }
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
        System.out.println(horse.getDistanceTravelled() == 0 && horse.hasFallen() == false);
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

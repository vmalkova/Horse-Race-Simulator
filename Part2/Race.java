import java.util.concurrent.TimeUnit;
import java.util.*;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author Vera Malkova
 * @version 23 April 2024
 */
public class Race
{
    private int raceLength;
    private ArrayList<Horse> horses;
    private double time;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance)
    {
        // initialise instance variables
        raceLength = distance;
        horses = new ArrayList<Horse>();
        time = 0.0;
    }

    public double getTime()
    {
        return this.time;
    }

    public ArrayList<Horse> getWinners()
    {
        ArrayList<Horse> winners = new ArrayList<Horse>();
        for (Horse horse : this.horses)
        {
            if (horse.getDistanceTravelled() == this.raceLength)
            {
                winners.add(horse);
            }
        }
        return winners;
    }

    public ArrayList<Horse> getHorses()
    {
        return this.horses;
    }

    public int getRaceLength()
    {
        return this.raceLength;
    }

    public ArrayList<Horse> getFallen()
    {
        ArrayList<Horse> fallen = new ArrayList<Horse>();
        for (Horse horse : this.horses)
        {
            if (horse.hasFallen())
            {
                fallen.add(horse);
            }
        }
        return fallen;
    }

    public boolean allFell()
    {
        if (this.getFallen().size() >= this.horses.size())
        {
            return true;
        }
        return false;
    }

    public void setTime(double newTime)
    {
        newTime = Math.round(newTime * 10) / 10.0;
        this.time = newTime;
    }

    public void setRaceLength(int newLength)
    {
        this.raceLength = newLength;
        return;
    }

    // Main method
    public static void main(String[] args)
    {
        Race race = new Race(15);
        int numLanes;
        final String MENU = "Again (A) | New race (N) | Quit (Q)";
        String instruction = "N";

        while (!instruction.equals("Q"))
        {
            if (instruction.equals("N"))
            {
                race = new Race(15);
                numLanes = 3;
                for (int i = 0; i < numLanes; i++)
                {
                    Horse horse = race.generateHorse();
                    race.addHorse(horse);
                }
                race.startRace();
            }
            else if (instruction.equals("A"))
            {
                race.startRace();
            }
            else
            {
                System.out.println("Invalid input");
            }

            System.out.println();
            instruction = input(MENU);
        }

        return;
    }

    // Return the input from a user
    public static String input(String message)
    {
        final Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.nextLine().toUpperCase();
    }

    public Horse generateHorse()
    {
        final Random RAND = new Random();
        ArrayList<String> taken_names = new ArrayList<String>();
        ArrayList<String> names = new ArrayList<String>(
                                    Arrays.asList("ALBERT", "BERTIE", "CHARLIE", "DAISY",
                                        "EDDIE", "FREDDIE", "GEORGE", "HARRY", "IVY", "JACK",
                                        "KATIE", "LILY", "MOLLY", "NANCY", "OLLIE", "PENNY",
                                        "QUEENIE", "ROSIE", "SOPHIE", "TOMMY", "URSULA",
                                        "VICTOR", "WILLIE", "XANDER", "YVONNE", "ZARA"));
        for (Horse horse : horses)
        {
            taken_names.add(horse.getName());
        }
        for (String name : taken_names) {
            name = name.toUpperCase();
            names.remove(name);
        }
        final int I = RAND.nextInt(names.size());

        final double CONFIDENCE = (RAND.nextInt(9) + 1)/10.0;

        Horse horse = new Horse(names.get(I).charAt(0), names.get(I).toUpperCase(), CONFIDENCE);
        return horse;
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse)
    {
        final Random RAND = new Random();
        int num = RAND.nextInt(theHorse.getSymbols().size());
        theHorse.setSymbol(theHorse.getSymbols().get(num));
        num = RAND.nextInt(theHorse.getColours().size());
        theHorse.setSymbolColour(theHorse.getColours().get(num));
        num = RAND.nextInt(theHorse.getAccessories().size());
        theHorse.setAccessory(theHorse.getAccessories().get(num));
        horses.add(theHorse);
        return;
    }

    public void removeHorse(Horse horse)
    {
        horse.deleteHorse();
        this.horses.remove(horse);
        return;
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace()
    {
        //reset all the lanes (all horses not fallen and back to 0). 
        resetLanes();
                      
        while (!allFell())
        {
            //move each horse
            this.moveHorses();
                        
            //print the race positions
            printRace();
            
            //if any of the three horses has won the race is finished
            if (raceWon())
            {
                return;
            }
           
            //wait for 100 milliseconds
            try{ 
                TimeUnit.MILLISECONDS.sleep(100);
                time += 0.1;
            }catch(Exception e){}
        }

        System.out.println();
        System.out.println("No winner");
        return;
    }

    public void moveHorses()
    {
        for (Horse horse : horses)
        {
            moveHorse(horse);
        }
        return;
    }

    public void resetLanes()
    {
        for (Horse horse : this.horses)
        {
            horse.goBackToStart();
        }
        time = 0.0;
        return;
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    public boolean moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
                theHorse.decreaseConfidence(raceLength);
                return false;
            }

            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
               return true;
            }
        }
        return false;
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    public boolean raceWon()
    {
        ArrayList<Horse> winners = new ArrayList<Horse>();
        for (Horse horse: this.horses)
        {
            if (horse.getDistanceTravelled() == this.raceLength)
            {
                winners.add(horse);
            }
        }
        if (winners.size() == 0)
        {
            return false;
        }
        System.out.println();
        if (winners.size() == 1)
        {
            System.out.println("Winner:");
        }
        else{
            System.out.println("Winners:");
        }
        for (Horse theHorse: winners)
        {
            theHorse.increaseConfidence();
            System.out.print(" - " + theHorse.getName());
            System.out.println(" (new confidence " + theHorse.getConfidence() + ") has won");
        }
        return true;
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        System.out.print('\u000C');  //clear the terminal window

        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();

        for (int i = 0; i < horses.size(); i++)
        {
            printLane(horses.get(i));
            System.out.println();
        }
        
        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            System.out.print('\u2322');
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        System.out.print('|');

        //print the name and confidence of the horse
        System.out.print("  " + theHorse.getName());
        System.out.print("  (current confidence " + theHorse.getConfidence() + ")");
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }
}

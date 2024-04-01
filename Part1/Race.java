import java.util.concurrent.TimeUnit;
import java.util.*;
import java.lang.Math;
import java.text.DecimalFormat;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McFarewell
 * @version 1.0
 */
public class Race
{
    private int raceLength;
    private ArrayList<Horse> horses;

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
    }

    // Main method
    public static void main(String[] args)
    {
        Race race = new Race(15);
        int MAX_LANES = 3;
        for (int i = 0; i < MAX_LANES; i++)
        {
            race.addHorse();
        }
        race.startRace();
        return;
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse()
    {
        Random rand = new Random();
        String[] names = {"Albert", "Bertie", "Charlie", "Daisy", "Eddie",
                         "Fredy", "George", "Hannah", "Ivy", "Jack", "Katie"};
        double[] confidences = {0.7, 0.6, 0.5, 0.4, 0.3};

        int i = rand.nextInt(names.length);
        int j = rand.nextInt(confidences.length);
        horses.add(new Horse(names[i].charAt(0), names[i].toUpperCase(), confidences[j]));
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
        for (Horse horse : horses)
        {
            horse.goBackToStart();
        }

        int fallen_horses = 0;
                      
        while (fallen_horses < horses.size())
        {
            //move each horse
            fallen_horses = 0;
            for (Horse horse : horses)
            {
                moveHorse(horse);
                if (horse.hasFallen())
                {
                    fallen_horses++;
                }
            }
                        
            //print the race positions
            printRace();
            
            //if any of the three horses has won the race is finished
            for (Horse horse : horses)
            {
                if (raceWonBy(horse))
                {
                    return;
                }
            }
           
            //wait for 100 milliseconds
            try{ 
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}
        }

        System.out.println();
        System.out.println("No winner");
        return;
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
                double old_conf = theHorse.getConfidence();
                double percent_done = (double)theHorse.getDistanceTravelled()/raceLength;
                double new_conf = 0.1 + (0.4 + percent_done)*old_conf*5/9;
                new_conf = (int)(Math.round(new_conf*10))/10.0;
                theHorse.setConfidence(new_conf);
            }
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            double old_conf = theHorse.getConfidence();
            double new_conf = 1.8*old_conf - old_conf*old_conf;
            new_conf = 0.1 + new_conf * 80 / 81;
            new_conf = (int)(Math.round(new_conf*10))/10.0;
            theHorse.setConfidence(new_conf);

            System.out.println();
            System.out.print(theHorse.getName() + " (new confidence " + new_conf);
            System.out.println(") has won");

            return true;
        }

        return false;
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

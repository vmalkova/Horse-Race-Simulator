import java.util.*;

class Main
{
    public static void addHorse(Race race)
    {
        char[] symbols = {'A', 'B', 'C', 'D', 'E', 'F'};
        String[] names = {"Albert", "Bertie", "Charlie", "Daisy", "Eddie", "Fredy"};
        double[] confidences = {0.9, 0.7, 0.5, 0.3};
        Random rand = new Random();
        int i = rand.nextInt(Math.min(symbols.length, names.length));
        int j = rand.nextInt(confidences.length);
        race.addHorse(new Horse(symbols[i], names[i], confidences[j]));
    }

    public static void main(String[] args)
    {
        Race race = new Race(15);
        int MAX_LANES = 3;
        for (int i = 0; i < MAX_LANES; i++)
        {
            addHorse(race);
        }
        race.startRace();
    }
}
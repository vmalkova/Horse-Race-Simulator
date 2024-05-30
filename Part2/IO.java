import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;


public class IO {

    private static String[] horseInfo = {"SYMBOL", "SYMBOLCOLOUR", "ACCESSORY", "NAME",
                                        "CONFIDENCE", "WINS", "RACESCOMPLETE", "AVGSPEED"};
    private static String[] raceInfo = {"WINNERS", "TIME", "LENGTH", "OTHERHORSES"};

    public static void main(String[] args)
    {
        reset();
    }

    public boolean isEmpty()
    {
        File folder = new File("Part2/HorseStats");
        File[] listOfFiles = folder.listFiles();
        return listOfFiles.length == 0;
    }

    public static void reset()
    {
        File folder = new File("Part2/HorseStats");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                file.delete();
            }
        }
        folder = new File("Part2/RaceStats");
        listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                file.delete();
            }
        }
    }

    public static void saveRace(Race race)
    {
        try
        {
            File folder = new File("Part2/RaceStats");
            String[] raceFiles = folder.list();
            String fileName = raceFiles.length + ".txt";
            File file = new File(folder, fileName);
            
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println("STARTWINNERS");
                for (Horse winner: race.getWinners())
                {
                    writer.println(winner.getName());
                }
                writer.println("ENDWINNERS");
                writer.println("STARTTIME");
                writer.println(race.getTime());
                writer.println("ENDTIME");
                writer.println("STARTLENGTH");
                writer.println(race.getRaceLength());
                writer.println("ENDLENGTH");
                writer.println("STARTOTHERHORSES");
                ArrayList<Horse> winners = race.getWinners();
                ArrayList<Horse> horses = race.getHorses();
                for (Horse horse: horses)
                {
                    if (!winners.contains(horse))
                    {
                        writer.println(horse.getName());
                    }
                }
                writer.println("ENDOTHERHORSES");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }   
    }

    public boolean restore(Horse horse)
    {
        try
        {
            File folder = new File("Part2/HorseStats");
            String fileName = horse.getName() + ".txt";
            File file = new File(folder, fileName);
            Scanner scanner = new Scanner(file);
            String line;
            for (int i = 0; scanner.hasNextLine(); i++)
            {
                line = scanner.nextLine();
                if (horseInfo[i].equals("WINS"))
                {
                    horse.setWins(Integer.parseInt(line));
                }
                else if(horseInfo[i].equals("RACESCOMPLETE"))
                {
                    horse.setRacesComplete(Integer.parseInt(line));
                }
                else if(horseInfo[i].equals("SYMBOL"))
                {
                    horse.setSymbol(line.charAt(0));
                }
                else if(horseInfo[i].equals("SYMBOLCOLOUR"))
                {
                    horse.setSymbolColour(line);
                }
                else if(horseInfo[i].equals("ACCESSORY"))
                {
                    horse.setAccessory(line);
                }
                else if(horseInfo[i].equals("AVGSPEED"))
                {
                    horse.setAverageSpeed(Double.valueOf(line));
                }
        }
        scanner.close();
        return true;
    }
    catch (FileNotFoundException e)
    {
        return false;
    }
}

    public static void saveHorse(Horse horse)
    {
        try
        {
            File folder = new File("Part2/HorseStats");
            String fileName = horse.getName() + ".txt";
            File file = new File(folder, fileName);

            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println(horse.getSymbol());
                writer.println(horse.getSymbolColour());
                writer.println(horse.getAccessory());
                writer.println(horse.getName());
                writer.println(horse.getConfidence());
                writer.println(horse.getWins());
                writer.println(horse.getRacesComplete());
                writer.println(horse.getAverageSpeed());
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void saveHorses(Race race)
    {
        for (Horse horse: race.getHorses())
        {
            saveHorse(horse);
        }
    }

    public static Horse horseByName(String name)
    {
        try
        {
            File folder = new File("Part2/HorseStats");
            String fileName = name + ".txt";
            File file = new File(folder, fileName);
            Scanner scanner = new Scanner(file);
            String symbol = scanner.nextLine();
            String symbolColour = scanner.nextLine();
            String accessory = scanner.nextLine();
            scanner.nextLine();
            String confidence = scanner.nextLine();
            String wins = scanner.nextLine();
            String racesComplete = scanner.nextLine();
            String avgSpeed = scanner.nextLine();
            scanner.close();

            Horse horse = new Horse(symbol.charAt(0), name, Double.valueOf(confidence));
            horse.setSymbolColour(symbolColour);
            horse.setAccessory(accessory);
            horse.setWins(Integer.parseInt(wins));
            horse.setRacesComplete(Integer.parseInt(racesComplete));
            horse.setAverageSpeed(Double.valueOf(avgSpeed));
            return horse;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Couldn't find horse " + name);
            return null;
        }
    }

    public JScrollPane drawHorses()
    {
        RaceGUI gui = new RaceGUI(2);
        try
        {
            File folder = new File("Part2/HorseStats");
            File[] listOfFiles = folder.listFiles();
            JPanel horsesPanel = gui.getPanel(listOfFiles.length, 1, 10);
            File checkEmpty = listOfFiles[0];
            for (File file: listOfFiles)
            {
                if (file.isFile())
                {
                    String horseName = file.getName().substring(0, file.getName().length()-4);
                    horsesPanel.add(drawHorse(horseByName(horseName)));
                }
            }
            return gui.getScrollPane(horsesPanel);
        }
        catch (Exception e)
        {
            System.out.println("Not found file to draw horses");
            e.printStackTrace();
            JPanel emptyPanel = gui.getPanel(1, 1, 0);
            Label emptyText = gui.getText("No horses found");
            emptyText.setAlignment(emptyText.CENTER);
            emptyPanel.add(emptyText);
            return gui.getScrollPane(emptyPanel);
        }
    }

    public JPanel drawHorse(Horse horse)
    {
        RaceGUI raceGUI = new RaceGUI(2);
        JPanel horsePanel = raceGUI.getPanel(4, 1, 10);
        JPanel heading = raceGUI.getPanel(1,2,0);
        heading.add(raceGUI.getHorse(horse));
        String text = horse.getName() + " (" + horse.getConfidence() + ")";
        Label nameSpeed = raceGUI.getText(text);
        nameSpeed.setFont(nameSpeed.getFont().deriveFont(Font.BOLD));
        heading.add(nameSpeed);
        horsePanel.add(heading);
        Label label = raceGUI.getText("Average speed: " + horse.getAverageSpeed() + "m/s");
        label.setAlignment(label.CENTER);
        horsePanel.add(label);
        label = raceGUI.getText("Wins: " + horse.getWins());
        label.setAlignment(label.CENTER);
        horsePanel.add(label);
        label = raceGUI.getText("Races complete: " + horse.getRacesComplete());
        label.setAlignment(label.CENTER);
        horsePanel.add(label);
        return horsePanel;
    }

    public static ArrayList<String> extractItem(String item, File file)
    {
        ArrayList<String> extracted = new ArrayList<String>();
        try
        {
            Scanner scanner = new Scanner(file);
            String line;
            for (int i=0; scanner.hasNextLine(); i++)
            {
                line = scanner.nextLine();
                if (line.equals("START" + item))
                {
                    line = scanner.nextLine();
                    while (!line.equals("END" + item))
                    {
                        extracted.add(line);
                        line = scanner.nextLine();
                    }
                }
            }
            scanner.close();
            return extracted;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return extracted;
        }
    }

    public static JPanel drawRace(HashMap<String, ArrayList<String>> raceData)
    {
        RaceGUI raceGUI = new RaceGUI(2);
        ArrayList<String> winners = raceData.get("WINNERS");
        ArrayList<String> otherHorses = raceData.get("OTHERHORSES");
        JPanel racePanel = raceGUI.getPanel(4+winners.size()+otherHorses.size(), 1, 10);
        String length = raceData.get("LENGTH").get(0);
        Label heading = raceGUI.getText(length + "m race");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD));
        heading.setAlignment(heading.CENTER);
        racePanel.add(heading);
        String lastInfo = "Other participants:";
        if (otherHorses.size() == 0)
        {
            lastInfo = "No other participants";
        }
        if (winners.size() == 0)
        {
            Label noWinners = raceGUI.getText("No winners");
            noWinners.setAlignment(noWinners.CENTER);
            racePanel.add(noWinners);
            lastInfo = "Participants:";
        }
        else
        {
            String row1 = "Winners:";
            if (winners.size() == 1)
            {
                row1 = "Winner:";
            }
            Label label = raceGUI.getText(row1);
            label.setAlignment(label.CENTER);
            racePanel.add(label);
            for (String winnerName: winners)
            {
                Horse winner = horseByName(winnerName);
                JPanel winnerPanel = raceGUI.getPanel(1, 2, 0);
                winnerPanel.add(raceGUI.getHorse(winner));
                String nameSpeed = winnerName + " (" + winner.getConfidence() + ")";
                winnerPanel.add(raceGUI.getText(nameSpeed));
                racePanel.add(winnerPanel);
            }
            String time = "Time: " + raceData.get("TIME").get(0) + "s";
            label = raceGUI.getText(time);
            label.setAlignment(label.CENTER);
            racePanel.add(label);
        }
        Label label = raceGUI.getText(lastInfo);
        label.setAlignment(label.CENTER);
        racePanel.add(label);
        for (String horseName: otherHorses)
        {
            Horse horse = horseByName(horseName);
            JPanel horsePanel = raceGUI.getPanel(1, 2, 0);
            horsePanel.add(raceGUI.getHorse(horse));
            String nameSpeed = horseName + " (" + horse.getConfidence() + ")";
            horsePanel.add(raceGUI.getText(nameSpeed));
            racePanel.add(horsePanel);
        }
        return racePanel;
    }

    public static JScrollPane drawRaces()
    {
        RaceGUI gui = new RaceGUI(2);
        try
        {
            File folder = new File("Part2/RaceStats");
            File[] listOfFiles = folder.listFiles();
            JPanel racesPanel = gui.getPanel(listOfFiles.length, 1, 10);
            File checkEmpty = listOfFiles[0];

            for (File file: listOfFiles)
            {
                if (file.isFile())
                {
                    HashMap<String, ArrayList<String>> raceData = new HashMap<String, ArrayList<String>>();
                    for (String item: raceInfo)
                    {
                        raceData.put(item, extractItem(item, file));
                    }
                    racesPanel.add(drawRace(raceData));
                }
            }
            return gui.getScrollPane(racesPanel);
        }
        catch (Exception e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
            JPanel emptyPanel = gui.getPanel(1, 1, 0);
            Label emptyText = gui.getText("No races found");
            emptyText.setAlignment(emptyText.CENTER);
            emptyPanel.add(emptyText);
            return gui.getScrollPane(emptyPanel);
        }
    }

}

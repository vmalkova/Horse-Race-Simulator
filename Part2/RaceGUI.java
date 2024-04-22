import javax.swing.*;
import java.awt.*;

public class RaceGUI {
    Race currentRace;
    Color lightBrown = new Color(222, 200, 175);
    Color darkBrown = new Color(39, 19, 10);
    Color warmWhite = new Color(255, 253, 248);

    public RaceGUI(int trackLength)
    {
        currentRace = new Race(trackLength);
    }
    public  static void main(String[] args)
    {
        RaceGUI raceGUI = new RaceGUI(10);
        for (int i = 0; i < 3; i++)
        {
            raceGUI.currentRace.addHorse();
        }
        raceGUI.mainMenu();
        return;
    }

    public Label getTitle(String text)
    {
        Label label = new Label(text);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setAlignment(Label.CENTER);
        label.setPreferredSize(new Dimension(300, 50));
        label.setForeground(darkBrown);
        return label;
    }

    public Label getText(String text)
    {
        Label label = new Label(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setAlignment(Label.LEFT);
        label.setPreferredSize(new Dimension(300, 20));
        label.setForeground(darkBrown);
        return label;
    }

    public JButton getButton(String text)
    {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 50));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusable(false);
        button.setBackground(this.warmWhite);
        button.setForeground(darkBrown);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        if (text.equals("Main Menu"))
        {
            button.addActionListener(e -> {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(button);
                frame.dispose();
                mainMenu();
            });
        }
        else if (text.equals("View statistics"))
        {
            button.addActionListener(e -> {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(button);
                frame.dispose();
                raceHistory();
            });
        }
        else if (text.equals("Edit track"))
        {
            button.addActionListener(e -> {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(button);
                frame.dispose();
                editTrack();
            });
        }
        else if (text.equals("Start race"))
        {
            button.addActionListener(e -> {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(button);
                frame.dispose();
                startRaceGUI();
            });
        }
        return button;
    }

    public JPanel getButtons(String[] labels)
    {
        JPanel buttons = new JPanel();
        buttons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttons.setLayout(new GridLayout(labels.length, 1, 10, 10));
        buttons.setBackground(this.lightBrown);
        for (String label : labels)
        {
            JButton button = getButton(label);
            buttons.add(button);
        }
        return buttons;
    }

    public void mainMenu()
    {
        JFrame frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Label label = getTitle("Horse Race Simulator");
        frame.add(label, BorderLayout.NORTH);

        JPanel buttons = getButtons(new String[]{"View statistics", "Edit track", "Start race"});
        frame.add(buttons, BorderLayout.CENTER);

        frame.getContentPane().setBackground(this.lightBrown);
        buttons.setBackground(this.lightBrown);
        frame.pack();
        frame.setVisible(true);
        return;
    }

    public void raceHistory()
    {
        JFrame frame = new JFrame("Race History");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Label label = getTitle("Statistics and Analytics");
        frame.add(label, BorderLayout.NORTH);
        JPanel buttons = getButtons(new String[]{"Main Menu"});
        frame.add(buttons, BorderLayout.SOUTH);
        frame.getContentPane().setBackground(this.lightBrown);
        frame.pack();
        frame.setVisible(true);
        return;
    }

    public void editTrack()
    {
        JFrame frame = new JFrame("Edit Track");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Label title = getTitle("Edit Track");
        frame.add(title, BorderLayout.NORTH);
        JPanel horseInfo = new JPanel();
        horseInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        horseInfo.setLayout(new GridLayout(currentRace.getHorses().size(), 1, 10, 10));
        horseInfo.setBackground(this.lightBrown);
        String text;
        for (int i=0; i < currentRace.getHorses().size(); i++)
        {
            Horse horse = currentRace.getHorses().get(i);
            text = "Lane " + (i+1) + ": " + horse.getName();
            text += " (confidence: " + horse.getConfidence() + ")";
            Label label = getText(text);
            horseInfo.add(label);
        }
        frame.add(horseInfo, BorderLayout.CENTER);
        JPanel buttons = getButtons(new String[]{"Main Menu"});
        frame.add(buttons, BorderLayout.SOUTH);
        frame.getContentPane().setBackground(this.lightBrown);
        frame.pack();
        frame.setVisible(true);
        return;
    }

    public void startRaceGUI()
    {
        JFrame frame = new JFrame("Horse Race");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Label label = getTitle("Horse race");
        frame.add(label, BorderLayout.NORTH);
        JPanel buttons = getButtons(new String[]{"Main Menu"});
        frame.add(buttons, BorderLayout.SOUTH);
        frame.getContentPane().setBackground(this.lightBrown);
        frame.pack();
        frame.setVisible(true);
        return;
    }
}

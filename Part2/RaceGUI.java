import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import javax.swing.Timer;

public class RaceGUI {
    Race currentRace;
    Color lightBrown = new Color(222, 200, 175);
    Color brownWhite = new Color(253, 240, 228);
    Color shadowWhite = new Color(248, 234, 222);
    Color darkBrown = new Color(39, 19, 10);
    Color warmWhite = new Color(255, 253, 248);
    Color darkRed = new Color(164, 23, 11);

    public RaceGUI(int trackLength)
    {
        currentRace = new Race(trackLength);
    }
    public  static void main(String[] args)
    {
        RaceGUI raceGUI = new RaceGUI(10);
        for (int i = 0; i < 3; i++)
        {
            Horse horse = raceGUI.currentRace.generateHorse();
            raceGUI.currentRace.addHorse(horse);
        }
        raceGUI.mainMenu();
        return;
    }

    public Label getTitle(String text)
    {
        Label label = new Label(text);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setAlignment(Label.CENTER);
        label.setPreferredSize(new Dimension(270, 50));
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
        button.setBackground(brownWhite);
        button.setForeground(darkBrown);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(shadowWhite);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(brownWhite);
            }
        });
        if (text.equals("Main Menu") || text.equals("View Statistics") || 
            text.equals("Edit Track") || text.equals("Start Race"))
        {
            button.addActionListener(e -> {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(button);
                frame.dispose();
            });
            if (text.equals("Main Menu"))
            {
                button.addActionListener(e -> {
                    mainMenu();
                });
            }
            else if (text.equals("View Statistics"))
            {
                button.addActionListener(e -> {
                    raceHistory();
                });
            }
            else if (text.equals("Edit Track"))
            {
                button.addActionListener(e -> {
                    editTrack();
                });
            }
            else
            {
                button.addActionListener(e -> {
                    startRaceGUI();
                });
            }
        }
        else if(!text.equals("Confirm"))
        {
            button.setPreferredSize(new Dimension(80, 30));
            button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }
        return button;
    }

    public JPanel getPanel(int rows, int cols, int padding)
    {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        panel.setLayout(new GridLayout(rows, cols, 10, 10));
        panel.setBackground(this.lightBrown);
        return panel;
    }

    public JPanel getButtons(String[] labels)
    {
        JPanel buttons = getPanel(labels.length, 1, 10);
        for (String label : labels)
        {
            JButton button = getButton(label);
            buttons.add(button);
        }
        return buttons;
    }

    public Label getText(String text)
    {
        Label label = new Label(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setAlignment(Label.LEFT);
        label.setPreferredSize(new Dimension(50, 20));
        label.setForeground(darkBrown);
        return label;
    }

    public JTextField getTextField(String text)
    {
        JTextField textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.LEFT);
        textField.setPreferredSize(new Dimension(60, 30));
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setText(text);
        textField.setEditable(false);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textField.setCaretColor(warmWhite);
        textField.setBackground(warmWhite);
        textField.setForeground(darkBrown);
        textField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textField.setText("");
                textField.setForeground(darkBrown);
                textField.setEditable(true);
                textField.setCaretColor(darkBrown);
            }
        });
        return textField;
    }

    public JTextField addError(String text, JTextField textField)
    {
        textField.setText(text);
        textField.setForeground(darkRed);
        textField.setCaretColor(warmWhite);
        textField.setEditable(false);
        return textField;
    }

    public <T> JComboBox<T> getDropDown(ArrayList<T> options)
    {
        T[] optionsArray = (T[]) options.toArray();
        JComboBox<T> dropDown = new JComboBox<>(optionsArray);
        dropDown.setPreferredSize(new Dimension(100, 30));
        dropDown.setBackground(warmWhite);
        dropDown.setForeground(darkBrown);
        dropDown.setFont(new Font("Arial", Font.PLAIN, 16));
        return dropDown;
    }

    public JPanel getHorse(Horse horse)
    {
        JPanel horsePanel = getPanel(1, 2, 0);
        if (horse == null)
        {
            return horsePanel;
        }
        Label horseSymbol = getText(Character.toString(horse.getSymbol()));
        horseSymbol.setAlignment(Label.RIGHT);
        horseSymbol.setForeground(horse.getColour());
        horseSymbol.setFont(new Font("Arial", Font.PLAIN, 21));
        horsePanel.add(horseSymbol);
        Label horseAccessory = getText(horse.getAccessory());
        horsePanel.add(horseAccessory);
        return horsePanel;
    }

    public JProgressBar getProgressBar(Horse horse)
    {
        JProgressBar progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(this.currentRace.getRaceLength());
        progressBar.setString("0");
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        UIManager.put("progressBar.selectionBackground", horse.getColour());
        UIManager.put("progressBar.selectionForeground", horse.getColour());
        progressBar.setPreferredSize(new Dimension(200, 30));
        return progressBar;
    }

    public void mainMenu()
    {
        JFrame frame = new JFrame("Horse Race Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Label label = getTitle("Main Menu");
        frame.add(label, BorderLayout.NORTH);

        JPanel buttons = getButtons(new String[]{"Start Race", "Edit Track", "View Statistics"});
        frame.add(buttons, BorderLayout.CENTER);

        frame.getContentPane().setBackground(this.lightBrown);
        buttons.setBackground(this.lightBrown);
        frame.pack();
        frame.setVisible(true);
        return;
    }

    public void raceHistory()
    {
        JFrame frame = new JFrame("Horse Race Simulator");
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
        // Title
        JFrame frame = new JFrame("Horse Race Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Label title = getTitle("Edit Track");
        frame.add(title, BorderLayout.NORTH);

        JPanel trackInfo = getPanel(5+currentRace.getHorses().size(), 1, 10);

        // Track length
        trackInfo.add(getText("Change track length:"));
        JPanel editLength1 = getPanel(1, 3, 0);
        JPanel editLength2 = getPanel(1, 2, 0);
        editLength1.add(getText(""));
        Label label = getText("Track length (from 2 to 99):");
        editLength1.add(label);
        JTextField lengthInput = getTextField(Integer.toString(currentRace.getRaceLength()));
        editLength2.add(lengthInput);
        JButton updateButton = getButton("Update");
        updateButton.addActionListener(e -> {
            try{
                int newLength = Integer.parseInt(lengthInput.getText());
                if (newLength <= 1)
                {
                    addError("INV <= 1", lengthInput);
                }
                else if (newLength >= 100)
                {
                    addError("INV > 99", lengthInput);
                }
                currentRace.setRaceLength(newLength);
                lengthInput.setEditable(false);
                lengthInput.setCaretColor(warmWhite);
            }
            catch (NumberFormatException ex)
            {
                addError("INV num", lengthInput);
            }
        });
        editLength2.add(updateButton);
        editLength1.add(editLength2);
        trackInfo.add(editLength1);

        frame.add(trackInfo, BorderLayout.CENTER);

        // Add horse
        Label addHorseLabel = getText("Add Horse:");
        trackInfo.add(addHorseLabel);
        Horse horse = currentRace.generateHorse();
        JPanel addHorse = getPanel(1, 6, 0);
        JTextField horseName = getTextField(horse.getName());
        JTextField horseSpeed = getTextField("" + horse.getConfidence());
        JButton randomButton = getButton("Random");
        randomButton.addActionListener(e -> {
            Horse newHorse = currentRace.generateHorse();
            horseName.setText(newHorse.getName());
            horseName.setForeground(darkBrown);
            horseSpeed.setText("" + newHorse.getConfidence());
            horseSpeed.setForeground(darkBrown);
        });
        addHorse.add(randomButton);
        addHorse.add(getText("Name:"));
        addHorse.add(horseName);
        addHorse.add(getText("Speed:"));
        addHorse.add(horseSpeed);
        JButton addHorseButton = getButton("Add");
        if (currentRace.getHorses().size() >= 10)
        {
            addHorseButton.setBackground(shadowWhite);
            addHorseButton.setEnabled(false);
        }
        else
        {
            addHorseButton.addActionListener(e -> {
                try{
                    String name = horseName.getText();
                    if (name.length() == 0 || name.equals("empty")) {
                        addError("empty", horseName);
                    }
                    if (horseSpeed.getText().length() == 0 || horseSpeed.getText().equals("empty")) {
                        addError("empty", horseSpeed);
                    }
                    name = name.toUpperCase();
                    for (Horse h : currentRace.getHorses())
                    {
                        if (h.getName().equals(name))
                        {
                            addError("duplicate", horseName);
                            return;
                        }
                    }
                    if (horseName.getForeground() == darkRed || horseSpeed.getForeground() == darkRed) {
                        return;
                    }
                    double inputSpeed = Double.parseDouble(horseSpeed.getText());
                    double speed = (int)(inputSpeed*10)/10.0;
                    if (speed < 0.1)
                    {
                        addError("INV < 0.1", horseSpeed);
                        return;
                    }
                    else if (speed > 0.9)
                    {
                        addError("INV > 0.9", horseSpeed);
                        return;
                    }
                    Horse newHorse = new Horse(name.charAt(0), name, speed);
                    frame.dispose();
                    currentRace.addHorse(newHorse);
                    editTrack();
                }
                catch (NumberFormatException ex)
                {
                    addError("INV num", horseSpeed);
                }
            });
        }
        addHorse.add(addHorseButton);
        trackInfo.add(addHorse);

        // Remove and edit horses
        trackInfo.add(getText("Edit / Remove Horse:"));
        for (int i=0; i < currentRace.getHorses().size(); i++)
        {
            JPanel horseRow = getPanel(1, 6, 0);
            Horse currentHorse = currentRace.getHorses().get(i);

            // edit horse
            JButton editButton = getButton("Edit");
            editButton.addActionListener(e -> {
                frame.dispose();
                Horse editedHorse = new Horse(  currentHorse.getSymbol(), 
                                                currentHorse.getName(), 
                                                currentHorse.getConfidence());
                editedHorse.setAccessory(currentHorse.getAccessory());
                editedHorse.setSymbol(currentHorse.getSymbol());
                editedHorse.setSymbolColour(currentHorse.getSymbolColour());
                editHorse(currentHorse, editedHorse);
            });
            horseRow.add(editButton);

            // horse info
            String text = "Lane " + (i+1) + ":";
            horseRow.add(getText(text));
            horseRow.add(getHorse(currentHorse));
            horseRow.add(getText(currentHorse.getName()));
            text = "Speed: " + currentHorse.getConfidence();
            horseRow.add(getText(text));

            // remove horse
            JButton removeButton = getButton("Remove");
            if (currentRace.getHorses().size() <= 2)
            {
                removeButton.setBackground(shadowWhite);
                removeButton.setEnabled(false);
            }
            else
            {
                removeButton.addActionListener(e -> {
                    if (currentRace.getHorses().size() <= 2)
                    {
                        return;
                    }
                    currentRace.removeHorse(currentHorse);
                    frame.dispose();
                    editTrack();
                });
            }
            horseRow.add(removeButton);
            trackInfo.add(horseRow);
        }

        frame.add(trackInfo, BorderLayout.CENTER);

        // Back to menu
        JPanel buttons = getButtons(new String[]{"Main Menu"});
        frame.add(buttons, BorderLayout.SOUTH);
        frame.getContentPane().setBackground(this.lightBrown);
        frame.pack();
        frame.setVisible(true);
        return;
    }

    public void editHorse(Horse originalHorse, Horse editedHorse)
    {
        JFrame frame = new JFrame("Horse Race Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Label label = getTitle("Edit Horse");
        frame.add(label, BorderLayout.NORTH);

        JPanel horseEditor = getPanel(5, 2, 10);
        
        // Horse displayed while editing
        horseEditor.add(getText(editedHorse.getName() + " (" + editedHorse.getConfidence() + ")"));
        horseEditor.add(getHorse(editedHorse));

        // Reset and cancel buttons
        JButton reset = getButton("Reset");
        reset.addActionListener(e -> {
            frame.dispose();
            editedHorse.setAccessory(originalHorse.getAccessory());
            editedHorse.setSymbol(originalHorse.getSymbol());
            editedHorse.setSymbolColour(originalHorse.getSymbolColour());
            editHorse(originalHorse, editedHorse);
        });
        horseEditor.add(reset);
        JButton cancel = getButton("Cancel");
        cancel.addActionListener(e -> {
            editedHorse.setSymbol(originalHorse.getSymbol());
            frame.dispose();
            editTrack();
        });
        horseEditor.add(cancel);

        // Change accessory
        horseEditor.add(getText("Accessory:"));
        JComboBox accessories = getDropDown(editedHorse.getAccessories());
        accessories.setSelectedItem(editedHorse.getAccessory());
        accessories.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newAccessory = (String) accessories.getSelectedItem();
                editedHorse.setAccessory(newAccessory);
                frame.dispose();
                editHorse(originalHorse, editedHorse);
            }
        });
        horseEditor.add(accessories);

        // Change symbol
        horseEditor.add(getText("Symbol:"));
        JComboBox symbols = getDropDown(editedHorse.getSymbols());
        symbols.setSelectedItem(editedHorse.getSymbol());
        symbols.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                char newSymbol = (char) symbols.getSelectedItem();
                editedHorse.setSymbol(newSymbol);
                frame.dispose();
                editHorse(originalHorse, editedHorse);
            }
        });
        horseEditor.add(symbols);

        // Change symbol colour
        horseEditor.add(getText("Symbol Colour:"));
        JComboBox colours = getDropDown(editedHorse.getColours());
        colours.setSelectedItem(editedHorse.getSymbolColour());
        colours.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newColour = (String) colours.getSelectedItem();
                editedHorse.setSymbolColour(newColour);
                frame.dispose();
                editHorse(originalHorse, editedHorse);
            }
        });
        horseEditor.add(colours);

        frame.add(horseEditor);

        // Confirm button
        JPanel buttons = getPanel(1,1,10);
        JButton confirm = getButton("Confirm");
        confirm.addActionListener(e -> {
            frame.dispose();
            originalHorse.setAccessory(editedHorse.getAccessory());
            originalHorse.setSymbol(editedHorse.getSymbol());
            originalHorse.setSymbolColour(editedHorse.getSymbolColour());
            editTrack();
        });
        buttons.add(confirm);
        frame.add(buttons, BorderLayout.SOUTH);
        frame.getContentPane().setBackground(this.lightBrown);
        frame.pack();
        frame.setVisible(true);
        return;
    }

    public void startRaceGUI()
    {
        JFrame frame = new JFrame("Horse Race Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Label label = getTitle("Horse race");
        frame.add(label, BorderLayout.NORTH);

        JPanel buttons = getButtons(new String[]{"Main Menu"});
        frame.add(buttons, BorderLayout.SOUTH);
        frame.getContentPane().setBackground(this.lightBrown);
        frame.pack();
        frame.setVisible(true);
        
        // start race
        this.currentRace.resetLanes();
        frame.dispose();
        displayRace();
    }

    public void displayRace()
    {
        JFrame frame = new JFrame("Horse Race Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(getTitle("Horse race"), BorderLayout.NORTH);
        Race race = this.currentRace;
        ArrayList<Horse> horses = race.getHorses();
        ArrayList<JProgressBar> progressBars = new ArrayList<JProgressBar>();
        JPanel lanes = getPanel(horses.size(), 1, 10);
        for (Horse horse : horses)
        {
            JPanel lane = getPanel(1, 2, 0);
            JPanel horseInfo = getPanel(1, 2, 0);
            horseInfo.add(getHorse(horse));
            horseInfo.add(getText(horse.getName() + " (" + horse.getConfidence() + ")"));
            lane.add(horseInfo);
            JProgressBar progressBar = getProgressBar(horse);
            lane.add(progressBar);
            progressBars.add(progressBar);
            lanes.add(lane);
        }
        frame.add(lanes, BorderLayout.CENTER);
        frame.getContentPane().setBackground(this.lightBrown);
        frame.pack();

        Label time = getText("Time: 0.0 seconds");

        // update progress bars
        int delay = 2000/race.getRaceLength();
        Timer timer = new Timer(delay, null);
        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                race.setTime(race.getTime()+0.1);
                time.setText("Time: " + race.getTime() + " seconds"); 
                for (int i = 0; i < horses.size(); i++) {
                    Horse horse = horses.get(i);
                    race.moveHorse(horse);
                    JProgressBar progressBar = progressBars.get(i);
                    progressBar.setValue(horse.getDistanceTravelled());
                    progressBar.setString(horse.getDistanceTravelled() + "");
                    if (horse.hasFallen()) {
                        progressBar.setString("FALLEN");
                    }
                    frame.pack();
                    frame.setVisible(true);
                }
                if (race.raceWon() || race.allFell())
                {
                    timer.stop();
                    frame.dispose();
                    displayResults(lanes);
                }
            }
        });

        timer.start();

        // quit
        JPanel bottomRow = getPanel(1,3,10);
        JButton quitButton = getButton("Quit");
        bottomRow.add(quitButton);
        quitButton.addActionListener(e -> {
            timer.stop();
            frame.dispose();
            mainMenu();
        });
        bottomRow.add(getText(""));
        bottomRow.add(time);
        frame.add(bottomRow, BorderLayout.SOUTH);
    }

    public void displayResults(JPanel lanes)
    {
        JFrame frame = new JFrame("Horse Race Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if(this.currentRace.allFell())
        {
            Label label = getTitle("No winner");
            frame.add(label, BorderLayout.NORTH);
            Label text = getText("All horses have fallen");
            text.setAlignment(Label.CENTER);
            frame.add(text, BorderLayout.CENTER);
        }
        else
        {
            ArrayList<Horse> winners = this.currentRace.getWinners();
            ArrayList<Horse> horses = this.currentRace.getHorses();
            ArrayList<Horse> fallen = this.currentRace.getFallen();
            JPanel centerPanel = getPanel(winners.size()*2+1+horses.size(), 1, 10);
            JPanel titles = getPanel(1, 2, 0);
            Label title = getTitle("Winner");;
            if (winners.size() > 1)
            {
                title = getTitle("Winners");
            }
            titles.add(title);
            if (fallen.size() > 0)
            {
                titles.add(getTitle("Fallen"));
                frame.add(titles, BorderLayout.NORTH);

                // display winners and fallen horses
                int rows = 2 * Math.max(winners.size(), fallen.size()) + 1 + horses.size();
                System.out.println(rows);
                centerPanel = getPanel(rows, 1, 10);
                for (int i=0; i<Math.max(winners.size(), fallen.size()); i++)
                {
                    Horse winner = winners.size() > i ? winners.get(i) : null;
                    Horse faller = fallen.size() > i ? fallen.get(i) : null;
                    JPanel row1 = getPanel(1, 2, 0);
                    row1.add(getHorse(winner));
                    row1.add(getHorse(faller));
                    centerPanel.add(row1);

                    JPanel row2 = getPanel(1, 2, 0);
                    for (Horse h : new Horse[]{winner, faller})
                    {
                        Label nameSpeed = getText(" ");
                        if (h != null)
                        {
                            nameSpeed.setText(h.getName() + " (new speed: " + h.getConfidence() + ")");
                        }
                        nameSpeed.setAlignment(Label.CENTER);
                        row2.add(nameSpeed);
                    }
                    centerPanel.add(row2);
                }
            }
            else
            {
                frame.add(title, BorderLayout.NORTH);
                for (Horse h: winners)
                {
                    centerPanel.add(getHorse(h));
                    Label nameSpeed = getText(h.getName() + " (new speed: " + h.getConfidence() + ")");
                    nameSpeed.setAlignment(Label.CENTER);
                    centerPanel.add(nameSpeed);
                }
            }
            Label winnerTime = getText("Time: " + this.currentRace.getTime() + " seconds");
            winnerTime.setAlignment(Label.CENTER);
            centerPanel.add(winnerTime);
            for (Component lane: lanes.getComponents())
            {
                centerPanel.add(lane);
            }
            frame.add(centerPanel, BorderLayout.CENTER);
        }

        JPanel buttons = getButtons(new String[]{"Start Race", "Main Menu"});
        frame.add(buttons, BorderLayout.SOUTH);
        frame.getContentPane().setBackground(this.lightBrown);
        frame.pack();
        frame.setVisible(true);
    }
}

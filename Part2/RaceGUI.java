import javax.swing.*;
import java.awt.*;

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
        else
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

        JPanel trackInfo = getPanel(4+currentRace.getHorses().size(), 1, 10);

        // Track length
        JPanel editLength1 = getPanel(1, 2, 0);
        JPanel editLength2 = getPanel(1, 2, 0);
        Label label = getText("Track length:");
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
        JPanel addHorse = getPanel(1, 4, 0);
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
        addHorse.add(horseName);
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
                    if (horseName.getForeground() == darkRed || horseSpeed.getForeground() == darkRed) {
                        return;
                    }
                    name = name.toUpperCase();
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

        // Remove horses
        String text;
        trackInfo.add(getText("Remove Horse:"));
        for (int i=0; i < currentRace.getHorses().size(); i++)
        {
            JPanel horseRow = getPanel(1, 4, 0);
            Horse currentHorse = currentRace.getHorses().get(i);
            text = "Lane " + (i+1) + ": ";
            horseRow.add(getText(text));
            horseRow.add(getText(currentHorse.getName()));
            text = "Speed: " + currentHorse.getConfidence();
            horseRow.add(getText(text));
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
        return;
    }
}

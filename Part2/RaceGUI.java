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
        else if (text.equals("Update"))
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
        textField.setPreferredSize(new Dimension(50, 30));
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
        textField.setForeground(Color.RED);
        textField.setCaretColor(warmWhite);
        textField.setEditable(false);
        return textField;
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
        // Title
        JFrame frame = new JFrame("Edit Track");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Label title = getTitle("Edit Track");
        frame.add(title, BorderLayout.NORTH);

        JPanel trackInfo = getPanel(1, 1, 10);

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

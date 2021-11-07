package com.baah;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GuessGame extends MiniGame {
    // Form Variables
    public JFrame frame;
    private JPanel panel1;
    private JLabel label1;
    private JButton checkButton;
    private JTextField guessField;
    private JLabel fieldlabel;
    private JLabel livesLabel;
    private JLabel helpLabel;

    private int triesLeft = 7;
    private int guessNum;

    // Constructor
    public GuessGame(MainGame mg) {
        super(mg);
        frame = new JFrame("Guess Game");
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        guessNum = new Random().nextInt(101); // because bound is exclusive we write 100+1 so 100 is included
        System.out.println(guessNum);
        checkButton.addActionListener(e -> check());
    }

    /**
     * Function to Update UI and Variable with Tries
     * 
     * @param amount
     */
    private void updateTries(int amount) {
        triesLeft += amount;
        livesLabel.setText("Tries: " + triesLeft);
    }

    /**
     * Function to Check users Guess
     */
    private void check() {
        try {
            int enteredVal = Integer.parseInt(guessField.getText());
            if (enteredVal >= 0 && enteredVal <= 100) {
                if (enteredVal > guessNum) {
                    helpLabel.setText("Please enter lesser number!");
                    updateTries(-1);
                } else if (enteredVal < guessNum) {
                    helpLabel.setText("Please enter greater number!");
                    updateTries(-1);
                } else if (enteredVal == guessNum) {
                    helpLabel.setText("Number Matched!");
                    win();
                }
                if (triesLeft == 0) {
                    helpLabel.setText("Sorry, no turns left.");
                    lose();
                }
            } else {
                throw new Exception("The range to enter integers is 0 to 100.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please enter only positive integers from 0 to 100.");
        }

    }

}

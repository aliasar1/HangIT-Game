package com.baah;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RockPaperScissor extends MiniGame {

    // Form Variables
    public JFrame frame;
    private JButton rockBtn;
    private JButton paperBtn;
    private JButton scissorBtn;
    private JLabel roundLabel;
    private JLabel statusLable;
    private JPanel panel1;
    private JLabel scoreLabel;

    private int round = 1;
    private int compWins = 0;
    private int playerWins = 0;

    // Constructor
    public RockPaperScissor(MainGame mg) {
        super(mg);
        frame = new JFrame("Rock Paper Scissor Game");
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        rockBtn.addActionListener(new rpsBtnListener());
        paperBtn.addActionListener(new rpsBtnListener());
        scissorBtn.addActionListener(new rpsBtnListener());
    }

    /**
     * Function to proceed to Next round
     */
    private void nextRound() {
        round += 1;
        roundLabel.setText("Round: " + round + "/3");
    }

    // Sub Class
    private class rpsBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            int computerChoice = new Random().nextInt(3);
            System.out.println(computerChoice);
            int playerChoice;

            if (a.getSource() == rockBtn) {
                playerChoice = 0;
            } else if (a.getSource() == paperBtn) {
                playerChoice = 1;
            } else if (a.getSource() == scissorBtn) {
                playerChoice = 2;
            } else {
                playerChoice = -1;
            }

            if (playerChoice == computerChoice) {
                statusLable.setText("Its a Draw");
                System.out.println("Its a Draw");
            } else if (playerChoice == 0 && computerChoice == 1) {
                statusLable.setText("Computer won this round!");
                compWins++;
            } else if (playerChoice == 1 && computerChoice == 2) {
                statusLable.setText("Computer won this round!");
                compWins++;
            } else if (playerChoice == 2 && computerChoice == 0) {
                statusLable.setText("Computer won this round!");
                compWins++;
            } else {
                statusLable.setText("You won this round!");
                playerWins++;
            }

            scoreLabel.setText("Computer Score: " + compWins + "      Player Score: " + playerWins);
            if (round == 3) {
                if (playerWins == compWins) {
                    statusLable.setText("The match is draw!");
                    lose();
                } else if (playerWins > compWins) {
                    statusLable.setText("Player Wins The Match!");
                    win();
                } else {
                    statusLable.setText("Computer Wins The Match!");
                    lose();
                }
            }
            if (playerChoice != computerChoice) {
                // Only if not DRAW
                nextRound(); // round+1
            }
        }
    }
}

package com.baah;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class TicTacToe extends MiniGame {
    // Form Variables
    public JFrame frame;
    private JButton[][] buttons = new JButton[3][3]; // Array of Buttons

    private int moveCounter = 9; // Total Moves
    private boolean gameWon = false;
    private int WhoseTurn = 1;

    // Constructor
    public TicTacToe(MainGame mg) {
        super(mg);
        // Set the Frame and show
        frame = new JFrame("TicTacToe");
        frame.setSize(350, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel game = new JPanel(new GridLayout(3, 3));
        frame.add(mainPanel);
        mainPanel.setPreferredSize(new Dimension(300, 300));
        game.setPreferredSize(new Dimension(300, 300));
        mainPanel.add(game, BorderLayout.SOUTH);

        // Dynamically Create grid of buttons for tic tac toe game
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setText("");
                buttons[i][j].setVisible(true);
                buttons[i][j].setBackground(new Color(173, 239, 209));

                game.add(buttons[i][j]);
                buttons[i][j].addActionListener(new myActionListener());
            }
        }
    }

    /**
     * Function to Check if some one has won
     * 
     * @param row
     * @param col
     */
    private void checkWin(int row, int col) {
        if (!gameWon) {
            String winner = "";
            /**
             * Total Win Conditions:
             * 8 Possibilities:
             * 1. Top Across
             * 2. Middle Across
             * 3. Bottom Across
             *
             * 4. Left Vertical
             * 5. Middle Vertical
             * 6. Right Vertical
             *
             * 7. Diagonal LtR \
             * 8. Diagonal RtL /
             */
            if (buttons[0][2].getText().equals(buttons[1][2].getText())
                    && buttons[1][2].getText().equals(buttons[2][2].getText())
                    && buttons[2][2].getText().equals(buttons[0][2].getText()) && !buttons[1][2].getText().equals("")) {
                gameWon = true;
                WhoseTurn = 0;
                winner = buttons[1][2].getText();
            } else if (buttons[0][1].getText().equals(buttons[1][1].getText())
                    && buttons[1][1].getText().equals(buttons[2][1].getText())
                    && buttons[2][1].getText().equals(buttons[0][1].getText()) && !buttons[1][1].getText().equals("")) {
                gameWon = true;
                WhoseTurn = 0;
                winner = buttons[1][1].getText();
            } else if (buttons[0][0].getText().equals(buttons[1][0].getText())
                    && buttons[1][0].getText().equals(buttons[2][0].getText())
                    && buttons[2][0].getText().equals(buttons[0][0].getText()) && !buttons[1][0].getText().equals("")) {
                gameWon = true;
                WhoseTurn = 0;
                winner = buttons[1][0].getText();
            } else if (buttons[2][0].getText().equals(buttons[2][1].getText())
                    && buttons[2][1].getText().equals(buttons[2][2].getText())
                    && buttons[2][2].getText().equals(buttons[2][0].getText()) && !buttons[2][1].getText().equals("")) {
                gameWon = true;
                WhoseTurn = 0;
                winner = buttons[2][1].getText();
            } else if (buttons[0][0].getText().equals(buttons[0][1].getText())
                    && buttons[0][1].getText().equals(buttons[0][2].getText())
                    && buttons[0][2].getText().equals(buttons[0][0].getText()) && !buttons[0][1].getText().equals("")) {
                gameWon = true;
                WhoseTurn = 0;
                winner = buttons[0][1].getText();
            } else if (buttons[1][0].getText().equals(buttons[1][1].getText())
                    && buttons[1][1].getText().equals(buttons[1][2].getText())
                    && buttons[1][2].getText().equals(buttons[1][0].getText()) && !buttons[1][1].getText().equals("")) {
                gameWon = true;
                WhoseTurn = 0;
                winner = buttons[1][1].getText();
            } else if (buttons[0][0].getText().equals(buttons[1][1].getText())
                    && buttons[1][1].getText().equals(buttons[2][2].getText())
                    && buttons[2][2].getText().equals(buttons[0][0].getText()) && !buttons[1][1].getText().equals("")) {
                gameWon = true;
                WhoseTurn = 0;
                winner = buttons[1][1].getText();
            } else if (buttons[0][2].getText().equals(buttons[1][1].getText())
                    && buttons[1][1].getText().equals(buttons[2][0].getText())
                    && buttons[2][0].getText().equals(buttons[0][2].getText()) && !buttons[1][1].getText().equals("")) {
                gameWon = true;
                WhoseTurn = 0;
                winner = buttons[1][1].getText();
            }

            // Check if there was a winner for the Game
            if (winner.equals("X")) {
                System.out.println("You Won!!!");
                JOptionPane.showMessageDialog(frame, "You Won!!!");
                win();
            } else if (winner.equals("O")) {
                System.out.println("You Lost!!!");
                JOptionPane.showMessageDialog(frame, "You Lost!!!");
                lose();
            } else if (moveCounter <= 0) {
                System.out.println("You Tied!!!");
                JOptionPane.showMessageDialog(frame, "Game Tied!!!");
                lose();
            }
        }
    }

    /**
     * Function to simulate Computers Turn
     * 
     * @param count
     */
    private void compTurn(int count) {
        if (count != 0) {
            int randomMove = count;
            randomMove = new Random().nextInt(randomMove) + 1;

            while (!gameWon && WhoseTurn == 2) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (buttons[i][j].isEnabled()) {
                            randomMove--;

                            if (randomMove == 0) {
                                buttons[i][j].setText("O");
                                buttons[i][j].setEnabled(false);
                                moveCounter--;
                                checkWin(i, j);
                                WhoseTurn = 1;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Class to implement ActionListener to use for Buttons
     */
    private class myActionListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            // Display X's or O's on the buttons
            if (!gameWon) {
                // Checking which button is pressed
                if (a.getSource() == buttons[0][0]) {
                    buttons[0][0].setText("X");
                    buttons[0][0].setEnabled(false);
                    WhoseTurn = 2;
                    moveCounter--;
                    checkWin(0, 0);
                    compTurn(moveCounter);
                } else if (a.getSource() == buttons[0][1]) {
                    buttons[0][1].setText("X");
                    buttons[0][1].setEnabled(false);
                    WhoseTurn = 2;
                    moveCounter--;
                    checkWin(0, 1);
                    compTurn(moveCounter);
                } else if (a.getSource() == buttons[1][0]) {
                    buttons[1][0].setText("X");
                    buttons[1][0].setEnabled(false);
                    WhoseTurn = 2;
                    moveCounter--;
                    checkWin(1, 0);
                    compTurn(moveCounter);
                } else if (a.getSource() == buttons[1][1]) {
                    buttons[1][1].setText("X");
                    buttons[1][1].setEnabled(false);
                    WhoseTurn = 2;
                    moveCounter--;
                    checkWin(1, 1);
                    compTurn(moveCounter);
                } else if (a.getSource() == buttons[1][2]) {
                    buttons[1][2].setText("X");
                    buttons[1][2].setEnabled(false);
                    WhoseTurn = 2;
                    moveCounter--;
                    checkWin(1, 2);
                    compTurn(moveCounter);
                } else if (a.getSource() == buttons[2][2]) {
                    buttons[2][2].setText("X");
                    buttons[2][2].setEnabled(false);
                    WhoseTurn = 2;
                    moveCounter--;
                    checkWin(2, 2);
                    compTurn(moveCounter);
                } else if (a.getSource() == buttons[0][2]) {
                    buttons[0][2].setText("X");
                    buttons[0][2].setEnabled(false);
                    WhoseTurn = 2;
                    moveCounter--;
                    checkWin(0, 2);
                    compTurn(moveCounter);
                } else if (a.getSource() == buttons[2][1]) {
                    buttons[2][1].setText("X");
                    buttons[2][1].setEnabled(false);
                    WhoseTurn = 2;
                    moveCounter--;
                    checkWin(2, 1);
                    compTurn(moveCounter);
                } else if (a.getSource() == buttons[2][0]) {
                    buttons[2][0].setText("X");
                    buttons[2][0].setEnabled(false);
                    WhoseTurn = 2;
                    moveCounter--;
                    checkWin(2, 0);
                    compTurn(moveCounter);
                }
            }
        }
    }
}
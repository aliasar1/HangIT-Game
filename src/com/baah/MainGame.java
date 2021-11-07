package com.baah;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class MainGame {

    // Form Variables
    private JFrame mainFrame = new JFrame("Main Game");
    private JPanel rootPanel;
    private JButton tictacBtn;
    private JPanel secondaryPanel;
    private JPanel primaryPanel;
    private JTextField guessField;
    private JLabel currentWordLabel;
    private JLabel livesLabel;
    private JButton guessBtn;
    private JButton rpsBtn;
    private JButton checkBtn;

    private String[] words;
    private int lives = 3;
    private String randomWord;
    private String currentWord;

    // MiniGames
    private GuessGame guessGame;
    private TicTacToe tictactoe;
    private RockPaperScissor rockPaperScissor;

    // Constructor
    public MainGame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Get Users Screen Size
        // Setup our MainFrame
        mainFrame.setMinimumSize(new Dimension(screenSize));
        mainFrame.setContentPane(rootPanel);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);

        MainGame mg = this; // store it to reference inside ActionListener

        // Add ActionListeners to Buttons

        // Tictactoe Button
        tictacBtn.addActionListener(e -> {
            primaryPanel.setVisible(false);
            secondaryPanel.setVisible(true);
            tictactoe = new TicTacToe(mg);
        });
        // Guess Game Button
        guessBtn.addActionListener(e -> {
            primaryPanel.setVisible(false);
            secondaryPanel.setVisible(true);
            guessGame = new GuessGame(mg);
        });
        // Rock Paper Scissor Button
        rpsBtn.addActionListener(e -> {
            primaryPanel.setVisible(false);
            secondaryPanel.setVisible(true);
            rockPaperScissor = new RockPaperScissor(mg);
        });
        // Check Button
        checkBtn.addActionListener(e -> {
            if (guessField.getText().length() != 0) {
                String guess = guessField.getText();
                checkGuess(guess);
            }
        });

        // Disable MiniGame Buttons untill Life is low
        disableGames();

        // Use Update Lives Function with 0 value to update UI without change in
        // original Value
        updateLives(0);

        try {
            words = extractWords(); // Extract the list of words from File
        } catch (IOException e) {
            e.printStackTrace();
        }
        int randomIndex = new Random().nextInt(words.length);
        randomWord = words[randomIndex];
        setCurrentWord(getCensoredWord(randomWord));
        int randomhintIndex = new Random().nextInt(randomWord.length());
        checkGuess(randomWord.substring(randomhintIndex, randomhintIndex + 1));
        System.out.println("Word: " + randomWord);
    }

    /**
     * Function to get Array of Words from the File
     * 
     * @return
     * @throws IOException
     */
    public String[] extractWords() throws IOException {
        System.out.println("Reading File: " + System.getProperty("user.dir") + "\\src\\com\\baah\\words.txt");
        Path filePath = new File(System.getProperty("user.dir") + "\\src\\com\\baah\\words.txt").toPath();
        return Files.readAllLines(filePath).toArray(new String[0]);
    }

    /**
     * Function to check the Guess
     * 
     * @param guess
     */
    private void checkGuess(String guess) {
        try {
            if (guess.length() != 1) {
                throw new Exception("Please Enter one character at a time.");
            }
            if (!guess.matches("^[a-zA-Z]*$")) { // Regex to only accept alphabetic characters
                throw new Exception("Please Enter only Alphabetic Characters.");
            }
            System.out.println(guess);

            boolean goodGuessFlag = false;
            for (int i = 0; i < randomWord.length(); i++) {
                if (guess.equalsIgnoreCase(Character.toString(randomWord.charAt(i)))) {
                    goodGuessFlag = true;
                    System.out.println("Good Guess");

                    StringBuilder newWord = new StringBuilder(currentWord);
                    newWord.setCharAt(i, randomWord.charAt(i));

                    setCurrentWord(newWord.toString());
                }
            }
            if (!goodGuessFlag) {
                System.out.println("lose a life");
                if (lives != 0) {
                    updateLives(-1);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        guessField.setText("");
        if (currentWord.equals(randomWord)) {
            winGame();
        }
    }

    /**
     * Function to Win the Game
     */
    private void winGame() {
        int confirm = JOptionPane.showConfirmDialog(null,
                "You guess the word correctly. Do you want to continue and play more?", "Game",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.NO_OPTION) {
            System.exit(1);
        } else {
            mainFrame.dispose();
            new MainGame();
        }
    }

    /**
     * Function to get a Censored Word
     * 
     * @param word
     * @return
     */
    private String getCensoredWord(String word) {
        String censoredWord = new String();
        for (int i = 0; i < word.length(); i++) {
            censoredWord = censoredWord + "-";
        }
        return censoredWord;
    }

    /**
     * Function to Update UI with the Word
     * 
     * @param word
     */
    private void setCurrentWord(String word) {
        currentWord = word;
        this.currentWordLabel.setText(word);
    }

    /**
     * Function to Update UI and set lives.
     * 
     * @param amount
     */
    public void updateLives(int amount) {
        lives += amount;
        livesLabel.setText("Lives: " + lives);
        closeMiniGames();
        if (lives == 0) {
            enableGames();
        }
    }

    /**
     * Function to enable the mini Games
     */
    public void enableGames() {
        guessField.setEnabled(false);
        checkBtn.setEnabled(false);
        rpsBtn.setEnabled(true);
        guessBtn.setEnabled(true);
        tictacBtn.setEnabled(true);
    }

    /**
     * Function to Disable Game Buttons and enable Check Button and Field
     */
    public void disableGames() {
        guessField.setEnabled(true);
        checkBtn.setEnabled(true);
        rpsBtn.setEnabled(false);
        guessBtn.setEnabled(false);
        tictacBtn.setEnabled(false);
    }

    /**
     * Function to close all the mini Games which ever is open at the time
     */
    private void closeMiniGames() {
        if (guessGame != null) {
            guessGame.frame.dispose();
        }
        if (tictactoe != null) {
            tictactoe.frame.dispose();
        }
        if (rockPaperScissor != null) {
            rockPaperScissor.frame.dispose();
        }
        primaryPanel.setVisible(true);
        secondaryPanel.setVisible(false);
    }

    /**
     * Function to end the Game and Close it
     */
    public void endGame() {
        JOptionPane.showMessageDialog(null, "You lost the game! No lives left.");
        mainFrame.dispose();
    }

}

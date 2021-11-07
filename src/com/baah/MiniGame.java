package com.baah;

/**
 * Mini Game Parent Class. This class is the base class for any Mini game we are
 * going to create.
 */
public class MiniGame {
    MainGame mainGame;

    MiniGame(MainGame mg) {
        mainGame = mg;// set this in constructor so we can reference its functions like Win and lose
    }

    public void win() {
        mainGame.updateLives(1); // Add one life to Main game
        mainGame.disableGames(); // Disable the miniGames for now
        System.out.println("WIN Guess");
    }

    public void lose() {
        mainGame.updateLives(0); // Update lives by 0
        System.out.println("Lost Guess");
        mainGame.endGame(); // End the game as the user lost the mini game and has no lives left
    }

}

package org.spdcalpoly;

import java.util.ArrayList;

/**
 * GameManager.java
 *
 * The GameManager class is the brain of the Liar's Dice AI and controls all
 * interactions between the terminal client and the logic of the AI.
 *
 * @author Brandon M. Kelley
 * @since 09/08/2015
 * @version 1.0
 */
class GameManager {

    // Used to communicate with the AI player.
    private DialogManager dialogManager;

    /**
     * An empty constructor. Initializes all instance variables.
     */
    GameManager() {
        dialogManager = new DialogManager();
    }

    /**
     * Runs the AI based on input from the user.
     */
    void startAI() {

        // The current game the AI player is playing.
        Game currentGame;

        // Loop over each game to play.
        do {

            // Start a new game.
            currentGame = startGame();

            // Loop over each command to enter until the game is over.
            do {

                // Prompt the AI player for a command.
                String command = dialogManager.promptString("\nAI @> ");

                // Allow the game to handle the command.
                currentGame.processCommand(command);

            }
            while (currentGame.isPlayable());

            dialogManager.println(currentGame.getResults());

        }
        while (dialogManager.promptBoolean("\nPlay another game (yes or no)? "));

    }

    /**
     * Creates a new game from settings the AI player inputs.
     * @return The new game that was created.
     */
    private Game startGame() {

        // The game being started.
        Game newGame;

        // Display a friendly welcome message.
        dialogManager.println("\nLiar's Dice AI is starting...");
        dialogManager.println("Welcome new player!");
        dialogManager.print("\nThe AI is controlled by entering commands into ");
        dialogManager.print("the AI console.\nFor a complete list of commands, ");
        dialogManager.println("enter the \"help\" command.\n");

        // Ask the user for important information and create the game.
        int numPlayers = dialogManager.promptInteger("How many players are there? ");
        int numDice = dialogManager.promptInteger("How many dice does each player get to start? ");
        boolean usingWilds = dialogManager.promptBoolean("Are you playing with wild numbers (yes or no)? ");
        if (usingWilds) {
            ArrayList<Integer> wilds = dialogManager.promptIntegerArray("Which numbers are wild (delimit with spaces)? ", 6, false);
            newGame = new Game(dialogManager, numPlayers, numDice, wilds, new ProbabilityAI(), 0, 0);
        }
        else {
            newGame = new Game(dialogManager, numPlayers, numDice, new ProbabilityAI(), 0, 0);
        }

        return newGame;

    }

}

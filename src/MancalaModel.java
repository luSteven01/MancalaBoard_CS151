/**
 * Fall 2024 - Doctor Kim's CS151
 * CS151 Team Project - Mancala
 * @author Monica Zhang
 * @author Billy Porras-Molina
 * @author Steven Lu
 * @version 1.0 11/29/24
 */

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.List;
import java.util.ArrayList;

/**
 * This class serves as the data and logic of the mancala game. <br>
 * It maintains list of pits, undo count, the players, player's mancala, and the current player. <br>
 * Have a list of listeners to tell the views to reflect the change after the player does something.
 */
public class MancalaModel {

    private ArrayList<ChangeListener> listeners;
    private BoardPatternStrategy boardPattern;
    private boolean stonesInitialized;
    private String currentPlayer;

    private ArrayList<Pit> pits;
    private Pit mancalaA;
    private Pit mancalaB;

    private ArrayList<Pit> previousState;
    private int undoCountA = 0;
    private int undoCountB = 0;
    private final int MAX_UNDO = 3;
    private String lastMovePlayer = null; // Track the player who made the last move
    private boolean canUndo = false;

    /**
     * Constructs a MancalaModel and initializes the game state
     */
    public MancalaModel() {
        listeners = new ArrayList<ChangeListener>();
        pits = new ArrayList<Pit>();
        previousState = new ArrayList<Pit>();
        boardPattern = new Pattern1();
        initializeEmptyPits();
        stonesInitialized = false;
        currentPlayer = "A";
    }

    /**
     * Initializes the board with empty pits
     */
    private void initializeEmptyPits() {
        updatePreviousBoardState();
        for (int i = 0; i < 6; i++) {
            pits.add(new Pit());
        }

        mancalaB = new Pit();
        pits.add(mancalaB);

        for (int i = 0; i < 6; i++) {
            pits.add(new Pit());
        }
        mancalaA = new Pit();
        pits.add(mancalaA);

        updateListeners();
    }

    /**
     * Initializes the pits with the given number of stones per pit
     *
     * @param stonesPerPit Number of stones to place in each pit
     */
    public void initializePitsStones(int stonesPerPit) {
        updatePreviousBoardState();

        for (int i = 0; i < 6; i++) {
            Pit pit = pits.get(i);
            pit.updateStones(stonesPerPit);
        }

        for (int i = 7; i < 13; i++) {
            Pit pit = pits.get(i);
            pit.updateStones(stonesPerPit);
        }

        stonesInitialized = true;
        updateListeners();
    }

    /**
     * Gets Player A's Mancala pit
     *
     * @return Mancala pit for Player A
     */
    public Pit getMancalaA() {
        return mancalaA;
    }

    /**
     * Gets Player B's Mancala pit
     *
     * @return Mancala pit for Player B
     */
    public Pit getMancalaB() {
        return mancalaB;
    }

    /**
     * Notifies listeners to update the game screen
     */
    public void setGameScreen() {
        updateListeners();
    }

    /**
     * Checks if stones are initialized
     *
     * @return true if stones are initialized, false if not
     */
    public boolean isStonesInitialized() {
        return stonesInitialized;
    }

    /**
     * Sets the board pattern strategy
     *
     * @param boardPattern The board pattern strategy to use
     */
    public void setBoardPattern(BoardPatternStrategy boardPattern) {
        this.boardPattern = boardPattern;
    }

    /**
     * Checks if the player's turn is valid
     *
     * @param pit The selected pit
     * @return true if the turn is valid, false if not
     */
    private boolean isPlayerTurnValid(Pit pit) {
        int pitIndex = pits.indexOf(pit);
        if (pitIndex == -1) {
            JOptionPane.showMessageDialog(null, "Invalid pit selected.", "Invalid move", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (currentPlayer.equals("A") && (pitIndex < 7 || pitIndex > 12)) {
            JOptionPane.showMessageDialog(null, "Player A can only play pits A1 to A6.", "Invalid move", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (currentPlayer.equals("B") && (pitIndex < 0 || pitIndex > 5)) {
            JOptionPane.showMessageDialog(null, "Player B can only play pits B1 to B6.", "Invalid move", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (pits.get(pitIndex).getStones() == 0) {
            JOptionPane.showMessageDialog(null, "Selected pit is empty. Choose a pit with stones.", "Invalid move", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Processes a move for the current player
     *
     * @param pit The selected pit to play
     */
    public void makeMove(Pit pit) {
        if (!isPlayerTurnValid(pit)) {
            return;
        }

        updatePreviousBoardState();

        int pitNumber = pits.indexOf(pit);
        if (pitNumber == -1) {
            JOptionPane.showMessageDialog(null, "Invalid pit selection", "Invalid move", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int stones = pit.removeStones();
        pitNumber += 1;

        while (stones > 0) {
            if (pitNumber >= pits.size()) {
                pitNumber = 0;
            }

            if ((currentPlayer.equals("A") && pitNumber == 6) || (currentPlayer.equals("B") && pitNumber == 13)) {
                pitNumber++;
                continue;
            }

            pits.get(pitNumber).increment();
            stones--;
            pitNumber++;
        }

        pitNumber--;
        handleCapture(pitNumber);

        if (checkGameOver()) {
            updateListeners();
            return;
        }

        canUndo = true;
        lastMovePlayer = currentPlayer;

        if ((currentPlayer.equals("A") && pitNumber == 13) || (currentPlayer.equals("B") && pitNumber == 6)) {
            updateListeners();
            return;
        }

        switchPlayer();
        undoCountA = currentPlayer.equals("A") ? 0 : undoCountA;
        undoCountB = currentPlayer.equals("B") ? 0 : undoCountB;

        updateListeners();
    }


    /**
     * Allows the current player to undo the last move
     */
    public void undoMove() {
        if (!canUndo) {
            JOptionPane.showMessageDialog(null, "Undo is not allowed at this time.", "Undo Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (lastMovePlayer.equals("A") && undoCountA >= MAX_UNDO) {
            JOptionPane.showMessageDialog(null, "Player A has reached the undo limit for this turn.", "Undo Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (lastMovePlayer.equals("B") && undoCountB >= MAX_UNDO) {
            JOptionPane.showMessageDialog(null, "Player B has reached the undo limit for this turn.", "Undo Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (int i = 0; i < pits.size(); i++) {
            pits.get(i).updateStones(previousState.get(i).getStones());
        }

        currentPlayer = lastMovePlayer;

        if (lastMovePlayer.equals("A")) {
            undoCountA++;
        } else {
            undoCountB++;
        }

        canUndo = false;

        // Notify listeners
        updateListeners();
    }


    /**
     * Handles capturing stones when a valid capture move is made
     *
     * @param currentIndex The index of the pit where the move ends
     */
    private void handleCapture(int currentIndex) {
        if ((currentPlayer.equals("A") && currentIndex >= 7 && currentIndex <= 12 && pits.get(currentIndex).getStones() == 1) ||
                (currentPlayer.equals("B") && currentIndex >= 0 && currentIndex <= 5 && pits.get(currentIndex).getStones() == 1)) {
            int oppositeIndex = 12 - currentIndex;
            int capturedStones = pits.get(oppositeIndex).removeStones() + pits.get(currentIndex).removeStones();
            if (currentPlayer.equals("A")) {
                mancalaA.increment(capturedStones);
            } else {
                mancalaB.increment(capturedStones);
            }
        }
    }

    /**
     * Retrieves the current board pattern strategy
     *
     * @return The current BoardPatternStrategy
     */
    public BoardPatternStrategy getBoardPattern() {
        return boardPattern;
    }

    /**
     * Retrieves the current player
     *
     * @return A string representing the current player, A or B
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Adds a ChangeListener to model
     * Listeners are notified of any updates to the model's state
     *
     * @param cl the ChangeListener to add
     */
    public void addChangeListener(ChangeListener cl) {
        listeners.add(cl);
    }

    /**
     * Updates all listeners with current state of the model
     */
    private void updateListeners() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener l : listeners)
            l.stateChanged(event);
    }

    /**
     * Retrieves the list of pits on the board
     * @return A List of Pit objects
     */
    public List<Pit> getPits()
    {
        return pits;
    }

    /**
     * Saves current state of the board for undo functionality
     */
    private void updatePreviousBoardState() {
        previousState = new ArrayList<>();
        for (Pit p : pits) {
            previousState.add(p.clone());
        }
    }

    /**
     * Switches current player to the next player
     */
    private void switchPlayer() {
        currentPlayer = currentPlayer.equals("A") ? "B" : "A";
    }

    /**
     * Checks if the game is over
     * @return true if game is over, false if not
     */
    private boolean checkGameOver() {
        boolean playerAEmpty = true;
        boolean playerBEmpty = true;

        for (int i = 7; i <= 12; i++) {
            if (pits.get(i).getStones() > 0) {
                playerAEmpty = false;
                break;
            }
        }

        for (int i = 0; i <= 5; i++) {
            if (pits.get(i).getStones() > 0) {
                playerBEmpty = false;
                break;
            }
        }
        if (playerAEmpty || playerBEmpty) {
            endGame();
            return true;
        }
        return false;
    }

    /**
     * Ends the game and calculates the final scores for both players
     * Then displays the winner or a tie message to the players
     */
    private void endGame() {
        for (int i = 0; i <= 5; i++) {
            mancalaB.increment(pits.get(i).removeStones());
        }

        for (int i = 7; i <= 12; i++) {
            mancalaA.increment(pits.get(i).removeStones());
        }

        int playerAStones = mancalaA.getStones();
        int playerBStones = mancalaB.getStones();

        if (playerAStones > playerBStones) {
            JOptionPane.showMessageDialog(null, "Player A wins with a total of " + playerAStones + " stones.", "Player A wins!", JOptionPane.INFORMATION_MESSAGE);
        } else if (playerBStones > playerAStones) {
            JOptionPane.showMessageDialog(null, "Player B wins with a total of " + playerBStones + " stones.", "Player B wins!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Both player has " + playerAStones + " stones.", "It's a tie!", JOptionPane.INFORMATION_MESSAGE);
        }
        updateListeners();
    }
}
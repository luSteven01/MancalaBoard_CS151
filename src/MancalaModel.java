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

    public MancalaModel() {
        listeners = new ArrayList<ChangeListener>();
        pits = new ArrayList<Pit>();
        previousState = new ArrayList<Pit>();
        boardPattern = new Pattern1();
        initializeEmptyPits();
        stonesInitialized = false;
        currentPlayer = "A";
    }

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

    public void initializePitsStones(int stonesPerPit) {
        updatePreviousBoardState();

        for (int i = 0; i < 6; i++) { // player B's pits
            Pit pit = pits.get(i);
            pit.updateStones(stonesPerPit);
        }

        for (int i = 7; i < 13; i++) { // player A's pits
            Pit pit = pits.get(i);
            pit.updateStones(stonesPerPit);
        }

        stonesInitialized = true;
        updateListeners();
    }

    public Pit getMancalaA() {
        return mancalaA;
    }

    public Pit getMancalaB() {
        return mancalaB;
    }

    public void setGameScreen() {
        updateListeners();
    }

    public boolean isStonesInitialized() {
        return stonesInitialized;
    }

    public void setBoardPattern(BoardPatternStrategy boardPattern) {
        this.boardPattern = boardPattern;
    }

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

    public void makeMove(Pit pit) {
        if (!isPlayerTurnValid(pit)) {
            return;
        }
        updatePreviousBoardState();

        int pitNumber = -1;
        int pitSize = pits.size();
        for (int i = 0; i < pitSize; i++) {
            if (pits.get(i) == pit) {
                pitNumber = i;
                break;
            }
        }

        if (pitNumber == -1) {
            JOptionPane.showMessageDialog(null, "Invalid pit selection", "Invalid move", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int stones = pit.removeStones();
        pitNumber += 1;

        while (stones > 0) {
            if (pitNumber >= pitSize) {
                pitNumber = pitNumber - pitSize;
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

        if ((currentPlayer.equals("A") && pitNumber == 13) || (currentPlayer.equals("B") && pitNumber == 6)) {
            updateListeners();
            return;
        }
        switchPlayer();
        updateListeners();
    }

    public void undoMove() {
        if ((currentPlayer.equals("A") && undoCountA >= MAX_UNDO) ||
                (currentPlayer.equals("B") && undoCountB >= MAX_UNDO)) {
            JOptionPane.showMessageDialog(null, "Undo limit has been reached. No more allowed.", "Invalid move", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (int i = 0; i < pits.size(); i++) {
            pits.get(i).updateStones(previousState.get(i).getStones());
        }

        if (currentPlayer.equals("A")) undoCountA++;
        else undoCountB++;

        updateListeners();
    }


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

    public BoardPatternStrategy getBoardPattern() {
        return boardPattern;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void addChangeListener(ChangeListener cl) {
        listeners.add(cl);
    }

    private void updateListeners() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener l : listeners)
            l.stateChanged(event);
    }

    public List<Pit> getPits() {
        return pits;
    }

    private void updatePreviousBoardState() {
        previousState = new ArrayList<>();
        for (Pit p : pits) {
            previousState.add(p.clone());
        }
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer.equals("A") ? "B" : "A";
    }

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
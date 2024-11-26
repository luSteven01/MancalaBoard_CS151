import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.List;
import java.util.ArrayList;

public class MancalaModel {

    private ArrayList<ChangeListener> listeners;
    private BoardPatternStrategy boardPattern;
    private boolean stonesInitialized;
    private String currentPlayer;
    private ArrayList<Pit> pits;
    private Pit mancalaA;
    private Pit mancalaB;
    private ArrayList<Pit> previousState;


    public MancalaModel() {
        listeners = new ArrayList<ChangeListener>();
        pits = new ArrayList<Pit>();
        previousState = new ArrayList<Pit>();
        boardPattern = new Pattern1(); // Set a default pattern
        initializeEmptyPits();
        stonesInitialized = false;
        currentPlayer = "A";
    }

    private void initializeEmptyPits() {
        updatePreviousBoardState();

        for (int i = 0; i < 6; i++) { // player B's pits
            pits.add(new Pit());
        }
        // Player B's Mancala
        mancalaB = new Pit();
        pits.add(mancalaB);

        for (int i = 0; i < 6; i++) { // player A's pits
            pits.add(new Pit());
        }
        // Player A's Mancala
        mancalaA = new Pit();
        pits.add(mancalaA);

        updateListeners();
    }

    private void initializeSidePits() {
        mancalaA = new Pit(); // Player A's Mancala
        mancalaB = new Pit(); // Player B's Mancala
    }


    public void initializePitsStones(int stonesPerPit) {
        updatePreviousBoardState();

//        pits.clear();
        for (int i = 0; i < 6; i++) { // player B's pits
//            pits.add(new Pit(stonesPerPit));
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
//        updateListeners();
    }

    public void makeMove(Pit pit) {
        updatePreviousBoardState();

        int pitNumber = -1;
        int pitSize = pits.size();
        for (int i = 0; i < pitSize; i++) {
            if (pits.get(i) == pit) {
                pitNumber = i;
                break;
            }
        }
        int stones = pit.removeStones();
        pitNumber += 1;

        while (stones > 0) {
            if (pitNumber >= pitSize) {
                pitNumber = pitNumber - pitSize;
            }

            if ((currentPlayer.equals("A") && pitNumber ==13) || (currentPlayer.equals("B") && pitNumber ==6)) {
                pitNumber++;
                continue;
            }
            pits.get(pitNumber).increment();
            stones--;
            pitNumber++;
        }
        handleSpecialRules(pitNumber);
        pitNumber--;

        if (checkGameOver()) {
            updateListeners();
            return;
        }

        if ((currentPlayer.equals("A") && pitNumber ==13) || (currentPlayer.equals("B") && pitNumber ==6)) {
            updateListeners();
            return;
        }
        switchPlayer();
        updateListeners();
    }

    public void undoMove() {
        for (int i = 0; i < pits.size(); i++) {
            pits.get(i).updateStones(previousState.get(i).getStones());
        }
        updateListeners();
    }

    private void handleSpecialRules(int currentIndex)
    {
        if((currentPlayer.equals("A") && currentIndex <= 7 && currentIndex <= 12 && pits.get(currentIndex).getStones() == 1) ||
                (currentPlayer.equals("B") && currentIndex >= 0 && currentIndex <= 5 && pits.get(currentIndex).getStones() == 1))
        {
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

    public String getCurrentPlayer()
    {
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

    public void makeMove(int pitIndex) {
        System.out.println("Pit clicked: " + pitIndex);
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

    private void switchPlayer()
    {
        currentPlayer = currentPlayer.equals("A") ? "B" : "A";
    }

    private boolean checkGameOver()
    {
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

    private void endGame()
    {
        for (int i = 0; i <= 5; i++) {
            mancalaB.increment(pits.get(i).removeStones());
        }

        for (int i = 7; i <= 12; i++) {
            mancalaA.increment(pits.get(i).removeStones());
        }

        int playerAStones = mancalaA.getStones();
        int playerBStones = mancalaB.getStones();

        if (playerAStones > playerBStones) {
            System.out.println("Player A wins with a total of " + playerAStones + " stones.");
        } else if (playerBStones > playerAStones) {
            System.out.println("Player B wins with a total of " + playerBStones + " stones.");
        } else {
            System.out.println("It's a tie!");
        }
        updateListeners();
    }
}
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.List;
import java.util.ArrayList;

public class MancalaModel {

    private ArrayList<ChangeListener> listeners;
    private BoardPatternStrategy boardPattern;
    private boolean stonesInitialized;

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
        // get pit number that was clicked
        for (int i = 0; i < pitSize; i++) {
            if (pits.get(i) == pit)
                pitNumber = i;
        }

        int stones = pit.removeStones();
        pitNumber += 1;

        // moves the stones across pits
        while (stones > 0) {
            if (pitNumber >= pitSize)
                pitNumber = pitNumber - pitSize;
            pits.get(pitNumber).increment();
            stones--;
            pitNumber++;
        }
        updateListeners();
    }

    public void undoMove() {
        for (int i = 0; i < pits.size(); i++) {
            pits.get(i).updateStones(previousState.get(i).getStones());
        }
        updateListeners();
    }

    public BoardPatternStrategy getBoardPattern() {
        return boardPattern;
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

}
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.List;
import java.util.ArrayList;

public class MancalaModel {

    private BoardPatternStrategy boardPattern;
    private ArrayList<ChangeListener> listeners;
    private ArrayList<Pit> pits;
    private Pit mancalaA;
    private Pit mancalaB;


    public MancalaModel() {
        listeners = new ArrayList<ChangeListener>();
        pits = new ArrayList<Pit>();
        boardPattern = new Pattern1(); // Set a default pattern
        initializeSidePits();
    }

    private void initializeSidePits() {
        mancalaA = new Pit(0); // Player A's Mancala
        mancalaB = new Pit(0); // Player B's Mancala
    }


    public void initializePits(int stonesPerPit) {
        pits.clear();
        for (int i = 0; i < 12; i++) { // Assume 12 pits total (6 per player)
            pits.add(new Pit(stonesPerPit));
        }
        mancalaA = new Pit(0); // Player A's Mancala
        mancalaB = new Pit(0); // Player B's Mancala
        updateListeners();
    }

    public Pit getMancalaA() {
        return mancalaA;
    }

    public Pit getMancalaB() {
        return mancalaB;
    }

    public void setBoardPattern(BoardPatternStrategy boardPattern) {
        this.boardPattern = boardPattern;
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

}
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;

public class MancalaModel {
    private BoardPatternStrategy boardPattern;
    private final ArrayList<ChangeListener> listeners;
    private final ArrayList<Pit> pits; // Regular pits (12 pits)
    private SidePit mancalaA; // Mancala for Player A
    private SidePit mancalaB; // Mancala for Player B

    public MancalaModel() {
        listeners = new ArrayList<>();
        pits = new ArrayList<>();
    }

    /**
     * Initializes the regular pits (12 pits) with a given number of stones and pit color.
     * @param initialStones The number of stones to initialize each pit with.
     * @param pitColor      The color of the pits.
     */
    public void initializePits(int initialStones, Color pitColor) {
        pits.clear();
        for (int i = 0; i < 12; i++) { // 12 pits (6 per side)
            pits.add(new Pit(initialStones, pitColor));
        }
        updateListeners();
    }

    /**
     * Initializes the side pits (Mancalas) for both players with 0 stones initially.
     * @param pitColor The color of the side pits.
     */
    public void initializeSidePits(Color pitColor) {
        mancalaA = new SidePit(0, pitColor); // Player A's Mancala
        mancalaB = new SidePit(0, pitColor); // Player B's Mancala
        updateListeners();
    }

    /**
     * Returns the list of regular pits.
     * @return The list of pits.
     */
    public ArrayList<Pit> getPits() {
        return pits;
    }

    /**
     * Returns Player A's Mancala (side pit).
     * @return Mancala A.
     */
    public SidePit getMancalaA() {
        return mancalaA;
    }

    /**
     * Returns Player B's Mancala (side pit).
     * @return Mancala B.
     */
    public SidePit getMancalaB() {
        return mancalaB;
    }

    /**
     * Sets the board pattern strategy.
     * @param boardPattern The board pattern strategy to use.
     */
    public void setBoardPattern(BoardPatternStrategy boardPattern) {
        this.boardPattern = boardPattern;
        updateListeners();
    }

    /**
     * Returns the current board pattern strategy.
     * @return The board pattern strategy.
     */
    public BoardPatternStrategy getBoardPattern() {
        return boardPattern;
    }

    /**
     * Adds a listener to listen for changes in the model.
     * @param cl The ChangeListener to add.
     */
    public void addChangeListener(ChangeListener cl) {
        listeners.add(cl);
    }

    /**
     * Updates all listeners when the state of the model changes.
     */
    private void updateListeners() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener l : listeners) {
            l.stateChanged(event);
        }
    }
}

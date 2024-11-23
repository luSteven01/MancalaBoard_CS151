import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.List;
import java.util.ArrayList;

public class MancalaModel {

    private BoardPatternStrategy boardPattern;
    private ArrayList<ChangeListener> listeners;
    private ArrayList<Pit> pits;

    public MancalaModel() {
        listeners = new ArrayList<ChangeListener>();
        pits = new ArrayList<Pit>();
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
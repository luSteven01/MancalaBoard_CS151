import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

public class MancalaModel {

    private BoardPatternStrategy boardPattern;
    private ArrayList<ChangeListener> listeners;

    public MancalaModel() {
        listeners = new ArrayList<ChangeListener>();
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

}
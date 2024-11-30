import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Pit extends JPanel {

    private static final int PIT_SIZE = 60; // Fixed size for pits
    private int stones; // Number of stones in the pit
    private final ArrayList<JLabel> stoneIcons;
    private Runnable onPress;

    public Pit() {
        this.stones = 0;
        this.stoneIcons = new ArrayList<>();

        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); // Align stones neatly
        setPreferredSize(new Dimension(PIT_SIZE, PIT_SIZE));
        setMaximumSize(new Dimension(PIT_SIZE, PIT_SIZE));
        setMinimumSize(new Dimension(PIT_SIZE, PIT_SIZE));
        setBackground(Color.LIGHT_GRAY); // Default pit background

        MouseListeners listeners = new MouseListeners();
        addMouseListener(listeners);
    }

    public Pit(int initialStones) {
        this.stones = initialStones;
        this.stoneIcons = new ArrayList<>();

        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); // Align stones neatly
        setPreferredSize(new Dimension(PIT_SIZE, PIT_SIZE));
        setMaximumSize(new Dimension(PIT_SIZE, PIT_SIZE));
        setMinimumSize(new Dimension(PIT_SIZE, PIT_SIZE));
        setBackground(Color.LIGHT_GRAY); // Default pit background
        updateStones(initialStones);

        MouseListeners listeners = new MouseListeners();
        addMouseListener(listeners);
    }

//    public void addStones(int count) {
//        stones += count;
//        updateStones(stones);
//    }

    public int removeStones() {
        int removed = stones;
        stones = 0;
        updateStones(stones);
        return removed;
    }

    public void increment() {
        stones++;
        updateStones(stones);
    }

    public void increment(int stonesToAdd) {
        stones += stonesToAdd;
        updateStones(stones);
    }

    public void updateStones(int count) {
        stones = count;
        removeAll(); // Clear existing stones
        stoneIcons.clear();

        for (int i = 0; i < count; i++) {
            JLabel stone = new JLabel();
            stone.setPreferredSize(new Dimension(10, 10)); // Size of each stone
            stone.setOpaque(true);
            stone.setBackground(Color.RED);
            stone.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            stoneIcons.add(stone);
            add(stone);
        }
        revalidate();
        repaint();
    }

    public Pit clone() {
        Pit clone = new Pit();
        clone.updateStones(stones);
        return clone;
    }

    public int getStones() {
        return stones;
    }

    public void setOnPress(Runnable onPress) {
        this.onPress = onPress;
    }

    private class MouseListeners extends MouseAdapter {
        public void mousePressed(MouseEvent event) {
            if (onPress != null)
                onPress.run();
        }
    }
}

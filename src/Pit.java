/**
 * Fall 2024 - Doctor Kim's CS151
 * CS151 Team Project - Mancala
 * @author Monica Zhang
 * @author Billy Porras-Molina
 * @author Steven Lu
 * @version 1.0 11/26/24
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class models a pit on the mancala game board.
 */
public class Pit extends JPanel {

    private static final int PIT_SIZE = 60;
    private int stones;
    private final ArrayList<JLabel> stoneIcons;
    private Runnable onPress;

    /**
     * Constructs a Pit with no stones
     * Initializes graphical representation and mouse listeners
     */
    public Pit() {
        this.stones = 0;
        this.stoneIcons = new ArrayList<>();

        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        setPreferredSize(new Dimension(PIT_SIZE, PIT_SIZE));
        setMaximumSize(new Dimension(PIT_SIZE, PIT_SIZE));
        setMinimumSize(new Dimension(PIT_SIZE, PIT_SIZE));
        setBackground(Color.LIGHT_GRAY);

        MouseListeners listeners = new MouseListeners();
        addMouseListener(listeners);
    }

    /**
     * removes all stones from pit and returns number of removed stones
     * @return the number of stones removed from pit
     */
    public int removeStones() {
        int removed = stones;
        stones = 0;
        updateStones(stones);
        return removed;
    }

    /**
     * Increments number of stones in pit by one and updates display
     */
    public void increment() {
        stones++;
        updateStones(stones);
    }

    /**
     * Increments number of stones in pit by a specified amount and updates display
     * @param stonesToAdd The number of stones to add to pit
     */
    public void increment(int stonesToAdd) {
        stones += stonesToAdd;
        updateStones(stones);
    }

    /**
     * Updates number of stones in pit and refreshes graphical representation
     *
     * @param count  new number of stones in the pit
     */
    public void updateStones(int count) {
        stones = count;
        removeAll();
        stoneIcons.clear();

        for (int i = 0; i < count; i++) {
            JLabel stone = new JLabel();
            stone.setPreferredSize(new Dimension(10, 10));
            stone.setOpaque(true);
            stone.setBackground(Color.RED);
            stone.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            stoneIcons.add(stone);
            add(stone);
        }
        revalidate();
        repaint();
    }

    /**
     * creates a clone of current pit including the number of stones
     * @return new Pit object with same number of stones
     */
    public Pit clone() {
        Pit clone = new Pit();
        clone.updateStones(stones);
        return clone;
    }

    /**
     * returns the number of stones currently in the pit
     * @return number of stones in the pit
     */
    public int getStones() {
        return stones;
    }

    /**
     * Sets action to be performed when pit is clicked
     * @param onPress a Runnable representing the action to be triggered
     */
    public void setOnPress(Runnable onPress) {
        this.onPress = onPress;
    }

    /**
     * internal class to handle mouse event for pit
     * triggers the onPress action when pit is clicked
     */
    private class MouseListeners extends MouseAdapter {
        public void mousePressed(MouseEvent event) {
            if (onPress != null)
                onPress.run();
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class MancalaController extends JPanel {

    private MancalaModel model;
    private JButton undoButton;
    private List<Pit> pits;

    public MancalaController(MancalaModel model) {
        super();
        this.model = model;

        pits = model.getPits();

        model.addChangeListener(e -> {
            if (!model.isStonesInitialized())
                askPitStones();
        });

        undoButton = new JButton("Undo");

        this.add(undoButton);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int pitIndex = getClickedPit(e.getX(), e.getY());
                if (pitIndex >= 0) {
                    model.makeMove(pitIndex);
                }
            }
        });
    }

    private int getClickedPit(int x, int y) {
        int pitCount = model.getPits().size();
        int pitHeight = 80;
        int pitWidth = 100;
        int startX = 50;
        int startY = 100;

        for (int i = 0; i < pitCount; i++) {
            int pitX = startX + (i % 6) * pitWidth;
            int pitY = i < 6 ? startY : startY + pitHeight + 20;
            Rectangle pitBounds = new Rectangle(pitX, pitY, pitWidth, pitHeight);
            if (pitBounds.contains(x, y)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * ask player for amount of stones
     */
    private void askPitStones() {
        boolean enteredStoneNumber = false;
        while (!enteredStoneNumber) {
            String stoneInput = JOptionPane.showInputDialog(null, "Enter the number of stones in each pit (3 or 4 only):", "Input Stone Amount", JOptionPane.PLAIN_MESSAGE);
            // check if an input is entered
            if (stoneInput == null || stoneInput.length() == 0) {
                JOptionPane.showMessageDialog(null, "Invalid Input.", "Error", JOptionPane.ERROR_MESSAGE);
                // check if input is 3 or 4, and initialize pits if it is
            } else {
                int stones = Integer.parseInt(stoneInput);
                if (stones == 3 || stones == 4) {
                    enteredStoneNumber = true;
                    model.initializePits(stones);
                    JOptionPane.showMessageDialog(null, "Initialized pits to " + stones, "Pits initialized successfully", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Invalid Input.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}


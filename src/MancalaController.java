/**
 * Fall 2024 - Doctor Kim's CS151
 * CS151 Team Project - Mancala
 * @author Monica Zhang
 * @author Billy Porras-Molina
 * @version 1.0 11/29/24
 */

import javax.swing.*;
import java.util.List;

/**
 * This class allows interactions between the game board and players. <br>
 * It has components for the player to interact with, and tells MancalaModel object the player made a move. <br>
 * A player can set the amount of stones per pit, make a move, and undo their move.
 */
public class MancalaController extends JPanel {

    private MancalaModel model;
    private JButton undoButton;
    private List<Pit> pits;

    /**
     * Constructs the MancalaController and links it to given game model
     * Initializes UI components and sets up interactions between player and game logic
     * @param model The MancalaModel providing game logic and state
     */
    public MancalaController(MancalaModel model) {
        super();
        this.model = model;

        model.addChangeListener(e -> {
            if (!model.isStonesInitialized())
                askPitStones();
        });

        pits = model.getPits();
        for (Pit p : pits) {
            p.setOnPress(() -> model.makeMove(p));
        }

        undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> model.undoMove());

        this.add(undoButton);
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
                    model.initializePitsStones(stones);
                    JOptionPane.showMessageDialog(null, "Initialized pits to " + stones, "Pits initialized successfully", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Invalid Input.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}


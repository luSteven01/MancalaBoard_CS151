/**
 * Fall 2024 - Doctor Kim's CS151
 * CS151 Team Project - Mancala
 * @author Monica Zhang
 * @author Billy Porras-Molina
 * @author Steven Lu
 * @version 1.0 11/29/24
 */

import javax.swing.*;
import java.awt.*;

/**
 * This class displays mancala game board and reflects the player's moves by updating the BoardPanel.
 */
public class MancalaView extends JPanel {

    private final MancalaModel model;
    private BoardPanel boardPanel;

    public MancalaView(MancalaModel model) {
        this.model = model;

        model.addChangeListener(e -> {
            BoardPatternStrategy boardPattern = model.getBoardPattern();
            setBackground(boardPattern.color()); // Update background color
            boardPanel.updatePattern(boardPattern, model.getCurrentPlayer()); // Update pits dynamically
            repaint();
        });

        setLayout(new BorderLayout());
        BoardPatternStrategy initialPattern = model.getBoardPattern();
        boardPanel = new BoardPanel(model.getPits(), model.getMancalaA(), model.getMancalaB(), initialPattern, "A");
        add(boardPanel, BorderLayout.CENTER);
    }
}
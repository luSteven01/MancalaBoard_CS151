/**
 * Fall 2024 - Doctor Kim's CS151
 * CS151 Team Project - Mancala
 * @author Monica Zhang
 * @author Steven Lu
 * @version 1.0 11/29/24
 */

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class models the graphical component of mancala game board.
 */
public class BoardPanel extends JPanel {

    private BoardPatternStrategy boardPattern;
    private JLabel currentPlayerLabel;

    public BoardPanel(List<Pit> pits, Pit mancalaA, Pit mancalaB, BoardPatternStrategy boardPattern, String currentPlayer) {
        this.boardPattern = boardPattern;
        setLayout(null);
        setPreferredSize(new Dimension(1020, 550));

        int pitSize = 100;
        int horizontalSpacing = 120;
        int verticalSpacing = 270;
        int mancalaWidth = 120, mancalaHeight = 360;

        currentPlayerLabel = new JLabel(currentPlayer, SwingConstants.CENTER);
        currentPlayerLabel.setFont(new Font("Serif", Font.BOLD, 40));
        currentPlayerLabel.setBounds(350, 15, 400, 50);
        this.add(currentPlayerLabel);

        // Left Mancala (Player B)
        mancalaB.setBounds(20, 80, mancalaWidth, mancalaHeight);
        mancalaB.setBackground(boardPattern.pitColor());
        add(mancalaB);

        // Right Mancala (Player A)
        mancalaA.setBounds(880, 80, mancalaWidth, mancalaHeight);
        mancalaA.setBackground(boardPattern.pitColor());
        add(mancalaA);

        // Top row (Player B's pits)
        for (int i = 0; i < 6; i++) {
            Pit pit = pits.get(5 - i); // Player B's pits (reverse order)
            pit.setBounds(160 + i * horizontalSpacing, 120, pitSize, pitSize);
            pit.setBackground(boardPattern.pitColor());
            add(pit);

            JLabel label = new JLabel("B" + (6 - i), SwingConstants.CENTER);
            label.setBounds(160 + i * horizontalSpacing, 100, pitSize, 20);
            add(label);
        }

        // Bottom row (Player A's pits)
        for (int i = 0; i < 6; i++) {
            Pit pit = pits.get(7 + i); // Player A's pits (left to right)
            pit.setBounds(160 + i * horizontalSpacing, verticalSpacing, pitSize, pitSize);
            pit.setBackground(boardPattern.pitColor());
            add(pit);

            JLabel label = new JLabel("A" + (i + 1), SwingConstants.CENTER);
            label.setBounds(160 + i * horizontalSpacing, verticalSpacing + pitSize, pitSize, 20);
            add(label);
        }
    }

    /**
     * Updates the board's pattern dynamically.
     * @param boardPattern the new board pattern strategy
     */
    public void updatePattern(BoardPatternStrategy boardPattern, String currentPlayer) {
        this.boardPattern = boardPattern;
        setBackground(boardPattern.color()); // Update background color

       
        for (Component c : getComponents()) {
            if (c instanceof Pit || c instanceof JPanel) {
                c.setBackground(boardPattern.pitColor());
            }
        }

        currentPlayerLabel.setText("Player " + currentPlayer + "'s turn");

        repaint();
    }
}

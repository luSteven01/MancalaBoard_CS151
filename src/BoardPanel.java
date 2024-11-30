import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BoardPanel extends JPanel {

    private BoardPatternStrategy boardPattern;

    public BoardPanel(List<Pit> pits, Pit mancalaA, Pit mancalaB, BoardPatternStrategy boardPattern) {
        this.boardPattern = boardPattern;
        setLayout(null); // Use absolute positioning for precise control
        setPreferredSize(new Dimension(1020, 500)); // Adjusted size for a more balanced board

        int pitSize = 100;  // Increased pit size to match the top board
        int horizontalSpacing = 120; // Adjusted spacing between pits
        int verticalSpacing = 220; // Adjusted gap between top and bottom rows
        int mancalaWidth = 120, mancalaHeight = 360;

        // Left Mancala (Player B)
        mancalaB.setBounds(20, 30, mancalaWidth, mancalaHeight); // Centered vertically
        mancalaB.setBackground(boardPattern.pitColor()); // Apply pattern color
        mancalaB.disable();
        add(mancalaB);

        // Right Mancala (Player A)
        mancalaA.setBounds(880, 30, mancalaWidth, mancalaHeight); // Centered vertically
        mancalaA.setBackground(boardPattern.pitColor()); // Apply pattern color
        add(mancalaA);

        // Top row (Player B's pits)
        for (int i = 0; i < 6; i++) {
            Pit pit = pits.get(5 - i); // Player B's pits (reverse order)
            pit.setBounds(160 + i * horizontalSpacing, 70, pitSize, pitSize); // Centered vertically
            pit.setBackground(boardPattern.pitColor()); // Apply pattern color
            add(pit);

            JLabel label = new JLabel("B" + (6 - i), SwingConstants.CENTER);
            label.setBounds(160 + i * horizontalSpacing, 50, pitSize, 20); // Above the pit
            add(label);
        }

        // Bottom row (Player A's pits)
        for (int i = 0; i < 6; i++) {
            Pit pit = pits.get(7 + i); // Player A's pits (left to right)
            pit.setBounds(160 + i * horizontalSpacing, verticalSpacing, pitSize, pitSize); // Centered vertically
            pit.setBackground(boardPattern.pitColor()); // Apply pattern color
            add(pit);

            JLabel label = new JLabel("A" + (i + 1), SwingConstants.CENTER);
            label.setBounds(160 + i * horizontalSpacing, verticalSpacing + pitSize, pitSize, 20); // Below the pit
            add(label);
        }
    }

    /**
     * Updates the board's pattern dynamically.
     * @param boardPattern the new board pattern strategy
     */
    public void updatePattern(BoardPatternStrategy boardPattern) {
        this.boardPattern = boardPattern;
        setBackground(boardPattern.color()); // Update background color

        // Update mancala and pits colors
        for (Component c : getComponents()) {
            if (c instanceof Pit || c instanceof JPanel) {
                c.setBackground(boardPattern.pitColor());
            }
        }

        repaint();
    }
}

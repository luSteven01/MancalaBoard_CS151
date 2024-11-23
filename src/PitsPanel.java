import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PitsPanel extends JPanel {
    /**
     * Constructs the panel with all pits, including the side pits (Mancalas) and regular pits.
     * @param pits     The list of regular pits (12 pits).
     * @param mancalaA The side pit for Player A.
     * @param mancalaB The side pit for Player B.
     */
    public PitsPanel(ArrayList<Pit> pits, SidePit mancalaA, SidePit mancalaB) {
        setLayout(new BorderLayout(10, 10)); // Use BorderLayout for side pits

        // Add the left side pit (Mancala B)
        add(createSidePitPanel(mancalaB, "MANCALA B"), BorderLayout.WEST);

        // Center panel for main pits (6 pits per row)
        JPanel centerPanel = new JPanel(new GridLayout(2, 6, 25, 25));

        // Add top row (Player B's pits, reversed order)
        for (int i = 5; i >= 0; i--) {
            centerPanel.add(createPitPanel(pits.get(i), "B" + (i + 1)));
        }

        // Add bottom row (Player A's pits)
        for (int i = 6; i < 12; i++) {
            centerPanel.add(createPitPanel(pits.get(i), "A" + (i - 5)));
        }

        add(centerPanel, BorderLayout.CENTER);

        // Add the right side pit (Mancala A)
        add(createSidePitPanel(mancalaA, "MANCALA A"), BorderLayout.EAST);
    }

    /**
     * Creates a panel for a regular pit with a label.
     * @param pit   The pit to be added.
     * @param label The label to display above the pit.
     * @return A JPanel containing the pit and its label.
     */
    private JPanel createPitPanel(Pit pit, String label) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel pitLabel = new JLabel(label, JLabel.CENTER);
        pitLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(pitLabel, BorderLayout.NORTH);
        panel.add(pit, BorderLayout.CENTER);
        panel.setOpaque(false);
        return panel;
    }

    /**
     * Creates a panel for a side pit (Mancala) with a label.
     * @param sidePit The side pit to be added.
     * @param label   The label to display below the pit.
     * @return A JPanel containing the side pit and its label.
     */
    private JPanel createSidePitPanel(SidePit sidePit, String label) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel pitLabel = new JLabel(label, JLabel.CENTER);
        pitLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(pitLabel, BorderLayout.SOUTH);
        panel.add(sidePit, BorderLayout.CENTER);
        return panel;
    }
}

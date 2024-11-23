import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SidePit extends JPanel {
    private static final int PIT_WIDTH = 100; // Width of the Mancala pit
    private static final int PIT_HEIGHT = 200; // Height of the Mancala pit
    private int stones; // Number of stones in the side pit
    private final JPanel stoneContainer; // Panel to hold stones
    private final Color pitColor; // Background color of the side pit

    /**
     * Constructs a side pit with an initial number of stones.
     * @param initialStones The initial number of stones in the side pit.
     * @param pitColor      The color of the side pit.
     */
    public SidePit(int initialStones, Color pitColor) {
        this.stones = initialStones;
        this.pitColor = pitColor;

        // Set layout for the main panel
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(PIT_WIDTH, PIT_HEIGHT));
        setMaximumSize(new Dimension(PIT_WIDTH, PIT_HEIGHT));
        setMinimumSize(new Dimension(PIT_WIDTH, PIT_HEIGHT));

        // Create a container for stones with FlowLayout
        stoneContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        stoneContainer.setOpaque(false); // Transparent background for the container
        add(stoneContainer, BorderLayout.CENTER);

        // Add stones
        updateStones(initialStones);

        // Add mouse listener for interaction
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Logic for interacting with the side pit can go here
            }
        });
    }

    /**
     * Adds stones to the side pit.
     * @param count The number of stones to add.
     */
    public void addStones(int count) {
        stones += count;
        updateStones(stones); // Update the visual display
    }

    /**
     * Updates the stones displayed in the side pit.
     * @param count The current number of stones in the side pit.
     */
    private void updateStones(int count) {
        stoneContainer.removeAll(); // Clear previous stones
        for (int i = 0; i < count; i++) {
            JLabel stone = new JLabel(); // Represent a stone
            stone.setPreferredSize(new Dimension(15, 15)); // Stone size
            stone.setOpaque(true);
            stone.setBackground(Color.WHITE); // Stone color
            stone.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Optional border
            stoneContainer.add(stone);
        }
        stoneContainer.revalidate();
        stoneContainer.repaint();
    }

    /**
     * Custom painting for the side pit background.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw rectangular side pit background
        g2.setColor(pitColor);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Returns the number of stones in the side pit.
     * @return The number of stones.
     */
    public int getStones() {
        return stones;
    }
}

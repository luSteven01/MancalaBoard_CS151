import javax.swing.*;
import java.awt.*;

public class MancalaView extends JPanel {
    private final MancalaModel model;
    private BoardPatternStrategy boardPattern;

    public MancalaView(MancalaModel model) {
        this.model = model;
        setLayout(new BorderLayout());

        // Add listener to repaint the board when the pattern changes
        model.addChangeListener(e -> {
            boardPattern = model.getBoardPattern();
            setBackground(boardPattern.color());
            repaint();
        });

        // Add pits panel, passing both regular pits and side pits
        PitsPanel pitsPanel = new PitsPanel(model.getPits(), model.getMancalaA(), model.getMancalaB());
        add(pitsPanel, BorderLayout.CENTER);

        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Example custom background painting
        if (boardPattern != null) {
            g2.setColor(boardPattern.color());
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}

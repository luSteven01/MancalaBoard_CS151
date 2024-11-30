import javax.swing.*;
import java.awt.*;

public class MancalaView extends JPanel {

    private final MancalaModel model;
    private BoardPanel boardPanel;

    public MancalaView(MancalaModel model) {
        this.model = model;

        model.addChangeListener(e -> {
            setLayout(new BorderLayout());
            BoardPatternStrategy initialPattern = model.getBoardPattern();
            BoardPatternStrategy boardPattern = model.getBoardPattern();
            setBackground(boardPattern.color()); // Update background color
            boardPanel.updatePattern(boardPattern); // Update pits dynamically
            repaint();
        });

        setLayout(new BorderLayout());
        BoardPatternStrategy initialPattern = model.getBoardPattern();
        boardPanel = new BoardPanel(model.getPits(), model.getMancalaA(), model.getMancalaB(), initialPattern);
        add(boardPanel, BorderLayout.CENTER);
    }
}
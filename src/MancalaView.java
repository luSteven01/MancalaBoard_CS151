import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MancalaView extends JPanel {

    private MancalaModel model;
    private BoardPatternStrategy boardPattern;

    public MancalaView(MancalaModel model) {
        super();
        this.model = model;

        model.addChangeListener(e -> {
            boardPattern = model.getBoardPattern();
        });

        JLabel label = new JLabel("mancala view");

        this.add(label);

    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(boardPattern.color());
        g2.drawString("sajdnfr", 100, 100);
    }

}

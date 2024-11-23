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
            repaint();
        });

        boardPattern = model.getBoardPattern();

        JLabel label = new JLabel("mancala view");

        this.add(label);

    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if (boardPattern != null) {
            g2.setColor(boardPattern.color());
            g2.fillRect(0, 0, getWidth(), getHeight());


            g2.setColor(boardPattern.pitColor());
            for (int i = 0; i < 6; i++) {
                g2.fillOval(50 + i * 100, 100, 80, 80);
            }
        }
    }
}

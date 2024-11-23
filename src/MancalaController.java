import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class MancalaController extends JPanel {

    private MancalaModel model;

    public MancalaController(MancalaModel model) {
        super();
        this.model = model;

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int pitIndex = getClickedPit(e.getX(), e.getY());
                if (pitIndex >= 0) {
                    model.makeMove(pitIndex);
                }
            }
        });
    }

    private int getClickedPit(int x, int y) {
        int pitCount = model.getPits().size();
        int pitHeight = 80;
        int pitWidth = 100;
        int startX = 50;
        int startY = 100;

        for (int i = 0; i < pitCount; i++) {
            int pitX = startX + (i % 6) * pitWidth;
            int pitY = i < 6 ? startY : startY + pitHeight + 20;
            Rectangle pitBounds = new Rectangle(pitX, pitY, pitWidth, pitHeight);
            if (pitBounds.contains(x, y)) {
                return i;
            }
        }
        return -1;
    }
}


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.geom.Rectangle2D;

public class MancalaController extends JPanel {

    private MancalaModel model;

    public MancalaController(MancalaModel model) {
        super();

        this.model = model;


        JLabel label = new JLabel("mancala controller");

        this.add(label);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
    }

}


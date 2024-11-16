import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MancalaController extends JPanel {
    private MancalaModel model;

    public MancalaController() {
        super();

        JLabel label = new JLabel("mancala controller");

        this.add(label);
    }
}


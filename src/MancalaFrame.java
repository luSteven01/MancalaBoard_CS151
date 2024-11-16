import javax.swing.*;
import java.awt.*;

public class MancalaFrame extends JFrame {

    private JPanel cardPanel;
    private StartScreen startScreen;
    private MancalaModel model;
    private MancalaController controller;

    public MancalaFrame() {
        super();
        this.setTitle("Mancala");
        this.setBounds(100, 0, 1200, 800);

        cardPanel = new JPanel();
        CardLayout cl = new CardLayout();
        cardPanel.setLayout(cl);

        model = new MancalaModel();
        startScreen = new StartScreen(model, e -> cl.show(cardPanel, "Controller"));
        controller = new MancalaController(model);

        cardPanel.add(startScreen, "Start");
        cardPanel.add(controller, "Controller");

        this.add(cardPanel);

    }
}

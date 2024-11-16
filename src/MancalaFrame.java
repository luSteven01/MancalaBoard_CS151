import javax.swing.*;
import java.awt.*;

public class MancalaFrame extends JFrame {
    private JPanel cardPanel;
    private StartScreen startScreen;
    private MancalaController mc;

    public MancalaFrame() {
        super();
        this.setTitle("Mancala");
        this.setBounds(100, 0, 1200, 800);

        cardPanel = new JPanel();
        CardLayout cl = new CardLayout();
        cardPanel.setLayout(cl);

        startScreen = new StartScreen();
        mc = new MancalaController();

        cardPanel.add(startScreen, "Start");
        cardPanel.add(mc, "Controller");

        startScreen.getStartButton().addActionListener(e -> cl.show(cardPanel, "Controller"));

        this.add(cardPanel);
    }
}

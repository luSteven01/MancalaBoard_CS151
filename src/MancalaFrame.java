import javax.swing.*;
import java.awt.*;

public class MancalaFrame extends JFrame {

    private JPanel cardPanel;
    private StartScreen startScreen;
    private MancalaModel model;
    private MancalaController controller;
    private MancalaView view;
    private JPanel gameScreen;

    public MancalaFrame() {
        super();
        this.setTitle("Mancala");
        this.setBounds(100, 0, 1200, 800);

        cardPanel = new JPanel();
        CardLayout cl = new CardLayout();
        cardPanel.setLayout(cl);

        model = new MancalaModel();
        startScreen = new StartScreen(model, e -> cl.show(cardPanel, "game screen"));
        controller = new MancalaController(model);
        view = new MancalaView(model);

        gameScreen = new JPanel();
        gameScreen.setLayout(new BorderLayout());
        gameScreen.add(controller, BorderLayout.SOUTH);
        gameScreen.add(view, BorderLayout.CENTER);

        cardPanel.add(startScreen, "Start");
        cardPanel.add(gameScreen, "game screen");

        this.add(cardPanel);

    }
}

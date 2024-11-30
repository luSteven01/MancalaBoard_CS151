import javax.swing.*;
import java.awt.*;

public class MancalaFrame extends JFrame {

    public MancalaFrame() {
        super();
        setTitle("Mancala");
        setBounds(100, 100, 1020, 550); // Fixed size
        setResizable(false);

        MancalaModel model = new MancalaModel();

        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        StartScreen startScreen = new StartScreen(model, e -> cardLayout.show(cardPanel, "Game"));

        MancalaView view = new MancalaView(model);
        MancalaController controller = new MancalaController(model);

        JPanel gameScreen = new JPanel(new BorderLayout());
        gameScreen.add(view, BorderLayout.CENTER);
        gameScreen.add(controller, BorderLayout.SOUTH);

        cardPanel.add(startScreen, "Start");
        cardPanel.add(gameScreen, "Game");

        add(cardPanel);
        cardLayout.show(cardPanel, "Start");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}



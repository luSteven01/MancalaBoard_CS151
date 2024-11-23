import javax.swing.*;
import java.awt.*;

public class MancalaFrame extends JFrame {
    private final MancalaModel model;

    public MancalaFrame() {
        setTitle("Mancala");
        setBounds(100, 100, 1200, 800);

        // Initialize the model
        model = new MancalaModel();
        model.initializePits(4, Color.LIGHT_GRAY); // Initialize with 4 stones per pit
        model.initializeSidePits(Color.DARK_GRAY); // Initialize side pits (Mancalas)

        // Create the view and controller
        MancalaView view = new MancalaView(model);
        MancalaController controller = new MancalaController(model);

        // Create the game screen
        JPanel gameScreen = new JPanel(new BorderLayout());
        gameScreen.add(view, BorderLayout.CENTER); // Center: Game board view
        gameScreen.add(controller, BorderLayout.SOUTH); // Bottom: Game controls (e.g., buttons)

        // Add the game screen to the frame
        add(gameScreen);

        // Set default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

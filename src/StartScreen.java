import javax.swing.*;

public class StartScreen extends JPanel {

    private JButton button;

    public StartScreen() {
        super();

        JLabel text = new JLabel("Choose a pattern");
        button = new JButton("Start game");

        this.add(text);
        this.add(button);
    }

    public JButton getStartButton() {
        return button;
    }

}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StartScreen extends JPanel {

    public StartScreen(MancalaModel model, ActionListener startGameAction) {
        super();
        setLayout(new GridLayout(3, 1));

        JLabel text = new JLabel("Choose a pattern", SwingConstants.CENTER);
        JButton pattern1Button = new JButton("Pattern 1");
        JButton pattern2Button = new JButton("Pattern 2");
        JButton startGameButton = new JButton("Start Game");

        pattern1Button.addActionListener(e -> model.setBoardPattern(new Pattern1()));
        pattern2Button.addActionListener(e -> model.setBoardPattern(new Pattern2()));

        startGameButton.addActionListener(startGameAction);

        add(text);
        add(pattern1Button);
        add(pattern2Button);
        add(startGameButton);
    }

}

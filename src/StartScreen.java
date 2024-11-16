import javax.swing.*;
import java.awt.event.ActionListener;

public class StartScreen extends JPanel {

    private MancalaModel model;
    private JButton button;
    private BoardPatternStrategy boardPattern;
    private JButton pattern1, pattern2;

    public StartScreen(MancalaModel model, ActionListener al) {
        super();

        JLabel text = new JLabel("Choose a pattern");
        button = new JButton("Start game");

        pattern1 = new JButton("Pattern 1");
        pattern2 = new JButton("Pattern 2");

        pattern1.addActionListener(e -> boardPattern = new Pattern1());
        pattern2.addActionListener(e -> boardPattern = new Pattern2());

        button.addActionListener(e -> {
            if (boardPattern == null)
                JOptionPane.showMessageDialog(this, "Select a pattern", "Warning", JOptionPane.WARNING_MESSAGE);
            else {
                model.setBoardPattern(boardPattern);
                al.actionPerformed(e);
            }
        });

        this.add(text);
        this.add(button);
        this.add(pattern1);
        this.add(pattern2);
    }

}

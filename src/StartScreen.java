/**
 * Fall 2024 - Doctor Kim's CS151
 * CS151 Team Project - Mancala
 * @author Monica Zhang
 * @author Steven Lu
 * @version 1.0 11/23/24
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * This class models the start screen of the mancala game. <br>
 * It lets the players select a pattern the game board will be in and informs MancalaModel.
 */
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

        startGameButton.addActionListener(e -> {
            startGameAction.actionPerformed(e);
            model.setGameScreen();
        });

        add(text);
        add(pattern1Button);
        add(pattern2Button);
        add(startGameButton);
    }

}

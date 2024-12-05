/**
 * Fall 2024 - Doctor Kim's CS151
 * CS151 Team Project - Mancala
 * @author Monica Zhang
 * @author Billy Porras-Molina
 * @version 1.0 11/23/24
 */

import java.awt.*;

/**
 * This class define a specific color scheme for the game board.
 */
public class Pattern1 implements BoardPatternStrategy {

    @Override
    public Color color() {
        return Color.RED;
    }

    public Color pitColor()
    {
        return Color.GREEN;
    }
}

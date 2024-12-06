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

    /**
     * Provides color of the pits on game board
     * @return A Color representing the pit color (white)
     */
    public Color pitColor()
    {
        return Color.GREEN;
    }
}

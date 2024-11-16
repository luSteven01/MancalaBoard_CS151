import java.awt.*;

public class Pit {

    private int stones;
    private Rectangle bound;

    public Pit() {

    }

    public void increment() {
        stones++;
    }

    public int remove() {
        int stones = this.stones;
        this.stones = 0;
        return stones;
    }

}

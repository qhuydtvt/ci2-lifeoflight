package maps;

/**
 * Created by huynq on 10/9/17.
 */
public class Player {
    int x;
    int y;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean match(int x, int y) {
        return this.x == x && this.y == y;
    }
}

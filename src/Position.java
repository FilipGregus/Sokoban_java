/**
 * Trieda reprezentujúca pozíciu na hernej ploche
 */

public class Position {
    private int x;
    private int y;

    /**
     * Konštruktor triedy Position inicializuje pozíciu s danými súradnicami
     *
     * @param x x-ová súradnica
     * @param y y-ová súradnica
     * @author Filip Greguš
     */

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Position other) {
        return this.x == other.x && this.y == other.y;
    }
}

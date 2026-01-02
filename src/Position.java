/**
 * Trieda reprezentujuca poziciu na hernej ploche
 */

public class Position {
    private int x;
    private int y;

    /**
     * Konstruktor triedy Position inicializuje poziciu s danymi suradnicami
     *
     * @param x x-ova suradnica
     * @param y y-ova suradnica
     * @author Filip Gregus
     */

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
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
/**
 * Trieda Player reprezentuje hraca v hre a poskytuje metody na jeho pohyb
 *
 * @author Filip Gregus
 * @version 1.0
 */

public class Player {
    private final GameObject playerObject;
    private final GameController gameController;
    private final Movement movement;

    /**
     * Konstruktor triedy Player inicializuje hraca s danym hernym objektom a hernym kontrolerom
     *
     * @param playerObject   herny objekt reprezentujuci hraca
     * @param gameController herny kontroler riadiaci logiku hry
     * @author Filip Gregus
     */

    public Player(GameObject playerObject, GameController gameController) {
        this.playerObject = playerObject;
        this.gameController = gameController;
        GameController.getGameManager().manageObject(this);
        this.movement = new Movement(gameController);
        playerObject.getImg().makeVisible();
    }

    /**
     * Ziskanie herneho objektu reprezentujuceho hraca
     *
     * @return herny objekt reprezentujuci hraca
     * @author Filip Gregus
     */

    public GameObject getPlayerObject() {
        return this.playerObject;
    }

    /**
     * Metoda na pohyb hraca smerom dole
     *
     * @author Filip Gregus
     */

    public void moveDown() {
        this.movement.makeMove(0, 1, this);
        this.gameController.checkWin();
    }

    /**
     * Metoda na pohyb hraca smerom hore
     *
     * @author Filip Gregus
     */

    public void moveUp() {
        this.movement.makeMove(0, -1, this);
        this.gameController.checkWin();
    }

    /**
     * Metoda na pohyb hraca smerom dolava
     *
     * @author Filip Gregus
     */

    public void moveLeft() {
        this.movement.makeMove(-1, 0, this);
        this.gameController.checkWin();
    }

    /**
     * Metoda na pohyb hraca smerom doprava
     *
     * @author Filip Gregus
     */

    public void moveRight() {
        this.movement.makeMove(1, 0, this);
        this.gameController.checkWin();
    }
}
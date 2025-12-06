public class Player {
    private final GameObject playerObject;
    private final GameController gameController;
    private final Movement movement;

    /**
     * Konštruktor triedy Player inicializuje hráča s daným herným objektom a herným kontrolérom
     * @param playerObject herný objekt reprezentujúci hráča
     * @param gameController herný kontrolér riadiaci logiku hry
     * @author Filip Greguš
     */

    public Player(GameObject playerObject, GameController gameController) {
        this.playerObject = playerObject;
        this.gameController = gameController;
        GameController.getGameManager().manageObject(this);
        movement = new Movement(gameController);
        playerObject.getImg().makeVisible();
    }

    /**
     * Získanie herného objektu reprezentujúceho hráča
     * @return herný objekt reprezentujúci hráča
     * @author Filip Greguš
     */

    public GameObject getPlayerObject() {
        return playerObject;
    }

    /**
     * Metóda na pohyb hráča smerom dole
     * @author Filip Greguš
     */

    public void moveDown() {
        movement.makeMove(0, 1, this);
        gameController.checkWin();
    }

    /**
     * Metóda na pohyb hráča smerom hore
     * @author Filip Greguš
     */

    public void moveUp() {
        movement.makeMove(0, -1, this);
        gameController.checkWin();
    }

    /**
     * Metóda na pohyb hráča smerom doľava
     * @author Filip Greguš
     */

    public void moveLeft() {
        movement.makeMove(-1, 0, this);
        gameController.checkWin();
    }

    /**
     * Metóda na pohyb hráča smerom doprava
     * @author Filip Greguš
     */

    public void moveRight() {
        movement.makeMove(1, 0, this);
        gameController.checkWin();
    }
}

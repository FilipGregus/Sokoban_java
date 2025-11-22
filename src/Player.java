public class Player {
    private GameObject playerObject;
    private GameController gameController;
    private Movement movement;

    public Player(GameObject playerObject, GameController gameController) {
        this.playerObject = playerObject;
        this.gameController = gameController;
        gameController.getGameManager().manageObject(this);
        movement = new Movement(gameController);
        playerObject.getImg().makeVisible();
    }

    public GameObject getPlayerObject() {
        return playerObject;
    }

    public void moveDown() {
        movement.makeMove(0, 1, this);
        gameController.checkWin();
    }

    public void moveUp() {
        movement.makeMove(0, -1, this);
        gameController.checkWin();
    }

    public void moveLeft() {
        movement.makeMove(-1, 0, this);
        gameController.checkWin();
    }

    public void moveRight() {
        movement.makeMove(1, 0, this);
        gameController.checkWin();
    }


}

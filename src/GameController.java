import fri.shapesge.Manager;

import java.util.ArrayList;

public class GameController {
    private Manager gameManager;
    private ArrayList<GameObject> board;

    public GameController() {
        this.gameManager = new Manager();
        this.gameManager.manageObject(this);
        this.board = new ArrayList<>();
    }

    public Manager getGameManager() {
        return gameManager;
    }





}

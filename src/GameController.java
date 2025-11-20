import fri.shapesge.ImageData;
import fri.shapesge.Manager;
import fri.shapesge.Image;

import java.util.ArrayList;

public class GameController {
    private Manager gameManager;
    private ArrayList<GameObject> gameObjects;
    private LevelManager levelManager;
    private ImageData boxImgData;
    private ImageData correctBoxImgData;
    private ImageData groundImgData;
    private ImageData playerImgData;
    private ImageData targetBoxImgData;
    private ImageData wallImgData;
    private int boxSize;    
    private Player player;


    public GameController(int boxSize) {
        this.gameManager = new Manager();
        this.gameManager.manageObject(this);
        this.gameObjects = new ArrayList<>();
        this.boxImgData = new ImageData("src/icons/box.png");
        this.correctBoxImgData = new ImageData("src/icons/correct_box.png");
        this.groundImgData = new ImageData("src/icons/ground.png");
        this.playerImgData = new ImageData("src/icons/player.png");
        this.targetBoxImgData = new ImageData("src/icons/target_box.png");
        this.wallImgData = new ImageData("src/icons/wall.png");
        this.boxSize = boxSize;
        this.levelManager = new LevelManager("./levels", playerImgData, targetBoxImgData,
                wallImgData, correctBoxImgData, boxImgData, boxSize);
        


        this.gameObjects = loadBoard(1);
        drawBoard();
    }

    public ArrayList<GameObject> loadBoard(int levelNumber) {
        return this.levelManager.loadLevel(levelNumber);
    }

    private void drawBoard() {

        for (int y = 0; y < levelManager.getRows(); y++) {
            for (int x = 0; x < levelManager.getColumns(); x++) {
                boolean isEmpty = true;
                for (GameObject obj : gameObjects) {
                    if (obj.getPosition().equals(new Position(x, y))) {
                        isEmpty = false;
                    }
                }

                if (isEmpty) {
                     new Image(groundImgData, x * this.boxSize, y * this.boxSize).makeVisible();
                }
            }
        }
    }

    private Image createPlayer(int x, int y) {
        this.player = new Player(x, y, this.gameObjects, this.gameManager);

        Image playerImg = new Image(playerImgData, x * this.boxSize, y * this.boxSize);
        gameManager.manageObject(playerImg);
        return playerImg;
    }


    public Manager getGameManager() {
        return gameManager;
    }


}

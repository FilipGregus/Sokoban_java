import fri.shapesge.ImageData;
import fri.shapesge.Manager;
import fri.shapesge.Image;

import java.util.ArrayList;

public class GameController {
    private Manager gameManager;
    private ArrayList<GameObject> gameObjects;
    private ArrayList<Image> grounds;
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
        this.grounds = new ArrayList<>();
        this.boxImgData = new ImageData("src/icons/box.png");
        this.correctBoxImgData = new ImageData("src/icons/correct_box.png");
        this.groundImgData = new ImageData("src/icons/ground.png");
        this.playerImgData = new ImageData("src/icons/player.png");
        this.targetBoxImgData = new ImageData("src/icons/target_box.png");
        this.wallImgData = new ImageData("src/icons/wall.png");
        this.boxSize = boxSize;
        this.levelManager = new LevelManager("./levels", boxSize);

        this.gameObjects = loadBoard(1);
        drawGround();
    }

    private ArrayList<GameObject> loadBoard(int levelNumber) {
        ArrayList<GameObject> loadedObjects = this.levelManager.loadLevel(levelNumber);
        ArrayList<GameObject> objects = new ArrayList<>();


        for (GameObject load : loadedObjects) {
            GameObject obj = null;
            switch (load.getObjectType()) {
                case Player -> {
                    obj = new GameObject(load.getPosition().getX(), load.getPosition().getY(), ObjectType.Player,  this.boxSize);
                    obj.setImg(this.playerImgData);
                    this.player = new Player(obj, this);
                    // player will be managed by Player wrapper; don't add yet
                }
                case Wall ->{
                    obj = new GameObject(load.getPosition().getX(), load.getPosition().getY(), ObjectType.Wall, this.boxSize);
                    obj.setImg(this.wallImgData);
                }

                case Box ->{
                    obj = new GameObject(load.getPosition().getX(), load.getPosition().getY(), ObjectType.Box,  this.boxSize);
                    obj.setImg(this.boxImgData);
                }
                case CorrectBox ->{
                    obj = new GameObject(load.getPosition().getX(), load.getPosition().getY(), ObjectType.CorrectBox, this.boxSize);
                    obj.setImg(this.correctBoxImgData);
                }
                case BoxTarget ->{
                    obj = new GameObject(load.getPosition().getX(), load.getPosition().getY(), ObjectType.BoxTarget,  this.boxSize);
                    obj.setImg(this.targetBoxImgData);
                }
            }
            if (obj != null && obj.getObjectType() != ObjectType.Player) {
                obj.getImg().makeVisible();
                objects.add(obj);
            }
        }

        return objects;
    }




    public void drawGround() {
        for(Image img : grounds) {
            img.makeInvisible();
        }

        grounds.clear();

        for (int y = 0; y < levelManager.getActualRows(); y++) {
            for (int x = 0; x < levelManager.getActualColumns(); x++) {
                boolean isEmpty = true;
                for (GameObject obj : gameObjects) {
                    if (obj.getPosition().equals(new Position(x, y)) || this.player.getPlayerObject().getPosition().equals(new Position(x, y))) {
                        isEmpty = false;
                    }
                }

                if (isEmpty) {
                    Image ground = new Image(groundImgData, x * this.boxSize, y * this.boxSize);
                    ground.makeVisible();
                     grounds.add(ground);
                }
            }
        }
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public Manager getGameManager() {
        return gameManager;
    }

    public int getActualLevelRows() {
        return levelManager.getActualRows();
    }

    public int getActualLevelColumns() {
        return levelManager.getActualColumns();
    }

    public ImageData getBoxImgData() {
        return boxImgData;
    }

    public ImageData getCorrectBoxImgData(){
        return correctBoxImgData;
    }

    public ImageData getTargetBoxImgData(){
        return targetBoxImgData;
    }

    public int getBoxSize() {
        return boxSize;
    }

    public void checkWin() {
        boolean allBoxesOnTargets = true;

        for (GameObject obj : gameObjects) {
            if (obj.getObjectType() == ObjectType.Box) {
                allBoxesOnTargets = false;
                break;
            }
        }

        if (allBoxesOnTargets) {
            System.out.println("You win!");
            // Additional win logic can be added here
        }
    }

}

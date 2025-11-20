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
        this.levelManager = new LevelManager("./levels", playerImgData, targetBoxImgData,
                wallImgData, correctBoxImgData, boxImgData, boxSize);
        


        this.gameObjects = loadBoard(1);
        drawBoard();
    }

    public ArrayList<GameObject> loadBoard(int levelNumber) {
        ArrayList<GameObject> temp = this.levelManager.loadLevel(levelNumber);

        for (GameObject obj : temp) {
            if (obj.getObjectType() == ObjectType.Player) {
                this.player = new Player(obj, this);
            }
        }

        temp.remove(this.player.getPlayerObject());

        return temp;


    }




    public void drawBoard() {
        for(Image img : grounds) {
            img.makeInvisible();
        }

        grounds.clear();

        for (int y = 0; y < levelManager.getRows(); y++) {
            for (int x = 0; x < levelManager.getColumns(); x++) {
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

    public int getRows() {
        return levelManager.getRows();
    }

    public int getColumns() {
        return levelManager.getColumns();
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

    public void moveDown() {
//        if(inBoard(new Position(this.player.getPosition().getX(), this.player.getPosition().getY()+1))) {
//            if(getObjectAtPosition(new Position(this.player.getPosition().getX(), this.player.getPosition().getY()+1))==null) {
//                this.player.setPosition(new Position(this.player.getPosition().getX(), this.player.getPosition().getY()+1));
//                drawBoard();
//            }
//        }
    }



}

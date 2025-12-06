import fri.shapesge.ImageData;
import fri.shapesge.Manager;
import fri.shapesge.Image;

import java.util.ArrayList;

/**
 * Trieda GameController riadi logiku hry
 * @author Filip Greguš
 * @version 1.0
 */

public class GameController {
    private final Manager gameManager;
    private ArrayList<GameObject> gameObjects;
    private ArrayList<Image> grounds;
    private final LevelManager levelManager;
    private final ImageData boxImgData;
    private final ImageData correctBoxImgData;
    private final ImageData groundImgData;
    private final ImageData playerImgData;
    private final ImageData targetBoxImgData;
    private final ImageData wallImgData;
    private Player player;
    private int currentLevel;
    private final int levelsCount;

    private static final int BOX_SIZE = 50;

    /**
     * Konštruktor triedy GameController inicializuje herný manažér, načíta úroveň a vykreslí hru
     * @author Filip Greguš
     */

    public GameController() {
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
        this.levelManager = new LevelManager("./levels", BOX_SIZE);

        this.currentLevel = 1;
        this.gameObjects = loadBoard(currentLevel);
        drawGround();
        this.levelsCount = this.levelManager.getLevelsCount();
    }

    /**
     * Metóda na načítanie hernej úrovne
     * @param levelNumber číslo úrovne na načítanie
     * @return zoznam herných objektov načítaných z úrovne
     * @author Filip Greguš
     */

    private ArrayList<GameObject> loadBoard(int levelNumber) {
        ArrayList<GameObject> loadedObjects = this.levelManager.loadLevel(levelNumber);
        ArrayList<GameObject> objects = new ArrayList<>();


        for (GameObject load : loadedObjects) {
            GameObject obj = null;
            switch (load.getObjectType()) {
                case PLAYER -> {
                    obj = new GameObject(load.getPosition().getX(), load.getPosition().getY(), ObjectType.PLAYER, BOX_SIZE);
                    obj.setImg(this.playerImgData);
                    this.player = new Player(obj, this);
                    // player will be managed by Player wrapper; don't add yet
                }
                case WALL -> {
                    obj = new GameObject(load.getPosition().getX(), load.getPosition().getY(), ObjectType.WALL, BOX_SIZE);
                    obj.setImg(this.wallImgData);
                }

                case BOX -> {
                    obj = new GameObject(load.getPosition().getX(), load.getPosition().getY(), ObjectType.BOX, BOX_SIZE);
                    obj.setImg(this.boxImgData);
                }
                case CORRECT_BOX -> {
                    obj = new GameObject(load.getPosition().getX(), load.getPosition().getY(), ObjectType.CORRECT_BOX, BOX_SIZE);
                    obj.setImg(this.correctBoxImgData);
                }
                case BOX_TARGET -> {
                    obj = new GameObject(load.getPosition().getX(), load.getPosition().getY(), ObjectType.BOX_TARGET, BOX_SIZE);
                    obj.setImg(this.targetBoxImgData);
                }
            }
            if (obj != null && obj.getObjectType() != ObjectType.PLAYER) {
                obj.getImg().makeVisible();
                objects.add(obj);
            }
        }

        return objects;
    }


    /**
     * Metóda na vykreslenie prázdnych políčok na hernej ploche
     * @author Filip Greguš
     */

    public void drawGround() {
        for (Image img : grounds) {
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
                    Image ground = new Image(groundImgData, x * BOX_SIZE, y * BOX_SIZE);
                    ground.makeVisible();
                    grounds.add(ground);
                }
            }
        }
    }

    /**
     * Gettery pre herné objekty
     * @author Filip Greguš
     */

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    /**
     * Getter pre herného manažéra
     * @return herný manažér
     * @author Filip Greguš
     */

    public Manager getGameManager() {
        return gameManager;
    }

    /** Gettery pre aktuálny počet riadkov úrovne
     * @author Filip Greguš
     * @return aktuálny počet riadkov úrovne
     */

    public int getActualLevelRows() {
        return levelManager.getActualRows();
    }

    /** Gettery pre aktuálny počet stĺpcov úrovne
     * @author Filip Greguš
     * @return aktuálny počet stĺpcov úrovne
     */

    public int getActualLevelColumns() {
        return levelManager.getActualColumns();
    }

    /** Getter pre box image
     * @author Filip Greguš
     * @return ImageData pre box
     */

    public ImageData getBoxImgData() {
        return boxImgData;
    }

    /** Getter pre correctBox image
     * @author Filip Greguš
     * @return ImageData pre correctBox
     */

    public ImageData getCorrectBoxImgData() {
        return correctBoxImgData;
    }

    /** Getter pre targetBox image
     * @author Filip Greguš
     * @return ImageData pre targetBox
     */

    public ImageData getTargetBoxImgData() {
        return targetBoxImgData;
    }

    /** Getter pre veľkosť boxu
     * @author Filip Greguš
     * @return veľkosť boxu
     */

    public int getBoxSize() {
        return BOX_SIZE;
    }

    /**
     * Metóda na kontrolu výhry
     * @author Filip Greguš
     */

    public void checkWin() {
        boolean allBoxesOnTargets = true;

        for (GameObject obj : gameObjects) {
            if (obj.getObjectType() == ObjectType.BOX) {
                allBoxesOnTargets = false;
                break;
            }
        }

        if (allBoxesOnTargets) {
            javax.swing.Timer timer;

            if (this.currentLevel < this.levelsCount) {
                timer = new javax.swing.Timer(120, e -> {
                    ((javax.swing.Timer) e.getSource()).stop();

                    int choice = MessageBox.showNextLevelDialog();

                    if (choice == 0) { // Continue
                        clearBoard();
                        currentLevel++;
                        this.gameObjects = loadBoard(currentLevel);
                        drawGround();
                    } else { // Close or dialog closed
                        System.exit(0);
                    }
                });
            } else {
                timer = new javax.swing.Timer(120, e -> {
                    ((javax.swing.Timer) e.getSource()).stop();

                    int choice = MessageBox.showRestartDialog();

                    if (choice == 0) { // Continue
                        clearBoard();
                        currentLevel = 1;
                        this.gameObjects = loadBoard(currentLevel);
                        drawGround();
                    } else { // Close or dialog closed
                        System.exit(0);
                    }
                });
            }
            timer.setRepeats(false);
            timer.start();
        }
    }

    /**
     * Metóda na vyčistenie hernej plochy
     * @author Filip Greguš
     */

    private void clearBoard() {
        for (GameObject obj : gameObjects) {
            if (obj.getImg() != null) obj.getImg().makeInvisible();
        }

        if (player != null && player.getPlayerObject() != null && player.getPlayerObject().getImg() != null) {
            player.getPlayerObject().getImg().makeInvisible();
            this.gameManager.stopManagingObject(this.player);
            player = null;
        }
        this.gameObjects.clear();
    }
}

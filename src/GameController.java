import fri.shapesge.Image;
import fri.shapesge.TextBlock;
import fri.shapesge.FontStyle;
import fri.shapesge.ImageData;
import fri.shapesge.Manager;

import java.util.ArrayList;

/**
 * Trieda GameController riadi logiku hry
 *
 * @author Filip Greguš
 * @version 1.0
 */

public class GameController {
    private static Manager gameManager;
    private ArrayList<GameObject> gameObjects;
    private final ArrayList<Image> grounds;
    private final LevelManager levelManager;
    private final ImageData groundImgData;
    private Player player;
    private int currentLevel;
    private final int levelsCount;
    private final TextBlock t;

    private static final int BOX_SIZE = 50;
    private static final int INTENT_X = 0;
    private static final int INTENT_Y = 40;

    /**
     * Konštruktor triedy GameController inicializuje herný manažér, načíta úroveň a vykreslí hru
     *
     * @author Filip Greguš
     */

    public GameController() {
        if (gameManager == null) {
            gameManager = new Manager();
        }
        gameManager.manageObject(this);
        this.gameObjects = new ArrayList<>();
        this.grounds = new ArrayList<>();
        this.groundImgData = new ImageData("src/icons/ground.png");
        this.levelManager = new LevelManager("./levels");

        this.currentLevel = 1;
        this.gameObjects = this.loadBoard(currentLevel);
        this.drawGround();
        this.levelsCount = this.levelManager.getLevelsCount();

        this.t = new TextBlock("Level " + currentLevel);
        this.showLevelInfo();
    }

    /**
     * Metóda na načítanie hernej úrovne
     *
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
                case PLAYER:
                    obj = new GameObject(load.getPosition().getX(), load.getPosition().getY(), ObjectType.PLAYER);
                    this.player = new Player(obj, this);
                    break;
                case WALL:
                    obj = new GameObject(load.getPosition().getX(), load.getPosition().getY(), ObjectType.WALL);
                    break;

                case BOX:
                    obj = new GameObject(load.getPosition().getX(), load.getPosition().getY(), ObjectType.BOX);
                    break;
                case CORRECT_BOX:
                    obj = new GameObject(load.getPosition().getX(), load.getPosition().getY(), ObjectType.CORRECT_BOX);
                    break;
                case BOX_TARGET:
                    obj = new GameObject(load.getPosition().getX(), load.getPosition().getY(), ObjectType.BOX_TARGET);
                    break;
            }
            if (obj != null && obj.getObjectType() != ObjectType.PLAYER) {
                obj.getImg().makeVisible();
                objects.add(obj);
            }
        }

        return objects;
    }


    /**
     * Metóda na vykreslenie prázdnych políčok plochy
     *
     * @author Filip Greguš
     */

    public void drawGround() {
        for (Image img : this.grounds) {
            img.makeInvisible();
        }

        this.grounds.clear();

        for (int y = 0; y < this.levelManager.getActualRows(); y++) {
            for (int x = 0; x < this.levelManager.getActualColumns(); x++) {
                boolean isEmpty = true;
                for (GameObject obj : this.gameObjects) {
                    if (obj.getPosition().equals(new Position(x, y)) || this.player.getPlayerObject().getPosition().equals(new Position(x, y))) {
                        isEmpty = false;
                    }
                }

                if (isEmpty) {
                    Image ground = new Image(this.groundImgData, x * BOX_SIZE + INTENT_X, y * BOX_SIZE + INTENT_Y);
                    ground.makeVisible();
                    this.grounds.add(ground);
                }
            }
        }
    }

    /**
     * Gettery pre herné objekty
     *
     * @author Filip Greguš
     */

    public ArrayList<GameObject> getGameObjects() {
        return this.gameObjects;
    }

    /**
     * Getter pre herného manažéra
     *
     * @return herný manažér
     * @author Filip Greguš
     */

    public static Manager getGameManager() {
        return gameManager;
    }

    /**
     * Gettery pre aktuálny počet riadkov úrovne
     *
     * @return aktuálny počet riadkov úrovne
     * @author Filip Greguš
     */

    public int getActualLevelRows() {
        return this.levelManager.getActualRows();
    }

    /**
     * Gettery pre aktuálny počet stĺpcov úrovne
     *
     * @return aktuálny počet stĺpcov úrovne
     * @author Filip Greguš
     */

    public int getActualLevelColumns() {
        return this.levelManager.getActualColumns();
    }

    /**
     * Getter pre veľkosť boxu
     *
     * @return veľkosť boxu
     * @author Filip Greguš
     */

    public static int getBoxSize() {
        return BOX_SIZE;
    }

    /**
     * Getter pre odsadenie X
     *
     * @return odsadenie X
     * @author Filip Greguš
     */
    public static int getIntentX() {
        return INTENT_X;
    }

    /**
     * Getter pre odsadenie Y
     *
     * @return odsadenie Y
     * @author Filip Greguš
     */
    public static int getIntentY() {
        return INTENT_Y;
    }

    /**
     * Metóda na kontrolu výhry
     *
     * @author Filip Greguš
     */

    public void checkWin() {
        boolean allBoxesOnTargets = true;

        for (GameObject obj : this.gameObjects) {
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
                        this.clearBoard();
                        this.currentLevel++;
                        this.gameObjects = loadBoard(currentLevel);
                        this.drawGround();
                        this.showLevelInfo();
                    } else { // Close or dialog closed
                        System.exit(0);
                    }
                });
            } else {
                timer = new javax.swing.Timer(120, e -> {
                    ((javax.swing.Timer) e.getSource()).stop();

                    int choice = MessageBox.showRestartCompleteDialog();

                    if (choice == 0) { // Continue
                        this.clearBoard();
                        this.currentLevel = 1;
                        this.gameObjects = loadBoard(currentLevel);
                        this.drawGround();
                    } else { // Close or dialog closed
                        System.exit(0);
                    }
                });
            }
            timer.setRepeats(false);
            timer.start();
        }
    }

    public void cancel() {
        javax.swing.Timer timer;
        timer = new javax.swing.Timer(120, e -> {
            ((javax.swing.Timer) e.getSource()).stop();

            int choice = MessageBox.showRestartDialog();

            if (choice == 0) { // Restart level
                this.clearBoard();
                this.gameObjects = loadBoard(currentLevel);
                this.drawGround();
            } else { // Close or dialog closed
                System.exit(0);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void clearBoard() {
        for (GameObject obj : this.gameObjects) {
            if (obj.getImg() != null) {
                obj.getImg().makeInvisible();
            }
        }

        if (this.player != null && this.player.getPlayerObject() != null && this.player.getPlayerObject().getImg() != null) {
            player.getPlayerObject().getImg().makeInvisible();
            gameManager.stopManagingObject(this.player);
            this.player = null;
        }
        this.gameObjects.clear();
    }

    private void showLevelInfo() {
        this.t.makeInvisible();
        this.t.changeText("Level " + this.currentLevel);
        this.t.changeFont("Serif", FontStyle.BOLD, 30);
        this.t.changePosition(200, 25);
        this.t.makeVisible();
    }
}

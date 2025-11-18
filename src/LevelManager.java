import java.util.ArrayList;

public class LevelManager {
    private String pathToLevels;
    private int currentLevel;
    private int totalLevels;

    public LevelManager(String pathToLevels) {
        this.pathToLevels = pathToLevels;
    }

    public ArrayList<GameObject> loadLevel(int levelNumber) {
        ArrayList<GameObject> levelObjects = new ArrayList<>();

        return levelObjects;
    }

}

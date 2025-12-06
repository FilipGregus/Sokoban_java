import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class LevelManager {
    private final String pathToLevels;
    private int actualColumns = 0;
    private int actualRows = 0;
    private final int boxSize;

    public LevelManager(String pathToLevels, int boxSize) {
        this.pathToLevels = pathToLevels;
        this.boxSize = boxSize;
    }

    public ArrayList<GameObject> loadLevel(int levelNumber) {
        ArrayList<GameObject> loadedObjects = new ArrayList<>();
        Scanner scanner = null;
        try {
            File file = new File(pathToLevels + "/level" + levelNumber + ".txt");
            scanner = new Scanner(file);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        if( scanner != null) {
            actualColumns = scanner.nextInt();
            actualRows = scanner.nextInt();
            scanner.nextLine();
        }

        for (int i = 0; i < actualRows; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < actualColumns; j++) {
                char ch = line.charAt(j);
                ObjectType type = switch (ch) {
                    case 'P' -> ObjectType.PLAYER;
                    case 'X' -> ObjectType.WALL;
                    case 'B' -> ObjectType.BOX;
                    case 'C' -> ObjectType.CORRECT_BOX;
                    case 'O' -> ObjectType.BOX_TARGET;
                    default -> null;
                };
                if (type != null) {
                    loadedObjects.add(new GameObject(j, i, type, this.boxSize) );
                }
            }
        }

        return loadedObjects;
    }

    public int getActualColumns() { return actualColumns; }
    public int getActualRows() { return actualRows; }

    public int getLevelsCount() {
        File folder = new File(pathToLevels);
        return Objects.requireNonNull(folder.listFiles((dir, name) -> name.endsWith(".txt"))).length;
    }
}

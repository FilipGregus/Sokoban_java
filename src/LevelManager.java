import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Trieda LevelManager riadi načítanie úrovní zo súborov
 *
 * @author Filip Greguš
 * @version 1.0
 */

public class LevelManager {
    private final String pathToLevels;
    private int actualColumns = 0;
    private int actualRows = 0;

    public LevelManager(String pathToLevels) {
        this.pathToLevels = pathToLevels;
    }

    /**
     * Metóda pre načítanie úrovne zo súboru
     *
     * @param levelNumber číslo úrovne na načítanie
     * @return zoznam načítaných herných objektov
     * @author Filip Greguš
     */

    public ArrayList<GameObject> loadLevel(int levelNumber) {
        ArrayList<GameObject> loadedObjects = new ArrayList<>();
        Scanner scanner = null;
        try {
            File file = new File(pathToLevels + "/level" + levelNumber + ".txt");
            scanner = new Scanner(file);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        if (scanner != null) {
            actualColumns = scanner.nextInt();
            actualRows = scanner.nextInt();
            scanner.nextLine();
        }

        for (int i = 0; i < actualRows; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < actualColumns; j++) {
                char ch = line.charAt(j);
                ObjectType type;
                switch (ch) {
                    case 'P':
                        type = ObjectType.PLAYER;
                        break;
                    case 'X':
                        type = ObjectType.WALL;
                        break;
                    case 'B':
                        type = ObjectType.BOX;
                        break;
                    case 'C':
                        type = ObjectType.CORRECT_BOX;
                        break;
                    case 'O':
                        type = ObjectType.BOX_TARGET;
                        break;
                    default:
                        type = null;
                };

                if (type != null) {
                    loadedObjects.add(new GameObject(j, i, type));
                }
            }
        }

        return loadedObjects;
    }

    /**
     * Metóda na získanie počtu stĺpcov v aktuálnej úrovni
     *
     * @author Filip Greguš
     */

    public int getActualColumns() {
        return actualColumns;
    }

    /**
     * Metóda na získanie počtu riadkov v aktuálnej úrovni
     *
     * @author Filip Greguš
     */

    public int getActualRows() {
        return actualRows;
    }

    /**
     * Metóda na získanie počtu dostupných úrovní
     *
     * @author Filip Greguš
     */

    public int getLevelsCount() {
        File folder = new File(pathToLevels);
        return Objects.requireNonNull(folder.listFiles((dir, name) -> name.endsWith(".txt"))).length;
    }
}

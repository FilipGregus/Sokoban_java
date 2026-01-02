import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Trieda LevelManager riadi nacitanie urovni zo suborov
 *
 * @author Filip Gregus
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
     * Metoda pre nacitanie urovne zo suboru
     *
     * @param levelNumber cislo urovne na nacitanie
     * @return zoznam nacitanych hernych objektov
     * @author Filip Gregus
     */

    public ArrayList<GameObject> loadLevel(int levelNumber) {
        ArrayList<GameObject> loadedObjects = new ArrayList<>();
        Scanner scanner = null;
        try {
            File file = new File(this.pathToLevels + "/level" + levelNumber + ".txt");
            scanner = new Scanner(file);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        if (scanner != null) {
            this.actualColumns = scanner.nextInt();
            this.actualRows = scanner.nextInt();
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
                }

                if (type != null) {
                    loadedObjects.add(new GameObject(j, i, type));
                }
            }
        }

        return loadedObjects;
    }

    /**
     * Metoda na ziskanie poctu stlpcov v aktualnej urovni
     *
     * @author Filip Gregus
     */

    public int getActualColumns() {
        return this.actualColumns;
    }

    /**
     * Metoda na ziskanie poctu riadkov v aktualnej urovni
     *
     * @author Filip Gregus
     */

    public int getActualRows() {
        return this.actualRows;
    }

    /**
     * Metoda na ziskanie poctu dostupnych urovni
     *
     * @author Filip Gregus
     */

    public int getLevelsCount() {
        File folder = new File(this.pathToLevels);
        return Objects.requireNonNull(folder.listFiles((dir, name) -> name.endsWith(".txt"))).length;
    }
}
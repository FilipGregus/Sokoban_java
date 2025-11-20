import fri.shapesge.Image;
import fri.shapesge.ImageData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LevelManager {
    private String pathToLevels;
    private int currentLevel;
    private int totalLevels;
    private int columns = 0;
    private int rows = 0;
    private ImageData boxImgData;
    private ImageData correctBoxImgData;
    private ImageData playerImgData;
    private ImageData targetBoxImgData;
    private ImageData wallImgData;
    private int boxSize;


    public LevelManager(String pathToLevels, ImageData playerImgData,
                        ImageData targetBoxImgData, ImageData wallImgData, ImageData correctBoxImgData, ImageData boxImgData
    , int boxSize) {
        this.pathToLevels = pathToLevels;
        this.playerImgData = playerImgData;
        this.targetBoxImgData = targetBoxImgData;
        this.wallImgData = wallImgData;
        this.correctBoxImgData = correctBoxImgData;
        this.boxImgData = boxImgData;
        this.boxSize = boxSize;

    }

    public ArrayList<GameObject> loadLevel(int levelNumber) {
        ArrayList<GameObject> levelObjects = new ArrayList<>();
        Scanner scanner = null;
        try {
            File file = new File(pathToLevels + "/level" + levelNumber + ".txt");
            scanner = new Scanner(file);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }


        assert scanner != null;
        columns = scanner.nextInt();
        rows = scanner.nextInt();

        scanner.nextLine();

        for (int i = 0; i < rows; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < columns; j++) {
                char ch = line.charAt(j);
                GameObject obj = switch (ch) {
                    case 'P' -> new GameObject(j, i, ObjectType.Player, playerImgData,this.boxSize);
                    case 'X' -> new GameObject(j, i, ObjectType.Wall, wallImgData,this.boxSize);
                    case 'B' -> new GameObject(j, i, ObjectType.Box, boxImgData,this.boxSize);
                    case 'C' -> new GameObject(j, i, ObjectType.CorrectBox, correctBoxImgData,this.boxSize);
                    case 'O' -> new GameObject(j, i, ObjectType.BoxTarget, targetBoxImgData,this.boxSize);
                    default -> null;
                };
                if (obj != null) {
                    levelObjects.add(obj);
                }
            }
        }


        return levelObjects;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }
}

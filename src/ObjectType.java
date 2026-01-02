import fri.shapesge.ImageData;

/**
 * Enum typ reprezentujúci rôzne typy herných objektov
 *
 * @author Filip Greguš
 * @version 1.0
 */

public enum ObjectType {
    PLAYER(new ImageData("src/icons/player.png")),
    WALL(new ImageData("src/icons/wall.png")),
    BOX(new ImageData("src/icons/box.png")),
    CORRECT_BOX(new ImageData("src/icons/correct_box.png")),
    BOX_TARGET(new ImageData("src/icons/target_box.png"));

    private final ImageData imageData;

    ObjectType(ImageData imageData) {
        this.imageData = imageData;
    }

    /**
     * Získanie obrázku pre daný typ objektu
     *
     * @return ImageData objektu
     * @author Filip Greguš
     */

    public ImageData getImageData() {
        return this.imageData;
    }
}
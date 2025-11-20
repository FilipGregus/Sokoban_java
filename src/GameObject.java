import fri.shapesge.Image;
import fri.shapesge.ImageData;

public class GameObject {
    private Position position;
    private ObjectType objectType;

    private Image img;

    public GameObject(int x, int y, ObjectType objectType, ImageData imgData, int boxSize) {
        this.position = new Position(x, y);
        this.objectType = objectType;
        this.img = new Image(imgData, x * boxSize, y * boxSize);
        this.img.makeVisible();
    }

    public Position getPosition() {
        return position;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public Image getImg() {
        return img;
    }
}

import fri.shapesge.Image;
import fri.shapesge.ImageData;

public class GameObject {
    private Position position;
    private ObjectType objectType;
    private int boxSize;

    private Image img;

    public GameObject(int x, int y, ObjectType objectType, ImageData imgData, int boxSize) {
        this.boxSize = boxSize;
        this.position = new Position(x, y);
        this.objectType = objectType;
        this.img = new Image(imgData, x * boxSize, y * boxSize);
        this.img.changePosition(position.getX() * boxSize, position.getY() * boxSize);
        this.img.makeVisible();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
        this.img.changePosition(position.getX() * boxSize, position.getY() * boxSize);
    }

    public ObjectType getObjectType() {
        return objectType;
    }


    public Image getImg() {
        return img;
    }

    public void setImg(ImageData img) {
        this.img.changeImage(img);
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

}

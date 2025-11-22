import fri.shapesge.Image;
import fri.shapesge.ImageData;

public class GameObject {
    private Position position;
    private ObjectType objectType;
    private int boxSize;

    private Image img;

    public GameObject(int x, int y, ObjectType objectType, int boxSize) {
        this.boxSize = boxSize;
        this.position = new Position(x, y);
        this.objectType = objectType;
        this.img = null;
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
        if(this.img == null) {
            this.img= new Image(img, this.position.getX() * boxSize, this.position.getY() * boxSize);
            return;
        }

        this.img.changeImage(img);
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

}

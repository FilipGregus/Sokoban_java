import fri.shapesge.Image;
import fri.shapesge.ImageData;

/**
 * Trieda reprezentujuca herny objekt v hre
 *
 * @author Filip Gregus
 * @version 1.0
 */

public class GameObject {
    private Position position;
    private ObjectType objectType;

    private Image img;

    /**
     * Konstruktor triedy GameObject inicializuje herny objekt s danou poziciou, typom a velkostou boxu
     *
     * @param x          x-ova suradnica herneho objektu
     * @param y          y-ova suradnica herneho objektu
     * @param objectType typ herneho objektu
     * @author Filip Gregus
     */

    public GameObject(int x, int y, ObjectType objectType) {

        this.position = new Position(x, y);
        this.objectType = objectType;
        this.setImg(objectType.getImageData());
    }

    /**
     * Ziskanie pozicie herneho objektu
     *
     * @return pozicia herneho objektu
     * @author Filip Gregus
     */

    public Position getPosition() {
        return this.position;
    }

    /**
     * Nastavenie pozicie herneho objektu
     *
     * @param position nova pozicia herneho objektu
     * @author Filip Gregus
     */

    public void setPosition(Position position) {
        this.position = position;
        this.img.changePosition(position.getX() * GameController.getBoxSize() + GameController.getIntentX(), position.getY() * GameController.getBoxSize() + GameController.getIntentY());
    }

    /**
     * Ziskanie typu herneho objektu
     *
     * @return typ herneho objektu
     * @author Filip Gregus
     */

    public ObjectType getObjectType() {
        return this.objectType;
    }

    /**
     * Ziskanie obrazku herneho objektu
     *
     * @return obrazok herneho objektu
     * @author Filip Gregus
     */

    public Image getImg() {
        return this.img;
    }

    /**
     * Nastavenie obrazku herneho objektu
     *
     * @param img novy obrazok herneho objektu
     * @author Filip Gregus
     */

    private void setImg(ImageData img) {
        if (this.img == null) {
            this.img = new Image(img, this.position.getX() * GameController.getBoxSize() + GameController.getIntentX(), this.position.getY() * GameController.getBoxSize() + GameController.getIntentY());
            return;
        }

        this.img.changeImage(img);
    }

    /**
     * Nastavenie typu herneho objektu
     *
     * @param objectType novy typ herneho objektu
     * @author Filip Gregus
     */

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
        this.setImg(objectType.getImageData());
    }

}
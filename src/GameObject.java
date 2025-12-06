import fri.shapesge.Image;
import fri.shapesge.ImageData;

/**
 * Trieda reprezentujúca herný objekt v hre
 * @author Filip Greguš
 * @version 1.0
 */

public class GameObject {
    private Position position;
    private ObjectType objectType;
    private final int boxSize;

    private Image img;

    /**
     * Konštruktor triedy GameObject inicializuje herný objekt s danou pozíciou, typom a veľkosťou boxu
     * @param x
     * @param y
     * @param objectType
     * @param boxSize
     */

    public GameObject(int x, int y, ObjectType objectType, int boxSize) {
        this.boxSize = boxSize;
        this.position = new Position(x, y);
        this.objectType = objectType;
        this.setImg(objectType.getImageData());
    }

    /**
     * Získanie pozície herného objektu
     * @return pozícia herného objektu
     * @author Filip Greguš
     */

    public Position getPosition() {
        return position;
    }

    /**
     * Nastavenie pozície herného objektu
     * @param position nová pozícia herného objektu
     * @author Filip Greguš
     */

    public void setPosition(Position position) {
        this.position = position;
        this.img.changePosition(position.getX() * boxSize, position.getY() * boxSize);
    }

    /**
     * Získanie typu herného objektu
     * @return typ herného objektu
     * @author Filip Greguš
     */

    public ObjectType getObjectType() {
        return objectType;
    }

    /**
     * Získanie obrázku herného objektu
     * @return obrázok herného objektu
     * @author Filip Greguš
     */

    public Image getImg() {
        return img;
    }

    /**
     * Nastavenie obrázku herného objektu
     * @param img nový obrázok herného objektu
     * @author Filip Greguš
     */

    private void setImg(ImageData img) {
        if(this.img == null) {
            this.img= new Image(img, this.position.getX() * boxSize, this.position.getY() * boxSize);
            return;
        }

        this.img.changeImage(img);
    }

    /**
     * Nastavenie typu herného objektu
     * @param objectType nový typ herného objektu
     * @author Filip Greguš
     */

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
        this.setImg(objectType.getImageData());
    }

}

import fri.shapesge.Manager;

public class Player extends GameObject {

    private Manager gameManager;

    public Player(int x, int y, Manager gameManager) {
        super(x, y, ObjectType.Player);
        this.gameManager = gameManager;
    }

    public void moveDown(){
        System.out.println("Moving down");
    }

    public void moveUp(){
        System.out.println("Moving up");
    }

    public void moveLeft(){
        System.out.println("Moving left");
    }

    public void moveRight(){
        System.out.println("Moving right");
    }

}

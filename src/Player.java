import fri.shapesge.Manager;

import java.util.ArrayList;

public class Player extends GameObject {

    private ArrayList<GameObject> gameObjects;
    private Manager gameManager;

    public Player(int x, int y, ArrayList<GameObject> gameObjects, Manager gameManager) {
        super(x, y, ObjectType.Player, new fri.shapesge.ImageData("src/icons/player.png"), 50);
        this.gameObjects = gameObjects;
        this.gameManager = gameManager;
        this.gameManager.manageObject(this);
    }

    public void moveDown(){

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

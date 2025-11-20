import fri.shapesge.engine.Game;

import java.util.ArrayList;

public class Movement {
    private GameController gameController;

    public Movement(GameController gameController) {
        this.gameController = gameController;
    }

    public void makeMove(int deltaX, int deltaY, Player player) {
        GameObject playerObj = player.getPlayerObject();
        Position currentPos = playerObj.getPosition();
        Position newPos = new Position(currentPos.getX() + deltaX, currentPos.getY() + deltaY);

        if (!inBoard(newPos, gameController.getRows(), gameController.getColumns())) {
            return;
        }

        if (getObjectAtPosition(newPos, gameController.getGameObjects()) == null || getObjectAtPosition(newPos, gameController.getGameObjects()).getObjectType() == ObjectType.BoxTarget) {
            playerObj.setPosition(newPos);
            gameController.drawBoard();
        } else {
            GameObject objAtNewPos = getObjectAtPosition(newPos, gameController.getGameObjects());
            if (objAtNewPos.getObjectType() == ObjectType.Box ) {
                Position boxNewPos = new Position(newPos.getX() + deltaX, newPos.getY() + deltaY);
                if (inBoard(boxNewPos, gameController.getRows(), gameController.getColumns())) {
                    if(getObjectAtPosition(boxNewPos, gameController.getGameObjects()) == null) {
                        // Move the box
                        objAtNewPos.setPosition(boxNewPos);
                        objAtNewPos.setImg(gameController.getBoxImgData());
                        // Move the player
                        playerObj.setPosition(newPos);
                        gameController.drawBoard();
                    }else if(getObjectAtPosition(boxNewPos, gameController.getGameObjects()).getObjectType() == ObjectType.BoxTarget) {
                        // Move the box onto the target
                        objAtNewPos.setPosition(boxNewPos);
                        objAtNewPos.setObjectType(ObjectType.CorrectBox);
                        objAtNewPos.setImg(gameController.getCorrectBoxImgData());

                        GameObject targetBox = getObjectAtPosition(boxNewPos, gameController.getGameObjects());
                        targetBox.getImg().makeInvisible();
                        gameController.getGameObjects().remove(targetBox);
                        // Move the player
                        playerObj.setPosition(newPos);
                        gameController.drawBoard();
                    }

                }
            } else if(objAtNewPos.getObjectType() == ObjectType.CorrectBox){
                Position boxNewPos = new Position(newPos.getX() + deltaX, newPos.getY() + deltaY);
                if (inBoard(boxNewPos, gameController.getRows(), gameController.getColumns())) {
                    if(getObjectAtPosition(boxNewPos, gameController.getGameObjects()) == null) {
                        // Move the box
                        objAtNewPos.setPosition(boxNewPos);
                        objAtNewPos.setImg(gameController.getBoxImgData());
                        objAtNewPos.setObjectType(ObjectType.Box);
                        gameController.getGameObjects().add( new GameObject(newPos.getX(), newPos.getY(),
                                ObjectType.BoxTarget, gameController.getTargetBoxImgData(),  gameController.getBoxSize()));
                        // Move the player
                        playerObj.setPosition(newPos);
                        playerObj.getImg().makeInvisible();
                        playerObj.getImg().makeVisible();
                        gameController.drawBoard();
                    }else if(getObjectAtPosition(boxNewPos, gameController.getGameObjects()).getObjectType() == ObjectType.BoxTarget) {
                        // Move the box onto the target
                        objAtNewPos.setPosition(boxNewPos);
                        objAtNewPos.setImg(gameController.getCorrectBoxImgData());
                        objAtNewPos.setObjectType(ObjectType.CorrectBox);
                        gameController.getGameObjects().add( new GameObject(newPos.getX(), newPos.getY(),
                                ObjectType.BoxTarget, gameController.getTargetBoxImgData(),  gameController.getBoxSize()));
                        // Move the player
                        playerObj.setPosition(newPos);
                        playerObj.getImg().makeInvisible();
                        playerObj.getImg().makeVisible();
                        gameController.drawBoard();
                    }

                }
            }

        }


    }

    private GameObject getObjectAtPosition(Position position, ArrayList<GameObject> gameObjects) {
        for (GameObject obj : gameObjects) {
            if (obj.getPosition().equals(position)) {
                return obj;
            }
        }
        return null;
    }

    private boolean inBoard(Position position, int rows, int columns) {
        return position.getX() >= 0 && position.getX() < columns &&
                position.getY() >= 0 && position.getY() < rows;
    }

}

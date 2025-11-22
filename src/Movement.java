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

        ArrayList<GameObject> objects = gameController.getGameObjects();
        GameObject destObj = getObjectAtPosition(newPos, objects);

        // Helper to finalize a successful move: update player position and redraw once
        Runnable finalizePlayerMove = () -> {
            playerObj.setPosition(newPos);
            // ensure player's image is refreshed if needed
            playerObj.getImg().makeInvisible();
            playerObj.getImg().makeVisible();
            gameController.drawBoard();
        };

        // If destination empty or is a target, just move player
        if (destObj == null || destObj.getObjectType() == ObjectType.BoxTarget) {
            finalizePlayerMove.run();
            return;
        }

        // If destination has a box (normal or correct), attempt to push
        if (destObj.getObjectType() == ObjectType.Box || destObj.getObjectType() == ObjectType.CorrectBox) {
            Position boxNewPos = new Position(newPos.getX() + deltaX, newPos.getY() + deltaY);
            if (!inBoard(boxNewPos, gameController.getRows(), gameController.getColumns())) {
                return; // can't push out of board
            }

            GameObject boxDestObj = getObjectAtPosition(boxNewPos, objects);
            // Can't push into another box or non-empty cell (except target)
            if (boxDestObj != null && boxDestObj.getObjectType() != ObjectType.BoxTarget) {
                return;
            }

            boolean boxWasOnTarget = destObj.getObjectType() == ObjectType.CorrectBox;
            boolean boxMovingOntoTarget = boxDestObj != null && boxDestObj.getObjectType() == ObjectType.BoxTarget;

            // Move the box object to new position and set its type/image
            destObj.setPosition(boxNewPos);
            if (boxMovingOntoTarget) {
                destObj.setObjectType(ObjectType.CorrectBox);
                destObj.setImg(gameController.getCorrectBoxImgData());

                // remove the target object now covered by the correct box
                objects.remove(boxDestObj);
                boxDestObj.getImg().makeInvisible();
            } else {
                // moving onto plain floor
                destObj.setObjectType(ObjectType.Box);
                destObj.setImg(gameController.getBoxImgData());
            }

            // If the box moved off a target, restore a BoxTarget at the old box position
            if (boxWasOnTarget) {
                GameObject restoredTarget = new GameObject(newPos.getX(), newPos.getY(),
                        ObjectType.BoxTarget, gameController.getTargetBoxImgData(), gameController.getBoxSize());
                objects.add(restoredTarget);
            }

            // Finally move the player into the box's old position
            finalizePlayerMove.run();
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

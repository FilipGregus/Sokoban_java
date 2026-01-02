import java.util.ArrayList;

/**
 * Trieda Movement riadi pohyb hráča a interakciu s objektmi na hernej ploche
 *
 * @author Filip Greguš
 * @version 1.0
 */

public class Movement {
    private final GameController gameController;
    private final SoundManager soundManager;

    /**
     * Konštruktor triedy Movement inicializuje pohybový manažér s odkazom na herný kontrolér
     *
     * @param gameController inštancia herného kontroléra
     * @author Filip Greguš
     */

    public Movement(GameController gameController) {
        this.gameController = gameController;
        this.soundManager = SoundManager.getInstance();
    }

    /**
     * Metóda na vykonanie pohybu hráča
     *
     * @param deltaX zmena v x-ovej súradnici
     * @param deltaY zmena v y-ovej súradnici
     * @param player inštancia hráča
     * @author Filip Greguš
     */

    public void makeMove(int deltaX, int deltaY, Player player) {
        GameObject playerObj = player.getPlayerObject();
        Position currentPos = playerObj.getPosition();
        Position newPos = new Position(currentPos.getX() + deltaX, currentPos.getY() + deltaY);

        if (!this.inBoard(newPos, this.gameController.getActualLevelRows(), this.gameController.getActualLevelColumns())) {
            return;
        }

        ArrayList<GameObject> objects = this.gameController.getGameObjects();
        GameObject destObj = this.getObjectAtPosition(newPos, objects);


        // Pokiaľ je cielova pozícia prázdna môže sa posunúť
        if (destObj == null || destObj.getObjectType() == ObjectType.BOX_TARGET) {
            this.finalizePlayerMove(newPos, playerObj);
            return;
        }

        // Pokiaľ je na cielovej pozícii box, môže ho posunúť
        if (destObj.getObjectType() == ObjectType.BOX || destObj.getObjectType() == ObjectType.CORRECT_BOX) {
            Position boxNewPos = new Position(newPos.getX() + deltaX, newPos.getY() + deltaY);
            if (!this.inBoard(boxNewPos, this.gameController.getActualLevelRows(), this.gameController.getActualLevelColumns())) {
                return;
            }

            GameObject boxDestObj = this.getObjectAtPosition(boxNewPos, objects);
            // Môže posunúť iba 1 box
            if (boxDestObj != null && boxDestObj.getObjectType() != ObjectType.BOX_TARGET) {
                return;
            }

            boolean boxWasOnTarget = destObj.getObjectType() == ObjectType.CORRECT_BOX;
            boolean boxMovingOntoTarget = boxDestObj != null && boxDestObj.getObjectType() == ObjectType.BOX_TARGET;

            // Posun boxu na novú korektnú pozíciu
            destObj.setPosition(boxNewPos);
            if (boxMovingOntoTarget) {
                destObj.setObjectType(ObjectType.CORRECT_BOX);

                objects.remove(boxDestObj);
                boxDestObj.getImg().makeInvisible();
            } else {
                // Posun na prázdne miesto
                destObj.setObjectType(ObjectType.BOX);
            }

            // Obnov cieľovú pozíciu, ak bol box na nej
            if (boxWasOnTarget) {
                GameObject restoredTarget = new GameObject(newPos.getX(), newPos.getY(),
                        ObjectType.BOX_TARGET);
                restoredTarget.getImg().makeVisible();
                objects.add(restoredTarget);
            }

            // Nakoniec posun hráča
            this.finalizePlayerMove(newPos, playerObj);
        }
    }

    private void finalizePlayerMove(Position newPos, GameObject playerObj) {
        this.soundManager.playStepSound();
        playerObj.setPosition(newPos);
        // Refresh obrázok hráča
        playerObj.getImg().makeInvisible();
        playerObj.getImg().makeVisible();
        this.gameController.drawGround();
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

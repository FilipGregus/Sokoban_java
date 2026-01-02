import java.util.ArrayList;

/**
 * Trieda Movement riadi pohyb hraca a interakciu s objektmi na hernej ploche
 *
 * @author Filip Gregus
 * @version 1.0
 */

public class Movement {
    private final GameController gameController;
    private final SoundManager soundManager;

    /**
     * Konstruktor triedy Movement inicializuje pohybovy manazer s odkazom na herny kontroler
     *
     * @param gameController instancia herneho kontrolera
     * @author Filip Gregus
     */

    public Movement(GameController gameController) {
        this.gameController = gameController;
        this.soundManager = SoundManager.getInstance();
    }

    /**
     * Metoda na vykonanie pohybu hraca
     *
     * @param deltaX zmena v x-ovej suradnici
     * @param deltaY zmena v y-ovej suradnici
     * @param player instancia hraca
     * @author Filip Gregus
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


        // Pokial je cielova pozicia prazdna moze sa posunut
        if (destObj == null || destObj.getObjectType() == ObjectType.BOX_TARGET) {
            this.finalizePlayerMove(newPos, playerObj);
            return;
        }

        // Pokial je na cielovej pozicii box, moze ho posunut
        if (destObj.getObjectType() == ObjectType.BOX || destObj.getObjectType() == ObjectType.CORRECT_BOX) {
            Position boxNewPos = new Position(newPos.getX() + deltaX, newPos.getY() + deltaY);
            if (!this.inBoard(boxNewPos, this.gameController.getActualLevelRows(), this.gameController.getActualLevelColumns())) {
                return;
            }

            GameObject boxDestObj = this.getObjectAtPosition(boxNewPos, objects);
            // Moze posunut iba 1 box
            if (boxDestObj != null && boxDestObj.getObjectType() != ObjectType.BOX_TARGET) {
                return;
            }

            boolean boxWasOnTarget = destObj.getObjectType() == ObjectType.CORRECT_BOX;
            boolean boxMovingOntoTarget = boxDestObj != null && boxDestObj.getObjectType() == ObjectType.BOX_TARGET;

            // Posun boxu na novu korektnu poziciu
            destObj.setPosition(boxNewPos);
            if (boxMovingOntoTarget) {
                destObj.setObjectType(ObjectType.CORRECT_BOX);

                objects.remove(boxDestObj);
                boxDestObj.getImg().makeInvisible();
            } else {
                // Posun na prazdne miesto
                destObj.setObjectType(ObjectType.BOX);
            }

            // Obnov cielovu poziciu, ak bol box na nej
            if (boxWasOnTarget) {
                GameObject restoredTarget = new GameObject(newPos.getX(), newPos.getY(),
                        ObjectType.BOX_TARGET);
                restoredTarget.getImg().makeVisible();
                objects.add(restoredTarget);
            }

            // Nakoniec posun hraca
            this.finalizePlayerMove(newPos, playerObj);
        }
    }

    private void finalizePlayerMove(Position newPos, GameObject playerObj) {
        this.soundManager.playStepSound();
        playerObj.setPosition(newPos);
        // Refresh obrazok hraca
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
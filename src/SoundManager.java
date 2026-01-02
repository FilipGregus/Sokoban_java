import fri.shapesge.SoundEffect;

/**
 * Singleton trieda na spravu zvukovych efektov v hre.
 *
 * @author Filip Gregus
 * @version 1.0
 */

public class SoundManager {
    private static SoundManager instance;
    private final SoundEffect stepSound1 = new SoundEffect("src/sounds/st1.wav");
    private final SoundEffect stepSound2 = new SoundEffect("src/sounds/st2.wav");

    private SoundManager() {
    }

    /**
     * Ziskanie instancie SoundManageru.
     *
     * @return instancia SoundManageru
     * @author Filip Gregus
     */

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    /**
     * Prehravanie nahodneho zvukoveho efektu kroku.
     *
     * @author Filip Gregus
     */

    public void playStepSound() {
        double random = Math.random();

        if (!this.stepSound1.isPlaying() && !this.stepSound2.isPlaying()) {
            if (random < 0.5) {
                this.stepSound1.play();
            } else {
                this.stepSound2.play();
            }
        }
    }
}
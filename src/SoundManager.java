import fri.shapesge.SoundEffect;

public class SoundManager {
    private static SoundManager instance;
    private final SoundEffect stepSound1 = new SoundEffect("src/sounds/st1.wav");
    private final SoundEffect stepSound2 = new SoundEffect("src/sounds/st2.wav");

    private SoundManager() {
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void PlayStepSound() {
        double random = Math.random();

        if (!stepSound1.isPlaying() && !stepSound2.isPlaying()) {
            if (random < 0.5) {
                stepSound1.play();
            } else {
                stepSound2.play();
            }
        }
    }
}

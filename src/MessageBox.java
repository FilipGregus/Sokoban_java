import javax.swing.JOptionPane;

/**
 * Trieda na zobrazovanie dialogovych okien
 *
 * @author Filip Gregus
 * @version 1.0
 */

public class MessageBox {

    /**
     * Zobrazi dialogove okno po dokonceni urovne
     *
     * @return int - vrati index zvolenej moznosti
     * @author Filip Gregus
     */

    public static int showNextLevelDialog() {
        String[] options = {"Pokracovat", "Ukoncit"};
        return JOptionPane.showOptionDialog(
                null,
                "Gratulujem! Chces pokracovat na dalsiu uroven?",
                "Uroven dokoncena",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );
    }

    /**
     * Zobrazi dialogove okno po dokonceni vsetkych urovni
     *
     * @return int - vrati index zvolenej moznosti
     * @author Filip Gregus
     */

    public static int showRestartCompleteDialog() {
        String[] options = {"Restartovat", "Ukoncit"};
        return JOptionPane.showOptionDialog(
                null,
                "Vyhral si vsetky levely! Chces pokracovat odznova?",
                "Gratulujem!",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );
    }

    /**
     * Zobrazi dialogove okno na potvrdenie restartu urovne
     *
     * @return int - vrati index zvolenej moznosti
     * @author Filip Gregus
     */

    public static int showRestartDialog() {
        String[] options = {"Restartovat", "Ukoncit"};
        return JOptionPane.showOptionDialog(
                null,
                "Naozaj chces restartovat uroven?",
                "Restartovat uroven",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]
        );
    }
}
import javax.swing.JOptionPane;

/**
 * Trieda na zobrazovanie dialógových okien
 * @author Filip Greguš
 * @version 1.0
 */

public class MessageBox {

    /**
     * Zobrazí dialógové okno po dokončení úrovne
     * @author Filip Greguš
     * @return int - vráti index zvolenej možnosti
     */

    public static int showNextLevelDialog() {
        String[] options = {"Pokračovať", "Ukončiť"};
        return JOptionPane.showOptionDialog(
                null,
                "Gratulujem! Chceš pokračovať na ďalšiu úroveň?",
                "Úroveň dokončená",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );
    }

    /**
     * Zobrazí dialógové okno po dokončení všetkých úrovní
     * @author Filip Greguš
     * @return int - vráti index zvolenej možnosti
     */

    public static int showRestartCompleteDialog() {
        String[] options = {"Reštartovať", "Ukončiť"};
        return JOptionPane.showOptionDialog(
                null,
                "Vyhral si všetky levely! Chceš pokračovať odznova?",
                "Gratulujem!",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );
    }

    /**
     * Zobrazí dialógové okno na potvrdenie reštartu úrovne
     * @author Filip Greguš
     * @return int - vráti index zvolenej možnosti
     */

    public static int showRestartDialog() {
        String[] options = {"Reštartovať", "Ukončiť"};
        return JOptionPane.showOptionDialog(
                null,
                "Naozaj chceš reštartovať úroveň?",
                "Reštartovať úroveň",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]
        );
    }
}

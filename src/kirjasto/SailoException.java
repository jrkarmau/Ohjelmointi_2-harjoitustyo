package kirjasto;

/**
 * poikkeusluokka tietorakenteesta aiheutuville poikkeuksille
 * @author jrkarmau
 * @version 21.4.2021
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;
    
    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksen viesti
     * @param viesti poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}

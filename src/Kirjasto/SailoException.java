package Kirjasto;


/**
 * Poikkeusluokka tietorakenteessa aiheutuville poikkeuksille
 * @author Jovan Karmakka (jrkarmau)
 * @version 22.2.2021
 *
 */
public class SailoException extends Exception {
        private static final long serialVersionUID = 1L;
        
        
        /**
         * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa käytettävä viesti
         * @param viesti virheviesti
         */
        public SailoException(String viesti) {
            super(viesti);
        }
    }

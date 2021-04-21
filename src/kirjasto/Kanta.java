package kirjasto;

/**
 * Apuluokka kirjaston käyttöä varten
 * @author jrkarmau
 * @version 21.4.2021
 */
public class Kanta {

    /**
     * Palauttaa satunnaisen luvun annetulta väliltä
     * @param ala luvun pienin numero
     * @param yla luvun ylin numero
     * @return satunnainen luku
     */
    public static int rand(int ala, int yla) {
        double n = (yla-ala) * Math.random() + ala;
        return (int)Math.round(n);
    }
}

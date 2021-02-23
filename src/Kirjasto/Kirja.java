package Kirjasto;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Kirja-luokka
 * @author Jovan Karmakka (jrkarmau)
 * @version 22.2.2021
 *
 */
public class Kirja {    
    
    private int kirjanID;
    private String nimi        = "";
    private String kirjailija  = "";
    private String Kieli       = "";
    private String julkaistu   = "";
    private String kustantaja  = "";
    private String ISBN        = "";
    private String sivumaara   = "";
    private String genre       = "";
    
    private static int seuraavaID = 1;
    
    
    /**
     * Pääohjelma kirja-luokan testaamiseksi
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
        Kirja pohjantahdenAlla = new Kirja();
        Kirja pohjantahdenAlla2 = new Kirja();
        
        pohjantahdenAlla.rekisteroi();
        pohjantahdenAlla2.rekisteroi();
        
        pohjantahdenAlla.tulosta(System.out);    
        pohjantahdenAlla.taytaKirjaTiedoilla();        
        pohjantahdenAlla.tulosta(System.out);
        
        pohjantahdenAlla2.tulosta(System.out);    
        pohjantahdenAlla2.taytaKirjaTiedoilla();        
        pohjantahdenAlla2.tulosta(System.out);        
    }
    
    
    /**
     * täyttää kirjan tiedot testejä varten
     */
    public void taytaKirjaTiedoilla() {
        nimi        = "Taru Sormusten Herrasta";
        kirjailija  = "";
        Kieli       = "";
        julkaistu   = "";
        kustantaja  = "";
        ISBN        = "";
        sivumaara   = "";
        genre       = "";
    }
    

    /**
     * Palauttaa kirjan ID numeron
     * @return kirjan ID numero
     * @example
     * <pre name="test">
     *      Kirja kirja = new Kirja();
     *      kirja.getID() === 0;
     *      kirja.rekisteroi();
     *      Kirja kirja2 = new Kirja();
     *      kirja2.rekisteroi();           
     * </pre>
     */
    public int getID() {
        return kirjanID;
        // TODO: testit loppuun
    }
    

    /**
     * asettaa kirjalle uuden ID:n
     * @return kirjan ID
     */
    public int rekisteroi() {
        kirjanID = seuraavaID;
        seuraavaID++;
        return kirjanID;
        
    }
    

    /**
     * Apumetodi tulostukseen
     * @param os output stream
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
        }
    
    
    /**
     * tulostaa jäsenen tiedot
     * @param out tietovirta johon tulsotetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", kirjanID) + " " + nimi + " ");
        // TODO: lisää tähän muut tulostukset
    }
}

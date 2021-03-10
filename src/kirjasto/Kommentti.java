package kirjasto;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * - tietää kommentin kentät (otsikko, pvm..)  
 * - osaa muuttaa 4|kirjanmerkki3|tällä sivul..  
 *   merkkijonon kommentin tiedoiksi           
 * - osaa antaa merkkijonona i:n kentän tiedot      
 * - osaa laittaa merkkijonon i:neksi kentäksi
 * 
 * @author jrkarmau
 * @version 10.3.2021
 */
public class Kommentti {

    private String otsikko = "";
    private String teksti = "";
    private int kommenttiID;
    private int kirjaID;
    private static int seuraavaID = 1;
    
    
    /**
     * Pääohjelma kommenttiluokan testaamiseksi
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kommentti kom1 = new Kommentti();
        kom1.taytaKommentinTiedot(1);
        kom1.tulosta(System.out);
    }
    
    
    /**
     * antaa kommentille seuraavan kommentti ID:n
     * @return kommentin iD-numero
     * @example
     * <pre name="test">
     *      Kommentti kom1 = new Kommentti();
     *      kom1.getKommentinID() === 0;
     *      kom1.rekisteroi();
     *      Kommentti kom2 = new Kommentti();
     *      kom2.rekisteroi();
     *      int n1 = kom1.getKommentinID();
     *      int n2 = kom2.getKommentinID();
     *      n1 === n2-1;  
     * </pre>
     */
    public int rekisteroi() {
        this.kommenttiID = seuraavaID;
        seuraavaID++;
        return this.kirjaID;
    }
    
    
    /**
     * Palauttaa mille kirjalle kommentti kuuluu
     * @return kirjan ID
     */
    public int getKirjanID() {
        return kirjaID;
    }
    
    
    /**
     * Palauttaa kommentin ID:n
     * @return kommentin ID
     */
    public int getKommentinID() {
        return kommenttiID;
    }
    
    
    /**
     * Tulostaa kommentin tiedot tietovirtaan
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("kommentin otsikko: " + otsikko + "\nkommentin sisältö: " + teksti);
    }
    
    
    /**
     * tulostaa annettuun tietovirtaan
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Täyttää kommentin sisällön 
     * @param id kirjan ID-numero
     */
    public void taytaKommentinTiedot(int id) {
        this.kirjaID = id;
        otsikko = "Otsikon tiedot " + Kanta.rand(1000,9999);
        teksti = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " + Kanta.rand(1000,9999) ;
    }


    /**
     * Alustaa uuden kommentin
     */
    public Kommentti() {
        //
    }
    
    
    /**
     * Alustaa tietyn kirjan kommentin
     * @param kirjaID kirja jolle kommentti alustetaan
     */
    public Kommentti(int kirjaID) {
        this.kirjaID = kirjaID;
    }
}

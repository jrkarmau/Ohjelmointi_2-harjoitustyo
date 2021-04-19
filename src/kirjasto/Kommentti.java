package kirjasto;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

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
public class Kommentti implements Cloneable {

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
     * @param rivi rivi josta kommentin tiedot otetaan
     * @example
     * <pre name="test">
     *  Kommentti kommentti = new Kommentti();
     *  kommentti.parse("4  | 2 |  otsikko212  |  tekstiätekstiä  ");
     *  kommentti.getKommentinID() === 4;
     *  kommentti.toString().startsWith("4|2|otsikko212|tekstiätekstiä|") === true;
     *  
     *  kommentti.rekisteroi();
     *  int n = kommentti.getKommentinID();
     *  kommentti.parse("" + (n+20));
     *  kommentti.rekisteroi();
     *  kommentti.getKommentinID() === n+20+1;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setID(Mjonot.erota(sb, '|', getKommentinID()));
        kirjaID  = Mjonot.erota(sb, '|', getKirjanID());
        otsikko  = Mjonot.erota(sb, '|', otsikko);
        teksti   = Mjonot.erota(sb, '|', teksti);  

    }
    
    
    /**
     * asettaa kommentille kirjan id
     * @param id kirjan id joka asetetaan
     */
    public void setKirjanID(int id) {
        kirjaID = id;
    }
    
    
    /**
     * tekee identtisen kopion kommentista
     * @return kloonattu kommentti
     */
    @Override
    public Kommentti clone() throws CloneNotSupportedException {
        Kommentti uusi;
        uusi = (Kommentti) super.clone();
        return uusi;
    }
    
    
    /**
     * Asettaa kommentille id:n ja samalla varmistaa että seuraava id on aina suurempi kuin aikaisempi id 
     * @param id asetettava id-numero
     */
    private void setID(int id) {
        kommenttiID = id;
        if (kommenttiID >= seuraavaID) seuraavaID = kommenttiID + 1;   
    } 
    
    
    /**
     * Muuttaa kommentin tiedot tiedostoon kirjoitettavaan muotoon
     */
    @Override
    public String toString() {
        return "" +
               getKommentinID() + "|" +
               getKirjanID()    + "|" +
               otsikko          + "|" +
               teksti           + "|";              
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
     * Palauttaa kommentin otsikon
     * @return kommentin otsikko
     */
    public String getOtsikko() {
        return otsikko;
    }
    
    
    /**
     * Palauttaa kommentin sisällön
     * @return kommentin sisältö
     */
    public String getTeksti() {
        return teksti;
    }
    
    
    /**
     * Asettaa uuden otsikon
     * @param s otsikon sisältö
     */
    public void setOtsikko(String s) {
        otsikko = s;
    }
    
    
    /**
     * Asettaa uuden tekstin kommenttiin
     * @param s tekstin sisältö
     */
    public void setTeksti(String s) {
        teksti = s;
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

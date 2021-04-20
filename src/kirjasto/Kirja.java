package kirjasto;

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.Year;
import java.util.regex.Pattern;
import fi.jyu.mit.ohj2.Mjonot;

/**
 *  Kirja luokka Kirjasto sovellusta varten
 * - tietää kirjan  kentät (nimi, kirjailija, kieli, genre jne.)
 * - osaa tarkistaa tietyn kentän oikeellisuuden      
 *  (ISBN, vuosi)                                     
 * - osaa muuttaa 1|Harry potter|1854|.. -merkkijonon 
 *  kirjan tiedoiksi                                 
 * - osaa antaa merkkijonona i:n kentän tiedot        
 * - osaa laittaa merkkijonon i:neksi kentäksi
 * 
 * @author jrkarmau
 * @version 8.3.2021
 *
 */
public class Kirja implements Cloneable {
    
    private int    kirjaID;
    private String kirjanNimi  = "";
    private String kirjailija  = "";  
    private String kieli       = "";
    private String kustantaja  = "";
    private String julkaistu   = "";
    private String isbn        = "";
    private String sivumaara   = "";
    private String genre       = "";
    private static int seuraavaID = 1;
    
    
    /**
     * Pääohjelma kirja-luokan testaamiseksi
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
        Kirja seitsemänVeljesta = new Kirja();       
        Kirja rautatie = new Kirja();
        
        seitsemänVeljesta.rekisteroi();
        rautatie.rekisteroi();
        
        seitsemänVeljesta.tulosta(System.out);
        seitsemänVeljesta.taytaKirjanTiedot();
        seitsemänVeljesta.tulosta(System.out);
        
        rautatie.tulosta(System.out);
        rautatie.taytaKirjanTiedot();
        rautatie.tulosta(System.out);
        }
    
    
    /**
     * Alustaa uuden kirjan
     */
    public Kirja() {
        //
    }
    
    
    /**
     * tekee identtisen kopion kirjasta.
     * @return kloonattu jäsen
     */
    @Override
    public Kirja clone() throws CloneNotSupportedException {
        Kirja uusi;
        uusi = (Kirja) super.clone();
        return uusi;
    }
    
   
    /**
     * Täyttää kirjan tiedot testitiedoilla
     */
    public void taytaKirjanTiedot() {
        this.kirjanNimi = "kirja" + Kanta.rand(1000,9999);
        this.kirjailija = "kirjailija";
        this.kieli = "kirjan kieli";
        this.kustantaja = "kustantaja";
        this.julkaistu = "1999";
        this.sivumaara = "100";
        this.isbn = "000-0000-00-0";
        this.genre = "Genre";
    }
    
    
    /**
     * @param rivi rivi josta jäsenen tiedot otetaan¨
     * @example
     * <pre name="test">
     *  Kirja kirja = new Kirja();
     *  kirja.parse("2  |  Taru sormusten herrasta  |  J R Tolkien  ");
     *  kirja.getKirjanID() === 2;
     *  kirja.toString().startsWith("2|Taru sormusten herrasta|J R Tolkien|") === true;
     *  
     *  kirja.rekisteroi();
     *  int n = kirja.getKirjanID();
     *  kirja.parse("" + (n+20));
     *  kirja.rekisteroi();
     *  kirja.getKirjanID() === n+20+1;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setID(Mjonot.erota(sb, '|', getKirjanID()));
         kirjanNimi  = Mjonot.erota(sb, '|', kirjanNimi);
         kirjailija  = Mjonot.erota(sb, '|', kirjailija);  
         kieli       = Mjonot.erota(sb, '|', kieli);
         kustantaja  = Mjonot.erota(sb, '|', kustantaja);
         julkaistu   = Mjonot.erota(sb, '|', julkaistu);
         isbn        = Mjonot.erota(sb, '|', isbn);
         sivumaara   = Mjonot.erota(sb, '|', sivumaara);
         genre       = Mjonot.erota(sb, '|', genre);
    }
    
    
    /**
     * Asettaa kirjalle id:n ja samalla varmistaa että seuraava id on aina suurempi kuin aikaisempi id
     * @param id asetettava id-numero
     */
    private void setID(int id) {
        kirjaID = id;
        if (kirjaID >= seuraavaID) seuraavaID = kirjaID + 1;
    }
  

    /**
     * antaa kirjalle seuraavan ID:n
     * @return kirjan id numero
     * @example
     * <pre name="test">
     *      Kirja kirja1 = new Kirja();
     *      kirja1.getKirjanID() === 0;
     *      kirja1.rekisteroi();
     *      Kirja kirja2 = new Kirja();
     *      kirja2.rekisteroi();
     *      int n1 = kirja1.getKirjanID();
     *      int n2 = kirja2.getKirjanID();
     *      n1 === n2-1;  
     * </pre>
     */
    public int rekisteroi() {
        this.kirjaID = seuraavaID;
        seuraavaID++;
        return this.kirjaID;
    }
    
    
    
    /**
     * Muuttaa kirjan tiedot tiedostoon kirjoitettavaan muotoon
     */
    @Override
    public String toString() {
        return "" +
               getKirjanID() + "|" +
               kirjanNimi    + "|" +
               kirjailija    + "|" +
               kieli         + "|" +
               kustantaja    + "|" +
               julkaistu     + "|" +
               isbn          + "|" +
               sivumaara     + "|" +
               genre         + "|";         
    }


    /**
     * Palauttaa kirjan id numeron
     * @return kirjan id
     */
    public int getKirjanID() {
        return this.kirjaID;
    }
    
    
    /**
     * Tulostaa kirjan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", kirjaID));
        out.println(kirjanNimi + " " + kirjailija );
        out.println(kieli + " " + kustantaja);
        out.println(julkaistu + " " + isbn);
        out.println(sivumaara + " " + genre);        
    }
    
    
    /**
     * tulostaa kirjan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Palauttaa kirjan nimen
     * @return kirjan nimi
     */
    public String getNimi() {
        return kirjanNimi;
    }
    
    
    /**
     * asettaa kentän tiedot sen paikan perusteella
     * @param i minkä kentän tiedot lisätään
     * @param s kentän sisältö
     * @return virheen sisältö, null jos kaikki ok
     */
    public String setKentta(int i, String s) {
        String ts = s.trim();
        switch ( i ) {
        case 0: kirjanNimi = ts; return null;
        case 1: kirjailija = ts; return null;
        case 2: kieli = ts; return null;
        case 3: kustantaja = ts; return null;
        case 4: return tarkistaVuosi(ts);
        case 5: isbn = ts; return null;
        case 6: return tarkistaSivut(ts);
        case 7: genre = ts; return null;
        default: return "kenttää ei ole olemassa";
        }
    }
    

    private String tarkistaSivut(String ts) {
        if ("".equals(ts) || !Pattern.matches("^[0-9]*$", ts)) return "Sivumäärä ei ole oikeaa muotoa!";
        if (Integer.parseInt(ts) < 0) return "Sivumäärä ei voi olla negatiivinen";
        sivumaara = ts;
        return null;
    }


    private String tarkistaVuosi(String ts) {
        Year year = Year.now();
        if ("".equals(ts) || !Pattern.matches("^[0-9]*$", ts) ) return "Anna vuosiluku muodossa vvvv!";
        if (Integer.parseInt(ts) < 0 || Integer.parseInt(ts) > year.getValue()) return "Vuosiluku on väärin";
        julkaistu = ts;
        return null;
    }
    
    
    /**
     * Palauttaa kirjan kentän sen paikan perusteella
     * @param i monesko kirjan kenttä
     * @return kirjan kentän tiedot
     */
    public String getKentta(int i) {
    switch ( i ) {
    case 0:  return "" + kirjanNimi;
    case 1:  return "" + kirjailija;
    case 2:  return "" + kieli;
    case 3:  return "" + kustantaja;
    case 4:  return "" + julkaistu;
    case 5:  return "" + isbn;
    case 6:  return "" + sivumaara;
    case 7:  return "" + genre;
    default: return "";
    }
}
}

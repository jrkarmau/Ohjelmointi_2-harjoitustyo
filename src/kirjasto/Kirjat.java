package kirjasto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import fi.jyu.mit.ohj2.Mjonot;
import fi.jyu.mit.ohj2.WildChars;

/**
 * - pitää yllä varsinaista kirjarekisteriä, eli osaa 
 *   lisätä ja poistaa kirjan                         
 * - lukee ja kirjoittaa kirjat tiedostoon           
 * - osaa etsiä ja lajitella 
 * 
 * @author jrkarmau
 * @version 22.4.2021
 */
public class Kirjat {

    private static final int MAX_KIRJOJA = 5;
    private int lkm = 0;
    private Kirja[] alkiot;
    private String tiedostonPerusNimi = "";
    private boolean muutettu = false;

    /**
     * Pääohjelma kirjat-luokan metodien käyttämiseksi
     * @param args ei käytössä
     */
    public static void main(String[] args) {

        Kirjat kirjat = new Kirjat();

        try {
            kirjat.lueTiedostosta("kirjasto");
        } catch (SailoException ex) {
            System.err.println(ex.getMessage());
        }

        Kirja kirja1 = new Kirja();
        kirja1.rekisteroi();
        kirja1.taytaKirjanTiedot();

        Kirja kirja2 = new Kirja();
        kirja2.rekisteroi();
        kirja2.taytaKirjanTiedot();

        try {
            kirjat.lisaa(kirja1);
            kirjat.lisaa(kirja2);

            System.out.println(
                    "=============================== Kirjat testi ===============================");

            for (int i = 0; i < kirjat.getLkm(); i++) {
                Kirja kirja = kirjat.anna(i);
                System.out.println("kirjan indeksi " + i);
                kirja.tulosta(System.out);
            }
        } catch (SailoException e) {
            System.err.println(e.getMessage());
        }

        try {
            kirjat.tallenna("kirjasto");
        } catch (SailoException e) {
            e.printStackTrace();
        }

    }
    
    
    /**
     * Poistaa kirjan taulukosta id numeron perusteella
     * @param id kirjan ID numero
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Kirjat kirjat = new Kirjat(); 
     * Kirja kir1 = new Kirja(), kir2 = new Kirja(), kir3 = new Kirja(); 
     * kir1.rekisteroi(); kir2.rekisteroi(); kir3.rekisteroi(); 
     * int id1 = kir1.getKirjanID(); 
     * kirjat.lisaa(kir1); kirjat.lisaa(kir2); kirjat.lisaa(kir3); 
     * kirjat.getLkm() === 3;
     * kirjat.poista(id1); 
     * kirjat.getLkm() === 2;
     * kirjat.etsiId(id1) === -1;
     * kirjat.poista(id1+1); 
     * kirjat.getLkm() === 1;
     * kirjat.etsiId(id1+1) === -1;
     * kirjat.etsiId(id1+2) === 0;
     * </pre> 
     */
    public void poista(int id) {
        int ind = etsiId(id); 
        if (ind < 0) return; 
        lkm--; 
        for (int i = ind; i < lkm; i++) 
            alkiot[i] = alkiot[i + 1]; 
        alkiot[lkm] = null; 
        muutettu = true; 
    }
    
    
    /**
     * etsii ja palauttaa kirjan indeksin taulukossa
     * @param id haettavan kirjan id
     * @return -1 jos ei löydy muuten kirjan indeksi taulukossa
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Kirjat kirjat = new Kirjat(); 
     * Kirja kir1 = new Kirja(), kir2 = new Kirja(), kir3 = new Kirja(); 
     * kir1.rekisteroi(); kir2.rekisteroi(); kir3.rekisteroi(); 
     * int id1 = kir1.getKirjanID(); 
     * kirjat.lisaa(kir1); kirjat.lisaa(kir2); kirjat.lisaa(kir3); 
     * kirjat.etsiId(5) === -1;
     * kirjat.etsiId(12) === -1;
     * kirjat.etsiId(-1) === -1;
     * kirjat.etsiId(id1) === 0;
     * kirjat.etsiId(id1+2) === 2;
     * kirjat.etsiId(id1+1) === 1;
     * </pre> 
     */
    public int etsiId(int id) {
        for (int i = 0; i < lkm; i++)
            if (id == alkiot[i].getKirjanID())
                return i;
        return -1;
    }

    
    /**
     * Etsii Kirjoista hakuehdot täyttävät kirjat
     * @param hakuehto käyttäjän hakuehto
     * @param hakukenttaNro minkä tiedon perusteella etsitään
     * @return lista kirjoista jotka täyttävät hakuehdot
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException
     * #import java.util.ArrayList; 
     *   Kirjat kirjat = new Kirjat(); 
     *   Kirja kirja1 = new Kirja(); kirja1.parse("1|taru sormusten herrasta |essi esimerkki    |englanti|1993|otava|123-456-0-33333-2| kauhu|"); 
     *   Kirja kirja2 = new Kirja(); kirja2.parse("2|Täällä pohjantähden alla|jaakko jaakonpoika|suomi   |2004|WSOY|343-467-0-23456-1 | fantasia|"); 
     *   Kirja kirja3 = new Kirja(); kirja3.parse("3|Tuntematon sotilas      |kissa kirjoittaja |suomi   |1985|WSOY|752-123-2-34658-5 | kaunokirjallisuus|"); 
     *   Kirja kirja4 = new Kirja(); kirja4.parse("4|Rautatie                |Juhani Aho|"); 
     *   Kirja kirja5 = new Kirja(); kirja5.parse("5|Harry Potter            |Rowling           |suomi|"); 
     *   kirjat.lisaa(kirja1); kirjat.lisaa(kirja2); kirjat.lisaa(kirja3); kirjat.lisaa(kirja4); kirjat.lisaa(kirja5);
     *   ArrayList<Kirja> loytyneet = new  ArrayList<Kirja>();  
     *   loytyneet = kirjat.etsi("*rr*",0);  
     *   loytyneet.size() === 2;  
     *   loytyneet.get(0) == kirja1 === true;  
     *   loytyneet.get(1) == kirja5 === true;  
     *   loytyneet = kirjat.etsi("*suo*",2);  
     *   loytyneet.size() === 3;  
     *   loytyneet.get(0) == kirja2 === true;  
     *   loytyneet.get(1) == kirja3 === true; 
     *   loytyneet.get(2) == kirja5 === true; 
     *   loytyneet = kirjat.etsi(null,-1);  
     *   loytyneet.size() === 5;  
     * </pre>
     */
    public ArrayList<Kirja> etsi(String hakuehto, int hakukenttaNro) {
        String ehto = "*"; 
        if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto;
        ArrayList<Kirja> loytyneet = new ArrayList<>();
        for (int i = 0; i < lkm; i++) {
            if (WildChars.onkoSamat(alkiot[i].getKentta(hakukenttaNro), ehto)) {
                loytyneet.add(alkiot[i]);
            }
        }
        return loytyneet;
    }

    
    /**
     * Lukee kirjaston tiedostosta
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Kirjat kirjat = new Kirjat();
     *  Kirja kirja1 = new Kirja(); kirja1.taytaKirjanTiedot();
     *  Kirja kirja2 = new Kirja(); kirja2.taytaKirjanTiedot();
     *  Kirja kirja3 = new Kirja(); kirja3.taytaKirjanTiedot(); 
     *  Kirja kirja4 = new Kirja(); kirja4.taytaKirjanTiedot(); 
     *  Kirja kirja5 = new Kirja(); kirja5.taytaKirjanTiedot(); 
     *  String tiedNimi = "testikirjat.dat";
     *  File ftied = new File(tiedNimi);
     *  ftied.delete();
     *  kirjat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  kirjat.lisaa(kirja1);
     *  kirjat.lisaa(kirja2);
     *  kirjat.lisaa(kirja3);
     *  kirjat.lisaa(kirja4);
     *  kirjat.lisaa(kirja5);
     *  kirjat.tallenna();
     *  kirjat = new Kirjat();
     *  kirjat.lueTiedostosta(tiedNimi);
     *  kirjat.anna(0).toString() === kirja1.toString();
     *  kirjat.anna(2).toString() === kirja3.toString();
     *  kirjat.anna(4).toString() === kirja5.toString();
     *  kirjat.tallenna();
     *  kirjat.lueTiedostosta(tiedNimi);
     *  ftied.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {     
        
        setTiedostonPerusNimi(hakemisto); 
        try (Scanner fi = new Scanner(new FileInputStream(getTiedostonPerusNimi()))) {
            while (fi.hasNext()) {
                String s = "";
                s = fi.nextLine();
                Kirja kirja = new Kirja();
                kirja.parse(s);
                lisaa(kirja);
            }
            muutettu = false;
            fi.close();
        } catch (FileNotFoundException e) {
            throw new SailoException("Ei saa luettua tiedostoa " + getTiedostonPerusNimi());
        }
    }


    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Asettaa tiedoston perusnimen ilman tarkenninta
     * @param nimi tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
    }


    /**
     * Tallentaa kirjaston tiedostoon
     * tiedoston muoto on:
     * <pre>
     * 1|Seitsemän veljestä|Aleksis Kivi|suomi|otava|1878|000-0000-00-0|draama
     * 2|Rautatie|Juhani aho|suomi|WSOY|1884|000-0000-12-1|draama
     * </pre>
     * @param tiedostonNimi tallennettavan tiedoston nimi
     * @throws SailoException jos tallennus epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  String tiedNimi = "testikirjat.dat";
     *  File ftied = new File(tiedNimi);
     *  ftied.delete();
     *  Kirjat kirjat = new Kirjat();
     *  Kirja kirja1 = new Kirja(); kirja1.taytaKirjanTiedot();
     *  Kirja kirja2 = new Kirja(); kirja2.taytaKirjanTiedot();
     *  Kirja kirja3 = new Kirja(); kirja3.taytaKirjanTiedot(); 
     *  Kirja kirja4 = new Kirja(); kirja4.taytaKirjanTiedot(); 
     *  Kirja kirja5 = new Kirja(); kirja5.taytaKirjanTiedot(); 
     *  kirjat.lisaa(kirja1);
     *  kirjat.lisaa(kirja2);
     *  kirjat.lisaa(kirja3);
     *  kirjat.lisaa(kirja4);
     *  kirjat.lisaa(kirja5);
     *  kirjat.tallenna(tiedNimi);
     *  kirjat = new Kirjat();
     *  kirjat.lueTiedostosta(tiedNimi);
     *  kirjat.anna(0).toString() === kirja1.toString();
     *  kirjat.anna(2).toString() === kirja3.toString();
     *  kirjat.anna(4).toString() === kirja5.toString();
     *  ftied.delete() === true;
     * </pre>
     */
    public void tallenna(String tiedostonNimi) throws SailoException {
        if ( !muutettu ) return;
        File ftied = new File(tiedostonNimi);
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
            for (int i = 0; i < getLkm(); i++) {
                Kirja kirja = anna(i);
                fo.println(kirja.toString());
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException(
                    "Tiedosto " + ftied.getAbsolutePath() + " ei aukea");
        }
        muutettu = false;
    }
    
    
    /**
     * Tallentaa tiedoston perusnimellä
     * @throws SailoException jos tallennus ei onnistu
     */
    public void tallenna() throws SailoException {
        tallenna(tiedostonPerusNimi);
    }


    /**
     * palauttaa alkiotaulukon paikassa i olevan alkion viitteen
     * @param i alkion paikka taulukossa
     * @throws IndexOutOfBoundsException jos indeksi ei ole sallituissa rajoissa
     * @return viite kirjaan
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Kirjat kirjat = new Kirjat();
     *  Kirja kirja1 = new Kirja(); kirja1.taytaKirjanTiedot();
     *  Kirja kirja2 = new Kirja(); kirja2.taytaKirjanTiedot();
     *  Kirja kirja3 = new Kirja(); kirja3.taytaKirjanTiedot(); 
     *  Kirja kirja4 = new Kirja(); kirja4.taytaKirjanTiedot(); 
     *  Kirja kirja5 = new Kirja(); kirja5.taytaKirjanTiedot(); 
     *  kirjat.lisaa(kirja1);
     *  kirjat.lisaa(kirja2);
     *  kirjat.lisaa(kirja3);
     *  kirjat.lisaa(kirja4);
     *  kirjat.lisaa(kirja5);
     *  kirjat.anna(0).toString() === kirja1.toString();
     *  kirjat.anna(2).toString() === kirja3.toString();
     *  kirjat.anna(4).toString() === kirja5.toString();
     *  kirjat.anna(1) === kirja2;
     *  kirjat.anna(3) === kirja4;
     * </pre>
     */
    public Kirja anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("laiton indeksi: " + i);
        return alkiot[i];
    }


    /**
     * Palauttaa alkiotaulukon kirjojen lukumäärän
     * @return kirjoejn lukumäärä
     */
    public int getLkm() {
        return lkm;
    }


    /**
     * Lisää uuden kirjan kirjataulukkoon
     * @param kirja kirja joka lisätään
     * @throws SailoException jos tietorakenne on täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     *      Kirjat kirjat = new Kirjat();
     *      Kirja kirja1 = new Kirja(), kirja2 = new Kirja();
     *      kirjat.getLkm() === 0;
     *      kirjat.lisaa(kirja1); kirjat.getLkm() === 1;
     *      kirjat.lisaa(kirja2); kirjat.getLkm() === 2;
     *      kirjat.lisaa(kirja1); kirjat.getLkm() === 3;
     *      kirjat.anna(0) === kirja1;
     *      kirjat.anna(1) === kirja2;
     *      kirjat.anna(2) === kirja1;
     *      kirjat.anna(1) == kirja1 === false;
     *      kirjat.anna(1) == kirja2 === true;
     *      kirjat.anna(3) === kirja1; #THROWS IndexOutOfBoundsException
     *      kirjat.lisaa(kirja1); kirjat.getLkm() === 4;
     *      kirjat.lisaa(kirja1); kirjat.getLkm() === 5;
     *      Kirja kirja6 = new Kirja();
     *      kirjat.lisaa(kirja6); kirjat.getLkm() === 6;
     *      kirjat.taulukonKoko() === 10;
     *      kirjat.lisaa(kirja6); kirjat.lisaa(kirja6); kirjat.lisaa(kirja6);
     *      kirjat.lisaa(kirja6); kirjat.lisaa(kirja6);
     *      kirjat.taulukonKoko() === 20;
     * </pre>
     */
    public void lisaa(Kirja kirja) throws SailoException {
        if (lkm >= alkiot.length)
            kasvataTaulukko();
        if (lkm >= alkiot.length)
            throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = kirja;
        lkm++;
        muutettu = true;
    }
    
    
    /**
     * Korvaa kirjan tietorakenteessa. Etsitään samalla tunnusnumerolla oleva kirja
     * ja jos ei löydy luodaan uusi.
     * @param kirja joka korvataan
     * @throws SailoException jos ei mahdu tietorakenteeseen
     * @example
     * <pre name="test">
     * #THROWS SailoException
     *   Kirjat kirjat = new Kirjat();
     *   Kirja kirja11 = new Kirja(); kirjat.lisaa(kirja11);
     *   Kirja kirja22 = new Kirja(); kirjat.lisaa(kirja22); 
     *   kirja11.parse("1|taru sormusten herrasta |essi esimerkki    |englanti|1993|otava|123-456-0-33333-2| kauhu|");
     *   kirja22.parse("2|Täällä pohjantähden alla|jaakko jaakonpoika|suomi   |2004|WSOY|343-467-0-23456-1 | fantasia|");
     *   Kirja kirja33 = new Kirja();
     *   kirja33.parse("3|Tuntematon sotilas      |kissa kirjoittaja |suomi   |1985|WSOY|752-123-2-34658-5 | kaunokirjallisuus|"); 
     *   Kirja kirja44 = new Kirja();
     *   kirja44.parse("1|Rautatie |Juhani Aho|"); 
     *   kirjat.anna(0) === kirja11;
     *   kirjat.korvaaTaiLisaa(kirja33);
     *   kirjat.anna(2) === kirja33;
     *   kirjat.getLkm() === 3;
     *   kirjat.korvaaTaiLisaa(kirja44);
     *   kirjat.anna(0).toString() === kirja44.toString();
     * </pre>
     */
    public void korvaaTaiLisaa(Kirja kirja) throws SailoException {
        int id = kirja.getKirjanID();
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getKirjanID() == id) {
                alkiot[i] = kirja;
                muutettu = true;
                return;
            }
        }
        lisaa(kirja);
    }
    

    /**
     * Kasvattaa taulukon kokoa kaksinkertaiseksi
     * @example
     * <pre name="test">
     * #THROWS SailoException
     *      Kirjat kirjat = new Kirjat();
     *      Kirja kirja1 = new Kirja(), kirja2 = new Kirja();
     *      kirjat.getLkm() === 0;
     *      kirjat.lisaa(kirja1); kirjat.getLkm() === 1;
     *      kirjat.lisaa(kirja2); kirjat.getLkm() === 2;
     *      kirjat.lisaa(kirja1); kirjat.getLkm() === 3;
     *      kirjat.lisaa(kirja1); kirjat.getLkm() === 4;
     *      kirjat.lisaa(kirja1); kirjat.getLkm() === 5;
     *      kirjat.taulukonKoko() === 5;
     *      Kirja kirja6 = new Kirja();
     *      kirjat.lisaa(kirja6); kirjat.getLkm() === 6;
     *      kirjat.taulukonKoko() === 10;
     *      kirjat.lisaa(kirja6); kirjat.lisaa(kirja6); kirjat.lisaa(kirja6);
     *      kirjat.lisaa(kirja6); kirjat.lisaa(kirja6);
     *      kirjat.taulukonKoko() === 20;
     * </pre>
     */
    private void kasvataTaulukko() {
        Kirja[] alkiotUusi = new Kirja[alkiot.length * 2];

        for (int i = 0; i < alkiot.length; i++) {
            alkiotUusi[i] = alkiot[i];
        }
        alkiot = alkiotUusi;
    }


    /**
     * Palauttaa kirjataulukon koon.
     * @return taulukon koko
     */
    public int taulukonKoko() {
        return alkiot.length;
    }


    /**
     * Luodaan alustava taulukko kirjoille
     */
    public Kirjat() {
        alkiot = new Kirja[MAX_KIRJOJA];
    }
    
    
    /**
     * Laskee suosituimman tiedon kentän perusteella
     * @param kentta kentta jota vertaillaan
     * @return suosituimman kentan sisältö
     */
    public String haeEsiintymat(int kentta) {
        int esiintymat = 0;
        String nimi = "";
        
        for (int i = 0; i < lkm; i++) {
            int luku = 0;
            for (int j = i; j < lkm; j++) {
                if (!"".equals(alkiot[i].getKentta(kentta)) && alkiot[i].getKentta(kentta).equals(alkiot[j].getKentta(kentta))) {
                    luku++;
                    if (luku > esiintymat) {
                        esiintymat = luku;
                        nimi = alkiot[j].getKentta(kentta);
                    }
                }
            }
        }
        return (nimi);
    }
    
    
    /**
     * Laskee suurimman arvon kentän perusteella
     * @param kentta kentta jota vertaillaan
     * @return palauttaa kirjan nimen
     */
    public String laskeSuurin(int kentta) {
        int pisin = 0;
        String nimi = "";

        for (int i = 0; i < lkm; i++) {
            StringBuilder sb = new StringBuilder(alkiot[i].getKentta(kentta));
            int luku = Mjonot.erotaInt(sb, 0);
            if (luku > pisin) {
                pisin = luku;
                nimi = alkiot[i].getNimi();
            }
        }
        return nimi;
    }


    /**
     * Laskee kirjaston tilastoja
     * @param ps tietovirta johon tulostetaan
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.ByteArrayOutputStream; 
     * #import java.io.PrintStream;
     *   ByteArrayOutputStream outContent = new ByteArrayOutputStream();
     *   PrintStream ps = new PrintStream(outContent, true);
     *   Kirjat kirjat = new Kirjat(); 
     *   Kirja kirja1 = new Kirja(); kirja1.parse("1|taru sormusten herrasta |essi esimerkki    |englanti|otava|1993|123-456-0-33333-2 |150 | kauhu|"); 
     *   Kirja kirja2 = new Kirja(); kirja2.parse("2|Täällä pohjantähden alla|jaakko jaakonpoika|suomi   |WSOY |2004|343-467-0-23456-1 |200 |fantasia|"); 
     *   Kirja kirja3 = new Kirja(); kirja3.parse("3|Tuntematon sotilas      |essi esimerkki    |suomi   |WSOY |1985|752-123-2-34658-5 |489 |kaunokirjallisuus|"); 
     *   Kirja kirja4 = new Kirja(); kirja4.parse("4|Rautatie                |Juhani Aho"); 
     *   Kirja kirja5 = new Kirja(); kirja5.parse("5|Harry Potter            |Rowling"); 
     *   kirjat.lisaa(kirja1); kirjat.lisaa(kirja2); kirjat.lisaa(kirja3); kirjat.lisaa(kirja4); kirjat.lisaa(kirja5);
     *   kirjat.laskeTilastot(ps);
     *   String vastaus = ("Kirjoja on kirjastossa: 5\r\n" +
     *                     "Eniten kirjoja on kirjoittanut: essi esimerkki\r\n" +
     *                     "Suosituin genre on: kauhu\r\n" +
     *                     "Pisin kirja on: Tuntematon sotilas\r\n" +
     *                     "Tuorein julkaistu kirja on: Täällä pohjantähden alla\r\n");
     *   outContent.toString().equals(vastaus) === true;         
     * </pre>
     */
    public void laskeTilastot(PrintStream ps) {

       ps.println("Kirjoja on kirjastossa: " + lkm);
       
       String kirjailija = haeEsiintymat(1);
       ps.println("Eniten kirjoja on kirjoittanut: " + kirjailija);   
       
       String genre = haeEsiintymat(7);
       ps.println("Suosituin genre on: " + genre);
       
       String enitenSivuja = laskeSuurin(6);
       ps.println("Pisin kirja on: " + enitenSivuja);
       
       String julkaistu = laskeSuurin(4);
       ps.println("Tuorein julkaistu kirja on: " + julkaistu);
    }
}
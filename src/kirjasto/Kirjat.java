package kirjasto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import fi.jyu.mit.ohj2.WildChars;

/**
 * - pitää yllä varsinaista kirjarekisteriä, eli osaa 
 *   lisätä ja poistaa kirjan                         
 * - lukee ja kirjoittaa kirjat tiedostoon           
 * - osaa etsiä ja lajitella 
 * 
 * @author jrkarmau
 * @version 9.3.2021
 *
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
     * Poistaa kirjan taulukosta
     * @param id kirjan ID numero
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
     * @return -1 jo sei löydy muuten kirjan indeksi taulukossa
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
     */
    public ArrayList<Kirja> etsi(String hakuehto, int hakukenttaNro) {
        ArrayList<Kirja> loytyneet = new ArrayList<>();
        for (int i = 0; i < lkm; i++) {
            if (WildChars.onkoSamat(alkiot[i].getKentta(hakukenttaNro), hakuehto)) {
                loytyneet.add(alkiot[i]);
            }
        }
        return loytyneet;
    }

    
    /**
     * Lukee kirjaston tiedostosta
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
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
     */
    public void tallenna(String tiedostonNimi) throws SailoException {
        if ( !muutettu ) return;
        File ftied = new File(tiedostonNimi);
        try (PrintStream fo = new PrintStream(
                new FileOutputStream(ftied, false))) {
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
     * Luodaan alustava taulukko
     */
    public Kirjat() {
        alkiot = new Kirja[MAX_KIRJOJA];
    }
}
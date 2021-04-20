package kirjasto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


/**
 *- pitää yllä varsinaista kommenttirekisteriä, eli 
 *  osaa lisätä ja poistaa kommentin                
 *- lukee ja kirjoittaa kommentit tiedostoon         
 *- osaa etsiä ja lajitella 
 * 
 * @author jrkarmau
 * @version 10.3.2021
 */
public class Kommentit {
    
    private ArrayList<Kommentti> alkiot = new ArrayList<Kommentti>();
    private String tiedostonPerusNimi = "";
    private boolean muutettu = false;
    
    /**
     * Pääohjelma harrastuksen testaamiseksi
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
        Kommentit kommentit = new Kommentit();
        
        try {
            kommentit.lueTiedostosta("kirjasto"); 
        } catch (SailoException ex) {
            System.err.println(ex.getMessage());
        }
        
        Kommentti kom1 = new Kommentti();
        kom1.taytaKommentinTiedot(2);
        kom1.rekisteroi();
        Kommentti kom2 = new Kommentti();
        kom2.taytaKommentinTiedot(1);
        kom2.rekisteroi();
        Kommentti kom3 = new Kommentti();
        kom3.taytaKommentinTiedot(2);
        kom3.rekisteroi();
        Kommentti kom4 = new Kommentti();
        kom4.taytaKommentinTiedot(1);
        kom4.rekisteroi();
        
        kommentit.lisaa(kom1);
        kommentit.lisaa(kom2);
        kommentit.lisaa(kom3);
        kommentit.lisaa(kom4);
        
        System.out.println("======================== kommentin testit ========================");
        

       List<Kommentti> kommentitLista = kommentit.annaKommentit(1);
        for (Kommentti kom : kommentitLista) {
            System.out.println(kom.getKirjanID() + " ");
            kom.tulosta(System.out);
        }
        
        try {
            kommentit.tallenna("kirjasto"); 
        } catch (SailoException e) {
            e.printStackTrace();
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
     * Lukee kirjan kommentit tiedostosta
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        setTiedostonPerusNimi(hakemisto);

        try (Scanner fi = new Scanner(new FileInputStream(getTiedostonPerusNimi()))) {
            while (fi.hasNext()) {
                String s = "";
                s = fi.nextLine();
                Kommentti kommentti = new Kommentti();
                kommentti.parse(s);
                lisaa(kommentti);
            }
            muutettu = false;
        } catch (FileNotFoundException e) {
            throw new SailoException("Ei saa luettua tiedostoa " + getTiedostonPerusNimi());
        }
    }
    
    
    /**
     * Tallentaa kirjaston kommentit tiedostoon
     * tiedoston muoto on:
     * <pre>
     * 1|Kommentin otsikko| kommenttia kommenttia|
     * 2|toisen Kommentin otsikko| kommenttia kommenttia kommenttia kommenttia|
     * </pre>
     * @param tiedostonNimi tallennettavan tiedoston nimi
     * @throws SailoException jos tallennus epäonnistuu
     */
    public void tallenna(String tiedostonNimi) throws SailoException {
        if (!muutettu) return;
        File ftied = new File(tiedostonNimi);
        try (PrintStream fo = new PrintStream(
                new FileOutputStream(ftied, false))) {
            for (Kommentti kom : alkiot) {
                fo.println(kom.toString());
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException( "Tiedosto " + ftied.getAbsolutePath() + " ei aukea");
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
     * Etsii kommenteista ne kommentit jotka kuuluvat tietylle kirjalle
     * @param id kirjan ID-numero
     * @return lista kommenteista
     * @example
     * <pre name="test">
     *   #import java.util.*;
     *   Kommentit kommentit = new Kommentit();
     *   Kommentti kom11 = new Kommentti(2); kommentit.lisaa(kom11);
     *   Kommentti kom22 = new Kommentti(1); kommentit.lisaa(kom22); 
     *   Kommentti kom33 = new Kommentti(2); kommentit.lisaa(kom33); 
     *   Kommentti kom44 = new Kommentti(1); kommentit.lisaa(kom44); 
     *   Kommentti kom55 = new Kommentti(2); kommentit.lisaa(kom55); 
     *   Kommentti kom66 = new Kommentti(5); kommentit.lisaa(kom66); 
     *   List<Kommentti> loytyneet;
     *   loytyneet = kommentit.annaKommentit(3);
     *   loytyneet.size() === 0;
     *   loytyneet = kommentit.annaKommentit(1);
     *   loytyneet.size() === 2;
     *   loytyneet.get(0) == kom22 === true;
     *   loytyneet.get(1) == kom44 === true; 
     *   loytyneet = kommentit.annaKommentit(5);
     *   loytyneet.size() === 1;
     *   loytyneet.get(0) == kom66 === true; 
     * </pre>
     */
    public List<Kommentti> annaKommentit(int id) {
        ArrayList<Kommentti> loydetyt = new ArrayList<Kommentti>();
        
        for (Kommentti kom : alkiot) {
            if (kom.getKirjanID() == id) loydetyt.add(kom);
        }
        return loydetyt;
    }
    
    
    /**
     * Lisää uuden kommentin
     * @param kom kommentti joka lisätään
     */
    public void lisaa(Kommentti kom) {
        alkiot.add(kom);
        muutettu = true;
    }
    
    
    /**
     * Alustaminen
     */
    public Kommentit() {
        //
    }


    /**
     * korvaa kommentin muutetulla kommentilla ja jos ei löydy tietorakenteesta lisää sen.
     * @param kommentti kommentti joka tallennetaan
     */
    public void korvaaTaiLisaa(Kommentti kommentti) {
        int id = kommentti.getKommentinID();

        for (Kommentti kom : alkiot) {
            if (kom.getKommentinID() == id) {                
                alkiot.remove(kom);
                alkiot.add(0, kommentti);
                muutettu = true;
                return;
            }
        }
        lisaa(kommentti);
    }
    
    /**
     * Poistaa yhden kommentin
     */
    public void poistaKommentti() {
        //
    }
    

    /**
     * Poistaa kaikki kirjan kommentit
     * @param kirjanID kirja jonka kommentit poistetaan
     */
    public void poistaKirjanKommentit(int kirjanID) {
        int n = 0;
        for (Iterator<Kommentti> it = alkiot.iterator(); it.hasNext();) {
            Kommentti kom = it.next();
            if (kom.getKirjanID() == kirjanID) {
                it.remove();
                n++;
            }
        }
        if (n > 0)
            muutettu = true;
    }


    /**
     * Poistaa yhden kommentin 
     * @param kommentinID kommentin id joka poistetaan
     */
    public void poistaKommentti(int kommentinID) {
        for (Kommentti kom : alkiot) {
            if (kom.getKommentinID() == kommentinID) {
                alkiot.remove(kom);
                muutettu = true;
                return;
            }
        }
    }      
}

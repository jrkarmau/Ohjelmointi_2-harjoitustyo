package kirjasto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * - huolehtii Kirjat ja Kommentit - luokkien     
 *   välisestä yhteistyöstä ja välittää näitä tietoja 
 *   pyydettäessä                                     
 * - lukee ja kirjoittaa kirjaston tiedostoon         
 *   pyytämällä apua avustajiltaan 
 * 
 * @author jrkarmau
 * @version 9.3.2021
 *
 */
public class Kirjasto {

    private Kirjat kirjat = new Kirjat();
    private Kommentit kommentit = new Kommentit();
    
    
    /**
     * Pääohjelma kirjaston testaamiseksi
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
        Kirjasto kirjasto = new Kirjasto();

        // kirjan testaaminen ----------------------------------
        Kirja kirja1 = new Kirja();      
        kirja1.rekisteroi();
        kirja1.taytaKirjanTiedot();
        
        Kirja kirja2 = new Kirja();   
        kirja2.rekisteroi();
        kirja2.taytaKirjanTiedot();
        
        try {
            kirjasto.lisaa(kirja1);
            kirjasto.lisaa(kirja2);
        } catch (SailoException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("======================== kirjan testit ========================");
        for (int i = 0; i < kirjasto.getKirjoja(); i++) {
            Kirja kirja = kirjasto.annaKirja(i);
            kirja.tulosta(System.out);
        }
            
        // kommentin testaaminen -----------------------------------    
        Kommentit kommentit = new Kommentit();
            
        Kommentti kom1 = new Kommentti();
        kom1.taytaKommentinTiedot(2);
        Kommentti kom2 = new Kommentti();
        kom2.taytaKommentinTiedot(1);
        Kommentti kom3 = new Kommentti();
        kom3.taytaKommentinTiedot(2);
        Kommentti kom4 = new Kommentti();
        kom4.taytaKommentinTiedot(1);
            
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
    }
    
    
    /**
     * Etsii Kirjoista hakuehdot täyttävät kirjat
     * @param hakuehto käyttäjän hakuehto
     * @param hakukenttaNro minkä tiedon perusteella etsitään
     * @return lista kirjoista jotka täyttävät hakuehdot
     * @throws SailoException jos etsimisessä on ongelmia
     */
    public ArrayList<Kirja> etsi(String hakuehto, int hakukenttaNro) throws SailoException {
        return kirjat.etsi(hakuehto, hakukenttaNro);
    }
    
    
    /**
     * Poistaa kirjan taulukosta
     * @param kirja poistettava kirja
     */
    public void poistaKirja(Kirja kirja) {
        if (kirja == null) return;
        kirjat.poista(kirja.getKirjanID());
        kommentit.poistaKirjanKommentit(kirja.getKirjanID());
    }
    
    /**
     * Poistaa yhden kommentin
     * @param kommentti kommentti joka poistetaan
     */
    public void poistaKommentti(Kommentti kommentti) {
        kommentit.poistaKommentti(kommentti.getKommentinID());
    }
    

    /**
     * Lukee kirjaston tiedot tiedostosta
     * @param nimi  jota käytetään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        kirjat = new Kirjat();
        kommentit = new Kommentit();
        
        setTiedosto(nimi);
        kirjat.lueTiedostosta();
        kommentit.lueTiedostosta();
    }
    
    
    /**
     * Tallentaa kirjaston tiedot tiedostoon
     * Yritetään tallentaa kirjat ja kommentit ennen
     * poikkeuksen heittämistä
     * @throws SailoException jos tallentaminen ei onnistu
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            kirjat.tallenna();
        } catch (SailoException ex) {
            virhe = ex.getMessage();
        }
        
        try {
            kommentit.tallenna();
        } catch (SailoException ex) {
            virhe += ex.getMessage();
        }
        if (!"".equals(virhe)) throw new SailoException(virhe);
    }
    
    
    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if (!nimi.isEmpty()) hakemistonNimi = nimi + "/";
        kirjat.setTiedostonPerusNimi(hakemistonNimi + "kirjat.dat");
        kommentit.setTiedostonPerusNimi(hakemistonNimi + "kommentit.dat");
    }
    
    
    /**
     * Palauttaa listan kommenteista jotka kuuluvat kirjalle
     * @param kirja jonka kommentit haetaan
     * @return lista kommenteista
     */
    public List<Kommentti> annaKommentit(Kirja kirja) {
        return kommentit.annaKommentit(kirja.getKirjanID());
    }
    
    
    /**
     * Lisää uuden kommentin
     * @param kom kommentti joka lisätään
     */
    public void lisaa(Kommentti kom) {
        kommentit.lisaa(kom);
    }
    
    /**
     * Korvaa kirjan tietorakenteessa. Jos kirjaa ei löydy tunnusnumerolla luodaan uusi kirja.
     * @param kirja kirja joka korvataan
     * @throws SailoException jos tietorakenne täynnä
     */
    public void korvaaTaiLisaa(Kirja kirja) throws SailoException {
        kirjat.korvaaTaiLisaa(kirja);
    }
    
    
    /**
     * Korvaa kommentin tietorakenteessa. Jos kommenttia ei löydy id:llä luodaan uusi kommentti.
     * @param kommentti joka korvataan
     * @throws SailoException jos tietorakenne täynnä
     */
    public void korvaaTaiLisaa(Kommentti kommentti) throws SailoException {
        kommentit.korvaaTaiLisaa(kommentti);
    }
    

    /**
     * Palauttaa kirjan pyydetyllä numerolla
     * @param i kirjan numero
     * @return kirja indeksistä i
     */
    public Kirja annaKirja(int i) {
        return kirjat.anna(i);
    }
    
    
    /**
     * Palauttaa kirjojen lukumäärän
     * @return kirjojen lukumäärä
     */
    public int getKirjoja() {
        return kirjat.getLkm();
    }
    

    /**
     * lisätään uusi kirja
     * @param kirja kirja joka lisätään
     * @throws SailoException jos lisääminen ei onnistu
     */
    public void lisaa(Kirja kirja) throws SailoException {
        kirjat.lisaa(kirja);
    } 
}
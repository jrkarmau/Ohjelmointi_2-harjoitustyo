package kirjasto;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
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
 * @version 22.4.2021
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
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.util.ArrayList; 
     *   Kirjasto kirjasto = new Kirjasto(); 
     *   Kirja kirja1 = new Kirja(); kirja1.parse("1|taru sormusten herrasta |essi esimerkki    |englanti|1993|otava|123-456-0-33333-2| kauhu|"); 
     *   Kirja kirja2 = new Kirja(); kirja2.parse("2|Täällä pohjantähden alla|jaakko jaakonpoika|suomi   |2004|WSOY|343-467-0-23456-1 | fantasia|"); 
     *   Kirja kirja3 = new Kirja(); kirja3.parse("3|Tuntematon sotilas      |kissa kirjoittaja |suomi   |1985|WSOY|752-123-2-34658-5 | kaunokirjallisuus|"); 
     *   Kirja kirja4 = new Kirja(); kirja4.parse("4|Rautatie                |Juhani Aho|"); 
     *   Kirja kirja5 = new Kirja(); kirja5.parse("5|Harry Potter            |Rowling           |suomi|"); 
     *   kirjasto.lisaa(kirja1); kirjasto.lisaa(kirja2); kirjasto.lisaa(kirja3); kirjasto.lisaa(kirja4); kirjasto.lisaa(kirja5);
     *   ArrayList<Kirja> loytyneet = new  ArrayList<Kirja>();  
     *   loytyneet = kirjasto.etsi("*rr*",0);  
     *   loytyneet.size() === 2;  
     *   loytyneet.get(0) == kirja1 === true;  
     *   loytyneet.get(1) == kirja5 === true;  
     *   loytyneet = kirjasto.etsi("*suo*",2);  
     *   loytyneet.size() === 3;  
     *   loytyneet.get(0) == kirja2 === true;  
     *   loytyneet.get(1) == kirja3 === true; 
     *   loytyneet.get(2) == kirja5 === true; 
     *   loytyneet = kirjasto.etsi(null,-1);  
     *   loytyneet.size() === 5;  
     * </pre>
     */
    public ArrayList<Kirja> etsi(String hakuehto, int hakukenttaNro) throws SailoException {
        return kirjat.etsi(hakuehto, hakukenttaNro);
    }
    
    
    /**
     * Poistaa kirjan taulukosta
     * @param kirja poistettava kirja
     * @example
     * <pre name="test">
     * #THROWS SailoException  
     * Kirjasto kirjasto = new Kirjasto(); 
     * Kirja kir1 = new Kirja(), kir2 = new Kirja(), kir3 = new Kirja(); 
     * kir1.rekisteroi(); kir2.rekisteroi(); kir3.rekisteroi(); 
     * kirjasto.lisaa(kir1); kirjasto.lisaa(kir2); kirjasto.lisaa(kir3); 
     * kirjasto.getKirjoja() === 3;
     * kirjasto.poistaKirja(kir1); 
     * kirjasto.getKirjoja() === 2;
     * kirjasto.poistaKirja(kir2); 
     * kirjasto.getKirjoja() === 1;
     * </pre>
     */
    public void poistaKirja(Kirja kirja) {
        if (kirja == null) return;
        kirjat.poista(kirja.getKirjanID());
        kommentit.poistaKirjanKommentit(kirja.getKirjanID());
    }
    
     
    /**
     * Poistaa yhden kommentin
     * @param kommentti kommentti joka poistetaan
     * @example
     * <pre name="test">
     * #THROWS SailoException  
     * #import java.util.*;
     *   Kirjasto kirjasto = new Kirjasto();
     *   Kirja kirja1 = new Kirja(); kirjasto.lisaa(kirja1);
     *   Kirja kirja2 = new Kirja(); kirjasto.lisaa(kirja2);
     *   kirja2.parse("2|Täällä pohjantähden alla|jaakko jaakonpoika|suomi   |WSOY |2004|343-467-0-23456-1 |200 |fantasia|");
     *   Kommentti kom11 = new Kommentti(2); kirjasto.lisaa(kom11);
     *   Kommentti kom22 = new Kommentti(1); kirjasto.lisaa(kom22); 
     *   Kommentti kom33 = new Kommentti(2); kirjasto.lisaa(kom33);
     *   kom11.parse("4  | 2 |  otsikko2  |  tekstiätekstiä  ");
     *   kom22.parse("5  | 2 |  otsikko21  |  tekstiätekstiä  ");
     *   kom33.parse("3  | 2 |  otsikko212  |  tekstiätekstiä  ");
     *   List<Kommentti> loytyneet;
     *   loytyneet = kirjasto.annaKommentit(kirja2);
     *   loytyneet.size() === 3;
     *   kirjasto.poistaKommentti(kom22);
     *   loytyneet = kirjasto.annaKommentit(kirja2);
     *   loytyneet.size() === 2;
     *   kirjasto.poistaKommentti(kom11);
     *   loytyneet = kirjasto.annaKommentit(kirja2);
     *   loytyneet.size() === 1;
     * </pre>
     */
    public void poistaKommentti(Kommentti kommentti) {
        kommentit.poistaKommentti(kommentti.getKommentinID());
    }
    

    /**
     * Lukee kirjaston tiedot tiedostosta
     * @param nimi  jota käytetään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.*;
     * #import java.util.*;
     *  String hakemisto = "testikirjasto";
     *  File dir = new File(hakemisto);
     *  dir.mkdir(); 
     *  String kirjat = "testikirjasto/kirjat.dat";
     *  String kommentit = "testikirjasto/kommentit.dat";
     *  File ftied1 = new File(kirjat);
     *  File ftied2 = new File(kommentit);
     *  ftied1.delete();
     *  ftied2.delete();
     *  Kirjasto kirjasto = new Kirjasto();
     *  kirjasto.lueTiedostosta(hakemisto); #THROWS SailoException
     *  Kirja kirja1 = new Kirja(); kirjasto.lisaa(kirja1);
     *  Kirja kirja2 = new Kirja(); kirjasto.lisaa(kirja2);
     *  Kirja kirja3 = new Kirja(); kirjasto.lisaa(kirja3);
     *  kirja1.parse("1|taru sormusten herrasta |essi esimerkki    |englanti|otava|1993|123-456-0-33333-2 |150 | kauhu|"); 
     *  kirja2.parse("2|Täällä pohjantähden alla|jaakko jaakonpoika|suomi   |WSOY |2004|343-467-0-23456-1 |200 |fantasia|"); 
     *  kirja3.parse("3|Tuntematon sotilas      |essi esimerkki    |suomi   |WSOY |1985|752-123-2-34658-5 |489 |kaunokirjallisuus|");
     *  Kommentti kom1 = new Kommentti(); kirjasto.lisaa(kom1);
     *  Kommentti kom2 = new Kommentti(); kirjasto.lisaa(kom2);
     *  Kommentti kom3 = new Kommentti(); kirjasto.lisaa(kom3);
     *  kom1.parse("4  | 2 |  otsikko2  |  tekstiätekstiä  ");
     *  kom2.parse("5  | 1 |  otsikko21  |  tekstiätekstiä  ");
     *  kom3.parse("3  | 2 |  otsikko212  |  tekstiätekstiä  ");
     *  kirjasto.tallenna();
     *  kirjasto = new Kirjasto();
     *  kirjasto.setTiedosto(hakemisto);
     *  kirjasto.lueTiedostosta(hakemisto);
     *  List<Kommentti> loytyneet = kirjasto.annaKommentit(kirja2);
     *  loytyneet.size() === 2;
     *  kirjasto.annaKirja(0).toString() === kirja1.toString();
     *  kirjasto.annaKirja(2).toString() === kirja3.toString();
     *  ftied1.delete()  === true;
     *  ftied2.delete() === true;
     *  dir.delete() === true;
     * </pre>
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
     * </pre>
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
     * @example
     * <pre name="test">
     *   #import java.util.*;
     *   Kirjasto kirjasto = new Kirjasto();
     *   Kirja kirja1 = new Kirja(); kirja1.parse("1|taru sormusten herrasta |essi esimerkki    |englanti|1993|otava|123-456-0-33333-2| kauhu|"); 
     *   Kirja kirja2 = new Kirja(); kirja2.parse("2|Täällä pohjantähden alla|jaakko jaakonpoika|suomi   |2004|WSOY|343-467-0-23456-1 | fantasia|"); 
     *   Kirja kirja3 = new Kirja(); kirja3.parse("5|Tuntematon sotilas      |kissa kirjoittaja |suomi   |1985|WSOY|752-123-2-34658-5 | kaunokirjallisuus|"); 
     *   Kirja kirja4 = new Kirja();
     *   Kommentti kom11 = new Kommentti(2); kirjasto.lisaa(kom11);
     *   Kommentti kom22 = new Kommentti(1); kirjasto.lisaa(kom22); 
     *   Kommentti kom33 = new Kommentti(2); kirjasto.lisaa(kom33); 
     *   Kommentti kom44 = new Kommentti(1); kirjasto.lisaa(kom44); 
     *   Kommentti kom55 = new Kommentti(2); kirjasto.lisaa(kom55); 
     *   Kommentti kom66 = new Kommentti(5); kirjasto.lisaa(kom66); 
     *   List<Kommentti> loytyneet;
     *   loytyneet = kirjasto.annaKommentit(kirja4);
     *   loytyneet.size() === 0;
     *   loytyneet = kirjasto.annaKommentit(kirja1);
     *   loytyneet.size() === 2;
     *   loytyneet.get(0) == kom22 === true;
     *   loytyneet.get(1) == kom44 === true; 
     *   loytyneet = kirjasto.annaKommentit(kirja3);
     *   loytyneet.size() === 1;
     *   loytyneet.get(0) == kom66 === true; 
     * </pre>
     */
    public List<Kommentti> annaKommentit(Kirja kirja) {
        return kommentit.annaKommentit(kirja.getKirjanID());
    }
    
    
    /**
     * Lisää uuden kommentin
     * @param kom kommentti joka lisätään
     * @example
     * <pre name="test">
     *   #import java.util.*;
     *   Kirjasto kirjasto = new Kirjasto();
     *   Kirja kirja1 = new Kirja(); kirja1.parse("1|taru sormusten herrasta |essi esimerkki    |englanti|1993|otava|123-456-0-33333-2| kauhu|"); 
     *   Kirja kirja2 = new Kirja(); kirja2.parse("2|Täällä pohjantähden alla|jaakko jaakonpoika|suomi   |2004|WSOY|343-467-0-23456-1 | fantasia|"); 
     *   Kirja kirja3 = new Kirja(); kirja3.parse("5|Tuntematon sotilas      |kissa kirjoittaja |suomi   |1985|WSOY|752-123-2-34658-5 | kaunokirjallisuus|"); 
     *   Kirja kirja4 = new Kirja();
     *   Kommentti kom11 = new Kommentti(2); kirjasto.lisaa(kom11);
     *   Kommentti kom22 = new Kommentti(1); kirjasto.lisaa(kom22); 
     *   Kommentti kom33 = new Kommentti(2); kirjasto.lisaa(kom33); 
     *   Kommentti kom44 = new Kommentti(1); kirjasto.lisaa(kom44); 
     *   Kommentti kom55 = new Kommentti(2); kirjasto.lisaa(kom55); 
     *   Kommentti kom66 = new Kommentti(5); kirjasto.lisaa(kom66); 
     *   List<Kommentti> loytyneet;
     *   loytyneet = kirjasto.annaKommentit(kirja4);
     *   loytyneet.size() === 0;
     *   loytyneet = kirjasto.annaKommentit(kirja1);
     *   loytyneet.size() === 2;
     *   loytyneet.get(0) == kom22 === true;
     *   loytyneet.get(1) == kom44 === true; 
     *   loytyneet = kirjasto.annaKommentit(kirja3);
     *   loytyneet.size() === 1;
     *   loytyneet.get(0) == kom66 === true; 
     * </pre>
     */
    public void lisaa(Kommentti kom) {
        kommentit.lisaa(kom);
    }
    
    /**
     * Korvaa kirjan tietorakenteessa. Jos kirjaa ei löydy tunnusnumerolla luodaan uusi kirja.
     * @param kirja kirja joka korvataan
     * @throws SailoException jos tietorakenne täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     *   Kirjasto kirjasto = new Kirjasto();
     *   Kirja kirja11 = new Kirja(); kirjasto.lisaa(kirja11);
     *   Kirja kirja22 = new Kirja(); kirjasto.lisaa(kirja22); 
     *   kirja11.parse("1|taru sormusten herrasta |essi esimerkki    |englanti|1993|otava|123-456-0-33333-2| kauhu|");
     *   kirja22.parse("2|Täällä pohjantähden alla|jaakko jaakonpoika|suomi   |2004|WSOY|343-467-0-23456-1 | fantasia|");
     *   Kirja kirja33 = new Kirja();
     *   kirja33.parse("3|Tuntematon sotilas      |kissa kirjoittaja |suomi   |1985|WSOY|752-123-2-34658-5 | kaunokirjallisuus|"); 
     *   Kirja kirja44 = new Kirja();
     *   kirja44.parse("1|Rautatie |Juhani Aho|"); 
     *   kirjasto.annaKirja(0) === kirja11;
     *   kirjasto.korvaaTaiLisaa(kirja33);
     *   kirjasto.annaKirja(2) === kirja33;
     *   kirjasto.getKirjoja() === 3;
     *   kirjasto.korvaaTaiLisaa(kirja44);
     *   kirjasto.annaKirja(0).toString() === kirja44.toString();
     * </pre>
     */
    public void korvaaTaiLisaa(Kirja kirja) throws SailoException {
        kirjat.korvaaTaiLisaa(kirja);
    }
    
    
    /**
     * Korvaa kommentin tietorakenteessa. Jos kommenttia ei löydy id:llä luodaan uusi kommentti.
     * @param kommentti joka korvataan
     * @throws SailoException jos tietorakenne täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.util.ArrayList;
     *   Kirjasto kirjasto = new Kirjasto();
     *   Kirja kirja2 = new Kirja();
     *   kirja2.parse("2|Täällä pohjantähden alla|jaakko jaakonpoika|suomi   |2004|WSOY|343-467-0-23456-1 | fantasia|");
     *   Kommentti kom11 = new Kommentti(2); kirjasto.lisaa(kom11);
     *   Kommentti kom22 = new Kommentti(2); kirjasto.lisaa(kom22); 
     *   kom11.parse("1  | 2 |  testi1  |  tekstiä1  ");
     *   kom22.parse("2  | 2 |  testi2  |  tekstiä2  ");
     *   Kommentti kom33 = new Kommentti(2);
     *   kom33.parse("3  | 2 |  testi3  |  tekstiä3  "); 
     *   Kommentti kom44 = new Kommentti(2);
     *   kom44.parse("1  | 2 |  testi4  |  tekstiä4  "); 
     *   List<Kommentti> loytyneet;
     *   loytyneet = kirjasto.annaKommentit(kirja2);
     *   loytyneet.get(0).getOtsikko() === "testi1";
     *   kirjasto.korvaaTaiLisaa(kom33);
     *   loytyneet = kirjasto.annaKommentit(kirja2);
     *   loytyneet.size() === 3;
     *   kirjasto.korvaaTaiLisaa(kom44);
     *   loytyneet = kirjasto.annaKommentit(kirja2);
     *   loytyneet.get(0).getOtsikko() === "testi4";
     * </pre>
     */
    public void korvaaTaiLisaa(Kommentti kommentti) throws SailoException {
        kommentit.korvaaTaiLisaa(kommentti);
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
     *  Kirjasto kirjasto = new Kirjasto();
     *  Kirja kirja1 = new Kirja(); kirja1.taytaKirjanTiedot();
     *  Kirja kirja2 = new Kirja(); kirja2.taytaKirjanTiedot();
     *  Kirja kirja3 = new Kirja(); kirja3.taytaKirjanTiedot(); 
     *  Kirja kirja4 = new Kirja(); kirja4.taytaKirjanTiedot(); 
     *  Kirja kirja5 = new Kirja(); kirja5.taytaKirjanTiedot(); 
     *  kirjasto.lisaa(kirja1);
     *  kirjasto.lisaa(kirja2);
     *  kirjasto.lisaa(kirja3);
     *  kirjasto.lisaa(kirja4);
     *  kirjasto.lisaa(kirja5);
     *  kirjasto.annaKirja(0).toString() === kirja1.toString();
     *  kirjasto.annaKirja(2).toString() === kirja3.toString();
     *  kirjasto.annaKirja(4).toString() === kirja5.toString();
     *  kirjasto.annaKirja(1) === kirja2;
     *  kirjasto.annaKirja(3) === kirja4;
     * </pre>
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
     * Lisää uuden kirjan kirjataulukkoon
     * @param kirja kirja joka lisätään
     * @throws SailoException jos tietorakenne on täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     *      Kirjasto kirjasto = new Kirjasto();
     *      Kirja kirja1 = new Kirja(), kirja2 = new Kirja();
     *      kirjasto.getKirjoja() === 0;
     *      kirjasto.lisaa(kirja1); kirjasto.getKirjoja() === 1;
     *      kirjasto.lisaa(kirja2); kirjasto.getKirjoja() === 2;
     *      kirjasto.lisaa(kirja1); kirjasto.getKirjoja() === 3;
     *      kirjasto.annaKirja(0) === kirja1;
     *      kirjasto.annaKirja(1) === kirja2;
     *      kirjasto.annaKirja(2) === kirja1;
     *      kirjasto.annaKirja(1) == kirja1 === false;
     *      kirjasto.annaKirja(1) == kirja2 === true;
     *      kirjasto.annaKirja(3) === kirja1; #THROWS IndexOutOfBoundsException
     * </pre>
     */
    public void lisaa(Kirja kirja) throws SailoException {
        kirjat.lisaa(kirja);
    }


    /**
     * Tekee uudet kirjat ja kommentit tiedostot uuteen kansioon
     * @param nimi kansion nimi
     * @example
     * <pre name="test">
     *  #import java.nio.file.Files;
     *  Kirjasto kirjasto = new Kirjasto();
     *  File f = new File("testikansio");
     *  File fKirjat = new File("testikansio/kirjat.dat");
     *  File fKommentit = new File("testikansio/kommentit.dat");
     *  f.exists() === false;
     *  fKirjat.exists() === false;
     *  fKommentit.exists() === false;
     *  kirjasto.luoTiedostot("testikansio");
     *  fKirjat.exists() === true;
     *  fKommentit.exists() === true;
     *  fKirjat.delete() === true;
     *  fKommentit.delete() === true;
     *  f.delete() === true;
     * </pre>
     */
    public void luoTiedostot(String nimi) {
        File kirjatFile = new File(nimi + "/kirjat.dat");
        File kommentitFile = new File(nimi + "/kommentit.dat");

        try {
            kirjatFile.getParentFile().mkdirs();
            kirjatFile.createNewFile();
            kommentitFile.createNewFile();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
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
     *   Kirjasto kirjasto = new Kirjasto(); 
     *   Kirja kirja1 = new Kirja(); kirja1.parse("1|taru sormusten herrasta |essi esimerkki    |englanti|otava|1993|123-456-0-33333-2 |150 | kauhu|"); 
     *   Kirja kirja2 = new Kirja(); kirja2.parse("2|Täällä pohjantähden alla|jaakko jaakonpoika|suomi   |WSOY |2004|343-467-0-23456-1 |200 |fantasia|"); 
     *   Kirja kirja3 = new Kirja(); kirja3.parse("3|Tuntematon sotilas      |essi esimerkki    |suomi   |WSOY |1985|752-123-2-34658-5 |489 |kaunokirjallisuus|"); 
     *   Kirja kirja4 = new Kirja(); kirja4.parse("4|Rautatie                |Juhani Aho"); 
     *   Kirja kirja5 = new Kirja(); kirja5.parse("5|Harry Potter            |Rowling"); 
     *   kirjasto.lisaa(kirja1); kirjasto.lisaa(kirja2); kirjasto.lisaa(kirja3); kirjasto.lisaa(kirja4); kirjasto.lisaa(kirja5);
     *   kirjasto.laskeTilastot(ps);
     *   String vastaus = ("Kirjoja on kirjastossa: 5\r\n" +
     *                     "Eniten kirjoja on kirjoittanut: essi esimerkki\r\n" +
     *                     "Suosituin genre on: kauhu\r\n" +
     *                     "Pisin kirja on: Tuntematon sotilas\r\n" +
     *                     "Tuorein julkaistu kirja on: Täällä pohjantähden alla\r\n");
     *   outContent.toString().equals(vastaus) === true;         
     * </pre>
     */
    public void laskeTilastot(PrintStream ps) {
        kirjat.laskeTilastot(ps);
    }
}
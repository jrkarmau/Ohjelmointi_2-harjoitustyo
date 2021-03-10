package kirjasto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


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
    
    private Collection<Kommentti> alkiot = new ArrayList<Kommentti>();

    
    /**
     * Pääohjelma harrastuksen testaamiseksi
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
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
    }
    
    
    /**
     * Alustaminen
     */
    public Kommentit() {
        //
    }
}

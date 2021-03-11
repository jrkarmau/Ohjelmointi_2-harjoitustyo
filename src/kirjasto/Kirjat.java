package kirjasto;

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
    
    /**
     * Pääohjelma kirjat-luokan metodien käyttämiseksi
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
        Kirjat kirjat = new Kirjat();

        Kirja kirja1 = new Kirja();      
        kirja1.rekisteroi();
        kirja1.taytaKirjanTiedot();
        
        Kirja kirja2 = new Kirja();   
        kirja2.rekisteroi();
        kirja2.taytaKirjanTiedot();
        
        try {
            kirjat.lisaa(kirja1);
            kirjat.lisaa(kirja2);
            
            System.out.println("=============================== Kirjat testi ===============================");
            
            for (int i = 0; i < kirjat.getLkm(); i++) {
                Kirja kirja = kirjat.anna(i);
                System.out.println("kirjan ID " + i);
                kirja.tulosta(System.out);
            }  
        } catch (SailoException e) {
            System.err.println(e.getMessage());
        }
    }
     
    
    /**
     * palauttaa alkiotaulukon paikassa i olevan alkion viitteen
     * @param i alkion paikka taulukossa
     * @throws IndexOutOfBoundsException jos indeksi ei ole sallituissa rajoissa
     * @return viite kirjaan
     */
    public Kirja anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i) throw new IndexOutOfBoundsException("laiton indeksi: " + i);
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
        if (lkm >= alkiot.length) kasvataTaulukko();
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = kirja;
        lkm++;
    }
    
    
    /**
     * Kasvattaa taulukon kokoa kaksinkertaiseksi
     */
    private void kasvataTaulukko() {
        Kirja[] alkiotUusi = new Kirja[alkiot.length*2];
        
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
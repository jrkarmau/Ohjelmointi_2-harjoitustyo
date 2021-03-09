/**
 * 
 */
package kirjasto;

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
    
    
    /**
     * Pääohjelma kirjaston testaamiseksi
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
        Kirjasto kirjasto = new Kirjasto();

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

        for (int i = 0; i < kirjasto.getKirjoja(); i++) {
            Kirja kirja = kirjasto.annaKirja(i);
            kirja.tulosta(System.out);
        }
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










package Kirjasto;

/**
 * Kirjat- luokka
 * @author Jovan Karmakka (jrkarmau)
 * @version 22.2.2021
 */
public class Kirjat {

    private static final int MAX_KIRJOJA = 20;
    private int lkm = 0;
    private Kirjat[] alkiot;
    

    
    /**
     * Pääohjelma kirjojen testaamiseksi
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kirjat kirjat = new Kirjat();
        Kirja kirja1 = new Kirja();
        Kirja kirja2 = new Kirja();
        
        kirja1.rekisteroi();
        kirja1.taytaKirjaTiedoilla();
        
        kirja2.rekisteroi();
        kirja2.taytaKirjaTiedoilla();
        
        kirjat.lisaa(kirja1);
        kirjat.lisaa(kirja2);
        
        for (int i = 0; i < kirjat.getLkm(); i++) {
            Kirja kirja = kirjat.anna(i);
            System.out.println("Kirjan indeksi: " + i);
            kirja.tulosta();
            // TODO: täydennä          
        }
        
        
        
        
        
        /**
         * @example
         * <pre name="test">
         * #THROWS SailoException
         * 
         * 
         * </pre>
         */
        public Kirjat() {
            alkiot = new Jasen[MAX_KIRJOJA];
        }
        
        
        
        
        
        public void lisaa(Kirja kirja) throws SailoException {
            
            if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
            
            alkiot[lkm] = kirja;
            lkm++;
        }
        
        
        
        public kirja anna(int i) {
            
            if (i< 0 || lkm <= i)
            
            return alkiot[i];
        }
        
        
        
        
        
        
    }
}

package kirjasto.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import kirjasto.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.03.11 10:25:18 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class KirjatTest {



  // Generated by ComTest BEGIN
  /** 
   * testLisaa78 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa78() throws SailoException {    // Kirjat: 78
    Kirjat kirjat = new Kirjat(); 
    Kirja kirja1 = new Kirja(), kirja2 = new Kirja(); 
    assertEquals("From: Kirjat line: 82", 0, kirjat.getLkm()); 
    kirjat.lisaa(kirja1); assertEquals("From: Kirjat line: 83", 1, kirjat.getLkm()); 
    kirjat.lisaa(kirja2); assertEquals("From: Kirjat line: 84", 2, kirjat.getLkm()); 
    kirjat.lisaa(kirja1); assertEquals("From: Kirjat line: 85", 3, kirjat.getLkm()); 
    assertEquals("From: Kirjat line: 86", kirja1, kirjat.anna(0)); 
    assertEquals("From: Kirjat line: 87", kirja2, kirjat.anna(1)); 
    assertEquals("From: Kirjat line: 88", kirja1, kirjat.anna(2)); 
    assertEquals("From: Kirjat line: 89", false, kirjat.anna(1) == kirja1); 
    assertEquals("From: Kirjat line: 90", true, kirjat.anna(1) == kirja2); 
    try {
    assertEquals("From: Kirjat line: 91", kirja1, kirjat.anna(3)); 
    fail("Kirjat: 91 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    kirjat.lisaa(kirja1); assertEquals("From: Kirjat line: 92", 4, kirjat.getLkm()); 
    kirjat.lisaa(kirja1); assertEquals("From: Kirjat line: 93", 5, kirjat.getLkm()); 
    Kirja kirja6 = new Kirja(); 
    kirjat.lisaa(kirja6); assertEquals("From: Kirjat line: 95", 6, kirjat.getLkm()); 
    assertEquals("From: Kirjat line: 96", 10, kirjat.taulukonKoko()); 
    kirjat.lisaa(kirja6); kirjat.lisaa(kirja6); kirjat.lisaa(kirja6); 
    kirjat.lisaa(kirja6); kirjat.lisaa(kirja6); 
    assertEquals("From: Kirjat line: 99", 20, kirjat.taulukonKoko()); 
  } // Generated by ComTest END
}
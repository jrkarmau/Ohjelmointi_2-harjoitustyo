package kirjasto.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * TestSuite kirjasto ohjelmalle
 * @author Jovan Karmakka (jrkarmau)
 * @version 21.4.2021
 */
@RunWith(Suite.class)
@SuiteClasses({
    KirjaTest.class,
    KirjatTest.class,
    KommentitTest.class,
    KommenttiTest.class })
public class AllTests {
    //
}

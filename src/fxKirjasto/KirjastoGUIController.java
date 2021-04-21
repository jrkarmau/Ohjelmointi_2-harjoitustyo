package fxKirjasto;

import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import kirjasto.Kirja;
import kirjasto.Kirjasto;
import kirjasto.Kommentti;
import kirjasto.SailoException;

/**
 * Hoitaa pääikkunaan liittyvät toiminnot
 * @author Jovan Karmakka (jrkarmau)
 * @version 21.4.2021
 */
public class KirjastoGUIController implements Initializable {
    
    @FXML private ListChooser<Kirja> chooserKirjat;
    @FXML private ListChooser<Kommentti> chooserKommentit;
    @FXML private ComboBoxChooser<String> hakuKentat;
    @FXML private TextField hakuehto;
    @FXML private TextField editNimi;
    @FXML private TextField editKirjailija;
    @FXML private TextField editKieli;
    @FXML private TextField editJulkaistu;
    @FXML private TextField editKustantaja;
    @FXML private TextField editISBN;
    @FXML private TextField editSivumaara;
    @FXML private TextField editGenre;
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    @FXML void handleHaku() {
        hae(0);
    }
        
    @FXML private void handleAvaa() {
        avaa();
    }

    @FXML private void handleLisaaKirja() {
        uusiKirja();
    }

    @FXML private void handleLisaaKommentti() {
        uusiKommentti();
    }   

    @FXML private void handleLopeta() {
        Platform.exit();
    }

    @FXML private void handleMuokkaaKirja() {
        muokkaaKirja();
    }

    @FXML private void handleMuokkaaKommentti() {
        muokkaaKommentti();
    }

    @FXML private void handlePoistaKirja() {
        poistaKirja();
    }

    @FXML private void handlePoistaKommentti() {
        poistaKommentti();
    }

    @FXML private void handleTallenna() {
        tallenna();
    }

    @FXML private void handleTietoja() {
        TietojaController.naytaTiedot();
    }

    @FXML private void handleTulosta() {
        TulostaController tulostusCtrl = TulostaController.tulosta(null);
        tulostaKirjat(tulostusCtrl.getTextArea());
    }
    
    @FXML private void handleTilastot() {
        TilastotController tilastotCtrl = TilastotController.naytaTilastot(null);
        laskeTilastot(tilastotCtrl.getTextArea());
    }

    
// ------------------------------------------------------------------------------------------------------------------------------------------


    private Kirjasto kirjasto;
    private Kirja kirjaKohdalla;
    private Kommentti kommenttiKohdalla;
    private String kirjastonNimi = "kirjasto";
    private TextField[] edits;
        
 
    /**
     * Tyhjentää kirja- ja kommenttivalitsimet sekä tekstikentät ja alustaa kuuntelijat ja tekstikenttätaulukon
     */
    private void alusta() {
        chooserKirjat.clear();
        chooserKirjat.addSelectionListener(e -> naytaKirja());
        chooserKommentit.addSelectionListener(e -> kommenttiKohdalla());
        chooserKirjat.setOnMouseClicked(e -> {if (e.getClickCount() > 1) muokkaaKirja(); });
        chooserKommentit.setOnMouseClicked(e -> {if (e.getClickCount() > 1) muokkaaKommentti(); }); 
        edits = new TextField[] {editNimi, editKirjailija, editKieli, editJulkaistu,
                                 editKustantaja, editISBN, editSivumaara, editGenre};
    }
    
    
    /**
     * Kysyy käyttäjältä varmistuksen ja poistaa valitun kirjan
     */
    private void poistaKirja() {
        Kirja kirja = kirjaKohdalla;
        if (kirja == null) return;
        if (!Dialogs.showQuestionDialog("Poisto", "Poistetaanko Kirja: " + kirja.getNimi(), "Kyllä", "Ei")) return;
        kirjasto.poistaKirja(kirja);
        int index = chooserKirjat.getSelectedIndex();
        hae(0);
        chooserKirjat.setSelectedIndex(index);
    }
    
    
    /**
     * Poistaa valitun kommentin
     */
    private void poistaKommentti() {
        if (kommenttiKohdalla == null) return;
        kirjasto.poistaKommentti(kommenttiKohdalla);
        naytaKommentit(kirjaKohdalla);
    }
    
    
    /**
     * Asettaa valitun kommenttin kohdalla olevaksi kommentiksi
     */
    private void kommenttiKohdalla() {
        kommenttiKohdalla = chooserKommentit.getSelectedObject();      
    }
    
    
    /**
     * Näyttää valitun kirjan tiedot tietokentissä sekä näyttää kirjan kommentit
     */
    private void naytaKirja() {        
        kirjaKohdalla = chooserKirjat.getSelectedObject();        
        if (kirjaKohdalla == null) return;
        LisaaKirjaController.naytaKirja(edits, kirjaKohdalla);
        naytaKommentit(kirjaKohdalla);
    }
    

    /**
     * Näyttää kirjan kommentit valitsimessa
     * @param kirja jonka kommentit näytetään
     */
    private void naytaKommentit(Kirja kirja) {
        chooserKommentit.clear();
        if (kirja == null) return;
        List<Kommentti> kommentit = kirjasto.annaKommentit(kirja);
        if (kommentit.size() == 0) return;
        for (Kommentti kom : kommentit) {
            chooserKommentit.add(kom.getOtsikko(), kom);
        }
    }
    
    
    /**
     * Avaa kirjan muokkausdialogin ja päivittää uudet tiedot kirjaan.
     * jos kirjaa ei ole enää olemassa luo uuden kirjan
     */
    private void muokkaaKirja() {
        if (kirjaKohdalla == null) return;
        Kirja kirja;
        
        try {
            kirja = kirjaKohdalla.clone();
            kirja = LisaaKirjaController.kysyKirja(null, kirja);
            if (kirja == null) return;
            kirjasto.korvaaTaiLisaa(kirja);
            hae(kirja.getKirjanID());
        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage() + " kloonaus ei onnistu");
        } catch (SailoException se) {
            System.err.println(se.getMessage() + " Ongelmia tietorakenteessa");
        }
    }
    
    /**
     * Aukaisee kommentin muokkausdialogin ja päivittää kommentin tiedot
     */
    private void muokkaaKommentti() {
        if (kommenttiKohdalla == null) return;
        Kommentti kommentti;

        try {
            kommentti = kommenttiKohdalla.clone();
            kommentti = LisaaKommenttiController.kysyKommentti(null, kommentti);
            if (kommentti == null) return;
            kirjasto.korvaaTaiLisaa(kommentti);
            naytaKommentit(kirjaKohdalla);
        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage() + " kloonaus ei onnistu");
        } catch (SailoException se) {
            System.err.println(se.getMessage() + " Ongelmia tietorakenteessa");
        }
    }


    /**
     * Alustaa kirjaston lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta kirjaston tiedot luetaan
     * @return false jos epäonnistuu true jos onnistuu
     */
    public Boolean lueTiedosto(String nimi) {
        if (!kirjasto.tarkistaTiedosto(nimi)) {
            return false;
        }
        kirjastonNimi = nimi;
        try {
            kirjasto.lueTiedostosta(nimi);
            hae(0);
            return true;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage();
            if (virhe != null)
                Dialogs.showMessageDialog(virhe);
            return false;
        }
    }

    
    /**
     * Tallentaa jos tehtyjä muutoksia
     * @return null jos onnistuu, muuten virhe-ilmoitus
     */
    private String tallenna() {
        try {
            kirjasto.tallenna();
            return null;
        } catch ( SailoException e) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + e.getMessage());
            return e.getMessage();
        }
    }
    
    
    /**
     * Lisää kirjastoon uuden kirjan ja päivittää kirjavalitsimen
     */
    private void uusiKirja() {
        try {
            Kirja uusi = new Kirja();
            uusi = LisaaKirjaController.kysyKirja(null, uusi);
            if (uusi == null) return;
            uusi.rekisteroi();
            kirjasto.lisaa(uusi);
            hae(uusi.getKirjanID());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden kirjan luomisessa " + e.getMessage());
        }
    }
    
    
    /**
     * Lisää kirjaan uuden kommentin ja päivittää kommenttivalitsimen
     */
    public void uusiKommentti() {  
        if (kirjaKohdalla == null) return;
        Kommentti uusi  = new Kommentti();
        uusi =  LisaaKommenttiController.kysyKommentti(null, uusi);
        if (uusi == null) return;
        uusi.rekisteroi();
        uusi.setKirjanID(kirjaKohdalla.getKirjanID());
        kirjasto.lisaa(uusi);
        naytaKommentit(kirjaKohdalla);
    }
    
    
    /**
     * Hakee kirjat valitsimeen hakusanan ja hakuehdon perusteella
     * @param kirjanNumero oletuskirjan numero
     */
    private void hae(int kirjanNumero) {
        int kirjanro = kirjanNumero;
        if (kirjanro == 0) {
            Kirja kohdalla = kirjaKohdalla;
            if (kohdalla != null) kirjanro = kohdalla.getKirjanID();
        }

        int kentanNumero = hakuKentat.getSelectionModel().getSelectedIndex();
        String hakusana = hakuehto.getText();
        if (hakusana.indexOf('*') < 0) hakusana = "*" + hakusana + "*";

        chooserKirjat.clear();

        int indeksi = 0;
        ArrayList<Kirja> kirjat;
        try {
            kirjat = kirjasto.etsi(hakusana, kentanNumero);
            for (int i = 0; i < kirjat.size(); i++) {
                Kirja kirja = kirjat.get(i);
                if (kirja.getKirjanID() == kirjanro) indeksi = i;
                chooserKirjat.add(kirja.getNimi(), kirja);
            }
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Kirjojen haussa ongelmia");
        }
        chooserKirjat.setSelectedIndex(indeksi);
    }

    
    /**
     * Asetetaan käytettävän kirjaston 
     * @param kirjasto jota käytetään 
     */
    public void setKirjasto(Kirjasto kirjasto) {
        this.kirjasto = kirjasto;
    }
    
    
    /**
     * Tyhjentää kaikki kentät ja kysyy uuden kirjaston. Jos kirjastoa ei löydy
     * kysyy tehdäänkö uusi kirjasto
     * @return true jos avaus onnistuu false jos ei haluta luoda uutta kirjastoa
     */
    public boolean avaa() {
        chooserKirjat.clear();
        chooserKommentit.clear();
        for (TextField field : edits) {
            field.clear();
        }
        String uusinimi = AloitusController.kysyNimi(null, kirjastonNimi);
        if (uusinimi == null) return false;
        return lueTiedosto(uusinimi);
    }
    
    
    /**
     * Tulostaa kirjat kirjavalitsimesta
     * @param tulostusAlue alue johon kirjan tiedot tulostetaan
     */
    private void tulostaKirjat(TextArea tulostusAlue) {
        try (PrintStream ps = TextAreaOutputStream.getTextPrintStream(tulostusAlue)) {
            ps.println("Tulostetaan kaikki kirjat");
            for (Kirja kirja : chooserKirjat.getObjects()) {
                tulosta(ps, kirja);
                ps.println("");
            }
        }
    }
    
    
    /**
     * Laskee kirjastossa olevien kirjojen tilastoja
     * @param tArea alue johon tilastot tulostetaan
     */
    private void laskeTilastot(TextArea tArea) {
        try(PrintStream ps = TextAreaOutputStream.getTextPrintStream(tArea)) {
            ps.println("Kirjastosi tilastot:\n");  
            kirjasto.laskeTilastot(ps);
        }
    }
    
    
    /**
     *  Tulostaa yhden kirjan tiedot tulostusalueelle
     *  @param ps tietovirta johon tulostetaan
     *  @param kirja jonka tiedot tulostetaan
     */
    private void tulosta(PrintStream ps, Kirja kirja) {
        ps.println("----------------------------------------------");
        kirja.tulosta(ps);

        List<Kommentti> kirjanKommentit = kirjasto.annaKommentit(kirja);
        for (Kommentti kom : kirjanKommentit) {
            kom.tulosta(ps);
        }
    }
}
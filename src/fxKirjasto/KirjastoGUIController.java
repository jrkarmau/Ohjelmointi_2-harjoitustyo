package fxKirjasto;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import kirjasto.Kirja;
import kirjasto.Kirjasto;
import kirjasto.Kommentti;
import kirjasto.SailoException;

/**
 * Hoitaa pääikkunaan liittyvät toiminnot
 * @author Jovan Karmakka (jrkarmau)
 * @version 15.2.2021
 *
 */
public class KirjastoGUIController implements Initializable {
    
    @FXML private ListChooser<Kirja> chooserKirjat;
    @FXML private ListChooser<Kommentti> chooserKommentit;
    @FXML private TextField editNimi;
    @FXML private TextField editKirjailija;
    @FXML private TextField editKieli;
    @FXML private TextField editJulkaistu;
    @FXML private TextField editKustantaja;
    @FXML private TextField editISBN;
    @FXML private TextField editSivumaara;
    @FXML private TextField editGenre;
    
    
    /**
     * TODO: väliaikainen alustus
     * Kutsuu alusta metodia 
     * @param url url
     * @param bundle bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }

    @FXML private void handleAvaa() {
        Dialogs.showMessageDialog("Vielä ei osata avata tiedostoa");
    }

    @FXML private void handleLisaaKirja() {
        // ModalController.showModal(KirjastoGUIController.class.getResource("LisaaKirjaDialog.fxml"), "Lisää kirja", null, "");
        uusiKirja();
    }

    @FXML private void handleLisaaKommentti() {
        //ModalController.showModal(KirjastoGUIController.class.getResource("LisaaKommenttiDialog.fxml"), "Lisää kommentti", null, "");
        uusiHarrastus();
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
        Dialogs.showMessageDialog("Vielä ei osata poistaa kirjaa");
    }

    @FXML private void handlePoistaKommentti() {
        Dialogs.showMessageDialog("Vielä ei osata poistaa kommenttia");
    }

    @FXML private void handleTallenna() {
        tallenna();
    }

    @FXML private void handleTietoja() {
        ModalController.showModal(KirjastoGUIController.class.getResource("TietojaView.fxml"), "Tietoja", null, "");
    }

    @FXML private void handleTulosta() {
        ModalController.showModal(KirjastoGUIController.class.getResource("TulostaView.fxml"), "Tulosta", null, "");
    }

    
// oma koodi alkaa ------------------------------------------------------------------------------------------------------------------------------------------

    private Kirjasto kirjasto;
    private Kirja kirjaKohdalla;
    private String kirjastonNimi = "";  // TODO: käytä
    private TextField[] edits;
        
 
    /**
     * Tyhjentää kirja ja kommenttivalitsimet sekä tekstikentät 
     */
    private void alusta() {        
        chooserKirjat.clear();
        chooserKirjat.addSelectionListener(e -> naytaKirja());
        edits = new TextField[] {editNimi, editKirjailija, editKieli, editJulkaistu,
                                 editKustantaja, editISBN, editSivumaara, editGenre};
    }
    
    
    private void naytaKirja() {        
        kirjaKohdalla = chooserKirjat.getSelectedObject();        
        if (kirjaKohdalla == null) return;
        LisaaKirjaController.naytaKirja(edits, kirjaKohdalla);
        naytaKommentit(kirjaKohdalla);
    }
    
    
    private void naytaKommentit(Kirja kirja) {
        chooserKommentit.clear();
        if (kirja == null) return;
        List<Kommentti> kommentit = kirjasto.annaKommentit(kirja); 
        if (kommentit.size() == 0) return;
        for (Kommentti kom : kommentit) {
            chooserKommentit.add(kom.getOtsikko(), kom);
        }
    }
    
    
    private void muokkaaKirja() {
        LisaaKirjaController.kysyKirja(null, kirjaKohdalla);
    }
    
    
    private void muokkaaKommentti() {
        ModalController.showModal(KirjastoGUIController.class.getResource("LisaaKommenttiDialog.fxml"), "Muokkaa kommenttia", null, "");
    }
    

    /**
     * Alustaa kirjaston lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta kirjaston tiedot luetaan
     * @return null jo sonnistuu, muuten virhe-viesti
     */
    protected String lueTiedosto(String nimi) {
        kirjastonNimi = nimi;
        //setTitle("Kirjastosi " + kirjastonNimi);  TODO: aseta tittle
        try {
            kirjasto.lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage();
            if (virhe != null) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
    }
    
    
    /**
     * Tietojen tallennus
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
     * Lisätään Kirjastoon uusi kirja
     */
    private void uusiKirja() {
        Kirja kirja1 = new Kirja();
        kirja1.rekisteroi();
        kirja1.taytaKirjanTiedot(); //TODO: korvataan dialogilla
        try {
            kirjasto.lisaa(kirja1);
        } catch (SailoException e) {
          Dialogs.showMessageDialog("ongelmia uuden luomisessa " + e.getMessage());
          return;
        }
        hae(kirja1.getKirjanID());
    }
    
    
    /**
     * Luo uuden harrastuksen testaamista varten
     */
    public void uusiHarrastus() {
        if (kirjaKohdalla == null) return;
        Kommentti kom = new Kommentti();
        kom.rekisteroi();
        kom.taytaKommentinTiedot(kirjaKohdalla.getKirjanID());
        kirjasto.lisaa(kom);
        hae(kirjaKohdalla.getKirjanID()); 
        
    }
    
    
    private void hae(int kirjanro) {        
        chooserKirjat.clear();
        
        int indeksi = 0;
        for (int i = 0; i < kirjasto.getKirjoja(); i++) {
            Kirja kirja = kirjasto.annaKirja(i);
            if (kirja.getKirjanID() == kirjanro) indeksi = i;
            chooserKirjat.add(kirja.getNimi(), kirja);
        }
        chooserKirjat.setSelectedIndex(indeksi);  // tästä tulee muutosviesti
    }
    
    
    /**
     * Asetetaan käytettävä kerho
     * @param kirjasto jota käytetään 
     */
    public void setKirjasto(Kirjasto kirjasto) {
        this.kirjasto = kirjasto;
    }
}

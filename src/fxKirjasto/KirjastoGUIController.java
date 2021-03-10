package fxKirjasto;


import java.io.PrintStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
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
    @FXML private ScrollPane panelKirja;             // väliaikainen
    
    
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

    @FXML private void handleMuokkaaKommentti() {
        ModalController.showModal(KirjastoGUIController.class.getResource("LisaaKommenttiDialog.fxml"), "Lisää kommentti", null, "");
    }

    @FXML private void handlePoistaKirja() {
        Dialogs.showMessageDialog("Vielä ei osata poistaa kirjaa");
    }

    @FXML private void handlePoistaKommentti() {
        Dialogs.showMessageDialog("Vielä ei osata poistaa kommenttia");
    }

    @FXML private void handleTallenna() {
        Dialogs.showMessageDialog("Vielä ei osata tallentaa");
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
    
    private TextArea areaKirja = new TextArea();  // TODO: väliaikainen
    
 
    /**
     * Luo tilapäisesti väliaikaisen tekstikentän
     * TODO: väliaikainen myös väliaikainen pane kirjastoguiview tiedostossa
     */
    private void alusta() {
        panelKirja.setContent(areaKirja);
        areaKirja.setFont(new Font("Courier New", 12));
        panelKirja.setFitToHeight(true);
        
        chooserKirjat.clear();
        chooserKirjat.addSelectionListener(e -> naytaKirja());
    }
    
    
    private void naytaKirja() {        
        kirjaKohdalla = chooserKirjat.getSelectedObject();        
        if (kirjaKohdalla == null) return;
        
        areaKirja.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaKirja)) {
            tulosta(os, kirjaKohdalla);
        }
    }
    
    
    /**
     * tulostaa kirjan tiedot sekä kommentit
     * @param os tietovirta johon tulostetaan 
     * @param kirja kirja jonka tiedot tulostetaan
     */
    private void tulosta(PrintStream os, Kirja kirja) {
        os.println("-------------------- kirja ------------------------------");
        kirja.tulosta(os);
        os.println("------------------ kommentit --------------------------------");
        List<Kommentti> kommentit = kirjasto.annaKommentit(kirja);
        for (Kommentti kom : kommentit) {
            kom.tulosta(os);
        }
        os.println("--------------------------------------------------");
        
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

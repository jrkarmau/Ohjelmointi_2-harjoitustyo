package fxKirjasto;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kirjasto.Kommentti;

/**
 * Hoitaa kommentin lisäämiseen ja muokkaamiseen liittyvät toiminnot
 * @author Jovan Karmakka (jrkarmau)
 * @version 21.4.2021
 */
public class LisaaKommenttiController implements ModalControllerInterface<Kommentti>, Initializable {
    
    @FXML private TextField otsikko;
    @FXML private TextArea teksti;
    private Kommentti kommenttiKohdalla;

    
    @FXML private void handlePeru() {
        kommenttiKohdalla = null;
        ModalController.closeStage(otsikko);
    }

    /**
     * Asettaa uudet tekstit kommentin kenttiin ja sulkee ikkunan
     */
    @FXML private void handleTallenna() {
        kommenttiKohdalla.setOtsikko(otsikko.getText());       
        kommenttiKohdalla.setTeksti(teksti.getText());
        ModalController.closeStage(otsikko);
    }

    
    @Override
    public Kommentti getResult() {
        return kommenttiKohdalla;
    }

    
    @Override
    public void handleShown() {
        otsikko.requestFocus();
        
    }
    

    @Override
    public void setDefault(Kommentti oletus) {
        kommenttiKohdalla = oletus;
        naytaKommentti(kommenttiKohdalla);
    }
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       // Auto-generated method stub
    }
    
    
    /**
     * Näyttää kommentin 
     * @param kommentti jota muokataan
     */
    public void naytaKommentti(Kommentti kommentti) {
        if (kommentti == null) return;
        otsikko.setText(kommentti.getOtsikko());
        teksti.setText(kommentti.getTeksti());
        }
    
    
    /**
     * Luodaan kommentin tietojen kysymys tai muokkausdialogi ja palautetaan sama tietue.
     * @param modalitystage stage jolle ollaan modaalisia null == sovellukselle
     * @param oletusTieto kommentti jonka tietoja käsitellään
     * @return null jos painetaan peru muuten tietue
     */
    public static Kommentti kysyKommentti(Stage modalitystage, Kommentti oletusTieto) {
        return ModalController.showModal(KirjastoGUIController.class.getResource("LisaaKommenttiDialog.fxml"), "Kommentti", modalitystage, oletusTieto);
    } 
}
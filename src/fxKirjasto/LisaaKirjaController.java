package fxKirjasto;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kirjasto.Kirja;


/**
 * Hoitaa kirjan lisäämiseen liittyvät toiminnot
 * @author Jovan Karmakka (jrkarmau)
 * @version 15.2.2021
 *
 */
public class LisaaKirjaController implements ModalControllerInterface<Kirja>, Initializable {
    
    private Kirja kirjaKohdalla;
    @FXML private Label labelVirhe;
    @FXML private TextField editNimi;
    @FXML private TextField editKirjailija;
    @FXML private TextField editKieli;
    @FXML private TextField editJulkaistu;
    @FXML private TextField editKustantaja;
    @FXML private TextField editISBN;
    @FXML private TextField editSivumaara;
    @FXML private TextField editGenre;
    
    
    @FXML private void handlePeru() {
        ModalController.closeStage(labelVirhe);
    }

    @FXML private void handleTallenna() {
        ModalController.closeStage(labelVirhe);
    }
    
    @Override
    public Kirja getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        editNimi.requestFocus();
        
    }

    @Override
    public void setDefault(Kirja oletus) {
        kirjaKohdalla = oletus;
        naytaKirja(kirjaKohdalla);     
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
    }   
    
    
    
    /**
     * Luodaan kirjan tietojen kysymys tai muokkausdialogi ja palautetaan sama tietue.
     * @param modalitystage stage jolle ollaan modaalisia null == sovellukselle
     * @param oletusTieto kirja jonka tietoja käsitellään
     * @return null jos painetaan Cancel muuten tietue
     */
    public static Kirja kysyKirja(Stage modalitystage, Kirja oletusTieto) {
      return ModalController.showModal(KirjastoGUIController.class.getResource("LisaaKirjaDialog.fxml"), "Muokkaa kirjaa", modalitystage, oletusTieto);
    }
    
    
    private void naytaKirja(Kirja kirja) {
        if (kirja == null) return;
        editNimi.setText(kirja.getNimi());
        editKirjailija.setText()      
        editKieli.setText()           
        editJulkaistu.setText()       
        editKustantaja.setText()      
        editISBN.setText()            
        editSivumaara.setText()       
        editGenre.setText()           
    }

    

}

package fxKirjasto;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * hoitaa aloitusikkunaan liittyvät toiminnot
 * @author Jovan Karmakka (jrkarmau)
 * @version 15.2.2021
 *
 */
public class AloitusController implements ModalControllerInterface<String> {
    
    @FXML private TextField kirjastonNimi;
    private String vastaus = null;
    
    @FXML private void handleCancel() {
        vastaus = null;
        ModalController.closeStage(kirjastonNimi);
    }
    

    @FXML private void handleOK() {
        vastaus = kirjastonNimi.getText();
        ModalController.closeStage(kirjastonNimi);
    }
    

    @Override
    public String getResult() {
        return vastaus;
    }
    

    @Override
    public void handleShown() {
        kirjastonNimi.requestFocus();
        
    }
    

    @Override
    public void setDefault(String oletus) {
        kirjastonNimi.setText(oletus);
    }        
    
    /**
     * Avaa kirjaston nimen kysymysikkunan
     * @param modalityStage kelle ollaan modaalisia
     * @param oletus kirjaston oletusnimi
     * @return null jos painetaan cancel muuten kirjoitettu uusi nimi
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(AloitusController.class.getResource("AloitusView.fxml"), "Kirjasto", modalityStage, oletus); 
    }
}

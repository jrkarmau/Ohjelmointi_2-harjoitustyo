package fxKirjasto;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


/**
 * Hoitaa tulostamiseen liittyvät toiminnot
 * @author Jovan Karmakka (jrkarmau)
 * @version 15.2.2021
 */
public class TulostaController implements ModalControllerInterface<String> {
    
    @FXML private Button okButton;
    

    @FXML private void handleOK() {
        ModalController.closeStage(okButton);
    }
    

    @FXML private void handleTulosta() {
        Dialogs.showMessageDialog("Vielä ei osata tulostaa");
    }
    

    @Override
    public String getResult() {
        // Auto-generated method stub
        return null;
    }

    
    @Override
    public void handleShown() {
        // Auto-generated method stub  
    }

    
    @Override
    public void setDefault(String oletus) {
        // Auto-generated method stub
    }
    
    
    /**
     * Avaa tulostusikkunan
     */
    public static void naytaTulosta() {
        ModalController.showModal(KirjastoGUIController.class.getResource("TulostaView.fxml"), "Tulosta", null, "");
    }
}

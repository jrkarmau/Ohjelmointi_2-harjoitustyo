package fxKirjasto;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;

/**
 * hoitaa aloitusikkunaan liittyvät toiminnot
 * @author Jovan Karmakka (jrkarmau)
 * @version 15.2.2021
 *
 */
public class AloitusController implements ModalControllerInterface<String> {
    
    @FXML private void handleCancel() {
        Dialogs.showMessageDialog("Vielä ei osata sulkea");
    }
    

    @FXML private void handleOK() {
        Dialogs.showMessageDialog("Vielä ei osata aloittaa pääohjelmaa");
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
}

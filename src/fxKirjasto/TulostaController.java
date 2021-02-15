package fxKirjasto;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;

/**
 * Hoitaa tulostamiseen liittyvät toiminnot
 * @author Jovan Karmakka (jrkarmau)
 * @version 15.2.2021
 *
 */
public class TulostaController implements ModalControllerInterface<String> {

    @FXML private void handleOK() {
        Dialogs.showMessageDialog("Vielä ei osata sulkea ikkunaa");
    }

    @FXML private void handleTulosta() {
        Dialogs.showMessageDialog("Vielä ei osata tulostaa");
    }

    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(String oletus) {
        // TODO Auto-generated method stub
        
    }

}

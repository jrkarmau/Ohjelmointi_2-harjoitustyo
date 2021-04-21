package fxKirjasto;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;


/**
 * Hoitaa tilastojen näyttämiseen liittyvät toiminnot
 * @author Jovan Karmakka (jrkarmau)
 * @version 15.2.2021
 */
public class TilastotController implements ModalControllerInterface<String> {
    
    @FXML private Button okButton;
    @FXML private TextArea tilastoAlue;
    

    @FXML private void handleOK() {
        ModalController.closeStage(okButton);
    }


    @Override
    public String getResult() {
        return null;
    }

    
    @Override
    public void handleShown() {
        // Auto-generated method stub  
    }

    
    @Override
    public void setDefault(String oletus) {
        tilastoAlue.setText(oletus);
    }
    

    /**
     * @return alue johon tulostetaan
     */
    public TextArea getTextArea() {
        return tilastoAlue;
    }

    
    /**
     * Luo ja näyttää tilastoalueessa tekstin
     * @param tilastot tulostettava teksti
     * @return tilastot controller jos tarvitaan pyytää lisää tietoa
     */
    public static TilastotController naytaTilastot(String tilastot) {
        TilastotController tilastotCtrl = ModalController.showModeless(TilastotController.class.getResource("TilastotView.fxml"), "Tilastot", tilastot);
        return tilastotCtrl;
    }
}

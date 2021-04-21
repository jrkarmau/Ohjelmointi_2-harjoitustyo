package fxKirjasto;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

/**
 * Hoitaa tietoja ikkunaan liittyvät toiminnot
 * @author Jovan Karmakka (jrkarmau)
 * @version 21.4.2021
 *
 */
public class TietojaController implements ModalControllerInterface<String> {
    
    @FXML private Hyperlink helpLinkki;
    
    
    /**
     * Avaa työn suunnitelmasivun uuteen ikkunaan
     */
    @FXML void handleApua() {
        try {
            Desktop.getDesktop().browse(new URL("https://tim.jyu.fi/view/kurssit/tie/ohj2/2021k/ht/jrkarmau").toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    

    @FXML private void handleOK() {
        ModalController.closeStage(helpLinkki);
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
     * Aukaisee tiedot ikkunan
     */
    public static void naytaTiedot() {
        ModalController.showModal(KirjastoGUIController.class.getResource("TietojaView.fxml"), "Tietoja", null, "");
    }
}

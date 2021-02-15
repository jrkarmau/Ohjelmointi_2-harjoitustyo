package fxKirjasto;


import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;

/**
 * Hoitaa pääikkunaan liittyvät toiminnot
 * @author Jovan Karmakka (jrkarmau)
 * @version 15.2.2021
 *
 */
public class KirjastoGUIController {

    @FXML private void handleAvaa() {
        Dialogs.showMessageDialog("Vielä ei osata avata tiedostoa");
    }

    @FXML private void handleLisaaKirja() {
        ModalController.showModal(KirjastoGUIController.class.getResource("LisaaKirjaDialog.fxml"), "Lisää kirja", null, "");
    }

    @FXML private void handleLisaaKommentti() {
        ModalController.showModal(KirjastoGUIController.class.getResource("LisaaKommenttiDialog.fxml"), "Lisää kommentti", null, "");
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
}

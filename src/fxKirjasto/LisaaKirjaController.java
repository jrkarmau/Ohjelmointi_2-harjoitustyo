package fxKirjasto;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kirjasto.Kirja;

/**
 * Hoitaa kirjan lisäämiseen liittyvät toiminnot
 * @author Jovan Karmakka (jrkarmau)
 * @version 15.2.2021
 */
public class LisaaKirjaController implements ModalControllerInterface<Kirja>, Initializable {

    private Kirja kirjaKohdalla;
    @FXML private TextField labelVirhe;
    @FXML private TextField editNimi;
    @FXML private TextField editKirjailija;
    @FXML private TextField editKieli;
    @FXML private TextField editJulkaistu;
    @FXML private TextField editKustantaja;
    @FXML private TextField editISBN;
    @FXML private TextField editSivumaara;
    @FXML private TextField editGenre;
    private TextField[] edits;

    
    @FXML private void handlePeru() {
        kirjaKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }


    @FXML private void handleTallenna() {
        if (kirjaKohdalla != null && kirjaKohdalla.getNimi().trim().equals("")) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }


    @Override
    public Kirja getResult() {
        return kirjaKohdalla;
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
        alusta();

    }


    /**
     * Alustaa uuden textfield taulukon käynnistyessä
     */
    private void alusta() {
        edits = new TextField[] { editNimi, editKirjailija, editKieli,
                editJulkaistu, editKustantaja, editISBN, editSivumaara,
                editGenre };
        int i = 0;
        for (TextField edit : edits) {
            final int k = i++;
            edit.setOnKeyReleased(e -> kasitteleMuutosKirjaan(k,
                    (TextField) (e.getSource())));
        }
    }


    /**
     * käsittelee jäsenen kenttään tullut muutos
     * @param k kentän numero
     * @param edit muuttunut kenttä
     */
    private void kasitteleMuutosKirjaan(int k, TextField edit) {
        if (kirjaKohdalla == null)
            return;
        String s = edit.getText();
        String virhe = null;
        virhe = kirjaKohdalla.setKentta(k, s);

        if (virhe == null) {
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit, virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }


    /**
     * Näyttää virheen dialogissa
     * @param virhe virheen teksti
     */
    private void naytaVirhe(String virhe) {
        if (virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }


    /**
     * Luodaan kirjan tietojen kysymys tai muokkausdialogi ja palautetaan sama tietue.
     * @param modalitystage stage jolle ollaan modaalisia null == sovellukselle
     * @param oletusTieto kirja jonka tietoja käsitellään
     * @return null jos painetaan Cancel muuten tietue
     */
    public static Kirja kysyKirja(Stage modalitystage, Kirja oletusTieto) {
        return ModalController.showModal(
                KirjastoGUIController.class
                        .getResource("LisaaKirjaDialog.fxml"),
                "Muokkaa kirjaa", modalitystage, oletusTieto);
    }


    /**
     * Näyttää kirjan tiedot editkentissä
     * @param kirja kirja jonka tiedot näytetään
     */
    private void naytaKirja(Kirja kirja) {
        naytaKirja(edits, kirja);
    }


    /**
     * Lisätään kirjan tiedot taulukolliseen edit kenttiä
     * @param edits editkentät joihin tietotallennetaan
     * @param kirja kirja jonka tiedot näytetään
     */
    public static void naytaKirja(TextField[] edits, Kirja kirja) {
        if (kirja == null)
            return;
        for (int i = 0; i < edits.length; i++) {
            edits[i].setText(kirja.getKentta(i));
        }
    }
}

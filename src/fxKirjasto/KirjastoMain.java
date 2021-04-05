package fxKirjasto;
	
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Application;
import javafx.stage.Stage;
import kirjasto.Kirjasto;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * Kirjasto sovelluksen pääohjelma
 * käynnistää aloitusikkunan
 * @author Jovan Karmakka (jrkarmau)
 * @version 26.1.2021
 *
 */
public class KirjastoMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("KirjastoGUIView.fxml"));
		    final FXMLLoader ldr = new FXMLLoader(getClass().getResource("KirjastoGUIView.fxml"));  // korvattu ylempi rivi tällä
		    final Pane root = (Pane)ldr.load();                                                     // lisätty
		    final KirjastoGUIController kirjastoCtrl = (KirjastoGUIController)ldr.getController();  // lisätty
			final Scene scene = new Scene(root);                                                    // lisätty final
			scene.getStylesheets().add(getClass().getResource("kirjasto.css").toExternalForm());
			primaryStage.setScene(scene);			
			primaryStage.setTitle("Kirjasto");
			
			
			Kirjasto kirjasto = new Kirjasto();
			kirjastoCtrl.setKirjasto(kirjasto);
			
			
	        ModalController.showModal(KirjastoGUIController.class.getResource("AloitusView.fxml"), "Valitse kirjasto", null, "");	//väliaikainen	TODO: kunnollinen aloitussivun aukaisu	
	        kirjastoCtrl.lueTiedosto("kirjasto");
	        primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

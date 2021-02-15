package fxKirjasto;
	
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("KirjastoGUIView.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("kirjasto.css").toExternalForm());
			primaryStage.setScene(scene);			
			primaryStage.setTitle("Kirjasto");
			
	        ModalController.showModal(KirjastoGUIController.class.getResource("AloitusView.fxml"), "Valitse kirjasto", null, "");	//väliaikainen	TODO: kunnollinen aloitussivun aukaisu	
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

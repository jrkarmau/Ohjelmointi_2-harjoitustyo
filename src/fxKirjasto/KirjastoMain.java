package fxKirjasto;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import kirjasto.Kirjasto;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

/**
 * Kirjasto sovelluksen pääohjelma
 * käynnistää aloitusikkunan kirjaston kysymistä varten
 * @author Jovan Karmakka (jrkarmau)
 * @version 21.4.2021
 *
 */
public class KirjastoMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			final FXMLLoader ldr = new FXMLLoader(getClass().getResource("KirjastoGUIView.fxml")); 		
			final Pane root = (Pane)ldr.load();                                                     
		    final KirjastoGUIController kirjastoCtrl = (KirjastoGUIController)ldr.getController();  
			final Scene scene = new Scene(root);                                                    
			scene.getStylesheets().add(getClass().getResource("kirjasto.css").toExternalForm());
			primaryStage.setScene(scene);	
	        primaryStage.setTitle("Kirjasto");

			Kirjasto kirjasto = new Kirjasto();
			kirjastoCtrl.setKirjasto(kirjasto);

			if (!kirjastoCtrl.avaa()) Platform.exit();
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

package Model;

//

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	@Override
	public void start(Stage stage) 
	{
		try {
			   Parent root = FXMLLoader.load(getClass().getResource("/Interfaces/InicioSesion.fxml"));
			   Scene scene = new Scene(root);
			   stage.setScene(scene);
			   stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}	
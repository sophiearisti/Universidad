package Model;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Alerta {

	public static boolean confirmar(String titulo, String header, String content) 
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(header);
		alert.setContentText(content);
		
		Optional<ButtonType> result = alert.showAndWait();
		 if(result.get()== ButtonType.OK) {
			 return true;
		 }else {
			 return false;
		 }
	}
	
	
	public static void warnning(String titulo, String header, String content) 
	{
		Alert alert =new Alert(AlertType.WARNING);
		alert.setTitle(titulo);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();	
	}
}

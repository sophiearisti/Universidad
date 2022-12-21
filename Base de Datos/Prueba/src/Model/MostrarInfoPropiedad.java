package Model;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MostrarInfoPropiedad implements Initializable
{
    public static int idCliente;
    
    public static int idPropiedad;
	
	public static void idClientePropiedad(Integer id, Integer propiedad)
	{
		idCliente=id;
		
		idPropiedad=propiedad;
	}
	
	@FXML
	private Text tipo;
	 
	 @FXML
	private Text pais;
	
	 @FXML
	private Text departamento;
	 
	 @FXML
	private Text municipio;
	 
	 @FXML
	private Text cantHab;
	 
	 @FXML
	private Text renta;
	 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		String infoTodo="select tp.TIPO as tipo,todo.CANT_HABITACIONES as canthab,todo.FECHA,todo.RENTA as rent,todo.ID_PROPIEDADES as propiedadess,todo.pais as country,todo.DEPARTAMENTO as dept,todo.MUNICIPIO as mun,todo.ID_DUENO,todo.DESCRIPCION,todo.ESTADO,todo.IMAGEN "
				+ "from TIPO_PROPIEDADES TP join "
				+ "(select c.ID_TIPO,c.CANT_HABITACIONES,c.FECHA,c.RENTA,propiedad.ID_PROPIEDADES,propiedad.pais,propiedad.DEPARTAMENTO,propiedad.MUNICIPIO,propiedad.ID_DUENO,propiedad.DESCRIPCION,propiedad.ESTADO,propiedad.IMAGEN "
				+ "from CARACTERISTICAS c join "
				+ "(select p.ID_PROPIEDADES,lugar.pais,lugar.DEPARTAMENTO,lugar.MUNICIPIO,p.ID_DUENO,p.DESCRIPCION,p.ESTADO,p.IMAGEN,p.ID_CARACTERISTICAS "
				+ "from propiedades p join "
				+ "(select l.ID_LOCALIZACION, u.pais,l.DEPARTAMENTO,l.MUNICIPIO "
				+ " from localizacion l join ubicacion u on l.ID_UBICACION_PAIS=u.ID_UBICACION) lugar "
				+ " on lugar.ID_LOCALIZACION=p.ID_LOCALIZACION) propiedad "
				+ " on propiedad.ID_CARACTERISTICAS=c.IDCARACTERISTICAS) todo "
				+ " on todo.ID_TIPO=TP.IDTIPO "
				+ " where ID_PROPIEDADES=?";
		
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
				PreparedStatement ps=conex.prepareStatement(infoTodo))
		{
			ps.setInt(1, idPropiedad);
			ResultSet rs=ps.executeQuery();
			rs.next();
			
			tipo.setText(rs.getString("tipo"));
			
			pais.setText(rs.getString("country"));
			
			departamento.setText(rs.getString("dept"));
			
			municipio.setText(rs.getString("mun"));
			
			cantHab.setText(Integer.toString( rs.getInt("canthab")));
			
			renta.setText(Float.toString(rs.getFloat("rent")));
			
			
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void devolverBuscador(ActionEvent event) throws IOException 
	{
		root = FXMLLoader.load(getClass().getResource("/Interfaces/Aceptado.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void siguiente(ActionEvent event) throws IOException 
	{
		root = FXMLLoader.load(getClass().getResource("/Interfaces/cantinquilinos.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	

	

}

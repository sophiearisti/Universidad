package Model;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CrearPropiedad implements Initializable{
	
	@FXML
	private ComboBox<String> ubicacion;
	ObservableList<String> PaisList= FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> Municipio;
	ObservableList<String> MuniList= FXCollections.observableArrayList();
	@FXML
	private ComboBox<String> Departamento;
	ObservableList<String> DeptoList= FXCollections.observableArrayList();
	@FXML
	private TextField valorRenta;
	@FXML
	private TextField cantH;
	@FXML
	private ComboBox<String> tipopropiedad;
	ObservableList<String> tipoPropiedadList= FXCollections.observableArrayList("casa","apartamento");
	@FXML
	private ComboBox<String> tipoDoc;
	ObservableList<String> tipoDocList= FXCollections.observableArrayList("CC", "CE");
	@FXML
	private TextField doc;
	@FXML
	private Text ERROR;
	@FXML
	private Button guardar;
	@FXML
	private TextArea descripcion;
	
	 private  static int idCliente;
	    
	   public static void idcliente(Integer id)
	   {
	    	idCliente=id;
	   }
	
	   
	   private Stage stage;
		private Scene scene;
		private Parent root;
		
		
	   public void devolverCuentaDueno(ActionEvent event) throws IOException 
		{
			root = FXMLLoader.load(getClass().getResource("/Interfaces/Dueno.fxml"));
		    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	   
	   
	   
	// Colocarle valores a la ubicación y localización
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
		{
		   ERROR.setVisible(false);
			
			//ubicaciones desde la BD
			tipopropiedad.setItems(tipoPropiedadList);
			 tipoDoc.setItems(tipoDocList);
			
			String Paises= "select pais "
					+ "from ubicacion";
			
			try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
					PreparedStatement ps=conex.prepareStatement(Paises))
			{
				ResultSet rs= ps.executeQuery();
				
				while(rs.next())
				{
					PaisList.add(rs.getString("pais"));
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
				
			}
			ubicacion.setItems(PaisList);
			
			
			//localizaciones desde la BD
			String Depar = "select DEPARTAMENTO "
					+ "from LOCALIZACION";
			
			try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
					PreparedStatement ps=conex.prepareStatement(Depar))
			{
				ResultSet rs= ps.executeQuery();
				while(rs.next())
				{
					DeptoList.add(rs.getString("DEPARTAMENTO"));
				}
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
			
			Departamento.setItems(DeptoList);
			
			
			String munip = "select MUNICIPIO "
					+ "from LOCALIZACION";
			
			try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
					PreparedStatement ps=conex.prepareStatement(munip))
			{
				ResultSet rs= ps.executeQuery();
				while(rs.next())
				{
					MuniList.add(rs.getString("MUNICIPIO"));
				}
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
			Municipio.setItems(MuniList);
		}
	
	
	
	public void guardar(ActionEvent event) throws IOException 
	{
		Boolean mal=false;
		
		ERROR.setVisible(false);
		
//para guardar el id del pais
		int idPais=0;
		String Paises= "select ID_UBICACION "
				+ "from ubicacion "
				+ "where pais=?";
		
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
				PreparedStatement ps=conex.prepareStatement(Paises))
		{
			ps.setString(1, ubicacion.getValue());
			ResultSet rs= ps.executeQuery();
			rs.next();
			idPais=rs.getInt("ID_UBICACION");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

//para guardar el id de la localización 
		int idLoca=0;
		String buscarLocalizacion = "select ID_LOCALIZACION "
				+ "from LOCALIZACION "
				+ "where (ID_UBICACION_PAIS=?) AND (DEPARTAMENTO=?) AND (MUNICIPIO=?) ";
		
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
				PreparedStatement ps=conex.prepareStatement(buscarLocalizacion))
		{
			ps.setInt(1, idPais);
			ps.setString(2, Municipio.getValue());
			ps.setString(3, Departamento.getValue());
			ResultSet rs= ps.executeQuery();
			rs.next();
			idLoca=rs.getInt("ID_LOCALIZACION");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
// Buscar documento del Id de la cuenta 
		int docuID=0;
		String buscarDocuID= "select DOCUMENTO "
				+ "from CUENTA "
				+ "where (TIPO_DOCUMENTO=?) AND (DOCUMENTO=?) ";
		
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
				PreparedStatement ps=conex.prepareStatement(buscarDocuID))
		{
			ps.setString(1, tipoDoc.getValue());
			ps.setString(2, doc.getText());
			ResultSet rs= ps.executeQuery();
			rs.next();
			docuID=rs.getInt("DOCUMENTO");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//Verificar que el documento que se ingresó corresponda a la cuenta 
		if(Integer.valueOf(doc.getText())==(docuID)) {
			if(ubicacion.getValue() != null && (valorRenta.getText()!=null && valorRenta.getText().length()>0) && (cantH.getText()!=null && cantH.getText().length()>0) && (tipopropiedad.getValue() !=null) && tipoDoc.getValue()!= null && doc.getText().length()>6)
			{
				try 
				{
					Integer.parseInt(cantH.getText()); 
				} 
				catch (Exception e) 
				{
					ERROR.setText("Lo que ingreso no es un numero");
					ERROR.setVisible(true);	
					mal=true;
				}	
				try 
				{
					Integer.parseInt(valorRenta.getText());
				} 
				catch (Exception e) 
				{
					ERROR.setText("Lo que ingreso no es un numero");
					ERROR.setVisible(true);	
					mal=true;
				}
				try 
				{
					Float.parseFloat(valorRenta.getText());   
				} 
				catch (Exception e) 
				{
					ERROR.setText("Lo que ingreso no es un numero");
					ERROR.setVisible(true);	
					mal=true;
				}
				if(!mal)
				{	
					//Insertar características
					String Insertarcaracte ="insert into CARACTERISTICAS (IDCARACTERISTICAS,ID_TIPO,CANT_HABITACIONES,RENTA) values (null,?,?,?)";
					try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
		    				PreparedStatement ps=conex.prepareStatement(Insertarcaracte))
		    		{
						if(tipopropiedad.getValue().equals("casa")) {
							ps.setInt(1, 1);
						}
						else {
							ps.setInt(1, 2);
						}
						ps.setInt(2, Integer.parseInt(cantH.getText()));
						ps.setFloat(3, Float.parseFloat(valorRenta.getText()));
		    			ps.executeQuery();
		    			
		    		} catch (SQLException e) {
		    			e.printStackTrace();
		    		}
					//Buscar del Id de características 
					int idC = 0;
					String buscarID = "select IDCARACTERISTICAS "
							+ "from CARACTERISTICAS "
							+ "where (pais=?) AND (ID_TIPO=?) AND (CANT_HABITACIONES=?) AND (RENTA=?) ";
					try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
		    				PreparedStatement ps=conex.prepareStatement(buscarID))
		    		{
						ps.setInt(1, idPais);
						if(tipopropiedad.getValue().equals("casa")) {
							ps.setInt(2, 1);
						}
						else {
							ps.setInt(2, 2);
						}
						ps.setInt(3, Integer.parseInt(cantH.getText()));
						ps.setFloat(4, Float.parseFloat(valorRenta.getText()));
		    			ResultSet rs= ps.executeQuery();
		    			idC=rs.getInt("IDCARACTERISTICAS");
		    		} catch (SQLException e) {
		    			e.printStackTrace();
		    		}
					// Insertar Propiedad
					String InsertarPropiedad ="insert into PROPIEDADES (ID_PROPIEDADES,ID_DUENO,DESCRIPCION,ESTADO,IMAGEN,ID_LOCALIZACION,ID_CARACTERISTICAS) values (null,?,?,?,?,?) ";
					
					try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
		    				PreparedStatement ps=conex.prepareStatement(InsertarPropiedad))
		    		{
						ps.setInt(1, idCliente);
						ps.setFloat(2, Float.parseFloat(descripcion.getText()));
						ps.setInt(3, 1);
						ps.setString(4, null);
						ps.setInt(5, idLoca);
						ps.setInt(6, idC);
		    			ps.executeQuery();
		    			ERROR.setVisible(true);
		    			ERROR.setText("Datos guardados");
		    		} catch (SQLException e) {
		    			e.printStackTrace();
		    		}
				}else
				{
					ERROR.setVisible(true);
					ERROR.setText("Debe llenar todos los campos");
				}
			}
			else
			{
				ERROR.setVisible(true);
				ERROR.setText("Debe llenar todos los campos");
			}
		}else
		{
			ERROR.setVisible(true);
			ERROR.setText("El numero de documento no corresponde con los credenciales de la cuenta");
		}	
	}
}
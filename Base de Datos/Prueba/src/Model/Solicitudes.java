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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Solicitudes implements Initializable
{
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
    private Button aceptar;

    @FXML
    private TableColumn<Solicitud, Integer> cliente;

    @FXML
    private TableColumn<Solicitud, String> fecha;

    @FXML
    private TableColumn<Solicitud, Integer> propiedad;
    
    @FXML
    private TableColumn<Solicitud, String> pais;
    
    @FXML
    private TableColumn<Solicitud, String> departamento;
    
    @FXML
    private TableColumn<Solicitud, String> municipio;
    
    @FXML
    private TableColumn<Solicitud, String> usuario;

    @FXML
    private Button rechazar;

    @FXML
    private TableView<Solicitud> tablasolicitudes;

	private ObservableList<Solicitud> listaSolicitudes=FXCollections.observableArrayList();
	
	public static int idDueno;
	
	public static void idDueno(Integer id)  
	{
		idDueno=id;
	}
	
	private Solicitud dato;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		dato=null;
		String Solicitudes="select solic.fecha as fech, solic.ID_PROPIEDAD as idp, solic.ID_CLIENTE as idc, p.ID_DUENO "
				+ "from "
				+ "(select fecha, ID_PROPIEDAD, ID_CLIENTE "
				+ "from solicitudes "
				+ "where (fecha, ID_PROPIEDAD) in "
				+ "(select min(fecha) fecha, ID_PROPIEDAD "
				+ "from solicitudes "
				+ "group by ID_PROPIEDAD)) solic "
				+ "join propiedades p on p.ID_PROPIEDADES=solic.ID_PROPIEDAD "
				+ "where p.ID_DUENO=?";
		
		String Usuario="select NOMBRE_USUARIO as usuario "
				+ "from cuenta "
				+ "where IDCUENTA=?";
		
		String PropiedadLocalizacion="select municipio_departamento.ID_PROPIEDADES,municipio_departamento.DEPARTAMENTO, municipio_departamento.MUNICIPIO, u.PAIS "
				+ "from UBICACION u join "
				+ "(select l.ID_UBICACION_PAIS, l.DEPARTAMENTO, l.MUNICIPIO, p.ID_PROPIEDADES "
				+ "from PROPIEDADES p join LOCALIZACION l on p.ID_LOCALIZACION=l.ID_LOCALIZACION) municipio_departamento "
				+ "on municipio_departamento.ID_UBICACION_PAIS=u.ID_UBICACION "
				+ "where municipio_departamento.ID_PROPIEDADES=?";
//		
		
		cliente.setCellValueFactory(new PropertyValueFactory<Solicitud, Integer>("idCliente"));	
		propiedad.setCellValueFactory(new PropertyValueFactory<Solicitud, Integer>("idPropiedad"));	
		fecha.setCellValueFactory(new PropertyValueFactory<Solicitud, String>("fecha"));
		pais.setCellValueFactory(new PropertyValueFactory<Solicitud, String>("pais"));
		departamento.setCellValueFactory(new PropertyValueFactory<Solicitud, String>("departemtno"));
		municipio.setCellValueFactory(new PropertyValueFactory<Solicitud, String>("municipio"));
		usuario.setCellValueFactory(new PropertyValueFactory<Solicitud, String>("usuario"));
		
		String user="";
		String pais="";
		String municipio="";
		String departamento="";
		
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,
			Constantes.USERNAME,Constantes.PASSWORD);
			PreparedStatement ps=conex.prepareStatement(Solicitudes))
		{
			ps.setInt(1, idDueno);
			
			ResultSet rs= ps.executeQuery();
			
			while(rs.next())
			{
				try(Connection conex2 = DriverManager.getConnection(Constantes.THINCONN,
						Constantes.USERNAME,Constantes.PASSWORD);
						PreparedStatement ps2=conex2.prepareStatement(Usuario))
				{
				
					ps2.setInt(1, rs.getInt("idc"));
					
					ResultSet rs2= ps2.executeQuery();
					
					rs2.next();
					
					user=rs2.getString("usuario");
					
				}
				catch (SQLException e) 
				{
			    	// TODO Auto-generated catch block
			    	e.printStackTrace();
				}	
				
				try(Connection conex3 = DriverManager.getConnection(Constantes.THINCONN,
						Constantes.USERNAME,Constantes.PASSWORD);
						PreparedStatement ps3=conex3.prepareStatement(PropiedadLocalizacion))
				{
				
					ps3.setInt(1, rs.getInt("idp"));
					
					ResultSet rs3= ps3.executeQuery();
					
					rs3.next();
					
					pais=rs3.getString("PAIS");
					municipio= rs3.getString("MUNICIPIO");
					departamento=rs3.getString("DEPARTAMENTO");
					
				}
				catch (SQLException e) 
				{
			    	// TODO Auto-generated catch block
			    	e.printStackTrace();
				}	
				
				
				
				listaSolicitudes.add(new Solicitud(rs.getInt("idc"),rs.getInt("idp"),rs.getString("fech"),user,pais,municipio,departamento));
			}
			
			tablasolicitudes.setItems(listaSolicitudes);		
		}
	    catch (SQLException e) 
		{
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
		}	
		
	}
	
	private int idp;
	private int idc;
	private String dates;
	
	@FXML
    private Text ERROR;
	
	
	public void Aceptar(ActionEvent  event) throws IOException 
	{
		ERROR.setVisible(false);
		
		if(dato==null)
		{
			ERROR.setVisible(true);
			ERROR.setText("No ha escogido una fila");
		}
		else
		{
			ERROR.setVisible(true);
			ERROR.setText("Se ha aceptado satisfactoriamente la solicitud");
		}
		
		String Aceptado="insert into aceptado (ID_aceptado ,id_propiedad,id_cliente) values (null,?,?)";
//		
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,
				Constantes.USERNAME,Constantes.PASSWORD);
				PreparedStatement ps=conex.prepareStatement(Aceptado))
		{
			
			ps.setInt(1,idp);
			ps.setInt(2,idc);
			
			ps.executeQuery();
			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public void Denegar(ActionEvent  event) throws IOException 
	{
		ERROR.setVisible(false);
		
		//se hace un delete en solicitudes
		
		if(dato==null)
		{
			ERROR.setVisible(true);
			ERROR.setText("No ha escogido una fila");
		}
		else
		{
			String deete="DELETE FROM SOLICITUDES WHERE ID_PROPIEDAD=? and ID_CLIENTE=?";
			
			try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,
					Constantes.USERNAME,Constantes.PASSWORD);
					PreparedStatement ps=conex.prepareStatement(deete))
			{
				//ps.setString(1,dates);
				ps.setInt(1,idp);
				ps.setInt(2,idc);
				
				ps.executeQuery();
				
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			
			
			ERROR.setVisible(true);
			ERROR.setText("Se ha rechazado satisfactoriamente la solicitud");
			
		}
	}

	public void Atras(ActionEvent  event) throws IOException 
	{
		root = FXMLLoader.load(getClass().getResource("/Interfaces/Dueno.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
    void obtenerDatos(MouseEvent event) 
	{
		rechazar.setVisible(true);
		aceptar.setVisible(true);
		ERROR.setVisible(true);
		dato=tablasolicitudes.getSelectionModel().getSelectedItem();
		dates=dato.getFecha();
		idc=dato.getIdCliente();
		idp=dato.getIdPropiedad();
    }

}

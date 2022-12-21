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

public class Aceptado implements Initializable
{
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	@FXML
	    private Text ERROR;
	
	@FXML
    private Button aceptar;

    @FXML
    private TableColumn<PropiedadAceptada, Integer> propiedad;
    
    @FXML
    private TableColumn<PropiedadAceptada, String> pais;
    
    @FXML
    private TableColumn<PropiedadAceptada, String> departamento;
    
    @FXML
    private TableColumn<PropiedadAceptada, String> municipio;
    
    @FXML
    private TableColumn<PropiedadAceptada, String> tipo;

    @FXML
    private TableView<PropiedadAceptada> tablasolicitudes;

	private ObservableList<PropiedadAceptada> listaSolicitudes=FXCollections.observableArrayList();
	
	public static int idcliente;
	
	public static void idDueno(Integer id)  
	{
		idcliente=id;
	}
	
	
	private PropiedadAceptada id;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
		String Solicitudes="select a.id_propiedad as ids "
				+ "from aceptado a join cuenta c on a.id_cliente =c.IDCUENTA "
				+ "where id_cliente=?";
//		
		
		String informacion="select tp.TIPO as tipo,todo.ID_PROPIEDADES as propiedadess,todo.pais as pais,todo.DEPARTAMENTO,todo.MUNICIPIO,todo.ID_DUENO "
				+ "from TIPO_PROPIEDADES TP join "
				+ "(select c.ID_TIPO,propiedad.ID_PROPIEDADES,propiedad.pais,propiedad.DEPARTAMENTO,propiedad.MUNICIPIO,propiedad.ID_DUENO "
				+ "from CARACTERISTICAS c join "
				+ "(select p.ID_PROPIEDADES,lugar.pais,lugar.DEPARTAMENTO,lugar.MUNICIPIO,p.ID_DUENO,p.ID_CARACTERISTICAS "
				+ "from propiedades p join "
				+ "(select l.ID_LOCALIZACION, u.pais,l.DEPARTAMENTO,l.MUNICIPIO "
				+ "from localizacion l join ubicacion u on l.ID_UBICACION_PAIS=u.ID_UBICACION) lugar "
				+ "on lugar.ID_LOCALIZACION=p.ID_LOCALIZACION) propiedad "
				+ "on propiedad.ID_CARACTERISTICAS=c.IDCARACTERISTICAS) todo "
				+ "on todo.ID_TIPO=TP.IDTIPO "
				+ "where todo.ID_PROPIEDADES=? and todo.ID_DUENO=?";
		
		
		propiedad.setCellValueFactory(new PropertyValueFactory<PropiedadAceptada, Integer>("idprop"));	
		pais.setCellValueFactory(new PropertyValueFactory<PropiedadAceptada, String>("pais"));
		departamento.setCellValueFactory(new PropertyValueFactory<PropiedadAceptada, String>("departemtno"));
		municipio.setCellValueFactory(new PropertyValueFactory<PropiedadAceptada, String>("municipio"));
		tipo.setCellValueFactory(new PropertyValueFactory<PropiedadAceptada, String>("tipo"));
		
		String tipo="";
		String pais="";
		String municipio="";
		String departamento="";
		
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,
			Constantes.USERNAME,Constantes.PASSWORD);
			PreparedStatement ps=conex.prepareStatement(Solicitudes))
		{
			ps.setInt(1, idcliente);
			
			ResultSet rs= ps.executeQuery();
			
			while(rs.next())
			{
				
				try(Connection conex2 = DriverManager.getConnection(Constantes.THINCONN,
						Constantes.USERNAME,Constantes.PASSWORD);
						PreparedStatement ps2=conex2.prepareStatement(informacion))
				{
					ps2.setInt(1, rs.getInt("ids"));
					ps2.setInt(2, idcliente);
					
					ResultSet rs2= ps2.executeQuery();

					rs.next();
					
					tipo=rs2.getString("tipo");
					pais=rs2.getString("pais");
					municipio= rs2.getString("MUNICIPIO");
					departamento=rs2.getString("DEPARTAMENTO");
						
				}
				 catch (SQLException e) 
				{
			    	e.printStackTrace();
				}
				
				listaSolicitudes.add(new PropiedadAceptada(rs.getInt("ids"),pais,municipio,departamento,tipo));
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
	
	@FXML
    void obtenerDatos(MouseEvent event) 
	{
		aceptar.setVisible(true);
		ERROR.setVisible(false);
		id=tablasolicitudes.getSelectionModel().getSelectedItem();
		idp=id.getIdprop();
    }
	
	public void Atras(ActionEvent  event) throws IOException 
	{
		root = FXMLLoader.load(getClass().getResource("/Interfaces/Cliente.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	public void Acepto(ActionEvent  event) throws IOException 
	{
		ERROR.setVisible(false);
		
		//se hace un delete en solicitudes
		
		if(id==null)
		{
			ERROR.setVisible(true);
			ERROR.setText("No ha escogido una fila");
		}
		else
		{
			root = FXMLLoader.load(getClass().getResource("/Interfaces/InfoPropiedadArrendarpt1.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}


}

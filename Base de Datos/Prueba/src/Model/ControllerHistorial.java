package Model;

import java.io.IOException;
import java.time.LocalDate; 
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControllerHistorial implements Initializable {
	
	
	@FXML
	private ComboBox <Integer > mesFinal;
	@FXML
	private ComboBox<Integer> mesInicio;
	private ObservableList<Integer> meses = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12);
	
	@FXML
	private Button buscar;
	@FXML
	private  Button volver;
	
	@FXML
    private ComboBox<Integer> year;
	private ObservableList<Integer> years= FXCollections.observableArrayList();
	
	
	
	public static int idDueno=2;
	public static void idCliente(Integer id)
	{
		idDueno=id;
	}
	
	private LocalDate Date=java.time.LocalDate.now();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
		
		
		int actual=Date.getYear();
		years.add(actual);
		
		for(int i=0; i<(actual%2000)-17;i++)
		{
			actual--;
			years.add(actual);
		}
		
		mesInicio.setItems(meses);
		mesFinal.setItems(meses);
		year.setItems(years);
	}
	
	@FXML
	private Text ERROR;
		 
	 
	public void Buscar(ActionEvent event)
	{
		
		ERROR.setVisible(false);
		
		if(mesInicio.getValue()!=null && mesFinal.getValue()!=null && year.getValue()!=null)
		{
			if(year.getValue()==Date.getYear())
			{
				if(mesInicio.getValue()<=Date.getMonthValue() && mesFinal.getValue()<=Date.getMonthValue())
				{	
					if(mesInicio.getValue()<=mesFinal.getValue())
					{
						
						historialPropiedades();
						
					}
					else
					{
						ERROR.setText("El mes de inicio debe se mayor o igual al mes final");
	    			    ERROR.setVisible(true);
					}
				}
				else
				{
					ERROR.setText("Escogio un mes que en el presente año no ha pasado");
    			    ERROR.setVisible(true);
				}
				
			}
			else
			{
				if(mesInicio.getValue()<=mesFinal.getValue())
				{
					
					historialPropiedades();
					
				}
				else
				{
					ERROR.setText("El mes de inicio debe se mayor o igual al mes final");
    			    ERROR.setVisible(true);
				}
				
			}
		}
		else
		{
			ERROR.setText("Debe llenar todos los espacios");
		    ERROR.setVisible(true);
		}
	}
	
    @FXML
    private TableColumn<Historial, Integer> VisitasTot;

    @FXML
    private TableColumn<Historial, String> idPropiedad;

    @FXML
    private TableColumn<Historial, Integer> mes;
    
    @FXML
    private TableView<Historial> tablaVistias;
    
    private ObservableList<Historial> listaHistorial=FXCollections.observableArrayList();
    
    @FXML
    private Text total;
	
	
	private void historialPropiedades()
	{
		
//		String PropiedadporMes="select count(ID_PROPIEDAD) as tot,ID_PROPIEDAD as id "
//				+ "from VISITAS "
//				+ "where EXTRACT(MONTH FROM fecha)=? and EXTRACT(YEAR FROM fecha)=? and ID_PROPIEDAD in "
//				+ "(select ID_PROPIEDADES "
//				+ "from PROPIEDADES "
//				+ "where ID_DUENO=?) "
//				+ "group by ID_PROPIEDAD";
//		
//		String TOTporMes="select count(ID_PROPIEDAD) as tot"
//				+ "from VISITAS "
//				+ "where EXTRACT(MONTH FROM fecha)=? and ID_PROPIEDAD in "
//				+ "(select ID_PROPIEDADES "
//				+ "from PROPIEDADES "
//				+ "where ID_DUENO=?)";
//		
//		
//		for(int i=mesInicio.getValue(); i<=mesFinal.getValue(); i++)
//		{
//			
//			try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
//    				PreparedStatement ps=conex.prepareStatement(PropiedadporMes))
//    		{
//				ps.setInt(1, i);
//				ps.setInt(2, year.getValue());
//				ps.setInt(3, idDueno);
//				
//				ResultSet rs= ps.executeQuery();
//				
//				if(!rs.next())
//				{
//					ERROR.setText("Usted no tiene propiedades");
//				    ERROR.setVisible(true);
//				}
//				else
//				{
//					do
//					{
//						listaHistorial.add(new Historial(rs.getInt("tot"),i,Integer.toString(rs.getInt("id"))));
//					}
//					while(rs.next());
//						
//				}
//			
//				
//    		}
//			catch (SQLException e)
//    		{
//    			e.printStackTrace();
//    		}
//			
//			try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
//    				PreparedStatement ps=conex.prepareStatement(TOTporMes))
//    		{
//				ps.setInt(1, i);
//				ps.setInt(2, idDueno);
//				
//				ResultSet rs= ps.executeQuery();
//				
//				if(rs.next())
//				{
//					listaHistorial.add(new Historial(rs.getInt("tot"),i,"TOTAL"));
//				}
//				
//    		}
//			catch (SQLException e)
//    		{
//    			e.printStackTrace();
//    		}
//
//		}
//		
//		tablaVistias.setItems(listaHistorial);	
//		
//		String totalTodo="select count(ID_PROPIEDAD) AS TOTALTODO"
//				+ "from VISITAS "
//				+ "where (EXTRACT(MONTH FROM fecha)) between ? and ? and ID_PROPIEDAD in "
//				+ "(select ID_PROPIEDADES "
//				+ "from PROPIEDADES "
//				+ "where ID_DUENO=?)";
//		
//		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
//				PreparedStatement ps=conex.prepareStatement(totalTodo))
//		{
//			ps.setInt(1, mesInicio.getValue());
//			ps.setInt(2, mesFinal.getValue()+1);
//			ps.setInt(3, idDueno);
//			
//			ResultSet rs= ps.executeQuery();
//			
//			rs.next();
//			
//			total.setText(Integer.toString(rs.getInt("TOTALTODO")));	
//			
//		}
//		catch (SQLException e)
//		{
//			e.printStackTrace();
//		}
		
	}
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void devolver(ActionEvent event) throws IOException 
	{
		root= FXMLLoader.load(getClass().getResource("/Interfaces/Dueno.fxml"));
		stage= (Stage)((Node)event.getSource()).getScene().getWindow();
		scene= new Scene (root);
		stage.setScene(scene);
		stage.show();				
	}
	
}
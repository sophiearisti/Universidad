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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InteresesController implements Initializable
{
	private Stage stage;
	private Scene scene;
	private Parent root;
		
       
	   @FXML
	   private TextField canthab;
	   @FXML
	   private Text ERROR;
	   @FXML
	   private TextField rentamaxima;

	   @FXML
	   private ComboBox<String> tipopropiedad;
	   ObservableList<String> tipoPropiedadList= FXCollections.observableArrayList("casa","apartamento");

	   @FXML
	   private ComboBox<String> ubicacion;
	   ObservableList<String> PaisList= FXCollections.observableArrayList();
	   
	   @FXML
	   private ImageView back;
	    
	   private  static int idCliente;
	    
	   public static void idcliente(Integer id)
	   {
	    	idCliente=id;
	   }

	   @Override
	   public void initialize(URL arg0, ResourceBundle arg1) 
		{
		   ERROR.setVisible(false);
			
			//ubicaciones desde la BD
			tipopropiedad.setItems(tipoPropiedadList);
			
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
				
				
			} catch (SQLException e) 
			{
				e.printStackTrace();
				
			}
			
			ubicacion.setItems(PaisList);

      //para guardar la info que tiene el cliente en intereses
			String InteresesAntiguos= "select ID_UBICACION, MAX_RENTA, HABITACIONES, TIPO "
					+ "from INTERES_CLIENTE "
					+ "where ID_CUENTA=?";
//			
			int paisID=0;
			Float rentaM=0f;
			int habitaciones=0;
			String tipoID="";
			
			String pais="";
//			
			try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
    				PreparedStatement ps=conex.prepareStatement(InteresesAntiguos))
    		{
				ps.setInt(1, idCliente);
				
    			ResultSet rs= ps.executeQuery();
    			
    			rs.next();
    			
    			paisID=rs.getInt("ID_UBICACION");
    			rentaM=rs.getFloat("MAX_RENTA");
    			habitaciones = rs.getInt("HABITACIONES");
    			tipoID = rs.getString("TIPO");
 		
    		} 
			catch (SQLException e) 
			{
    			e.printStackTrace();
    		}
			
//			
			String buscaNomPais= "select PAIS "
					+ "from UBICACION "
					+ "where ID_UBICACION=?";
			
			try(Connection conex1 = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
    				PreparedStatement ps1=conex1.prepareStatement(buscaNomPais)){
				
				ps1.setInt(1, paisID);
				
				ResultSet rs1= ps1.executeQuery();
    			
    			rs1.next();
    			
    			pais = rs1.getNString("PAIS");
			}
			catch (SQLException e) 
			{
    			e.printStackTrace();
    		}
			
			canthab.setText(Integer.toString(habitaciones));
			rentamaxima.setText(Float.toString(rentaM));
			tipopropiedad.setValue(tipoID);
			ubicacion.setValue(pais);
				
			
		}
		
		// Puede volver a la pantalla del cliente 
				public void volverPrincipal(MouseEvent mouseEvent){
					Parent root;
					try {
						root = FXMLLoader.load(getClass().getResource("/Interfaces/Cliente.fxml"));
						Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
						Scene scene = new Scene(root);
						stage.setScene(scene);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		

		public void guardar(ActionEvent event) throws IOException 
		{
			Boolean mal=false;
			
			ERROR.setVisible(false);
			
			if((canthab.getText().length()>0 || canthab.getText() == null) && (rentamaxima.getText().length()>0 || rentamaxima.getText() == null) && tipopropiedad.getValue()!=null && ubicacion.getValue()!=null )
			{
				try 
				{
					Integer.parseInt(canthab.getText());
				    
				} 
				catch (Exception e) 
				{
					ERROR.setText("Lo que ingreso no es un numero");
					ERROR.setVisible(true);	
					mal=true;
				}
				
				try 
				{
					Float.parseFloat(rentamaxima.getText());
				    
				} 
				catch (Exception e) 
				{
					ERROR.setText("Lo que ingreso no es un numero");
					ERROR.setVisible(true);	
					mal=true;
				}
				
				if(!mal)
				{

					int idPais=0;
					//para guardar el id del pais
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
		    			// TODO Auto-generated catch block
		    			e.printStackTrace();
		    		}
					
					int idInte=0;
					//para guardar el id del pais
					String Intereses= "select ID_INTERESES "
							+ "from INTERES_CLIENTE "
							+ "	where ID_CUENTA=?";
					
					try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
		    				PreparedStatement ps=conex.prepareStatement(Intereses))
		    		{
						ps.setInt(1, idCliente);//
						
		    			ResultSet rs= ps.executeQuery();
		    			
		    			rs.next();
		    			
		    			idInte=rs.getInt("ID_INTERESES");
						
		    		} catch (SQLException e) {
		    			e.printStackTrace();
		    		}
					

					String InsertarIntereses="UPDATE INTERES_CLIENTE SET ID_UBICACION= ?, MAX_RENTA= ?,HABITACIONES= ?,TIPO=? WHERE ID_INTERESES=?";
					
					try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
		    				PreparedStatement ps=conex.prepareStatement(InsertarIntereses))
		    		{
						ps.setInt(1, idPais);
						ps.setFloat(2, Float.parseFloat(rentamaxima.getText()));
						ps.setInt(3, Integer.parseInt(canthab.getText()));
						ps.setString(4, tipopropiedad.getValue());
						ps.setInt(5, idInte);
						
		    			ps.executeQuery();

		    			
		    			ERROR.setVisible(true);
		    			ERROR.setText("Datos guardados");
						
		    		} catch (SQLException e) {
		    			e.printStackTrace();
		    		}
				}
			}
			else
			{
				ERROR.setVisible(true);
				ERROR.setText("Debe llenar todos los campos");
			}
		}
}
package Model;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import Model.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControllerListarActualizarPropiedad implements Initializable

{
	@FXML
    private Text ERROR;
    
	private Stage stage;
	private Scene scene;
	private Parent root; 
       
	public static int idCliente=2;
	
	public static void idCliente(Integer id)
	{
		idCliente=id;	
	}
	
	private List<Vivienda> Viviendas= new ArrayList <Vivienda>();
	
	@FXML
    private Button actualizar;
	
   @FXML
    private Button eliminar;

   @FXML
   private TextField costo;
   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		myListener =new MyListener() 
		{

			@Override
			public void onClickListener(Vivienda vivienda) 
			{
				setChosenVivienda(vivienda);
				
			}
	    	
	    };
	    
		String PropiedadesDelDueno="select todo.IDCARACTERISTICAS as idarriendo,tp.TIPO as tipo,todo.CANT_HABITACIONES,todo.FECHA as dat,todo.RENTA as rent,todo.ID_PROPIEDADES as propiedadess,todo.pais as pais,todo.DEPARTAMENTO,todo.MUNICIPIO,todo.ID_DUENO,todo.DESCRIPCION as descripcion,todo.ESTADO as state,todo.IMAGEN "
				+ "from TIPO_PROPIEDADES TP join "
				+ "(select c.IDCARACTERISTICAS, c.ID_TIPO,c.CANT_HABITACIONES,c.FECHA,c.RENTA,propiedad.ID_PROPIEDADES,propiedad.pais,propiedad.DEPARTAMENTO,propiedad.MUNICIPIO,propiedad.ID_DUENO,propiedad.DESCRIPCION,propiedad.ESTADO,propiedad.IMAGEN "
				+ "from CARACTERISTICAS c join "
				+ "(select p.ID_PROPIEDADES,lugar.pais,lugar.DEPARTAMENTO,lugar.MUNICIPIO,p.ID_DUENO,p.DESCRIPCION,p.ESTADO,p.IMAGEN,p.ID_CARACTERISTICAS "
				+ "from propiedades p join "
				+ "(select l.ID_LOCALIZACION, u.pais,l.DEPARTAMENTO,l.MUNICIPIO "
				+ "from localizacion l join ubicacion u on l.ID_UBICACION_PAIS=u.ID_UBICACION) lugar "
				+ "on lugar.ID_LOCALIZACION=p.ID_LOCALIZACION) propiedad "
				+ "on propiedad.ID_CARACTERISTICAS=c.IDCARACTERISTICAS) todo "
				+ "on todo.ID_TIPO=TP.IDTIPO "
				+ "where todo.ID_DUENO=?";
		
		Boolean tienePropiedades=false;
		
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
				PreparedStatement ps=conex.prepareStatement(PropiedadesDelDueno))
		{
			ps.setInt(1, idCliente);
			
			ResultSet rs= ps.executeQuery();
			
			
			tienePropiedades=rs.next();
			if(tienePropiedades)
			{
				actualizar.setDisable(false);
				eliminar.setDisable(false);
				costo.setDisable(false);
				
				do
				{
					
					//aqui se guarda todo
					Viviendas.add(obtenerDatos (rs));
					
	//				System.out.println(rs.getString("propiedadess"));
	//				System.out.println("\n");
					
				}while(rs.next());
						
				EstablecerDatos ();
				
				//setChosenVivienda(Viviendas.get(0));
			}
			else
			{
				ERROR.setText("Usted Tovadia no tiene propiedades");
			    ERROR.setVisible(true);
			}
			
			
		}
		catch (SQLException | IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private Vivienda obtenerDatos (ResultSet rs) throws SQLException
	{
		 
		 Vivienda vivienda=new Vivienda();
		 
		 
		 vivienda.setPais(rs.getString("pais"));
		 vivienda.setEstado(rs.getInt("state"));
		 vivienda.setId(rs.getInt("propiedadess"));
		 vivienda.setArriendo(rs.getFloat("rent"));
		 vivienda.setDescripcion(rs.getString("descripcion"));
		 vivienda.setFecha(rs.getString("dat"));
		 vivienda.setTipo(rs.getString("tipo"));
		 vivienda.setIdArriendo(rs.getInt("idarriendo"));
		 
		 return vivienda;
		 
	}
	
	private MyListener myListener;
	
	 @FXML
	 private GridPane grid;
		
	@FXML
	private ScrollPane scroll;

	private void EstablecerDatos () throws IOException
	{
		int column=0;
		int row=0;
		
		for(int i=0; i<Viviendas.size();i++)
		{
			FXMLLoader fxmlLoader2 = new FXMLLoader();
			fxmlLoader2.setLocation(getClass().getResource("/Interfaces/ViviendaDueno.fxml"));
			AnchorPane anchorpane=fxmlLoader2.load();
			
			
			ViviendaDuenoController viviendaDuenoController=fxmlLoader2.getController();
			viviendaDuenoController.setData(Viviendas.get(i), myListener);
			
			if(column==1)
			{
				column=0;
				row++;
			}
			
			column++;
			
			grid.add(anchorpane,column,row);
			
			GridPane.setMargin(anchorpane, new Insets(10));;
			
		}
		 
		 
	}
	
	 @FXML
    private Button atras;

    @FXML
    private Text codigo;


    @FXML
    private Text descripcion;

    @FXML
    private Text estado;

    @FXML
    private Text tipo;

	private void setChosenVivienda(Vivienda vivienda)
	{
		
		costo.setText(Float.toString(vivienda.getArriendo()));
		
		switch (vivienda.getEstado())
		{
			case 1:
				estado.setText("Disponible"); 
			break;
			case 2:
				estado.setText("Arrendada"); 
			break;
			case 3:
				estado.setText("Eliminada"); 
			break;
		}
		
		codigo.setText(Integer.toString(vivienda.getId()));
		
		descripcion.setText(vivienda.getDescripcion()); 
		
		tipo.setText(vivienda.getTipo());
		
	}
	
	
    public void actualizar( ActionEvent event) throws IOException
    {
    	ERROR.setVisible(false);
    	Boolean EstaEnINT=true;
    	
    	if(codigo.getText().length()!=0)
    	{
 
	    	
	    	try 
			{
				Float.parseFloat(costo.getText());
			    
			} 
	    	catch (Exception e) 
			{
				ERROR.setText("El arriendo no es un numero");
				ERROR.setVisible(true);
				EstaEnINT=false;
			}
	    	
	    	if(EstaEnINT)
	    	{
	    		String actualizar= "UPDATE caracteristicas "+
		    	"SET RENTA=? "+
		    	"WHERE  IDCARACTERISTICAS=?";
		    	
		    	Integer idArriendo=0;
		    	
		    	for (int i=0; i<Viviendas.size(); i++)
		    	{
		    		if(Integer.parseInt(codigo.getText())==Viviendas.get(i).getId())
		    		{
		    			idArriendo=Viviendas.get(i).getIdArriendo();
		    		}
		    	}
		    	
		    		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
						PreparedStatement ps=conex.prepareStatement(actualizar))
		    		{
		    		
			    		ps.setFloat(1, Float.parseFloat(costo.getText()));
			    		ps.setInt(2, idArriendo);
			    		
			    		ps.executeQuery();
			    		
			    		ERROR.setText("La propiesdad se actualizo");
						ERROR.setVisible(true);
		 	
			    	}
		    		catch (SQLException e)
					{
						e.printStackTrace();
				    }
		    	}
    	}
    	else
    	{
    		ERROR.setText("Debe seleccionar una propiedad para actualizar");
			ERROR.setVisible(true);
    	}
    	
    }
    
    
    public void Eliminar( ActionEvent event) throws IOException
    {
    	String actualizar= "UPDATE PROPIEDADES "+
		    	"SET ESTADO=3 "+
		    	"WHERE ID_PROPIEDADES=?";
    	
    	if(codigo.getText().length()!=0)
    	{
    		
    		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
					PreparedStatement ps=conex.prepareStatement(actualizar))
    		{
    			ps.setInt(1, Integer.parseInt(codigo.getText()));
    			ps.executeQuery();
	    		
	    		ERROR.setText("La propiesdad se elimino");
				ERROR.setVisible(true);
    		}
    		catch (SQLException e)
			{
				e.printStackTrace();
		    }
    	}
    	else
    	{
    		ERROR.setText("Debe seleccionar una propiedad para eliminar");
			ERROR.setVisible(true);
    	}
    	
    }



	public void devolver(ActionEvent event) throws IOException 
	{
		root= FXMLLoader.load(getClass().getResource("/Interfaces/Dueno.fxml"));
		stage= (Stage)((Node)event.getSource()).getScene().getWindow();
		scene= new Scene (root);
		stage.setScene(scene);
		stage.show();	
		
	}
}
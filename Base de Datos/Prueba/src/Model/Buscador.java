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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Buscador implements Initializable
{
    @FXML
    private GridPane grid;
	
	@FXML
	private ScrollPane scroll;

    @FXML
	private Text ERROR;
	 
    @FXML
    private TextField maxrenta;

    @FXML
    private TextField minrenta;
    
    @FXML
    private TextField canthabitaciones;

    @FXML
    private ComboBox<String> scrolldepartamento;
    ObservableList<String> DepartamentoList= FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> scrollmunicipio;
    ObservableList<String> MunicipioList= FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> scrollpais;
    ObservableList<String> PaisList= FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> scrolltipopropiedad;
    ObservableList<String> tipoPropiedadList= FXCollections.observableArrayList("apartamento","casa");
    
    
    @FXML
	 private Text rentapropiedad;
	 
	 @FXML
	 private Text estadopropiedad;
	 
	 @FXML
	 private Text codigopropiedad;
	 
	 @FXML
	    private Text blahblahblah;
	 
	 @FXML
	    private Text ddmmaa;
	 
	 @FXML
	    private Text textopais;
	 
	private Stage stage;
	private Scene scene;
	private Parent root;
	
    public static int idCliente=0;
    
    public static String PaisDefault;
	
	public static void idCliente(Integer id)
	{
		idCliente=id;
		UbicacionDefault();
		
	}
	
	private static void UbicacionDefault()
	{
		
		//va a guardar el pais del cliente por si acaso
		
		String ConsultaPaisCliente= "select u.pais as pais "
				+ "from UBICACION u join "
				+ "(select c.IDCUENTA,i.ID_UBICACION "
				+ "from cuenta c join INTERES_CLIENTE i "
				+ "on c.IDCUENTA=i.ID_CUENTA) cliente "
				+ "on u.ID_UBICACION=cliente.ID_UBICACION "
				+ "where cliente.IDCUENTA=?";
		
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
				PreparedStatement ps=conex.prepareStatement(ConsultaPaisCliente))
		{
			
			ps.setInt(1, idCliente);
			
			ResultSet rs= ps.executeQuery();
			
			rs.next();
			
			 PaisDefault=rs.getString("pais");
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		ERROR.setVisible(false);
		
		scrolltipopropiedad.setItems(tipoPropiedadList);
		
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
		
		
		scrollpais.setItems(PaisList);
		
		myListener =new MyListener() 
		{

			@Override
			public void onClickListener(Vivienda vivienda) 
			{
				setChosenVivienda(vivienda);
				
			}
	    	
	    };
	   	
	}
	
	public void EvaluarPais(MouseEvent event) throws IOException 
	{
		ERROR.setVisible(false);
		
		DepartamentoList.clear();
		scrolldepartamento.setItems(DepartamentoList);
		
		String pais= scrollpais.getValue();
		Boolean existe;
		
		if(pais==null)
     	{
     		ERROR.setText("Antes de elegir el departamento, debe elegir el pais");
		    ERROR.setVisible(true);
     		
     	}	
		else
		{
			//consulta para mostrar el departamento segun lo escogido en el pais
			
			String ConsultaDepartamentos= "select  l.departamento as departamento "
					+ "    from localizacion l join ubicacion u on l.ID_UBICACION_PAIS=u.ID_UBICACION "
					+ "where u.pais=?";
    		
    		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
    				PreparedStatement ps=conex.prepareStatement(ConsultaDepartamentos))
    		{
    			
    			ps.setString(1, pais);
    			
    			ResultSet rs= ps.executeQuery();
    			
    			existe= rs.next();
    			
    			if(existe)
    			{
    				do
    				{
    					DepartamentoList.add(rs.getString("departamento"));
    					
    				}while(rs.next());
    				
    				
    				scrolldepartamento.setItems(DepartamentoList);
    			}
    			else
    			{
    				
    				ERROR.setText("No hay propiedades para ese pais");
    			    ERROR.setVisible(true);
    			}
    			 
    		} 
    		catch (SQLException e)
    		{
    			e.printStackTrace();
    		}
			
		}
	}
	
	public void EvaluarPaisDepartamento(MouseEvent event) throws IOException 
	{
		MunicipioList.clear();
		scrollmunicipio.setItems(MunicipioList);
		
		
		ERROR.setVisible(false);
		
     	String pais= scrollpais.getValue();
     	String departamento= scrolldepartamento.getValue();
     	
     	if(pais==null || departamento==null)
     	{
     		ERROR.setText("Antes de elegir el municipio, debe elegir el pais y el departamento");
		    ERROR.setVisible(true);
     		
     	}
     	else
     	{
     		//consulta para llenar el municipio con base al pais y al departamento
     		String ConsultaMunicipios= "select  l.MUNICIPIO as municipio "
     				+ "    from localizacion l join ubicacion u on l.ID_UBICACION_PAIS=u.ID_UBICACION "
     				+ "where u.pais=? and l.DEPARTAMENTO=?";
    		
    		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
    				PreparedStatement ps=conex.prepareStatement(ConsultaMunicipios))
    		{
    			
    			ps.setString(1, pais);
    			
    			ps.setString(2, departamento);
    			
    			ResultSet rs= ps.executeQuery();
    			
    			while(rs.next())
    			{
    				MunicipioList.add(rs.getString("municipio"));
    			}
    			
    			scrollmunicipio.setItems(MunicipioList);
    			 
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
     		
     	}
     	
 
		
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
private List<Vivienda> Viviendas= new ArrayList <Vivienda>();

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
	 vivienda.setIdArriendo(0);
	 
	 return vivienda;
	 
	 
}

private MyListener myListener;

private void EstablecerDatos () throws IOException
{
	int column=0;
	int row=0;
	
	for(int i=0; i<Viviendas.size();i++)
	{
		FXMLLoader fxmlLoader2 = new FXMLLoader();
		fxmlLoader2.setLocation(getClass().getResource("/Interfaces/MiniVivienda.fxml"));
		AnchorPane anchorpane=fxmlLoader2.load();
		
		
		ViviendaControler viviendaController=fxmlLoader2.getController();
		viviendaController.setData(Viviendas.get(i), myListener);
		
		if(column==2)
		{
			column=0;
			row++;
		}
		
		column++;
		
		grid.add(anchorpane,column,row);
		
		GridPane.setMargin(anchorpane, new Insets(10));;
		
	}
	 
	 
}

private void setChosenVivienda(Vivienda vivienda)
{
	rentapropiedad.setText(Float.toString(vivienda.getArriendo()));
	estadopropiedad.setText(Integer.toString(vivienda.getEstado())); 
	codigopropiedad.setText(Integer.toString(vivienda.getId()));
	blahblahblah.setText(vivienda.getDescripcion()); 
	ddmmaa.setText(vivienda.getFecha());
	textopais.setText(vivienda.getPais());
	
	
}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void Buscar(ActionEvent event) throws IOException 
	{
		//grid.getChildren().retainAll(grid.getChildren().get(0));
		
		grid.getColumnConstraints().clear();
		grid.getRowConstraints().clear();
		grid.getChildren().clear();
		
		Viviendas.removeAll(Viviendas);
		
	    ERROR.setVisible(false);
	    
	    
		String pais= scrollpais.getValue();
		String departamento= scrolldepartamento.getValue();
		String municipio= scrollmunicipio.getValue();
		String tipo= scrolltipopropiedad.getValue();
		
		
		String maxRenta=maxrenta.getText();
		String minRenta=minrenta.getText();
		String cantHabitaciones=canthabitaciones.getText();
		
		Boolean EstaEnINT=true;
		
		Float MINRENTA=0f;
		Float MAXRENTA=0f;
		Integer CANTHAB;	
		
		
		StringBuilder ConsultaBuscador= new StringBuilder("select tp.TIPO as tipo,todo.CANT_HABITACIONES,todo.FECHA as dat,todo.RENTA as rent,todo.ID_PROPIEDADES as propiedadess,todo.pais as pais,todo.DEPARTAMENTO,todo.MUNICIPIO,todo.ID_DUENO,todo.DESCRIPCION as descripcion,todo.ESTADO as state,todo.IMAGEN "
				+ "from TIPO_PROPIEDADES TP join "
				+ "(select c.ID_TIPO,c.CANT_HABITACIONES,c.FECHA,c.RENTA,propiedad.ID_PROPIEDADES,propiedad.pais,propiedad.DEPARTAMENTO,propiedad.MUNICIPIO,propiedad.ID_DUENO,propiedad.DESCRIPCION,propiedad.ESTADO,propiedad.IMAGEN "
				+ "from CARACTERISTICAS c join "
				+ "(select p.ID_PROPIEDADES,lugar.pais,lugar.DEPARTAMENTO,lugar.MUNICIPIO,p.ID_DUENO,p.DESCRIPCION,p.ESTADO,p.IMAGEN,p.ID_CARACTERISTICAS "
				+ "from propiedades p join "
				+ "    (select l.ID_LOCALIZACION, u.pais,l.DEPARTAMENTO,l.MUNICIPIO "
				+ "    from localizacion l join ubicacion u on l.ID_UBICACION_PAIS=u.ID_UBICACION) lugar "
				+ "    on lugar.ID_LOCALIZACION=p.ID_LOCALIZACION) propiedad "
				+ "on propiedad.ID_CARACTERISTICAS=c.IDCARACTERISTICAS) todo "
				+ "on todo.ID_TIPO=TP.IDTIPO "
				+ "where todo.estado!=3 and todo.pais=? ");
		
		if(pais==null && departamento==null && municipio==null && tipo==null && maxRenta.length()==0 && minRenta.length()==0 && cantHabitaciones.length()==0)
		{
			
			try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
    				PreparedStatement ps=conex.prepareStatement(ConsultaBuscador.toString()))
    		{
    			
    			ps.setString(1, PaisDefault);
    			
    			ResultSet rs= ps.executeQuery();
    			
    			EstaEnINT=rs.next();
    			
    			if(EstaEnINT)
    			{
    				do
    				{
    					
    					//aqui se guarda todo
    					Viviendas.add(obtenerDatos (rs));
    					
//    					System.out.println(rs.getString("propiedadess"));
//    					System.out.println("\n");
    					
    				}while(rs.next());
    				
    				EstablecerDatos ();
    				
    				setChosenVivienda(Viviendas.get(0));
    			}
    			else
    			{
    				ERROR.setText("No Existen resultados para su busqueda");
    			    ERROR.setVisible(true);
    			}
    			
    			//hacer lectura de todo para poner la info de todas las propiedades
    			//toca mirar si esta vacia
    			 
    		} 
			catch (SQLException e) 
			{
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
		}
		else
		{
			
			if(departamento!=null)
			{
				ConsultaBuscador.append(" and todo.DEPARTAMENTO='"+departamento+"' ");
			}
			
			if(municipio!=null)
			{
				ConsultaBuscador.append(" and todo.MUNICIPIO='"+municipio+"' ");
			}
			
			if(tipo!=null)
			{
				
					ConsultaBuscador.append(" and tp.TIPO='"+tipo+"' ");
					
			}
			
			if(minRenta.length()!=0)
			{
				try 
				{
					MINRENTA= Float.parseFloat(minRenta);
				    
				} 
				catch (Exception e) 
				{
					ERROR.setText("La renta minima no es un numero");
					ERROR.setVisible(true);	
					EstaEnINT=false;
				}
				
				ConsultaBuscador.append(" and todo.RENTA>"+(MINRENTA-0.001f)+" ");
				
			}
			
			if(maxRenta.length()!=0 && EstaEnINT)
			{
				try 
				{
					MAXRENTA= Float.parseFloat(maxRenta);
				    
				} 
				catch (Exception e) 
				{
					ERROR.setText("La renta maxima no es un numero");
					ERROR.setVisible(true);	
					EstaEnINT=false;
				}
				
				ConsultaBuscador.append(" and todo.RENTA<"+(MAXRENTA+0.001f)+" ");
				
			}
			
			if(cantHabitaciones.length()!=0 && EstaEnINT)
			{
				try 
				{
					CANTHAB= Integer.parseInt(cantHabitaciones);
				    
				} catch (Exception e) 
				{
					ERROR.setText("La cant de habitaciones no es un numero");
					ERROR.setVisible(true);
					EstaEnINT=false;
				}	
				
				ConsultaBuscador.append(" and todo.CANT_HABITACIONES="+cantHabitaciones+" ");
				
			}
			
			if(EstaEnINT)
			{
				try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
	    				PreparedStatement ps=conex.prepareStatement(ConsultaBuscador.toString()))
	    		{
					if(pais==null)
					{
						ps.setString(1, PaisDefault);
					}
					
					if(pais!=null)
					{
						ps.setString(1, pais);
					}
	    			
	    		
	    			
	    			ResultSet rs= ps.executeQuery();
	    			
	    			
	    			EstaEnINT=rs.next();
	    			
	    			if(EstaEnINT)
	    			{
	    				do
	    				{
	    					
	    					Viviendas.add(obtenerDatos (rs));
	    					
	    					
	    					System.out.println(rs.getString("propiedadess"));
	    					System.out.println("\n");
	    					
	    				}while(rs.next());
	    				
	    				EstablecerDatos ();
	    				
	    				setChosenVivienda(Viviendas.get(0));
	    				
	    			}
	    			else
	    			{
	    				ERROR.setText("No Existen resultados para su busqueda");
	    			    ERROR.setVisible(true);
	    			}
	    		} 
				catch (SQLException e) 
				{
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
			}
		}		
	}
	
	public void devolverCuentaCliente(ActionEvent event) throws IOException 
	{
		root = FXMLLoader.load(getClass().getResource("/Interfaces/Cliente.fxml"));
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	 
	private int Codigo;
	
	public void Arrendar(ActionEvent event) throws IOException 
	{
		ERROR.setVisible(false);
		
		Codigo=Integer.parseInt(codigopropiedad.getText());
		
		int disponible=Integer.parseInt(estadopropiedad.getText());
		
		Boolean siFueVisitada;
		
		String visitada="select * "
				+ "from VISITAS "
				+ "where ID_PROPIEDAD=? and ID_CUENTA=?";
		
		
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
				PreparedStatement ps=conex.prepareStatement(visitada))
		{
			ps.setInt(1, Codigo);
			ps.setInt(2, idCliente);//idcliente
			
			ResultSet rs=ps.executeQuery();
			
			
			siFueVisitada=rs.next();
			
			if(siFueVisitada)
			{
				//evaluar si esta disponible o no 
				
				if(disponible==1)
				{
					//evaluar solicitudes
					evaluarsolicitudes();
					
				}
				else
				{
					ERROR.setText("La propiedad esta arrendada actualmente por otro cliente");
				    ERROR.setVisible(true);	
				}
				
				
			}
			else
			{
				ERROR.setText("La propiedad no ha sido visitada aun");
			    ERROR.setVisible(true);
			}
			
			
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void evaluarsolicitudes()
	{
		
			String solicitudes="insert into SOLICITUDES (ID_SOLICITUD,ID_PROPIEDAD,ID_CLIENTE) values (null,?,?)";
		
			try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
					PreparedStatement ps=conex.prepareStatement(solicitudes))
			{
				ps.setInt(1, Codigo);
				ps.setInt(2, idCliente);
				
				ps.executeQuery();
				
				
				ERROR.setText("Hay cola de solicitudes, su solicitud fue enviada");
			    ERROR.setVisible(true);	
				
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
					e.printStackTrace();
			}
		
		
		
	}
	

	public void Visitada(ActionEvent event) throws IOException 
	{	
		ERROR.setVisible(false);
		
		float Renta= Float.parseFloat(rentapropiedad.getText());
		float max=0f;
		
		int Codigo=Integer.parseInt(codigopropiedad.getText());
		
		String maxrenta="select i.MAX_RENTA as max "
				+ "from cuenta c join INTERES_CLIENTE i "
				+ "on i.ID_CUENTA=?";
		
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
				PreparedStatement ps=conex.prepareStatement(maxrenta))
		{
			ps.setInt(1,idCliente);//idcliente
			
			ResultSet rs= ps.executeQuery();
			
			rs.next();
			
			max=rs.getInt("max");	
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(Renta>max)
		{
			ERROR.setText("La propiedad no puede añadirse a la lista porque su renta máxima es menor a la renta de la propiedad");
		    ERROR.setVisible(true);
		}
		else
		{
			String visitada="Insert into VISITAS (ID_VISITAS,ID_PROPIEDAD,ID_CUENTA) values (null,?,?)";
			
			try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
					PreparedStatement ps=conex.prepareStatement(visitada))
			{
				ps.setInt(1, Codigo);
				ps.setInt(2, idCliente);//idcliente
				
				ps.executeQuery();
				
				ERROR.setText("La propiedad fue añadida");
			    ERROR.setVisible(true);
				
			}
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			;
		}
	}
	
	
	public void EliminardeVisitas(ActionEvent event) throws IOException 
	{
		int Codigo=Integer.parseInt(codigopropiedad.getText());
		
		 ERROR.setVisible(false);
		
		String EliminarVisitada="delete from VISITAS "
				+ "where ID_PROPIEDAD=? and ID_CUENTA=?";
		
		try(Connection conex = DriverManager.getConnection(Constantes.THINCONN,Constantes.USERNAME,Constantes.PASSWORD);
				PreparedStatement ps=conex.prepareStatement(EliminarVisitada))
		{
			ps.setInt(1, Codigo);
			ps.setInt(2, idCliente);
			
			ps.executeQuery();
			
			ERROR.setText("Si usted hab[ia visitado la propiedad, esta fue borrada de su lista");
		    ERROR.setVisible(true);
			
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

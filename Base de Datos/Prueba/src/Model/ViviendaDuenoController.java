package Model;

import javafx.event.ActionEvent;


import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import Model.MyListener;


public class ViviendaDuenoController 
{

    @FXML
    private Text codigo;

    @FXML
    private Text costo;

    @FXML
    private Text descripcion;

    @FXML
    private Text estado;

    @FXML
    private Text tipo;
    
    @FXML
    private Text pais;
    
    private MyListener mylistener;
    
    
    @FXML
    private void click(MouseEvent mouseEvent)
    {
    	mylistener.onClickListener(vivienda);
    }
    
    
    private Vivienda vivienda;
    
    public void setData(Vivienda vivienda, MyListener mylistener)
    {
    	this.vivienda=vivienda;
    	this.mylistener=mylistener;
    	descripcion.setText(vivienda.getDescripcion());
    	estado.setText(Integer.toString(vivienda.getEstado()));
    	tipo.setText(vivienda.getTipo());
    	codigo.setText(Integer.toString(vivienda.getId()));
    	pais.setText(vivienda.getPais());
    	costo.setText(Float.toString(vivienda.getArriendo()));
    	
    }

}

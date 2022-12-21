package Model;

import javafx.event.ActionEvent;


import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import Model.MyListener;


public class ViviendaControler 
{
	@FXML
    private Text descripcionLabel;

    @FXML
    private Text estadoLabel;

    @FXML
    private Text fechaLabel;

    @FXML
    private Text idLabel;

    @FXML
    private Text paisLabel;

    @FXML
    private Text rentaLabel;
    
    @FXML
    private ImageView imagenLabel;
    
    @FXML
    private void click(MouseEvent mouseEvent)
    {
    	mylistener.onClickListener(vivienda);
    }
    
    private Vivienda vivienda;
    
    private MyListener mylistener;
    
    
    public void setData(Vivienda vivienda,MyListener mylistener)
    {
    	this.vivienda=vivienda;
    	this.mylistener=mylistener;
    	descripcionLabel.setText(vivienda.getDescripcion());
    	estadoLabel.setText(Integer.toString(vivienda.getEstado()));
    	fechaLabel.setText(vivienda.getFecha());
    	idLabel.setText(Integer.toString(vivienda.getId()));
    	paisLabel.setText(vivienda.getPais());
    	rentaLabel.setText(Float.toString(vivienda.getArriendo()));
    	
    }
}

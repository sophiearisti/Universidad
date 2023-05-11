package modelo;

public class Figura 
{
  private String ID_Figura;
  private Double area;
  private Double CoordenadaCentroX;
  private Double CoordenadaCentroY;

  
  Figura(String id_Figura,Double coordenadaCentroX,Double coordenadaCentroY)
  {
    this.ID_Figura=id_Figura;
    this.CoordenadaCentroX=coordenadaCentroX;
    this.CoordenadaCentroY=coordenadaCentroY;
  }
  
  public Double calcular_area()
  {
	  return null;
  }

public Double getArea() {
	return area;
}

public void setArea(Double area) {
	this.area = area;
}

public String getID_Figura() {
	return ID_Figura;
}

public void setID_Figura(String iD_Figura) {
	ID_Figura = iD_Figura;
}
 
}
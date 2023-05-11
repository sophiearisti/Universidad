package modelo;

public class Rectangulo extends Figura 
{
	
  private Double ancho;
  private Double largo;

  Rectangulo(String idFigura,Double coordenadaCentroX, Double coordenadaCentroY, Double ancho, Double largo) {
    
	super(idFigura, coordenadaCentroX, coordenadaCentroY);
    this.ancho = ancho;
    this.largo = largo;
  }

  public Double calcular_area() {
    Double area = this.ancho*this.largo;
    super.setArea(area);
    return area;
  }

@Override
public String toString() {
	return "Rectangulo [ancho=" + ancho + ", largo=" + largo + ", getArea()=" + getArea() + ", getID_Figura()="
			+ getID_Figura() + "]";
}


  
  
}
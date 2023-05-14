package modelo;

public class Cuadrado extends Figura {

  private Double lado;

   public Cuadrado(String idFigura, Double coordenadaCentroX, Double coordenadaCentroY, Double lado) {
    super(idFigura, coordenadaCentroX, coordenadaCentroY);
    this.lado = lado;
  }

  public Double calcular_area() {
    Double area = this.lado*this.lado;
    super.setArea(area);
    return area;
    
  }

public Double getLado() {
	return lado;
}

public void setLado(Double lado) {
	this.lado = lado;
}

@Override
public String toString() {
	return "Cuadrado [lado=" + lado + ", getArea()=" + getArea() + ", getID_Figura()=" + getID_Figura() + "]";
}



  
}
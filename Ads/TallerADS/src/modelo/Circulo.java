package modelo;

import java.lang.Math;

public class Circulo extends Figura {

  private double radio;

 
  public Circulo(String iD_Figura, Double coordenadaCentroX,Double coordenadaCentroY, Double radio){ 
    super(iD_Figura, coordenadaCentroX, coordenadaCentroY);
    this.radio = radio;
  }

	public double calcularArea() 
	{	
		super.setArea(Math.PI * Math.pow(this.radio, 2));
	    return Math.PI * Math.pow(radio, 2);
	}
   
    
   public double getRadio() {
        return this.radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }

	@Override
	public String toString() {
		return "Circulo [radio=" + radio + ", calcular_area()=" + calcular_area() + ", getArea()=" + getArea() + "]";
	}
    
    
  
}
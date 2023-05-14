package modelo;

public class Triangulo extends Figura
{
  
private Double Base;

  private Double Altura;public Double getBase() {
		return Base;
	}

	public void setBase(Double base) {
		Base = base;
	}

	public Double getAltura() {
		return Altura;
	}

	public void setAltura(Double altura) {
		Altura = altura;
	}

  
  

  public Triangulo(String iD_Figura, Double coordenadaCentroX,Double coordenadaCentroY, Double altura,Double base)
  {
    super(iD_Figura, coordenadaCentroX, coordenadaCentroY);
    
    this.Base=base;
    this.Altura=altura;
  }
  
  public Double calcular_area() 
  {
	  super.setArea(this.Altura*this.Base/2);
      return this.Altura*this.Base/2;
  }

@Override
public String toString() {
	return "Triangulo [Base=" + Base + ", Altura=" + Altura + ", getArea()=" + getArea() + ", getID_Figura()="
			+ getID_Figura() + "]";
}

	

}
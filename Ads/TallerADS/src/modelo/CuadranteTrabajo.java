package modelo;


import java.util.ArrayList; 

public class CuadranteTrabajo
{
  
  private double coordenadaXinicial;
  private double coordenadaXfinal;
  private double coordenadaYinicial;
  private double coordenadaYfinal;
  private ArrayList<Figura> Figuras;

  public CuadranteTrabajo()
  {
    
  }

    public CuadranteTrabajo(double coordenadaXinicial, double coordenadaXfinal, double coordenadaYinicial, double coordenadaYfinal)     {
      this.coordenadaXinicial = coordenadaXinicial;
      this.coordenadaXfinal = coordenadaXfinal;
      this.coordenadaYinicial = coordenadaYinicial;
      this.coordenadaYfinal = coordenadaYfinal;

      
    }

    public void anadirFigura(Figura figura)
    {
      this.Figuras.add(figura);
    }

    public void ListarFigura()
    {

      
    }

   public double getCoordenadaXinicial() {
        return this.coordenadaXinicial;
    }

    public void setCoordenadaXinicial(double coordenadaXinicial) {
        this.coordenadaXinicial = coordenadaXinicial;
    }

    public double getCoordenadaXfinal() {
        return this.coordenadaXfinal;
    }

    public void setCoordenadaXfinal(double coordenadaXfinal) {
        this.coordenadaXfinal = coordenadaXfinal;
    }

    public double getCoordenadaYinicial() {
        return this.coordenadaYinicial;
    }

    public void setCoordenadaYinicial(double coordenadaYinicial) {
        this.coordenadaYinicial = coordenadaYinicial;
    }

    public double getCoordenadaYfinal() {
        return coordenadaYfinal;
    }

    public void setCoordenadaYfinal(double coordenadaYfinal) {
        this.coordenadaYfinal = coordenadaYfinal;
    }
  
}
package modelo;
import java.util.ArrayList;

public class Main 
{
	public static void main(String[] args) 
	{
	    ArrayList<Figura> Figuras= new ArrayList<Figura>();
	    //1
	    Figuras.add(new Triangulo("figT1",0d,5d, 4d,8d));
	    //2
	    Figuras.add(new Triangulo("figT2",0.4d,4d, 3d,20d));
	    //3
	    Figuras.add(new Cuadrado("764", 2.0d, 4.0d, 9.0d));
	    //4
	    Figuras.add(new Cuadrado("798", 3.0d, 5.0d, 10.0d));
	    //5
	    Figuras.add(new Triangulo("figT3",0d,5d, 54d,8d));
	    //6
	    Figuras.add(new Circulo("798", 3.0d, 5.0d, 22d));
	    //7
	    Figuras.add(new Circulo("798", 3.0d, 5.0d, 8d));
	    //8
	    Figuras.add(new Rectangulo("3476", 2.0d, 4.0d, 8.0d,3.0d));
	    //9
	    Figuras.add(new Rectangulo("9876", 3.0d, 5.0d, 7.0d,5d));
	    //10
	    Figuras.add(new Rectangulo("9876", 3.0d, 5.0d, 21.0d,4d));
	    
	    
	    for(Figura fig: Figuras)
	    {
	    	 if (fig instanceof Cuadrado) 
	    	 {
	    		 fig.calcular_area();
	    	 }
	    	 
	    	 
	    	 System.out.println(fig.toString());
	    	 
	    }
	    
	    
	}
}

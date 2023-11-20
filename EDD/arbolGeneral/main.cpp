#include <iostream>

#include "ArbolGeneral.h"
#include "NodoGeneral.h"

int main() {
  
  ArbolGeneral<int> prueba(15);

 //std::cout << prueba.obtenerRaiz()->obtenerDato();
 
  prueba.insertarNodo(15, 6);
  prueba.insertarNodo(15, 20);
  prueba.insertarNodo(6, 3);
  prueba.insertarNodo(6, 9);
  prueba.insertarNodo(20, 18);
  prueba.insertarNodo(20, 24);
  prueba.insertarNodo(3, 1);
  prueba.insertarNodo(3, 4);
  prueba.insertarNodo(9, 7);
  prueba.insertarNodo(9, 12);
  prueba.insertarNodo(18, 17);

  std::cout<<"\neliminar: "<<prueba.eliminarNodo(18)<<std::endl;
 NodoGeneral<int>*  ver=prueba.obtenerRaiz();
  
 std::cout<<"\nhijos: "<<ver->cantHijos()<<std::endl;
 std::cout<<std::endl<< prueba.altura()<<std::endl;

  prueba.preOrden();

}

#include <iostream>

#include "ArbolBinario.h"

int main() 
{
  int num; 
    ArbolBinario<int> prueba;
    prueba.insertarNodo(7);
    prueba.insertarNodo(3);
    prueba.insertarNodo(20);
    prueba.insertarNodo(0);
    prueba.insertarNodo(5);
    prueba.insertarNodo(15);
    prueba.insertarNodo(25);
    prueba.insertarNodo(8);
    prueba.insertarNodo(1);  
    prueba.insertarNodo(4);
    prueba.insertarNodo(6);
    prueba.insertarNodo(30);
    prueba.insertarNodo(70);

    prueba.nivelOrden();

    std::cout<<std::endl<<prueba.altura();

    std::cout<<std::endl<<prueba.extremo_der()->obtenerDato();
    std::cout<<std::endl<<prueba.extremo_izq()->obtenerDato();

  
    std::cout<<std::endl<<prueba.buscar(0);
    std::cout<<std::endl<<prueba.buscar(40);

    std::cout<<std::endl<<prueba.datoRaiz();

    std::cout<<std::endl<<prueba.tamano();

    std::cout<<std::endl;
    prueba.nivelOrden();

    std::cout<<std::endl;
    prueba.preOrden();

    std::cout<<std::endl;
    prueba.posOrden();
  
  

}
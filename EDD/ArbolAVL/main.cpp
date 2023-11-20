#include "ArbolAVL.h"
#include <algorithm>
#include <cmath>
#include <ctime>
#include <fstream>
#include <iostream>
#include <iterator>
#include <list>
#include <set>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

int main(int argc, char *argv[]) {
 
  
  ArbolAVL<int> arbolAvl;

  arbolAvl.insertarNodo(13);
  arbolAvl.insertarNodo(12);
  arbolAvl.insertarNodo(21);
  arbolAvl.insertarNodo(44);
  arbolAvl.insertarNodo(55);
  arbolAvl.insertarNodo(19);
  arbolAvl.insertarNodo(1);
  arbolAvl.insertarNodo(3);
  arbolAvl.insertarNodo(9);
  arbolAvl.insertarNodo(2);
  arbolAvl.insertarNodo(39);
  arbolAvl.insertarNodo(0);

 std::cout<<std::endl;
  arbolAvl.nivelOrden();
  std::cout<<std::endl;
  arbolAvl.preOrden();
  std::cout<<std::endl;
  arbolAvl.posOrden();
  std::cout<<std::endl;
  arbolAvl.inOrden();
 
  
  std::cout<<std::endl;
  
  std::cout<<"EVAL"<<std::endl;
  
  std::cout<<arbolAvl.altura();
  
  std::cout<<std::endl;
  std::cout<<arbolAvl.extremo_der()->obtenerDato();
  std::cout<<std::endl;
  std::cout<<arbolAvl.extremo_izq()->obtenerDato();
  
  std::cout<<std::endl;
  std::cout<<arbolAvl.tamano();

  

  return 0;
}
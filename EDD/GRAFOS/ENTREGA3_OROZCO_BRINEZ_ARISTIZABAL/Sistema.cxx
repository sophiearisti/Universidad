#ifndef __SISTEMA__HXX__
#define __SISTEMA__HXX__

#include "Sistema.h"

//ingresar elemento a la lista de punto de interes
void Sistema::insertarPuntoInteres(Punto_de_interes puntoInteres)
{
  this->puntos_de_interes.push_back(puntoInteres);
}

//ingresar comando a la lista de comandos 
void Sistema::insertarComando(Comando comando)
{
  this->comandos.push_back(comando);
}

//obtener lista de comando
std::list<Comando> Sistema::obtenerComando()
{
  return this->comandos; 
}

//obtener lista de punto de interes
std::list<Punto_de_interes> Sistema::obtenerPuntoInteres()
{
  return this->puntos_de_interes;
}

void Sistema::borrarlistaPuntoInteres()
{
  return this->puntos_de_interes.clear(); 
}

void Sistema::borrarlistaComando()
{
  return this->comandos.clear(); 
}

int Sistema::tamanoPuntosInteres()
{
  return this->puntos_de_interes.size(); 
}

#endif
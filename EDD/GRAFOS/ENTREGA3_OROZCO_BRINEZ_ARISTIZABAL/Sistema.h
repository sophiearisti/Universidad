#ifndef __SISTEMA__H__
#define __SISTEMA__H__

#include "Comando.h"
#include "Punto_de_interes.h"
#include<list>

class Sistema 
{
  private:
  // declaracion lista puntos de interes
  std::list<Punto_de_interes> puntos_de_interes;

  // declaración lista comandos
  std::list<Comando> comandos;
  
  public:

    Sistema() = default;

    //ingresar elemento a la lista de punto de interes
    void insertarPuntoInteres(Punto_de_interes puntoInteres);

    //ingresar comando a la lista de comandos 
    void insertarComando(Comando comando);

    //obtener lista de comando
    std::list<Comando> obtenerComando();

    //obtener lista de punto de interes
    std::list<Punto_de_interes> obtenerPuntoInteres();

    void borrarlistaPuntoInteres();

    void borrarlistaComando();

    int tamanoPuntosInteres();

};

#endif
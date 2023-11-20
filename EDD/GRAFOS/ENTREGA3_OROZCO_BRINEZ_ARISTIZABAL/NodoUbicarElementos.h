#ifndef _NODOUbicarElementos_H_
#define _NODOUbicarElementos_H_

#include "Punto_de_interes.h"
#include <queue>
#include <list>

// Visualizador del estado del nodo.
enum class estadoHoja { ES_HOJA, NO_ES_HOJA };

class NodoUbicarElementos {

protected:
  Punto_de_interes punto;

  // Primera (I) posición.
  NodoUbicarElementos *hijoSupIzq;

  // Segunda (II) posición.
  NodoUbicarElementos *hijoSupDer;

  // Tercera (III) posición.
  NodoUbicarElementos *hijoInfDer;

  // Primera (IV) posición.
  NodoUbicarElementos *hijoInfIzq;

public:
  NodoUbicarElementos();
  NodoUbicarElementos(Punto_de_interes punto_de_interes);
  ~NodoUbicarElementos();
  Punto_de_interes obtenerPunto_de_interes();
  void fijarPunto_de_interes(Punto_de_interes punto_de_interes);
  NodoUbicarElementos *obtenerHijoSupIzq();
  NodoUbicarElementos *obtenerHijoSupDer();
  NodoUbicarElementos *obtenerHijoInfDer();
  NodoUbicarElementos *obtenerHijoInfIzq();
  void fijarHijoSupIzq(Punto_de_interes punto_de_interes);
  void fijarHijoSupDer(Punto_de_interes punto_de_interes);
  void fijarHijoInfDer(Punto_de_interes punto_de_interes);
  void fijarHijoInfIzq(Punto_de_interes punto_de_interes);
  estadoHoja esHoja();
  NodoUbicarElementos *insertar(Punto_de_interes punto_de_interes,
                                NodoUbicarElementos *nodo,std::list<Punto_de_interes> &puntos_sin_ingresar);
  void preOrden(NodoUbicarElementos *nodo);
  void inOrden(NodoUbicarElementos *nodo);
  void posOrden(NodoUbicarElementos *nodo);
  void nivelOrden();
  void evaluarCuadrante(NodoUbicarElementos *nodo, float x_min,float x_max,float y_min,float y_max,std::list<Punto_de_interes> &Elementos_en_cuadrante);
  void evaluarNodo(NodoUbicarElementos *nodo, float x_min,float x_max,float y_min,float y_max,std::list<Punto_de_interes> &Elementos_en_cuadrante);
};

#endif
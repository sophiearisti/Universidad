#ifndef _ARBOLUbicarElementos_H_
#define _ARBOLUbicarElementos_H_

#include "NodoUbicarElementos.h"
#include <list>

// Visualizador del estado del arbol.
enum class estadoVacio { ES_VACIO, NO_ES_VACIO };

class ArbolUbicarElementos {
protected:
  NodoUbicarElementos *raiz;

public:
  ArbolUbicarElementos();
  ArbolUbicarElementos(Punto_de_interes punto_de_interes);
  ~ArbolUbicarElementos();
  Punto_de_interes punto_de_interesRaiz();
  NodoUbicarElementos *obtenerRaiz();
  void fijarRaiz(Punto_de_interes punto_de_interes);
  void fijarRaiz(NodoUbicarElementos *nodo);
  estadoVacio esVacio();
  bool insertar(Punto_de_interes punto_de_interes,std::list<Punto_de_interes> &puntos_sin_ingresar);

  // Recoridos iguales al arbol binario ordenado.
  void preOrden();

  // Visita en la mitad de los hijos inOrden.
  void inOrden();
  void posOrden();
  void nivelOrden();
  // No buscar.
  // No eliminar.
  void evaluarCuadrante(float x_min,float x_max,float y_min,float y_max,std::list<Punto_de_interes> &Elementos_en_cuadrante);
};

#endif
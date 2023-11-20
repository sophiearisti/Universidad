#ifndef ArbolN_H
#define ArbolN_H

#include "NodoGeneral.h"
#include <iostream>
#include <list>

template <class T> class ArbolGeneral {
protected:
  NodoGeneral<T> *raiz;

public:
  ArbolGeneral();
  ArbolGeneral(T val);
  ~ArbolGeneral();

  bool esVacio();

  NodoGeneral<T> *obtenerRaiz();
  void fijarRaiz(NodoGeneral<T> *nraiz);

  bool insertarNodo(T padre, T n);
  bool insertarNodo(T padre, T n, NodoGeneral<T> *nodo);

  bool eliminarNodo(T nodo);
  bool eliminarNodo(T n, NodoGeneral<T> *nodo);

  bool buscar(T nodo);
  bool buscar(T n, NodoGeneral<T> *nodo);

  int altura();
  int altura(NodoGeneral<T> *nodo);

  unsigned int tamano();
  int tamano(NodoGeneral<T> *nodo);

  void preOrden();
  void preOrden(NodoGeneral<T> *nodo);

  void posOrden();
  void posOrden(NodoGeneral<T> *nodo);

  void nivelOrden();
};

#include "ArbolGeneral.hxx"

#endif

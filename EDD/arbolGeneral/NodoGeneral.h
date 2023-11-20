#ifndef NODOGENERAL_H
#define NODOGENERAL_H

#include <iostream>
#include <list>


template <class T>
class NodoGeneral {
protected:
  T dato;

public:
  std::list<NodoGeneral<T> *> desc;
  NodoGeneral();
  //~NodoGeneral();
  T &obtenerDato();
  void fijarDato(T &val);
  void limpiarLista();
  void adicionarDesc(T &nval);
  bool eliminarDesc(T val);
  bool esHoja();
  unsigned int cantHijos();
};

#include "NodoGeneral.hxx"

#endif

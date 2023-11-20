#ifndef A_binario_H
#define A_binario_H

#include "nodoBinario.h"

#include <iostream>
#include <list>

template <class T>

class ArbolBinario {

protected:
  NodoBinario<T> *raiz;

public:
  
  ArbolBinario();
  ArbolBinario(T val);
  ~ArbolBinario();


  bool esVacio();
  T datoRaiz();
  NodoBinario<T>* obtenerRaiz();
  void cambiarRaiz(NodoBinario<T>* nodo);
  int altura();
  int altura(NodoBinario<T>* nodo);
  unsigned int tamano();
  unsigned tamano(NodoBinario<T>* nodo);
  bool insertarNodo(T n);
  bool deleteNodo(T n);
  bool buscar(T n);
  void preOrden();
  void preOrden(NodoBinario<T>* nodo);  
  void inOrden();
  void inOrden(NodoBinario<T>* nodo);
  void posOrden();
  void posOrden(NodoBinario<T>* nodo);
  void nivelOrden();
   NodoBinario<T>* extremo_der();
   NodoBinario<T>* extremo_izq();
};

#include "ArbolBinario.hxx"

#endif

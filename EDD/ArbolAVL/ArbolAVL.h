#ifndef A_AVL_H
#define A_AVL_H

#include "nodoAVL.h"

#include <iostream>
#include <list>

template <class T>

class ArbolAVL {

protected:
  NodoAVL<T> *raiz;

public:
  
  ArbolAVL();
  ArbolAVL(T val);
  ~ArbolAVL();


  bool esVacio();
  T datoRaiz();
  NodoAVL<T>* obtenerRaiz();
  void cambiarRaiz(NodoAVL<T>* nodo);
  int altura();
  int altura(NodoAVL<T>* nodo);
  unsigned int tamano();
  unsigned tamano(NodoAVL<T>* nodo);
  bool insertarNodo(T n);
  bool deleteNodo(T n);
  bool buscar(T n);
  void preOrden();
  void preOrden(NodoAVL<T>* nodo);  
  void inOrden();
  void inOrden(NodoAVL<T>* nodo);
  void posOrden();
  void posOrden(NodoAVL<T>* nodo);
  void nivelOrden();
  NodoAVL<T>* extremo_izq();
  NodoAVL<T>* extremo_der();

//AVL
    void balancear(T val, NodoAVL<T>** nodo);
    NodoAVL<T>* giroDerecha(NodoAVL<T> *&padre);
    NodoAVL<T>* giroIzquierda(NodoAVL<T> *&padre);
    NodoAVL<T> * giroIzquierdaDerecha(NodoAVL<T> *&padre);
    NodoAVL<T> * giroDerechaIzquierda(NodoAVL<T> *&padre);

};

#include "ArbolAVL.hxx"

#endif

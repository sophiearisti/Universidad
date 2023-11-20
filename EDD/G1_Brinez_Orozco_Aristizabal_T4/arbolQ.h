#ifndef A_Q_H
#define A_Q_H

#include "nodoQ.h"
#include <vector>

class ArbolQ {
protected:
  NodoQ *raiz;

public:
  ArbolQ();
  ArbolQ(int val);
  ~ArbolQ();
  int datoRaiz();
  NodoQ *obtenerRaiz();
  void fijarRaiz(NodoQ *n_raiz);
  bool esVacio();
  bool insertar(int val);
  bool insertarNodo(int nval, NodoQ *nodo);
  void preOrden();
  void preOrden(NodoQ *nodo);
  void inOrden();
  void inOrden(NodoQ *nodo);
  void posOrden();
  void posOrden(NodoQ *nodo);
  void nivelOrden();
  void armarMatriz(std::vector<std::vector<int>> &imagen, int tamano);
  void armarMatriz(NodoQ *nodo, std::vector<std::vector<int>> &imagen,
                   int tamano, int inicioI, int inicioJ);
};

#endif

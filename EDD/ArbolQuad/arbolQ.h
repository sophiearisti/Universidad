#ifndef A_Q_H
#define A_Q_H

#include "nodoQ.h"

class ArbolQ 
{
  protected:
    NodoQ* raiz;
  public:
    ArbolQ();
    ArbolQ(punto val);
    ~ArbolQ();
    punto datoRaiz();
    NodoQ* obtenerRaiz();
    void fijarRaiz(NodoQ* n_raiz);
    bool esVacio();
    bool insertar(punto val);
    void preOrden();
    void preOrden(NodoQ* nodo);
    void inOrden();
    void inOrden(NodoQ* nodo);
    void posOrden();
    void posOrden(NodoQ* nodo);
    void nivelOrden();

};

#include "arbolQ.hxx"

#endif
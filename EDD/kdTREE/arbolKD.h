#ifndef A_KD_H
#define A_KD_H

#include "nodoKD.h"

class ArbolKD 
{
  protected:
    NodoKD* raiz;
  public:
    ArbolKD();
    ArbolKD(punto val);
    ~ArbolKD();
    punto datoRaiz();
    NodoKD* obtenerRaiz();
    void fijarRaiz(NodoKD* n_raiz);
    bool esVacio();
    bool insertar(punto val);
    void preOrden();
    void preOrden(NodoKD* nodo);
    void inOrden();
    void inOrden(NodoKD* nodo);
    void posOrden();
    void posOrden(NodoKD* nodo);
    void nivelOrden();
};

#include "arbolKD.hxx"

#endif
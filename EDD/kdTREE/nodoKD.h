#ifndef N_KD_H
#define N_KD_H

#include "Punto.h"

class NodoKD {
  protected:
    punto dato;
    NodoKD* hijoIzq;
    NodoKD* hijoDer;
  public:
    NodoKD();
    NodoKD(punto val);
    ~NodoKD();
    bool esHoja();
    punto obtenerDato();
    void fijarDato(punto val);
    NodoKD* obtenerHijoIzq();
    NodoKD* obtenerHijoDer();
    void fijarHijoIzq(NodoKD* izq);
    void fijarHijoDer(NodoKD* der);
};

#include "nodoKD.hxx"

#endif

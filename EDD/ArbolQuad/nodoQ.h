#ifndef N_Q_H
#define N_Q_H

#include "Punto.h"

#include <iostream>
#include <list>

class  NodoQ
{
    protected:
      punto dato;
      NodoQ* hijoSupIzq;
      NodoQ* hijoSupDer;
      NodoQ* hijoInfIzq;
      NodoQ* hijoInfDer;

    public:
      NodoQ();
      NodoQ(punto val);
      ~NodoQ();
      punto obtenerDato();
      void fijarDato(punto val);
      NodoQ* obtenerHijoSupIzq();
      NodoQ* obtenerHijoSupDer();
      NodoQ* obtenerHijoInfIzq();
      NodoQ* obtenerHijoInfDer();
      void fijarHijoSupIzq(NodoQ* sizq);
      void fijarHijoSupDer(NodoQ* sder);
      void fijarHijoInfIzq(NodoQ* iizq);
      void fijarHijoInfDer(NodoQ* ider);
      bool esHoja();
};

#include "nodoQ.hxx"

#endif

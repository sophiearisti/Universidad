#ifndef N_Q_H
#define N_Q_H


#include <iostream>
#include <list>

class  NodoQ
{
    protected:
      int dato;
      NodoQ* hijoSupIzq;
      NodoQ* hijoSupDer;
      NodoQ* hijoInfIzq;
      NodoQ* hijoInfDer;

    public:
      NodoQ();
      NodoQ(int val);
      ~NodoQ();
      int obtenerDato();
      void fijarDato(int val);
      NodoQ* obtenerHijoSupIzq();
      NodoQ* obtenerHijoSupDer();
      NodoQ* obtenerHijoInfIzq();
      NodoQ* obtenerHijoInfDer();
      void fijarHijoSupIzq(NodoQ* sizq);
      void fijarHijoSupDer(NodoQ* sder);
      void fijarHijoInfIzq(NodoQ* iizq);
      void fijarHijoInfDer(NodoQ* ider);
      bool esHoja();
      bool estaCompleto();
    int estaCompletode1sy0s();
};

#endif

#ifndef __NODOBINARIO__H__
#define __NODOBINARIO__H__

#include <iostream>
#include <list>

template< class T >
class NodoBinario
{
  protected:
    T dato;
    NodoBinario<T> *hijoIzq;
    NodoBinario<T> *hijoDer;

  public:
  	NodoBinario();
    T& obtenerDato();
    void fijarDato(T& val);
    NodoBinario<T>* obtenerHijoIzq();
    NodoBinario<T>* obtenerHijoDer();
    void fijarHijoIzqNULL();
    void fijarHijoDerNULL();
    void fijarHijoIzq(NodoBinario<T> *izq);
    void fijarHijoDer(NodoBinario<T> *der);
    void limpiar();
    void adicionarDesc(T &nval);
    bool eliminarDesc(T val);
    bool esHoja();
    unsigned int cantHijos(); 
};

#include "nodoBinario.hxx"
#endif

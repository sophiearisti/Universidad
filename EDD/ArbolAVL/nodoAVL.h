#ifndef __NODOAVL__H__
#define __NODOAVL__H__

#include <iostream>
#include <list>

template< class T >
class NodoAVL
{
  protected:
    T dato;
    NodoAVL<T> *hijoIzq;
    NodoAVL<T> *hijoDer;

  public:
  	NodoAVL();
    T& obtenerDato();
    void fijarDato(T& val);
    NodoAVL<T>*& obtenerHijoIzq();
    NodoAVL<T>*& obtenerHijoDer();
    void fijarHijoIzqNULL();
    void fijarHijoDerNULL();
    void fijarHijoIzq(NodoAVL<T> *izq);
    void fijarHijoDer(NodoAVL<T> *der);
    void limpiar();
    void adicionarDesc(T &nval);
    bool eliminarDesc(T val);
    bool esHoja();
    unsigned int cantHijos();

};

#include "nodoAVL.hxx"
#endif

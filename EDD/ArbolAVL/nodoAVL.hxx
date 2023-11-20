#ifndef __NODOAVL__HXX__
#define __NODOAVL__HXX__

#include "nodoAVL.h"


template<class T>
NodoAVL<T>::NodoAVL()
{
    this->hijoIzq=NULL;
    this->hijoDer=NULL;
}


template<class T>
T& NodoAVL<T>::obtenerDato()
{
    return this->dato;
}


template<class T>
void NodoAVL<T>::fijarDato(T& val)
{
    this->dato= val;
}


template <class T>
void NodoAVL<T>::limpiar()
{
  this->hijoIzq=NULL;
  this->hijoDer=NULL;
}


template<class T>
NodoAVL<T>*& NodoAVL<T>::obtenerHijoIzq()
{
    return this->hijoIzq;
}


template<class T>
NodoAVL<T>*& NodoAVL<T>::obtenerHijoDer()
{
    return this->hijoDer;
}


template<class T>
void NodoAVL<T>::fijarHijoIzq(NodoAVL<T> *izq)
{
    this->hijoIzq = izq;
}


template<class T>
void NodoAVL<T>::fijarHijoDer(NodoAVL<T> *der)
{
    this->hijoDer = der;
}

template<class T>
void NodoAVL<T>::fijarHijoIzqNULL()
{
    this->hijoIzq = nullptr;
}

template<class T>
void NodoAVL<T>::fijarHijoDerNULL()
{
    this->hijoDer = nullptr;
}


template <class T>
bool NodoAVL<T>::esHoja()
{
  return this->hijoDer==NULL && this->hijoIzq==NULL;
}

template <class T>
unsigned int NodoAVL<T>::cantHijos()
{
  int cant=0;
  
  if(this->hijoDer!=NULL)
  {
    cant++;
  }

  if(this->hijoIzq!=NULL)
  {
    cant++;
  }

  return cant;
  
}

#endif


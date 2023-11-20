#ifndef __NODOBINARIO__HXX__
#define __NODOBINARIO__HXX__

#include "nodoBinario.h"


template<class T>
NodoBinario<T>::NodoBinario()
{
    this->hijoIzq=NULL;
    this->hijoDer=NULL;
}


template<class T>
T& NodoBinario<T>::obtenerDato()
{
    return this->dato;
}


template<class T>
void NodoBinario<T>::fijarDato(T& val)
{
    this->dato = val;
}


template <class T>
void NodoBinario<T>::limpiar()
{
  this->hijoIzq=NULL;
  this->hijoDer=NULL;
}


template<class T>
NodoBinario<T>* NodoBinario<T>::obtenerHijoIzq()
{
    return this->hijoIzq;
}


template<class T>
NodoBinario<T>* NodoBinario<T>::obtenerHijoDer()
{
    return this->hijoDer;
}


template<class T>
void NodoBinario<T>::fijarHijoIzq(NodoBinario<T> *izq)
{
    this->hijoIzq = izq;
}


template<class T>
void NodoBinario<T>::fijarHijoDer(NodoBinario<T> *der)
{
    this->hijoDer = der;
}

template<class T>
void NodoBinario<T>::fijarHijoIzqNULL()
{
    this->hijoIzq = nullptr;
}

template<class T>
void NodoBinario<T>::fijarHijoDerNULL()
{
    this->hijoDer = nullptr;
}


template <class T>
bool NodoBinario<T>::esHoja()
{
  return this->hijoDer==NULL && this->hijoIzq==NULL;
}

template <class T>
unsigned int NodoBinario<T>::cantHijos()
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
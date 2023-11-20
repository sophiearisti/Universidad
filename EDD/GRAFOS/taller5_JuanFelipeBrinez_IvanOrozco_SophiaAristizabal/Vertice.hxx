#ifndef __Vertice__HXX__
#define __Vertice__HXX__

#include <iostream>
#include <iomanip>
#include "Vertice.h"

template <class T, class U>
Vertice<T,U>::Vertice()
{
  this->Peso=0;
  this->Grado=0;
}

template <class T, class U>   
Vertice<T, U>::Vertice(const U peso, const T indice)
{
  this->Peso=peso;
  this->Indice=indice; 
  this->Grado=0;
}

template <class T, class U>    
void Vertice<T, U>::setIndice(T indice)
{
   this->Indice=indice; 
}

template <class T, class U>
void Vertice<T, U>::setPeso(U peso)
{
   this->Peso=peso;
}

template <class T, class U>
void Vertice<T, U>::setVisitado(bool vis)
{
   this->visitado=vis;
}
template <class T, class U>
T Vertice<T, U>::getIndice()
{
  return this->Indice;
}

template <class T, class U>
U Vertice<T, U>::getPeso()
{
  return this->Peso;
}

template <class T, class U>
int Vertice<T, U>::getGrado() {
    return this->Grado;
};

template <class T, class U>
void Vertice<T, U>::setGrado(int grado) {
    this->Grado = grado;
};

template <class T, class U>
void Vertice<T, U>::setpredecesor(unsigned long predecesor)
{
  this->Predecesor=predecesor;
}

template <class T, class U>
unsigned long Vertice<T, U>::getpredecesor()
{
  return this->Predecesor;
}

#endif
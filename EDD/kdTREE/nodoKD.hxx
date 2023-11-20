#ifndef KD_NODO_HXX
#define KD_NODO_HXX

#include "nodoKD.h"


  NodoKD::NodoKD()
  {
     this->hijoIzq=NULL;
     this->hijoDer=NULL;
  }

  NodoKD::NodoKD(punto val)
  {
    this->fijarDato(val);
  }

//mirar
  NodoKD::~NodoKD()
  {
     this->hijoIzq=NULL;
     this->hijoDer=NULL;
  }

  void NodoKD::fijarDato(punto val)
  {
    this->dato= val;
  }

  punto NodoKD::obtenerDato()
  {
    return this->dato;
  }

  NodoKD* NodoKD::obtenerHijoIzq()
  {
     return this->hijoIzq;
  }
  
  NodoKD* NodoKD::obtenerHijoDer()
  {
     return this->hijoDer;  
  }

  void NodoKD::fijarHijoIzq(NodoKD* sizq)
  {
     this->hijoIzq=sizq;
  }

  void NodoKD::fijarHijoDer(NodoKD* sder)
  {
     this->hijoDer=sder;  
  }  

  bool NodoKD::esHoja()
  {
     return this->hijoDer==NULL && this->hijoIzq==NULL;
  }

#endif

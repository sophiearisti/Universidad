#ifndef Q_NODO_HXX
#define Q_NODO_HXX

#include "nodoQ.h"

  NodoQ::NodoQ()
  {
     this->hijoSupIzq=NULL;
     this->hijoSupDer=NULL;
     this->hijoInfIzq=NULL;
     this->hijoInfDer=NULL;
  }

  NodoQ::NodoQ(punto val)
  {
    this->fijarDato(val);
  }

//mirar
  NodoQ::~NodoQ()
  {
     this->hijoSupIzq=NULL;
     this->hijoSupDer=NULL;
     this->hijoInfIzq=NULL;
     this->hijoInfDer=NULL;
  }

  void NodoQ::fijarDato(punto val)
  {
    this->dato= val;
  }

  punto NodoQ::obtenerDato()
  {
    return this->dato;
  }

  NodoQ* NodoQ::obtenerHijoSupIzq()
  {
     return this->hijoSupIzq;
  }
  
  NodoQ* NodoQ::obtenerHijoSupDer()
  {
     return this->hijoSupDer;  
  }

  NodoQ* NodoQ::obtenerHijoInfIzq()
  {
     return this->hijoInfIzq;    
  }

  NodoQ* NodoQ::obtenerHijoInfDer()
  {
     return this->hijoInfDer;    
  }

  void NodoQ::fijarHijoSupIzq(NodoQ* sizq)
  {
     this->hijoSupIzq=sizq;
  }

  void NodoQ::fijarHijoSupDer(NodoQ* sder)
  {
     this->hijoSupDer=sder;  
  }

  void NodoQ::fijarHijoInfIzq(NodoQ* iizq)
  {
     this->hijoInfIzq=iizq;
  }

  void NodoQ::fijarHijoInfDer(NodoQ* ider)
  {
     this->hijoInfDer=ider;    
  }   

  bool NodoQ::esHoja()
  {
     return this->hijoInfDer==NULL && this->hijoInfIzq==NULL && this->hijoSupDer==NULL && this->hijoSupIzq==NULL;
  }

#endif 

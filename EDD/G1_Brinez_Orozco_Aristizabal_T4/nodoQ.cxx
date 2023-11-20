#ifndef Q_NODO_HXX
#define Q_NODO_HXX

#include "nodoQ.h"

  NodoQ::NodoQ()
  {
     this->dato =0;
     this->hijoSupIzq=NULL;
     this->hijoSupDer=NULL;
     this->hijoInfIzq=NULL;
     this->hijoInfDer=NULL;
  }

  NodoQ::NodoQ(int val)
  {
      this->fijarDato(val);
      this->hijoSupIzq=NULL;
      this->hijoSupDer=NULL;
      this->hijoInfIzq=NULL;
      this->hijoInfDer=NULL;
  }

//mirar
  NodoQ::~NodoQ()
  {
     this->hijoSupIzq=NULL;
     this->hijoSupDer=NULL;
     this->hijoInfIzq=NULL;
     this->hijoInfDer=NULL;
  }

  void NodoQ::fijarDato(int val)
  {
    this->dato= val;
  }

  int NodoQ::obtenerDato()
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

 bool NodoQ::estaCompleto()
  {
     return this->hijoInfDer!=NULL && this->hijoInfIzq!=NULL && this->hijoSupDer!=NULL && this->hijoSupIzq!=NULL;
  }

 int NodoQ::estaCompletode1sy0s()
 {
     //eta completo
     if(this->hijoInfDer!=NULL && this->hijoInfIzq!=NULL && this->hijoSupDer!=NULL && this->hijoSupIzq!=NULL)
     {
         //ver si hay un dos en el cuarto
         if(this->hijoInfIzq->obtenerDato()==2)
         {
            return 4;
         }
         
         return 0;
     }
     
     //no esta completo
     return -1;
     
 }


#endif 

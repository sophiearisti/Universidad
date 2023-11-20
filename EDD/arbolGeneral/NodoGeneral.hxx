#ifndef NODO_HXX
#define NODO_HXX

#include "NodoGeneral.h"

template <class T>
NodoGeneral<T>:: NodoGeneral()
{
  this->desc.clear();
}


/*template <class T>
NodoGeneral<T>:: ~NodoGeneral()
{
  typename std::list<NodoGeneral<T>>::iterator it;
  for (it = this->desc.begin(); it != this->desc.end(); it++) {
    delete *it;
  }
  this->desc.clear();
}*/


template <class T>
T &NodoGeneral<T>::obtenerDato()
{
  return this->dato;
}

template <class T>
void NodoGeneral<T>::fijarDato(T &val)
{
  this->dato=val;
}


template <class T>
void NodoGeneral<T>::limpiarLista()
{
  this->desc.clear();
}


template <class T>
void NodoGeneral<T>::adicionarDesc(T &nval)
{
  NodoGeneral<T> *nodo= new NodoGeneral<T>;

  nodo->fijarDato(nval);
  this->desc.push_back(nodo);
  
}


template <class T>
bool NodoGeneral<T>::eliminarDesc(T val)
{
  typename std::list<NodoGeneral<T>*>::iterator it;

  NodoGeneral<T> *nodoAux= new NodoGeneral<T>;

  bool eliminado=false;
  
  for(it=this->desc.begin(); it!=this->desc.end(); it++)
  {
    nodoAux= *it;

    if(nodoAux->obtenerDato()==val)
    {
      //para el ciclo porque lo encontro
      break;
    }
    
  }

  //lo encontre
  if(it!=this->desc.end())
  {

    //elimine el apuntador de la lista
    this->desc.erase(it);

    //elimine la memoria
   delete *it;
    
    eliminado=true;
    
  }
 
  return eliminado;

}


template <class T>
bool NodoGeneral<T>::esHoja()
{
  return this->desc.size()==0;
}

template <class T>
unsigned int NodoGeneral<T>::cantHijos()
{
  return this->desc.size();
}

#endif

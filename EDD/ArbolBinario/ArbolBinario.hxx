#ifndef A_binario_HXX
#define A_binario_HXX


#include "ArbolBinario.h"
#include<queue>


template <class T>
  ArbolBinario<T>::ArbolBinario()
  {
    this->raiz=NULL;
  }


template <class T>
ArbolBinario<T>::ArbolBinario(T val)
{
    NodoBinario <T>* nodo= new NodoBinario<T>;
    nodo->fijarDato(val);
    this->raiz=nodo;   
}

template <class T>
void ArbolBinario<T>::cambiarRaiz(NodoBinario<T>* nodo)
{
   this->raiz=nodo;
}

template <class T>
NodoBinario<T>* ArbolBinario<T>::obtenerRaiz()
{ 
  return this->raiz;
}

//REVISAR
template <class T>
ArbolBinario<T>::~ArbolBinario()
{
  if(this->raiz!=NULL)
  {
    this->raiz=NULL;
  }
}


template <class T>
bool ArbolBinario<T>::esVacio()
{
    return this->raiz==NULL;
}


template <class T>
T ArbolBinario<T>::datoRaiz()
{
  return (this->raiz)->obtenerDato();
}

//CORRECTO
template <class T>
  int ArbolBinario<T>::altura()
  {
    if(this->esVacio())
    {
      return -1;
    } 
    else
    {
      return this->altura(this->obtenerRaiz());
    }
  }

//CORRECTO
template <class T> 
int ArbolBinario<T>::altura(NodoBinario<T>* nodo)
{ 
  int alt;

  //ver si el nodo es hoja
  if(nodo->esHoja())
  {
      alt=0;
  }
  else
  {
    
    int alt_izq=-1;
    int alt_der=-1;

    if(nodo->obtenerHijoIzq()!=NULL)
    {
      alt_izq=altura(nodo->obtenerHijoIzq());
    }

    if(nodo->obtenerHijoDer()!=NULL)
    {
       alt_der=altura(nodo->obtenerHijoDer());
    }

    if( alt_izq>alt_der)
    {
      alt=alt_izq+1;
    }
    else
    {
      alt=alt_der+1;
    }
  }
  
  return alt;
}


template <class T>
unsigned int ArbolBinario<T>::tamano()
{
   if(this->esVacio())
    {
      return 0;
    } 
    else
    {
      return this->tamano(this->obtenerRaiz());
    }
}

//CORRECTO
template <class T>
unsigned int ArbolBinario<T>::tamano(NodoBinario<T>* nodo)
{

  int tam;
  
  //ver si el nodo es hoja
  if(nodo->esHoja())
  {
      tam=0;
  }
  else
  {
    
    int tamano_izq=0;
    int tamano_der=0;

    if(nodo->obtenerHijoIzq()!=NULL)
    {
      tamano_izq+=tamano(nodo->obtenerHijoIzq());
    }

    if(nodo->obtenerHijoDer()!=NULL)
    {
       tamano_der+=tamano(nodo->obtenerHijoDer());
    }

    tam=tamano_der+tamano_izq;
  }
  
  return tam+1;
  
}


//CORRECTO
//ITERATIVA
template <class T>
bool ArbolBinario<T>::insertarNodo(T n)
{
    if(this->raiz==NULL)
    {
       NodoBinario <T>* nodo= new NodoBinario<T>;
       nodo->fijarDato(n);
       this->raiz=nodo;
    }
    
    NodoBinario<T>* nodo=this->raiz;
    //nodo al que lo voy a insertar
    NodoBinario<T>* padre=this->raiz;
    bool insertado=false, duplicado=false;

  
        while(nodo!=NULL && !duplicado)
        {
            padre=nodo;
          
            if(n<nodo->obtenerDato())
            {
              nodo=nodo->obtenerHijoIzq();
            }
            else if(n>nodo->obtenerDato())
            {
              nodo=nodo->obtenerHijoDer();
            }
            else
            {
              //ver si el dato esta duplicado, porque no se puede
              duplicado=true;
            }
        }

        if(!duplicado)
        {
          NodoBinario<T>* nuevo=new NodoBinario<T>;
          
          nuevo->fijarDato(n);

          if(nuevo!=NULL)
          {
            if(n>padre->obtenerDato())
            {
              padre->fijarHijoDer(nuevo);
            }
            else
            {
              padre->fijarHijoIzq(nuevo);
            }

            insertado=true;
             //delete nuevo;
          }
        }

   
    //delete padre;
   // delete nodo;
  
    return insertado;
}

//ITERATIVA
template <class T>
bool ArbolBinario<T>::deleteNodo(T n) {
    NodoBinario<T> *padre= this->raiz;
    NodoBinario<T> *nodo=this->raiz;;
  
    if(this->esVacio())
    {  
       return false;
    }

    while(nodo!=NULL && nodo->obtenerDato()!= n)
    {
        padre=nodo;
      
        if(n<nodo->obtenerDato())
        {
          nodo=nodo->obtenerHijoIzq();
        }
        else if(n>nodo->obtenerDato())
        {
          nodo=nodo->obtenerHijoDer();
        }          
    }
  
    if (nodo== nullptr) {
        // NodoBinario<T> with the given n not found
        return false;
    }
  
    // Case 1: NodoBinario<T> to be deleted has no children
    if (nodo->obtenerHijoIzq() == nullptr && nodo->obtenerHijoDer() == nullptr) {
        if (nodo == this->raiz) {
                  std::cout<<"hola";
                  this->raiz = NULL;
                }
                if (padre->obtenerHijoIzq() == nodo) {
                    padre->fijarHijoIzqNULL();
                }
                else {
                    padre->fijarHijoDerNULL();
                }
      
        delete nodo;
    }
    // Case 2: NodoBinario<T> to be deleted has only one child
    else if (nodo->obtenerHijoIzq() == nullptr || nodo->obtenerHijoDer() == nullptr) {
      
        NodoBinario<T> *child = (nodo->obtenerHijoIzq() != nullptr) ? nodo->obtenerHijoIzq() : nodo->obtenerHijoDer();
        if (nodo == this->obtenerRaiz()) {
            this->cambiarRaiz(child);
        }
        else if (padre->obtenerHijoIzq() == nodo) {
            padre->fijarHijoIzq(child);
        }
        else {
            padre->fijarHijoDer(child);
        }
        delete nodo;
    }
    // Case 3: NodoBinario<T> to be deleted has two children
    else {
        NodoBinario<T> *successor = nodo->obtenerHijoIzq();
        NodoBinario<T> *successorpadre = nodo;

        while (successor->obtenerHijoDer() != nullptr) {
            successorpadre = successor;
            successor = successor->obtenerHijoDer();
        }

        nodo->fijarDato(successor->obtenerDato());

       

        if (successorpadre->obtenerHijoIzq() == successor) {
            successorpadre->fijarHijoIzq(successor->obtenerHijoIzq());
        }
        else {
            successorpadre->fijarHijoDer(successor->obtenerHijoDer());
        }

        delete successor;
    }

    return true;
}



//CORRECTO
//ITERATIVA
template <class T>
bool ArbolBinario<T>::buscar(T n)
{
  NodoBinario<T>* nodo=this->raiz;
  bool encontrado=false;

  while(nodo!=NULL && !encontrado)
  {
      if(n<nodo->obtenerDato())
      {
        nodo=nodo->obtenerHijoIzq();
      }
      else if(n>nodo->obtenerDato())
      {
        nodo=nodo->obtenerHijoDer();
      }
      else
      {
        encontrado=true;
      }
        
  }

  return encontrado;
}

//CORRECTO
template <class T>
void ArbolBinario<T>::inOrden()
{
  if(!this->esVacio())
  {
    this->inOrden(this->raiz);
  }
}

//CORRECTO
template <class T>
void ArbolBinario<T>::inOrden(NodoBinario<T>* nodo)
{
  if(nodo!=NULL)
  {
    this->inOrden(nodo->obtenerHijoIzq());
    std::cout<<nodo->obtenerDato()<<" ";

    this->inOrden(nodo->obtenerHijoDer());
  }
  
}

//CORRECTO
template <class T>
void ArbolBinario<T>::preOrden()
{
  if(!this->esVacio())
  {
    this->preOrden(this->raiz);
  }
}

//CORRECTO
template <class T>
void ArbolBinario<T>::preOrden(NodoBinario<T>* nodo)
{
  if(nodo!=NULL)
  {
    std::cout<<nodo->obtenerDato()<<" ";
  
    this->preOrden(nodo->obtenerHijoIzq());

    this->preOrden(nodo->obtenerHijoDer());
  }
}

//CORRECTO
template <class T>
void ArbolBinario<T>::posOrden()
{
  if(!this->esVacio())
  {
    this->posOrden(this->raiz);
  }
}

//CORRECTO
template <class T>
void ArbolBinario<T>::posOrden(NodoBinario<T>* nodo)
{

  if(nodo!=NULL)
  {
    this->posOrden(nodo->obtenerHijoIzq());

    this->posOrden(nodo->obtenerHijoDer());

    std::cout<<nodo->obtenerDato()<<" ";
  }
 
}


template <class T>
void ArbolBinario<T>::nivelOrden()
{
   if(!this->esVacio())
   {
      std::queue <NodoBinario<T>*> aux;

      aux.push(this->obtenerRaiz());

      while(!aux.empty())
      {
         std::cout<<aux.front()->obtenerDato()<<" ";

        if(aux.front()->obtenerHijoIzq()!=NULL)
          aux.push(aux.front()->obtenerHijoIzq());

        if(aux.front()->obtenerHijoDer()!=NULL)
           aux.push(aux.front()->obtenerHijoDer());

          aux.pop(); 
      }  
   }
}

template <class T>
 NodoBinario<T>* ArbolBinario<T>::extremo_izq()
{
  NodoBinario<T>* nodo=this->raiz;
  NodoBinario<T>* nodoReserva=this->raiz;
  
  while(nodo!=NULL )
  {
      nodoReserva=nodo;
      nodo=nodo->obtenerHijoIzq();
  }

  return nodoReserva;
}

template <class T>
 NodoBinario<T>* ArbolBinario<T>::extremo_der()
{
  NodoBinario<T>* nodo=this->raiz;
  NodoBinario<T>* nodoReserva=this->raiz;
  
  while(nodo!=NULL )
  {
      nodoReserva=nodo;
      nodo=nodo->obtenerHijoDer();
  }

  return nodoReserva;
}

#endif

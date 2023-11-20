#ifndef A_AVL_HXX
#define A_AVL_HXX


#include "ArbolAVL.h"
#include<queue>
#include<list>

template <class T>
ArbolAVL<T>::ArbolAVL()
{
  this->raiz=NULL;
}


template <class T>
ArbolAVL<T>::ArbolAVL(T n)
{
    NodoAVL <T>* nodo= new NodoAVL<T>;
    nodo->fijarDato(n);
    this->raiz=nodo; 
    //delete nodo;
}

template <class T>
void ArbolAVL<T>::cambiarRaiz(NodoAVL<T>* nodo)
{
   this->raiz=nodo;
}

template <class T>
NodoAVL<T>* ArbolAVL<T>::obtenerRaiz()
{ 
  return this->raiz;
}

//REVISAR
template <class T>
ArbolAVL<T>::~ArbolAVL()
{
    if (this->raiz != NULL) {
        delete this->raiz;
        this->raiz = NULL;
      }
}

template <class T>
bool ArbolAVL<T>::esVacio()
{
    return this->raiz==NULL;
}


template <class T>
T ArbolAVL<T>::datoRaiz()
{
  (this->raiz)->obtenerDato();
}

//CORRECTO
template <class T>
  int ArbolAVL<T>::altura()
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
int ArbolAVL<T>::altura(NodoAVL<T>* nodo)
{ 
  int alt;
  
  if(nodo==NULL)
  {
    return -1;
  }

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
unsigned int ArbolAVL<T>::tamano()
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
unsigned int ArbolAVL<T>::tamano(NodoAVL<T>* nodo)
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


//ITERATIVA
template <class T>
bool ArbolAVL<T>::insertarNodo(T n)
{
  
    if(this->raiz==NULL)
    {
       NodoAVL <T>* nodo= new NodoAVL<T>;
       nodo->fijarDato(n);
       this->raiz=nodo; 
    }

    NodoAVL<T>* nodo=this->raiz;
    //nodo al que lo voy a insertar
    NodoAVL<T>* padre=this->raiz;
  
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
      NodoAVL<T>* nuevo=new NodoAVL<T>;
      
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
    
    if(insertado)
    {
      balancear(n,&(this->raiz));
    }
 
   return insertado;
}

//ITERATIVA
template <class T>
bool ArbolAVL<T>::deleteNodo(T n) {
    
    NodoAVL<T> *padre= this->raiz;
    NodoAVL<T> *nodo=this->raiz;;
  
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
        // NodoAVL<T> with the given n not found
        return false;
    }
  
    // Case 1: NodoAVL<T> to be deleted has no children
    if (nodo->obtenerHijoIzq() == nullptr && nodo->obtenerHijoDer() == nullptr) {
      
        if (nodo == this->raiz) {
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
    // Case 2: NodoAVL<T> to be deleted has only one child
    else if (nodo->obtenerHijoIzq() == nullptr || nodo->obtenerHijoDer() == nullptr) {
      
        NodoAVL<T> *child = (nodo->obtenerHijoIzq() != nullptr) ? nodo->obtenerHijoIzq() : nodo->obtenerHijoDer();
      
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
    // Case 3: NodoAVL<T> to be deleted has two children
    else {
        NodoAVL<T> *successor = nodo->obtenerHijoIzq();
        NodoAVL<T> *successorpadre = nodo;

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
 
    // Balancear el árbol después de eliminar el nodo
    balancear(n,&(this->raiz));
  
    return true;
}

template <class T>
void ArbolAVL<T>::balancear(T val, NodoAVL<T>** nodo)
{
    
  int alturaHijoIzq, alturaHijoDer, alturaHijoIzqIzq, alturaHijoIzqDer,
      alturaHijoDerDer, alturaHijoDerIzq;
  
  if(nodo!=NULL)
  {
            //Se recorre al arbol para balancear el arbol en cada nivel
    
        //tiene que hacer el balanceo de abajo hacia arriba
        if (val < (*nodo)->obtenerDato())
        {
            balancear(val,&((*nodo)->obtenerHijoIzq()));
        }
        else if (val > (*nodo)->obtenerDato())
        {
           balancear(val,&((*nodo)->obtenerHijoDer()));
        }
      
      
        // Se calculan las alturas del hijo izquierdo y derecho, para ver la diferencia
        alturaHijoIzq = altura((*nodo)->obtenerHijoIzq());
        alturaHijoDer = altura((*nodo)->obtenerHijoDer());
          
        //Si la diferencia es mahyor a 1, se realiza un balanceo
        
        //si la altura del hijo derecho es mayor al del izquierdo
        if (alturaHijoIzq - alturaHijoDer > 1)
        {
            
          //se deben examinar las laturas de los hijos del izquierdo
          alturaHijoIzqIzq = altura((*nodo)->obtenerHijoIzq()->obtenerHijoIzq());
          alturaHijoIzqDer = altura((*nodo)->obtenerHijoIzq()->obtenerHijoDer());
          
          //Se evalua que tipo de rotacion se debe realizar
            
          if (alturaHijoIzqIzq > alturaHijoIzqDer)
          {
            //rotación simple a la derecha
            *nodo = giroDerecha(*nodo);
          }
          else
          {
            //rotación doble izquierda-derecha
            *nodo = giroIzquierdaDerecha(*nodo);
          }
            
        }
        
        //si la altura del derecho es mayor que la del izquierdo
        if (alturaHijoDer - alturaHijoIzq > 1)
        {
          //se deben examinar las laturas de los hijos del derecho
          alturaHijoDerDer = altura((*nodo)->obtenerHijoDer()->obtenerHijoDer());
          alturaHijoDerIzq = altura((*nodo)->obtenerHijoDer()->obtenerHijoIzq());
          
          //Se evalua que tipo de rotacion se debe realizar
          if (alturaHijoDerDer > alturaHijoDerIzq)
          {
            //rotación simple a la izquierda
            *nodo = giroIzquierda(*nodo);
          }
          else
          {
            //rotación doble derecha-izquierda
            *nodo = giroDerechaIzquierda(*nodo);
          }
            
        }
  }
}

template <class T>
NodoAVL<T> *ArbolAVL<T>::giroDerecha(NodoAVL<T> *&padre) {
  NodoAVL<T> *n_padre = padre->obtenerHijoIzq();
  padre->fijarHijoIzq(n_padre->obtenerHijoDer());
  n_padre->fijarHijoDer(padre);
  return n_padre;
}

template <class T>
NodoAVL<T> *ArbolAVL<T>::giroIzquierda(NodoAVL<T> *&padre) {
  NodoAVL<T> *n_padre = padre->obtenerHijoDer();
  padre->fijarHijoDer(n_padre->obtenerHijoIzq());
  n_padre->fijarHijoIzq(padre);
  return n_padre;
}

template <class T>
NodoAVL<T> *
ArbolAVL<T>::giroIzquierdaDerecha(NodoAVL<T> *&padre) {
  NodoAVL<T> **nodo = &(padre->obtenerHijoIzq());
  padre->fijarHijoIzq(giroIzquierda(*nodo));
  return giroDerecha(padre);
}

template <class T>
NodoAVL<T> *
ArbolAVL<T>::giroDerechaIzquierda(NodoAVL<T> *&padre) {
  NodoAVL<T> **nodo = &(padre->obtenerHijoDer());
  padre->fijarHijoDer(giroDerecha(*nodo));
  return giroIzquierda(padre);
}

//CORRECTO
//ITERATIVA
template <class T>
bool ArbolAVL<T>::buscar(T n)
{
  NodoAVL<T>* nodo=this->raiz;
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

  delete nodo;
  return encontrado;
}

//CORRECTO
template <class T>
void ArbolAVL<T>::inOrden()
{
  if(!this->esVacio())
  {
    this->inOrden(this->raiz);
  }
}

//CORRECTO
template <class T>
void ArbolAVL<T>::inOrden(NodoAVL<T>* nodo)
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
void ArbolAVL<T>::preOrden()
{
  if(!this->esVacio())
  {
    this->preOrden(this->raiz);
  }
}

//CORRECTO
template <class T>
void ArbolAVL<T>::preOrden(NodoAVL<T>* nodo)
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
void ArbolAVL<T>::posOrden()
{
  if(!this->esVacio())
  {
    this->posOrden(this->raiz);
  }
}

//CORRECTO
template <class T>
void ArbolAVL<T>::posOrden(NodoAVL<T>* nodo)
{

  if(nodo!=NULL)
  {
    this->posOrden(nodo->obtenerHijoIzq());

    this->posOrden(nodo->obtenerHijoDer());

    std::cout<<nodo->obtenerDato()<<" ";
  }
 
}


template <class T>
void ArbolAVL<T>::nivelOrden()
{
  
   if(!this->esVacio())
   {
      std::queue <NodoAVL<T>*> aux;

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
NodoAVL<T>* ArbolAVL<T>::extremo_izq()
{
 NodoAVL<T>* nodo=this->raiz;
 NodoAVL<T>* nodoReserva=this->raiz;
  
  while(nodo!=NULL )
  {
      nodoReserva=nodo;
      nodo=nodo->obtenerHijoIzq();
  }

  return nodoReserva;
}

template <class T>
NodoAVL<T>* ArbolAVL<T>::extremo_der()
{
 NodoAVL<T>* nodo=this->raiz;
 NodoAVL<T>* nodoReserva=this->raiz;
  
  while(nodo!=NULL )
  {
      nodoReserva=nodo;
      nodo=nodo->obtenerHijoDer();
  }

  return nodoReserva;
}


#endif

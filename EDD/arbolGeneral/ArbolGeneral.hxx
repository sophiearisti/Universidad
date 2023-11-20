#ifndef ARBOL_HXX
#define ARBOL_HXX


#include "ArbolGeneral.h"
#include<queue>

template <class T>
  ArbolGeneral<T>::ArbolGeneral()
  {
    this->raiz=NULL;
  }



template <class T>
  ArbolGeneral<T>::ArbolGeneral(T val)
  {
    NodoGeneral<T>* nodo= new NodoGeneral<T>;
    nodo->fijarDato(val);
    this->raiz=nodo; 
  }


//CORRECTO
template <class T>
ArbolGeneral<T>::~ArbolGeneral()
{ 
  delete this->raiz;
  this->raiz=NULL;
}


//CORRECTO
template <class T>  
bool ArbolGeneral<T>::esVacio()
{ 
  return this->raiz==NULL;
}


//CORRECTO
template <class T>
NodoGeneral<T>* ArbolGeneral<T>::obtenerRaiz()
{ 
  return this->raiz;
}


template <class T>
void ArbolGeneral<T>::fijarRaiz(NodoGeneral<T> *nraiz)
{ 
  this->raiz=nraiz;
}

//CORRECTO
template <class T>
bool ArbolGeneral<T>::insertarNodo(T padre, T n)
{
  //si el arbol es vacio
  //crear nuevo nodo, asignar el dato y poner ese nodo como la raiz
  if(this->esVacio())
  {
    
    NodoGeneral<T>* nodo= new NodoGeneral<T>;
    nodo->fijarDato(n);
    this->raiz=nodo;
    return true;
  }
  //si el arbol no esta vacio
  else
  {
    return this->insertarNodo(padre,n, this->obtenerRaiz());
  }
  //si existe al menos un nodo, toca buscar el nodo padre por recursion
  //si se encuentra pues se ingresa el nuevo nodo
}

//CORRECTO
template <class T>
bool ArbolGeneral<T>::insertarNodo(T padre, T n, NodoGeneral<T>* nodo)
{
  bool efectivo=false;
  
  if(nodo->obtenerDato()==padre)
  {
    nodo->adicionarDesc(n);
    efectivo=true;
  }
  else
  {
     typename std::list<NodoGeneral<T>*>::iterator it;
      for(it=nodo->desc.begin(); it!=nodo->desc.end(); it++)
      {
          efectivo=this->insertarNodo(padre,n,*it);  
      }    
  }

  return efectivo;
}

//CORRECTO
template <class T>
bool ArbolGeneral<T>::eliminarNodo(T nodo)
{
  if(this->esVacio())
  {  
    return false;
  }
  else if(this->obtenerRaiz()->obtenerDato()==nodo)
  {
    this->~ArbolGeneral();
    return true;
  }
  else
  {
    
    return this->eliminarNodo(nodo,this->raiz);
  }
  
}

//CORRECTO
template <class T>
bool ArbolGeneral<T>::eliminarNodo(T n, NodoGeneral<T>* nodo)
{
    bool efectivo=false;

        typename std::list<NodoGeneral<T>*>::iterator it;
      
        for(it=nodo->desc.begin(); it!=nodo->desc.end(); it++)
        {
           if((**it).obtenerDato()==n)
            {
               nodo->eliminarDesc(n);
               efectivo=true;
               break;
            }
            else
            {
               efectivo=this->eliminarNodo(n,*it); 
                if(efectivo)
                {
                  break;
                }
            }
        }   
  
    return efectivo;
}

//CORRECTO
template <class T>
bool ArbolGeneral<T>::buscar(T nodo)
{
  if(this->esVacio())
  {
    return false;
  }
  //si el dato es el actual 
  else if(this->raiz->obtenerDato()== nodo)
  {
     return true;
  }
  else
  {
    return this->buscar(nodo,this->obtenerRaiz());
  }
}

//CORRECTO
template <class T>
bool ArbolGeneral<T>::buscar(T n, NodoGeneral<T>* nodo)
{
  bool encontrado=false;
   if(nodo->obtenerDato()==n)
   {
     encontrado=true;
   }
   else
   {
     typename std::list<NodoGeneral<T>*>::iterator it;
        for(it=nodo->desc.begin(); it!=nodo->desc.end(); it++)
        {
            encontrado=this->buscar(n,*it);  

            if(encontrado)
            {
              break;
            }
        }   
   }
    return encontrado;  
}

//CORRECTO
template <class T> 
int ArbolGeneral<T>::altura()
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
int ArbolGeneral<T>::altura(NodoGeneral<T>* nodo)
{ 
  int alt=-1;

   if(this->esVacio())
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
    
    int alth;
    //tiene hijos
    typename std::list<NodoGeneral<T>*>::iterator it;
    for(it=nodo->desc.begin(); it!=nodo->desc.end(); it++)
    {
        alth=this->altura(*it);
      
        //altura padre
        if(alt<alth+1)
          alt=alth+1;  
    }
  }
  return alt;
}

//CORRECTO
template <class T>
unsigned int ArbolGeneral<T>::tamano()
{
  if(this->esVacio())
  {
    return 0;
  } 
  else
  {
    return ArbolGeneral<T>::tamano(this->obtenerRaiz())+1;
      //para cada uno de los hijos llamo a tamano y acumulo
  }
}

//CORRECTO
template <class T> 
int ArbolGeneral<T>::tamano(NodoGeneral<T>* nodo)
{
  int tamano=0;
  
  if(nodo->esHoja())
  {
     tamano=0;
  }
  else
  {
    
     typename std::list<NodoGeneral<T>*>::iterator it;
    
    for(it=nodo->desc.begin(); it!=nodo->desc.end(); it++)
    {
        tamano+=this->tamano(*it);
    }

    tamano+=nodo->desc.size();
    
  }
  return tamano; 
}



//CORRECTO
template <class T>
void ArbolGeneral<T>::preOrden()
{
  if(!this->esVacio())
  {
    this->preOrden(this->obtenerRaiz());
  }
}

//CORRECTO
template <class T>
//pre-orden: visitar el nodo padre antes que todos los nodos hijos
void ArbolGeneral<T>::preOrden(NodoGeneral<T>* nodo)
{
  //visitar
  std::cout<<nodo->obtenerDato()<<" ";

  //recorrer cada uno de los hijos
  typename std::list<NodoGeneral<T>*>::iterator it;
  
   for(it=nodo->desc.begin(); it!=nodo->desc.end(); it++)
    {
        this->preOrden(*it); 
    }
  
}

//CORRECTO
template <class T>
void ArbolGeneral<T>::posOrden()
{
  if(!this->esVacio())
  {
    this->posOrden(this->raiz);
  }
}

//CORRECTO
template <class T>
//pre-orden: visitar el nodo padre antes que todos los nodos hijos
void ArbolGeneral<T>::posOrden(NodoGeneral<T>* nodo)
{

  //recorrer cada uno de los hijos
  typename std::list<NodoGeneral<T>*>::iterator it;
  
   for(it=nodo->desc.begin(); it!=nodo->desc.end(); it++)
    {
        this->posOrden(*it); 
    }

   //visitar
  std::cout<<nodo->obtenerDato()<<" ";
  
}


//ESTA BIEN
template <class T>
void ArbolGeneral<T>::nivelOrden()
{
  if(!this->esVacio())
  {
        //NO ES RECURRENTE 
    std::queue <NodoGeneral<T>*> aux;
    
    NodoGeneral<T> auxN;
    
    aux.push(this->obtenerRaiz());
  
    while(!aux.empty())
    {
    
      std::cout<<aux.front()->obtenerDato()<<" ";
  
     typename std::list<NodoGeneral<T>*>::iterator it;
    
     for(it=aux.front()->desc.begin(); it!=aux.front()->desc.end(); it++)
      {
          aux.push(*it);
      }
  
      aux.pop();
      
    }
  
  }
  
}


#endif


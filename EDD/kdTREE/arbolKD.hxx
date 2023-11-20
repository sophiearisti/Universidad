#ifndef A_KD_HXX
#define A_KD_HXX

#include "arbolKD.h"
#include <queue>

  ArbolKD::ArbolKD ()
  {
    this->raiz=NULL;
  }

  ArbolKD::ArbolKD (punto val)
  {
    NodoKD* nodo= new NodoKD;
    nodo->fijarDato(val);
    this->raiz=nodo; 
  }

   ArbolKD::~ArbolKD ()
  {
    if (this->raiz != NULL) 
    {
        delete this->raiz;
        this->raiz = NULL;
    }
  }

  punto ArbolKD::datoRaiz()
  {
    return (this->raiz)->obtenerDato();
  }

  NodoKD* ArbolKD::obtenerRaiz()
  {
    return this->raiz;
  }

  void ArbolKD::fijarRaiz(NodoKD* n_raiz)
  {
    this->raiz=n_raiz;
  }

  bool ArbolKD::esVacio()
  {
     return this->raiz==NULL;
  }

//REVISAR
  bool ArbolKD::insertar(punto val)
  {
    //NOTA X SERA EL INICIO Y ASI...
    
    if(this->raiz==NULL)
    {
       NodoKD* nodo= new NodoKD;
       nodo->fijarDato(val);
       this->raiz=nodo; 
    }

    NodoKD* nodo=this->raiz;
    //nodo al que lo voy a insertar
    NodoKD* padre=this->raiz;
  
    bool insertado=false, duplicado=false; 
    int contador=0;
    int valX;
    int valY;
  
    while(nodo!=NULL && !duplicado)
    {
        padre=nodo;

        valX=nodo->obtenerDato().x;
        valY=nodo->obtenerDato().y;

        if(val.x==valX && val.y==valY)
        {
          duplicado=true;
        }
        else
        {
            if(contador%2==0)
            //los pares son de x
            {
                if(val.x<=valX )
                { 
                  
                  nodo=nodo->obtenerHijoIzq();
                }
                else if(val.x>valX )
                {
                  //std::cout<<"\nok\n";
                  nodo=nodo->obtenerHijoDer();
                }
            }
            else
            //los impares de y
            {
                if(val.y<=valY)
                {
                  nodo=nodo->obtenerHijoIzq();
                }
                else if(val.y>valY)
                {
                  nodo=nodo->obtenerHijoDer();
                }
            }

        }
      
        contador++;
    }


    if(!duplicado)
    {  
      NodoKD* nuevo=new NodoKD;
      
      nuevo->fijarDato(val);

      valX=padre->obtenerDato().x;
      valY=padre->obtenerDato().y;

      if(nuevo!=NULL)
      {
        if(contador%2==1)
        //los pares son de x
        {
            if(val.x<=valX )
            {
              padre->fijarHijoIzq(nuevo);
            }
            else if(val.x>valX )
            {
              padre->fijarHijoDer(nuevo);
            }
        }
        else
        //los impares de y
        {
            if(val.y<=valY)
            {
              padre->fijarHijoIzq(nuevo);
            }
            else if(val.y>valY)
            {
              padre->fijarHijoDer(nuevo);
            }
        }
        insertado=true;
         //delete nuevo;
      }
    }
    
  // delete nodo;
   return insertado;
}

  void ArbolKD  ::preOrden()
  {
       if(!this->esVacio())
    {
      this->preOrden(this->obtenerRaiz());
    }
  }

  void ArbolKD ::preOrden(NodoKD* nodo)
  {
      if(nodo!=NULL)
    {
      std::cout<<nodo->obtenerDato()<<" ";
    
      this->preOrden(nodo->obtenerHijoIzq());
  
      this->preOrden(nodo->obtenerHijoDer());
    }
    
  }

  void ArbolKD::inOrden()
  {
      if(!this->esVacio())
      {
        this->inOrden(this->raiz);
      }
  }

  void ArbolKD::inOrden(NodoKD* nodo)
  {
    if(nodo!=NULL)
    {
      this->inOrden(nodo->obtenerHijoIzq());

      std::cout<<nodo->obtenerDato()<<" ";

      this->inOrden(nodo->obtenerHijoDer());
      
    }
    
  }

  void ArbolKD::posOrden()
  {
      if(!this->esVacio())
    {
      this->posOrden(this->raiz);
    }
  }

  void ArbolKD::posOrden(NodoKD* nodo)
  {
  
    if(nodo!=NULL)
    {
      this->posOrden(nodo->obtenerHijoIzq());
  
      this->posOrden(nodo->obtenerHijoDer());
  
      std::cout<<nodo->obtenerDato()<<" ";
    }
   
  }

  void ArbolKD::nivelOrden()
  {
      if(!this->esVacio())
     {
        std::queue <NodoKD*> aux;
  
        aux.push(this->obtenerRaiz());
  
        while(!aux.empty())
        {

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
  }

#endif

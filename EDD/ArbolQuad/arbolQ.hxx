#ifndef Q_Arbol_HXX
#define Q_Arbol_HXX

#include "arbolQ.h"
#include <queue>

    ArbolQ ::ArbolQ()
  {
    this->raiz=NULL;
  }

    ArbolQ::ArbolQ(punto val)
  {
    NodoQ* nodo= new NodoQ;
    nodo->fijarDato(val);
    this->raiz=nodo; 
  }

    ArbolQ ::~ArbolQ()
  {
    if (this->raiz != NULL) 
    {
        delete this->raiz;
        this->raiz = NULL;
    }
  }

    punto ArbolQ ::datoRaiz()
  {
    return (this->raiz)->obtenerDato();
  }

    NodoQ* ArbolQ ::obtenerRaiz()
  {
    return this->raiz;
  }

    void ArbolQ ::fijarRaiz(NodoQ* n_raiz)
  {
    this->raiz=n_raiz;
  }

    bool ArbolQ ::esVacio()
  {
     return this->raiz==NULL;
  }

    bool ArbolQ ::insertar(punto val)
  {
    
    if(this->raiz==NULL)
    {
       NodoQ* nodo= new NodoQ;
       nodo->fijarDato(val);
       this->raiz=nodo; 
    }

    NodoQ* nodo=this->raiz;
    //nodo al que lo voy a insertar
    NodoQ* padre=this->raiz;
  
    bool insertado=false, duplicado=false; 

    int valX;
    int valY;
  
    while(nodo!=NULL && !duplicado)
    {
        padre=nodo;

        valX=nodo->obtenerDato().x;
        valY=nodo->obtenerDato().y;
      
        if(val.x<valX && val.y<valY)
        {
          nodo=nodo->obtenerHijoInfIzq();
        }
        //si solo tiene el val igual a y
        else if(val.x<valX && val.y>=valY)
        {
          nodo=nodo->obtenerHijoSupIzq();
        }
        else if(val.x>valX && val.y<valY)
        {
          nodo=nodo->obtenerHijoInfDer();
        }
         //si solo tiene el val igual a x
        else if(val.x>=valX && val.y>valY)
        {
          nodo=nodo->obtenerHijoSupDer();
        }
        else
        {
          //ver si el dato esta duplicado, porque no se puede
          duplicado=true;
        }
          
    }


    if(!duplicado)
    {  
      NodoQ* nuevo=new NodoQ;
      
      nuevo->fijarDato(val);

      valX=padre->obtenerDato().x;
      valY=padre->obtenerDato().y;

      if(nuevo!=NULL)
      {
        
        if(val.x<valX && val.y<valY)
        {
          padre->fijarHijoInfIzq(nuevo);
        }
        else if(val.x<valX && val.y>=valY)
        {
          padre->fijarHijoSupIzq(nuevo);
        }
        else if(val.x>valX && val.y<valY)
        {
          padre->fijarHijoInfDer(nuevo);
        }
        else if(val.x>=valX && val.y>valY)
        {
          padre->fijarHijoSupDer(nuevo);
        }
        
        insertado=true;
         //delete nuevo;
      }
    }
    
  // delete nodo;
   return insertado;
}

    void ArbolQ ::preOrden()
  {
       if(!this->esVacio())
    {
      this->preOrden(this->obtenerRaiz());
    }
  }

  void ArbolQ::preOrden(NodoQ* nodo)
  {
      if(nodo!=NULL)
    {
      std::cout<<nodo->obtenerDato()<<" ";
    
      this->preOrden(nodo->obtenerHijoSupIzq());
  
      this->preOrden(nodo->obtenerHijoSupDer());

      this->preOrden(nodo->obtenerHijoInfDer());

      this->preOrden(nodo->obtenerHijoInfIzq());

    }
    
  }

    void ArbolQ ::inOrden()
  {
      if(!this->esVacio())
      {
        this->inOrden(this->raiz);
      }
  }

  void ArbolQ ::inOrden(NodoQ* nodo)
  {
    if(nodo!=NULL)
    {
      this->inOrden(nodo->obtenerHijoSupIzq());
  
      this->inOrden(nodo->obtenerHijoSupDer());

      std::cout<<nodo->obtenerDato()<<" ";
      
      this->inOrden(nodo->obtenerHijoInfDer());

      this->inOrden(nodo->obtenerHijoInfIzq());
    }
    
  }

    void ArbolQ ::posOrden()
  {
      if(!this->esVacio())
    {
      this->posOrden(this->raiz);
    }
  }

  void ArbolQ ::posOrden(NodoQ* nodo)
  {
  
    if(nodo!=NULL)
    {
      this->posOrden(nodo->obtenerHijoSupIzq());
  
      this->posOrden(nodo->obtenerHijoSupDer());
      
      this->posOrden(nodo->obtenerHijoInfDer());
      
      this->posOrden(nodo->obtenerHijoInfIzq());
      
      std::cout<<nodo->obtenerDato()<<" ";
    }
   
  }

    void ArbolQ ::nivelOrden()
  {
      if(!this->esVacio())
     {
        std::queue <NodoQ*> aux;
  
        aux.push(this->obtenerRaiz());
  
        while(!aux.empty())
        {
            std::cout<<aux.front()->obtenerDato()<<" ";
  
            if(aux.front()->obtenerHijoSupIzq()!=NULL)
              aux.push(aux.front()->obtenerHijoSupIzq());
    
            if(aux.front()->obtenerHijoSupDer()!=NULL)
               aux.push(aux.front()->obtenerHijoSupDer());
          
            if(aux.front()->obtenerHijoInfDer()!=NULL)
               aux.push(aux.front()->obtenerHijoInfDer());
          
            if(aux.front()->obtenerHijoInfIzq()!=NULL)
               aux.push(aux.front()->obtenerHijoInfIzq());
  
           
    
            aux.pop(); 
        }  
   }
    
  }

#endif

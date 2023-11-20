#ifndef Q_Arbol_HXX
#define Q_Arbol_HXX

#include "arbolQ.h"
#include <queue>
#include <vector>

    ArbolQ ::ArbolQ()
  {
    this->raiz=NULL;
  }

    ArbolQ::ArbolQ(int val)
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

    int ArbolQ ::datoRaiz()
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

bool ArbolQ::insertar(int val) 
{
   
  bool estaInsertado{false};
  
    if (this->raiz==NULL) 
    {
        NodoQ* nodo= new NodoQ;
        nodo->fijarDato(val);
        this->raiz=nodo;
        estaInsertado = true;
    } 
    else 
    {
        estaInsertado =insertarNodo(val, this->raiz);
    }
  
  return estaInsertado;
}

// Inserta un nuevo nodo dentro del arbol.
bool ArbolQ::insertarNodo(int nval, NodoQ* nodo) 
{
    bool eval=false;
    
       //si es hoja se ingesa
       if (nodo->obtenerHijoSupIzq() == NULL) 
        {
            NodoQ* nuevo=new NodoQ;
            nuevo->fijarDato(nval);
            nodo->fijarHijoSupIzq(nuevo);
            eval=true;
        }
        //si es dos y no esta lleno
        else if(nodo->obtenerHijoSupIzq()->obtenerDato()==2 && nodo->obtenerHijoSupIzq()->estaCompletode1sy0s()==-1)
        {
            eval=insertarNodo(nval,nodo->obtenerHijoSupIzq());
        }
        //si es dos, si esta lleno y su cuarto hijo es 2 y su cuarto hijo no esta completo de 1s y 0s
        else if(nodo->obtenerHijoSupIzq()->obtenerDato()==2 && nodo->obtenerHijoSupIzq()->estaCompletode1sy0s()==4 && nodo->obtenerHijoSupIzq()->obtenerHijoInfIzq()->estaCompletode1sy0s()!=0)
        {
            eval=insertarNodo(nval,nodo->obtenerHijoSupIzq());
        }
    
    //si al revisar la primera rama no se encuentra donde insertar, se va a la segunda
    //y se hace lo mismo, pero sin evaluar la primera rama
    if(!eval)
    {
        //si es hoja se ingesa
          if (nodo->obtenerHijoSupDer()== NULL)
          {
              NodoQ* nuevo=new NodoQ;
              nuevo->fijarDato(nval);
              nodo->fijarHijoSupDer(nuevo);
              eval=true;
          }
          //si es dos y no esta lleno
          else if(nodo->obtenerHijoSupDer()->obtenerDato()==2 && nodo->obtenerHijoSupDer()->estaCompletode1sy0s()==-1)
          {
              eval=insertarNodo(nval,nodo->obtenerHijoSupDer());
          }
          else if(nodo->obtenerHijoSupDer()->obtenerDato()==2 && nodo->obtenerHijoSupDer()->estaCompletode1sy0s()==4 && nodo->obtenerHijoSupDer()->obtenerHijoInfIzq()->estaCompletode1sy0s()!=0)
          {
              eval=insertarNodo(nval,nodo->obtenerHijoSupDer());
          }
    }
    
    //si al revisar la segunda rama no se encuentra donde insertar, se va a la tercera
    //y se hace lo mismo, pero sin evaluar la primera y segunda rama
    if(!eval)
    {
        if (nodo->obtenerHijoInfDer()== NULL)
        {
            NodoQ* nuevo=new NodoQ;
            nuevo->fijarDato(nval);
            nodo->fijarHijoInfDer(nuevo);
            eval=true;
        }
        else if(nodo->obtenerHijoInfDer()->obtenerDato()==2 && nodo->obtenerHijoInfDer()->estaCompletode1sy0s()==-1)
        {
            eval=insertarNodo(nval,nodo->obtenerHijoInfDer());
        }
        else if(nodo->obtenerHijoInfDer()->obtenerDato()==2 && nodo->obtenerHijoInfDer()->estaCompletode1sy0s()==4 && nodo->obtenerHijoInfDer()->obtenerHijoInfIzq()->estaCompletode1sy0s()!=0)
        {
             eval=insertarNodo(nval,nodo->obtenerHijoInfDer());
        }
                
    }
    
    
    //si al revisar la tercera rama no se encuentra donde insertar, se va a la cuarta
    //y se hace lo mismo, pero sin evaluar la primera, segunda y tercera rama
    if(!eval)
    {
         if (nodo->obtenerHijoInfIzq()== NULL)
          {
              NodoQ* nuevo=new NodoQ;
              nuevo->fijarDato(nval);
              nodo->fijarHijoInfIzq(nuevo);
              eval=true;
          }
          else if(nodo->obtenerHijoInfIzq()->obtenerDato()==2 && nodo->obtenerHijoInfIzq()->estaCompletode1sy0s()==-1)
          {
              eval=insertarNodo(nval,nodo->obtenerHijoInfIzq());
          }
          else if(nodo->obtenerHijoInfIzq()->obtenerDato()==2 && nodo->obtenerHijoInfIzq()->estaCompletode1sy0s()==4 && nodo->obtenerHijoInfIzq()->obtenerHijoInfIzq()->estaCompletode1sy0s()!=0)
          {
              eval=insertarNodo(nval,nodo->obtenerHijoInfIzq());
          }
    }
            
   
    return eval;
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

 void ArbolQ ::armarMatriz(std::vector<std::vector<int>>& imagen, int tamano)
  {
    if(!this->esVacio())
    {
      
        this->armarMatriz(this->obtenerRaiz(),imagen, tamano,0,0); 
    }
  }

  void ArbolQ::armarMatriz(NodoQ* nodo, std::vector<std::vector<int>>& imagen, int tamano, int inicioI,int inicioJ)
  {
    //si no es nulo, se entra
    if(nodo!=NULL)
    {
      //si no es dos significa que se debe ingresar datos en la matriz
      if(nodo->obtenerDato()!=2)
      {
        //se inicializa en 0
        int val=0;

        //pero si el dato del nodo es 1, se cambia
        if(nodo->obtenerDato()==1)
        {
          val=1;
        }
        
        //se llena el pedazo correspondiente de la matriz con el dato del nodo
        
        for(int i=inicioI; i<inicioI+tamano; i++)
        {
            for(int j=inicioJ; j<inicioJ+tamano; j++)
            {
               imagen[i][j]=val;
            }
        }
        
      }

      //de lo contrario es dos
      //primero se visita siempre el extremo SupIzq
      this->armarMatriz(nodo->obtenerHijoSupIzq(), imagen, tamano/2, inicioI,inicioJ);
  
      this->armarMatriz(nodo->obtenerHijoSupDer(), imagen, tamano/2, inicioI,inicioJ+tamano/2);

      this->armarMatriz(nodo->obtenerHijoInfDer(), imagen, tamano/2, inicioI+tamano/2,inicioJ+tamano/2);
      
      this->armarMatriz(nodo->obtenerHijoInfIzq(), imagen, tamano/2, inicioI+tamano/2,inicioJ);

    }
  
  }

#endif

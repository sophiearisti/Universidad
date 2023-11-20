#ifndef _GRAFO_HXX_
#define _GRAFO_HXX_

#include "Grafo.h"
#include <iostream>
#include <stack>
#include <algorithm>
#include <vector>
#include <list>

// Crea un grafo con datos vacios.
template <class T,class U>
Grafo<T,U>::Grafo() 
{
 
};



// Destructor del grafo.
template <class T,class U>
Grafo<T,U>::~Grafo()
{
  
};



//FUNCIONA
template <class T,class U>
std::vector<std::vector<U>> Grafo<T,U>::getGrafo()
{
  return this->grafo;
}



//FUNCIONA
// Retorna la condicion actual del grafo.
template <class T,class U>
bool Grafo<T,U>::esVacio()
{
  return this->vertices.size()==0;
};



//FUNCIONA
template <class T,class U>
unsigned int Grafo<T,U>::cantidadVertices()
{
  return this->vertices.size();
}



//FUNCIONA
template <class T,class U>
unsigned int Grafo<T,U>::cantidadAristas()
{
  return this->grafo.size();
}

template <class T,class U>
void Grafo<T,U>::setEsDirigido(bool tipoGrafo)
{
  this->esDirigido=tipoGrafo;
}

//FUNCIONA
template <class T,class U>
bool Grafo<T,U>::insertarVertice(T vertice)
{
  bool estaInsertado = false;
  // Primero se verifique que el vertice no exista en la lista:
 if(!BuscarVertice(vertice)) 
 {
    // En caso negativo, se añande el vertice a la lista de vertices:
    this->vertices.push_back(vertice);
    
    // Luego se agrega una lista con valor que representa el infinito:
   
    typename std::vector<U> nuevaLista(cantidadVertices()-1, 0);
    this->grafo.push_back(nuevaLista);

    
    //se pone la nueva columna para los demas vertices en infinito
    typename std::vector<std::vector<U>>::iterator itList;
          
    for(itList = this->grafo.begin(); itList != this->grafo.end(); itList++)
    {
      itList->push_back(0);
    }
  
    estaInsertado = true;
  }
  
  return estaInsertado;
};



//FUNCIONA
template <class T,class U>
bool Grafo<T,U>::insertarArista(T VerticeOrigen, T VerticeDestino, U peso)
{
  int Origen=BuscarPos(VerticeOrigen);
  int Destino=BuscarPos(VerticeDestino);
  
   if(Origen!=-1 && Destino!=-1)
   {
     if(!BuscarArista(VerticeOrigen, VerticeDestino))
    {
      this->grafo[Origen][Destino]=peso;
      return true;
    }
   }
  
  return false;
};



//FUNCIONA
template <class T, class U>
int Grafo<T,U>::BuscarPos(T valor)
{
   for ( int i=0; i< this->vertices.size(); i++) 
   {
     if(this->vertices[i]==valor)
     {
       return i;
     }
   }
  
  return -1;
};



//FUNCIONA
template <class T, class U>
bool Grafo<T,U>::BuscarVertice(T indice)
{
  for ( int i=0; i< this->vertices.size(); i++) 
   {
     if(this->vertices[i]==indice)
     {
       return true;
     }
   }

  return false;
}



//FUNCIONA
template <class T, class U>
bool Grafo<T,U>::BuscarArista(T VerticeOrigen, T VerticeDestino)
{
  int Origen=BuscarPos(VerticeOrigen);
  int Destino=BuscarPos(VerticeDestino);
  
  if(this->grafo[Origen][Destino]==0)
  {
    return false;
  }
  
  return true;
}



template <class T, class U>
bool Grafo<T,U>::BuscarAristaPorPosicion(int Origen, int Destino)
{
  if(this->grafo[Origen][Destino]==0)
  {
    return false;
  }
  
  return true;
}



//FUNCIONA
template <class T, class U>
bool Grafo<T,U>::eliminarVertice(T indice)
{
  int contador=0;
  bool eliminado=false;

  typename std::vector<std::vector<U>>::iterator itGrafo, guardarLista;
   typename std::vector<T>::iterator itVertice, guardarPos;
    
    itGrafo = this->grafo.begin();
    itVertice = this->vertices.begin();
    
   for ( ; itGrafo != this->grafo.end() &&  !eliminado; itGrafo++,itVertice++,contador++) 
    {   
      if(*itVertice==indice)
      {
        eliminado=true;
        guardarLista=itGrafo;
        guardarPos=itVertice;
      } 
    }
  
    if(eliminado)
    {

      for (std::vector<U>& row : this->grafo) 
      {
            row.erase(row.begin() + contador-1);
      }
      
      //eliminarlo de la lsita de vertices
      this->vertices.erase(guardarPos);
      //eliminar su lista
      this->grafo.erase(guardarLista);
    }

  return eliminado;
}



//FUNCIONA
template <class T, class U>
bool Grafo<T,U>::eliminarArista(T VerticeOrigen, T VerticeDestino)
{
  int Origen=BuscarPos(VerticeOrigen);
  int Destino=BuscarPos(VerticeDestino);
  
   if(Origen!=-1 && Destino!=-1)
   {
     if(BuscarArista(VerticeOrigen, VerticeDestino))
    {
      this->grafo[Origen][Destino]=0;
      return true;
    }
   }
  
  return false;
}



//FUNCIONA
template <class T, class U>
void Grafo<T,U>::recorridoPlano()
{
  for(int i=0; i<this->vertices.size();i++)
  {
    std::cout<<this->vertices[i]<<" ";
  }
}



//FUNCIONA
template <class T, class U>
std::vector<bool> Grafo<T,U>::recorridoEnProfundidad(T valor)
{
  std::vector<bool> Visitados (cantidadVertices(),false);
  if(BuscarVertice(valor))
  {
    recorridoEnProfundidad(BuscarPos(valor),Visitados);
    
  }
  return  Visitados;
};

//FUNCIONA
template <class T, class U>
void Grafo<T,U>::recorridoEnProfundidad(int valor,std::vector<bool> &Visitados)
{
  //mirar si ya se visito ese vertice

  //si no se ha visitado
  if(!Visitados[valor])
  {  
      //guardarlo e imprimirlo
      Visitados[valor]=true;
    
      std::cout<<this->vertices[valor]<<" ";
  
      //buscar el siguiente vertice con el que este conectado
      //y hacer como si fuera un preorden raro
      for(int i=0; i<this->grafo[valor].size();i++)
      {
        if(this->grafo[valor][i]!=0)
        {
          recorridoEnProfundidad(i, Visitados);
        }
      }
    
  } 
  
};



//FUNCIONA
template <class T, class U>
void Grafo<T,U>::recorridoAdyacencias(T valor)
{
  //aqui se usa el mismo procedimeinto que en el arbol 
  //en niveles
std::vector<bool> Visitados(cantidadVertices(),false);
  //buscar 
  if(!this->vertices.empty())
  {
    std::queue <int> aux;

    aux.push(BuscarPos(valor));
    Visitados[BuscarPos(valor)]=true;
    
    while(!aux.empty())
    {
        std::cout<<this->vertices[aux.front()]<<" ";

        for(int i=0; i<this->grafo[aux.front()].size();i++)
        {
          if(this->grafo[aux.front()][i]!=0)
          {
            if(!Visitados[i])
            {  
              Visitados[i]=true;
              aux.push(i);
            }
          }
        }
      
        aux.pop(); 
    }
  }
};


// Propiedades:
//FUNCIONA
template <class T, class U>
ResultadoHamilton<T> Grafo<T,U>::esHamilton(T inicio)
{
   ResultadoHamilton<T> res;
  res.tipo=-1;
  if(BuscarVertice(inicio))
  {
    std::vector<bool> Visitados (cantidadVertices(),false);
    std::vector<T>recorrido;
    return esHamilton(BuscarPos(inicio),Visitados,BuscarPos(inicio),recorrido);
  }

  //significa que no se puede realizar porque no existe el vertice
  
  return res;
};

//FUNCIONA
template <class T, class U>
ResultadoHamilton<T> Grafo<T,U>::esHamilton(int valor, std::vector<bool> &Visitados, int inicio,std::vector<T>&recorrido)
{
    //si retorna 1 es porque no es hamilton
    ResultadoHamilton<T> tipo;
    tipo.tipo=1;
  
    //mirar si ya se visito ese vertice
    //si no se ha visitado
    if(!Visitados[valor])
    {  
        Visitados[valor]=true;
        recorrido.push_back(this->vertices[valor]);
      
        if(this->vertices.size()==recorrido.size())
        {
            //mirar si es circuito o si es camino
            //ver si el ultimo vertice llega al primero
            //por el metodo buscar
                                    
            if(BuscarArista(this->vertices[recorrido.size()-1],this->vertices[inicio]))
            {
              tipo.tipo=3;
              recorrido.push_back(this->vertices[inicio]);
              tipo.recorrido=recorrido;
              return tipo;
            }
            else
            {
              tipo.tipo=2;
              tipo.recorrido=recorrido;
              return tipo;
            }      
        }
        
        //buscar el siguiente vertice con el que este conectado
        //y hacer como si fuera un preorden raro

        for(int i=0; i<this->grafo[valor].size();i++)
        {
          if(this->grafo[valor][i]!=0)
          {
              tipo=esHamilton(i, Visitados, inicio, recorrido);
  
              if(tipo.tipo!=1)
              {
                break;
              }
          }
        }  
    }  

    return tipo;
};



template <class T, class U>
bool Grafo<T,U>::esEuler()
{ 
  
  std::vector<bool> visitado (cantidadVertices(),false);
  std::vector<int> gradoCero;
  int inicio;
  //ver si es dirigido o no es dirigido
  if(this->esDirigido)
  {
std::vector<int> mismosGrados,gradosDiferentes;
    
    for(int i=0; i<this->grafo.size(); i++)
    {
      int contadorInicio=0;
      int contadorFinal=0;
      for(int j=0; j<this->grafo[i].size(); j++)
      {
        if(this->grafo[i][j]!=0)
        {
           contadorInicio++;
        }

        if(this->grafo[j][i]!=0)
        {
           contadorFinal++;
        }
      }

      if(contadorFinal==contadorInicio)
      {
        if(contadorFinal==0)
        {
          gradoCero.push_back(i);
        }
        else
        {
          mismosGrados.push_back(i);
          inicio=i;
        }
      }
      else if(contadorFinal-contadorInicio==1||contadorFinal-contadorInicio==-1)
      {
        gradosDiferentes.push_back(i);
      }
    
    }

    if(mismosGrados.size()==cantidadVertices()-gradoCero.size())
    {
      /*Un grafo dirigido tiene un circuito de Euler si y sólo si cada vértice tiene el mismo grado de salida como de entrada, y todos los vértices con grado diferente de cero pertenecen al mismo componente fuertemente conectado.*/
        if(evalComponente(gradoCero, inicio))
      {
        
        std::cout<<"\nes un circuito de euler";
        return true;
      }
       std::cout<<"\nno es un circuito de euler ni camino";
      return false;
    }
    else if(gradosDiferentes.size()==2 && mismosGrados.size()==cantidadVertices()-2-gradoCero.size())
    {
      if(evalComponente(gradoCero, inicio))
      {
        std::cout<<"\nes un camino de euler";
        return true;
      }
       std::cout<<"\nno es un circuito de euler ni camino";
      return false;
    }
    else
    {
      std::cout<<"\nno es un circuito de euler ni camino";
      return false;
    }

    
  }
  else
  {
    int gradoImpar=0;
    
    //ver si todos los vertices tienen grado par 
    for(int i=0; i<this->grafo.size(); i++)
    {
      int contador=0;
      for(int j=0; j<this->grafo[i].size(); j++)
      {
        if(this->grafo[i][j]!=0)
        {
           contador++;
        }
      }

      if(contador%2!=0)
      {
        gradoImpar++;
      }
      else
      {
         if(contador==0)
         {
           gradoCero.push_back(i);
         }
         else
         {
           inicio=i;
         } 
      }
          
    }
    

    if(gradoImpar==0)
    {
      //se mira si todos son un componente conectado
      //se inicia desde cualquier vertice
      //se hace un recorrido en adyacencia
      //si el vector de visitados tiene alguno en falso, debe ser de los de grado cero, de lo contrario no es camino ni circuido de euler
      if(evalComponente(gradoCero, inicio))
      {
        /*Un grafo no dirigido tiene un circuito de Euler si y sólo si cada vértice tiene grado par, y todos los vértices con grado diferente de cero pertenecen al mismo componente conectado.*/
        std::cout<<"\nes un circuito de euler";
        return true;
      }
      std::cout<<"\nno es un circuito de euler ni camino";
      return false;
      
    }
    else if(gradoImpar==2)
    {
      
      if(evalComponente(gradoCero, inicio))
      {
        std::cout<<"\nes un camino de euler";
        return true;
      }
      std::cout<<"\nno es un circuito de euler ni camino";
      return false;
    }
    else
    {
      std::cout<<"\nno es un circuito de euler ni camino";
      return false;
    }
    
  }
}

template <class T, class U>
bool Grafo<T,U>::evalComponente(std::vector<int> gradoCero, int inicio)
{
  std::vector<bool> componente=recorridoEnProfundidad(this->vertices[inicio]);
      
      for(int i=0; i<componente.size(); i++)
      {
          for(int j=0; i<gradoCero.size(); j++)
          {
            if(i!=j && componente[i]!=true)
            {
              std::cout<<"\nno es un circuito de euler ni camino";
              return false;
            }
          }
      }
  return true;
}



//FUNCIONA 
//DIJKSTRA
template <class T, class U>
std::vector< std::vector< int >>  Grafo<T,U>::Dijkstra(T inicio)
{
  //logica para hacerlo
  //vector de las distancias de cada vertice desde el vertice de referencia
  std::vector<U> pesos;
  
  pesos.resize(cantidadVertices());
  //llenar los pesos en infinito menos el primero
  //funcion para buscar la posicion del indice en el arreglo
  
  for(int i=0; i<cantidadVertices(); i++)
  {  
      pesos[i]=this->infinito;
  }
  
  pesos[BuscarPos(inicio)]=0;
  
  //predecesores de cada vertice
  std::vector<int> predecesores;
  predecesores.resize(cantidadVertices());
  
  std::vector<int> recorrido;
  
  std::vector<int> cola;
  
  //PRIMERO LLENAR COLA
  for(int i=0;i<this->vertices.size();i++)
  {
    cola.push_back(i);
  }

  predecesores[BuscarPos(inicio)]=BuscarPos(inicio);

  int pos;
  
  while(cola.size()!=0)
  {
    
    //escoger vertice con menor peso
    //se retorna la posicion
     pos=escogerSiguiente(cola,pesos);
    
    //en el recorrido se ingresan las posiciones de los vertices que
    //estan en la lista de vertices
     recorrido.push_back(pos);

    
     eliminarDeCola(cola,pos); 

    //SE METEN TODOS LO VECINOS DEL VERTICE QUE SE ESTARA ANALIZANDO 
     std::list<int> vecino=encontrarVecinos(pos);
      
     typename std::list<int>::iterator itVecinos=vecino.begin();
  
      itVecinos=vecino.begin();

    
      for(;itVecinos!=vecino.end();itVecinos++)
      {
        //peso del vertice actual mas
        //el peso del vertice actual a uno de sus vecinos
        U nuevoPeso= pesos[pos]+ this->grafo[pos][*itVecinos];

        //si el peso que tiene el vecino es mayor al que tendria con el nuevo peso
        //se cambia el peso por el nuevo
        //se dice que el predecesor es el vertice que se esta analizando
        if(pesos[*itVecinos]>nuevoPeso)
        {
          pesos[*itVecinos] =  nuevoPeso;
          predecesores[*itVecinos]=pos;  
        }
        
      }
    
 }
  
  std::vector<std::vector<int>> recorridos;
  //hacer recorridos con base a los predecesores :3 y se arregl todo
 
  hacerRecorridos(recorridos, recorrido,predecesores,BuscarPos(inicio));

  return recorridos;
};

template <class T, class U>
int Grafo<T,U>::escogerSiguiente(std::vector<int> &cola, std::vector<U> pesos)
{  
  
  int referencia=cola[0];
  int pesoRef=pesos[cola[0]];

  for(int i=0; i<cola.size(); i++)
  {
    if(pesos[cola[i]]<pesoRef)
    {
      referencia=cola[i];
      pesoRef=pesos[cola[i]];
    }
  }
  
  return referencia;
}

template <class T, class U>
void Grafo<T,U>::eliminarDeCola(std::vector<int> &cola, int pos)
{
  typename std::vector<int>::iterator itVertices=cola.begin();

  typename std::vector<int>::iterator itBorrar;

  for(;itVertices!=cola.end();itVertices++)
  {
    if(*itVertices==pos)
    {
      itBorrar=itVertices;
    } 
  }
  
  cola.erase(itBorrar);
}

template <class T, class U>
std::list<int> Grafo<T,U>::encontrarVecinos(int pos)
{
  
  std::list<int> vecinos;

  for(int i=0; i<this->grafo[pos].size(); i++)
  {
    //si existe la arista
    if(this->grafo[pos][i]!=0)
    {
       vecinos.push_back(i);        
    }
  }
  
  return vecinos;
}



//PRIM
//FUNCIONA
template <class T, class U>
std::vector< std::vector< int >> Grafo<T,U>::Prim(T inicial)
{
  std::vector<std::vector<int>> recorridos;
  
  if(!this->vertices.empty())
  {
    //llenar visitados de true or false y no tener que hacer la funcion visitados
    std::vector<bool> Visitados(cantidadVertices()-1,false);
    
    //GUARDA EL ORDEN DE LOS VERTICES QUE SE VAN VISITANDO
    std::vector<int> ordenVertices;
    
    //guarda los predecesores de los vertices, para despues armar el grafo
    std::vector<int> predVertices;

    //va guardando los "vertices vecinos" (incluyendo su peso) y los ordena por su peso de menor a mayor
    std::priority_queue<VPrim<T, U>, std::vector<VPrim<T, U>>, CompareVPrim<T, U>> aux;
    
    int contadorVertices=0, costoGeneral=0;

    //obtener el vertice de inicio de prim
    VPrim<T,U> inicio; 
    //guarda el valor de inicio enviado por el usuario
    inicio.valor=inicial;
    //guarda la posicion del valor de inicio en el vector de vectores del grafo
    inicio.posicion=BuscarPos(inicial);
    //guarda el peso de la conexion desde el inicio hasta si mismo
    //en este caso es cero
    inicio.peso=0;
    //el predecesor es si mismo
    //pero servira para armar el vector de predecesores
    inicio.predecesor=BuscarPos(inicial);
    //se guarda en la cola
    aux.push(inicio);
    //se usa para hacer las iteraciones segun la posicion del vector en el arreglo de vectores
    int referencia=inicio.posicion;
      
    ordenVertices.push_back(referencia);
    predVertices.resize(cantidadVertices());
    predVertices[referencia]=referencia;

    //se anaden a la cola los vertices vecinos no visitados
    //de este primer vertice
    AnadirVertices(referencia,aux,Visitados);
  
  while(!aux.empty() && contadorVertices!=cantidadVertices()-1)
  { 
    inicio =aux.top();    
    aux.pop();

    if(!Visitados[inicio.posicion])
    {
       referencia=inicio.posicion;

       ordenVertices.push_back(referencia);
     
       predVertices[referencia]=inicio.predecesor;
    
       contadorVertices++;
    
       costoGeneral+=inicio.peso;

       AnadirVertices(referencia,aux,Visitados);
    }
    
  }
    
    hacerRecorridos(recorridos, ordenVertices,predVertices,BuscarPos(inicial));

  }

  return recorridos;
};

//FUNCIONA
template <class T, class U>
void Grafo<T,U>::AnadirVertices(int referencia,std::priority_queue<VPrim<T, U>, std::vector<VPrim<T, U>>, CompareVPrim<T, U>> &aux, std::vector<bool> &Visitados)
{
    //SE VISITO ESE VERTICE Y NO SE PUEDE VOLVER A VISTIAR
    Visitados[referencia]=true;

    //se buscan los vecinos del vertice escogido
    //la referencia dice cual fue el vertice escogido
    for(int i=0; i<this->grafo[referencia].size(); i++)
    {
      //si no se ha visitado
      //y si existe la arista
      if(!Visitados[i] && this->grafo[referencia][i]!=0)
      {
        //se anade ese vecino
          VPrim<T, U> anadir;  
          anadir.valor=this->vertices[i];
          anadir.posicion=i;
          anadir.peso=this->grafo[referencia][i];
          //el predecesor es claramente el vector de referencia
          // el que se le buscan los vecinos
          anadir.predecesor=referencia;
          //se anade a la cola de prioridad
          aux.push(anadir);
        
      }
    }
}



//KRUSKAL
//FUNCIONA
template <class T, class U>
void Grafo<T,U>::Kruskal()
{
  //llenar las aristas 
  std::vector<Arista<T,U>> AristasOrdenadas;
  std::vector<Arista<T,U>> Predecesores;
  
  llenarAristas(AristasOrdenadas);

  //ordenarlas
  sort(AristasOrdenadas.begin(), AristasOrdenadas.end(), compararAristas);
  
  //crear los bosques iniciales por posiciones
  std::vector<std::vector<int>> Bosques;
  Bosques.resize(this->vertices.size());

  //llenar bosques iniciales
  for(int i=0; i<this->vertices.size(); i++)
  {
    Bosques[i].push_back(i);
  }

  while(!AristasOrdenadas.empty())
  {
    Arista<T,U> AEval=AristasOrdenadas.front();
    
    if(!mismoBosque(Bosques,AEval))
    {
      cambiarBosques(Bosques,AEval);
      Predecesores.push_back(AEval);
    }
    
    AristasOrdenadas.erase(AristasOrdenadas.begin());
  }

  for(int i=0; i<Predecesores.size(); i++)
  {
    std::cout<<Predecesores[i].inicio<<" "<<Predecesores[i].fin<<" "<<Predecesores[i].peso<<"\n";
  }
};

//FUNCIONA
template <class T, class U>
bool Grafo<T,U>::compararAristas(const Arista<T,U>& a, const Arista<T,U>& b) 
{
  return a.peso < b.peso;
}

//FUNCIONA
template <class T, class U>
bool Grafo<T,U>:: mismoBosque( std::vector<std::vector<int>> Bosques, Arista<T,U> AEval)
{
  bool EncontradoInicio,EncontradoFinal;
  
  for(int i=0; i<Bosques.size(); i++)
  {
     EncontradoInicio=false;
     EncontradoFinal=false;
    
    for(int j=0; j<Bosques[i].size(); j++)
    {
      
      if(Bosques[i][j]==AEval.inicio)
      {
        EncontradoInicio=true;
      }
      
      if(Bosques[i][j]==AEval.fin)
      {
        EncontradoFinal=true;
      }
      
      if(EncontradoInicio && EncontradoFinal)
      {
        return true;
      }
      
    }
  }

  return false;
  
}

//FUNCIONA
template <class T, class U>
void Grafo<T,U>::cambiarBosques(std::vector<std::vector<int>> &Bosques, Arista<T,U> AEval)
{
  typename std::vector<std::vector<int>>::iterator itBosque, guardarBosqueFin,guardarBosqueInicio;
  
  typename std::vector<int>::iterator itVertices;
    itBosque = Bosques.begin();

  //obtengo los dos bosques
  for ( ; itBosque != Bosques.end(); itBosque++) 
  {  
    itVertices=itBosque->begin();
    for(;itVertices!=itBosque->end();itVertices++)
    {
      if(*itVertices==AEval.inicio)
      {
        guardarBosqueInicio=itBosque;
        //std::cout<<"\ninicio"<<AEval.inicio;
      } 

      if(*itVertices==AEval.fin)
      {
        guardarBosqueFin=itBosque;
       // std::cout<<" fin"<<AEval.fin;
      }
    } 
  }

  itVertices=guardarBosqueFin->begin();
  
  for(;itVertices!=guardarBosqueFin->end();itVertices++)
  {
    guardarBosqueInicio->push_back(*itVertices);
  }

  Bosques.erase(guardarBosqueFin);
}

//FUNCIONA
template <class T, class U>
void Grafo<T,U>:: llenarAristas( std::vector<Arista<T,U>> &AristasOrdenadas)
{
  Arista<T,U> nueva;
  
  for(int i=0; i<this->grafo.size(); i++)
  {
    for(int j=0; j<this->grafo.size(); j++)
    {
      if(this->grafo[i][j]!=0)
      {
        nueva.inicio=i;
        nueva.fin=j;
        nueva.peso=this->grafo[i][j];
        AristasOrdenadas.push_back(nueva);
      }
    }
  }
}



//FUNCIONA
//floyd para encontrar el camino mas corto (primero)
template <class T, class U>
void Grafo<T,U>::Floyd(T Inicio, T Final)
{
  std::vector<std::vector<U>> MatrizPesos=prepararMatrizPesos();

  std::vector<std::vector<int>> MatrizPredecesores=prepararMatrizPredecesores();

   for (int k = 0; k < this->grafo.size(); ++k) 
   {
        for (int i = 0; i < this->grafo.size(); ++i) 
        {
            for (int j = 0; j < this->grafo.size(); ++j) 
            {
              
                if (MatrizPesos[i][k] + MatrizPesos[k][j] < MatrizPesos[i][j] && i!=j && i!=k && j!=k) 
                {
                    MatrizPesos[i][j] = MatrizPesos[i][k] + MatrizPesos[k][j];
                    MatrizPredecesores[i][j] = k+1; 
                }
              
            }
        }
    }
/*
  std::cout << "\n";
  for (int i = 0; i < MatrizPesos.size(); i++) {
    for (int j = 0; j < MatrizPesos[i].size(); j++) {
      std::cout <<  MatrizPesos[i][j] << " ";
    }
    
    std::cout << "\n";
  }
  std::cout << "\n";

  for (int i = 0; i < MatrizPredecesores.size(); i++) {
    for (int j = 0; j < MatrizPredecesores[i].size(); j++) {
      std::cout <<  MatrizPredecesores[i][j] << " ";
    }
    
    std::cout << "\n";
  }
  */

  //despues de esto se debe reconstruir la ruta mas corta para los dos puntos recibidos
  if(MatrizPredecesores[BuscarPos(Inicio)][BuscarPos(Final)]==-1)
  {
    //no se puede llegar de inicio a final de inguna manera
    std::cout<<"\nno se puede llegar de"<<Inicio<<" a final "<<Final;
  }
  else
  {
    //se deberia construir

    std::stack<U> ruta;
    
    ruta.push(Final);

    while (Inicio != Final) {
      //OJO CON LOS -1
        Final = MatrizPredecesores[Inicio-1][Final-1];
        ruta.push(Final);
    }

    //la ruta esta desde el final hasta el inicio Ojo

    while(!ruta.empty())
    {
       std::cout<<ruta.top()<<" ";
       ruta.pop();
    }
  }

  
};

//FUNCIONA
template <class T, class U>
std::vector<std::vector<U>> Grafo<T,U>::prepararMatrizPesos()
{
  std::vector<std::vector<U>> matrizRetorno=this->grafo;
  
   for(int i=0; i<this->grafo.size();i++)
   {
     for(int j=0; j<this->grafo.size();j++)
     {
       if(i==j)
       {
         matrizRetorno[i][j]=0;
       }
       else if(matrizRetorno[i][j]==0)
       {
         matrizRetorno[i][j]=this->infinito;
       }
     }
   }
  
   return matrizRetorno;
};

//FUNCIONA
template <class T, class U>
std::vector<std::vector<int>> Grafo<T,U>::prepararMatrizPredecesores()
{
  std::vector<std::vector<int>> matrizRetorno=this->grafo;
  
   for(int i=0; i<this->grafo.size();i++)
   {
     for(int j=0; j<this->grafo.size();j++)
     {
       if(BuscarAristaPorPosicion(i, j))
       {
         matrizRetorno[i][j]=i+1;
       }
       else
       {
         matrizRetorno[i][j]=-1;
       }
     }
   }
  
   return matrizRetorno;
};


template <class T, class U>
void Grafo<T,U>::hacerRecorridos(std::vector<std::vector<int>>&recorridos, std::vector<int> ordenVertices, std::vector<int> predecesores, int posInicial){ 
  
  int contadorCaminos=0;
  
  recorridos.resize(cantidadVertices());

  //recorre cada vertice para hacer su recorrido

  std::vector<int> recorridoPrelim;

  int pos=0;
    
  for (int i=0; i<this->vertices.size(); i++,contadorCaminos++,pos++)
  {
    int valor=pos;
    
    while(valor!=ordenVertices[posInicial])
    {  
      recorridoPrelim.push_back(valor);
      valor=predecesores[valor];
    }

    recorridoPrelim.push_back(valor);


    typename std::vector<int>::reverse_iterator rit;

    for (rit=recorridoPrelim.rbegin(); rit!=recorridoPrelim.rend(); rit++)
    {
      recorridos[contadorCaminos].push_back(*rit);
    }
    
    recorridoPrelim.clear();
  }
  
};


template <class T, class U>
U Grafo<T,U>::ObtenerCosto(int VerticeOrigen, int VerticeDestino){
  return this->grafo[VerticeOrigen][VerticeDestino];
};


template <class T, class U>
void Grafo<T,U>::ObtenerLongitudCamMatricial(int numeroMultiplicaciones)
{
  std::vector<std::vector<int>> MatrizAdyacencia=crearMatrizAdyacencia();
  std::vector<std::vector<int>> MatrizAdyacencia2=MatrizAdyacencia;
  std::vector<std::vector<int>> MatrizSuma=MatrizAdyacencia;

  //crear matriz de indentidad

   std::vector< std::vector<int>> matrizIdentidad(this->grafo.size(),  std::vector<int>(this->grafo.size(), 0));

  for (int i = 0; i < this->grafo.size(); i++) {
      matrizIdentidad[i][i] = 1;
  }
  
  for(int i=0; i<numeroMultiplicaciones; i++)
  {

    MatrizAdyacencia2=MultiplicarMatrices(MatrizAdyacencia2, MatrizAdyacencia);
    
    MatrizSuma=SumarMatrices(MatrizSuma, MatrizAdyacencia2);
    
  }

  MatrizSuma=SumarMatrices(MatrizSuma, matrizIdentidad);
  
}

template <class T, class U>
std::vector<std::vector<int>> Grafo<T,U>::crearMatrizAdyacencia()
{
  std::vector<std::vector<int>> MatrizAdyacencia;
  for(int i=0; i<this->grafo.size();i++)
  {
    for(int j=0; j<this->grafo[i].size();j++)
    {
       if(this->grafo[i][j]!=0)
       {
         MatrizAdyacencia[i][j]=1;
       }
    }
  }

  return MatrizAdyacencia;
}

template <class T, class U>
std::vector<std::vector<int>> Grafo<T,U>::MultiplicarMatrices(std::vector<std::vector<int>> M1, std::vector<std::vector<int>>M2)
{
  std::vector< std::vector<int>> resultado(this->grafo.size(),  std::vector<int>(this->grafo.size(), 0));
  
   for (int i = 0; i < this->grafo.size(); i++) {
        for (int j = 0; j < this->grafo.size(); j++) {
            for (int k = 0; k < this->grafo.size(); k++) {
                resultado[i][j] += M1[i][k] * M2[k][j];
            }
        }
    }

  return resultado;
}

template <class T, class U>
std::vector<std::vector<int>> Grafo<T,U>::SumarMatrices(std::vector<std::vector<int>> M1, std::vector<std::vector<int>>M2)
{
  std::vector< std::vector<int>> resultado(this->grafo.size(),  std::vector<int>(this->grafo.size(), 0));

  for (int i = 0; i < this->grafo.size(); i++) {
        for (int j = 0; j < this->grafo.size(); j++) {
            resultado[i][j] = M1[i][j] + M2[i][j];
        }
    }

    return resultado;

}

#endif
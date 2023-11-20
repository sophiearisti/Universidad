#ifndef _GRAFO_HXX_
#define _GRAFO_HXX_

#include "Grafo.h"
#include <map>

// Crea un grafo con datos vacios.
template <class T, class U>
Grafo<T,U>::Grafo() {
  this->esDirigido=false;
};
  
// Crea un grafo con una lista de vertices y una multista de grafo por defecto.
template <class T, class U>
Grafo<T,U>::Grafo(std::list<Vertice<T,U> > vertices, std::list< std::list<Vertice<T,U> > > graf, bool tipoGrafo){
  this->vertices = vertices;
  this->grafo = grafo;
  this->esDirigido = tipoGrafo;
};

// Destructor del grafo.
template <class T, class U>
Grafo<T,U>::~Grafo(){
  
};

// Retorna la condicion actual del grafo.
template <class T, class U>
bool Grafo<T,U>::esVacio(){
  return this->vertices.size()==0;
};

//FUNCIONA
template <class T, class U>
bool Grafo<T,U>::InsertarVertice(T vertice){
    bool estaInsertado = false;
  // Primero se verifique que el vertice no exista en la lista:
 
    Vertice<T,U> nuevoVer;
    nuevoVer.setIndice(vertice);
  
    // En caso negativo, se añande el vertice a la lista de vertices:
    this->vertices.push_back(nuevoVer);

    // Luego se agrega una lista vacia:
    typename std::list< Vertice<T,U> > nuevaLista;
    this->grafo.push_back(nuevaLista);
    estaInsertado = true;
    
    return estaInsertado;
};

//FUNCIONA
template <class T, class U>
bool Grafo<T,U>::InsertarArista(long VerticeOrigen, long VerticeDestino, U peso){
  bool estaInsertado = false;
  bool aristaExistente = false;
  unsigned int contador = 0;

  // Verificar que el vértice origen y el vértice destino están:
  Punto VerticeOrigenAux = ObtenerVertice(VerticeOrigen);
  Punto VerticeDestinoAux = ObtenerVertice(VerticeDestino);
  Punto cero{};
  if(VerticeOrigenAux != cero && VerticeDestinoAux != cero){
      // Agregar la nueva arista(caso de ser un grafo dirigido).
      // Con el índice del vértice origen, extraer la lista correspondiente:
      typename std::list< std::list<Vertice<T,U>> >::iterator itVer;
      for(itVer = this->grafo.begin(); itVer != this->grafo.end(); itVer++, contador++)
      {
          if(contador == VerticeOrigen)
          {
              // Verificar que el vértice destino no está en la lista:
              typename std::list< Vertice<T,U> >::iterator itList;
              for(itList = (itVer)->begin(); itList != (itVer)->end(); itList++)
              {
                  if(contador == VerticeDestino)
                  {
                      aristaExistente = true;
                  }
              }
              if(!aristaExistente)
              {
                  Vertice<T,U> nuevaArista(peso, VerticeDestinoAux);
                  nuevaArista.setpredecesor(VerticeOrigen);
                  (*itVer).push_back(nuevaArista);
                  estaInsertado = true;
              }
              break;
          }
      }
  }
  return estaInsertado;
};


//FUNCIONA
template <class T, class U>
unsigned int Grafo<T,U>::NumeroVertices(){
  return this->vertices.size();
};

//FUNCIONA
template <class T, class U>
unsigned int Grafo<T,U>::cantidadAristas(){
  unsigned int cantidad=0;
  typename std::list< std::list< Vertice<T,U> > >::iterator itGrafo;
  typename std::list< Vertice<T,U> >::iterator itVertice;
  
  itGrafo = this->grafo.begin();
  
  for ( ; itGrafo != this->grafo.end(); itGrafo++) 
  {
       cantidad += itGrafo->size(); 
  }
  
  return cantidad;
};

//FUNCIONA
template <class T, class U>
Punto Grafo<T,U>::ObtenerVertice(int pos){
  typename std::list<Vertice<T,U>>::iterator itVertices;
  
  itVertices = this->vertices.begin();
  int contador=0;

  Vertice<T,U> v;
  
  for ( ; contador<pos; contador++) 
    itVertices++;


  v=*itVertices;

  return v.getIndice();
  
};

//FUNCIONA
template <class T, class U>
bool Grafo<T,U>::eliminarVertice(T indice){
    bool eliminado=false;
  
    typename std::list< std::list<Vertice<T,U>> >::iterator itGrafo, guardarLista;
    typename std::list<Vertice<T,U> >::iterator itVertice, guardarPos;
    
    itGrafo = this->grafo.begin();
    itVertice = this->vertices.begin();
    
    for ( ; itGrafo != this->grafo.end() &&  !eliminado; itGrafo++,itVertice++) 
    {
      
      if(itVertice->getIndice()==indice)
      {
        eliminado=true;
        guardarLista=itGrafo;
        guardarPos=itVertice;
      }
      
    }

  if(eliminado)
  {
    //eliminarlo de la lsita de vertices
    this->vertices.erase(guardarPos);
    //eliminar su lista
    this->grafo.erase(guardarLista);
  }
  
  return eliminado;
  
};

//FUNCIONA
template <class T, class U>
bool Grafo<T,U>::eliminarArista(T VerticeOrigen, T VerticeDestino){
  bool eliminado=false;
  
  typename std::list< std::list<Vertice<T,U> > >::iterator itGrafo,guardarLista;
  typename std::list<Vertice<T,U> >::iterator itVertice, guardarPos;
  typename std::list<Vertice<T,U> >::iterator itVertices;
    
    itGrafo = this->grafo.begin();
    itVertices = this->vertices.begin();
    
  for ( ; itGrafo != this->grafo.end(); itGrafo++, itVertices++) 
  {
      
      if(itVertices->getIndice()==VerticeOrigen)
      {
        itVertice = itGrafo->begin();
      
        for ( ; itVertice != itGrafo->end(); itVertice++)
        {
           if(itVertice->getIndice()==VerticeDestino)
           {
             guardarPos=itVertice;
             guardarLista=itGrafo;
             eliminado=true;
           }
          
        }
        
      }
  }

   if(eliminado)
   {
      guardarLista->erase(guardarPos);
   }
  
  return eliminado;
};

// Define si el grafo es de tipo dirigido o no dirigido.
template <class T, class U>
void Grafo<T,U>::setEsDirigido(bool tipoGrafo){
  this->esDirigido = tipoGrafo;
};

// Obtiene el tipo de grafo.
template <class T, class U>
bool Grafo<T,U>::getEsDirigido(){
  return this->esDirigido;
};

//FUNCIONA
// Recorridos:
template <class T, class U>
void Grafo<T,U>::recorridoPlano(){
  typename std::list<Vertice<T,U> >::iterator itVertices;

  itVertices = this->vertices.begin();
    
  for ( ; itVertices != this->vertices.end(); itVertices++) 
  {
    Vertice<T,U> v= *itVertices;
    Punto p=v.getIndice();
    std::cout<<"PUNTO: "<<p.X<<" "<<p.Y<<"\n"; 
  }
  
};

//FUNCIONA
template <class T, class U> 
void Grafo<T,U>::recorridoEnProfundidad(T valor){
  if(BuscarVertice(valor))
  {
    std::vector<T,U> Visitados;
    recorridoEnProfundidad(this->grafo,valor,Visitados);
  }
};

//FUNCIONA
template <class T, class U> 
void Grafo<T,U>::recorridoEnProfundidad(std::list< std::list<Vertice<T,U> > > Vertices,T valor,std::vector<T,U> &Visitados){
  bool visitado=false;
  
  //mirar si ya se visito ese vertice
  for(int i=0; i<Visitados.size(); i++)
  {
    if(valor==Visitados[i])
    {
      visitado=true;
    }
  }

  //si no se ha visitado
  if(!visitado)
  {  
    
      //guardarlo e imprimirlo
      Visitados.push_back(valor);
      std::cout<<valor<<" ";
  
      //buscar el siguiente vertice con el que este conectado
      //y hacer como si fuera un preorden raro
  
      typename std::list< std::list<Vertice<T,U> > >::iterator itGrafo;
      typename std::list<Vertice<T,U> >::iterator itVertice;
      typename std::list<Vertice<T,U> >::iterator itVertices;
      
      itGrafo = Vertices.begin();
      itVertices = this->vertices.begin();
      
      for ( ; itGrafo != Vertices.end(); itGrafo++,itVertices++) 
      {  
          if(itVertices->getIndice()==valor)
          {
            itVertices->setVisitado(true);
            
            itVertice = itGrafo->begin();
          
            for ( ; itVertice != itGrafo->end(); itVertice++)
            {  
              recorridoEnProfundidad(Vertices, itVertice->getIndice(), Visitados);
            }
            
          }
    
      } 
  } 
  
};

//FUNCIONA
template <class T, class U> 
void Grafo<T,U>::recorridoAdyacencias(T valor){
  //aqui se usa el mismo procedimeinto que en el arbol 
  //en niveles

  //buscar 
  if(!this->vertices.empty())
  {
    std::queue <T,U> aux;
    std::vector<T,U> Visitados;

    aux.push(valor);

    while(!aux.empty())
    {
        std::cout<<aux.front()<<" ";
      
        typename std::list< std::list<Vertice<T,U> > >::iterator itGrafo;
        typename std::list<Vertice<T,U> >::iterator itVertice;
        typename std::list<Vertice<T,U> >::iterator itVertices;
        
        itGrafo = this->grafo.begin();
        itVertices = this->vertices.begin();
        
        for ( ; itGrafo != this->grafo.end(); itGrafo++,itVertices++) 
        {
          
          if(itVertices->getIndice()==aux.front())
          {
            itVertice = itGrafo->begin();
          
            for ( ; itVertice != itGrafo->end(); itVertice++)
            {
              bool yaVisitado=false;
              
              for(int i=0; i<Visitados.size() && !yaVisitado; i++)
              {
                if(Visitados[i]==itVertice->getIndice())
                {
                  yaVisitado=true;
                }
              }
              
              if(!yaVisitado)
              {  
                Visitados.push_back(itVertice->getIndice());
                aux.push(itVertice->getIndice());
              }
            }
            
          }
    
        } 
      
        aux.pop(); 
    }
  }
};

// Propiedades:
template <class T, class U>
Resultado<T,U> Grafo<T,U>::esHamilton(T inicio){
  if(BuscarVertice(inicio))
  {
    std::vector<T,U> Visitados;
    return esHamilton(inicio,Visitados);
  }
  
  Resultado<T,U> res;
  res.tipo=-1;
  return res;
};

template <class T, class U>
Resultado<T,U> Grafo<T,U>::esHamilton(T valor, std::vector<T,U> Visitados){
    Resultado<T,U> tipo;
    tipo.tipo=1;
    bool visitado=false;
    
    //mirar si ya se visito ese vertice
    for(int i=0; i<Visitados.size(); i++)
    {
      if(valor==Visitados[i])
      {
        visitado=true;
      }
    }

    //si no se ha visitado
    if(!visitado)
    {  
        //guardarlo e imprimirlo
        Visitados.push_back(valor);
      
      
        if(this->vertices.size()==Visitados.size())
        {
            //mirar si es circuito o si es camino
            //ver si el ultimo vertice llega al primero
            //por el metodo buscar
                                    
            if(BuscarArista(Visitados[Visitados.size()-1],Visitados[0]))
            {
              tipo.tipo=3;
              Visitados.push_back(Visitados[0]);
              tipo.recorrido=Visitados;
              return tipo;
            }
            else
            {
              tipo.tipo=2;
              tipo.recorrido=Visitados;
              return tipo;
            }        
        }
    
        //buscar el siguiente vertice con el que este conectado
        //y hacer como si fuera un preorden raro
    
        typename std::list< std::list<Vertice<T,U> > >::iterator itGrafo;
        typename std::list<Vertice<T,U> >::iterator itVertice;
        typename std::list<Vertice<T,U> >::iterator itVertices;
        
        itGrafo = this->grafo.begin();
        itVertices = this->vertices.begin();
        
        for ( ; itGrafo != this->grafo.end(); itGrafo++,itVertices++) 
        {  
            if(itVertices->getIndice()==valor)
            {
              itVertices->setVisitado(true);
              
              itVertice = itGrafo->begin();
            
              for ( ; itVertice != itGrafo->end(); itVertice++)
              {  
                tipo=esHamilton(itVertice->getIndice(), Visitados);

                if(tipo.tipo!=1)
                {
                  break;
                }
              }

            }
      
        } 
    }  

    return tipo;
};

template <class T, class U>
bool Grafo<T,U>::esCircuitoEuler(){
  bool esCircuitoEuler = true;

  // Para comprobar la existencia de un circuito de Euler se debe evaluar que todos los vertices del grafo tengan un grado par:
  typename std::list< Vertice<T,U>>::iterator itList;
  for(itList = this->vertices.begin(); itList != this->vertices.end(); itList++){
      if(!esPar(*itList)){
          esCircuitoEuler = false;
          break;
      }
  }
  return esCircuitoEuler;
};

template <class T, class U>
bool Grafo<T,U>::esPar(Vertice<T,U> vertice){
    bool esPar = false;
    unsigned int contador = 0;

    // Comprobamos la paridad:
    typename std::list< Vertice<T,U>>::iterator itList;
    for(itList = this->vertices.begin(); itList != this->vertices.end(); itList++){
        if((*itList) == vertice) {

            // Comprobamos la paridad:
            if((*itList).getGrado() % 2 == 0) {
                esPar = true;
                break;
            }
        }
    }
    return esPar;
};


// Recorridos por minimo Valor:
//FUNCIONA
template <class T, class U>
std::vector< std::vector< unsigned long >>  Grafo<T,U>::Dijkstra(unsigned long inicio){
 //logica para hacerlo
  //vector de las distancias de cada vertice desde el vertice de referencia
  std::vector<U> pesos;
  U infinito =1000000000000000;
  
  pesos.resize(NumeroVertices());
  //llenar los pesos en infinito menos el primero
  //funcion para buscar la posicion del indice en el arreglo
  
  for(int i=0; i<NumeroVertices(); i++)
  {  
      pesos[i]=infinito;
  }
  
  pesos[inicio]=0;
  
  std::vector<unsigned long> predecesores;
  predecesores.resize(NumeroVertices());
  
  std::vector<unsigned long> recorrido;
  
  std::vector<int> cola;
  
  //PRIMERO LLENAR COLA
  llenarCola(cola);

  predecesores[inicio]=inicio;

  while(cola.size()!=0)
  {
    
    //escoger vertice con menor peso
    //se retorna la posicion
    inicio=escogerSiguiente(cola,pesos);
    //en el recorrido se ingresan las posiciones de los ertices que
    //estan en la lista de vertices
    recorrido.push_back(inicio);

    
    eliminarDeCola(cola,inicio); 

   std::list<int> vecino=encontrarVecinos(inicio);
typename std::list<int>::iterator itVecinos=vecino.begin();

    itVecinos=vecino.begin();

    for(;itVecinos!=vecino.end();itVecinos++)
    {
      U nuevoPeso= pesos[inicio]+ buscaDistancia(inicio,*itVecinos);
      
      if(pesos[*itVecinos]>nuevoPeso)
      {
        pesos[*itVecinos] =  nuevoPeso;
        predecesores[*itVecinos]=inicio;  
      }
      
    }
    
 }
  
  std::vector<std::vector<unsigned long>> recorridos;
  //hacer recorridos con base a los predecesores :3 y se arregl todo
 
hacerRecorridos(recorridos, recorrido,predecesores);

  return recorridos;
};

template <class T, class U>
void Grafo<T,U>::eliminarDeCola(std::vector<int> &cola, int inicio){
  typename std::vector<int>::iterator itVertices=cola.begin();

  typename std::vector<int>::iterator itBorrar;

  for(;itVertices!=cola.end();itVertices++)
  {
    if(*itVertices==inicio)
    {
      itBorrar=itVertices;
    } 
  }

  cola.erase(itBorrar);
}

template <class T, class U>
int Grafo<T,U>::escogerSiguiente(std::vector<int> &cola, std::vector<U> pesos){  
  
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
std::list<int> Grafo<T,U>::encontrarVecinos(int inicio){
  std::list<int> vecinos;
  
  //es un buscar pero solo retornar la lista
    typename std::list< std::list<Vertice<T,U> > >::iterator itGrafo;
  
    typename std::list<Vertice<T,U> >::iterator itVertice;
  
    typename std::list<Vertice<T,U> >::iterator itVertices;
    
    itGrafo = this->grafo.begin();
  
    itVertices = this->vertices.begin();

    int pos=0;
    
    for ( ; itGrafo != this->grafo.end(); itGrafo++,itVertices++,pos++)
    {
       
      if(pos==inicio)
      { 
        
        itVertice = itGrafo->begin();
      
        for ( ; itVertice != itGrafo->end(); itVertice++)
        {

          T verticeV2=itVertice->getIndice();;
         
          for(int i=0; i<NumeroVertices();i++)
          {
              //se va a buscar la posicion del vertice en la lista de vertices 
          //y esa es la que se pone
            
            T verticeV=ObtenerVertice(i);
            
            if(verticeV.X==verticeV2.X && verticeV.Y==verticeV2.Y)
            {
              vecinos.push_back(i);
            }
            
          }

        }
        
      }

    }
  
  return vecinos;
}


template <class T, class U>
U Grafo<T,U>::buscaDistancia(int VerticeOrigen, int VerticeDestino){
  //es un buscar arista pero se retorna la distancia
  U distancia;
  int posOrigen=0;
  T VDestino= ObtenerVertice(VerticeDestino);

  typename std::list< std::list<Vertice<T,U> > >::iterator itGrafo;
  
    typename std::list<Vertice<T,U> >::iterator itVertices;
    
    itGrafo = this->grafo.begin();
  
    itVertices = this->vertices.begin();
    
    for ( ; itGrafo != this->grafo.end(); itGrafo++,posOrigen++)
    {
       
      itVertices = itGrafo->begin();
      
      if(posOrigen==VerticeOrigen)
      {
        for ( ; itVertices != itGrafo->end(); itVertices++)
        {
          T vertEval=itVertices->getIndice();
          
          if(vertEval.X==VDestino.X && vertEval.Y==VDestino.Y)
            {
              distancia=itVertices->getPeso();
            }
        }
      }

    }
  
  return distancia;
}

template <class T, class U>
void Grafo<T,U>::llenarCola(std::vector<int> &cola){
  //se llena con las posiciones
  typename std::list<Vertice<T,U> >::iterator itVertices=this->vertices.begin();
  int i=0;
  
  for(;itVertices!=this->vertices.end();itVertices++,i++)
  {
    cola.push_back(i);
  }
  
}

template <class T, class U>
void Grafo<T,U>::hacerRecorridos(std::vector<std::vector<unsigned long>>&recorridos, std::vector<unsigned long> ordenVertices, std::vector<unsigned long> predecesores){ 
  unsigned long contadorCaminos=0;
  
  recorridos.resize(NumeroVertices());

  //recorre cada vertice para hacer su recorrido
  typename std::list<Vertice<T,U> >::iterator itVertices;

  itVertices = this->vertices.begin();

  std::vector<unsigned long> recorridoPrelim;

  unsigned long pos=0;
    
  for ( ; itVertices != this->vertices.end(); itVertices++,contadorCaminos++,pos++)
  {
    unsigned long valor=pos;
    
    while(valor!=ordenVertices[0])
    {  
      recorridoPrelim.push_back(valor);
      valor=predecesores[valor];
    }

    recorridoPrelim.push_back(valor);


    typename std::vector<unsigned long>::reverse_iterator rit;

    for (rit=recorridoPrelim.rbegin(); rit!=recorridoPrelim.rend(); rit++)
    {
      recorridos[contadorCaminos].push_back(*rit);
    }
    
    recorridoPrelim.clear();
  }
  
}





template <class T, class U>
std::vector< std::vector< unsigned long >> Grafo<T,U>::Prim(unsigned long referencia){
  
  std::vector<std::vector<unsigned long>> recorridos;

  if(!this->vertices.empty())
  {
    
    std::vector<unsigned long> Visitados;
    
    std::vector<unsigned long> ordenVertices;
    std::vector<unsigned long> predVertices;
    
    std::priority_queue<Vertice<T,U>> aux;
    std::vector<std::pair<U, unsigned long>> pred;
    
    int contadorVertices=0, costoGeneral=0;

    //encontrar el vertice con la posicion de referencia
    Vertice<T,U> inicio=this->vertices.front(); 
    inicio.setPeso(0);
    inicio.setpredecesor(0);
    
    aux.push(inicio);
    
    ordenVertices.push_back(referencia);
    predVertices.resize(NumeroVertices());
    predVertices[referencia]=referencia;
    
   AnadirVertices(referencia,aux,Visitados,pred);
    
   while(!aux.empty() && contadorVertices!=NumeroVertices()-1)
    { 
      inicio =aux.top();    
      aux.pop();

      typename std::vector<std::pair<U, unsigned long>>::iterator itVertices;
  
          typename std::vector<std::pair<U, unsigned long>>::iterator itBorrar;

      unsigned long predNuevo;
  
          itVertices=pred.begin();
        
          for(;itVertices!=pred.end();itVertices++)
          {
            if(itVertices->first==inicio.getPeso())
            {
              itBorrar=itVertices;
              predNuevo=itVertices->second;
              pred.erase(itBorrar);
              break;
            }
          }

      if(!Visitado(inicio.getIndice(),Visitados))
      {
         
         referencia=BuscarPos(inicio.getIndice());
        
         ordenVertices.push_back(referencia);
       
        predVertices[referencia]=predNuevo;
      
         contadorVertices++;
      
         costoGeneral+=inicio.getPeso();

         AnadirVertices(referencia,aux,Visitados,pred);
      }
      
    }


   // hacerRecorridos(recorridos, ordenVertices,predVertices);

  /*  std::cout<<std::endl;
    for(int i=0; i<ordenVertices.size(); i++)
    {
      std::cout<<ordenVertices[i]<<" ";
    }
     std::cout<<"\ncostos "<<costoGeneral;*/

    //imprimir recorridos
    std::cout<<std::endl;

     hacerRecorridos(recorridos, ordenVertices,predVertices);
    
 /*for(int i=0; i<recorridos.size(); i++)
    {
      for(int j=0; j<recorridos[i].size(); j++)
      {
        std::cout<<recorridos[i][j]<<" ";
      }
      std::cout<<std::endl;
    }*/
  }

  return recorridos;
};

template <class T, class U>
void Grafo<T,U>::AnadirVertices(unsigned long referencia,std::priority_queue<Vertice<T,U>>& aux, std::vector<unsigned long> &Visitados,std::vector<std::pair<U, unsigned long>> &pred){  
    
    Visitados.push_back(referencia);
  
    typename std::list< std::list<Vertice<T,U> > >::iterator itGrafo;
    typename std::list<Vertice<T,U> >::iterator itVertice;
    typename std::list<Vertice<T,U> >::iterator itVertices;
          
    itGrafo = this->grafo.begin();
    itVertices = this->vertices.begin();
    int pos=0;
        
   for ( ; itGrafo != this->grafo.end(); itGrafo++,itVertices++,pos++)
    {
      if(pos==referencia)
      {
         itVertice = itGrafo->begin();

        for ( ; itVertice != itGrafo->end(); itVertice++)
        { 
          if(!Visitado(itVertice->getIndice(),Visitados))
          {
            itVertice->getpredecesor();
            aux.push(*itVertice);
            pred.push_back(std::make_pair(itVertice->getPeso(),itVertice->getpredecesor()));
          }        
        }
        
      }
    }
}

template <class T, class U>
int Grafo<T,U>::BuscarPos(T valor){
  typename std::list<Vertice<T,U>>::iterator itVertices;
  
  itVertices = this->vertices.begin();
  int contador=0;
  
  for ( ; itVertices != this->vertices.end(); contador++,itVertices++) 
  {
    
    T Punto=itVertices->getIndice();
    
    if(Punto.X==valor.X && Punto.Y==valor.Y)
    {
      break;
    }
    
  }
  
  return contador;
}


template <class T, class U>
bool Grafo<T,U>::Visitado(T valor,  std::vector<unsigned long> Visitados)
{
    //buscar posicion del punto
    int v=BuscarPos(valor);
  
    for(int i=0; i<Visitados.size(); i++)
    {
  
      if(v==Visitados[i])
      {
        return true;
      }
    }

    return false;
}

template <class T, class U>
U Grafo<T,U>::ObtenerCosto(int VerticeOrigen, int VerticeDestino){
  //es un buscar arista pero se retorna la distancia
  U distancia;
  int posOrigen=0;
  T VDestino= ObtenerVertice(VerticeDestino);

  typename std::list< std::list<Vertice<T,U> > >::iterator itGrafo;
  
    typename std::list<Vertice<T,U> >::iterator itVertices;
    
    itGrafo = this->grafo.begin();
  
    itVertices = this->vertices.begin();
    
    for ( ; itGrafo != this->grafo.end(); itGrafo++,posOrigen++)
    {
       
      itVertices = itGrafo->begin();
      
      if(posOrigen==VerticeOrigen)
      {
        for ( ; itVertices != itGrafo->end(); itVertices++)
        {
          T vertEval=itVertices->getIndice();
          
          if(vertEval.X==VDestino.X && vertEval.Y==VDestino.Y)
            {
              distancia=itVertices->getPeso();
            }
        }
      }

    }
  
  return distancia;
}

#endif

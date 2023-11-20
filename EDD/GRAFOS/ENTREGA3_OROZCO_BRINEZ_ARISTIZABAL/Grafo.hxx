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
  this->esDirigido = false;
};

// Constructor con los por los parametros por defecto de la clase.
template<class T, class U>
Grafo<T, U>::Grafo( std::vector<T> vertices, std::vector<std::vector<U> > aristas, bool tipoGrafo ){
    this->vertices = vertices;
    this->aristas = aristas;
    this->esDirigido = tipoGrafo;
};

// Destructor del grafo.
template <class T,class U>
Grafo<T,U>::~Grafo()
{
  
};

template<class T, class U>
void Grafo<T, U>::setAristas( std::vector<std::vector<T> > grafo ){
    this->grafo = grafo;
};

// Obtiene la actual matriz de vectores. 
template<class T, class U>
std::vector<std::vector<U> > Grafo<T, U>::getAristas(){
    return this->grafo;
};

// Asigna un nuevo vector de vertices.
template<class T, class U>
void Grafo<T, U>::setVertices( std::vector<T> vertices ){
    this->vertices = vertices;
};

template <class T,class U>
std::vector<T> Grafo<T,U>::getVertices()
{
  return this->vertices;
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

//FUNCIONA
template <class T,class U>
bool Grafo<T,U>::insertarVertice(T vertice)
{
  bool estaInsertado = false;
    
    // Se verifica que efectivamente no exista ya el vértice en el grafo:
    if(!BuscarVertice(vertice)) {

        // Se agrega el vértice a al vector de vertices:
        this->vertices.push_back(vertice);

        // Se agrega una nueva fila y nueva columna a la matriz (llena de ceros):
        typename std::vector<U> nuevaLista(cantidadVertices()-1, 0);
        this->grafo.push_back(nuevaLista);
        typename std::vector<std::vector<U> >::iterator itVector;
        for(itVector = this->grafo.begin(); itVector < grafo.end(); itVector++) {
            itVector->push_back(0);
        }
        estaInsertado = true;
    }

  
  return estaInsertado;
};

//FUNCIONA
template <class T,class U>
bool Grafo<T,U>::insertarArista(int VerticeOrigen, int VerticeDestino, U peso)
{ 
  
   if(this->grafo[VerticeOrigen][VerticeDestino]==0)
   {
      this->grafo[VerticeOrigen][VerticeDestino]=peso;
      return true;
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
     if(this->vertices[i].getCoordenada_x()==indice.getCoordenada_x() && this->vertices[i].getCoordenada_y()==indice.getCoordenada_y())
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

template <class T, class U>
void Grafo<T,U>::recorridoPlano()
{
  for(int i=0; i<this->vertices.size();i++)
  {
    std::cout<<this->vertices[i]<<" ";
  }
}

// Realiza un ciclo desde todos los vertices para encontrar la ruta mas largo desde la matriz de distancias mas cortas:
template<class T, class U>
void Grafo<T, U>::encontrarCaminoMasLargo(std::pair<T, T> &puntosMasAlejados, U &pesoTotal, std::vector<T> &caminoMasLargo) {
    float pesoAux = 0;
    unsigned int tamGrafo = this->grafo.size();
    std::vector<T> caminoActual;

    // Vector para almacenar los pesos máximos calculados hasta el momento para cada vértice
    std::vector<U> pesosMaximos(tamGrafo, 0);

    // Realizamos un ciclo de recorrido de profundidad para cada vértice en el grafo, utilizando la programación dinámica
    for (int i = 0; i < tamGrafo; i++) {
        caminoActual.clear();
        recorridoProfundidad(this->vertices[i], reinterpret_cast<float &>(pesoAux), caminoActual, pesosMaximos);

        if (pesoAux > pesoTotal) {
            pesoTotal = pesoAux;
            caminoMasLargo = caminoActual;
        }
        pesoAux = 0;
    }

    puntosMasAlejados.first = caminoMasLargo[0];
    puntosMasAlejados.second = caminoMasLargo[caminoMasLargo.size() - 1];
};

// Nueva función para encontrar el camino mas largo por profundidad:
// Realiza un recorrido de profundidad sobre el grafo.
template<class T, class U>
void Grafo<T, U>::recorridoProfundidad(T verticeInicio, U &maxPeso, std::vector<T> &caminoActual, std::vector<U> &pesosMaximos) {
    if (!this->esVacio()) {
        std::vector<bool> visitados(this->grafo.size(), false);
        U pesoActual = 0, PesoPreliminar = 0;
        std::vector<T> caminoPreliminar;
        dfs(BuscarPos(verticeInicio), visitados, pesoActual, PesoPreliminar, maxPeso, caminoActual, caminoPreliminar, pesosMaximos);
    }
}

// Realiza un recorrido de profundidad sobre el grafo.
template<class T, class U>
void Grafo<T, U>::dfs(unsigned int indiceVertice, std::vector<bool> visitados, U &pesoActual, U PesoPreliminar, U &maxPeso, std::vector<T> &caminoActual, std::vector<T> caminoPreliminar, std::vector<U> &pesosMaximos) {
    visitados[indiceVertice] = true;
    caminoPreliminar.push_back(this->vertices[indiceVertice]);
    PesoPreliminar += pesoActual;

    // Verificar si ya se calculó el peso máximo para este vértice
    if (pesosMaximos[indiceVertice] != 0 && PesoPreliminar + pesosMaximos[indiceVertice] <= maxPeso) {
        // Si el peso máximo almacenado más el peso actual no supera al peso máximo actual, no es necesario continuar el recorrido
        return;
    }

    bool tieneAdyacentesNoVisitados = false;

    for (int i = 0; i < this->grafo.size(); i++) {
        if (this->grafo[indiceVertice][i] != 0 && !visitados[i]) {
            tieneAdyacentesNoVisitados = true;
            dfs(i, visitados, this->grafo[indiceVertice][i], PesoPreliminar, maxPeso, caminoActual, caminoPreliminar, pesosMaximos);
        }
    }

    if (!tieneAdyacentesNoVisitados) {
        if (PesoPreliminar > maxPeso) {
            maxPeso = PesoPreliminar;
            caminoActual = caminoPreliminar;

            // Actualizar el peso máximo almacenado para este vértice
            pesosMaximos[indiceVertice] = maxPeso - PesoPreliminar;
        }
    }
}

#endif

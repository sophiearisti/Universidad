#ifndef _GRAFO_H_
#define _GRAFO_H_

//#include "Vertice.h"
#include <limits>
#include <list>
#include <queue>
#include <vector>

template <class T> struct Resultado {
  std::vector<T> recorrido;
  int tipo;
};

template <class T, class U> struct Arista {
  int inicio;
  int fin;
  U peso;
};

template <class T, class U> struct VPrim {
  T valor;
  int posicion;
  int predecesor;
  U peso;
};

template <class T, class U> struct CompareVPrim {
  bool operator()(const VPrim<T, U> &v1, const VPrim<T, U> &v2) const {
    return v1.peso > v2.peso; // Compare by peso in ascending order
  }
};

template <class T, class U> class Grafo {
protected:
  std::vector<T> vertices;
  std::vector<std::vector<U>> grafo;
  bool esDirigido;
  U infinito = 100; // std::numeric_limits<U>::max();

public:
  Grafo();

  Grafo(std::vector<T> vertices, std::vector<std::vector<U>> aristas,
        bool tipoGrafo);

  ~Grafo();

  // HECHO
  bool esVacio();
  void setEsDirigido(bool tipoGrafo);
  bool getEsDirigido();
  std::vector<std::vector<U>> getGrafo();
  void setVertices(std::vector<T> vertices);
  std::vector<T> getVertices();
  void setAristas(std::vector<std::vector<T>> aristas);
  std::vector<std::vector<U>> getAristas();

  // HECHO
  bool insertarVertice(T vertice);
  bool insertarArista(int VerticeOrigen, int VerticeDestino, U peso);

  // HECHO
  unsigned int cantidadVertices();
  unsigned int cantidadAristas();

  // HECHO
  int BuscarPos(T valor);
  bool BuscarVertice(T indice);
  bool BuscarArista(T VerticeOrigen, T VerticeDestino);
  bool BuscarAristaPorPosicion(int Origen, int Destino);

  // Recorridos:
  void recorridoPlano(); ////HECHO
  /*void recorridoProfundidad(T verticeInicio, U &maxPeso,
                            std::vector<T> &caminoActual);
  void dfs(unsigned int indiceVertice, std::vector<bool> visitados,
           U &pesoActual, U PesoPreliminar, U &maxPeso,
           std::vector<T> &caminoActual, std::vector<T> caminoPreliminar);*/
  void encontrarCaminoMasLargo(std::pair<T, T> &puntosMasAlejados, U &pesoTotal, std::vector<T> &caminoMasLargo);
  void recorridoProfundidad(T verticeInicio, U &maxPeso, std::vector<T> &caminoActual, std::vector<U> &pesosMaximos);
  void dfs(unsigned int indiceVertice, std::vector<bool> visitados, U &pesoActual, U PesoPreliminar, U &maxPeso, std::vector<T> &caminoActual, std::vector<T> caminoPreliminar, std::vector<U> &pesosMaximos);
  
};

#include "Grafo.hxx"
#endif
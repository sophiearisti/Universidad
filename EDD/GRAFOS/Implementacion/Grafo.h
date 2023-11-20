#ifndef _GRAFO_H_
#define _GRAFO_H_

//#include "Vertice.h"
#include <limits>
#include <list>
#include <queue>
#include <vector>

template <class T> struct ResultadoHamilton {
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

  Grafo(std::vector<T> ind, std::vector<std::vector<T>> vert, bool tipoGrafo);

  ~Grafo();

  // HECHO
  bool esVacio();
  void setEsDirigido(bool tipoGrafo);
  bool getEsDirigido();
  std::vector<std::vector<U>> getGrafo();

  // HECHO
  bool insertarVertice(T vertice);
  bool insertarArista(T VerticeOrigen, T VerticeDestino, U peso);

  // HECHO
  unsigned int cantidadVertices();
  unsigned int cantidadAristas();

  // HECHO
  int BuscarPos(T valor);
  bool BuscarVertice(T indice);
  bool BuscarArista(T VerticeOrigen, T VerticeDestino);
  bool BuscarAristaPorPosicion(int Origen, int Destino);

  // HECHO
  bool eliminarVertice(T indice);
  bool eliminarArista(T VerticeOrigen, T VerticeDestino);

  // Recorridos:
  void recorridoPlano();                                                // HECHO
  std::vector<bool> recorridoEnProfundidad(T valor);                    // HECHO
  void recorridoEnProfundidad(int valor, std::vector<bool> &Visitados); // HECHO
  void recorridoAdyacencias(T valor);                                   // HECHO

  // Propiedades:
  ResultadoHamilton<T> esHamilton(T inicio); // HECHO
  ResultadoHamilton<T> esHamilton(int valor, std::vector<bool> &Visitados,
                                  int inicio,
                                  std::vector<T> &recorrido); // HECHO

  bool esPar(T vert);     // HECHO
  bool esEuler();         // FALTA
  bool esCircuitoEuler(); // FALTA
  bool evalComponente(std::vector<int> gradoCero, int inicio);
  // Recorridos por minimo Valor:

  // HECHO
  std::vector<std::vector<int>> Dijkstra(T inicio);
  int escogerSiguiente(std::vector<int> &cola, std::vector<U> pesos);
  void eliminarDeCola(std::vector<int> &cola, int pos);
  std::list<int> encontrarVecinos(int pos);

  // HECHO
  std::vector<std::vector<int>> Prim(T inicial);
  bool operator()(const VPrim<T, U> &v1, const VPrim<T, U> &v2) const {
    return v1.peso > v2.peso; // Compare by peso in ascending order
  }
  void AnadirVertices(int referencia,
                      std::priority_queue<VPrim<T, U>, std::vector<VPrim<T, U>>,
                                          CompareVPrim<T, U>> &aux,
                      std::vector<bool> &Visitados);

  // HECHO
  void Kruskal();
  static bool compararAristas(const Arista<T, U> &a, const Arista<T, U> &b);
  void llenarAristas(std::vector<Arista<T, U>> &AristasOrdenadas);
  bool mismoBosque(std::vector<std::vector<int>> Bosques, Arista<T, U> AEval);
  void cambiarBosques(std::vector<std::vector<int>> &Bosques,
                      Arista<T, U> AEval);

  // HECHO
  void Floyd(T Inicio, T Final);
  std::vector<std::vector<U>> prepararMatrizPesos();
  std::vector<std::vector<int>> prepararMatrizPredecesores();

  // HECHO
  void hacerRecorridos(std::vector<std::vector<int>> &recorridos,
                       std::vector<int> ordenVertices,
                       std::vector<int> predecesores, int posInicial);

  // HECHO
  U ObtenerCosto(int VerticeOrigen, int VerticeDestino);

  // crear matriz de adyacencia

  std::vector<std::vector<int>> crearMatrizAdyacencia();

  std::vector<std::vector<int>>
  MultiplicarMatrices(std::vector<std::vector<int>> M1,
                      std::vector<std::vector<int>> M2);

  std::vector<std::vector<int>> SumarMatrices(std::vector<std::vector<int>> M1,
                                              std::vector<std::vector<int>> M2);

  void ObtenerLongitudCamMatricial(int numeroMultiplicaciones);
};

#include "Grafo.hxx"
#endif
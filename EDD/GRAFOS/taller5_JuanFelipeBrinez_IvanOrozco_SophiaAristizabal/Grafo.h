#ifndef _GRAFO_H_
#define _GRAFO_H_

#include "Vertice.h"
#include <cmath>
#include <list>
#include <queue>

template <class T, class U> struct Resultado {
  std::vector<T, U> recorrido;
  int tipo;
};

struct Punto 
{
    float X, Y;
    float distanciaA(const Punto &b) const {
    float x = X - b.X;
    float y = Y - b.Y;
    return (std::sqrt((x * x) + (y * y)));
   }
  // Sobreescribe el operador '==' para facilitar la comparación de un vertice.
  bool operator!=(const Punto &p) const { return (X != p.X && Y != p.Y); }
  // Sobreescribe el operador '=' para facilitar la asignación de un vertice.
  Punto &operator=(const Punto &p) 
  {
    X = p.X;
    Y = p.Y;
    return *this;
  }
};

template <class T, class U> class Grafo {
protected:
  std::list<Vertice<T, U>> vertices;
  std::list<std::list<Vertice<T, U>>> grafo;
  bool esDirigido;

public:
  Grafo();
  Grafo(std::list<Vertice<T,U> > vertices, std::list< std::list<Vertice<T,U> > > graf, bool tipoGrafo);
  ~Grafo();
  bool esVacio();
  bool InsertarVertice(T vertice);
  bool InsertarArista(long VerticeOrigen, long VerticeDestino, U peso);
  unsigned int NumeroVertices();
  unsigned int cantidadAristas();
  bool BuscarVertice(long indice);
  bool BuscarArista(long VerticeOrigen, long VerticeDestino);
  bool eliminarVertice(T indice);
  bool eliminarArista(T VerticeOrigen, T VerticeDestino);
  void setEsDirigido(bool tipoGrafo);
  bool getEsDirigido();
  Punto ObtenerVertice(int pos);

  // Recorridos:

  void recorridoPlano();
  void recorridoEnProfundidad(T valor);
  void recorridoEnProfundidad(std::list<std::list<Vertice<T, U>>> Vertices,
                              T valor, std::vector<T, U> &Visitados);
  void recorridoAdyacencias(T valor);

  // Propiedades:

  Resultado<T, U> esHamilton(T analizar, std::vector<T, U> Visitados);
  Resultado<T, U> esHamilton(T inicio);

  bool esPar(Vertice<T, U> vert);
  bool esEuler();
  bool esCircuitoEuler();

  // Recorridos por minimo Valor:
  std::vector<std::vector<unsigned long>> Dijkstra(unsigned long inicio);
  void eliminarDeCola(std::vector<int> &cola, int inicio);
  int escogerSiguiente(std::vector<int> &cola, std::vector<U> pesos);
  std::list<int> encontrarVecinos(int inicio);
  U buscaDistancia(int VerticeOrigen, int VerticeDestino);
  void llenarCola(std::vector<int> &cola);
  void hacerRecorridos(std::vector<std::vector<unsigned long>> &recorridos,
                       std::vector<unsigned long> ordenVertices,
                       std::vector<unsigned long> predecesores);

  std::vector<std::vector<unsigned long>> Prim(unsigned long inicio);
  void AnadirVertices(unsigned long referencia,
                      std::priority_queue<Vertice<T, U>> &aux,
                      std::vector<unsigned long> &Visitados,
                      std::vector<std::pair<U, unsigned long>> &pred);
  int BuscarPos(T valor);
  bool Visitado(T valor, std::vector<unsigned long> Visitados);
  //bool Buscar(int inicio, int destino);

  U ObtenerCosto(int VerticeOrigen, int VerticeDestino);
};

#include "Grafo.hxx"
#endif

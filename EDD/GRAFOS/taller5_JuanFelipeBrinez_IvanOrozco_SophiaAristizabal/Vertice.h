#ifndef __Vertice__H__
#define __Vertice__H__

#include <iomanip>
#include <iostream>

template <class T, class U> class Vertice {
private:
  U Peso;
  T Indice;
  bool visitado;
  int Grado;
  unsigned long Predecesor;

public:
  Vertice();
  Vertice(const U peso, const T indice);
  void setIndice(T indice);
  void setPeso(U peso);
  void setVisitado(bool vis);
  T getIndice();
  U getPeso();
  unsigned long getpredecesor();
  bool getVisit();
  int getGrado();
  void setGrado(int grado);
  void setpredecesor(unsigned long predecesor);

  // Sobreescribe el operador '=' para facilitar la asignación de un vertice.
  Vertice &operator=(const Vertice &v) {
    Peso = v.Peso;
    Indice = v.Indice;
    return *this;
  }

  // Sobreescribe el operador '==' para facilitar la comparación de un vertice.
  bool operator==(const Vertice &v) {
    return (Indice == v.Indice && Peso == v.Peso);
  }

  bool operator<(const Vertice<T, U> &other) const   {
    return this->Peso >
           other.Peso; // ">" for ascending order, "<" for descending order
  }

  // Sobreescribe el operador '<<' para facilitar la impresión de un vertice.
  friend std::ostream &operator<<(std::ostream &o, const Vertice &v) {
    o << "[Indice: " << v.Indice.X << v.Indice.Y << ", Peso: " << v.Peso
      << ", visitado: " << v.visitado<< ", pred: " << v.Predecesor<< "]";
    return o;
  }
};

#include "Vertice.hxx"
#endif
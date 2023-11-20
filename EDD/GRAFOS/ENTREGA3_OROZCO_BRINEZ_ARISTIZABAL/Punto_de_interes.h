#ifndef __INTERES__H__
#define __INTERES__H__

#include <cmath>
#include <iomanip>
#include <iostream>

class Punto_de_interes {
private:
  std::string tipo_elemento;
  float tamano;
  std::string unidad_medida;
  float coordenada_x;
  float coordenada_y;
  float distancia;

public:
  Punto_de_interes() = default;
  Punto_de_interes(const std::string tipo_elem, const float tam,
                   const std::string unidad_med, const float coordenada_x,
                   const float coordenada_y);
  void eliminarPunto_de_interes();
  void setTipo_elemento(std::string tipo_elem);
  void setUnidad_medida(std::string und_medida);
  void setTamano(float tamano);
  void setCoordenada_x(float coord_x);
  void setCoordenada_y(float coord_y);
  void setdistancia(float dist);

  std::string getTipo_elemento();
  float getTamano();
  std::string getUnidad_medida();
  float getCoordenada_x();
  float getCoordenada_y();
  float getDistancia();

  std::string printPunto();

  // Sobreescribe el operador '<<' para facilitar la impresión de un punto.
  friend std::ostream &operator<<(std::ostream &o, const Punto_de_interes &p) {
    /* o << "[Elemento: " << p.tipo_elemento
      << ", tamano: " << std::setprecision(2) << std::fixed << p.tamano
      << ", unidad_medida: " << p.unidad_medida
      << ", Coord X: " << std::setprecision(2) << std::fixed << p.coordenada_x
      << ", Coord Y: " << std::setprecision(2) << std::fixed << p.coordenada_y
      << "]";
      */
     o << "(" << std::setprecision(2) << std::fixed << p.coordenada_x << ", " << std::setprecision(2) << std::fixed << p.coordenada_y << ")";
    return o;
  }

  // Sobreescribe el operador '==' para facilitar la comparación de un punto.
  bool operator==(const Punto_de_interes &p) const { return (coordenada_x == p.coordenada_x && coordenada_y == p.coordenada_y); }
};

#endif

#ifndef __INTERES__HXX__
#define __INTERES__HXX__

#include "Punto_de_interes.h"

Punto_de_interes::Punto_de_interes(const std::string tipo_elem, const float tam, const std::string unidad_med,const float coordenada_x, const float coordenada_y)
{
  Punto_de_interes::setTipo_elemento(tipo_elem);
  Punto_de_interes::setTamano(tam);
  Punto_de_interes::setUnidad_medida(unidad_med);
  Punto_de_interes::setCoordenada_x(coordenada_x);
  Punto_de_interes::setCoordenada_y(coordenada_y);
}

void Punto_de_interes::setTipo_elemento(std::string tipo_elem)
{
  tipo_elemento =tipo_elem;
}

void Punto_de_interes::setUnidad_medida(std::string und_medida)
{
  unidad_medida=und_medida;
}


void Punto_de_interes::setTamano(float tam)
{
  tamano=tam;
}

void Punto_de_interes::setCoordenada_x(float coord_x)
{
  coordenada_x =coord_x;
}
void Punto_de_interes::setCoordenada_y(float coord_y)
{
  coordenada_y =coord_y;
}

std::string Punto_de_interes::getTipo_elemento()
{
  return tipo_elemento;
}

float Punto_de_interes::getTamano()
{
  return tamano;
}

std::string Punto_de_interes::getUnidad_medida()
{
  return unidad_medida;
}

float Punto_de_interes::getCoordenada_x()
{
  return coordenada_x;
}

float Punto_de_interes::getCoordenada_y()
{
  return coordenada_y;
}

 std::string Punto_de_interes::printPunto()
{
  return "[Elemento: " + this->tipo_elemento
       + ", tamano: " + std::to_string(tamano)
       + ", unidad_medida: " + this->unidad_medida
       + ", Coord X: " + std::to_string(this->coordenada_x)
       + ", Coord Y: " + std::to_string(this->coordenada_y)
       + "]";
}

#endif
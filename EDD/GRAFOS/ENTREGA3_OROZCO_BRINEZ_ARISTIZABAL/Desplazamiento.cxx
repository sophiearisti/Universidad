#ifndef __DESPLAZAMIENTO__HXX__
#define __DESPLAZAMIENTO__HXX__

#include "Desplazamiento.h"

Desplazamiento::Desplazamiento(const std::string TipoMov, const float mag, const std::string unidadMed) {
  Desplazamiento::setMagnitud(mag);
  Desplazamiento::setUnidadMedida(unidadMed);
  Desplazamiento::setTipoMovimiento(TipoMov);  
};


void Desplazamiento::setTipoMovimiento(std::string TipoMov){
  TipoMovimiento = TipoMov;
}; 

std::string Desplazamiento::getTipoMovimiento(){
  return TipoMovimiento;
}; 

void Desplazamiento::setMagnitud(float mag)
{
  magnitud=mag;
}
    
void Desplazamiento::setUnidadMedida(std::string unidadMed)
{
  unidadMedida=unidadMed;
}

float Desplazamiento::getMagnitud()
{
  return magnitud;
}

std::string  Desplazamiento::getUnidadMedida()
{
  return unidadMedida;
}

#endif
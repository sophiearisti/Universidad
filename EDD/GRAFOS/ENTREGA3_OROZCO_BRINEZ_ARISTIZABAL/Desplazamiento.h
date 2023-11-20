#ifndef __DESPLAZAMIENTO__H__
#define __DESPLAZAMIENTO__H__

#include <iostream>

class Desplazamiento {

private:
  std::string TipoMovimiento;
  float magnitud;
  std::string unidadMedida;

public:
  Desplazamiento() = default;
  Desplazamiento(const std::string TipoMov, const float mag,
                 const std::string unidadMed);
  void setTipoMovimiento(std::string TipoMov);
  void setMagnitud(float mag);
  void setUnidadMedida(std::string unidadMed);
  std::string getTipoMovimiento();
  float getMagnitud();
  std::string getUnidadMedida();
};


#endif

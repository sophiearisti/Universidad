#ifndef __COMANDO__H__
#define __COMANDO__H__

#include "Analisis.h"
#include "Desplazamiento.h"

class Comando {
private:
  bool Tipo;
  Desplazamiento CDesplazamiento;
  Analisis CAnalisis;

public:
  Comando() = default;
  Comando(const bool tip, const Desplazamiento des);
  Comando(const bool tip, const Analisis ana);
  Comando(const bool tip, const Desplazamiento des, const Analisis ana);
  void setTipo(bool t);
  void setCDesplazamiento(Desplazamiento desp);
  void setCAnalisis(Analisis analisis);
  bool getTipo();
  Desplazamiento getCDesplazamiento();
  Analisis getCAnalisis();
};


#endif

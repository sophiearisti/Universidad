#ifndef __COMANDO__HXX__
#define __COMANDO__HXX__

#include "Comando.h"

  Comando::Comando(const bool tip, const Desplazamiento des)
  {
    Comando::setTipo(tip);
    Comando::setCDesplazamiento(des);
  }
  
  Comando::Comando(const bool tip, const Analisis ana)
  {
    Comando::setTipo(tip);
    Comando::setCAnalisis(ana);
  }
  
  Comando::Comando(const bool tip, const Desplazamiento des, const Analisis ana)
  {
    Comando::setTipo(tip);
    Comando::setCDesplazamiento(des);
    Comando::setCAnalisis(ana);
  }

void Comando::setTipo(bool t)
{
  Tipo=t;
}
      
void Comando::setCDesplazamiento(Desplazamiento desp)
{
  CDesplazamiento=desp;
}
      
void Comando::setCAnalisis(Analisis analisis)
{
  CAnalisis=analisis;
}
     
bool Comando::getTipo()
{
   return Tipo;
}
     
Desplazamiento Comando::getCDesplazamiento()
{
   return CDesplazamiento;
}
   
Analisis Comando::getCAnalisis()
{
   return CAnalisis;
}

#endif
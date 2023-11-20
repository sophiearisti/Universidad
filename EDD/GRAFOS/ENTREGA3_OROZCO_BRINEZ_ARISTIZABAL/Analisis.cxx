#ifndef __ANALISIS__HXX__
#define __ANALISIS__HXX__

#include "Analisis.h"

Analisis::Analisis(const std:: string tipo_a, const std:: string obj, const std::string com)
  {
    Analisis::setTipo_analisis(tipo_a);
    Analisis::setObjeto(obj);
    Analisis::setComentario(com);
  }

    void Analisis::setTipo_analisis(std ::string tipo_an)
  {
      tipo_analisis=tipo_an;
  }


    void Analisis::setObjeto(std ::string obj)
{
  objeto=obj;
}


    void Analisis::setComentario(std ::string com)
{
  comentario=com;
}


    std ::string Analisis::getTipo_analisis()
{
  return tipo_analisis;
}
    

    std ::string Analisis::getObjeto()
{
  return objeto;
}


std ::string Analisis::getComentario()
{
    return comentario;
}

#endif
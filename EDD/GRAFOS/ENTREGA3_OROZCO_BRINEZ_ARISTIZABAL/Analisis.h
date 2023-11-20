#ifndef __ANALISIS__H__
#define __ANALISIS__H__
#include <iostream>

class Analisis {

private:
  std::string tipo_analisis;
  std::string objeto;
  std::string comentario;

public:
  Analisis() = default;
  Analisis(const std::string tipo_analisis, const std::string objeto,
           const std::string comentario);
  void eliminar_Analisis();
  void setTipo_analisis(std ::string tipo_an);
  void setObjeto(std ::string obj);
  void setComentario(std ::string com);
  std ::string getTipo_analisis();
  std ::string getObjeto();
  std ::string getComentario();
};


#endif

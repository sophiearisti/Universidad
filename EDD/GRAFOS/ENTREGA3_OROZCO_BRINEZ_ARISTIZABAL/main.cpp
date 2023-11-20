// nombres: Ivan Orozco, Juan Felipe Briñez, Sophia Aristizabal
//  primera entrega

#include <array>
// se acepta en todos los sitemas operativos
#include <algorithm>
#include <cmath>
#include <fstream>
#include <iomanip>
#include <iostream>
#include <list>
#include <regex>
#include <sstream>
#include <stdlib.h>
#include <string>
#include <vector>

// librarias de los tads que se deben incluir para que el programa funcione
#include "ArbolUbicarElementos.h"
#include "Comando.h"
#include "Grafo.h"
#include "Punto_de_interes.h"
#include "Sistema.h"

using namespace std;

// COMPONENTE 1
void ayuda(string *arreglo_input, int numPalabras);
void abrirFlujo(string *arreglo_input, int numPalabras, Sistema &curiosity);
void cargarComandos_cargarElementos(string *arreglo_input, int numPalabras,
                                    ifstream &flujo_arch_curiosity,
                                    Sistema &curiosity);
int lecturaArchivoComandos(ifstream &flujo_arch_curiosity, Sistema &curiosity);
int lecturaArchivoElementos(ifstream &flujo_arch_curiosity, Sistema &curiosity);
void agregarMovimiento(string *arreglo_input, int numPalabras,
                       Sistema &curiosity);
void agregarAnalisis(string *arreglo_input, int numPalabras, string input,
                     Sistema &curiosity);
void agregarElemento(string *arreglo_input, int numPalabras,
                     Sistema &curiosity);
void guardar(string *arreglo_input, int numPalabras, Sistema &curiosity);
void simularComandos(string *arreglo_input, int numPalabras,
                     array<double, 2> arreglo_coordenadas, Sistema &curiosity);

// COMPONENTE 2
void ubicarElementos(string *arreglo_input, int numPalabras, bool &valUbicar,
                     ArbolUbicarElementos &ElementosUbicados,
                     Sistema &curiosity);

void enCuadrante(string *arreglo_input, int numPalabras, bool &valUbicar,
                 ArbolUbicarElementos ElementosUbicados);

// COMPONENTE 3
void crearMapa(string *arreglo_input, int numPalabras, bool &valMapa,
               Sistema &curiosity,
               Grafo<Punto_de_interes, float> &MapaElementos);
void RutaMasLarga(string *arreglo_input, int numPalabras, bool &valMapa,
                  Grafo<Punto_de_interes, float> &MapaElementos);

bool compareSecond(const std::pair<int, float> &a,
                   const std::pair<int, float> &b);

int main() {

  Sistema curiosity;
  Grafo<Punto_de_interes, float> MapaElementos;
  // Respuesta del usuario
  // guardar_input: separacion del arreglos por espacios
  string input, guardar_input;

  // Cantidad de palabras que se digitaron
  int numPalabras = 0;

  // Declaracion de arreglo para guardar respuestas
  string *arreglo_input = new string[6];

  // Declaración de arreglo para probar simular_comandos.
  array<double, 2> arreglo_coordenadas;

  // Declaración de bandera para probar en cuadrante
  bool valUbicar = false;

  // Declaración de bandera para probar en cuadrante
  bool valMapa = false;

  ArbolUbicarElementos ElementosUbicados;

  do {
    cout << "\n\n$ ";
    // Ingreso del comando
    getline(cin, input);

    // Separar por espacios
    stringstream X(input);
    while (getline(X, guardar_input, ' ') && numPalabras != 6) {
      arreglo_input[numPalabras] = guardar_input;

      numPalabras++;
    }

    if (arreglo_input[0] == "ayuda") {
      // que el comando no funcione
      ayuda(arreglo_input, numPalabras);
      // Cada comando debe presentar en pantalla mensajes de éxito o error
      cout << "\n\ncomando de ayuda finalizado.";
    }
    // debe ser exactamente salir
    else if (input == "salir") {
      delete[] arreglo_input;
      cout << endl;
      return -1;
      // cout << "\n\n comando de salida ejecutado exitosamente.";
    }
    // COMPONENTE 1
    else if (arreglo_input[0] == "cargar_comandos") {
      abrirFlujo(arreglo_input, numPalabras, curiosity);
    } else if (arreglo_input[0] == "cargar_elementos") {
      abrirFlujo(arreglo_input, numPalabras, curiosity);
    } else if (arreglo_input[0] == "agregar_movimiento") {
      agregarMovimiento(arreglo_input, numPalabras, curiosity);
    } else if (arreglo_input[0] == "agregar_analisis") {
      agregarAnalisis(arreglo_input, numPalabras, input, curiosity);
    } else if (arreglo_input[0] == "agregar_elemento") {
      agregarElemento(arreglo_input, numPalabras, curiosity);
    } else if (arreglo_input[0] == "guardar") {
      guardar(arreglo_input, numPalabras, curiosity);
    } else if (arreglo_input[0] == "simular_comandos") {
      simularComandos(arreglo_input, numPalabras, arreglo_coordenadas,
                      curiosity);
    }
    // COMPONENTE 2
    else if (arreglo_input[0] == "ubicar_elementos") {
      ubicarElementos(arreglo_input, numPalabras, valUbicar, ElementosUbicados,
                      curiosity);

    } else if (arreglo_input[0] == "en_cuadrante") {
      enCuadrante(arreglo_input, numPalabras, valUbicar, ElementosUbicados);
    }
    // COMPONENTE 3
    else if (arreglo_input[0] == "crear_mapa") {
      crearMapa(arreglo_input, numPalabras, valMapa, curiosity, MapaElementos);
    } else if (arreglo_input[0] == "ruta_mas_larga") {
      RutaMasLarga(arreglo_input, numPalabras, valMapa, MapaElementos);
    } else {
      // Caso donde no se digite ningun comando conocido.
      cout << "\nEl comando no existe.";
    }

    // se reinicia la cantidad de palabras para esperar otro comando
    numPalabras = 0;
    input.clear();

  } while (input != "salir");
  cout << endl;
  return 0;
}

void ayuda(string *arreglo_input, int numPalabras) {
  bool existe_el_comando = true; // en el txt esto era falso

  if (numPalabras == 1) {
    cout << "\ncargar_comandos";
    cout << "\ncargar_elementos";
    cout << "\nagregar_movimiento";
    cout << "\nagregar_analisis";
    cout << "\nagregar_elemento";
    cout << "\nguardar";
    cout << "\nsimular_comandos";
    cout << "\nsalir";
    cout << "\nubicar_elementos";
    cout << "\nen_cuadrante";
    cout << "\ncrear_mapa";
    cout << "\nruta_mas_larga";
    cout << "\nayuda";
  }

  else if (numPalabras < 3) {
    if (*(arreglo_input + 1) == "cargar_comandos") {
      cout << "\ncargar_comandos [nombre_archivo] Carga en memoria los "
              "comandos de desplazamiento en el archivo de ejecución.";
    } else if (*(arreglo_input + 1) == "cargar_elementos") {
      cout << "\ncargar_elementos [nombre_archivo] Carga en memoria los datos "
              "de puntos de interés o elementos en el archivo de ejecución.";
    } else if (*(arreglo_input + 1) == "agregar_movimiento") {
      cout << "\nagregar_movimiento [tipo_mov] [magnitud] [unidad_med] Agrega "
              "el comando de movimiento a la lista de comandos del robot "
              "“Curiosity”.";
    } else if (*(arreglo_input + 1) == "agregar_analisis") {
      cout << "\nagregar_analisis [tipo_analisis] [objeto] [comentario] Agrega "
              "el componente o elemento a la lista de puntos de interés del "
              "robot “Curiosity”.";
    } else if (*(arreglo_input + 1) == "agregar_elemento") {
      cout << "\nagregar_elemento [tipo_comp] [tamaño] [unidad_med] [coordX] "
              "[coordY] Agrega el componente o elemento descrito a la lista de "
              "puntos de interés del robot Curiosity";
    } else if (*(arreglo_input + 1) == "guardar") {
      cout << "\nguardar [tipo_archivo] [nombre_archivo] Guarda en el archivo "
              "(nombre_archivo) la información solicitada de acuerdo al tipo "
              "de archivo.";
    } else if (*(arreglo_input + 1) == "simular_comandos") {
      cout << "\nsimular_comandos [coordX] [coordY] Permite simular el "
              "resultado de los comandos de movimiento que se enviarán al "
              "robot Curiosity desde la Tierra.";
    } else if (*(arreglo_input + 1) == "salir") {
      cout << "\nsalir Termina la ejecución de la aplicación.";
    } else if (*(arreglo_input + 1) == "ubicar_elementos") {
      cout << "\nubicar_elementos Utiliza la información de datos de interés "
              "almacenada en memoria y los ubica en una estructura de puntos.";
    } else if (*(arreglo_input + 1) == "en_cuadrante") {
      cout << "\nen_cuadrante [coordX1] [coordX2] [coordY1] [coordY2] Retorna "
              "una lista de los componentes o elementos que éstan dentro del "
              "cuadrante geográfico descrito por límites indicados.";
    } else if (*(arreglo_input + 1) == "crear_mapa") {
      cout
          << "\ncrear_mapa [coeficiente_conectividad] Utiliza la información "
             "de los puntos de interés almacenada en memoria para conectarlos "
             "entre sí teniendo en cuenta el coeficiente de conectividad dado.";
    } else if (*(arreglo_input + 1) == "ruta_mas_larga") {
      cout << "\nruta_mas_larga Permite identificar los dos componentes más "
              "alejados entre sí de acuerdo a las conexiones generadas";
    } else if (*(arreglo_input + 1) == "ayuda") {
      cout << "ayuda [ayuda]";
    } else {
      existe_el_comando = false;
    }
  }
  if (!existe_el_comando) {
    cout << "\nEl comando de ayuda no existe";
  }
}

void abrirFlujo(string *arreglo_input, int numPalabras, Sistema &curiosity) {

  if (numPalabras == 2) {

    bool ArchivoTexto;

    // evaluar si es un archivo binario o de texto
    regex reg("\\.txt$", regex_constants::icase);

    if (regex_search(arreglo_input[1], reg)) {

      ifstream flujo_arch_curiosity(arreglo_input[1]);
      cargarComandos_cargarElementos(arreglo_input, numPalabras,
                                     flujo_arch_curiosity, curiosity);
    } else {
      ifstream flujo_arch_curiosity(arreglo_input[1], ios::binary);
      cargarComandos_cargarElementos(arreglo_input, numPalabras,
                                     flujo_arch_curiosity, curiosity);
    }

  } else {
    cout << "\nIngreso de comando erroneo";
  }
}

void cargarComandos_cargarElementos(string *arreglo_input, int numPalabras,
                                    ifstream &flujo_arch_curiosity,
                                    Sistema &curiosity) {
  // se revisa si el archivo efectivamente existe o se puede abrir
  if (!flujo_arch_curiosity.is_open() || flujo_arch_curiosity.fail()) {
    cout << endl << arreglo_input[1] << " no se encuentra o no puede leerse.";
  } else {

    int cant_comandos;

    // evalua cual es el archivo que se quiere leer para guardar los comandos
    if (arreglo_input[0] == "cargar_comandos") {
      // lee el contenido del archivo
      // funcion para leer archivo de comandos
      // retorna la cantidad de comandos leidos
      cant_comandos = lecturaArchivoComandos(flujo_arch_curiosity, curiosity);

    }
    // de lo contrario es para guardar los elementos
    else {
      // lee el contenido del archivo
      // funcion para leer archivo de elementos
      // retorna la cantidad de comandos leidos
      cant_comandos = lecturaArchivoElementos(flujo_arch_curiosity, curiosity);
    }

    // evalua si el archivo esta vacio o tiene comandos
    if (cant_comandos > 0) {
      cout << endl
           << cant_comandos << " elemento(s) cargado(s) correctamente desde '"
           << arreglo_input[1] << "' .";
    } else {
      cout << endl << endl << arreglo_input[1] << " no contiene elementos.";
    }
  }

  // se cierra el flujo del archivo
  flujo_arch_curiosity.close();
}

int lecturaArchivoComandos(ifstream &flujo_arch_curiosity, Sistema &curiosity) {
  int cant_comandos = 0;
  string comando, guardar_input;

  // se borra el contenido de todo porque siempre que se lea un archivo debe
  // pasar
  // comandos.clear();
  curiosity.borrarlistaComando();

  string tipo_mov_arreglo[2] = {"avanzar", "girar"};
  string tipo_analisis_arreglo[3] = {"fotografiar", "composicion", "perforar"};

  // va a leer linea por linea
  while (getline(flujo_arch_curiosity, comando)) {

    bool formatoCorrecto = false;

    // donde voy a guardar el comando leido y meterlo en la lista
    Comando comand;

    // Separar por espacios
    stringstream X(comando);
    getline(X, guardar_input, ' ');
    // si es igual a alguno de tipo de movimiento, significa que es un comando
    // de tipo movimiento
    if (guardar_input == tipo_mov_arreglo[0] ||
        guardar_input == tipo_mov_arreglo[1]) {

      Desplazamiento desplazamiento;
      if (guardar_input == "avanzar" || guardar_input == "girar") {
        // se guarda la primera palabra, la cual es tipo de movimiento
        desplazamiento.setTipoMovimiento(guardar_input);

        getline(X, guardar_input, ' ');
        // la segunda palabra es la magnitud
        desplazamiento.setMagnitud(stof(guardar_input));

        getline(X, guardar_input, ' ');
        if (guardar_input == "cm" || guardar_input == "m" ||
            guardar_input == "grd" || guardar_input == "rad" ||
            guardar_input == "km" || guardar_input == "dm") {
          // la ltima palabra es la unidad de medida
          desplazamiento.setUnidadMedida(guardar_input);

          // al final esto se guarda  en el tad comandos
          comand.setCDesplazamiento(desplazamiento);
          // verdadero es para desplazamiento
          comand.setTipo(true);

          formatoCorrecto = true;
        }
      }

    } else if (guardar_input == tipo_analisis_arreglo[0] ||
               guardar_input == tipo_analisis_arreglo[1] ||
               guardar_input == tipo_analisis_arreglo[2]) {

      Analisis analisis;

      // utiliza la primera palabra
      // guarda la primera palabra, la cual es tipo de analisis
      analisis.setTipo_analisis(guardar_input);

      getline(X, guardar_input, ' ');
      // guarda el objeto
      analisis.setObjeto(guardar_input);

      // si X no esta vacia para ese entonces, entonces existe un comentario
      // guarda lo que queda, ya que es el comentario
      string comentario;

      getline(X, comentario);
      if (!comentario.empty()) {
        analisis.setComentario(comentario);
      }

      // se guarda el comando de analisis en el tad comandos
      comand.setCAnalisis(analisis);
      comand.setTipo(false);
      formatoCorrecto = true;
    }

    if (formatoCorrecto) {
      cant_comandos++;
      // se guarda el comando creado en la lista de comandos
      // INGRESAR SISTEMA
      // comandos.push_back(comand);

      curiosity.insertarComando(comand);
    }
  }

  return cant_comandos;
}

int lecturaArchivoElementos(ifstream &flujo_arch_curiosity,
                            Sistema &curiosity) {
  int cant_comandos = 0;
  string comando, guardar_input;

  // se elimina el contenido para sobreescribir todo
  curiosity.borrarlistaPuntoInteres();

  Punto_de_interes dato;

  while (getline(flujo_arch_curiosity, comando)) {

    cant_comandos++;
    // agregar_elemento tipo_comp tamaño unidad_med coordX
    //  coordY

    // Separar por espacios
    stringstream X(comando);
    getline(X, guardar_input, ' ');
    // la primera palabra es el tipo del elemento
    dato.setTipo_elemento(guardar_input);

    // la segunda es el tamano
    getline(X, guardar_input, ' ');
    dato.setTamano(stof(guardar_input));

    // la tercera es la unidad de medida
    getline(X, guardar_input, ' ');
    dato.setUnidad_medida(guardar_input);

    // la cuarda es la coordenada x
    getline(X, guardar_input, ' ');
    dato.setCoordenada_x(stof(guardar_input));

    // la quinta es la coordenada y
    getline(X, guardar_input, ' ');
    dato.setCoordenada_y(stof(guardar_input));

    // se guarda el dato en la lista
    curiosity.insertarPuntoInteres(dato);
    // puntos_de_interes.push_back(dato);
  }

  // se retorna la lista de comandos
  return cant_comandos;
}

// estructura del comando: agregar_movimiento tipo_mov magnitud unidad_med
void agregarMovimiento(string *arreglo_input, int numPalabras,
                       Sistema &curiosity) {
  // arreglo con las unicas respuestas validas para tipo_mov
  string tipo_mov_arreglo[2] = {"avanzar", "girar"};
  string tipo_magn_arreglo[6] = {"cm", "m", "grd", "rad", "km", "dm"};

  bool correcto = false;
  // este exactamente debe tener 4 palabras
  if (numPalabras == 4) {

    // se evaluara  si tipo_mov es correcto
    for (int i = 0; i < 2 && !correcto; i++) {
      if (*(arreglo_input + 1) == tipo_mov_arreglo[i]) {
        // por ahora el comando es correcto
        // evaluar si la magnitud es correcta
        for (int j = 0; j < 6 && !correcto; j++) {
          if (*(arreglo_input + 3) == tipo_magn_arreglo[j]) {
            correcto = true;
            // se evalua si la unidad de medida puede ser un float
            // si no lo se puede castear el string a float es incorrecto
            try {
              stod(*(arreglo_input + 2));
            } catch (const std::invalid_argument &ex) {
              correcto = false;
            }
          }
        }
      }
    }

    if (!correcto) {
      cout << "\nLa información del movimiento no corresponde a los puntos "
              "esperados (tipo, magnitud, unidad).";
    }
    // si todo el comando es correcto
    else {

      // Se añade el comando a la lista de comandos
      Desplazamiento guardarMovimiento;
      guardarMovimiento.setTipoMovimiento(*(arreglo_input + 1));
      guardarMovimiento.setMagnitud(stod(*(arreglo_input + 2)));
      guardarMovimiento.setUnidadMedida(*(arreglo_input + 3));

      Comando nuevoComando;
      nuevoComando.setTipo(true);
      nuevoComando.setCDesplazamiento(guardarMovimiento);

      // comandos.push_back(nuevoComando);
      curiosity.insertarComando(nuevoComando);

      cout << "\nEl comando de movimiento ha sido agregado exitosamente.";
    }

  } else {
    // si el comando no tiene exactamente 4 palabras
    cout << "\nLa información del movimiento no corresponde a los puntos "
            "esperados (tipo, magnitud, unidad).";
  }
}

// estructura comando: agregar_analisis tipo_analisis objeto comentario
// el comentario DEBE estar en comillas simples
void agregarAnalisis(string *arreglo_input, int numPalabras, string input,
                     Sistema &curiosity) {

  // los tipos que unicamente son aceptados en tipo_analisis
  string tipo_analisis_arreglo[3] = {"fotografiar", "composicion", "perforar"};

  bool correcto_analisis = false, correcto_coment = false;
  smatch m;
  // ingreso comentario es 4 no ingreso comentario es 3
  if (numPalabras >= 4 || numPalabras == 3) {
    for (int i = 0; i < 3 && !correcto_analisis; i++) {
      // si el tipo de analisis pertenece a los esteblecidos en el arreglo

      if (*(arreglo_input + 1) == tipo_analisis_arreglo[i]) {
        correcto_analisis = true;

        if (numPalabras >= 4) {
          // evalua si esta en comillas simples
          regex reg("'.*'$");

          // smatch m;
          //  si la expresion regular matches con el comentario ingresado, es
          //  correcto
          //  se utiliza input porque el comentario puede ser de longitud
          //  variable y el arreglo declarado debe ser solo de maximo 6
          //  regex_search busca por todo el string la expresion que haga
          //  match con las comillas
          if (regex_search(input, m, reg))
            correcto_coment = true;
        } else {
          // dado el caso que no contenga comentario
          correcto_coment = true;
        }
      }
    }
  }

  // si todo esta correcto
  if (correcto_analisis && correcto_coment) {

    // se anade el comando a la lista de comandos
    Analisis guardarAnalisis;
    guardarAnalisis.setTipo_analisis(*(arreglo_input + 1));
    guardarAnalisis.setObjeto(*(arreglo_input + 2));

    // si existe comentario
    if (!m.empty())
      guardarAnalisis.setComentario(m[0]);

    Comando comandoAnadir;
    comandoAnadir.setTipo(false);
    comandoAnadir.setCAnalisis(guardarAnalisis);

    // se anade el comando de tipo analisis a la lista de comandos
    // comandos.push_back(comandoAnadir);
    curiosity.insertarComando(comandoAnadir);

    // se anuncia que el comando es correcto
    cout << "\nEl comando de análisis ha sido agregado exitosamente.";
  } else {
    cout << "\nLa información del análisis no corresponde a los puntos "
            "esperados (tipo, objeto, comentario).";
  }
}

// estructura comando: agregar_elemento tipo_comp tamaño unidad_med coordX
// coordY
void agregarElemento(string *arreglo_input, int numPalabras,
                     Sistema &curiosity) {
  // los tipos que unicamente son aceptados en objeto
  string tipo_comp_arreglo[4] = {"roca", "crater", "monticulo", "duna"};

  // Evalua si se encuentra en el arreglo
  // Evalua si el casting de todos los elementos
  bool correcto_tipo_comp = false, correcto_casting = true;

  // el comando debe ser exactamente de 6 palabras
  if (numPalabras == 6) {

    for (int i = 0; i < 4 && !correcto_tipo_comp; i++) {

      // si  tipo_comp pertenece a los componentes establecidos
      if (*(arreglo_input + 1) == tipo_comp_arreglo[i]) {
        correcto_tipo_comp = true;

        // evalua si los numeros ingresados de tamano y las coordenadas son
        // numeros reales

        try {
          stod(*(arreglo_input + 2));
          stod(*(arreglo_input + 4));
          stod(*(arreglo_input + 5));

        } catch (const std::invalid_argument &ex) {
          correcto_casting = false;
        }
      }
    }

    if (correcto_casting && correcto_tipo_comp) {

      // Agregar el elemento a la lista de puntos de interes
      Punto_de_interes nuevoDatoI;
      nuevoDatoI.setTipo_elemento(*(arreglo_input + 1));
      nuevoDatoI.setTamano(stod(*(arreglo_input + 2)));
      nuevoDatoI.setUnidad_medida(*(arreglo_input + 3));
      nuevoDatoI.setCoordenada_x(stod(*(arreglo_input + 4)));
      nuevoDatoI.setCoordenada_y(stod(*(arreglo_input + 5)));

      Sistema curiosity;
      // puntos_de_interes.push_back(nuevoDatoI);

      curiosity.insertarPuntoInteres(nuevoDatoI);

      cout << "\nEl elemento ha sido agregado exitosamente.";
    } else {
      cout << "\nLa información del elemento no corresponde a los puntos "
              "esperados (tipo, tamaño, unidad, x, y).";
    }

  } else {
    cout << "\nLa información del elemento no corresponde a los puntos "
            "esperados (tipo, tamaño, unidad, x, y).";
  }
}

// Estructura comando: guardar tipo_archivo nombre_archivo.
void guardar(string *arreglo_input, int numPalabras, Sistema &curiosity) {
  // Validación del tipo de archivo es equivalente a alguno de los elementos de
  // "tipoArchivo":
  bool tipoArchivoVal{false};
  bool exisInfoVal{false};

  list<Comando> eval = curiosity.obtenerComando();
  list<Punto_de_interes> eval2 = curiosity.obtenerPuntoInteres();

  // Validación de la existencia del tipo de  información indicada:
  array<bool, 2> tipoArchivoOption{false, false};

  // Arreglo que contiene de elementos los posibles tipos de datos:
  array<const string, 2> tipoArchivo{"comandos", "elementos"};

  // Ciclo de evaluación para encontrar equivalencia entre el tipo de archivo
  // ingresado y las opciones posibles dentro de tipoArchivo:
  for (auto tipo : tipoArchivo)
    if (*(arreglo_input + 1) == tipo /*&& *(arreglo_input + 2) == tipo*/)
      tipoArchivoVal = true;

  // Validación del número de parámetros que deben haber y "tipoArchivoVal":
  if (numPalabras == 3 && tipoArchivoVal) {

    // Validación que existan datos para el tipo de archivo indicado:
    if (!eval.empty()) {
      if (*(arreglo_input + 1) == tipoArchivo[0] &&
              (eval.back().getCDesplazamiento().getTipoMovimiento() ==
                   "avanzar" ||
               eval.back().getCDesplazamiento().getTipoMovimiento() ==
                   "girar") ||
          eval.back().getCAnalisis().getTipo_analisis() != "\0")
        tipoArchivoOption[0] = true;
    }
    if (*(arreglo_input + 1) == tipoArchivo[1] && !eval2.empty()) {
      tipoArchivoOption[1] = true;
    }
    for (auto tipo : tipoArchivoOption)
      if (tipo == true)
        exisInfoVal = true;

    if (exisInfoVal) {

      // Definición del nombre de archivo:
      string nuevoArch{*(arreglo_input + 2)};

      // Prueba de exito para flujo de creación del archivo.
      try {

        // Creamos el archivo (si ya exsite lo sobreescribe);
        ofstream archSalida;
        archSalida.open(nuevoArch, ios::out);

        if (archSalida.is_open()) {
          if (tipoArchivoOption[0]) {
            for (list<Comando>::iterator itC = eval.begin(); itC != eval.end();
                 itC++) {
              if (itC->getTipo()) {
                archSalida << itC->getCDesplazamiento().getTipoMovimiento()
                           << " " << itC->getCDesplazamiento().getMagnitud()
                           << " " << itC->getCDesplazamiento().getUnidadMedida()
                           << endl;
              } else {
                archSalida << itC->getCAnalisis().getTipo_analisis() << " "
                           << itC->getCAnalisis().getObjeto() << " "
                           << itC->getCAnalisis().getComentario() << endl;
              }
            }
          } else if (tipoArchivoOption[1]) {
            for (list<Punto_de_interes>::iterator itD = eval2.begin();
                 itD != eval2.end(); itD++) {
              archSalida << itD->getTipo_elemento() << " " << itD->getTamano()
                         << " " << itD->getUnidad_medida() << " "
                         << itD->getCoordenada_x() << " "
                         << itD->getCoordenada_y() << endl;
            }
          }
        }
        archSalida.close();

        // Flujo sin problemas.
        if (archSalida.good()) {
          cout << "\nLa información ha sido guardada en " + nuevoArch + " .";

          // Flujo con problemas (activa el disparador del error).
        } else if (archSalida.bad()) {
          throw "\nError guardando en " + nuevoArch + " .\n";
        }
      } catch (string errorGuardando) {
        cout << "\nError guardando en " + nuevoArch + " .\n";
      }
    } else {
      cout << "\nLa información requerida no está almacenada en memoria.";
    }
  } else {
    cout << "\nLa información requerida no está almacenada en memoria.";
  }
};

// Estructura comando: simular_comandos CoordX CoordY.
// El tipo de dato en CoordX y CoordY NO puede ser una cadena de caracteres.
void simularComandos(string *arreglo_input, int numPalabras,
                     array<double, 2> arreglo_coordenadas, Sistema &curiosity) {

  bool infoVal{false};
  bool parametrosVal{true};
  double auxConversion{0.0}, almacenadorGrados{0.0};
  const double pi{3.1416};

  list<Comando> eval = curiosity.obtenerComando();
  // Leaks de memoria.

  array<const string, 6> unidades{"cm", "m", "grd", "rad", "km", "dm"};

  // Validación del tipo de dato de los parametros (debe ser real):
  try {
    stod(*(arreglo_input + 1));
    stod(*(arreglo_input + 2));
  } catch (const std::invalid_argument &ex) {
    parametrosVal = false;
  }

  // Validación de la existencia de al menos un movimiento dentro de la lista
  // de comandos:
  for (list<Comando>::iterator itC = eval.begin();
       itC != eval.end() && !infoVal; itC++)
    if (itC->getTipo() &&
        (itC->getCDesplazamiento().getTipoMovimiento() == "avanzar" ||
         itC->getCDesplazamiento().getTipoMovimiento() == "girar"))
      infoVal = true;

  if (numPalabras == 3 && parametrosVal) {
    if (infoVal) {
      // Actualizacion de la posición (inicial):
      arreglo_coordenadas[0] = stod(*(arreglo_input + 1));
      arreglo_coordenadas[1] = stod(*(arreglo_input + 2));

      for (list<Comando>::iterator itC = eval.begin(); itC != eval.end();
           itC++) {
        if (itC->getTipo()) {

          // Actualizar la posición (girar):
          if (itC->getCDesplazamiento().getTipoMovimiento() == "girar") {

            // si es grados
            if (itC->getCDesplazamiento().getUnidadMedida() == unidades[2]) {
              almacenadorGrados +=
                  itC->getCDesplazamiento().getMagnitud() * (pi / 180);
            }
            // si es radianes
            else if (itC->getCDesplazamiento().getUnidadMedida() ==
                     unidades[3]) {
              almacenadorGrados = itC->getCDesplazamiento().getMagnitud();
            }
          }
          // Actualizar la posición (avanzar):
          else if (itC->getCDesplazamiento().getTipoMovimiento() == "avanzar") {
            // si es cm
            if (itC->getCDesplazamiento().getUnidadMedida() == unidades[0]) {

              auxConversion = itC->getCDesplazamiento().getMagnitud() / 100;

            }
            // si es m
            else if (itC->getCDesplazamiento().getUnidadMedida() ==
                     unidades[1]) {

              auxConversion = itC->getCDesplazamiento().getMagnitud();

            }
            // si es km
            else if (itC->getCDesplazamiento().getUnidadMedida() ==
                     unidades[4]) {
              auxConversion = itC->getCDesplazamiento().getMagnitud() * 1000;
            }
            // si es dm
            else if (itC->getCDesplazamiento().getUnidadMedida() ==
                     unidades[5]) {
              auxConversion = itC->getCDesplazamiento().getMagnitud() / 10;
            }

            arreglo_coordenadas[0] += auxConversion * cos(almacenadorGrados);
            arreglo_coordenadas[1] += auxConversion * sin(almacenadorGrados);
          }
        }
      }
      cout << "\nLa simulación de los comandos, a partir de la posición ("
           << setprecision(2) << fixed << stod(*(arreglo_input + 1)) << ", "
           << setprecision(2) << fixed << stod(*(arreglo_input + 2))
           << "), deja al robot en la nueva posición ("
           << arreglo_coordenadas[0] << ", " << arreglo_coordenadas[1] << ") ."
           << endl;
    } else {
      cout << "\nLa información requerida no está almacenada en memoria.";
    }
  } else {
    cout << "\nLa estructura del comando es incorrecta";
  }
};

// Estructura comando: ubicar_elementos
void ubicarElementos(string *arreglo_input, int numPalabras, bool &valUbicar,
                     ArbolUbicarElementos &ElementosUbicados,
                     Sistema &curiosity) {
  bool problemas_elementos = false;
  bool resultado_exitoso = true;

  list<Punto_de_interes> eval2 = curiosity.obtenerPuntoInteres();

  if (numPalabras != 1) {
    cout << "\nLa información del comando no corresponde a la estructura "
            "esperada.";
  } else {

    // se realiza el proceso de ingreso
    if (eval2.empty()) {
      cout << "\nLa información requerida no está almacenada en memoria.";
    } else {
      list<Punto_de_interes> puntos_sin_ingresar;
      valUbicar = true;

      // se llena el arbol
      for (list<Punto_de_interes>::iterator itP = eval2.begin();
           itP != eval2.end(); itP++) {
        ElementosUbicados.insertar(*itP, puntos_sin_ingresar);
      }

      if (puntos_sin_ingresar.size() == 0) {

        cout << "\nLos elementos han sido procesados exitosamente.";

      } else {

        cout << "\nLos siguientes elementos no pudieron procesarse "
                "adecuadamente:\n";

        for (list<Punto_de_interes>::iterator its = puntos_sin_ingresar.begin();
             its != puntos_sin_ingresar.end(); its++) {
          cout << std::endl << (*its);
        }
      }
    }
  }
}
// Estructura comando: en_cuadrante coordX1 coordX2 coordY1 coordY2
void enCuadrante(string *arreglo_input, int numPalabras, bool &valUbicar,
                 ArbolUbicarElementos ElementosUbicados) {

  bool correcto_casting = true;

  try {
    stod(*(arreglo_input + 1));
    stod(*(arreglo_input + 2));
    stod(*(arreglo_input + 3));
    stod(*(arreglo_input + 4));
  } catch (const std::invalid_argument &ex) {
    correcto_casting = false;
  }
  // Tipos de puntos validos.

  if (numPalabras == 5) {
    if (correcto_casting) {
      // Validación de de que CoordX1< CoordX2 y coordY1 < CoordY2.
      if (valUbicar) {
        if (stof(*(arreglo_input + 1)) < stof(*(arreglo_input + 2)) &&
            stof(*(arreglo_input + 3)) < stof(*(arreglo_input + 4))) {

          list<Punto_de_interes> Elementos_en_cuadrante;

          // se llama a la funcion
          ElementosUbicados.evaluarCuadrante(
              stof(*(arreglo_input + 1)), stof(*(arreglo_input + 2)),
              stof(*(arreglo_input + 3)), stof(*(arreglo_input + 4)),
              Elementos_en_cuadrante);

          if (Elementos_en_cuadrante.size() != 0) {
            cout << "\nLos elementos del cuadrante solicitado son:\n";

            for (list<Punto_de_interes>::iterator its =
                     Elementos_en_cuadrante.begin();
                 its != Elementos_en_cuadrante.end(); its++) {
              cout << std::endl << (*its).printPunto();
            }
          } else {
            cout << "\nNo existen elementos en el cuadrante especificado";
          }

        } else {
          cout << "\nLa información del cuadrante no corresponde a los puntos "
                  "esperados (x_min, x_max, y_min, y_max).";
        }
      } else {
        cout << "\nLos elementos no han sido ubicados todavía.";
      }
    } else {
      cout << "\nLa información del cuadrante no corresponde a los puntos "
              "esperados (x_min, x_max, y_min, y_max).";
    }
  } else {
    cout << "\nLa información del elemento no corresponde a los puntos "
            "esperados (coordX1, coordX2, coordY1, coordY2).";
  }
}

// Estructuras comando: crear_mapa coeficiente_conectividad
void crearMapa(string *arreglo_input, int numPalabras, bool &valMapa,
               Sistema &curiosity,
               Grafo<Punto_de_interes, float> &MapaElementos) {

  bool no_hay_info = curiosity.tamanoPuntosInteres() == 0,
       resultado_exitoso = true, correcto_casting = true;
  int cantVecinos = 0;

  float coeficienteConectividad;

  // mira si el coeficiente es un flotante
  try {
    coeficienteConectividad = stof(*(arreglo_input + 1));
  } catch (const std::invalid_argument &ex) {
    correcto_casting = false;
  }

  // Validación de que CoordX1 no tenga algún caracter.
  // stringstream coeficienteConectividad(*(arreglo_input + 1));

  if (!correcto_casting || numPalabras != 2) {
    cout << "\nFormato de comando erróneo.";
  } else {
    if (no_hay_info) {
      cout << "\nLa información requerida no está almacenada en memoria.";
    } else {
      bool sinproblema = true;

      cantVecinos =
          round(coeficienteConectividad * curiosity.tamanoPuntosInteres());

      // SE INGRESA EN EL GRAFO CADA ELEMENTO
      list<Punto_de_interes> VerticesNuevos = curiosity.obtenerPuntoInteres();

      int i = 0, j = 0;
      // va a evaluar si hay dos elementos en la misma posicion
      for (list<Punto_de_interes>::iterator itP = VerticesNuevos.begin();
           itP != VerticesNuevos.end(); itP++, i++) {
        j = 0;
        for (list<Punto_de_interes>::iterator itP2 = VerticesNuevos.begin();
             itP2 != VerticesNuevos.end(); itP2++, j++) {
          if (itP->getCoordenada_x() == itP2->getCoordenada_x() &&
              itP->getCoordenada_y() == itP2->getCoordenada_y() && i != j) {
            sinproblema = false;
          }
        }
      }
      // sii no hay elementos en la misma posicion estos se ingresan
      if (sinproblema) {

        // se ingresan los elementos como vertices
        for (list<Punto_de_interes>::iterator itElemento =
                 VerticesNuevos.begin();
             itElemento != VerticesNuevos.end() && sinproblema; itElemento++) {
          MapaElementos.insertarVertice(*itElemento);
        }
        std::vector<Punto_de_interes> vertices = MapaElementos.getVertices();

        // tiene el peso y la posicion
        std::vector<std::pair<int, float>> ColaPrioridadVert;

        int seguimiento = 0;

        // aqui se va a hacer una comparacion importantr
        // para cada vertice k se va a obtener la distancia euclidiana que tiene
        // con rewpecto a los otros vertices
        for (int k = 0; k < vertices.size(); seguimiento++, k++) {
          int posEval = 0;

          float xInicio = vertices[k].getCoordenada_x();
          float yInicio = vertices[k].getCoordenada_y();

          // aqui se ingresa en la cola
          for (int j = 0; j < vertices.size(); j++, posEval++) {
            if (posEval != seguimiento) {
              float x = xInicio - vertices[j].getCoordenada_x();
              float y = yInicio - vertices[j].getCoordenada_y();
              // y ese resultado sera la posision del vertice j y la distancia
              // de k a j pair
              ColaPrioridadVert.push_back(
                  {posEval, std::sqrt((x * x) + (y * y))});
              // se anade a la cola de vertices
            }
          }

          // despues de obtener todas las distancias
          // se ordenaran de menor a mayor peso
          std::sort(ColaPrioridadVert.begin(), ColaPrioridadVert.end(),
                    compareSecond);

          // aqui se anaden
          for (int i = 0; i < cantVecinos; i++) {
            // se ingresa en cada arista de la siguiente manera
            // seguimiento es el vertice de referencia
            // ColaPrioridadVert[i].first es el  que guarda la posicion de
            // destino ColaPrioridadVert[i].second guarda el peso
            MapaElementos.insertarArista(seguimiento,
                                         ColaPrioridadVert[i].first,
                                         ColaPrioridadVert[i].second);
            // ColaPrioridadVert.erase(ColaPrioridadVert.begin());
          }
          // se pone vacia porque se hara ahora para k+1 y asi
          ColaPrioridadVert.clear();
        }

        cout << "\nEl mapa se ha generado exitosamente. Cada elemento tiene "
             << cantVecinos << " vecinos.";
        valMapa = true;

      } else {
        cout << "\nExisten 2 elementos que se encuentran en la misma posicion";
      }
    }
  }
}

bool compareSecond(const std::pair<int, float> &a,
                   const std::pair<int, float> &b) {
  return a.second <
         b.second; // Compare based on the second value in ascending order
}

// Estructuras comando: ruta_mas_larga
void RutaMasLarga(string *arreglo_input, int numPalabras, bool &valMapa,
                  Grafo<Punto_de_interes, float> &MapaElementos) {
  bool resultado_exitoso = true;

  // Costo de ruta de mayor longitud:
  float longitudTotal = 0;

  // Secuencia de vectores del camino mas largo:
  std::vector<Punto_de_interes> caminoMasLargo;

  // Vector inicial y vector final del camino mas largo:
  std::pair<Punto_de_interes, Punto_de_interes> parDeVectoresMasAlejados;

  if (numPalabras != 1) {
    cout << "\nFormato de comando erróneo.";
  } else {
    if (valMapa && resultado_exitoso) {

      // Ejecutamos la función:
      MapaElementos.encontrarCaminoMasLargo(parDeVectoresMasAlejados,
                                            longitudTotal, caminoMasLargo);

      std::cout << "\nLos puntos de interés más alejados entre sí son : \t"
                << parDeVectoresMasAlejados.first << " y "
                << parDeVectoresMasAlejados.second << " .\n";
      std::cout << "La ruta que los conecta tiene una longitud total de : \t"
                << longitudTotal << " m .\n";
      std::cout << "Y pasa por los siguientes elementos : "; //\t\t\t";
      for (int i = 0; i < caminoMasLargo.size(); i++) {
        if (i == caminoMasLargo.size() - 1) {
          std::cout << caminoMasLargo[i] << " .";
        } else {
          std::cout << caminoMasLargo[i] << " -> ";
        }
      }
    } else {
      cout << "\nEl mapa no ha sido generado todavía.";
    }
  }
}

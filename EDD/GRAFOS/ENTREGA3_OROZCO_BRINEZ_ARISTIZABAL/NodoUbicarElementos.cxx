#include "NodoUbicarElementos.h"
#include <iostream>

// Constructor por defecto del nodo.
NodoUbicarElementos::NodoUbicarElementos() {
    Punto_de_interes punto_de_interes;
    this->punto = punto_de_interes;
    this->hijoSupIzq = 0;
    this->hijoSupDer = 0;
    this->hijoInfDer = 0;
    this->hijoInfIzq = 0;
};

// Constructor de un nodo con un Punto_de_interes por defecto.
NodoUbicarElementos::NodoUbicarElementos(Punto_de_interes punto_de_interes) {
    this->punto = punto_de_interes;
    this->hijoSupIzq = 0;
    this->hijoSupDer = 0;
    this->hijoInfDer = 0;
    this->hijoInfIzq = 0;
};

// Destructor por defecto del nodo.
NodoUbicarElementos::~NodoUbicarElementos(){};

// Retorna el Punto_de_interes actual del nodo.
Punto_de_interes NodoUbicarElementos::obtenerPunto_de_interes() { return this->punto; };

// Asigna un nuevo Punto_de_interes al nodo actual.
void NodoUbicarElementos::fijarPunto_de_interes(Punto_de_interes punto_de_interes) { this->punto = punto_de_interes; };

// Retorna el nodo hijo superior izquierdo.
NodoUbicarElementos *NodoUbicarElementos::obtenerHijoSupIzq() { return this->hijoSupIzq; };

// Retorna el nodo hijo superior derecho.
NodoUbicarElementos *NodoUbicarElementos::obtenerHijoSupDer() { return this->hijoSupDer; };

// Retorna el nodo hijo inferior derecho.
NodoUbicarElementos *NodoUbicarElementos::obtenerHijoInfDer() { return this->hijoInfDer; };

// Retorna el nodo hijo inferior izquierdo.
NodoUbicarElementos *NodoUbicarElementos::obtenerHijoInfIzq() { return this->hijoInfIzq; };

// Crea un nuevo hijo superior izquierdo con un Punto_de_interes por defecto.
void NodoUbicarElementos::fijarHijoSupIzq(Punto_de_interes punto_de_interes) {
    NodoUbicarElementos *nuevoNodoSupIzq = new NodoUbicarElementos;
    nuevoNodoSupIzq->fijarPunto_de_interes(punto_de_interes);
    this->hijoSupIzq = nuevoNodoSupIzq;
    nuevoNodoSupIzq = nullptr;
};

// Crea un nuevo hijo superior izquierdo con un Punto_de_interes por defecto.
void NodoUbicarElementos::fijarHijoSupDer(Punto_de_interes punto_de_interes) {
    NodoUbicarElementos *nuevoNodoSupDer = new NodoUbicarElementos;
    nuevoNodoSupDer->fijarPunto_de_interes(punto_de_interes);
    this->hijoSupDer = nuevoNodoSupDer;
    nuevoNodoSupDer = nullptr;
};

// Crea un nuevo hijo superior izquierdo con un Punto_de_interes por defecto.
void NodoUbicarElementos::fijarHijoInfDer(Punto_de_interes punto_de_interes) {
    NodoUbicarElementos *nuevoNodoInfDer = new NodoUbicarElementos;
    nuevoNodoInfDer->fijarPunto_de_interes(punto_de_interes);
    this->hijoInfDer = nuevoNodoInfDer;
    nuevoNodoInfDer = nullptr;
};

// Crea un nuevo hijo superior izquierdo con un Punto_de_interes por defecto.
void NodoUbicarElementos::fijarHijoInfIzq(Punto_de_interes punto_de_interes) {
    NodoUbicarElementos *nuevoNodoInfIzq = new NodoUbicarElementos;
    nuevoNodoInfIzq->fijarPunto_de_interes(punto_de_interes);
    this->hijoInfIzq = nuevoNodoInfIzq;
    nuevoNodoInfIzq = nullptr;
};

// retorna el estado del nodo actual.
estadoHoja NodoUbicarElementos::esHoja() {
    if (this->hijoSupIzq == 0 && this->hijoSupDer == 0 &&
        this->hijoInfDer == 0 && this->hijoInfIzq == 0)
        return estadoHoja::ES_HOJA;
    return estadoHoja::NO_ES_HOJA;
};

// Inserta un nuevo hijo al arbol.
NodoUbicarElementos *NodoUbicarElementos::insertar(Punto_de_interes punto_de_interes, NodoUbicarElementos *nodo,std::list<Punto_de_interes> &puntos_sin_ingresar) {
 
  if (nodo == NULL) {
        nodo = new NodoUbicarElementos;
        nodo->fijarPunto_de_interes(punto_de_interes);
    }

    // (I = NW) = n < x, n > y
    else if (punto_de_interes.getCoordenada_x() < nodo->punto.getCoordenada_x() && punto_de_interes.getCoordenada_y() >= nodo->punto.getCoordenada_y()) {
        nodo->hijoSupIzq = insertar(punto_de_interes, nodo->hijoSupIzq,puntos_sin_ingresar);
    }

    // (II = NE) = n > x , n > y
    else if ((punto_de_interes.getCoordenada_x() >= nodo->punto.getCoordenada_x() && punto_de_interes.getCoordenada_y() > nodo->punto.getCoordenada_y()) || (punto_de_interes.getCoordenada_x() > nodo->punto.getCoordenada_x() && punto_de_interes.getCoordenada_y() >= nodo->punto.getCoordenada_y())) {
        nodo->hijoSupDer = insertar(punto_de_interes, nodo->hijoSupDer,puntos_sin_ingresar);
    }

    // (IV = SW) = n < x, n < Y
    else if (punto_de_interes.getCoordenada_x() < nodo->punto.getCoordenada_x() && punto_de_interes.getCoordenada_y() < nodo->punto.getCoordenada_y()) {
      nodo->hijoInfIzq = insertar(punto_de_interes, nodo->hijoInfIzq,puntos_sin_ingresar);
    }

    // (III = SE) = n > x, n < y
    else if ((punto_de_interes.getCoordenada_x() > nodo->punto.getCoordenada_x() && punto_de_interes.getCoordenada_y() < nodo->punto.getCoordenada_y()) || (punto_de_interes.getCoordenada_x() >= nodo->punto.getCoordenada_x() && punto_de_interes.getCoordenada_y() < nodo->punto.getCoordenada_y())) {
         nodo->hijoInfDer = insertar(punto_de_interes, nodo->hijoInfDer,puntos_sin_ingresar);
    }
  else
  {
        //ver si el dato esta duplicado, porque no se puede
        //anadir a la lista 
        puntos_sin_ingresar.push_back(punto_de_interes);
  }

    return nodo;
};

// Imprime el recorrido "Pre-orden" del arbol.
void NodoUbicarElementos::preOrden(NodoUbicarElementos *nodo) {
    if (nodo == 0)
        return;
    Punto_de_interes imprimir=nodo->obtenerPunto_de_interes();
    std::cout << imprimir.printPunto() << " ";
    preOrden(nodo->hijoSupIzq);
    preOrden(nodo->hijoSupDer);
    preOrden(nodo->hijoInfDer);
    preOrden(nodo->hijoInfIzq);
};



// Imprime el recorrido "In-orden" del arbol.
void NodoUbicarElementos::inOrden(NodoUbicarElementos *nodo) {
    if (nodo == 0)
        return;
    inOrden(nodo->hijoSupIzq);
    inOrden(nodo->hijoSupDer);
    Punto_de_interes imprimir=nodo->obtenerPunto_de_interes();
    std::cout << imprimir.printPunto() << " ";
    inOrden(nodo->hijoInfDer);
    inOrden(nodo->hijoInfIzq);
};

// Imprime el recorrido "Pos-orden" del arbol.
void NodoUbicarElementos::posOrden(NodoUbicarElementos *nodo) {
    if (nodo == 0)
        return;
    posOrden(nodo->hijoSupIzq);
    posOrden(nodo->hijoSupDer);
    posOrden(nodo->hijoInfDer);
    posOrden(nodo->hijoInfIzq);
    Punto_de_interes imprimir=nodo->obtenerPunto_de_interes();
    std::cout << imprimir.printPunto() << " ";
};

// Autor del codigo: https://www.interviewbit.com/blog/level-order-traversal/
// Imprime el recorrido "Nivel-orden" del árbol.
void NodoUbicarElementos::nivelOrden() {
    if (this->esHoja() == estadoHoja::ES_HOJA)
        return;
    std::queue<NodoUbicarElementos *> cola;
    cola.push(this);
    while (!cola.empty()) {
        NodoUbicarElementos *nodo = cola.front();
        Punto_de_interes imprimir=nodo->obtenerPunto_de_interes();
        std::cout << imprimir.printPunto() << " ";
        cola.pop();
        if (nodo->hijoSupIzq != 0)
            cola.push(nodo->hijoSupIzq);
        if (nodo->hijoSupDer != 0)
            cola.push(nodo->hijoSupDer);
        if (nodo->hijoInfDer != 0)
            cola.push(nodo->hijoInfDer);
        if (nodo->hijoInfIzq != 0)
            cola.push(nodo->hijoInfIzq);
        nodo = nullptr;
    }
};

void NodoUbicarElementos::evaluarCuadrante(NodoUbicarElementos *nodo, float x_min,float x_max,float y_min,float y_max,std::list<Punto_de_interes> &Elementos_en_cuadrante) {
    
   if (nodo == 0)
        return;

  evaluarNodo(nodo,x_min,x_max,y_min,y_max,Elementos_en_cuadrante);
  
      evaluarCuadrante(nodo->hijoSupIzq,x_min,x_max,y_min,y_max,Elementos_en_cuadrante);

      evaluarCuadrante(nodo->hijoSupDer,x_min,x_max,y_min,y_max,Elementos_en_cuadrante);

      evaluarCuadrante(nodo->hijoInfDer,x_min,x_max,y_min,y_max,Elementos_en_cuadrante);
  
      evaluarCuadrante(nodo->hijoInfIzq,x_min,x_max,y_min,y_max,Elementos_en_cuadrante);
  
};

void NodoUbicarElementos::evaluarNodo(NodoUbicarElementos *nodo, float x_min,float x_max,float y_min,float y_max,std::list<Punto_de_interes> &Elementos_en_cuadrante)
{
  if((nodo->obtenerPunto_de_interes().getCoordenada_x()>=x_min && nodo->obtenerPunto_de_interes().getCoordenada_x()<=x_max) && (nodo->obtenerPunto_de_interes().getCoordenada_y()>=y_min && nodo->obtenerPunto_de_interes().getCoordenada_y()<=y_max))
    {
    Elementos_en_cuadrante.push_back(nodo->obtenerPunto_de_interes());
    }
}

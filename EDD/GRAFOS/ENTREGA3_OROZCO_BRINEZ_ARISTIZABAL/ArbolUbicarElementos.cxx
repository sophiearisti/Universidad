#include "ArbolUbicarElementos.h"
#include <iostream>

// Constructor por defecto de la clase.
ArbolUbicarElementos::ArbolUbicarElementos() {
    this->raiz = 0;
};

// Constructor de la clase con un punto_de_interes como raiz por defecto.
ArbolUbicarElementos::ArbolUbicarElementos(Punto_de_interes punto_de_interes) {
    NodoUbicarElementos *nuevaRaiz = new NodoUbicarElementos;
    nuevaRaiz->fijarPunto_de_interes(punto_de_interes);
    this->raiz = nuevaRaiz;
    nuevaRaiz = nullptr;
};

// Destructor por defecto del nodo.
ArbolUbicarElementos::~ArbolUbicarElementos(){};

// Retorna el punto_de_interes actual de la raiz.
Punto_de_interes ArbolUbicarElementos::punto_de_interesRaiz() { return this->raiz->obtenerPunto_de_interes(); };

// Retorna la raiz del arbol actual.
NodoUbicarElementos* ArbolUbicarElementos::obtenerRaiz() {
    return this->raiz;
};

// Asigna un nuevo punto_de_interes a la raíz del arbol actual. 
void ArbolUbicarElementos::fijarRaiz(Punto_de_interes punto_de_interes){
    NodoUbicarElementos *nuevaRaiz = new NodoUbicarElementos;
    nuevaRaiz->fijarPunto_de_interes(punto_de_interes);
    this->raiz = nuevaRaiz;
    nuevaRaiz = nullptr;
};

// Asigna una nueva raíz al arbol actual. 
void ArbolUbicarElementos::fijarRaiz(NodoUbicarElementos* nodo){
    this->raiz = nodo;
};


// Retorna el estado del arbol.
estadoVacio ArbolUbicarElementos::esVacio() {
    if (this->raiz == 0)
        return estadoVacio::ES_VACIO;
    return estadoVacio::NO_ES_VACIO;
};

// Inserta un nuevo nodo dentro del arbol.
bool ArbolUbicarElementos::insertar(Punto_de_interes punto_de_interes,std::list<Punto_de_interes> &puntos_sin_ingresar) {
    bool estaInsertado = false;
    if(this->esVacio() == estadoVacio::ES_VACIO) {
        this->fijarRaiz(punto_de_interes);
        estaInsertado = true;
    } else {
        NodoUbicarElementos *nodoTemporal = this->raiz->insertar(punto_de_interes, this->raiz,puntos_sin_ingresar);
        this->fijarRaiz(nodoTemporal);
        estaInsertado = true;
        nodoTemporal = nullptr;
    }
    return estaInsertado;
};

// Imprime el recorrido "Pre-orden" del arbol.
void ArbolUbicarElementos::preOrden() {
    if (this->esVacio() == estadoVacio::NO_ES_VACIO)
        this->raiz->preOrden(this->raiz);
};

// Imprime el recorrido "In-orden" del arbol.
void ArbolUbicarElementos::inOrden() {
    if (this->esVacio() == estadoVacio::NO_ES_VACIO)
        this->raiz->inOrden(this->raiz);
};

// Imprime el recorrido "Pos-orden" del arbol.
void ArbolUbicarElementos::posOrden() {
    if (this->esVacio() == estadoVacio::NO_ES_VACIO)
        this->raiz->posOrden(this->raiz);
};

// Imprime el recorrido "Nivel-orden" del arbol.
void ArbolUbicarElementos::nivelOrden() {
    if (this->esVacio() == estadoVacio::NO_ES_VACIO)
        this->raiz->nivelOrden();
};

void ArbolUbicarElementos::evaluarCuadrante(float x_min,float x_max,float y_min,float y_max,std::list<Punto_de_interes> &Elementos_en_cuadrante) {


     this->raiz->evaluarCuadrante(this->raiz,x_min,x_max,y_min,y_max,Elementos_en_cuadrante);
     
};
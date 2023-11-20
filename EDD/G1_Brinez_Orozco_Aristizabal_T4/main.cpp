#include "arbolQ.h"
#include <fstream>
#include <iostream>
#include <sstream>

int main(int argc, char *argv[]) {

    // Creamos variables.
    std::istream *archivoPreorden = &std::cin;
    std::string *nombreArchivoPBM = nullptr;
    ArbolQ arbol;
    std::string linea;
    unsigned int tamano = 0, preordenAux;
    std::vector<int> preOrdenVect;
    bool divisible = true;

    // Conseguimos parámetros.
    if (argc > 2) {
        archivoPreorden = new std::ifstream(argv[1]);
        nombreArchivoPBM = new std::string(argv[2]);
    }
    if (!(*archivoPreorden) || !(nombreArchivoPBM)) {
        std::cerr << "Error opening input stream." << std::endl;
        return (-1);
    }

    // Obtenemos datos del archivoPreorden;
    std::getline(*archivoPreorden, linea);
    std::stringstream X(linea);
    std::getline(X, linea, ' ');
    tamano = std::stoi(linea);
    std::getline(*archivoPreorden, linea);
    for(int i = 0; i < linea.length(); i++) {
        preOrdenVect.push_back(linea[i] - '0');
    }


    // Insertamos datos en el arbol QuadTree.
    for (std::vector<int>::iterator it = preOrdenVect.begin();
         it != preOrdenVect.end(); it++) {
        arbol.insertar(*it);
    }

    // Construimos la matriz.
    std::vector< std::vector<int> > imagen(tamano, std::vector<int>(tamano));
    arbol.armarMatriz(imagen, tamano);

    // Creamos y escribimos archivo.
    std::ofstream outFile(*nombreArchivoPBM);// + ".pbm");
    outFile << "P1\n";
    outFile << "# Esta es una imagen de prueba\n";
    outFile << tamano << " " << tamano << "\n";
    for(int i = 0; i < imagen.size(); i++) {
        for(int j = 0 ; j < imagen.size(); j++) {
            outFile << imagen[i][j] << " ";
        }
        outFile << std::endl;
    }
    if(outFile.good()) 
      std::cout << "\nEl archivo '" << *nombreArchivoPBM + "' has sido creado correctamente!" << std::endl << std::endl; 
    
    outFile.close();

    return 0;
};

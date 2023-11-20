#include "Grafo.h"
#include <iostream>

int main() {
  Grafo<char, int> Mirar;
  Mirar.setEsDirigido(true);
  // a b h g f c i d e
  // 1 1 7 6 5 2 8 3 4
  Mirar.insertarVertice('A'); // 1
  Mirar.insertarVertice('B'); // 1
  Mirar.insertarVertice('C'); // 2
  Mirar.insertarVertice('D'); // 3
  // Mirar.insertarVertice('E'); // 4
  // Mirar.insertarVertice('F'); // 4

  // insertar las aristas
  Mirar.insertarArista('A', 'D', 1);
  Mirar.insertarArista('B', 'A', 1);
  Mirar.insertarArista('C', 'B', 1);
  Mirar.insertarArista('D', 'C', 1);

  std::vector<std::vector<int>> mi = Mirar.getGrafo();

  for (int i = 0; i < mi.size(); i++) {
    for (int j = 0; j < mi[i].size(); j++) {
      std::cout << mi[i][j] << " ";
    }

    std::cout << "\n";
  }

  std::cout << "\n";

  // std::cout << Mirar.cantidadVertices() << std::endl;

  Mirar.esEuler();
}
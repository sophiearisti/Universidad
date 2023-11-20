#include <iostream>
#include "arbolKD.h"

int main() {
  
  ArbolKD arbolKD;

   punto p1;
   int res;
    /*do
    {
      std::cout<<"\nEje x:";
      std::cin>>p1.x;
      std::cout<<"\nEje y:";
      std::cin>>p1.y;

     arbolKD.insertar(p1);
      
     std::cout<<"\nres:";
     std::cin>>res;
      
    }while(res==1);*/

    p1.x=35;
    p1.y=40;
    arbolKD.insertar(p1);
    p1.x=50;
    p1.y=10;
    arbolKD.insertar(p1);
    p1.x=60;
    p1.y=75;
    arbolKD.insertar(p1);
    p1.x=80;
    p1.y=65;
    arbolKD.insertar(p1);
    p1.x=85;
    p1.y=15;
    arbolKD.insertar(p1);
    p1.x=5;
    p1.y=45;
    arbolKD.insertar(p1);
    p1.x=25;
    p1.y=35;
    arbolKD.insertar(p1);
    p1.x=90;
    p1.y=5;
    arbolKD.insertar(p1);

    std::cout<<"\npreorden\n";
    arbolKD.preOrden();
    std::cout<<"\ninorden\n";
    arbolKD.inOrden();
    std::cout<<"\nposorden\n";
    arbolKD.posOrden();
    std::cout<<"\nnivel orden\n";
    arbolKD.nivelOrden();

}
#include <iostream>

#include "arbolQ.h"


int main() 
{
   ArbolQ arbolQ;
  
   punto p1;
   int res;
    /*do
    {
      std::cout<<"\nEje x:";
      std::cin>>p1.x;
      std::cout<<"\nEje y:";
      std::cin>>p1.y;

     arbolQ.insertar(p1);
      
     std::cout<<"\nres:";
     std::cin>>res;
      
    }while(res==1);*/

    p1.x=35;
    p1.y=40;
    arbolQ.insertar(p1);
    p1.x=50;
    p1.y=10;
    arbolQ.insertar(p1);
    p1.x=35;
    p1.y=75;
    arbolQ.insertar(p1);
    p1.x=80;
    p1.y=65;
    arbolQ.insertar(p1);
    p1.x=85;
    p1.y=15;
    arbolQ.insertar(p1);
    p1.x=5;
    p1.y=45;
    arbolQ.insertar(p1);
    p1.x=25;
    p1.y=40;
    arbolQ.insertar(p1);
    p1.x=90;
    p1.y=5;
    arbolQ.insertar(p1);

    std::cout<<"\npreorden\n";
    arbolQ.preOrden();
    std::cout<<"\ninorden\n";
    arbolQ.inOrden();
    std::cout<<"\nposorden\n";
    arbolQ.posOrden();
    std::cout<<"\nnivel orden\n";
    arbolQ.nivelOrden();
}
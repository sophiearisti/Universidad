#ifndef Q_TREE_H
#define Q_TREE_H


#include <iostream>

//template <class T>
struct punto 
{
  int x;
  int y;

  punto& operator = (const punto &p) 
  {
    x = p.x;
    y = p.y;
    return *this;
  }

  bool operator == (const punto &p) const 
  {
    return (x == p.x && y == p.y);
  }

  friend std::ostream& operator << (std::ostream &o, const punto &p) 
  {
    o << "(" << p.x << "," << p.y << ")";
    return o;
  }
};


#endif

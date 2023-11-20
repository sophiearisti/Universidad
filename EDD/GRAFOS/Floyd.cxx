template <class T, class U>
void Grafo<T,U>::Floyd(T Inicio, T Final)
{
  std::vector<std::vector<U>> MatrizPesos=prepararMatrizPesos();

  std::vector<std::vector<int>> MatrizPredecesores=prepararMatrizPredecesores();

   for (int k = 0; k < this->grafo.size(); ++k) 
   {
        for (int i = 0; i < this->grafo.size(); ++i) 
        {
            for (int j = 0; j < this->grafo.size(); ++j) 
            {
              
                if (MatrizPesos[i][k] + MatrizPesos[k][j] < MatrizPesos[i][j] && i!=j && i!=k && j!=k) 
                {
                    MatrizPesos[i][j] = MatrizPesos[i][k] + MatrizPesos[k][j];
                    MatrizPredecesores[i][j] = k+1; 
                }
              
            }
        }
    }
/*
  std::cout << "\n";
  for (int i = 0; i < MatrizPesos.size(); i++) {
    for (int j = 0; j < MatrizPesos[i].size(); j++) {
      std::cout <<  MatrizPesos[i][j] << " ";
    }
    
    std::cout << "\n";
  }
  std::cout << "\n";

  for (int i = 0; i < MatrizPredecesores.size(); i++) {
    for (int j = 0; j < MatrizPredecesores[i].size(); j++) {
      std::cout <<  MatrizPredecesores[i][j] << " ";
    }
    
    std::cout << "\n";
  }
  */

  //despues de esto se debe reconstruir la ruta mas corta para los dos puntos recibidos
  if(MatrizPredecesores[BuscarPos(Inicio)][BuscarPos(Final)]==-1)
  {
    //no se puede llegar de inicio a final de inguna manera
    std::cout<<"\nno se puede llegar de"<<Inicio<<" a final "<<Final;
  }
  else
  {
    //se deberia construir

    std::stack<U> ruta;
    
    ruta.push(Final);

    while (Inicio != Final) {
      //OJO CON LOS -1
        Final = MatrizPredecesores[Inicio-1][Final-1];
        ruta.push(Final);
    }

    //la ruta esta desde el final hasta el inicio Ojo

    while(!ruta.empty())
    {
       std::cout<<ruta.top()<<" ";
       ruta.pop();
    }
  }

  
};

//FUNCIONA
template <class T, class U>
std::vector<std::vector<U>> Grafo<T,U>::prepararMatrizPesos()
{
  std::vector<std::vector<U>> matrizRetorno=this->grafo;
  
   for(int i=0; i<this->grafo.size();i++)
   {
     for(int j=0; j<this->grafo.size();j++)
     {
       if(i==j)
       {
         matrizRetorno[i][j]=0;
       }
       else if(matrizRetorno[i][j]==0)
       {
         matrizRetorno[i][j]=this->infinito;
       }
     }
   }
  
   return matrizRetorno;
};

//FUNCIONA
template <class T, class U>
std::vector<std::vector<int>> Grafo<T,U>::prepararMatrizPredecesores()
{
  std::vector<std::vector<int>> matrizRetorno=this->grafo;
  
   for(int i=0; i<this->grafo.size();i++)
   {
     for(int j=0; j<this->grafo.size();j++)
     {
       if(BuscarAristaPorPosicion(i, j))
       {
         matrizRetorno[i][j]=i+1;
       }
       else
       {
         matrizRetorno[i][j]=-1;
       }
     }
   }
  
   return matrizRetorno;
};

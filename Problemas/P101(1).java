/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AA;

import java.util.ArrayList;

/**
 *
 * @author ara65
 */
public class P101 {

    int[][] datos;
    boolean[] supers;
    ArrayList<Integer> solucion;
    ArrayList<Integer> auxiliar;

    int[] cotas;
    int best;


    /**
     * @param args the command line arguments
     */
  /*  public static void main(String[] args) {
        P101 p= new P101();
        String[] params={"10 4 4 99 4 4", "11 5 5 5 5 5"};
        String[] params1={"5 9 5 6 9 3 2 1 2 7 9 8 9 4 7" ,
                          "20 1 3 7 3 6 3 1 7 8 5 3 9 9 9" ,
                          "2 8 1 7 8 1 3 8 2 1 4 5 9 3 7" ,
                          "50 9 7 2 4 3 1 3 2 6 1 7 9 1 9" ,
                          "4 8 3 9 4 5 1 6 5 3 1 5 6 5 1" ,
                          "4 5 8 1 9 7 7 9 4 2 5 2 7 6 7" ,
                          "15 8 5 6 5 1 8 7 6 4 9 7 3 1 9"};
        String[] params2={"10 4 4 99 4 4 12", "11 5 5 5 5 5 15", "12 4 5 6 8 1 16" ,"7 8 9 3 7 7 7"};
        //[2,2,2,2,2]
        System.out.println(p.bestSolution(params));
        System.out.println(p.best);
        System.out.println(p.bestSolution(params2));
        System.out.println(p.best);
        System.out.println(p.bestSolution(params1));
        System.out.println(p.best);
    }*/
    
    public int suma(){
        int res=Integer.MAX_VALUE;
        int aux=0;
        for(int i=0;i<datos.length;++i){//Supermercados
            aux=0;
            for(int j=0;j<datos[i].length;++j){//Productos
                aux+=getProducto(j,i);
                if(aux>res){
                    break;
                }
            }
            res=Integer.min(res,aux);
        }
        return res;
    }
    
    /**
     * Devuelve el precio del producto prod en el super sup
     * @param prod Producto a comprobar
     * @param supermercado String del super a comprobar
     * @return 
     */
    public int getProducto(int prod, int supermercado){
        return datos[supermercado][prod];        
    }
    
    public int cota(int suma, int supers, int prod){
        int actual;
        
        actual=Integer.MAX_VALUE;
        for(int j=supers-1;j>=0;--j){//Obtiene el mínimo
            actual=Integer.min(actual,getProducto(prod,j));
        }       
        if(actual==0){
            ++actual;
        }
        return suma+actual;
    }
    
    public ArrayList<Integer> almacen(int prod,boolean[] supers, int suma){
       //Si podemos mejorar exploramos
        int aux;
      //  System.out.println(cotas[prod]+getSupers(supers));
        if ( cotas[prod]+suma < best ) {//Si se puede mejorar
            if ( prod == 0 ) { // no quedan trabajos
                if(suma <= best){
                    best = suma;
                    solucion= new ArrayList<>();
                    for(int i=auxiliar.size()-1;i>=0;--i){
                        solucion.add(auxiliar.get(i));
                    }
                }                
            } else {
                for(int i=0;i< supers.length;++i){
                    if(!supers[i]){//Si supers[i] estaba a falso
                        aux=suma;
                        supers[i]=true;
                        auxiliar.add(i+1);
                        almacen(prod-1, supers, suma+getProducto(prod,i)+ getProducto(0, i));
                        if(auxiliar.size()>0){
                            auxiliar.remove(auxiliar.size()-1);
                        }
                        supers[i]=false;
                        suma=aux;
                    }
                    else{
                        auxiliar.add(i+1);
                        almacen(prod-1, supers, suma+getProducto(prod,i));
                        if(auxiliar.size()>0){
                            auxiliar.remove(auxiliar.size()-1);
                        }
                    }
                }
            }
        }
        return solucion;        
    }
    
    public void inicializar(String[] data){
        String[] aux=data[0].split("\\p{Space}+");
        datos= new int [data.length][aux.length];
        cotas= new int[datos[0].length];

        for(int i=0;i<data.length;++i){
            for(int j=0;j<aux.length;++j){
                datos[i][j]=Integer.parseInt(aux[j]);
            }
            if(i<data.length-1){
                aux=data[i+1].split("\\p{Space}+");
            }
        }
        
        cotas[0]=0;
        for(int i=1;i<cotas.length;++i){
            cotas[i]=cota(cotas[i-1],data.length,i);
        }
    }
    
    public ArrayList<Integer> bestSolution(String[] data){
        solucion=auxiliar=new ArrayList<>();
        boolean[] supers= new boolean[data.length];
        inicializar(data);
        best=suma()+1;//Cota pesimista
        return almacen(cotas.length-1,supers,0);
       // return solucion;
    }
    
}
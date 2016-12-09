package AA;

import java.util.ArrayList;


public class P101 {
	/**
	*La primera dimension va a tener tamanyo de n supermecados, la segunda, va a tener tamanyo de m productos.
	*/
	private int[][] costesProductos; 
	private int[] costesIrSupermercado;
	//private ArrayList<Integer> costesIrSupermercado;
	//private ArrayList<ArrayList<Integer>> costesProductos;
	private boolean[] pasarSupermercado; 
	//private ArrayList<Boolean> pasarSupermercado;
	private ArrayList<Integer> supermercadosElegidos;

	private int best;
	private ArrayList<Integer> bestSuperElected;

	private int[] sumasMinimos;
	//private ArrayList<Integer> sumasMinimos;


	public P101() {
		best = Integer.MAX_VALUE;

		//costesIrSupermercado = new ArrayList<>();
		//costesProductos = new ArrayList<>();
		//pasarSupermercado = new ArrayList<>();
		supermercadosElegidos = new ArrayList<>();
		bestSuperElected = new ArrayList<>();
		//sumasMinimos = new ArrayList<>();
	}

	/////////////////
	/// SOLUCIONES DEL PROBLEMA
	/////////////////

	public ArrayList<Integer> bestSolution(String[] data) {
		init(data);
		//printSumaMinimos();
		//printArrays();

		best = pessimisticBound()+1;
		//System.out.println("Empezamos con best="+best);
		rp1(costesProductos[0].length, 0, supermercadosElegidos);

		//System.out.println("-- Hemos hecho "+it+" iteraciones --");
		//printSolucion();
		return bestSuperElected;
	}

	/*
	private void backtracking(int n, int coste, ArrayList<Integer> elecciones) {
		//Si uso arrays: Arrays.copyOf(arr)
		//printSolucion();
		if(n==0) {
			if(coste < best) {
				best = coste;
				bestSuperElected = new ArrayList<>(elecciones);
			}

			//System.out.println("TENEMOS COMO SOLUCION "+best);
		}
		else {
			//Call backtracking here
			int aux;
			for(int i=0; i<costesProductos.length; i++) {
				if(pasarSupermercado[i]) { //Si ya hemos pasado por el supermercado
					elecciones.set(n-1, i+1);
					//Actualizamos el valor de tValue para el supermercado i
					backtracking(n-1, coste 
						+ costesProductos[i][n-1], elecciones);
				}
				else { //Si no
					elecciones.set(n-1, i+1);
					//Actualizamos el valor de tValue para el supermercado i; tambien contamos el coste de ir al supermercado
					pasarSupermercado[i] = true;
					backtracking(n-1, coste 
						+ costesProductos[i][n-1] + costesIrSupermercado[i], elecciones);
					pasarSupermercado[i] = false;
				}
			}
		}
	}
	*/

	private void rp1(int n, int coste, ArrayList<Integer> elecciones) {
		//Si uso arrays: Arrays.copyOf(arr)
		//printSolucion();

		if(lowerBound2(n, coste) < best) {
			if(n==0) {
				if(coste < best) {
					best = coste;
					
					bestSuperElected = new ArrayList<>(elecciones);
					/*
					bestSuperElected.clear(); 
					for(int i=elecciones.size()-1; i>= 0; --i) {
						bestSuperElected.add(elecciones.get(i));
					}
					*/
				}
				//System.out.println("TENEMOS COMO SOLUCION "+best);
			}
			else {
				//if(lowerBound2(n, coste) < best) {
				//Call recursion here
				for(int i=0; i<costesProductos.length; i++) { //Iteramos por los supermercados
					if(pasarSupermercado[i]) { //Si ya hemos pasado por el supermercado
						elecciones.set(n-1, i+1);
						//Actualizamos el valor de coste para el supermercado i
						rp1(n-1, coste 
							+ costesProductos[i][n-1], elecciones);
					}
					else { //Si no
						elecciones.set(n-1, i+1);
						//Actualizamos el valor de coste para el supermercado i; tambien contamos el coste de ir al supermercado
						pasarSupermercado[i] = true;
						rp1(n-1, coste 
							+ costesProductos[i][n-1] + costesIrSupermercado[i], elecciones);
						pasarSupermercado[i] = false;
					}
				}
			}
		}
	}

	///////////////
	// METODOS DE COTAS
	///////////////

	private int lowerBound1(int n, int coste) {
		return coste;
	}

	private int lowerBound2(int n, int coste) {
		if(n == 0)
			return coste;
		int suma = sumasMinimos[n-1]+coste;

		return suma;
	}

	private int pessimisticBound() {
		int minimo = Integer.MAX_VALUE;
		int suma;

		for(int i=0; i<costesProductos.length; ++i) {
			suma = costesIrSupermercado[i];
			for(int j=0; j< costesProductos[i].length; j++) {
				suma += costesProductos[i][j];
			}
			minimo = Math.min(minimo, suma);
		}

		return minimo;
	}

	///////////////
	/// METODOS AUXILIARES
	///////////////
	private int bound(int sum, int nSupermarkets, int product) {
		int actual = Integer.MAX_VALUE;

		for(int i=0; i<nSupermarkets-1; ++i) {
			actual = Math.min(actual, costesProductos[i][product]);
		}

		if(actual == 0)
			++actual;

		return sum + actual;
	}


	private void init(String[] data) {
		costesProductos = new int[data.length][];
		costesIrSupermercado = new int[data.length];
		supermercadosElegidos.clear();
		bestSuperElected.clear();

		for(int i=0; i<data.length; i++) {
			String[] splited = data[i].split("\\p{Space}+");

			costesIrSupermercado[i] = Integer.parseInt(splited[0]);
			costesProductos[i] = new int[splited.length-1];

			for(int j=1; j<splited.length; j++) {
				costesProductos[i][j-1] = Integer.parseInt(splited[j]);
			}
		}

		pasarSupermercado = new boolean[costesIrSupermercado.length];
		for(int k=0; k<costesIrSupermercado.length; k++) {
			pasarSupermercado[k] = false;
		}

		for(int m=0; m<costesProductos[0].length; m++) {
			supermercadosElegidos.add(0); //Una eleccion por producto
			bestSuperElected.add(0);
		}

		//Inicializamos un vector de sumas de minimos
		sumasMinimos = new int[costesProductos[0].length];
		int suma = 0;
		for(int i=0; i<costesProductos[0].length; ++i) { //Iteramos por los productos
			int minimo=Integer.MAX_VALUE;
			for(int j=0; j<costesProductos.length; ++j) { //Iteramos por los supermercados
				//Buscamos el valor minimo para cada producto
				minimo = Math.min(minimo, costesProductos[j][i]); //Producto i del supermercado j
			}

			suma += minimo;
			sumasMinimos[i] = suma;
		}
	}

	private void printArrays() {
		System.out.println("-----------------------");
		System.out.println("-- TENEMOS "+costesIrSupermercado.length+" SUPERMERCADOS");
		System.out.println("-----------------------");

		for(int i=0; i<costesProductos.length; i++) {
			System.out.print("S"+(i+1)+": ("+costesIrSupermercado[i]+")\n\t");
			for(int j=0; j<costesProductos[i].length; j++) {
				System.out.print(costesProductos[i][j]+" ");
			}
			System.out.println();
		}

		/*
		System.out.println("-----------------------");
		System.out.println("-- HEMOS PASADO POR LOS SUPERMERCADOS");
		System.out.println("-----------------------");
		for(int k=0; k<pasarSupermercado.length; k++) {
			System.out.print(k+": "+pasarSupermercado[k]+" ");
		}
		*/

		System.out.println();
		
	}

	public void printSolucion() {
		//System.out.println("TENEMOS COMO SOLUCION "+best);

		System.out.println("-----------------------");
		System.out.println("-- HEMOS ELEGIDO LOS SUPERMERCADOS");
		System.out.println("-----------------------");
		for(int k=0; k<bestSuperElected.size(); k++) {
			System.out.println("\tProducto "+(k+1)+": "+bestSuperElected.get(k));
		}
		System.out.println();

		System.out.println("-----------------------");
		System.out.println("-- CON UN COSTE TOTAL DE "+best);
		System.out.println("-----------------------");
		System.out.println();
	}

	private void printSumaMinimos() {
		System.out.println("-----------------------");
		System.out.println("-- SUMA DE LOS MINIMOS");
		System.out.println("-----------------------");
		System.out.print("\t");
		for(int i=0; i<sumasMinimos.length; i++) {
			System.out.print(sumasMinimos[i]+" ");
		}
		System.out.println();
	}
}
package AA;

import java.util.ArrayList;

public class P083 {
	private int N; //n maximo de dias de permiso
	private int L; //Separacion minima de los dias
	private int[] M; //Calendario con preferencias
	private ArrayList<Integer> solucion;
	int[][] almacen;
	int[][] almacen2;

	public P083() {
		solucion = new ArrayList<>();
	}

	public int best(String s) {
		init(s);
		//solucion = recursiveSolution(M.size()-1, N);
		int sol = recursiveSolutionWithStorage(M.length, N);
		//solucion = restoreSolutionFromStorage();
		//solucion = iterativeSolution(M.size()-1, N);

//		printStorage();
//		System.out.println("-- Value returned: "+sol+ " --");
//
//		System.out.println("-----");
//		System.out.println("-- Solucion P083");
//		System.out.println("-----");
//		printVarValues();
//		System.out.println();

		return sol;
	}

	/////
	// INITIALIZE
	/////

	private void init(String v) {
		//System.out.println("----------");
		//System.out.println("-- Initial string:");
		//System.out.println("-- "+v);
		//System.out.println("----------");

		String[] parts = v.split(" ");

		N = Integer.parseInt(parts[0]);
		L = Integer.parseInt(parts[1]);

		M = new int[parts.length-2]; //Los dias
		for(int i=2; i<parts.length; i++) {
			M[i-2] = Integer.parseInt(parts[i]);
		}

		almacen = new int[M.length+1][N+1];
		almacen2 = new int[M.length+1][N+1];

		//printVarValues();
		//System.out.println();
	}

	/////
	// SOLUTION METHODS
	/////

	private ArrayList<Integer> recursiveSolution(int m, int n) {
		if(m < 1 || n == 0) {
			return new ArrayList<Integer>();
		}
		else {
			ArrayList<Integer> sol1, sol2;
			sol1 = recursiveSolution(m-L, n-1);
			sol1.add(m+1); //To start in 1
			sol2 = recursiveSolution(m-1, n);
			if(getSolutionValue(sol1) > getSolutionValue(sol2)) {
				//We choose the day
				return sol1;
			}
			else {
				return sol2;
			}
		}
	}

	private int recursiveSolutionWithStorage(int m, int n) {
		if(m < 1 || n == 0) {
			return 0;
		}
		else {
			if(almacen[m][n] != 0) {
				//Ya ha sido almacenado
				return almacen[m][n]; //pasamos el valor del día
			}
			else {
				int sol1 = recursiveSolutionWithStorage(m-1, n); //No se pilla
				int sol2 = recursiveSolutionWithStorage(m-L, n-1); //si se pilla el dia
				sol2 += M[m-1];

				if(sol1 > sol2) { //No se pilla
					almacen[m][n] = sol1;
					return sol1;
				}
				else {
					almacen[m][n] = sol2; //guardamos el dia
					return sol2;
				}
			}
		}
	}

	/*
	private void recursiveSolutionWithStorage(int m, int n) {
		if(m < 1 || n == 0) {
			solucion.clear();
		}
		else {
			int sol1 = recursiveSolutionWithStorageInt(m-1, n);
			int sol2 = M[m-1] + recursiveSolutionWithStorageInt(m-L, n-1);

			if(sol1 > sol2) { //No lo pillamos
				recursiveSolutionWithStorage(m-1, n);
			}
			else {
				//Si que lo pillamos
				recursiveSolutionWithStorage(m-L, n-1);
				solucion.add(m);
			}
		}
	}

	private int recursiveSolutionWithStorageInt(int m, int n) {
		//Same algoritm as recursive returning the acumulated value, but with storage
		if(m < 1 || n == 0) {
			return 0;
		}
		else {
			if(almacen[m][n] < 1) { //no ha sido rellenado
				int sol1 = recursiveSolutionWithStorageInt(m-1, n);
				int sol2 = M[m-1] + recursiveSolutionWithStorageInt(m-L, n-1);
				almacen[m][n] = Integer.max(sol1, sol2);
			}
			//Tanto si ha sido rellenado como si no, devolvemos el almacen al final
			return almacen[m][n];
		}
	}
	*/

	/*private ArrayList<Integer> iterativeSolution(int m , int n) {
		//here completes the storage
		

		//here returns the solution
	}*/

	////////
	// AUXILIAR METHODS
	////////

	private ArrayList<Integer> restoreSolutionFromStorage() {
		ArrayList<Integer> l = new ArrayList<>();
		int max_valor, dia;
		int m = M.length, n = N;

		while(m >= 0 && n >= 0 && almacen2[m][n] != 0) {
			if(almacen2[m][n] != -1) {
				l.add(0, almacen2[m][n]);
				m -= L;
				n--;
			}
			else {
				m--;
			}
		}

		return l;
	}

	private int getSolutionValue(ArrayList<Integer> l) {
		int suma = 0;
		for(int i=0; i<l.size(); i++) {
			suma += M[l.get(i)-1];
		}

		return suma;
	}

	////////
	// PRINT METHODS
	////////

	private void printVarValues() {
		System.out.println("N: "+N);
		System.out.println("L: "+L);

		System.out.print("M: ");
		for(int i=0; i<M.length; i++) {
			System.out.print(M[i]);
			if(i != M.length-1) {
				System.out.print(", ");
			}
		}
		System.out.println();

		System.out.print("solucion: ");
		for(int i=0; i<solucion.size(); i++) {
			System.out.print(solucion.get(i));
			if(i != solucion.size()-1) {
				System.out.print(", ");
			}
		}
		System.out.println();

	}

	private void printList(ArrayList<Integer> l) {
		for(int i=0; i<l.size(); ++i) {
			System.out.print(l.get(i)+" ");
		}
		System.out.println();
	}

	private void printStorage() {
		for(int i=0; i<M.length+1;i++) {
			for(int j=0; j<N+1; j++) {
				System.out.print(almacen[i][j] + " ");
			}

			System.out.println();
		}

		System.out.println("-- ALMACEN 2 --");

		for(int i=0; i<M.length+1;i++) {
			for(int j=0; j<N+1; j++) {
				System.out.print(almacen2[i][j] + " ");
			}

			System.out.println();
		}
	}


}

/*

private class tuplaDia {
	private int valor;
	private ArrayList<Integer> dias;

	public tuplaDia(int v, ArrayList<Integer> d){
		this->valor = v;
		this->dias = d;
	}

	public int getValor() {
		return valor;
	}

	public ArrayList<Integer> getDia() {
		return dias;
	}

	public void setValor(int v) {
		this->valor = v;
	}

	public void setDia(ArrayList<Integer> d) {
		this->dias = d;
	}
}

*/
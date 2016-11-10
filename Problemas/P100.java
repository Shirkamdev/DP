import java.util.ArrayList;

public class P100 {
	private int N; //n maximo de dias de permiso
	private int L; //Separacion minima de los dias
	private ArrayList<Integer> preferenciaDias;
	Integer[][] almacen;

	private ArrayList<Integer> solucion;

	public P100() {
		preferenciaDias = new ArrayList<Integer>();
		solucion = new ArrayList<Integer>();
		almacen = new int[preferenciaDias.size()][N];
	}

	private void init(String v) {
		System.out.println("----------");
		System.out.println("-- Initial string:");
		System.out.println("-- "+v);
		System.out.println("----------");

		String[] parts = v.split(" ");
		N = Integer.parseInt(parts[0]);
		L = Integer.parseInt(parts[1]);
		for(int i=2; i<parts.length; i++) {
			preferenciaDias.add(Integer.parseInt(parts[i]));
		}

		printVarValues();
		System.out.println();
	}

	public ArrayList<Integer> bestSolution(String s) {
		init(s);
		solucion = recursiveSolution(preferenciaDias.size()-1, N);

		return solucion;
	}

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

	
	/*private ArrayList<Integer> recursiveSolutionWithStorage(int m, int n) {
		if(almacen[m][n] != null) {
			return getStorageValue(m, n);
		}
		else {
			if(m < 1 || n == 0) { //TODO finish this
				almacen[m][n] = 0;
				return 0;
			}
			else {
				int sol1 = almacen[m-L][n]+preferenciaDias.get(m);

			}
		}
	}*/

	private ArrayList<Integer> iterativeSolution(int m , int n) {
		for(int i=0; i<preferenciaDias.size(); ++i) {
			for(int j=0; j<N; ++j) {
				
			}
		}
	}

	private int getStorageValue(int m, int n) {
		int suma = 0;
		while(true) {
			if(n == 0 || m == 0) break;
			if(almacen[m][n] == 0) break;
			suma += preferenciaDias.get(almacen[m][n]-1);
			m = almacen[m][n];
			n--;
		}

		return suma;
	}

	private int getSolutionValue(ArrayList<Integer> l) {
		int suma=0;
		for(int i=0; i<l.size();i++) {
			suma += preferenciaDias.get(l.get(i)-1);
		}

		return suma;
	}


	private void printVarValues() {
		System.out.println("N: "+N);
		System.out.println("L: "+L);

		System.out.print("preferenciaDias: ");
		for(int i=0; i<preferenciaDias.size(); i++) {
			System.out.print(preferenciaDias.get(i));
			if(i != preferenciaDias.size()-1) {
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


}
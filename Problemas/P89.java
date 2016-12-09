import java.util.ArrayList;

class P89 {
	private int N; //n maximo de dias de permiso
	private int L; //Separacion minima de los dias
	private ArrayList<Integer> preferenciaDias;
	private int solucion;
	Integer[][] almacen;

	public P89() {
		preferenciaDias = new ArrayList<>();
	}

	public int bestSolution(String s) {
		init(s);
		solucion = recursiveSolution(preferenciaDias.size()-1, N);
		//solucion = recursiveSolutionWithStorage(preferenciaDias.size()-1, N);
		//solucion = iterativeSolution(preferenciaDias.size()-1, N);
		return solucion;
	}

	/////////
	// SOLUTIONS
	/////////
	private int recursiveSolution(int m, int n) {
		if(m < 1 || n == 0) {
			return 0;
		}
		else {
			int sol1, sol2;
			sol1 = recursiveSolution(m-L, n-1);
			sol1 += preferenciaDias.get(m-1); //To start in 1
			sol2 = recursiveSolution(m-1, n);
			if(sol1 > sol2) {
				//We choose the day
				return sol1;
			}
			else {
				return sol2;
			}
		}
	}


	/////////
	// AUX METODS
	/////////

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

		almacen = new Integer[preferenciaDias.size()][N];

		printVarValues();
		System.out.println();
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

		System.out.println("Solucion: "+solucion);
	}

	private void printList(ArrayList<Integer> l) {
		for(int i=0; i<l.size(); ++i) {
			System.out.print(l.get(i)+" ");
		}
		System.out.println();
	}
}
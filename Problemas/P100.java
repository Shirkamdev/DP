import java.util.ArrayList;

public class P100 {
	private int N; //n maximo de dias de permiso
	private int L; //Separacion minima de los dias
	private ArrayList<Integer> preferenciaDias;

	private ArrayList<Integer> solucion;

	public P100() {
		preferenciaDias = new ArrayList<Integer>();
		solucion = new ArrayList<Integer>();
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
	}

	public ArrayList<Integer> bestSolution(String s) {
		init(s);
		solucion = recursiveSolution(N, -1);

		return solucion;
	}

	private ArrayList<Integer> recursiveSolution(int n, int last) {
		if(n == 1) { //caso base
			ArrayList<Integer> aux = new ArrayList<>();
			aux.add(maxOfList(last));

			//////
			System.out.println("\tWith last="+last+" the best choice is "+aux.get(0)+" ValueOf: "+solutionListValue(aux));
			/////

			return aux;
		}
		else {
			//TODO make this recursion great again XD
		}
	}

	private void printVarValues() {
		System.out.println("N: "+N);
		System.out.println("L: "+L);

		for(int i=0; i<preferenciaDias.size(); i++) {
			System.out.print(preferenciaDias.get(i));
			if(i != preferenciaDias.size()-1) {
				System.out.print(", ");
			}
			else {
				System.out.println();
			}
		}

		for(int i=0; i<solucion.size(); i++) {
			System.out.print(solucion.get(i));
			if(i != solucion.size()-1) {
				System.out.print(", ");
			}
			else {
				System.out.println();
			}
		}

	}

	private int maxOfList(int last) {
		int max = 0;
		for(int i=0; i<preferenciaDias.size(); ++i) {
			if(Math.abs(last - i) >= L //If the distance between days is equal or greater than L
				&& preferenciaDias.get(i) > preferenciaDias.get(max)) {
				max = i;
			}
		}

		return max+1;
	}

	private int solutionListValue(ArrayList<Integer> sol) {
		int suma = 0;
		for(int i=0; i<sol.size(); ++i) {
			suma= preferenciaDias.get(sol.get(i)-1);
		}

		return suma;
	}

	private boolean isNotIntheList(ArrayList<Integer> list, int n) {
		boolean found = false;

		for(int i=0; i<list.size() && !found; ++i) {
			if(list.get(i) == n) {
				found = true;
			}
		}

		return !found;
	}


}
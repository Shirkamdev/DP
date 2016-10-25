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

		return solucion;
	}
}
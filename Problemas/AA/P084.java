package AA;

import java.util.ArrayList;


public class P084 {
	private ArrayList<Integer> costesIrSupermercado;
	private ArrayList<ArrayList<Integer>> costesProductos;
	private ArrayList<Boolean> pasarSupermercado;

	private int best;



	public P084() {
		best = Integer.MAX_VALUE;

		costesIrSupermercado = new ArrayList<>();
		costesProductos = new ArrayList<>();
		pasarSupermercado = new ArrayList<>();
	}

	/////////////////
	/// SOLUCIONES DEL PROBLEMA
	/////////////////

	public int best(String[] data) {
		init(data);
		//printArrays();

		backtracking(costesProductos.get(0).size(), 0);

		printArrays();
		return best;
	}

	private void backtracking(int n, int coste) {
		printArrays();

		if(n==0) {
			best = Math.min(best, coste);
			System.out.println("TENEMOS COMO SOLUCION "+best);
		}
		else {
			//Call backtracking here
			int aux;
			for(int i=0; i<costesProductos.size(); i++) {
				aux = coste;
				if(pasarSupermercado.get(i)) { //Si ya hemos pasado por el supermercado
					System.out.println("Vamos al producto "+n+" con valor "+costesProductos.get(i).get(n-1) + " en el supermecado "+ i);
					System.out.println("\tLlevamos de compra "+aux);

					coste += costesProductos.get(i).get(n-1); //Actualizamos el valor de tValue para el supermercado i
					backtracking(n-1, coste);
				}
				else { //Si no
					System.out.println("Vamos al producto "+n+" con valor "+costesProductos.get(i).get(n-1) + " en el supermecado "+ i);
					System.out.println("\tLlevamos de compra "+aux);
					System.out.println("\tHemos de añadir el coste de ir al supermercado ("+costesIrSupermercado.get(i)+")");

					coste += costesProductos.get(i).get(n-1)+costesIrSupermercado.get(i); //Actualizamos el valor de tValue para el supermercado i; tambien contamos el coste de ir al supermercado
					pasarSupermercado.set(i, true);
					backtracking(n-1, coste);
					pasarSupermercado.set(i, false);
				}
				coste = aux; //Volvemos a poner el valor del supermercado i al que estaba
				System.out.println();
				System.out.println();
			}
		}
	}

	///////////////
	/// METODOS AUXILIARES
	///////////////
	private void init(String[] data) {
		costesIrSupermercado.clear();
		costesProductos.clear();
		pasarSupermercado.clear();

		for(int i=0; i<data.length; i++) {
			String[] splited = data[i].split("\\s+");

			costesIrSupermercado.add(Integer.parseInt(splited[0]));

			ArrayList<Integer> aux = new ArrayList<>();
			for(int j=1; j<splited.length; j++) {
				aux.add(Integer.parseInt(splited[j]));
			}
			costesProductos.add(aux);
		}

		for(int k=0; k<costesIrSupermercado.size(); k++) {
			pasarSupermercado.add(false);
		}
	}

	private void printArrays() {
		System.out.println("-----------------------");
		System.out.println("-- TENEMOS "+costesIrSupermercado.size()+" SUPERMERCADOS");
		System.out.println("-----------------------");

		for(int i=0; i<costesProductos.size(); i++) {
			System.out.print("S"+(i+1)+": ("+costesIrSupermercado.get(i)+")\n\t");
			for(int j=0; j<costesProductos.get(i).size(); j++) {
				System.out.print(costesProductos.get(i).get(j)+" ");
			}
			System.out.println();
		}

		
		System.out.println("-----------------------");
		System.out.println("-- HEMOS PASADO POR LOS SUPERMERCADOS");
		System.out.println("-----------------------");
		for(int k=0; k<pasarSupermercado.size(); k++) {
			System.out.print(k+": "+pasarSupermercado.get(k)+" ");
		}

		System.out.println();
		System.out.println();
		
	}

}
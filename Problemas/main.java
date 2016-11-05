import java.util.ArrayList;

public class main {
	public static void main(String[] args) {
		P100 p = new P100();
		ArrayList<Integer> solucion;

		for(int j=0; j<args.length; ++j) {
			solucion = p.bestSolution(args[j]);

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
	}
}
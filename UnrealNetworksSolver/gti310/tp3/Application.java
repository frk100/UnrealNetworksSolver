package gti310.tp3;

import gti310.tp3.parser.ConcreteParser;
import gti310.tp3.solver.ConcreteSolver;
import gti310.tp3.solver.Solution;
import gti310.tp3.writer.ConcreteWriter;

/**
 * The Application class defines a template method to call the elements to
 * solve the problem Unreal-Networks is fa�ing.
 * 
 * @author Fran�ois Caron <francois.caron.7@ens.etsmtl.ca>
 */
public class Application {

	/**
	 * The Application's entry point.
	 * 
	 * The main method makes a series of calls to find a solution to the
	 * problem. The program awaits two arguments, the complete path to the
	 * input file, and the complete path to the output file.
	 * 
	 * @param args The array containing the arguments to the files.
	 */
	public static void main(String args[]) {
		System.out.println("Unreal Networks Solver !");
		
		String entre = "Vendeur.txt";
		String sorti = "Solution.txt";
		
		 if(entre.endsWith(".txt")){
			 ConcreteParser parser = new ConcreteParser();
			Graph graph = parser.parse(entre);
			ConcreteSolver solver = new ConcreteSolver();
			Solution solution = solver.solve(graph);
			ConcreteWriter writer = new ConcreteWriter();
			writer.write(sorti , solution);
			System.out.println("\n\nVotre fichier est "+ sorti);
         } else{
        	 System.out.println("Ceci n'est pas un fichier texte (*.txt)");
         }
		
		
		
	}
}

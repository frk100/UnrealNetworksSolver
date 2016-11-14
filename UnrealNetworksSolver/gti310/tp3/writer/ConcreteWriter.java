package gti310.tp3.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import gti310.tp3.solver.Solution;

public class ConcreteWriter implements Writer<Solution> {

	@Override
	public void write(String filename, Solution output) {

		try {
			
			//creation du writer pour l'ecriture
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			
			//ecriture du point de depart
			writer.print(output.getDepart());
			System.out.print(output.getDepart());
			
			int[][] j = output.getSolution();
			
			//pour chaque noeud, on ecrit les information de la solution dans le fichier
			for (int i = 0; i < j[0].length; i++) {
				writer.println();
				System.out.println();
				if (i + 1 == output.getDepart()) {
					writer.print("-1");
					System.out.print("-1");
				} else {
					writer.print(j[0][i]);
					System.out.print(j[0][i]);
				}
				writer.print("\t");
				System.out.print("\t");

				writer.print(j[1][i]);
				System.out.print(j[1][i]);
				
			}
			//fermeture de l'ecriture
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

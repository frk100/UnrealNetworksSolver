package gti310.tp3.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gti310.tp3.Graph;

public class ConcreteParser implements Parser<Object> {

	private List<Vertex> nodes;
	private List<Edge> edges;

	@Override
	public Graph parse(String filename) {

		//initialisation
		nodes = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		
		//valeur par defaut
		int infinite = 0;
		int start = 1;
		
		BufferedReader br;
		try {
			//creation du reader
			br = new BufferedReader(new FileReader(filename));

			//lecture de la premiere contenant le nombre de noeud
			String line = br.readLine();

			//creation du bon nombre de noeud
			for (int i = 1; i <= Integer.parseInt(line); i++) {
				Vertex location = new Vertex(i);
				nodes.add(location);
			}

			//lecture de la ligne representant l'infini
			line = br.readLine();
			infinite = Integer.parseInt(line);

			//lecture de la ligne du noeud de depart (si ligne contient rien, le noeud est par defaut 1)
			line = br.readLine();
			if (line != null) {
				start = Integer.parseInt(line);	
			}
			
			
			String[] parts;
			line = br.readLine();
			//tant que la fin du fichier (le symbole $)
			while (!line.contains("$")) {	
				parts = line.split("\t");
				//ajout des liens
				addLane(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
				line = br.readLine();
			}
			
			//fermeture du fichier et du reader
			br.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier que vous desirez ouvrir n'existe pas!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//creation d'un graph
		Graph graph = new Graph(nodes, edges, infinite, start);
		return graph;

	}

	/**
	 * Ajout d'un lien entre les noeuds
	 * @param sourceLoc
	 * @param destLoc
	 * @param duration
	 */
	private void addLane(int sourceLoc, int destLoc, int duration) {
		Edge lane = new Edge(nodes.get(sourceLoc - 1), nodes.get(destLoc - 1), duration);
		edges.add(lane);
	}

}

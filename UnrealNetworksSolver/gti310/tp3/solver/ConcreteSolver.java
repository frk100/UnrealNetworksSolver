package gti310.tp3.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gti310.tp3.Graph;
import gti310.tp3.parser.Edge;
import gti310.tp3.parser.Vertex;

public class ConcreteSolver implements Solver<Graph, Solution> {

	private List<Vertex> nodes;
	private List<Edge> edges;
	private Set<Vertex> settledNodes;
	private Set<Vertex> unSettledNodes;
	private Map<Vertex, Vertex> predecessors;
	private Map<Vertex, Integer> distance;
	private int[][] solution;

	/**
	 * Execution de l'algorithme de Dijkstra, permettant la creation d'un graphe
	 * modifie et l'obtention des distances.
	 * 
	 * @param source
	 *            le noeud initial
	 */
	public void execute(Vertex source) {

		// initialisation
		settledNodes = new HashSet<Vertex>();
		unSettledNodes = new HashSet<Vertex>();
		distance = new HashMap<Vertex, Integer>();
		predecessors = new HashMap<Vertex, Vertex>();

		// ajout des valeurs de depart
		distance.put(source, 0);
		unSettledNodes.add(source);

		// pour tout les noeud, on les met dans un nouveau graph classe selon
		// les distances minimales
		while (unSettledNodes.size() > 0) {
			Vertex node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}

	/**
	 * Permet de trouver les ditances minimales d'un noeud vers ses voisins
	 * 
	 * @param node
	 */
	private void findMinimalDistances(Vertex node) {
		// obtention des voisins du noeud
		List<Vertex> adjacentNodes = getNeighbors(node);

		// Pour tout les voisins on note les distances les plus petites (et on
		// remplace si elle est plus petite)
		for (Vertex target : adjacentNodes) {
			if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
				distance.put(target, getShortestDistance(node) + getDistance(node, target));
				predecessors.put(target, node);
				unSettledNodes.add(target);
			}
		}

	}

	/**
	 * Obtention de la distance entre 2 noeuds
	 * 
	 * @param node
	 * @param target
	 * @return
	 */
	private int getDistance(Vertex node, Vertex target) {
		for (Edge edge : edges) {
			if (edge.getSource().equals(node) && edge.getDestination().equals(target)) {
				return edge.getWeight();
			}
		}
		throw new RuntimeException("Should not happen");
	}

	/**
	 * Permet d<avoir les voisins d'un noeud
	 * 
	 * @param node
	 * @return
	 */
	private List<Vertex> getNeighbors(Vertex node) {
		List<Vertex> neighbors = new ArrayList<Vertex>();

		// on verifie tout les liens entre les noeuds pour connaitre les voisins
		// du noeud
		for (Edge edge : edges) {
			if (edge.getSource().equals(node) && !isSettled(edge.getDestination())) {
				neighbors.add(edge.getDestination());
			}
		}
		return neighbors;
	}

	/**
	 * Permet l'obtention du noeud minimum
	 * 
	 * @param vertexes
	 * @return
	 */
	private Vertex getMinimum(Set<Vertex> vertexes) {
		Vertex minimum = null;
		for (Vertex vertex : vertexes) {
			if (minimum == null) {
				minimum = vertex;
			} else {
				if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
					minimum = vertex;
				}
			}
		}
		return minimum;
	}

	private boolean isSettled(Vertex vertex) {
		return settledNodes.contains(vertex);
	}

	/**
	 * Verification dans la liste distance et retour de la valeur
	 * 
	 * @param destination
	 * @return
	 */
	private int getShortestDistance(Vertex destination) {
		Integer d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	/*
	 * CEtte methode retourne le chemin le plus court entre un noeud a un autre
	 * NULL si rien existe
	 * note : si plusieurs chemin existe (meme distance) l'algorithme de Dijkstra selectionne de base 1 des chemins
	 */
	public LinkedList<Vertex> getPath(Vertex target) {
		LinkedList<Vertex> path = new LinkedList<Vertex>();
		Vertex step = target;
		// si le chemin n'existe pas, retourne null tout de suite
		if (predecessors.get(step) == null) {
			return null;
		}
		// creation du chemin
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		// Reverse, pour mettre le chemin du bon cote
		Collections.reverse(path);
		return path;
	}

	@Override
	public Solution solve(Graph input) {

		// initialisation
		edges = input.getEdges();
		nodes = input.getVertexes();
		solution = new int[2][nodes.size()];
		LinkedList<Vertex> path = new LinkedList<Vertex>();

		// creation d'un graph selon le noeud de depart
		execute(nodes.get(input.getStart() - 1));

		// pour chaque noeud, on doit entrer les donner
		for (int i = 0; i < nodes.size(); i++) {

			//si la distance est plus courte que la valeur etablit pour l'infini
			if (getShortestDistance(nodes.get(i)) < input.getInfinite()) {
				//mise de la distance 
				solution[1][i] = getShortestDistance(nodes.get(i));
				
				//calcul du chemin
				path = getPath(nodes.get(i));
				
				//si il n'y a p[as de chemin valeur est 0
				//si le noeud de depart, la valeur est le noeud de depart
				//sinon, on selectionne le noeud parent
				if (path == null)
					solution[0][i] = 0;
				else if (path.size() == 1)
					solution[0][i] = path.get(0).getId();
				else
					solution[0][i] = path.get(path.size() - 2).getId();
			} 
			//si la distance depasse la valeur etablise pour l'infini
			else {
				//parent 0 et distance d'infini (valeur)
				solution[1][i] = input.getInfinite();
				solution[0][i] = 0;
			}

		}
		
		//creation de la solution
		Solution sol = new Solution(solution, input.getStart());

		return sol;
	}

}

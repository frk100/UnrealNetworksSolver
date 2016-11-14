package gti310.tp3;

import java.util.List;

import gti310.tp3.parser.Edge;
import gti310.tp3.parser.Vertex;

public class Graph {
	private final List<Vertex> vertexes;
	private final List<Edge> edges;
	private final int infinite;
	private final int start;

	public Graph(List<Vertex> vertexes, List<Edge> edges, int infinite, int start) {
		this.vertexes = vertexes;
		this.edges = edges;
		this.infinite = infinite;
		this.start = start;
	}

	public List<Vertex> getVertexes() {
		return vertexes;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public int getInfinite() {
		return infinite;
	}
	
	public int getStart(){
		return start;
	}

}
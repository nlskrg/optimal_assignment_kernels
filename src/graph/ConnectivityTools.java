package graph;

import java.util.LinkedList;

import graph.Graph.Vertex;
import graph.properties.VertexArray;
import graph.properties.VertexProperty;

public class ConnectivityTools {
	
	/**
	 * Checks if a graph is connected.
	 * @param g a graph
	 * @return true iff g is connected
	 */
	public static boolean isConnected(Graph g) {
		if (g.getEdgeCount() < g.getVertexCount()-1)
			return false;
		
		if (g.getVertexCount() == 0)
			return true;
		
		VertexProperty<Integer> found = new VertexArray<Integer>(g);
		doDFS(g.vertices().iterator().next(), found, 1);
		for (Vertex v : g.vertices()) {
			if (found.get(v) == null) return false;
		}
		
		return true;
	}

	/**
	 * Determines the number of connected components and returns a
	 * list of representative vertices, each of which exclusively 
	 * resides in a connected component.
	 * @param g
	 * @return vertex list
	 */
	public static LinkedList<Vertex> connectedComponents(Graph g) {
		
		LinkedList<Vertex> cc = new LinkedList<Vertex>();
		VertexProperty<Integer> visited = new VertexArray<Integer>(g);
		for (Vertex v : g.vertices()) {
			visited.set(v, -1);
		}
		
		for (Vertex v : g.vertices()) {
			if (visited.get(v) == -1) { //start dfs
				cc.add(v);
				doDFS(v, visited, cc.size());
			}
		}
		
		return cc;
	}
	
	private static void doDFS(Vertex v, VertexProperty<Integer> found, int ccId) {
		found.set(v, ccId);
		for (Vertex w : v.neighbors()) {
			if (found.get(w)==null) {
				doDFS(w, found, ccId);
			}
		}
	}


}

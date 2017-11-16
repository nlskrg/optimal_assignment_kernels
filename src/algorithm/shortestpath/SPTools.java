package algorithm.shortestpath;

import java.util.LinkedList;

import datastructure.Matrix;
import datastructure.Pair;
import graph.AdjMatrixGraph;
import graph.ConnectivityTools;
import graph.Digraph;
import graph.Graph;
import graph.Graph.Edge;
import graph.Graph.Vertex;
import graph.LGraph;
import graph.properties.EdgeArray;
import graph.properties.EdgeProperty;
import graph.properties.VertexArray;

/**
 * Computes shortest paths. All pair shortest path is computed by BFS
 * in case of unweighted graphs and by the Floyd-Warshall algorithm otherwise
 * with running time O(n(n+m)) and O(n^3), respectively.
 * 
 * @author kriege
 *
 */
public class SPTools {
	
	private static AllPairShortestPath apspModule = new AllPairShortestPath() {
		FloydWarshallAPSP fw = new FloydWarshallAPSP();
		BreadthFirstSearchAPSP bfs = new BreadthFirstSearchAPSP();
				
		@Override
		public Matrix<Integer> computeShortestPaths(Graph g, EdgeProperty<Integer> weights) {
			return fw.computeShortestPaths(g, weights);
		}
		
		@Override
		public Matrix<Integer> computeShortestPaths(Graph g) {
			return bfs.computeShortestPaths(g);
		}
	};
	
	/**
	 * Note: vertex labels will not be cloned; the object is the same as stored in lg!
	 * @param g
	 * @return result from type AdjMatrixGraph (high memory consumption, but some operations are faster!
	 */
	public static <V> LGraph<V,Integer> getAPSPTransformation(LGraph<V, ?> lg) {
		Graph g = lg.getGraph();
		if (g instanceof Digraph) {
			throw new UnsupportedOperationException("This opertion is currently not supported for directed graphs.");
		} else {
			Matrix<Integer> dist = apspModule.computeShortestPaths(g);
			int n = g.getVertexCount();
			AdjMatrixGraph r = new AdjMatrixGraph(n, n*(n-1));
			EdgeArray<Integer> rl = new EdgeArray<Integer>(r,n*(n-1));
			for (int i=0; i<g.getVertexCount(); i++) {
				Vertex v = r.createVertex();
				assert(v.getIndex() == i);
			}
			for (int i=0; i<n; i++) {
				for (int j=i+1; j<n; j++) {
					if (dist.get(i, j) != Integer.MAX_VALUE) {
						Edge e = r.createEdge(r.getVertex(i), r.getVertex(j));
						rl.set(e, dist.get(i, j));
					}
				}
			}
			
			return new LGraph<V, Integer>(r, lg.getVertexLabel(), rl);
		}
	}
	
	/**
	 * Computes the diameter of an unweighted connected graph in O(|V|+|E|)
	 * @return the diameter
	 */
	public static int getDiameter(Graph g) {
		
		int diam = 0;
		for (Vertex start : ConnectivityTools.connectedComponents(g)) {
			Pair<Vertex, Integer> clp = computeLongestPath(g, start);
			start = clp.getFirst();
			clp = computeLongestPath(g, start);
			diam = Math.max(diam, clp.getSecond());
		}
		
		return diam;
	}
	
	/**
	 * Computes the length of a longest shortest path in a graph starting from 
	 * a specified vertex by BFS.
	 * @param g 
	 * @param start
	 * @return the longest path length and a vertex such that the shortest 
	 * path between start and this vertex has this length.
	 */
	public static Pair<Vertex, Integer> computeLongestPath(Graph g, Vertex start) {
		VertexArray<Boolean> visited = new VertexArray<Boolean>(g);
		for (Vertex v : g.vertices()) {
			visited.set(v, false);
		}
		LinkedList<Vertex> level = new LinkedList<Vertex>();
		LinkedList<Vertex> nextLevel = new LinkedList<Vertex>();
		int depth = 0;
		level.add(start);
		Vertex u = start; // the vertex considered last
		while (!(level.isEmpty() && nextLevel.isEmpty())) {
			if (level.isEmpty()) {
				// swap
				LinkedList<Vertex> tmp = level;
				level = nextLevel;
				nextLevel = tmp;
				depth++;
			}
			u = level.removeFirst();
			visited.set(u, true);
			for (Vertex v : u.neighbors()) {
				if (!visited.get(v)) {
					nextLevel.add(v);
				}
			}
		}
		return new Pair<Vertex, Integer>(u,depth);
	}

}

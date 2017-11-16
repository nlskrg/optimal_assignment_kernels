package algorithm.shortestpath;

import datastructure.FullMatrix;
import datastructure.Matrix;
import graph.Digraph;
import graph.Digraph.DiEdge;
import graph.Graph;
import graph.Graph.Edge;
import graph.properties.EdgeProperty;

/**
 * Implements the Floyd-Warshall algorithm; runtime O(|V|^3).
 * 
 * @author kriege
 *
 */
// TODO optimize for undirected graph, e.g. using symmetric matrix
public class FloydWarshallAPSP implements AllPairShortestPath {

	
	public Matrix<Integer> computeShortestPaths(Graph g) {
		Matrix<Integer> dist = createInitialMatrix(g, null);
		return floydWarshall(dist, g);
	}

	public Matrix<Integer> computeShortestPaths(Graph g, EdgeProperty<Integer> weights) {
		Matrix<Integer> dist = createInitialMatrix(g, weights);
		return floydWarshall(dist, g);
	}

	
	private Matrix<Integer> floydWarshall(Matrix<Integer> dist, Graph g) {
		int n = g.getVertexCount();
		for (int k=0; k<n; k++)
			for (int i=0; i<n; i++)
				for (int j=0; j<n; j++)
					if (dist.get(i, k)==Integer.MAX_VALUE || dist.get(k, j)==Integer.MAX_VALUE)
						continue;
					else
						dist.set(i, j, Math.min(dist.get(i, j), dist.get(i, k)+dist.get(k, j)));		
		
		return dist;
	}

	/**
	 * Creates initial distance matrices.
	 * @param g
	 * @param weights distances; if null all edges are considered to have distance 1
	 * @return
	 */
	private Matrix<Integer> createInitialMatrix(Graph g, EdgeProperty<Integer> weights) {
		int n = g.getVertexCount();
		Matrix<Integer> dist = new FullMatrix<Integer>(n, n);
		dist.fill(Integer.MAX_VALUE);

		for (int i=0; i<n; i++)
			dist.set(i, i, 0);

		if (weights == null) {
			if (g instanceof Digraph) {
				Digraph dg = (Digraph)g;
				for (DiEdge e : dg.edges()) {
					int iU = e.getSourceVertex().getIndex();
					int iV = e.getTargetVertex().getIndex();
					dist.set(iU, iV, 1);
				}
			} else {
				for (Edge e : g.edges()) {
					int iU = e.getFirstVertex().getIndex();
					int iV = e.getSecondVertex().getIndex();
					dist.set(iU, iV, 1);
					dist.set(iV, iU, 1);
				}
			}
		} else {
			if (g instanceof Digraph) {
				Digraph dg = (Digraph)g;
				for (DiEdge e : dg.edges()) {
					int iU = e.getSourceVertex().getIndex();
					int iV = e.getTargetVertex().getIndex();
					dist.set(iU, iV, weights.get(e));
				}
			} else {
				for (Edge e : g.edges()) {
					int iU = e.getFirstVertex().getIndex();
					int iV = e.getSecondVertex().getIndex();
					Integer w = weights.get(e);
					dist.set(iU, iV, w);
					dist.set(iV, iU, w);
				}
			}			
		}

		return dist;
	}

}

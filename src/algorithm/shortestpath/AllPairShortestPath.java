package algorithm.shortestpath;

import datastructure.Matrix;
import graph.Graph;
import graph.properties.EdgeProperty;

// TODO support continues values like doubles etc.
public interface AllPairShortestPath {
	
	/**
	 * Note: The matrix is indexed by the indices of the vertices of g.
	 * @param g
	 * @return
	 */
	public Matrix<Integer> computeShortestPaths(Graph g);
	public Matrix<Integer> computeShortestPaths(Graph g, EdgeProperty<Integer> weights);
	
}

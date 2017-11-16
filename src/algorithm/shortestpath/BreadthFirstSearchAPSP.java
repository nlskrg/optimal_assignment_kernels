package algorithm.shortestpath;

import java.util.LinkedList;

import datastructure.FullMatrix;
import datastructure.Matrix;
import graph.Graph;
import graph.Graph.Vertex;
import graph.properties.EdgeProperty;

public class BreadthFirstSearchAPSP implements AllPairShortestPath {

	@Override
	public Matrix<Integer> computeShortestPaths(Graph g) {
		
		int n = g.getVertexCount();
		Matrix<Integer> dist = new FullMatrix<Integer>(n, n);
		dist.fill(Integer.MAX_VALUE);

		for (Vertex v : g.vertices()) {
			LinkedList<Vertex> level = new LinkedList<>();
			LinkedList<Vertex> nextLevel = new LinkedList<>();
			boolean[] visited = new boolean[n];
			int distance = 0;
			level.add(v);
			visited[v.getIndex()] = true;
			while (!level.isEmpty()) {
				while (!level.isEmpty()) {
					Vertex w = level.pop();
					dist.set(v.getIndex(), w.getIndex(), distance);
					for (Vertex x : w.neighbors()) {
						if (!visited[x.getIndex()]) {
							nextLevel.add(x);
							visited[x.getIndex()] = true;
						}
					}
				}
				// swap
				LinkedList<Vertex> tmp = level;
				level = nextLevel;
				nextLevel = tmp;
				distance++;
			}
		}
		
		return dist;
	}
	

	@Override
	public Matrix<Integer> computeShortestPaths(Graph g, EdgeProperty<Integer> weights) {
		throw new UnsupportedOperationException("BFS does not support weights for shortest path computation.");
	}

}

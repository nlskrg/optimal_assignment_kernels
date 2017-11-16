package graph;

import java.util.ArrayList;

/**
 * Just like AdjListGraph but features an additional 
 * adjacency matrix to speed up the operations
 * hasEdge() and getEdge(u,v).
 * 
 * @author kriege
 *
 */
public class AdjMatrixGraph extends AdjListGraph {
	
	// TODO half the matrix for undirected (simple?) graphs
	// use single ArrayList + index calculation
	private ArrayList<ArrayList<AdjListEdge>> matrix;
	
	public AdjMatrixGraph() {
		super();
		matrix = new ArrayList<ArrayList<AdjListEdge>>();
	}
	
	public AdjMatrixGraph(int n, int m) {
		super(n, m);
		matrix = new ArrayList<ArrayList<AdjListEdge>>();
		assureMatrixCapacity(n);
	}
	
	private void assureMatrixCapacity(int n) {
		if (matrix.size() >= n) return;
		
		while (matrix.size() < n) {
			matrix.add(new ArrayList<AdjListEdge>());
		}
		for (ArrayList<AdjListEdge> a : matrix) {
			while (a.size() < n) {
				a.add(null);
			}
		}
	}
	
	/**
	 * Note: This method has runtime O(1)
	 */
	public AdjListEdge getEdge(Vertex u, Vertex v) {
		return (AdjListEdge)matrix.get(u.getIndex()).get(v.getIndex());
	}

	public AdjListVertex createVertex() {
		assureMatrixCapacity(super.getVertexCount()+1);
		return super.createVertex();
	}
	
	public AdjListEdge createEdge(Vertex u, Vertex v) {
		AdjListEdge e = super.createEdge(u, v);
		matrix.get(u.getIndex()).set(v.getIndex(), e);
		matrix.get(v.getIndex()).set(u.getIndex(), e);
		return e;
	}
	
//	public void deleteEdge(Edge e) {
//		super.deleteEdge(e);
//		Vertex u = e.getFirstVertex();
//		Vertex v = e.getSecondVertex();
//		matrix.get(u.getIndex()).set(v.getIndex(), null);
//		matrix.get(v.getIndex()).set(u.getIndex(), null);
//	}
	
}
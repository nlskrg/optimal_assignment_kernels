package graph;

import concepts.AttributedObject;

/**
 * The interface for general graphs G=(V,E) consisting of a set
 * of vertices V and a set of edges E.
 */
// TODO: add <V extends Vertex, E extends Edge> to avoid casting!?
public interface Graph extends AttributedObject {
	
	/**
	 * Allows iterating over the set of vertices.
	 */
	public Iterable<? extends Vertex> vertices();
	
	/**
	 * Allows iterating over the set of edges.
	 */
	public Iterable<? extends Edge> edges();
	
	/**
	 * Returns the vertex with the specified index.
	 */
	public Vertex getVertex(int index);
	
	/**
	 * Returns the edge with the specified index.
	 */
	public Edge getEdge(int index);
	
	/**
	 * Returns the number of nodes of the graph.
	 */
	public int getVertexCount();
	
	/**
	 * Returns the number of edges of the graph.
	 */
	public int getEdgeCount();
	
	/**
	 * Returns true iff the specified edge exists.
	 * Note: For undirected graphs the order of u and v
	 * is irrelevant.
	 */
	// TODO move note to sub-interface
	public boolean hasEdge(Vertex u, Vertex v);
	
	/**
	 * Returns the specified edge.
	 */
	public Edge getEdge(Vertex u, Vertex v);
	
	/**
	 * An interface for general graph elements (nodes and edges).
	 */
	// TODO getGraph()?
	public interface Element {
		
		/**
		 * Returns the index of this graph element.
		 */
		public int getIndex();
	}
	
	/**
	 * An interface representing a node of a graph.
	 */
	public interface Vertex extends Element {
		
		/**
		 * Allows iterating over the set of incident edges.
		 * 
		 * Note: A self-loop occurs twice in the list!
		 */
		public Iterable<? extends Edge> edges();
		
		/**
		 * Allows iterating of the set of adjacent nodes.
		 * 
		 * Note: For each self-loop a vertex is a neighbor of 
		 * itself two times!
		 */
		public Iterable<? extends Vertex> neighbors();
		
		/**
		 * The number of incident edges.
		 */
		public int getDegree();
	}

	/**
	 * An interface representing an edge connecting
	 * two nodes.
	 */
	public interface Edge extends Element {
		public Vertex getFirstVertex();
		public Vertex getSecondVertex();
		
		/**
		 * Returns the opposite node.
		 * @param v a node that is incident to this edge
		 * @return the other incident node
		 */
		public Vertex getOppositeVertex(Vertex v);
	}
}

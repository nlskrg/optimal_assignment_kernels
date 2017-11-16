package graph;

/**
 * Represents graphs that can be extended, i.e. vertices
 * and edge can be added.
 * 
 * @see EditableGraph
 * 
 * @author kriege
 */
public interface ExtendibleGraph extends Graph {
	
	/**
	 * Creates a new vertex. Implementing classes must notify
	 * all registered vertex observers.
	 * @return the created vertex
	 */
	public Vertex createVertex();
	
	/**
	 * Creates a new edge connecting the two specified vertices.
	 * Implementing classes must notify all registered edge
	 * observers.
	 * @param u the first vertex
	 * @param v the second vertex
	 * @return the edge connecting u and v
	 */
	public Edge createEdge(Vertex u, Vertex v);
	
	/**
	 * Returns the index that would be assigned to the
	 * vertex created next, i.e. the maximum index that
	 * has bee assigned plus one.
	 */
	public int getNextVertexIndex();

	/**
	 * Returns the index that would be assigned to the
	 * edge created next, i.e. the maximum index that
	 * has bee assigned plus one.
	 */
	public int getNextEdgeIndex();
	
	/**
	 * Adds a vertex observer to be notified on vertex 
	 * creation/deletion. 
	 * @param o the observer
	 */
	public void addVertexObserver(GraphObserver<Vertex> o);

	/**
	 * Removes a vertex observer.
	 * @param o the observer that should be removed
	 * @return true iff o was removed successfully
	 */
	public boolean removeVertexObserver(GraphObserver<Vertex> o);
	
	/**
	 * Adds an edge observer to be notified on edge
	 * creation/deletion. 
	 * @param o the observer
	 */
	public void addEdgeObserver(GraphObserver<Edge> o);
	
	/**
	 * Removes an edge observer.
	 * @param o the observer that should be removed
	 * @return true iff o was removed successfully
	 */
	public boolean removeEdgeObserver(GraphObserver<Edge> o);

	/**
	 * Implementing classes can be registered to an
	 * editable graph and notified on vertex/edge change 
	 * events.
	 * 
	 * @author kriege
	 */
	public static interface GraphObserver<T extends Graph.Element> {
		public void created(T e);
		public void deleted(T e);
	}

}

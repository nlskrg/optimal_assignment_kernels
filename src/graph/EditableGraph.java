package graph;

/**
 * Represents graphs that can be modified, i.e. vertices
 * and edge can be added and deleted.
 * 
 * @author kriege
 */
public interface EditableGraph extends ExtendibleGraph {
	
	
	/**
	 * Deletes the specified vertex and all adjacent edges.
	 * Implementing classes must notify all registered vertex
	 * observers.
	 * @param v the vertex that should be deleted
	 */
	public void deleteVertex(Vertex v);

	/**
	 * Deletes the specified edge.
	 * Implementing classes must notify all registered edge
	 * observers.
	 * @param e the edge that should be deleted
	 */
	public void deleteEdge(Edge e);
	
}

package graph;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * The same as AdjListEditableGraph, but provides fast iteration
 * for edges() and vertices().
 * 
 * @author kriege
 *
 */
public class AdjListEditable2Graph extends AdjListGraph implements EditableGraph {
	
	LinkedList<AdjListVertex> llVertices;
	LinkedList<AdjListEdge> llEdges;
	
	public AdjListEditable2Graph() {
		super();
		llVertices = new LinkedList<AdjListVertex>();
		llEdges = new LinkedList<AdjListEdge>();
	}
	
	public AdjListEditable2Graph(int n, int m) {
		super(n,m);
		llVertices = new LinkedList<AdjListVertex>();
		llEdges = new LinkedList<AdjListEdge>();
	}

	public Iterable<AdjListEdge> edges() {
		return llEdges;
	}

	public Iterable<AdjListVertex> vertices() {
		return llVertices;
	}

	public int getVertexCount() {
		return llVertices.size();
	}
	
	public int getEdgeCount() {
		return llEdges.size();
	}
	
	public int getNextVertexIndex() {
		return vertices.size();
	}

	public int getNextEdgeIndex() {
		return edges.size();
	}
	
	public AdjListVertex createVertex() {
		AdjListVertex v = super.createVertex();
		llVertices.addFirst(v);
		return v;
	}
	
	public AdjListEdge createEdge(Vertex u, Vertex v) {
		AdjListEdge e = super.createEdge(u, v);
		llEdges.addFirst(e);
		return e;
	}
	
	public void deleteVertex(Vertex v) {
		vertices.set(v.getIndex(), null);
		ArrayList<AdjListEdge> es = ((AdjListVertex)v).edges();
		while (!es.isEmpty()) {
			deleteEdge(es.get(0));
		}
		llVertices.remove(v);
		
		notifyVertexDeleted(v);
	}
	
	public void deleteEdge(Edge e) {
		edges.set(e.getIndex(), null);
		((AdjListVertex)e.getFirstVertex()).removeEdge((AdjListEdge)e);
		((AdjListVertex)e.getSecondVertex()).removeEdge((AdjListEdge)e);
		llEdges.remove(e);
		
		notifyEdgeDeleted(e);
	}
}
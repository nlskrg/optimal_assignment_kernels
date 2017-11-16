package graph.properties;

import graph.ExtendibleGraph;
import graph.Graph;
import graph.Graph.Vertex;


public class VertexArray<T> extends GraphArray<Vertex, T> implements VertexProperty<T> {
	
	public VertexArray(Graph g, boolean grow) {
		super(g);
		if (grow) assureAdjustment(g);
	}

	public VertexArray(Graph g) {
		this(g, false);
	}

	public VertexArray(Graph g, int n, boolean grow) {
		super(g, n);
		if (grow) assureAdjustment(g);
	}

	public VertexArray(Graph g, int n) {
		this(g, n, false);
	}
	
	public VertexArray(VertexArray<T> va) {
		super(va);
//		assureAdjustment(g);
	}
	
	protected void assureCapacity() {
		if (g instanceof ExtendibleGraph)
			assureCapacity(((ExtendibleGraph)g).getNextVertexIndex());
		else
			assureCapacity(g.getVertexCount());
	}
	
	private void assureAdjustment(Graph g) {
		if (g instanceof ExtendibleGraph) {
			((ExtendibleGraph)g).addVertexObserver(this);
		}
	}
}

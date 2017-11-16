package graph.properties;

import graph.ExtendibleGraph;
import graph.Graph;
import graph.Graph.Edge;

public class EdgeArray<T> extends GraphArray<Edge, T> implements EdgeProperty<T> {
	
	public EdgeArray(Graph g, boolean grow) {
		super(g);
		if (grow) assureAdjustment(g);
	}
	
	public EdgeArray(Graph g) {
		this(g, false);
	}
	
	public EdgeArray(Graph g, int n, boolean grow) {
		super(g, n);
		if (grow) assureAdjustment(g);
	}
	
	public EdgeArray(Graph g, int n) {
		this(g, n, false);
	}
	
	public EdgeArray(EdgeArray<T> ea) {
		super(ea);
//		assureAdjustment(g);
	}

	protected void assureCapacity() {
		assureCapacity(((ExtendibleGraph)g).getNextEdgeIndex());
	}
	
	private void assureAdjustment(Graph g) {
		if (g instanceof ExtendibleGraph) {
			((ExtendibleGraph)g).addEdgeObserver(this);
		}
	}
}

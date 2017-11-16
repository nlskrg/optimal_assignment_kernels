package graph.properties;

import java.util.ArrayList;

import graph.ExtendibleGraph.GraphObserver;
import graph.Graph;

public abstract class GraphArray<E extends Graph.Element,T> implements GraphObserver<E> {

	protected Graph g;
	protected ArrayList<T> values;
	
	public GraphArray(Graph g) {
		this.g=g;
		this.values = new ArrayList<T>();
		assureCapacity();
	}
	
	public GraphArray(Graph g, int n) {
		this.g=g;
		this.values = new ArrayList<T>();
		assureCapacity(n);
	}
	
	/**
	 * Copy constructor.
	 */
	@SuppressWarnings("unchecked")
	public GraphArray(GraphArray<E,T> ga) {
		this.g = ga.g;
		this.values = (ArrayList<T>)ga.values.clone();
	}

	public void set(E e, T value) {
		values.set(e.getIndex(), value);
	}
	
	public T get(E e) {
		return values.get(e.getIndex());
	}

	public void created(E e) {
		assureCapacity();
	}
	
	public void deleted(E e) {
		values.set(e.getIndex(), null);
	}

	public Graph getGraph() {
		return g;
	}
	
	protected void assureCapacity(int n) {
		while (values.size() < n) {
			values.add(null);
		}
	}
	
	protected abstract void assureCapacity();
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<values.size(); i++) {
			sb.append(i);
			sb.append(':');
			sb.append(values.get(i));
			sb.append(' ');
		}
		return sb.toString();
	}
}

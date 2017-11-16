package graph;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import graph.Graph.Edge;
import graph.Graph.Vertex;
import graph.properties.EdgeArray;
import graph.properties.VertexArray;

/**
 * Stores a graph together with its vertex and edge labels.
 * 
 * @author kriege
 *
 * @param <V> vertex label type
 * @param <E> edge label type
 */
public class AbstractLGraph<G extends Graph,V,E> {
	
	protected G graph;
	protected VertexArray<V> vertexLabel;
	protected EdgeArray<E> edgeLabel;
	
	public AbstractLGraph(G graph, VertexArray<V> vertexLabel, EdgeArray<E> edgeLabel) {
		this.graph = graph;
		this.vertexLabel = vertexLabel;
		this.edgeLabel = edgeLabel;
	}
	
	public void setGraph(G graph) {
		this.graph = graph;
	}
	
	public G getGraph() {
		return graph;
	}
	
	public void setVertexLabel(VertexArray<V> vertexLabel) {
		this.vertexLabel = vertexLabel;
	}
	
	public VertexArray<V> getVertexLabel() {
		return vertexLabel;
	}
	
	public void setEdgeLabel(EdgeArray<E> edgeLabel) {
		this.edgeLabel = edgeLabel;
	}
	
	public EdgeArray<E> getEdgeLabel() {
		return edgeLabel;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AbstractLGraph<?,?,?>) {
			AbstractLGraph<?,?,?> lg = (AbstractLGraph<?,?,?>)obj;
			return this.graph.equals(lg.graph) &&
			       this.vertexLabel.equals(lg.vertexLabel) &&
			       this.edgeLabel.equals(lg.edgeLabel);
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return graph.hashCode()+vertexLabel.hashCode()+edgeLabel.hashCode();
	}
	
	/**
	 * Returns the same graph where the labels are converted to strings.
	 * @see String#valueOf(Object)
	 * @return
	 */
	public LGraph<String, String> createStringLGraph() {
		VertexArray<String> va = new VertexArray<String>(graph);
		EdgeArray<String> ea = new EdgeArray<String>(graph);
		
		for (Vertex v : graph.vertices()) {
			va.set(v, String.valueOf(vertexLabel.get(v)));
		}
		
		for (Edge e : graph.edges()) {
			ea.set(e, String.valueOf(edgeLabel.get(e)));
		}
		return new LGraph<String, String>(graph, va, ea);
	}
	
	/**
	 * Provides a Collection<Graph>-view based on the given Collection<LabeledGraph>. 
	 * @param graphs
	 * @return
	 */
	public static Collection<Graph> toGraphCollection(final Collection<? extends AbstractLGraph<?,?,?>> graphs) {
		return new AbstractCollection<Graph>() {

			public Iterator<Graph> iterator() {
				return new Iterator<Graph>() {
					Iterator<? extends AbstractLGraph<?,?,?>> it = graphs.iterator();
					public boolean hasNext() {
						return it.hasNext();
					}
					public Graph next() {
						return it.next().getGraph();
					}
					public void remove() {
						throw new UnsupportedOperationException();						
					}
				};
			}

			public int size() {
				return graphs.size();
			}
		};
	}
	
	public int getVertexLabelCount() {
		HashSet<V> labels = new HashSet<>();
		for (Vertex v : graph.vertices()) {
			labels.add(vertexLabel.get(v));
		}
		return labels.size();
	}
}
